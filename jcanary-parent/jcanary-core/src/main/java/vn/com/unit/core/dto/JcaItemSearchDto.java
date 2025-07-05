/*******************************************************************************
 * Class        ：ItemSearchDto
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：MinhNV
 * Change log   ：2020/12/07：01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractCompanyDto;

/**
 * ItemSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaItemSearchDto extends AbstractCompanyDto{

    private String functionCode;
    private String functionName;
    private String description;
    private Boolean displayFlag;
    private String lang;

    private Boolean actived;
    
    public Boolean getDisplayFlag() {
        return displayFlag;
    }
    
    public Boolean getActived() {
        return actived;
    }

  
}