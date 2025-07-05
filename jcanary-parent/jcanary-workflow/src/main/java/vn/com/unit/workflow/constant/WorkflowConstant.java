/********************************************************************************
* Class        : WorkflowConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/
package vn.com.unit.workflow.constant;

import vn.com.unit.common.constant.CommonConstant;

/**
* <p> WorkflowConstant </p>
* 
* @version : 01-00
* @since 01-00
* @author : TaiTT
 */
public class WorkflowConstant extends CommonConstant {


	// DATA TYPE WORKFLOW CONSTANT
	public static final String DATA_TYPE_STRING = "String";
	public static final String DATA_TYPE_LONG = "Long";
	public static final String DATA_TYPE_DOUBLE = "Double";
	public static final String DATA_TYPE_FLOAT = "Float";
	public static final String DATA_TYPE_INTEGER = "Integer";
	public static final String DATA_TYPE_DATE = "Date";
	public static final String DATA_TYPE_BOOLEAN = "Boolean";

	// KEY
	public static final String PERMISSION_CODE_KEY = "JPM";

	// PARAMETTER
	public static final String TASK_RETURN_ID = "taskReturnId";
	public static final String SUBMITTED_BY = "submittedBy";
	public static final String SUBMITTER = "submitter";
	public static final String BUSINESS_KEY = "businessKey";
	public static final String IS_RETURN = "isReturn";
	public static final String TASK_OUT_COME = "taskOutcome";
	public static final String PNG = "png";
	public static final String MAP_KEY = "action";
	public static final String PASSIVE_KEY = "actionPassive";
	public static final String LANG_KEY = "language";
	public static final String ACT_ID_KEY = "actId";
	public static final String INITIATOR = "initiator";
	public static final String DOC_AUTHOR = "docAuthor";
	public static final String DOC_TYPE_ID = "docTypeId";
	public static final String DOC_TYPE = "docType";
	public static final String COMPANY_ID = "companyId";
	public static final String SUBMITTED_DATE = "submittedDate";
	public static final String PRE_TASK_ID = "preTaskId";
	public static final String EXPIRED_SLA = "EXPIRED_SLA";
	public static final String NONE_EXPIRED_FIELD = "NONE_EXPIRED_SLA";
	public static final String STEP_CODE = "stepCode";
	public static final String JPM_TASK_ID = "jpmTaskId";
	public static final String APPROVED = "approved";

