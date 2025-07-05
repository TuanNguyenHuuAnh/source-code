/*******************************************************************************
 * Class        AppSystemConfigServiceImpl
 * Created date 2019/01/22
 * Lasted date  2019/01/22
 * Author       VinhLT
 * Change log   2019/01/2201-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.common.logger.DebugLogger;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.config.SystemSettingKey;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.entity.JcaSystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.impl.JcaSystemConfigServiceImpl;
import vn.com.unit.dts.api.enumdef.APIStatus;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.enumdef.ConstantDisplayType;
import vn.com.unit.ep2p.admin.repository.SystemSettingRepository;
import vn.com.unit.ep2p.admin.service.AppSystemConfigService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.admin.utils.JCanaryPasswordUtil;
import vn.com.unit.ep2p.constant.AppSystemSettingKey;
import vn.com.unit.ep2p.constant.AppUrlConst;
import vn.com.unit.ep2p.constant.CommonConstant;
import vn.com.unit.ep2p.dto.AppSystemConfigDto;
import vn.com.unit.ep2p.dto.ResMapObject;
import vn.com.unit.ep2p.enumdef.ComplexityEnum;
import vn.com.unit.ep2p.enumdef.UpdateAppVersionTypeEnum;
import vn.com.unit.ep2p.utils.ExecMessage;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.service.FileStorageService;
import vn.com.unit.storage.service.JcaRepositoryService;

/**
 * AppSystemConfigServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
@Service
@Primary
@Transactional(readOnly = true)
public class AppSystemConfigServiceImpl extends JcaSystemConfigServiceImpl implements AppSystemConfigService {

	private static final Logger logger = LoggerFactory.getLogger(AppSystemConfigServiceImpl.class);
	private static final String SYSTEM_IMAGE_LOGO = "system-image-logo/";

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private SystemSettingRepository systemSettingRepository;

	@Autowired
	private MessageSource msg;
	
    @Autowired
    private FileStorageService fileStorageService;

	@Autowired
	private CommonService comService;

	@Autowired
	private JcaConstantService jcaConstantService;

	@Autowired
	private SystemLogsService systemLogsService;
    @Autowired
    private JcaRepositoryService jcaRepositoryService;

	public void buildConfigAbtract(AppSystemConfigDto systemConfigDto, JcaSystemConfig ss) {

		// Long companyId = UserProfileUtils.getCompanyId();
		Long companyId = systemConfigDto.getCompanyId();
		MessageList messageList = new MessageList();
		List<Message> messages = new ArrayList<>();
		// OZ_REPOSITORY_URL
		if (AppSystemSettingKey.OZ_REPOSITORY_URL.equals(ss.getSettingKey())) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setOzRepositoryUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getOzRepositoryUrl())) {
					systemConfigDto.setOzRepositoryUrl(ss.getSettingValue());
				}
			}
		} else if (AppSystemSettingKey.OZ_REPOSITORY_LOCAL_URL.equals(ss.getSettingKey())) { // OZ_REPOSITORY_LOCAL_URL
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setOzRepositoryLocalUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getOzRepositoryLocalUrl())) {
					systemConfigDto.setOzRepositoryLocalUrl(ss.getSettingValue());
				}
			}
		} else if (AppSystemSettingKey.OZ_REPOSITORY_USER.equals(ss.getSettingKey())) { // OZ_REPOSITORY_USER
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setOzRepositoryUser(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getOzRepositoryUser())) {
					systemConfigDto.setOzRepositoryUser(ss.getSettingValue());
				}
			}
		} else if (AppSystemSettingKey.OZ_REPOSITORY_PASSWORD.equals(ss.getSettingKey())) { // OZ_REPOSITORY_PASSWORD
			try {
				if (companyId.equals(ss.getCompanyId())) {
					systemConfigDto.setOzRepositoryPassword(JCanaryPasswordUtil.decryptString(ss.getSettingValue()));
				} else {
					if (StringUtils.isBlank(systemConfigDto.getOzRepositoryPassword())) {
						systemConfigDto
								.setOzRepositoryPassword(JCanaryPasswordUtil.decryptString(ss.getSettingValue()));
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else if (AppSystemSettingKey.ECM_REPOSITORY_DOCUMENT.equals(ss.getSettingKey())) { // OZ_REPOSITORY_USER
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setEcmRepositoryDocument(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getEcmRepositoryDocument())) {
					systemConfigDto.setEcmRepositoryDocument(ss.getSettingValue());
				}
			}
		}

		// AUTHEN_KEY
		if (AppSystemSettingKey.AUTHEN_KEY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			String value = JCanaryPasswordUtil.decryptString(ss.getSettingValue());
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setAuthenKey(value);
			} else {
				if (StringUtils.isBlank(systemConfigDto.getAuthenKey())) {
					systemConfigDto.setAuthenKey(value);
				}
			}
		}
		// DEFAULTGROUP
		if (AppSystemSettingKey.SYNC_USER_KEY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setDefaultGroup(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getDefaultGroup())) {
					systemConfigDto.setDefaultGroup(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.INTEG_URL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setIntegUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getIntegUrl())) {
					systemConfigDto.setIntegUrl(ss.getSettingValue());
				}
			}
		}

		// INTEG_SECRET_PASSWORD
		if (AppSystemSettingKey.INTEG_SECRET_PASSWORD.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			// String value = JCanaryPasswordUtil.decryptString(ss.getSettingValue());
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setIntegSecretPassword(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getIntegSecretPassword())) {
					systemConfigDto.setIntegSecretPassword(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.INTEG_URL_PROCESS.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setIntegUrlProcess(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getIntegUrlProcess())) {
					systemConfigDto.setIntegUrlProcess(ss.getSettingValue());
				}
			}
		}

		// AUTHEN_KEY_PROCESS
		if (AppSystemSettingKey.AUTHEN_KEY_PROCESS.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			String value = JCanaryPasswordUtil.decryptString(ss.getSettingValue());
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setAuthenKeyProcess(value);
			} else {
				if (StringUtils.isBlank(systemConfigDto.getAuthenKey())) {
					systemConfigDto.setAuthenKeyProcess(value);
				}
			}
		}

		// HSM_URL
		if (AppSystemSettingKey.HSM_URL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setHsmUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getHsmUrl())) {
					systemConfigDto.setHsmUrl(ss.getSettingValue());
				}
			}
		}
		// LOGIN API
		if (AppSystemSettingKey.LOGIN_API_URL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setLoginAPIUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getLoginAPIUrl())) {
					systemConfigDto.setLoginAPIUrl(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.LOGIN_API_AUTHEN_KEY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			// String value = JCanaryPasswordUtil.decryptString(ss.getSettingValue());
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setLoginAPIAuthenKey(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getLoginAPIAuthenKey())) {
					systemConfigDto.setLoginAPIAuthenKey(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.LOGIN_API_SECRET_KEY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			String value = JCanaryPasswordUtil.decryptString(ss.getSettingValue());
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setLoginAPISecretKey(value);
			} else {
				if (StringUtils.isBlank(systemConfigDto.getLoginAPISecretKey())) {
					systemConfigDto.setLoginAPISecretKey(value);
				}
			}
		}

		if (AppSystemSettingKey.LOGIN_API_HOST.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setLoginAPIHost(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getLoginAPIHost())) {
					systemConfigDto.setLoginAPIHost(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.LOGIN_API_KEY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			String value = JCanaryPasswordUtil.decryptString(ss.getSettingValue());
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setLoginAPIKey(value);
			} else {
				if (StringUtils.isBlank(systemConfigDto.getLoginAPIKey())) {
					systemConfigDto.setLoginAPIKey(value);
				}
			}
		}

		// PUSH NOTIF & MAIL
		if (AppSystemSettingKey.FIREBASE_URL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setFirebaseUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getFirebaseUrl())) {
					systemConfigDto.setFirebaseUrl(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.FIREBASE_AUTHEN_KEY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setFirebaseAuthenKey(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getFirebaseAuthenKey())) {
					systemConfigDto.setFirebaseAuthenKey(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.PUSH_NOTIFICATION.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				int flagPushNotif = Integer.parseInt(ss.getSettingValue());
				if (flagPushNotif == 1) {
					systemConfigDto.setPushNotif(true);
				} else {
					systemConfigDto.setPushNotif(false);
				}
			}
		}

		if (AppSystemSettingKey.PUSH_EMAIL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				int flagPushEmail = Integer.parseInt(ss.getSettingValue());
				if (flagPushEmail == 1) {
					systemConfigDto.setPushEmail(true);
				} else {
					systemConfigDto.setPushEmail(false);
				}
			}
		}

		if (AppSystemSettingKey.FIREBASE_WEB_API_KEY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setFirebaseWebApiKey(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getFirebaseWebApiKey())) {
					systemConfigDto.setFirebaseWebApiKey(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.FIREBASE_AUTH_DOMAIN.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setFirebaseAuthDomain(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getFirebaseAuthDomain())) {
					systemConfigDto.setFirebaseAuthDomain(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.FIREBASE_DATABASE_URL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setFirebaseDatabaseUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getFirebaseDatabaseUrl())) {
					systemConfigDto.setFirebaseDatabaseUrl(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.FIREBASE_PROJECT_ID.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setFirebaseProjectId(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getFirebaseProjectId())) {
					systemConfigDto.setFirebaseProjectId(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.FIREBASE_STORAGE_BUCKET.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setFirebaseStoreBucket(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getFirebaseStoreBucket())) {
					systemConfigDto.setFirebaseStoreBucket(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.FIREBASE_MESSAGE_ID.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setFirebaseMessageId(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getFirebaseMessageId())) {
					systemConfigDto.setFirebaseMessageId(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.FIREBASE_APP_ID.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setFirebaseAppId(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getFirebaseAppId())) {
					systemConfigDto.setFirebaseAppId(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.FIREBASE_MEASUREMENT_ID.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setFirebaseMeasurementId(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getFirebaseMeasurementId())) {
					systemConfigDto.setFirebaseMeasurementId(ss.getSettingValue());
				}
			}
		}

		if (AppSystemSettingKey.FIREBASE_PUBLIC_VAPID_KEY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setFirebasePublicVapidKey(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getFirebasePublicVapidKey())) {
					systemConfigDto.setFirebasePublicVapidKey(ss.getSettingValue());
				}
			}
		}

		// FLAG_FIREBASE
		if (AppSystemSettingKey.FIREBASE_FLAG.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				int flagPushNotif = Integer.parseInt(ss.getSettingValue());
				if (flagPushNotif == 1) {
					systemConfigDto.setFlagFirebase(true);
				} else {
					systemConfigDto.setFlagFirebase(false);
				}
			}
		}

		// MOBILE_APP_VERSION
		if (AppSystemSettingKey.UPDATE_APP_VERSION_ANDROID.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setAndroidAppVersion(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getAndroidAppVersion())) {
					systemConfigDto.setAndroidAppVersion(ss.getSettingValue());
				}
			}
		}
		if (AppSystemSettingKey.UPDATE_APP_VERSION_IOS.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setIosAppVersion(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getIosAppVersion())) {
					systemConfigDto.setIosAppVersion(ss.getSettingValue());
				}
			}
		}
		if (AppSystemSettingKey.UPDATE_APP_VERSION_TYPE.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setUpdateVersionAppType(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getUpdateVersionAppType())) {
					systemConfigDto.setUpdateVersionAppType(ss.getSettingValue());
				}
			}
		}
		if (ss.getSettingKey() != null && ss.getSettingKey().contains(AppSystemSettingKey.UPDATE_APP_VERSION_MESSAGE)
				&& ss.getSettingValue() != null) {
			Map<String, String> messageMap = systemConfigDto.getUpdateVersionAppMessages();
			if (null == messageMap) {
				messageMap = new HashMap<>();
				systemConfigDto.setUpdateVersionAppMessages(messageMap);
			}
			String key = ss.getSettingKey().substring(ss.getSettingKey().lastIndexOf(ConstantCore.UNDERSCORE) + 1);
			String value = messageMap.get(key);
			if (companyId.equals(ss.getCompanyId())) {
				messageMap.put(key, ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(value)) {
					messageMap.put(key, ss.getSettingValue());
				}
			}
		}

		if (SystemSettingKey.UPLOAD_FILE_TYPE.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setUploadFileType(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getUploadFileType())) {
					systemConfigDto.setUploadFileType(ss.getSettingValue());
				}
			}
		}

		if (SystemSettingKey.UPLOAD_FILE_SIZE_MAIN_FILE.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setUploadFileSizeMainFile(Integer.parseInt(ss.getSettingValue()));
			} else {
				if (null == systemConfigDto.getUploadFileSizeMainFile()) {
					systemConfigDto.setUploadFileSizeMainFile(Integer.parseInt(ss.getSettingValue()));
				}
			}
		}

		if (SystemSettingKey.UPLOAD_FILE_SIZE_ATTACH.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setUploadFileSizeAttach(Integer.parseInt(ss.getSettingValue()));
			} else {
				if (null == systemConfigDto.getUploadFileSizeAttach()) {
					systemConfigDto.setUploadFileSizeAttach(Integer.parseInt(ss.getSettingValue()));
				}
			}
		}

		if (AppSystemSettingKey.NUM_DATE_DELETE_NOTIFI.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setNumDateDeleteNotify(Integer.parseInt(ss.getSettingValue()));
			} else {
				if (null == systemConfigDto.getUploadFileSizeAttach()) {
					systemConfigDto.setNumDateDeleteNotify(Integer.parseInt(ss.getSettingValue()));
				}
			}
		}
		if (SystemSettingKey.AWS_EMAIL_SENDER.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setEmailSenderAws(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getEmailSenderAws())) {
					systemConfigDto.setEmailSenderAws(ss.getSettingValue());
				}
			}
		 }
		 if (SystemSettingKey.AWS_EMAIL_REGION.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setEmailRegionAws(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getEmailRegionAws())) {
					systemConfigDto.setEmailRegionAws(ss.getSettingValue());
				}
			}
		 }
		 if (SystemSettingKey.AWS_REPO_URL_ATTACH_FILE.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setRepoAttachFileAws(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getRepoAttachFileAws())) {
					systemConfigDto.setRepoAttachFileAws(ss.getSettingValue());
				}
			}
		}
		 if (SystemSettingKey.AWS_URL_DEFAULT.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setUrlDefaultAws(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getUrlDefaultAws())) {
					systemConfigDto.setUrlDefaultAws(ss.getSettingValue());
				}
			}
		 }
		 if (SystemSettingKey.AWS_ACCESS_KEY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setAccessKeyAws(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getAccessKeyAws())) {
					systemConfigDto.setAccessKeyAws(ss.getSettingValue());
				}
			}
		 }
		 if (SystemSettingKey.AWS_SECRET_KEY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			try {
				if (companyId.equals(ss.getCompanyId())) {
					systemConfigDto.setSecretKeyAws(JCanaryPasswordUtil.decryptString(ss.getSettingValue()));
				} else {
					if (StringUtils.isBlank(systemConfigDto.getSecretKeyAws())) {
						systemConfigDto.setSecretKeyAws(JCanaryPasswordUtil.decryptString(ss.getSettingValue()));
					}
				}
			} catch (Exception e) {
				messages.add(new Message(Message.ERROR, "Email Password : " + String.valueOf(e.getMessage())));
			}
		}
		if (SystemSettingKey.SEND_SMS_URL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setSendSmsUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getSendSmsUrl())) {
					systemConfigDto.setSendSmsUrl(ss.getSettingValue());
				}
			}
		}
		if (SystemSettingKey.SENDER_SMS.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setSenderSms(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getSenderSms())) {
					systemConfigDto.setSenderSms(ss.getSettingValue());
				}
			}
		}
		if (SystemSettingKey.SMS_TYPE.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setSmsType(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getSmsType())) {
					systemConfigDto.setSmsType(ss.getSettingValue());
				}
			}
		}
		if (SystemSettingKey.TYPE_OF_SENDMAIL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setTypeOfSendmail(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getTypeOfSendmail())) {
					systemConfigDto.setTypeOfSendmail(ss.getSettingValue());
				}
			}
		}
		if (SystemSettingKey.GOOGLE_MAP_API_KEY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setGoogleMapApiKey(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getGoogleMapApiKey())) {
					systemConfigDto.setGoogleMapApiKey(ss.getSettingValue());
				}
			}
		}
		if (SystemSettingKey.IIBHCMS_BASE_URL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setIibhcmsBaseUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getIibhcmsBaseUrl())) {
					systemConfigDto.setIibhcmsBaseUrl(ss.getSettingValue());
				}
			}
		}
		if (SystemSettingKey.EAPPIBPS_BASE_URL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setEappibpsBaseUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getEappibpsBaseUrl())) {
					systemConfigDto.setEappibpsBaseUrl(ss.getSettingValue());
				}
			}
		}
		if (SystemSettingKey.EAPP_BASE_URL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setEappBaseUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getEappBaseUrl())) {
					systemConfigDto.setEappBaseUrl(ss.getSettingValue());
				}
			}
		}
		if (SystemSettingKey.ERECRUIT_BASE_URL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setErecruitBaseUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getErecruitBaseUrl())) {
					systemConfigDto.setErecruitBaseUrl(ss.getSettingValue());
				}
			}
		}
		if (SystemSettingKey.CSPORTAL_BASE_URL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setCsportalBaseUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getCsportalBaseUrl())) {
					systemConfigDto.setCsportalBaseUrl(ss.getSettingValue());
				}
			}
		}
		if (SystemSettingKey.PERSONAL_INFO_SUBMITED.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setPersonalInfoSubmited(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getPersonalInfoSubmited())) {
					systemConfigDto.setPersonalInfoSubmited(ss.getSettingValue());
				}
			}
		}
		if (SystemSettingKey.QA_CODE_URL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setFaceDetectionUploadBaseUrl(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getFaceDetectionUploadBaseUrl())) {
					systemConfigDto.setFaceDetectionUploadBaseUrl(ss.getSettingValue());
				}
			}
		}

		if (SystemSettingKey.EXPIRED_TIME_NUMBER.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setExpiredTimeNumber(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getExpiredTimeNumber())) {
					systemConfigDto.setExpiredTimeNumber(ss.getSettingValue());
				}
			}
		}
		
		if (SystemSettingKey.SLOGAN_DSUCCESS.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
				systemConfigDto.setSloganDsuccess(ss.getSettingValue());
			} else {
				if (StringUtils.isBlank(systemConfigDto.getSloganDsuccess())) {
					systemConfigDto.setSloganDsuccess(ss.getSettingValue());
				}
			}
		}
		
		if (SystemSettingKey.PATH_DOMAIN_ADMIN.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
            if (companyId.equals(ss.getCompanyId())) {
                systemConfigDto.setPathDomainAdmin(ss.getSettingValue());
            } else {
                if (StringUtils.isBlank(systemConfigDto.getPathDomainAdmin())) {
                    systemConfigDto.setPathDomainAdmin(ss.getSettingValue());
                }
            }
        }
		
		if (SystemSettingKey.PATH_DOMAIN_API.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
            if (companyId.equals(ss.getCompanyId())) {
                systemConfigDto.setPathDomainApi(ss.getSettingValue());
            } else {
                if (StringUtils.isBlank(systemConfigDto.getPathDomainApi())) {
                    systemConfigDto.setPathDomainApi(ss.getSettingValue());
                }
            }
        }
		
		//base

		if (SystemSettingKey.BASE_DOMAIN_ADMIN.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
            if (companyId.equals(ss.getCompanyId())) {
                systemConfigDto.setBaseDomainAdmin(ss.getSettingValue());
            } else {
                if (StringUtils.isBlank(systemConfigDto.getBaseDomainAdmin())) {
                    systemConfigDto.setBaseDomainAdmin(ss.getSettingValue());
                }
            }
        }
		
		if (SystemSettingKey.BASE_DOMAIN_API.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
            if (companyId.equals(ss.getCompanyId())) {
                systemConfigDto.setBaseDomainApi(ss.getSettingValue());
            } else {
                if (StringUtils.isBlank(systemConfigDto.getBaseDomainApi())) {
                    systemConfigDto.setBaseDomainApi(ss.getSettingValue());
                }
            }
        }
		
		if (SystemSettingKey.REGULATION_CATEGORY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
            if (companyId.equals(ss.getCompanyId())) {
                systemConfigDto.setRegulationCategory(ss.getSettingValue());
            } else {
                if (StringUtils.isBlank(systemConfigDto.getRegulationCategory())) {
                    systemConfigDto.setRegulationCategory(ss.getSettingValue());
                }
            }
        }
		
		if (SystemSettingKey.AGENT_CAREER_CATEGORY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
            if (companyId.equals(ss.getCompanyId())) {
                systemConfigDto.setAgentCareerCategory(ss.getSettingValue());
            } else {
                if (StringUtils.isBlank(systemConfigDto.getAgentCareerCategory())) {
                    systemConfigDto.setAgentCareerCategory(ss.getSettingValue());
                }
            }
        }
		
		if (SystemSettingKey.PROCESS_REGULATIONS_LEGISLATION_GA.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
            if (companyId.equals(ss.getCompanyId())) {
                systemConfigDto.setProcessRegulationsLegislationGa(ss.getSettingValue());
            } else {
                if (StringUtils.isBlank(systemConfigDto.getAgentCareerCategory())) {
                    systemConfigDto.setProcessRegulationsLegislationGa(ss.getSettingValue());
                }
            }
        }

		if (AppSystemSettingKey.LIST_USER_ALLOW_LOGIN.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
			if (companyId.equals(ss.getCompanyId())) {
                systemConfigDto.setListUserAllowLogin(ss.getSettingValue());
            } else {
                if (StringUtils.isBlank(systemConfigDto.getListUserAllowLogin())) {
                    systemConfigDto.setListUserAllowLogin(ss.getSettingValue());
                }
            }
		}
	}

	public void updateConfigAbtract(Map<String, JcaSystemConfig> mapSetting,
			SystemSettingRepository systemSettingRepository, AppSystemConfigDto systemConfigDto) throws Exception {
		List<JcaSystemConfig> settingList = new ArrayList<>();
		JcaSystemConfig setting = null;
		// Long companyId = UserProfileUtils.getCompanyId();
		Long companyId = systemConfigDto.getCompanyId();
		Long user = UserProfileUtils.getAccountId();
		Date date = comService.getSystemDateTime();

		// OZ_REPOSITORY_URL
		String ozRepositoryUrl = systemConfigDto.getOzRepositoryUrl();
		if (!StringUtils.isBlank(ozRepositoryUrl)) {
			setting = generateSettingEntity(mapSetting, AppSystemSettingKey.OZ_REPOSITORY_URL, ozRepositoryUrl,
					companyId, user, date);
			settingList.add(setting);
		}

		// OZ_REPOSITORY_LOCAL_URL
		String ozRepositoryLocalUrl = systemConfigDto.getOzRepositoryLocalUrl();
		if (!StringUtils.isBlank(ozRepositoryLocalUrl)) {
			setting = generateSettingEntity(mapSetting, AppSystemSettingKey.OZ_REPOSITORY_LOCAL_URL,
					ozRepositoryLocalUrl, companyId, user, date);
			settingList.add(setting);
		}

		// OZ_REPOSITORY_USER
		String ozRepositoryUser = systemConfigDto.getOzRepositoryUser();
		if (!StringUtils.isBlank(ozRepositoryUser)) {
			setting = generateSettingEntity(mapSetting, AppSystemSettingKey.OZ_REPOSITORY_USER, ozRepositoryUser,
					companyId, user, date);
			settingList.add(setting);
		}

		// OZ_REPOSITORY_PASSWORD
		String ozRepositoryPassword = systemConfigDto.getOzRepositoryPassword();
		if (StringUtils.isNotBlank(ozRepositoryPassword)
				&& !CommonConstant.PASSWORD_ENCRYPT.equals(ozRepositoryPassword)) {
			setting = generateSettingEntity(mapSetting, AppSystemSettingKey.OZ_REPOSITORY_PASSWORD,
					JCanaryPasswordUtil.encryptString(ozRepositoryPassword), companyId, user, date);
			settingList.add(setting);
		}

		// ECM_REPOSITORY_DOCUMENT
		String ecmRepositoryDocument = systemConfigDto.getEcmRepositoryDocument();
		if (!StringUtils.isBlank(ozRepositoryUrl)) {
			setting = generateSettingEntity(mapSetting, AppSystemSettingKey.ECM_REPOSITORY_DOCUMENT,
					ecmRepositoryDocument, companyId, user, date);
			settingList.add(setting);
		}

		// AUTHEN_KEY
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.AUTHEN_KEY,
				encryptString(systemConfigDto.getAuthenKey()), companyId, user, date);
		settingList.add(setting);
		// DEFAULTGROUP
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.SYNC_USER_KEY,
				systemConfigDto.getDefaultGroup(), companyId, user, date);
		settingList.add(setting);

		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.INTEG_URL, systemConfigDto.getIntegUrl(),
				companyId, user, date);
		settingList.add(setting);

		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.INTEG_SECRET_PASSWORD,
				systemConfigDto.getIntegSecretPassword(), companyId, user, date);
		settingList.add(setting);

		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.INTEG_URL_PROCESS,
				systemConfigDto.getIntegUrlProcess(), companyId, user, date);
		settingList.add(setting);

		// AUTHEN_KEY_PROCESS
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.AUTHEN_KEY_PROCESS,
				encryptString(systemConfigDto.getAuthenKeyProcess()), companyId, user, date);
		settingList.add(setting);

		// HSM_URL
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.HSM_URL, systemConfigDto.getHsmUrl(), companyId,
				user, date);
		settingList.add(setting);

		// LOGIN API
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.LOGIN_API_URL, systemConfigDto.getLoginAPIUrl(),
				companyId, user, date);
		settingList.add(setting);

		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.LOGIN_API_AUTHEN_KEY,
				systemConfigDto.getLoginAPIAuthenKey(), companyId, user, date);
		settingList.add(setting);

		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.LOGIN_API_SECRET_KEY,
				systemConfigDto.getLoginAPISecretKey(), companyId, user, date);
		settingList.add(setting);

		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.LOGIN_API_HOST,
				systemConfigDto.getLoginAPIHost(), companyId, user, date);
		settingList.add(setting);

		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.LOGIN_API_KEY,
				encryptString(systemConfigDto.getLoginAPIKey()), companyId, user, date);
		settingList.add(setting);

		// NOTIFICATION
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.PUSH_NOTIFICATION,
				String.valueOf(systemConfigDto.getPushNotif() ? 1 : 0), companyId, user, date);
		settingList.add(setting);

		/*
		 * setting = generateSettingEntity(mapSetting, AppSystemSettingKey.PUSH_EMAIL,
		 * String.valueOf(systemConfigDto.getPushEmail() ? 1 : 0), companyId, user,
		 * date); settingList.add(setting);
		 */

		// FIREBASE
		this.configFireBase(user, companyId, date, setting, mapSetting
				, settingList, systemConfigDto);

		//set aws config
		this.configAWS(user, companyId, date, setting, mapSetting
				, settingList, systemConfigDto);
		// smtp config
