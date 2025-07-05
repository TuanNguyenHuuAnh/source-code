/*******************************************************************************
 * Class        ：QrtzMJobTypeReq
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
 * Get deleted by.
 * </p>
 *
 * @author khadm
 * @return {@link String}
 */
@Getter

/**
 * <p>
 * Sets the deleted by.
 * </p>
 *
 * @author khadm
 * @param deletedBy
 *            the new deleted by
 */
@Setter
public class QrtzMJobTypeReq {
    
    /** The id. */
    private Long id;

    /** The type. */
    private String type;

    /** The code. */
    private String code;

    /** The official name. */
    private String officialName;

    /** The description. */
    private String description;

    /** The company id. */
    private Long companyId;

}
