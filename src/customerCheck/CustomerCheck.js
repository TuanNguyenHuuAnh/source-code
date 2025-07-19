import React, { Component } from 'react';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, EXPIRED_MESSAGE, AUTHENTICATION, COMPANY_KEY, USER_DEVICE_TOKEN, ZALO_LOGINED, CLAIM_ID, FE_BASE_URL, WEB_BROWSER_VERSION } from '../constants';
import { showMessage, isInteger, setSession, getSession } from '../util/common';
import { CPGetClientProfileByCLIID, logoutSession, checkAuthZalo, checkCCTokenExpire, CPSubmitAnswer } from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import AlertPopupClaim from '../components/AlertPopupClaim';
import parse from 'html-react-parser';
import NumberFormat from 'react-number-format';

let device = window.btoa(navigator.userAgent);
if (device.length > 50) {
    device = device.substring(0, 49);
}
class CustomerCheck extends Component {
    constructor(props) {
        super(props);

        this.state = {
            clientProfile: null,
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
            loginProfile: null,
            userLogin: ''

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
        let request = {
            jsonDataInput: {
                Action: "CheckLinkProposalConfirm",
                OS: "Web",
                DeviceId: device,
                LinkID: this.props.match.params.id
            }
        }
        checkCCTokenExpire(request)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESS') {
                    const myArray = response.Response.Message.split("@");
                    const fullName = (myArray.length > 0 && myArray[0]) ? myArray[0] : "";
                    const eapId = (myArray.length > 0 && myArray[1]) ? myArray[1] : "";

                    var jsonState = this.state;
                    // jsonState.token = response.Response.NewAPIToken;
                    jsonState.expireProfile = response.Response.ClientProfile;
                    jsonState.isExpired = false;
                    jsonState.isNotAvailable = false;
                    jsonState.isSubmitYes = false;
                    jsonState.errorMessage = '';
                    jsonState.nextScreen = false;
                    jsonState.eapID = eapId;
                    this.setState(jsonState);
                } else if ((response.Response.ErrLog === '11') || (response.Response.ErrLog === '21')) {
                    this.setState({ isNotAvailable: true, nextScreen: false, isExpired: false, isSubmitYes: false });
                } else if (response.Response.ErrLog === '12') {
                    this.setState({ isNotAvailable: false, nextScreen: false, isExpired: false, isSubmitYes: true });
                } else if (response.Response.ErrLog === '13') {
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
        this.setState({ loading: true });
        let loginRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Action: 'CheckAuthenProposalConfirm',
                APIToken: '',
                Authentication: AUTHENTICATION,
                DeviceId: device,
                // DeviceToken: getSession(USER_DEVICE_TOKEN) ? getSession(USER_DEVICE_TOKEN) : '',
                UserLogin: '0000000001',
                OS: '',
                Project: 'mcp',
                Password: '000000000',
                AuthenID: this.state.eapID,
                AuthenDOB: this.state.password,
                LinkID: this.props.match.params.id
            }
        };
        checkAuthZalo(loginRequest)
            .then(response => {
                if (response.Response.Result === 'true' && response.Response.ErrLog === '00') {
                    const myArray = response.Response.Message.split("@");
                    var jsonState = this.state;
                    jsonState.token = response.Response.NewAPIToken;
                    jsonState.loginProfile = response.Response.ClientProfile;
                    jsonState.userLogin = (myArray.length > 0 && myArray[0]) ? myArray[0] : "";
                    jsonState.eapID = (myArray.length > 0 && myArray[1]) ? myArray[1] : "";
                    jsonState.loading = false;

                    this.setState(jsonState);
                } else if (response.Response.ErrLog === 'WRONGPASSEXCEED') {
                    this.setState({ errorMessage: 'Mã xác thực không đúng, Quý khách đã nhập sai thông tin quá 5 lần. Vui lòng thử lại sau 30 phút.', loading: false });
                } else if (response.Response.ErrLog === '23') {
                    this.setState({ errorMessage: 'Mã xác thực không đúng, Quý khách vui lòng nhập lại.', loading: false });
                } else {
                    this.setState({ errorMessage: 'Mã xác thực không đúng, Quý khách vui lòng nhập lại.', loading: false });
                }

            }).catch(error => {
                this.setState({ errorMessage: 'Mã xác thực không đúng, Quý khách vui lòng nhập lại.', loading: false });
            });


