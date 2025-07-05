/*******************************************************************************
 * Class        ：JcaRoleSearchDto
 * Created date ：2020/12/10
 * Lasted date  ：2020/12/10
 * Author       ：taitt
 * Change log   ：2020/12/10：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractCompanyDto;

/**
 * JcaRoleSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class JcaRoleSearchDto extends AbstractCompanyDto{
    private Long companyId;
    private Boolean active;
    private String code;
    private String name;
    private String description;
}
