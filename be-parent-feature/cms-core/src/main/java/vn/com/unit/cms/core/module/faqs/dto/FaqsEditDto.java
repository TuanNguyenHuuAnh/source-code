/*******************************************************************************
 * Class        ：FaqsEditDto
 * Created date ：2017/03/19
 * Lasted date  ：2017/03/19
 * Author       ：TaiTM
 * Change log   ：2017/03/19：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.faqs.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;

/**
 * FaqsEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class FaqsEditDto extends CmsCommonEditDto {

    private Long cateFaqsId;

    private String categoryName;

    private String linkAlias;

    private String buttonAction;

    private String customerAlias;

    /** Status name */
    private String statusName;

    /** faqsLanguageList */
    List<FaqsLanguageDto> faqsLanguageList;

    private Long customerId;

    private String lang;
}