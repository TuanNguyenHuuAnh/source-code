/*******************************************************************************
 * Class        ：SlaDateResultDto
 * Created date ：2020/11/16
 * Lasted date  ：2020/11/16
 * Author       ：TrieuVD
 * Change log   ：2020/11/16：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.dto;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.common.sla.dto.SlaDateResult;
import vn.com.unit.sla.utils.SlaDateUtils;

/**
 * SlaDateResultDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlaDateResultDto extends SlaDateResult {

    private Date submittedDate;
    private List<SlaCalendarDto> calendarList;
    private int slaMinutes;
    private Date dueDate;
    private Date completedDate;
    private int elapsedMinutes;
    private boolean continueCheckCalendar;

    public SlaDateResultDto(Date submittedDate, int slaMinutes, Date dueDate, List<SlaCalendarDto> calendarList,
            boolean continueCheckCalendar) {
        super();
        this.submittedDate = submittedDate;
        this.slaMinutes = slaMinutes;
        this.dueDate = dueDate;
        this.calendarList = calendarList;
        this.continueCheckCalendar = continueCheckCalendar;
        
        super.setActualStartDate(submittedDate);
        super.setPlanStartDate(submittedDate);
        super.setPlanDueDate(dueDate);
        super.setPlanTotalTime(Long.valueOf(slaMinutes));
        super.setPlanEstimateUnitTime(SlaDateUtils.timeUnitToFullTime(slaMinutes, TimeUnit.MINUTES, null));
    }

    public SlaDateResultDto(Date submittedDate, Date completedDate, int elapsedMinutes, int slaMinutes,
            List<SlaCalendarDto> calendarList) {
        super();
        this.submittedDate = submittedDate;
        this.completedDate = completedDate;
        this.elapsedMinutes = elapsedMinutes;
        this.slaMinutes = slaMinutes;
        this.calendarList = calendarList;
        
        super.setPlanStartDate(submittedDate);
        //this.planDueDate = dueDate;
        super.setPlanTotalTime(Long.valueOf(slaMinutes));
        super.setPlanEstimateUnitTime(SlaDateUtils.timeUnitToFullTime(slaMinutes, TimeUnit.MINUTES, null));
        super.setActualElapseTime(Long.valueOf(elapsedMinutes));
    }
    
    public SlaDateResultDto(Date submittedDate, Date planStartDate, int slaMinutes, Date dueDate, List<SlaCalendarDto> calendarList,
            boolean continueCheckCalendar) {
        super();
        this.submittedDate = submittedDate;
        this.slaMinutes = slaMinutes;
        this.dueDate = dueDate;
        this.calendarList = calendarList;
        this.continueCheckCalendar = continueCheckCalendar;
        
        super.setActualStartDate(submittedDate);
        super.setPlanStartDate(planStartDate);
        super.setPlanDueDate(dueDate);
        super.setPlanTotalTime(Long.valueOf(slaMinutes));
        super.setPlanEstimateUnitTime(SlaDateUtils.timeUnitToFullTime(slaMinutes, TimeUnit.MINUTES, null));
    }
}
