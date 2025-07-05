/*******************************************************************************
 * Class        ：JcaPositionPathDto
 * Created date ：2020/12/25
 * Lasted date  ：2020/12/25
 * Author       ：SonND
 * Change log   ：2020/12/25：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaPositionPathDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaPositionPathDto extends AbstractTracking {

    private Long id;
    private int depth;
    private Long ancestorId;
    private Long descendantId;
}
