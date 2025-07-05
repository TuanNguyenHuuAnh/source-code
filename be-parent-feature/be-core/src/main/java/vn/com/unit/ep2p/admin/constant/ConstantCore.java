/*******************************************************************************
 * Class        ：ConstantCore
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：KhoaNA
 * Change log   ：2017/02/14：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.constant;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * ConstantCore
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class ConstantCore {
    public static final String SLASH = "/";
    public static final String DOLLAR = "$";
    public static final String AMPERSAND = "&";
    public static final String OPENING_CURLY_BRACE = "{";
    public static final String CLOSING_CURLY_BRACE = "}";
    public static final String OPENING_BRACKET = "[";
    public static final String CLOSING_BRACKET = "]";
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String QUESTION_MARK = "?";
    public static final String EMPTY_CODE = "empty.label";
    public static final String DOT = ".";
    public static final String COLON = ":";
    public static final String EQUAL = "=";    
    public static final String SEMI_COLON = ";";
    public static final String UNDERSCORE = "_";
    public static final String COMMA = ",";
    public static final String AT_SIGN = "@";
    public static final String SHARP = "#";
    public static final String COLON_EDIT = ":Edit";
    public static final String COLON_DISP = ":Disp";
    public static final String COLON_DELETE = ":Delete";
    public static final String HTML = "html";
    public static final String CONST_SUBJECT = "subject";
    public static final String CONST_CONTENT = "content";
    public static final String UTF_8 = "UTF-8";
    public static final String HYPHEN = " - ";
    public static final String AT_FILE = "@@@";
    public static final String ELLIPSIS = "...";
    
    public static final String PAGE = "page";
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_WRAPPER = "pageWrapper";
    public static final String APPROVE_HISTORY = "approveHistory";
    public static final String OPEN = "open";
    
    public static final String FORMAT_DATE = "formatDate";
    public static final String FORMAT_NUMBER = "formatNumber";
    
    public static final String DATE_FORMAT = "dateFormat";
    
    public static final String PATTERN_CURRENCY = "#,###.##";
    
    public static final String MSG_LIST = "messageList";
    public static final String MSG_ERROR_CREATE = "message.error.create.label";
    public static final String MSG_ERROR_UPDATE = "message.error.update.label";
    public static final String MSG_ERROR_DELETE = "message.delete.fail";
    public static final String MSG_ERROR_DELETE_PROCESS_DEPLOY = "message.delete.process.deploy.fail";
    public static final String MSG_ERROR_UPDATE_VERSION = "message.error.update.version.label";
    public static final String MSG_ERROR_ACTION = "message.error.action.label";
    public static final String MSG_ERROR_RUN = "message.error.run.label";
    public static final String MSG_ERROR_IMPORT = "message.error.import.label";
    public static final String MSG_ERROR_DEPLOY = "message.error.deploy.label";
    public static final String MSG_ERROR_CREATE_UPDATE = "message.error.create.update.label";
    public static final String MSG_SUCCESS_CREATE = "message.success.create.label";
    public static final String MSG_SUCCESS_REVERT = "message.success.revert.label";
    public static final String MSG_ERROR_REVERT = "message.error.revert.label";
    public static final String MSG_SUCCESS_CAPTURE = "message.success.capture.label";
    public static final String MSG_SUCCESS_SAVE = "message.success.save.label";
    public static final String MSG_FAIL_SAVE = "message.save.fail";
    public static final String MSG_FAIL_CONNECT = "message.connect.fail";
    public static final String MSG_SUCCESS_SAVE_DRAFT = "message.success.saveDraft.label";
    public static final String MSG_SUCCESS_SEND_REQUEST = "message.success.send.request.label";
    public static final String MSG_SUCCESS_RETURN = "message.success.return.label";
    public static final String MSG_SUCCESS_APPROVE = "message.success.approve.label";
    public static final String MSG_SUCCESS_REJECT = "message.success.reject.label";
    public static final String MSG_SUCCESS_ASSIGN = "message.success.assign.label";
    public static final String MSG_SUCCESS_UPDATE = "message.success.update.label";
    public static final String MSG_SUCCESS_DELETE = "message.success.delete.label";
    public static final String MSG_SUCCESS_UNLOCK = "message.success.unlock.label";
    public static final String MSG_SUCCESS_RUN = "message.success.run.label";
    public static final String MSG_SUCCESS_IMPORT = "message.success.import.label";
    public static final String MSG_SUCCESS_DEPLOY = "message.success.deploy.label";
    public static final String MSG_ERROR_LOGIN_USERNAME_NULL = "message.error.login.username.null.label";
    public static final String MSG_ERROR_LOGIN_FAILED = "message.error.login.failed.label";
    public static final String MS_ERROR_DEACTIVATED = "message.error.login.failed.deactivated";
    public static final String MSG_INFO_SEARCH_NO_DATA = "search.results.no.data";
    public static final String MSG_DOC_DUEDATE_ERROR = "document.message.confirm.completed.date.label";
    public static final String MESSAGE_NONE = "message.none";
    public static final String MESSAGE_INVALID_COLUMN_EXCEL = "exchangerate.file.fail.column";
    public static final String MESSAGE_INVALID_COLUMN_EXCEL_NOTFOUND_DATA = "exchangerate.file.fail.column.notfound.data";
    public static final String MESSAGE_EXISTS_EMAIL= "email.validate.exists";
    public static final String MESSAGE_ERROR_UNEXPECTED = "message.unexpected";
    public static final String MESSAGE_FAILED_DELETE = "message.error.repository.could.not.delete";
    public static final String MESSAGE_NOT_HAVE_PERMISSION = "message.you.do.not.have.permission";
    public static final String MESSAGE_NOT_HAVE_REPOSITORY = "message.connect.fail";
    
    public static final String MSG_SUCCESS_CALL_BO = "bo.request.success";
    public static final String MSG_ERROR_CALL_BO = "bo.request.error";
    
    public static final String MSG_SUCCESS_CLONE = "message.success.clone.label";
    public static final String MSG_ERROR_CLONE = "message.error.clone.label";
    
    public static final String MSG_ERROR_CONNECT_SFTP = "message.error.connect.sftp";
    
    public static final String MSG_ERROR_ROLE_FOR_TEAM = "message.error.role.team.lable";
    public static final String MSG_ERROR_ACCOUNT_FOR_TEAM = "message.error.account.team.lable";
    
    public static final String MSG_SUCCESS_AND_BUTTON_NAME = "message.success.and.button.name";
    public static final String MSG_ERROR_AND_BUTTON_NAME = "message.error.and.button.name";
    public static final String MSG_ERROR_INTEG_TASK_PROCESS = "message.task.error.content";
    
    public static final String MSG_DATA_NOT_FOUND_EXPORT_PDF = "message.data.not.found.export.pdf";
    public static final String MSG_WAITTING_EXPORT_PDF = "message.waitting.export.pdf";
    public static final String MSG_ERROR_EXPORT_PDF = "message.error.export.pdf";
    public static final String MSG_FAILED_EXPORT_PDF = "message.failed.export.pdf";
    public static final String MSG_INVALID_CRON_REGEX = "message.invalid.cron.regex.label";
    
    public static final String MSG_NOT_FOUND_USER_GUIDE = "message.dont.exits.user.guide";
    
    //messages of notification
    public static final String MSG_NOTIFICATION_TOKEN_ERROR = "notification.error.token.label";
    public static final String MSG_NOTIFICATION_CHECK_ERROR = "notification.error.checking.device.label";
    public static final String MSG_NOTIFICATION_BUILD_ERROR = "notification.error.build.label";
    public static final String MSG_NOTIFICATION_UPDATE_ERROR = "notification.error.update.label";
    public static final String MSG_NOTIFICATION_DELETE_ERROR = "notification.error.delete.label";
    public static final String MSG_NOTIFICATION_DELETE_ALL_ERROR = "notification.error.delete.all.label";
    
    public static final String MSG_WARNING_NEED_RESTART_JOB = "message.warning.need.restart.job";
    
//    public static final String SHAREHOLDER_DOCUMENT_TYPE_CODE = "SHAREHOLDER_DOC";
//    public static final String FORM_DOCUMENT_TYPE_CODE = "FORM_DOC";
//    public static final String PRENIUM_DOCUMENT_TYPE_CODE = "PRENIUM_DOC";
//    public static final String JOB_DOCUMENT_TYPE_CODE = "JOB_DOC";
    
    public static final String DEFAULT = "Default";
    
    public static final boolean BOOLEAN_FALSE = false;
    public static final boolean BOOLEAN_TRUE = true;
    
    public static final String STR_ZERO = "0";
    public static final String STR_ONE = "1";
    public static final String STR_TWO = "2";
    public static final String STR_THREE = "3";
    public static final String STR_FOUR = "4";
    
    public static final int NUMBER_ZERO = 0;
    public static final int NUMBER_ONE = 1;
    public static final int NUMBER_TWO = 2;
    public static final int NUMBER_THREE = 3;
    
    public static final String VCCB_ADMIN_SUB_DOMAIN = "VCCBAdmin";
    public static final String VCCB_WEBSITE_SUB_DOMAIN = "VCCBWebsite";
    
    public static final String DOCUMENT_FOLDER_NAME = "document";
    
    public static final String LOAN_INTEREST_EXPRESSION_TYPE = "EXPRESSION_01_LOAN_INTEREST";
    public static final String DEPOSIT_INTEREST_EXPRESSION_TYPE = "EXPRESSION_02_DEPOSIT_INTEREST";
    public static final String MSG_SUCCESS_POST = "message.success.post";
    

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String MESSAGE_EXISTS_DATA = "message.exists.data";
    
    public static final String FILE_NOT_FOUND="excel.com.file.notfound";
    public static final String IMPORT_EXCEL_SUCCESS="excel.com.import.success";
    public static final String IMPORT_EXCEL_FAIL="excel.com.import.fail";
    
    public static final String IMPORT_FILE_WORD="import.file.word";
    public static final String IMPORT_FILE_EMPTY = "import.file.empty";
    public static final String SEND_MAIL_SUCCESS = "send.mail.success";
    public static final String SEND_MAIL_FAIL = "send.mail.fail";
    public static final String MAIL_SAVED = "mail.saved";
    public static final String IMPORT_FILE_FAIL = "import.file.fail";
    public static final String ATTACH_FILE_BIG = "import.attach.file";
    public static final String ATTACH_FILE_FAIL_EXT = "import.attach.file.ext";
    public static final List<String> EXT_ATTACH_FILE_LIST = Arrays.asList("jpg", "jpeg", "png", "doc", "docx", "ppt", "pptx", "pdf", "xls", "xlsx","xlsm" ,"txt");
    public static final Long ATTACH_FILE_SIZE = 5L; //MB
    public static final Long ATTACH_FILE_SIZE_BYTE = ATTACH_FILE_SIZE*1024*1024;
    public static final String IMPORT_ATTACH_FAIL = "import.attach.fail";
    public static final String ATTACH_FILE_ZERO = "import.attach.file.zero";
    public static final String ATTACH_FILE_TYPE = "attach.file.content.type.denine";
    public static final String ATTACH_FILE_NAME = "attach.file.content.name.denine";
    public static final String DELETE_ATTACH_FILE_NOT_CREATER = "message.error.delete.attach.not.creater";
    
    public static final String MSG_UPPER = "account.upper";
    public static final String MSG_LOWER = "account.lower";
    public static final String MSG_NUMBER = "account.number";
    public static final String MSG_SYMBOL = "account.symbol";
    
    public static final String MSG_STRONG_PW = "account.strong.password";
    public static final String MSG_STRONG_PW1 = "account.strong.password1";
    public static final String MSG_HAVE_USED = "account.have.been.used";
    public static final String MSG_STRONG_REGEX = "account.password.not.regex";
    
    
    public static final String MSG_SUCCESS_REGISTER_SERVICE = "register.svc.msg.register.success";
    public static final String MSG_ERROR_REGISTER_SERVICE = "register.svc.msg.register.fail";
    public static final String MSG_ERROR_SERVICE_EXISTS = "register.svc.msg.service.name.exists";
    public static final String MSG_WARNING_REPLAY_FAIL = "message.warning.replay.fail";
    public static final String MSG_WARNING_REPLAY_NO_DATA = "message.warning.replay.no.data";
    
    public static final String URL_PRIOR_LOGIN = "URL_PRIOR_LOGIN";
    
    /** yyyyMMddHHmmssSSS */
    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    
    /** yyyyMMddHHmmss */
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    
    /** TaiTT - 20190703 */
    public static final String EXCEL = "excel";
    public static final String BREAK_LINE = "\n";
    public static final String HTMI_BREAK_LINE = "<br>";
    
    public static final String VERSION = "v";
    
    public static final String SYSTEM = "app_system";
    public static final String SYSTEM_ID = "99";
    
    public static final String EMAIL_ATTACH_FOLDER = "email_attach_folder";
    
    /** EXTENTION */
    public static final String EXTENTION_PDF = ".pdf";
    public static final String EXTENTION_OZD = ".ozd";
    
    public static final String PROCESS_FUNCTION_TYPE = "3";
    
    public static final String STEP_TYPE_USER_TASK = "USER_TASK";
    public static final String STEP_TYPE_END_EVENT = "END_EVENT";
    
    public static final String PARAM_START_WITH = "${";
    public static final String PARAM_END_WITH = "}";
    public static final Pattern PARAM_PATTERN = Pattern.compile(Pattern.quote(PARAM_START_WITH) + "(.*?)" + Pattern.quote(PARAM_END_WITH));

    /** STORE PROCEDURE MESSAGE */
    public static final String SP_MSG_SUCCESS = "success";
    public static final String SP_MSG_FAIL = "fail";
    
    /* STATUS EXPORT PDF */
    public static final String SUCCESS_EXPORT_PDF = "success";
    public static final String PENDING_EXPORT_PDF = "pending";
    public static final String SIGN_FAIL = "sign_fail";
    public static final String EXPORT_FAIL = " EXPORTING_ERROR";
    
    /*handover*/
    public static final String MSG_HANDOVER_SUCCESS = "message.handover.success.label";
    public static final String MSG_HANDOVER_ERROR = "message.handover.error.label";
    
    /*formType*/
    public static final String FIXED_FORM_TYPE = "2";
    public static final String FREE_FORM_TYPE = "1";
    
    public static final String SYSTEM_CODE_PPL = "PPL";
    public static final String SYSTEM_CODE_HDB = "HDB";
    public static final String APP_CODE_WEB = "PPL-WEBAPP";
    public static final String APP_CODE_MOBILE = "PPL-MOBILE";
    
    /*PADDING LENGTH ACT*/
    public static final int PADDING_LENGTH_ACT = 20;
    public static final int PADDING_LENGTH = 20;
    
    /*Filter status commons*/
    public static final String Unapproved_Profile = "1";
    public static final String Approved_Profile = "2";
    public static final String Rejected_Profile = "3";
	public static final String PRO_STATUS_MAPPING = "PRO_STATUS_MAPPING";
	
	/*Filter action commons*/
    public static final String Approved_Task = "2";
    public static final String Rejected_Task = "3";
	public static final String TASK_FILTER = "TASK_FILTER_MAP";
	
	/** Char first Document Code */
	public static final String CHAR_FIRST_DOCUMENT_CODE = "I";
	public static final String EFO_OZ_DOC = "EFO_OZ_DOC";
	public static final String IS_REFERENCE = "IS_REFERENCE";
	public static final String TITLE_REFERENCE_DOCUMENT = "document.title.reference.document";
	public static final String MESSAGE_DOCUMENT_HISTORY_ACTIVITY = "document.history.message.activity";
	
	/**number formater**/
	public static final String SALARY_DOT = "###,###.##";
	
	
	/** Integ url process external */
	public static final String EXTERNAL_URL_PROCESS = "sp_DanhSachThongTinNguoiDuyet";
	
	/** number of day check device token*/
	public static final int DAY_CHECK_DEVICE_TOKEN = 10;
	
	public static final String MSG_SUCCESS_SYNC_LDAP = "sync.ldap.success";
    public static final String MSG_ERROR_SYNC_LDAP = "sync.ldap.error";
    public static final String MSG_SUCCESS_SYNC_SAP = "sync.sap.success";
    public static final String MSG_ERROR_SYNC_SAP = "sync.sap.error";
	
    // CMS BANNER
    public static final String FORMAT_DATE_DDMMYYY = "dd/MM/yyyy";
    public static final String FORMAT_DATE_FULL = "dd/MM/yyyy HH:mm:ss";
    
    public static final String MSG_DATA_IS_UPDATED_BY_OTHERS = "error.message.data.is.updated.by.others";
    public static final String MSG_NOT_FOUND_ENTITY_ID = "error.not.found.entity.id";
    // CMS BANNER
    
    // CMS ALL
