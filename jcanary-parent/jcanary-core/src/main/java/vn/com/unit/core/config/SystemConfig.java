/*******************************************************************************
 * Class        ：SystemConfig
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：tantm
 * Change log   ：2021/01/20：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import vn.com.unit.common.crypto.AesUtil;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.common.utils.Utility;
import vn.com.unit.core.dto.JcaStyleDto;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.entity.JcaCompany;
import vn.com.unit.core.entity.JcaSystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.AppLanguageService;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.core.service.JcaCompanyService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.core.service.StyleService;
import vn.com.unit.core.utils.CookieUtils;
import vn.com.unit.core.utils.PasswordUtil;
import vn.com.unit.storage.entity.JcaRepository;

/**
 * SystemConfig
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Component(value = "systemConfig")
@Scope(value = "singleton")
public class SystemConfig {

	@Autowired
	@Qualifier("jcaSystemConfigServiceImpl")
	private JcaSystemConfigService systemSettingService;
	
	@Autowired
	private JcaCompanyService jcaCompanyService;

	@Autowired
    private JRepositoryService repositoryService;

    @Autowired
    private AppLanguageService languageService;

    @Autowired
    private StyleService styleService;

    @Autowired
    private CommonService comService;

	private Map<String, String> configMap = new HashMap<String, String>();
	
	private Map<String, JcaRepository> repositoryMap = new HashMap<String, JcaRepository>();

    private Map<String, LanguageDto> languageMap = new HashMap<String, LanguageDto>();

    private Map<String, JcaStyleDto> styleMap = new HashMap<String, JcaStyleDto>();

	// Repository config
	public static final String REPO_UPLOADED_MAIN = "REPO_UPLOADED_MAIN";
	public static final String REPO_DOCUMENT = "REPO_DOCUMENT";
	public static final String TEMP_FOLDER = "TEMP_FOLDER";

	public static final String FLAG_USED_ECM = "FLAG_USED_ECM";

	public static final String REPO_ATTACH_FILE = "REPO_ATTACH_FILE";

	/** System config - LIST_PAGE_SIZE */
	public static final String LIST_PAGE_SIZE = "LIST_PAGE_SIZE";

	public static final String PAGING_SIZE = "PAGING_SIZE";
	public static final String LOGIN_API_URL = "LOGIN_API_URL";
	public static final String LDAP_DOMAIN = "LDAP_DOMAIN";
	public static final String LDAP_MAIN_GROUP = "LDAP_MAIN_GROUP";
	public static final String LDAP_CN_FILTER = "LDAP_CN_FILTER";
	public static final String LDAP_ACCOUNT_FILTER = "LDAP_ACCOUNT_FILTER";
	public static final String LDAP_INITIAL_CONTEXT_FACTORY = "LDAP_INITIAL_CONTEXT_FACTORY";
	public static final String LDAP_PROVIDER_URL = "LDAP_PROVIDER_URL";
	public static final String LDAP_SECURITY_AUTHENTICATION = "LDAP_SECURITY_AUTHENTICATION";
	public static final String LDAP_SECURITY_PRINCIPAL = "LDAP_SECURITY_PRINCIPAL";
	public static final String LDAP_SECURITY_CREDENTIALS = "LDAP_SECURITY_CREDENTIALS";
	public static final String LDAP_PEOPLE_GROUP = "LDAP_PEOPLE_GROUP";
	public static final String LDAP_SERVICE_GROUP = "LDAP_SERVICE_GROUP";
	public static final String LDAP_PREFIX_FILTER = "LDAP_PREFIX_FILTER";
	public static final String SEND_EMAIL_TYPE_DEFAULT = "SEND_EMAIL_TYPE_DEFAULT";
	public static final String TYPE_OF_SENDMAIL = "TYPE_OF_SENDMAIL";
	public static final String EXPIRED_TIME_NUMBER = "EXPIRED_TIME_NUMBER";
	public static final String PATH_DOMAIN_ADMIN = "PATH_DOMAIN_ADMIN";
	public static final String PATH_DOMAIN_API = "PATH_DOMAIN_API";

	public static final String BASE_DOMAIN_ADMIN = "BASE_DOMAIN_ADMIN";
	public static final String BASE_DOMAIN_API = "BASE_DOMAIN_API";
	
	public static final String DBTYPE = "DBTYPE";
    public static final String VERSION = "VERSION";
    public static final String DISPLAY_SYSTEM_NAME = "DISPLAY_SYSTEM_NAME";
    public static final String LANGUAGE_DEFAULT = "LANGUAGE_DEFAULT";
    public static final String STYLE_DEFAULT = "STYLE_DEFAULT";
    public static final String LIMIT_NUMBER_COMPANY_ID_ADMIN = "LIMIT_NUMBER_COMPANY_ID_ADMIN";
    public static final String DATE_PATTERN = "DATE_PATTERN";
    public static final String REPO_UPLOADED_TEMPLATE = "REPO_UPLOADED_TEMPLATE";
    public static final String REPO_UPLOADED_TEMP = "REPO_UPLOADED_TEMP";
    public static final String SYS_CONFIG_SYNC = "SYS_CONFIG_SYNC";
    public static final String REPO_URL_TEMPLATE_EMAIL = "REPO_URL_TEMPLATE_EMAIL";
    public static final String TIME_LOCK = "TIME_LOCK";
    public static final String EXPIRED_PASSWORD = "EXPIRED_PASSWORD";
    public static final String MIN_PASSWORD_AGE = "MIN_PASSWORD_AGE";
    public static final String FLAG_UPPER_CASE = "FLAG_UPPER_CASE";
    public static final String FLAG_LOWER_CASE = "FLAG_LOWER_CASE";
    public static final String FLAG_NUMBER_CASE = "FLAG_NUMBER_CASE";
    public static final String FLAG_SPECIAL_CASE = "FLAG_SPECIAL_CASE";
    public static final String FLAG_COMPLEXITY = "FLAG_COMPLEXITY";
    public static final String FAILED_LOGIN_COUNT = "FAILED_LOGIN_COUNT";
    public static final String FLAG_AUTO_UNLOCK = "FLAG_AUTO_UNLOCK";
    public static final String NUMBER_PASSWORD_USED = "NUMBER_PASSWORD_USED";
    public static final String MIN_PASSWORD_LENGTH = "MIN_PASSWORD_LENGTH";
    public static final String MAX_PASSWORD_LENGTH = "MAX_PASSWORD_LENGTH";
    public static final String FLAG_FIRST_TIME_LOGIN = "FLAG_FIRST_TIME_LOGIN";
    public static final String EMAIL_HOST = "EMAIL_HOST";
    public static final String EMAIL_DEFAULT = "EMAIL_DEFAULT";
    public static final String EMAIL_PASSWORD = "EMAIL_PASSWORD";
    public static final String REPO_URL_ATTACH_FILE = "REPO_URL_ATTACH_FILE";
    public static final String EMAIL_PORT = "EMAIL_PORT";
    public static final String EMAIL_PORT_INBOX = "EMAIL_PORT_INBOX";
    public static final String EMAIL_DEFAULT_NAME = "EMAIL_DEFAULT_NAME";
    public static final String URL_DEFAULT = "URL_DEFAULT";
    public static final String FLAG_USED_BPM = "FLAG_USED_BPM";

    public static final String CMS_CONTACT_EMAIL_FEEDBACK_DSUCCESS = "CMS_CONTACT_EMAIL_FEEDBACK_DSUCCESS";
    public static final String CMS_CONTACT_EMAIL_RECOVERY_CODE = "CMS_CONTACT_EMAIL_RECOVERY_CODE";
    public static final String CMS_CONTACT_EMAIL_CONTRACT_ASSIGNMENT = "CMS_CONTACT_EMAIL_CONTRACT_ASSIGNMENT";
    public static final String CMS_CONTACT_EMAIL_TERMINATE_AGENCY_CONTRACT = "CMS_CONTACT_EMAIL_TERMINATE_AGENCY_CONTRACT";
    public static final String CMS_CONTACT_EMAIL_PROMOTION_AND_DEMOTION = "CMS_CONTACT_EMAIL_PROMOTION_AND_DEMOTION";
    public static final String CMS_CONTACT_EMAIL_COMMISSION_AND_BONUS = "CMS_CONTACT_EMAIL_COMMISSION_AND_BONUS";
    public static final String CMS_CONTACT_EMAIL_INCOME_DEBT = "CMS_CONTACT_EMAIL_INCOME_DEBT";
    public static final String CMS_CONTACT_EMAIL_HOLD_INCOME = "CMS_CONTACT_EMAIL_HOLD_INCOME";
    public static final String CMS_CONTACT_EMAIL_EMULATION = "CMS_CONTACT_EMAIL_EMULATION";
    public static final String CMS_CONTACT_EMAIL_OTHER = "CMS_CONTACT_EMAIL_OTHER";

	public static final String IIBHCMS_BASE_URL = "IIBHCMS_BASE_URL";
	public static final String EAPPIBPS_BASE_URL = "EAPPIBPS_BASE_URL";
	public static final String EAPP_BASE_URL = "EAPP_BASE_URL";
	public static final String ERECRUIT_BASE_URL = "ERECRUIT_BASE_URL";
	public static final String CSPORTAL_BASE_URL = "CSPORTAL_BASE_URL";
	public static final String PERSONAL_INFO_SUBMITED = "PERSONAL_INFO_SUBMITED";
    
    /**
     * logo
     */
    public static final String LOGO_LARGE = "LOGO_LARGE";
    public static final String LOGO_LARGE_REPO_ID = "LOGO_LARGE_REPO_ID";
    public static final String LOGO_MINI = "LOGO_MINI";
    public static final String LOGO_MINI_REPO_ID = "LOGO_MINI_REPO_ID";
    public static final String POPUP = "POPUP";

    /** System config - DATE_PATTERN */
    public static final String MONTH_PATTERN = "MONTH_PATTERN";
    /** System config - FROM_YEAR */
    public static final String FROM_YEAR = "FROM_YEAR";
    /** System config - TO_YEAR */
    public static final String TO_YEAR = "TO_YEAR";

    public static final String EMAIL_CONTACT = "EMAIL_CONTACT";
    public static final String CUSTOMER_SERVICE_EMAIL = "CUSTOMER_SERVICE_EMAIL";

    /** Folder config - REPOSITORY_ROOT_FOLDER */
    public static final String REPOSITORY_ROOT_FOLDER = "REPOSITORY_ROOT_FOLDER";

    public static String PARENT_LINK_SYMBOL = "PARENT_LINK_SYMBOL";
    public static String TEAM_TYPE = "TEAM_TYPE";
    public static String CURRENCY_DEFAULT = "CURRENCY_DEFAULT";
    public static String EMAIL_OPTION = "EMAIL_OPTION";
    public static String SCHEDULE_MAIL = "SCHEDULE_MAIL";
    public static String CHECK_LIMIT_EMAIL = "CHECK_LIMIT_EMAIL";
    public static String LIMIT_EMAIL = "LIMIT_EMAIL";
    public static String UPLOAD_DOMAIN = "UPLOAD_DOMAIN";
    public static String UPLOAD_PATH = "UPLOAD_PATH";
    public static final String INDEX_DIR = "INDEX_DIR";
    public static final String HOTLINE_PHONE_NUMBER = "HOTLINE_PHONE_NUMBER";
    public static String DBCASE = "DBCASE"; // case HSSA

    // LOCKED_USER
    public static Long LOCKED_DURATION = Long.parseLong("3600"); // by second
    public static Integer MAX_ACCESS = 5;
    public static Integer STATUS_USER_NORMAL = 1;
    public static Integer STATUS_USER_LOCKED = 0;

    // jBPM config
    public static String JBPM_HOST = "JBPM_HOST";
    public static String JBPM_LOS_DEPLOYMENT_ID = "JBPM_LOS_DEPLOYMENT_ID";
    public static String JBPM_LOS_PROCESS_DEFINITION_ID = "JBPM_LOS_PROCESS_DEFINITION_ID";

    // nodejs
    public static String MAX_CONNECTION_CHAT = "MAX_CONNECTION_CHAT";
    public static String NODE_HOST = "NODE_HOST";
    public static String SESSION_TIMEOUT_CHAT = "SESSION_TIMEOUT_CHAT";
    public static String SESSION_TIMEOUT_CHAT_SERVER = "SESSION_TIMEOUT_CHAT_SERVER";

    // agent
    public static String LEVEL_DEFAULT_AGENCY = "LEVEL_DEFAULT_AGENCY";
    public static String GRADE_AGENCY_VIEW_DETAIL = "GRADE_AGENCY_VIEW_DETAIL";

    // LDAP
    public static final String LDAP_IS_USING_WHEN_LOGIN = "LDAP_IS_USING_WHEN_LOGIN";

    // TN5250J config
    public static final String TN5250J_USERNAME_1 = "TN5250J_USERNAME_1";
    public static final String TN5250J_PASSWORD_1 = "TN5250J_PASSWORD_1";
    public static final String TN5250J_USERNAME_2 = "TN5250J_USERNAME_2";
    public static final String TN5250J_PASSWORD_2 = "TN5250J_PASSWORD_2";
    public static final String TN5250J_HOSTNAME = "TN5250J_HOSTNAME";

    // SWIFT SMB FILE
    public static final String MT_DOMAIN = "MT_DOMAIN";
    public static final String MT_SWIFT_PATH = "MT_SWIFT_PATH";
    public static final String MT_USERNAME = "MT_USERNAME";
    public static final String MT_PASSWORD = "MT_PASSWORD";
    public static final String MT_SWIFT_PATH_SEND = "MT_SWIFT_PATH_SEND";
    public static final String MT_SWIFT_PATH_RECEIVE = "MT_SWIFT_PATH_RECEIVE";

    public static final String MT_SWIFT_PATH_CLIENT = "MT_SWIFT_PATH_CLIENT";
    public static final String MT_SWIFT_PATH_CLIENT_ARCHIVE = "MT_SWIFT_PATH_CLIENT_ARCHIVE";
    public static final String MT_SWIFT_PATH_CLIENT_ERROR = "MT_SWIFT_PATH_CLIENT_ERROR";
    public static final String MT_SWIFT_PATH_CLIENT_EXCEPTION = "MT_SWIFT_PATH_CLIENT_EXCEPTION";

    // currency
    public static String CURRENCY_PATTERN_VND = "CURRENCY_PATTERN_VND";
    public static String CURRENCY_PATTERN_USD = "CURRENCY_PATTERN_USD";
    public static String QUANTITY_PATTERN = "QUANTITY_PATTERN";

    public static final String DATE_FORMAT = "DATE_FORMAT";

    // WEB API
    public static String API_URL = "API_URL";

    public static final String GROW_COLLECTION_LIMIT = "GROW_COLLECTION_LIMIT";

    public static final String MAX_THREAD_POOL = "MAX_THREAD_POOL";

    public static final String MAX_MINUTES_AWAIT_TERMINATION = "MAX_MINUTES_AWAIT_TERMINATION";

    public static final String ADMIN_GROUP_CODE = "ADMIN_GROUP_CODE";

    public static final String MAX_NUM_ERROR = "MAX_NUM_ERROR";

    /** phatvt : Password Account Policy */

    public static final String PASSWORD_COMPLEXITY = "PASSWORD_COMPLEXITY";

    public static final String LENGTH_CASE = "LENGTH_CASE";

    /** END */
    // sFTP
    public static final String SFTP_FOLDER = "SFTP_FOLDER";
    public static final String SFTP_HOST = "SFTP_HOST";
    public static final String SFTP_PORT = "SFTP_PORT";
    public static final String SFTP_USERNAME = "SFTP_USERNAME";
    public static final String SFTP_PASSWORD = "SFTP_PASSWORD";

    public static final String SEND_BY_BATCH = "SEND_BY_BATCH";
    public static final String SEND_DIRECT_NO_SAVE = "SEND_DIRECT_NO_SAVE";
    public static final String SEND_DIRECT_SAVE = "SEND_DIRECT_SAVE";
    
    // CMS BANNER
    public static final String PHYSICAL_PATH_TEMP = "PHYSICAL_PATH_TEMP";
    public static final String PHYSICAL_PATH_MAIN = "PHYSICAL_PATH_MAIN";
    // CMS BANNER
    
    // CMS ALL
    public static final String PRODUCT_MICROSITE = "PRODUCT_MICROSITE";
    public static final String ON_OFF_CHAT = "ON_OFF_CHAT";
    // capcha secret key
    public static final String CAPTCHA_SECRET_KEY = "CAPTCHA_SECRET_KEY";
    
    

    // @Value("${configuration.cryptor.isEncryptWithSystemUUID}")
    private static final Boolean isEncryptWithSystemUUID = BooleanUtils.toBooleanDefaultIfNull(
            BooleanUtils.toBooleanObject(Utility.getConfigureProperty("configuration.cryptor.isEncryptWithSystemUUID")), Boolean.FALSE);

    // @Value("${configuration.cryptor.secretKey}")
    private static final String secretKey = Utility.getConfigureProperty("configuration.cryptor.secretKey");
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(SystemConfig.class);
    
	@PostConstruct
	@Transactional(rollbackFor = Exception.class)
	public void init() {
		try {
			logger.error("BEGIN Init");

			logger.error("BEGIN initSystemSetting");
			// Init system setting
			initSystemSetting(null);
			logger.error("END initSystemSetting");

			logger.error("BEGIN initRepository");
			// Init repository
			initRepository(null);
			logger.error("END initRepository");

			logger.error("BEGIN languages");
			// Set list language
			List<LanguageDto> languages = languageService.getList();
			for (LanguageDto languageDto : languages) {
				String key = LANGUAGE_DEFAULT;
				if (null != languageDto.getCompanyId()) {
					key = key.concat(String.valueOf(languageDto.getCompanyId()));
				}
				key = languageDto.getCode().concat(key);
				languageMap.put(key, languageDto);
			}
			logger.error("END languages");

			logger.error("BEGIN styles");
			// Set liststyle
			List<JcaStyleDto> styles = styleService.getStyleList();
			for (JcaStyleDto styleDto : styles) {
				String key = STYLE_DEFAULT;
				if (null != styleDto.getCompanyId()) {
					key = key.concat(String.valueOf(styleDto.getCompanyId()));
				}
				key = styleDto.getCode().concat(key);
				styleMap.put(key, styleDto);
			}
			logger.error("END styles");
		} catch (Exception e) {
			logger.error("Init", e);
		}
    }

