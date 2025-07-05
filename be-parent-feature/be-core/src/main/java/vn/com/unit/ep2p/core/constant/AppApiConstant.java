
/********************************************************************************
* Class        : AppApiConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/

package vn.com.unit.ep2p.core.constant;

import vn.com.unit.common.constant.CommonConstant;

/**
 * <p>
 * AppApiConstant
 * </p>
 * 
 * @version : 01-00
 * @since 01-00
 * @author : TaiTT
 */
public class AppApiConstant extends CommonConstant {

    // API
    public static final String API = "/api";

    public static final String VERSION = "/v1";

    /** Api root version 1.0 */
    public static final String API_V1 = API + VERSION;

    // API_AUTHEN
    /** Authen url root */
    public static final String API_AUTHEN = "/authen";
    /** Tag Swagger name Module authentication */
    public static final String API_AUTHEN_AUTHENTICAITON_DESCR = "Module authen of authentication ";
    /** Authen by Account */
    public static final String API_AUTHEN_AUTHENTICAITON = "/authentication";
    /** Authen login */
    public static final String API_AUTHEN_AUTHENTICAITON_LOGIN = "/login";
    public static final String API_AUTHEN_AUTHENTICAITON_LOGIN_MOBILE = "/login-mobile";

    /** Refresh token login */
    public static final String API_AUTHEN_AUTHENTICAITON_REFRESH_TOKEN = "/refresh-token";

    // API_ADMIN
    /** Admin url root */
    public static final String API_ADMIN = "/admin";
    /** Tag Swagger name Module admin by account masterdata */
    public static final String API_ADMIN_ACCOUNT_DESCR = "Account";
    /** Account for master admin */
    public static final String API_ADMIN_ACCOUNT = "/account";
    /** Tag Swagger name Module admin by position masterdata */
    public static final String API_ADMIN_POSITION_DESCR = "Position";
    /** Position for master admin */
    public static final String API_ADMIN_POSITION = "/position";
    /** Tag Swagger name Module admin by role masterdata */
    public static final String API_ADMIN_ROLE_DESCR = "Role";
    /** ROLE for master admin */
    public static final String API_ADMIN_ROLE = "/role";
    /** Tag Swagger name Module admin by item masterdata */
    public static final String API_ADMIN_ITEM_DESCR = "Item";
    /** ITEM for master admin */
    public static final String API_ADMIN_ITEM = "/item";
    public static final String API_ACCOUNT_REGISTER_IS_SENDMAIL = "/account-sendmail";

    public static final String API_LIST_CONFIRM_DOCUMENT= "/list-confirm-document";
    public static final String API_LIST_TERMS_CONDITIONS= "/list-terms-conditions";
    
