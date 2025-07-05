package vn.com.unit.ep2p.core.utils;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.SSLContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import fr.opensagres.xdocreport.document.json.JSONObject;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.AgentBankDto;
import vn.com.unit.ep2p.core.dto.AccountApiDto;
import vn.com.unit.ep2p.core.dto.AckResultDto;
import vn.com.unit.ep2p.core.dto.EmailResetPassResultDto;
import vn.com.unit.ep2p.core.dto.ForgotPasswordResultDto;
import vn.com.unit.ep2p.core.dto.PasswordChangeResultDto;
import vn.com.unit.ep2p.core.dto.PostGetLetterRes;
import vn.com.unit.ep2p.core.dto.PostNotificationletterRes;
import vn.com.unit.ep2p.core.dto.QaCodeDto;
import vn.com.unit.ep2p.core.dto.QaCodeRes;
import vn.com.unit.ep2p.core.dto.SendSmsApiRes;
import vn.com.unit.ep2p.core.dto.SigninADPortalRes;
import vn.com.unit.ep2p.core.dto.UpdateBankAccountRes;
import vn.com.unit.ep2p.core.dto.ValidateForgotPasswordResultDto;
import vn.com.unit.ep2p.core.res.dto.AckSubmitSearchDto;
import vn.com.unit.ep2p.core.res.dto.DocumentAPISearchDto;
import vn.com.unit.ep2p.core.res.dto.EmailResetPassDto;
import vn.com.unit.ep2p.core.res.dto.PostGetLetterSearchDto;
import vn.com.unit.ep2p.core.res.dto.PostNotificationletterSearchDto;
import vn.com.unit.ep2p.core.service.LoggingService;
import vn.com.unit.ep2p.log.entity.LogApiExternal;

@Component
public class RetrofitUtils {
	private static MessageSource messageSource;
	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(RetrofitUtils.class);
	
	private static final String CLIENT_ID = "6a3c8b4c-3310-4f93-aca9-8922f9248e5e";
	
	private static final String CLIENT_SECRET = "8acf9510-eb8e-4cfe-a103-d71fe366906f";
	
	private static String DEVICE_ID = UserProfileUtils.getDeviceId();
	private static String DEVICE_TOKEN = UserProfileUtils.getDeviceToken();
	private static String API_TOKEN = UserProfileUtils.getApiToken();
	private static JcaSystemConfigService jcaSystemConfigService;
	private static LoggingService loggingService;
	
    @Autowired
    public RetrofitUtils(JcaSystemConfigService jcaSystemConfigService, MessageSource messageSource, LoggingService loggingService) {
    	RetrofitUtils.jcaSystemConfigService = jcaSystemConfigService;
    	RetrofitUtils.loggingService = loggingService;
    	this.messageSource = messageSource;
    }
	
	public static AccountApiDto loginApi(String token, String agentId, String password, String deviceToken,
			String deviceId) throws Exception {
		
		String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "DSAuthLogin";

		AccountApiDto dtoResult = null;

		String jsonInput = 
		      "{\"jsonDataInput\":"
			+ 		"{"
		    + 			"\"AgentId\": \"" + agentId + "\"," 
			+ 			"\"Password\": \"" + password + "\"," 
			+			"\"DeviceId\": \"" + deviceId   + "\"," 
			+			"\"Project\": " + "\"magp"  + "\"," 
			+ 			"\"DeviceToken\": \"" + deviceToken + "\""
			+ 		"}"
			+ "}";
		logger.error("jsonInput"+ jsonInput, jsonInput);
		logger.error("url"+ url, url);
		try {
			JSONObject jsonObject = callApi(url, jsonInput);
			logger.error("loginApi callApi success");

			if (jsonObject != null) {
				ObjectMapper mapper = new ObjectMapper();
				logger.error("loginApi - Response: " + jsonObject.get("Response").toString());
				// Set AccountApiDto
				dtoResult = mapper.readValue(jsonObject.get("Response").toString(), new TypeReference<AccountApiDto>() {
				});
			} else {
				throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
			}
		} catch (Exception e) {
			logger.error("loginApi err: " +  e.toString());
			logger.error("loginApi err: ", e);
			throw new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
		}

		return dtoResult;
	}

