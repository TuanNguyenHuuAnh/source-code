/*******************************************************************************
 * Class        ：DocumentSearchDto
 * Created date ：2017/02/22
 * Lasted date  ：2017/02/22
 * Author       ：TaiTM
 * Change log   ：2017/02/22：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.document.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

/**
 * DocumentSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class DocumentSearchResultDto extends CmsCommonSearchResultFilterDto {

    private String categoryName;
    private Date postedDate;
    private Date expirationDate;
    private String itemFunctionCode;
}
