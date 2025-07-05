/*******************************************************************************
 * Class        ：JcaConfigParamsDto
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：ngannh
 * Change log   ：2020/12/15：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractAuditTracking;

/**
 * JcaConfigParamsDto
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class JcaSystemConfigDto  extends AbstractAuditTracking{

    private String settingKey;

    private String settingValue;

    private Long companyId;

    private String groupCode;
    
}
