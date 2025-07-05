/*******************************************************************************
 * Class        ：SlaConfigAlertToDto
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：TrieuVD
 * Change log   ：2021/01/13：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * SlaConfigAlertToDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class SlaConfigAlertToReq {

//    @ApiModelProperty(notes = "id of sla config alert to on system", example = "0", required = true, position = 0)
//    private Long id;
//
//    @ApiModelProperty(notes = "sla config detai id", example = "0", required = true, position = 0)
//    private Long detailId;
    
    @ApiModelProperty(notes = "userTypeId of sla config detai id", example = "1", required = true, position = 0)
    private Long userTypeId;
    
    @ApiModelProperty(notes = "accountId of sla config detai id", example = "0", position = 0)
    private Long accountId;
    
    @ApiModelProperty(notes = "accountType of sla config detai id", example = "TO", required = true, position = 0)
    private String accountType;
}
