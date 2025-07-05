/*******************************************************************************
 * Class        CommonConstant
 * Created date 2017/04/27
 * Lasted date  2017/04/27
 * Author       trieunh <trieunh@unit.com.vn>
 * Change log   2017/04/27 01-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.ep2p.constant;

/**
 * CommonConstant
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh <trieunh@unit.com.vn>
 */
public class CommonConstant {
	
	
	 
    /* --- Start jBPM constant ------------------------------------------------------------------ */
   
    public static final String PARAM_ACTION = "action";
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_STATUS = "status";
    
    public static final String ACTION_VALUE_SAVE = "save";
    public static final String ACTION_VALUE_SUBMIT = "submit";
    public static final String ACTION_VALUE_REJECT = "reject";
    public static final String ACTION_VALUE_APPROVE = "approve";
    
    public static final String STATUS_SAVED = "saved";
    public static final String STATUS_SUBMITTED = "submitted";
    public static final String STATUS_REJECTED = "rejected";
    public static final String STATUS_APPROVED = "approved";
    
    public static final String PROCESS_NEWS = "news";
    public static final String PROCESS_JOB = "job";
    public static final String PROCESS_INTRODUCTION = "introduction";
    public static final String PROCESS_MENU = "menu";
    public static final String PROCESS_DOCUMENT = "document";
    public static final String PROCESS_FAQS = "faqs";
    public static final String PROCESS_PRODUCTION = "production";
    public static final String PROCESS_PRODUCTION_CATEGORY = "production_category";
    public static final String PROCESS_SHAREHOLDER = "shareholder";
    /* --- End jBPM constant -------------------------------------------------------------------- */
    
    
    /** Constant Excel */
    public static final String REAL_PATH_TEMPLATE_EXCEL = "/WEB-INF/excel_template";
    public static final String CONTENT_TYPE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String ATTCHMENT_FILENAME = "attachment; filename=\"";
    
    /** File */
    public static final String TYPE_EXCEL = ".xlsx";
    public static final String TYPE_WORD = ".docx";
    public static final String TYPE_EXCEL_XLS = ".xls";
    public static final String TYPE_FREEMARKER = ".ftl";
    
    public static final String TEMPLATE_EXCEL_SYSTEM_LOGS = "SystemLogsReport";
    public static final String TEMPLATE_EXCEL_AUTHORITY_REPORT = "AuthorityReport";
    
    // HungHT add constant to show password on web
    public static final String PASSWORD_ENCRYPT = "********";
	
    /**Email Constant*/
    public static final String EMAIL_SAVED = "Saved";
    public static final String EMAIL_SENDING = "Sending";
    public static final String EMAIL_SEND_SUCCESS = "Success";
    public static final String EMAIL_SEND_FAIL = "Failed";
    public static final String EMAIL_ERROR = "Error";
    public static final String EMAIL_RESEND = "Resend";
    public static final String EMAIL_CANCEL = "Cancel";
    
    public static final int EMAIL_TYPE_TEXT = 0;
    public static final int EMAIL_TYPE_TEMP_HTML = 1;
    public static final int EMAIL_TYPE_URL_HTML = 2;
    
    public static final String FILE_FORMAT_WORD = "WORD";
    public static final String FILE_FORMAT_EXCEL = "EXCEL";
    public static final String FILE_FORMAT_HTML = "HTML";
    
    public static final String GROUP_JCA_APP_SLA = "JCA_APP_SLA";
    public static final String KIND_SLA_SEND_STATUS = "SLA_SEND_STATUS";
    public static final String KIND_SLA_SEND_TYPE = "SLA_SEND_TYPE";
    
    public static final String  LOWER_CASE = "lower case";
    public static final String  UPPER_CASE = "upper case";
    public static final String  NUMBER = "number";
    public static final String  SYMBOL = "symbol";
    
    public static final String SEND_DIRECT_NO_SAVE = "1";
    public static final String SEND_DIRECT_SAVE = "2";
    public static final String SEND_BY_BATCH = "3";
    
    public static final String EMAIL_PROCESS_TEMPLATE = "common-process-template";
    
    public static final String MSG_ERROR_LOGIN_USERNAME_NULL = "message.error.login.username.null.label";
    public static final String MSG_ERROR_LOGIN_USERNAME_NOT_EXIST = "message.error.login.username.not.exist";
	
	//sla management
    public static final String MSG_SLA_MAIL_TO = "Assignee";
    public static final String MSG_SLA_MAIL_CC = "Supervisor";
    public static final String MSG_SLA_MAIL_REQUEST = "Requester";
    public static final String MSG_SLA_MAIL_RELATION = "Related People";
    public static final String MSG_SLA_MAIL_TRANSFER = "Transfer";
    public static final String MSG_SLA_MAIL_REQUEST_LABEL = "sla.email.requester";
    public static final String MSG_SLA_MAIL_TRANSFER_LABEL = "sla.email.transfer";
    public static final String MSG_SLA_MAIL_TO_LABEL = "sla.email.to";
    public static final String MSG_SLA_MAIL_CC_LABEL = "sla.mail.cc";
    public static final String MSG_SLA_MAIL_RELATION_LABEL = "sla.email.relation";
    public static final String MSG_SLA_LANG = "en";
    
    public static final Long UNKNOWN_ID = 0L;
    public static final String CONTENT_TEMPLATE_BLANK ="<html><body></body></html>";
	
    // KhoaNA - 20191101 - Account
    public static final int PADDING_LENGTH = 20;
    
    public static final String DOT = ".";
}
