/*******************************************************************************
 * Class        RoleEditDto
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.entity.JcaConstant;

/**
 * RoleEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Getter
@Setter
public class RoleEditDto extends JcaAbstractCompanyDto {

    /** id */
    private Long id;
    
    /** name */
    private String name;
    
    private String code;
    
    /** description */
    private String description;
    
    /** active */
    private boolean actived;
    
    private String url;
    
    private String roleType;
    
    private List<JcaConstant> listRoleType;
    
    
}
