/*******************************************************************************
 * Class        ：CalendarTypeUpdateReq
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：TrieuVD
 * Change log   ：2020/12/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * CalendarTypeUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class DayOffUpdateReq {
    
    @ApiModelProperty(notes = "Id of day off on system", example = "1", required = true, position = 0)
    private Long id;
    
    @ApiModelProperty(notes = "day off format dd/MM/", example = "1", required = true, position = 0)
    private Date dayOff;
    
    @ApiModelProperty(notes = "Id of day off on system", example = "1", required = true, position = 0)
    private String description;
    
    @ApiModelProperty(notes = "Id of day off on system", example = "1", required = true, position = 0)
    private Long calendarTypeId;
    
    @ApiModelProperty(notes = "Id of day off on system", example = "1", required = true, position = 0)
    private String dayOffType;
    
    @ApiModelProperty(notes = "Id of day off on system", example = "1", required = true, position = 0)
    private Long companyId;
    
    @ApiModelProperty(notes = "Id of day off on system", example = "1", required = true, position = 0)
    private boolean dayOffFlag;
    
    @ApiModelProperty(notes = "Id of org on system", example = "1", required = true, position = 0)
    private Long orgId;
}