    // API_USER
    /** User url root */
    public static final String API_USER = "/user";
    /** Tag Swagger name Module usser */
    public static final String API_USER_DESCR = "User";
    /** Form template */
    public static final String API_USER_FORM = "/form";
    /** Tag Swagger name Module admin by company masterdata */
    public static final String API_ADMIN_COMPANY_DESCR = "Company";
    /** Company for master admin */
    public static final String API_ADMIN_COMPANY = "/company";
    /** Tag Swagger name Module admin by menu masterdata */
    public static final String API_ADMIN_MENU_DESCR = "Menu";
    /** Menu for master admin */
    public static final String API_ADMIN_MENU = "/menu";
    /** Tag Swagger name Module admin by business masterdata */
    public static final String API_ADMIN_BUSINESS_DESCR = "Business";
    /** Business for master admin */
    public static final String API_ADMIN_BUSINESS = "/business";
    /** Tag Swagger name Module admin by process masterdata */
    public static final String API_ADMIN_PROCESS_DESCR = "Process";
    /** Process for master admin */
    public static final String API_ADMIN_PROCESS = "/process";
    /** Tag Swagger name Module admin by team masterdata */
    public static final String API_ADMIN_TEAM_DESCR = "Team";
    /** TEAM for master admin */
    public static final String API_ADMIN_TEAM = "/team";
    /** Business for master admin */
    public static final String API_ADMIN_CALENDAR_TYPE = "/calendar-type";
    /** Tag Swagger name Module admin by organization masterdata */
    public static final String API_ADMIN_CALENDAR_TYPE_DESCR = "Calendar";
    /** Business for master admin */
    public static final String API_ADMIN_ORGANIZATION = "/organization";
    /** Organization for master admin */
    public static final String API_ADMIN_ORGANIZATION_DESCR = "Organization";
    /** Tag Swagger name Module admin by process deploy masterdata */
    public static final String API_ADMIN_PROCESS_DEPLOY_DESCR = "Process Deploy";
    /** Process deploy for master admin */
    public static final String API_ADMIN_PROCESS_DEPLOY = "/process-deploy";
    /** Tag Swagger name Module admin by system config masterdata */
    public static final String API_ADMIN_SYSTEM_CONFIG_DESCR = "System Config";
    /** System config for master admin */
    public static final String API_ADMIN_SYSTEM_COFIG = "/systemConfig";
    /** Tag Swagger name Module admin by group system config masterdata */
    public static final String API_ADMIN_GROUP_SYSTEM_CONFIG_DESCR = "Group System Config";
    /** Group system config for master admin */
    public static final String API_ADMIN_GROUP_SYSTEM_COFIG = "/groupSystemConfig";
    /** Tag Swagger name Module admin by certificate authorities masterdata */
    public static final String API_ADMIN_ACCOUNT_CA_DESCR = "Certificate Authorities";
    /** Certificate authorities for master admin */
    public static final String API_ADMIN_ACCOUNT_CA = "/ca-management";
    /** Tag Swagger name Module admin by certificate authorities masterdata */
    public static final String API_ADMIN_ACCOUNT_ORG_DESCR = "Acount organization";
    /** Account organization for master admin */
    public static final String API_ADMIN_ACCOUNT_ORG = "/account/org";
    /** Tag Swagger name Module admin by category masterdata */
    public static final String API_ADMIN_CATEGORY_DESCR = "Category";
    /** Category for master admin */
    public static final String API_ADMIN_CATEGORY = "/category";
    /** Tag Swagger name Module admin by repository masterdata */
    public static final String API_ADMIN_REPOSITORY_DESCR = "Repository";
    /** Repository for master admin */
    public static final String API_ADMIN_REPOSITORY = "/repository";
    /** Tag Swagger name Module admin by role for company masterdata */
    public static final String API_ADMIN_ROLE_FOR_COMPANY_DESCR = "Role for company";
    /** Role company for master admin */
    public static final String API_ADMIN_ROLE_FOR_COMPANY = "/role-company";
    /** Tag Swagger name Module admin by role for team masterdata */
    public static final String API_ADMIN_ROLE_FOR_TEAM_DESCR = "Role for team";
    /** Role team for master admin */
    public static final String API_ADMIN_ROLE_FOR_TEAM = "/role-team";
    /** Tag Swagger name Module admin by form masterdata */
    public static final String API_ADMIN_FORM_DESCR = "Form";
    /** Form master admin */
    public static final String API_ADMIN_FORM = "/form";
    /** Tag Swagger name Module admin by email template masterdata */
    public static final String API_ADMIN_EMAIL_TEMPLATE_DESCR = "Email template";
    /** Email template of master admin */
    public static final String API_ADMIN_EMAIL_TEMPLATE = "/email-template";
    /** Tag Swagger name Module admin by role for item masterdata */
    public static final String API_ADMIN_ROLE_FOR_ITEM_DESCR = "Role for item";
    /** role item of master admin */
    public static final String API_ADMIN_ROLE_FOR_ITEM = "/role-item";
    /** Register form master admin */
    public static final String API_ADMIN_FORM_REGISTER = "/form/register";
    /** Group constant master admin */
    public static final String API_ADMIN_GROUP_CONSTANT = "/group-constant";
    /** Tag Swagger name Module admin by group constant masterdata */
    public static final String API_ADMIN_GROUP_CONSTANT_DESCR = "Group of constant display";
    /** Constant master admin */
    public static final String API_ADMIN_CONSTANT = "/constant";
    /** Tag Swagger name Module admin by constant masterdata */
    public static final String API_ADMIN_CONSTANT_DESCR = "Constant display";
    /** Day off master admin */
    public static final String API_ADMIN_DAY_OFF = "/day-off";
    /** Tag Swagger name Module admin by day off masterdata */
    public static final String API_ADMIN_DAY_OFF_DESCR = "Day off";
    /** Process button */
    public static final String API_ADMIN_PROCESS_BUTTON = "/process-button";
    /** Tag Swagger name Module admin by process param */
    public static final String API_ADMIN_PROCESS_BUTTON_DESCR = "Process button";
    /** Process param */
    public static final String API_ADMIN_PROCESS_PARAM = "/process-param";
    /** Tag Swagger name Module admin by process param */
    public static final String API_ADMIN_PROCESS_PARAM_DESCR = "Process param";
    /** Process step */
    public static final String API_ADMIN_PROCESS_STEP = "/process-step";
    /** Tag Swagger name Module admin by process step */
    public static final String API_ADMIN_PROCESS_STEP_DESCR = "Process step";
    /** Process permission */
    public static final String API_ADMIN_PROCESS_PERMISSION = "/process-permission";
    /** Tag Swagger name Module admin by process permission */
    public static final String API_ADMIN_PROCESS_PERMISSION_DESCR = "Process permission";
    /** Process status */
    public static final String API_ADMIN_PROCESS_STATUS = "/process-status";
    /** Tag Swagger name Module admin by process status */
    public static final String API_ADMIN_PROCESS_STATUS_DESCR = "Process status";
    /** sla config */
    public static final String API_SLA_CONFIG = "/sla-config";
    /** sla config detail */
    public static final String API_SLA_CONFIG_DETAIL = "/sla-config-detail";
    /** SLA Config */
    public static final String API_SLA_CONFIG_DESCR = "SLA Config";
    /** SLA Config detail */
    public static final String API_SLA_CONFIG_DETAIL_DESCR = "SLA Config detail";
    /** Create */
    public static final String CREATE = "/create";
    /** Update */
    public static final String UPDATE = "/update";
    /** Delete */
    public static final String DELETE = "/delete";
    /** List */
    public static final String LIST = "/list";
    public static final String LIST_BY_CONDITION = "/list-by-condition";
    /** Tag Swagger name Module admin by account team masterdata */
    public static final String API_ADMIN_ACCOUNT_TEAM_DESCR = "Acount Team";
    /** Account team for master admin */
    public static final String API_ADMIN_ACCOUNT_TEAM = "/account/team";
    /** Tag Swagger name Module admin by role for process masterdata */
    public static final String API_ADMIN_ROLE_FOR_PROCESS_DESCR = "Role for process";
    /** Role process of master admin */
    public static final String API_ADMIN_ROLE_FOR_PROCESS = "/role-process";

    // API_APP
    public static final String API_APP = "/app";
    public static final String API_APP_DOCUMENT = "/document";
    public static final String API_APP_DOCUMENT_DESCR = "Document";

