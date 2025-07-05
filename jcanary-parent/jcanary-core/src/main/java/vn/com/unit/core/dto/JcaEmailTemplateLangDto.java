/*******************************************************************************
 * Class        ：JcaEmailTemplateLangDto
 * Created date ：2020/01/15
 * Lasted date  ：2020/01/15
 * Author       ：SonND
 * Change log   ：2020/01/15：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaEmailTemplateLangDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaEmailTemplateLangDto extends AbstractTracking {
    
    private Long id;
    private Long emailTemplateId;
    private String langCode;
    private String title;
    private String notification;
}
