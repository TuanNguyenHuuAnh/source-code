/*******************************************************************************
* Class        JpmTask
* Created date 2021/03/02
* Lasted date  2021/03/02
* Author       KhuongTH
* Change log   2021/03/02 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractAuditTracking;
import vn.com.unit.workflow.constant.WorkflowConstant;

/**
 * JpmTask
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_TASK)
public class JpmTask extends AbstractAuditTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long id;

    /** Column: PROCESS_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "PROCESS_DEPLOY_ID")
    private Long processDeployId;

    /** Column: STEP_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "STEP_DEPLOY_ID")
    private Long stepDeployId;

    /** Column: PERMISSION_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "PERMISSION_DEPLOY_ID")
    private Long permissionDeployId;

    /** Column: STEP_DEPLOY_CODE type VARCHAR2(100.0) NULL */
    @Column(name = "STEP_DEPLOY_CODE")
    private String stepDeployCode;

    /** Column: PERMISSION_TYPE type NUMBER(2,0) NULL */
    @Column(name = "PERMISSION_TYPE")
    private Integer permissionType;

    /** Column: TASK_TYPE type VARCHAR2(255.0) NULL */
    @Column(name = "TASK_TYPE")
    private String taskType;

    /** Column: DOC_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "DOC_ID")
    private Long docId;
    
    /** Column: CORE_TASK_ID type NVARCHAR2(64) NOT NULL */
    @Column(name = "CORE_TASK_ID")
    private String coreTaskId;

    /** Column: SUBMITTED_ID type NUMBER(20,0) NULL */
    @Column(name = "SUBMITTED_ID")
    private Long submittedId;

    /** Column: SUBMITTED_DATE type DATE NULL */
    @Column(name = "SUBMITTED_DATE")
    private Date submittedDate;

    /** Column: SUBMITTED_ORG_ID type NUMBER(20,0) NULL */
    @Column(name = "SUBMITTED_ORG_ID")
    private Long submittedOrgId;

    /** Column: SUBMITTED_POSITION_ID type NUMBER(20,0) NULL */
    @Column(name = "SUBMITTED_POSITION_ID")
    private Long submittedPositionId;

    /** Column: COMPLETED_ID type NUMBER(20,0) NULL */
    @Column(name = "COMPLETED_ID")
    private Long completedId;

    /** Column: COMPLETED_DATE type DATE NULL */
    @Column(name = "COMPLETED_DATE")
    private Date completedDate;

    /** Column: COMPLETED_ORG_ID type NUMBER(20,0) NULL */
    @Column(name = "COMPLETED_ORG_ID")
    private Long completedOrgId;

    /** Column: COMPLETED_POSITION_ID type NUMBER(20,0) NULL */
    @Column(name = "COMPLETED_POSITION_ID")
    private Long completedPositionId;

    /** Column: OWNER_ID type NUMBER(20,0) NULL */
    @Column(name = "OWNER_ID")
    private Long ownerId;

    /** Column: OWNER_ORG_ID type NUMBER(20,0) NULL */
    @Column(name = "OWNER_ORG_ID")
    private Long ownerOrgId;

    /** Column: OWNER_POSITION_ID type NUMBER(20,0) NULL */
    @Column(name = "OWNER_POSITION_ID")
    private Long ownerPositionId;

    /** Column: SYSTEM_CODE type VARCHAR2(255.0) NULL */
    @Column(name = "SYSTEM_CODE")
    private String systemCode;

    /** Column: APP_CODE type VARCHAR2(255.0) NULL */
    @Column(name = "APP_CODE")
    private String appCode;

    /** Column: TASK_STATUS type VARCHAR2(255.0) NULL */
    @Column(name = "TASK_STATUS")
    private String taskStatus;

    /** Column: SLA_CONFIG_ID type NUMBER(20,0) NULL */
    @Column(name = "SLA_CONFIG_ID")
    private Long slaConfigId;

    /** Column: PLAN_START_DATE type DATE NULL */
    @Column(name = "PLAN_START_DATE")
    private Date planStartDate;

    /** Column: PLAN_DUE_DATE type DATE NULL */
    @Column(name = "PLAN_DUE_DATE")
    private Date planDueDate;

    /** Column: PLAN_ESTIMATE_TIME type NUMBER(10,0) NULL */
    @Column(name = "PLAN_ESTIMATE_TIME")
    private Long planEstimateTime;

    /** Column: PLAN_CALANDAR_TYPE type NUMBER(1,0) NULL */
    @Column(name = "PLAN_CALANDAR_TYPE")
    private Integer planCalandarType;

    /** Column: PLAN_ESTIMATE_UNIT_TIME type VARCHAR2(30.0) NULL */
    @Column(name = "PLAN_ESTIMATE_UNIT_TIME")
    private String planEstimateUnitTime;

    /** Column: PLAN_TOTAL_TIME type NUMBER(20,0) NULL */
    @Column(name = "PLAN_TOTAL_TIME")
    private Long planTotalTime;

    /** Column: ACTUAL_START_DATE type DATE NULL */
    @Column(name = "ACTUAL_START_DATE")
    private Date actualStartDate;

    /** Column: ACTUAL_END_DATE type DATE NULL */
    @Column(name = "ACTUAL_END_DATE")
    private Date actualEndDate;

    /** Column: ACTUAL_ELAPSE_TIME type NUMBER(20,0) NULL */
    @Column(name = "ACTUAL_ELAPSE_TIME")
    private Long actualElapseTime;

    /** Column: PARENT_TASK_ID type NUMBER(20,0) NULL */
    @Column(name = "PARENT_TASK_ID")
    private Long parentTaskId;
    
    /** Column: NOTE type NCLOB NULL */
    @Column(name = "NOTE")
    private String note;
    
    /** Column: JSON_DATA type NCLOB NULL */
    @Column(name = "JSON_DATA")
    private String jsonData;
    
    /** Column: ACTION_ID type NUMBER(20,0) NULL */
    @Column(name = "ACTION_ID")
    private Long actionId;

}