    public static final String API_APP_COMMON_DOCUMENT_DESCR = "Common Document Process";
    /** App Workflow */
    public static final String API_APP_WORKFLOW = "/workflow";
    /** Tag Swagger name Module app workflow */
    public static final String API_APP_WORKFLOW_DESCR = "Workflow";
    /** Start */
    public static final String START = "/start";
    /** Complete */
    public static final String COMPLETE = "/complete";
    public static final String API_APP_DOCUMENT_MAIN_FILE = "/document-main-file";
    public static final String API_APP_DOCUMENT_MAIN_FILE_DESCR = "Document main file";
    public static final String API_APP_DASHBOARD = "/dashboard";
    public static final String API_APP_DASHBOARD_DESCR = "Dashboard";
    /** Tag Swagger name Module admin by Role for account masterdata */
    public static final String API_ADMIN_ROLE_FOR_ACCOUNT_DESCR = "Role for account";
    /** Role for account for master admin */
    public static final String API_ADMIN_ROLE_FOR_ACCOUNT = "/role-for-account";
    /** API_CONNECTION_MANAGEMENT */
    public static final String API_CONNECTION_MANAGEMENT = "/connection-management";
    /** POOL_STATUS */
    public static final String POOL_STATUS = "/pool-status";
    /** Tag Swagger name Module admin by role for position masterdata */
    public static final String API_ADMIN_ROLE_FOR_POSITION_DESCR = "Role for position";
    /** Role for position for master admin */
    public static final String API_ADMIN_ROLE_FOR_POSITION = "/role-for-position";
    /** Qrtz job class */
    public static final String API_QRTZ_JOB_CLASS = "/qrtz-job-class";
    /** Qrtz job class */
    public static final String API_QRTZ_JOB_CLASS_DESCR = "Qrtz Job Class";
    /** Qrtz job log */
    public static final String API_QRTZ_JOB_LOG = "/qrtz-job-log";
    /** Qrtz job log */
    public static final String API_QRTZ_JOB_LOG_DESCR = "Qrtz Job Log";
    /** Qrtz Job Schedule */
    public static final String API_QRTZ_JOB_SCHEDULE = "/qrtz-job-schedule";
    /** Qrtz Job Schedule */
    public static final String API_QRTZ_JOB_SCHEDULE_DESCR = "Qrtz Job Schedule";
    /** Qrtz Job */
    public static final String API_QRTZ_JOB = "/qrtz-job";
    /** Qrtz Job */
    public static final String API_QRTZ_JOB_DESCR = "Qrtz Job";
    /** Qrtz Job Store */
    public static final String API_QRTZ_JOB_STORE = "/qrtz-jog-store";
    /** Qrtz Job Store */
    public static final String API_QRTZ_JOB_STORE_DESCR = "Qrtz Job Store";
    /** Qrtz Job Type */
    public static final String API_QRTZ_JOB_TYPE = "/qrtz-jog-type";
    /** Qrtz Job Type */
    public static final String API_QRTZ_JOB_TYPE_DESCR = "Qrtz Job Type";
    /** Qrtz Schedule */
    public static final String API_QRTZ_SCHEDULE = "/qrtz-schedule";
    /** Qrtz Schedule */
    public static final String API_QRTZ_SCHEDULE_DESCR = "Qrtz Schedule";
    public static final String API_APP_DASHBOARD_TODO = "/dashboard/todo";
    public static final String API_APP_DASHBOARD_MY_DOCUMENT = "/dashboard/my-document";
    public static final String API_APP_DASHBOARD_TOTAL_TODO = "/dashboard/total/todo";
    public static final String API_APP_DASHBOARD_DOCUMENT_TASK = "/dashboard/document-task";
    /** Qrtz Job Schedule Resum */
    public static final String API_QRTZ_JOB_SCHEDULE_RESUM = "/qrtz-job-schedule-resum";
    /** Qrtz Job Schedule Pause */
    public static final String API_QRTZ_JOB_SCHEDULE_PAUSE = "/qrtz-job-schedule-pause";
    /** Qrtz Job Schedule Run */
    public static final String API_QRTZ_JOB_SCHEDULE_RUN = "/qrtz-job-schedule-run";
    /** Tag Swagger name Module by download */
    public static final String API_APP_DOWNLOAD_DESCR = "Download file";
    /** Download */
    public static final String API_APP_DOWNLOAD = "/download";
    /** Tag Swagger name Module by process service board */
    public static final String API_APP_JPM_SVC_BOARD_DESCR = "Jpm Svc Board";
    /** Process service board */
    public static final String API_APP_JPM_SVC_BOARD = "/jpm-svc-board";
    /** Combobox */
    public static final String API_APP_COMBOBOX = "/combobox";
    /** Combobox */
    public static final String API_APP_COMBOBOX_DESCR = "Combobox";
    /** Workflow diagram */
    public static final String API_APP_WORKFLOW_DIAGRAM = "/workflow-diagram";
    /** Workflow diagram */
    public static final String API_APP_WORKFLOW_DIAGRAM_DESCR = "Workflow diagram";
    /** Get role item one user */
    public static final String API_AUTHEN_ROLE = "/authen-role";
    /** Get role item one user */
    public static final String API_AUTHEN_ROLE_AUTHENTICAITON_DESCR = "Authen role";
    /** Notication of user */
    public static final String API_APP_NOTIFICATION = "/app-notification";
    /** Notication of user */
    public static final String API_APP_NOTIFICATION_DESCR = "App notification";
    /** Attach file */
    public static final String API_APP_ATTACH_FILE = "/attach-file";
    /** Attach file */
    public static final String API_APP_ATTACH_FILE_DESCR = "Attach file";
    public static final String API_APP_DASHBOARD_TOTAL_MY_DOCUMENT = "/dashboard/total/my-document";
    /** I18n translation */
    public static final String API_ADMIN_I18N_LOCALE = "I18n Translation";
    /** I18n translation */
    public static final String API_APP_GET_TRANSLATION = "/i18n/locales/{lng}/translation.json";
    /** I18n translation */
    public static final String API_APP_CLONE_TRANSLATION = "/i18n/locales/clone";
    /** I18n translation */
    public static final String API_APP_GET_VERSION_OF_TRANSLATION = "/i18n/locales/translation/version";
    public static final String API_ADMIN_MENU_TREE_BY_USER_ID = "/menu-tree-by-user";
    public static final String API_ADMIN_MENU_BY_USER_ID = "/menu-by-user";
    public static final String API_ADMIN_SYSTEM_COFIG_BY_KEY = "/get-system-config-by-key";
    
    /** News*/
    public static final String API_NEWS_LIST = "/list";
    public static final String LIST_ACTIVITY_ENABLED = "/list-activity-enabled";
    public static final String API_NEWS_DETAIL = "/detail";
    public static final String API_NEWS_TYPE = "/type";
    public static final String API_NEWS_BY_TYPE = "/news-by-type";
    public static final String API_NEWS_CATEGORY = "/category";
    public static final String API_NEWS_DETAIL_BY_LINK = "/detail-by-link";
    public static final String API_NEWS_TYPE_BY_NEWS = "/search-all-type-by-news";
    public static final String API_NEWS_LIST_BY_TYPE = "/list-by-type";
    public static final String API_BRANCH_BY_CONDITION = "/list-branch-by-condition";
    public static final String API_REGION = "/list-region-by-country";
    public static final String API_CITY_BY_REGION = "/list-city-by-region-country";
    public static final String API_CONSTANT_BY_KIND_AND_CODE = "/list-constant-by-kind-and-code";
    public static final String API_GET_AGENT_LIST = "/get-list-agent";
    public static final String API_GET_AGENT_DETAIL_BY_USERNAME = "/get-agent-detail-by-username";
    public static final String API_GET_AGENT_LOGIN_BY_USERNAME = "/get-agent-login-by-username";
    public static final String API_GET_AGENT_DETAIL_BY_FACE_MASK = "/get-agent-detail-by-face-mask";
    public static final String API_AGENT_ACTIVITY = "/get-agent-activity";
    public static final String API_GROUP_ACTIVITY = "/get-group-activity";
    public static final String API_NEWS_TYPE_BY_LANG = "/get-type-by-lang";
    public static final String API_NEWS_LIST_CAREER = "/list-career";
    public static final String API_NEWS_LIST_NEWS = "/list-news";
    public static final String API_CHECK_AGENT_BY_CODE = "/check-agent-by-code";
    public static final String API_NOTIFYS_LIST_BY_TYPE = "/list-notify-by-type";
    public static final String API_NOTIFYS_CHECK_READ = "/list-notify-check-read";
    public static final String API_INFO_AGENT_LIST= "/list-info-agent";
    public static final String API_INFO_AGENT_LIST_DETAIL= "/list-info-agent-detail";
    public static final String API_INFO_AGENT_LIST_BRANCH= "/list-info-branch-agent";
    public static final String API_CITY_BY_COUNTRY = "/list-city-by-country";
    public static final String API_DISTRICT_BY_CITY = "/list-district-by-city";
    public static final String API_PERSONAL_INFO_SUBMIT= "/personal-info-submit";
    public static final String API_EXPORT_LIST_INFO_AGENT= "/export-list-info-agent";
    public static final String API_LIKE_MESSAGE = "/like-message";
    public static final String API_CERTIFICATION_CONTRACT_DOCUMENT= "/certification-contract-document";
    public static final String API_WARD_BY_DISTRICT = "/list-ward-by-district";
    
