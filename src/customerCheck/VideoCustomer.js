import React, {Component} from 'react';
import {FE_BASE_URL, FIRE_BASE_DYNAMIC_ENDPOINT, FIRE_BASE_DYNAMIC_KEY, MEETING_SHORT_LINK, DOMAIN_PREFIX} from '../constants';
import {getSession, isMobile, setSession, appendScript, removeScript} from '../util/common';
import {checkZoomToken} from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import AlertPopupClaim from '../components/AlertPopupClaim';
import Video from './Video';

let device = window.btoa(navigator.userAgent);
if (device.length > 50) {
    device = device.substring(0, 49);
}
class VideoCustomer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isEmpty: true,
            nodata: false,
            isCompleted: false,
            ignored: false,
            errorMessage: '',
            password: '',
            token: '',
            nextScreen: false,
            loading: false,
            agree: null,
            isExpired: false,
            isSubmitYes: false,
            isNotAvailable: false,
            expireProfile: null,
            eapID: '',
            Passcode: '',
            imobile: false,
            poName: '',
            MeetingID: '',
            ProposalID: '',
            lyrics: [],
            shortLink: '',
            isVerifyMobile: false,
            Gender: ''

        }
        this.handleInputPass = this.handleInputPass.bind(this);
        this.popupLaPassSubmit = this.popupLaPassSubmit.bind(this);

    }

    handleInputPass(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value.trim();
        const re = /^[0-9\b]+$/;
        if ((inputValue === '') || re.test(inputValue)) {
            this.setState({
                [inputName]: inputValue
            });
        }
    }

    checkLinkExpire() {
        if (isMobile()) {
            if (getSession(MEETING_SHORT_LINK + this.props.match.params.id)) {
                this.setState({isVerifyMobile: true});
            }
        }
        let request = {
            jsonDataInput: {
                Action: "CheckToken",
                Project: "mAGP",
                DeviceId: device,
                APIToken: "",
                InviteCode: this.props.match.params.id
            }
        }
        checkZoomToken(request)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'CheckTokenDC Valid.') {
                    const MeetingInfoZoom = response.Response.MeetingInfoZoom;
                    const fullName = MeetingInfoZoom ?MeetingInfoZoom.POName : "";
                    const eapId = MeetingInfoZoom ?MeetingInfoZoom.ProposalNo : "";
                    const Gender = MeetingInfoZoom ?MeetingInfoZoom.Gender : "";
                    const jsonState = this.state;
                    if (isMobile()) {
                        if (getSession(MEETING_SHORT_LINK + this.props.match.params.id)) {
                            window.location.href = getSession(MEETING_SHORT_LINK + this.props.match.params.id);
                        }
                    }
                    jsonState.isExpired = false;
                    jsonState.isNotAvailable = false;
                    jsonState.isSubmitYes = false;
                    jsonState.errorMessage = '';
                    jsonState.nextScreen = false;
                    jsonState.eapID = eapId;
                    jsonState.poName = fullName;
                    jsonState.Gender = Gender;
                    this.setState(jsonState);
                } else {
                    this.setState({ isNotAvailable: false, nextScreen: false, isExpired: true, isSubmitYes: false });
                }

            }).catch(error => {
                this.setState({ isNotAvailable: true, nextScreen: false, isExpired: false, isSubmitYes: false });
            });
    }
    popupLaPassSubmit(event) {
        event.preventDefault();
        if (document.getElementById('btn-cc-pass').className === "btn btn-primary disabled") {
            return;
        }
        if (!this.props.match.params.id) {
            return;
        }
        // this.setState({ loading: true, token: '123456' });
        let loginRequest = {
            jsonDataInput: {
                Action: 'CheckDOB',
                Project: 'mAGP',
                DeviceId: device,
                APIToken: '',
                InviteCode: this.props.match.params.id,
                DOB: this.state.password
            }
        };
        checkZoomToken(loginRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.MeetingInfoZoom ) {
                    const MeetingInfoZoom = response.Response.MeetingInfoZoom;
                    var jsonState = this.state;
                    jsonState.Passcode = MeetingInfoZoom.Passcode;
                    jsonState.MeetingID = MeetingInfoZoom.MeetingID;
                    jsonState.ProposalID = MeetingInfoZoom.ProposalID;
                    jsonState.loading = false;
                    jsonState.isNotAvailable = false;
                    if (isMobile()) {
                        this.setState(jsonState);
                        this.creatDynamicLinkForApp(MeetingInfoZoom.AgentID, MeetingInfoZoom.MeetingID, MeetingInfoZoom.Passcode, MeetingInfoZoom.ProposalID);
                    } else {
                        jsonState.token = MeetingInfoZoom.Passcode;
                        this.setState(jsonState);
                    }
                    
                } else {
                    this.setState({ errorMessage: 'Mã xác thực không đúng, Quý khách vui lòng nhập lại.', loading: false });
                }

            }).catch(error => {
                this.setState({ errorMessage: 'Mã xác thực không đúng, Quý khách vui lòng nhập lại.', loading: false });
            });


        // this.setState({ token: getSession(ACCESS_TOKEN) });
    }
    creatDynamicLinkForApp(AgentID, MeetingID, Passcode, ProposalID)  {
        let authEndpoint = FIRE_BASE_DYNAMIC_ENDPOINT + '?key=' + FIRE_BASE_DYNAMIC_KEY
        fetch(authEndpoint, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            dynamicLinkInfo: {
              domainUriPrefix: DOMAIN_PREFIX,
              link:  FE_BASE_URL + "/?AgentID=" + AgentID + "&Passcode=" + Passcode + "&MeetingID=" + MeetingID + "&ProposalID=" + ProposalID,
              androidInfo: {
                androidPackageName: "com.dlvn.mcustomerportal"
              },
              iosInfo: {
                iosBundleId: "vn.com.dai-ichi-life.mCP",
                iosAppStoreId: "1435474783"
              }
            }
          }),
        })
          .then((res) => res.json())
          .then((response) => {
            setSession(MEETING_SHORT_LINK + this.props.match.params.id, response.shortLink);
            this.setState({shortLink: response.shortLink});
            window.location.href = response.shortLink;
          })
          .catch((error) => {
            console.error(error);
          });
      };
    componentDidMount() {
        this.checkLinkExpire();
        try {
            this.loadVendorScript();
        } catch(error) {

        }
    }

    componentWillUnmount() {
        try {
            this.unloadVendorScript();
        } catch(error) {

        }
    }

    loadVendorScript() {
        appendScript("/js/zoom-vendor/lodash.min.js");
        appendScript("/js/zoom-vendor/react.min.js");
        appendScript("/js/zoom-vendor/react-dom.min.js");
        appendScript("/js/zoom-vendor/redux.min.js");
        appendScript("/js/zoom-vendor/redux-thunk.min.js");
        appendScript("/js/zoom-vendor/js_media.min.js");
    }

    unloadVendorScript() {
        removeScript("/js/zoom-vendor/lodash.min.js");
        removeScript("/js/zoom-vendor/react.min.js");
        removeScript("/js/zoom-vendor/react-dom.min.js");
        removeScript("/js/zoom-vendor/redux.min.js");
        removeScript("/js/zoom-vendor/redux-thunk.min.js");
        removeScript("/js/zoom-vendor/js_media.min.js");
    }

    render() {
        const showHidePass = () => {
            var x = document.getElementById("la-passsword");
            if (x.type === "password") {
                x.type = "text";
                document.getElementById('la-password-input').className = "input special-input-extend password-input show";
            } else {
                x.type = "password";
                document.getElementById('la-password-input').className = "input special-input-extend password-input";
            }

        }

        const closeLaPass = () => {
            this.setState({ ignored: true });
            this.props.history.push('/');
        }
        const closePopup = () => {
            const jsonState = this.state;
            // document.getElementById('popup').className = "popup envelop";
            jsonState.nextScreen = false;
            jsonState.isCompleted = true;
            this.setState(jsonState);
            this.props.history.push('/');
        }

        const closeExpire = () => {
            this.setState({ isExpired: false });
            this.props.history.push('/');
        }

        const closeNotAvailable = () => {
            this.setState({ isNotAvailable: false });
            this.props.history.push('/');
        }

        const openDCApp = () => {
            this.setState({imobile: false});
            window.location.href=this.state.shortLink;
        }
        return (
            <div className='cus-popup'>
                {this.state.ignored ? (
                    <main className="logined" style={{ display: 'flex', justifyContent: 'center', paddingLeft: '0' }}>
                        <div className="main-warpper basic-mainflex">
                            <section className="sccontract-warpper no-data nodata2-fullwidth">
                                <div className="breadcrums" style={{ backgroundColor: '#ffffff' }}>
                                    <div className="breadcrums__item">
                                        <p>Theo dõi yêu cầu</p>
                                        <p className='breadcrums__item_arrow'>></p>
                                    </div>
                                    <div className="breadcrums__item">
                                        <p>Giải quyết quyền lợi</p>
                                        <p className='breadcrums__item_arrow'>></p>
                                    </div>
                                </div>
                                <div className="sccontract-container">
                                    <div className="insurance">
                                        <div className="empty">
                                            <div className="icon">
                                                <img src="../../../img/empty-state.svg" alt="" />
                                            </div>
                                            <h4 className="basic-semibold" style={{ textAlign: 'center' }}>Để hoàn thành quy trình thẩm định, <br /> Quý khách vui lòng bấm vào thông báo <br /> trong tin nhắn đã nhận được.</h4>
                                        </div>
                                    </div>
                                </div>
                            </section>
                            <LoadingIndicator area="login-area" />
                        </div>
                    </main>
                ) : (
                    this.state.token ? (
                        this.state.nodata === true ? (
                            <main className="logined" style={{ display: 'flex', justifyContent: 'center', paddingLeft: '0' }}>
                                <div className="main-warpper basic-mainflex">
                                    <section className="sccontract-warpper no-data nodata2-fullwidth">
                                        <div className="breadcrums" style={{ backgroundColor: '#ffffff' }}>
                                            <div className="breadcrums__item">
                                                <p>Theo dõi yêu cầu</p>
                                                <p className='breadcrums__item_arrow'>></p>
                                            </div>
                                            <div className="breadcrums__item">
                                                <p>Giải quyết quyền lợi</p>
                                                <p className='breadcrums__item_arrow'>></p>
                                            </div>
                                        </div>
                                        <div className="sccontract-container">
                                            <div className="insurance">
                                                <div className="empty">
                                                    <div className="icon">
                                                        <img src="../../../img/2.2-nodata-image.png" alt="" />
                                                    </div>
                                                    <h4 className="basic-semibold" style={{ textAlign: 'center' }}>Không có dữ liệu</h4>
                                                </div>
                                            </div>
                                        </div>
                                    </section>
                                    <LoadingIndicator area="login-area" />
                                </div>
                            </main>
                        ) : (
                            <Video MeetingID={this.state.MeetingID} Passcode={this.state.Passcode} poName={this.state.poName} ProposalID={this.state.ProposalID} DOB={this.state.DOB}/>
                        )
                    ) : (
                        this.state.isExpired ? (
                            <AlertPopupClaim closePopup={closeExpire} msg="Liên kết thực hiện ghi âm/ghi hình đã hết hạn. Quý khách vui lòng liên hệ Tư vấn Tài chính để được hỗ trợ." imgPath={FE_BASE_URL + "/img/popup/key-expired.svg"} />
                        ) : (
                            this.state.isVerifyMobile?(
                                // <OptionPopup closePopup={() => this.setState({imobile: false})} firstFunc={() => openDCApp()} msg={parse('<p>Ghi âm/ghi hình sẽ được thực hiện trên ứng dụng D-Connect. Quý khách muốn tiếp tục?</p>')} firstText='Có, mở ứng dụng' secondText='Không' secondFunc={()=>alert('Tải Ứng dụng D-connect')} />
                                <></>
                            ):( 
                            this.state.isSubmitYes ? (
                                <AlertPopupClaim closePopup={closeExpire} msg="Chúng tôi đã nhận được ý kiến của Quý khách và đang trong quá trình xử lý/phát hành Hồ sơ Yêu cầu Bảo hiểm của Quý khách" imgPath={FE_BASE_URL + "/img/popup/reinstament-success.png"} />
                            ) : (
                                this.state.isNotAvailable ? (
                                    <AlertPopupClaim closePopup={closeNotAvailable} msg="Kết nối không thành công. Quý khách vui lòng kiểm tra lại đường dẫn hoặc thử lại sau ít phút." imgPath={FE_BASE_URL + "/img/popup/not-connect.png"} />) : (
                                    <div className="popup special new-password-popup show" id="claim-la-popup">
                                        <form onSubmit={this.popupLaPassSubmit}>
                                            <div className="popup__card">
                                                <div className="new-password-card" style={{ maxWidth: '360px' }}>
                                                    <div className="header">
                                                        <h4>Xác thực thông tin</h4>
                                                        <i className="closebtn"><img src="../../../img/icon/close.svg" alt="" onClick={() => closeLaPass()} /></i>
                                                    </div>
                                                    <div className="body">
                                                        {(this.state.errorMessage.length > 0) &&
                                                            <div className="error-message validate">
                                                                {(this.state.errorMessage.length > 58) ? (
                                                                    <i className="icon" style={{ marginTop: '-28px' }}>
                                                                        <img src="../../../img/icon/warning_sign.svg" alt="" />
                                                                    </i>
                                                                ) : (
                                                                    (this.state.errorMessage.length > 29) ? (
                                                                        <i className="icon" style={{ marginTop: '-10px', width: '20px', height: '20px' }}>
                                                                            <img src="../../../img/icon/warning_sign.svg" alt="" />
                                                                        </i>
                                                                    ) : (
                                                                        <i className="icon">
                                                                            <img src="../../../img/icon/warning_sign.svg" alt="" />
                                                                        </i>
                                                                    )
                                                                )}
                                                                <p style={{ 'lineHeight': '20px' }}>{this.state.errorMessage}</p>
                                                            </div>
                                                        }
                                                        <p style={{ lineHeight: '20px', marginTop: '-12px' }}>Công ty Bảo hiểm Nhân thọ Dai-ichi Việt Nam (“Dai-ichi Life Việt Nam”) xin chân thành cảm ơn Quý khách <b>{this.state.poName?this.state.poName:''}</b> đã tham gia yêu cầu bảo hiểm số <b>{this.state.eapID?this.state.eapID:''}</b> cùng chúng tôi.</p>
                                                        <div className="input-wrapper" style={{ marginTop: '31px' }}>
                                                            <div className="input-wrapper-item" style={{ paddingTop: '0px' }}>
                                                                <div className="input special-input-extend password-input" id="la-password-input" style={{ paddingBottom: '9px', paddingLeft: '16px' }}>
                                                                    <div className="input__content">
                                                                        <label htmlFor="">Mã xác thực (6 ký tự)</label>
                                                                        <input type="password" name="password" id="la-passsword" required placeholder='MMYYYY' value={this.state.password} onChange={this.handleInputPass} maxLength='6' style={{ paddingLeft: '0' }} />
                                                                    </div>
                                                                    <i className="password-toggle" onClick={() => showHidePass()}></i>
                                                                </div>
                                                            </div>
                                                            <div className="input-wrapper-item" style={{ marginTop: '-8px' }}>
                                                                {this.state.Gender && this.state.Gender === 'C'?(
                                                                <p>
                                                                    Ví dụ: Lịch hẹn ghi âm/ghi hình vào tháng 09/2024. <br /> Mã xác thực sẽ là: <span className='semi'>092024.</span>
                                                                </p>
                                                                ):(
                                                                <p>
                                                                    Ví dụ: Quý khách có Tháng và năm sinh là 09/2024. <br /> Mã xác thực sẽ là: <span className='semi'>092024.</span>
                                                                </p>
                                                                )}
                                                                <LoadingIndicator area="login-area" />
                                                            </div>
                                                        </div>
                                                        <div className="btn-wrapper" style={{ marginTop: '33px' }}>
                                                            {(this.state.password.length === 6) && !this.state.loading ? (
                                                                <button className="btn btn-primary" id="btn-cc-pass" style={{ fontWeight: '600' }}>Xác nhận</button>
                                                            ) : (
                                                                <button className="btn btn-primary disabled" id="btn-cc-pass" style={{ fontWeight: '600' }}>Xác nhận</button>
                                                            )}

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="popupbg"></div>
                                        </form>
                                    </div>
                                )
                            )
                            )
                        )
                    )

                )}

            </div>
        );
    }
}


export default VideoCustomer;