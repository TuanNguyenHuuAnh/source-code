/*******************************************************************************
 * Class        ：JpmSlaConfig
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
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
 * <p>
 * JpmSlaConfig
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_HI_TASK_SLA)
public class JpmHiTaskSla extends AbstractAuditTracking {

    /** The id. */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "JPM_TASK_ID")
    private Long jpmTaskId;
    
    /** Column: DOC_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "DOC_ID")
    private Long docId;
    
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

}