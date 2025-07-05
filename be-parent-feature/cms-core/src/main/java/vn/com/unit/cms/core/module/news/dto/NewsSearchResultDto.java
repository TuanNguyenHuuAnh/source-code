/*******************************************************************************
 * Class        ：NewsLanguageSearchDto
 * Created date ：2017/03/03
 * Lasted date  ：2017/03/03
 * Author       ：hand
 * Change log   ：2017/03/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.news.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

/**
 * NewsLanguageSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Getter
@Setter
public class NewsSearchResultDto extends CmsCommonSearchResultFilterDto {
    private String newsType;

    private String newsCategory;

    private String customerTypeName;

    private int homepage;
    private int event;

    private Date postedDate;
    private Date expirationDate;

    private Integer stt;

    private byte[] content;
    private String contentString;
    private String shortContent;
    private String label;
    private String linkAlias;
    private Long newsCategoryId;
    private String parentLinkAlias;
    private String imageUrl;
    private String physicalImgUrl;
    private Integer hot;
    private String keyWord;
    private int sort;
    private String codeNewsType;
}
