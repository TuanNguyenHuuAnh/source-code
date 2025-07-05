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
public class SlaConfigInfoReq {
    
    @ApiModelProperty(notes = "Name of sla config on system", example = "0", required = true, position = 0)
    private String slaName;
    
    @ApiModelProperty(notes = "due time of sla config on system", example = "1", required = true, position = 1)
    private Long slaDueTime;
    
    @ApiModelProperty(notes = "time type of sla config on system", example = "0", required = true, position = 2)
    private String slaTimeType;
    
    @ApiModelProperty(notes = "calendar option of sla config on system", example = "DEPARTMENT", required = true, position = 3)
    private String calendarOption;
    
    @ApiModelProperty(notes = "org of sla config on system", example = "1", required = true, position = 4)
    private Long orgId;
    
    @ApiModelProperty(notes = "calendar type id of sla config on system", example = "1", required = true, position = 5)
    private Long calendarTypeId;
    
    @ApiModelProperty(notes = "active flag of sla config on system", example = "true", required = true, position = 6)
    private boolean activeFlag;
    
    @ApiModelProperty(notes = "display order of sla config on system", example = "1", required = true, position = 7)
    private Long displayOrder;
    
    //jcaSla
    @ApiModelProperty(notes = "sla type of sla config on system", example = "1", required = true, position = 9)
    private String slaType;
    
    @ApiModelProperty(notes = "business of sla config on system", example = "1", required = true, position = 10)
    private Long businessId;
    
    @ApiModelProperty(notes = "process deploy of sla config on system", example = "1", required = true, position = 11)
    private Long processDeployId;
    
    @ApiModelProperty(notes = "step deploy of sla config on system", example = "1", required = true, position = 12)
    private Long stepDeployId;
}
