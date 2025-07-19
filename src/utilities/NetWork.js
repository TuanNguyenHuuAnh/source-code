import React, {Component} from 'react';
import {
    ACCESS_TOKEN,
    API_GET_NETWORK_BY_TYPE,
    AUTHENTICATION,
    CLIENT_ID,
    CLIENT_PROFILE,
    FE_BASE_URL,
    HEADOFFICE_LAT,
    HEADOFFICE_LNG,
    PageScreen,
    USER_LOGIN
} from '../constants';
import '../common/Common.css';
import {cpCallMAGP, CPSaveLog, getListByPath} from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import queryString from 'query-string';
import {getDeviceId, getSession, trackingEvent} from '../util/common';
import {Helmet} from "react-helmet";
import {isEmpty} from "lodash";
import { Link } from "react-router-dom";


class NetWork extends Component {
    constructor(props) {
        super(props);
        this.state = {
            UserProfile: null,
            jsonRequest: {
                URL: 'GetMapMarker',
                JsonInput: {
                    jsonDataInput: {
                        APIToken: '90d58b959d954755880b92cdf022f625',
                        AgentID: '',
                        DeviceName: 'Samsung_SM-A125F-Android-11',
                        Distance: '3',
                        Lat: Number(HEADOFFICE_LAT),
                        Lng: Number(HEADOFFICE_LNG),
                        Password: '',
                        Project: 'mAGP',
                        Search: '',
                        TypeOffice: 'DLVN Office'
                    }
                }

            }, getMapMarkerResult: null,
            clientProfile: null,
            isLogin: null,
            accountRole: null,
            searchcontent: '',
            searchTypeOffice: 'DLVN Office',
            searchResult: false,
            searchLoading: false,
            postback: false,
            defaultCenter: {lat: Number(HEADOFFICE_LAT), lng: Number(HEADOFFICE_LNG)},
            defaultZoom: 15,
            markerlocation: null,
            markerpositions: null,
            markericon: '/img/icon/10.1/mapOffice.svg',
            iconid: null,
            renderMeta: false,
            targetTabId: 'btnDLVNOffice',
        };

        this.handle_Click = this.handle_Click.bind(this);
    }

    getCurrentLocationBeforeGetListOfNetworks() {
        this.getListOfNetworks(this.state.searchcontent, "Hospital");
    }

    handle_Click(event) {

        let jsonState;
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        if (target.id && (target.id === 'btnDLVNOffice' || target.id === 'pDLVNOffice')) {
            jsonState = this.state;
            jsonState.searchLoading = true;
            jsonState.markerlocation = null;
            jsonState.markerpositions = null;
            jsonState.searchcontent = "";
            jsonState.targetTabId = target.id;
            this.setState(jsonState);
            this.callAPISearch(this.state.searchcontent, "DLVN Office");
        } else if (target.id && (target.id === 'bnHospital' || target.id === 'pHospital')) {
            jsonState = this.state;
            jsonState.searchLoading = true;
            jsonState.markerlocation = null;
            jsonState.markerpositions = null;
            jsonState.searchcontent = "";
            jsonState.targetTabId = target.id;
            this.setState(jsonState);
            this.getCurrentLocationBeforeGetListOfNetworks();
        } else if (target.id && (target.id === 'btnPostOffice' || target.id === 'pPostOffice')) {
            jsonState = this.state;
            jsonState.searchLoading = true;
            jsonState.markerlocation = null;
            jsonState.markerpositions = null;
            jsonState.searchcontent = "";
            jsonState.targetTabId = target.id;
            this.setState(jsonState);
            this.callAPISearch(this.state.searchcontent, "PostOffice");
        } else if (target.id && target.id === 'txtSearchContent') {
            if (inputValue.trim() === "") {
                document.getElementById("btnSearch").className = "btn btn-primary disabled";
                document.getElementById("btnSearch").disabled = true;
            } else {
                document.getElementById("btnSearch").className = "btn btn-primary";
                document.getElementById("btnSearch").disabled = false;
            }
            this.setState({
                searchcontent: inputValue
            });
        } else if (target.id && target.id === 'btnSearch') {
            if (this.state.searchcontent === "") {
                return;
            }
            jsonState = this.state;
            jsonState.searchLoading = true;
            jsonState.markerlocation = null;
            jsonState.markerpositions = null;

            this.setState(jsonState);
            if (jsonState.targetTabId === "bnHospital") {
                this.getCurrentLocationBeforeGetListOfNetworks();
            } else {
                this.callAPISearch(this.state.searchcontent, this.state.searchTypeOffice);
            }
        } else {
            jsonState = this.state;
            jsonState.searchLoading = true
            this.setState(jsonState);
        }

    }

