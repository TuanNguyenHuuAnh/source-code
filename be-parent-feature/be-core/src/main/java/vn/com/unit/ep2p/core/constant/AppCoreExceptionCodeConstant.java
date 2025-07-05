
/********************************************************************************
* Class        : AppCoreExceptionCodeConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/

package vn.com.unit.ep2p.core.constant;

import vn.com.unit.common.constant.CommonExceptionCodeConstant;
/**
* <p> AppCoreExceptionCodeConstant </p>
* 
* <pre> 
* The First character is for the description level module: 1
* The next two characters are for the module descriptive name: 01
* The next three characters continue are for the module description error code: 600
* </pre> 
* 
* <pre> 
* JCOMMON is module code required, next characters is message error: INTERNAL_ERROR
* </pre> 
* @version : 01-00
* @since 01-00
* @author : TaiTT
 */
public class AppCoreExceptionCodeConstant extends CommonExceptionCodeConstant {

   // ERROR DEFAULT (600-700)
   /** APPAPI_ACCOUNT_ORG_NOT_FOUND */ 
  public static final String E401600_APPCORE_INTERNAL_ERROR = "4022306_APPAPI_ACCOUNT_ORG_NOT_FOUND";

   // EFO_FORM (800-899)
   /** Error register form exists */ 
  public static final String E401801_CORE_REGISTER_FORM_EXISTS = "401801_CORE_REGISTER_FORM_EXISTS";
   /** Error register form failed */ 
  public static final String E401802_CORE_REGISTER_FORM_FAIL = "401802_CORE_REGISTER_FORM_FAIL";
   /** Auto gen decsription form */ 
  public static final String E401803_CORE_REGISTER_FORM_AUTO_GEN_DESCRIPTION = "401803_CORE_REGISTER_FORM_AUTO_GEN_DESCRIPTION";
   /** Create function failed */ 
  public static final String E401805_CORE_REGISTER_FORM_CANNOT_RETRIEVE_OZ = "401805_CORE_REGISTER_FORM_CANNOT_RETRIEVE_OZ";
   /** Save form failed */ 
  public static final String E401806_CORE_REGISTER_FORM_SAVE_FAIL = "401806_CORE_REGISTER_FORM_SAVE_FAIL";
   /** Register form success */ 
  public static final String E401808_CORE_REGISTER_FORM_SUCCESS = "401808_CORE_REGISTER_FORM_SUCCESS";
   /** Company to register form not found */ 
  public static final String E401809_CORE_REGISTER_FORM_COMPANY_NOT_FOUNT = "401809_CORE_REGISTER_FORM_COMPANY_NOT_FOUNT";
   /** Business to register form not found */ 
  public static final String E401810_CORE_REGISTER_FORM_BUSINESS_NOT_FOUNT = "401810_CORE_REGISTER_FORM_BUSINESS_NOT_FOUNT";

   // API ANNOTATION (900-1099)
   /** APPAPI_ANNOTATION_FORMAT_DATE */ 
  public static final String E4024801_APPAPI_ANNOTATION_FORMAT_DATE = "4024801_APPAPI_ANNOTATION_FORMAT_DATE";
   /** APPAPI_ANNOTATION_NON_NULL */ 
  public static final String E4024802_APPAPI_ANNOTATION_NON_NULL = "4024802_APPAPI_ANNOTATION_NON_NULL";
   /** APPAPI_ANNOTATION_STRING_REQUIRED */ 
  public static final String E4024803_APPAPI_ANNOTATION_STRING_REQUIRED = "4024803_APPAPI_ANNOTATION_STRING_REQUIRED";
   /** APPAPI_ANNOTATION_NOT_MATH_PATTERN */ 
  public static final String E4024804_APPAPI_ANNOTATION_NOT_MATH_PATTERN = "4024804_APPAPI_ANNOTATION_NOT_MATH_PATTERN";
   /** APPAPI_ANNOTATION_CONTAIN_ARRAY */ 
  public static final String E4024805_APPAPI_ANNOTATION_CONTAIN_ARRAY = "4024805_APPAPI_ANNOTATION_CONTAIN_ARRAY";
   /** APPAPI_ANNOTATION_MAXLENGTH_ARRAY */ 
  public static final String E4024806_APPAPI_ANNOTATION_MAXLENGTH_ARRAY = "4024806_APPAPI_ANNOTATION_MAXLENGTH_ARRAY";
   /** APPAPI_ANNOTATION_NOT_MATH_ENUM */ 
  public static final String E4024807_APPAPI_ANNOTATION_NOT_MATH_ENUM = "4024807_APPAPI_ANNOTATION_NOT_MATH_ENUM";
}