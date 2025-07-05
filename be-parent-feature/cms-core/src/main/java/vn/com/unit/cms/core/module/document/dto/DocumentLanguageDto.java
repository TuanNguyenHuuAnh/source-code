/*******************************************************************************
 * Class        ：DocumentLanguage
 * Created date ：2017/03/07
 * Lasted date  ：2017/03/07
 * Author       ：TaiTM
 * Change log   ：2017/03/07：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.document.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DocumentLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class DocumentLanguageDto {

    private Long id;
    private Long documentId;
    private String languageCode;
    private String title;
    private String subTitle;
    private String languageDispName;

    private String keyword;
    private String keywordDescription;
    private String linkAlias;
}