//    @SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public void initSystemSetting(Long companyId) {
		// Init System Setting
		List<JcaSystemConfig> settings = null;
		if (null == companyId) {
			Iterable<JcaCompany> companyList = jcaCompanyService.findAll();
			for (JcaCompany company : companyList) {
				if (null != company.getDeletedId() && 0 == company.getDeletedId()) {
					systemSettingService.mergeSystemSetting(company.getId());
				}
			}
			settings = systemSettingService.findAll();
		} else {
			systemSettingService.mergeSystemSetting(companyId);
			settings = systemSettingService.findAllByCompanyId(companyId);
		}
		for (JcaSystemConfig systemSetting : settings) {
			String key = systemSetting.getSettingKey();
			if (null != systemSetting.getCompanyId()) {
				key = key.concat(String.valueOf(systemSetting.getCompanyId()));
			}
			String value = systemSetting.getSettingValue();
			configMap.put(key, value);
		}
	}

    @Transactional
    public void initRepository(Long repoId) {
        // Init repository
        List<JcaRepository> repositorys = repositoryService.getAllRepositoryByCompanyId(null, repoId);
        for (JcaRepository repository : repositorys) {
            String key = repository.getId().toString();
            repositoryMap.put(key, repository);
        }
    }
    /**
     * Get physical path
     * 
     * @param idStr    type String
     * @param dateRule type Date
     * @return String
     * @author KhoaNA
     */
    public String getPhysicalPathById(String idStr, Date dateRule) {
        StringBuilder physicalPathBuilder = new StringBuilder();

        JcaRepository repository = repositoryMap.get(idStr);
        if (repository == null) {
            String msg = "Can not found repository id " + idStr;
            logger.error(msg);
            throw new SystemException(msg);
        }

        String physicalPath = repository.getPhysicalPath();
        physicalPathBuilder.append(physicalPath);

        String subFolderRule = repository.getSubFolderRule();
        if (!StringUtils.isEmpty(subFolderRule)) {
            if (dateRule == null) {
                dateRule = comService.getSystemDateTime();
            }

            String dateFormat = CommonDateUtil.formatDateToString(dateRule, subFolderRule);
            physicalPathBuilder.append(dateFormat);
        }

        return physicalPathBuilder.toString();
    }
