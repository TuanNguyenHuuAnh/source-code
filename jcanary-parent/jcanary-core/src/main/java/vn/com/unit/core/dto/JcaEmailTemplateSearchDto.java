/*******************************************************************************
 * Class        ：JcaEmailTemplateSearchDto
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：SonND
 * Change log   ：2020/12/23：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JcaEmailTemplateSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaEmailTemplateSearchDto {
    private Long companyId;
    private String templateName;
    private String createdBy;
    private String code;
}
