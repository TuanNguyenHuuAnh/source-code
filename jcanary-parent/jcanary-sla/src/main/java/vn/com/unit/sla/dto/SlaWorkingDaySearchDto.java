/*******************************************************************************
 * Class        ：SlaWorkingDaySearchDto
 * Created date ：2021/03/10
 * Lasted date  ：2021/03/10
 * Author       ：ngannh
 * Change log   ：2021/03/10：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.sla.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * SlaWorkingDaySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class SlaWorkingDaySearchDto {
    private Long companyId;
    private Long calendarTypeId;
    private Date fromDate;
    private Date toDate;
}
