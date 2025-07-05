/**
 * @author vunt
 */
package vn.com.unit.ep2p.admin.config;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.gson.Gson;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.dto.MenuInfo;
import vn.com.unit.core.dto.MenuItem;
import vn.com.unit.core.dto.UserAuthorityDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaAccountTeam;
import vn.com.unit.core.enumdef.AccountGroupEnum;
import vn.com.unit.core.enumdef.AccountTypeEnum;
import vn.com.unit.core.security.UserPrincipal;
import vn.com.unit.core.service.AuthorityService;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.core.service.JcaAccountTeamService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.constant.FirebaseConstant;
import vn.com.unit.ep2p.admin.dto.AgentInfoSaleSopDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.RoleDocumentDto;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.admin.service.TeamService;
import vn.com.unit.ep2p.constant.ApiAccountTypeConstant;
import vn.com.unit.ep2p.constant.ResponseErrorCodeConstant;
import vn.com.unit.ep2p.core.dto.AccountApiDto;
import vn.com.unit.ep2p.core.utils.LDAPUtil;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.ers.dto.AuthenticationExceptionMockup;
import vn.com.unit.ep2p.ers.dto.CustomUsernamePasswordAuthentication;
import vn.com.unit.ep2p.utils.LangugeUtil;

/**
 * CustomAuthenticationProvider
 *
 * @version 01-00
 * @since 01-00
 * @author vunt
 * @Last updated: 13/05/2024	nt.tinh SR16541 - Enhance new agent type SM, SS, AT
 */
@Component
@Primary
public class CustomAuthenticationApiProvider implements AuthenticationProvider {

	/** accountService */
	@Autowired
	private JcaAccountService accService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	JcaAccountTeamService jcaAccountTeamService;
	@Autowired
	TeamService teamService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private Environment env;
	@Autowired
	SystemConfig systemConfig;

	@Autowired
	@Qualifier("appSystemConfigServiceImpl")
	private JcaSystemConfigService jcaSystemConfigService;

	@Autowired
	private AccountService accountService;
	@Autowired
	private Db2ApiService db2ApiService;

	@Autowired
	private  HttpServletRequest request;
	/**
	 * Authentication database system and LDAP
	 *
	 * @param authentication type Authentication
	 * @return Authentication
	 * @throws AuthenticationException
	 * @author vunt
	 */
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private static final String DEVICE_TOKEN = "web_Yjg5YzkzZjA3OGRhM2NhY2ZlNjQ0YjVlNzRlZjE2N2VlNzE1ODE3ZmJiYzEwN2EzMjdmZmFjNTNkZjJhZWE1N0RMVk4=";

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		CustomUsernamePasswordAuthentication customAuthentication = (CustomUsernamePasswordAuthentication) authentication;

		//deviceToken will be blank when user using web app
		String deviceToken = StringUtils.isBlank(customAuthentication.getDeviceToken()) ? DEVICE_TOKEN : customAuthentication.getDeviceToken();

		String deviceId = customAuthentication.getDeviceId();
		boolean isWebapp = customAuthentication.isWebapp();

		Locale locale = LangugeUtil.getLanguageFromHeader(request);

		JcaAccount account = null;
		boolean isSuccessLogin = false;
		boolean forceChangePass = false;
		boolean userLocal = false;

		// messageError
		String messageError = StringUtils.EMPTY;

		// exists Login
		boolean existsUserLogin = false;

		Long companyId = 2L;
		String TOKEN = jcaSystemConfigService.getValueByKey("LOGIN_API_SECRET_KEY", companyId);
		// get list user allow login
		String listUserAllowLogin = jcaSystemConfigService.getValueByKey("LIST_USER_ALLOW_LOGIN", companyId);
		List<String> listAllow = new ArrayList<>();
		CollectionUtils.addAll(listAllow, listUserAllowLogin.split(","));
		if (CollectionUtils.isNotEmpty(listAllow) && StringUtils.isNotEmpty(listUserAllowLogin) && !listAllow.contains(username) ) {
			messageError = checkErrorMessage("account.does.not.exist", new Object[] { username }, locale);
			throw new AuthenticationExceptionMockup(messageError);
		}

		// Get configure so lan login fail se khoa tai khoan
		int failLoginCount = systemConfig.getIntConfig("FAILED_LOGIN_COUNT");

		// Get thoi gian khoa tai khoan trong bao lau
		String timeInLocked = systemConfig.getConfig("TIME_LOCK");

		// listAccount
		List<JcaAccount> listAccount = new ArrayList<>();

