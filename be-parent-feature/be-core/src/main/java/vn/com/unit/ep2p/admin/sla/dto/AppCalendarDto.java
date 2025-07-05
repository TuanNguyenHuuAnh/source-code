/*******************************************************************************
 * Class        ：AppCalendarDto
 * Created date ：2021/03/09
 * Lasted date  ：2021/03/09
 * Author       ：ngannh
 * Change log   ：2021/03/09：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.sla.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.sla.dto.SlaCalendarDto;

/**
 * AppCalendarDto
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppCalendarDto {
    private Date calendar;
    private String calendarType;
    List<SlaCalendarDto> slaCalendarDtoList;
}
