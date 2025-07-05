/*******************************************************************************
 * Class        ：RoleForItemAddReq
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：ngannh
 * Change log   ：2020/12/24：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.RoleForItemInfoReq;

/**
 * RoleForItemAddReq.
 *
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */

/**
 * Gets the can edit flg.
 *
 * @return the can edit flg
 */
@Getter

/**
 * Sets the can edit flg.
 *
 * @param canEditFlg
 *            the new can edit flg
 */
@Setter
public class RoleForItemAddReq{


    @ApiModelProperty(notes = "Id of role", example = "1", required = true, position = 1)
    /** The role id. */
    private Long roleId;
    
    @ApiModelProperty(notes = "List role for item information add", required = true, position = 2)
    List<RoleForItemInfoReq> listRoleForItemInfoReq;
    
}
