/********************************************************************************
* Class        : CommonConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/
package vn.com.unit.common.constant;

import vn.com.unit.dts.constant.DtsConstant;

/**
* <p> CommonConstant </p>
* 
* @version : 01-00
* @since 01-00
* @author : TaiTT
 */
public class CommonConstant extends DtsConstant {

    public static final Long COMPANY_DEFAULT = 2L;

	// BEAN POOL
	public static final String BEAN_REST_FULL_API_EXTERNAL = "restFullApiExternal";
	public static final String BEAN_SOAP_API_EXTERNAL = "soapApiExternal";

	// COLON_ROLE
	public static final String COLON_EDIT = ":Edit";
	public static final String COLON_DISP = ":Disp";
	public static final String COLON_DELETE = ":Delete";
	public static final String COLON = ":";

	// PASSWORD_WEB
	public static final String PASSWORD_ENCRYPT = "********";

	// RESULT_STATUS
	public static final int RESULT_STATUS_SUCCESS = 1;
	public static final int RESULT_STATUS_FAIL = 0;

	// STORE PROCEDURE MESSAGE
	public static final String SP_MSG_SUCCESS = "success";
	public static final String SP_MSG_FAIL = "fail";

	// STRING CONSTANT
	public static final String STR_TRUE = "true";
	public static final String STR_FALSE = "false";

	// API - EXTERNAL 
	public static final String REPLACE_CHARACTER_SOAP = "\\<\\?xml(.+?)\\?\\>";
	public static final String DATA_TAG_REGEX = "\\<data\\>(.*?)(<\\/data>)";

	// CHARACTER SPECIAL
	public static final String SLASH = "/";
	public static final String DOLLAR = "$";
	public static final String AMPERSAND = "&";
	public static final String OPENING_CURLY_BRACE = "{";
	public static final String CLOSING_CURLY_BRACE = "}";
	public static final String OPENING_BRACKET = "[";
	public static final String CLOSING_BRACKET = "]";
	public static final String SPACE = " ";
	public static final String DOUBLE_SLASH = "//";
	public static final String FOUR_BACK_SLASH = "\\\\";
	public static final String UNDERSCORE = "_";
	public static final char CHAR_DOUBLE_QUOTATION_MARK = '"';
	public static final char CHAR_IS_LESS_THAN = '<';
	public static final char CHAR_IS_MORE_THAN = '>';
	public static final char CHAR_AMPERSAND = '&';
	public static final String SHARP = "#";

	// ESCAPE
	public static final String ESCAPE_QUOT = "&quot;";
	public static final String ESCAPE_AMP = "&amp;";
	public static final String ESCAPE_LT = "&lt;";
	public static final String ESCAPE_GT = "&gt;";

	// NUMBER CONSTANT
	public static final int NUMBER_ONE = 1;
	public static final int NUMBER_ZERO = 0;
	public static final int NUMBER_TWO = 2;
	public static final int NUMBER_THREE = 3;
	public static final int NUMBER_FOUR = 4;

	// NUMBER FORMAT CHARACTER OR INT
	public static final String STR_ZERO = "0";
	public static final String STR_ONE = "1";
	public static final String STR_TWO = "2";
	public static final String STR_THREE = "3";
	public static final String STR_FOUR = "4";

	// NUMBER LONG CONSTANT
	public static final long NUMBER_ONE_L = 1L;
	public static final long NUMBER_ZERO_L = 0L;

	// SYSTEM
	public static final long SYSTEM_ID = 0;
	public static final String SYSTEM_PREFIX = "DSUCCESS-";
	public static final String SYSTEM_CODE_PPL = "DSUCCESS";
	public static final String APP_CODE_WEB = "DSUCCESS-WEBAPP";
	
	public static final String BUSINESS_EMAIL = "BUSINESS_EMAIL";
	
	// email
	public static final String SEND_DIRECT_NO_SAVE = "SEND_DIRECT_NO_SAVE";
    public static final String SEND_DIRECT_SAVE = "SEND_DIRECT_SAVE";
    public static final String SEND_BY_BATCH = "SEND_BY_BATCH";
    
    public static final String EMAIL_PROCESS_TEMPLATE = "common-process-template";
    public static final String EMAIL_PROCESS_NOTIFICATION_TEMPLATE = "email-notification-requester-process";
    
    public static final String EMAIL_PROCESS_CATALOG = "process-email-catalog-template";
    public static final String SEND_EMAIL_STATUS = "SENDEMAILSTATUS";
    public static final String SEND_EMAIL_OPT = "SENDEMAILOPT";
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
}