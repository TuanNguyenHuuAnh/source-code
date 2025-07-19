import React, {Component} from 'react';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CLIENT_ID,
    COMPANY_KEY,
    EXPIRED_MESSAGE,
    FE_BASE_URL,
    LINK_SUB_MENU_NAME,
    LINK_SUB_MENU_NAME_ID,
    PageScreen,
    POL_LIST_CLIENT,
    USER_LOGIN,
    NUM_OF_MANUAL_RETRY, 
    WEB_BROWSER_VERSION
} from '../constants';
import 'antd/dist/antd.min.css';
import {
    CPGetPolicyInfoByPOLID,
    CPGetPolicyListByCLIID,
    CPGetProductListByPOLID,
    CPSaveLog,
    logoutSession
} from '../util/APIUtils';
import phibaohiem from '../img/icon/phibaohiem.svg';
import sanphambaohiem from '../img/icon/sanpham.svg';
import giatrihopdong from '../img/icon/giatrihopdong.svg';
import nguoithuhuong from '../img/icon/nguoithuhuong.svg';
import tuvanphucvu from '../img/icon/tuvanphucvu.svg';
import LoadingIndicator from '../common/LoadingIndicator2';
import {format} from 'date-fns';
import {Link, Redirect} from 'react-router-dom';
import '../common/Common.css';
import {getDeviceId, getSession, setSession, showMessage, trackingEvent} from '../util/common';
import {Helmet} from "react-helmet";
import NoticePopup from '../components/NoticePopup';
import iconArrowLeftBrown from '../img/icon/arrow-left-brown.svg';
import iconArrowDownBrown from '../img/icon/arrow-down-bronw.svg';
import LoadingIndicatorBasic from "../common/LoadingIndicatorBasic";

//Including all libraries, for access to extra methods.

class PolInfo extends Component {

