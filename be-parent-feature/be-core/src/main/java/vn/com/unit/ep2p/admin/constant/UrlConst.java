/*******************************************************************************
 * Class         : UrlConst
 * Created date  : 2017/02/14
 * Lasted date   : 2017/02/14
 * Author        : trieunh
 * Change log    : 2017/02/14 : 01-00 trieunh create a new
 * Change log	 : 2018/04/20 : 02-00 LongPNT add UrlConst.WARD constant
 ******************************************************************************/
package vn.com.unit.ep2p.admin.constant;

/**
 * UrlConst
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh
 */
public class UrlConst {
	// System configure constant
	public static final String SYSTEM_CONFIG = "system-config";
	public static final String CONFIG = "config";

	public static final String JBPM = "/jbpm";
	public static final String JBPM_SHOW_IMAGE = "/show-image";
	public static final String JBPM_FIND_PROCESS_INSTANCE = "/find-process-instance";

	// Access denied page
	public static final String ACCESS_DENIED_PAGE = "/access-denied-page";

	// Common error page
	public static final String COMMON_ERROR_PAGE = "/common-error-page";

	/** URL Root */
	public static final String ROOT = "/";

	/** URL Login */
	public static final String LOGIN = "/login";

	/** URL Change lang */
	public static final String CHANGE_LANG = "/change/lang";
	
	/** URL Change lang */
    public static final String CHANGE_STYLE= "/change/style";

	// Left Menu
	public static final String MENU_LEFT = "/menu";

	/** Redirect page. */
	public static final String REDIRECT = "redirect:";
	/** URL List. */
	public static final String LIST = "/list";
	/** URL Call ajax get list. */
	public static final String AJAX_LIST = "/ajax/list";
	/** URL Add. */
	public static final String ADD = "/add";
	/** URL Call ajax add. */
	public static final String AJAX_ADD = "/ajax/add";
	/** URL Edit. */
	public static final String EDIT = "/edit";

	/** URL Call ajax edit. */
	public static final String AJAX_EDIT = "/ajax/edit";
	/** URL Delete. */
	public static final String DELETE = "/delete";
	/** URL Call ajax delete. */
	public static final String AJAX_DELETE = "/ajax/delete";
	
	public static final String DOWNLOAD = "/download";
	public static final String ASYNC_DOWNLOAD = "/async/download-pdf";

	public static final String MAIN = "/main";
	public static final String UPDATE = "/update";

	/** URL Detail. */
	public static final String DETAIL = "/detail";
	/** URL Call ajax detail. */
	public static final String AJAX_DETAIL = "/ajax/detail";

	/** URL Account. */
	public static final String ACCOUNT = "account";

	/** URL Role. */
	public static final String ROLE = "/role";

	public static final String ROLE_LIST = "/list-role";

	public static final String ROLE_AJAXLIST = "/ajaxList-role";

	public static final String ROLE_ADD = "/add-role";

	public static final String ROLE_EDIT = "/edit-role";

	public static final String ROLE_DELETE = "/delete-role";

	/** URL Authority. */
	public static final String AUTHORITY = "/authority";
	public static final String AUTHORITY_LIST = "/list-authority";

	public static final String AUTHORITY_AJAX_EDIT = "/ajax-edit-authority";

	/** URL Position Authority. */
	public static final String POSITION_AUTHORITY = "/position-authority";

	public static final String SHAREHOLDER = "/shareholder";

	/** URL OrgInfo. */
	public static final String ORG_INF = "/orgInfo";
	/*----------------------------- URL For region start------------------*/
	public static final String REGION = "/region";
	/*----------------------------- URL For position start------------------*/
	public static final String POSITION = "/position";
	public static final String POSITION_TREE = "/position-tree";
	/*----------------------------- URL For city start------------------*/
	public static final String CITY = "/city";
	/*----------------------------- URL For district start------------------*/
	public static final String DISTRICT = "/district";
	/*----------------------------- URL For ward start------------------*/
	public static final String WARD = "/ward";
	/*----------------------------- URL For country start------------------*/
	public static final String COUNTRY = "/country";
	/*----------------------------- URL Faqs------------------*/
	public static final String FAQS = "faqs";
	/*----------------------------- URL For Item Management start------------------*/
	public static final String ITEM_MANAGEMENT = "/item";
	/*----------------------------- URL For Item Management start------------------*/
	public static final String CONSTANT_DISPLAY = "/constant-display";

