/*******************************************************************************
 * Class        ：AccountRest
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.common.validator.CommonValidator;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaAccountRegister;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.entity.AccountSms;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.utils.NullAwareBeanUtils;
import vn.com.unit.ep2p.dto.req.*;
import vn.com.unit.ep2p.dto.res.AccountInfoRes;
import vn.com.unit.ep2p.dto.res.CheckCaptchaRes;
import vn.com.unit.ep2p.dto.res.ForgotPasswordByAgentCodeRes;
import vn.com.unit.ep2p.dto.res.RegisterAccountRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiAccountService;
import vn.com.unit.ep2p.service.ReCaptchaService;
import vn.com.unit.ep2p.utils.AppStringUtils;
import vn.com.unit.ep2p.utils.LangugeUtil;

/**
 * AccountRest
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_ACCOUNT_DESCR })
public class AccountRest extends AbstractRest {
   
    @Autowired
    private ApiAccountService apiAccountService;
    @Autowired
    private AccountService accountService;
    
    @Autowired
	private ReCaptchaService reCaptchaService;
    
    @GetMapping(AppApiConstant.API_ADMIN_ACCOUNT + "/detail")
    @ApiOperation("Detail of account")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402805, message = "Error process get account information detail"),
            @ApiResponse(code = 402806, message = "Error account not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse detailAccountByUsername(
            @ApiParam(name = "userId", value = "Get account information detail on system by username", example = "username") @RequestParam("username") String username) {
        long start = System.currentTimeMillis();
        try {
            AccountInfoRes resObj = apiAccountService.getAccountInfoResByUsername(username);
            return this.successHandler.handlerSuccess(resObj, start, username);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null);
        }
    }

    @GetMapping(AppApiConstant.API_ADMIN_ACCOUNT + "/check-agent-exist")
    @ApiOperation("Detail of account")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402805, message = "Error process get account information detail"),
            @ApiResponse(code = 402806, message = "Error account not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse checkAgentExist(
            @ApiParam(name = "agentCode", value = "check Agent exist ion ODS", example = "agentCode") @RequestParam("agentCode") String agentCode) {
        long start = System.currentTimeMillis();
        try {
            Db2AgentDto resObj = apiAccountService.checkAgentExist(agentCode);
            return this.successHandler.handlerSuccess(resObj, start, agentCode);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null);
        }
    }

    @PostMapping(AppApiConstant.API_ADMIN_ACCOUNT +"/change-password")
    @ApiOperation("Change password account")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402806, message = "Error account not found"),
            @ApiResponse(code = 402807, message = "Error account change password"),
            @ApiResponse(code = 402808, message = "Error password old incorrect"),
            @ApiResponse(code = 402809, message = "Error confirm password new incorrect"),
            @ApiResponse(code = 500, message = "Internal server error"),
            })
    public DtsApiResponse changePasswordAccount(
            @ApiParam(name = "body", value = "Account information to change password") @RequestBody AccountChangePasswordReq accountChangePasswordReq) {
        long start = System.currentTimeMillis();
        try {
        	if (accountChangePasswordReq.getPasswordNew().length() < 8
        			|| !AppStringUtils.checkByRegex(accountChangePasswordReq.getPasswordNew(), "(?=.*[a-z])")
        			|| !AppStringUtils.checkByRegex(accountChangePasswordReq.getPasswordNew(), "(?=.*[A-Z])")
        			|| !AppStringUtils.checkByRegex(accountChangePasswordReq.getPasswordNew(), "(?=.*[0-9])")) {
        		return this.errorHandler.handlerException(new Exception("Mật khẩu không hợp lệ, vui lòng nhập lại"), start, null);
        	}
    		Long userId = UserProfileUtils.getAccountId();
        	accountChangePasswordReq.setUserId(accountChangePasswordReq.getUserId() != null ? accountChangePasswordReq.getUserId() : userId);
            apiAccountService.changePassword(accountChangePasswordReq);
            return this.successHandler.handlerSuccess(null, start, userId);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null);
        }
    }

    @GetMapping(AppApiConstant.API_ADMIN_ACCOUNT + "/enums")
    @ApiOperation("Update account")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402803, message = "Error process update info account"),
            @ApiResponse(code = 402810, message = "Error username is duplicated"),
            @ApiResponse(code = 402813, message = "Error email is not in the correct format [Example: abc@gmail.com]"),
            @ApiResponse(code = 402811, message = "Error email is duplicated"),
            @ApiResponse(code = 402812, message = "Error phone is not in the correct format [Example: 1234567890 hoặc 123-456-7890 hoặc (123)456-7890 hoặc (123)4567890]"),
            @ApiResponse(code = 402806, message = "Error account not found"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse getEnumsSearch() {
        long start = System.currentTimeMillis();
        try {
            List<EnumsParamSearchRes> results = apiAccountService.getListEnumSearch();
            return this.successHandler.handlerSuccess(results, start, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null);
        }
    }
    
    @PostMapping(AppApiConstant.API_ADMIN_ACCOUNT + "/register")
    @ApiOperation("Register account")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4026100, message = "Email is exists"),
            @ApiResponse(code = 4026101, message = "Phone is exists"),
            @ApiResponse(code = 4026102, message = "Email is required or Phone is required"),
            })
    public DtsApiResponse register(@RequestBody RegisterAccountReq registerAccountReq) {
        long start = System.currentTimeMillis();

        try {
            // validation
            CommonValidator.validate(registerAccountReq);
            
            String key = registerAccountReq.getEmail();
        	CheckCaptchaRes captchaRes = reCaptchaService.verifyCaptcha(key, registerAccountReq.getValueToken());
			if (captchaRes != null) {
				return this.successHandler.handlerSuccess(captchaRes, start, key, null);
			}
            RegisterAccountRes res = apiAccountService.create(registerAccountReq);
            
            return this.successHandler.handlerSuccess(res, start, registerAccountReq.getEmail());
        } catch (Exception e) {
            return this.errorHandler.handlerException(e, start, null);
        }
    }
    @PostMapping(AppApiConstant.API_ADMIN_ACCOUNT + "/check-duplicate-candidate")
    @ApiOperation("Check duplicate candidate")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4026100, message = "Email is exists"),
            @ApiResponse(code = 4026101, message = "Phone is exists"),
            @ApiResponse(code = 4026102, message = "Email is required or Phone is required"),
    })
    public DtsApiResponse checkDuplicateCandidate(@RequestBody RegisterAccountReq registerAccountReq) {
        long start = System.currentTimeMillis();
        RegisterAccountRes res = new RegisterAccountRes();
        try {

            // validation
            CommonValidator.validate(registerAccountReq);
            if (registerAccountReq.isConfirmApplyCandidate()) {
                res = apiAccountService.getAccountCandidateByEmail(registerAccountReq.getEmail(), registerAccountReq.getFullname(), registerAccountReq.getPhone());
            }

            return this.successHandler.handlerSuccess(res, start, registerAccountReq.getEmail());
        } catch (Exception e) {
            return this.errorHandler.handlerException(e, start, null);
        }
    }

    @PostMapping(AppApiConstant.API_ADMIN_ACCOUNT + "/forgot-password/{agentCode}")
    @ApiOperation("Forgot password for account")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse forgotPassword(@PathVariable(value = "agentCode") String agentCode,
    		@RequestBody ForgotPasswordReq forgotPasswordReq) {
        long start = System.currentTimeMillis();

        try {
        	CheckCaptchaRes captchaRes = reCaptchaService.verifyCaptcha(agentCode, forgotPasswordReq.getValueToken());
			if (captchaRes != null) {
				return this.successHandler.handlerSuccess(captchaRes, start, agentCode, null);
			}
            ForgotPasswordByAgentCodeRes res = apiAccountService.forgotPasswordByAgentCode(agentCode);

            return this.successHandler.handlerSuccess(res, start, agentCode);
        } catch (Exception e) {
            return this.errorHandler.handlerException(e, start, null);
        }
    }
    
    @PostMapping(AppApiConstant.API_ADMIN_ACCOUNT + "/forgot-password")
    @ApiOperation("Send OTP for email/phone")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse forgotPassword(@RequestBody ForgotPasswordReq forgotPasswordReq) {
        long start = System.currentTimeMillis();

        try {
        	/*String key = forgotPasswordReq.getAgentCode();
        	if (StringUtils.isNotEmpty(forgotPasswordReq.getEmailPersonal())) {
        		key = forgotPasswordReq.getEmailPersonal();
        	} else if (StringUtils.isNotEmpty(forgotPasswordReq.getEmailDlvn())) {
        		key = forgotPasswordReq.getEmailDlvn();
        	} else if (StringUtils.isNotEmpty(forgotPasswordReq.getPhoneNumber())) {
        		key = forgotPasswordReq.getPhoneNumber();
        	}
        	CheckCaptchaRes captchaRes = reCaptchaService.verifyCaptcha(key, forgotPasswordReq.getValueToken());
			if (captchaRes != null) {
				return this.successHandler.handlerSuccess(captchaRes, start, key, null);
			}*/
            AccountSms accountSms = apiAccountService.forgotPassword(forgotPasswordReq);
            return this.successHandler.handlerSuccess(accountSms, start, accountSms.getAccountId());
        } catch (Exception e) {
            return this.errorHandler.handlerException(e, start, null);
        }
    }
    
    @PostMapping(AppApiConstant.API_ADMIN_ACCOUNT + "/renew-password")
    @ApiOperation("Renew password")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse forgotPasswordRenewByOtp(@RequestBody RenewPasswordReq forgotPasswordRenewReq) {
        long start = System.currentTimeMillis();

        try {
            String result = apiAccountService.renewPasswordByOtp(forgotPasswordRenewReq);
            return this.successHandler.handlerSuccess(result, start, null);
        } catch (Exception e) {
            return this.errorHandler.handlerException(e, start, null);
        }
    }

    @GetMapping(AppApiConstant.API_ADMIN_ACCOUNT + "/first-login")
    @ApiOperation("Check first login")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse isFirstLogin(@RequestParam("userId") Long userId) {
        long start = System.currentTimeMillis();

        try {
            return this.successHandler.handlerSuccess(apiAccountService.isFirstLogin(userId), start, userId);
        } catch (Exception e) {
            return this.errorHandler.handlerException(e, start, null);
        }
    }
    @PostMapping(AppApiConstant.API_APP_UPLOAD_AVATAR)
	public DtsApiResponse updateAccountAvatar(
            @RequestParam(required = false, name = "file") MultipartFile lstFile, UpdateAccountRest account , HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
        	Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	Long userId = UserProfileUtils.getAccountId();
        	JcaAccountDto acc = accountService.updateAccountAvatar(userId, account.getUrlZalo(), account.getUrlFacebook(), lstFile, locale.getLanguage(), locale);
			return this.successHandler.handlerSuccess(acc, start, userId);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null);
		}
	}
    @PostMapping(AppApiConstant.API_APP_SWITCH_ACCOUNT)
	public DtsApiResponse switchAccount(
			@RequestBody AccountSwitchAppDto account, HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
        	Long userId = UserProfileUtils.getAccountId();
        	JcaAccountDto acc = new JcaAccountDto();
        	NullAwareBeanUtils.copyPropertiesWONull(account, acc);
			return this.successHandler.handlerSuccess(accountService.switchAccount(userId, acc), start, userId);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null);
		}
	}

    @PostMapping(AppApiConstant.API_ADMIN_ACCOUNT +"/change-password-gad")
    @ApiOperation("Change password account")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402806, message = "Error account not found"),
            @ApiResponse(code = 402807, message = "Error account change password"),
            @ApiResponse(code = 402808, message = "Error password old incorrect"),
            @ApiResponse(code = 402809, message = "Error confirm password new incorrect"),
            @ApiResponse(code = 500, message = "Internal server error"),
            })
    public DtsApiResponse changePasswordAccountGa(
            @ApiParam(name = "body", value = "Account information to change password") @RequestBody AccountChangePasswordGadReq accountChangePasswordReq) {
        long start = System.currentTimeMillis();
        try {
    		Long userId = UserProfileUtils.getAccountId();
    		accountChangePasswordReq.setUserId(accountChangePasswordReq.getUserId() != null ? accountChangePasswordReq.getUserId() : userId);
    		JcaAccount jcaAccount = apiAccountService.changePasswordAccountGa(accountChangePasswordReq);
            return this.successHandler.handlerSuccess(jcaAccount, start, userId);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null);
        }
    }
    @PostMapping(AppApiConstant.API_ADMIN_ACCOUNT +"/check-otp")
    public DtsApiResponse checkOTP(@RequestBody CheckOtpReq checkOtpReq) {
        long start = System.currentTimeMillis();
        try {
            boolean result = apiAccountService.checkOtp(checkOtpReq);
            return this.successHandler.handlerSuccess(result, start, checkOtpReq.getAgentCode());
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null);
        }
    }

    @PostMapping(AppApiConstant.API_APP_UPDATE_FACE_MASK)
	public DtsApiResponse updateFaceMask(
            @RequestBody AgentInfoReq sensitiveReq, HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
        	Long userId = UserProfileUtils.getAccountId();
			return this.successHandler.handlerSuccess(accountService.updateFaceMask(userId, sensitiveReq.getAgentCode()), start, userId);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null);
		}
	}
    @PostMapping("/check-password-agent")
    public DtsApiResponse checkPasswordAgent(
            @RequestBody AgentInfoReq agentInfoReq, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        try {
            Long userId = UserProfileUtils.getAccountId();
            JcaAccount jcaAccount = accountService.getAccountById(userId);
            if (jcaAccount == null) {
                return this.successHandler.handlerSuccess(false, start, userId);
            }
            return this.successHandler.handlerSuccess(apiAccountService.checkPasswordAgent(jcaAccount, agentInfoReq.getPasswordAgent()), start, userId);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null);
        }
    }
    @PostMapping("/check-password-gad")
    public DtsApiResponse checkPasswordGad(
            @RequestBody AgentInfoReq agentInfoReq, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        try {
            Long userId = UserProfileUtils.getAccountId();
            JcaAccount jcaAccount = accountService.getAccountById(userId);
            if (jcaAccount == null) {
                return this.successHandler.handlerSuccess(false, start, userId);
            }
            return this.successHandler.handlerSuccess(apiAccountService.checkPasswordOld(jcaAccount.getGadPassword(), agentInfoReq.getPasswordGad()), start, userId);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null);
        }
    }
    @PostMapping("/login-gad")
    public DtsApiResponse loginGad(@RequestBody LoginGadReq loginGadReq, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        try {
            Long userId = UserProfileUtils.getAccountId();
            JcaAccount jcaAccount = accountService.getAccountById(userId);
            if (jcaAccount == null) {
                return this.successHandler.handlerSuccess(false, start, userId);
            }
            apiAccountService.loginGad(jcaAccount, loginGadReq.getPassword());
            return this.successHandler.handlerSuccess(null, start, userId);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null);
        }
    }
    @PostMapping("/resend-otp-gad")
    public DtsApiResponse resendOtpGad(HttpServletRequest request) {
        long start = System.currentTimeMillis();
        try {
            Long userId = UserProfileUtils.getAccountId();
            JcaAccount jcaAccount = accountService.getAccountById(userId);
            if (jcaAccount == null) {
                return this.successHandler.handlerSuccess(false, start, userId);
            }
            apiAccountService.resendOtpGad(jcaAccount);
            return this.successHandler.handlerSuccess(null, start, userId);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null);
        }
    }
    @GetMapping("/check-otp-gad")
    public DtsApiResponse checkOtpGad(String otp, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        try {
            Long userId = UserProfileUtils.getAccountId();
            JcaAccount jcaAccount = accountService.getAccountById(userId);
            if (jcaAccount == null) {
                return this.successHandler.handlerSuccess(false, start, userId);
            }
            return this.successHandler.handlerSuccess( apiAccountService.checkOtpGad(jcaAccount, otp), start, userId);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null);
        }
    }
    @PostMapping("/renew-password-gad")
    @ApiOperation("Change password account")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402809, message = "Error confirm password new incorrect"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    public DtsApiResponse renewPasswordAccountGa(
            @ApiParam(name = "body", value = "Account information to change password") @RequestBody AccountRenewPasswordGadReq accountRenewPasswordGadReq) {
        long start = System.currentTimeMillis();
        try {
            Long userId = UserProfileUtils.getAccountId();
            apiAccountService.renewPasswordAccountGa(accountRenewPasswordGadReq);
            return this.successHandler.handlerSuccess(null, start, userId);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null);
        }
    }
    @PostMapping(AppApiConstant.API_ACCOUNT_REGISTER_IS_SENDMAIL)
    @ApiOperation("update send email register")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse updateSendemailAccount(
            @ApiParam(name = "body", value = "Account information to add new") @RequestBody Long id) {
        long start = System.currentTimeMillis();
        try {
            JcaAccountRegister resObj = apiAccountService.saveJcaAccountRegisterContacted(id);
            return this.successHandler.handlerSuccess(resObj, start, id.toString(), null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null);
        }
    }
}