		// If password is empty
		if (StringUtils.isEmpty(password)) {
			// Password is required.
			messageError = checkErrorMessage("users.not.found.database", new Object[] { username }, locale);
			throw new AuthenticationExceptionMockup(messageError);
		}

		// 1. Username is empty -> login by facebook or google or apply id
		if (StringUtils.isEmpty(username)) {
			//get UID from firebase
			//we have idToken
			String idToken = password;
			try{
				String fileName = env.getProperty(FirebaseConstant.FIREBASE_CONFIGURATOION_FILE);
				GoogleCredentials googleCredentials = GoogleCredentials
						.fromStream(getClass().getClassLoader().getResourceAsStream(fileName));

				@SuppressWarnings("deprecation") FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(googleCredentials).build();

				if (FirebaseApp.getApps().isEmpty()) {
					FirebaseApp.initializeApp(options);
				}

				FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
				password = decodedToken.getUid();

			} catch(Exception ex){
				logger.error("firebase error: ", ex);
				throw new UsernameNotFoundException("Hệ thống đang gặp sự cố kết nối, Bạn vui lòng đăng nhập lại sau!");
			}

			// find account uid
			JcaAccount accountUid = accService.getAccountByUid(password);
			if (accountUid != null) {
				listAccount.add(accountUid);

				isSuccessLogin = true;

				// set existsUserLogin
				existsUserLogin = true;
			} else {
				messageError = checkErrorMessage("users.not.register.account.system", null, locale);
				throw new AuthenticationExceptionMockup(messageError);
			}

			if (listAccount == null || listAccount.isEmpty()) {
				// Username is required.
				messageError = checkErrorMessage("users.not.found.database", new Object[] { username }, locale);
				throw new AuthenticationExceptionMockup(messageError);

			}
			//check user login is agent and terminated
			checkTerAndDiscipline(locale, accountUid);
		} else {
			/*String verifyFlag = systemConfig.getConfig("VERIFY_CAPCHA_FLAG");
			if (StringUtils.isNotEmpty(verifyFlag) && "1".equals(verifyFlag)) {
				try {

					String secret = systemConfig.getConfig(SystemConfig.CAPTCHA_SECRET_KEY);
					String url = "https://www.google.com/recaptcha/api/siteverify";
					String param = "?secret="+ secret +"&response=" + customAuthentication.getValueToken();
					String jsonDtoSubmit = "";
					logger.debug("PERSONAL_INFO_SUBMITED: "+ url + param);
					logger.debug("jsonDtoSubmit: "+ jsonDtoSubmit);
					JSONObject obj = RetrofitUtils.callApi(url + param, jsonDtoSubmit);
					String result = obj.get("success").toString();
					logger.debug("result: "+ result);
					if (StringUtils.isEmpty(result) || result != "true") {
						messageError = checkErrorMessage("account.does.not.exist", new Object[] { username }, locale);
						throw new AuthenticationExceptionMockup(messageError);
					}
				}
				catch(Exception e) {
					logger.error("recaptchaError", e);
					throw new UsernameNotFoundException("Hệ thống đang gặp sự cố kết nối, Bạn vui lòng đăng nhập lại sau!");
				}
				
			}*/
			// Get Account by username
			listAccount = accService.getListByUserName(username);
		}

		// Get Account
		if (!CollectionUtils.isEmpty(listAccount)) {
			account = listAccount.get(0);

			// Check lock user
			Duration timeElapsed = this.getDurationBetween(account);
			checkLockedUser(locale, account, failLoginCount, timeInLocked, timeElapsed);
			//if account's valid then reset failLoginCount = 0
			if (account.getFailedLoginCount() >= failLoginCount && Long.compare(timeElapsed.getSeconds(), Long.valueOf(timeInLocked) * 60) > 0) {
				account.setFailedLoginCount(0);
			}
		}

		// AccountApiDto
		AccountApiDto accountApiDto = null;

