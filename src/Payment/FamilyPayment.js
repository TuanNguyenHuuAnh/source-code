import React from 'react';
import {
    ACCESS_TOKEN,
    EXPIRED_MESSAGE,
    USER_LOGIN,
    CLIENT_PROFILE,
    COMPANY_KEY,
    AUTHENTICATION,
    FE_BASE_URL,
    CLIENT_ID,
    PageScreen
} from '../constants';
import { CPGetPolicyInfoByPOLID, logoutSession, CPSaveLog } from '../util/APIUtils';
import PaymentKeyin from './PaymentKeyin';
import PaymentDetail from './PaymentDetail';
import 'antd/dist/antd.min.css';
import LoadingIndicator from '../common/LoadingIndicator2';
import {showMessage, getSession, getDeviceId, trackingEvent} from '../util/common';
import { Helmet } from "react-helmet";
import { Redirect } from 'react-router-dom';

class FamilyPayment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
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
                        Company: COMPANY_KEY
                    }
                }
            },
            jsonPolicySearch: {
                jsonDataInput: {
                    Company: '',
                    Project: 'mcp',
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    APIToken: getSession(ACCESS_TOKEN),
                    Action: 'PolicyClient',
                    OS: 'Samsung_SM-G973F-Android-7.1.2',
                    UserID: getSession(USER_LOGIN),
                    PolID: '001085110',
                    POID: '121648466',
                    DOB: "1987-02-03",
                }
            },
            jsonPolicySearchResponse: null,
            PolicyClientProfile: null,
            PolicyInfo: null,
            UserProfile: null,
            SearchResult: false,
            loading: true,
            loadingpaymentDetai: true,
            isLogin: '',
            renderMeta: false,
            policyInquiryExceed: ''

        };
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
            this.setState({ renderMeta: true });
        }).catch(error => {
            this.setState({ renderMeta: true });
        });
    }
    componentWillUnmount() {
        this.cpSaveLog(`Web_Close_${PageScreen.FAMILY_PAYMENT_PAGE}`);
        trackingEvent(
            "Đóng phí bảo hiểm",
            `Web_Close_${PageScreen.FAMILY_PAYMENT_PAGE}`,
            `Web_Close_${PageScreen.FAMILY_PAYMENT_PAGE}`,
        );
    }

    componentDidMount() {
        if (getSession(ACCESS_TOKEN) && getSession(CLIENT_PROFILE)) {
            this.setState({
                UserProfile: JSON.parse(getSession(CLIENT_PROFILE))
                , isLogin: "logined"
            });
        }
        trackingEvent(
            "Đóng phí bảo hiểm",
            `Web_Open_${PageScreen.FAMILY_PAYMENT_PAGE}`,
            `Web_Open_${PageScreen.FAMILY_PAYMENT_PAGE}`,
        );
        this.cpSaveLog(`Web_Open_${PageScreen.FAMILY_PAYMENT_PAGE}`);}

    convertDateToString = (value) => {
        var date = new Date(value); // M-D-YYYY
        var d = date.getDate();
        var m = date.getMonth() + 1;
        var y = date.getFullYear();
        var dateString = y + '-'  + (m <= 9 ? '0' + m : m) + '-' +  (d <= 9 ? '0' + d : d);
        return dateString;
    }

    callAPISearchPolicy(PolicyNo, insuredIDNo, insuredDOB) {
        var jsonState = this.state;
        jsonState.loading = false;
        jsonState.loadingpaymentDetai = false;
        jsonState.SearchResult = false;
        jsonState.jsonPolicySearch.jsonDataInput.PolID = PolicyNo;
        jsonState.jsonPolicySearch.jsonDataInput.POID = insuredIDNo;
        jsonState.jsonPolicySearch.jsonDataInput.DOB = insuredDOB;
        jsonState.jsonPolicySearch.jsonDataInput.Action = 'PolicyInquiry';
        jsonState.jsonPolicySearch.jsonDataInput.Company = COMPANY_KEY;
        this.setState(jsonState);
        const apiRequest = Object.assign({}, this.state.jsonPolicySearch);
        CPGetPolicyInfoByPOLID(apiRequest).then(Res => {
            jsonState.loading = true;
            let Response5 = Res.Response;
            jsonState.jsonPolicySearchResponse = Response5;
            jsonState.SearchResult = true;
            jsonState.loadingpaymentDetai = true;
            if (Response5.ErrLog && Response5.ErrLog === 'SUCCESSFUL') {

                if (Res.Response.PolicyInfo) {
                    jsonState.PolicyInfo = Res.Response.PolicyInfo;
                }
                else {
                    jsonState.PolicyInfo = null;
                }
                if (Res.Response.ClientProfile) {
                    var formattedDate = Response5.ClientProfile[0].DOB.trim().substr(0, 10);
                    if (insuredIDNo.trim() === Response5.ClientProfile[0].POID.trim()
                        && insuredDOB.trim() === formattedDate) {
                        jsonState.PolicyClientProfile = Response5.ClientProfile;

                    }
                    else {
                        jsonState.PolicyClientProfile = null;
                        jsonState.PolicyInfo = null;
                    }
                }
                else {
                    jsonState.PolicyClientProfile = null;
                    jsonState.PolicyInfo = null;
                }
                jsonState.policyInquiryExceed = '';
                this.setState(jsonState);


            } else if (Response5.Message === 'POLICY_INQUIRY_EXCEED') {
                jsonState.policyInquiryExceed = Response5.ErrLog;
                this.setState(jsonState);
            } else if (Response5.NewAPIToken === 'invalidtoken' || Response5.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: { authenticated: false, hideMain: false }

                })

            }
            else {
                jsonState.loading = true;
                jsonState.jsonPolicySearchResponse = null;
                jsonState.PolicyInfo = null;
                jsonState.PolicyClientProfile = null;
                this.setState(jsonState);
            }
            // console.log(this.state.PolicyInfo);
            // console.log(this.state.PolicyClientProfile);
        }).catch(error => {
            this.props.history.push('/maintainence');
        });
    }

    callbackPolicySearch = (PolicyNo, insuredIDNo, insuredDOB) => {
        this.goDetail();
        this.callAPISearchPolicy(PolicyNo, insuredIDNo, insuredDOB);
    }
    goDetail() {
        const main = document.getElementById("main-id");
        if (main) {
            main.classList.remove("nodata");
        }
    }

    render() {
        if (!this.state.UserProfile && getSession(ACCESS_TOKEN) && getSession(CLIENT_PROFILE)) {
            this.setState({
                UserProfile: JSON.parse(getSession(CLIENT_PROFILE))
                , isLogin: "logined"
            });
        }
        const goBack = () => {
            const main = document.getElementById("main-id");
            if (main) {
                main.classList.toggle("nodata");
            }
        }
        if (!getSession(ACCESS_TOKEN)) {
            return <Redirect to={{
                pathname: '/home'
            }} />;
        }
        return (
            <div>
                {this.state.renderMeta &&
                    <Helmet>
                        <title>Hợp đồng của người thân – Dai-ichi Life Việt Nam</title>
                        <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
                        <meta name="robots" content="noindex, nofollow" />
                        <meta property="og:type" content="website" />
                        <meta property="og:url" content={FE_BASE_URL + "/familypayment"} />
                        <meta property="og:title" content="Hợp đồng của người thân - Dai-ichi Life Việt Nam" />
                        <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
                        <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
                        <link rel="canonical" href={FE_BASE_URL + "/familypayment"} />
                    </Helmet>
                }
                <main className={this.state.isLogin} id="main-id">
                    <div className="main-warpper basic-mainflex">
                        <PaymentKeyin parentCallback={this.callbackPolicySearch} />
                        <section className="sccontract-warpper">
                            {this.state.loadingpaymentDetai && this.state.SearchResult === false ? (
                                <div className="breadcrums" style={{ backgroundColor: '#ffffff' }}>
                                    <div className="breadcrums__item">
                                        <p>Đóng phí bảo hiểm</p>
                                        <p className='breadcrums__item_arrow'>></p>
                                    </div>
                                    <div className="breadcrums__item">
                                        <p>Hợp đồng của người thân</p>
                                        <span></span>
                                    </div>
                                </div>) : (
                                <div className="breadcrums" style={{ backgroundColor: '#f1f1f1' }}>
                                    <div className="breadcrums__item">
                                        <p>Đóng phí bảo hiểm</p>
                                        <p className='breadcrums__item_arrow'>></p>
                                    </div>
                                    <div className="breadcrums__item">
                                        <p>Hợp đồng của người thân</p>
                                        <span></span>
                                    </div>
                                </div>

                            )}
                            <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                <p>Chọn thông tin</p>
                                <i><img src="img/icon/return_option.svg" alt="" /></i>
                            </div>
                            {!this.state.loadingpaymentDetai ? (<LoadingIndicator area="policy-info" />)
                                : (<PaymentDetail UserProfile={this.state.UserProfile} PolicyInfo={this.state.PolicyInfo}
                                    PolicyClientProfile={this.state.PolicyClientProfile} SearchResult={this.state.SearchResult} policyInquiryExceed={this.state.policyInquiryExceed}/>)
                            }
                        </section>
                    </div>

                </main>
            </div>
        )
    }
}


export default FamilyPayment;