//		this.configSMTP(user, companyId, date, setting, mapSetting
//				, settingList, systemConfigDto);
		// send otp config
//		this.configSendOtp(user, companyId, date, setting, mapSetting
//				, settingList, systemConfigDto);
		// config api
		
		
		setting = generateSettingEntity(mapSetting, SystemSettingKey.GOOGLE_MAP_API_KEY,
				systemConfigDto.getGoogleMapApiKey(), companyId, user, date);
		settingList.add(setting);

		setting = generateSettingEntity(mapSetting, SystemSettingKey.TYPE_OF_SENDMAIL,
				systemConfigDto.getTypeOfSendmail(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.EXPIRED_TIME_NUMBER,
				systemConfigDto.getExpiredTimeNumber(), companyId, user, date);
		settingList.add(setting);

		setting = generateSettingEntity(mapSetting, SystemSettingKey.SLOGAN_DSUCCESS,
				systemConfigDto.getSloganDsuccess(), companyId, user, date);
		settingList.add(setting);
		
		setting = generateSettingEntity(mapSetting, SystemSettingKey.PATH_DOMAIN_ADMIN,
                systemConfigDto.getPathDomainAdmin(), companyId, user, date);
        settingList.add(setting);
        
        setting = generateSettingEntity(mapSetting, SystemSettingKey.PATH_DOMAIN_API,
                systemConfigDto.getPathDomainApi(), companyId, user, date);
        settingList.add(setting);

		setting = generateSettingEntity(mapSetting, SystemSettingKey.BASE_DOMAIN_ADMIN,
                systemConfigDto.getBaseDomainAdmin(), companyId, user, date);
        settingList.add(setting);
        
        setting = generateSettingEntity(mapSetting, SystemSettingKey.BASE_DOMAIN_API,
                systemConfigDto.getBaseDomainApi(), companyId, user, date);
        settingList.add(setting);
        
        setting = generateSettingEntity(mapSetting, SystemSettingKey.REGULATION_CATEGORY,
                systemConfigDto.getRegulationCategory(), companyId, user, date);
        settingList.add(setting);
        
        setting = generateSettingEntity(mapSetting, SystemSettingKey.AGENT_CAREER_CATEGORY,
                systemConfigDto.getAgentCareerCategory(), companyId, user, date);
        settingList.add(setting);
        
        setting = generateSettingEntity(mapSetting, SystemSettingKey.PROCESS_REGULATIONS_LEGISLATION_GA,
                systemConfigDto.getProcessRegulationsLegislationGa(), companyId, user, date);
        settingList.add(setting);
		// MOBILE_APP_VERSION
		// clear refresh token
		if (UpdateAppVersionTypeEnum.MANDATORY.getValue().equals(systemConfigDto.getUpdateVersionAppType())) {
//            String oldIosVersion = systemConfig.getConfig(AppSystemSettingKey.UPDATE_APP_VERSION_IOS, null);
//            if (null != oldIosVersion && !oldIosVersion.equals(systemConfigDto.getIosAppVersion())) {
//                refreshTokenService.clearRefreshToken(MobileOSEnum.IOS.toString(), oldIosVersion);
//            }
//            String oldAndroidVersion = systemConfig.getConfig(AppSystemSettingKey.UPDATE_APP_VERSION_ANDROID, null);
//            if (null != oldAndroidVersion && !oldAndroidVersion.equals(systemConfigDto.getAndroidAppVersion())) {
//                refreshTokenService.clearRefreshToken(MobileOSEnum.ANDROID.toString(), oldAndroidVersion);
//            }
		}

		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.UPDATE_APP_VERSION_ANDROID,
				systemConfigDto.getAndroidAppVersion(), companyId, user, date);
