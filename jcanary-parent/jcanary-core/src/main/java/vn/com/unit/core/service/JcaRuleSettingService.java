/*******************************************************************************
 * Class        ：JcaRuleSettingService
 * Created date ：2020/11/12
 * Lasted date  ：2020/11/12
 * Author       ：KhoaNA
 * Change log   ：2020/11/12：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.core.dto.JcaRuleSettingDto;
import vn.com.unit.core.entity.JcaRuleSetting;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * JcaRuleSettingService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface JcaRuleSettingService extends DbRepositoryService<JcaRuleSetting, Long>{

    /**
     * save.
     *
     * @param entity
     *            the entity type JcaRuleSetting
     * @return the jca rule setting
     * @author tantm
     */
    JcaRuleSetting saveJcaRuleSetting(JcaRuleSetting entity);

    /**
     * <p>
     * Get list jca rule setting dto by business id.
     * </p>
     *
     * @param businessId
     *            type {@link long}
     * @return {@link List<JcaRuleSettingDto>}
     * @author tantm
     */
    List<JcaRuleSettingDto> getListJcaRuleSettingDtoByBusinessId(long businessId);

    /**
     * <p>
     * Delete jca rule setting by PK.
     * </p>
     *
     * @param businessId
     *            type {@link Long}
     * @param orgId
     *            type {@link Long}
     * @param positionId
     *            type {@link Long}
     * @author tantm
     */
    void deleteJcaRuleSettingByPK(Long businessId, Long orgId, Long positionId);


    /**
     * <p>
     * Save list jca rule setting dto.
     * </p>
     *
     * @param listRuleSettingDto
     *            type {@link List<JcaRuleSettingDto>}
     * @author tantm
     */
    void saveListJcaRuleSettingDto(List<JcaRuleSettingDto> listRuleSettingDto);

    /**
     * <p>
     * Validate org list.
     * </p>
     *
     * @param listRuleSettingDto
     *            type {@link List<JcaRuleSettingDto>}
     * @param locale
     *            type {@link Locale}
     * @return {@link String}
     * @author tantm
     */
    String validateOrgList(List<JcaRuleSettingDto> listRuleSettingDto, Locale locale);
}
