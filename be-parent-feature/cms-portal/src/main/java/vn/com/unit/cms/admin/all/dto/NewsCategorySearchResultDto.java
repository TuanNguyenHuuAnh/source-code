/*******************************************************************************
 * Class        ：NewsCategoryLanguageSearchDto
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：TaiTM
 * Change log   ：2017/02/28：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

/**
 * NewsCategoryLanguageSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class NewsCategorySearchResultDto extends CmsCommonSearchResultFilterDto {

    /** id */
    private Long id;
    
    /** label */
    private String label;

    /** sort */
    private Long sort;
    
    /** typeName */
    private String typeName;
    
    /** customerTypeName */
    private String customerTypeName;

    private String status;
    
    private int numberNews;
    private Long statusDelete;
    private Date postedDate;
}
