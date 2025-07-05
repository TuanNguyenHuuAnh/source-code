/*******************************************************************************
 * Class        SystemConfigDto
 * Created date 2017/02/14
 * Lasted date  2017/02/14
 * Author       trieunh
 * Change log   2017/02/1401-00 trieunh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.ArrayList;
import java.util.List;

import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaSystemConfigDto;
import vn.com.unit.ep2p.admin.constant.MessageList;

/**
 * SystemConfigDto
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh
 */
public class SystemConfigDto extends JcaSystemConfigDto {

	private String pageSize;
	private String datePattern;
	private String monthPattern;
	private String systemConfigSync;
	private String defaultLanguage;
	private Integer fromYear;
	private Integer toYear;
	private String version;
	private String emailDefault;
	private String emailDefaultName;
	private String emailHost;
	private Integer emailPort;
	private String emailPassword;
	private String repository;
	private String urlDefault;
	private Integer emailOption;
	private String emailSchedule;
	private Integer checkLimmitEmail;
	private Integer limmitEmail;
	private String jbpmHost;
	private String jbpmContext;
	private String jbpmLOSDeployId;
	private String jbpmLOSProcessId;
	private String nodeHost;
	private Integer maxConnectionChat;
	private String ldapAccountFilter;
	private String ldapCnFilter;
	private String ldapDomain;
	private String ldapInitialContextFactory;
	private String ldapMainGroup;
	private String ldapProviderUrl;
	private String ldapSecurityAuthentication;
	private String ldapSecurityCredentials;
	private String ldapSecurityPrincipal;
	private Integer sessionTimeoutChat;

	private Integer sessionTimeoutChatServer;

	private List<JcaConstantDto> listPatternDate;
	private List<JcaConstantDto> listPatternMonth;

	private MessageList messageList;

	private Integer failedLoginCount;

	private String apiUrl;

	private String sftpFolder;
	private String sftpHost;
	private Integer sftpPort;
	private String sftpUsername;
	private String sftpPassword;

	private Integer timeLock;

	private Integer flagAutoUnlock;

	private boolean autoUnlock;

	private Integer expiredPassword;

	private Integer flagFirstTimeLogin;

	private boolean firstTimeLogin;

	private Integer numberPasswordUsed;

	private Integer minPasswordAge;

	private Integer minPasswordLength;
	private Integer maxPasswordLength;

	private Integer flagUpperCase;
	private Integer flagLowerCase;
	private Integer flagNumberCase;
	private Integer flagSpecialCase;

	private Integer flagComplexity;

	private boolean complexity;

	private Integer lengthCase;

	private List<String> fieldValues = new ArrayList<>();

	private Integer sendEmailTypeDefault;

	private String displaySystemName;

	
	private String repoUploadCommon;
	private String repoUploadTemp;
    private String repoUploadMain;
    private String tempFolder;
    private String repoUrlTemplateEmail;

	private String repoAttachFile;

	private Integer emailPortInbox;
	
	private Integer flagUsedECM;
	private boolean usedECM;
	private Integer flagUsedBPM;
	private boolean usedBPM;
	
	private String uploadFileType;
	private Integer uploadFileSizeMainFile;
	private Integer uploadFileSizeAttach;
	
	private Integer numDateDeleteNotify;
	
	public SystemConfigDto() {

	}
	/**
	 * @author: TriNT
	 * @since: 14/03/2022 11:13 SA
	 * @description:  get Max password length
	 * @update:
	 *
	 * */
	public Integer getMaxPasswordLength() {
		return maxPasswordLength;
	}
	/**
	 * @author: TriNT
	 * @since: 14/03/2022 11:13 SA
	 * @description:  set Max password length
	 * @update:
	 *
	 * */
	public void setMaxPasswordLength(Integer maxPasswordLength) {
		this.maxPasswordLength = maxPasswordLength;
	}
	/**
	 * Get pageSize
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getPageSize() {
		return pageSize;
	}

	/**
	 * Set pageSize
	 * 
	 * @param pageSize type String
	 * @return
	 * @author trieunh
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Get datePattern
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getDatePattern() {
		return datePattern;
	}

	/**
	 * Set datePattern
	 * 
	 * @param datePattern type String
	 * @return
	 * @author trieunh
	 */
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	/**
	 * Get defaultLanguage
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	/**
	 * Set defaultLanguage
	 * 
	 * @param defaultLanguage type String
	 * @return
	 * @author trieunh
	 */
	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	/**
	 * Get fromYear
	 * 
	 * @return Integer
	 * @author trieunh
	 */
	public Integer getFromYear() {
		return fromYear;
	}