		// 2. Agent, Candidate - If isSuccessLogin is false && (account not in DB or account type is agent, candidate)
		if (!existsUserLogin && (account == null
				|| (account != null && (StringUtils.equals(account.getAccountType(), ApiAccountTypeConstant.TYPE_AGENT)
						|| StringUtils.equals(account.getAccountType(), ApiAccountTypeConstant.TYPE_CANDIDATE)
						|| account.getAccountType() == null)))) {
			// Check login API
			try {
				accountApiDto = RetrofitUtils.loginApi(TOKEN, username, password, deviceToken, deviceId);
			} catch (Exception e) {
				throw new AuthenticationExceptionMockup(e.getMessage());
			}

			if (accountApiDto != null) {
				// ResponseMsg
				String responseMsg = accountApiDto.getResponseMsg();
				// AuthStatus
				String authStatus = accountApiDto.getAuthStatus();
				// Account exists in API
				if (StringUtils.equals(authStatus, "true") || (StringUtils.equals(authStatus, "false")
						&& (StringUtils.equalsIgnoreCase(responseMsg, ResponseErrorCodeConstant.WRONG_PASSWORD_VI)
								|| StringUtils.equals(responseMsg, ResponseErrorCodeConstant.PASSWORD_EXPIRED_VI)))) {

					// Set existsUserLogin
					existsUserLogin = true;

					if (StringUtils.equals(authStatus, "true")) {
						// Login API Success
						isSuccessLogin = true;
					}

					if (listAccount == null || listAccount.isEmpty()) {
						account = new JcaAccount();
						//in case of Candidate login, set user = id number
						account.setUsername(username);
					}

					if (StringUtils.equalsIgnoreCase(responseMsg, ResponseErrorCodeConstant.WRONG_PASSWORD_VI)) {
						// Set Fail Login count
                        Duration timeElapsed = getDurationBetween(account);
                        if (Long.compare(timeElapsed.getSeconds(), Long.valueOf(timeInLocked) * 60) > 0) {
                            account.setFailedLoginCount(1);
                        } else {
                            account.setFailedLoginCount(Optional.ofNullable(account.getFailedLoginCount()).orElse(0) + 1);
                        }

						account.setLoginLock(true);
						deviceToken = null;
					} else {
						// Truong hop so lan login fail < so lan login fail config
						if (account.getFailedLoginCount() < failLoginCount) {
							account.setLoginLock(false);
							account.setFailedLoginCount(0);
						} else {
							account.setLoginLock(true);
						}
					}

					account.setLoginDate(new Date());
					account.setChannel(accountApiDto.getChannel());
					
					// set account for save
					updateAccount(account, accountApiDto, deviceToken, isWebapp);
					// Save to DB
					account = accountService.saveJcaAccount(account);
				}
//				check ter and discipline first
//				account first log in and call api true
				if(accountApiDto != null && (StringUtils.equals(accountApiDto.getAccountType(), ApiAccountTypeConstant.TYPE_AGENT))) {
					// check ter and discipline first
					JcaAccount accountCheckTerAndDiscipline = new JcaAccount();
					accountCheckTerAndDiscipline.setUsername(username);
					accountCheckTerAndDiscipline.setAccountType(accountApiDto.getAccountType());
					checkTerAndDiscipline(locale, accountCheckTerAndDiscipline);
				}
				// Validate Agent/Candidate
				if (StringUtils.equals(authStatus, "true")) {
					// Agent là terminated thì lỗi
//					checkTerAndDiscipline(locale, account);

				} else {
					// WRONG PASSWORD
					if (StringUtils.equalsIgnoreCase(responseMsg, ResponseErrorCodeConstant.WRONG_PASSWORD_VI)) {

						// showErrorWrongPassword
						this.showErrorWrongPassword(account, failLoginCount, timeInLocked, locale);

					} else if (StringUtils.equalsIgnoreCase(responseMsg, ResponseErrorCodeConstant.WRONG_USERNAME_VI)) { // WRONG USERNAME
						messageError = checkErrorMessage("account.does.not.exist", null, locale);
						throw new AuthenticationExceptionMockup(messageError);

					} else if (StringUtils.equalsIgnoreCase(responseMsg, ResponseErrorCodeConstant.PASSWORD_EXPIRED_VI)) { // PASSWORD EXPIRED
						// Redirect ra màn hình yêu cầu đổi mật khẩu
						isSuccessLogin = true;
						existsUserLogin = true;
					} else if (StringUtils.equalsIgnoreCase(responseMsg, ResponseErrorCodeConstant.TERMINATED_VI)) { // User is terminate
						messageError = checkErrorMessage("account.has.been.terminated", null, locale);
						throw new AuthenticationExceptionMockup(messageError);
					} else if (StringUtils.equalsIgnoreCase(responseMsg, ResponseErrorCodeConstant.DISCIPLINE_VI)) { // User is discipline
						throw new AuthenticationExceptionMockup("Discipline");
					} else if (StringUtils.isNotBlank(responseMsg)){
						throw new AuthenticationExceptionMockup(responseMsg);
					}
				}
			}
		}

