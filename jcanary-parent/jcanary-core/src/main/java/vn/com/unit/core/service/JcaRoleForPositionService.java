/*******************************************************************************
 * Class        ：JcaRoleForPositionService
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：taitt
 * Change log   ：2021/01/25：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.JcaPositionAuthorityDto;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleForPositionDto;
import vn.com.unit.core.entity.JcaRoleForPosition;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * JcaRoleForPositionService.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface JcaRoleForPositionService extends DbRepositoryService<JcaRoleForPosition, Long>{

    /**
     * saveJcaPositionAuthorityDto.
     *
     * @param jcaPositionAuthorityDto
     *            type {@link JcaPositionAuthorityDto}
     * @author taitt
     */
    void saveJcaPositionAuthorityDto(JcaPositionAuthorityDto jcaPositionAuthorityDto);

    /**
     * saveJcaRoleForPosition.
     *
     * @param jcaRoleForPosition
     *            type {@link JcaRoleForPosition}
     * @return {@link JcaRoleForPosition}
     * @author taitt
     */
    JcaRoleForPosition saveJcaRoleForPosition(JcaRoleForPosition jcaRoleForPosition);

    /**
     * getJcaRoleForPositionDtoListByPositionId.
     *
     * @param positionId
     *            type {@link Long}
     * @param companyId
     *            type {@link Long}
     * @return {@link List<JcaRoleForPositionDto>}
     * @author taitt
     */
    List<JcaRoleForPositionDto> getJcaRoleForPositionDtoListByPositionId(Long positionId, Long companyId);

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
    List<JcaRoleDto> getJcaRoleDtoByPositionIdAndCompanyId(Long positionId, Long companyId);
    

}