	/**
	 * Set fromYear
	 * 
	 * @param fromYear type Integer
	 * @return
	 * @author trieunh
	 */
	public void setFromYear(Integer fromYear) {
		this.fromYear = fromYear;
	}

	/**
	 * Get toYear
	 * 
	 * @return Integer
	 * @author trieunh
	 */
	public Integer getToYear() {
		return toYear;
	}

	/**
	 * Set toYear
	 * 
	 * @param toYear type Integer
	 * @return
	 * @author trieunh
	 */
	public void setToYear(Integer toYear) {
		this.toYear = toYear;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Get emailDefault
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getEmailDefault() {
		return emailDefault;
	}

	/**
	 * Set emailDefault
	 * 
	 * @param emailDefault type String
	 * @return
	 * @author trieunh
	 */
	public void setEmailDefault(String emailDefault) {
		this.emailDefault = emailDefault;
	}

	/**
	 * Get emailDefaultName
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getEmailDefaultName() {
		return emailDefaultName;
	}

	/**
	 * Set emailDefaultName
	 * 
	 * @param emailDefaultName type String
	 * @return
	 * @author trieunh
	 */
	public void setEmailDefaultName(String emailDefaultName) {
		this.emailDefaultName = emailDefaultName;
	}

	/**
	 * Get emailHost
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getEmailHost() {
		return emailHost;
	}

	/**
	 * Set emailHost
	 * 
	 * @param emailHost type String
	 * @return
	 * @author trieunh
	 */
	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}

	/**
	 * Get emailPort
	 * 
	 * @return Integer
	 * @author trieunh
	 */
	public Integer getEmailPort() {
		return emailPort;
	}

	/**
	 * Set emailPort
	 * 
	 * @param emailPort type Integer
	 * @return
	 * @author trieunh
	 */
	public void setEmailPort(Integer emailPort) {
		this.emailPort = emailPort;
	}

	/**
	 * Get emailPassword
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getEmailPassword() {
		return emailPassword;
	}

	/**
	 * Set emailPassword
	 * 
	 * @param emailPassword type String
	 * @return
	 * @author trieunh
	 */
	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	/**
	 * Get repository
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getRepository() {
		return repository;
	}

	/**
	 * Set repository
	 * 
	 * @param repository type String
	 * @return
	 * @author trieunh
	 */
	public void setRepository(String repository) {
		this.repository = repository;
	}

	/**
	 * Get urlDefault
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getUrlDefault() {
		return urlDefault;
	}

	/**
	 * Set urlDefault
	 * 
	 * @param urlDefault type String
	 * @return
	 * @author trieunh
	 */
	public void setUrlDefault(String urlDefault) {
		this.urlDefault = urlDefault;
	}

	/**
	 * Get emailOption
	 * 
	 * @return Integer
	 * @author trieunh
	 */
	public Integer getEmailOption() {
		return emailOption;
	}

	/**
	 * Set emailOption
	 * 
	 * @param emailOption type Integer
	 * @return
	 * @author trieunh
	 */
	public void setEmailOption(Integer emailOption) {
		this.emailOption = emailOption;
	}

	/**
	 * Get emailSchedule
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getEmailSchedule() {
		return emailSchedule;
	}

	/**
	 * Set emailSchedule
	 * 
	 * @param emailSchedule type String
	 * @return
	 * @author trieunh
	 */
	public void setEmailSchedule(String emailSchedule) {
		this.emailSchedule = emailSchedule;
	}

