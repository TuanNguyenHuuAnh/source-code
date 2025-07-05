/*******************************************************************************
 * Class        :EfoFormRepository
 * Created date :2019/04/17
 * Lasted date  :2019/04/17
 * Author       :NhanNV
 * Change log   :2019/04/17:01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.efo.dto.EfoFormAuthorityDto;
import vn.com.unit.ep2p.core.efo.entity.EfoFormAuthority;

/**
 * EfoFormRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
public interface EfoFormAuthorityRepository extends DbRepository<EfoFormAuthority, Long> {

    /**
     * <p>
     * Gets the efo form authority dtos by business id and role id.
     * </p>
     *
     * @param businessId
     *            type {@link Long}
     * @param roleId
     *            type {@link Long}
     * @return the efo form authority dtos by business id and role id
     * @author KhuongTH
     */
    List<EfoFormAuthorityDto> getEfoFormAuthorityDtosByBusinessIdAndRoleId(@Param("businessId") Long businessId,
            @Param("roleId") Long roleId);

    /**
     * <p>
     * Delete by business id and role id.
     * </p>
     *
     * @param businessId
     *            type {@link Long}
     * @param roleId
     *            type {@link Long}
     * @return {@link int}
     * @author KhuongTH
     */
    @Modifying
    int deleteByBusinessIdAndRoleId(@Param("businessId") Long businessId, @Param("roleId") Long roleId);
}