/*******************************************************************************
 * Class        ：GroupSystemConfigInfoReq
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：ngannh
 * Change log   ：2020/12/16：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * GroupSystemConfigInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class GroupSystemConfigInfoReq {

    @ApiModelProperty(notes = "Code of group config on system", example = "email", required = true, position = 0)
    private String groupCode;
    
    @ApiModelProperty(notes = "Name of group config on system", example = "email", required = true, position = 0)
    private String groupName;
    
    @ApiModelProperty(notes = "Id of company", example = "1", position = 0)
    private Long companyId;
}
