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
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;

/**
 * FaqsCategorySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class FaqsCategorySearchDto extends CmsCommonSearchFilterDto {
    private static final long serialVersionUID = 3549391213669680043L;

    private Long faqsCategoryParentId;

    private String itemFunctionName;

    private String itemFunctionCode;

    private String code;

    private String title;

    private String enabled;

    private  String createDate;

    private String createBy;

    private String updateDate;

    private String updateBy;

}
