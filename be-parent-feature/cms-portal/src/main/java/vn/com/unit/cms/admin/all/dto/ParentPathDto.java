/*******************************************************************************
 * Class        ：JcaMenuDto
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：TaiTM
 * Change log   ：2020/12/09：01-00 TaiTM create a new
 ******************************************************************************/

package vn.com.unit.cms.admin.all.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaMenuDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Setter
@Getter
@NoArgsConstructor
public class ParentPathDto extends AbstractTracking {

    private Long ancestorId;
    private Long descendantId;
    private Long depth;
    private String type;
}
