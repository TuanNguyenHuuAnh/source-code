/*******************************************************************************
 * Class        ：AuthenRest
 * Created date ：2020/11/30
 * Lasted date  ：2024/05/07
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 * Change log   ：2024/03/22	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 * Change log   ：2024/05/07 nt.tinh SR16681 - SAM Quyền xem danh sách hoạt động của admin user chữ
 ******************************************************************************/
package vn.com.unit.ep2p.rest.authen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.module.account.dto.ConditionTable;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetail;
import vn.com.unit.cms.core.module.candidate.dto.ProfileCandidateDetailDto;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JwtTokenInfo;
import vn.com.unit.core.dto.JwtTokenValidate;
import vn.com.unit.core.dto.MenuInfo;
import vn.com.unit.core.dto.MenuItem;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.security.UserPrincipal;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.security.jwt.JwtToken;
import vn.com.unit.core.security.jwt.TokenProvider;
import vn.com.unit.core.service.AuthorityService;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.dto.AgentInfoSaleSopDto;
import vn.com.unit.ep2p.admin.dto.ConfirmDecreeDto;
import vn.com.unit.ep2p.admin.dto.RoleDocumentDto;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.constant.ApiAccountTypeConstant;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.dto.req.AuthenLoginReq;
import vn.com.unit.ep2p.dto.req.FormCaptchaReq;
import vn.com.unit.ep2p.dto.req.RefreshTokenRes;
import vn.com.unit.ep2p.dto.res.AccountLoginRes;
import vn.com.unit.ep2p.dto.res.AuthenLoginInfoRes;
import vn.com.unit.ep2p.dto.res.AuthenLoginRes;
import vn.com.unit.ep2p.dto.res.CheckCaptchaRes;
import vn.com.unit.ep2p.ers.dto.CustomUsernamePasswordAuthentication;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.CandidateService;
import vn.com.unit.ep2p.service.ReCaptchaService;
import vn.com.unit.ep2p.utils.LangugeUtil;
import vn.com.unit.ep2p.utils.RestUtil;

