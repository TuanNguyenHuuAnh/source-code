/*******************************************************************************
 * Class        ：RoleForProcessAddReq
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：KhuongTH
 * Change log   ：2021/01/19：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * RoleForProcessAddReq
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class RoleForProcessAddReq {

    /** The item id. */
    private Long itemId;

    /** The can access flg. */
    private boolean canAccessFlg;

    /** The can disp flg. */
    private boolean canDispFlg;

    /** The can edit flg. */
    private boolean canEditFlg;

}
