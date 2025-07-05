/*******************************************************************************
 * Class        ：JcaOrganizationPathDto
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：SonND
 * Change log   ：2020/12/14：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractCreatedTracking;

/**
 * JcaOrganizationPathDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaOrganizationPathDto extends AbstractCreatedTracking {

    private int depth;
    private Long ancestorId;
    private Long descendantId;
}