		// 3. LDAP - If isSuccessLogin is false and account type is TYPE_STAFF
		if (!existsUserLogin && account != null
				&& StringUtils.equals(account.getAccountType(), ApiAccountTypeConstant.TYPE_STAFF)) {
			// check login LDAP
			isSuccessLogin = LDAPUtil.checkLogin(account.getUsername(), password, systemConfig, account.getCompanyId());
			if(!isSuccessLogin) {
				// throw new UsernameNotFoundException("B003");
				messageError = checkErrorMessage("users.ldap.login.failed", null, locale);
				throw new AuthenticationExceptionMockup(messageError);
			}
			// set existsUserLogin
			existsUserLogin = true;
		}

		// 4. Check Guest login
		if (!existsUserLogin
				&& account != null && StringUtils.equals(account.getAccountType(), ApiAccountTypeConstant.TYPE_GUEST)) {

			// set existsUserLogin
			existsUserLogin = true;

			// Authentication DB
			try {
				// checkLoginDB
				isSuccessLogin = checkLoginDB(account, password);

				if (!isSuccessLogin) {
					validateLoginFail(account, failLoginCount, timeInLocked);
				} else {
					account.setLoginDate(new Date());
					account.setLoginLock(false);
					account.setFailedLoginCount(0);
					accountService.saveJcaAccount(account);
				}

				userLocal = true;
			} catch (NoSuchAlgorithmException e) {
				logger.error("ERROR: ", e);
				// Login failed.
				throw new UsernameNotFoundException("Hệ thống đang gặp sự cố kết nối, Bạn vui lòng đăng nhập lại sau!");
			}
		}

		// If account not exists DB and not exits API
		if (account == null && accountApiDto == null) {
			// throw new UsernameNotFoundException("B003");
			messageError = checkErrorMessage("users.does.not.exits", null, locale);
			throw new AuthenticationExceptionMockup(messageError);
		}

		// Check login
		if (!isSuccessLogin) {
			// throw new UsernameNotFoundException("B003");
			messageError = checkErrorMessage("users.does.not.exits", null, locale);
			throw new AuthenticationExceptionMockup(messageError);
		}

		// Set Role
		List<GrantedAuthority> authorities = new ArrayList<>();

		// check first login or expired password
		UserAuthorityDto authorityDto = new UserAuthorityDto();
		authorityDto.setId(account.getId());

		//check face mask
		if(ObjectUtils.isNotEmpty(account) && StringUtils.isNotEmpty(account.getFaceMask())) {

			List<JcaAccount> listAccountMask = accService.getListByUserName(username);
			if(CollectionUtils.isNotEmpty(listAccountMask) && ObjectUtils.isNotEmpty(listAccountMask.get(0))) {
				JcaAccount accountMask = listAccountMask.get(0);
				authorityDto.setId(accountMask.getId());
			}
		}

		//add agent into group
		if(account != null){
			addAgentToGroup(account);
		}
		authorities = authorityService.findAuthorityDetail(authorityDto);
		// SR14148 check role of iTrust Portal
		GrantedAuthority iTrustPortalMenu = new SimpleGrantedAuthority("SCR#FE_QLKH_QLQM_ITRUST");
		String itrustFlg = null;
		if (authorities.contains(iTrustPortalMenu)) {
			itrustFlg = db2ApiService.getITrustFlag(username);
			// Khong co quyen
			if (itrustFlg == null || "0".equals(itrustFlg)) {
				authorities.remove(iTrustPortalMenu);
			}
		}
		// TODO: Set role
		authorities.add(new SimpleGrantedAuthority("ROLE_AUTHED"));
		// Build UserProfile
		UserPrincipal userProfile = accService.buildUserProfile(account, authorities);
		
		// SR17237 check BDTH role
		GrantedAuthority listApprovedByBDTHMenu = new SimpleGrantedAuthority("SCR#FE_QLTD_XDTDS");
		if (authorities.contains(listApprovedByBDTHMenu)) {
			boolean checkBDTHRole = db2ApiService.checkBDTHRole(username);
			if (checkBDTHRole) {
				// Role of account is BDTH -> not remove role of approved by BDTH
			}
			else {
				authorities.remove(listApprovedByBDTHMenu);
			}
		}
			