    Marker_Click(mLat, mLng, mName) {
        window.scrollTo(0, 0);
        const jsonState = this.state;
        const positions = {lat: Number(mLat), lng: Number(mLng)};
        jsonState.markerlocation = positions;
        this.setState(jsonState);

    }

    getListOfNetworks = async (searchName, searchTypeOffice, page = 0, pageSize = 100, type = "BLVP") => {
        const {state} = this;

        if (state.searchTypeOffice !== searchTypeOffice) {
            this.setState({searchTypeOffice});
        }

        const jsonRequest = state.jsonRequest;
        jsonRequest.JsonInput.jsonDataInput.Search = searchName;
        jsonRequest.JsonInput.jsonDataInput.TypeOffice = searchTypeOffice;

        if (state.defaultCenter) {
            const {lat, lng} = state.defaultCenter;
            jsonRequest.JsonInput.jsonDataInput.Lat = lat;
            jsonRequest.JsonInput.jsonDataInput.Lng = lng;
        }

        this.setState({postback: true, jsonRequest});

        try {
            const res = await getListByPath(`${API_GET_NETWORK_BY_TYPE}/${type}/${page}/${pageSize}/${this.state.defaultCenter.lat}/${this.state.defaultCenter.lng}/5/${searchName ? searchName : "null"}`);

            if (res.statusCode === 200 && res.statusMessages === 'success' && res.data) {
                const {datas} = res.data;
                const positions = datas?.map(item => ({lat: Number(item.latitude), lng: Number(item.longtitude)}));

                this.setState({
                    searchResult: !isEmpty(datas),
                    getMapMarkerResult: {
                        Result: "true",
                        ErrLog: "",
                        dtProposal: datas?.map(item => ({
                            ID: item.id,
                            Name: item.name,
                            Address: item.address,
                            Type: item.type,
                            Phone: item.phone,
                            Fax: item.fax,
                            Distance: null, // Assuming no distance information from datas
                            Lat: item.latitude,
                            Lng: item.longtitude
                        })),
                    },
                    markerpositions: positions,
                    searchLoading: false,
                });
            }
        } catch (error) {
            console.error(error);
            // Handle error as needed
        }
    };


    callAPISearch(searchcontent, searchTypeOffice) {
        const jsonState = this.state;
        if (this.state.searchTypeOffice !== searchTypeOffice) {
            jsonState.searchTypeOffice = searchTypeOffice;
        }
        jsonState.postback = true;
        jsonState.jsonRequest.JsonInput.jsonDataInput.Search = searchcontent;
        jsonState.jsonRequest.JsonInput.jsonDataInput.TypeOffice = searchTypeOffice;
        if (this.state.defaultCenter) {
            jsonState.jsonRequest.JsonInput.jsonDataInput.Lat = this.state.defaultCenter.lat;
            jsonState.jsonRequest.JsonInput.jsonDataInput.Lng = this.state.defaultCenter.lng;
        }
        this.setState(jsonState);
        const apiRequest = Object.assign({}, this.state.jsonRequest);
        cpCallMAGP(apiRequest).then(Res => {
            let Response = Res.GetMapMarkerResult;

            if (Response && Response.Result && Response.Result === 'true' && Response.dtProposal
                && Response.dtProposal.length > 0
            ) {
                jsonState.searchResult = true;
                jsonState.getMapMarkerResult = Response;
                let positions = []
                Response.dtProposal.forEach(item => {
                    positions.push({lat: Number(item.Lat), lng: Number(item.Lng)});
                });
                jsonState.markerpositions = positions;

            } else {
                jsonState.searchResult = false;
                jsonState.getMapMarkerResult = null;
                jsonState.markerpositions = null;
            }
            jsonState.searchLoading = false;
            this.setState(jsonState);
        }).catch(error => {
            this.props.history.push('/maintainence');
        });
    }

