/*******************************************************************************
 * Class        ：QrtzMJobStoreReq
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Get validflag.
 * </p>
 *
 * @author khadm
 * @return {@link Long}
 */
@Getter

/**
 * <p>
 * Sets the validflag.
 * </p>
 *
 * @author khadm
 * @param validflag
 *            the new validflag
 */
@Setter
public class QrtzMJobStoreReq {
    
    /** The id. */
    private Long id;

    /** The group code. */
    private String groupCode;

    /** The store name. */
    private String storeName;

    /** The exec order. */
    private Long execOrder;

    /** The description. */
    private String description;

    /** The validflag. */
    private Long validflag;

}
