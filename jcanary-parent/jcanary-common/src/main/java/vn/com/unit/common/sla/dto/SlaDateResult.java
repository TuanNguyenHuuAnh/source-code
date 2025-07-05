/*******************************************************************************
 * Class        ：SlaDateResultDto
 * Created date ：2020/11/16
 * Lasted date  ：2020/11/16
 * Author       ：TrieuVD
 * Change log   ：2020/11/16：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.common.sla.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * SlaDateResultDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class SlaDateResult {
    // SLA Plan
    private Long slaConfigId;
    private Date planStartDate;
    private Date planDueDate;
    private int planEstimateTime;
    private int planCalandarType;
    private String planEstimateUnitTime;
    private Long planTotalTime;

    // SLA Actual
    private Date actualStartDate;
    private Date actualEndDate;
    private Long actualElapseTime;
}
