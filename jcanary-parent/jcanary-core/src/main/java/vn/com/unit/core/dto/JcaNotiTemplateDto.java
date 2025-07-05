/*******************************************************************************
 * Class        ：JcaNotiTemplateDto
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * JcaNotiTemplateDto
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
public class JcaNotiTemplateDto {
    
    private Long id;
    private String name;
    private String code;
    private String description;
    private Long companyId;
    private String companyName;
    
    private List<JcaNotiTemplateLangDto> notiTemplateLangDtoList;
}
