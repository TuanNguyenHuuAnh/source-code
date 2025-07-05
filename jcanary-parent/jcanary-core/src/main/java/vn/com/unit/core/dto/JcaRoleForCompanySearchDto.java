/*******************************************************************************
 * Class        ：JcaRoleForCompanySearchDto
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：ngannh
 * Change log   ：2020/12/22：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JcaRoleForCompanySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaRoleForCompanySearchDto {
    private String keySearch;
    private Long companyId;
    private Long roleId;
}
