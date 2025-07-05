/*******************************************************************************
 * Class        ：DocumentCategoryLanguageDto
 * Created date ：2017/04/17
 * Lasted date  ：2017/04/17
 * Author       ：TaiTM
 * Change log   ：2017/04/17：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * 
 * DocumentCategoryLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class DocumentCategoryLanguageDto extends AbstractTracking {
    private Long id;
    private Long categoryId;
    private String languageCode;
    private String title;

    private String keywordsSeo;
    private String keywords;
    private String keywordsDesc;

}