//        if( null == setting.getId() ) {
//            setting.setCompanyId(null);
//            settingList.add(setting);
//        } else {
//            systemSettingRepository.updatedByKey(SystemSettingKey.UPDATE_APP_VERSION_ANDROID, systemConfigDto.getAndroidAppVersion());
//        }
//        
//        setting = generateSettingEntity(mapSetting, AppSystemSettingKey.UPDATE_APP_VERSION_IOS, systemConfigDto.getIosAppVersion(), companyId, user, date);
//        if( null == setting.getId() ) {
//            setting.setCompanyId(null);
//            settingList.add(setting);
//        } else {
//            systemSettingRepository.updatedByKey(SystemSettingKey.UPDATE_APP_VERSION_IOS, systemConfigDto.getIosAppVersion());
//        }
//        
//        setting = generateSettingEntity(mapSetting, AppSystemSettingKey.UPDATE_APP_VERSION_TYPE, systemConfigDto.getUpdateVersionAppType(), companyId, user, date);
//        if( null == setting.getId() ) {
//            setting.setCompanyId(null);
//            settingList.add(setting);
//        } else {
//            systemSettingRepository.updatedByKey(SystemSettingKey.UPDATE_APP_VERSION_TYPE, systemConfigDto.getUpdateVersionAppType());
//        }
//        
//        List<LanguageDto> languageList = languageService.getLanguageDtoList();
//        for(LanguageDto lang : languageList) {
//            String langCode = lang.getCode();
//            String key = SystemSettingKey.UPDATE_APP_VERSION_MESSAGE.concat(ConstantCore.UNDERSCORE).concat(langCode);
//            String value = systemConfigDto.getUpdateVersionAppMessages().get(langCode);
//            
//            setting = generateSettingEntity(mapSetting, key, value, companyId, user, date);
//            if( null == setting.getId() ) {
//                setting.setCompanyId(null);
//                settingList.add(setting);
//            } else {
//                systemSettingRepository.updatedByKey(key, value);
//            }
//        };

		// FLAG_FIREBASE
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.FIREBASE_FLAG,
				String.valueOf(systemConfigDto.isFlagFirebase() ? 1 : 0), companyId, user, date);
		settingList.add(setting);

		String uploadFileType = systemConfigDto.getUploadFileType();
		if (!StringUtils.isBlank(uploadFileType)) {
			setting = generateSettingEntity(mapSetting, AppSystemSettingKey.UPLOAD_FILE_TYPE, uploadFileType, companyId,
					user, date);
			settingList.add(setting);
		}

		if (!StringUtils.isBlank(uploadFileType)) {
			setting = generateSettingEntity(mapSetting, AppSystemSettingKey.UPLOAD_FILE_SIZE_MAIN_FILE,
					String.valueOf(systemConfigDto.getUploadFileSizeMainFile()), companyId, user, date);
			settingList.add(setting);
		}

		if (!StringUtils.isBlank(uploadFileType)) {
			setting = generateSettingEntity(mapSetting, AppSystemSettingKey.UPLOAD_FILE_SIZE_ATTACH,
					String.valueOf(systemConfigDto.getUploadFileSizeAttach()), companyId, user, date);
			settingList.add(setting);
		}

		Integer numDateDeleteNotif = systemConfigDto.getNumDateDeleteNotify();
		if (null != numDateDeleteNotif) {
			setting = generateSettingEntity(mapSetting, AppSystemSettingKey.NUM_DATE_DELETE_NOTIFI,
					String.valueOf(systemConfigDto.getNumDateDeleteNotify()), companyId, user, date);
			settingList.add(setting);
		}

		// LIST_USER_ALLOW_LOGIN
		String listUserAllowLogin = systemConfigDto.getListUserAllowLogin();
		if (!StringUtils.isBlank(listUserAllowLogin)) {
			setting = generateSettingEntity(mapSetting, AppSystemSettingKey.LIST_USER_ALLOW_LOGIN, listUserAllowLogin,
					companyId, user, date);
			settingList.add(setting);
		}

		setting = generateSettingEntity(mapSetting, SystemSettingKey.QA_CODE_URL,
				systemConfigDto.getFaceDetectionUploadBaseUrl(), companyId, user, date);
		settingList.add(setting);

		// Save entity
