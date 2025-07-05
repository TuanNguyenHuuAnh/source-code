/********************************************************************************
* Class        : SlaExceptionCodeConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/
package vn.com.unit.sla.constant;

import vn.com.unit.common.constant.CommonExceptionCodeConstant;

/**
* <p> SlaExceptionCodeConstant </p>
* 
* <pre> 
* The First character is for the description level module: 1
* The next two characters are for the module descriptive name: 01
* The next three characters continue are for the module description error code: 600
* </pre> 
* 
* <pre> 
* COMMON is module code required, next characters is message error: INTERNAL_ERROR
* </pre> 
* @version : 01-00
* @since 01-00
* @author : TaiTT
 */
public class SlaExceptionCodeConstant extends CommonExceptionCodeConstant {


	// ERROR DEFAULT (600-699)
	/** SLA_INTERNAL_ERROR */ 
	public static final String E201600_SLA_INTERNAL_ERROR = "201600_SLA_INTERNAL_ERROR";

	// SLA_VALIDATE (1000-1099)
	/** SLA_CONFIG_ALERT_TO_REQUEST_NULL */ 
	public static final String E2011001_SLA_CONFIG_ALERT_TO_REQUEST_NULL = "2011001_SLA_CONFIG_ALERT_TO_REQUEST_NULL";

	// SLA_VALIDATE (700-799)
	/** SLA_VALIDATE_REQUIRED_ERROR */ 
	public static final String E201701_SLA_VALIDATE_REQUIRED_ERROR = "201701_SLA_VALIDATE_REQUIRED_ERROR";
	/** SLA_DATA_NOT_FOUND_ERROR */ 
	public static final String E201702_SLA_DATA_NOT_FOUND_ERROR = "201702_SLA_DATA_NOT_FOUND_ERROR";

	// SLA_VALIDATE (800-899)
	/** SLA_CONFIG_REQUEST_NULL */ 
	public static final String E201801_SLA_CONFIG_REQUEST_NULL = "201801_SLA_CONFIG_REQUEST_NULL";
	/** SLA_CONFIG_NOT_FOUND_ID */ 
	public static final String E201802_SLA_CONFIG_NOT_FOUND_ID = "201802_SLA_CONFIG_NOT_FOUND_ID";

	// SLA_VALIDATE (900-999)
	/** SLA_CONFIG_DETAIL_REQUEST_NULL */ 
	public static final String E201901_SLA_CONFIG_DETAIL_REQUEST_NULL = "201901_SLA_CONFIG_DETAIL_REQUEST_NULL";
	/** SLA_CONFIG_DETAIL_NOT_FOUND_ID */ 
	public static final String E201902_SLA_CONFIG_DETAIL_NOT_FOUND_ID = "201902_SLA_CONFIG_DETAIL_NOT_FOUND_ID";
}