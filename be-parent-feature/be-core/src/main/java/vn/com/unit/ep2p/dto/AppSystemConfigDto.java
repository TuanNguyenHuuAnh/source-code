/*******************************************************************************
 * Class        SystemConfigDto
 * Created date 2019/01/22
 * Lasted date  2019/01/22
 * Author       KhoaNA
 * Change log   2019/01/2201-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * SystemConfigDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Setter
@Getter
public class AppSystemConfigDto extends SystemConfigDto {

	private String repoUrlAttachFile;

	// OZ Repositoy
	private String ozRepositoryUrl;
	private String ozRepositoryLocalUrl;
	private String ozRepositoryUser;
	private String ozRepositoryPassword;
	private String ozRepositoryCompany;

	// ECM repository
	private String ecmRepositoryDocument;

	// integ
	private String authenKey;
	private String defaultGroup;
	private String integUrl;
	private String integSecretPassword;
	private String integUrlProcess;

	// integ process
	private String authenKeyProcess;

	// HSM_URL
	private String hsmUrl;
	private String accessKeyAws;
	private String secretKeyAws;
	private String emailSenderAws;
	private String emailRegionAws;
	private String repoAttachFileAws;
	private String urlDefaultAws;
	private String emailHostAwsSmtp;
	private String emailPortAwsSmtp;
	private String emailDefaultAwsSmtp;
	private String emailPortInboxAwsSmtp;
	private String emailPasswordAwsSmtp;
	private Integer sendEmailTypeDefaultAwsSmtp;
	private String emailDefaultNameAwsSmtp;
	private String repoAttachFileAwsSmtp;
	private String urlDefaultAwsSmtp;
	private String emailUsernameAwsSmtp;
	private String emailHostSmtp;
	private String emailPortSmtp;
	private String emailDefaultSmtp;
	private String emailPortInboxSmtp;
	private String emailPasswordSmtp;
	private Integer sendEmailTypeDefaultSmtp;
	private String emailDefaultNameSmtp;
	private String repoAttachFileSmtp;
	private String urlDefaultSmtp;
	private String smsType;
	private String senderSms;
	private String sendSmsUrl;
	private String typeOfSendmail;
	private String googleMapApiKey;
	
	// LOGIN API
	private String loginAPIUrl;
	private String loginAPIAuthenKey;
	private String loginAPISecretKey;
	private String loginAPIHost;
	private String loginAPIKey;

	// Push Notification & send mail
	private String firebaseUrl;
	private String firebaseAuthenKey;
	private Integer flagPushNotif;
	private boolean pushNotif;
	private Integer flagPushEmail;
	private boolean pushEmail;
	private String firebaseWebApiKey;
	private String firebaseAuthDomain;
	private String firebaseDatabaseUrl;
	private String firebaseProjectId;
	private String firebaseStoreBucket;
	private String firebaseMessageId;
	private String firebaseAppId;
	private String firebaseMeasurementId;
	private String firebasePublicVapidKey;
	
	/* MOBILE APP VERSION */
	private String androidAppVersion;
	private String iosAppVersion;
	private String updateVersionAppType;
	private Map<String, String> updateVersionAppMessages;
	
	private String expiredTimeNumber;
    private String logoLarge;
    private String logoLargeRepoId;
    private String logoMini;
    private String logoMiniRepoId;
    private String packageLogoLarge;
    private String packageLogoMini;
    private MultipartFile fileLogoLarge;
    private MultipartFile fileLogoMini;
    private String listUserAllowLogin;
	
	public String getExpiredTimeNumber() {
		return expiredTimeNumber;
	}

	public void setExpiredTimeNumber(String expiredTimeNumber) {
		this.expiredTimeNumber = expiredTimeNumber;
	}

	
	@Getter
	@Setter
	private String sloganDsuccess;
	
    private String pathDomainAdmin;
    
    private String pathDomainApi;
    private String baseDomainAdmin;
    
    private String baseDomainApi;
    
    private String regulationCategory;
    
    private String agentCareerCategory;
    
    private String processRegulationsLegislationGa;
	
	
	@Getter
	@Setter
	private String iibhcmsBaseUrl;
	
	@Getter
	@Setter
	private String eappibpsBaseUrl;
	
	@Getter
	@Setter
	private String eappBaseUrl;
	
	@Getter
	@Setter
	private String erecruitBaseUrl;
	
	@Getter
	@Setter
	private String csportalBaseUrl;
	
	@Getter
	@Setter
	private String personalInfoSubmited;
	
	@Getter
	@Setter
	private String faceDetectionUploadBaseUrl;
	
	private boolean flagFirebase;

	@Getter
	@Setter
	private String cmsContactEmailFeedbackDSuccess;

	@Getter
	@Setter
	private String cmsContactEmailRecoveryCode;

	@Getter
	@Setter
	private String cmsContactEmailContractAssignment;

	@Getter
	@Setter
	private String cmsContactEmailTerminateAgencyContract;

	@Getter
	@Setter
	private String cmsContactEmailPromotionAndDemotion;

	@Getter
	@Setter
	private String cmsContactEmailCommissionAndBonus;

	@Getter
	@Setter
	private String cmsContactEmailIncomeDebt;

	@Getter
	@Setter
	private String cmsContactEmailHoldIncome;

	@Getter
	@Setter
	private String cmsContactEmailEmulation;

	@Getter
	@Setter
	private String cmsContactEmailOther;

	@Getter
	@Setter
	private String popup;
	
	@Getter
	@Setter
	private String channel;

	/**
	 * getFirebasePublicVapidKey
	 * 
	 * @return
	 * @author datnv
	 */
	public String getFirebasePublicVapidKey() {
		return firebasePublicVapidKey;
	}

	/**
	 * setFirebasePublicVapidKey
	 * 
	 * @param firebasePublicVapidKey
	 * @author datnv
	 */
	public void setFirebasePublicVapidKey(String firebasePublicVapidKey) {
		this.firebasePublicVapidKey = firebasePublicVapidKey;
	}

	/**
	 * getFirebaseWebApiKey
	 * 
	 * @return
	 * @author datnv
	 */
	public String getFirebaseWebApiKey() {
		return firebaseWebApiKey;
	}

	/**
	 * setFirebaseWebApiKey
	 * 
	 * @param firebaseWebApiKey
	 * @author datnv
	 */
	public void setFirebaseWebApiKey(String firebaseWebApiKey) {
		this.firebaseWebApiKey = firebaseWebApiKey;
	}

	/**
	 * getFirebaseAuthDomain
	 * 
	 * @return
	 * @author datnv
	 */
	public String getFirebaseAuthDomain() {
		return firebaseAuthDomain;
	}

	/**
	 * setFirebaseAuthDomain
	 * 
	 * @param firebaseAuthDomain
	 * @author datnv
	 */
	public void setFirebaseAuthDomain(String firebaseAuthDomain) {
		this.firebaseAuthDomain = firebaseAuthDomain;
	}

	/**
	 * getFirebaseDatabaseUrl
	 * 
	 * @return
	 * @author datnv
	 */
	public String getFirebaseDatabaseUrl() {
		return firebaseDatabaseUrl;
	}

	/**
	 * setFirebaseDatabaseUrl
	 * 
	 * @param firebaseDatabaseUrl
	 * @author datnv
	 */
	public void setFirebaseDatabaseUrl(String firebaseDatabaseUrl) {
		this.firebaseDatabaseUrl = firebaseDatabaseUrl;
	}

	/**
	 * getFirebaseProjectId
	 * 
	 * @return
	 * @author datnv
	 */
	public String getFirebaseProjectId() {
		return firebaseProjectId;
	}

	/**
	 * setFirebaseProjectId
	 * 
	 * @param firebaseProjectId
	 * @author datnv
	 */
	public void setFirebaseProjectId(String firebaseProjectId) {
		this.firebaseProjectId = firebaseProjectId;
	}

	/**
	 * getFirebaseStoreBucket
	 * 
	 * @return
	 * @author datnv
	 */
	public String getFirebaseStoreBucket() {
		return firebaseStoreBucket;
	}

	/**
	 * setFirebaseStoreBucket
	 * 
	 * @param firebaseStoreBucket
	 * @author datnv
	 */
	public void setFirebaseStoreBucket(String firebaseStoreBucket) {
		this.firebaseStoreBucket = firebaseStoreBucket;
	}

	/**
	 * getFirebaseMessageId
	 * 
	 * @return
	 * @author datnv
	 */
	public String getFirebaseMessageId() {
		return firebaseMessageId;
	}

	/**
	 * setFirebaseMessageId
	 * 
	 * @param firebaseMessageId
	 * @author datnv
	 */
	public void setFirebaseMessageId(String firebaseMessageId) {
		this.firebaseMessageId = firebaseMessageId;
	}

	/**
	 * getFirebaseAppId
	 * 
	 * @return
	 * @author datnv
	 */
	public String getFirebaseAppId() {
		return firebaseAppId;
	}

	/**
	 * setFirebaseAppId
	 * 
	 * @param firebaseAppId
	 * @author datnv
	 */
	public void setFirebaseAppId(String firebaseAppId) {
		this.firebaseAppId = firebaseAppId;
	}

	/**
	 * getFirebaseMeasurementId
	 * 
	 * @return
	 * @author datnv
	 */
	public String getFirebaseMeasurementId() {
		return firebaseMeasurementId;
	}

	/**
	 * setFirebaseMeasurementId
	 * 
	 * @param firebaseMeasurementId
	 * @author datnv
	 */
	public void setFirebaseMeasurementId(String firebaseMeasurementId) {
		this.firebaseMeasurementId = firebaseMeasurementId;
	}

	/**
	 * getRepoUrlAttachFile
	 * 
	 * @return
	 */
	public String getRepoUrlAttachFile() {
		return repoUrlAttachFile;
	}

	/**
	 * setRepoUrlAttachFile
	 * 
	 * @param repoUrlAttachFile
	 */
	public void setRepoUrlAttachFile(String repoUrlAttachFile) {
		this.repoUrlAttachFile = repoUrlAttachFile;
	}

	/**
	 * Get ozRepositoryUrl
	 * 
	 * @return String
	 * @author HungHT
	 */
	public String getOzRepositoryUrl() {
		return ozRepositoryUrl;
	}

	/**
	 * Set ozRepositoryUrl
	 * 
	 * @param ozRepositoryUrl type String
	 * @return
	 * @author HungHT
	 */
	public void setOzRepositoryUrl(String ozRepositoryUrl) {
		this.ozRepositoryUrl = ozRepositoryUrl;
	}

	/**
	 * Get ozRepositoryUser
	 * 
	 * @return String
	 * @author HungHT
	 */
	public String getOzRepositoryUser() {
		return ozRepositoryUser;
	}

	/**
	 * Set ozRepositoryUser
	 * 
	 * @param ozRepositoryUser type String
	 * @return
	 * @author HungHT
	 */
	public void setOzRepositoryUser(String ozRepositoryUser) {
		this.ozRepositoryUser = ozRepositoryUser;
	}

	/**
	 * Get ozRepositoryPassword
	 * 
	 * @return String
	 * @author HungHT
	 */
	public String getOzRepositoryPassword() {
		return ozRepositoryPassword;
	}

	/**
	 * Set ozRepositoryPassword
	 * 
	 * @param ozRepositoryPassword type String
	 * @return
	 * @author HungHT
	 */
	public void setOzRepositoryPassword(String ozRepositoryPassword) {
		this.ozRepositoryPassword = ozRepositoryPassword;
	}

	/**
	 * Get ozRepositoryCompany
	 * 
	 * @return String
	 * @author HungHT
	 */
	public String getOzRepositoryCompany() {
		return ozRepositoryCompany;
	}

	/**
	 * Set ozRepositoryCompany
	 * 
	 * @param ozRepositoryCompany type String
	 * @return
	 * @author HungHT
	 */
	public void setOzRepositoryCompany(String ozRepositoryCompany) {
		this.ozRepositoryCompany = ozRepositoryCompany;
	}

	public String getEcmRepositoryDocument() {
		return ecmRepositoryDocument;
	}

	public void setEcmRepositoryDocument(String ecmRepositoryDocument) {
		this.ecmRepositoryDocument = ecmRepositoryDocument;
	}

	/**
	 * getAuthenKey
	 * 
	 * @return
	 * @author trieuvd
	 */
	public String getAuthenKey() {
		return authenKey;
	}

	/**
	 * setAuthenKey
	 * 
	 * @param authenKey
	 * @author trieuvd
	 */
	public void setAuthenKey(String authenKey) {
		this.authenKey = authenKey;
	}

	/**
	 * @return the defaultGroup
	 */
	public String getDefaultGroup() {
		return defaultGroup;
	}

	/**
	 * @param defaultGroup the defaultGroup to set
	 */
	public void setDefaultGroup(String defaultGroup) {
		this.defaultGroup = defaultGroup;
	}

	/**
	 * getHsmUrl
	 * 
	 * @return
	 * @author trieuvd
	 */
	public String getHsmUrl() {
		return hsmUrl;
	}

	/**
	 * setHsmUrl
	 * 
	 * @param hsmUrl
	 * @author trieuvd
	 */
	public void setHsmUrl(String hsmUrl) {
		this.hsmUrl = hsmUrl;
	}

	/**
	 * getLoginAPIUrl
	 * 
	 * @return
	 * @author trieuvd
	 */
	public String getLoginAPIUrl() {
		return loginAPIUrl;
	}

	/**
	 * setLoginAPIUrl
	 * 
	 * @param loginAPIUrl
	 * @author trieuvd
	 */
	public void setLoginAPIUrl(String loginAPIUrl) {
		this.loginAPIUrl = loginAPIUrl;
	}

	/**
	 * getLoginAPIAuthenKey
	 * 
	 * @return
	 * @author trieuvd
	 */
	public String getLoginAPIAuthenKey() {
		return loginAPIAuthenKey;
	}

	/**
	 * setLoginAPIAuthenKey
	 * 
	 * @param loginAPIAuthenKey
	 * @author trieuvd
	 */
	public void setLoginAPIAuthenKey(String loginAPIAuthenKey) {
		this.loginAPIAuthenKey = loginAPIAuthenKey;
	}

	/**
	 * getLoginAPISecretKey
	 * 
	 * @return
	 * @author trieuvd
	 */
	public String getLoginAPISecretKey() {
		return loginAPISecretKey;
	}

	/**
	 * setLoginAPISecretKey
	 * 
	 * @param loginAPISecretKey
	 * @author trieuvd
	 */
	public void setLoginAPISecretKey(String loginAPISecretKey) {
		this.loginAPISecretKey = loginAPISecretKey;
	}

	/**
	 * Get ozRepositoryLocalUrl
	 * 
	 * @return String
	 * @author HungHT
	 */
	public String getOzRepositoryLocalUrl() {
		return ozRepositoryLocalUrl;
	}

	/**
	 * Set ozRepositoryLocalUrl
	 * 
	 * @param ozRepositoryLocalUrl type String
	 * @return
	 * @author HungHT
	 */
	public void setOzRepositoryLocalUrl(String ozRepositoryLocalUrl) {
		this.ozRepositoryLocalUrl = ozRepositoryLocalUrl;
	}

	/**
	 * getLoginAPIHost
	 * 
	 * @return
	 * @author trieuvd
	 */
	public String getLoginAPIHost() {
		return loginAPIHost;
	}

	/**
	 * setLoginAPIHost
	 * 
	 * @param loginAPIHost
	 * @author trieuvd
	 */
	public void setLoginAPIHost(String loginAPIHost) {
		this.loginAPIHost = loginAPIHost;
	}

	/**
	 * getLoginAPIKey
	 * 
	 * @return
	 * @author trieuvd
	 */
	public String getLoginAPIKey() {
		return loginAPIKey;
	}

	/**
	 * setLoginAPIKey
	 * 
	 * @param loginAPIKey
	 * @author trieuvd
	 */
	public void setLoginAPIKey(String loginAPIKey) {
		this.loginAPIKey = loginAPIKey;
	}

	public String getFirebaseUrl() {
		return firebaseUrl;
	}

	public void setFirebaseUrl(String firebaseUrl) {
		this.firebaseUrl = firebaseUrl;
	}

	public String getFirebaseAuthenKey() {
		return firebaseAuthenKey;
	}

	public void setFirebaseAuthenKey(String firebaseAuthenKey) {
		this.firebaseAuthenKey = firebaseAuthenKey;
	}

	public boolean getPushNotif() {
		return pushNotif;
	}

	public void setPushNotif(boolean pushNotif) {
		this.pushNotif = pushNotif;
	}

	public Integer getFlagPushNotif() {
		return flagPushNotif;
	}

	public void setFlagPushNotif(Integer flagPushNotif) {
		this.flagPushNotif = flagPushNotif;
	}

	public Integer getFlagPushEmail() {
		return flagPushEmail;
	}

	public void setFlagPushEmail(Integer flagPushEmail) {
		this.flagPushEmail = flagPushEmail;
	}

	public boolean getPushEmail() {
		return pushEmail;
	}

	public void setPushEmail(boolean pushEmail) {
		this.pushEmail = pushEmail;
	}

	public String getIntegUrl() {
		return integUrl;
	}

	public void setIntegUrl(String integUrl) {
		this.integUrl = integUrl;
	}

	/**
	 * @return the flagFirebase
	 */
	public boolean isFlagFirebase() {
		return flagFirebase;
	}

	/**
	 * @param flagFirebase the flagFirebase to set
	 */
	public void setFlagFirebase(boolean flagFirebase) {
		this.flagFirebase = flagFirebase;
	}

	public String getAndroidAppVersion() {
		return androidAppVersion;
	}

	public void setAndroidAppVersion(String androidAppVersion) {
		this.androidAppVersion = androidAppVersion;
	}

	public String getIosAppVersion() {
		return iosAppVersion;
	}

	public void setIosAppVersion(String iosAppVersion) {
		this.iosAppVersion = iosAppVersion;
	}

	public String getIntegSecretPassword() {
		return integSecretPassword;
	}

	public void setIntegSecretPassword(String integSecretPassword) {
		this.integSecretPassword = integSecretPassword;
	}

	public String getUpdateVersionAppType() {
		return updateVersionAppType;
	}

	public void setUpdateVersionAppType(String updateVersionAppType) {
		this.updateVersionAppType = updateVersionAppType;
	}

	public Map<String, String> getUpdateVersionAppMessages() {
		return updateVersionAppMessages;
	}

	public void setUpdateVersionAppMessages(Map<String, String> updateVersionAppMessages) {
		this.updateVersionAppMessages = updateVersionAppMessages;
	}

	/**
	 * Get integUrlProcess
	 * 
	 * @return String
	 * @author DaiTrieu
	 */
	public String getIntegUrlProcess() {
		return integUrlProcess;
	}

	/**
	 * Set integUrlProcess
	 * 
	 * @param integUrlProcess type String
	 * @return
	 * @author DaiTrieu
	 */
	public void setIntegUrlProcess(String integUrlProcess) {
		this.integUrlProcess = integUrlProcess;
	}

	/**
	 * Get authenKeyProcess
	 * 
	 * @return String
	 * @author luannm
	 */
	public String getAuthenKeyProcess() {
		return authenKeyProcess;
	}

	/**
	 * Set authenKeyProcess
	 * 
	 * @param authenKeyProcess type String
	 * @return
	 * @author luannm
	 */
	public void setAuthenKeyProcess(String authenKeyProcess) {
		this.authenKeyProcess = authenKeyProcess;
	}

	public String getAccessKeyAws() {
		return accessKeyAws;
	}

	public void setAccessKeyAws(String accessKeyAws) {
		this.accessKeyAws = accessKeyAws;
	}

	public String getSecretKeyAws() {
		return secretKeyAws;
	}

	public void setSecretKeyAws(String secretKeyAws) {
		this.secretKeyAws = secretKeyAws;
	}

	public String getEmailSenderAws() {
		return emailSenderAws;
	}

	public void setEmailSenderAws(String emailSenderAws) {
		this.emailSenderAws = emailSenderAws;
	}

	public String getEmailRegionAws() {
		return emailRegionAws;
	}

	public void setEmailRegionAws(String emailRegionAws) {
		this.emailRegionAws = emailRegionAws;
	}

	public String getRepoAttachFileAws() {
		return repoAttachFileAws;
	}

	public void setRepoAttachFileAws(String repoAttachFileAws) {
		this.repoAttachFileAws = repoAttachFileAws;
	}

	public String getUrlDefaultAws() {
		return urlDefaultAws;
	}

	public void setUrlDefaultAws(String urlDefaultAws) {
		this.urlDefaultAws = urlDefaultAws;
	}

	public String getEmailHostAwsSmtp() {
		return emailHostAwsSmtp;
	}

	public void setEmailHostAwsSmtp(String emailHostAwsSmtp) {
		this.emailHostAwsSmtp = emailHostAwsSmtp;
	}

	public String getEmailPortAwsSmtp() {
		return emailPortAwsSmtp;
	}

	public void setEmailPortAwsSmtp(String emailPortAwsSmtp) {
		this.emailPortAwsSmtp = emailPortAwsSmtp;
	}

	public String getEmailDefaultAwsSmtp() {
		return emailDefaultAwsSmtp;
	}

	public void setEmailDefaultAwsSmtp(String emailDefaultAwsSmtp) {
		this.emailDefaultAwsSmtp = emailDefaultAwsSmtp;
	}

	public String getEmailPortInboxAwsSmtp() {
		return emailPortInboxAwsSmtp;
	}

	public void setEmailPortInboxAwsSmtp(String emailPortInboxAwsSmtp) {
		this.emailPortInboxAwsSmtp = emailPortInboxAwsSmtp;
	}

	public String getEmailPasswordAwsSmtp() {
		return emailPasswordAwsSmtp;
	}

	public void setEmailPasswordAwsSmtp(String emailPasswordAwsSmtp) {
		this.emailPasswordAwsSmtp = emailPasswordAwsSmtp;
	}

	public Integer getSendEmailTypeDefaultAwsSmtp() {
		return sendEmailTypeDefaultAwsSmtp;
	}

	public void setSendEmailTypeDefaultAwsSmtp(Integer sendEmailTypeDefaultAwsSmtp) {
		this.sendEmailTypeDefaultAwsSmtp = sendEmailTypeDefaultAwsSmtp;
	}

	public String getEmailDefaultNameAwsSmtp() {
		return emailDefaultNameAwsSmtp;
	}

	public void setEmailDefaultNameAwsSmtp(String emailDefaultNameAwsSmtp) {
		this.emailDefaultNameAwsSmtp = emailDefaultNameAwsSmtp;
	}

	public String getRepoAttachFileAwsSmtp() {
		return repoAttachFileAwsSmtp;
	}

	public void setRepoAttachFileAwsSmtp(String repoAttachFileAwsSmtp) {
		this.repoAttachFileAwsSmtp = repoAttachFileAwsSmtp;
	}

	public String getUrlDefaultAwsSmtp() {
		return urlDefaultAwsSmtp;
	}

	public void setUrlDefaultAwsSmtp(String urlDefaultAwsSmtp) {
		this.urlDefaultAwsSmtp = urlDefaultAwsSmtp;
	}

	public String getEmailUsernameAwsSmtp() {
		return emailUsernameAwsSmtp;
	}

	public void setEmailUsernameAwsSmtp(String emailUsernameAwsSmtp) {
		this.emailUsernameAwsSmtp = emailUsernameAwsSmtp;
	}

	public String getEmailHostSmtp() {
		return emailHostSmtp;
	}

	public void setEmailHostSmtp(String emailHostSmtp) {
		this.emailHostSmtp = emailHostSmtp;
	}

	public String getEmailPortSmtp() {
		return emailPortSmtp;
	}

	public void setEmailPortSmtp(String emailPortSmtp) {
		this.emailPortSmtp = emailPortSmtp;
	}

	public String getEmailDefaultSmtp() {
		return emailDefaultSmtp;
	}

	public void setEmailDefaultSmtp(String emailDefaultSmtp) {
		this.emailDefaultSmtp = emailDefaultSmtp;
	}

	public String getEmailPortInboxSmtp() {
		return emailPortInboxSmtp;
	}

	public void setEmailPortInboxSmtp(String emailPortInboxSmtp) {
		this.emailPortInboxSmtp = emailPortInboxSmtp;
	}

	public String getEmailPasswordSmtp() {
		return emailPasswordSmtp;
	}

	public void setEmailPasswordSmtp(String emailPasswordSmtp) {
		this.emailPasswordSmtp = emailPasswordSmtp;
	}

	public Integer getSendEmailTypeDefaultSmtp() {
		return sendEmailTypeDefaultSmtp;
	}

	public void setSendEmailTypeDefaultSmtp(Integer sendEmailTypeDefaultSmtp) {
		this.sendEmailTypeDefaultSmtp = sendEmailTypeDefaultSmtp;
	}

	public String getEmailDefaultNameSmtp() {
		return emailDefaultNameSmtp;
	}

	public void setEmailDefaultNameSmtp(String emailDefaultNameSmtp) {
		this.emailDefaultNameSmtp = emailDefaultNameSmtp;
	}

	public String getRepoAttachFileSmtp() {
		return repoAttachFileSmtp;
	}

	public void setRepoAttachFileSmtp(String repoAttachFileSmtp) {
		this.repoAttachFileSmtp = repoAttachFileSmtp;
	}

	public String getUrlDefaultSmtp() {
		return urlDefaultSmtp;
	}

	public void setUrlDefaultSmtp(String urlDefaultSmtp) {
		this.urlDefaultSmtp = urlDefaultSmtp;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public String getSenderSms() {
		return senderSms;
	}

	public void setSenderSms(String senderSms) {
		this.senderSms = senderSms;
	}

	public String getSendSmsUrl() {
		return sendSmsUrl;
	}

	public void setSendSmsUrl(String sendSmsUrl) {
		this.sendSmsUrl = sendSmsUrl;
	}

	public String getTypeOfSendmail() {
		return typeOfSendmail;
	}

	public void setTypeOfSendmail(String typeOfSendmail) {
		this.typeOfSendmail = typeOfSendmail;
	}

	public String getGoogleMapApiKey() {
		return googleMapApiKey;
	}

	public void setGoogleMapApiKey(String googleMapApiKey) {
		this.googleMapApiKey = googleMapApiKey;
	}

	public String getLogoLarge() {
		return logoLarge;
	}

	public void setLogoLarge(String logoLarge) {
		this.logoLarge = logoLarge;
	}

	public String getLogoLargeRepoId() {
		return logoLargeRepoId;
	}

	public void setLogoLargeRepoId(String logoLargeRepoId) {
		this.logoLargeRepoId = logoLargeRepoId;
	}

	public String getLogoMini() {
		return logoMini;
	}

	public void setLogoMini(String logoMini) {
		this.logoMini = logoMini;
	}

	public String getLogoMiniRepoId() {
		return logoMiniRepoId;
	}

	public void setLogoMiniRepoId(String logoMiniRepoId) {
		this.logoMiniRepoId = logoMiniRepoId;
	}

	public String getPackageLogoLarge() {
		return packageLogoLarge;
	}

	public void setPackageLogoLarge(String packageLogoLarge) {
		this.packageLogoLarge = packageLogoLarge;
	}

	public String getPackageLogoMini() {
		return packageLogoMini;
	}

	public void setPackageLogoMini(String packageLogoMini) {
		this.packageLogoMini = packageLogoMini;
	}

	public MultipartFile getFileLogoLarge() {
		return fileLogoLarge;
	}

	public void setFileLogoLarge(MultipartFile fileLogoLarge) {
		this.fileLogoLarge = fileLogoLarge;
	}

	public MultipartFile getFileLogoMini() {
		return fileLogoMini;
	}

	public void setFileLogoMini(MultipartFile fileLogoMini) {
		this.fileLogoMini = fileLogoMini;
	}
	
}
