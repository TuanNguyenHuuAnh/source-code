/*******************************************************************************
 * Class        ：slaCalendarAddDto
 * Created date ：2021/03/08
 * Lasted date  ：2021/03/08
 * Author       ：ngannh
 * Change log   ：2021/03/08：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.sla.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * slaCalendarAddDto
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
@NoArgsConstructor
public class SlaCalendarAddDto {
    private Long companyId;
    private Date fromDate;
    private Date toDate;
    private Long calendarTypeId;
    private String startTimeMorning;
    private String endTimeMorning;
    private String startTimeEvening;
    private String endTimeEvening;
    private String description;
    private List<Integer> listDate;
    
}
