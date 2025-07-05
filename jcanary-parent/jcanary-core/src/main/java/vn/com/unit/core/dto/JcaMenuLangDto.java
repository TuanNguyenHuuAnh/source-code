/*******************************************************************************
 * Class        ：JcaMenuLanguageDto
 * Created date ：2021/02/24
 * Lasted date  ：2021/02/24
 * Author       ：taitt
 * Change log   ：2021/02/24：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JcaMenuLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JcaMenuLangDto {
    
    private Long menuId;
    private Long langId;
    private String langCode;
    private String name;
    private String nameAbv;
}
