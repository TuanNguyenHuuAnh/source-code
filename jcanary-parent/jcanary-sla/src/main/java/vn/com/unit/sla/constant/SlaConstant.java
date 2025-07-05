/********************************************************************************
* Class        : SlaConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/
package vn.com.unit.sla.constant;

import vn.com.unit.common.constant.CommonConstant;

/**
* <p> SlaConstant </p>
* 
* @version : 01-00
* @since 01-00
* @author : TaiTT
 */
public class SlaConstant extends CommonConstant {


	// CONVERT DATETIME
	public static final long SECOND_2_MILLISECONDS = 1000;
	public static final long MINUTE_2_MILLISECONDS = 60000;
	public static final long HOUR_2_MILLISECONDS = 3600000;
	public static final long DAY_2_MILLISECONDS = 86400000;
	public static final long HOUR_2_MINUTE = 60;
	public static final long DAY_2_MINUTE = 1440;

	// FIREBASE CONFIG KEY
	public static final String FIREBASE_URL_KEY = "FIREBASE_URL_KEY";
	public static final String FIREBASE_AUTHEN_KEY = "FIREBASE_AUTHEN_KEY";

	// PARAM TEMPLATE
	public static final String PARAM_START_WITH = "${";
	public static final String PARAM_END_WITH = "}";
	public static final String PARAM_REGEX_PATTERN = "${(.*?)}";

	// PATTERN
	public static final String SLA_DATE_PATTERN = "yyyyMMdd";
	public static final String SLA_HOUR_MINUS_PATTERN = "HH:mm";

	// REQUEST PROPERTY
	public static final String REQUEST_CONTEN_TYPE = "Content-Type";
	public static final String REQUEST_JSON = "application/json";
	public static final String REQUEST_AUTHORIZATION = "Authorization";
	public static final String REQUEST_KEY = "key=";

	// SEQUENCE
	public static final String SEQ_SLA_ALERT = "SEQ_SLA_ALERT";
	public static final String SEQ_SLA_ALERT_HISTORY = "SEQ_SLA_ALERT_HISTORY";
	public static final String SEQ_SLA_CALENDAR_TYPE = "SEQ_SLA_CALENDAR_TYPE";
	public static final String SEQ_SLA_CONFIG = "SEQ_SLA_CONFIG";
	public static final String SEQ_SLA_CONFIG_DETAIL = "SEQ_SLA_CONFIG_DETAIL";
	public static final String SEQ_SLA_DAY_OFF = "SEQ_SLA_DAY_OFF";

	// TABLE
	public static final String TABLE_SLA_EMAIL_ALERT = "SLA_EMAIL_ALERT";
	public static final String TABLE_SLA_EMAIL_ALERT_HISTORY = "SLA_EMAIL_ALERT_HISTORY";
	public static final String TABLE_SLA_CALENDAR_TYPE = "SLA_CALENDAR_TYPE";
	public static final String TABLE_SLA_CONFIG = "SLA_CONFIG";
	public static final String TABLE_SLA_CONFIG_DETAIL = "SLA_CONFIG_DETAIL";
	public static final String TABLE_SLA_CALENDAR = "SLA_CALENDAR";
	public static final String TABLE_SLA_CONFIG_ALERT_TO = "SLA_CONFIG_ALERT_TO";
	public static final String TABLE_SLA_USER_TYPE = "SLA_USER_TYPE";
	public static final String TABLE_SLA_EMAIL_ALERT_TO = "SLA_EMAIL_ALERT_TO";
	public static final String TABLE_SLA_EMAIL_ALERT_TO_HISTORY = "SLA_EMAIL_ALERT_TO_HISTORY";
	public static final String TABLE_SLA_WORKING_DAY = "SLA_WORKING_DAY";
	public static final String TABLE_SLA_CALENDAR_TYPE_LOG = "SLA_CALENDAR_TYPE_LOG";
	public static final String TABLE_SLA_INVOLED_TYPE = "SLA_INVOLED_TYPE";
	public static final String TABLE_SLA_INVOLED_TYPE_LANG = "SLA_INVOLED_TYPE_LANG";
	public static final String TABLE_SLA_NOTI_ALERT = "SLA_NOTI_ALERT";
	public static final String TABLE_SLA_NOTI_ALERT_HISTORY = "SLA_NOTI_ALERT_HISTORY";
	public static final String TABLE_SLA_NOTI_ALERT_TO = "SLA_NOTI_ALERT_TO";
	public static final String TABLE_SLA_NOTI_ALERT_TO_HISTORY = "SLA_NOTI_ALERT_TO_HISTORY";

	// VALUE DEFAULT
	public static final String START_TIME_DEFAULT = "00:00";
	public static final String END_TIME_DEFAULT = "23:59";
	public static final int NUM_DAYS_DEFAULT = 7;

	// CHARACTER SPECIAL
	public static final String COMMA = ",";
}