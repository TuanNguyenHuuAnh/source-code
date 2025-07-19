import React, {useEffect, useState} from "react";
// refresh token
import {
	ACCESS_TOKEN,
	ACCOUNT_REGISTER,
	ACCOUNT_STATUS,
	ADDRESS,
	AKTIVOLABS_ID,
	AUTHENTICATION,
	CELL_PHONE,
	CLASSPO,
	CLASSPOMAP,
	CLIENT_ID,
	CLIENT_PROFILE,
	COMPANY_KEY,
	DCID,
	DOB,
	EMAIL,
	EXPIRED_MESSAGE,
	FORGOT_PASSWORD,
	FORGOT_PASSWORD_NEW,
	FULL_NAME,
	GENDER,
	IRACE_ID,
	IRACE_SECRET_KEY,
	LINK_FB,
	LINK_GMAIL,
	LOGIN_TIME,
	OTHER_ADDRESS,
	PAGE_IRACE_BASE,
	POID,
	POINT,
	PREVIOUS_SCREENS,
	TRANSACTION_ID,
	TWOFA,
	USER_DEVICE_TOKEN,
	USER_LOGIN,
	VERIFY_CELL_PHONE,
	VERIFY_EMAIL,
	WEB_BROWSER_VERSION,
  	ICALLBACK,
	FE_BASE_URL,
	DOCTOR_ID,
	USER_NAME,
	CLIENT_CLASS,
	REVIEW_LINK,
	SIGNATURE
} from '../constants';
import {GetConfiguration, getPointByClientID, login2, logoutSession} from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import {GoogleReCaptcha} from "react-google-recaptcha-v3";
import {
	buildParamsAndRedirect,
	getDeviceId,
	getLinkPartner,
	getSession,
	roundDown,
	setSession,
	showMessage,
	removeSession,
	getLinkAktivoPartner,
	formatTime
} from '../util/common';
import { useLocation } from 'react-router-dom';
import {LoadCanvasTemplate, loadCaptchaEnginge, reloadCaptchaEnginge, validateCaptcha} from '../util/captcha2';
import LoginGoogle from './LoginGoogle';
import '../common/Common.css';
// import toast, { Toaster } from 'react-hot-toast';
import md5 from 'md5';
import ReactGA from "react-ga4";


