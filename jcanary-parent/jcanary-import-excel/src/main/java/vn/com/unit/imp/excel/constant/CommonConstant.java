/*******************************************************************************
 * Class        CommonConstant
 * Created date 2017/04/27
 * Lasted date  2017/04/27
 * Author       trieunh <trieunh@unit.com.vn>
 * Change log   2017/04/2701-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.constant;

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
    public static final String REAL_PATH_API_TEMPLATE_EXCEL = "classpath:excel_template";
    public static final String CONTENT_TYPE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String CONTENT_TYPE_CSV = "application/vnd.ms-excel";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String ATTCHMENT_FILENAME = "attachment; filename=\"";
    public static final String PATH_FILE_MNT = "mnt";
    public static final String PATH_FILE_UAT = "uat";
    public static final String PATH_FILE_MAIN = "main";
    public static final String PATH_FILE_EXPORT = "export-excel";
    public static final String PATH_FILE_DLVN = "DLVN";
    
    /** Constant Pdf */
    public static final String REAL_PATH_TEMPLATE_PDF = "/WEB-INF/pdf_template";
    public static final String CONTENT_TYPE_PDF = "application/x-pdf";
    public static final String TYPE_PDF = ".pdf";
    
    /** File */
    public static final String TYPE_EXCEL = ".xlsx";
    public static final String TYPE_WORD = ".docx";
    public static final String TYPE_EXCEL_XLS = ".xls";
    public static final String TYPE_FREEMARKER = ".ftl";
    public static final String TYPE_CSV = ".csv";
    public static final String REAL_PATH_FONT = "/WEB-INF/fonts";
    
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
    
    public static final String SEND_EMAIL_STATUS = "SENDEMAILSTATUS";
    public static final String SEND_EMAIL_OPT = "SENDEMAILOPT";
    
    public static final String  LOWER_CASE = "lower case";
    public static final String  UPPER_CASE = "upper case";
    public static final String  NUMBER = "number";
    public static final String  SYMBOL = "symbol";
    
    public static final String SEND_DIRECT_NO_SAVE = "SEND_DIRECT_NO_SAVE";
    public static final String SEND_DIRECT_SAVE = "SEND_DIRECT_SAVE";
    public static final String SEND_BY_BATCH = "SEND_BY_BATCH";
    
    public static final String EMAIL_PROCESS_TEMPLATE = "common-process-template";
    public static final String EMAIL_PROCESS_NOTIFICATION_TEMPLATE = "email-notification-requester-process";
    
    public static final String EMAIL_PROCESS_CATALOG = "process-email-catalog-template";
    
	public static final String MESSAGE_ERROR_VI = "MESSAGE_ERROR_VI";
	public static final String MESSAGE_ERROR_EN = "MESSAGE_ERROR_EN";
	
	public static final Integer SCALE_MONEY = 10;
	
	public static final String PAGE_URL = "pageUrl";
	public static final String SEARCH_DTO = "searchDto";
	public static final String CURRENT_PAGE = "currentPage";
	public static final String SIZE_OF_PAGE = "sizeOfPage";
	
	public static final String PERCENT = "percent";
	public static final String DOUBLE_SHOW_ALL = "double_show_all";
	public static final String MONEY_WITHOUT_DEC = "money_without_dec";
	public static final String DDMMYYYY_HHMMSS = "dd/mm/yyyy hh:mm:ss";
	
	public static final String DATE_PATTERN = "DATE_PATTERN";
	public static final String PAGING_SIZE = "PAGING_SIZE";
	

    public static final String REAL_PATH_WEB_INF= "/WEB-INF";
}
