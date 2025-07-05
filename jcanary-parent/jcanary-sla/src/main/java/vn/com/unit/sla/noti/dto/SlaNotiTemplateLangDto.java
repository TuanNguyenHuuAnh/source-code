/*******************************************************************************
 * Class        ：SlaTemplateDto
 * Created date ：2021/01/14
 * Lasted date  ：2021/01/14
 * Author       ：TrieuVD
 * Change log   ：2021/01/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.noti.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * SlaTemplateDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class SlaNotiTemplateLangDto {

    private Long templateId;
    private Long langId;
    private Long langCode;
    private String notiTitle;
    private String notiContent;
}
