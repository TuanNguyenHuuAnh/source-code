/*******************************************************************************
 * Class        ：NewsCategoryEdit
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：hand
 * Change log   ：2017/02/27：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

/**
 * NewsCategoryEdit
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Getter
@Setter
public class NewsCategoryEditDto extends CmsCommonEditDto {

    /** mNewsTypeId */
    private Long mNewsTypeId;

    private Long mNewsTypeIdDto;

    /** ProductTypeId */
    private Long customerTypeId;

    /** name */
    private String name;

    private boolean hasDisableCheckEnabled;

    /** categoryLanguageList */
    @Valid
    private List<NewsCategoryLanguageDto> categoryLanguageList;

    /** typeName */
    private String typeName;

    /** categoryJsonHidden */
    private String typeJsonHidden;

    /** customerTypeName */
    private String customerTypeName;

    /** linkAlias */
    private String linkAlias;

    /** sortOderList */
    private List<SortOrderDto> sortOderList;

    private String status;
    private String createdBy;
    private String approvedBy;
    private String publishedBy;
    private Long beforeId;
    private String comment;

    private int numbereNews;

    private String lang;
    private Integer indexLangActive;

    private String searchDto;

    private String pageType;

    private String categoryType;
    
    private String channel;
    
    private List<String> channelList;
}
