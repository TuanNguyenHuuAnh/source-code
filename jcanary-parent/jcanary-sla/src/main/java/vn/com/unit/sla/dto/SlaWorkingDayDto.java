/*******************************************************************************
 * Class        ：SlaWorkingDayDto
 * Created date ：2021/03/10
 * Lasted date  ：2021/03/10
 * Author       ：ngannh
 * Change log   ：2021/03/10：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.sla.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractAuditTracking;

/**
 * SlaWorkingDayDto
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlaWorkingDayDto extends AbstractAuditTracking{

    /** The id. */
    private Long id;
    
    /** The calendar date. */
    private Date workingDay;

    /** The calendar type id. */
    private Long calendarTypeId;
    
    /** The start time. */
    private String startTime;

    /** The end time. */
    private String endTime;
    
    /** The description. */
    private String description;
    
}