    //CANDIDATE
    public static final String API_GET_CANDIDATE_EXAM_SCHEDULE_HOMEPAGE = "/get-candidate-exam-schedule-homepage";
    public static final String API_GET_CANDIDATE_EXAM_SCHEDULE = "/get-list-candidate-exam-schedule";
    public static final String API_GET_CANDIDATE_REGULATION_HOMEPAGE = "/get-candidate-regulation";
    public static final String API_GET_CANDIDATE_INFO = "/get-candidate-info";
    public static final String API_GET_CANDIDATE_SCHEDULE = "/get-list-candidate-schedule";
    public static final String API_EXPORT_CANDIDATE_EXAM_SCHEDULE = "/export-candidate-exam-schedule";
    public static final String API_EXPORT_CANDIDATE_SCHEDULE = "/export-candidate-schedule";
    public static final String API_EXPORT_CANDIDATE_REGULATION = "/export-candidate-regulation-by-type-regulation";
    public static final String API_GET_PROFILE_CANDIDATE = "/get-list-profile-candidate";
    public static final String API_CHECK_IS_BDOH = "/check-is-bdoh";
    
    public static final String API_GET_PROFILE_CANDIDATE_DETAIL = "/get-list-profile-candidate-detail";
    public static final String API_GET_CANDIDATE_TEMPOARY = "/get-list-candidate-temporary";
    public static final String API_EXPORT_CANDIDATE_TEMPOARY = "/export-list-candidate-temporary";
    // DS_APP
    public static final String DS_APP = "/ds";
    
    public static final String API_ACCOUNT_QUESTION_DESCR = "Account question";
    public static final String API_JWT_LOGIN = "/login-jwt";
    public static final String API_E_CARD_MANAGEMENT = "/e-card-management";
    public static final String API_LIST_OFFICE_DB2 = "/list-office-db2";
    public static final String API_FAVORITE_ADD = "/favorite-add";
    public static final String API_FAVORITE_DELETE = "/favorite-delete";
    public static final String API_FAVORITE_DETAIL = "/favorite-detail";
    public static final String API_EXPORT_FAVORITE = "/export-list-favorite";
    public static final String API_FAVORITE_DELETE_BY_IDS = "/favorite-delete-by-ids";
    public static final String API_FAVORITE_INSERT = "/favorite/add";

    /** Fags*/
    public static final String API_CATEROGY_FAQS_BY_ID = "/search-faqs-by-id-caterogy";

    public static final String API_PRODUCT_BY_POLICY = "/get-list-product-by-policy";
    public static final String API_INSURANCE_FEES_BY_POLICY = "/get-list-insurance-fees-by-policy";
    

    public static final String API_CERTIFICATE = "/get-list-cartificate";
    public static final String API_LETTER_AGENT = "/get-list-letter-agent";
    public static final String API_INTRODUCE_LETTER = "/get-list-introduce-letter";
    public static final String API_ZIPCODE = "/zipcode";
    public static final String API_APP_UPLOAD_AVATAR = "/upload-avatar";
    public static final String API_ANGENT_SA = "/get-list-agent-sa";
    public static final String API_GET_DATA_RANGE = "/get-data-range";
    public static final String API_EXPORT_ANGENT_SA = "/export-agent-sa";
    public static final String API_ANGENT_DISCIPLINARY = "/get-list-agent-disciplinary";
    public static final String API_EXPORT_LETTER_AGENT = "/export-list-letter-agent";
    public static final String API_EXPORT_INTRODUCE_LETTER = "/export-list-introduce-letter";
    public static final String API_ANGENT_DISCIPLINARY_DETAIL = "/get-agent-disciplinary-detail";
    public static final String API_EXPORT_ANGENT_DISCIPLINARY = "/export-agent-disciplinary";
    public static final String API_APP_SWITCH_ACCOUNT = "/switch-account";
    public static final String API_EXPORT_CUSTOMER_AGENT = "/export-list-customer-by-agent";
    public static final String API_EXPORT_POLICY_AGENT = "/export-list-policy-by-agent";
    public static final String API_EXPORT_PERSONAL_INSURANCE_DOC = "/export-list-personal-insurance-doc";
    public static final String API_APP_UPDATE_FACE_MASK = "/update-face-mask";
    public static final String API_EXPORT_TERMS_CONDITIONS = "/export-terms-conditions";
    public static final String API_EXPORT_CONTRACT_DOCUMENT = "/export-contract-document";
    public static final String API_EDIT_EVENTS= "/edit";
    public static final String API_LIST_EVENTS_BY_DATE= "/get-list-events-by-date";
    public static final String API_LIST_CUSTOMER_BIRTHDAY_BY_DATE= "/get-list-customer-birthday-by-date";
    public static final String API_LIST_CUSTOMER_CHARGE_BY_DATE= "/get-list-customer-charge-by-date";
    public static final String API_GET_ALL_EVENT_IN_MONTH= "/get-list-all-event-in-month";
    public static final String API_CERTIFICATE_ANOTHER = "/get-list-certificate-another";
    public static final String API_EXPORT_CERTIFICATE = "/export-certificate";
    public static final String API_LETTER_AGENT_TER = "/letter-agent-ter";
    public static final String API_GET_LIST_MASTERDATA = "/get-list-masterdata";
    public static final String API_GET_LIST_EVENTS_BY_CONDITION = "/get-list-events-by-conditon";
    public static final String API_GET_DETAIL_EVENT = "/get-detail-event";
    public static final String API_GET_QR_CODE = "/get-qr-code";
    public static final String API_UPDATE_EVENT = "/update-event";
    public static final String API_DELETE_EVENT = "/delete-event";
    public static final String API_GET_TITLE_LIST_OF_GUEST = "/get-title-list-of-guest";
    public static final String API_GET_LIST_GUESTS_EVENT = "/get-list-guests-event";
    public static final String API_CHECK_EVENT = "/check-event";
    public static final String API_CHECKIN_EVENT = "/checkin-event";
    public static final String API_EXPORT_LIST_EVENTS = "/export-list-events";
    public static final String API_EXPORT_LIST_EVENT_GUESTS = "/export-list-event-guests";

