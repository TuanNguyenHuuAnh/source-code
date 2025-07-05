/********************************************************************************
* Class        : CoreExceptionCodeConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/
package vn.com.unit.core.constant;

import vn.com.unit.common.constant.CommonExceptionCodeConstant;

/**
* <p> CoreExceptionCodeConstant </p>
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
public class CoreExceptionCodeConstant extends CommonExceptionCodeConstant {


	// EFO_FORM (800-899)
	/** Create function failed */ 
	public static final String E301804_CORE_REGISTER_FORM_CREATE_FUNCTION_FAIL = "301804_CORE_REGISTER_FORM_CREATE_FUNCTION_FAIL";
	/** Save component failed */ 
	public static final String E301807_CORE_REGISTER_FORM_SAVE_COMPONENT = "301807_CORE_REGISTER_FORM_SAVE_COMPONENT";

	// ERROR DEFINE  (750-799)
	/** Error documentService not implement */ 
	public static final String E301750_CORE_DOCUMENT_SERVICE_NOT_IMPLEMENT = "301750_CORE_DOCUMENT_SERVICE_NOT_IMPLEMENT";

	// ERROR DEFAULT (600-699)
	/** Internal_error */ 
	public static final String E301600_CORE_INTERNAL_ERROR = "301600_CORE_INTERNAL_ERROR";

	// JCA_ACCOUNT (900-999)
	/** Encrypt password account error */ 
	public static final String E301901_ENCRYPT_PASSWORD_ERROR = "301901_ENCRYPT_PASSWORD_ERROR";
	/** Decrypt password account error */ 
	public static final String E301902_DECRYPT_PASSWORD_ERROR = "301902_DECRYPT_PASSWORD_ERROR";

	// MODEL MAPPER (700-710)
	/** Mapper object */ 
	public static final String E301701_CORE_MODEL_MAPPER_ERROR = "301701_CORE_MODEL_MAPPER_ERROR";
}