//        systemSettingRepository.save(settingList);
		if (CommonCollectionUtil.isNotEmpty(settingList)) {
			for (JcaSystemConfig jcaSystemConfig : settingList) {
				if (null != this.getJcaSystemConfigByCompanyAndKey(companyId, jcaSystemConfig.getSettingKey())) {
					systemSettingRepository.update(jcaSystemConfig);
				} else {
					systemSettingRepository.create(jcaSystemConfig);
				}
			}
		}
	}
	private void configApi(Long user, Long companyId, Date date, JcaSystemConfig setting, Map<String, JcaSystemConfig> mapSetting
			, List<JcaSystemConfig> settingList, AppSystemConfigDto systemConfigDto){
		setting = generateSettingEntity(mapSetting, SystemSettingKey.IIBHCMS_BASE_URL,
				systemConfigDto.getIibhcmsBaseUrl(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.EAPPIBPS_BASE_URL,
				systemConfigDto.getEappibpsBaseUrl(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.EAPP_BASE_URL,
				systemConfigDto.getEappBaseUrl(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.ERECRUIT_BASE_URL,
				systemConfigDto.getErecruitBaseUrl(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.CSPORTAL_BASE_URL,
				systemConfigDto.getCsportalBaseUrl(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.PERSONAL_INFO_SUBMITED,
				systemConfigDto.getPersonalInfoSubmited(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.QA_CODE_URL,
				systemConfigDto.getFaceDetectionUploadBaseUrl(), companyId, user, date);
		settingList.add(setting);
	}
	
	private void configSendOtp(Long user, Long companyId, Date date, JcaSystemConfig setting, Map<String, JcaSystemConfig> mapSetting
			, List<JcaSystemConfig> settingList, AppSystemConfigDto systemConfigDto){
		setting = generateSettingEntity(mapSetting, SystemSettingKey.SEND_SMS_URL,
				systemConfigDto.getSendSmsUrl(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.SENDER_SMS,
				systemConfigDto.getSenderSms(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.SMS_TYPE,
				systemConfigDto.getSmsType(), companyId, user, date);
		settingList.add(setting);
	}
	private void configSMTP(Long user, Long companyId, Date date, JcaSystemConfig setting, Map<String, JcaSystemConfig> mapSetting
			, List<JcaSystemConfig> settingList, AppSystemConfigDto systemConfigDto){
		setting = generateSettingEntity(mapSetting, SystemSettingKey.SMTP_EMAIL_HOST,
				systemConfigDto.getEmailHostSmtp(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.SMTP_EMAIL_PORT,
				systemConfigDto.getEmailPortSmtp(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.SMTP_EMAIL_DEFAULT,
				systemConfigDto.getEmailDefaultSmtp(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.SMTP_EMAIL_PORT_INBOX,
				systemConfigDto.getEmailPortInbox().toString(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.SMTP_EMAIL_PASSWORD,
				systemConfigDto.getEmailPasswordSmtp(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.SMTP_SEND_EMAIL_TYPE_DEFAULT,
				systemConfigDto.getSendEmailTypeDefaultSmtp().toString(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.SMTP_EMAIL_DEFAULT_NAME,
				systemConfigDto.getEmailDefaultName(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.SMTP_REPO_URL_ATTACH_FILE,
				systemConfigDto.getRepoAttachFileSmtp(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.SMTP_URL_DEFAULT,
				systemConfigDto.getUrlDefaultSmtp(), companyId, user, date);
		settingList.add(setting);
	}
	private void configAWS(Long user, Long companyId, Date date, JcaSystemConfig setting, Map<String, JcaSystemConfig> mapSetting
			, List<JcaSystemConfig> settingList, AppSystemConfigDto systemConfigDto){
		setting = generateSettingEntity(mapSetting, SystemSettingKey.AWS_ACCESS_KEY,
				systemConfigDto.getAccessKeyAws(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.AWS_SECRET_KEY,
				systemConfigDto.getSecretKeyAws(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.AWS_EMAIL_SENDER,
				systemConfigDto.getEmailSenderAws(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.AWS_EMAIL_REGION,
				systemConfigDto.getEmailRegionAws(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.AWS_REPO_URL_ATTACH_FILE,
				systemConfigDto.getRepoUrlAttachFile(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, SystemSettingKey.AWS_URL_DEFAULT,
				systemConfigDto.getUrlDefaultAws(), companyId, user, date);
		settingList.add(setting);
	}
	private void configFireBase(Long user, Long companyId, Date date, JcaSystemConfig setting, Map<String, JcaSystemConfig> mapSetting
			, List<JcaSystemConfig> settingList, AppSystemConfigDto systemConfigDto) {
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.FIREBASE_URL, systemConfigDto.getFirebaseUrl(),
				companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.FIREBASE_AUTHEN_KEY,
				systemConfigDto.getFirebaseAuthenKey(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.FIREBASE_WEB_API_KEY,
				systemConfigDto.getFirebaseWebApiKey(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.FIREBASE_AUTH_DOMAIN,
				systemConfigDto.getFirebaseAuthDomain(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.FIREBASE_DATABASE_URL,
				systemConfigDto.getFirebaseDatabaseUrl(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.FIREBASE_PROJECT_ID,
				systemConfigDto.getFirebaseProjectId(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.FIREBASE_STORAGE_BUCKET,
				systemConfigDto.getFirebaseStoreBucket(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.FIREBASE_MESSAGE_ID,
				systemConfigDto.getFirebaseMessageId(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.FIREBASE_APP_ID,
				systemConfigDto.getFirebaseAppId(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.FIREBASE_MEASUREMENT_ID,
				systemConfigDto.getFirebaseMeasurementId(), companyId, user, date);
		settingList.add(setting);
		setting = generateSettingEntity(mapSetting, AppSystemSettingKey.FIREBASE_PUBLIC_VAPID_KEY,
				systemConfigDto.getFirebasePublicVapidKey(), companyId, user, date);
		settingList.add(setting);
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see vn.com.unit.mbal.service.SystemConfigEP2PService#buildSystemConfigEP2P()
	 */
	@Override
	public AppSystemConfigDto buildSystemConfigForcs(AppSystemConfigDto systemConfigEP2PDto, Long companyId) {
		AppSystemConfigDto configDto = buildSystemConfig(systemConfigEP2PDto, companyId);
		return configDto;
	}

	@Override
	@Transactional
	public void updateSystemConfigForcs(AppSystemConfigDto systemConfigDto) throws Exception {
		updateSystemConfig(systemConfigDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vn.com.unit.mbal.service.AppSystemConfigService#connectToOZRepository()
	 */
	@Override
	public ResMapObject connectToOZRepository() {
		ResMapObject resObj = new ResMapObject();
		String ozserverUrl = systemConfig.getConfig(AppSystemSettingKey.OZ_REPOSITORY_LOCAL_URL) + AppUrlConst.SERVER;
		String ozserverUser = systemConfig.getConfig(AppSystemSettingKey.OZ_REPOSITORY_USER);
		String ozserverPassword = systemConfig.getConfigDecrypted(AppSystemSettingKey.OZ_REPOSITORY_PASSWORD);

		// Create RestTemplate
		RestTemplate restTemplate = new RestTemplate();
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(120000);
		requestFactory.setReadTimeout(120000);
		restTemplate.setRequestFactory(requestFactory);

		// Create HttpEntity
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> requestObj = new LinkedMultiValueMap<>();
		requestObj.add("ozadminlogin", "true");
		requestObj.add("oz_id", ozserverUser);
		requestObj.add("oz_pass", ozserverPassword);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(requestObj,
				headers);

		// Call API login
		try {
			ResponseEntity<String> result = restTemplate.exchange(ozserverUrl, HttpMethod.POST, entity, String.class);
			resObj.setStatus(APIStatus.SUCCESS.toString());
			Map<String, Object> resultObj = new HashMap<>();
			resultObj.put("result", result.getBody());
			resObj.setResultObj(resultObj);
		} catch (Exception e) {
			String lang = systemConfig.getConfig(SystemConfig.LANGUAGE_DEFAULT);
			resObj.setObjErrorsWithErrorMessage(ExecMessage.getErrorMessage(msg, "B102", lang, e.getMessage()));
		}
		return resObj;
	}

	private String encryptString(String value) throws Exception {
		if (StringUtils.isNotBlank(value)) {
			value = JCanaryPasswordUtil.encryptString(value);
		}
		return value;
	}

	/**
	 * Validate System Configure
	 * 
	 * @param systemConfigDto
	 * @param locale
	 * @return MessageList
	 * @author trieunh
	 */
	@Override
	public MessageList validateSystemConfig(AppSystemConfigDto systemConfigDto, Locale locale) {
		MessageList messageLst = new MessageList(Message.ERROR);
		// Check fromYear and toYear
		Integer fromYear = systemConfigDto.getFromYear();
		Integer toYear = systemConfigDto.getToYear();
		if (fromYear != null && toYear != null && fromYear >= toYear) {
			String msgError = msg.getMessage("message.save.fail.formyeartoyear", null, locale);
			messageLst.add(msgError);
		}
		return messageLst;
	}

	/**
	 * Build dto for System Configure
	 * 
	 * @return SystemConfigDto
	 * @author trieunh
	 */
	@Override
	public AppSystemConfigDto buildSystemConfig(AppSystemConfigDto systemConfigDto, Long companyId) {

		MessageList messageList = new MessageList();
		List<Message> messages = new ArrayList<>();

		// Merge config
//      systemSettingRepository.mergeSystemSetting(companyId);
		this.mergeSystemSetting(companyId);

		List<JcaSystemConfig> listSystemSetting = systemSettingRepository.findByCompanyId(companyId);
		systemConfigDto.setCompanyId(companyId);
		for (JcaSystemConfig ss : listSystemSetting) {
			try {
				// PAGING_SIZE
				if (SystemConfig.PAGING_SIZE.equals(ss.getSettingKey())) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setPageSize(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getPageSize())) {
							systemConfigDto.setPageSize(ss.getSettingValue());
						}
					}
					continue;
				}

				// DATE_PATTERN
				if (SystemConfig.DATE_PATTERN.equals(ss.getSettingKey())) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setDatePattern(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getDatePattern())) {
							systemConfigDto.setDatePattern(ss.getSettingValue());
						}
					}
					continue;
				}

				// REPO_UPLOADED_TEMPLATE
				if (SystemConfig.REPO_UPLOADED_TEMPLATE.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setRepoUploadCommon(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getRepoUploadCommon())) {
							systemConfigDto.setRepoUploadCommon(ss.getSettingValue());
						}
					}
					continue;
				}

				// REPO_UPLOADED_TEMP
				if (SystemConfig.REPO_UPLOADED_TEMP.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setRepoUploadTemp(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getRepoUploadTemp())) {
							systemConfigDto.setRepoUploadTemp(ss.getSettingValue());
						}
					}
					continue;
				}

				// SYS_CONFIG_SYNC
				if (SystemConfig.SYS_CONFIG_SYNC.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setSystemConfigSync(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getRepoUploadTemp())) {
							systemConfigDto.setSystemConfigSync(null);
						}
					}
					continue;
				}

				// REPO_UPLOADED_MAIN
				if (SystemConfig.REPO_UPLOADED_MAIN.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setRepoUploadMain(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getRepoUploadMain())) {
							systemConfigDto.setRepoUploadMain(ss.getSettingValue());
						}
					}
					continue;
				}

				// TEMP_FOLDER
				if (SystemConfig.TEMP_FOLDER.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setTempFolder(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getTempFolder())) {
							systemConfigDto.setTempFolder(ss.getSettingValue());
						}
					}
					continue;
				}

				// REPO_URL_TEMPLATE_EMAIL
				if (SystemConfig.REPO_URL_TEMPLATE_EMAIL.equals(ss.getSettingKey())
						&& ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setRepoUrlTemplateEmail(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getRepoUrlTemplateEmail())) {
							systemConfigDto.setRepoUrlTemplateEmail(ss.getSettingValue());
						}
					}
					continue;
				}

				// PhatVT time lock account
				// TIME_LOCK
				if (SystemConfig.TIME_LOCK.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setTimeLock(Integer.parseInt(ss.getSettingValue()));
					} else {
						if (null == systemConfigDto.getTimeLock()) {
							systemConfigDto.setTimeLock(Integer.parseInt(ss.getSettingValue()));
						}
					}
					continue;
				}

				// PhatVT expired password
				// EXPIRED_PASSWORD
				if (SystemConfig.EXPIRED_PASSWORD.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setExpiredPassword(Integer.parseInt(ss.getSettingValue()));
					} else {
						if (null == systemConfigDto.getExpiredPassword()) {
							systemConfigDto.setExpiredPassword(Integer.parseInt(ss.getSettingValue()));
						}
					}
					continue;
				}

				// PhatVT Minimum Password Age
				// MIN_PASSWORD_AGE
				if (SystemConfig.MIN_PASSWORD_AGE.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setMinPasswordAge(Integer.parseInt(ss.getSettingValue()));
					} else {
						if (null == systemConfigDto.getMinPasswordAge()) {
							systemConfigDto.setMinPasswordAge(Integer.parseInt(ss.getSettingValue()));
						}
					}
					continue;
				}

				// PhatVT getConfig Password isStrong
				// FLAG_UPPER_CASE
				if (SystemConfig.FLAG_UPPER_CASE.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (StringUtils.isBlank(ss.getSettingValue())) {
						ss.setSettingValue("0");
					}
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setFlagUpperCase(Integer.parseInt(ss.getSettingValue()));
						if (systemConfigDto.getFlagUpperCase() == 1) {
							systemConfigDto.getFieldValues().add(ComplexityEnum.UPPER.name());
						}
					} else {
						if (null == systemConfigDto.getFlagUpperCase()) {
							systemConfigDto.setFlagUpperCase(Integer.parseInt(ss.getSettingValue()));
							if (systemConfigDto.getFlagUpperCase() == 1) {
								systemConfigDto.getFieldValues().add(ComplexityEnum.UPPER.name());
							}
						}
					}
					continue;
				}

				// FLAG_LOWER_CASE
				if (SystemConfig.FLAG_LOWER_CASE.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (StringUtils.isBlank(ss.getSettingValue())) {
						ss.setSettingValue("0");
					}
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setFlagLowerCase(Integer.parseInt(ss.getSettingValue()));
						if (systemConfigDto.getFlagLowerCase() == 1) {
							systemConfigDto.getFieldValues().add(ComplexityEnum.LOWER.name());
						}
					} else {
						if (null == systemConfigDto.getFlagLowerCase()) {
							systemConfigDto.setFlagLowerCase(Integer.parseInt(ss.getSettingValue()));
							if (systemConfigDto.getFlagLowerCase() == 1) {
								systemConfigDto.getFieldValues().add(ComplexityEnum.LOWER.name());
							}
						}
					}
					continue;
				}

				// FLAG_NUMBER_CASE
				if (SystemConfig.FLAG_NUMBER_CASE.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (StringUtils.isBlank(ss.getSettingValue())) {
						ss.setSettingValue("0");
					}
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setFlagNumberCase(Integer.parseInt(ss.getSettingValue()));
						if (systemConfigDto.getFlagNumberCase() == 1) {
							systemConfigDto.getFieldValues().add(ComplexityEnum.NUMBER.name());
						}
					} else {
						if (null == systemConfigDto.getFlagNumberCase()) {
							systemConfigDto.setFlagNumberCase(Integer.parseInt(ss.getSettingValue()));
							if (systemConfigDto.getFlagNumberCase() == 1) {
								systemConfigDto.getFieldValues().add(ComplexityEnum.NUMBER.name());
							}
						}
					}
					continue;
				}

				// FLAG_SPECIAL_CASE
				if (SystemConfig.FLAG_SPECIAL_CASE.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (StringUtils.isBlank(ss.getSettingValue())) {
						ss.setSettingValue("0");
					}
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setFlagSpecialCase(Integer.parseInt(ss.getSettingValue()));
						if (systemConfigDto.getFlagSpecialCase() == 1) {
							systemConfigDto.getFieldValues().add(ComplexityEnum.SPECIAL.name());
						}
					} else {
						if (null == systemConfigDto.getFlagSpecialCase()) {
							systemConfigDto.setFlagSpecialCase(Integer.parseInt(ss.getSettingValue()));
							if (systemConfigDto.getFlagSpecialCase() == 1) {
								systemConfigDto.getFieldValues().add(ComplexityEnum.SPECIAL.name());
							}
						}
					}
					continue;
				}

				// FLAG_COMPLEXITY
				if (SystemConfig.FLAG_COMPLEXITY.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (StringUtils.isBlank(ss.getSettingValue())) {
						ss.setSettingValue("0");
					}
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setFlagComplexity(Integer.parseInt(ss.getSettingValue()));
						if (systemConfigDto.getFlagComplexity() == 1) {
							systemConfigDto.setComplexity(true);
						} else {
							systemConfigDto.setComplexity(false);
						}
					} else {
						if (null == systemConfigDto.getFlagComplexity()) {
							systemConfigDto.setFlagComplexity(Integer.parseInt(ss.getSettingValue()));
							if (systemConfigDto.getFlagComplexity() == 1) {
								systemConfigDto.setComplexity(true);
							} else {
								systemConfigDto.setComplexity(false);
							}
						}
					}
					continue;
				}

				// FAILED_LOGIN_COUNT
				if (SystemConfig.FAILED_LOGIN_COUNT.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setFailedLoginCount(Integer.parseInt(ss.getSettingValue()));
					} else {
						if (null == systemConfigDto.getFailedLoginCount()) {
							systemConfigDto.setFailedLoginCount(Integer.parseInt(ss.getSettingValue()));
						}
					}
					continue;
				}

				// PhatVT time lock account
				// FLAG_AUTO_UNLOCK
				if (SystemConfig.FLAG_AUTO_UNLOCK.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (StringUtils.isBlank(ss.getSettingValue())) {
						ss.setSettingValue("0");
					}
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setFlagAutoUnlock(Integer.parseInt(ss.getSettingValue()));
						if (systemConfigDto.getFlagAutoUnlock() == 1) {
							systemConfigDto.setAutoUnlock(true);
						} else {
							systemConfigDto.setAutoUnlock(false);
						}
					} else {
						if (null == systemConfigDto.getFlagAutoUnlock()) {
							systemConfigDto.setFlagAutoUnlock(Integer.parseInt(ss.getSettingValue()));
							if (systemConfigDto.getFlagAutoUnlock() == 1) {
								systemConfigDto.setAutoUnlock(true);
							} else {
								systemConfigDto.setAutoUnlock(false);
							}
						}
					}
					continue;
				}

				// PhatVT The number of times password is used
				// NUMBER_PASSWORD_USED
				if (SystemConfig.NUMBER_PASSWORD_USED.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setNumberPasswordUsed(Integer.parseInt(ss.getSettingValue()));
					} else {
						if (null == systemConfigDto.getNumberPasswordUsed()) {
							systemConfigDto.setNumberPasswordUsed(Integer.parseInt(ss.getSettingValue()));
						}
					}
					continue;
				}

				// PhatVT Minimum Password Length
				// MIN_PASSWORD_LENGTH
				if (SystemConfig.MIN_PASSWORD_LENGTH.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setMinPasswordLength(Integer.parseInt(ss.getSettingValue()));
					} else {
						if (null == systemConfigDto.getMinPasswordLength()) {
							systemConfigDto.setMinPasswordLength(Integer.parseInt(ss.getSettingValue()));
						}
					}
					continue;
				}
				// MAX_PASSWORD_LENGTH
				if (SystemConfig.MAX_PASSWORD_LENGTH.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setMaxPasswordLength(Integer.parseInt(ss.getSettingValue()));
					} else {
						if (null == systemConfigDto.getMaxPasswordLength()) {
							systemConfigDto.setMaxPasswordLength(Integer.parseInt(ss.getSettingValue()));
						}
					}
					continue;
				}
				// PhatVT first time login change password
				// FLAG_FIRST_TIME_LOGIN
				if (SystemConfig.FLAG_FIRST_TIME_LOGIN.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setFlagFirstTimeLogin(Integer.parseInt(ss.getSettingValue()));
						if (systemConfigDto.getFlagFirstTimeLogin() == 1) {
							systemConfigDto.setFirstTimeLogin(true);
						} else {
							systemConfigDto.setFirstTimeLogin(false);
						}
					} else {
						if (null == systemConfigDto.getFlagFirstTimeLogin()) {
							systemConfigDto.setFlagFirstTimeLogin(Integer.parseInt(ss.getSettingValue()));
							if (systemConfigDto.getFlagFirstTimeLogin() == 1) {
								systemConfigDto.setFirstTimeLogin(true);
							} else {
								systemConfigDto.setFirstTimeLogin(false);
							}
						}
					}
					continue;
				}

				// EMAIL_HOST
				if (SystemConfig.EMAIL_HOST.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setEmailHost(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getEmailHost())) {
							systemConfigDto.setEmailHost(ss.getSettingValue());
						}
					}
					continue;
				}

				// EMAIL_DEFAULT
				if (SystemConfig.EMAIL_DEFAULT.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setEmailDefault(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getEmailDefault())) {
							systemConfigDto.setEmailDefault(ss.getSettingValue());
						}
					}
					continue;
				}

				// EMAIL_PASSWORD
				if (SystemConfig.EMAIL_PASSWORD.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					try {
						if (companyId.equals(ss.getCompanyId())) {
							systemConfigDto.setEmailPassword(JCanaryPasswordUtil.decryptString(ss.getSettingValue()));
						} else {
							if (StringUtils.isBlank(systemConfigDto.getEmailPassword())) {
								systemConfigDto
										.setEmailPassword(JCanaryPasswordUtil.decryptString(ss.getSettingValue()));
							}
						}
					} catch (Exception e) {
						messages.add(new Message(Message.ERROR, "Email Password : " + String.valueOf(e.getMessage())));
					}
					continue;
				}

				// SEND_EMAIL_TYPE_DEFAULT
				if (SystemConfig.SEND_EMAIL_TYPE_DEFAULT.equals(ss.getSettingKey())
						&& ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setSendEmailTypeDefault(Integer.parseInt(ss.getSettingValue()));
					} else {
						if (null == systemConfigDto.getSendEmailTypeDefault()) {
							systemConfigDto.setSendEmailTypeDefault(Integer.parseInt(ss.getSettingValue()));
						}
					}
					continue;
				}

				// REPO_URL_ATTACH_FILE
				if (SystemConfig.REPO_URL_ATTACH_FILE.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setRepoAttachFile(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getRepoAttachFile())) {
							systemConfigDto.setRepoAttachFile(ss.getSettingValue());
						}
					}
					continue;
				}

				// EMAIL_PORT
				if (SystemConfig.EMAIL_PORT.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setEmailPort(Integer.parseInt(ss.getSettingValue()));
					} else {
						if (null == systemConfigDto.getEmailPort()) {
							systemConfigDto.setEmailPort(Integer.parseInt(ss.getSettingValue()));
						}
					}
					continue;
				}

				// EMAIL_PORT_INBOX
				if (SystemConfig.EMAIL_PORT_INBOX.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setEmailPortInbox(Integer.parseInt(ss.getSettingValue()));
					} else {
						if (null == systemConfigDto.getEmailPortInbox()) {
							systemConfigDto.setEmailPortInbox(Integer.parseInt(ss.getSettingValue()));
						}
					}
					continue;
				}

				// EMAIL_DEFAULT_NAME
				if (SystemConfig.EMAIL_DEFAULT_NAME.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setEmailDefaultName(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getEmailDefaultName())) {
							systemConfigDto.setEmailDefaultName(ss.getSettingValue());
						}
					}
					continue;
				}

				// URL_DEFAULT
				if (SystemConfig.URL_DEFAULT.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setUrlDefault(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getUrlDefault())) {
							systemConfigDto.setUrlDefault(ss.getSettingValue());
						}
					}
					continue;
				}

				// LDAP_DOMAIN
				if (SystemConfig.LDAP_DOMAIN.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setLdapDomain(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getLdapDomain())) {
							systemConfigDto.setLdapDomain(ss.getSettingValue());
						}
					}
					continue;
				}

				// LDAP_MAIN_GROUP
				if (SystemConfig.LDAP_MAIN_GROUP.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setLdapMainGroup(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getLdapMainGroup())) {
							systemConfigDto.setLdapMainGroup(ss.getSettingValue());
						}
					}
					continue;
				}

				// LDAP_CN_FILTER
				if (SystemConfig.LDAP_CN_FILTER.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setLdapCnFilter(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getLdapCnFilter())) {
							systemConfigDto.setLdapCnFilter(ss.getSettingValue());
						}
					}
					continue;
				}

				// LDAP_ACCOUNT_FILTER
				if (SystemConfig.LDAP_ACCOUNT_FILTER.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setLdapAccountFilter(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getLdapAccountFilter())) {
							systemConfigDto.setLdapAccountFilter(ss.getSettingValue());
						}
					}
					continue;
				}

				// LDAP_INITIAL_CONTEXT_FACTORY
				if (SystemConfig.LDAP_INITIAL_CONTEXT_FACTORY.equals(ss.getSettingKey())
						&& ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setLdapInitialContextFactory(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getLdapInitialContextFactory())) {
							systemConfigDto.setLdapInitialContextFactory(ss.getSettingValue());
						}
					}
					continue;
				}

				// LDAP_PROVIDER_URL
				if (SystemConfig.LDAP_PROVIDER_URL.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setLdapProviderUrl(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getLdapProviderUrl())) {
							systemConfigDto.setLdapProviderUrl(ss.getSettingValue());
						}
					}
					continue;
				}

				// LDAP_SECURITY_AUTHENTICATION
				if (SystemConfig.LDAP_SECURITY_AUTHENTICATION.equals(ss.getSettingKey())
						&& ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setLdapSecurityAuthentication(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getLdapSecurityAuthentication())) {
							systemConfigDto.setLdapSecurityAuthentication(ss.getSettingValue());
						}
					}
					continue;
				}

				// LDAP_SECURITY_PRINCIPAL
				if (SystemConfig.LDAP_SECURITY_PRINCIPAL.equals(ss.getSettingKey())
						&& ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setLdapSecurityPrincipal(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getLdapSecurityPrincipal())) {
							systemConfigDto.setLdapSecurityPrincipal(ss.getSettingValue());
						}
					}
					continue;
				}

				// LDAP_SECURITY_CREDENTIALS
				if (SystemConfig.LDAP_SECURITY_CREDENTIALS.equals(ss.getSettingKey())
						&& ss.getSettingValue() != null) {
					try {
						if (companyId.equals(ss.getCompanyId())) {
							systemConfigDto.setLdapSecurityCredentials(
									JCanaryPasswordUtil.decryptString(ss.getSettingValue()));
						} else {
							if (StringUtils.isBlank(systemConfigDto.getLdapSecurityCredentials())) {
								systemConfigDto.setLdapSecurityCredentials(
										JCanaryPasswordUtil.decryptString(ss.getSettingValue()));
							}
						}
					} catch (Exception e) {
						messages.add(new Message(Message.ERROR,
								"LDAP_SECURITY_CREDENTIALS Password : " + String.valueOf(e.getMessage())));
					}
					continue;
				}

				// FLAG_USED_ECM
				if (SystemConfig.FLAG_USED_ECM.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setFlagUsedECM(Integer.parseInt(ss.getSettingValue()));
						if (systemConfigDto.getFlagUsedECM() == 1) {
							systemConfigDto.setUsedECM(true);
						} else {
							systemConfigDto.setUsedECM(false);
						}
					} else {
						if (null == systemConfigDto.getFlagUsedECM()) {
							systemConfigDto.setFlagUsedECM(Integer.parseInt(ss.getSettingValue()));
							if (systemConfigDto.getFlagUsedECM() == 1) {
								systemConfigDto.setUsedECM(true);
							} else {
								systemConfigDto.setUsedECM(false);
							}
						}
					}
					continue;
				}

				// FLAG_USED_BPM
				if (SystemConfig.FLAG_USED_BPM.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setFlagUsedBPM(Integer.parseInt(ss.getSettingValue()));
						if (systemConfigDto.getFlagUsedBPM() == 1) {
							systemConfigDto.setUsedBPM(true);
						} else {
							systemConfigDto.setUsedBPM(false);
						}
					} else {
						if (null == systemConfigDto.getFlagUsedBPM()) {
							systemConfigDto.setFlagUsedBPM(Integer.parseInt(ss.getSettingValue()));
							if (systemConfigDto.getFlagUsedBPM() == 1) {
								systemConfigDto.setUsedBPM(true);
							} else {
								systemConfigDto.setUsedBPM(false);
							}
						}
					}
					continue;
				}

				// CMS CONTACT EMAIL FEEDBACK DSUCCESS
				if (SystemConfig.CMS_CONTACT_EMAIL_FEEDBACK_DSUCCESS.equals(ss.getSettingKey())) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setCmsContactEmailFeedbackDSuccess(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getCmsContactEmailFeedbackDSuccess())) {
							systemConfigDto.setCmsContactEmailFeedbackDSuccess(ss.getSettingValue());
						}
					}
					continue;
				}

				// CMS CONTACT EMAIL RECOVERY CODE
				if (SystemConfig.CMS_CONTACT_EMAIL_RECOVERY_CODE.equals(ss.getSettingKey())) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setCmsContactEmailRecoveryCode(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getCmsContactEmailRecoveryCode())) {
							systemConfigDto.setCmsContactEmailRecoveryCode(ss.getSettingValue());
						}
					}
					continue;
				}

				// CMS CONTACT EMAIL CONTRACT ASSIGNMENT
				if (SystemConfig.CMS_CONTACT_EMAIL_CONTRACT_ASSIGNMENT.equals(ss.getSettingKey())) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setCmsContactEmailContractAssignment(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getCmsContactEmailContractAssignment())) {
							systemConfigDto.setCmsContactEmailContractAssignment(ss.getSettingValue());
						}
					}
					continue;
				}

				// CMS CONTACT EMAIL TERMINATE AGENCY CONTRACT
				if (SystemConfig.CMS_CONTACT_EMAIL_TERMINATE_AGENCY_CONTRACT.equals(ss.getSettingKey())) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setCmsContactEmailTerminateAgencyContract(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getCmsContactEmailTerminateAgencyContract())) {
							systemConfigDto.setCmsContactEmailTerminateAgencyContract(ss.getSettingValue());
						}
					}
					continue;
				}

				// CMS CONTACT EMAIL PROMOTION AND DEMOTION
				if (SystemConfig.CMS_CONTACT_EMAIL_PROMOTION_AND_DEMOTION.equals(ss.getSettingKey())) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setCmsContactEmailPromotionAndDemotion(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getCmsContactEmailPromotionAndDemotion())) {
							systemConfigDto.setCmsContactEmailPromotionAndDemotion(ss.getSettingValue());
						}
					}
					continue;
				}

				// CMS CONTACT EMAIL COMMISSION AND BONUS
				if (SystemConfig.CMS_CONTACT_EMAIL_COMMISSION_AND_BONUS.equals(ss.getSettingKey())) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setCmsContactEmailCommissionAndBonus(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getCmsContactEmailCommissionAndBonus())) {
							systemConfigDto.setCmsContactEmailCommissionAndBonus(ss.getSettingValue());
						}
					}
					continue;
				}

				// CMS CONTACT EMAIL INCOME DEBT
				if (SystemConfig.CMS_CONTACT_EMAIL_INCOME_DEBT.equals(ss.getSettingKey())) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setCmsContactEmailIncomeDebt(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getCmsContactEmailIncomeDebt())) {
							systemConfigDto.setCmsContactEmailIncomeDebt(ss.getSettingValue());
						}
					}
					continue;
				}

				// CMS CONTACT EMAIL HOLD INCOME
				if (SystemConfig.CMS_CONTACT_EMAIL_HOLD_INCOME.equals(ss.getSettingKey())) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setCmsContactEmailHoldIncome(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getCmsContactEmailHoldIncome())) {
							systemConfigDto.setCmsContactEmailHoldIncome(ss.getSettingValue());
						}
					}
					continue;
				}

				// CMS CONTACT EMAIL EMULATION
				if (SystemConfig.CMS_CONTACT_EMAIL_EMULATION.equals(ss.getSettingKey())) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setCmsContactEmailEmulation(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getCmsContactEmailEmulation())) {
							systemConfigDto.setCmsContactEmailEmulation(ss.getSettingValue());
						}
					}
					continue;
				}

				// CMS CONTACT EMAIL OTHER
				if (SystemConfig.CMS_CONTACT_EMAIL_OTHER.equals(ss.getSettingKey())) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setCmsContactEmailOther(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getCmsContactEmailOther())) {
							systemConfigDto.setCmsContactEmailOther(ss.getSettingValue());
						}
					}
					continue;
				}

				// file logo
				if (SystemConfig.LOGO_LARGE.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setLogoLarge(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getLogoLarge())) {
							systemConfigDto.setLogoLarge(ss.getSettingValue());
						}
					}
					continue;
				}
				if (SystemConfig.LOGO_LARGE_REPO_ID.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setLogoLargeRepoId(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getLogoLargeRepoId())) {
							systemConfigDto.setLogoLargeRepoId(ss.getSettingValue());
						}
					}
					continue;
				}
				if (SystemConfig.LOGO_MINI.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setLogoMini(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getLogoMini())) {
							systemConfigDto.setLogoMini(ss.getSettingValue());
						}
					}
					continue;
				}
				if (SystemConfig.LOGO_MINI_REPO_ID.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setLogoMiniRepoId(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getLogoMiniRepoId())) {
							systemConfigDto.setLogoMiniRepoId(ss.getSettingValue());
						}
					}
					continue;
				}
				if (SystemConfig.POPUP.equals(ss.getSettingKey()) && ss.getSettingValue() != null) {
					if (companyId.equals(ss.getCompanyId())) {
						systemConfigDto.setPopup(ss.getSettingValue());
					} else {
						if (StringUtils.isBlank(systemConfigDto.getPopup())) {
							systemConfigDto.setPopup(ss.getSettingValue());
						}
					}
					continue;
				}

				buildConfigAbtract(systemConfigDto, ss);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
		}

		// Set list pattern date
		String lang = UserProfileUtils.getLanguage();
		List<JcaConstantDto> listPatternDate = jcaConstantService
				.getListJcaConstantDtoByGroupCodeAndLang(ConstantDisplayType.JCA_ADMIN_DATE.toString(), lang);
		List<JcaConstantDto> listPatternDateDto = new ArrayList<JcaConstantDto>();
		if (listPatternDate != null && !listPatternDate.isEmpty()) {
			for (JcaConstantDto cd : listPatternDate) {
				listPatternDateDto.add(new JcaConstantDto(cd.getId(), cd.getCode()));
				if (cd.getCode().equals(systemConfigDto.getDatePattern())) {
					systemConfigDto.setDatePattern(cd.getCode());
				}
			}
		}
		systemConfigDto.setListPatternDate(listPatternDateDto);

		// Set list pattern month
		List<JcaConstantDto> listPatternMonth = jcaConstantService
				.getListJcaConstantDtoByGroupCodeAndLang(ConstantDisplayType.JCA_ADMIN_DATE.toString(), lang);
		List<JcaConstantDto> listPatternMonthDto = new ArrayList<>();
		if (listPatternMonth != null && !listPatternMonth.isEmpty()) {
			for (JcaConstantDto cd : listPatternMonth) {
				listPatternMonthDto.add(new JcaConstantDto(cd.getId(), cd.getCode()));
				if (cd.getCode().equals(systemConfigDto.getMonthPattern())) {
					systemConfigDto.setMonthPattern(cd.getCode());
				}
			}
		}
		systemConfigDto.setListPatternMonth(listPatternMonthDto);

		messageList.setMessages(messages);
		systemConfigDto.setMessageList(messageList);

		return systemConfigDto;
	}

	/**
	 * Update System Setting
	 * 
	 * @param systemConfigDto
	 * @author trieunh
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateSystemConfig(AppSystemConfigDto systemConfigDto) throws Exception {
		List<JcaSystemConfig> settingList = new ArrayList<>();
		Long companyId = systemConfigDto.getCompanyId();
		if ("AD".equals(systemConfigDto.getChannel())) {
			companyId = 1L;
		}
		if (null == companyId) {
			systemLogsService.writeSystemLogs("SYSTEM_SETTING_LOG_ERROR", "Save system setting error",
					"Save error by company is null", null);
			return;
		}

		//
		List<JcaSystemConfig> lstSetting = this.findAllByCompanyId(companyId);
		Map<String, JcaSystemConfig> mapSetting = lstSetting.stream()
				.collect(Collectors.toMap(JcaSystemConfig::getSettingKey, systemSetting -> systemSetting));

		Long user = UserProfileUtils.getAccountId();
		Date date = comService.getSystemDateTime();
		JcaSystemConfig setting = null;

		if (systemConfigDto.isAutoUnlock()) {
			systemConfigDto.setFlagAutoUnlock(1);
		} else {
			systemConfigDto.setFlagAutoUnlock(0);
		}
		if (systemConfigDto.isComplexity()) {
			systemConfigDto.setFlagComplexity(1);
		} else {
			systemConfigDto.setFlagComplexity(0);
		}
		if (systemConfigDto.isFirstTimeLogin()) {
			systemConfigDto.setFlagFirstTimeLogin(1);
		} else {
			systemConfigDto.setFlagFirstTimeLogin(0);
		}

		if (systemConfigDto.isUsedECM()) {
			systemConfigDto.setFlagUsedECM(1);
		} else {
			systemConfigDto.setFlagUsedECM(0);
		}

		if (systemConfigDto.isUsedBPM()) {
			systemConfigDto.setFlagUsedBPM(1);
		} else {
			systemConfigDto.setFlagUsedBPM(0);
		}

		// Set param
		setParam(systemConfigDto);

		// PAGING_SIZE
		if (StringUtils.isNotBlank(systemConfigDto.getPageSize())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.PAGING_SIZE, systemConfigDto.getPageSize(),
					companyId, user, date);
			settingList.add(setting);
		}

		// DATE_PATTERN
		if (StringUtils.isNotBlank(systemConfigDto.getDatePattern())) {
			String datePattern = "dd/MM/yyyy";
			String lang = UserProfileUtils.getLanguage();
			List<JcaConstantDto> listPatternDate = jcaConstantService
					.getListJcaConstantDtoByGroupCodeAndLang(ConstantDisplayType.JCA_ADMIN_DATE.toString(), lang);
			List<JcaConstantDto> listPatternDateDto = new ArrayList<JcaConstantDto>();
			for (JcaConstantDto cd : listPatternDate) {
				listPatternDateDto.add(new JcaConstantDto(cd.getId(), cd.getCode()));
				if (systemConfigDto.getDatePattern().equals(cd.getCode())) {
					datePattern = cd.getCode();
					break;
				}
			}
			setting = generateSettingEntity(mapSetting, SystemConfig.DATE_PATTERN, datePattern, companyId, user,
					date);
			settingList.add(setting);
			systemConfigDto.setListPatternDate(listPatternDateDto);
		}

		// REPO_UPLOADED_TEMPLATE
		if (StringUtils.isNotBlank(systemConfigDto.getRepoUploadCommon())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.REPO_UPLOADED_TEMPLATE,
					systemConfigDto.getRepoUploadCommon(), companyId, user, date);
			settingList.add(setting);
		}

		// REPO_UPLOADED_TEMP
		if (StringUtils.isNotBlank(systemConfigDto.getRepoUploadTemp())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.REPO_UPLOADED_TEMP,
					systemConfigDto.getRepoUploadTemp(), companyId, user, date);
			settingList.add(setting);
		}

		// SYS_CONFIG_SYNC
		if (systemConfigDto.getSystemConfigSync() != null) {
			setting = generateSettingEntity(mapSetting, SystemConfig.SYS_CONFIG_SYNC,
					systemConfigDto.getSystemConfigSync(), companyId, user, date);
			settingList.add(setting);
		}

		// REPO_UPLOADED_MAIN
		if (StringUtils.isNotBlank(systemConfigDto.getRepoUploadMain())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.REPO_UPLOADED_MAIN,
					systemConfigDto.getRepoUploadMain(), companyId, user, date);
			settingList.add(setting);
		}

		// TEMP_FOLDER
		if (StringUtils.isNotBlank(systemConfigDto.getTempFolder())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.TEMP_FOLDER, systemConfigDto.getTempFolder(),
					companyId, user, date);
			settingList.add(setting);
		}

		// REPO_URL_TEMPLATE_EMAIL
		if (StringUtils.isNotBlank(systemConfigDto.getRepoUrlTemplateEmail())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.REPO_URL_TEMPLATE_EMAIL,
					systemConfigDto.getRepoUrlTemplateEmail(), companyId, user, date);
			settingList.add(setting);
		}

		// TIME_LOCK
		if (null != systemConfigDto.getTimeLock()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.TIME_LOCK,
					String.valueOf(systemConfigDto.getTimeLock()), companyId, user, date);
			settingList.add(setting);
		}

		// EXPIRED_PASSWORD
		if (null != systemConfigDto.getExpiredPassword()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.EXPIRED_PASSWORD,
					String.valueOf(systemConfigDto.getExpiredPassword()), companyId, user, date);
			settingList.add(setting);
		}

		// MIN_PASSWORD_AGE
		if (null != systemConfigDto.getMinPasswordAge()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.MIN_PASSWORD_AGE,
					String.valueOf(systemConfigDto.getMinPasswordAge()), companyId, user, date);
			settingList.add(setting);
		}

		// FLAG_UPPER_CASE
		if (null != systemConfigDto.getFlagUpperCase()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.FLAG_UPPER_CASE,
					String.valueOf(systemConfigDto.getFlagUpperCase()), companyId, user, date);
			settingList.add(setting);
		}

		// FLAG_LOWER_CASE
		if (null != systemConfigDto.getFlagLowerCase()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.FLAG_LOWER_CASE,
					String.valueOf(systemConfigDto.getFlagLowerCase()), companyId, user, date);
			settingList.add(setting);
		}

		// FLAG_NUMBER_CASE
		if (null != systemConfigDto.getFlagNumberCase()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.FLAG_NUMBER_CASE,
					String.valueOf(systemConfigDto.getFlagNumberCase()), companyId, user, date);
			settingList.add(setting);
		}

		// FLAG_SPECIAL_CASE
		if (null != systemConfigDto.getFlagSpecialCase()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.FLAG_SPECIAL_CASE,
					String.valueOf(systemConfigDto.getFlagSpecialCase()), companyId, user, date);
			settingList.add(setting);
		}

		// FLAG_COMPLEXITY
		if (null != systemConfigDto.getFlagComplexity()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.FLAG_COMPLEXITY,
					String.valueOf(systemConfigDto.getFlagComplexity()), companyId, user, date);
			settingList.add(setting);
		}

		// FAILED_LOGIN_COUNT
		if (null != systemConfigDto.getFailedLoginCount() && systemConfigDto.getFailedLoginCount() >= 0) {
			setting = generateSettingEntity(mapSetting, SystemConfig.FAILED_LOGIN_COUNT,
					String.valueOf(systemConfigDto.getFailedLoginCount()), companyId, user, date);
			settingList.add(setting);
		}

		// FLAG_AUTO_UNLOCK
		if (null != systemConfigDto.getFlagAutoUnlock()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.FLAG_AUTO_UNLOCK,
					String.valueOf(systemConfigDto.getFlagAutoUnlock()), companyId, user, date);
			settingList.add(setting);
		}

		// NUMBER_PASSWORD_USED
		if (null != systemConfigDto.getNumberPasswordUsed()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.NUMBER_PASSWORD_USED,
					String.valueOf(systemConfigDto.getNumberPasswordUsed()), companyId, user, date);
			settingList.add(setting);
		}

		// MIN_PASSWORD_LENGTH
		if (null != systemConfigDto.getMinPasswordLength()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.MIN_PASSWORD_LENGTH,
					String.valueOf(systemConfigDto.getMinPasswordLength()), companyId, user, date);
			settingList.add(setting);
		}
		// MAX_PASSWORD_LENGTH
		if (null != systemConfigDto.getMaxPasswordLength()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.MAX_PASSWORD_LENGTH,
					String.valueOf(systemConfigDto.getMaxPasswordLength()), companyId, user, date);
			settingList.add(setting);
		}
		// FLAG_FIRST_TIME_LOGIN
		if (null != systemConfigDto.getFlagFirstTimeLogin()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.FLAG_FIRST_TIME_LOGIN,
					String.valueOf(systemConfigDto.getFlagFirstTimeLogin()), companyId, user, date);
			settingList.add(setting);
		}

		// EMAIL_HOST
		if (StringUtils.isNotBlank(systemConfigDto.getEmailHost())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.EMAIL_HOST, systemConfigDto.getEmailHost(),
					companyId, user, date);
			settingList.add(setting);
		}

		// EMAIL_DEFAULT
		if (StringUtils.isNotBlank(systemConfigDto.getEmailDefault())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.EMAIL_DEFAULT,
					systemConfigDto.getEmailDefault(), companyId, user, date);
			settingList.add(setting);
		}

		// EMAIL_PASSWORD
		if (StringUtils.isNotBlank(systemConfigDto.getEmailPassword())
				&& !CommonConstant.PASSWORD_ENCRYPT.equals(systemConfigDto.getEmailPassword())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.EMAIL_PASSWORD,
					JCanaryPasswordUtil.encryptString(systemConfigDto.getEmailPassword()), companyId, user, date);
			settingList.add(setting);
		}

		// SEND_EMAIL_TYPE_DEFAULT
		if (null != systemConfigDto.getSendEmailTypeDefault()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.SEND_EMAIL_TYPE_DEFAULT,
					String.valueOf(systemConfigDto.getSendEmailTypeDefault()), companyId, user, date);
			settingList.add(setting);
		}

		// REPO_URL_ATTACH_FILE
		if (StringUtils.isNotBlank(systemConfigDto.getRepoAttachFile())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.REPO_URL_ATTACH_FILE,
					systemConfigDto.getRepoAttachFile(), companyId, user, date);
			settingList.add(setting);
		}

		// EMAIL_PORT
		if (null != systemConfigDto.getEmailPort()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.EMAIL_PORT,
					String.valueOf(systemConfigDto.getEmailPort()), companyId, user, date);
			settingList.add(setting);
		}

		// EMAIL_PORT_INBOX
		if (null != systemConfigDto.getEmailPortInbox()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.EMAIL_PORT_INBOX,
					String.valueOf(systemConfigDto.getEmailPortInbox()), companyId, user, date);
			settingList.add(setting);
		}

		// EMAIL_DEFAULT_NAME
		if (StringUtils.isNotBlank(systemConfigDto.getEmailDefaultName())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.EMAIL_DEFAULT_NAME,
					systemConfigDto.getEmailDefaultName(), companyId, user, date);
			settingList.add(setting);
		}

		// URL_DEFAULT
		if (StringUtils.isNotBlank(systemConfigDto.getUrlDefault())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.URL_DEFAULT, systemConfigDto.getUrlDefault(),
					companyId, user, date);
			settingList.add(setting);
		}

		// LDAP_DOMAIN
		if (StringUtils.isNotBlank(systemConfigDto.getLdapDomain())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.LDAP_DOMAIN, systemConfigDto.getLdapDomain(),
					companyId, user, date);
			settingList.add(setting);
		}

		// LDAP_MAIN_GROUP
		if (StringUtils.isNotBlank(systemConfigDto.getLdapMainGroup())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.LDAP_MAIN_GROUP,
					systemConfigDto.getLdapMainGroup(), companyId, user, date);
			settingList.add(setting);
		}

		// LDAP_CN_FILTER
		if (StringUtils.isNotBlank(systemConfigDto.getLdapCnFilter())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.LDAP_CN_FILTER,
					systemConfigDto.getLdapCnFilter(), companyId, user, date);
			settingList.add(setting);
		}

		// LDAP_ACCOUNT_FILTER
		if (StringUtils.isNotBlank(systemConfigDto.getLdapAccountFilter())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.LDAP_ACCOUNT_FILTER,
					systemConfigDto.getLdapAccountFilter(), companyId, user, date);
			settingList.add(setting);
		}

		// LDAP_INITIAL_CONTEXT_FACTORY
		if (StringUtils.isNotBlank(systemConfigDto.getLdapInitialContextFactory())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.LDAP_INITIAL_CONTEXT_FACTORY,
					systemConfigDto.getLdapInitialContextFactory(), companyId, user, date);
			settingList.add(setting);
		}

		// LDAP_PROVIDER_URL
		if (StringUtils.isNotBlank(systemConfigDto.getLdapProviderUrl())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.LDAP_PROVIDER_URL,
					systemConfigDto.getLdapProviderUrl(), companyId, user, date);
			settingList.add(setting);
		}

		// LDAP_SECURITY_AUTHENTICATION
		if (StringUtils.isNotBlank(systemConfigDto.getLdapSecurityAuthentication())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.LDAP_SECURITY_AUTHENTICATION,
					systemConfigDto.getLdapSecurityAuthentication(), companyId, user, date);
			settingList.add(setting);
		}

		// LDAP_SECURITY_PRINCIPAL
		if (StringUtils.isNotBlank(systemConfigDto.getLdapSecurityPrincipal())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.LDAP_SECURITY_PRINCIPAL,
					systemConfigDto.getLdapSecurityPrincipal(), companyId, user, date);
			settingList.add(setting);
		}

		// LDAP_SECURITY_CREDENTIALS
		if (StringUtils.isNotBlank(systemConfigDto.getLdapSecurityCredentials())
				&& !CommonConstant.PASSWORD_ENCRYPT.equals(systemConfigDto.getLdapSecurityCredentials())) {
			setting = generateSettingEntity(mapSetting, SystemConfig.LDAP_SECURITY_CREDENTIALS,
					JCanaryPasswordUtil.encryptString(systemConfigDto.getLdapSecurityCredentials()), companyId, user,
					date);
			settingList.add(setting);
		}

		// FLAG_USED_ECM
		if (null != systemConfigDto.getFlagUsedECM()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.FLAG_USED_ECM,
					String.valueOf(systemConfigDto.getFlagUsedECM()), companyId, user, date);
			settingList.add(setting);
		}

		// FLAG_USED_BPM
		if (null != systemConfigDto.getFlagUsedBPM()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.FLAG_USED_BPM,
					String.valueOf(systemConfigDto.getFlagUsedBPM()), companyId, user, date);
			settingList.add(setting);
		}

		// CMS CONTACT EMAIL FEEDBACK DSUCCESS
		if (null != systemConfigDto.getCmsContactEmailFeedbackDSuccess()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.CMS_CONTACT_EMAIL_FEEDBACK_DSUCCESS,
					String.valueOf(systemConfigDto.getCmsContactEmailFeedbackDSuccess()), companyId, user, date);
			settingList.add(setting);
		}

		// CMS CONTACT EMAIL RECOVERY CODE
		if (null != systemConfigDto.getCmsContactEmailRecoveryCode()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.CMS_CONTACT_EMAIL_RECOVERY_CODE,
					String.valueOf(systemConfigDto.getCmsContactEmailRecoveryCode()), companyId, user, date);
			settingList.add(setting);
		}

		// CMS CONTACT EMAIL CONTRACT ASSIGNMENT
		if (null != systemConfigDto.getCmsContactEmailContractAssignment()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.CMS_CONTACT_EMAIL_CONTRACT_ASSIGNMENT,
					String.valueOf(systemConfigDto.getCmsContactEmailContractAssignment()), companyId, user, date);
			settingList.add(setting);
		}

		// CMS CONTACT EMAIL TERMINATE AGENCY CONTRACT
		if (null != systemConfigDto.getCmsContactEmailTerminateAgencyContract()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.CMS_CONTACT_EMAIL_TERMINATE_AGENCY_CONTRACT,
					String.valueOf(systemConfigDto.getCmsContactEmailTerminateAgencyContract()), companyId, user, date);
			settingList.add(setting);
		}

		// CMS CONTACT EMAIL PROMOTION AND DEMOTION
		if (null != systemConfigDto.getCmsContactEmailPromotionAndDemotion()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.CMS_CONTACT_EMAIL_PROMOTION_AND_DEMOTION,
					String.valueOf(systemConfigDto.getCmsContactEmailPromotionAndDemotion()), companyId, user, date);
			settingList.add(setting);
		}

		// CMS CONTACT EMAIL COMMISSION AND BONUS
		if (null != systemConfigDto.getCmsContactEmailCommissionAndBonus()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.CMS_CONTACT_EMAIL_COMMISSION_AND_BONUS,
					String.valueOf(systemConfigDto.getCmsContactEmailCommissionAndBonus()), companyId, user, date);
			settingList.add(setting);
		}

		// CMS CONTACT EMAIL INCOME DEBT
		if (null != systemConfigDto.getCmsContactEmailIncomeDebt()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.CMS_CONTACT_EMAIL_INCOME_DEBT,
					String.valueOf(systemConfigDto.getCmsContactEmailIncomeDebt()), companyId, user, date);
			settingList.add(setting);
		}

		// CMS CONTACT EMAIL HOLD INCOME
		if (null != systemConfigDto.getCmsContactEmailHoldIncome()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.CMS_CONTACT_EMAIL_HOLD_INCOME,
					String.valueOf(systemConfigDto.getCmsContactEmailHoldIncome()), companyId, user, date);
			settingList.add(setting);
		}

		// CMS CONTACT EMAIL EMULATION
		if (null != systemConfigDto.getCmsContactEmailEmulation()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.CMS_CONTACT_EMAIL_EMULATION,
					String.valueOf(systemConfigDto.getCmsContactEmailEmulation()), companyId, user, date);
			settingList.add(setting);
		}

		// CMS CONTACT EMAIL OTHER
		if (null != systemConfigDto.getCmsContactEmailOther()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.CMS_CONTACT_EMAIL_OTHER,
					String.valueOf(systemConfigDto.getCmsContactEmailOther()), companyId, user, date);
			settingList.add(setting);
		}

		// CMS CONTACT EMAIL OTHER
		if (null != systemConfigDto.getCmsContactEmailOther()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.CMS_CONTACT_EMAIL_OTHER,
					String.valueOf(systemConfigDto.getCmsContactEmailOther()), companyId, user, date);
			settingList.add(setting);
		}
		// CSPORTAL_BASE_URL
		if (null != systemConfigDto.getCsportalBaseUrl()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.CSPORTAL_BASE_URL,
					String.valueOf(systemConfigDto.getCsportalBaseUrl()), companyId, user, date);
			settingList.add(setting);
		}
		// EAPP_BASE_URL
		if (null != systemConfigDto.getEappBaseUrl()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.EAPP_BASE_URL,
					String.valueOf(systemConfigDto.getEappBaseUrl()), companyId, user, date);
			settingList.add(setting);
		}
		// EAPPIBPS_BASE_URL
		if (null != systemConfigDto.getEappibpsBaseUrl()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.EAPPIBPS_BASE_URL,
					String.valueOf(systemConfigDto.getEappibpsBaseUrl()), companyId, user, date);
			settingList.add(setting);
		}
		// ERECRUIT_BASE_URL
		if (null != systemConfigDto.getErecruitBaseUrl()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.ERECRUIT_BASE_URL,
					String.valueOf(systemConfigDto.getErecruitBaseUrl()), companyId, user, date);
			settingList.add(setting);
		}
		// IIBHCMS_BASE_URL
		if (null != systemConfigDto.getIibhcmsBaseUrl()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.IIBHCMS_BASE_URL,
					String.valueOf(systemConfigDto.getIibhcmsBaseUrl()), companyId, user, date);
			settingList.add(setting);
		}
		// PERSONAL_INFO_SUBMITED
		if (null != systemConfigDto.getPersonalInfoSubmited()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.PERSONAL_INFO_SUBMITED,
					String.valueOf(systemConfigDto.getPersonalInfoSubmited()), companyId, user, date);
			settingList.add(setting);
		}
		// POPUP
		if (null != systemConfigDto.getPopup()) {
			setting = generateSettingEntity(mapSetting, SystemConfig.POPUP,
					String.valueOf(systemConfigDto.getPopup()), companyId, user, date);
			settingList.add(setting);
		}
		JcaRepositoryDto dto = jcaRepositoryService.getJcaRepositoryDto("REPO_MAIN", null);
		if(systemConfigDto.getFileLogoLarge() != null && StringUtils.isNotEmpty(systemConfigDto.getFileLogoLarge().getOriginalFilename())) {
			String subFilePath = SYSTEM_IMAGE_LOGO;
			MultipartFile file = systemConfigDto.getFileLogoLarge();
            String fileName = file.getOriginalFilename();
            String ext = FilenameUtils.getExtension(fileName);
            String fileNameRename = CommonDateUtil.formatDateToString(comService.getSystemDateTime(), CommonDateUtil.YYYYMMDDHHMMSS).concat(CommonConstant.DOT).concat(ext);
            byte[] imageByte = file.getBytes();
            //
            //fileupload
            FileUploadParamDto param = new FileUploadParamDto();
            param.setFileByteArray(imageByte);
            param.setFileName("logo_large_"+fileNameRename);
            param.setRename(null);
            
            param.setTypeRule(2);
            param.setDateRule(null);
            param.setSubFilePath(subFilePath);
            param.setCompanyId(UserProfileUtils.getCompanyId());
            
           param.setRepositoryId(dto.getId());
            FileUploadResultDto uploadResultDto = fileStorageService.upload(param);
            //hardcode 
            String filePath = uploadResultDto.getFilePath();
            setting = generateSettingEntity(mapSetting, SystemConfig.LOGO_LARGE,
					String.valueOf(filePath), companyId, user, date);
			settingList.add(setting);
			systemConfigDto.setLogoLarge(filePath);
			
            setting = generateSettingEntity(mapSetting, SystemConfig.LOGO_LARGE_REPO_ID,
					String.valueOf(uploadResultDto.getRepositoryId()), companyId, user, date);
			settingList.add(setting);
			systemConfigDto.setLogoLargeRepoId(uploadResultDto.getRepositoryId().toString());
		}

		if(systemConfigDto.getFileLogoMini() != null && StringUtils.isNotEmpty(systemConfigDto.getFileLogoMini().getOriginalFilename())) {
			String subFilePath = SYSTEM_IMAGE_LOGO;
			MultipartFile file = systemConfigDto.getFileLogoMini();
            String fileName = file.getOriginalFilename();
            String ext = FilenameUtils.getExtension(fileName);
            String fileNameRename = CommonDateUtil.formatDateToString(comService.getSystemDateTime(), CommonDateUtil.YYYYMMDDHHMMSS).concat(CommonConstant.DOT).concat(ext);
            byte[] imageByte = file.getBytes();
            //
            //fileupload
            FileUploadParamDto param = new FileUploadParamDto();
            param.setFileByteArray(imageByte);
            param.setFileName("logo_mini_"+fileNameRename);
            param.setRename(null);
            
            param.setTypeRule(2);
            param.setDateRule(null);
            param.setSubFilePath(subFilePath);
            param.setCompanyId(UserProfileUtils.getCompanyId());
            
           param.setRepositoryId(dto.getId());
            FileUploadResultDto uploadResultDto = fileStorageService.upload(param);
            //hardcode 
            String filePath = uploadResultDto.getFilePath();
            setting = generateSettingEntity(mapSetting, SystemConfig.LOGO_MINI,
					String.valueOf(filePath), companyId, user, date);
			settingList.add(setting);
			systemConfigDto.setLogoMini(filePath);
            setting = generateSettingEntity(mapSetting, SystemConfig.LOGO_MINI_REPO_ID,
					String.valueOf(uploadResultDto.getRepositoryId()), companyId, user, date);
			settingList.add(setting);
			systemConfigDto.setLogoMiniRepoId(uploadResultDto.getRepositoryId().toString());
		}

		// PT Tracking - checkLoginAPI - Start
		DebugLogger debugLogger = new DebugLogger();
		DebugLogger.debug("[PT Tracking] | [updateSystemConfig] | [%d] | [Begin] | [%s] | [%d] | [%s]",
				Thread.currentThread().getId(), debugLogger.getStart(), 0, "companyId: " + companyId);

		// Update Config Abtract
		updateConfigAbtract(mapSetting, systemSettingRepository, systemConfigDto);

		// PT Tracking - checkLoginAPI - End
		debugLogger.setEndTime();
		DebugLogger.debug("[PT Tracking] | [updateSystemConfig] | [%d] | [End] | [%s] | [%d] | [%s]",
				Thread.currentThread().getId(), debugLogger.getEnd(), debugLogger.getElapsedTime(),
				"companyId: " + companyId);

		// Save entity
//        systemSettingRepository.save(settingList);
		if (CommonCollectionUtil.isNotEmpty(settingList)) {
			for (JcaSystemConfig jcaSystemConfig : settingList) {
				if (null != this.getJcaSystemConfigByCompanyAndKey(companyId, jcaSystemConfig.getSettingKey())) {
					systemSettingRepository.update(jcaSystemConfig);
				} else {
					systemSettingRepository.create(jcaSystemConfig);
				}
			}
		}

		// Init system setting
		systemConfig.initSystemSetting(companyId);
//        //save item for to synchronize system data
//        Date current = comService.getSystemDateTime();
//        Date max = CommonDateUtil.setMaxTime(current);
//        List<SystemSettingSync> records = systemSettingSyncRepository.findRecordsByCondition(Boolean.TRUE, max, companyId);
//        SystemSettingSync item = new SystemSettingSync();
//        if(CollectionUtils.isNotEmpty(records)) {
//          Set<Long> ids = records.stream().map(SystemSettingSync::getId).collect(Collectors.toSet());
//          Date deleteDate = comService.getSystemDateTime();
//          String deleteBy = UserProfileUtils.getUserNameLogin();
//          systemSettingSyncRepository.updateByIds(new ArrayList<>(ids), deleteDate, deleteBy);
//        } 
//        item.setIsSync(Long.valueOf(1));
//        item.setCompanyId(companyId);
//        item.setCreatedBy(UserProfileUtils.getUserNameLogin());
//        item.setCreatedDate(current);
//        systemSettingSyncRepository.save(item);
//        InetAddress ip = InetAddress.getLocalHost();
//        StringBuilder host = new StringBuilder(ip.getHostAddress());
//      host.append(ConstantCore.COLON);
//      host.append(ip.getHostName());
//      Map<String, String> map = systemConfigurationFilter.getFilter();
//      map.put(host.toString(), new SimpleDateFormat(DateUtil.YYYYMMDDHHMMSS).format(current));
//      systemConfigurationFilter.setFilter(map);
//      Set<Long> comIds = systemConfigurationFilter.getCompanyIds();
//      comIds.add(companyId);
//      systemConfigurationFilter.setCompanyIds(comIds);
	}

	private void setParam(AppSystemConfigDto systemConfigDto) {
		if (!systemConfigDto.getFieldValues().isEmpty()) {
			for (String field : systemConfigDto.getFieldValues()) {
				if (StringUtils.equals(field, ComplexityEnum.LOWER.name())) {
					systemConfigDto.setFlagLowerCase(1);
					continue;
				}
				if (StringUtils.equals(field, ComplexityEnum.UPPER.name())) {
					systemConfigDto.setFlagUpperCase(1);
					continue;
				}
				if (StringUtils.equals(field, ComplexityEnum.NUMBER.name())) {
					systemConfigDto.setFlagNumberCase(1);
					continue;
				}
				if (StringUtils.equals(field, ComplexityEnum.SPECIAL.name())) {
					systemConfigDto.setFlagSpecialCase(1);
					continue;
				}
			}
			if (null == systemConfigDto.getFlagLowerCase()) {
				systemConfigDto.setFlagLowerCase(0);
			}
			if (null == systemConfigDto.getFlagUpperCase()) {
				systemConfigDto.setFlagUpperCase(0);
			}
			if (null == systemConfigDto.getFlagNumberCase()) {
				systemConfigDto.setFlagNumberCase(0);
			}
			if (null == systemConfigDto.getFlagSpecialCase()) {
				systemConfigDto.setFlagSpecialCase(0);
			}
		}
	}

	/**
	 * generateSettingEntity
	 * 
	 * @param key
	 * @param value
	 * @param companyId
	 * @param user
	 * @param date
	 * @return
	 * @author HungHT
	 */
	protected JcaSystemConfig generateSettingEntity(Map<String, JcaSystemConfig> mapSetting, String key, String value,
			Long companyId, Long user, Date date) {
		// Check if not exist then insert else update
		JcaSystemConfig setting = mapSetting.get(key);

		if (null == setting) {
			setting = new JcaSystemConfig();
			setting.setCompanyId(companyId);
			setting.setSettingKey(key);
			setting.setSettingValue(value);
			setting.setCreatedId(user);
			setting.setCreatedDate(date);
		} else {
			setting.setSettingValue(value);
			setting.setUpdatedId(user);
			setting.setUpdatedDate(date);
		}
		return setting;
	}

	/**
	 * mergeSystemSetting
	 * 
	 * @param companyId
	 * @author HungHT
	 */
	public void mergeSystemSetting(Long companyId) {
		if (companyId != null) {
			systemSettingRepository.mergeSystemSetting(companyId);
		}
	}
}
