/*******************************************************************************
 * Class        ：FaqsSearchDto
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：TaiTM
 * Change log   ：2017/02/23：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.faqs.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

/**
 * FaqsSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class FaqsSearchResultDto extends CmsCommonSearchResultFilterDto {
    private String categoryName;

    private Long customerId;

    private String token;

    private Integer stt;

    private String enableFaqs;
    
    private Long categoryId;
    
    private String note;
    
    private Long sort;
    
    private Date postedDate;
    
    private Date expirationDate;
    
    private Long docId;
    
    private String content;
    
    private String contentString;
    
    private String shortContent;
    
    private String keyword;
    
    private String keywordDescription;
    
    private String linkAlias;
    
    private String itemFunctionCode;
}
