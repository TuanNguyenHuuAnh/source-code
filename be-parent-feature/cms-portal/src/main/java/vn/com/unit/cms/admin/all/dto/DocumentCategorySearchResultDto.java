/*******************************************************************************
 * Class        ：DocumentCategorySearchDto
 * Created date ：2017/04/20
 * Lasted date  ：2017/04/20
 * Author       ：TaiTM
 * Change log   ：2017/04/20：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

/**
 * IntroductionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class DocumentCategorySearchResultDto extends CmsCommonSearchResultFilterDto {
    private String itemFunctionName;
}