	/**
	 * Get checkLimmitEmail
	 * 
	 * @return Integer
	 * @author trieunh
	 */
	public Integer getCheckLimmitEmail() {
		return checkLimmitEmail;
	}

	/**
	 * Set checkLimmitEmail
	 * 
	 * @param checkLimmitEmail type Integer
	 * @return
	 * @author trieunh
	 */
	public void setCheckLimmitEmail(Integer checkLimmitEmail) {
		this.checkLimmitEmail = checkLimmitEmail;
	}

	/**
	 * Get limmitEmail
	 * 
	 * @return Integer
	 * @author trieunh
	 */
	public Integer getLimmitEmail() {
		return limmitEmail;
	}

	/**
	 * Set limmitEmail
	 * 
	 * @param limmitEmail type Integer
	 * @return
	 * @author trieunh
	 */
	public void setLimmitEmail(Integer limmitEmail) {
		this.limmitEmail = limmitEmail;
	}

	/**
	 * Get jbpmHost
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getJbpmHost() {
		return jbpmHost;
	}

	/**
	 * Set jbpmHost
	 * 
	 * @param jbpmHost type String
	 * @return
	 * @author trieunh
	 */
	public void setJbpmHost(String jbpmHost) {
		this.jbpmHost = jbpmHost;
	}

	/**
	 * Get jbpmContext
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getJbpmContext() {
		return jbpmContext;
	}

	/**
	 * Set jbpmContext
	 * 
	 * @param jbpmContext type String
	 * @return
	 * @author trieunh
	 */
	public void setJbpmContext(String jbpmContext) {
		this.jbpmContext = jbpmContext;
	}

	/**
	 * Get jbpmLOSDeployId
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getJbpmLOSDeployId() {
		return jbpmLOSDeployId;
	}

	/**
	 * Set jbpmLOSDeployId
	 * 
	 * @param jbpmLOSDeployId type String
	 * @return
	 * @author trieunh
	 */
	public void setJbpmLOSDeployId(String jbpmLOSDeployId) {
		this.jbpmLOSDeployId = jbpmLOSDeployId;
	}

	/**
	 * Get jbpmLOSProcessId
	 * 
	 * @return String
	 * @author trieunh
	 */
	public String getJbpmLOSProcessId() {
		return jbpmLOSProcessId;
	}

	/**
	 * Set jbpmLOSProcessId
	 * 
	 * @param jbpmLOSProcessId type String
	 * @return
	 * @author trieunh
	 */
	public void setJbpmLOSProcessId(String jbpmLOSProcessId) {
		this.jbpmLOSProcessId = jbpmLOSProcessId;
	}

	/**
	 * Get listPatternDate
	 * 
	 * @return List<ConstantDisplayDto>
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	public List<JcaConstantDto> getListPatternDate() {
		return listPatternDate;
	}

	/**
	 * Set listPatternDate
	 * 
	 * @param listPatternDate type List<ConstantDisplayDto>
	 * @return
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	public void setListPatternDate(List<JcaConstantDto> listPatternDate) {
		this.listPatternDate = listPatternDate;
	}

	/**
	 * Get nodeHost
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getNodeHost() {
		return nodeHost;
	}

	/**
	 * Set nodeHost
	 * 
	 * @param nodeHost type String
	 * @return
	 * @author phunghn
	 */
	public void setNodeHost(String nodeHost) {
		this.nodeHost = nodeHost;
	}

	/**
	 * Get maxConnectionChat
	 * 
	 * @return Integer
	 * @author phunghn
	 */
	public Integer getMaxConnectionChat() {
		return maxConnectionChat;
	}

	/**
	 * Set maxConnectionChat
	 * 
	 * @param maxConnectionChat type Integer
	 * @return
	 * @author phunghn
	 */
	public void setMaxConnectionChat(Integer maxConnectionChat) {
		this.maxConnectionChat = maxConnectionChat;
	}

	/**
	 * Get sessionTimeoutChat
	 * 
	 * @return Integer
	 * @author phunghn
	 */
	public Integer getSessionTimeoutChat() {
		return sessionTimeoutChat;
	}