//    public static final String SLASH = "/";
//    public static final String DOLLAR = "$";
    public static final String LEFT_CURLY_BRACE = "{";
    public static final String RIGHT_CURLY_BRACE = "}";
//    public static final String EMPTY = "";
//    public static final String SPACE = " ";
//    public static final String EMPTY_CODE = "empty.label";
//    public static final String DOT = ".";
//    public static final String COLON = ":";
//    public static final String EQUAL = "=";    
//    public static final String SEMI_COLON = ";";
//    public static final String UNDERSCORE = "_";
//    public static final String COMMA = ",";
//    public static final String AT_SIGN = "@";
//    public static final String COLON_EDIT = ":Edit";
//    public static final String COLON_DISP = ":Disp";
//    public static final String COLON_DELETE = ":Delete";
//    public static final String HTML = "html";
//    public static final String CONST_SUBJECT = "subject";
//    public static final String CONST_CONTENT = "content";
//    public static final String UTF_8 = "UTF-8";
//    public static final String HYPHEN = " - ";
//    public static final String AT_FILE = "@@@";
    
//    public static final String PAGE = "page";
//    public static final String PAGE_SIZE = "pageSize";
//    public static final String PAGE_WRAPPER = "pageWrapper";
//    public static final String APPROVE_HISTORY = "approveHistory";
//    public static final String OPEN = "open";
    
