import React, {useEffect, useState} from 'react';
import {useHistory} from 'react-router-dom';
import {CLAIM_TYPE, AUTHENTICATION, COMPANY_KEY, FE_BASE_URL, WEB_BROWSER_VERSION, CLAIMTYPEMAP} from '../../../../../constants';
import {CPConsentConfirmation} from '../../../../../util/APIUtils';
import LoadingIndicator from '../../../../../common/LoadingIndicator';
import AlertPopupClaim from '../../../../../components/AlertPopupClaim';
import './styles.css';
import {getDeviceId} from '../../../../../util/common';
import ND13LIConfirm from "../ND13LIConfirm";
import ND13LILinkExpired from "../ND13LILinkExpired";
import {useParams} from "react-router-dom/cjs/react-router-dom";
import { isEmpty } from 'lodash';

let device = window.btoa(navigator.userAgent);
if (device.length > 50) {
    device = device.substring(0, 49);
}

const PasscodeCheckPayment = () => {
    const { id } = useParams();
    const [errorMessage, setErrorMessage] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [isNotAvailable, setIsNotAvailable] = useState(false);
    const [isExpired, setIsExpired] = useState(false);
    const [clientProfile, setClientProfile] = useState([]);
    const [newAPIToken, setNewAPIToken] = useState('');
    const [isShowQLBHNoti, setIsShowQLBHNoti] = useState(false);
    const [isOpenPassCode, setIsOpenPassCode] = useState(true);
    const [passRemain, setPassRemain] = useState('');

    const history = useHistory();

    useEffect(() => {
        checkLinkExpire();
    }, []);

    const handleInputPass = (event) => {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value.trim();
        const re = /^[0-9\b]+$/;
        if ((inputValue === '') || re.test(inputValue)) {
            if (inputName === 'password') setPassword(inputValue);
        }
    };

    const checkLinkExpire = () => {
        // Implement your check link expire logic here
    };

    const popupLaPassSubmit = (event) => {
        event.preventDefault();
        setLoading(true);
        const request = {
            jsonDataInput: {
                Action: "CheckLinkConsent",
                APIToken: "",
                Authentication: AUTHENTICATION,
                Company: COMPANY_KEY,
                Password: password,
                DeviceId: getDeviceId(),
                OS: WEB_BROWSER_VERSION,
                Project: "mcp",
                // LinkPath: 'https://khuat.dai-ichi-life.com.vn/cs/nd13/9740920591653',
                LinkPath: FE_BASE_URL + '/tt/' + id,
            }
        };

        CPConsentConfirmation(request).then(res => {
            let Response = res.Response;
            if (Response.ErrLog === 'PassCode_Valid' && Response.Result === 'true' && Response.ClientProfile) {
                setClientProfile(Response.ClientProfile);
                setNewAPIToken(Response.NewAPIToken);
                setIsNotAvailable(false);
                setIsExpired(false);
                setIsOpenPassCode(false);
                setIsShowQLBHNoti(true);
                setLoading(false);
            } else if ((res.Response.ErrLog === 'PassCode_InCorrect') || (res.Response.ErrLog === 'PassCode_Invalid')) {
                setLoading(false);
                let messageArray = Response.Message.split(':');
                if (!isEmpty(messageArray) && messageArray.length ===2) {
                    setPassRemain(messageArray[1]);
                    if (messageArray[1] === '0') {
                        setErrorMessage('Quý khách đã nhập sai thông tin quá 5 lần. Vui lòng thử lại sau 30 phút.');
                    } else {
                        setErrorMessage(`Mã xác thực không đúng, Quý khách vui lòng thử lại. ${messageArray[1]?' Quý khách còn ' + messageArray[1] + ' lần thử': '' }`);
                    }
                    
                } else {
                    setErrorMessage('Mã xác thực không đúng, Quý khách vui lòng thử lại.');
                }
            } else if (res.Response.ErrLog === 'PassCode_Lock') {
                setErrorMessage('Quý khách đã nhập sai thông tin quá 5 lần. Vui lòng thử lại sau 30 phút.');
                setLoading(false);
            } 
        }).catch(error => {
            console.log(error);
            setErrorMessage('Mã xác thực không đúng, Quý khách vui lòng thử lại.');
            setLoading(false);
        });
    };

    const showHidePass = () => {
        const x = document.getElementById("la-passsword");
        if (x.type === "password") {
            x.type = "text";
            document.getElementById('la-password-input').className = "input special-input-extend password-input show";
        } else {
            x.type = "password";
            document.getElementById('la-password-input').className = "input special-input-extend password-input";
        }
    };

    const closeLaPass = () => {
        history.push('/');
    };

    const closeNotAvailable = () => {
        setIsNotAvailable(false);
        history.push('/');
    };

    let heathCares = '';
    let claimTypes = clientProfile[0]?.ClaimType?.split(';');
    let claimTypeArr = claimTypes?.filter(cType => (cType === CLAIM_TYPE.HEALTH_CARE) || (cType === CLAIM_TYPE.HS));
    
    if (!isEmpty(claimTypeArr)) {
        for (let i=0; i < claimTypeArr.length; i++) {
            if (!isEmpty(claimTypeArr[i])) {
                if (i=== 0) {
                    heathCares =  CLAIMTYPEMAP[claimTypeArr[i]];
                } else {
                    heathCares = heathCares + ',' + CLAIMTYPEMAP[claimTypeArr[i]];
                }
            }
        }
    }
    return (<div className='cus-popup'>
        {isNotAvailable && (<AlertPopupClaim
            closePopup={closeNotAvailable}
            msg="Kết nối không thành công. Quý khách vui lòng kiểm tra lại đường dẫn hoặc thử lại sau ít phút."
            imgPath={`${FE_BASE_URL}/img/popup/not-connect.png`}
        />)}

        {isExpired && (<ND13LILinkExpired />)}

        {isShowQLBHNoti && ( 
            <main>
            <section style={{paddingTop: '50px', paddingBottom: '50px'}}>
              <div className="e-singnature">
                <span style={{fontSize:'15px'}}>
                    <p className='basic-bold' style={{textAlign: 'center'}}>Thông báo về Yêu cầu</p>
                    <p className='basic-bold' style={{textAlign: 'center'}}>giải quyết quyền lợi bảo hiểm</p>
                </span> 
                <div style={{paddingTop:'20px', fontFamily: 'Inter',fontSize: '16px',fontWeight: '500',lineHeight: '24px',textAlign: 'left'}}>
                    <p>Kính thưa Quý khách,</p> 
                    <p>Công ty Bảo hiểm Nhân thọ Dai-ichi Việt Nam (“Dai-ichi Life Việt Nam”) xin gửi đến Quý khách lời chào trân trọng.<br/><br/></p>
                    <span>Bên mua bảo hiểm <span className='basic-bold'>{clientProfile[0]?.RequesterName}</span> của HĐBH <span className='basic-bold'>{clientProfile[0]?.policyID}</span> đã yêu cầu giải quyết quyền lợi bảo hiểm <span className='basic-bold'>{heathCares}</span> của Người được bảo hiểm <span className='basic-bold'>{clientProfile[0]?.CustomerName}</span>, theo đó, Bên mua bảo hiểm là người nhận tiền và cam kết chịu trách nhiệm về việc nhận tiền này, nếu yêu cầu được chấp thuận chi trả.</span><br/><br/>
                    <span>Trường hợp cần trao đổi, Quý khách vui lòng liên hệ Bên mua bảo hiểm hoặc Tổng đài Dịch vụ Khách hàng (028) 38 100 888 hoặc thư điện tử <span className='simple-brown' style={{textDecoration: 'none'}}>bhchamsocsuckhoe@dai-ichi-life.com.vn</span>. Chúng tôi luôn sẵn sàng phục vụ Quý khách.</span><br/><br/>
                    <span>Trân trọng kính chào,</span><br/>
                    <span className='basic-bold'>Dai-ichi Life Việt Nam<br/>Phòng Giải quyết Quyền lợi bảo hiểm</span>
                </div>  

              </div>
            </section>
            </main>
        )}

        {isOpenPassCode && <div className="popup special show">
            <form onSubmit={popupLaPassSubmit}>
                <div className="popup__card">
                    <div className="new-password-card" style={{maxWidth: '360px'}}>
                        <div className="header">
                            <h4>Xác thực thông tin</h4>
                            <i className="closebtn">
                                <img src="../../../img/icon/close.svg" alt="" onClick={() => closeLaPass()}/>
                            </i>
                        </div>
                        <div className="body">
                            <p style={{
                                fontSize: '14px',
                                fontWeight: '500',
                                lineHeight: '20px',
                                textAlign: 'left',
                                color: '#292929',
                                marginBottom: 6,
                            }}>
                                Quý khách nhập Mật khẩu là 6 ký tự gồm Tháng và năm sinh của Quý khách.
                            </p>
                            {errorMessage.length > 0 && (
                                <div className="error-message validate" style={{padding: '12px'}}>
                                    <i className="icon">
                                        <img src="../../../img/icon/warning_sign.svg" alt=""/>
                                    </i>
                                    <p style={{lineHeight: '20px', textAlign: 'left'}}>{errorMessage}</p>
                                </div>)}
                            <div className="input-wrapper" style={{marginTop: '16px'}}>
                                <div className="input-wrapper-item" style={{paddingTop: '0px'}}>
                                    <div className="input special-input-extend password-input"
                                         id="la-password-input" style={{paddingBottom: '9px', paddingLeft: '16px'}}>
                                        <div className="input__content">
                                            <label htmlFor="">Mã xác thực (6 ký tự)</label>
                                            <input
                                                type="password"
                                                name="password"
                                                id="la-passsword"
                                                required
                                                placeholder='MMYYYY'
                                                value={password}
                                                onChange={handleInputPass}
                                                maxLength='6'
                                                style={{paddingLeft: '0'}}
                                            />
                                        </div>
                                        <i className="password-toggle" onClick={() => showHidePass()}></i>
                                    </div>
                                </div>
                                <div className="input-wrapper-item" style={{marginTop: '-8px'}}>
                                    <p style={{fontSize: 13, color: '#727272', textAlign: 'left'}}>
                                        Ví dụ: Quý khách có Tháng và năm sinh là 02/1999 <br/> Mã xác thực sẽ
                                        là: <span className='semi'>021999</span>
                                    </p>
                                </div>
                            </div>
                            <div className="btn-wrapper" style={{marginTop: '33px'}}>
                                <LoadingIndicator area="submit-init-claim"/>
                                <button
                                    className={`btn btn-primary ${password.length < 6 || loading ? 'disabled' : ''}`}
                                    style={{fontWeight: '600'}}>Xác nhận
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="popupbg"></div>
            </form>
        </div>}
    </div>);
};

export default PasscodeCheckPayment;
