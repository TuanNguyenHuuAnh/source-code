/*******************************************************************************
 * Class        ：RoleForProcessAddListReq
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：KhuongTH
 * Change log   ：2021/01/19：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.RoleForProcessInfoReq;

/**
 * <p>
 * RoleForProcessAddListReq
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class RoleForProcessAddListReq  extends RoleForProcessInfoReq{
    
    /** The process deploy id. */
    @ApiModelProperty(notes = "Process deploy id", required = true, position = 0)
    private Long processDeployId;
    
    /** The data. */
    @ApiModelProperty(notes = "List of authority", required = true, position = 0)
    private List<RoleForProcessAddReq> data;
}
