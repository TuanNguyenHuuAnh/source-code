/*******************************************************************************
 * Class        ：JcaRoleForItemDto
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：ngannh
 * Change log   ：2020/12/24：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaRoleForItemDto
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class JcaAuthorityDto extends AbstractTracking{

    private Long authorityId;
    private Long roleId;
    private Long itemId;
    private String accessFlg;
    
    // Dto for get role
    private String functionName;
    private String functionCode;
    private String functionType;
    
    
    // Other
    private Boolean canAccessFlag;
    private Boolean canDispFlg;
    private Boolean canEditFlg;
    private String authorityType;
    
    private String businessCode;
}
