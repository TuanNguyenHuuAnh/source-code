/*******************************************************************************
 * Class        ：JcaRoleAddDto
 * Created date ：2021/02/23
 * Lasted date  ：2021/02/23
 * Author       ：khadm
 * Change log   ：2021/02/23：01-00 khadm create a new
******************************************************************************/

package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.JcaConstantDto;

/**
 * <p>
 * Get list role type.
 * </p>
 *
 * @author khadm
 * @return {@link List<ConstantDisplay>}
 */
@Getter

/**
 * <p>
 * Sets the list role type.
 * </p>
 *
 * @author khadm
 * @param listRoleType
 *            the new list role type
 */
@Setter
public class JcaRoleAddDto extends JcaAbstractCompanyDto {

    /** The id. */
    private Long id;
    
    /** name. */
    @Size(min=1,max=255)
    private String name;
    
    /** The code. */
    @Size(min=1,max=30)
    private String code;
    
    /** description. */
    private String description;
    
    /** active. */
    private boolean active;
    
    /** The url. */
    private String url;
    
    /** The role type. */
    private String roleType;
    
    /** The list role type. */
    private List<JcaConstantDto> listRoleType;

    
}
