/*******************************************************************************
 * Class        ：NewsEditDto
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：hand
 * Change log   ：2017/02/23：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

/**
 * NewsEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Getter
@Setter
public class NewsEditDto extends CmsCommonEditDto {
    private Long typeId;

    private Long custTypeId;

    private Long categoryId;

    private String categoryName;

    private String name;

    private String note;

    @Valid
    List<NewsLanguageDto> newsLanguageList;

    private String typeName;

    private String typeJsonHidden;

    private String customerTypeName;

    /** sortOderList */
    private List<SortOrderDto> sortOderList;

    private String lang;

    private String languageCode;

    private String customerAlias;

    private boolean homepage;
    private boolean event;
    private boolean hot;
    
    private String checkHot;
    
    private String channel;
    
    private List<String> channelList;
}