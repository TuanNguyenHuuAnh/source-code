/*******************************************************************************
* Class        JpmTaskDto
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmTaskDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmTaskDto {

    private Long id;

    private Long processDeployId;

    private Long stepDeployId;

    private Long permissionDeployId;

    private String stepDeployCode;

    private Integer permissionType;

    private String taskType;

    private Long docId;

    private Long summittedId;

    private Date submittedDate;

    private Long submittedOrgId;

    private Long submittedPositionId;

    private Long completedId;

    private Date completedDate;

    private Long completedOrgId;

    private Long completedPositionId;

    private Long ownerId;

    private Long ownerOrgId;

    private Long ownerPositionId;

    private String systemCode;

    private String appCode;

    private String taskStatus;

    private Long slaConfigId;

    private Date planStartDate;

    private Date planDueDate;

    private Long planEstimateTime;

    private Integer planCalandarType;

    private String planEstimateUnitTime;

    private Long planTotalTime;

    private Date actualStartDate;

    private Date actualEndDate;

    private Long actualElapseTime;

    private Long parentTaskId;
    
    private String coreTaskId;
}