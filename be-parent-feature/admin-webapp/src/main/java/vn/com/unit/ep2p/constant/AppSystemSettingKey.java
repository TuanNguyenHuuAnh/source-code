/*******************************************************************************
 * Class        AppSystemSettingKey
 * Created date 2019/01/22
 * Lasted date  2019/01/22
 * Author       VinhLT
 * Change log   2019/01/2201-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.constant;

/**
 * AppSystemSettingKey
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public class AppSystemSettingKey {
    
	 // JCA CONNECT HUB
    public static final String JCA_HUB_DRIVER = "JCA_HUB_DRIVER";
    public static final String JCA_HUB_URL = "JCA_HUB_URL";
    public static final String JCA_HUB_USERNAME = "JCA_HUB_USERNAME";
    public static final String JCA_HUB_PAZZWORD = "JCA_HUB_PASSWORD";
    public static final String JCA_HUB_LIMIT = "JCA_HUB_LIMIT";
    public static final String JCA_HUB_CALL_SP_AFTER_SYNC = "JCA_HUB_CALL_SP_AFTER_SYNC";
    public static final String JCA_HUB_SQL_PATTERN = "JCA_HUB_SQL_PATTERN";
    // JCA FOLDER
    public static final String JCA_FOLDER = "JCA_FOLDER";

    // JCA CONNECT TREATS
    public static final String JCA_TREATS_URL = "JCA_TREATS_URL";
    public static final String JCA_TREATS_USERNAME = "JCA_TREATS_USERNAME";
    public static final String JCA_TREATS_PAZZWORD = "JCA_TREATS_PASSWORD";

    // REPORT FOLDER
    public static final String REPORT_BALANCE_FOLDER = "REPORT_BALANCE_FOLDER";
    public static final String REPORT_DAILY_TRADE_TO_CUSTOMER_FOLDER = "REPORT_DAILY_TRADE_TO_CUSTOMER_FOLDER";

    // REPO BMPN
    public static final String REPO_ACTIVITI_BMPN = "REPO_ACTIVITI_BMPN";
    
    // OZ Repository
    public static final String OZ_REPOSITORY_URL = "OZ_REPOSITORY_URL";
    public static final String OZ_REPOSITORY_LOCAL_URL = "OZ_REPOSITORY_LOCAL_URL";
    public static final String OZ_REPOSITORY_USER = "OZ_REPOSITORY_USER";
    public static final String OZ_REPOSITORY_PASSWORD = "OZ_REPOSITORY_PASSWORD";
    
    // ECM Repository
    public static final String ECM_REPOSITORY_DOCUMENT = "ECM_REPOSITORY_DOCUMENT";
    
    // HSM_URL
    public static final String HSM_URL = "HSM_URL";
    
    //INTEG
    public static final String AUTHEN_KEY = "AUTHEN_KEY";
    public static final String SYNC_USER_KEY = "DEFAULT_GROUP_KEY";
    public static final String INTEG_URL = "INTEG_URL";
    public static final String INTEG_SECRET_PASSWORD = "INTEG_SECRET_PASSWORD";
    public static final String INTEG_URL_PROCESS = "INTEG_URL_PROCESS";
    public static final String AUTHEN_KEY_PROCESS = "AUTHEN_KEY_PROCESS";
    
    //LOGIN API
    public static final String LOGIN_API_URL = "LOGIN_API_URL";
    public static final String LOGIN_API_AUTHEN_KEY = "LOGIN_API_AUTHEN_KEY";
    public static final String LOGIN_API_SECRET_KEY = "LOGIN_API_SECRET_KEY";
    public static final String LOGIN_API_HOST = "LOGIN_API_HOST";
    public static final String LOGIN_API_KEY = "LOGIN_API_KEY";
    
    //PUSH NOTIF & MAIL
    public static final String FIREBASE_URL = "FIREBASE_URL";
    public static final String FIREBASE_AUTHEN_KEY = "FIREBASE_AUTHEN_KEY";
    public static final String PUSH_NOTIFICATION = "PUSH_NOTIFICATION";
    public static final String PUSH_EMAIL = "PUSH_EMAIL";
    public static final String FIREBASE_WEB_API_KEY = "FIREBASE_WEB_API_KEY";
    public static final String FIREBASE_AUTH_DOMAIN = "FIREBASE_AUTH_DOMAIN";
    public static final String FIREBASE_DATABASE_URL = "FIREBASE_DATABASE_URL";
    public static final String FIREBASE_PROJECT_ID = "FIREBASE_PROJECT_ID";
    public static final String FIREBASE_STORAGE_BUCKET = "FIREBASE_STORAGE_BUCKET";
    public static final String FIREBASE_MESSAGE_ID = "FIREBASE_MESSAGE_ID";
    public static final String FIREBASE_APP_ID = "FIREBASE_APP_ID";
    public static final String FIREBASE_MEASUREMENT_ID = "FIREBASE_MEASUREMENT_ID";
    public static final String FIREBASE_PUBLIC_VAPID_KEY = "FIREBASE_PUBLIC_VAPID_KEY";
    
    public static final String FIREBASE_FLAG = "FIREBASE_FLAG";
    
    /** MOBILE_APP_VERSION */
    public static final String UPDATE_APP_VERSION_ANDROID = "UPDATE_APP_VERSION_ANDROID";
    public static final String UPDATE_APP_VERSION_IOS = "UPDATE_APP_VERSION_IOS";
    public static final String UPDATE_APP_VERSION_TYPE = "UPDATE_APP_VERSION_TYPE";
    public static final String UPDATE_APP_VERSION_MESSAGE = "UPDATE_APP_VERSION_MESSAGE";
    
    /** prioritize between director and vice director {0:CACURATOR || 1:SUPER_CACURATOR}*/
    public static final String PRIORITIZE_CACURATOR = "PRIORITIZE_CACURATOR";
    
    public static final String UPLOAD_FILE_TYPE = "UPLOAD_FILE_TYPE";
    public static final String UPLOAD_FILE_SIZE_MAIN_FILE = "UPLOAD_FILE_SIZE_MAIN_FILE";
    public static final String UPLOAD_FILE_SIZE_ATTACH = "UPLOAD_FILE_SIZE_ATTACH";
    
    public static final String NUM_DATE_DELETE_NOTIFI = "NUM_DATE_DELETE_NOTIFI";

    public static final String DISPLAY_SYSTEM_NAME = "DISPLAY_SYSTEM_NAME";
    
    public static final String LIST_USER_ALLOW_LOGIN = "LIST_USER_ALLOW_LOGIN";

}