	// PARAMETTER WORKFLOW
	public static final String P_PROCESS_DEPLOY_ID = "P_PROCESS_DEPLOY_ID";
	public static final String P_SYS_DATE = "P_SYS_DATE";
	public static final String P_COMPANY_ID = "P_COMPANY_ID";
	public static final String P_DOC_CODE = "P_DOC_CODE";
	public static final String P_COMMON_STATUS_CODE = "P_COMMON_STATUS_CODE";
	public static final String P_PROCESS_STATUS_CODE = "P_PROCESS_STATUS_CODE";
	public static final String P_PROCESS_STATUS_NAME = "P_PROCESS_STATUS_NAME";
	public static final String P_SYSTEM_CODE = "P_SYSTEM_CODE";
	public static final String P_APP_CODE = "P_APP_CODE";
	public static final String P_CORE_TASK_ID = "P_CORE_TASK_ID";
	public static final String P_OWNER_ID = "P_OWNER_ID";
	public static final String P_JPM_TASK_ID = "P_JPM_TASK_ID";
	public static final String P_STEP_CODE = "P_STEP_CODE";
	public static final String P_SUBMITER_ID = "P_SUBMITER_ID";
	public static final String P_BUTTON_ID = "P_BUTTON_ID";
	public static final String P_STEP_ID = "P_STEP_ID";
	public static final String P_NOTE = "P_NOTE";
	public static final String P_DOC_ID = "P_DOC_ID";
	public static final String P_SUBMITTED_DATE = "P_SUBMITTED_DATE";
	public static final String P_SUBMITTED_ORG_ID = "P_SUBMITTED_ORG_ID";
	public static final String P_SUBMITTED_POSITION_ID = "P_SUBMITTED_POSITION_ID";
	public static final String P_COMPLETED_ID = "P_COMPLETED_ID";
	public static final String P_COMPLETED_DATE = "P_COMPLETED_DATE";
	public static final String P_COMPLETED_ORG_ID = "P_COMPLETED_ORG_ID";
	public static final String P_COMPLETED_POSITION_ID = "P_COMPLETED_POSITION_ID";
	public static final String P_OWNER_ORG_ID = "P_OWNER_ORG_ID";
	public static final String P_OWNER_POSITION_ID = "P_OWNER_POSITION_ID";
	public static final String P_SLA_CONFIG_ID = "P_SLA_CONFIG_ID";
	public static final String P_PLAN_START_DATE = "P_PLAN_START_DATE";
	public static final String P_PLAN_DUE_DATE = "P_PLAN_DUE_DATE";
	public static final String P_PLAN_ESTIMATE_TIME = "P_PLAN_ESTIMATE_TIME";
	public static final String P_PLAN_CALANDAR_TYPE = "P_PLAN_CALANDAR_TYPE";
	public static final String P_PLAN_ESTIMATE_UNIT_TIME = "P_PLAN_ESTIMATE_UNIT_TIME";
	public static final String P_PLAN_TOTAL_TIME = "P_PLAN_TOTAL_TIME";
	public static final String P_ACTUAL_START_DATE = "P_ACTUAL_START_DATE";
	public static final String P_ACTUAL_END_DATE = "P_ACTUAL_END_DATE";
	public static final String P_ACTUAL_ELAPSE_TIME = "P_ACTUAL_ELAPSE_TIME";
	public static final String P_STEP_DEPLOY_ID = "P_STEP_DEPLOY_ID";
	public static final String P_BUSINESS_ID = "P_BUSINESS_ID";
	public static final String P_SUBMITTED_ID = "P_SUBMITTED_ID";
	public static final String P_ASSGINEE_ID_LIST = "P_ASSGINEE_ID_LIST";
	public static final String P_SUBMITTED_ID_LIST = "P_SUBMITTED_ID_LIST";
	public static final String P_OWNER_ID_LIST = "P_OWNER_ID_LIST";
	public static final String P_STEP_CODE_PREV = "P_STEP_CODE_PREV";

