/*******************************************************************************
 * Class        ：SystemSettingKey
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：tantm
 * Change log   ：2021/01/20：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.config;

/**
 * SystemSettingKey
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public class SystemSettingKey {

    // OZ Repository
    public static final String OZ_REPOSITORY_LOCAL_URL = "OZ_REPOSITORY_LOCAL_URL";
    public static final String OZ_REPOSITORY_USER = "OZ_REPOSITORY_USER";
    public static final String OZ_REPOSITORY_PASSWORD = "OZ_REPOSITORY_PASSWORD";

    // ECM Repository
    public static final String ECM_REPOSITORY_DOCUMENT = "ECM_REPOSITORY_DOCUMENT";

    public static final String UPLOAD_FILE_TYPE = "UPLOAD_FILE_TYPE";
    public static final String UPLOAD_FILE_SIZE_MAIN_FILE = "UPLOAD_FILE_SIZE_MAIN_FILE";
    public static final String UPLOAD_FILE_SIZE_ATTACH = "UPLOAD_FILE_SIZE_ATTACH";
    /** CONFIG MAIL FOR AWS*/
	public static final String AWS_EMAIL_SENDER = "AWS_EMAIL_SENDER";
	public static final String AWS_EMAIL_REGION = "AWS_EMAIL_REGION";
	public static final String AWS_REPO_URL_ATTACH_FILE = "AWS_REPO_URL_ATTACH_FILE";
	public static final String AWS_URL_DEFAULT = "AWS_URL_DEFAULT";
	public static final String AWS_ACCESS_KEY = "AWS_ACCESS_KEY";
	public static final String AWS_SECRET_KEY = "AWS_SECRET_KEY";
    // system setting smtp
	public static final String SMTP_EMAIL_HOST = "EMAIL_HOST";
	public static final String SMTP_EMAIL_PORT = "EMAIL_PORT";
	public static final String SMTP_EMAIL_PORT_INBOX= "EMAIL_PORT_INBOX";
	public static final String SMTP_EMAIL_PASSWORD = "EMAIL_PASSWORD";
	public static final String SMTP_EMAIL_DEFAULT = "EMAIL_DEFAULT";
	public static final String SMTP_SEND_EMAIL_TYPE_DEFAULT = "SEND_EMAIL_TYPE_DEFAULT";
	public static final String SMTP_EMAIL_CONTACT = "EMAIL_CONTACT";
	public static final String SMTP_CUSTOMER_SERVICE_EMAIL = "CUSTOMER_SERVICE_EMAIL";
	public static final String SMTP_EMAIL_DEFAULT_NAME = "EMAIL_DEFAULT_NAME";
	public static final String SMTP_REPO_URL_ATTACH_FILE = "REPO_URL_ATTACH_FILE";
	public static final String SMTP_URL_DEFAULT = "URL_DEFAULT";
	public static final String SMTP_EMAIL_USER_NAME = "SMTP_EMAIL_USER_NAME";

	public static final String SEND_SMS_URL = "SEND_SMS_URL";
	public static final String SENDER_SMS = "SENDER_SMS";
	public static final String SMS_TYPE = "SMS_TYPE";
	public static final String GOOGLE_MAP_API_KEY = "GOOGLE_MAP_API_KEY";
	public static final String TYPE_OF_SENDMAIL = "TYPE_OF_SENDMAIL";
	public static final String EXPIRED_TIME_NUMBER = "EXPIRED_TIME_NUMBER";
	public static final String SLOGAN_DSUCCESS = "SLOGAN_DSUCCESS";
	public static final String PATH_DOMAIN_ADMIN = "PATH_DOMAIN_ADMIN";
	public static final String PATH_DOMAIN_API = "PATH_DOMAIN_API";
	public static final String REGULATION_CATEGORY = "REGULATION_CATEGORY";
	public static final String AGENT_CAREER_CATEGORY = "AGENT_CAREER_CATEGORY";
	public static final String PROCESS_REGULATIONS_LEGISLATION_GA = "PROCESS_REGULATIONS_LEGISLATION_GA";
	public static final String BASE_DOMAIN_ADMIN = "BASE_DOMAIN_ADMIN";
	public static final String BASE_DOMAIN_API = "BASE_DOMAIN_API";
	
	public static final String IIBHCMS_BASE_URL = "IIBHCMS_BASE_URL";
	public static final String EAPPIBPS_BASE_URL = "EAPPIBPS_BASE_URL";
	public static final String EAPP_BASE_URL = "EAPP_BASE_URL";
	public static final String ERECRUIT_BASE_URL = "ERECRUIT_BASE_URL";
	public static final String CSPORTAL_BASE_URL = "CSPORTAL_BASE_URL";
	public static final String PERSONAL_INFO_SUBMITED = "PERSONAL_INFO_SUBMITED";
	public static final String QA_CODE_URL = "QA_CODE_URL";
}
