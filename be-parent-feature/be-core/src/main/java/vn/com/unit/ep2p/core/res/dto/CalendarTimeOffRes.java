/*******************************************************************************
 * Class        ：CalendarTimeOffRes
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.res.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * CalendarTimeOffRes
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarTimeOffRes {

    /** The start time. */
    private Date startTime;
    
    /** The end time. */
    private Date endTime;
    
    /** The start hour. */
    private String startHour;
    
    /** The end hour. */
    private String endHour;
}