    constructor(props) {
        super(props);

        this.state = {
            jsonInput: {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    APIToken: getSession(ACCESS_TOKEN),
                    Project: 'mcp',
                    ClientID: getSession(CLIENT_ID),
                    UserLogin: getSession(USER_LOGIN)
                }
            },
            jsonInput2: {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    OS: WEB_BROWSER_VERSION,
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    APIToken: getSession(ACCESS_TOKEN),
                    Project: 'mcp',
                    ClientID: getSession(CLIENT_ID),
                    UserLogin: getSession(USER_LOGIN),
                    UserID: getSession(USER_LOGIN),
                    PolID: '',
                    Action: ''
                }
            },
            ClientProfile: null,
            ClientProfileFee: null,
            ClientProfileProducts: null,
            ClientProfileValue: null,
            ClientProfileBene: null,
            ClientProfileAgent: null,
            polID: '',
            AgentName: '',
            jsonResponse: null,
            FeeResponse: null,
            ValueResponse: null,
            AgentResponse: null,
            BeneResponse: null,
            ProductsResponse: null,
            isEmpty: true,
            ProvinceList: [],
            PolicyClassCD: '',
            PolicyInfo: null,
            isDropdownFeeDetails: false,
            isDropdownTotalFee: false,
            isHideBorder: false,
            isShowSPBSDetails: false,
            isClicked: false,
            loading: false,
            firstLoad: true,
            renderMeta: false,
            showNoticeEffectiveDate: false,
            showNotice: false,
            title: '',
            msg: '',
            isNonePolicyList: false, // timeout policy list
            isReloadPolicyList: false // show reload policy list button
            , countRetry: 0
            , isRetrying: false,
            showDividendNote : false,
        }
        // this.buttonClickcontractValue = this.buttonClickcontractValue.bind(this);
        // this.buttonClickcontractFee = this.buttonClickcontractFee.bind(this);
        // this.buttonClickcontractProducts = this.buttonClickcontractProducts.bind(this);
        // this.buttonClickcontractBene = this.buttonClickcontractBene.bind(this);
        // this.buttonClickcontractAgent = this.buttonClickcontractAgent.bind(this);b
        this.selectedCardInfo = this.selectedCardInfo.bind(this);

    }

    componentDidMount() {
        if (!getSession(POL_LIST_CLIENT)) {
            this.getPolicyListByCLIID(false);
        } else {
            let jsonState = this.state;
            jsonState.ClientProfile = JSON.parse(getSession(POL_LIST_CLIENT));
            if (this.props.match.params.id && this.props.match.params.id !== "" && !this.state.polID) {
                jsonState.polID = this.props.match.params.id;
                jsonState.isEmpty = false;
                this.selectedCardInfo(this.props.match.params.id);
            }
            this.setState(jsonState);
        }
        this.cpSaveLog(`Web_Open_${PageScreen.POLICY_PAGE}`);
        trackingEvent(
            "Thông tin hợp đồng",
            `Web_Open_${PageScreen.POLICY_PAGE}`,
            `Web_Open_${PageScreen.POLICY_PAGE}`,
        );
    }

    getPolicyListByCLIID (retry) {
        if(retry){
            const jsonState = this.state;
            jsonState.countRetry = this.state.countRetry + 1;
            jsonState.isNonePolicyList = false;
            jsonState.isReloadPolicyList = false;
            jsonState.isRetrying = true;
            this.setState(jsonState);
        }
        const TIMEOUT = "TIMEOUT"
        const apiRequest = Object.assign({}, this.state.jsonInput);
        CPGetPolicyListByCLIID(apiRequest).then(Res => {
            let Response = Res.Response;
            if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
                const jsonState = this.state;
                jsonState.jsonResponse = Response;
                jsonState.ClientProfile = Response.ClientProfile;
                setSession(POL_LIST_CLIENT, JSON.stringify(Response.ClientProfile));

                if (this.props.match.params.id && this.props.match.params.id !== "" && !this.state.polID) {

                    jsonState.polID = this.props.match.params.id;
                    jsonState.isEmpty = false;

                    this.selectedCardInfo(this.props.match.params.id);
                }
                this.setState(jsonState);

            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: {authenticated: false, hideMain: false}

                })

            } else if(Response.ErrLog === TIMEOUT){
                this.setStateTimeoutOrEmpty(Response)
            }


        }).catch(error => {
            // response with status out of range of 2XX or the request was made but no reponse was received.
            if(error.response || error.request){
                this.setStateTimeoutOrEmpty(error.response ?? this.state.jsonResponse);
            }
            else{
                // others case
                this.props.history.push('/maintainence');
            }
            
        }).finally(()=>{
            const jsonState = this.state;
            jsonState.isRetrying = false;
            this.setState(jsonState);
        });
    }

    setStateTimeoutOrEmpty(Response){
        const jsonState = this.state;
        jsonState.jsonResponse = Response;
        jsonState.isNonePolicyList = true;
        jsonState.isReloadPolicyList = jsonState.countRetry < NUM_OF_MANUAL_RETRY;
        console.log('retry', jsonState.countRetry, NUM_OF_MANUAL_RETRY, jsonState.isNonePolicyList, jsonState.isReloadPolicyList);
        this.setState(jsonState);
    }

    componentWillUnmount() {
        this.cpSaveLog(`Web_Close_${PageScreen.POLICY_PAGE}`);
        trackingEvent(
            "Thông tin hợp đồng",
            `Web_Close_${PageScreen.POLICY_PAGE}`,
            `Web_Close_${PageScreen.POLICY_PAGE}`,
        );
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

    sortListByName(obj) {
        obj.sort((a, b) => {
            if (a.ProvinceCode < b.ProvinceCode) return -1;
            else if (a.ProvinceCode > b.ProvinceCode) return 1;
            else return 0;
        });
        return obj;
    }

    selectedCardInfo(polID) {
        try {
            setTimeout(() => {
                this.handSelectCard(polID);
            }, 2000);
        } catch (error) {
            console.error(error);
            // Handle error if needed
        }
    }

    handSelectCard(polID) {
        try {
            const pathSegments = window.location.pathname.split('/');
            const lastPathSegment = pathSegments[pathSegments.length - 1];

            setTimeout(() => {
              if (lastPathSegment === '1') {
                this.callAPIcontractFee(polID);
              } else if (lastPathSegment === '2') {
                this.callAPIcontractProducts(polID);
              } else if (lastPathSegment === '3') {
                this.callAPIcontractValue(polID);
              } else if (lastPathSegment === '4') {
                this.callAPIcontractBene(polID);
              } else if (lastPathSegment === '5') {
                this.callAPIcontractAgent(polID);
              } else {
                this.callAPIcontractFee(polID);
              }
            }, 0);
            this.state.ClientProfile.forEach((item, i) => {
                if (item.PolicyID.trim() === polID.trim()) {
                    const cardElement = document.getElementById(i.toString());
                    if (cardElement) {
                        document.getElementById(i.toString()).className = "card choosen";
                    }
                }
            });
        } catch (err) {
            // alert(err);
        }
    }

    callAPIcontractFee = (polID) => {
        let jsonState = this.state;
        if (polID) {
            jsonState.jsonInput2.jsonDataInput.PolID = polID.trim();
        }        jsonState.jsonInput2.jsonDataInput.Action = 'PolicyPayment';
        jsonState.jsonInput2.jsonDataInput.Company = COMPANY_KEY;
        jsonState.loading = true;
        this.setState(jsonState);
        const apiRequest = Object.assign({}, this.state.jsonInput2);
        CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
            let Response1 = Res.Response;
            console.log("CPGetPolicyInfoByPOLID", Response1);
            if (Response1.ErrLog === 'SUCCESSFUL') {
                jsonState.FeeResponse = Response1;
                jsonState.ClientProfileFee = Response1.ClientProfile;
                jsonState.loading = false;

                this.setState({
                    ProvinceList: this.sortListByName(jsonState.ClientProfileFee),
                });
                this.setState(jsonState);
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: {authenticated: false, hideMain: false}

                });
                jsonState.loading = false;
                this.setState(jsonState);
            } else {
                jsonState.loading = false;
                this.setState(jsonState);
            }

                document.getElementById('contractFee').className = "contract-fee show-component"
                document.getElementById('contractFeeButton').className = "contract-list-menu__item contract-list-menu__item__active"
                document.getElementById('contractProducts').className = "contract-products";
                document.getElementById('contractProductsButton').className = "contract-list-menu__item"
                document.getElementById('contractValue').className = "contract-value";
                document.getElementById('contractValueButton').className = "contract-list-menu__item"
                document.getElementById('contractAgent').className = "contract-advise";
                document.getElementById('contractAgentButton').className = "contract-list-menu__item"
                document.getElementById('contractBene').className = "contract-beneficiary card-extend-container";
                document.getElementById('contractBeneButton').className = "contract-list-menu__item"

            this.setState({isClicked: true});
        }).catch(error => {
            this.props.history.push('/maintainence');
        });
    }
    // buttonClickcontractFee = () => {
    //     document.getElementById('contractFee').className = "contract-fee show-component"
    //     document.getElementById('contractFeeButton').className = "contract-list-menu__item contract-list-menu__item__active"
    //     document.getElementById('contractProducts').className = "contract-products";
    //     document.getElementById('contractProductsButton').className = "contract-list-menu__item"
    //     document.getElementById('contractValue').className = "contract-value";
    //     document.getElementById('contractValueButton').className = "contract-list-menu__item"
    //     document.getElementById('contractAgent').className = "contract-advise";
    //     document.getElementById('contractAgentButton').className = "contract-list-menu__item"
    //     document.getElementById('contractBene').className = "contract-beneficiary card-extend-container";
    //     document.getElementById('contractBeneButton').className = "contract-list-menu__item"
    //
    // }
    callAPIcontractProducts = (polID) => {
        let jsonState = this.state;
        if (polID) {
            jsonState.jsonInput2.jsonDataInput.PolID = polID.trim();
        }
        jsonState.jsonInput2.jsonDataInput.Action = 'PolicyProduct';
        jsonState.loading = true;
        this.setState(jsonState);
        const apiRequest = Object.assign({}, this.state.jsonInput2);
        CPGetProductListByPOLID(apiRequest).then(Res => {
            let Response2 = Res.Response;
            if (Response2.ErrLog === 'SUCCESSFUL') {
                jsonState.ProductsResponse = Response2;
                jsonState.ClientProfileProducts = Response2.Products;
                jsonState.loading = false;
                this.setState(jsonState);
            } else if (Response2.NewAPIToken === 'invalidtoken' || Response2.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: {authenticated: false, hideMain: false}
                });
                jsonState.loading = false;
                this.setState(jsonState);
            } else {
                jsonState.loading = false;
                this.setState(jsonState);
            }
            document.getElementById('contractFee').className = "contract-fee"
            document.getElementById('contractProducts').className = "contract-products show-component";
            document.getElementById('contractValue').className = "contract-value";
            document.getElementById('contractAgent').className = "contract-advise";
            document.getElementById('contractBene').className = "contract-beneficiary card-extend-container";
            document.getElementById('contractFeeButton').className = "contract-list-menu__item"
            document.getElementById('contractProductsButton').className = "contract-list-menu__item contract-list-menu__item__active"
            document.getElementById('contractValueButton').className = "contract-list-menu__item"
            document.getElementById('contractAgentButton').className = "contract-list-menu__item"
            document.getElementById('contractBeneButton').className = "contract-list-menu__item"
            this.setState({isClicked: true});
        }).catch(error => {
            this.props.history.push('/maintainence');
        });
    }
    // buttonClickcontractProducts = () => {
    //     document.getElementById('contractFee').className = "contract-fee"
    //     document.getElementById('contractProducts').className = "contract-products show-component";
    //     document.getElementById('contractValue').className = "contract-value";
    //     document.getElementById('contractAgent').className = "contract-advise";
    //     document.getElementById('contractBene').className = "contract-beneficiary card-extend-container";
    //     document.getElementById('contractFeeButton').className = "contract-list-menu__item"
    //     document.getElementById('contractProductsButton').className = "contract-list-menu__item contract-list-menu__item__active"
    //     document.getElementById('contractValueButton').className = "contract-list-menu__item"
    //     document.getElementById('contractAgentButton').className = "contract-list-menu__item"
    //     document.getElementById('contractBeneButton').className = "contract-list-menu__item"
    //     this.setState({isClicked: true});
    //
    // }
    callAPIcontractValue = (polID) => {
        let jsonState = this.state;
        if (polID) {
            jsonState.jsonInput2.jsonDataInput.PolID = polID.trim();
        }        jsonState.jsonInput2.jsonDataInput.Action = 'PolicyAnn';
        jsonState.jsonInput2.jsonDataInput.Company = COMPANY_KEY;
        jsonState.loading = true;
        this.setState(jsonState);
        const apiRequest = Object.assign({}, this.state.jsonInput2);
        CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
            let Response3 = Res.Response;

            if (Response3.ErrLog === 'SUCCESSFUL') {
                jsonState.ValueResponse = Response3;
                jsonState.ClientProfileValue = Response3.ClientProfile;
                jsonState.PolicyInfo = Response3.PolicyInfo;
                jsonState.loading = false;
                jsonState.showDividendNote = Response3.ClientProfile[0].showDividendNote;
                this.setState(jsonState);
            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: {authenticated: false, hideMain: false}
                });
                jsonState.loading = false;
                this.setState(jsonState);
            } else {
                jsonState.loading = false;
                this.setState(jsonState);
            }
            document.getElementById('contractFee').className = "contract-fee"
            document.getElementById('contractProducts').className = "contract-products";
            document.getElementById('contractValue').className = "contract-value show-component";
            document.getElementById('contractAgent').className = "contract-advise";
            document.getElementById('contractBene').className = "contract-beneficiary card-extend-container";
            document.getElementById('contractFeeButton').className = "contract-list-menu__item"
            document.getElementById('contractProductsButton').className = "contract-list-menu__item"
            document.getElementById('contractValueButton').className = "contract-list-menu__item contract-list-menu__item__active"
            document.getElementById('contractAgentButton').className = "contract-list-menu__item"
            document.getElementById('contractBeneButton').className = "contract-list-menu__item"
            this.setState({
                isClicked: true,
            });
        }).catch(error => {
            this.props.history.push('/maintainence');
        });
    }
    // buttonClickcontractValue = () => {
    //     document.getElementById('contractFee').className = "contract-fee"
    //     document.getElementById('contractProducts').className = "contract-products";
    //     document.getElementById('contractValue').className = "contract-value show-component";
    //     document.getElementById('contractAgent').className = "contract-advise";
    //     document.getElementById('contractBene').className = "contract-beneficiary card-extend-container";
    //     document.getElementById('contractFeeButton').className = "contract-list-menu__item"
    //     document.getElementById('contractProductsButton').className = "contract-list-menu__item"
    //     document.getElementById('contractValueButton').className = "contract-list-menu__item contract-list-menu__item__active"
    //     document.getElementById('contractAgentButton').className = "contract-list-menu__item"
    //     document.getElementById('contractBeneButton').className = "contract-list-menu__item"
    //     this.setState({isClicked: true});
    //
    // }
    callAPIcontractBene = (polID) => {
        let jsonState = this.state;
        if (polID) {
            jsonState.jsonInput2.jsonDataInput.PolID = polID.trim();
        }        jsonState.jsonInput2.jsonDataInput.Action = 'PolicyBene';
        jsonState.jsonInput2.jsonDataInput.Company = COMPANY_KEY;
        jsonState.loading = true;
        this.setState(jsonState);
        const apiRequest = Object.assign({}, this.state.jsonInput2);
        CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
            console.log("callAPIcontractBene", Res);
            let Response4 = Res.Response;
            if (Response4.ErrLog === 'SUCCESSFUL') {
                jsonState.AgentResponse = Response4;
                jsonState.ClientProfileBene = Response4.ClientProfile;
                jsonState.loading = false;
                this.setState(jsonState);

            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: {authenticated: false, hideMain: false}
                });
                jsonState.loading = false;
                this.setState(jsonState);
            } else {
                jsonState.loading = false;
                this.setState(jsonState);
            }
            document.getElementById('contractFee').className = "contract-fee"
            document.getElementById('contractProducts').className = "contract-products";
            document.getElementById('contractValue').className = "contract-value";
            document.getElementById('contractAgent').className = "contract-advise";
            document.getElementById('contractBene').className = "contract-beneficiary card-extend-container show-component";
            document.getElementById('contractFeeButton').className = "contract-list-menu__item"
            document.getElementById('contractProductsButton').className = "contract-list-menu__item"
            document.getElementById('contractValueButton').className = "contract-list-menu__item"
            document.getElementById('contractAgentButton').className = "contract-list-menu__item"
            document.getElementById('contractBeneButton').className = "contract-list-menu__item contract-list-menu__item__active"
            this.setState({
                isClicked: true,
            });
        }).catch(error => {
            this.props.history.push('/maintainence');
        });
    }
    // buttonClickcontractBene = () => {
    //     document.getElementById('contractFee').className = "contract-fee"
    //     document.getElementById('contractProducts').className = "contract-products";
    //     document.getElementById('contractValue').className = "contract-value";
    //     document.getElementById('contractAgent').className = "contract-advise";
    //     document.getElementById('contractBene').className = "contract-beneficiary card-extend-container show-component";
    //     document.getElementById('contractFeeButton').className = "contract-list-menu__item"
    //     document.getElementById('contractProductsButton').className = "contract-list-menu__item"
    //     document.getElementById('contractValueButton').className = "contract-list-menu__item"
    //     document.getElementById('contractAgentButton').className = "contract-list-menu__item"
    //     document.getElementById('contractBeneButton').className = "contract-list-menu__item contract-list-menu__item__active"
    //     this.setState({isClicked: true});
    //
    // }
    callAPIcontractAgent = (polID) => {
        let jsonState = this.state;
        if (polID) {
            jsonState.jsonInput2.jsonDataInput.PolID = polID.trim();
        }
        jsonState.jsonInput2.jsonDataInput.Action = 'PolicyAgent';
        jsonState.jsonInput2.jsonDataInput.Company = COMPANY_KEY;
        jsonState.loading = true;
        this.setState(jsonState);
        const apiRequest = Object.assign({}, this.state.jsonInput2);
        CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
            let Response5 = Res.Response;
            if (Response5.ErrLog === 'SUCCESSFUL') {
                jsonState.AgentResponse = Response5;
                jsonState.ClientProfileAgent = Response5.ClientProfile;
                jsonState.loading = false;
                this.setState(jsonState);

            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: {authenticated: false, hideMain: false}
                });
                jsonState.loading = false;
                this.setState(jsonState);
            } else {
                jsonState.loading = false;
                this.setState(jsonState);
            }
            document.getElementById('contractFee').className = "contract-fee"
            document.getElementById('contractProducts').className = "contract-products";
            document.getElementById('contractValue').className = "contract-value";
            document.getElementById('contractAgent').className = "contract-advise show-component";
            document.getElementById('contractBene').className = "contract-beneficiary card-extend-container";
            document.getElementById('contractFeeButton').className = "contract-list-menu__item"
            document.getElementById('contractProductsButton').className = "contract-list-menu__item"
            document.getElementById('contractValueButton').className = "contract-list-menu__item"
            document.getElementById('contractAgentButton').className = "contract-list-menu__item contract-list-menu__item__active"
            document.getElementById('contractBeneButton').className = "contract-list-menu__item"
            this.setState({
                isClicked: true,
            });
        }).catch(error => {
            this.props.history.push('/maintainence');
        });
    }
    // buttonClickcontractAgent = () => {
    //     document.getElementById('contractFee').className = "contract-fee"
    //     document.getElementById('contractProducts').className = "contract-products";
    //     document.getElementById('contractValue').className = "contract-value";
    //     document.getElementById('contractAgent').className = "contract-advise show-component";
    //     document.getElementById('contractBene').className = "contract-beneficiary card-extend-container";
    //     document.getElementById('contractFeeButton').className = "contract-list-menu__item"
    //     document.getElementById('contractProductsButton').className = "contract-list-menu__item"
    //     document.getElementById('contractValueButton').className = "contract-list-menu__item"
    //     document.getElementById('contractAgentButton').className = "contract-list-menu__item contract-list-menu__item__active"
    //     document.getElementById('contractBeneButton').className = "contract-list-menu__item"
    //     this.setState({isClicked: true});
    //
    // }

    render() {
        const callAPIcontractAgent = (polID) => {
            const jsonState = this.state;
            if (polID) {
                jsonState.jsonInput2.jsonDataInput.PolID = polID.trim();
            }
            jsonState.jsonInput2.jsonDataInput.Action = 'PolicyAgent';
            jsonState.jsonInput2.jsonDataInput.Company = COMPANY_KEY;
            jsonState.loading = true;
            this.setState(jsonState);
            const apiRequest = Object.assign({}, this.state.jsonInput2);
            CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
                let Response5 = Res.Response;
                if (Response5.ErrLog === 'SUCCESSFUL') {
                    jsonState.AgentResponse = Response5;
                    jsonState.ClientProfileAgent = Response5.ClientProfile;
                    jsonState.loading = false;
                    this.setState(jsonState);
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home',
                        state: {authenticated: false, hideMain: false}
                    });
                    jsonState.loading = false;
                    this.setState(jsonState);
                } else {
                    jsonState.loading = false;
                    this.setState(jsonState);
                }
            }).catch(error => {
                this.props.history.push('/maintainence');
            });
        };
        const callAPIcontractBene = (polID) => {
            const jsonState = this.state;
            if (polID) {
                jsonState.jsonInput2.jsonDataInput.PolID = polID.trim();
            }
            jsonState.jsonInput2.jsonDataInput.Action = 'PolicyBene';
            jsonState.jsonInput2.jsonDataInput.Company = COMPANY_KEY;
            jsonState.loading = true;
            this.setState(jsonState);
            const apiRequest = Object.assign({}, this.state.jsonInput2);
            CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
                let Response4 = Res.Response;
                if (Response4.ErrLog === 'SUCCESSFUL') {
                    jsonState.AgentResponse = Response4;
                    jsonState.ClientProfileBene = Response4.ClientProfile;
                    jsonState.loading = false;
                    this.setState(jsonState);
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home',
                        state: {authenticated: false, hideMain: false}
                    });
                    jsonState.loading = false;
                    this.setState(jsonState);
                } else {
                    jsonState.loading = false;
                    this.setState(jsonState);
                }
            }).catch(error => {
                this.props.history.push('/maintainence');
            });
        };
        const callAPIcontractValue = (polID) => {
            const jsonState = this.state;
            if (polID) {
                jsonState.jsonInput2.jsonDataInput.PolID = polID.trim();
            }
            jsonState.jsonInput2.jsonDataInput.Action = 'PolicyAnn';
            jsonState.jsonInput2.jsonDataInput.Company = COMPANY_KEY;
            jsonState.loading = true;
            this.setState(jsonState);

            const apiRequest = Object.assign({}, this.state.jsonInput2);
            CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
                let Response3 = Res.Response;

                if (Response3.ErrLog === 'SUCCESSFUL') {
                    jsonState.ValueResponse = Response3;
                    jsonState.ClientProfileValue = Response3.ClientProfile;
                    jsonState.PolicyInfo = Response3.PolicyInfo;
                    jsonState.loading = false;
                    jsonState.showDividendNote = Response3.ClientProfile[0].showDividendNote;
                    this.setState(jsonState);
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home',
                        state: {authenticated: false, hideMain: false}
                    });
                    jsonState.loading = false;
                    this.setState(jsonState);
                } else {
                    jsonState.loading = false;
                    this.setState(jsonState);
                }
            }).catch(error => {
                this.props.history.push('/maintainence');
            });
        };
        const callAPIcontractProducts = (polID) => {
            const jsonState = this.state;
            jsonState.jsonInput2.jsonDataInput.PolID = polID.trim();
            jsonState.jsonInput2.jsonDataInput.Action = 'PolicyProduct';
            jsonState.loading = true;
            this.setState(jsonState);
            const apiRequest = Object.assign({}, this.state.jsonInput2);
            CPGetProductListByPOLID(apiRequest).then(Res => {
                let Response2 = Res.Response;
                if (Response2.ErrLog === 'SUCCESSFUL') {
                    jsonState.ProductsResponse = Response2;
                    jsonState.ClientProfileProducts = Response2.Products;
                    jsonState.loading = false;
                    this.setState(jsonState);
                    document.getElementById('contractProducts').className = "contract-products";
                    document.getElementById('contractProductsButton').className = "contract-list-menu__item";
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home',
                        state: {authenticated: false, hideMain: false}

                    });
                    jsonState.loading = false;
                    this.setState(jsonState);
                } else {
                    jsonState.loading = false;
                    this.setState(jsonState);
                }
            }).catch(error => {
                this.props.history.push('/maintainence');
            });
        };
        const callAPIcontractFee = (polID) => {
            const jsonState = this.state;
            if (polID) {
                jsonState.jsonInput2.jsonDataInput.PolID = polID.trim();
            }
            jsonState.jsonInput2.jsonDataInput.Action = 'PolicyPayment';
            jsonState.jsonInput2.jsonDataInput.Company = COMPANY_KEY;
            jsonState.loading = true;
            this.setState(jsonState);
            const apiRequest = Object.assign({}, this.state.jsonInput2);
            CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
                let Response1 = Res.Response;
                if (Response1.ErrLog === 'SUCCESSFUL') {
                    jsonState.FeeResponse = Response1;
                    jsonState.ClientProfileFee = Response1.ClientProfile;
                    jsonState.loading = false;
                    this.setState({
                        ProvinceList: this.sortListByName(jsonState.ClientProfileFee),
                    });
                    this.setState(jsonState);
                    if (!this.state.isClicked) {
                        document.getElementById('contractFee').className = "contract-fee show-component";
                        document.getElementById('contractFeeButton').className = "contract-list-menu__item contract-list-menu__item__active";
                        if ((document.getElementById('contractProducts') !== null) && (document.getElementById('contractProducts') !== undefined)) document.getElementById('contractProducts').className = "contract-products";
                        document.getElementById('contractProductsButton').className = "contract-list-menu__item"
                        if ((document.getElementById('contractValue') !== null) && (document.getElementById('contractValue') !== undefined)) document.getElementById('contractValue').className = "contract-value";
                        document.getElementById('contractValueButton').className = "contract-list-menu__item"
                        if ((document.getElementById('contractAgent') !== null) && (document.getElementById('contractAgent') !== undefined)) document.getElementById('contractAgent').className = "contract-advise";
                        document.getElementById('contractAgentButton').className = "contract-list-menu__item"
                        if ((document.getElementById('contractBene') !== null) && (document.getElementById('contractBene') !== undefined)) document.getElementById('contractBene').className = "contract-beneficiary card-extend-container";
                        document.getElementById('contractBeneButton').className = "contract-list-menu__item"
                    }
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home',
                        state: {authenticated: false, hideMain: false}

                    });
                    jsonState.loading = false;
                    this.setState(jsonState);
                } else {
                    jsonState.loading = false;
                    this.setState(jsonState);
                }
            }).catch(error => {
                this.props.history.push('/maintainence');
            });
        };

        const selectedSubMenu = (id, subMenuName) => {
            setSession(LINK_SUB_MENU_NAME, subMenuName);
            setSession(LINK_SUB_MENU_NAME_ID, id);
        }
        const showNotice = (m) => {
            if (m) {
                this.setState({showNotice: true, msg: m});
            }
        }
        const closeNotice = () => {
            this.setState({showNotice: false});
        }
        const showNoticeEffectiveDate = (m) => {
            if (m) {
                this.setState({showNoticeEffectiveDate: true, msg: m});
            }
        }
        const closeNoticeEffectiveDate = () => {
            this.setState({showNoticeEffectiveDate: false});
        }

        const showCardInfo = (polID, index, AgentName, PolicyClassCD) => {
            const jsonState = this.state;
            jsonState.polID = polID;
            jsonState.AgentName = AgentName;
            jsonState.PolicyClassCD = PolicyClassCD;
            jsonState.ClientProfileAgent = null;
            jsonState.ClientProfileFee = null;
            jsonState.ClientProfileProducts = null;
            jsonState.ClientProfileValue = null;
            jsonState.PolicyInfo = null;
            jsonState.ClientProfileBene = null;
            jsonState.isDropdownFeeDetails = false;
            jsonState.isDropdownTotalFee = false;

            this.setState(jsonState);
            if (document.getElementById(index).className === "card choosen") {
                document.getElementById(index).className = "card";
                jsonState.polID = '';
                jsonState.AgentName = '';
                jsonState.ClientProfileAgent = null;
                jsonState.ClientProfileFee = null;
                jsonState.ClientProfileProducts = null;
                jsonState.ClientProfileValue = null;
                jsonState.PolicyInfo = null;
                jsonState.ClientProfileBene = null;
                jsonState.isEmpty = true;
                this.setState(jsonState);
            } else {
                jsonState.isEmpty = false;
                this.state.ClientProfile.forEach((polID, i) => {
                    if (i === index) {
                        document.getElementById(i).className = "card choosen";
                    } else {
                        document.getElementById(i).className = "card";
                    }
                });
                callAPIcontractFee(polID);
                callAPIcontractProducts(polID);
                callAPIcontractValue(polID);
                callAPIcontractBene(polID);
                callAPIcontractAgent(polID);
                if (!this.state.isClicked) {
                    document.getElementById('contractFee').className = "contract-fee show-component";
                    if (document.getElementById('contractFeeButton')) document.getElementById('contractFeeButton').className = "contract-list-menu__item contract-list-menu__item__active";
                }
            }
        };

        const toTitleCase = str => str.split(" ").map(
            e => e.substring(0, 1).toUpperCase() + e.substring(1).toLowerCase()).join(" ");

        const goBack = () => {
            const main = document.getElementById("main-id");
            if (main) {
                main.classList.toggle("nodata");
            }
        }

        const showFundValue0 = () => {
            const jsonState = this.state;
            if (document.getElementById("fundvalue0").className === "header2 dropdown show") {
                document.getElementById("fundvalue0").className = "header2 dropdown";
            } else {
                document.getElementById("fundvalue0").className = "header2 dropdown show";
            }
        };

        const showFundValue = (i) => {
            const jsonState = this.state;
            if (document.getElementById("fundvalue" + i).className === "contract-fee__form__item dropdown show") {
                document.getElementById("fundvalue" + i).className = "contract-fee__form__item dropdown";
            } else {
                document.getElementById("fundvalue" + i).className = "contract-fee__form__item dropdown show";
            }
        };

        const dropdownContent = (index, prdIndex) => {
            if (document.getElementById('dropdownContent' + index + prdIndex).className === "card__footer-item dropdown show") {
                document.getElementById('dropdownContent' + index + prdIndex).className = "card__footer-item dropdown";
            } else {
                document.getElementById('dropdownContent' + index + prdIndex).className = "card__footer-item dropdown show";
            }
        };
        const dropdownTotalFee = () => {
            const jsonState = this.state;
            if (this.state.loading) {
                return;
            }
            if (document.getElementById('TotalFee').className === "dropdown show") {
                document.getElementById('TotalFee').className = "dropdown";
                jsonState.isDropdownTotalFee = false;
                this.setState(jsonState);
            } else {
                document.getElementById('TotalFee').className = "dropdown show";
                jsonState.isDropdownTotalFee = true;
                this.setState(jsonState);
            }
        };
        const dropdownFeeDetails = () => {
            const jsonState = this.state;
            if (this.state.loading) {
                return;
            }
            if (document.getElementById('FeeDetails').className === "dropdown show") {
                document.getElementById('FeeDetails').className = "dropdown";
                jsonState.isDropdownFeeDetails = false;
                this.setState(jsonState);
            } else {
                document.getElementById('FeeDetails').className = "dropdown show";
                jsonState.isDropdownFeeDetails = true;
                this.setState(jsonState);
            }
        };
        if (!getSession(CLIENT_ID)) {
            return <Redirect to={{
                pathname: '/home'
            }}/>;
        }
        return (
            <div>
                {this.state.renderMeta &&
                    <Helmet>
                        <title>Danh sách hợp đồng – Dai-ichi Life Việt Nam</title>
                        <meta name="description"
                              content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                        <meta name="robots" content="noindex, nofollow"/>
                        <meta property="og:type" content="website"/>
                        <meta property="og:url" content={FE_BASE_URL + "/mypolicyinfo"}/>
                        <meta property="og:title" content="Danh sách hợp đồng - Dai-ichi Life Việt Nam"/>
                        <meta property="og:description"
                              content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                        <meta property="og:image"
                              content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                        <link rel="canonical" href={FE_BASE_URL + "/mypolicyinfo"}/>
                    </Helmet>
                }
                <main className="logined" id="main-id">
                    <div className="main-warpper basic-mainflex">
                        <section className="sccard-warpper">
                            <h5 className="basic-bold">Vui lòng chọn hợp đồng:</h5>

                            <div className="card-warpper">
                                <LoadingIndicator area="policyList-by-cliID"/>
                                {this.state.ClientProfile !== null && this.state.ClientProfile.map((item, index) => (
                                    <div className="item">
                                        <div className="card"
                                             onClick={() => showCardInfo(item.PolicyID, index, item.AgentName, item.PolicyClassCD)}
                                             id={index}>
                                            <div className="card__header">
                                                <h4 className="basic-bold">{item.ProductName}</h4>
                                                <p>Dành
                                                    cho: {(item.PolicyLIName !== undefined && item.PolicyLIName !== '' && item.PolicyLIName !== null) ? (toTitleCase((item.PolicyLIName).toLowerCase())) : ''}</p>
                                                <div className="d-flex" style={{ justifyContent: 'space-between' }}>
                                                    {item.PolicyStatus.length < 25 ?
                                                        <p>Hợp đồng: {item.PolicyID}</p> :
                                                        <p className="policy">Hợp đồng: {item.PolicyID}</p>}
                                                    {(item.PolicyStatusCode === 'E' || item.PolicyStatusCode === 'B') ? (
                                                        <div className="dcstatus">
                                                            {item.PolicyStatus.length < 25 ?
                                                                <p className="inactive">{item.PolicyStatus}</p> :
                                                                <p className="inactiveLong">{item.PolicyStatus.replaceAll('(', ' (')}</p>}
                                                        </div>) : (
                                                        <div className="dcstatus">
                                                            {item.PolicyStatus.length < 25 ?
                                                                <p className="active">{item.PolicyStatus}</p> :
                                                                <p className="activeLong">{item.PolicyStatus.replaceAll('(', ' (')}</p>}
                                                        </div>
                                                    )}
                                                </div>
                                                <div className="choose">
                                                    <div className="dot"></div>
                                                </div>
                                            </div>
                                            <div className="card__footer">
                                                <div className="card__footer-item">
                                                    <p>Ngày hiệu lực</p>
                                                    <p>{item.PolIssEffDate}</p>
                                                </div>
                                                <div className="card__footer-item">
                                                    <p>Số tiền bảo hiểm</p>
                                                    <p className="basic-red basic-semibold">{item.FaceAmount} VNĐ</p>
                                                </div>
                                                <div className="card__footer-item">
                                                    <p>Đại lý bảo hiểm</p>
                                                    <p>{item.AgentName ? item.AgentName.toUpperCase() : ''}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                <p>Tiếp tục</p>
                                <i><img src="../img/icon/arrow-left.svg" alt=""/></i>
                            </div>
                        </section>


                        <section className="sccontract-warpper">
                            {this.state.isEmpty ? (
                                <div className="breadcrums" style={{backgroundColor: '#ffffff'}}>
                                    <div className="breadcrums__item">
                                        <p>Thông tin hợp đồng</p>
                                        <p className='breadcrums__item_arrow'>></p>
                                    </div>
                                    <div className="breadcrums__item">
                                        <p>Danh sách hợp đồng</p>
                                        <span></span>
                                    </div>
                                </div>) : (
                                <div className="breadcrums" style={{backgroundColor: '#f1f1f1'}}>
                                    <div className="breadcrums__item">
                                        <p>Thông tin hợp đồng</p>
                                        <p className='breadcrums__item_arrow'>></p>
                                    </div>
                                    <div className="breadcrums__item">
                                        <p>Danh sách hợp đồng</p>
                                        <span></span>
                                    </div>
                                </div>)}
                            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                <p>Chọn thông tin</p>
                                <i><img src="../img/icon/return_option.svg" alt=""/></i>
                            </div>
                            {this.state.isEmpty ? (
                                <div className="sccontract-container" style={{backgroundColor: '#ffffff'}}>
                                    <div className="insurance">
                                        <div className="empty">
                                        {this.state.isNonePolicyList ? (
                                            <>
                                             <div className="icon">
                                                <img src="../img/icon/timeout.svg" alt=""/>
                                             </div>
                                             {this.state.isReloadPolicyList ? (
                                                <>
                                                <p style={{paddingTop: '20px'}}><b style={{fontWeight: 1000}}>Hệ thống đang bận​</b></p>
                                                 <p style={{paddingTop: '10px'}}>Vui lòng nhấn nút 'Thử lại' bên dưới để tải lại thông tin hợp đồng​</p>
                                                 <p> <div className="btn-wrapper center-all">
                                                         <button className="btn btn-primary center-all"  style={{justifyContent: 'center'}} onClick={()=>{
                                                            this.getPolicyListByCLIID(true);
                                                         }}>Thử lại</button>
                                                     </div>
                                                 </p>
                                                 
                                                </>
                                                ) : (
                                                    <>
                                                        <p style={{paddingTop: '20px'}}><b style={{fontWeight: 1000}}>Hệ thống đang bị gián đoạn​</b></p>
                                                        <p style={{paddingTop: '10px'}}>Quý khách vui lòng thử lại sau​</p>
                                                    </>

                                                )}
                                                </>
                                           ) : (
                                            <>
                                           <div className="icon">
                                                <img src="../img/icon/empty.svg" alt=""/>
                                             </div>
                                             {this.state.isRetrying ? <p style={{paddingTop: '20px'}}>
                                             <>
                                                        <p style={{paddingTop: '10px'}}>Đang cập nhật dữ liệu</p>
                                                        <p style={{paddingTop: '10px'}}>Vui lòng đợi trong giây lát</p>
                                                    </>
                                             </p> 
                                             :<p style={{paddingTop: '20px'}}>Thông tin chi tiết sẽ hiển thị khi bạn
                                             chọn một hợp đồng ở bên trái.</p>}
                                             </>
                                            )}
                                            
                                        </div>
                                    </div>
                                </div>
                            ) : (

                                <div className="contract-list-menu">
                                    <div className="contract-list-menu__item" id="contractFeeButton"
                                         onClick={() => this.callAPIcontractFee('')}>
                                        <div className="contract-menu-content">
                                            <div className="item__icon">
                                                <img src={phibaohiem} alt=""/>
                                            </div>
                                            <div className="item__title"><p>Phí bảo hiểm</p></div>
                                            <div className="item__polygon-active" style={{margin: '5px'}}></div>
                                        </div>
                                    </div>

                                    <div className="contract-list-menu__item" id="contractProductsButton"
                                         onClick={() => this.callAPIcontractProducts('')}>
                                        <div className="contract-menu-content">
                                            <div className="item__icon">
                                                <img src={sanphambaohiem} alt=""/>
                                            </div>
                                            <div className="item__title"><p>Sản phẩm bảo hiểm</p></div>
                                            <div className="item__polygon-active" style={{margin: '5px'}}></div>
                                        </div>
                                    </div>

                                    <div className="contract-list-menu__item" id="contractValueButton"
                                         onClick={() => this.callAPIcontractValue('')}>
                                        <div className="contract-menu-content">
                                            <div className="item__icon">
                                                <img src={giatrihopdong} alt=""/>
                                            </div>
                                            <div className="item__title"><p>Giá trị hợp đồng</p></div>
                                            <div className="item__polygon-active" style={{margin: '5px'}}></div>
                                        </div>
                                    </div>

                                    <div className="contract-list-menu__item" id="contractBeneButton"
                                         onClick={() => this.callAPIcontractBene('')}>
                                        <div className="contract-menu-content">
                                            <div className="item__icon">
                                                <img src={nguoithuhuong} alt=""/>
                                            </div>
                                            <div className="item__title"><p>Người thụ hưởng</p></div>
                                            <div className="item__polygon-active" style={{margin: '5px'}}></div>
                                        </div>
                                    </div>

                                    <div className="contract-list-menu__item" id="contractAgentButton"
                                         onClick={() => this.callAPIcontractAgent('')}>
                                        <div className="contract-menu-content">
                                            <div className="item__icon">
                                                <img src={tuvanphucvu} alt=""/>
                                            </div>
                                            <div className="item__title"><p>Đại lý bảo hiểm</p></div>
                                            <div className="item__polygon-active" style={{margin: '5px'}}></div>
                                        </div>
                                    </div>
                                </div>
                            )}


                            <div className="tthd-main">
                                {this.state.loading && <LoadingIndicatorBasic />}
                                <div className="tthd-main__wrapper">
                                    <LoadingIndicator area="policy-info"/>

                                    <div className="contract-fee" id="contractFee">

                                        <h3 className="basic-semibold">Phí bảo hiểm <span>(VNĐ)</span></h3>

                                        {this.state.ClientProfileFee !== null && this.state.ClientProfileFee.map((item, index) => (
                                            <>
                                                <div className="contract-fee__form"
                                                     style={{border: '1.5px solid #e6e6e6', borderRadius: '6px'}}>
                                                    <div
                                                        className="contract-fee__form__item contract-fee__form__item-header">
                                                        <div className="header1">
                                                            <div className="header1-item">
                                                                <p className="basic-semibold">Số tiền</p>
                                                            </div>
                                                            <div className="header1-item">
                                                                <p className="basic-semibold">Kỳ đóng phí</p>
                                                            </div>
                                                        </div>
                                                        <div className="header2">
                                                            <div className="header2-item">
                                                                <p className="basic-semibold"
                                                                   style={{color: '#de181f'}}>{item.PolNextMPremAmt.split(';')[1]}</p>
                                                            </div>
                                                            <div className="header2-item">
                                                                <p className="basic-semibold"
                                                                   style={{color: '#292929'}}>{item.BillToDate.split(';')[1]}</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    {this.state.polID < 2000000 ? (
                                                        <>
                                                            <div className="item">
                                                                <div className="card__footer" style={{padding: '0px'}}>
                                                                    <div className="dropdown" style={{
                                                                        borderBottom: '1px solid #e6e6e6',
                                                                        padding: '0px'
                                                                    }}>
                                                                        <div className="dropdown__content">
                                                                            <p className="card-dropdown-title" style={{
                                                                                padding: '20px 12px 0px',
                                                                                cursor: 'default',
                                                                                fontSize: '1.6rem',
                                                                                color: '#727272'
                                                                            }}>Định kỳ đóng phí</p>
                                                                            <p className="arrow" style={{
                                                                                padding: '20px 20px 0px',
                                                                                cursor: 'default',
                                                                                fontSize: '1.6rem'
                                                                            }}>{item.Frequency.split(';')[1]}</p>
                                                                        </div>
                                                                        <div className="card__footer dropdown__items2">
                                                                        </div>
                                                                    </div>
                                                                    <div className="dropdown" id={'FeeDetails'} style={{
                                                                        borderBottom: '1px solid #e6e6e6',
                                                                        padding: '0px'
                                                                    }}>
                                                                        <div className="dropdown__content"
                                                                             onClick={() => dropdownFeeDetails()}>
                                                                            <p className="card-dropdown-title" style={{
                                                                                padding: '20px 12px 0px',
                                                                                fontSize: '1.6rem',
                                                                                color: '#727272'
                                                                            }}>Thông tin phí bảo hiểm</p>
                                                                            {this.state.isDropdownFeeDetails === false ?
                                                                                <p className="feeDetailsArrow"
                                                                                   style={{padding: '20px 20px 0px'}}>Xem
                                                                                    chi tiết&nbsp;<img
                                                                                        src={iconArrowLeftBrown}
                                                                                        alt="arrow-left-icon"/></p> :
                                                                                <p className="feeDetailsArrowDown"
                                                                                   style={{padding: '20px 20px 0px'}}>Thu
                                                                                    gọn&nbsp;<img
                                                                                        src={iconArrowDownBrown}
                                                                                        alt="arrow-down-icon"/></p>}
                                                                        </div>
                                                                        <div
                                                                            className={!this.state.isDropdownFeeDetails ? "card__footer dropdown__items" : "card__footer dropdown__items2"}>
                                                                            <div className="card__footer-item">
                                                                                <p className="TTHDdot" style={{
                                                                                    display: 'flex',
                                                                                    alignItems: 'center',
                                                                                    color: '#727272'
                                                                                }}>Phí định kỳ/cơ bản định kỳ</p>
                                                                                <p>{item.PolMPremAmt.split(';')[1]}</p>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div className="dropdown" id={'TotalFee'}>
                                                                        <div className="dropdown__content"
                                                                             onClick={() => dropdownTotalFee()}>
                                                                            <p className="card-dropdown-title" style={{
                                                                                padding: '20px 12px 0px',
                                                                                fontSize: '1.6rem',
                                                                                color: '#727272'
                                                                            }}>Phí bảo hiểm đã đóng</p>
                                                                            {this.state.isDropdownTotalFee === false ?
                                                                                <p className="feeDetailsArrow"
                                                                                   style={{padding: '20px 20px 0px'}}>Xem
                                                                                    chi tiết&nbsp;<img
                                                                                        src={iconArrowLeftBrown}
                                                                                        alt="arrow-left-icon"/></p> :
                                                                                <p className="feeDetailsArrowDown"
                                                                                   style={{padding: '20px 20px 0px'}}>Thu
                                                                                    gọn&nbsp;<img
                                                                                        src={iconArrowDownBrown}
                                                                                        alt="arrow-down-icon"/></p>}
                                                                        </div>
                                                                        <div
                                                                            className={!this.state.isDropdownTotalFee ? "card__footer dropdown__items" : "card__footer dropdown__items2"}>
                                                                            <div className="card__footer-item">
                                                                                <p className="remove-dot"
                                                                                   style={{color: '#727272'}}>Tổng phí
                                                                                    đã đóng</p>
                                                                                <p>{item.PolSndryAmt.split(';')[1] !== 'null' ? item.PolSndryAmt.split(';')[1] : '-'}</p>
                                                                            </div>
                                                                            <div className="card__footer-item">
                                                                                <p className="TTHDdot" style={{
                                                                                    display: 'flex',
                                                                                    alignItems: 'center',
                                                                                    color: '#727272'
                                                                                }}>Phí đóng trước cho kỳ tới</p>
                                                                                <p>{item.PremiumSuspense.split(';')[1]}</p>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </>
                                                    ) : (
                                                        <>
                                                            <div className="item">
                                                                <div className="card__footer" style={{padding: '0px'}}>
                                                                    <div className="dropdown" style={{
                                                                        borderBottom: '1px solid #e6e6e6',
                                                                        padding: '0px'
                                                                    }}>
                                                                        <div className="dropdown__content">
                                                                            <p className="card-dropdown-title" style={{
                                                                                padding: '20px 12px 0px',
                                                                                cursor: 'default',
                                                                                fontSize: '1.6rem',
                                                                                color: '#727272'
                                                                            }}>Định kỳ đóng phí</p>
                                                                            <p className="arrow" style={{
                                                                                padding: '20px 20px 0px',
                                                                                cursor: 'default',
                                                                                fontSize: '1.6rem'
                                                                            }}>{item.Frequency.split(';')[1]}</p>
                                                                        </div>
                                                                        <div className="card__footer dropdown__items2">
                                                                        </div>
                                                                    </div>
                                                                    <div className="dropdown" id={'FeeDetails'} style={{
                                                                        borderBottom: '1px solid #e6e6e6',
                                                                        padding: '0px'
                                                                    }}>
                                                                        <div className="dropdown__content"
                                                                             onClick={() => dropdownFeeDetails()}>
                                                                            <p className="card-dropdown-title" style={{
                                                                                padding: '20px 12px 0px',
                                                                                fontSize: '1.6rem',
                                                                                color: '#727272'
                                                                            }}>Thông tin phí bảo hiểm</p>
                                                                            {this.state.isDropdownFeeDetails === false ?
                                                                                <p className="feeDetailsArrow"
                                                                                   style={{padding: '20px 20px 0px'}}>Xem
                                                                                    chi tiết&nbsp;<img
                                                                                        src={iconArrowLeftBrown}
                                                                                        alt="arrow-left-icon"/></p> :
                                                                                <p className="feeDetailsArrowDown"
                                                                                   style={{padding: '20px 20px 0px'}}>Thu
                                                                                    gọn&nbsp;<img
                                                                                        src={iconArrowDownBrown}
                                                                                        alt="arrow-down-icon"/></p>}
                                                                        </div>
                                                                        <div
                                                                            className={!this.state.isDropdownFeeDetails ? "card__footer dropdown__items" : "card__footer dropdown__items2"}>
                                                                            <div className="card__footer-item">
                                                                                <p className="TTHDdot" style={{
                                                                                    display: 'flex',
                                                                                    alignItems: 'center',
                                                                                    color: '#727272'
                                                                                }}>Phí dự tính định kỳ</p>
                                                                                <p>{item.PolSndryAmt.split(';')[1]}</p>
                                                                            </div>
                                                                            <div className="card__footer-item">
                                                                                <p className="TTHDdot" style={{
                                                                                    display: 'flex',
                                                                                    alignItems: 'center',
                                                                                    color: '#727272'
                                                                                }}>Phí định kỳ/cơ bản định kỳ</p>
                                                                                <p>{item.PolMPremAmt.split(';')[1]}</p>
                                                                            </div>
                                                                            <div className="card__footer-item">
                                                                                <p className="TTHDdot" style={{
                                                                                    display: 'flex',
                                                                                    alignItems: 'center',
                                                                                    color: '#727272'
                                                                                }}>Phí các kỳ trước chưa đóng</p>
                                                                                <p>{item.PolGrsAPremAmt.split(';')[1]}</p>
                                                                            </div>

                                                                        </div>
                                                                    </div>
                                                                    <div className="dropdown" id={'TotalFee'}>
                                                                        <div className="dropdown__content"
                                                                             onClick={() => dropdownTotalFee()}>
                                                                            <p className="card-dropdown-title" style={{
                                                                                padding: '20px 12px 0px',
                                                                                fontSize: '1.6rem',
                                                                                color: '#727272'
                                                                            }}>Phí bảo hiểm đã đóng</p>
                                                                            {this.state.isDropdownTotalFee === false ?
                                                                                <p className="feeDetailsArrow"
                                                                                   style={{padding: '20px 20px 0px'}}>Xem
                                                                                    chi tiết&nbsp;<img
                                                                                        src={iconArrowLeftBrown}
                                                                                        alt="arrow-left-icon"/></p> :
                                                                                <p className="feeDetailsArrowDown"
                                                                                   style={{padding: '20px 20px 0px'}}>Thu
                                                                                    gọn&nbsp;<img
                                                                                        src={iconArrowDownBrown}
                                                                                        alt="arrow-down-icon"/></p>}
                                                                        </div>
                                                                        <div
                                                                            className={!this.state.isDropdownTotalFee ? "card__footer dropdown__items" : "card__footer dropdown__items2"}>
                                                                            <div className="card__footer-item">
                                                                                <p className="remove-dot"
                                                                                   style={{color: '#727272'}}>Tổng phí
                                                                                    đã đóng</p>
                                                                                <p>{item.TotalDeposit.split(';')[1] !== 'null' ? item.TotalDeposit.split(';')[1] : '-'}</p>
                                                                            </div>
                                                                            <div className="card__footer-item">
                                                                                <p className="TTHDdot" style={{
                                                                                    display: 'flex',
                                                                                    alignItems: 'center',
                                                                                    color: '#727272'
                                                                                }}>Phí cơ bản</p>
                                                                                <p>{item.BasicPrem.split(';')[1]}</p>
                                                                            </div>
                                                                            <div className="card__footer-item">
                                                                                <p className="TTHDdot" style={{
                                                                                    display: 'flex',
                                                                                    alignItems: 'center',
                                                                                    color: '#727272'
                                                                                }}>Phí đóng thêm</p>
                                                                                <p>{item.ExcessPrem.split(';')[1]}</p>
                                                                            </div>
                                                                            <div className="card__footer-item">
                                                                                <p className="TTHDdot" style={{
                                                                                    display: 'flex',
                                                                                    alignItems: 'center',
                                                                                    color: '#727272'
                                                                                }}>Phí đóng trước cho kỳ tới</p>
                                                                                <p>{item.PremiumSuspense.split(';')[1]}</p>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </>
                                                    )}


                                                </div>
                                                <div className="contract-fee__button">
                                                    <Link to={"/mypayment/" + this.state.polID}
                                                          onClick={selectedSubMenu('item-13-0', 'Hợp đồng của tôi')}>
                                                        <button className="btn btn-primary fullwidth">
                                                            <p>Đóng phí bảo hiểm</p>
                                                            <button></button>
                                                        </button>
                                                    </Link>
                                                </div>
                                            </>
                                        ))}

                                    </div>
                                    <div className="contract-products" id="contractProducts">
                                        {(this.state.ClientProfileProducts !== null
                                            && this.state.ClientProfileProducts !== ''
                                            && this.state.ClientProfileProducts !== undefined) && this.state.ClientProfileProducts.map((item, index) => (
                                            <div className="card-extend-container">
                                                <div className="card-extend-wrapper">
                                                    {((item[0].IsMainCvg === '1') ? (
                                                        item.map((mainproductsList, mainprdIndex) => (
                                                            <div className="item">
                                                                <div className="card__label">
                                                                    <p className="basic-bold">Bảo hiểm chính</p>
                                                                </div>
                                                                <div className="card" style={{cursor: 'default'}}>
                                                                    <div className="card__header">
                                                                        <h4 className="basic-bold">{mainproductsList.ProductName.split(';')[1]}</h4>
                                                                        <p>Dành
                                                                            cho {toTitleCase(mainproductsList.PolicyLIName.split(';')[1].toLowerCase())}</p>
                                                                    </div>
                                                                    <div className="card__footer">
                                                                        <div className="card__footer-item">
                                                                            <p>Tình trạng</p>
                                                                            <div className="dcstatus">
                                                                                {(mainproductsList.PolicyStatusCode === 'E'
                                                                                    || mainproductsList.PolicyStatusCode === 'B') ? (
                                                                                    <p className="inactive">{mainproductsList.PolicyStatus.split(';')[1]}</p>
                                                                                ) : (
                                                                                    <p className="active">{mainproductsList.PolicyStatus.split(';')[1]}</p>
                                                                                )}
                                                                            </div>
                                                                        </div>
                                                                        <div className="card__footer-item">
                                                                            <p>Số tiền bảo hiểm</p>
                                                                            {mainproductsList.FaceAmount.split(';')[1] === 'Chi tiết theo quy tắc điều khoản' ?
                                                                                <p>Chi tiết theo điều khoản Hợp
                                                                                    đồng</p> :
                                                                                <p>{mainproductsList.FaceAmount.split(';')[1]}</p>
                                                                            }
                                                                        </div>
                                                                        <div className="card__footer-item">
                                                                            <p>Phí định kỳ/cơ bản định kỳ</p>
                                                                            <p>{mainproductsList.PolMPremAmt.split(';')[1]}</p>
                                                                        </div>
                                                                        <div className="card__footer-item">
                                                                            <p>Ngày hiệu lực</p>
                                                                            <p>{mainproductsList.PolIssEffDate.split(';')[1]}</p>
                                                                        </div>
                                                                        <div className="card__footer-item">
                                                                            <p style={{display: 'inline'}}>{mainproductsList.PolQuarDate.split(';')[0]}</p>
                                                                            {mainproductsList.CvgNote &&
                                                                                <i className="info-icon-light" style={{
                                                                                    marginTop: '-6.5px',
                                                                                    width: '262px'
                                                                                }}
                                                                                   onClick={() => showNotice(mainproductsList.CvgNote)}
                                                                                ><img src="../../img/icon/info.svg"
                                                                                      alt="information-icon"
                                                                                      className="info-icon-light"
                                                                                /></i>
                                                                            }
                                                                            <p>{mainproductsList.PolQuarDate.split(';')[1]}</p>
                                                                        </div>
                                                                        <div className="card__footer-item">
                                                                            <p>{mainproductsList.PolExpiryDate.split(';')[0]}</p>
                                                                            <p>{mainproductsList.PolExpiryDate.split(';')[1]}</p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        ))
                                                    ) : (
                                                        <div className="item">
                                                            <div className="card__label card__label-brown">
                                                                <p className="basic-bold">Bảo hiểm BỔ SUNG</p>
                                                            </div>
                                                            <div className="card">
                                                                <div className="card__header">
                                                                    <h4 className="basic-bold">{item[0].ProductName.split(';')[1]}</h4>
                                                                </div>
                                                                {item.map((productsList, prdIndex) => (
                                                                    <div className="card__footer"
                                                                         onClick={() => dropdownContent(index, prdIndex)}
                                                                    >
                                                                        <div className="card__footer-item dropdown"
                                                                             id={'dropdownContent' + index + prdIndex}
                                                                             style={{
                                                                                 borderBottom: '1px solid #e7e7e7',
                                                                                 padding: '0px'
                                                                             }}>
                                                                            <div className="dropdown__content">
                                                                                <p className="card-dropdown-title"
                                                                                   style={{
                                                                                       color: '#292929',
                                                                                       padding: '20px 20px',
                                                                                       margin: '0px'
                                                                                   }}>{toTitleCase(productsList.PolicyLIName.split(';')[1].toLowerCase())}</p>
                                                                                <p className="arrow">></p>
                                                                            </div>
                                                                            <div
                                                                                className="card__footer dropdown__items2">
                                                                                <div className="card__footer-item">
                                                                                    <p>Tình trạng</p>
                                                                                    <div className="dcstatus">
                                                                                        {(item[0].PolicyStatus.split(';')[1] === 'Hết hiệu lực'
                                                                                            || item[0].PolicyStatus.split(';')[1] === 'Mất hiệu lực') ? (
                                                                                            <p className="inactive">{productsList.PolicyStatus.split(';')[1]}</p>
                                                                                        ) : (
                                                                                            <p className="active">{productsList.PolicyStatus.split(';')[1]}</p>
                                                                                        )}
                                                                                    </div>
                                                                                </div>
                                                                                <div className="card__footer-item"
                                                                                     style={{
                                                                                         borderBottom: '1px solid #e7e7e7',
                                                                                         padding: '20px 20px',
                                                                                         margin: '0px'
                                                                                     }}>
                                                                                    <p>Số tiền bảo hiểm</p>
                                                                                    {productsList.FaceAmount.split(';')[1] === 'Chi tiết theo quy tắc điều khoản' ?
                                                                                        <p>Chi tiết theo điều khoản Hợp
                                                                                            đồng</p> :
                                                                                        <p>{productsList.FaceAmount.split(';')[1]}</p>
                                                                                    }
                                                                                </div>
                                                                                <div className="card__footer-item"
                                                                                     style={{
                                                                                         borderBottom: '1px solid #e7e7e7',
                                                                                         padding: '20px 20px',
                                                                                         margin: '0px'
                                                                                     }}>
                                                                                    <p>Phí định kỳ/cơ bản định kỳ</p>
                                                                                    <p>{productsList.PolMPremAmt.split(';')[1]}</p>
                                                                                </div>
                                                                                <div className="card__footer-item"
                                                                                     style={{
                                                                                         borderBottom: '1px solid #e7e7e7',
                                                                                         padding: '20px 20px',
                                                                                         margin: '0px'
                                                                                     }}>
                                                                                    <p>Ngày hiệu lực</p>
                                                                                    {productsList.CvgNote &&
                                                                                        <i className="info-icon-light"
                                                                                           style={{
                                                                                               marginTop: '-6.5px',
                                                                                               width: '262px'
                                                                                           }} onClick={(event) => {
                                                                                            event.stopPropagation();
                                                                                            showNoticeEffectiveDate(productsList.CvgNote);
                                                                                        }}
                                                                                        ><img
                                                                                            src="../../img/icon/info.svg"
                                                                                            alt="information-icon"
                                                                                            className="info-icon-light"
                                                                                        /></i>
                                                                                    }
                                                                                    <p>{productsList.PolIssEffDate.split(';')[1]}</p>
                                                                                </div>
                                                                                <div className="card__footer-item"
                                                                                     style={{
                                                                                         borderBottom: '1px solid #e7e7e7',
                                                                                         padding: '20px 20px',
                                                                                         margin: '0px'
                                                                                     }}>
                                                                                    <p>{productsList.PolQuarDate.split(';')[0]}</p>
                                                                                    <p>{productsList.PolQuarDate.split(';')[1]}</p>
                                                                                </div>
                                                                                <div className="card__footer-item"
                                                                                     style={{
                                                                                         padding: '20px 20px',
                                                                                         margin: '0px'
                                                                                     }}>
                                                                                    <p>{productsList.PolExpiryDate.split(';')[0]}</p>
                                                                                    <p>{productsList.PolExpiryDate.split(';')[1]}</p>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                ))}
                                                            </div>
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                    <div className="contract-value" id="contractValue">
                                        {this.state.ClientProfileValue != null && this.state.ClientProfileValue.map((item, index) => (

                                            <div className="card-extend-container">
                                                {this.state.polID < 2000000 && (
                                                    <div className="card-extend-wrapper">
                                                        <div className="contract-fee__form">
                                                            <div className="contract-fee__form__item-footer" style={{borderBottom : '1px solid #e6e6e6'}}>
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="remove-dot"
                                                                       style={{fontSize: '1.6rem'}}>Giá trị hợp đồng</p>
                                                                    <p style={{fontSize: '1.6rem'}}>{item.LastAnnDate.split(';')[1]}</p>
                                                                </div>
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot">Lãi chia tích lũy</p>
                                                                    <p>{item.Dividend.split(';')[1]}</p>
                                                                </div>
                                                                {this.state.showDividendNote === true &&
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot_remove-dot">(đã bao gồm trong Giá trị hợp đồng)</p>
                                                                    <p></p>
                                                                </div>  
                                                                }
                                                            </div>
                                                            {/* <div className="contract-fee__form__item-footer">
                                                                <p>Giá trị hợp đồng</p>
                                                                <p>{item.LastAnnDate.split(';')[1]}</p>
                                                            </div>
                                                            <div className="contract-fee__form__item">
                                                                <p>Lãi chia tích lũy</p>
                                                                <p>{item.Dividend.split(';')[1]}</p>
                                                            </div> */}
                                                            <div className="contract-fee__form__item">
                                                                <p>Tiền mặt định kỳ</p>
                                                                <p>{item.Coupon.split(';')[1]}</p>
                                                            </div>
                                                            <div className="contract-fee__form__item-footer">
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="remove-dot"
                                                                       style={{fontSize: '1.6rem'}}>Tạm ứng từ giá trị
                                                                        hoàn lại</p>
                                                                    <p style={{fontSize: '1.6rem'}}>{item.PrincIntAmt.split(';')[1]}</p>
                                                                </div>
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot">Tạm ứng tiền mặt (OPL)</p>
                                                                    <p>{item.Loan.split(';')[1]}</p>
                                                                </div>
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot">Đóng phí bảo hiểm tự động
                                                                        (APL)</p>
                                                                    <p>{item.APL.split(';')[1]}</p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                )}
                                                {this.state.polID >= 8000000 && (
                                                    <div className="card-extend-wrapper">
                                                        <div className="contract-fee__form">
                                                            <div
                                                                className="contract-fee__form__item contract-fee__form__item-flexRow">
                                                                <p className="basic-semibold">Giá trị tài khoản đầu ngày</p>
                                                                <p className="basic-semibold">{item.PolAccountValue.split(';')[1]}</p>
                                                            </div>
                                                            <div className="contract-fee__form__item-footer">
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot">Phí bảo hiểm đầu tư vào các
                                                                        quỹ</p>
                                                                    <p>{item.AllocateMPrem.split(';')[1]}</p>
                                                                </div>
                                                                {/*<div className="contract-fee__form__item-footer__item">
                  <p className="add-dot">Lãi đầu tư</p>
                  <p>{item.ACFIP.split(';')[1]}</p>
                </div>*/}
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot">Thưởng duy trì hợp đồng</p>
                                                                    <p>{item.LoyaltyBonus.split(';')[1]}</p>
                                                                </div>
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot">Chi phí rủi ro và quản lý hợp
                                                                        đồng</p>
                                                                    <p>{item.CiaShrtAmt.split(';')[1]}</p>
                                                                </div>
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot">Số tiền đã rút từ các quỹ (đã
                                                                        bao gồm phí rút tiền)</p>
                                                                    <p>{item.NetWithDrawal.split(';')[1]}</p>
                                                                </div>
                                                                {/*<div className="contract-fee__form__item-footer__item">
                  <p className="add-dot">Tạm ứng từ giá trị hoàn lại</p>
                  <p>{item.PrincIntAmt.split(';')[1]}</p>
                </div>*/}
                                                            </div>
                                                        </div>
                                                        <div className="contract-fee__form contract-fee__form2">
                                                            {(this.state.PolicyInfo !== null && this.state.PolicyInfo !== '' && this.state.PolicyInfo !== undefined) && (
                                                                <div>
                                                                    <div className="contract-fee__form__item-header">
                                                                        <div className="header1">
                                                                            <p className="basic-bold">Chi tiết các
                                                                                quỹ</p>
                                                                        </div>
                                                                        {((this.state.PolicyInfo[0] !== null) && (this.state.PolicyInfo[0] !== undefined)) &&
                                                                            <div className="header2 dropdown"
                                                                                 onClick={() => showFundValue0()}
                                                                                 id="fundvalue0"
                                                                                 style={{paddingLeft: '4px'}}>
                                                                                <div
                                                                                    className="header2__dropdown__content dropdown__content">
                                                                                    <div
                                                                                        className=" contract-fee__form__item-footer__item">
                                                                                        <p className="add-dot">{this.state.PolicyInfo[0].FundNameCode.split(';')[1]}</p>
                                                                                    </div>
                                                                                    <p className="arrow"
                                                                                       style={{paddingRight: '4px'}}>></p>
                                                                                </div>
                                                                                <div
                                                                                    className="header2__dropdown__items2 dropdown__items2"
                                                                                    style={{paddingLeft: '8px'}}>
                                                                                    <div className="item">
                                                                                        <p>Tỉ lệ đầu tư (%)</p>
                                                                                        <p style={{paddingRight: '14px'}}>{this.state.PolicyInfo[0].cdi_alloc_pct.split(';')[1]}</p>
                                                                                    </div>
                                                                                    <div className="item">
                                                                                        <p>Số dư đơn vị Quỹ cuối kỳ</p>
                                                                                        <p style={{paddingRight: '14px'}}>{this.state.PolicyInfo[0].fia_unit_cum_qty.split(';')[1]}</p>
                                                                                    </div>
                                                                                    <div className="item">
                                                                                        <p>Giá đơn vị Quỹ (VNĐ)</p>
                                                                                        <p style={{paddingRight: '14px'}}>{this.state.PolicyInfo[0].fia_unit_pric_amt.split(';')[1]}</p>
                                                                                    </div>
                                                                                    <div className="item">
                                                                                        <p>Giá trị Quỹ (VNĐ)</p>
                                                                                        <p style={{paddingRight: '14px'}}>{this.state.PolicyInfo[0].fund_val_each_funf.split(';')[1]}</p>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        }
                                                                    </div>
                                                                    {
                                                                        this.state.PolicyInfo.map((item, index) => {
                                                                            if (item != null && index !== 0) {
                                                                                return (
                                                                                    <div
                                                                                        className="contract-fee__form__item dropdown"
                                                                                        onClick={() => showFundValue(index)}
                                                                                        id={"fundvalue" + index}>
                                                                                        <div
                                                                                            className="contract-fee__form__item__dropdown__content dropdown__content">
                                                                                            <div
                                                                                                className=" contract-fee__form__item-footer__item">
                                                                                                <p className="add-dot">{item.FundNameCode.split(';')[1]}</p>
                                                                                            </div>
                                                                                            <p className="arrow">></p>
                                                                                        </div>
                                                                                        <div
                                                                                            className="contract-fee__form__item__dropdown__items2 dropdown__items2">
                                                                                            <div className="item">
                                                                                                <p>Tỉ lệ đầu tư (%)</p>
                                                                                                <p>{item.cdi_alloc_pct.split(';')[1]}</p>
                                                                                            </div>
                                                                                            <div className="item">
                                                                                                <p>Số dư đơn vị Quỹ
                                                                                                    cuối kỳ</p>
                                                                                                <p>{item.fia_unit_cum_qty.split(';')[1]}</p>
                                                                                            </div>
                                                                                            <div className="item">
                                                                                                <p>Giá đơn vị Quỹ
                                                                                                    (VNĐ)</p>
                                                                                                <p>{item.fia_unit_pric_amt.split(';')[1]}</p>
                                                                                            </div>
                                                                                            <div className="item">
                                                                                                <p>Giá trị Quỹ
                                                                                                    (VNĐ)</p>
                                                                                                <p>{item.fund_val_each_funf.split(';')[1]}</p>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                )
                                                                            }

                                                                        })}

                                                                </div>
                                                            )}
                                                        </div>
                                                    </div>
                                                )}
                                                {this.state.polID > 2000000 && this.state.polID < 8000000 && (
                                                    <div className="card-extend-wrapper">
                                                        <div className="contract-fee__form contract-fee__form2">
                                                            <div
                                                                className="contract-fee__form__item contract-fee__form__item-flexRow">
                                                                <p className="basic-semibold">Giá trị tài khoản đầu ngày</p>
                                                                    
                                                                <p className="basic-semibold">{item.PolAccountValue.split(';')[1]}</p>
                                                            </div>
                                                            <div className="contract-fee__form__item-footer">
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot">Tổng phí bảo hiểm đã phân
                                                                        bổ</p>
                                                                    <p>{item.AllocateMPrem.split(';')[1]}</p>
                                                                </div>
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot">Lãi đầu tư</p>
                                                                    <p>{item.ACFIP.split(';')[1]}</p>
                                                                </div>
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot">Thưởng duy trì hợp đồng</p>
                                                                    <p>{item.LoyaltyBonus.split(';')[1]}</p>
                                                                </div>
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot">Chi phí rủi ro và quản lý hợp
                                                                        đồng</p>
                                                                    <p>{item.TotalDeduct.split(';')[1]}</p>
                                                                </div>
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot">Tiền rút giá trị tài khoản
                                                                        (Đã bao gồm phí rút)</p>
                                                                    <p>{item.NetWithDrawal.split(';')[1]}</p>
                                                                </div>
                                                                <div className="contract-fee__form__item-footer__item">
                                                                    <p className="add-dot">Tạm ứng từ giá trị hoàn
                                                                        lại</p>
                                                                    <p>{item.PrincIntAmt.split(';')[1]}</p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                )}

                                            </div>
                                        ))}
                                    </div>
                                    <div className="contract-beneficiary card-extend-container" id="contractBene">
                                        {this.state.ClientProfileBene !== null && this.state.ClientProfileBene.map((item, index) => (
                                            <div className="card-extend-wrapper">
                                                <div className="item">
                                                    <div className="card">
                                                        <div className="card__header">
                                                            <h4 className="basic-bold">{toTitleCase(item.FullName.split(';')[1].toLowerCase())}</h4>
                                                            <p>Tỉ lệ thụ hưởng: {item.Note.split(';')[1]}</p>
                                                        </div>
                                                        <div className="card__footer">
                                                            <div className="card__footer-item">
                                                                <p>Ngày sinh</p>
                                                                <p>{format(new Date(item.DOB.split(';')[1]), 'dd/MM/yyyy')}</p>
                                                            </div>
                                                            <div className="card__footer-item">
                                                                <p>Số giấy tờ tuỳ thân</p>
                                                                <p>{item.POID.split(';')[1]}</p>
                                                            </div>
                                                            <div className="card__footer-item">
                                                                <p>Quan hệ với người được bảo hiểm</p>
                                                                <p>{item.RelationPO.split(';')[1]}</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                    <div className="contract-advise" id="contractAgent">
                                        {this.state.ClientProfileAgent !== null && this.state.ClientProfileAgent.map((item, index) => (
                                            <div className="consulting-service">
                                                <div className="avatar-field">
                                                    <div className="avatar">
                                                        <img src="../img/ava_tvtc.png" alt=""/>
                                                    </div>
                                                    <h3 className="basic-bold">{item.ServAgent.split(';')[1].split('-')[1].trim()}</h3>
                                                    <p>{item.AgentLevel}</p>
                                                </div>
                                                <div className="form">
                                                    <div className="form__item">
                                                        <p>Tình trạng</p>
                                                        <div className="dcstatus"
                                                             style={{alignItems: 'center', display: 'flex'}}>
                                                            {item.AgentStatus === 'Term' ? (
                                                                <p className="inactiveContent">Ngưng hoạt động</p>
                                                            ) : (
                                                                <p className="activeContent">Đang hoạt động</p>
                                                            )}
                                                        </div>
                                                    </div>
                                                    <div className="form__item">
                                                        <p>Mã đại lý</p>
                                                        <p>{item.ServAgent.split(';')[1].split('-')[0].trim()}</p>
                                                    </div>
                                                    <div className="form__item">
                                                        <p>Văn phòng</p>
                                                        <p>{item.CSSaleOffice}</p>
                                                    </div>
                                                    {item.AgentStatus !== 'Term' && (
                                                        <div className="form__item">
                                                            <p>Điện thoại</p>
                                                            <p>{item.CellPhone.split(';')[1]}</p>
                                                        </div>
                                                    )}
                                                    {item.AgentStatus !== 'Term' && (
                                                        <div className="form__item"
                                                             style={{borderBottom: '0px solid #e6e6e6'}}>
                                                            <p>Email</p>
                                                            <p>{item.ContactEmail.split(';')[1]}</p>
                                                        </div>
                                                    )}
                                                </div>
                                                {item.AgentStatus !== 'Term' ? (
                                                    <div className="contact">
                                                        <div className="mail-icon">
                                                            {(item.ContactEmail.split(';')[1] != null
                                                                && item.ContactEmail.split(';')[1] != ''
                                                                && item.ContactEmail.split(';')[1] != undefined) ?
                                                                <a href={"mailto:" + item.ContactEmail.split(';')[1]}><img
                                                                    src="../img/icon/icon_email.svg" alt=""/></a> :
                                                                <a href="mailto: customer.services@dai-ichi-life.com.vn"><img
                                                                    src="../img/icon/icon_email.svg" alt=""/></a>}
                                                        </div>
                                                        <div className="phone-icon">
                                                            <a href={"tel:" + item.CellPhone.split(';')[1]}><img
                                                                src="../img/icon/icon_phone.svg" alt=""/></a>
                                                        </div>
                                                    </div>
                                                ) : (
                                                    <><p><br/></p><p className="basic-bold">Liên hệ với Dai-ichi Life
                                                        Việt Nam</p>
                                                        <div className="contact">
                                                            <div className="mail-icon">
                                                                <a href="mailto: customer.services@dai-ichi-life.com.vn"><img
                                                                    src="../img/icon/icon_email.svg" alt=""/></a>
                                                            </div>
                                                            <div className="phone-icon">
                                                                <a href="tel:02838100888"><img
                                                                    src="../img/icon/icon_phone.svg" alt=""/></a>
                                                            </div>
                                                        </div>
                                                    </>
                                                )}
                                            </div>
                                        ))}
                                    </div>

                                </div>
                            </div>
                        </section>
                    </div>
                </main>
                {this.state.showNotice &&
                    <NoticePopup closePopup={closeNotice} title={this.state.title} msg={this.state.msg}
                                 imgPath={FE_BASE_URL + '/img/popup/fee-time.svg'}/>
                }
                {this.state.showNoticeEffectiveDate &&
                    <NoticePopup closePopup={closeNoticeEffectiveDate} title={this.state.title} msg={this.state.msg}
                                 imgPath={FE_BASE_URL + '/img/icon/dieukhoan_icon.svg'}/>
                }
            </div>

        );

    }

}


export default PolInfo;