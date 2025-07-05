/*******************************************************************************
 * Class        ：SlaDayOffDto
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractAuditTracking;
import vn.com.unit.sla.constant.SlaConstant;
import vn.com.unit.sla.utils.SlaDateUtils;

/**
 * <p>
 * SlaCalendarDto
 * </p>
 * 
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlaCalendarDto extends AbstractAuditTracking{

    /** The id. */
    private Long id;
    
    /** The calendar date. */
    private Date calendarDate;

    /** The calendar type id. */
    private Long calendarTypeId;
    
    /** The start time. */
    private String startTime;

    /** The end time. */
    private String endTime;
    
    /** The description. */
    private String description;
    
    
    /**
     * <p>
     * Instantiates a new sla calendar dto.
     * </p>
     *
     * @author TrieuVD
     * @param calendarDate
     *            type {@link Date}
     * @param startTime
     *            type {@link String}
     * @param endTime
     *            type {@link String}
     */
    public SlaCalendarDto(Date calendarDate, String startTime, String endTime) {
        super();
        this.calendarDate = calendarDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    /**
     * <p>
     * Get calendar date string.
     * </p>
     *
     * @author TrieuVD
     * @return {@link String}
     */
    public String getCalendarDateString() {
        return SlaDateUtils.formatDateToString(this.calendarDate, SlaConstant.SLA_DATE_PATTERN);
    }

    /**
     * <p>
     * Get start calendar.
     * </p>
     *
     * @author TrieuVD
     * @return {@link Date}
     */
    public Date getStartCalendar() {
        return SlaDateUtils.parseDate(this.getCalendarDateString().concat(SlaConstant.SPACE).concat(this.getStartTime()),
                SlaConstant.SLA_DATE_PATTERN.concat(SlaConstant.SPACE).concat(SlaConstant.SLA_HOUR_MINUS_PATTERN));
    }
    
    /**
     * <p>
     * Get end calendar.
     * </p>
     *
     * @author TrieuVD
     * @return {@link Date}
     */
    public Date getEndCalendar() {
        return SlaDateUtils.parseDate(this.getCalendarDateString().concat(SlaConstant.SPACE).concat(this.getEndTime()),
                SlaConstant.SLA_DATE_PATTERN.concat(SlaConstant.SPACE).concat(SlaConstant.SLA_HOUR_MINUS_PATTERN));
    }
    
    /**
     * <p>
     * Get off minutes.
     * </p>
     *
     * @author TrieuVD
     * @return {@link long}
     */
    public long getOffMinutes() {
        return SlaDateUtils.calcMilliseconds(this.getStartCalendar(), this.getEndCalendar())/SlaConstant.MINUTE_2_MILLISECONDS;
    }

}
