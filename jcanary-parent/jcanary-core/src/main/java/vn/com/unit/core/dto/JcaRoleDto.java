/*******************************************************************************
 * Class        ：JcaRoleDto
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：NganNH
 * Change log   ：2020/12/02：01-00 NganNH create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaRoleDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaRoleDto extends AbstractTracking {
    
    private Long roleId;
    
    private String name;
    
    private String code;
    
    private String description;
    
    private Boolean active;
    
    private Long companyId;
    
    private String companyName;

}