	/*
	 * ----------------------------- Agency Chart Start
	 * ------------------------------
	 */
	public static final String URL_AGENCY_CHART = "/agency-chart";
	public static final String URL_AGENCY_CHART_TREE = "/agency-chart-tree";
	/*
	 * ----------------------------- Agency Chart End
	 * --------------------------------
	 */

	/*----------------------------- URL Type category faqs------------------*/
	public static final String FAQS_TYPE = "faqs-type";

	/*----------------------------- URL category faqs------------------*/
	public static final String FAQS_CATEGORY = "/faqs-category";

	public static final String ORGANIZATION = "/organization";
	public static final String ORGANIZATION_PERSON = "/organization-person";
	public static final String CURATOR_UNIT = "/curator-unit";
	public static final String BUILD_ORGANIZATION = "/build-organization";
	public static final String BUILD_POSITION = "/build-position";
	public static final String VIEW_ACCOUNT_LIST = "/view-account-list";

	/** URL Call ajax status authority. */
	public static final String AJAX_EDIT_STATUS_AUTHORITY = "ajax/status/edit";
	/** URL Call ajax status authority table. */
	public static final String AJAX_STATUS_AUTHORITY_PROCESS = "ajax/status/process";

	/** URL BANNER. */
	public static final String BANNER = "banner";

	/** URL BANNER. */
	public static final String HOMEPAGE_SETTING = "homepage-setting";

	/** URL NEWS. */
	public static final String NEWS = "news";

	/** URL NEWS CATEGORY. */
	public static final String NEWS_CATEGORY = "news-category";

	/** URL NEWS TYPE. */
	public static final String NEWS_TYPE = "news-type";
	/** URL RECRUIMENT **/
	public static final String REC = "recruitment";
	/** URL EXCHANGE RATE **/
	public static final String EXCHANGE_RATE = "exchange-rate";
	/*** URL SERVICE */
	public static final String SERVICE = "service";
	/*** URL PROMOTION */
	public static final String PROMOTION = "promotion";

	// Account
	public static final String BUILD_ROLE_FOR_ACCOUNT_DETAIL = "/build_role_for_account_detail";
	public static final String BUILD_ROLE_FOR_ACCOUNT = "/build_role_for_account";
	public static final String ADD_ROLE_FOR_ACCOUNT = "/add_role_for_account";
	public static final String DELETE_ROLE_FOR_ACCOUNT = "/delete_role_for_account";
	public static final String UPDATE_ROLE_FOR_ACCOUNT = "/update_role_for_account";
	
	public static final String BUILD_ORG_FOR_ACCOUNT_DETAIL = "/build_org_for_account_detail";
    public static final String BUILD_ORG_FOR_ACCOUNT = "/build_org_for_account";
    public static final String ADD_ORG_FOR_ACCOUNT = "/add_org_for_account";
    public static final String DELETE_ORG_FOR_ACCOUNT = "/delete_org_for_account";
    public static final String UPDATE_ORG_FOR_ACCOUNT = "/update_org_for_account";

	/** URL BRANCH */
	public static final String BRANCH = "/branch";
	/** APPROVER */
	public static final String APPROVE = "/approve";
	/** AJAXLIST */
	public static final String AJAXLIST = "/ajaxList";
	/** AJAXEDIT */
	public static final String AJAXEDIT = "/ajaxEdit";
	/** AJAXEDIT */
	public static final String AJAXDELETE = "/ajaxDelete";
	/** save draft */
	public static final String SAVE_DRAFT = "/saveDraft";
	/** send request */
	public static final String SEND_REQUEST = "/sendRequest";
	/** reject */
	public static final String REJECT = "/reject";

	/** URL type change. */
	public static final String TYPE_CHANGE = "/type/change";
	/** URL category change. */
	public static final String CATEGORY_CHANGE = "/category/change";


	public static final String DISCOUNT_PAYMENT = "discountpayment";

	public static final String MATH_EXPRESSION = "math-expression";

	/** ajax history */
	public static final String AJAX_HISTORY = "/ajaxHistory";

	/** URL CUSTOMER TYPE. */
	public static final String CUSTOMER_TYPE = "customer-type";

	/** URL PRODUCT CATEGORY. */
	public static final String PRODUCT_CATEGORY = "/product-category";

	/** URL PRODUCT CATEGORY. */
	public static final String PRODUCT_CATEGORY_SUB = "/product-category-sub";

	/** URL PRODUCT LIST. */
	public static final String PRODUCT_LIST = "/product-list";

	/** URL PRODUCT DETAIL. */
	public static final String PRODUCT_DETAIL = "/product-detail";

	/** URL PRODUCT. */
	public static final String PRODUCT = "product";