//    public static final String FORMAT_DATE = "formatDate";
//    public static final String FORMAT_NUMBER = "formatNumber";
    
//    public static final String DATE_FORMAT = "dateFormat";
    
//    public static final String PATTERN_CURRENCY = "#,###.##";
    
//    public static final String MSG_LIST = "messageList";
//    public static final String MSG_ERROR_CREATE = "message.error.create.label";
//    public static final String MSG_ERROR_UPDATE = "message.error.update.label";
//    public static final String MSG_ERROR_DELETE = "message.delete.fail";
//    public static final String MSG_ERROR_UPDATE_VERSION = "message.error.update.version.label";
//    public static final String MSG_ERROR_ACTION = "message.error.action.label";
//    public static final String MSG_ERROR_IMPORT = "message.error.import.label";
//    public static final String MSG_ERROR_CREATE_UPDATE = "message.error.create.update.label";
//    public static final String MSG_SUCCESS_CREATE = "message.success.create.label";
//    public static final String MSG_SUCCESS_CAPTURE = "message.success.capture.label";
//    public static final String MSG_SUCCESS_SAVE = "message.success.save.label";
//    public static final String MSG_FAIL_SAVE = "message.save.fail";
//    public static final String MSG_SUCCESS_SAVE_DRAFT = "message.success.saveDraft.label";
//    public static final String MSG_SUCCESS_SEND_REQUEST = "message.success.send.request.label";
//    public static final String MSG_SUCCESS_RETURN = "message.success.return.label";
//    public static final String MSG_SUCCESS_APPROVE = "message.success.approve.label";
//    public static final String MSG_SUCCESS_REJECT = "message.success.reject.label";
//    public static final String MSG_SUCCESS_ASSIGN = "message.success.assign.label";
//    public static final String MSG_SUCCESS_UPDATE = "message.success.update.label";
//    public static final String MSG_SUCCESS_DELETE = "message.success.delete.label";
//    public static final String MSG_SUCCESS_UNLOCK = "message.success.unlock.label";
//    public static final String MSG_SUCCESS_RUN = "message.success.run.label";
//    public static final String MSG_SUCCESS_IMPORT = "message.success.import.label";
//    public static final String MSG_ERROR_LOGIN_USERNAME_NULL = "message.error.login.username.null.label";
//    public static final String MSG_ERROR_LOGIN_FAILED = "message.error.login.failed.label";
//    public static final String MS_ERROR_DEACTIVATED = "message.error.login.failed.deactivated";
//    public static final String MSG_INFO_SEARCH_NO_DATA = "search.results.no.data";
//    public static final String MESSAGE_NONE = "message.none";
//    public static final String MESSAGE_INVALID_COLUMN_EXCEL = "exchangerate.file.fail.column";
//    public static final String MESSAGE_INVALID_COLUMN_EXCEL_NOTFOUND_DATA = "exchangerate.file.fail.column.notfound.data";
//    public static final String MESSAGE_EXISTS_EMAIL= "email.validate.exists";
//    public static final String MESSAGE_ERROR_UNEXPECTED = "message.unexpected";
    public static final String MESSAGE_ERROR_PROCESSING = "message.error.processing";
    
    
