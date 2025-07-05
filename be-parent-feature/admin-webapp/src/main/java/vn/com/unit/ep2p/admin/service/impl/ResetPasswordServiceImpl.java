/*******************************************************************************
 * Class        RepositoryServiceImpl
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/01 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import vn.com.unit.common.utils.CommonNullAwareBeanUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.enumdef.SendEmailTypeEnum;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentInformationParamDto;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.admin.service.ResetPasswordService;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.dto.ForgotPasswordResultDto;
import vn.com.unit.ep2p.core.dto.SendSmsApiRes;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.dto.ResetPasswordDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Qualifier(value = "ResetPasswordServiceImpl")
public class ResetPasswordServiceImpl implements ResetPasswordService {

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private Db2ApiService db2ApiService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private JcaAccountService jcaAccountService;

	@Autowired
	private JcaEmailTemplateService jcaEmailTemplateService;

	@Autowired
	private JcaEmailService jcaEmailService;

	@Autowired
	@Qualifier("jcaSystemConfigServiceImpl")
	private JcaSystemConfigService jcaSystemConfigService;

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static final String DS_SP_GET_INFORMATION_AGENT = "RPT_ODS.DS_SP_GET_INFORMATION_AGENT";
	public static final String TEMPLATE_GIVE_PASSWORD_FOR_ACCOUNT = "TEMPLATE_MAIL_RESET_PASSWORD";
	public static final String TEMPLATE_GIVE_PASSWORD_FOR_ACCOUNT_GAD = "TEMPLATE_MAIL_RESET_PASSWORD_GAD";
	public static final String TEMPLATE_SEND_MAIL_RESET_PASSWORD_SUCCESSFULLY = "TEMPLATE_SEND_MAIL_RESET_PASSWORD_SUCCESSFULLY";
	public static final String NOT_SEND = "NOT_SEND";
	public static final String NOT_SEND_EMAIL = "NOT_SEND_EMAIL";
	public static final String NOT_SEND_EMAIL_DLVN = "NOT_SEND_EMAIL_DLVN";
	public static final String NOT_SEND_PHONE = "NOT_SEND_PHONE";

	private Logger logger = LoggerFactory.getLogger(ResetPasswordServiceImpl.class);

	@Override
	public Db2AgentDto checkAgent(String agent) {
		return db2ApiService.getAgentInfoByConditionPlus(agent);
	}
	
	
	public String changePassword() {
		// Get system config
		Integer MIN_PASSWORD_LENGTH = Integer.parseInt(systemConfig.getConfig(SystemConfig.MIN_PASSWORD_LENGTH));
		String FLAG_LOWER_CASE = systemConfig.getConfig(SystemConfig.FLAG_LOWER_CASE);
		String FLAG_NUMBER_CASE = systemConfig.getConfig(SystemConfig.FLAG_NUMBER_CASE);
		String FLAG_SPECIAL_CASE = systemConfig.getConfig(SystemConfig.FLAG_SPECIAL_CASE);
		String FLAG_UPPER_CASE = systemConfig.getConfig(SystemConfig.FLAG_UPPER_CASE);

		// min length
		Integer total = MIN_PASSWORD_LENGTH;

		String lowerCaseLetters = "";
		String upperCaseLetters = "";
		String numbers = "";
		String specialChar = "";
		String totalChars = null;

		// char use
		Integer chars = 1;

		if (StringUtils.equalsIgnoreCase(FLAG_LOWER_CASE, "1")) {
			lowerCaseLetters = RandomStringUtils.random(chars, 97, 122, true, true);
			total = total - chars;
		}
		if (StringUtils.equalsIgnoreCase(FLAG_UPPER_CASE, "1")) {
			upperCaseLetters = RandomStringUtils.random(chars, 65, 90, true, true);
			total = total - chars;
		}
		if (StringUtils.equalsIgnoreCase(FLAG_NUMBER_CASE, "1")) {
			numbers = RandomStringUtils.randomNumeric(chars);
			total = total - chars;
		}
		if (StringUtils.equalsIgnoreCase(FLAG_SPECIAL_CASE, "1")) {
			specialChar = RandomStringUtils.random(chars, 33, 47, false, false);
			total = total - chars;
		}

		
		
		totalChars = RandomStringUtils.randomAlphanumeric(total);

		String combinedChars = upperCaseLetters.concat(lowerCaseLetters).concat(numbers).concat(specialChar)
				.concat(totalChars);

		
		return combinedChars;
		
	}

	@Override
	public ResetPasswordDto resetPassword(ResetPasswordDto editDto) {
		
		String combinedChars = changePassword();
		boolean check ;
	do {
		check = false;
		if(combinedChars.contains("+")|| combinedChars.contains(",")|| combinedChars.contains("$")|| combinedChars.contains("&")	|| combinedChars.contains("^")	) {
			combinedChars = changePassword();
			check = true;
		}
		}while(check);
			
	List<Character> pwdChars = combinedChars.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

	Collections.shuffle(pwdChars);

	String password = pwdChars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
			.toString();
						
	editDto.setNewPassword(password);
		
	return editDto;
	}

	@Override
	public ResetPasswordDto activeSend(ResetPasswordDto editDto) {

		JcaAccountDto data = new JcaAccountDto();
		String info = null;

		Db2AgentInformationParamDto param = new Db2AgentInformationParamDto();
		param.agent = editDto.getAgent();
		db2ApiService.callStoreinfomationAgent(DS_SP_GET_INFORMATION_AGENT, param);

		switch (editDto.getChooseMail()) {
		case "1":
			// email ca nhan
			if (ObjectUtils.isNotEmpty(data) && StringUtils.isNotBlank(param.data.get(0).getEmail())) {
				info = param.data.get(0).getEmail();
			} else {
				editDto.setResult(NOT_SEND_EMAIL);
			}
			break;
		case "2":
			// email dlvn
			if (ObjectUtils.isNotEmpty(data) && StringUtils.isNotBlank(param.data.get(0).getEmailDlvn())) {
				info = param.data.get(0).getEmailDlvn();
			} else {
				editDto.setResult(NOT_SEND_EMAIL_DLVN);
			}
			break;
		case "3":
			// SMS
			if (ObjectUtils.isNotEmpty(data) && StringUtils.isNotBlank(param.data.get(0).getMobilePhone())) {
				info = param.data.get(0).getMobilePhone();
				if(StringUtils.endsWithIgnoreCase(info,"999999999")){
					editDto.setResult(NOT_SEND_PHONE);
				}
			} else {
				editDto.setResult(NOT_SEND_PHONE);
			}
			break;
		default:
			break;
		}
		editDto.setSend(info);
		return editDto;
	}

	@Override
	public String setPasswordReset(ResetPasswordDto editDto, Locale locale) throws Exception {
		String result = "";
		switch (editDto.getChooseLogin()) {
		case "1":
			try {
				// password D-Success
				changePasswordAccountDS(editDto);
			} catch (Exception e) {
				e.printStackTrace();
				result = NOT_SEND;
			}
			break;
		case "2":
			try {
				// password GAD
				changePasswordAccountGa(editDto);
			} catch (Exception e) {
				e.printStackTrace();
				result = NOT_SEND;
			}
			break;
		case "3":
			try {
				// Both
				changePasswordAccountDS(editDto);
				changePasswordAccountGa(editDto);
			} catch (Exception e) {
				e.printStackTrace();
				result = NOT_SEND;
			}
			break;
		default:
			break;
		}
		return result;
	}

	public List<JcaAccount> changePasswordAccountGa(ResetPasswordDto editDto) throws Exception {

		List<JcaAccount> jcaAccount = jcaAccountService.getListByUserName(editDto.getAgent());

		if (ObjectUtils.isNotEmpty(jcaAccount)) {
			String newPassword = editDto.getNewPassword();
			String encryptedPassword = bCryptPasswordEncoder().encode(newPassword);

			accountService.updatePasswordGad(jcaAccount.get(0).getId(), encryptedPassword);

			// SEND MAIL OR SMS
			if (StringUtils.equalsIgnoreCase(editDto.getChooseMail(), "3")) {
				sendSMS(editDto);

			} else {
				try {
					sendEmailChangePasswordGad(jcaAccount.get(0).getFullname(), editDto.getSend(), newPassword, jcaAccount.get(0).getGender());
				} catch (Exception e) {
					throw new Exception("Send Maill error:" + e);
				}

			}
			return jcaAccount;
		} else {
			throw new DetailException(AppApiExceptionCodeConstant.E402806_APPAPI_ACCOUNT_NOT_FOUND);
		}
	}

	public String changePasswordAccountDS(ResetPasswordDto editDto) throws Exception {
		String resultMessage = "";
		final String API_TOKEN = "1726e098d6f941688a01819d5d78d406";
		ForgotPasswordResultDto result = null;
		try {
			result = RetrofitUtils.forgotPassword(editDto.getAgent(), editDto.getNewPassword(),
					API_TOKEN);
		} catch (Exception e){
			throw new Exception(e.getMessage());
		}

		if (result == null) {
			throw new Exception("Renew password: Call API error");
		}
		if (result != null && !StringUtils.equalsIgnoreCase(result.getResult(), "true")) {
			String error = result.getErrLog();
			logger.error(error);
			throw new Exception(error);
		}
		if (result != null && StringUtils.equalsIgnoreCase(result.getResult(), "true")) {
			// check key reset password
			List<JcaAccount> jcaAccount = jcaAccountService.getListByUserName(editDto.getAgent());
			if (jcaAccount.size() > 0) {
				accountService.isCheckResetPassword(jcaAccount.get(0).getId());
			}
			resultMessage = result.getErrLog();
			// SEND MAIL OR SMS
			if (StringUtils.equalsIgnoreCase(editDto.getChooseMail(), "3")) {
				sendSMS(editDto);
			} else {
				sendEmailChangePasswordDs(editDto.getAgent(), editDto.getSend(), editDto.getNewPassword());
			}
		}
		return resultMessage;
	}

	private void sendEmailChangePasswordGad(String gadName, String email, String password, String gender) {
		// send password for email or phone
		Map<String, Object> paramEmail = new HashMap<String, Object>();
		paramEmail.put("gadName", gadName);
		paramEmail.put("email", email);
		paramEmail.put("password", password);
        paramEmail.put("gender", StringUtils.equalsIgnoreCase(gender, "M") ? "Anh" : "Chị");
        paramEmail.put("min", "5");
		sendEmail(paramEmail, TEMPLATE_GIVE_PASSWORD_FOR_ACCOUNT_GAD);
	}
	private void sendEmailChangePasswordDs(String gadName, String email, String password) {
		// send password for email or phone
		Map<String, Object> paramEmail = new HashMap<String, Object>();
		paramEmail.put("gadName", gadName);
		paramEmail.put("email", email);
		paramEmail.put("password", password);
		sendEmail(paramEmail, TEMPLATE_GIVE_PASSWORD_FOR_ACCOUNT);
	}

	private void sendEmail(Map<String, Object> paramEmail, String templateEmail) {
		// set toAddress
		try {
			String email = paramEmail.get("email").toString();
			List<String> toAddress = new ArrayList<>();
			toAddress.add(email);

			JcaEmailTemplateDto templateDto = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(templateEmail);
			if (templateDto != null) {
				String content = jcaEmailService.replaceParam(templateDto.getTemplateContent(), paramEmail);
				String subject = jcaEmailService.replaceParam(templateDto.getTemplateSubject(), paramEmail);
				// set email
				JcaEmailDto jcaEmailDto = jcaEmailService.convertValue(templateDto);
				jcaEmailDto.setToString(email);
				jcaEmailDto.setToAddress(toAddress);
				jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
				jcaEmailDto.setEmailContent(content);
				jcaEmailDto.setSubject(subject);
				JcaEmailDto dto = new JcaEmailDto();
				CommonNullAwareBeanUtil.copyPropertiesWONull(jcaEmailDto, dto);
				jcaEmailService.sendEmail(dto);
			}
		} catch (Exception e) {
			logger.error("Send mail: ", e);
		}
	}

	public void sendSMS(ResetPasswordDto editDto) throws DetailException {
		// GET CONFIG SEND OTP
		final String URL = jcaSystemConfigService.getValueByKey("IIBHCMS_BASE_URL", 2L);
		final String SENDER = "OP";
		final String SMS_TYPE = "12";
		final String TOKEN = jcaSystemConfigService.getValueByKey("SEND_SMS_TOKEN", 2L);
		final String TEMPLATE = jcaSystemConfigService.getValueByKey("SMS_RESET_PASSWORD", 2L);
		String INFO = StringUtils.replaceOnce(TEMPLATE, "{password}", editDto.getNewPassword());
		INFO = StringUtils.replaceOnce(INFO, "{agentcode}", editDto.getAgent());
		SendSmsApiRes sendSmsApiRes = RetrofitUtils.sendSMSApi(TOKEN, URL, editDto.getAgent(), editDto.getSend(), SENDER, SMS_TYPE,
					String.valueOf(INFO));
		if (sendSmsApiRes == null || !StringUtils.equalsIgnoreCase(sendSmsApiRes.getResponseCode(), "00")) {
			throw new DetailException(AppApiExceptionCodeConstant.E4026110_APPAPI_CAN_NOT_SEND_OTP);
		} else {
			logger.error("SEND SMS SUCCESS");
		}
	}

	@Override
	public String checkGad(String agent) {
		return accountService.checkGad(agent);
	}

	@Override
	public Db2AgentDto checkAgentExit(String agent) {
		return db2ApiService.getAgentInfoByCondition(agent);
	}

	@Override
	public boolean checkAgentIsGad(String agent) {
		return 	db2ApiService.checkAgentIsGad(agent);
	}

//	public void sendSMSSuccess(ResetPasswordDto editDto) throws DetailException {
//		// GET CONFIG SEND OTP
//
//		final String URL = jcaSystemConfigService.getValueByKey("IIBHCMS_BASE_URL", 2L);
////		final String SENDER = jcaSystemConfigService.getValueByKey("SENDER_SMS", 2L);
////		final String SMS_TYPE = jcaSystemConfigService.getValueByKey("SMS_TYPE", 2L);
//		final String SENDER = "OP";
//		final String SMS_TYPE = "12";
//		final String TOKEN = jcaSystemConfigService.getValueByKey("SEND_SMS_TOKEN", 2L);
//		final String TEMPLATE = jcaSystemConfigService.getValueByKey("SMS_RESET_PASSWORD_SUCCESSFULLY", 2L);
//		System.out.println("Send SMS Success Begin => ");
//		//CALL API SEND OTP
//		SendSmsApiRes sendSmsApiRes = RetrofitUtils.sendSMSApi(TOKEN, URL, editDto.getSend(), SENDER, SMS_TYPE,
//				String.valueOf(TEMPLATE));
//
//		System.out.println("Send SMS Success result => " + sendSmsApiRes);
//		logger.error(String.valueOf(sendSmsApiRes));
//		if (sendSmsApiRes == null || !StringUtils.equalsIgnoreCase(sendSmsApiRes.getResponseCode(), "00")) {
//			throw new DetailException(AppApiExceptionCodeConstant.E4026110_APPAPI_CAN_NOT_SEND_OTP);
//		}else {
//			logger.error("SEND SMS SUCCESS");
//		}
//
//	}
//
//	private void sendEmailChangePasswordSuccessfully(String agentCode) {
//
//		JcaAccount account = accountService.findByUserName(agentCode, 2L);
//		// send password for email or phone
//		Map<String, Object> paramEmail = new HashMap<String, Object>();
//		if (account != null) {
//			paramEmail.put("email", account.getEmail());
//			// set toAddress
//			try {
//				String email = paramEmail.get("email").toString();
//				List<String> toAddress = new ArrayList<>();
//				toAddress.add(email);
//				JcaEmailTemplateDto templateDto = jcaEmailTemplateService
//						.findJcaEmailTemplateDtoByCode(TEMPLATE_SEND_MAIL_RESET_PASSWORD_SUCCESSFULLY);
//				if (templateDto != null) {
//					String content = jcaEmailService.replaceParam(templateDto.getTemplateContent(), paramEmail);
//					String subject = jcaEmailService.replaceParam(templateDto.getTemplateSubject(), paramEmail);
//					// set email
//					JcaEmailDto jcaEmailDto = jcaEmailService.convertValue(templateDto);
//					jcaEmailDto.setToString(email);
//					jcaEmailDto.setToAddress(toAddress);
//					jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
//					jcaEmailDto.setEmailContent(content);
//					jcaEmailDto.setSubject(subject);
//					JcaEmailDto dto = new JcaEmailDto();
//					CommonNullAwareBeanUtil.copyPropertiesWONull(jcaEmailDto, dto);
//
//					System.out.println("Send Mail change pass word success " + dto);
//					jcaEmailService.sendEmail(dto);
//					System.out.println("Send Mail change pass word success result  ");
//				}
//			} catch (Exception e) {
//				logger.error("Send mail: ", e);
//			}
//		}
//	}
}
