/*******************************************************************************
 * Class        ：RoleForPositionAddInforReq
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：SonND
 * Change log   ：2021/01/25：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * RoleForPositionAddInforReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class RoleForPositionAddInforReq{
    /** The process deploy id. */
    @ApiModelProperty(notes = "Position id", required = true, position = 0)
    private Long positionId;
    
    /** The data. */
    @ApiModelProperty(notes = "List role", required = true, position = 0)
    private List<RoleForPositionAddReq> data;
}
