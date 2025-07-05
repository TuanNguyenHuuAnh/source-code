/*******************************************************************************
 * Class        ：FaqsLanguageDto
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：TaiTM
 * Change log   ：2017/02/27：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.emulate.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * FaqsLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class EmulateLanguageDto {

    private Long id;

    private String languageCode;

    private String title;

    private String shortContent;

    private byte[] contentByte;

    private String content;

    private String linkAlias;

    private String keyword;

    private String keywordDescription;

    private Date createDate;
    private String createBy;

    private Date updateDate;
    private String updateBy;

    private Date deleteDate;
    private String deleteBy;

    private String physicalImgUrl;

    private String imgUrl;

}