//    public static final String MSG_SUCCESS_CALL_BO = "bo.request.success";
//    public static final String MSG_ERROR_CALL_BO = "bo.request.error";
    
//    public static final String MSG_ERROR_CONNECT_SFTP = "message.error.connect.sftp";
    
//    public static final String MSG_ERROR_ROLE_FOR_TEAM = "message.error.role.team.lable";
//    public static final String MSG_ERROR_ACCOUNT_FOR_TEAM = "message.error.account.team.lable";
    
    public static final String MSG_ERROR_EXPORT_EXCEL = "message.error.export.excel";
//    public static final String SHAREHOLDER_DOCUMENT_TYPE_CODE = "SHAREHOLDER_DOC";
//    public static final String FORM_DOCUMENT_TYPE_CODE = "FORM_DOC";
//    public static final String PRENIUM_DOCUMENT_TYPE_CODE = "PRENIUM_DOC";
//    public static final String JOB_DOCUMENT_TYPE_CODE = "JOB_DOC";
    
//    public static final String DEFAULT = "Default";
    
//    public static final boolean BOOLEAN_FALSE = false;
//    public static final boolean BOOLEAN_TRUE = true;
    
//    public static final String STR_ZERO = "0";
//    public static final String STR_ONE = "1";
//    public static final String STR_TWO = "2";
//    public static final String STR_THREE = "3";
    
