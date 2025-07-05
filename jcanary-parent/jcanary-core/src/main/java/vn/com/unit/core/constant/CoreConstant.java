/********************************************************************************
* Class        : CoreConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/
package vn.com.unit.core.constant;

import vn.com.unit.common.constant.CommonConstant;

/**
* <p> CoreConstant </p>
* 
* @version : 01-00
* @since 01-00
* @author : TaiTT
 */
public class CoreConstant extends CommonConstant {

    // PASSWORD_ENCRYPT
    //public static final String PASSWORD_ENCRYPT = "******";

	// ALIAS
	public static final String ALIAS_JCA_ACCOUNT = "acc";
	public static final String ALIAS_EFO_CATEGORY = "cat";

	// API CONSTANT
	public static final String API_V1_FORM = "/api/v1/form";
	public static final String API_V1_FORM_CONVERT_OZD = "/convert-ozd";
	public static final String API_V1_DOC_SAVE_OZD = "/api/v1/doc/save-ozd";

	// BEAN POOL
	public static final String BEAN_BPMN_DOCUMENT_WORKFLOW = "bpmnDocumentWorkflow";
	public static final String BEAN_SAVE_OZDOC_CORE = "saveOzDocCore";
	public static final String BEAN_INTEGRATE_DOCUMENT_WORKFLOW = "integrateDocumentWorkflow";
	public static final String BEAN_SAVE_OZDOC_AUDIT_CORE = "saveOzDocAuditCore";
	public static final String BEAN_FREE_DOCUMENT_WORKFLOW = "freeDocumentWorkflow";
	public static final String BEAN_SAVE_OZDOC_PAYMENT_CORE = "saveOzDocPaymentCore";

	// DOCUMENT
	public static final String EXTEND_OZD = ".ozd";
	public static final String EXTEND_PDF = ".pdf";
	public static final String MINE_PDF = "application/pdf";

	// PARAMETTER
	public static final String DOC_DTO = "ozDocDto";
	public static final String ACTION_DTO = "actionDto";
	public static final String HTTP_REQUEST = "httpRequest";
	public static final String KEY_COMPANY_NAME = "httpRequest";

	// SEQUENCE
	/** SEQ_JCA_ROLE */ 
	public static final String SEQ_JCA_ROLE = "SEQ_JCA_ROLE";

