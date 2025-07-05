/********************************************************************************
* Class        : CommonExceptionCodeConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/
package vn.com.unit.common.constant;

import vn.com.unit.dts.constant.DtsExceptionCodeConstant;

/**
* <p> CommonExceptionCodeConstant </p>
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
public class CommonExceptionCodeConstant extends DtsExceptionCodeConstant {


	// ERROR_SORT_DYNAMIC_TABLE (700-720)
	/** Sort dynamic table error not found */ 
	public static final String E101700_SORT_DYNAMIC_TABLE = "101700_SORT_DYNAMIC_TABLE";

	// ERROR DEFAULT (600-699)
	/** Internal_error */ 
	public static final String E101600_COMMON_INTERNAL_ERROR = "101600_COMMON_INTERNAL_ERROR";
	
	public static final String E101600_CODE_EXISTS_ERROR = "101600_CODE_EXISTS_ERROR";
}