//    public static final int NUMBER_ZERO = 0;
//    public static final int NUMBER_ONE = 1;
//    public static final int NUMBER_THREE = 3;
    
//    public static final String VCCB_ADMIN_SUB_DOMAIN = "VCCBAdmin";
//    public static final String VCCB_WEBSITE_SUB_DOMAIN = "VCCBWebsite";
    
//    public static final String DOCUMENT_FOLDER_NAME = "document";
    
//    public static final String LOAN_INTEREST_EXPRESSION_TYPE = "EXPRESSION_01_LOAN_INTEREST";
//    public static final String DEPOSIT_INTEREST_EXPRESSION_TYPE = "EXPRESSION_02_DEPOSIT_INTEREST";
//    public static final String MSG_SUCCESS_POST = "message.success.post";
    

//    public static final String ROLE_ADMIN = "ROLE_ADMIN";
//    public static final String MESSAGE_EXISTS_DATA = "message.exists.data";
    
//    public static final String FILE_NOT_FOUND="excel.com.file.notfound";
//    public static final String IMPORT_EXCEL_SUCCESS="excel.com.import.success";
//    public static final String IMPORT_EXCEL_FAIL="excel.com.import.fail";
    
//    public static final String IMPORT_FILE_WORD="import.file.word";
//    public static final String IMPORT_FILE_EMPTY = "import.file.empty";
//    public static final String SEND_MAIL_SUCCESS = "send.mail.success";
//    public static final String SEND_MAIL_FAIL = "send.mail.fail";
//    public static final String MAIL_SAVED = "mail.saved";
//    public static final String IMPORT_FILE_FAIL = "import.file.fail";
    
