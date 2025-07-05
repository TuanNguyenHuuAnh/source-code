/*******************************************************************************
 * Class        ：JcaAuthoritySearchDto
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：ngannh
 * Change log   ：2020/12/24：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * JcaAuthoritySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class JcaAuthoritySearchDto {
    private Long roleId;
    private Long companyId;
    private List<String> functionTypes;
    
}
