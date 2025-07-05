/*******************************************************************************
 * Class        ：JcaNotiTemplateService
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaNotiTemplateDto;
import vn.com.unit.core.dto.JcaNotiTemplateSearchDto;
import vn.com.unit.core.entity.JcaNotiTemplate;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * <p>
 * JcaNotiTemplateService
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JcaNotiTemplateService extends DbRepositoryService<JcaNotiTemplate, Long> {
    
    /** The Constant TABLE_ALIAS_JCA_NOTI_TEMPLATE. */
    static final String TABLE_ALIAS_JCA_NOTI_TEMPLATE= "noti_template";
    
    /**
     * <p>
     * Save jca noti template.
     * </p>
     *
     * @author TrieuVD
     * @param jcaNotiTemplate
     *            type {@link JcaNotiTemplate}
     * @return {@link JcaNotiTemplate}
     */
    public JcaNotiTemplate saveJcaNotiTemplate(JcaNotiTemplate jcaNotiTemplate);
    
    /**
     * <p>
     * Save jca noti template dto.
     * </p>
     *
     * @author TrieuVD
     * @param notiTemplateDto
     *            type {@link JcaNotiTemplateDto}
     * @return {@link JcaNotiTemplate}
     */
    public JcaNotiTemplate saveJcaNotiTemplateDto(JcaNotiTemplateDto notiTemplateDto);
    
    /**
     * <p>
     * Get jca noti template dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param templateId
     *            type {@link Long}
     * @return {@link JcaNotiTemplateDto}
     */
    public JcaNotiTemplateDto getJcaNotiTemplateDtoById(Long templateId);
    
    /**
     * <p>
     * Count jca noti template dto by condition.
     * </p>
     *
     * @author TrieuVD
     * @param jcaNotiTemplateSearchDto
     *            type {@link JcaNotiTemplateSearchDto}
     * @return {@link int}
     */
    public  int countJcaNotiTemplateDtoByCondition(JcaNotiTemplateSearchDto jcaNotiTemplateSearchDto);
    
    /**
     * <p>
     * Get jca noti template dto list by condition.
     * </p>
     *
     * @author TrieuVD
     * @param jcaNotiTemplateSearchDto
     *            type {@link JcaNotiTemplateSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JcaNotiTemplateDto>}
     */
    public  List<JcaNotiTemplateDto> getJcaNotiTemplateDtoListByCondition(JcaNotiTemplateSearchDto jcaNotiTemplateSearchDto,  Pageable pageable);
    
    /**
     * <p>
     * Delete jca noti template.
     * </p>
     *
     * @author TrieuVD
     * @param templateId
     *            type {@link Long}
     */
    public void deleteJcaNotiTemplate(Long templateId);
    
    /**
     * <p>
     * Delete jca noti template dto.
     * </p>
     *
     * @author TrieuVD
     * @param templateId
     *            type {@link Long}
     */
    public void deleteJcaNotiTemplateDto(Long templateId);
    
    /**
     * <p>
     * Get jca noti template dto by code and compnay id.
     * </p>
     *
     * @author TrieuVD
     * @param code
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link JcaNotiTemplateDto}
     */
    public JcaNotiTemplateDto getJcaNotiTemplateDtoByCodeAndCompnayId(String code, Long companyId);
}
