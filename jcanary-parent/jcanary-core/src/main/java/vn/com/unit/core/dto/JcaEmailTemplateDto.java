/*******************************************************************************
 * Class        ：JcaEmailTemplateDto
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：SonND
 * Change log   ：2020/12/23：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaEmailTemplateDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaEmailTemplateDto extends AbstractTracking {
    
    private Long id;
    private String name;
    private String code;
    private String templateContent;
    private String templateSubject;
    private Long companyId;
    private String companyName;
    private String createdBy;
    private String updatedBy;
    
//    private List<JcaEmailTemplateLangDto> jcaEmailTemplateLangDtos;
}