//    public static final String MSG_UPPER = "account.upper";
//    public static final String MSG_LOWER = "account.lower";
//    public static final String MSG_NUMBER = "account.number";
//    public static final String MSG_SYMBOL = "account.symbol";
    
//    public static final String MSG_STRONG_PW = "account.strong.password";
//    public static final String MSG_STRONG_PW1 = "account.strong.password1";
//    public static final String MSG_HAVE_USED = "account.have.been.used";
    
//    public static final String URL_PRIOR_LOGIN = "URL_PRIOR_LOGIN";
    
//    public static final String MSG_DATA_IS_UPDATED_BY_OTHERS = "error.message.data.is.updated.by.others";
    public static final String MSG_NOT_FOUND_ENTITY_WITH_ID = "error.not.found.entity.with.id";
//    public static final String MSG_NOT_FOUND_ENTITY_ID = "error.not.found.entity.id";
    public static final String MSG_RULE_LEVEL_ID = "error.rule.level.id";
    
//    public static final String FORMAT_DATE_FULL = "dd/MM/yyyy HH:mm:ss";
    
    
    public static final String MSG_ERROR_EXISTS_CATEGORYTYPE_PARENTID = "message.error.exists.categoryType.parentId";
    public static final int INVESTOR_CATEGORY_LOCATION_LEFT = 1;
    public static final int INVESTOR_CATEGORY_LOCATION_RIGHT_TOP = 2;
    public static final int INVESTOR_CATEGORY_LOCATION_RIGHT_BOTTOM = 3;
    // CMS ALL
    
    public static final String MSG_SYSTEM_CODE_EXISTED = "company.system.code.existed";
    
    public static final String MSG_EMULATE_ERROR_EXPIRATION_DATE = "emulate.error.expiration.date";
    
    public static final String MSG_ERROR_SERIAL_INFO_EXPIRED = "message.error.serial.info.expired";
    
    public static final Long COMP_CUSTOMER_ID = 2L;

    //RESET PASSWORD
    public static final String MSG_CHECK_AGENT = "message.check.agent";
    public static final String MSG_NO_SEND = "message.can.not.reset.password";
    public static final String MSG_ERROR_RESET_PASSWORD = "message.error.reset.password";
    public static final String MSG_RESET_PASSWORD_SUCCESS = "message.success.reset.password";
    public static final String NOT_SEND_EMAIL = "message.error.reset.password.not.mail";
    public static final String NOT_SEND_EMAIL_DLVN = "message.error.reset.password.not.mail.dlvn";
    public static final String NOT_SEND_PHONE = "message.error.reset.password.not.phone";


}