	/**
	 * Set sessionTimeoutChat
	 * 
	 * @param sessionTimeoutChat type Integer
	 * @return
	 * @author phunghn
	 */
	public void setSessionTimeoutChat(Integer sessionTimeoutChat) {
		this.sessionTimeoutChat = sessionTimeoutChat;
	}

	/**
	 * Get sessionTimeoutChatServer
	 * 
	 * @return Integer
	 * @author phunghn
	 */
	public Integer getSessionTimeoutChatServer() {
		return sessionTimeoutChatServer;
	}

	/**
	 * Set sessionTimeoutChatServer
	 * 
	 * @param sessionTimeoutChatServer type Integer
	 * @return
	 * @author phunghn
	 */
	public void setSessionTimeoutChatServer(Integer sessionTimeoutChatServer) {
		this.sessionTimeoutChatServer = sessionTimeoutChatServer;
	}

	public MessageList getMessageList() {
		return messageList;
	}

	public void setMessageList(MessageList messageList) {
		this.messageList = messageList;
	}

	public String getLdapAccountFilter() {
		return ldapAccountFilter;
	}

	public void setLdapAccountFilter(String ldapAccountFilter) {
		this.ldapAccountFilter = ldapAccountFilter;
	}

	public String getLdapCnFilter() {
		return ldapCnFilter;
	}

	public void setLdapCnFilter(String ldapCnFilter) {
		this.ldapCnFilter = ldapCnFilter;
	}

	public String getLdapDomain() {
		return ldapDomain;
	}

	public void setLdapDomain(String ldapDomain) {
		this.ldapDomain = ldapDomain;
	}

	public String getLdapInitialContextFactory() {
		return ldapInitialContextFactory;
	}

	public void setLdapInitialContextFactory(String ldapInitialContextFactory) {
		this.ldapInitialContextFactory = ldapInitialContextFactory;
	}

	public String getLdapMainGroup() {
		return ldapMainGroup;
	}

	public void setLdapMainGroup(String ldapMainGroup) {
		this.ldapMainGroup = ldapMainGroup;
	}

	public String getLdapProviderUrl() {
		return ldapProviderUrl;
	}

	public void setLdapProviderUrl(String ldapProviderUrl) {
		this.ldapProviderUrl = ldapProviderUrl;
	}

	public String getLdapSecurityAuthentication() {
		return ldapSecurityAuthentication;
	}

	public void setLdapSecurityAuthentication(String ldapSecurityAuthentication) {
		this.ldapSecurityAuthentication = ldapSecurityAuthentication;
	}

	public String getLdapSecurityCredentials() {
		return ldapSecurityCredentials;
	}

	public void setLdapSecurityCredentials(String ldapSecurityCredentials) {
		this.ldapSecurityCredentials = ldapSecurityCredentials;
	}

	public String getLdapSecurityPrincipal() {
		return ldapSecurityPrincipal;
	}

	public void setLdapSecurityPrincipal(String ldapSecurityPrincipal) {
		this.ldapSecurityPrincipal = ldapSecurityPrincipal;
	}

	public List<JcaConstantDto> getListPatternMonth() {
		return listPatternMonth;
	}

	public void setListPatternMonth(List<JcaConstantDto> listPatternMonth) {
		this.listPatternMonth = listPatternMonth;
	}

	public String getMonthPattern() {
		return monthPattern;
	}

	public void setMonthPattern(String monthPattern) {
		this.monthPattern = monthPattern;
	}

	/**
	 * @return the systemConfigSync
	 */
	public String getSystemConfigSync() {
		return systemConfigSync;
	}

	/**
	 * @param systemConfigSync the systemConfigSync to set
	 */
	public void setSystemConfigSync(String systemConfigSync) {
		this.systemConfigSync = systemConfigSync;
	}

	/**
	 * Get failedLoginCount
	 * 
	 * @return Integer
	 * @author HUNGHT
	 */
	public Integer getFailedLoginCount() {
		return failedLoginCount;
	}

