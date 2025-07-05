/*******************************************************************************
* Class        JpmTaskAssigneeDto
* Created date 2021/03/02
* Lasted date  2021/03/02
* Author       KhuongTH
* Change log   2021/03/02 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmTaskAssigneeDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmTaskAssigneeDto {

    private Long id;

    private Long stepDeployId;

    private Long permissionDeployId;

    private Long taskId;

    private Long accountId;
    
    private Integer permissionType;
    
    private Long processDeployId;

	/*
	 * private Integer permissionType;
	 * 
	 * private boolean submittedFlag;
	 * 
	 * private boolean ownerFlag;
	 * 
	 * private boolean assigneeFlag;
	 * 
	 * private boolean delegateFlag;
	 */

}