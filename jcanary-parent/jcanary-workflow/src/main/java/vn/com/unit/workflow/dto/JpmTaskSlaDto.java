/*******************************************************************************
 * Class        ：JpmTaskSlaDto
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/

package vn.com.unit.workflow.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * JpmTaskSlaDto
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */

@Getter
@Setter
public class JpmTaskSlaDto {
    
    /** The jpm task id. */
    private Long jpmTaskId;
    
    /** The doc id. */
    private Long docId;
    
    private Long ownerId;
    
    private Long ownerOrgId;
    
    /** The sla config id. */
    private Long slaConfigId;
    
    /** The plan start date. */
    private Date planStartDate;
    
    /** The plan due date. */
    private Date planDueDate;
    
    /** The plan estimate time. */
    private Long planEstimateTime;
    
    /** The plan calandar type. */
    private Integer planCalandarType;
    
    /** The plan estimate unit time. */
    private String planEstimateUnitTime;
    
    /** The plan total time. */
    private Long planTotalTime;
    
    /** The actual start date. */
    private Date actualStartDate;
    
    /** The actual end date. */
    private Date actualEndDate;
    
    /** The actual elapse time. */
    private Long actualElapseTime;
}