	/**
	 * Set failedLoginCount
	 * 
	 * @param failedLoginCount type Integer
	 * @return
	 * @author HUNGHT
	 */
	public void setFailedLoginCount(Integer failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	/**
	 * Get apiUrl
	 * 
	 * @return String
	 * @author HungHT
	 */
	public String getApiUrl() {
		return apiUrl;
	}

	/**
	 * Set apiUrl
	 * 
	 * @param apiUrl type String
	 * @return
	 * @author HungHT
	 */
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	/**
	 * Get sftpFolder
	 * 
	 * @return String
	 * @author HungHT
	 */
	public String getSftpFolder() {
		return sftpFolder;
	}

	/**
	 * Set sftpFolder
	 * 
	 * @param sftpFolder type String
	 * @return
	 * @author HungHT
	 */
	public void setSftpFolder(String sftpFolder) {
		this.sftpFolder = sftpFolder;
	}

	/**
	 * Get sftpHost
	 * 
	 * @return String
	 * @author HungHT
	 */
	public String getSftpHost() {
		return sftpHost;
	}

	/**
	 * Set sftpHost
	 * 
	 * @param sftpHost type String
	 * @return
	 * @author HungHT
	 */
	public void setSftpHost(String sftpHost) {
		this.sftpHost = sftpHost;
	}

	/**
	 * Get sftpPort
	 * 
	 * @return Integer
	 * @author HungHT
	 */
	public Integer getSftpPort() {
		return sftpPort;
	}

	/**
	 * Set sftpPort
	 * 
	 * @param sftpPort type Integer
	 * @return
	 * @author HungHT
	 */
	public void setSftpPort(Integer sftpPort) {
		this.sftpPort = sftpPort;
	}

	/**
	 * Get sftpUsername
	 * 
	 * @return String
	 * @author HungHT
	 */
	public String getSftpUsername() {
		return sftpUsername;
	}

	/**
	 * Set sftpUsername
	 * 
	 * @param sftpUsername type String
	 * @return
	 * @author HungHT
	 */
	public void setSftpUsername(String sftpUsername) {
		this.sftpUsername = sftpUsername;
	}

	/**
	 * Get sftpPassword
	 * 
	 * @return String
	 * @author HungHT
	 */
	public String getSftpPassword() {
		return sftpPassword;
	}

	/**
	 * Set sftpPassword
	 * 
	 * @param sftpPassword type String
	 * @return
	 * @author HungHT
	 */
	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}

	public Integer getTimeLock() {
		return timeLock;
	}

	public void setTimeLock(Integer timeLock) {
		this.timeLock = timeLock;
	}

	public Integer getFlagAutoUnlock() {
		return flagAutoUnlock;
	}

	public void setFlagAutoUnlock(Integer flagAutoUnlock) {
		this.flagAutoUnlock = flagAutoUnlock;
	}

	public boolean isAutoUnlock() {
		return autoUnlock;
	}

	public void setAutoUnlock(boolean autoUnlock) {
		this.autoUnlock = autoUnlock;
	}

	public Integer getExpiredPassword() {
		return expiredPassword;
	}

	public void setExpiredPassword(Integer expiredPassword) {
		this.expiredPassword = expiredPassword;
	}

	public Integer getFlagFirstTimeLogin() {
		return flagFirstTimeLogin;
	}

	public void setFlagFirstTimeLogin(Integer flagFirstTimeLogin) {
		this.flagFirstTimeLogin = flagFirstTimeLogin;
	}

	public boolean isFirstTimeLogin() {
		return firstTimeLogin;
	}

	public void setFirstTimeLogin(boolean firstTimeLogin) {
		this.firstTimeLogin = firstTimeLogin;
	}

	public Integer getNumberPasswordUsed() {
		return numberPasswordUsed;
	}

	public void setNumberPasswordUsed(Integer numberPasswordUsed) {
		this.numberPasswordUsed = numberPasswordUsed;
	}

	public Integer getMinPasswordAge() {
		return minPasswordAge;
	}