    /** Emulate*/
    public static final String API_EMULATE_PROGRAM= "/emulate-program-month";
    public static final String API_EMULATE_CONTEST_TYPE= "/list-emulate-contest-type";
    public static final String API_EMULATE_AND_CHELLENGE_PERSONAL= "/list-emulate-and-chellenge-personal";
    public static final String API_EXPORT_EMULATE_AND_CHELLENGE_PERSONAL= "/export-emulate-and-chellenge-personal";
    public static final String API_EMULATE_AND_CHELLENGE_RESULT_PERSONAL= "/list-result-emulate-and-chellenge-personal";
    public static final String API_EXPORT_EMULATE_AND_CHELLENGE_RESULT_PERSONAL= "/export-result-emulate-and-chellenge-personal";
    public static final String API_EMULATE_AND_CHELLENGE_GROUP= "/list-emulate-and-chellenge-group";
    public static final String API_EMULATE_AND_CHELLENGE_RESULT_GROUP= "/list-result-emulate-and-chellenge-group";
    public static final String API_EMULATE_AND_CHELLENGE_RESULT_GA= "/list-result-emulate-and-chellenge-ga";
    public static final String API_EXPORT_EMULATE_AND_CHELLENGE_RESULT_GA = "/export-list-result-emulate-and-chellenge-ga";
    public static final String API_EXPORT_EMULATE_AND_CHELLENGE_RESULT_GROUP= "/export-result-emulate-and-chellenge-group";
    public static final String API_EXPORT_EMULATE_AND_CHELLENGE_GROUP= "/export-list-emulate-and-chellenge-group";
    public static final String API_CHECK_AGENT_CHILD_IN_GROUP= "/check-agent-child-in-group";
    
    /** Report income*/
    public static final String API_REPORT_INCOME_BONUS_MO= "/get-list-report-income-bonus-mo";
    public static final String API_REPORT_INCOME_BONUS_TQP= "/get-list-report-income-bonus-tqp";
    public static final String API_REPORT_INCOME_BONUS_TSM= "/get-list-report-income-bonus-tsm";
    public static final String API_EXPORT_REPORT_INCOME_BONUS= "/export-report-income-bonus";
    
    public static final String API_INCOME_PERSONAL_WEEKLY= "/get-list-income-personal-weekly";
    public static final String API_EXPORT_INCOME_PERSONAL_WEEKLY= "/export-income-personal-weekly";
    
    public static final String API_DROPLIST_INCOME_WEEKLY_GA= "/get-droplist-income-weekly-ga";
    public static final String API_LIST_INCOME_WEEKLY_GA= "/get-list-income-weekly-ga";
    public static final String API_EXPORT_INCOME_WEEKLY_GA= "/export-income-weekly-ga";
    
    public static final String API_INCOME_PERSONAL_MONTHLY_DETAIL= "/get-list-income-personal-monthly-detail";
    public static final String API_INCOME_PERSONAL_MONTHLY_SUMARY= "/get-list-income-personal-monthly-sumary";
    public static final String API_EXPORT_INCOME_PERSONAL_MONTHLY= "/export-income-personal-monthly";
    
    public static final String API_INCOME_PERSONAL_YEARLY= "/get-list-income-personal-yearly";
    public static final String API_EXPORT_INCOME_PERSONAL_YEARLY= "/export-income-personal-yearly";
    
    /** Infomation page*/
    public static final String API_LIST_INFORMATION_DEBT= "/get-list-information-debt";
    public static final String API_EXPORT_LIST_INFORMATION_DEBT= "/export-list-information-debt";
    public static final String API_LIST_INFORMATION_GA= "/get-list-information-ga";
    public static final String API_LIST_OFFICE_GAD= "/get-list-office-of-gad";
    public static final String API_LIST_CHALLENGE_LETTER= "/get-list-challenge-letter-of-gad";
    public static final String API_LIST_CHALLENGE_LETTER_RESULT= "/get-list-challenge-letter-result-of-gad";
    public static final String API_EXPORT_CHALLENGE_LETTER_RESULT= "/export-list-challenge-letter-result-of-gad";
    
    /** Infomation page*/
    public static final String API_LIST_REPORT_BUSINESS_SUMARY= "/get-list-report-business-sumary";
    
    public static final String API_LIST_REPORT_BUSINESS_VIEW_GAD_TAB_PREMIUM= "/get-list-report-business-view-gad-premium";
    public static final String API_LIST_REPORT_BUSINESS_VIEW_GAD_TAB_MANPOWER= "/get-list-report-business-view-gad-manpower";
    public static final String API_EXPORT_REPORT_BUSINESS_VIEW_GAD_PREMIUM= "/export-list-report-business-view-gad-premium";
    public static final String API_EXPORT_REPORT_BUSINESS_VIEW_GAD_MANPOWER= "/export-list-report-business-view-gad-manpower";
    
    public static final String API_LIST_REPORT_BUSINESS_VIEW_BM_TAB_PREMIUM= "/get-list-report-business-view-bm-premium";
    public static final String API_LIST_REPORT_BUSINESS_VIEW_BM_TAB_MANPOWER= "/get-list-report-business-view-bm-manpower";
    public static final String API_EXPORT_REPORT_BUSINESS_VIEW_BM= "/export-list-report-business-view-bm";
    
    public static final String API_LIST_REPORT_BUSINESS_VIEW_UM_TAB_PREMIUM= "/get-list-report-business-view-um-premium";
    public static final String API_LIST_REPORT_BUSINESS_VIEW_UM_TAB_MANPOWER= "/get-list-report-business-view-um-manpower";
    public static final String API_EXPORT_REPORT_BUSINESS_VIEW_UM= "/export-list-report-business-view-um";
    