	/** URL TERM TYPE. */
	public static final String TERM = "term";

	/** URL NEW_LETTERS. */
	public static final String NEW_LETTERS = "new-letters";

	/** URL FAQS DETAIL. */
	public static final String FAQS_DETAIL = "ajax/faqsDetail";

	/** URL INTERST RATE TYPE. */
	public static final String INTEREST_RATE = "interest-rate";

	/** URL INTERST RATE CALCULATION TYPE. */
	public static final String DEPOSIT_INTEREST_CALCULATION = "deposit-interest-calculation";

	/** URL CURRENCY TYPE. */
	public static final String CURRENCY = "currency";

	public static final String WEB_APP_DIRECTORY = "web-app";

	public static final String MENU_HOME = "/menu-home";

	public static final String AJAX_FOOTER = "/ajax-footer";

	public static final String URL_EDITOR_UPLOAD = "/editor/upload";

	public static final String URL_EDITOR_DOWNLOAD = "/editor/download";

	public static final String URL_DOWNLOAD_IMAGE = "/image/download";

	public static final String RECOMMEND_LINK = "/recommend-link";

	public static final String DEPOSIT_INTEREST_EXPRESSION = "deposit-interest-expression";

	/** URL JOB TYPE */
	public static final String JOB_TYPE = "job-type";
	/** URL JOB FORM APPLY */
	public static final String JOB_FORM_APPLY = "job-form-apply";

	/** URL JOB TYPE SUB. **/
	public static final String JOB_TYPE_SUB = "/job-type-sub";

	/** URL JOB TYPE DETAIL. **/
	public static final String JOB_TYPE_DETAIL = "/job-type-detail";

	/** URL GUARANTEE CERTIFICATE */
	public static final String GUARANTEE_CERTIFICATE = "guarantee-certificate";

	/** Link for menu */
	public static final String MENU = "menu";
	public static final String MENU_ROOT = ROOT + MENU;
	public static final String MENULEFT = "/menu-left";
	public static final String BREADCRUMB = "/breadcrumb";
	public static final String MENUHOME = "/menu-home";

	/** HTTP_PROTOCOL */
	public static final String HTTP_PROTOCOL = "http";

	/** WORLD_WIDE_WEB */
	public static final String WORLD_WIDE_WEB = "www.";

	/** Custom Role */
	public static final String CUSTOM_ROLE = "/custom/role";

	/** REPORT_AUTH */
	public static final String REPORT_AUTH = "/report-auth";

	/** ROLE_TEAM */
	public static final String ROLE_TEAM = "/role-team";

	/** SERIAL_INFO */
	public static final String SERIAL_INFO = "/serial-info";

	/** ISSUE */
	public static final String ISSUE = "/issue";

	/** SERIAL_SETUP */
	public static final String SERIAL_SETUP = "/serial-setup";

	/** REPOSITORY */
	public static final String REPOSITORY = "/repository";

	/** HOLIDAY */
	public static final String HOLIDAY = "/holidays";

	/** Change Password */
	public static final String CHANGE_PASSWORD = "/change-password";
	public static final String AJAX_CHANGE_PASSWORD = "/ajax-change-password";
	public static final String REDIRECT_CHANGE_PASSWORD = "account/change-password";

	/** Send Email */
	public static final String EMAIL = "/email";
	public static final String SEND_MAIL = "/send-mail";
	public static final String IMPORT = "/import";
	public static final String SELECT2_TEMPLATE = "/select2/template";
	public static final String LOAD_TEMPLATE = "/load/template";
	public static final String IMPORT_FILE_ATTACH = "/import/attach";
	public static final String EMAIL_LIST = "/email-list";
	public static final String EMAIL_DETAIL = "/email-detail";
	public static final String EMAIL_SEND = "/send";
	public static final String EMAIL_GET_DETAIL = "/email-get-detail";
	public static final String EMAIL_TEMPLATE_DETAIL = "/template-email-detail";
	public static final String TEMPLATE_DETAIL = "/template-detail";
	public static final String TEMPLATE_SAVE = "/save-template";
	public static final String TEMPLATE_DELETE = "/delete-template";
	public static final String IMPORT_MODAL = "/import-modal";

	/** Public Holiday */
	public static final String PUBLIC_HOLIDAYS = "/public-holidays";
	public static final String SAVE = "/save";
	public static final String AJAX = "/ajax";
	public static final String VIETNAM_HOLIDAY = "/getVietnameseHoliday";

	public static final String TEMPLATE_EMAIL = "/email/template";

