/********************************************************************************
* Class        : WorkflowActivitiExceptionCodeConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/
package vn.com.unit.workflow.activiti.constant;

import vn.com.unit.common.constant.CommonExceptionCodeConstant;

/**
* <p> WorkflowActivitiExceptionCodeConstant </p>
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
public class WorkflowActivitiExceptionCodeConstant extends CommonExceptionCodeConstant {


	// ERROR DEFAULT (600-699)
	/** WFA_INTERNAL_ERROR */ 
	public static final String E204600_WFA_INTERNAL_ERROR = "204600_WFA_INTERNAL_ERROR";
}