/*******************************************************************************
* Class        JpmStepDeploy
* Created date 2021/03/05
* Lasted date  2021/03/05
* Author       KhuongTH
* Change log   2021/03/05 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;
import vn.com.unit.workflow.constant.WorkflowConstant;

/**
 * JpmStepDeploy
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_STEP_DEPLOY)
public class JpmStepDeploy extends AbstractTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_STEP_DEPLOY)
    private Long id;

    /** Column: STEP_NO type NUMBER(3,0) NULL */
    @Column(name = "STEP_NO")
    private Integer stepNo;

    /** Column: STEP_CODE type VARCHAR2(100.0) NULL */
    @Column(name = "STEP_CODE")
    private String stepCode;

    /** Column: STEP_NAME type NVARCHAR2(255.0) NULL */
    @Column(name = "STEP_NAME")
    private String stepName;

    /** Column: DESCRIPTION type NVARCHAR2(2000.0) NULL */
    @Column(name = "DESCRIPTION")
    private String description;

    /** Column: COMMON_STATUS_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "COMMON_STATUS_ID")
    private Long commonStatusId;

    /** Column: COMMON_STATUS_CODE type VARCHAR2(100.0) NULL */
    @Column(name = "COMMON_STATUS_CODE")
    private String commonStatusCode;

    /** Column: STATUS_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "STATUS_DEPLOY_ID")
    private Long statusDeployId;

    /** Column: STATUS_CODE type VARCHAR2(100.0) NULL */
    @Column(name = "STATUS_CODE")
    private String statusCode;

    /** Column: PROCESS_DEPLOY_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "PROCESS_DEPLOY_ID")
    private Long processDeployId;

    /** Column: STEP_KIND type VARCHAR2(100.0) NULL */
    @Column(name = "STEP_KIND")
    private String stepKind;

    /** Column: STEP_TYPE type VARCHAR2(100.0) NULL */
    @Column(name = "STEP_TYPE")
    private String stepType;

    /** Column: USE_CLAIM_BUTTON type NUMBER(1,0) NULL */
    @Column(name = "USE_CLAIM_BUTTON")
    private boolean useClaimButton;

    /** Column: STEP_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "STEP_ID")
    private Long stepId;

}