	// TABLE
	public static final String TABLE_JPM_BUSINESS = "JPM_BUSINESS";
	public static final String TABLE_JPM_BUTTON = "JPM_BUTTON";
	public static final String TABLE_JPM_BUTTON_DEFAULT = "JPM_BUTTON_DEFAULT";
	public static final String TABLE_JPM_BUTTON_DEFAULT_LANG = "JPM_BUTTON_DEFAULT_LANG";
	public static final String TABLE_JPM_BUTTON_DEPLOY = "JPM_BUTTON_DEPLOY";
	public static final String TABLE_JPM_BUTTON_FOR_STEP = "JPM_BUTTON_FOR_STEP";
	public static final String TABLE_JPM_BUTTON_FOR_STEP_DEPLOY = "JPM_BUTTON_FOR_STEP_DEPLOY";
	public static final String TABLE_JPM_STEP_DEPLOY = "JPM_STEP_DEPLOY";
	public static final String TABLE_JPM_PROCESS_DEPLOY = "JPM_PROCESS_DEPLOY";
	public static final String TABLE_JPM_TASK = "JPM_TASK";
	public static final String TABLE_JPM_STATUS_LANG_DEPLOY = "JPM_STATUS_LANG_DEPLOY";
	public static final String TABLE_JPM_TASK_TRACKING = "JPM_TASK_TRACKING";
	public static final String TABLE_JPM_HI_TASK = "JPM_HI_TASK";
	public static final String TABLE_JPM_PARAM_DEPLOY = "JPM_PARAM_DEPLOY";
	public static final String TABLE_JPM_BUTTON_LANG_DEPLOY = "JPM_BUTTON_LANG_DEPLOY";
	public static final String TABLE_JPM_PARAM_CONFIG_DEPLOY = "JPM_PARAM_CONFIG_DEPLOY";
	public static final String TABLE_JPM_HI_BUSINESS = "JPM_HI_BUSINESS";
	public static final String TABLE_JPM_STATUS = "JPM_STATUS";
	public static final String TABLE_JPM_PARAM_CONFIG = "JPM_PARAM_CONFIG";
	public static final String TABLE_JPM_STATUS_DEFAULT_LANG = "JPM_STATUS_DEFAULT_LANG";
	public static final String TABLE_JPM_HI_PARAM = "JPM_HI_PARAM";
	public static final String TABLE_JPM_HI_STATUS = "JPM_HI_STATUS";
	public static final String TABLE_JPM_STATUS_DEPLOY = "JPM_STATUS_DEPLOY";
	public static final String TABLE_JPM_STEP = "JPM_STEP";
	public static final String TABLE_JPM_STATUS_DEFAULT = "JPM_STATUS_DEFAULT";
	public static final String TABLE_JPM_PROCESS_DEPLOY_LANG = "JPM_PROCESS_DEPLOY_LANG";
	public static final String TABLE_JPM_PROCESS = "JPM_PROCESS";
	public static final String TABLE_JPM_PERMISSION_DEPLOY = "JPM_PERMISSION_DEPLOY";
	public static final String TABLE_JPM_BUTTON_LANG = "JPM_BUTTON_LANG";
	public static final String TABLE_JPM_HI_PROCESS = "JPM_HI_PROCESS";
	public static final String TABLE_JPM_STATUS_LANG = "JPM_STATUS_LANG";
	public static final String TABLE_JPM_PROCESS_LANG = "JPM_PROCESS_LANG";
	public static final String TABLE_JPM_PERMISSION = "JPM_PERMISSION";
	public static final String TABLE_JPM_HI_BUTTON = "JPM_HI_BUTTON";
	public static final String TABLE_JPM_BUTTON_STEP_DEFAULT = "JPM_BUTTON_STEP_DEFAULT";
	public static final String TABLE_JPM_HI_PERMISSION = "JPM_HI_PERMISSION";
	public static final String TABLE_JPM_STEP_LANG = "JPM_STEP_LANG";
	public static final String TABLE_JPM_HI_STEP = "JPM_HI_STEP";
	public static final String TABLE_JPM_PARAM = "JPM_PARAM";
	public static final String TABLE_JPM_STEP_LANG_DEPLOY = "JPM_STEP_LANG_DEPLOY";
	public static final String TABLE_JPM_AUTHORITY = "JPM_AUTHORITY";
	public static final String TABLE_JPM_TASK_ASSIGNEE = "JPM_TASK_ASSIGNEE";
	public static final String TABLE_JPM_HI_TASK_ASSIGNEE = "JPM_HI_TASK_ASSIGNEE";
	public static final String TABLE_JPM_STATUS_COMMON = "JPM_STATUS_COMMON";
	public static final String TABLE_JPM_PROCESS_INST_ACT = "JPM_PROCESS_INST_ACT";
	public static final String TABLE_JPM_SLA_INFO = "JPM_SLA_INFO";
	public static final String TABLE_JPM_SLA_CONFIG = "JPM_SLA_CONFIG";
	public static final String TABLE_JPM_PROCESS_DMN = "JPM_PROCESS_DMN";
	public static final String TABLE_JPM_PROCESS_DMN_DEPLOY = "JPM_PROCESS_DMN_DEPLOY";
	public static final String TABLE_JPM_TASK_SLA = "JPM_TASK_SLA";
	public static final String TABLE_JPM_HI_TASK_SLA = "JPM_HI_TASK_SLA";
	public static final String TABLE_JPM_TASK_EMAIL_ALERT = "JPM_TASK_EMAIL_ALERT";
	public static final String TABLE_JPM_TASK_NOTI_ALERT = "JPM_TASK_NOTI_ALERT";
}