	public static SendSmsApiRes sendSMSApi(String token, String url, String agentCode, String phoneNumber, String sender, String smsType,
			String otp){

		SendSmsApiRes dtoResult = null;

		String jsonInput ="{" + "\"AgentCode\": \"" + agentCode + "\","
				+ "\"PhoneNumber\": \"" + phoneNumber + "\","
				+ "\"Content\": \"" + otp + "\","
				+ "\"Sender\": \"" + sender + "\","
				+ "\"SMSType\": \"" + smsType + "\","
				+ "\"PolicyNumber\": \"" + "\""
				+ "}";

		try {
			JSONObject jsonObject = callApi(url, jsonInput);
			logger.error("sendSMSApi callApi success");

			if (jsonObject != null) {
				ObjectMapper mapper = new ObjectMapper();
				logger.error("sendSMSApi - Response: " + jsonObject.toString());
				// Set SendSmsApiRes
				dtoResult = mapper.readValue(jsonObject.toString(), new TypeReference<SendSmsApiRes>() {
				});
			}
		} catch (Exception e) {
			logger.error("sendSMSApi err: ", e);
		}

		return dtoResult;
	};
	public static EmailResetPassResultDto sendEmailResetPass(EmailResetPassDto emailResetPassDto) throws Exception {
		EmailResetPassResultDto emailResetPassResultDto = null;
		String url = jcaSystemConfigService.getValueByKey("CSPORTAL_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "/EmailResetPass";
//		String url = "http://localhost:8080/dsuccess-api-uat/api/v1/app/test2";
		String jsonInput = "{"
					+ 			"\"username\": \"" + emailResetPassDto.getUsername() + "\","
					+ 			"\"password\": \"" + emailResetPassDto.getPassword() + "\""
					+ 		"}";
		try {
			logger.error(jsonInput);
			JSONObject jsonObject = RetrofitUtils.callApiNoHeader(url, jsonInput);
			if (jsonObject != null) {
				logger.error("sendEmailResetPass - Response: " + jsonObject.toString());
				ObjectMapper mapper = new ObjectMapper();
				logger.error("sendEmailResetPass begin1");
				String jsonResult = jsonObject.toString();
				logger.error("sendEmailResetPass begin2");
				emailResetPassResultDto = mapper.readValue(jsonResult, EmailResetPassResultDto.class);
				logger.error("sendEmailResetPass end");
			} else {
				throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
			}
		} catch (Exception e) {
			logger.error("validateForgotPassword", e);
			throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
		}
		return emailResetPassResultDto;
	}
	public static ValidateForgotPasswordResultDto validateForgotPassword(String agentId, String action, String email) throws Exception {

		ValidateForgotPasswordResultDto dtoResult = null;

		String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "DLPadAuthLogin";

		String jsonInput =
				"{\"jsonDataInput\":"
						+ 		"{" + "\"AgentId\": \"" + agentId + "\","
						+ 			"\"Project\": \"" + "magp" + "\","
						+ 			"\"DeviceId\": \"" + DEVICE_ID + "\","
						+			"\"DeviceToken\"" + DEVICE_TOKEN + "\","
						+			"\"Action\": \"" + action + "\","
						+ 			"\"Email\": \"" + email +  "\""
						+ 		"}"
						+ "}";
		try {
			logger.error(jsonInput);
			JSONObject jsonObject = RetrofitUtils.callApiNoHeader(url, jsonInput);
			if (jsonObject != null) {
				logger.error("validateForgotPassword - Response: " + jsonObject.get("PDLPadAuthLoginResult").toString());
				ObjectMapper mapper = new ObjectMapper();
				logger.error("validateForgotPassword begin1");
				String jsonResult = jsonObject.get("PDLPadAuthLoginResult").toString();
				logger.error("validateForgotPassword begin2");
				dtoResult = mapper.readValue(jsonResult,
						new TypeReference<ValidateForgotPasswordResultDto>() {
						});
				logger.error("validateForgotPassword end");
			} else {
				throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
			}
		} catch (Exception e) {
			logger.error("validateForgotPassword", e);
			throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
		}

		return dtoResult;
	}
	public static PasswordChangeResultDto changePassword(String agentId, String password, String newPassword, String apiToken) throws Exception {
		
		PasswordChangeResultDto dtoResult = null;
		
		String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "DSChangePassword";
		
		String jsonInput = 
			      "{\"jsonDataInput\":"
				+ 		"{" + "\"AgentID\": \"" + agentId + "\","
			 	 + 			"\"Password\": \"" + password + "\","
			 	 + 			"\"NewPassword\": \"" + newPassword + "\","
				+ 			"\"APIToken\": \"" + API_TOKEN + "\","
				+			"\"Action\": \"" + "ChangePasswordDS" + "\""
				+ 		"}"
				+ "}";
		try {
			logger.error(jsonInput);
			JSONObject jsonObject = RetrofitUtils.callApi(url, jsonInput);
			if (jsonObject != null) {
				logger.error("changePassword - Response: " + jsonObject.get("Response").toString());
				ObjectMapper mapper = new ObjectMapper();
				logger.error("changePassword begin1");
				String jsonResult = jsonObject.get("Response").toString();
				logger.error("changePassword begin2");
				dtoResult = mapper.readValue(jsonResult,
						new TypeReference<PasswordChangeResultDto>() {
						});
				logger.error("changePassword end");
			} else {
				throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
			}
		} catch (Exception e) {
			logger.error("changePassword", e);
			throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
		}

		return dtoResult;
	}
	public static ForgotPasswordResultDto forgotPassword(String agentId, String newPassword, String apiToken) throws Exception {

		ForgotPasswordResultDto dtoResult = null;

		String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "DSForgotPassword";

		String jsonInput =
				"{\"jsonDataInput\":"
						+ 		"{" + "\"AgentID\": \"" + agentId + "\","
						+ 			"\"NewPassword\": \"" + newPassword + "\","
						+ 			"\"APIToken\": \"" + apiToken + "\","
						+			"\"Action\": \"" + "ChangePassword" + "\""
						+ 		"}"
						+ "}";
		try {
			logger.error(jsonInput);
			JSONObject jsonObject = RetrofitUtils.callApi(url, jsonInput);
			if (jsonObject != null) {
				logger.error("forgotPassword - Response: " + jsonObject.get("Response").toString());
				ObjectMapper mapper = new ObjectMapper();
				logger.error("forgotPassword begin1");
				String jsonResult = jsonObject.get("Response").toString();
				logger.error("forgotPassword begin2");
				dtoResult = mapper.readValue(jsonResult,
						new TypeReference<ForgotPasswordResultDto>() {
						});
				logger.error("forgotPassword end");
			} else {
				throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
			}
		} catch (Exception e) {
			logger.error("forgotPassword", e);
			throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
		}

		return dtoResult;
	}
	private static JSONObject getJsonObject(String url, String jsonInput, HttpHeaders headers) {
		long startTime = System.currentTimeMillis();
		JSONObject jsonObject = null;
		RestTemplate restTemplate = new RestTemplate();

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

		HttpEntity<String> entity = new HttpEntity<>(jsonInput, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
				String.class);
		long endTime = System.currentTimeMillis();
		long totalActionTime = endTime - startTime;

		logger.error("callApi - responseEntity: " + responseEntity.getStatusCode());

		String username = UserProfileUtils.getUserNameLogin();
		LogApiExternal logApiEx = new LogApiExternal();
		logApiEx.setUrl(url);
		logApiEx.setJsonInput(jsonInput);
		logApiEx.setCreatedDate(new Date());
		logApiEx.setStatus(responseEntity.getStatusCode()!= null ? responseEntity.getStatusCode().toString() : null);
		logApiEx.setTats(totalActionTime);
		logApiEx.setUsername(username);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {

			String response = responseEntity.getBody();
			logApiEx.setResponseJson(response);
			logger.error("Response API:response ", response);
			if (!StringUtils.isBlank(response)) {
				jsonObject = new JSONObject(response);
				logger.error("Response API: ", jsonObject);
			}
		} else {
			logger.error("callApi Error: " + responseEntity.getStatusCode());
		}
		try {
			loggingService.saveLogApiExternal(logApiEx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static JSONObject callApi(String url, String jsonInput) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("clientid", CLIENT_ID);
		headers.add("clientsecret", CLIENT_SECRET);

		return getJsonObject(url, jsonInput, headers);
	}

	public static JSONObject callRecapchaApi(String url, String jsonInput) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		return getJsonObject(url, jsonInput, headers);
	}
	public static JSONObject callApiNoHeader(String url, String jsonInput) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return getJsonObject(url, jsonInput, headers);
	}

	public static AckResultDto ackSubmit(AckSubmitSearchDto searchDto) throws Exception {
        AckResultDto dtoResult = null;
        
        String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "eapp_SubmitForm";

        String jsonInput = 
                "{\"jsonDataInput\":"
              +       "{" + "\"Action\": \"" + "SubmitPropsImage" + "\","
              +           "\"Project\": \"" + searchDto.getProject() + "\","
              +           "\"DeviceID\": \"" + searchDto.getDeviceID() + "\","
              +           "\"APIToken\": \"" + searchDto.getAPIToken() + "\","
              +           "\"AgentID\": \"" + searchDto.getAgentID() + "\"," 
              +           "\"ProposalID\": \"" + searchDto.getProposalID() + "\"," 
              +           "\"PotentialID\": \"" + searchDto.getPotentialID() + "\"," 
              +           "\"DocProcessID\": \"" + "NBE" + "\"," 
              +           "\"DocTypeID\": \"" + "ACK" + "\"," 
              +           "\"NumberOfPage\": \"" + searchDto.getNumberOfPage() + "\"," 
              +           "\"Image\": \"" + searchDto.getImage() + "\"" 
              +       "}"
              + "}";
        
        try {
        	logger.error("epp_ack_SubmitForm - Response: url " + url);
            logger.error(jsonInput);
            JSONObject jsonObject = RetrofitUtils.callApi(url, jsonInput);
            if (jsonObject != null) {
                logger.error("epp_ack_SubmitForm - Response: " + jsonObject.get("Response").toString());
                ObjectMapper mapper = new ObjectMapper();
                logger.error("epp_ack_SubmitForm begin1");
                String jsonResult = jsonObject.get("Response").toString();
                logger.error("epp_ack_SubmitForm begin2");
                dtoResult = mapper.readValue(jsonResult,
                        new TypeReference<AckResultDto>() {
                        });
                logger.error("epp_ack_SubmitForm end");
            } else {
                throw  new Exception(messageSource.getMessage("epp ack SubmitForm", null, new Locale("vi")));
            }
        } catch (Exception e) {
            logger.error("epp_ack_SubmitForm", e);
            throw  new Exception(messageSource.getMessage("epp ack SubmitForm", null, new Locale("vi")));
        }

        return dtoResult;
    }
	
	public static AckResultDto DocumentAPI(DocumentAPISearchDto searchDto) throws Exception {
        AckResultDto dtoResult = null;
        
        String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "DocumentAPI";
        
        String jsonInput = 
                "{\"jsonDataInput\":"
              +       "{" + "\"Action\": \"" + "SubmitDocument" + "\","
              +           "\"Project\": \"" + "mAGP" + "\","
              +           "\"DeviceID\": \"" + searchDto.getDeviceID() + "\","
              +           "\"APIToken\": \"" + searchDto.getAPIToken() + "\","
              +           "\"AgentID\": \"" + searchDto.getAgentID() + "\"," 
              +           "\"PolicyID\": \"" + searchDto.getProposalID() + "\"," 
              +           "\"DocTypeID    \": \"" + "ACK" + "\""
              +       "}"
              + "}";
        
        try {
            logger.error(jsonInput);
            logger.error("Start : jsonInput" + jsonInput);
            JSONObject jsonObject = RetrofitUtils.callApi(url, jsonInput);
            if (jsonObject != null) {
                logger.error("DocumentAPI - Response: " + jsonObject.get("Response").toString());
                ObjectMapper mapper = new ObjectMapper();
                logger.error("DocumentAPI begin1");
                String jsonResult = jsonObject.get("Response").toString();
                logger.error("DocumentAPI begin2");
                dtoResult = mapper.readValue(jsonResult,
                        new TypeReference<AckResultDto>() {
                        });
                logger.error("DocumentAPI end");
            } else {
                throw  new Exception(messageSource.getMessage("DocumentAPI", null, new Locale("vi")));
            }
        } catch (Exception e) {
            logger.error("DocumentAPI", e);
            throw  new Exception(messageSource.getMessage("DocumentAPI", null, new Locale("vi")));
        }

        return dtoResult;
    }
	
	public static PostNotificationletterRes PostNotificationletter(PostNotificationletterSearchDto searchDto) throws Exception {
	    PostNotificationletterRes dtoResult = null;
        
        String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "PostNotificationletter";
        
        String jsonInput = 
                "{\"jsonDataInput\":"
              +       "{" + "\"PolicyNo\": \"" + searchDto.getPolicyNo() + "\""
              +       "}"
              + "}";
        System.out.println("jsonDataInput_PostNotificationletter: "+jsonInput);
        System.out.println("url_PostNotificationletter: "+url);
        try {
            logger.error(jsonInput);
            JSONObject jsonObject = RetrofitUtils.callApi(url, jsonInput);
            if (jsonObject != null) {
                logger.error("PostNotificationletter - Response: " + jsonObject.get("Response").toString());
                ObjectMapper mapper = new ObjectMapper();
                logger.error("PostNotificationletter begin1");
                String jsonResult = jsonObject.get("Response").toString();
                logger.error("PostNotificationletter begin2");
                dtoResult = mapper.readValue(jsonResult,
                        new TypeReference<PostNotificationletterRes>() {
                        });
                logger.error("PostNotificationletter end");
            } else {
                throw  new Exception(messageSource.getMessage("PostNotificationletter", null, new Locale("vi")));
            }
        } catch (Exception e) {
            logger.error("PostNotificationletter", e);
            throw  new Exception(messageSource.getMessage("PostNotificationletter", null, new Locale("vi")));
        }

        return dtoResult;
	}
	
	//chi tiet pdf
	public static PostGetLetterRes PostGetLetter(PostGetLetterSearchDto searchDto) throws Exception {
	    PostGetLetterRes dtoResult = null;
        
        String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "PostGetLetter";
        
        String jsonInput = 
                "{\"jsonDataInput\":"
              +       "{" + "\"PolicyNo\": \"" + searchDto.getPolicyNo() + "\","
              +             "\"ClientId\": \"" + searchDto.getClientId() + "\","
              +             "\"LetterType\": \"" + searchDto.getLetterType() + "\""
              +       "}"
              + "}";
        System.out.println("jsonDataInput_PostGetLetter: "+jsonInput);
        System.out.println("url_PostGetLetter: "+url);
        try {
            logger.error(jsonInput);
            JSONObject jsonObject = RetrofitUtils.callApi(url, jsonInput);
            if (jsonObject != null) {
                logger.error("PostGetLetter - Response: " + jsonObject.get("Response").toString());
                ObjectMapper mapper = new ObjectMapper();
                logger.error("PostGetLetter begin1");
                String jsonResult = jsonObject.get("Response").toString();
                logger.error("PostGetLetter begin2");
                dtoResult = mapper.readValue(jsonResult,
                        new TypeReference<PostGetLetterRes>() {
                        });
                logger.error("PostGetLetter end");
            } else {
                throw  new Exception(messageSource.getMessage("PostGetLetter", null, new Locale("vi")));
            }
        } catch (Exception e) {
            logger.error("PostGetLetter", e);
            throw  new Exception(messageSource.getMessage("PostGetLetter", null, new Locale("vi")));
        }

        return dtoResult;
    }

	public static QaCodeRes getQaCode(QaCodeDto dto) throws Exception {
		QaCodeRes dtoResult = null;
        
        String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "eRecruitment";
        if (StringUtils.isNotEmpty(API_TOKEN)) {
        	dto.setApiToken(API_TOKEN);
        }
        if (StringUtils.isNotEmpty(DEVICE_ID)) {
        	dto.setDeviceId(DEVICE_ID);
        }
        String jsonInput = 
                "{\"jsonDataInput\":"
              +       "{" + "\"APIToken\": \"" + dto.getApiToken() + "\","
              +             "\"Action\": \"" + "GenQRCode" + "\","
              +             "\"AgentID\": \"" + UserProfileUtils.getFaceMask() + "\","
              +             "\"DeviceID\": \"" + dto.getDeviceId() + "\","
              +             "\"DeviceName\": \"" + dto.getDeviceName() + "\","
              +             "\"Project\": \"" + "magp" + "\""
              +       "}"
              + "}";
        System.out.println("jsonDataInput_PostGetLetter: "+jsonInput);
        System.out.println("url_PostGetLetter: "+url);
        try {
            logger.error(jsonInput);
            JSONObject jsonObject = RetrofitUtils.callApi(url, jsonInput);
            if (jsonObject != null) {
                logger.error("eRecruitment - Response: " + jsonObject.get("Response").toString());
                ObjectMapper mapper = new ObjectMapper();
                logger.error("eRecruitment begin1");
                String jsonResult = jsonObject.get("Response").toString();
                logger.error("eRecruitment begin2");
                dtoResult = mapper.readValue(jsonResult,
                        new TypeReference<QaCodeRes>() {
                        });
                logger.error("eRecruitment end");
            } else {
                throw  new Exception(messageSource.getMessage("eRecruitment", null, new Locale("vi")));
            }
        } catch (Exception e) {
            logger.error("eRecruitment", e);
            throw  new Exception(messageSource.getMessage("eRecruitment", null, new Locale("vi")));
        }

        return dtoResult;
    }
	
	public static void updateCandidateInOfficeInActive(String officeCodes) throws Exception {
        
        String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "eRecruitment";
        System.out.println("updateCandidateInOfficeInActive EAPPIBPS_BASE_URL : "+url);
       
        String jsonInput = 
                "{\"jsonDataInput\":"
              +       "{" + "\"Action\": \"" + "UpdateCanditeInOfficeInActive" + "\","
              +             "\"OfficeCodes\": \"" + officeCodes + "\","        
              +             "\"Project\": \"" + "magp" + "\""
              +       "}"
              + "}";
        System.out.println("updateCandidateInOfficeInActive jsonDataInput : "+jsonInput);
        try {
            logger.error(jsonInput);
            JSONObject jsonObject = RetrofitUtils.callApi(url, jsonInput);
            if (jsonObject != null) {
                logger.info("updateCandidateInOfficeInActive - Response: " + jsonObject.get("Response").toString());
                ObjectMapper mapper = new ObjectMapper();
                String jsonResult = jsonObject.get("Response").toString();
                logger.info("updateCandidateInOfficeInActive jsonResult: " + jsonResult);
               
                logger.error("updateCandidateInOfficeInActive end");
            } else {
                throw  new Exception(messageSource.getMessage("eRecruitment", null, new Locale("vi")));
            }
        } catch (Exception e) {
            logger.error("eRecruitment", e);
            throw  new Exception(messageSource.getMessage("eRecruitment", null, new Locale("vi")));
        }
       
    }
	
	public static void updateCandidateOverDueToExpired() throws Exception {
        
        String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "eRecruitment";
        System.out.println("updateCandidateOverDueToExpired EAPPIBPS_BASE_URL : "+url);
       
        String jsonInput = 
                "{\"jsonDataInput\":"
              +       "{" + "\"Action\": \"" + "UpdateCandidateOverDueToExpired" + "\","
              +             "\"Project\": \"" + "magp" + "\""
              +       "}"
              + "}";
        System.out.println("updateCandidateOverDueToExpired jsonDataInput : "+jsonInput);
        try {
            logger.error(jsonInput);
            JSONObject jsonObject = RetrofitUtils.callApi(url, jsonInput);
            if (jsonObject != null) {
                logger.info("updateCandidateOverDueToExpired - Response: " + jsonObject.get("Response").toString());
                ObjectMapper mapper = new ObjectMapper();
                String jsonResult = jsonObject.get("Response").toString();
                logger.info("updateCandidateOverDueToExpired jsonResult: " + jsonResult);
               
                logger.error("updateCandidateOverDueToExpired end");
            } else {
                throw  new Exception(messageSource.getMessage("eRecruitment", null, new Locale("vi")));
            }
        } catch (Exception e) {
            logger.error("eRecruitment", e);
            throw  new Exception(messageSource.getMessage("eRecruitment", null, new Locale("vi")));
        }
       
    }
    
	public static void deleteAccountZoom() throws Exception {
		String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "VideoRecoding";
		String jsonInput = 
		      "{\"jsonDataInput\":"
			+ 		"{"
			+			"\"Action\": " + "\"DeleteAccountNotUsed" + "\""
			+ 		"}"
			+ "}";
		logger.error("url"+ url, url);
		try {
			callApi(url, jsonInput);
			logger.error("DeleteAccountNotUsed success");
		} catch (Exception e) {
			logger.error("DeleteAccountNotUsed err: " +  e.toString());
			throw new Exception("DeleteAccountNotUsed failed");
		}
	}
	
	public static void pushNotificationMeetingZoom() throws Exception {
		String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "VideoRecoding";
		String jsonInput = 
		      "{\"jsonDataInput\":"
			+ 		"{"
			+			"\"Action\": " + "\"PushNotifyMeetingZoom" + "\""
			+ 		"}"
			+ "}";
		logger.error("url"+ url, url);
		try {
			callApi(url, jsonInput);
			logger.error("PushNotificationMeetingZoom success");
		} catch (Exception e) {
			logger.error("PushNotificationMeetingZoom err: " +  e.toString());
			throw new Exception("PushNotificationMeetingZoom failed");
		}
	}
	
	public static String getDaiIchiOnUrl(String agentCode, String agentName) throws Exception {
		String result = null;
        
        String url = jcaSystemConfigService.getValueByKey("SOB_BASE_URL", ConstantCore.COMP_CUSTOMER_ID);
        String jsonInput = "{"
        					+ "\"systemId\": \"" + "325D9B829AC97D14E25B8AFE64837F9C123498F5" + "\","
        					+ "\"referralId\": \"" + agentCode + "\","
        					+ "\"referralName\": \"" + agentName + "\""
        					+ "}";
        System.out.println("jsonDataInput_GetDaiIchiOnUrl: "+jsonInput);
        System.out.println("url_SOP: "+url);
        SigninADPortalRes dtoResult = null;
        try {
            logger.error(jsonInput);
            JSONObject jsonObject = RetrofitUtils.callApiNoHeader(url, jsonInput);
            if (jsonObject != null) {
                logger.error("Response: " + jsonObject.toString());
                ObjectMapper mapper = new ObjectMapper();
                String message = jsonObject.get("message").toString();
                dtoResult = mapper.readValue(message,
                        new TypeReference<SigninADPortalRes>() {
                        });
                if (dtoResult != null && "00".equals(dtoResult.getCode())) {
                	JSONObject data = new JSONObject(jsonObject.get("data").toString());
                	result = data.get("productSharingUrl").toString();
                }
            }
        } catch (Exception e) {
            throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
        }

        return result;
    }
	
	public static String getPurchaseUrl(String product, String agentCode, String agentName, String branchCode, String emailAddress, String phoneNumber, String partnerCode) throws Exception {
		String result = null;
        
        String url = jcaSystemConfigService.getValueByKey("SOB_BASE_URL", ConstantCore.COMP_CUSTOMER_ID);
        String jsonInput = "{"
        					+ "\"systemId\": \"" + "A10BABF56CC45F5D748F29B7E0E364E4FF479DE0" + "\","
        					+ "\"referralId\": \"" + agentCode + "\","
                            + "\"referralBranch\": \"" + branchCode + "\","
                            + "\"referralEmail\": \"" + emailAddress + "\","
                            + "\"referralName\": \"" + agentName + "\","
                            + "\"referralPhone\": \"" + phoneNumber + "\","
                            + "\"partner\": \"" + partnerCode + "\","
                            + "\"referralTrainingList\": [{"
                            + "\"productCode\": \"" + "KCP105" + "\","
                            + "\"isAvailable\": \"" + (product.contains("KCP105") ? "true" : "false")
                            + "\"},{"
                            + "\"productCode\": \"" + "MPF102" + "\","
                            + "\"isAvailable\": \"" + (product.contains("MPF102") ? "true" : "false")
                            + "\"}]"
        					+ "}";
        System.out.println("jsonDataInput_GetDaiIchiOnUrl: "+jsonInput);
        System.out.println("url_SOP: "+url);
        SigninADPortalRes dtoResult = null;
        try {
            logger.error(jsonInput);
            JSONObject jsonObject = RetrofitUtils.callApiNoHeader(url, jsonInput);
            if (jsonObject != null) {
                logger.error("Response: " + jsonObject.toString());
                ObjectMapper mapper = new ObjectMapper();
                String message = jsonObject.get("message").toString();
                dtoResult = mapper.readValue(message,
                        new TypeReference<SigninADPortalRes>() {
                        });
                if (dtoResult != null && "00".equals(dtoResult.getCode())) {
                	JSONObject data = new JSONObject(jsonObject.get("data").toString());
                	result = data.get("productSharingUrl").toString();
                }
            }
        } catch (Exception e) {
            throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
        }

        return result;
    }
	
	public static boolean confirmPolicyDelivery(String action, String agentCode, String ids, String unitIds) throws Exception {
		String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "ConfirmPolicyDelivery";
		String jsonInput = 
		      "{\"jsonDataInput\":"
			+ 		"{"
			+			"\"Action\": \"" + action + "\","
			+			"\"AgentCode\": \"" + agentCode + "\","
			+			"\"UnitId\": \"" + ids + "\","
			+			"\"Id\": \"" + ids + "\""
			+ 		"}"
			+ "}";
		logger.error("url"+ url, url);
		try {
			JSONObject jsonObject = callApi(url, jsonInput);
            logger.error("confirmPolicyDelivery - Response: " + jsonObject.toString());
            String response = jsonObject.get("Response").toString();
            JSONObject responseObject = null;
			if (!StringUtils.isBlank(response)) {
				responseObject = new JSONObject(response);
				String errLog = responseObject.get("errLog").toString();
	            if (!StringUtils.isEmpty(errLog)) {
	            	throw  new Exception(errLog);
	            }
	            logger.error("confirmPolicyDelivery success");
	            return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("confirmPolicyDelivery err: " +  e.toString());
			throw new Exception("confirmPolicyDelivery failed");
		}
	}

    public static boolean checkPasswordExpired(String deviceToken, String agentId) throws Exception {

		boolean result = false;
		
		String url = jcaSystemConfigService.getValueByKey("EAPPIBPS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "eapp_SubmitForm";

		String jsonInput =
				"{\"jsonDataInput\":"
						+ 		"{" + "\"AgentID\": \"" + agentId + "\","					
			            +           "\"Project\": \"" + "mAGP" + "\"," 
						+ 			"\"DeviceToken\": \"" + deviceToken + "\","
						+			"\"Action\": \"" + "CheckPasswordExpired" + "\""
						+ 		"}"
						+ "}";
		try {
			logger.error(jsonInput);
			JSONObject jsonObject = RetrofitUtils.callApi(url, jsonInput);
			if (jsonObject != null) {
				 JSONObject responseObject = jsonObject.getJSONObject("Response");
				 result = Boolean.parseBoolean(responseObject.getString("Result"));
				 logger.error("checkPasswordExpired success");
				 return result;
			} else {
				throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
			}
		} catch (Exception e) {
			logger.error("checkPasswordExpired", e);
			throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
		}
	}
    
    public static String updateBankAccount(AgentBankDto agentBankInfo) throws Exception {
		String result = null;
        
        String url = jcaSystemConfigService.getValueByKey("DMS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID) + "agent-info/update-bank-account";
        String jsonInput = "{"
        					+ "\"agentCode\": \"" + agentBankInfo.getAgentCode() + "\","
        					+ "\"idNumber\": \"" + agentBankInfo.getIdNumber() + "\","
        					+ "\"bankAccountNummber\": \"" + agentBankInfo.getBankAccountNumber() + "\","
        					+ "\"bankAccountName\": \"" + agentBankInfo.getBankAccountName() + "\","
        					+ "\"bankCode\": \"" + agentBankInfo.getBankCode() + "\""
        					+ "}";
        UpdateBankAccountRes res = null;
        try {
        	HttpHeaders headers = new HttpHeaders();
        	String user = jcaSystemConfigService.getValueByKey("DMS_CLIENT_USER", ConstantCore.COMP_CUSTOMER_ID);
            if (StringUtils.isNotEmpty(user)) {
            	String password = jcaSystemConfigService.getValueByKey("DMS_CLIENT_PASSWORD", ConstantCore.COMP_CUSTOMER_ID);
            	String auth = user + ":" + password;
                String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
                String authHeader = "Basic " + encodedAuth;
                headers.set("Authorization", authHeader);
            }
    		headers.setContentType(MediaType.APPLICATION_JSON);
    		JSONObject jsonObject = getJsonObjectBypassSSL(url, jsonInput, headers);
            if (jsonObject != null) {
                ObjectMapper mapper = new ObjectMapper();
                res = mapper.readValue(jsonObject.toString(),
                        new TypeReference<UpdateBankAccountRes>() {
                        });
                if (res != null && "500".equals(res.getCode())) {
                	result = res.getMessage();
                }
            }
        } catch (Exception e) {
            throw  new Exception(messageSource.getMessage("login.api.failed", null, new Locale("vi")));
        }

        return result;
    }
    
    private static JSONObject getJsonObjectBypassSSL(String url, String jsonInput, HttpHeaders headers) 
    		throws Exception {
    	TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) {
				return true;
			}
		};
 
    	SSLContext sslContext = SSLContexts.custom()
				.loadTrustMaterial(null, acceptingTrustStrategy)
				.build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
		CloseableHttpClient httpClient = HttpClients.custom()
								.setSSLSocketFactory(csf)
								.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.setMessageConverters(messageConverters);

		long startTime = System.currentTimeMillis();
		JSONObject jsonObject = null;

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

		HttpEntity<String> entity = new HttpEntity<>(jsonInput, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
				String.class);
		long endTime = System.currentTimeMillis();
		long totalActionTime = endTime - startTime;
		
		String username = UserProfileUtils.getUserNameLogin();
		LogApiExternal logApiEx = new LogApiExternal();
		logApiEx.setUrl(url);
		logApiEx.setJsonInput(jsonInput);
		logApiEx.setCreatedDate(new Date());
		logApiEx.setStatus(responseEntity.getStatusCode()!= null ? responseEntity.getStatusCode().toString() : null);
		logApiEx.setTats(totalActionTime);
		logApiEx.setUsername(username);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {

			String response = responseEntity.getBody();
			logApiEx.setResponseJson(response);
			if (!StringUtils.isBlank(response)) {
				jsonObject = new JSONObject(response);
			}
		} else {
			logger.error("callApi Error: " + responseEntity.getStatusCode());
		}
		try {
			loggingService.saveLogApiExternal(logApiEx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	} 
}
