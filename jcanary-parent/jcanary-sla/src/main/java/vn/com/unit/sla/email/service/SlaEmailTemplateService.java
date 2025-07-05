/*******************************************************************************
 * Class        ：SlaTemplateService
 * Created date ：2021/01/14
 * Lasted date  ：2021/01/14
 * Author       ：TrieuVD
 * Change log   ：2021/01/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.email.service;

import java.util.Map;

import vn.com.unit.sla.email.dto.SlaEmailTemplateDto;
import vn.com.unit.sla.noti.dto.SlaNotiTemplateDto;

/**
 * SlaTemplateService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaEmailTemplateService {

    /**
     * <p>
     * Get sla email template dto by template id.
     * </p>
     *
     * @author TrieuVD
     * @param templateId
     *            type {@link Long}
     * @param mapData
     *            type {@link Map<String,String>}
     * @return {@link SlaNotiTemplateDto}
     */
    public SlaEmailTemplateDto getSlaEmailTemplateDtoByTemplateId(Long templateId, Map<String, Object> mapData);

    String replaceParam(String content, Map<String, Object> mapData);

}