		// Set force change password when login successful
		userProfile.setFirstLogin(false);
		userProfile.setPasswordExpired(false);
		userProfile.setConfirmDecree(accountService.checkConfirmDecree(account.getUsername()));
		userProfile.setForceChangeGadPassword(false);
		userProfile.setResetPassword(false);
		String loginIgnogeFirst = jcaSystemConfigService.getValueByKey("LOGIN_IGNORE_FIRST", companyId);
		if (StringUtils.isEmpty(loginIgnogeFirst) || "0".equalsIgnoreCase(loginIgnogeFirst)) {
			boolean firstLogin = false;//accountPasswordService.isFirstLogin(account.getId());
			if (accountApiDto != null && StringUtils.equals("true", accountApiDto.getFirstTimeLogin())) {
				firstLogin = true;
			}
			
			if (firstLogin && StringUtils.equalsIgnoreCase(account.getAccountType(),ApiAccountTypeConstant.TYPE_AGENT)) {
				forceChangePass = true;
				userProfile.setFirstLogin(firstLogin);
			}

			// nếu account type là agency, staff và responseErorr = 001 thì set isPasswordExpired = true báo lỗi password expired
			if (accountApiDto != null
					&& (StringUtils.equalsIgnoreCase(accountApiDto.getAccountType(),ApiAccountTypeConstant.TYPE_AGENT)
							|| StringUtils.equalsIgnoreCase(accountApiDto.getAccountType(),ApiAccountTypeConstant.TYPE_CANDIDATE))
					&& StringUtils.equalsIgnoreCase(accountApiDto.getResponseMsg(),ResponseErrorCodeConstant.PASSWORD_EXPIRED_VI)) {
				userProfile.setPasswordExpired(true);
			}
			if (accountApiDto != null && StringUtils.equalsIgnoreCase(account.getAccountType(),ApiAccountTypeConstant.TYPE_AGENT)) {
				// check password gad default
				try {

					boolean forceChangeGadPassword = db2ApiService.checkAgentIsGad(account.getUsername());
					//Bug #55991
//					if(forceChangeGadPassword){
//						userProfile.setFirstLogin(false);
//					}
					userProfile.setGad(forceChangeGadPassword);
					boolean isStillDefaultGadPassword = bCryptPasswordEncoder().matches(accountApiDto.getBirthDay().replace("/", ""), account.getGadPassword());
					if (forceChangeGadPassword && isStillDefaultGadPassword) {
						userProfile.setForceChangeGadPassword(true);
					}
				} catch(Exception e) {
					logger.error("SQLException", e);
					throw new UsernameNotFoundException("Hệ thống đang gặp sự cố kết nối, Bạn vui lòng đăng nhập lại sau!");
				}
			}
			//reset password flag: account type = 2
			if(StringUtils.equalsIgnoreCase(account.getAccountType(), ApiAccountTypeConstant.TYPE_AGENT)
			&& account.getResetPassword() != null && account.getResetPassword().compareTo(BigDecimal.ONE) == 0){
				userProfile.setResetPassword(true);
			}
		}
		
		userProfile.setForceChangePass(forceChangePass);
		userProfile.setLocalAccount(userLocal);

		userProfile.setAccountId(account.getId());
		userProfile.setPhone(account.getPhone());
		userProfile.setGender(account.getGender());

