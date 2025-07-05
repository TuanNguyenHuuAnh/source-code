/*******************************************************************************
* Class        JpmProcessInstAct
* Created date 2021/03/02
* Lasted date  2021/03/02
* Author       KhuongTH
* Change log   2021/03/02 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.activiti.entity;

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
 * JpmProcessInstAct
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_PROCESS_INST_ACT)
public class JpmProcessInstAct extends AbstractAuditTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_PROCESS_INST_ACT)
    private Long id;

    /** Column: PROCESS_DEPLOY_ID type VARCHAR2(100.0) NOT NULL */
    @Column(name = "PROCESS_DEPLOY_ID")
    private String processDeployId;

    /** Column: BUSINESS_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "BUSINESS_ID")
    private Long businessId;

    /** Column: PROCESS_INST_ACT_ID type NVARCHAR2(64.0) NOT NULL */
    @Column(name = "PROCESS_INST_ACT_ID")
    private String processInstActId;

    /** Column: REFERENCE_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "REFERENCE_ID")
    private Long referenceId;

    /** Column: REFERENCE_TYPE type NUMBER(2,0) NOT NULL */
    @Column(name = "REFERENCE_TYPE")
    private Integer referenceType;

    /** Column: PROCESS_STATUS_ID type NUMBER(20,0) NULL */
    @Column(name = "PROCESS_STATUS_ID")
    private Long processStatusId;

    /** Column: COMMON_STATUS_ID type NUMBER(20,0) NULL */
    @Column(name = "COMMON_STATUS_ID")
    private Long commonStatusId;

}