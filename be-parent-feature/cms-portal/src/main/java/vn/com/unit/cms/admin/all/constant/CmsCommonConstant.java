/*******************************************************************************
 * Class        ：CommonConstant
 * Created date ：2017/05/30
 * Lasted date  ：2017/05/30
 * Author       ：trieunh <trieunh@unit.com.vn>
 * Change log   ：2017/05/30：01-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.constant;

/**
 * CommonConstant
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh <trieunh@unit.com.vn>
 */
public class CmsCommonConstant {

	// JCANARY
	/*
	 * --- Start jBPM constant
	 * ------------------------------------------------------------------
	 */
    public static final String STATUS_COMMON_FINISHED = "999";
    
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
	public static final String PROCESS_HOMEPAGE = "homepage";
	/*
	 * --- End jBPM constant
	 * --------------------------------------------------------------------
	 */
	// mail job default
	public static final String EMAIL_JOB_DEFAULT = "tuyendung@vietcapitalbank.com.vn";
	/** Constant Excel */
//    public static final String REAL_PATH_TEMPLATE_EXCEL = "/WEB-INF/excel_template";
//    public static final String CONTENT_TYPE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//    public static final String CONTENT_DISPOSITION = "Content-Disposition";
//    public static final String ATTCHMENT_FILENAME = "attachment; filename=\"";
	/** Constant Word */
	public static final String CONTENT_TYPE_WORD_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	/** File */
	public static final String TYPE_EXCEL = ".xlsx";
	public static final String TYPE_WORD = ".docx";
	public static final String TYPE_EXCEL_XLS = ".xls";
	public static final String TYPE_FREEMARKER = ".ftl";

	public static final String TEMPLATE_EXCEL_SYSTEM_LOGS = "SystemLogsReport";
	public static final String TEMPLATE_EXCEL_AUTHORITY_REPORT = "AuthorityReport";

	// HungHT add constant to show password on web
	public static final String PW_ENCRYPT = "********";

	/** Email Constant */
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

	public static final String LOWER_CASE = "lower case";
	public static final String UPPER_CASE = "upper case";
	public static final String NUMBER = "number";
	public static final String SYMBOL = "symbol";

	public static final String SEND_DIRECT_NO_SAVE = "SEND_DIRECT_NO_SAVE";
	public static final String SEND_DIRECT_SAVE = "SEND_DIRECT_SAVE";
	public static final String SEND_BY_BATCH = "SEND_BY_BATCH";

	public static final String EMAIL_PROCESS_TEMPLATE = "common-process-template";
	public static final String BUSINESS_PRODUCT_CD = "BUSINESS_PRODUCT";
	public static final String DN_BUSINESS_PRODUCT = "DN_BUSINESS_PRODUCT";
	public static final String CN_BUSINESS_FAQS = "CN_BUSINESS_FAQS";
	public static final String DN_BUSINESS_FAQS = "DN_BUSINESS_FAQS";
	public static final String CN_BUSINESS_DOCUMENT = "CN_BUSINESS_DOCUMENT";
	public static final String DN_BUSINESS_DOCUMENT = "DN_BUSINESS_DOCUMENT";
	public static final String NDT_BUSINESS_DOCUMENT = "NDT_BUSINESS_DOCUMENT";
	public static final String BUSINESS_BANNER = "BUSINESS_BANNER";
	public static final String CN_BUSINESS_NEWS = "BUSINESS_NEWS";
	public static final String DN_BUSINESS_NEWS = "DN_BUSINESS_NEWS";
	public static final String NDT_BUSINESS_NEWS = "NDT_BUSINESS_NEWS";
	public static final String HDBANK_BUSINESS_NEWS = "HDBANK_BUSINESS_NEWS";
	public static final String CN_BUSINESS_MATH_EXPRESSION = "CN_BUSINESS_MATH_EXPRESSION";
	public static final String DN_BUSINESS_MATH_EXPRESSION = "DN_BUSINESS_MATH_EXPRESSION";

