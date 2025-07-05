/*******************************************************************************
 * Class        ：RoleForProcessInfoReq
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：KhuongTH
 * Change log   ：2021/01/19：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Sets the role id.
 * </p>
 *
 * @param roleId
 *            the new role id
 * @author KhuongTH
 */
@Getter
@Setter
public class RoleForProcessInfoReq {

    /** roleId */
    @ApiModelProperty(notes = "Id of role", example = "1", required = true, position = 0)
    private Long roleId;

}