    //teritory
    public static final String API_LIST_TERRITORY_DB2 = "/list-territory-db2";
    //region
    public static final String API_LIST_REGION_DB2 = "/list-region-db2";
    public static final String API_LIST_AREA_DB2 = "/list-area-db2";
    public static final String API_LIST_EVENT_OFFICE_DB2 = "/list-event-office-db2";
    public static final String API_LIST_POSITION_DB2 = "/list-position-db2";
    public static final String API_PARENT_BY_AGENT_DB2 = "/parent-by-agent-db2";
    public static final String API_LIST_OFFICE_BY_GAD = "/list-office-by-gad";
    public static final String API_LIST_OFFICE_BY_GAD_PAYMENT = "/list-office-by-gad-for-payment";
    //personal insurance document
    public static final String API_LIST_PERSONAL_INSURANCE_DOC = "/list-personal-insurance-doc";
    public static final String API_LIST_PERSONAL_INSURANCE_DOC_DETAIL = "/personal-insurance-doc/detail";
    public static final String API_LIST_PERSONAL_INSURANCE_DOC_PRODUCT = "/personal-insurance-doc/list-product";
    public static final String API_LIST_PERSONAL_INSURANCE_REQUEST_APPRAISAL = "/personal-insurance-doc/list-request-appraisal";
    public static final String API_LIST_PERSONAL_INSURANCE_REQUEST_ADDITIONAL = "/personal-insurance-doc/list-request-additional";
    public static final String API_LIST_PERSONAL_INSURANCE_FILE = "/personal-insurance-doc/list-file-submitted";
    public static final String API_LIST_OFFICE_INSURANCE = "/office-insurance/list";
    public static final String API_LIST_OFFICE_INSURANCE_TYPE = "/office-insurance/list-type";
    public static final String API_EXPORT_OFFICE_INSURANCE_TYPE_BM_UM = "export-office-insurance-document-bm-um";
    public static final String API_LIST_OFFICE_INSURANCE_CONTRACT_TYPE = "/office-insurance/contract/list-type";
    public static final String API_LIST_OFFICE_INSURANCE_CONTRACT_TYPE_TYPE = "/office-insurance/contract/detail-type";
    public static final String API_EXPORT_OFFICE_INSURANCE = "export-office-insurance";
    public static final String API_EXPORT_OFFICE_INSURANCE_BM_UM = "export-office-insurance-bm-um";
    public static final String API_EXPORT_OFFICE_INSURANCE_BM_UM_DETAIL = "export-office-insurance-bm-um-detail";
    public static final String API_DETAIL_OFFICE_INSURANCE = "/office-insurance/detail";
    public static final String API_DETAIL_OFFICE_INSURANCE_POLICY = "/office-insurance/detail-policy";

    //Office policy detail
    public static final String API_LIST_OFFICE_POLICY_CLAIM = "/detail-policy-claim";
    public static final String API_LIST_OFFICE_POLICY_RENEW= "/detail-policy-renew";
    public static final String API_LIST_OFFICE_POLICY_EXPIRED= "/detail-policy-expired";
    public static final String API_LIST_OFFICE_POLICY_ORPHAN= "/detail-policy-orphan";
    public static final String API_EXPORT_OFFICE_POLICY_ORPHAN= "/export-list-policy-orphan";
    public static final String API_LIST_CONTRACT_BY_GROUP = "/list-contracts-by-group";
    public static final String API_EXPORT_LIST_CONTRACT_BY_GROUP = "/export-list-contracts-by-group";
    public static final String API_LIST_CONTRACT_OVERDUE_FEE = "/list-contracts-overdue-fee";
    public static final String API_LIST_CONTRACT_FEE_BY_GROUP = "/list-contracts-fee-by-group";
    public static final String API_EXPORT_LIST_CONTRACT_FEE_BY_GROUP = "/export-list-contracts-fee-by-group";
    public static final String API_LIST_CONTRACT_EFFECT_BY_GROUP = "/list-contracts-effect-by-group";
    public static final String API_LIST_CONTRACT_BUSINESS_HANDLE = "/list-contracts-business-group";
    public static final String API_EXPROT_CONTRACT_BUSINESS_HANDLE = "/export-list-contracts-business-group";
    public static final String API_EXPORT_OFFICE_POLICY_CLAIM = "/export-list-policy-claim";
    public static final String API_LIST_LAPSED_CONTRACT_INDIVIDUAL = "/list-lapsed-contract-individual";
    public static final String API_POLICY_BY_AGENT = "/get-list-policy-by-agent";
    public static final String API_POLICY_BY_POLICY = "/get-list-policy-detail-by-policy";
    public static final String API_LIST_POLICY_MATURED_BY_GROUP = "/list-policy-matured-by-group";
    //export office policy
    public static final String API_LIST_CONTRACT_FEE_BY_GROUP_DETAIL = "/list-contracts-fee-by-group-detail";
    public static final String API_EXPORT_LIST_CONTRACT_EFFECT_BY_GROUP = "/export-list-contracts-effect-by-group";
    public static final String API_EXPORT_LIST_CONTRACT_OVERDUE_FEE = "/export-list-contracts-overdue-fee";
    public static final String API_EXPORT_OFFICE_POLICY_EXPIRED= "/export-policy-expired";
    public static final String API_EXPORT_LIST_AGENT_STANDARD= "/export-list-agent-standard";
    public static final String API_EXPORT_LIST_POLICY_MATURED_BY_GROUP = "/export-list-policy-matured-by-group";
    
    public static final String API_EXPORT_LIST_CLAIM_BY_GROUP = "/export-list-claim-by-group";
    public static final String API_EXPORT_LIST_CLAIM_BY_GROUP_BD = "/export-list-claim-by-group-bd";
    
    //Customers
    public static final String API_LIST_CUSTOMER = "/list-customers";
    public static final String API_LIST_CUSTOMER_DETAIL = "/customers-detail";
    public static final String API_LIST_INSURANCE_CONTRACT_DETAIL = "/insurance-contract-detail";
    public static final String API_CUSTOMER_INFO_BY_CUMSTOMERNO = "/get-list-customer-by-customerno";
    public static final String API_CUSTOMER_INFO_BY_AGENT = "/get-list-customer-by-agent";
    public static final String API_LIST_CUSTOMER_POTENTIAL = "/get-list-customer-potential";
    public static final String API_EXPORT_CUSTOMER_POTENTIAL = "/export-customer-potential";
    public static final String API_DETAIL_CUSTOMER_POTENTIAL = "/get-customer-potential-by-customerno";
    public static final String API_LIST_INTERACTION_HISTORY = "/get-list-interaction-history";
    public static final String API_EXPORT_INTERACTION_HISTORY = "/export-interaction-history";
    public static final String API_DETAIL_INTERACTION_HISTORY = "/get-detail-interaction-history";
    