	// TABLE
	public static final String TABLE_EFO_OZ_DOC = "EFO_OZ_DOC";
	public static final String TABLE_EFO_OZ_DOC_MAIN_FILE = "EFO_OZ_DOC_MAIN_FILE";
	public static final String TABLE_EFO_OZ_DOC_MAIN_FILE_VERSION = "EFO_OZ_DOC_MAIN_FILE_VERSION";
	public static final String TABLE_EFO_HI_TRACK_OZ_DOC = "EFO_HI_TRACK_OZ_DOC";
	public static final String TABLE_EFO_HI_TRACK_FULL_OZ_DOC = "EFO_HI_TRACK_FULL_OZ_DOC";
	public static final String TABLE_EFO_OZ_DOC_VIEW_TRACKING = "EFO_OZ_DOC_VIEW_TRACKING";
	public static final String TABLE_EFO_DOC_UNIT_SUBMITTED = "EFO_DOC_UNIT_SUBMITTED";
	public static final String TABLE_EFO_DOC_UNITS_REVIEW = "EFO_DOC_UNITS_REVIEW";
	public static final String TABLE_EFO_FORM = "EFO_FORM";
	public static final String TABLE_EFO_OZ_DOC_VERSION = "EFO_OZ_DOC_VERSION";
	public static final String TABLE_JCA_ACCOUNT = "JCA_ACCOUNT";
	public static final String TABLE_JCA_ACCOUNT_REGISTER = "JCA_ACCOUNT_REGISTER";
	public static final String TABLE_JCA_ROLE = "JCA_ROLE";
	public static final String TABLE_JCA_POSITION = "JCA_POSITION";
	public static final String TABLE_JCA_ITEM = "JCA_ITEM";
	public static final String TABLE_JCA_TEAM = "JCA_TEAM";
	public static final String TABLE_JCA_MENU = "JCA_MENU";
	public static final String TABLE_JCA_MENU_PATH = "JCA_MENU_PATH";
	public static final String TABLE_JCA_ORGANIZATION = "JCA_ORGANIZATION";
	public static final String TABLE_JCA_ORGANIZATION_PATH = "JCA_ORGANIZATION_PATH";
	public static final String TABLE_JCA_COMPANY = "JCA_COMPANY";
	public static final String TABLE_JCA_GROUP_SYSTEM_SETTING = "JCA_GROUP_SYSTEM_SETTING";
	public static final String TABLE_JCA_SYSTEM_SETTING = "JCA_SYSTEM_SETTING";
	public static final String TABLE_JCA_ACCOUNT_CA = "JCA_ACCOUNT_CA";
	public static final String TABLE_JCA_ACCOUNT_ORG = "JCA_ACCOUNT_ORG";
	public static final String TABLE_EFO_CATEGORY = "EFO_CATEGORY";
	public static final String TABLE_JCA_ROLE_FOR_TEAM = "JCA_ROLE_FOR_TEAM";
	public static final String TABLE_JCA_EMAIL_TEMPLATE = "JCA_EMAIL_TEMPLATE";
	public static final String TABLE_JCA_AUTHORITY = "JCA_AUTHORITY";
	public static final String TABLE_JCA_POSITION_PATH = "JCA_POSITION_PATH";
	public static final String TABLE_JCA_GROUP_CONSTANT = "JCA_GROUP_CONSTANT";
	public static final String TABLE_JCA_CONSTANT = "JCA_CONSTANT";
	public static final String TABLE_JCA_GROUP_CONSTANT_LANGUAGE = "JCA_GROUP_CONSTANT_LANGUAGE";
	public static final String TABLE_JCA_CONSTANT_LANGUAGE = "JCA_CONSTANT_LANGUAGE";
	public static final String TABLE_EFO_COMPONENT = "TABLE_EFO_COMPONENT";
	public static final String TABLE_JCA_RULE_SETTING = "JCA_RULE_SETTING";
	public static final String TABLE_JCA_EMAIL_TEMPLATE_LANG = "JCA_EMAIL_TEMPLATE_LANG";
	public static final String TABLE_EFO_OZ_DOC_FILTER_IN = "EFO_OZ_DOC_FILTER_IN";
	public static final String TABLE_EFO_OZ_DOC_FILTER_OUT = "EFO_OZ_DOC_FILTER_OUT";
	public static final String TABLE_EFO_OZ_DOC_FILTER_REF = "EFO_OZ_DOC_FILTER_REF";
	public static final String TABLE_JCA_ACCOUNT_TEAM = "JCA_ACCOUNT_TEAM";
	public static final String TABLE_JCA_SLA_CONFIG = "JCA_SLA_CONFIG";
	public static final String TABLE_JCA_ROLE_FOR_ACCOUNT = "JCA_ROLE_FOR_ACCOUNT";
	public static final String TABLE_JCA_ROLE_FOR_POSITION = "JCA_ROLE_FOR_POSITION";
	public static final String TABLE_EFO_OZ_DOC_FILTER_TRACKING = "EFO_OZ_DOC_FILTER_TRACKING";
	public static final String TABLE_JCA_DATATABLE_CONFIG = "JCA_DATATABLE_CONFIG";
	public static final String TABLE_JCA_APP_INBOX = "JCA_APP_INBOX";
	public static final String TABLE_JCA_APP_INBOX_LANG = "JCA_APP_INBOX_LANG";
	public static final String TABLE_JCA_EMAIL = "JCA_EMAIL";
	public static final String TABLE_I18N_LOCALE_DEFAULT = "I18N_LOCALE_DEFAULT";
	public static final String TABLE_I18N_LOCALE = "I18N_LOCALE";
	public static final String TABLE_JCA_ATTACH_FILE = "JCA_ATTACH_FILE";
	public static final String TABLE_JCA_MENU_LANGUAGE = "JCA_MENU_LANGUAGE";
	public static final String TABLE_JCA_NOTI_TEMPLATE = "JCA_NOTI_TEMPLATE";
	public static final String TABLE_JCA_NOTI_TEMPLATE_LANG = "JCA_NOTI_TEMPLATE_LANG";
	public static final String TABLE_JCA_ATTACH_FILE_EMAIL = "JCA_M_ATTACH_FILE_EMAIL";
	public static final String TABLE_JCA_GROUP_CONSTANT_LANG = "JCA_GROUP_CONSTANT_LANG";
	public static final String TABLE_JCA_MENU_LANG = "JCA_MENU_LANG";
	public static final String TABLE_JCA_HI_SYSTEM_SETTING = "JCA_HI_SYSTEM_SETTING";
	public static final String TABLE_JCA_STYLE = "JCA_STYLE";
	public static final String TABLE_JCA_USER_GUIDE = "JCA_USER_GUIDE";
}