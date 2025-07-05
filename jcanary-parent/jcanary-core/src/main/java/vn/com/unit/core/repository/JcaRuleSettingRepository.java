/*******************************************************************************
 * Class        ：JcaRuleSettingRepository
 * Created date ：2020/11/13
 * Lasted date  ：2020/11/13
 * Author       ：KhoaNA
 * Change log   ：2020/11/13：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaRuleSettingDto;
import vn.com.unit.core.entity.JcaRuleSetting;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaRuleSettingRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface JcaRuleSettingRepository extends DbRepository<JcaRuleSetting, Long> {

    /**
     * <p>
     * Find one by PK.
     * </p>
     *
     * @param businessId
     *            type {@link Long}
     * @param orgId
     *            type {@link Long}
     * @param positionId
     *            type {@link Long}
     * @return {@link JcaRuleSetting}
     * @author tantm
     */
    JcaRuleSetting getJcaRuleSettingByPK(@Param("businessId") Long businessId, @Param("orgId") Long orgId,
            @Param("positionId") Long positionId);

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
    List<JcaRuleSettingDto> getListJcaRuleSettingDtoByBusinessId(@Param("businessId") long businessId);

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
    @Modifying
    void deleteJcaRuleSettingByPK(@Param("businessId") Long businessId, @Param("orgId") Long orgId, @Param("positionId") Long positionId);

}