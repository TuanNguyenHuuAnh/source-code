/*******************************************************************************
 * Class        ：JcaNotiTemplateRepository
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaNotiTemplateDto;
import vn.com.unit.core.dto.JcaNotiTemplateSearchDto;
import vn.com.unit.core.entity.JcaNotiTemplate;
import vn.com.unit.db.repository.DbRepository;

/**
 * <p>
 * JcaNotiTemplateRepository
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JcaNotiTemplateRepository extends DbRepository<JcaNotiTemplate, Long> {

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
    public JcaNotiTemplateDto getJcaNotiTemplateDtoById(@Param("templateId") Long templateId);
    
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
    public  int countJcaNotiTemplateDtoByCondition(@Param("searchDto") JcaNotiTemplateSearchDto jcaNotiTemplateSearchDto);
    
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
    public  Page<JcaNotiTemplateDto> getJcaNotiTemplateDtoListByCondition(@Param("searchDto") JcaNotiTemplateSearchDto jcaNotiTemplateSearchDto,  Pageable pageable);
    
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
    public JcaNotiTemplateDto getJcaNotiTemplateDtoByCodeAndCompnayId(@Param("code") String code, @Param("companyId") Long companyId);
}