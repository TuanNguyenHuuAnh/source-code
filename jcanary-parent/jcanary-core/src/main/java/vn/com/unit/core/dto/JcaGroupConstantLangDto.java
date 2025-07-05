/*******************************************************************************
 * Class        :JcaGroupConstantLanguageDto
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :tantm
 * Change log   :2020/12/01:01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractCompanyDto;

/**
 * 
 * JcaGroupConstantLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JcaGroupConstantLangDto extends AbstractCompanyDto {

    private Long groupId;

    private Long langId;
    
    private String langCode;

    private String text;

}