/*******************************************************************************
 * Class        ：JcaRoleForPositionRepository
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：taitt
 * Change log   ：2021/01/25：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleForPositionDto;
import vn.com.unit.core.entity.JcaRoleForPosition;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaRoleForPositionRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface JcaRoleForPositionRepository extends DbRepository<JcaRoleForPosition, Long> {

    /**
     * <p>
     * Delete jca role for position by ids.
     * </p>
     *
     * @param ids
     *            type {@link List<Long>}
     * @param userDeleteId
     *            type {@link Long}
     * @param systemDate
     *            type {@link Date}
     * @author taitt
     */
    @Modifying
    void deleteJcaRoleForPositionByIds(@Param("ids") List<Long> ids, @Param("userDeleteId") Long userDeleteId, @Param("systemDate") Date systemDate);
    
    
    /**
     * <p>
     * Get jca role for position dto list by position id.
     * </p>
     *
     * @param positionId
     *            type {@link Long}
     * @param companyId
     *            type {@link Long}
     * @return {@link List<JcaRoleForPositionDto>}
     * @author taitt
     */
    List<JcaRoleForPositionDto> getJcaRoleForPositionDtoListByPositionId(@Param("positionId") Long positionId, @Param("companyId") Long companyId);

    /**
     * <p>
     * Get jca role dto by position id and company id.
     * </p>
     *
     * @param positionId
     *            type {@link Long}
     * @param companyId
     *            type {@link Long}
     * @return {@link List<JcaRoleDto>}
     * @author SonND
     */
    List<JcaRoleDto> getJcaRoleDtoByPositionIdAndCompanyId(@Param("positionId") Long positionId, @Param("companyId") Long companyId);
}