var recaptchToken = '';
function Login({ parentCallback, hideMain, callbackShowLogin, showMoving, showIraceQ, path, isShowPopupApp }) {
	const [UserLogin, SetUserLogin] = useState('');
	const [Password, SetPassword] = useState('');
	const [captcha, setCaptcha] = useState('');
	const [requiredCaptch, setRequiredCaptch] = useState(false);
	const [showPass, setShowPass] = useState(false);
	const [errorCaptcha, setErrorCaptcha] = useState('');
	const [errorLogin, setErrorLogin] = useState('');
	const [enableLogin, setEnableLogin] = useState(false);
	const [loading, setLoading] = useState(false);
	const [showRegister, setShowRegister] = useState(false);
	const [countdown, setCountdown] = useState(0);
	// const warning = () =>  toast.error(<ToastMaintainence/>); 
	// const classLoginWrapper = (hideMain || (!getSession(ACCESS_TOKEN) && window.location.pathname !== '/'))?"login-warpper hidelogin":"login-warpper";
	// const classLogin = (hideMain || (!getSession(ACCESS_TOKEN) && window.location.pathname !== '/'))?"login":"login show";
	const classLoginWrapper = "login-warpper";
	const classLogin = "login show";
	const handleSubmit = (event) => {
		event.preventDefault();
		handleLogin();
	}
	const handleLogin = (event) => {
		setLoading(true);
		let loginRequest = {
			jsonDataInput: {
				Company: COMPANY_KEY,
				Action: 'CPLOGINV2',
				APIToken: '',
				Authentication: AUTHENTICATION,
				DeviceId: getDeviceId(),
				DeviceToken: getSession(USER_DEVICE_TOKEN) ? getSession(USER_DEVICE_TOKEN) : '',
				UserLogin: UserLogin,
				UserName: UserLogin,
				OS: '',
				Project: 'mcp',
				Password: Password,
				RecaptchaToken: recaptchToken,
				Captcha: captcha
			}
		};
		let byPass = false;
		if (requiredCaptch) {
			byPass = true;
		}
		login2(loginRequest, byPass)
			.then(response => {
				if (response.Response.Result === 'true' && response.Response.ErrLog.toLowerCase() === 'Login successfull'.toLowerCase() && response.Response.ClientProfile) {
					setSession(ACCESS_TOKEN, response.Response.NewAPIToken);
					setSession(CLIENT_ID, response.Response.ClientProfile[0].ClientID);
					setSession(SIGNATURE, response.Response.ClientProfile[0].ClientID);
					setSession(USER_LOGIN, response.Response.ClientProfile[0].LoginName);//fix for rewamp
					setSession(CLIENT_PROFILE, JSON.stringify(response.Response.ClientProfile));
					setSession(CELL_PHONE, response.Response.ClientProfile[0].CellPhone);
					setSession(FULL_NAME, response.Response.ClientProfile[0].FullName);
					if (response.Response.ClientProfile[0].Gender) {
						setSession(GENDER, response.Response.ClientProfile[0].Gender);
					}
					setSession(ADDRESS, response.Response.ClientProfile[0].Address);
					if (response.Response.ClientProfile[0].OtherAddress) {
						setSession(OTHER_ADDRESS, response.Response.ClientProfile[0].OtherAddress);
					}
					if (response.Response.ClientProfile[0].Email) {
						setSession(EMAIL, response.Response.ClientProfile[0].Email);
					}
					if (response.Response.ClientProfile[0].VerifyCellPhone) {
						setSession(VERIFY_CELL_PHONE, response.Response.ClientProfile[0].VerifyCellPhone);
					}
					if (response.Response.ClientProfile[0].VerifyEmail) {
						setSession(VERIFY_EMAIL, response.Response.ClientProfile[0].VerifyEmail);
					}
					if (response.Response.ClientProfile[0].TwoFA) {
						setSession(TWOFA, response.Response.ClientProfile[0].TwoFA);
					}
					if (response.Response.ClientProfile[0].LinkFaceBook) {
						setSession(LINK_FB, response.Response.ClientProfile[0].LinkFaceBook);
					}
					if (response.Response.ClientProfile[0].LinkGmail) {
						setSession(LINK_GMAIL, response.Response.ClientProfile[0].LinkGmail);
					}
					if (response.Response.ClientProfile[0].POID) {
						setSession(POID, response.Response.ClientProfile[0].POID);
					}
					if (response.Response.ClientProfile[0].DOB) {
						setSession(DOB, response.Response.ClientProfile[0].DOB);
					}
					if (response.Response.ClientProfile[0].DCID) {
						setSession(DCID, response.Response.ClientProfile[0].DCID);
						ReactGA.set({ userId: response.Response.ClientProfile[0].DCID });
						// Thiết lập user_id khi đăng nhập
						// gtag('set', {'user_id': response.Response.ClientProfile[0].DCID});
					}

					if (response.Response.ClientProfile[0].UserName) {
						setSession(USER_NAME, response.Response.ClientProfile[0].UserName);
					}

					const pointRequest = {
						jsonDataInput: {
							Company: COMPANY_KEY,
							OS: '',
							APIToken: response.Response.NewAPIToken,
							Authentication: AUTHENTICATION,
							ClientID: response.Response.ClientProfile[0].ClientID,
							DeviceId: getDeviceId(),
							Project: 'mcp',
									UserLogin: response.Response.ClientProfile[0].LoginName
						}
					}
					getPointByClientID(pointRequest).then(res => {
						let Response = res.CPGetPointByCLIIDResult;
						if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
							setSession(CLASSPO, CLASSPOMAP[Response.ClassPO] ? CLASSPOMAP[Response.ClassPO] : Response.ClassPO);
							setSession(CLIENT_CLASS, Response.ClassPO);
							setSession(POINT, roundDown(parseFloat(Response.Point) / 1000));
							parentCallback(true, response.Response.ClientProfile, Response.Point);
						} else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
							showMessage(EXPIRED_MESSAGE);
							logoutSession();
							// this.props.history.push({
							// 	pathname: '/home',
							// 	state: { authenticated: false, hideMain: false }

							// })

						} else {
							parentCallback(true, response.Response.ClientProfile, 0);
						}
						if (path) {
							buildParamsAndRedirect(path);
						}
						if (!isShowPopupApp) {
 
                            setTimeout(() => {
                                const headerContainer = document.getElementById('header-id');
                                const mainContainer = document.querySelector('main');
           
                                headerContainer.style.marginTop = '0px';
                                mainContainer.style.marginTop = '0px';
           
                            }, 200);
                        }

					}).catch(error => {
						//this.props.history.push('/maintainence');
						if (path) {
							buildParamsAndRedirect(path);
						}
					});
					setSession(LOGIN_TIME, new Date().getTime() / 1000);
					setLoading(false);
					SetUserLogin('');
					SetPassword('');
					setRequiredCaptch(false);
					setErrorLogin('');
					if (getSession(PREVIOUS_SCREENS) && ((getSession(PREVIOUS_SCREENS) === '/song-vui-khoe')) || (getSession(PREVIOUS_SCREENS) === '/home') || (getSession(PREVIOUS_SCREENS) === '/') || getSession(ICALLBACK)) {
						// document.getElementById('popup-irace-option-id').className = 'popup option-popup show';
						getLinkId();
					}
					try {
						getLinkAktivoPartner(AKTIVOLABS_ID);
					} catch (e) {
						// console.log('AKTIVOLABS_ID', e);
					}
					if (getSession(REVIEW_LINK)) {
						window.location.href = getSession(REVIEW_LINK);
					}
					recaptchToken = ''; //Login success reset to fix logout relogin
				} else if (response.Response.ErrLog === 'RECACTCHA_REQUIRED_OTP') {
					setRequiredCaptch(true);
					loadCaptchaEnginge(6, 'grey', 'white', response.Response.Captcha);
					setLoading(false);
					if (requiredCaptch) {
						reloadCaptchaEnginge(6, 'grey', 'white', UserLogin);
					}
				} else if (response.Response.ErrLog === 'WRONGPASSEXCEED') {
					setErrorLogin('Thông tin nhập đã sai quá 5 lần. Chức năng tạm ngừng hoạt động trong 30 phút.');
					setCountdown(response.Response.RemainSeconds);
					setLoading(false);
				} else if ((response.Response.ErrLog === 'WRONGPASS') || (response.Response.ErrLog === 'CLINOEXIST') || (response.Response.ErrLog === 'Login Fail')) {
					setErrorLogin('Thông tin đăng nhập không chính xác. Quý khách còn ' + response.Response.numOfRemaining + ' lần thử.');
					setLoading(false);
					if (requiredCaptch) {
						reloadCaptchaEnginge(6, 'grey', 'white', UserLogin);
					}
				} else {
					setErrorLogin('Thông tin đăng nhập không chính xác. Vui lòng kiểm tra lại.');
					setLoading(false);
					if (requiredCaptch) {
						reloadCaptchaEnginge(6, 'grey', 'white', UserLogin);
					}
				}

			}).catch(error => {
			setErrorLogin('Đăng nhập không thành công. Vui lòng kiểm tra lại.');
			setLoading(false);
			if (requiredCaptch) {
				reloadCaptchaEnginge(6, 'grey', 'white', UserLogin);
			}
			// warning();
		});
	}

	function useInterval(callback, delay) {
        const intervalRef = React.useRef(null);
        const savedCallback = React.useRef(callback);
        React.useEffect(() => {
            savedCallback.current = callback;
        }, [callback]);
        React.useEffect(() => {
            const tick = () => savedCallback.current();
            if (typeof delay === 'number') {
                intervalRef.current = window.setInterval(tick, delay);
                return () => window.clearInterval(intervalRef.current);
            } else {
                return () => window.clearInterval(intervalRef.current);
            }
        }, [delay]);
        return intervalRef;
    }

	const getLinkId = () => {
		if (getSession(PREVIOUS_SCREENS) === '/song-vui-khoe') {
			const doctorId = getSession(DOCTOR_ID);
			removeSession(DOCTOR_ID);
			let href = FE_BASE_URL + "/tu-van-suc-khoe/tu-van-tu-xa?iddr=" + doctorId + '&hinh-thuc=chat';
			window.location.href = href;
		}
		if (getSession(IRACE_ID)) {
			let dlvn_id = getSession(DCID);
			let cdyt_id = getSession(IRACE_ID);
			let checksum = md5(IRACE_SECRET_KEY + '|' + dlvn_id + '|' + cdyt_id);
			let href = PAGE_IRACE_BASE + '?dlvn_id='+ dlvn_id + '&cdyt_id=' + cdyt_id + '&checksum=' + checksum;
			if (getSession(ICALLBACK)) {
				removeSession(ICALLBACK);
				window.location.href = href;
			} else {
				window.open(href);
			}
		} else {
			let request = {
				jsonDataInput: {
					OS: WEB_BROWSER_VERSION,
					Platform: 'Web_mCP',
					APIToken: getSession(ACCESS_TOKEN),
					Action: 'GetLinkIDByFunc3P',
					Authentication: AUTHENTICATION,
					ClientID: getSession(CLIENT_ID),
					DeviceId: getDeviceId(),
					Project: 'mcp',
					DCID: getSession(DCID),
					FunctionID: '27',
					UserLogin: getSession(USER_LOGIN)
				}
			}
			GetConfiguration(request).then(res => {
				let Response = res.Response;
				if (Response.ErrLog === 'Successfull' && Response.Result === 'true' && Response.ClientProfile) {
					setSession(IRACE_ID, Response.ClientProfile[0].LinkID ? Response.ClientProfile[0].LinkID : '');
					if (!Response.ClientProfile[0].LinkID) {
						document.getElementById('popup-irace-option-id').className = 'popup option-popup show';
					} else {
						let dlvn_id = getSession(DCID);
						let cdyt_id = Response.ClientProfile[0].LinkID;
						let checksum = md5(IRACE_SECRET_KEY + '|' + dlvn_id + '|' + cdyt_id);
						let href = PAGE_IRACE_BASE + '?dlvn_id='+ dlvn_id + '&cdyt_id=' + cdyt_id + '&checksum=' + checksum;
						if (getSession(ICALLBACK)) {
							removeSession(ICALLBACK);
							window.location.href = href;
						} else {
							window.open(href);
						}
					}
				} else {
					document.getElementById('popup-irace-option-id').className = 'popup option-popup show';
				}
			}).catch(error => {
			});
	
		}
	}
	const forgotPassword = (event) => {
		event.preventDefault();
		callbackShowLogin(false);
		setSession(FORGOT_PASSWORD, FORGOT_PASSWORD);
		setSession(ACCOUNT_STATUS, FORGOT_PASSWORD_NEW);
		if (document.getElementById('option-popup')) {
			document.getElementById('option-popup').className = "popup option-popup show";
		}
	}

	const tokenVerify = (token) => {
		recaptchToken = token;
	}
	const registerAccount = () => {
		callbackShowLogin(false);
		if (getSession(FORGOT_PASSWORD)) {
			sessionStorage.removeItem(FORGOT_PASSWORD);
		}
		if (getSession(TRANSACTION_ID)) {
			sessionStorage.removeItem(TRANSACTION_ID);
		}
		setSession(ACCOUNT_STATUS, ACCOUNT_REGISTER);
		if (document.getElementById('option-popup')) {
			document.getElementById('option-popup').className = "popup option-popup show";
		}
	}
	const showHidePass = () => {
		setShowPass(!showPass);
	}
	const handleValidateInput = () => {
		let validCaptcha = true;
		if (requiredCaptch) {
			let errCaptcha = !validateCaptcha(captcha, false) ? "Mã Bảo vệ không đúng" : "";
			if (errCaptcha !== errorCaptcha) {
				setErrorCaptcha(errCaptcha);
			}
			if (errCaptcha.length > 0) {
				validCaptcha = false;
			}
		}

		if (UserLogin !== '' && Password !== '' && validCaptcha) {
			setEnableLogin(true);
			// handleReCaptchaVerify();
		} else {
			setEnableLogin(false);
		}
	}
	const hideLogin = () => {
		// if (document.getElementById('login-id')) {
		// 	document.getElementById('login-id').className = 'login';
		// }
		callbackShowLogin(false);
		// if (document.getElementById("login-wrapper-id")) {
		// 	document.getElementById("login-wrapper-id").className = 'login-warpper hidelogin';
		// }
	}

	const reloadCaptcha = (numberOfCharacters, backgroundColor, fontColor, userLogin) => {
		reloadCaptchaEnginge(numberOfCharacters, backgroundColor, fontColor, userLogin);
	}

	const turnOnMoving = () => {
		showMoving(true);
		setTimeout(turnOffMoving, 4000);
	}

	const turnOffMoving = () => {
		showMoving(false);


	}

	useEffect(() => {
		handleValidateInput();
	});
	useEffect(() => {
        if (countdown === 0) {
			setErrorLogin('');
        }
    }, [countdown]);
    
    useInterval(
        () => {
            setCountdown((countdown) => countdown - 1);
        },
        countdown > 0 ? 1000 : null
    );

	function ToastMaintainence() {
		return (
			<div>
				<p>Ứng dụng Không thể kết nối đến máy chủ, quý khách vui lòng thử lại!</p>
				<p>Hoặc liên hệ tổng đài qua số điện thoại: (028)38 100 888, bấm phím số 1 để được hỗ trợ.</p>
			</div>
		);
	};
	return (
		<div className={classLoginWrapper} id="login-wrapper-id">
			<div className="container">
				<form onSubmit={(event) => handleSubmit(event)}>
					<div className={classLogin} id="login-id">
						<div className="login__card" style={{ marginTop: '56px' }}>
							<div className="login__card-header">
								<p>Đăng nhập</p>
								<i className="login-closebtn" onClick={() => hideLogin()}><img src="../../../../img/icon/close.svg" alt="" /></i>
							</div>
							<LoadingIndicator area="login-area" />
							<div className="login__card-body">
								{(errorLogin.length > 0) &&
									<div className="error-message validate">
										<i className="icon">
											<img src="../../../../img/icon/warning_sign.svg" alt="" />
										</i>
										<p style={{ 'lineHeight': '20px' }}>{errorLogin}
										{countdown > 0 && <span
                                        	style={{color: '#FAE340', fontSize: 13}}> {formatTime(countdown)}</span>}
										</p>
									</div>
								}
								{showRegister &&
									<div className="error-message validate">
										<i className="icon">
											<img src="../../../../img/icon/warning_sign.svg" alt="" />
										</i>
										<p style={{ 'lineHeight': '20px' }}>Tài khoản không tồn tại. Vui lòng kiểm tra Tên đăng nhập hoặc bấm vào đây để <span className="light-register" onClick={() => registerAccount()}>Đăng ký</span></p>
									</div>
								}
								<div className="loginform">
									<div className="loginform-item">
										<div className={errorLogin.length > 0 ? "input validate" : "input"} id="user-input">
											<div className="input__content">
												{UserLogin !== null &&
													UserLogin !== undefined &&
													UserLogin !== '' &&
													<label htmlFor="">Tên đăng nhập/Mã khách hàng</label>
												}
												<input placeholder="Tên đăng nhập/Mã khách hàng" type="search" name="UserLogin" value={UserLogin} onChange={(event) => SetUserLogin(event.target.value.trim())} required />
											</div>
											<i><img src="../../../../img/icon/edit.svg" alt="" /></i>
										</div>
									</div>
									<div className="loginform-item">
										<div className={showPass ? (errorLogin.length > 0 ? "input password-input show validate" : "input password-input show") : (errorLogin.length > 0 ? "input password-input validate" : "input password-input")} id="password-input">
											<div className="input__content">
												{Password !== null &&
													Password !== undefined &&
													Password !== '' &&
													<label htmlFor="">Mật khẩu</label>
												}
												<input placeholder="Mật khẩu" type={showPass ? "text" : "password"} id="passwordId" name="Password" value={Password} onChange={(event) => SetPassword(event.target.value.trim())} required />
											</div>
											<i className="password-toggle" onClick={() => showHidePass()}></i>
										</div>
									</div>
									{requiredCaptch &&
										<div className="captcha-container" style={{ margin: '4px 0 12px 0', display: '-webkit-inline-box' }}>
											<div className="captcha-container-input" style={{ width: '48%' }}>
												<div className="input">
													<div className="input__content">
														<label htmlFor="" className="text-code">Mã bảo vệ*</label>
														<input type="search" name="captcha" value={captcha} maxLength="6" id="captcha-id"
															onChange={(event) => setCaptcha(event.target.value)}
														/>
													</div>
												</div>
											</div>
											<div className="captcha-container-captcha" style={{ width: '42%', display: '-webkit-inline-box', paddingTop: '11px' }}>
												<div className="capt-img">
													<LoadCanvasTemplate />
												</div>
												<div className="reload-icon" style={{ margin: '5px' }}>
													<img src="/../../../../img/icon/reload-icon.svg" alt="reload" onClick={() => reloadCaptcha(6, 'grey', 'white', UserLogin)} />
												</div>
											</div>
										</div>
									}
									{requiredCaptch && (errorCaptcha.length > 0) && (captcha.length > 0) && <span style={{ color: 'red', margin: '0 0 8px 0' }}>{errorCaptcha}</span>}
								</div>
								<a className="forgotpass-btn" onClick={(event) => forgotPassword(event)} style={{ width: '100%', marginTop: 20 }}>Quên thông tin đăng nhập?</a>
								{enableLogin && !loading ? (
									<button type='submit' className="login-submit btn btn-primary" id="loginBtn" style={{justifyContent: 'center'}}>Đăng nhập</button>
								) : (
									<button type='submit' className="login-submit btn btn-primary disabled" id="loginBtn" disabled style={{justifyContent: 'center'}}>Đăng nhập</button>
								)}

								<div className="method-warpper">
									<p>Hoặc đăng nhập bằng</p>
									<div className="method">
										<div className="method__item">
											<LoginGoogle parentCallback={parentCallback} path={path}/>

										</div>
									</div>
								</div>
								<div className="signup">
									<p>Bạn chưa có tài khoản?</p>
									<span className="basic-red basic-bold" onClick={() => registerAccount()}>Đăng ký</span>
								</div>
							</div>
						</div>

						<div className="login__bg"></div>
					</div>
					{((!recaptchToken) && !requiredCaptch) && Password &&
					<GoogleReCaptcha onVerify={(token) => tokenVerify(token)} />
					}
				</form>
			</div>
		</div>

	);
}

export default Login;
