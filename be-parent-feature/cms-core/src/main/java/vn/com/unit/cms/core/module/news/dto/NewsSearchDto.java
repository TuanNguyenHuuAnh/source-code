/*******************************************************************************
 * Class        ：NewsSearchDto
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：TaiTM
 * Change log   ：2017/02/23：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.news.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;

/**
 * NewsSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class NewsSearchDto extends CmsCommonSearchFilterDto {

    private static final long serialVersionUID = -3773026563987653740L;

    private String name;

    private String newsType;
    private String newsTypeName;
    private String newsTypeId;
    private String newsCategory;
    private String categoryName;
    private String categoryId;
    private String customerTypeName;
    private String homepage;
    private String event;
    private String postedDate;
    private String mNewsCategoryId;
    private String hot;
    private Long custTypeId;
}