    componentDidMount() {
        // get typeOffice from param
        const pSearch = this.props.location.search;
        let pSearchTypeOffice = '';
        if (pSearch) {
            pSearchTypeOffice = queryString.parse(pSearch)['searchTypeOffice'];
            if (pSearchTypeOffice !== null && pSearchTypeOffice !== undefined && pSearchTypeOffice !== '') {
                const jState = this.state;
                jState.searchTypeOffice = pSearchTypeOffice;
                this.setState(jState);
            }
        }
        this.LoadDefault();
        this.cpSaveLog(`Web_Open_${PageScreen.NETWORK_PAGE}`);
        trackingEvent(
            "Tiện ích",
            `Web_Open_${PageScreen.NETWORK_PAGE}`,
            `Web_Open_${PageScreen.NETWORK_PAGE}`,
        );
    }

    componentWillUnmount() {
        this.cpSaveLog(`Web_Close_${PageScreen.NETWORK_PAGE}`);
        trackingEvent(
            "Tiện ích",
            `Web_Close_${PageScreen.NETWORK_PAGE}`,
            `Web_Close_${PageScreen.NETWORK_PAGE}`,
        );
    }

    LoadDefault() {
        let jsonState;
        if (!this.state.UserProfile) {
            if (getSession(ACCESS_TOKEN) && getSession(CLIENT_PROFILE)) {
                this.setState({
                    UserProfile: JSON.parse(getSession(CLIENT_PROFILE))
                    , isLogin: "logined"
                });
            }
        }
        jsonState = this.state;
        try {

            navigator.geolocation.getCurrentPosition(function (position) {
                //console.log("latitude", position.coords.latitude);
                //console.log("longitude", position.coords.longitude);

                jsonState.defaultCenter.lat = position.coords.latitude;
                jsonState.defaultCenter.lng = position.coords.longitude;
            });
        } catch (error) {
            console.error("Error getting geolocation:", error);
            // Xử lý lỗi ở đây
            jsonState.defaultCenter.lat = 10.795009;
            jsonState.defaultCenter.lng = 106.676679;
        }

        this.setState(jsonState);
        if (!this.state.iconid) {
            const id = this.props.match.params.id;
            if (id && id !== "") {
                jsonState = this.state;
                jsonState.iconid = id;
                jsonState.searchTypeOffice = id;
                this.setState(jsonState);
            } else {
                jsonState = this.state;
                jsonState.iconid = "DLVN Office";
                this.setState(jsonState);
            }

        }
        if (!this.state.postback) {
            this.callAPISearch("", this.state.searchTypeOffice);
        }
    }

