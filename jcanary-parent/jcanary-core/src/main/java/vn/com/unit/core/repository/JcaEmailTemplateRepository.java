/*******************************************************************************
 * Class        :JcaEmailTemplateRepository
 * Created date :2020/12/23
 * Lasted date  :2020/12/23
 * Author       :SonND
 * Change log   :2020/12/23:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.dto.JcaEmailTemplateSearchDto;
import vn.com.unit.core.dto.JcaGroupEmailDto;
import vn.com.unit.core.entity.JcaEmailTemplate;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaEmailTemplateRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaEmailTemplateRepository extends DbRepository<JcaEmailTemplate, Long> {

    /**
     * <p>
     * Count jca email template dto by condition.
     * </p>
     *
     * @param jcaEmailTemplateSearchDto
     *            type {@link JcaEmailTemplateSearchDto}
     * @return {@link int}
     * @author sonnd
     */
    int countJcaEmailTemplateDtoByCondition(@Param("jcaEmailTemplateSearchDto") JcaEmailTemplateSearchDto jcaEmailTemplateSearchDto);
    
    /**
     * <p>
     * Get jca email template dto by condition.
     * </p>
     *
     * @author TrieuVD
     * @param jcaEmailTemplateSearchDto
     *            type {@link JcaEmailTemplateSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Page<JcaEmailTemplateDto>}
     */
    Page<JcaEmailTemplateDto> getJcaEmailTemplateDtoByCondition(@Param("jcaEmailTemplateSearchDto") JcaEmailTemplateSearchDto jcaEmailTemplateSearchDto, Pageable pageable );
    
    /**
     * <p>
     * Gets the jca email template dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the jca email template dto by id
     * @author sonnd
     */
    JcaEmailTemplateDto getJcaEmailTemplateDtoById(@Param("id") Long id);

    JcaEmailTemplateDto findJcaEmailTemplateDtoByCode(@Param("code")String code);

    List<JcaGroupEmailDto> findGourpMailById(@Param("id") String id);
    
    JcaGroupEmailDto findUserNameActionByUser(@Param("userName") String userName);


}