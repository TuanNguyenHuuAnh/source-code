/*******************************************************************************
 * Class        ：JcaNotiTemplateLangDto
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * JcaNotiTemplateLangDto
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
public class JcaNotiTemplateLangDto {
    
    private Long notiId;
    private Long langId;
    private String langCode;
    private String templateTitle;
    private String templateContent;
}