	public void setMinPasswordAge(Integer minPasswordAge) {
		this.minPasswordAge = minPasswordAge;
	}

	public Integer getMinPasswordLength() {
		return minPasswordLength;
	}

	public void setMinPasswordLength(Integer minPasswordLength) {
		this.minPasswordLength = minPasswordLength;
	}

	public Integer getFlagUpperCase() {
		return flagUpperCase;
	}

	public void setFlagUpperCase(Integer flagUpperCase) {
		this.flagUpperCase = flagUpperCase;
	}

	public Integer getFlagLowerCase() {
		return flagLowerCase;
	}

	public void setFlagLowerCase(Integer flagLowerCase) {
		this.flagLowerCase = flagLowerCase;
	}

	public Integer getFlagNumberCase() {
		return flagNumberCase;
	}

	public void setFlagNumberCase(Integer flagNumberCase) {
		this.flagNumberCase = flagNumberCase;
	}

	public Integer getFlagSpecialCase() {
		return flagSpecialCase;
	}

	public void setFlagSpecialCase(Integer flagSpecialCase) {
		this.flagSpecialCase = flagSpecialCase;
	}

	public Integer getFlagComplexity() {
		return flagComplexity;
	}

	public void setFlagComplexity(Integer flagComplexity) {
		this.flagComplexity = flagComplexity;
	}

	public boolean isComplexity() {
		return complexity;
	}

	public void setComplexity(boolean complexity) {
		this.complexity = complexity;
	}

	public Integer getLengthCase() {
		return lengthCase;
	}

	public void setLengthCase(Integer lengthCase) {
		this.lengthCase = lengthCase;
	}