	/** URL constant excel */
	public static final String URL_UPLOAD_EXCEL = "/upload-excel";
	public static final String URL_DOWNLOAD_TEMPLATE_EXCEL = "/download-template-excel";
	public static final String URL_LIST_IMPORTED = "/list-imported";
	
	
	/**URL ATTACHED FIle*/
	public static final String URL_ATTACHED_FILE_UPLOAD = "/upload-attached-file";
	public static final String URL_ATTACHED_FILE_DOWNLOAD = "/download-attached-file";
	public static final String URL_ATTACHED_FILE_DELETE = "/delete-attached-file";

	/** ECM Repository */
	public static final String URL_ECM_REPOSITORY = "/ecm-repository";
	
	/** Category */
	public static final String CATEGORY = "/category";
	
    /** FORM */
    public static final String FORM = "/form";
    
    /** SERVICE_BOARD */
    public static final String SERVICE_BOARD = "/svc-board";
    public static final String JPM_SERVICE_BOARD = "/jpm-svc-board";
    
    public static final String REPORTS_OZR_VIEW = "/ozr-view";
	
	/** Document */
	public static final String DOCUMENT = "/document";
	public static final String DOC = "/doc";
	public static final String DOCUMENT_EXPORT = "/export";
	public static final String VIEW_DOC = "/view-doc";
	public static final String DOC_SHOW_PROCESS_HISTORY = "/show-process-history";
	public static final String DOC_SHOW_WORKFLOW = "/show-workflow";
	
	/** GET_LIST_FOR_SELECT */
    public static final String GET_LIST_FOR_SELECT = "/get-list-for-select";
    
    /** STATUS_GET_LIST_FOR_SELECT */
    public static final String STATUS_GET_LIST_FOR_SELECT = "/status/get-list-for-select";

    /** BUSINESS */
    public static final String JPM_BUSINESS = "/jpm-business";
    
    /** PROCESS */
    public static final String JPM_PROCESS = "/jpm-process";
    
    /** PROCESS DELOY */
    public static final String JPM_PROCESS_DEPLOY = "/jpm-process-deploy";
    
    /** SLA */
    public static final String SLA = "/sla";
    public static final String SLA_RESET_STEP = "/reset-step";
    
    /** TO DO */
    public static final String TODO = "/todo";
    
    /** OZ_FILE */
    public static final String OZ_DOC_MAIN_FILE = "/oz-doc-main-file";
    
    /** API_V1_FORM_CONVERT_OZD */
    public static final String API_V1_FORM_CONVERT_OZD = "/convert-ozd";
    
    /** API_V1_FORM */
    public static final String API_V1_FORM = "/api/v1/form";   
    
    public static final String API_V1_SIGN_PDF = "api/v1/sign-pdf";
    
    public static final String API_DOWNLOAD_TEMPLATE = "/download";
    
    /** URL ACOUNT_TEAM */
    public static final String ACCOUNT_TEAM = "/account-team";
    public static final String ACCOUNT_TEAM_GET = "/get-user-for-team";
    public static final String ACCOUNT_TEAM_ADD = "/add-user-for-team";
    public static final String ACCOUNT_TEAM_DELETE = "/delete-user-for-team";
    public static final String ACCOUNT_TEAM_DETAIL_GET = "/get-user-for-team-detail";
    
    public static final String INFO = "/info";
    
    /** FeedBack */
    public static final String FEEDBACK = "/feedback";
    
    /** FeedBack */
    public static final String USERGUIDE = "/user-guide";
    
    /** Get Firebase Config */
    public static final String AJAX_FIREBASE_CONFIG = "/ajaxFirebaseConfig";
    
    /** API_DOWNLOAD_MAIN_FILE */
    public static final String API_DOWNLOAD_PDF = "api/v1/doc/download/pdf";
    // private static final String URL_API_DOWNLOAD_PDF_ECM = "api/v1/doc/download/pdf-ecm";
    public static final String API_DOWNLOAD_PDF_ECM_INCLUDE_OZR = "api/v1/doc/download/pdf-ecm-include-ozr";
    public static final String API_PUT_JSON_TO_OZD = "api/v1/doc/put/json-to-ozd";
    
    /** Task statistic */
    public static final String TASK_STATISTIC = "/task-statistic";
    
    /** ARCHIVE */
    public static final String ARCHIVE = "/archive";
    public static final String ARCHIVE_ROLLBACK = "/archive-rollback";
    public static final String ARCHIVE_CHECK = "/archive-check";
    
    public static final String NOTI_TEMPLATE = "/noti-template";
}