/**
     * getRepoByKey
     * 
     * @param key
     * @param companyId
     * @param dateRule
     * @return
     * @author HungHT
     */
    public JcaRepository getRepoByKey(String key, Long companyId, Date dateRule) {
        String repoId = this.getConfig(key, companyId);
        if (StringUtils.isEmpty(repoId)) {
            String msg = "Can not found repository by key: " + key;
            logger.error(msg);
            throw new SystemException(msg);
        }
        return this.getRepoById(Long.valueOf(repoId), dateRule);
    }

    /**
     * getRepoById
     * 
     * @param repoId
     * @param dateRule
     * @return
     * @author HungHT
     */
    public JcaRepository getRepoById(Long repoId, Date dateRule) {
        JcaRepository repoResult = null;
        JcaRepository repository = repositoryMap.get(repoId.toString());
        if (repository == null) {
            String msg = "Can not found repository id: " + repoId.toString();
            logger.error(msg);
            throw new SystemException(msg);
        }

        // Get physicalPath
        try {
            if (null == repoResult) {
                repoResult = new JcaRepository();
            }
            repoResult.setId(repository.getId());
            repoResult.setCode(repository.getCode());
            repoResult.setCompanyId(repository.getCompanyId());
            repoResult.setFileProtocol(repository.getFileProtocol());
            repoResult.setUsername(repository.getUsername());
            repoResult.setPassword(StringUtils.isBlank(repository.getPassword()) ? null
                    : PasswordUtil.decryptString(repository.getPassword()));
            repoResult.setPhysicalPath(repository.getPhysicalPath());
            // Get subFolderRule
            String subFolderRule = repository.getSubFolderRule();
            if (!StringUtils.isEmpty(subFolderRule)) {
                if (dateRule == null) {
                    dateRule = comService.getSystemDateTime();
                }
                String dateFormat = CommonDateUtil.formatDateToString(dateRule, subFolderRule);
                repoResult.setSubFolderRule(dateFormat);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new SystemException(e.getMessage());
        }
        return repoResult;
    }
    /**
     * Get configure by key
     * 
     * @param key
     * @return String
     */
    public String getConfig(String key) {
        Long companyId = UserProfileUtils.getCompanyId();
        return this.getConfig(key, companyId != null ? companyId : 2L);
    }

	public String getConfig(String key, Long companyId) {
//        String configKey = key;
//        // Get config default based on company
//        if (null != companyId) {
//            configKey = configKey.concat(String.valueOf(companyId));
//        }
//        String result = configMap.get(configKey);
//
//        // Get config default
//        if (CommonStringUtil.isBlank(result)) {
//            result = configMap.get(key);
//        }
//        
//        if(CommonStringUtil.isBlank(result)) {
////            logger.error("[getConfig] missing key: " + configKey);
////            20200716_removeLog 
////            DebugLogger.debug("[getConfig] missing key: [%s]", configKey);
//        }
//    	
//        return result;

		if (null != companyId) {
			return systemSettingService.getValueByKey(key, companyId);
		}
		return systemSettingService.getValueByKeyDefault(key);

	}
    /**
     * getConfigDecrypted
     * 
     * @param key
     * @return
     * @author HungHT
     */
    public String getConfigDecrypted(String key) {
        String result = StringUtils.isNotBlank(this.getConfig(key))
                ? PasswordUtil.decryptString(this.getConfig(key))
                : this.getConfig(key);
        return result;
    }

	public int getIntConfig(String key) {
		int result = 0;
		String value = this.getConfig(key);
		if (CommonStringUtil.isNotEmpty(value)) {
			try {
				result = Integer.parseInt(value);
			} catch (NumberFormatException e) {
				result = 0;
			}
		}
		return result;
	}

	public int getIntConfig(String key, Long companyId) {
		int result = 0;
		String value = this.getConfig(key, companyId);
		if (CommonStringUtil.isNotEmpty(value)) {
			try {
				result = Integer.parseInt(value);
			} catch (NumberFormatException e) {
				result = 0;
			}
		}
		return result;
	}

	public String getConfigDecrypted(String key, Long companyId) {
//        String result = CommonStringUtil.isNotBlank(this.getConfig(key, companyId))
//                ? JCanaryPasswordUtil.decryptString(this.getConfig(key, companyId))
//                : getConfig(key, companyId);

		String result = getConfig(key, companyId);
		return result;
	}

	public List<Integer> getListPageSize() {
		List<Integer> list = new ArrayList<Integer>();
		String listPageSize = getConfig(LIST_PAGE_SIZE);
		if (listPageSize != null) {
			String[] pages = listPageSize.split(",");
			if (pages.length > 0) {
				for (String i : pages) {
					list.add(Integer.parseInt(i));
				}
			}
		}
		return list;
	}
/**
     * getListPage
     * 
     * @param pageSize
     * @return
     * @author HungHT
     */
    public List<Integer> getListPage(int pageSize) {
        return getListPageSize();
    }

    /**
     * getSizeOfPage
     * 
     * @param listPageSize
     * @param pageSize
     * @return
     * @author HungHT
     */
    public int getSizeOfPage(List<Integer> listPageSize, int pageSize) {

        int sizeOfPage = 0;
        if (pageSize == 0) {
            sizeOfPage = !listPageSize.isEmpty() ? listPageSize.get(0) : getIntConfig(SystemConfig.PAGING_SIZE);
        } else {
            sizeOfPage = pageSize;
        }
        return sizeOfPage;
    }
    /**
     * getStartIndex
     * 
     * @param currentPage
     * @param sizeOfPage
     * @return
     * @author HungHT
     */
    public int getStartIndex(int currentPage, int sizeOfPage) {
        return (currentPage - 1) * sizeOfPage;
    }

/**
     * getStyle
     * 
     * @return
     * @author HungHT
     */
    public JcaStyleDto getStyle() {
        String style = null;
        JcaStyleDto styleDto = new JcaStyleDto();
        try {
            // Get HttpServletRequest
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes();
            HttpServletRequest httpServletRequest = requestAttributes.getRequest();
            // Get style
            String cookieStyle = CookieUtils.getCookie(UserProfileUtils.getAccountId(), CookieUtils.COOKIE_STYLE,
                    httpServletRequest);
            if (StringUtils.isNotBlank(cookieStyle)) {
                style = cookieStyle;
            } else {
                style = UserProfileUtils.getUserPrincipal().getStyle();
            }
            Long companyId = UserProfileUtils.getCompanyId();
            String key = STYLE_DEFAULT;
            if (null != companyId) {
                key = key.concat(String.valueOf(companyId));
            }
            JcaStyleDto check = styleMap.get(style.concat(key));
            if (null == check) {
                key = STYLE_DEFAULT;
            }
            String keyMap = style.concat(key);
//            for (Map.Entry<String, StyleDto> entry : styleMap.entrySet()) {
//                if (keyMap.equalsIgnoreCase(entry.getKey())) {
//                    styleDto = entry.getValue();
//                    break;
//                }
//            }
            styleDto = styleMap.getOrDefault(keyMap, new JcaStyleDto());

        } catch (Exception e) {
            // 20200716_removeLog
            // logger.error(e.getMessage());
        }
//        if (null == styleDto) {
//            styleDto = new StyleDto();
//        }
        return styleDto;
    }
    
    /**
     * getStyleLogin
     * 
     * @param companyId
     * @param styleDefault
     * @return
     * @author HungHT
     */
    public JcaStyleDto getStyleLogin(Long companyId, String styleDefault) {
        String style = null;
        JcaStyleDto styleDto = new JcaStyleDto();
        try {
            // Get HttpServletRequest
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes();
            HttpServletRequest httpServletRequest = requestAttributes.getRequest();
            // Get style
            String cookieStyle = CookieUtils.getCookie(UserProfileUtils.getAccountId(), CookieUtils.COOKIE_STYLE,
                    httpServletRequest);
            if (StringUtils.isNotBlank(cookieStyle)) {
                style = cookieStyle;
            } else {
                style = styleDefault;
            }
            String key = STYLE_DEFAULT;
            if (null != companyId) {
                key = key.concat(String.valueOf(companyId));
            }
            JcaStyleDto check = styleMap.get(style.concat(key));
            if (null == check) {
                key = STYLE_DEFAULT;
            }
            String keyMap = style.concat(key);
            for (Map.Entry<String, JcaStyleDto> entry : styleMap.entrySet()) {
                if (keyMap.equalsIgnoreCase(entry.getKey())) {
                    styleDto = entry.getValue();
                    break;
                }
            }
        } catch (Exception e) {
            // 20200716_removeLog
            // logger.error(e.getMessage());
        }
        if (null == styleDto) {
            styleDto = new JcaStyleDto();
        }
        return styleDto;
    }
    
    /**
     * getStyleList
     * 
     * @return
     * @author HungHT
     */
    public List<JcaStyleDto> getStyleList() {
        try {
            Long companyId = UserProfileUtils.getCompanyId();
            List<JcaStyleDto> styles = new ArrayList<>();
            String key = STYLE_DEFAULT;
            if (null != companyId) {
                key = key.concat(String.valueOf(companyId));
            }
            JcaStyleDto check = styleMap.get(key);
            if (null == check) {
                key = STYLE_DEFAULT;
            }
            for (Map.Entry<String, JcaStyleDto> entry : styleMap.entrySet()) {
                String keyMap = entry.getValue().getCode().concat(key);
                if (keyMap.equalsIgnoreCase(entry.getKey())) {
                    styles.add(entry.getValue());
                }
            }
            Collections.sort(styles, Comparator.comparing(JcaStyleDto::getSort));
            return styles;
        } catch (Exception e) {
            // 20200716_removeLog
//            logger.error(e.getMessage());
            return null;
        }
    }
    
    /**
     * getLanguageList
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    public List<LanguageDto> getLanguageList() {
        try {
            Long companyId = UserProfileUtils.getCompanyId();
            List<LanguageDto> languages = new ArrayList<>();
            String key = LANGUAGE_DEFAULT;
            if (null != companyId) {
                key = key.concat(String.valueOf(companyId));
            }
            LanguageDto check = languageMap.get(key);
            if (null == check) {
                key = LANGUAGE_DEFAULT;
            }
            for (Map.Entry<String, LanguageDto> entry : languageMap.entrySet()) {
                String keyMap = entry.getValue().getCode().concat(key);
                if (keyMap.equalsIgnoreCase(entry.getKey())) {
                    languages.add(entry.getValue());
                }
            }
            Collections.sort(languages, Comparator.comparing(LanguageDto::getSort));
            return languages;
        } catch (Exception e) {
            // 20200716_removeLog
//            logger.error(e.getMessage());
            return null;
        }
    }

	public int settingPageSizeList(int pageSize, PageWrapper<?> pageWrapper, int page) {
		List<Integer> listPageSize = getListPageSize();
		int pagingSizeDB = getIntConfig(SystemConfig.PAGING_SIZE);
		int sizeOfPage = 0;
		if (pageSize == 0) {
			sizeOfPage = listPageSize.isEmpty() ? pagingSizeDB : listPageSize.get(listPageSize.indexOf(pagingSizeDB));
		} else {
			sizeOfPage = pageSize;
		}
		if (page == 0) {
			page = 1;
		}

		pageWrapper.setCurrentPage(page);
		pageWrapper.setSizeOfPage(sizeOfPage);
		pageWrapper.setListPageSize(listPageSize);
		// pageWrapper = new PageWrapper<>(page, sizeOfPage, listPageSize);
		return sizeOfPage;
	}

    public static String decryptString(String encryptedString, Boolean isEncryptWithSystemUUID, String secretKey) {
        if (BooleanUtils.isTrue(isEncryptWithSystemUUID)) {
            return AesUtil.decryptWithSystemUUID(encryptedString);
        } else {
            return AesUtil.decryptWithSecretKey(encryptedString, secretKey);
        }
    }

    public static String decryptString(String encryptedString) {
        return decryptString(encryptedString, isEncryptWithSystemUUID, secretKey);
    }

    /**
     * getDefautLanguage
     * 
     * @return
     * @author HungHT
     */
    public LanguageDto getDefautLanguage() {
        try {
            // Get HttpServletRequest
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes();
            HttpServletRequest httpServletRequest = requestAttributes.getRequest();
            String lang = CookieUtils.getCookie(null, CookieUtils.COOKIE_LANGUAGE, httpServletRequest);
            if (StringUtils.isNotBlank(lang)) {
                return getLanguageByCode(lang);
            } else {
                LanguageDto languageDto = getLanguageByCode(UserProfileUtils.getLanguage());
                return languageDto;
            }
        } catch (Exception e) {
            // 20200716_removeLog
//            logger.error(e.getMessage());
            return new LanguageDto();
        }
    }
    
/**
     * getLanguageByCode
     * 
     * @param code
     * @return
     * @author HungHT
     */
    private LanguageDto getLanguageByCode(String code) {
        Long companyId = UserProfileUtils.getCompanyId();
        LanguageDto lang = new LanguageDto();
        String key = LANGUAGE_DEFAULT;
        if (null != companyId) {
            key = key.concat(String.valueOf(companyId));
        }
        LanguageDto check = languageMap.get(key);
        if (null == check) {
            key = LANGUAGE_DEFAULT;
        }
        String keyMap = code.concat(key);
        for (Map.Entry<String, LanguageDto> entry : languageMap.entrySet()) {
            if (keyMap.equalsIgnoreCase(entry.getKey())) {
                lang = entry.getValue();
            }
        }
        return lang;
    }

    /**
     * getBuildVersion
     * 
     * @return
     * @author HungHT
     */
    public String getBuildVersion() {
        return Utility.getConfigureProperty("build.version");
    }

    /**
     * getUploadfileType
     * 
     * @param companyId
     * @return
     * @author DaiTrieu
     */
    public List<String> getUploadfileType(Long companyId) {
        List<String> result = new ArrayList<>();
        String uploadFileType = this.getConfig(SystemSettingKey.UPLOAD_FILE_TYPE, companyId);
        if (StringUtils.isNotBlank(uploadFileType)) {
            Pattern pattern = Pattern.compile(",");
            try {
                result = pattern.splitAsStream(uploadFileType).map(m -> m.trim()).collect(Collectors.toList());
            } catch (Exception e) {
                logger.error("[SystemConfig] | [getUploadfileType] uploadFileType: " + uploadFileType, e);
            }
        }
        return result;
    }

    public String getRev() {
    	return String.valueOf(Calendar.getInstance().getTime().getTime());
    }

}