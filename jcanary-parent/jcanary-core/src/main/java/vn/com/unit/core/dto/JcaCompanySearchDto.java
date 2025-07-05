/*******************************************************************************
 * Class        ：CompanySearchDto
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：NganNH
 * Change log   ：2020/12/07：01-00 NganNH create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CompanySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */

@Setter
@Getter
@NoArgsConstructor
public class JcaCompanySearchDto {
    
    private String keySearch;
    
    private String name;
    
    private String description;
    
    private String systemCode;
    
    private String systemName;
}
