/*******************************************************************************
 * Class        ：RoleForPositionAddReq
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：taitt
 * Change log   ：2021/01/25：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * RoleForPositionAddReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class RoleForPositionAddReq {
    /** The role id. */
    private Long roleId;

    /** The can access flg. */
    private Boolean authorityFlag;

}
