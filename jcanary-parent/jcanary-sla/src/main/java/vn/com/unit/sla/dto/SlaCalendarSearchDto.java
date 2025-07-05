/*******************************************************************************
 * Class        ：SlaDayOffSearchDto
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：TrieuVD
 * Change log   ：2020/12/16：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * SlaDayOffSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class SlaCalendarSearchDto {
    private Long companyId;
    private Long calendarTypeId;
    private Date fromDate;
    private Date toDate;
    private int year;
}
