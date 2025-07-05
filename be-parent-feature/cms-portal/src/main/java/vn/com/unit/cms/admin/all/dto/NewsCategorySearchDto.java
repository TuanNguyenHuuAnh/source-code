/*******************************************************************************
 * Class        ：NewsCategorySearchDto
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：hand
 * Change log   ：2017/02/27：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;

//import vn.com.unit.util.Util;

/**
 * NewsCategorySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Getter
@Setter
public class NewsCategorySearchDto extends CmsCommonSearchFilterDto {
    private static final long serialVersionUID = 5614641939728298249L;
    private String label;
    private String newsTypeId;
    private String newsTypeName;

    private String customerTypeName;

    private String typeName;

    private String statusActive;
    private Long statusDelete;

    private String categoryType;
}
