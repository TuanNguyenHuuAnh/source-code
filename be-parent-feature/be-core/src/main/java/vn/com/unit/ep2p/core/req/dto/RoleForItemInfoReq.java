/*******************************************************************************
 * Class        ：RoleForItemInfoReq
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：ngannh
 * Change log   ：2020/12/24：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * RoleForItemInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class RoleForItemInfoReq {
    /** The item id. */
    @ApiModelProperty(notes = "Id of item", example = "1", required = true, position = 1)
    private Long itemId;
    
    /** The can access flg. */
    @ApiModelProperty(notes = "Access item", example = "false", required = true, position = 2)
    private Boolean canAccessFlg;
    
    /** The can disp flg. */
    @ApiModelProperty(notes = "Display item", example = "false", required = true, position = 3)
    private Boolean canDispFlg;
    
    /** The can edit flg. */
    @ApiModelProperty(notes = "Edit item", example = "true", required = true, position = 4)
    private Boolean canEditFlg;
    
}
