/********************************************************************************
* Class        : DtsExceptionCodeConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/
package vn.com.unit.dts.constant;



/**
* <p> DtsExceptionCodeConstant </p>
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
public class DtsExceptionCodeConstant {


	// ERROR AUTHEN
	/** Unauthorized */ 
	public static final String E401_UNAUTHORIZED = "401_UNAUTHORIZED";
	/** Forbidden */ 
	public static final String E401_FORBIDDEN = "401_FORBIDDEN";

	// ERROR DATABASE (600 - 699)
	/** Error connect database */ 
	public static final String E601_ERROR_DB = "601_ERROR_DB";
	/** Error data not found */ 
	public static final String E602_ERROR_DATA_NOT_FOUND = "602_ERROR_DATA_NOT_FOUND";

	// ERROR HYPERTEXT TRANSFER PROTOCOL (000 - 599)
	/** ERROR_INTERNAL */ 
	public static final String E500_ERROR_INTERNAL = "500_ERROR_INTERNAL";
	/** Error timeout request */ 
	public static final String E501_TIME_OUT = "501_TIME_OUT";
	/** Error invalid params */ 
	public static final String E502_INVALID_INPUT = "502_INVALID_INPUT";
	/** Error invalid header */ 
	public static final String E503_INVALID_HEADER = "503_INVALID_HEADER";
}