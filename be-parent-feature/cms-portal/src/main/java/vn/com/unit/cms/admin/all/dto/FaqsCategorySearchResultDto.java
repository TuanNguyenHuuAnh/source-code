/*******************************************************************************
 * Class        ：FaqsCategorySearchDto
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：TaiTM
 * Change log   ：2017/04/18：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

/**
 * FaqsCategorySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class FaqsCategorySearchResultDto extends CmsCommonSearchResultFilterDto {
    private String itemFunctionCode;
    private String itemFunctionName;
}
