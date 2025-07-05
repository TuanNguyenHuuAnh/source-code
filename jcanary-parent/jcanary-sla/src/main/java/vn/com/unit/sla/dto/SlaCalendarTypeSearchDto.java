/*******************************************************************************
 * Class        ：SlaCalendarTypeSearchDto
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：TrieuVD
 * Change log   ：2020/12/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * SlaCalendarTypeSearchDto
 * </p>
 *
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */

@Getter
@Setter
public class SlaCalendarTypeSearchDto {

    /** The company id. */
    private Long companyId;
    
    /** The calendar type code. */
    private String calendarTypeCode;
    
    /** The calendar type name. */
    private String calendarTypeName;
    
    /** The description. */
    private String description;
}
