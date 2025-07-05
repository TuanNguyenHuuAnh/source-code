/*******************************************************************************
 * Class        ：JcaPositionAuthorityDto
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：taitt
 * Change log   ：2021/01/25：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JcaPositionAuthorityDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JcaPositionAuthorityDto {
    /** positionId */
    private Long positionId;
    
    /** data */
    private List<JcaRoleForPositionDto> data;

}
