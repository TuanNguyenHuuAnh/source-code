/*******************************************************************************
 * Class        RoleCustomizableDto
 * Created date 2018/01/31
 * Lasted date  2018/01/31
 * Author       Phucdq
 * Change log   2018/01/3101-00 Phucdq create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * RoleCustomizableDto
 * 
 * @version 01-00
 * @since 01-00
 * @author Phucdq
 */
@Getter
@Setter
public class RoleCustomizableDto extends AbstractCompanyDto {

    private Long roleId;
    private String roleCode;
    private String roleName;
    private Long menuId;
    private Integer displayOrder;
    private String defaultLink;
    private boolean actived;

    
}
