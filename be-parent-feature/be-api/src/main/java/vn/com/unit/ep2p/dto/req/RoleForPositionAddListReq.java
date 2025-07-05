/*******************************************************************************
 * Class        ：RoleForPositionAddListReq
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：taitt
 * Change log   ：2021/01/25：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * RoleForPositionAddListReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class RoleForPositionAddListReq{
    @ApiModelProperty(notes = "List of role for position add", required = true, position = 0)
    private List<RoleForPositionAddInforReq> listRoleForPositionAddInforReqs;
}
