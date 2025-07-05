/*******************************************************************************
 * Class        ：CalendarTaskSlaRes
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.res.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * CalendarTaskSlaRes
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
public class CalendarTaskSlaRes {
    
    /** The calendar date. */
    private Date calendarDate;
    
    /** The calendar time off list. */
    private List<CalendarTimeOffRes> calendarTimeOffList;
}
