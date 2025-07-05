/*******************************************************************************
 * Class        ：SlaTemplateDto
 * Created date ：2021/01/14
 * Lasted date  ：2021/01/14
 * Author       ：TrieuVD
 * Change log   ：2021/01/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.noti.dto;

import java.util.List;

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
public class SlaNotiTemplateDto {

    private Long templateId;
    private String notiTitle;
    private String notiContent;
    private List<SlaNotiTemplateLangDto> notiTemplateLangDtoList;
}
