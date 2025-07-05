/*******************************************************************************
 * Class        ：DocumentSearchDto
 * Created date ：2017/02/22
 * Lasted date  ：2017/02/22
 * Author       ：TaiTM
 * Change log   ：2017/02/22：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.document.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;

/**
 * DocumentSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class DocumentSearchDto extends CmsCommonSearchFilterDto {

    private static final long serialVersionUID = -398403483751316866L;

    private Long categoryId;
    private String categoryName;
    private String channel;
}
