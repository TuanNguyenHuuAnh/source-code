/*******************************************************************************
 * Class        ：JcaRoleForPositionDto
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：taitt
 * Change log   ：2021/01/25：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JcaRoleForPositionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JcaRoleForPositionDto {
    /** id */
    private Long id;
    
    /** positionId */
    public Long positionId;
    
    /** roleId */
    public Long roleId;
    
    /** roleName */
    public String roleName;
    
    /** checked */
    private Boolean checked = false;
}
