import React, {Component} from 'react';
import ShortcutBar from './ShortcutBar';
import Products from '../home/Products';
import Promotion from '../home/Promotion';
import Shortcut from '../home/Shortcut';
import NEWS from '../home/NEWS';
import DownloadApp from '../home/DownloadApp';
import {ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, CMS_HOMEPAGE, AUTHENTICATION, COMPANY_KEY} from '../constants';
import {cms} from '../util/APIUtils';
import {setSession, getSession, getDeviceId} from '../util/common';
import '../common/Common.css';
import Banner from './Banner';
import { isEmpty } from 'lodash';

let htmlFeeReminder = "";
let cmsbanner = null;
let cmsshortcutBar = null;
let cmsproducts = null;
let cmsnews = null;
let cmsshortcut = null;

class MainPublic extends Component {
    constructor(props) {
        super(props);
        this.state = {
            homepage: null, feereminder: null,
            jsonFeeRemider: {
                URL: 'CPGetPolicyInfoByPOLID',
                JsonInput: {
                    jsonDataInput: {
                        Project: 'mcp',
                        APIToken: 'ac4810ae39294ef2a48c239ef6ded986',
                        Authentication: AUTHENTICATION,
                        DeviceId: '8e8056d71967b3f6',
                        Action: 'PolicyPolDueToDate',
                        OS: 'Samsung_SM-A125F-Android-11',
                        UserID: '0000774450',
                        Company: 'mcp_dlvn'
                    }
                }
            },
            jsonInput2: {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    APIToken: getSession(ACCESS_TOKEN),
                    Project: 'mcp',
                    Action: 'PolicyPolDueToDate',
                    ClientID: getSession(CLIENT_ID),
                    UserID: getSession(USER_LOGIN),
                    LifeInsureID: ''
                }
            },
            loaded: false,
            cmsbanner: null,
            cmsshortcutBar: null,
            cmsproducts: null,
            cmsnews: null,
            cmsshortcut: null,
            clientId: null,
            clientProfile: null,
            isLogin: null,
            accountRole: null,
            postback: false

        };
        this.next = this.next.bind(this);
        this.previous = this.previous.bind(this);
        this.carousel = React.createRef();

    }

    next() {
        this.carousel.next();
    }

    previous() {
        this.carousel.prev();
    }

    componentDidMount() {
        this.GetHomePage();
    }

    GetHomePage() {
        if (!isEmpty(getSession(CMS_HOMEPAGE)) && (getSession(CMS_HOMEPAGE) !== undefined) && (getSession(CMS_HOMEPAGE) !== 'undefined') && (getSession(CMS_HOMEPAGE) !== null) && (getSession(CMS_HOMEPAGE) !== 'null')) {
            let response = JSON.parse(getSession(CMS_HOMEPAGE));
            cmsbanner = this.GetContent(response?.data, "banner");
            cmsshortcutBar = this.GetContent(response?.data, "shortcutBar");
            cmsproducts = this.GetContent(response?.data, "favoriteProducts");
            cmsnews = this.GetContent(response?.data, "articleByCategory");
            cmsshortcut = this.GetContent(response?.data, "shortcut");
            this.setState({
                homepage: response
            });
        } else {
            const homepageRequest = {"Action": "GetHomePage"};
            cms(homepageRequest)
                .then(response => {
                    if (!isEmpty(response) && response?.data && (response?.data !== null)) {
                        setSession(CMS_HOMEPAGE, JSON.stringify(response));
                        cmsbanner = this.GetContent(response?.data, "banner");
                        cmsshortcutBar = this.GetContent(response?.data, "shortcutBar");
                        cmsproducts = this.GetContent(response?.data, "favoriteProducts");
                        cmsnews = this.GetContent(response?.data, "articleByCategory");
                        cmsshortcut = this.GetContent(response?.data, "shortcut");
                        this.setState({homepage: response});
                    }

                }).catch(error => {
            });
        }
    }

    GetContent = (jsonState, cmstype) => {
        let cmsbanner = null;
        if (jsonState) {
            jsonState.forEach(function (item, index, array) {
                if (cmstype === item.type) {

                    switch (item.type) {
                        case 'banner': {
                            cmsbanner = item.data.items;
                            break;
                        }
                        case 'shortcut': {
                            cmsbanner = item.data.items;
                            break;
                        }
                        case 'favoriteProducts': {
                            cmsbanner = item.data;
                            break;
                        }
                        case 'shortcutBar': {
                            cmsbanner = item.data.items;
                            break;
                        }
                        case 'articleByCategory': {
                            cmsbanner = item.data.items;
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
            })
        }
        return cmsbanner;
    }

    render() {
        const callbackApp = (hideMain) => {
            this.props.parentCallback(hideMain);
        }
        if (this.state.homepage) {
            return (
                <div>
                    <Banner cmsbanner={cmsbanner} LoginLoading={this.props.LoginLoading}
                            parentCallback={this.props.parentCallback} enscryptStr={this.props.enscryptStr}/>
                    <ShortcutBar cmsshortcutBar={cmsshortcutBar} parentCallback={this.props.parentCallback}/>
                    <Products cmsproducts={cmsproducts} parentCallback={this.props.parentCallback}/>
                    <Promotion parentCallback={this.props.parentCallback}/>
                    <Shortcut cmsshortcut={cmsshortcut} parentCallback={this.props.parentCallback}/>
                    <NEWS parentCallback={this.props.parentCallback}/>
                    <DownloadApp/>
                </div>
            )
        } else {
            return (
                <div>
                    <NEWS parentCallback={this.props.parentCallback}/>
                    <DownloadApp/>
                </div>
            )
        }

    }

}

export default MainPublic;