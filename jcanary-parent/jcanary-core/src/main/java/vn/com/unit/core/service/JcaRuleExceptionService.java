/*******************************************************************************
 * Class        ：JcaRuleExceptionService
 * Created date ：2020/11/12
 * Lasted date  ：2020/11/12
 * Author       ：tantm
 * Change log   ：2020/11/12：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.core.dto.JcaRuleExceptionDto;
import vn.com.unit.core.entity.JcaRuleException;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * JcaRuleExceptionService.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface JcaRuleExceptionService extends DbRepositoryService<JcaRuleException, Long> {

    /**
     * save.
     *
     * @param entity
     *            the entity type JcaRuleException
     * @return the jca rule Exception
     * @author tantm
     */
    JcaRuleException saveJcaRuleException(JcaRuleException entity);

    /**
     * <p>
     * Get list jca rule Exception dto by business id.
     * </p>
     *
     * @param businessId
     *            type {@link long}
     * @return {@link List<JcaRuleExceptionDto>}
     * @author tantm
     */
    List<JcaRuleExceptionDto> getListJcaRuleExceptionDtoByBusinessId(long businessId);

    /**
     * <p>
     * Delete jca rule Exception by PK.
     * </p>
     *
     * @param businessId
     *            type {@link Long}
     * @param orgId
     *            type {@link Long}
     * @param accountId
     *            type {@link Long}
     * @author tantm
     */
    void deleteJcaRuleExceptionByPK(Long businessId, Long orgId, Long accountId);

    /**
     * <p>
     * Save list jca rule Exception dto.
     * </p>
     *
     * @param listRuleExceptionDto
     *            type {@link List<JcaRuleExceptionDto>}
     * @author tantm
     */
    void saveListJcaRuleExceptionDto(List<JcaRuleExceptionDto> listRuleExceptionDto);

    /**
     * <p>
     * Validate org list.
     * </p>
     *
     * @param listRuleExceptionDto
     *            type {@link List<JcaRuleExceptionDto>}
     * @param locale
     *            type {@link Locale}
     * @return {@link String}
     * @author tantm
     */
    String validateOrgList(List<JcaRuleExceptionDto> listRuleExceptionDto, Locale locale);
}
