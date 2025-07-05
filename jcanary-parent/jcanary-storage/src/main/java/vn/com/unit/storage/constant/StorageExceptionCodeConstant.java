/********************************************************************************
* Class        : StorageExceptionCodeConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/
package vn.com.unit.storage.constant;

import vn.com.unit.common.constant.CommonExceptionCodeConstant;

/**
* <p> StorageExceptionCodeConstant </p>
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
public class StorageExceptionCodeConstant extends CommonExceptionCodeConstant {


	// ERROR DEFAULT (600-699)
	/** FS_INTERNAL_ERROR */ 
	public static final String E202600_FS_INTERNAL_ERROR = "202600_FS_INTERNAL_ERROR";

	// ERROR DOWNLOAD (800-899)
	/** FS_CAN_NOT_DOWNLOAD_FILE */ 
	public static final String E202804_FS_CAN_NOT_DOWNLOAD_FILE = "202804_FS_CAN_NOT_DOWNLOAD_FILE";

	// ERROR UPLOAD (700-799)
	/** FS_CAN_NOT_CREATE_FOLDER */ 
	public static final String E202701_FS_CAN_NOT_CREATE_FOLDER = "202701_FS_CAN_NOT_CREATE_FOLDER";
	/** FS_CAN_NOT_ACCESS_REPOSITORY */ 
	public static final String E202702_FS_CAN_NOT_ACCESS_REPOSITORY = "202702_FS_CAN_NOT_ACCESS_REPOSITORY";
	/** FS_CAN_NOT_UPLOAD_FILE */ 
	public static final String E202703_FS_CAN_NOT_UPLOAD_FILE = "202703_FS_CAN_NOT_UPLOAD_FILE";
}