/**
 * AuthenRest
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 * @Last updated: 2024/05/07 nt.tinh SR16681 - SAM Quyền xem danh sách hoạt động của admin user chữ
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_AUTHEN)
@Api(tags = { AppApiConstant.API_AUTHEN_AUTHENTICAITON_DESCR })
public class AuthenRest extends AbstractRest {
	@Autowired
	private TokenProvider tokenProvider;

	@Qualifier("customAuthenticationApiProvider")
	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Autowired
	CandidateService candidateService;

	@Autowired
	ApiAgentDetailService apiAgentDetailService;

	@Autowired
	private ConstantDisplayService constantDisplayService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private Db2ApiService db2ApiService;
	
	@Autowired 
	private AccountService accountService ; 
	
	@Autowired
	private ReCaptchaService reCaptchaService;
	
	@SuppressWarnings("deprecation")
	@PostMapping(AppApiConstant.API_AUTHEN_AUTHENTICAITON + AppApiConstant.API_AUTHEN_AUTHENTICAITON_LOGIN)
	@ApiOperation("Login to your account to provide tokens to the system")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 401700, message = "Username is required."),
			@ApiResponse(code = 401701, message = "Password is required."),
			@ApiResponse(code = 401702, message = "Username does not exist."),
			@ApiResponse(code = 401703, message = "The system is required."),
			@ApiResponse(code = 401704, message = "The system does not exist."),
			@ApiResponse(code = 401705, message = "The system expires. Please contact the administrator to support."),
			@ApiResponse(code = 401706, message = "Account locked."),
			@ApiResponse(code = 401707, message = "Account deactivated."),
			@ApiResponse(code = 401708, message = "Login failed."),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
	public DtsApiResponse authenticationToLogin(@RequestBody AuthenLoginReq auLogin) {
		long start = System.currentTimeMillis();
		this.trimPropertiesLogin(auLogin);
		/** BEGIN Authentication */
		try {
			String userName = auLogin.getUsername();
			String password = auLogin.getPassword();
			Boolean rememberMe = auLogin.getRememberMe();
			
			if (auLogin.getValueToken() != null) {
				CheckCaptchaRes captchaRes = reCaptchaService.verifyCaptcha(userName, auLogin.getValueToken());
				if (captchaRes != null) {
					return this.successHandler.handlerSuccess(captchaRes, start, auLogin.getUsername(), null);
				}	
			}
			
			CustomUsernamePasswordAuthentication customAuthenticationToken = new CustomUsernamePasswordAuthentication(userName, password, auLogin.getDeviceId(), auLogin.getNotiToken(), auLogin.getValueToken(), true);
			Authentication authentication = authenticationProvider.authenticate(customAuthenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			boolean isRememberMe = Optional.ofNullable(rememberMe).orElse(Boolean.FALSE);
			String jwt = tokenProvider.createToken(authentication, isRememberMe);

			Object user = authentication.getPrincipal();
			ModelMapper mapper = new ModelMapper();
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			AccountLoginRes userDTO = mapper.map(user, AccountLoginRes.class);
			String accessToken = new JwtToken(jwt).getIdToken();
			authorityService.insertToken(accessToken);
			userDTO.setAccessToken(accessToken);
			userDTO.setTokenType("Bearer");
			userDTO.setEncodeAuthorities(RestUtil.encrypt(userDTO.getAuthorities()));
			try {
				String expiredTimeNumber = systemConfig.getConfig(SystemConfig.EXPIRED_TIME_NUMBER);
				if (StringUtils.isEmpty(expiredTimeNumber) || expiredTimeNumber.length() > 7) {
					userDTO.setExpiredTimeNumber(1800000);
				} else {
					userDTO.setExpiredTimeNumber(Integer.parseInt(expiredTimeNumber));
				}
			} catch (Exception e) {
				System.out.print(e);
			}

			return this.successHandler.handlerSuccess(userDTO, start, auLogin.getUsername(), null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, auLogin.getUsername(), null);
		}
		/** END */
	}

	@PostMapping(AppApiConstant.API_AUTHEN_AUTHENTICAITON + AppApiConstant.API_AUTHEN_AUTHENTICAITON_LOGIN_MOBILE)
	@ApiOperation("Login to your account to provide tokens to the system")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 401700, message = "Username is required."),
			@ApiResponse(code = 401701, message = "Password is required."),
			@ApiResponse(code = 401702, message = "Username does not exist."),
			@ApiResponse(code = 401703, message = "The system is required."),
			@ApiResponse(code = 401704, message = "The system does not exist."),
			@ApiResponse(code = 401705, message = "The system expires. Please contact the administrator to support."),
			@ApiResponse(code = 401706, message = "Account locked."),
			@ApiResponse(code = 401707, message = "Account deactivated."),
			@ApiResponse(code = 401708, message = "Login failed."),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
	public DtsApiResponse authenticationToLoginForMobile(@RequestBody AuthenLoginReq auLogin) {
		long start = System.currentTimeMillis();
		this.trimPropertiesLogin(auLogin);
		/** BEGIN Authentication */
		try {

			String userName = auLogin.getUsername();
			String password = auLogin.getPassword();
			// login by facebook or google or apply id
			if (StringUtils.isEmpty(userName)) {
				password = auLogin.getTokenFirebase();
			}
			Boolean rememberMe = auLogin.getRememberMe();
			CustomUsernamePasswordAuthentication customAuthenticationToken = new CustomUsernamePasswordAuthentication(userName, password, auLogin.getDeviceId(), auLogin.getNotiToken(), auLogin.getValueToken(), false);

			Authentication authentication = authenticationProvider.authenticate(customAuthenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			boolean isRememberMe = Optional.ofNullable(rememberMe).orElse(Boolean.FALSE);
			String jwt = tokenProvider.createToken(authentication, isRememberMe);

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(AppApiConstant.JWT_AUTHORIZATION, "Bearer " + jwt);

			Object user = authentication.getPrincipal();
			ModelMapper mapper = new ModelMapper();
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			AuthenLoginRes userDTO = mapper.map(user, AuthenLoginRes.class);

			String accessToken = new JwtToken(jwt).getIdToken();
			authorityService.insertToken(accessToken);
			userDTO.setAccessToken(accessToken);
			userDTO.setTokenType("Bearer");
			userDTO.setExpired(tokenProvider.getExpired());

			userDTO.setRefreshToken(createRefreshToken(authentication, isRememberMe));
			userDTO.setConfirmDecree(accountService.checkConfirmDecree(userName));
			try {
				String expiredTimeNumber = systemConfig.getConfig(SystemConfig.EXPIRED_TIME_NUMBER);
				if (StringUtils.isEmpty(expiredTimeNumber) || expiredTimeNumber.length() > 7) {
					userDTO.setExpiredTimeNumber(1800000);
				} else {
					userDTO.setExpiredTimeNumber(Integer.parseInt(expiredTimeNumber));
				}
			} catch (Exception e) {
				System.out.print(e);
			}
			return this.successHandler.handlerSuccess(userDTO, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, auLogin.getUsername(), null);
		}
		/** END */
	}
	
	private String createRefreshToken(Authentication authentication, boolean requireTracking) {
		String idRefreshToken = "";
		JwtTokenInfo refreshToken = tokenProvider.createRefreshToken(authentication, requireTracking);
		if (null != refreshToken) {
			idRefreshToken = refreshToken.getToken();
		}
//        if (requireTracking) {
//            userPrincipalService.trackingRefreshToken((UserPrincipal) authentication.getPrincipal(), refreshToken);
//        }
		return idRefreshToken;
	}

	private void trimPropertiesLogin(AuthenLoginReq auLogin) {
		if (CommonStringUtil.isNotBlank(auLogin.getSystemCode())) {
			auLogin.setSystemCode(auLogin.getSystemCode().trim());
		}
		if (CommonStringUtil.isNotBlank(auLogin.getUsername())) {
			auLogin.setUsername(auLogin.getUsername().trim());
		}
		if (CommonStringUtil.isNotBlank(auLogin.getPassword())) {
			auLogin.setPassword(auLogin.getPassword().trim());
		}
	}

	/**
	 * {@code GET  /account} : get the current user.
	 *
	 * @return the current user.
	 * @throws RuntimeException {@code 500 (Internal Server Error)} if the user
	 *                          couldn't be returned.
	 */
	@GetMapping("/account")
	public DtsApiResponse getAccount() {
		long start = System.currentTimeMillis();
		UserPrincipal userPrincipal = UserProfileUtils.getUserPrincipal();

		try {
			AuthenLoginInfoRes userDTO = new AuthenLoginInfoRes();

			// set Data
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			userDTO.setUsername(StringUtils.isNotBlank(userPrincipal.getUsername()) ? userPrincipal.getUsername() : "");
			userDTO.setFullname(StringUtils.isNotBlank(userPrincipal.getFullname()) ? userPrincipal.getFullname() : "");
			userDTO.setEmail(StringUtils.isNotBlank(userPrincipal.getEmail()) ? userPrincipal.getEmail() : "");
			userDTO.setBirthday(userPrincipal.getBirthday());
			userDTO.setPhone(StringUtils.isNotBlank(userPrincipal.getPhone()) ? userPrincipal.getPhone() : "");
			userDTO.setAvatar(StringUtils.isNotBlank(userPrincipal.getAvatar()) ? userPrincipal.getAvatar() : "");
			userDTO.setGender(StringUtils.isNotBlank(userPrincipal.getGender()) ? userPrincipal.getGender() : "");
			userDTO.setAccountType(
					StringUtils.isNotBlank(userPrincipal.getAccountType()) ? userPrincipal.getAccountType() : "");
			userDTO.setGoogleFlag(userPrincipal.getGoogleFlag());
			userDTO.setFacebookFlag(userPrincipal.getFacebookFlag());
			userDTO.setAppleFlag(userPrincipal.getAppleFlag());
			userDTO.setUrlFacebook(userPrincipal.getUrlFacebook());
			userDTO.setUrlZalo(userPrincipal.getUrlZalo());
			userDTO.setFaceMask(userPrincipal.getFaceMask());
			
			GrantedAuthority iTrustPartnerMenu = new SimpleGrantedAuthority("SCR#FE_QLKH_QLQM_ITRUST");
			String itrustFlg = null;
			if (userPrincipal.getAuthorities().contains(iTrustPartnerMenu)) {
				itrustFlg = db2ApiService.getITrustFlag(userDTO.getUsername());
				// Khong co quyen
				if (itrustFlg == null || "0".equals(itrustFlg)) {
					userPrincipal.getAuthorities().remove(iTrustPartnerMenu);
				}
			}
			// SR17237 check BDTH role
			GrantedAuthority listApprovedByBDTHMenu = new SimpleGrantedAuthority("SCR#FE_QLTD_XDTDS");
			if (userPrincipal.getAuthorities().contains(listApprovedByBDTHMenu)) {
				boolean checkBDTHRole = db2ApiService.checkBDTHRole(userDTO.getUsername());
				if (checkBDTHRole) {
					// Role of account is BDTH -> not remove role of approved by BDTH
				}
				else {
					userPrincipal.getAuthorities().remove(listApprovedByBDTHMenu);
				}
			}
			String strAuten = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(","));
			if (StringUtils.isNotBlank(strAuten)) {
				if (userDTO.getAuthorities() == null) {
					userDTO.setAuthorities(new ArrayList<>());
				}
				CollectionUtils.addAll(userDTO.getAuthorities(), strAuten.split(","));
			}

			// check role
			String role = userPrincipal.getAccountType();
			if (StringUtils.isBlank(role)) {
				role = ApiAccountTypeConstant.GUEST;

				JcaConstantDto cons = constantDisplayService.getJcaConstantDtoByCodeAndGroupCodeAndKind(role,
						"JCA_ACCOUNT", "ACCOUNT_TYPE", "EN");
				if (cons != null) {
					role = cons.getName();
				}
			}
			userDTO.setRole(role);
			CmsAgentDetail resObj = new CmsAgentDetail();
			ProfileCandidateDetailDto candidate = new ProfileCandidateDetailDto();
			try {
				List<String> listAgent = new ArrayList<>();
				listAgent.add("2");
				listAgent.add("5");
				List<String> listCandidate = new ArrayList<>();
				listCandidate.add("3");
				listCandidate.add("6");
				if (listAgent.contains(userDTO.getAccountType()))
					resObj = apiAgentDetailService.getCmsAgentDetailByUsername(userDTO.getUsername());
				if (listCandidate.contains(userDTO.getAccountType()))
					candidate = candidateService.getProfileCandidateDetail(userDTO.getUsername());
			} catch (Exception e) {
				System.out.print(e);
			}
			userDTO.setAgentInfomation(resObj);
			userDTO.setCanidateInfomation(candidate);
			JcaAccount account = accountService.findById(userPrincipal.getAccountId());
			if (account != null) {
				userDTO.setChannel(account.getChannel());
			}
			List<MenuInfo> menuInfo = authorityService.getMenuInfo(userPrincipal.getAccountId());
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
				if (userPrincipal.getAuthorities().contains(iTrustPartnerMenu) && (itrustFlg == null || "0".equals(itrustFlg))) {
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
								if(!db2ApiService.checkBDTHRole(userDTO.getUsername())) {
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
			userDTO.setMenuInfo(gson.toJson(menuInfo));
			return this.successHandler.handlerSuccess(userDTO, start, userDTO.getUsername());
		} catch (Exception e) {
			return this.errorHandler.handlerException(e, start, null);
		}
	}

	@GetMapping("/authorities")
	public DtsApiResponse getAuthorities() {
		long start = System.currentTimeMillis();
		return this.successHandler.handlerSuccess(getListAuthorities(UserProfileUtils.getAuthentication()), start);
	}

	private List<String> getListAuthorities(Authentication authentication) {
		List<String> reulst = new ArrayList<>();

		String strAuten = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		if (StringUtils.isNotBlank(strAuten)) {
			CollectionUtils.addAll(reulst, strAuten.split(","));
		}

		return reulst;
	}

	@PostMapping("/refresh-token")
	@ApiOperation("Used to get tokens back when timeout")
	public DtsApiResponse getRefreshToken(@RequestBody RefreshTokenRes refreshToken, HttpServletRequest request) {
		long start = System.currentTimeMillis();

		try {
			String tokenRequest = refreshToken.getRefreshToken();
			JwtTokenValidate checkToken = tokenProvider.validateToken(tokenRequest);
			if (checkToken.isValid()) {
				tokenProvider.getKey();
			}

			boolean rememberMe = false;
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			Authentication authentication = tokenProvider.getAuthenticationByRefreshToken(tokenRequest, locale);

			SecurityContextHolder.getContext().setAuthentication(authentication);
			boolean isRememberMe = Optional.ofNullable(rememberMe).orElse(Boolean.FALSE);
			String jwt = tokenProvider.createToken(authentication, isRememberMe);

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(AppApiConstant.JWT_AUTHORIZATION, "Bearer " + jwt);

			Object user = authentication.getPrincipal();
			ModelMapper mapper = new ModelMapper();
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			AuthenLoginRes userDTO = mapper.map(user, AuthenLoginRes.class);

			JwtToken token = new JwtToken(jwt);
			userDTO.setAccessToken(token.getIdToken());
			userDTO.setTokenType("Bearer");
			userDTO.setExpired(tokenProvider.getExpired());
			userDTO.setRefreshToken(createRefreshToken(authentication, isRememberMe));

			return this.successHandler.handlerSuccess(userDTO, start);
		} catch (Exception e) {
			return this.errorHandler.handlerException(e, start);
		}
	}

	@PostMapping(AppApiConstant.API_AUTHEN_AUTHENTICAITON + AppApiConstant.API_JWT_LOGIN)
	@ApiOperation("Login to your account to provide tokens to the system")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 401700, message = "Username is required."),
			@ApiResponse(code = 401701, message = "Password is required."),
			@ApiResponse(code = 401702, message = "Username does not exist."),
			@ApiResponse(code = 401703, message = "The system is required."),
			@ApiResponse(code = 401704, message = "The system does not exist."),
			@ApiResponse(code = 401705, message = "The system expires. Please contact the administrator to support."),
			@ApiResponse(code = 401706, message = "Account locked."),
			@ApiResponse(code = 401707, message = "Account deactivated."),
			@ApiResponse(code = 401708, message = "Login failed."),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
	public DtsApiResponse authenticationToLogin(HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer isFistLogin,@RequestParam(defaultValue = "") String token) {
		long start = System.currentTimeMillis();
		/** BEGIN Authentication */
		try {
			request.setAttribute("Authorization", token);
			String authorizationHeaderValue = request.getHeader("Authorization");
			Object user = UserProfileUtils.getUserPrincipal();
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			String tokens = CommonBase64Util.encode(token);
			ModelMapper mapper = new ModelMapper();
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			AccountLoginRes userDTO = mapper.map(user, AccountLoginRes.class);
			userDTO.setFirstLogin(isFistLogin == 1);
//            userDTO.setAuthentication(new JwtToken(jwt));
			userDTO.setAccessToken(new JwtToken(authorizationHeaderValue).getIdToken());
			userDTO.setTokenType("Bearer");
			userDTO.setConfirmDecree(accountService.checkConfirmDecree(userDTO.getUsername()));
			userDTO.setGad(db2ApiService.checkAgentIsGad(userDTO.getUsername()));
			
			// SR15277
			if ("AG".equals(userDTO.getChannel()) &&
					(StringUtils.equalsIgnoreCase(userDTO.getAccountType(),ApiAccountTypeConstant.TYPE_AGENT)
					|| StringUtils.equalsIgnoreCase(userDTO.getAccountType(),ApiAccountTypeConstant.TYPE_CANDIDATE))) {
				userDTO.setPasswordExpired(RetrofitUtils.checkPasswordExpired(userDTO.getAccessToken(), userDTO.getUsername()));
			}
			
			JcaAccount account = accountService.findById(Long.parseLong(userDTO.getAccountId()));
			if (account != null) {
				userDTO.setChannel(account.getChannel());	
			}
			List<MenuInfo> menuInfo = authorityService.getMenuInfo(Long.parseLong(userDTO.getAccountId()));
			Gson gson = new Gson();
			userDTO.setMenuInfo(gson.toJson(menuInfo));
			return this.successHandler.handlerSuccess(userDTO, start, authorizationHeaderValue, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, token, null);
		}
		/** END */
	}

	@PostMapping("check-item-by-user-login")
	public DtsApiResponse checkDataByCondition(@RequestBody ConditionTable condition) {
		long start = System.currentTimeMillis();
		try {
			if (StringUtils.isEmpty(condition.getAgentCode())) {
				condition.setAgentCode(condition.getUsername());
			}
			return this.successHandler.handlerSuccess(apiAgentDetailService.checkDataByCondition(condition), start,
					condition.getAgentCode(), null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@PostMapping("/confirmDecree")
	@ApiOperation("update user had confirm online-security")
	public DtsApiResponse saveConfirm(@RequestBody ConfirmDecreeDto confirmDto){
		long start = System.currentTimeMillis();
		try {
			return this.successHandler.handlerSuccess(accountService.saveConfirmDecree(confirmDto), start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@PostMapping("/logout")
	public DtsApiResponse loggingOut(HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
			//handle logging out
			String authHeader = request.getHeader("Authorization");
			if(StringUtils.isNotBlank(authHeader)){
				String token = authHeader.replace("Bearer ", "");
				tokenProvider.destroyToken(token);
			}
			return this.successHandler.handlerSuccess(null, start,
					null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	@PostMapping("/user-login-info")
	public DtsApiResponse getUserLoginInfo(HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
			String authorization = request.getHeader("Authorization");
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Object user = authentication.getPrincipal();
			ModelMapper mapper = new ModelMapper();
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			AccountLoginRes userDTO = mapper.map(user, AccountLoginRes.class);
			userDTO.setAccessToken(authorization.replace("Bearer ", ""));
			return this.successHandler.handlerSuccess(userDTO, start,
					null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@PostMapping("/recaptcha/generate")
	@ApiOperation("Login to your account to provide tokens to the system")
	public DtsApiResponse generateCaptcha(@RequestBody FormCaptchaReq request) {
		long start = System.currentTimeMillis();
        try {
        	String captcha = CmsUtils.genCaptcha(6);
        	reCaptchaService.createCaptcha(request.getUserId(), captcha);
            return this.successHandler.handlerSuccess(CommonBase64Util.encode(captcha), start, null, null);
        } catch (Exception ex) {
        	return this.errorHandler.handlerException(ex, start, null, null);
        }
	}
}
