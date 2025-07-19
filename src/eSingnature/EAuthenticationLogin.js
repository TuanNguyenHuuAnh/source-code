import React, { useState, useEffect, useCallback } from "react";
// refresh token
import { refreshTokenSetup } from '../util/refreshToken';
import { GENDER, ADDRESS, EMAIL, OTHER_ADDRESS, ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, CLIENT_PROFILE, FORGOT_PASSWORD, POID, DOB, CLASSPO, POINT, CELL_PHONE, FULL_NAME, CLASSPOMAP, EXPIRED_MESSAGE, VERIFY_CELL_PHONE, VERIFY_EMAIL, TWOFA, LINK_FB, LINK_GMAIL, USER_DEVICE_TOKEN, COMPANY_KEY, AUTHENTICATION, LOGIN_TIME, TRANSACTION_ID, LOGINED, POL_LIST_CLIENT, DCID } from '../constants';
import { login2, getPointByClientID, logoutSession, checkAccount } from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import { GoogleReCaptcha, useGoogleReCaptcha } from "react-google-recaptcha-v3";
import { roundDown, showMessage, setSession, getSession, getDeviceId } from '../util/common';
import { loadCaptchaEnginge, LoadCanvasTemplate, validateCaptcha, reloadCaptchaEnginge } from '../util/captcha2';
import '../common/Common.css';
import ReactGA from 'react-ga4';
// import toast, { Toaster } from 'react-hot-toast';


