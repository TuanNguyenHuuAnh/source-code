/*******************************************************************************
* Class        JpmHiTaskAssignee
* Created date 2021/03/02
* Lasted date  2021/03/02
* Author       KhuongTH
* Change log   2021/03/02 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.entity;

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
 * JpmHiTaskAssignee
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_HI_TASK_ASSIGNEE)
public class JpmHiTaskAssignee extends AbstractAuditTracking {

    /** Column: PROCESS_DEPLOY_ID type NUMBER(20,0) NOT NULL */
	@Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "PROCESS_DEPLOY_ID")
    private Long processDeployId;

    /** Column: STEP_DEPLOY_ID type NUMBER(20,0) NOT NULL */
	@Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "STEP_DEPLOY_ID")
    private Long stepDeployId;

    /** Column: PERMISSION_DEPLOY_ID type NUMBER(20,0) NOT NULL */
	@Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "PERMISSION_DEPLOY_ID")
    private Long permissionDeployId;

    /** Column: TASK_ID type NUMBER(20,0) NOT NULL */
	@Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "TASK_ID")
    private Long taskId;

    /** Column: ACCOUNT_ID type NUMBER(20,0) NOT NULL */
	@Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    /** Column: PERMISSION_TYPE type NUMBER(2,0) NOT NULL */
    @Column(name = "PERMISSION_TYPE")
    private Integer permissionType;

    /** Column: SUBMITTED_FLAG type NUMBER(1,0) NOT NULL */
    @Column(name = "SUBMITTED_FLAG")
    private boolean submittedFlag;

    /** Column: OWNER_FLAG type NUMBER(1,0) NOT NULL */
    @Column(name = "OWNER_FLAG")
    private boolean ownerFlag;

    /** Column: ASSIGNEE_FLAG type NUMBER(1,0) NOT NULL */
    @Column(name = "ASSIGNEE_FLAG")
    private boolean assigneeFlag;

    /** Column: DELEGATE_FLAG type NUMBER(1,0) NOT NULL */
    @Column(name = "DELEGATE_FLAG")
    private boolean delegateFlag;

}