/*******************************************************************************
 * Class        ：JcaRuleExceptionRepository
 * Created date ：2020/11/13
 * Lasted date  ：2020/11/13
 * Author       ：KhoaNA
 * Change log   ：2020/11/13：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaRuleExceptionDto;
import vn.com.unit.core.entity.JcaRuleException;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaRuleExceptionRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface JcaRuleExceptionRepository extends DbRepository<JcaRuleException, Long> {

    /**
     * <p>
     * Find one by PK.
     * </p>
     *
     * @param businessId
     *            type {@link Long}
     * @param orgId
     *            type {@link Long}
     * @param accountId
     *            type {@link Long}
     * @return {@link JcaRuleException}
     * @author tantm
     */
    JcaRuleException getJcaRuleExceptionByPK(@Param("businessId") Long businessId, @Param("orgId") Long orgId,
            @Param("accountId") Long accountId);

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
    List<JcaRuleExceptionDto> getListJcaRuleExceptionDtoByBusinessId(@Param("businessId") long businessId);

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
    @Modifying
    void deleteJcaRuleExceptionByPK(@Param("businessId") Long businessId, @Param("orgId") Long orgId, @Param("accountId") Long accountId);

}