		userProfile.setAccountType(account.getAccountType());
		userProfile.setUrlFacebook(account.getUrlFacebook());
		userProfile.setUrlZalo(account.getUrlZalo());
		userProfile.setFacebookFlag(account.getFacebookLogin());
		userProfile.setGoogleFlag(account.getGoogleLogin());
		userProfile.setAppleFlag(account.getAppleLogin());
//		userProfile.setFirstLogin(true);
		// set ApiToken
		if (accountApiDto != null) {
			userProfile.setApiToken(accountApiDto.getApiToken());
			userProfile.setChannel(accountApiDto.getChannel());
		}
		/** END */
		// set languge
		if (!StringUtils.isBlank(locale.toString())) {
			userProfile.setDefaultLang(locale.toString());
			userProfile.setLocale(locale);
		}
		userProfile.setDeviceId(deviceId);
		userProfile.setDeviceToken(deviceToken);
		userProfile.setFaceMask(account.getFaceMask());
		List<MenuInfo> menuInfo = authorityService.getMenuInfo(account.getId());
		if ("AD".equals(account.getChannel())) {
			if (account.getPartner() == null || !account.getPartner().contains("LPBNK")) {
				for (MenuInfo menu : menuInfo) {
					if ("MENU#FE_CCHT".equals(menu.getItemId())) {
						menu.getSubMenuList().removeIf(sub -> sub.getItemId().equals("SCR#FE_CCHT_FE_SOP"));
					}
				}
			} else {
				for (MenuInfo menu : menuInfo) {
					if ("MENU#FE_CCHT".equals(menu.getItemId())) {
						AgentInfoSaleSopDto agentInfo = db2ApiService.getAgentInfoSaleSop(account.getUsername());
						if (agentInfo == null) {
							menu.getSubMenuList().removeIf(sub -> sub.getItemId().equals("SCR#FE_CCHT_FE_SOP"));
						} else {
							userProfile.setConfirmSop(accountService.checkConfirmSop(account.getUsername()));
						}
					}
				}
			}
			boolean roleDocument = true;
			for (MenuInfo menu : menuInfo) {
				if ("MENU#FE_DOCUMENT_INFO".equals(menu.getItemId())) {
					RoleDocumentDto roleInfo = db2ApiService.getRoleDocument(account.getUsername());
					if (roleInfo == null) {
						roleDocument = false;
					} else {
						if (roleInfo.getViewDocument() == 0) {
							menu.getSubMenuList().removeIf(sub -> sub.getItemId().equals("SCREEN#FE_DOCUMENT_CLAIM"));
						}
						if (roleInfo.getShareDocument() == 0) {
							menu.getSubMenuList().removeIf(sub -> sub.getItemId().equals("SCREEN#FE_DOCUMENT_PARTNER_SHARE"));
						}
					}
				}
			}
			if (!roleDocument) {
				menuInfo.removeIf(sub -> sub.getItemId().equals("MENU#FE_DOCUMENT_INFO"));
			}
		} else {
			// Channel : AG
			if (authorities.contains(iTrustPortalMenu) && (itrustFlg == null || "0".equals(itrustFlg))) {
				for (MenuInfo menu : menuInfo) {
					if ("MENU#FE_QLKH".equals(menu.getItemId())) {
						menu.getSubMenuList().removeIf(sub -> sub.getItemId().equals("SCR#FE_QLKH_GDQM_ITRUST"));
					}
				}
			}
			//SR17237
			for (MenuInfo menu : menuInfo) {
				if ("MENU#FE_QLTD".equals(menu.getItemId())) {
					List<MenuItem> subMenuList = menu.getSubMenuList();
					MenuItem removedItem = null; 
					for (MenuItem item : subMenuList) {
						if ("SCR#FE_QLTD_XDTDS".equals(item.getItemId())) {
							if(!db2ApiService.checkBDTHRole(username)) {
								removedItem = item;
							}
						}
							
					}
					if(removedItem != null) {
						subMenuList.remove(removedItem);
					}
				}
			}
		}
		Gson gson = new Gson();
		userProfile.setMenuInfo(gson.toJson(menuInfo));
		return new UsernamePasswordAuthenticationToken(userProfile, userProfile.getPassword(), authorities);
	}

	private void checkLockedUser(Locale locale, JcaAccount account, int failLoginCount, String timeInLocked, Duration timeElapsed) {
		if (account.getFailedLoginCount() >= failLoginCount && Long.compare(timeElapsed.getSeconds(), Long.valueOf(timeInLocked) * 60) < 1) {
			long lockedTime = (Long.valueOf(timeInLocked) * 60) - timeElapsed.getSeconds();
			// check how much time account locked left
			long lockedTimeMinutes = lockedTime / 60;
			if(lockedTime >= 60){
				if(lockedTime % 60 <= 30){
					throw new AuthenticationExceptionMockup(checkErrorMessage("account.has.been.locked.minutes", new Object[] { failLoginCount, lockedTimeMinutes },
							locale));
				} else {
					throw new AuthenticationExceptionMockup(checkErrorMessage("account.has.been.locked.minutes", new Object[] { failLoginCount, lockedTimeMinutes + 1 },
							locale));
				}
			} else {
				throw new AuthenticationExceptionMockup(checkErrorMessage("account.has.been.locked.seconds", new Object[] { failLoginCount, lockedTime },
						locale));
			}
		}
	}
	private void saveAccountTeam(JcaAccount jcaAccount, String group){
		Long companyId = 2L;
		JcaTeamDto team = teamService.findByCodeAndCompanyId(group, companyId);
		JcaAccountTeam jcaAccountTeam = new JcaAccountTeam();
		jcaAccountTeam.setAccountId(jcaAccount.getId());
		jcaAccountTeam.setTeamId(team.getTeamId());
		jcaAccountTeam.setCreatedDate(new Date());
		jcaAccountTeam.setCreatedId(companyId);
		jcaAccountTeamService.saveJcaAccountTeam(jcaAccountTeam);
	}
	private void removeAccountGroup(JcaAccount jcaAccount, List<String> lstGroup){
		Long companyId = 2L;
		List<String> lstTeamIds = teamService.getListTeamIdByCode(lstGroup, companyId);
		jcaAccountTeamService.removeAccountGroup(jcaAccount, lstTeamIds);
	}
	private void addAgentToGroup(JcaAccount jcaAccount){
		//remove account from group
		List<String> lstGroup = AccountGroupEnum.getListValue();

		//type CANDIDATE
		if(StringUtils.equalsIgnoreCase(jcaAccount.getAccountType(), AccountTypeEnum.TYPE_CANDIDATE.toString())){
			removeAccountGroup(jcaAccount, lstGroup);
			saveAccountTeam(jcaAccount, AccountGroupEnum.GROUP_CANDIDATE.toString());
		}
		if(StringUtils.equalsIgnoreCase(jcaAccount.getAccountType(), AccountTypeEnum.TYPE_AGENT.toString())){
			try {
				Db2AgentDto agentDto = db2ApiService.getAgentInfoByCondition(jcaAccount.getUsername());
				removeAccountGroup(jcaAccount, lstGroup);
				if ("AD".equals(agentDto.getChannel())) {
					List<String> groups = db2ApiService.getGroupNameForAD(jcaAccount.getUsername());
					for (String item : groups) {
						String[] arrGroup = item.split(",");
						for (String group : arrGroup) {
							saveAccountTeam(jcaAccount, group);
						}
					}
				} else {
					//agent leader
					String agt = "AGT";
					if(StringUtils.equalsIgnoreCase(agentDto.getGroupType(),"AGENT") || StringUtils.equalsIgnoreCase(agentDto.getAgentType(),agt)){
						if(agentDto.getAgentLevel() > 1 && !StringUtils.equalsIgnoreCase(agentDto.getAgentGroup(),"GA")){
							saveAccountTeam(jcaAccount, AccountGroupEnum.GROUP_AGENT_LEADER.toString());
						}
						else if(agentDto.getAgentLevel() == 1) {
							saveAccountTeam(jcaAccount, AccountGroupEnum.GROUP_AGENT.toString());
						}
					}else if(StringUtils.equalsIgnoreCase(agentDto.getGroupType(),"STAFF")){
						// String[] agentTypeBd = new String[] {"SGM", "BHO"};
						// String[] agentTypeSupport = new String[] {"EMP", "SS", "SM", "Trainner"};
						// if(Arrays.stream(agentTypeBd).anyMatch(agentDto.getAgentType()::equalsIgnoreCase)){
						if (agentDto.getAgentLevel() == 4) {
							saveAccountTeam(jcaAccount, AccountGroupEnum.GROUP_BD.toString());
						}
						// if(Arrays.stream(agentTypeSupport).anyMatch(agentDto.getAgentType()::equalsIgnoreCase)){
						else if (agentDto.getAgentLevel() == 99) {
							saveAccountTeam(jcaAccount, AccountGroupEnum.GROUP_SUPPORT.toString());
						}
					}
					if(agentDto.getIsGad() == 1){
						//remove before add
						saveAccountTeam(jcaAccount, AccountGroupEnum.GROUP_GAD.toString());
					}	
				}
			} catch(Exception e) {
				logger.error("SQLException", e);
				throw new UsernameNotFoundException("Hệ thống đang gặp sự cố kết nối, Bạn vui lòng đăng nhập lại sau!");
			}
		}
	}
	private void checkTerAndDiscipline(Locale locale, JcaAccount account) {
		String messageError;
		if (StringUtils.equals(account.getAccountType(), ApiAccountTypeConstant.TYPE_AGENT)) {
			Db2AgentDto agentDto = db2ApiService.getAgentInfoByCondition(account.getUsername());
			if (agentDto != null && agentDto.getAgentStatusCode() == 0) {
				messageError = checkErrorMessage("account.has.been.terminated", null, locale);
				throw new AuthenticationExceptionMockup(messageError);
			}
			// xet agent còn vi phạm có hiệu lực hay không
			int blockLevel = db2ApiService.getAgentDiscipline(account.getUsername());
			if (blockLevel == 1 || blockLevel == 2) {
				throw new AuthenticationExceptionMockup("Discipline");
			}
		}
	}

	private Duration getDurationBetween(JcaAccount account) {
		Instant start = Optional.ofNullable(account.getLoginDate()).orElse(new Date()).toInstant(); ;
		Instant end = Instant.now();
		return Duration.between(start, end);
	}

	private void validateLoginFail(JcaAccount account, int failLoginCount, String lockedTimeMinutes) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		Duration timeElapsed = getDurationBetween(account);
        if (Long.compare(timeElapsed.getSeconds(), Long.valueOf(lockedTimeMinutes) * 60) > 0) {
            account.setFailedLoginCount(1);
        } else {
            account.setFailedLoginCount(Optional.ofNullable(account.getFailedLoginCount()).orElse(0) + 1);
        }
		
		if (account.getFailedLoginCount() < failLoginCount) {
			account.setLoginDate(new Date());
			account.setLoginLock(account.isLoginLock());
			accountService.saveJcaAccount(account);
			String messageError = checkErrorMessage("password.is.incorrect.lock", new Object[] { failLoginCount,
					(failLoginCount - Optional.ofNullable(account.getFailedLoginCount()).orElse(0)) }, locale);
			throw new AuthenticationExceptionMockup(messageError);
		} else {
			account.setLoginDate(new Date());
			account.setLoginLock(true);
			accountService.saveJcaAccount(account);
			throw new AuthenticationExceptionMockup(checkErrorMessage("account.has.been.locked.minutes", new Object[] { failLoginCount, lockedTimeMinutes },
						locale));
			}
		}

	/**
	 *
	 * @param account
	 * @param failLoginCount
	 * @param timeInLocked
	 * @param locale
	 */
	private void showErrorWrongPassword(JcaAccount account, int failLoginCount, String timeInLocked, Locale locale) {
		String messageError = StringUtils.EMPTY;

		if (account.getFailedLoginCount() < failLoginCount) {
			messageError = checkErrorMessage("password.is.incorrect.lock", new Object[] { failLoginCount,
					(failLoginCount - Optional.ofNullable(account.getFailedLoginCount()).orElse(0)) }, locale);
			throw new AuthenticationExceptionMockup(messageError);
		} else {
			// Check lock user
			Duration timeElapsed = this.getDurationBetween(account);
			checkLockedUser(locale, account, failLoginCount, timeInLocked, timeElapsed);
		}
	}
	public void updateAccount(JcaAccount account, AccountApiDto accountApiDto, String deviceToken, boolean isWebapp) {
//		String compareStr1 = Arrays.stream(account.getClass().getDeclaredFields()).reduce("", (a,b) -> a + b.get(b.getName()));
		account.setUsername(Optional.ofNullable(accountApiDto.getUserId()).orElse(account.getUsername()));
		account.setEmail(accountApiDto.getContactEmail());
		account.setAccountType(accountApiDto.getAccountType());
		account.setFullname(accountApiDto.getUsername());
		account.setDepartment(accountApiDto.getDepartment());
		account.setPhone(accountApiDto.getCellPhone());
		account.setGender(accountApiDto.getGender());
		account.setPassword(bCryptPasswordEncoder().encode(accountApiDto.getPassword()));
		
		if (StringUtils.equals("true", accountApiDto.getFirstTimeLogin())) {
			if (!StringUtils.isBlank(accountApiDto.getPasswordGad())) {
				account.setGadPassword(bCryptPasswordEncoder().encode(accountApiDto.getPasswordGad()));
			}
		}
		account.setCompanyId(CommonConstant.COMPANY_DEFAULT);
		account.setActived(true);
		account.setEnabled(true);
//		account.setLoginLock(false);
		try{
			account.setBirthday(new SimpleDateFormat("dd/MM/yyyy").parse(accountApiDto.getBirthDay()));
		} catch (Exception e){
			account.setBirthday(null);
		}
		//save device_token
		if(!isWebapp && deviceToken != null){
			account.setDeviceTokenMobile(deviceToken);
		}
		if ("AD".equals(account.getChannel())) {
			String partner = db2ApiService.getPartnerByAgentCode(account.getUsername());
			account.setPartner(partner);
		}
	}
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	/**
	 * checkLoginDB
	 *
	 * @param account  type Account
	 * @param password type String
	 * @return boolean
	 * @author vunt
	 */
	public boolean checkLoginDB(JcaAccount account, String password) throws NoSuchAlgorithmException {
		return bCryptPasswordEncoder().matches(password, account.getPassword());
	}

	public String checkErrorMessage(String codeError, Object[] param, Locale locale) {
		String messageError = messageSource.getMessage(codeError,param,locale);

		return messageError;
	}
}
