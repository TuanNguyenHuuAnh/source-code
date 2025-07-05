/********************************************************************************
* Class        : WorkflowExceptionCodeConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/
package vn.com.unit.workflow.constant;

import vn.com.unit.common.constant.CommonExceptionCodeConstant;

/**
* <p> WorkflowExceptionCodeConstant </p>
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
public class WorkflowExceptionCodeConstant extends CommonExceptionCodeConstant {


	// ERROR DEFAULT (600-699)
	/** WF_INTERNAL_ERROR */ 
	public static final String E203600_WF_INTERNAL_ERROR = "203600_WF_INTERNAL_ERROR";
	/** WF_INVALID_ACCESS_ERROR */ 
	public static final String E203601_WF_INVALID_ACCESS_ERROR = "203601_WF_INVALID_ACCESS_ERROR";
	/** WF_SUBMIT_PROCESS_ERROR */ 
	public static final String E203602_WF_SUBMIT_PROCESS_ERROR = "203602_WF_SUBMIT_PROCESS_ERROR";

	// ERROR START WORKFLOW (700-799)
	/** WORKFLOW_NOT_FOUND */ 
	public static final String E203703_WF_WORKFLOW_NOT_FOUND = "203703_WF_WORKFLOW_NOT_FOUND";
	/** WORKFLOW_OLD */ 
	public static final String E203704_WF_WORKFLOW_OLD = "203704_WF_WORKFLOW_OLD";
}