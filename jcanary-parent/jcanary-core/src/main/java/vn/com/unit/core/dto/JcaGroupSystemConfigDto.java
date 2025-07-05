/*******************************************************************************
 * Class        ：JcaGroupSystemConfigDto
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：ngannh
 * Change log   ：2020/12/15：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaGroupSystemConfigDto
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class JcaGroupSystemConfigDto  extends AbstractTracking  {

    private Long id;
    
    private String groupCode;

    private String groupName;

    private Long companyId;
}