    cpSaveLog(functionName) {
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
            this.setState({renderMeta: true});
        }).catch(error => {
            this.setState({renderMeta: true});
        });
    }

    render() {
        if (!this.state.UserProfile && getSession(ACCESS_TOKEN) && getSession(CLIENT_PROFILE)) {
            this.setState({
                UserProfile: JSON.parse(getSession(CLIENT_PROFILE))
                , isLogin: "logined"
            });
        }

        return (

            <main className={this.state.isLogin}>
                {this.state.renderMeta &&
                    <Helmet>
                        <title>Mạng lưới – Dai-ichi Life Việt Nam</title>
                        <meta name="description"
                              content="Trang thông tin về mạng lưới văn phòng, địa chỉ các cơ sở bảo lãnh viện phí và các điểm nộp phí của Dai-ichi Life Việt Nam."/>
                        <meta name="keywords"
                              content="dai-ichi life, Dai-ichi Life Việt Nam, Gắn bó dài lâu, Mạng lưới"/>
                        <meta name="robots" content="index, follow"/>
                        <meta property="og:type" content="website"/>
                        <meta property="og:url" content={FE_BASE_URL + "/network"}/>
                        <meta property="og:title" content="Mạng lưới - Dai-ichi Life Việt Nam"/>
                        <meta property="og:description"
                              content="Trang thông tin về mạng lưới văn phòng, địa chỉ các cơ sở bảo lãnh viện phí và các điểm nộp phí của Dai-ichi Life Việt Nam."/>
                        <meta property="og:image"
                              content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                        <link rel="canonical" href={FE_BASE_URL + "/network"}/>
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
                                    <p>Mạng lưới</p>
                                    <p className='breadcrums__item_arrow'>></p>
                                </div>
                            </div>
                        </div>
                    </section>

                    <section className="scnetwork">
                        <div className="network">
                            <div className="googlemap-searching-field">
                                <div className="stepform__body">
                                    <div className="info">
                                        <div className="info__body">
                                            <div className="item">
                                                <div className="item__content">
                                                    <div className="tab">
                                                        <div className="tab__content">
                                                            <div className="searchbar">
                                                                <div className="input">
                                                                    <div className="input__content">
                                                                        <input
                                                                            placeholder="Nhập văn phòng, chi nhánh cần tìm"
                                                                            type="search"
                                                                            value={this.state.searchcontent}
                                                                            id="txtSearchContent"
                                                                            onChange={this.handle_Click}/>
                                                                    </div>
                                                                    <i><img src="/img/icon/10.1/10.1-icon-searching.svg"
                                                                            alt=""/></i>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div className="tab__content">
                                                            <div className="contract-list-menu">
                                                                <div
                                                                    className={this.state.searchTypeOffice && this.state.searchTypeOffice === "DLVN Office" ?
                                                                        ("contract-list-menu__item contract-list-menu__item__active")
                                                                        : ("contract-list-menu__item")
                                                                    }>
                                                                    <div className="contract-menu-content">
                                                                        <div className="item__icon">
                                                                            <img
                                                                                src="/img/icon/10.1/10.1-icon-vanphong.svg"
                                                                                alt="" id='btnDLVNOffice'
                                                                                onClick={this.handle_Click}/>
                                                                        </div>
                                                                        <div className="item__title">
                                                                            <h2 id='pDLVNOffice'
                                                                                onClick={this.handle_Click}>Văn phòng
                                                                                DLVN</h2>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div
                                                                    className={this.state.searchTypeOffice && this.state.searchTypeOffice === "Hospital" ?
                                                                        ("contract-list-menu__item contract-list-menu__item__active")
                                                                        : ("contract-list-menu__item")
                                                                    }>
                                                                    <div className="contract-menu-content">
                                                                        <div className="item__icon">
                                                                            <img src="/img/icon/10.1/10.1-icon-coso.svg"
                                                                                 alt="" id="bnHospital"
                                                                                 onClick={this.handle_Click}/>
                                                                        </div>
                                                                        <div className="item__title">
                                                                            <h2 id='pHospital'
                                                                                onClick={this.handle_Click}>Cơ sở bảo
                                                                                lãnh viện phí</h2>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                {/* <div
                                                                    className={this.state.searchTypeOffice && this.state.searchTypeOffice === "PostOffice" ?
                                                                        ("contract-list-menu__item contract-list-menu__item__active")
                                                                        : ("contract-list-menu__item")
                                                                    }>
                                                                    <div className="contract-menu-content">
                                                                        <div className="item__icon">
                                                                            <img
                                                                                src="/img/icon/10.1/10.1-icon-diemdongphi.svg"
                                                                                alt="" id='btnPostOffice'
                                                                                onClick={this.handle_Click}/>
                                                                        </div>
                                                                        <div className="item__title"><h2
                                                                            id='pPostOffice'
                                                                            onClick={this.handle_Click}>Điểm nộp
                                                                            phí</h2></div>
                                                                    </div>
                                                                </div> */}
                                                            </div>
                                                        </div>

                                                        <div className="tab__content">
                                                            <button className="btn btn-primary disabled" id="btnSearch"
                                                                    onClick={this.handle_Click}>Tìm kiếm
                                                            </button>
                                                        </div>

                                                        {this.state.searchLoading ? (<div className="tab__content">
                                                                <div className="searching-nodata">
                                                                    <div className="icon-wrapper">
                                                                        <LoadingIndicator area="network-loading"/>
                                                                    </div>
                                                                </div>
                                                            </div>)
                                                            : (

                                                                <div>
                                                                    <div
                                                                        className={!this.state.searchResult ? ("tab__content basic-displaynone")
                                                                            : ("tab__content")}>
                                                                        <div className="recommendation-wrapper">
                                                                            <p>Đề xuất</p>
                                                                            {this.state.getMapMarkerResult
                                                                                && this.state.getMapMarkerResult.dtProposal
                                                                                && this.state.getMapMarkerResult.dtProposal.map((item, index) => (
                                                                                    <div className="item">
                                                                                        <a onClick={() => this.Marker_Click(item.Lat, item.Lng, item.Name)}>
                                                                                            <div className="title">
                                                                                                <p>{item.Name}</p>
                                                                                            </div>

                                                                                            <div className="address">
                                                                                                <div
                                                                                                    className="icon-wrapper">
                                                                                                    <img
                                                                                                        src="/img/icon/10/location-icon.svg"
                                                                                                        alt=""/>
                                                                                                </div>
                                                                                                <p>{item.Address}</p>
                                                                                            </div>
                                                                                        </a>
                                                                                        <div
                                                                                            className="basic-info-group">
                                                                                            <div
                                                                                                className="basic-info-tab">
                                                                                                <div
                                                                                                    className="phone basic-info">
                                                                                                    <div
                                                                                                        className="icon-wrapper">
                                                                                                        <img
                                                                                                            src="/img/icon/10.1/10.1-icon-phone.svg"
                                                                                                            alt=""/>
                                                                                                    </div>
                                                                                                    <span>{item.Phone}</span>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div
                                                                                                className="basic-info-tab">
                                                                                                <a href={"https://www.google.es/maps/dir/'" + this.state.defaultCenter.lat + "," + this.state.defaultCenter.lng + "'/'" + item.Lat + "," + item.Lng + "'/data=!4m2!4m1!3e0"}
                                                                                                   target="_blank">
                                                                                                    <div
                                                                                                        className="basic-info">
                                                                                                        <div
                                                                                                            className="icon-wrapper">
                                                                                                            <img
                                                                                                                src="/img/icon/10.1/10.1-icon-guide.svg"
                                                                                                                alt=""/>
                                                                                                        </div>
                                                                                                        <span>Chỉ đường</span>
                                                                                                    </div>
                                                                                                </a>
                                                                                            </div>
                                                                                        </div>

                                                                                    </div>

                                                                                ))


                                                                            }

                                                                        </div>
                                                                    </div>

                                                                    <div
                                                                        className={this.state.searchResult ? ("tab__content basic-displaynone")
                                                                            : ("tab__content")}>
                                                                        <div className="searching-nodata">
                                                                            <div className="icon-wrapper">
                                                                                <img
                                                                                    src="/img/icon/10.1/10.1-icon-magnifying.svg"
                                                                                    alt=""/>
                                                                            </div>
                                                                            <div className="content">
                                                                                <p>Chưa tìm thấy kết quả bạn cần rồi.
                                                                                    Hãy thử lại từ khoá khác nhé!</p>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            )}
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </section>
                </div>
            </main>
        )
    }

}

export default NetWork;