    //contract
    public static final String API_LIST_ALL_CONTRACT = "/get-list-all-contract";
    public static final String API_LIST_ALL_CONTRACT_EXPORT = "/get-list-all-contract-export";
    public static final String API_LIST_DUE_DATE_CONTRACT = "/get-list-due-date-contract";
    public static final String API_LIST_DUE_DATE_CONTRACT_EXPORT = "/get-list-due-date-contract-export";
    public static final String API_LIST_BUSINESS_CONTRACT = "/get-list-business-contract";
    public static final String API_DETAIL_BUSINESS_CONTRACT = "/get-detail-business-contract";
    public static final String API_LIST_BUSINESS_CONTRACT_EXPORT = "/get-list-business-contract-export";
    public static final String API_LIST_EXPIRES_CONTRACT = "/get-list-expires-contract";
    public static final String API_DETAIL_EXPIRES_CONTRACT = "/get-detail-expires-contract";
    public static final String API_LIST_EXPIRES_CONTRACT_EXPORT = "/get-list-expires-contract-export";
    public static final String API_LIST_CLAIM_CONTRACT = "/get-list-claim-contract";
    public static final String API_LIST_CLAIM_CONTRACT_EXPORT = "/get-list-claim-contract-export";
    public static final String API_CATEROGY_LIBRARY_MATERIAL_BY_ID = "/search-library-material-by-id-caterogy";
    public static final String API_CATEROGY_LIBRARY_MATERIAL_BY_TYPE_SUPER = "/search-library-material-by-super-caterogy";
    public static final String API_CATEROGY_LIBRARY_MATERIAL_GA = "/search-library-material-ga";
    public static final String API_CATEROGY_LIBRARY_MATERIAL_BY_TYPE_SUB = "/search-library-material-by-sub-caterogy";
    public static final String API_CATEROGY_BY_CODE = "/get-library-by-code";
    public static final String API_LIST_POLICY_MATURED = "/get-list-policy-matured";
    public static final String API_LIST_POLICY_MATURED_EXPORT = "/get-list-policy-matured-export";
    public static final String API_DETAIL_POLICY_MATURED = "/get-detail-policy-matured";

    //add doc
    public static final String API_LIST_DOC_INFORMATION="/get-list-inportmation";
    public static final String API_LIST_ALL_DOC_ADD ="/get-list-add-doc";
    public static final String API_LIST_All_DOC_ADD_SUBMIT="get-list-add-doc-submit";
    
    //Result
    public static final String API_LIST_RESULT_PROMOTION_PERSONAL = "/list-promotion-personal";
    public static final String API_LIST_RESULT_MAINTAIN_PERSONAL = "/list-maintain-personal";
    public static final String API_LIST_RESULT_PROMOTION = "/list-promotion";
    public static final String API_LIST_RESULT_MAINTAIN = "/list-maintain";
    public static final String API_RESULT_PROMOTION_EXPORT= "/export-result-promotion-by-agentTitle"; 
    public static final String API_RESULT_MAINTAIN_EXPORT= "/export-result-maintain-by-agentTitle"; 

    //Report detail 
    public static final String API_GET_REPORT_DETAIL = "/get-report-detail"; 
    public static final String API_LIST_REPORT_DETAIL = "/list-report-detail-agentCode"; 
    public static final String API_LIST_REPORT_GROUP_DETAIL = "/list-report-group-detail-agentGroup"; 
    public static final String API_RESULT_DETAIL_EXPORT = "/export-result-detail-agentCode"; 
    public static final String API_RESULT_GROUP_DETAIL_EXPORT = "/export-result-group-detail-agentGroup"; 
    
    //Report active
    public static final String API_LIST_REPORT_ACTIVE = "/get-list-report-active-agentCode";
    public static final String API_LIST_REPORT_ACTIVE_3MONTHSAGO = "/get-list-report-active-3monthsAgo-agentCode";
    public static final String API_LIST_CONTRACT_MONTH = "/get-list-contract-month";
    public static final String API_EXPORT_CONTRACT_MONTH = "/export-list-contract-month";
    public static final String GEN_QA_CODE="/gen-string-qa-code";
    public static final String API_ADD_NOTIFY_AUTO= "/add-notify-auto";
    public static final String API_LIST_REPORT_GROUP_TOTAL = "/report-group-total";
    public static final String API_LIST_REPORT_GROUP_PREMIUM = "/report-group-premium";
    public static final String API_LIST_REPORT_GROUP_PREMIUM_DETAIL = "/report-group-premium-detail";
    public static final String API_LIST_REPORT_GROUP_MANPOWER = "/report-group-manpower";
    public static final String API_LIST_REPORT_GROUP_MANPOWER_DETAIL = "/report-group-manpower-detail";
    public static final String API_LIST_REPORT_GROUP_MANPOWER_UM_BM_DETAIl = "/report-group-manpower-um-bm-detail";
    public static final String API_LIST_REPORT_GROUP_PREMIUM_UM_BM_DETAIl = "/report-group-premium-um-bm-detail";
    public static final String API_LIST_REPORT_GROUP_AUTOTER_REPORT = "/report-group-autoter-report";
    public static final String API_LIST_REPORT_GROUP_PREMIUM_EXPORT = "/export-report-group-premium";
    public static final String API_LIST_REPORT_GROUP_PREMIUM_EXPORT_BM_UM = "/export-report-group-premium-bm-um";
    public static final String API_LIST_REPORT_GROUP_MANPOWER_EXPORT = "/export-report-group-manpower";
    public static final String API_LIST_REPORT_GROUP_MANPOWER_EXPORT_BM_UM = "/export-report-group-manpower-bm-um";
    public static final String API_LIST_REPORT_GROUP_TARGET_ACHIEVEMENT_SALE = "/report-group-target-achievement-sale";
    public static final String API_LIST_REPORT_GROUP_TARGET_ACHIEVEMENT_COMPARE = "/report-group-target-achievement-compare";
    public static final String API_LIST_REPORT_GROUP_TARGET_ACHIEVEMENT_MISSING = "/report-group-target-achievement-missing";
    public static final String API_LIST_REPORT_GROUP_TARGET_ACHIEVEMENT_PAY_FEES = "/report-group-target-achievement-pay-fees";
    public static final String API_LIST_REPORT_AUTOTER_DETAIL = "/report-autoter-detail";
    public static final String API_LIST_REPORT_AUTOTER_DETAIL_EXPORT = "/export-report-autoter";
    public static final String API_AUTOTER_SUMARY = "/autoter-sumary";

