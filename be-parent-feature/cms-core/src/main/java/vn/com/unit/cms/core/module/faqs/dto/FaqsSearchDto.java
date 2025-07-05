/*******************************************************************************
 * Class        ：FaqsSearchDto
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：TaiTM
 * Change log   ：2017/02/23：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.faqs.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;

/**
 * FaqsSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class FaqsSearchDto extends CmsCommonSearchFilterDto {
    private static final long serialVersionUID = -3626885651253147492L;

    private String categoryId;
    private String categoryName;

    private String createBy;

    private Integer stt;

    private String content;
}
