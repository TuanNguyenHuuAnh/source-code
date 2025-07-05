/*******************************************************************************
 * Class        ：JpmTaskSla
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
 * JpmTaskSla
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_TASK_SLA)
public class JpmTaskSla extends AbstractAuditTracking {

    /** The jpm task id. */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "JPM_TASK_ID")
    private Long jpmTaskId;
    
    /** The doc id. */
    @Column(name = "DOC_ID")
    private Long docId;
    
    /** The sla config id. */
    @Column(name = "SLA_CONFIG_ID")
    private Long slaConfigId;

    /** The plan start date. */
    @Column(name = "PLAN_START_DATE")
    private Date planStartDate;

    /** The plan due date. */
    @Column(name = "PLAN_DUE_DATE")
    private Date planDueDate;

    /** The plan estimate time. */
    @Column(name = "PLAN_ESTIMATE_TIME")
    private Long planEstimateTime;

    /** The plan calandar type. */
    @Column(name = "PLAN_CALANDAR_TYPE")
    private Integer planCalandarType;

    /** The plan estimate unit time. */
    @Column(name = "PLAN_ESTIMATE_UNIT_TIME")
    private String planEstimateUnitTime;

    /** The plan total time. */
    @Column(name = "PLAN_TOTAL_TIME")
    private Long planTotalTime;

    /** The actual start date. */
    @Column(name = "ACTUAL_START_DATE")
    private Date actualStartDate;

    /** The actual end date. */
    @Column(name = "ACTUAL_END_DATE")
    private Date actualEndDate;

    /** The actual elapse time. */
    @Column(name = "ACTUAL_ELAPSE_TIME")
    private Long actualElapseTime;

}