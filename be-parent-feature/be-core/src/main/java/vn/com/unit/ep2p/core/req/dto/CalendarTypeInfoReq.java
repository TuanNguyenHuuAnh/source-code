/*******************************************************************************
 * Class        ：CalendarTypeInfoReq
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：TrieuVD
 * Change log   ：2020/12/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * CalendarTypeInfoReq.
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class CalendarTypeInfoReq {
    
    /** The code. */
    @ApiModelProperty(notes = "code of calendar type on system", example = "CODE", required = true, position = 0)
    private String code;
    
    /** The name. */
    @ApiModelProperty(notes = "name of calendar type on system", example = "NAME", required = true, position = 1)
    private String name;
    
    /** The company id. */
    @ApiModelProperty(notes = "companyId of calendar type on system", example = "1", required = true, position = 2)
    private Long companyId;
    
    /** The company id. */
    @ApiModelProperty(notes = "orgId of calendar type on system", example = "1", required = true, position = 3)
    private Long orgId;
    
    /** The description. */
    @ApiModelProperty(notes = "description of calendar type on system", example = "DESCRIPTION", required = false, position = 4)
    private String description;
    
    /** The start morning time. */
    @ApiModelProperty(notes = "start working morning (format HH:mm)", example = "08:30", required = true, position = 5)
    private String startMorningTime;
    
    /** The end morning time. */
    @ApiModelProperty(notes = "end working morning (format HH:mm)", example = "12:00", required = true, position = 6)
    private String endMorningTime;
    
    /** The start afternoon time. */
    @ApiModelProperty(notes = "start working afternoon (format HH:mm)", example = "13:00", required = true, position = 7)
    private String startAfternoonTime;
    
    /** The end afternoon time. */
    @ApiModelProperty(notes = "start working afternoon (format HH:mm)", example = "17:30", required = true, position = 8)
    private String endAfternoonTime;
}
