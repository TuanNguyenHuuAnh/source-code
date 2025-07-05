/*******************************************************************************
 * Class        ：JcaAuthorityProcessSearchDto
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：KhuongTH
 * Change log   ：2021/01/19：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * <p> JcaAuthorityProcessSearchDto </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class JcaAuthorityProcessSearchDto extends JcaAuthoritySearchDto {
    private Long processDeployId;
}
