/*******************************************************************************
* Class        JpmHiTaskDto
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
 * JpmHiTaskDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmHiTaskDto {

    private String id;

    private Long processDeployId;

    private Long stepDeployId;

    private Long permissionDeployId;

    private String stepDeployCode;

    private Integer permissionType;

    private String taskType;

    private Long docId;

    private Long submittedId;
    private String submittedName;
    private String submittedFullName;
    private String submittedCode;
    private String submittedEmail;
    private String submittedAvatar;
    private Long submittedAvatarRepoId;
    private Date submittedDate;
    private Long submittedOrgId;
    private String submittedOrgName;
    private Long submittedPositionId;
    private String submittedPositionName;


    private Long completedId;
    private String completedName;
    private String completedFullName;
    private String completedCode;
    private String completedEmail;
    private String completedAvatar;
    private Long completedAvatarRepoId;
    private Date completedDate;
    private Long completedOrgId;
    private String completedOrgName;
    private Long completedPositionId;
    private String completedPositionName;

    private Long ownerId;
    private String ownerName;
    private String ownerFullName;
    private String ownerCode;
    private String ownerEmail;
    private String ownerAvatar;
    private Long ownerAvatarRepoId;
    private Long ownerOrgId;
    private String ownerOrgName;
    private Long ownerPositionId;
    private String ownerPositionName;
    
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

    // only view
    private String actionName;
    private String actionTime;
    private String stepName;
    private String statusName;
    private String nextStatus;
    private String buttonName;
    
    private String jsonData;
    private Long actionId;
    private String note;
    private String actTaskId;

}