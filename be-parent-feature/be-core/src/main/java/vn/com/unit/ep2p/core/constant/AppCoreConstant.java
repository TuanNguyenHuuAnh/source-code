
/********************************************************************************
* Class        : AppCoreConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/

package vn.com.unit.ep2p.core.constant;

import vn.com.unit.common.constant.CommonConstant;
/**
* <p> AppCoreConstant </p>
* 
* @version : 01-00
* @since 01-00
* @author : TaiTT
 */
public class AppCoreConstant extends CommonConstant {

   // ACTION TYPE
  public static final String ACTION_SUBMIT = "actionType=submit";

   // PARAMETTER
  public static final String PARAMETTER_ORG_IDS = "orgIds";
  public static final String PARAMETTER_POS_IDS = "posIds";

   // URL_CONSTANT_REPO
  public static final String URL_CONSTANT_REPO_FORM = "/api/v1/form";
  public static final String URL_CONSTANT_REPO_DOWNLOAD_TEAMPLATE = "/download";
  public static final String URL_CONSTANT_REPO_REPORT_TEMPLATE = "/reports";
  public static final String URL_CONSTANT_REPO_REPORT_COMPONENT_TEMPLATE = "/component";
  public static final String URL_CONSTANT_REPO_CONVERT_OZD = "/convert-ozd";
  public static final String URL_CONSTANT_REPO_SAVE_OZD = "/doc/save-ozd";
  public static final String URL_CONSTANT_REPO_DOC = "/api/v1/doc";
  public static final String URL_CONSTANT_REPO_DOC_DOWNLOAD_PDF_ECM_INCLUDE_OZR = "/download/pdf-ecm-include-ozr";
  public static final String URL_CONSTANT_URL_DOC_DOWNLOAD_PDF = "/download/pdf-by-ozd";

   // REPO_PARAM
  public static final String REPO_PARAM_OZ_SERVER_URL = "ozServerUrl";
  public static final String REPO_PARAM_OZ_USER = "ozUser";
  public static final String REPO_PARAM_OZ_PASSWORD = "ozPassword";

   // CONSTANT_STATUS_COMMON_DOCUMENT
  public static final String PROC_STATUS_TYPE = "PROC_STATUS_TYPE";
  public static final String PROFILE_STATUS_TYPE = "PROFILE_STATUS_TYPE";

   // TABLE
  public static final String TABLE_EFO_FORM = "EFO_FORM";
  public static final String TABLE_EFO_FORM_AUTHORITY = "EFO_FORM_AUTHORITY";
  public static final String TABLE_EFO_CATEGORY = "EFO_CATEGORY";
  public static final String TABLE_EFO_CATEGORY_LANG = "EFO_CATEGORY_LANG";
  public static final String TABLE_EFO_COMPONENT = "EFO_COMPONENT";
  public static final String TABLE_EFO_OZ_DOC = "EFO_OZ_DOC";
  public static final String TABLE_EFO_DOC = "EFO_DOC";
  public static final String TABLE_EFO_FORM_LANG = "EFO_FORM_LANG";
  public static final String TABLE_EFO_DOC_VERSION = "EFO_DOC_VERSION";

   // CONSTANT_GROUP_PROFILE_STATUS
  public static final String JCA_APP_PROFILE_STATUS = "JCA_APP_PROFILE_STATUS";

   // CONSTANT_GROUP_PROCESS_STATUS
  public static final String JCA_APP_PROC_STATUS = "JCA_APP_PROC_STATUS";
  public static final String AT_SIGN = "@";
}