	public static final String BUSINESS_INTEREST_RATE = "BUSINESS_INTEREST_RATE";

	public static final String HDBANK_BUSINESS_INTRODUCTION = "HDBANK_BUSINESS_INTRODUCTION";

	public static final String BUSINESS_HOMEPAGESETTIMG = "BUSINESS_HOMEPAGESETTIMG";

	public static final String BUSINESS_INVESTOR = "NDT_BUSINESS_INVESTOR_CATEGORY";

	public static final String BUSINESS_CUSTOMER = "BUSINESS_CUSTOMER";
	
	public static final String BUSINESS_CMS = "BUSINESS_CMS";
	// JCANARY

	// CMS ADMIN CONSTANT
	/** Template export excel */
	public static final String TEMPLATE_CHAT_HISTORY = "ChatHistory.xlsx";
	public static final String TEMPLATE_CHAT_LIST_OFFLINE = "ChatListOffline.xlsx";
	public static final String TEMPLATE_CONTACTBOOKING = "ContactBooking.xlsx";
	public static final String TEMPLATE_CONTACTEMAIL = "ContactEmail.xlsx";
	public static final String TEMPLATE_MAIL = "ContactEMail.xlsx";
	public static final String TEMPLATE_PRODUCTCONSULT = "ProductConsult";
	public static final String TEMPLATE_PRODUCT_CATEGORY = "Product_Category";
	public static final String TEMPLATE_PRODUCT_CATEGORY_SUB = "Product_Category_Sub";
	public static final String TEMPLATE_PRODUCT = "Product";
	public static final String TEMPLATE_MATH_EXPRESSION = "Math_Expression";
	/* Tool for Personal and Corporate */
	public static final String TEMPLATE_TOOL_PERSONAL = "Tool_Personal";
	public static final String TEMPLATE_TOOL_CORPORATE = "Tool_Corporate";
	public static final String TEMPLATE_DOCUMENT_TYPE = "Document_Type";
	public static final String TEMPLATE_NEWS_TYPE = "News_Type";
	/* Danh sach Cau Hoi */
	public static final String TEMPLATE_FAQS = "Faqs";

	public static final String TEMPLATE_INVESTOR = "Investor";
	public static final String TEMPLATE_INVESTOR_CATEGORY = "Investor_Category";

	/* Danh Sach Uu Dai */
	public static final String TEMPLATE_NEWS = "News";
	public static final String TEMPLATE_PROMOTION = "Promotion";
	/* Danh Sach Tai Lieu */
	public static final String TEMPLATE_DOCUMENT = "Document";

	/* Danh Mục Thông tin giới thiệu */
	public static final String TEMPLATE_INTRODUCTION_CATEGORY = "Introduction_Category";

	public static final String TEMPLATE_INTRODUCTION = "Introduction";

	public static final String TEMPLATE_CUSTOMER_VIP = "Customer_Vip";
	public static final String TEMPLATE_CUSTOMER_FDI = "Customer_Fdi";

	/** Constant for export excel */
	public static final String REAL_PATH_TEMPLATE_EXCEL = "/WEB-INF/excel_template";
	public static final String CONTENT_TYPE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String ATTCHMENT_FILENAME = "attachment; filename=\"";

	/* File User's Guide */
	public static final String TEMPLATE_USER_GUIDE = "UNIT-HDBank User's Guide";

	// LEVEL_ID_INVESTOR_CATEGORY
	public static final Integer LEVEL_ID_1 = 1;
	public static final Integer LEVEL_ID_2 = 2;
	public static final Integer LEVEL_ID_3 = 3;
	public static final Integer LEVEL_ID_4 = 4;
	
	// CMS ADMIN CONSTANT
	public static final Integer SEARCH_TYPE_ALL = 0;
	public static final Integer SEARCH_TYPE_AT_FIELD = 1;

}