/*******************************************************************************
 * Class        ：JcaGroupConstantSearchDto
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：tantm
 * Change log   ：2020/12/01：01-00 tantm create a new
 ******************************************************************************/

package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * JcaGroupConstantSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Setter
@Getter
public class JcaGroupConstantSearchDto {
    private Long companyId;
    private String code;
    private String text;
}