var recaptchToken = '';
function EAuthenticationLogin({ eSingnatureClientID, setShowAuthentication, setLoginSuccess }) {
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
	// const warning = () =>  toast.error(<ToastMaintainence/>); 
	// const classLoginWrapper = (hideMain || (!getSession(ACCESS_TOKEN) && window.location.pathname !== '/'))?"login-warpper hidelogin":"login-warpper";
	// const classLogin = (hideMain || (!getSession(ACCESS_TOKEN) && window.location.pathname !== '/'))?"login":"login show";
	const handleSubmit = (event) => {
		event.preventDefault();
		handleLogin();
	}
	const handleLogin = ()=> {
		if (UserLogin !== eSingnatureClientID) {
			setErrorLogin('Thông tin đăng nhập không chính xác. Vui lòng kiểm tra lại.');
			return;
		}
		setLoading(true);
		let loginRequest = {
			jsonDataInput: {
				Company: COMPANY_KEY,
				Action: 'CPLOGIN',
				APIToken: '',
				Authentication: AUTHENTICATION,
				DeviceId: getDeviceId(),
				DeviceToken: getSession(USER_DEVICE_TOKEN)?getSession(USER_DEVICE_TOKEN): '',
				UserLogin: UserLogin,
				OS: '',
				Project: 'mcp',
				Password: Password,
				RecaptchaToken: recaptchToken,
				Captcha: captcha
			}
		};
		loginRequest.jsonDataInput.Action = 'CHECKACCOUNT';
		checkAccount(loginRequest).then(resp => {
			if (resp.Response.Result === 'true' && resp.Response.ErrLog === 'CLIEXIST') {
				loginRequest.jsonDataInput.Action = 'CPLOGIN';
				setShowRegister(false);
				var byPass = false;
				if (requiredCaptch) {
					byPass = true;
				}
				login2(loginRequest, byPass)
					.then(response => {
						//console.log(response.Response);
						if (response.Response.Result === 'true' && response.Response.ErrLog === 'Login successfull' && response.Response.ClientProfile) {
							//clear danh sach hop dong cua user truoc neu dang login
							if (getSession(POL_LIST_CLIENT)) {
								sessionStorage.removeItem(POL_LIST_CLIENT);
							}
							setSession(ACCESS_TOKEN, response.Response.NewAPIToken);
							setSession(CLIENT_ID, response.Response.ClientProfile[0].ClientID);
							//setSession(USER_LOGIN, UserLogin);
							setSession(USER_LOGIN, response.Response.ClientProfile[0].LoginName);//fix impact for rewamp username
							setSession(CLIENT_PROFILE, JSON.stringify(response.Response.ClientProfile));
							setSession(CELL_PHONE, response.Response.ClientProfile[0].CellPhone);
							setSession(FULL_NAME, response.Response.ClientProfile[0].FullName);
							setSession(GENDER, response.Response.ClientProfile[0].Gender);
							setSession(ADDRESS, response.Response.ClientProfile[0].Address);
							setSession(OTHER_ADDRESS, response.Response.ClientProfile[0].OtherAddress);
							setSession(EMAIL, response.Response.ClientProfile[0].Email);
							setSession(VERIFY_CELL_PHONE, response.Response.ClientProfile[0].VerifyCellPhone);
							setSession(VERIFY_EMAIL, response.Response.ClientProfile[0].VerifyEmail);
							setSession(TWOFA, response.Response.ClientProfile[0].TwoFA);
							setSession(LINK_FB, response.Response.ClientProfile[0].LinkFaceBook);
							setSession(LINK_GMAIL, response.Response.ClientProfile[0].LinkGmail);
							if (response.Response.ClientProfile[0].POID) {
								setSession(POID, response.Response.ClientProfile[0].POID);
							}
							if (response.Response.ClientProfile[0].DOB) {
								setSession(DOB, response.Response.ClientProfile[0].DOB);
							}
							if (response.Response.ClientProfile[0].DCID) {
								setSession(DCID, response.Response.ClientProfile[0].DCID);
								ReactGA.set({ userId: response.Response.ClientProfile[0].DCID });
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
									UserLogin: UserLogin
								}
							}
							getPointByClientID(pointRequest).then(res => {
								let Response = res.CPGetPointByCLIIDResult;
								if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
									setSession(CLASSPO, CLASSPOMAP[Response.ClassPO] ? CLASSPOMAP[Response.ClassPO] : Response.ClassPO);
									setSession(POINT, roundDown(parseFloat(Response.Point) / 1000));
									// parentCallback(true, response.Response.ClientProfile, Response.Point);
									setSession(LOGINED, LOGINED);
								} else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
									showMessage(EXPIRED_MESSAGE);
									logoutSession();
									this.props.history.push({
										pathname: '/home',
										state: { authenticated: false, hideMain: false }
		
									})
		
								} else {
									// parentCallback(true, response.Response.ClientProfile, 0);
									setSession(LOGINED, LOGINED);
								}
							}).catch(error => {
								//this.props.history.push('/maintainence');
							});
		
							setSession(LOGIN_TIME, new Date().getTime()/1000);
							setLoading(false);
							SetUserLogin('');
							SetPassword('');
							setRequiredCaptch(false);
							setErrorLogin('');
							setShowAuthentication(false);
							setLoginSuccess(true);
							//Alert.success("You're successfully logged in!");
						} else if (response.Response.ErrLog === 'RECACTCHA_REQUIRED_OTP') {
							setRequiredCaptch(true);
							loadCaptchaEnginge(6, 'grey', 'white', response.Response.Captcha);
							setLoading(false);
							if (requiredCaptch) {
								reloadCaptchaEnginge(6, 'grey', 'white', UserLogin);
							}
						} else if (response.Response.ErrLog === 'WRONGPASSEXCEED') {
							setErrorLogin('Tài khoản của Quý khách đang bị khóa do nhập sai thông tin nhiều lần. Vui lòng thử lại sau 30 phút.');
							setLoading(false);
						} else if (response.Response.ErrLog === 'WRONGPASS') {
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
		
			} else if (resp.Response.Result === 'true' && (resp.Response.ErrLog === 'CLINOEXIST' || resp.Response.ErrLog === 'CLIEXISTNOACTIVE')) {
				setErrorLogin('');
				setShowRegister(true);
				setLoading(false);
			} 
		}).catch(error => {

		});


	}
	const forgotPassword = (event) => {
		event.preventDefault();
		setSession(FORGOT_PASSWORD, FORGOT_PASSWORD);
		document.getElementById('option-popup').className = "popup option-popup show";
	}

	const tokenVerify = (token) => {
		recaptchToken = token;
	}
	const registerAccount = () => {
		if (getSession(FORGOT_PASSWORD)) {
			sessionStorage.removeItem(FORGOT_PASSWORD);
		}
		if (getSession(TRANSACTION_ID)) {
			sessionStorage.removeItem(TRANSACTION_ID);
		}
		document.getElementById('option-popup').className = "popup option-popup show";
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
		setShowAuthentication(false);
	}

	const reloadCaptcha = (numberOfCharacters, backgroundColor, fontColor, userLogin) => {
		reloadCaptchaEnginge(numberOfCharacters, backgroundColor, fontColor, userLogin);
	}
	useEffect(() => {
		handleValidateInput();
	});
	function ToastMaintainence() {
		return (
		  <div>
			<p>Ứng dụng Không thể kết nối đến máy chủ, quý khách vui lòng thử lại!</p>
			<p>Hoặc liên hệ tổng đài qua số điện thoại: (028)38 100 888, bấm phím số 1 để được hỗ trợ.</p>
		</div>
		);
	  };
	return (
		<div className="login-warpper" id="e-singnature-login-wrapper-id">
			<div className="container">
				<form onSubmit={(event) => handleSubmit(event)}>
					<div className="login show" id="e-singnature-login-id">
						<div className="e_signature_login__card">
							<div className="login__card-header">
								<h4>Đăng nhập</h4>
								<i className="e_signature_login-closebtn" onClick={() => hideLogin()}><img src="img/icon/close.svg" alt="" /></i>
							</div>
							<LoadingIndicator area="login-area" />
							<div className="login__card-body">
								{(errorLogin.length > 0) &&
									<div className="error-message validate">
										<i className="icon">
											<img src="img/icon/warning_sign.svg" alt="" />
										</i>
										<p style={{ 'lineHeight': '20px' }}>{errorLogin}</p>
									</div>
								}
								{showRegister&&
									<div className="error-message validate">
										<i className="icon">
											<img src="img/icon/warning_sign.svg" alt="" />
										</i>
										<p style={{ 'lineHeight': '20px' }}>Tài khoản không tồn tại. Vui lòng kiểm tra Tên đăng nhập hoặc bấm vào đây để <span className="light-register" onClick={() => registerAccount()}>Đăng ký</span></p>
									</div>
								}
								<div className="loginform">
									<div className="loginform-item">
										<div className={errorLogin.length > 0 ? "input validate":"input"} id="user-input">
											<div className="input__content">
												{UserLogin !== null &&
													UserLogin !== undefined &&
													UserLogin !== '' &&
													<label htmlFor="">Mã khách hàng</label>
												}
												<input placeholder="Mã khách hàng" type="search" name="UserLogin" value={UserLogin} onChange={(event) => SetUserLogin(event.target.value.trim())} required />
											</div>
											<i><img src="img/icon/edit.svg" alt="" /></i>
										</div>
									</div>
									<div className="loginform-item">
										<div className={showPass ? (errorLogin.length > 0 ? "input password-input show validate": "input password-input show" ): (errorLogin.length > 0 ? "input password-input validate": "input password-input")} id="password-input">
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
														<label for="" className="text-code">Nhập mã Captcha</label>
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
													<img src="/img/icon/reload-icon.svg" alt="reload" onClick={()=>reloadCaptcha(6, 'grey', 'white', UserLogin)}/>
												</div>
											</div>
										</div>
									}
									{requiredCaptch && (errorCaptcha.length > 0) && (captcha.length > 0) && <span style={{ color: 'red', margin: '0 0 8px 0' }}>{errorCaptcha}</span>}
								</div>
								{enableLogin && !loading ? (
									<button type='submit' className="login-submit btn btn-primary" id="loginBtn">Đăng nhập</button>
								) : (
									<button type='submit' className="login-submit btn btn-primary disabled" id="loginBtn" disabled>Đăng nhập</button>
								)}

							</div>
						</div>

						<div className="login__bg"></div>
					</div>
					{((Password.length === 1) && !requiredCaptch) &&
					<GoogleReCaptcha onVerify={(token) => tokenVerify(token)} />
					}
				</form>
			</div>
		</div>

	);
}

export default EAuthenticationLogin;