        // this.setState({ token: getSession(ACCESS_TOKEN) });
    }

    componentDidMount() {
        this.checkLinkExpire();
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

        const answerSubmit = (e) => {
            e.preventDefault();
            this.setState({ loading: true });
            let request = {
                jsonDataInput: {
                    Company: COMPANY_KEY,
                    Action: 'SubmitProposalConfirm',
                    APIToken: this.state.token,
                    Authentication: AUTHENTICATION,
                    DeviceId: device,
                    UserLogin: '0000000001',
                    OS: WEB_BROWSER_VERSION,
                    Project: 'mcp',
                    Password: '000000000',
                    AuthenID: this.state.eapID,
                    AuthenDOB: this.state.password && isInteger(this.state.password) ? parseInt(this.state.password) + '' : this.state.password,
                    LinkID: this.props.match.params.id,
                    Answer: this.state.agree ? 'YES' : 'NO',
                    FromSystem: 'DCW'

                }
            };
            CPSubmitAnswer(request)
                .then(response => {
                    if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUBMIT SUCCESS') {
                        this.setState({ nextScreen: true, loading: false });
                    } else {
                        this.setState({ errorMessage: 'Yêu cầu không thành công!', nextScreen: false, loading: false });
                    }

                }).catch(error => {
                    this.setState({ errorMessage: 'Yêu cầu không thành công!', nextScreen: false, loading: false });
                });
        }

        const answerYes = () => {
            this.setState({ agree: true });
        }

        const answerNo = () => {
            this.setState({ agree: false });
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
                            <main className="logined nodata" style={{ display: 'flex', justifyContent: 'center', paddingLeft: '0' }}>
                                <div className="main-warpper basic-mainflex">
                                    {/**câu hỏi */}
                                    <div className="sccontract-container" style={{ backgroundColor: '#f5f3f2' }}>
                                        <div className="insurance">
                                            <div className='stepform' style={{ marginTop: '125px', marginBottom: '30px' }}>

                                                <div className="an-hung-page" style={{ padding: '0 10px' }}>
                                                    <div className="bo-hop-dong-container page-eleven" style={{ alignItems: 'flex-start' }}>
                                                        <div className="title" style={{ paddingLeft: '16px', paddingBottom: '0px', paddingTop: '43px' }}>
                                                            <h4 className='basic-upload-padding basic-text-upper'>DÀNH CHO KHÁCH HÀNG THAM GIA BẢO HIỂM CỦA DAI-ICHI LIFE VIỆT NAM</h4>
                                                        </div>
                                                        <div className="customer-informationthe-bh info__body" style={{ width: '100%' }}>
                                                            <div className="customer-informationthe-bh-bottom__body" style={{ marginBottom: '0' }}>


                                                                <div className="information-card-item" style={{ paddingTop: '0' }}>

                                                                    {/* {item.QuestionList && item.QuestionList.map((element, idx) => {
                      return ( */}
                                                                    <div className="content">

                                                                        <div className="list__item">
                                                                            {/* <div className="dot"></div> */}
                                                                            {this.state.loginProfile && this.state.loginProfile.map((item, index) => {
                                                                                return (
                                                                                    <p className={index === 0 ? 'questionaire padding-top-first-questionaire' : 'questionaire'}>{parse(item.Content)}</p>
                                                                                )

                                                                            })}

                                                                        </div>
                                                                        <div className="item__content">
                                                                            <div className="tab">
                                                                                <div className="tab__content">
                                                                                    {this.state.loading ? (
                                                                                        <div className="checkbox-warpper" style={{ marginTop: '-4px' }}>

                                                                                            <div className="checkbox-item">
                                                                                                <div className="round-checkbox">
                                                                                                    <label className="customradio" style={{ alignItems: 'center' }} >
                                                                                                        <input type="checkbox" checked={(this.state.agree !== null) && (this.state.agree === true)} disabled />
                                                                                                        <div className="checkmark-readonly" ></div>
                                                                                                        <p className="text" style={{ whiteSpace: 'nowrap', minWidth: '80px', paddingLeft: '6px' }}>Đồng ý</p>
                                                                                                    </label>
                                                                                                </div>
                                                                                            </div>

                                                                                            <div className="checkbox-item" style={{ marginLeft: '10px' }}>
                                                                                                <div className="round-checkbox">
                                                                                                    <label className="customradio" style={{ alignItems: 'center' }}>
                                                                                                        <input type="checkbox" checked={(this.state.agree !== null) && (this.state.agree === false)} disabled />
                                                                                                        <div className="checkmark-readonly" ></div>
                                                                                                        <p className="text" style={{ whiteSpace: 'nowrap', paddingLeft: '6px' }}>Không đồng ý</p>
                                                                                                    </label>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div className="checkbox-item"></div>
                                                                                            <div className="checkbox-item"></div>
                                                                                        </div>
                                                                                    ) : (
                                                                                        <div className="checkbox-warpper" style={{ marginTop: '-4px' }}>

                                                                                            <div className="checkbox-item">
                                                                                                <div className="round-checkbox">
                                                                                                    <label className="customradio" style={{ alignItems: 'center' }} >
                                                                                                        <input type="checkbox" checked={(this.state.agree !== null) && (this.state.agree === true)} onClick={() => answerYes()} />
                                                                                                        <div className="checkmark" ></div>
                                                                                                        <p className="text" style={{ whiteSpace: 'nowrap', minWidth: '80px', paddingLeft: '6px' }}>Đồng ý</p>
                                                                                                    </label>
                                                                                                </div>
                                                                                            </div>

                                                                                            <div className="checkbox-item" style={{ marginLeft: '10px' }}>
                                                                                                <div className="round-checkbox">
                                                                                                    <label className="customradio" style={{ alignItems: 'center' }}>
                                                                                                        <input type="checkbox" checked={(this.state.agree !== null) && (this.state.agree === false)} onClick={() => answerNo()} />
                                                                                                        <div className="checkmark" ></div>
                                                                                                        <p className="text" style={{ whiteSpace: 'nowrap', paddingLeft: '6px' }}>Không đồng ý</p>
                                                                                                    </label>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div className="checkbox-item"></div>
                                                                                            <div className="checkbox-item"></div>
                                                                                        </div>
                                                                                    )}

                                                                                </div>
                                                                            </div>
                                                                        </div>

                                                                    </div>

                                                                    {/* )
                    })} */}

                                                                </div>




                                                            </div>



                                                        </div>


                                                        <img className="decor-clip-big" src="../img/mock-new.svg" alt="" />
                                                        <img className="decor-person" src="../img/person.png" alt="" />

                                                    </div>

                                                </div>

                                            </div>
                                            <div className='stepform' style={{ marginTop: '0', display: 'flex', justifyContent: 'center', padding: '0 10px' }}>
                                                {((this.state.agree !== null) && (!this.state.loading)) ? (
                                                    <div className="bottom-btn cus-cfm-btn" onClick={(e) => answerSubmit(e)}>
                                                        <button className="btn btn-primary" style={{ fontWeight: '600' }}>Gửi ý kiến</button>
                                                    </div>
                                                ) : (
                                                    <div className="bottom-btn cus-cfm-btn">
                                                        <button className="btn btn-primary disabled" style={{ fontWeight: '600' }}>Gửi ý kiến</button>
                                                    </div>
                                                )}

                                            </div>
                                        </div>
                                        <LoadingIndicator area="login-area" />
                                    </div>
                                </div>
                                {this.state.nextScreen === true && (
                                    <div className="popup envelop show" id="popup">
                                        <div className="popup__card envelop-infomation-big">
                                            <button className="envelop-infomation__close-button" style={{ top: '14px', right: '49px' }} onClick={() => closePopup()}>
                                                <img src="../../../img/icon/close-icon.svg" alt="" />
                                            </button>
                                            <div className="envelop-infomation__content">
                                                <h5 className="basic-bold">gửi ý kiến thành công</h5>
                                                {this.state.agree ? (
                                                    <p>Cảm ơn Quý khách đã đồng hành cùng Dai-ichi Life Việt Nam. Kết quả Hồ sơ yêu cầu Bảo hiểm sẽ được thông báo đến Quý khách sau khi hoàn tất Thẩm định.<br /><br /></p>
                                                ) : (
                                                    <p>Cảm ơn Quý khách đã đồng hành cùng Dai-ichi Life Việt Nam. Tư vấn viên sẽ liên hệ Quý khách hàng để tư vấn thêm.</p>
                                                )}

                                            </div>
                                        </div>
                                        <div className="popupbg"></div>
                                    </div>
                                )}
                            </main>
                        )
                    ) : (
                        this.state.isExpired ? (
                            <AlertPopupClaim closePopup={closeExpire} msg="Hồ sơ yêu cầu bảo hiểm của Quý khách đã hết thời gian xác nhận thông tin tư vấn." imgPath={FE_BASE_URL + "/img/popup/key-expired.svg"} />
                        ) : (
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
                                                        <p style={{ lineHeight: '20px', marginTop: '-12px' }}>{this.state.expireProfile && (this.state.expireProfile.length > 0) && parse(this.state.expireProfile[0].Content)}</p>
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
                                                                <p>
                                                                    Ví dụ: Quý khách có Tháng và năm sinh là 02/1999 <br /> Mã xác thực sẽ là: <span className='semi'>021999</span>
                                                                </p>
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

                )}

            </div>
        );
    }
}


export default CustomerCheck;