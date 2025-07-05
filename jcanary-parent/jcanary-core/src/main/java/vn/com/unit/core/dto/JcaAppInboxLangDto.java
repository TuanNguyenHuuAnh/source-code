/*******************************************************************************
 * Class        ：JcaAppInboxLangDto
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：SonND
 * Change log   ：2021/02/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaAppInboxLangDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class JcaAppInboxLangDto extends AbstractTracking {
    private Long appInboxLangId;
    private Long appInboxId;
    private String title;
    private String description;
    private String langCode;
}