    //Acceptance ga 
    public static final String API_GET_ACCEPTANCE_CERTIFICATION_GA = "/get-acceptance-ga";
    public static final String API_ACCEPTANCE_CERTIFICATION_CONFIRM_GA = "/confirm-acceptance-ga";
    public static final String API_ACCEPTANCE_CERTIFICATION_DENY_GA = "/deny-acceptance-ga";
    public static final String API_EXPORT_ACCEPTANCE_CERTIFICATION_GA = "/export-acceptance-certification-ga";
    public static final String API_OTP_ACCEPTANCE_CERTIFICATION_GA = "/get-otp-acceptance-certification-ga";
    public static final String API_GET_AGENT_TRAINED_STATUS = "/get-agent-trained-status";

    //Acceptance AG
    public static final String API_GET_AGENT_TAX_BANK_INFO = "/get-agent-tax-bank-ag";
    public static final String API_GET_PAYMENT_MONTH_AG = "/get-payment-month-ag";
    public static final String API_ACCEPTANCE_CERTIFICATION_CONFIRM_AG = "/confirm-acceptance-ag";
    public static final String API_ACCEPTANCE_CERTIFICATION_DENY_AG = "/deny-acceptance-ag";
    public static final String API_EXPORT_ACCEPTANCE_CERTIFICATION_AG = "/export-acceptance-certification-ag";
    public static final String API_OTP_ACCEPTANCE_CERTIFICATION_AG = "/get-otp-acceptance-certification-ag";
    
    
    //Growth ga
    public static final String API_LIST_REPORT_GROWTH_GA= "/get-list-report-growth-ga";
    public static final String API_LIST_REPORT_GROWTH_GA_BONUS= "/get-list-report-growth-ga-bonus";
    public static final String API_EXPORT_REPORT_GROWTH_GA = "/export-list-report-growth-ga";
    public static final String API_EXPORT_REPORT_GROWTH_GA_BONUS = "/export-list-report-growth-ga-bonus";
    //Income ga
    public static final String API_LIST_INCOME_GA_DETAIL= "/get-list-income-ga-detail";
    public static final String API_LIST_INCOME_GA= "/get-list-income-ga";
    public static final String API_LIST_INCOME_GA_YEAR= "/get-list-income-ga-year";
    public static final String API_EXPORT_INCOME_GA= "/export-income-ga";
    public static final String API_EXPORT_INCOME_GA_YEAR= "/export-income-ga-year";

	public static final String API_LIST_TOTAL_BD_BY_AGENT_CODE = "/total-bd-by-agent-code";
	public static final String API_GET_AGENT_GROUP_HIST = "/get-agent-group-hist";
	
	public static final String API_CHECK_EXPORT_TAX_COMMITMENT = "/check-export-tax-commitment";
	public static final String API_GET_TEMPLATE_EXPORT_TAX_COMMITMENT = "/template-export-tax-commitment";
	public static final String API_LIST_COMMITMENTS = "/list-commitments";
	public static final String API_UPLOAD_TAX_COMMITMENT = "/upload-tax-commitment";

	public static final String API_GET_FORCE_UPDATE = "/get-all-version";
	public static final String API_FORCE_UPDATE_QUESTION_DESCR = "Force Update";

	/** All APIs of SAM Integration*/
	public static final String API_ACTIVITIES_SEARCH = "/search-activities";
	public static final String API_ACTIVITY_FIND_TOP_FIVE = "/find-top-five-activities";
	public static final String API_ACTIVITY_FIND_ALL_STATUS = "/find-activities-status";
	public static final String API_FIND_ALL_PARTICIPANT = "/find-all-participant";
	public static final String API_ACTIVITY_FIND_ALL_CATEGORIES = "/find-all-categories";
	public static final String API_ACTIVITY_DETAIL = "/get-activity-detail";
	public static final String API_ACTIVITY_CREATE = "/create-activity";
	public static final String API_ACTIVITY_UPDATE = "/update-activity";
	public static final String API_ACTIVITY_EXPORT_CSV = "/export-csv";
	public static final String API_ACTIVITY_ORGANIZATION_LOCATION = "/get-org-location";
	public static final String API_ACTIVITY_GET_AGENT_AND_ROLES = "/get-agent-and-roles";
	public static final String API_ACTIVITY_GET_ROLES_PERMISSION = "/get-roles-permission";
	
	/** All APIs of Categorize Customer Integration **/
	public static final String API_LIST_CATEGORIZE_CUSTOMER = "/get-list-categorize-customer";
	public static final String API_GET_PERMISSION = "/get-permission";
	public static final String API_DETAIL_CATEGORIZE_CUSTOMER = "/get-detail-categorize-customer";
	public static final String API_EDIT_DETAIL_CATEGORIZE_CUSTOMER ="/edit-detail-categorize-customer";
	public static final String API_GET_LIST_AGENT ="/get-list-agent";
	
	/** Bang luong nam for AG , GA**/
	public static final String API_GET_YEARLY_PAYMENT_FOR_AG = "/get-yearly-payment-for-ag";
	public static final String API_EXPORT_YEARLY_PAYMENT_FOR_AG = "/export-yearly-payment-for-ag";
	public static final String API_GET_YEARLY_PAYMENT_FOR_GA = "/get-yearly-payment-for-ga";
	public static final String API_EXPORT_YEARLY_PAYMENT_FOR_GA = "/export-yearly-payment-for-ga";

    /** All APIs of Training Integration **/
    public static final String API_GET_LIST_TRAINEES = "/get-list-trainees";
    public static final String API_CMS_TRAINING_COURSES = "/training-courses";
    public static final String API_CMS_TRAINING_COURSES_DESCR = "Training Courses";

    public static final String API_GET_LIST_GUESTS_TRAINING = "/get-list-guests-training";
    public static final String API_GET_LIST_TRAINING_BY_CONDITION = "/get-list-training-by-conditon";
    public static final String API_EDIT_TRAINING_COURSE = "/edit";
    public static final String API_GET_DETAILS_TRAINING_COURSE = "/get-detail-training-course";
    public static final String API_GET_LIST_OFFICE = "/get-list-office";
    public static final String API_UPDATE_STATUS = "/update-status";
    public static final String API_CHECKIN_TRAINING_COURSE = "/checkin-training-course";
    public static final String API_EXPORT_LIST_TRAINING_COURSE = "/export-list-training-course";
}
