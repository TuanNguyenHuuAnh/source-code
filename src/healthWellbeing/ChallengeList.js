import React, {Component} from 'react';
import {Link, withRouter} from "react-router-dom";
import {
    ACCESS_TOKEN,
    AKTIVO_ACCESS_TOKEN,
    AKTIVO_MEMBER_ID,
    AUTHENTICATION,
    CLIENT_ID, FE_BASE_URL,
    FROM_APP,
    LINK_MENU_NAME,
    LINK_MENU_NAME_ID,
    LINK_SUB_MENU_NAME,
    LINK_SUB_MENU_NAME_ID,
    PageScreen,
    USER_LOGIN,
    WEB_BROWSER_VERSION
} from '../constants';
import {CPSaveLog, getChallenges} from '../util/APIUtils';
import {getDeviceId, getSession, numOfDates, setSession, trackingEvent} from '../util/common';
import '../dest/swiper-bundle.min.css';
import SwiperCore, {Autoplay, Navigation, Pagination} from 'swiper';
import ChallengesSwiper from "../components/CustomSwiper/ChallengesSwiper";
import homeIcon from '../img/icon/homeIcon.svg';
import {Helmet} from "react-helmet";

SwiperCore.use([Autoplay, Pagination, Navigation]);

class ChallengeList extends Component {
    _isMounted = false;

    constructor(props) {
        super(props);
        this.state = {
            toggle: false,
            showRequireLogin: false,
            challenging: null,
            coming: null
        }
    }


    getChallenges() {
        let challengesRequest = {
            SubmitFrom: WEB_BROWSER_VERSION,
            APIToken: getSession(AKTIVO_ACCESS_TOKEN)?getSession(AKTIVO_ACCESS_TOKEN): '',
            ClientID: getSession(AKTIVO_MEMBER_ID)?getSession(AKTIVO_MEMBER_ID):''
        }
        getChallenges(challengesRequest).then(response => {
            let challenging = response.data.filter(item => {
                return numOfDates(item.start_date, new Date()) >= 0;
            });
            let coming = response.data.filter(item => {
                return numOfDates(item.start_date, new Date()) < 0;
            });
            if (challenging && challenging.length > 0) {
                this.setState({challenging: challenging});
            }
            if (coming && coming.length > 0) {
                this.setState({coming: coming});
            }
            
        }).catch(error => {
            console.log(error);
        });
    }

    componentDidMount() {
        this._isMounted = true;
        this.cpSaveLog(`Web_Open_${PageScreen.HEALTH_WELLBEING_CHALLENGES_LIST}`);
        trackingEvent(
            "Sống vui khỏe",
            `Web_Open_${PageScreen.HEALTH_WELLBEING_CHALLENGES_LIST}`,
            `Web_Open_${PageScreen.HEALTH_WELLBEING_CHALLENGES_LIST}`,
        );
        //highlight the active menu
        setSession(LINK_MENU_NAME, 'Trang chủ');
        setSession(LINK_MENU_NAME_ID, 'ah4');
        setSession(LINK_SUB_MENU_NAME, 'Thử thách sống khỏe');
        setSession(LINK_SUB_MENU_NAME_ID, 'h3');
        this.getChallenges();
    }

    componentWillUnmount() {
        this._isMounted = false;
        this.cpSaveLog(`Web_Close_${PageScreen.HEALTH_WELLBEING_CHALLENGES_LIST}`);
        trackingEvent(
            "Sống vui khỏe",
            `Web_Close_${PageScreen.HEALTH_WELLBEING_CHALLENGES_LIST}`,
            `Web_Close_${PageScreen.HEALTH_WELLBEING_CHALLENGES_LIST}`,
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
            this.setState({ renderMeta: true });
        }).catch(error => {
            this.setState({ renderMeta: true });
        });
    }

    render() {

        let classNm = 'hw-page';
        if (getSession(ACCESS_TOKEN)) {
            classNm = "hw-page logined";
        }

        return (
            <main className={classNm} id="scrollableDiv">
                {this.state.challenging &&
                    <Helmet>
                        <title>Thử thách Sống khỏe - Dai-ichi Life Việt Nam</title>
                        <meta name="description"
                              content="Cải thiện thể chất, nhận quà hấp dẫn với các Thử thách Sống khỏe được cập nhật liên tục mỗi tháng."/>
                        <meta name="keywords"
                              content="thử thách sống khỏe, dai-ichi life việt nam, dai-ichi connect, sống vui sống khỏe"/>
                        <meta name="robots" content="index, follow"/>
                        <meta property="og:type" content="website"/>
                        <meta property="og:url" content={FE_BASE_URL + "/song-vui-khoe/thu-thach-song-khoe"}/>
                        <meta property="og:title" content="Sống vui khỏe – Dai-ichi Life Việt Nam"/>
                        <meta property="og:description"
                              content="Cải thiện thể chất, nhận quà hấp dẫn với các Thử thách Sống khỏe được cập nhật liên tục mỗi tháng."/>
                        <meta property="og:image"
                              content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40songvuikhoe2_1668399023175.png"/>
                        <link rel="canonical" href={FE_BASE_URL + "/song-vui-khoe/thu-thach-song-khoe"}/>
                    </Helmet>
                }

                <div className="container">
                    {/* <!-- Breadcrums --> */}
                    <div className="breadcrums challenge-flex-mobile" style={{ paddingLeft: 0 }}>
                        <div className="breadcrums__item">
                            <p className="challenge-home"><Link to="/" className='breadcrums__link'>Trang chủ</Link></p>
                            <img className="challenge-mobile-wrapper" src={homeIcon} alt="home-ico"/>
                            <p className='breadcrums__item_arrow'>&gt;</p>
                        </div>
                        <div className="breadcrums__item">
                            <p><Link to="/song-vui-khoe" className='breadcrums__link'>Sống vui khỏe</Link></p>
                            <p className='breadcrums__item_arrow'>&gt;</p>
                        </div>
                        <div className="breadcrums__item">
                            <p>Thử thách Sống khỏe</p>
                            <p className='breadcrums__item_arrow'>&gt;</p>
                        </div>
                    </div>
                    {/* <!-- End Breadcrums --> */}
                </div>

                <div className="container">
                    {/* <!-- Health challenges Section --> */}
                    {this.state.challenging && 
                    <div className="cate-section mt-mobile-16">
                        <div className="cate-heading">
                            <h3 className="cate-heading-title">Thử thách đang diễn ra</h3>
                        </div>
                        <ChallengesSwiper data={this.state.challenging}/>
                    </div>
                    }
                    {this.state.coming && 
                    <div className="cate-section">
                        <div className="cate-heading">
                            <h3 className="cate-heading-title">Thử thách sắp diễn ra</h3>
                        </div>
                        <ChallengesSwiper data={this.state.coming}/>
                    </div>
                    }
                    {/* <!-- End Health challenges Section Section --> */}

                </div>
                {getSession(FROM_APP) && getSession(FROM_APP) === "IOS" ?
                    <div className='padding-bottom-30'></div> :  <div className='padding-bottom-60'></div>
                }
            </main>
        )
    }

}

export default withRouter(ChallengeList);