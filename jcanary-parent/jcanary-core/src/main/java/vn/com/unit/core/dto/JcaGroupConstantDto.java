/*******************************************************************************
 * Class        :JcaGroupConstantDto
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :tantm
 * Change log   :2020/12/01:01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractCompanyDto;

/**
 * 
 * JcaGroupConstantDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */

@Getter
@Setter
public class JcaGroupConstantDto extends AbstractCompanyDto {

    private Long id;

    private String code;

    private Long displayOrder;

    private String languageCode;

    private String text;

    private Long languageId;
    
    private Long constantId;
    
    private String constantCode;
    
    private Long constantDisplayOrder;
    
    private String constantLangCode;
    
    private String constantText;
    
    private Long constantLanguageId;

    List<JcaGroupConstantLangDto> languages;

    List<JcaConstantDto> constants;
    
    private String nameEn;
    private String nameVi;
    private String langCodeEn = "EN";
    private String langCodeVi = "VI";
    private Long langIdEn;
    private Long langIdVi;
    private String langCode;
    private Long langId;
    private Integer actived;
    

    public JcaGroupConstantDto() {
        this.languages = new ArrayList<JcaGroupConstantLangDto>();
        this.constants = new ArrayList<JcaConstantDto>();
    }

}