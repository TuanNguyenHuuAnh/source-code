/*******************************************************************************
 * Class        ：CalendarTypeUpdateReq
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：TrieuVD
 * Change log   ：2020/12/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.SlaConfigInfoReq;

/**
 * CalendarTypeUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class SlaConfigUpdateReq extends SlaConfigInfoReq {
    
    @ApiModelProperty(notes = "Id of sla config type on system", example = "1", required = true, position = 0)
    private Long id;
    
    //jcaSla
    @ApiModelProperty(notes = "jcaSlaConfigId of sla config on system", example = "1", required = true, position = 8)
    private Long jcaSlaConfigId;
}