	public List<String> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}

	public Integer getSendEmailTypeDefault() {
		return sendEmailTypeDefault;
	}

	public void setSendEmailTypeDefault(Integer sendEmailTypeDefault) {
		this.sendEmailTypeDefault = sendEmailTypeDefault;
	}

	public String getDisplaySystemName() {
		return displaySystemName;
	}

	public void setDisplaySystemName(String displaySystemName) {
		this.displaySystemName = displaySystemName;
	}

	public String getRepoUploadCommon() {
		return repoUploadCommon;
	}

	public void setRepoUploadCommon(String repoUploadCommon) {
		this.repoUploadCommon = repoUploadCommon;
	}

	public String getRepoAttachFile() {
		return repoAttachFile;
	}

	public void setRepoAttachFile(String repoAttachFile) {
		this.repoAttachFile = repoAttachFile;
	}

	/**
	 * Get emailPortInbox
	 * 
	 * @return Integer
	 * @author VinhLT
	 */
	public Integer getEmailPortInbox() {
		return emailPortInbox;
	}

	/**
	 * Set emailPortInbox
	 * 
	 * @param emailPortInbox type Integer
	 * @return
	 * @author VinhLT
	 */
	public void setEmailPortInbox(Integer emailPortInbox) {
		this.emailPortInbox = emailPortInbox;
	}
    
    /**
     * Get repoUploadTemp
     * @return String
     * @author HungHT
     */
    public String getRepoUploadTemp() {
        return repoUploadTemp;
    }
    
    /**
     * Set repoUploadTemp
     * @param   repoUploadTemp
     *          type String
     * @return
     * @author  HungHT
     */
    public void setRepoUploadTemp(String repoUploadTemp) {
        this.repoUploadTemp = repoUploadTemp;
    }

    /**
     * Get repoUploadMain
     * @return String
     * @author HungHT
     */
    public String getRepoUploadMain() {
        return repoUploadMain;
    }

    /**
     * Set repoUploadMain
     * @param   repoUploadMain
     *          type String
     * @return
     * @author  HungHT
     */
    public void setRepoUploadMain(String repoUploadMain) {
        this.repoUploadMain = repoUploadMain;
    }
    
    /**
     * Get tempFolder
     * @return String
     * @author HungHT
     */
    public String getTempFolder() {
        return tempFolder;
    }

    /**
     * Set tempFolder
     * @param   tempFolder
     *          type String
     * @return
     * @author  HungHT
     */
    public void setTempFolder(String tempFolder) {
        this.tempFolder = tempFolder;
    }
    
    /**
     * Get repoUrlTemplateEmail
     * @return String
     * @author HungHT
     */
    public String getRepoUrlTemplateEmail() {
        return repoUrlTemplateEmail;
    }

    /**
     * Set repoUrlTemplateEmail
     * @param   repoUrlTemplateEmail
     *          type String
     * @return
     * @author  HungHT
     */
    public void setRepoUrlTemplateEmail(String repoUrlTemplateEmail) {
        this.repoUrlTemplateEmail = repoUrlTemplateEmail;
    }
    
    /**
     * Get flagUsedECM
     * @return Integer
     * @author HungHT
     */
    public Integer getFlagUsedECM() {
        return flagUsedECM;
    }

    /**
     * Set flagUsedECM
     * @param   flagUsedECM
     *          type Integer
     * @return
     * @author  HungHT
     */
    public void setFlagUsedECM(Integer flagUsedECM) {
        this.flagUsedECM = flagUsedECM;
    }

    /**
     * Get usedECM
     * @return boolean
     * @author HungHT
     */
    public boolean isUsedECM() {
        return usedECM;
    }

    /**
     * Set usedECM
     * @param   usedECM
     *          type boolean
     * @return
     * @author  HungHT
     */
    public void setUsedECM(boolean usedECM) {
        this.usedECM = usedECM;
    }
    
    /**
     * Get flagUsedBPM
     * @return Integer
     * @author HungHT
     */
    public Integer getFlagUsedBPM() {
        return flagUsedBPM;
    }

    /**
     * Set flagUsedBPM
     * @param   flagUsedBPM
     *          type Integer
     * @return
     * @author  HungHT
     */
    public void setFlagUsedBPM(Integer flagUsedBPM) {
        this.flagUsedBPM = flagUsedBPM;
    }
    
    /**
     * Get usedBPM
     * @return boolean
     * @author HungHT
     */
    public boolean isUsedBPM() {
        return usedBPM;
    }

    /**
     * Set usedBPM
     * @param   usedBPM
     *          type boolean
     * @return
     * @author  HungHT
     */
    public void setUsedBPM(boolean usedBPM) {
        this.usedBPM = usedBPM;
    }

    
    /**
     * Get uploadFileType
     * @return String
     * @author DaiTrieu
     */
    public String getUploadFileType() {
        return uploadFileType;
    }

    
    /**
     * Set uploadFileType
     * @param   uploadFileType
     *          type String
     * @return
     * @author  DaiTrieu
     */
    public void setUploadFileType(String uploadFileType) {
        this.uploadFileType = uploadFileType;
    }

	/**
	 * getUploadFileSizeMainFile
	 * @return
	 * @author trieuvd
	 */
	public Integer getUploadFileSizeMainFile() {
		return uploadFileSizeMainFile;
	}

	/**
	 * setUploadFileSizeMainFile
	 * @param uploadFileSizeMainFile
	 * @author trieuvd
	 */
	public void setUploadFileSizeMainFile(Integer uploadFileSizeMainFile) {
		this.uploadFileSizeMainFile = uploadFileSizeMainFile;
	}

	/**
	 * getUploadFileSizeAttach
	 * @return
	 * @author trieuvd
	 */
	public Integer getUploadFileSizeAttach() {
		return uploadFileSizeAttach;
	}

	/**
	 * setUploadFileSizeAttach
	 * @param uploadFileSizeAttach
	 * @author trieuvd
	 */
	public void setUploadFileSizeAttach(Integer uploadFileSizeAttach) {
		this.uploadFileSizeAttach = uploadFileSizeAttach;
	}

    
    public Integer getNumDateDeleteNotify() {
        return numDateDeleteNotify;
    }

    
    public void setNumDateDeleteNotify(Integer numDateDeleteNotify) {
        this.numDateDeleteNotify = numDateDeleteNotify;
    }
    

}
