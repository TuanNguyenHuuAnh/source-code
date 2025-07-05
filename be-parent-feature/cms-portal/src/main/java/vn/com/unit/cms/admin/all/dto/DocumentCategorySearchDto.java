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
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;

/**
 * IntroductionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class DocumentCategorySearchDto extends CmsCommonSearchFilterDto {

    private static final long serialVersionUID = 8067023347993547101L;

    private String name;
    private String note;
    private String parentName;
    private String typeName;
    private String description;
    private String itemFunctionName;
    private Long sortOrder;
    private String customerTypeIdText;
    private Long typeId;
    private Long parentId;
    private String forCandidate;
    private String categoryType;
    private String channel;
}
