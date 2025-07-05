/*******************************************************************************
 * Class        RoleForPositionService
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/0801-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.core.dto.JcaPositionAuthorityDto;
import vn.com.unit.core.dto.JcaRoleForPositionDto;

/**
 * RoleForPositionService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface RoleForPositionService {

    /**
     * Get AuthorityDto List By RoleId
     * @param positionId
     *          type Long
     * @return List<RoleForPositionDto>
     * @author KhoaNA
     */
    public List<JcaRoleForPositionDto> getRoleForPositionDtoListByPositionId(Long positionId);
    
    /**
     * Save roleForPosition list to positionAuthorityDto
     * @param positionAuthorityDto
     *          type PositionAuthorityDto
     * @return
     * @author KhoaNA
     */
    public void savePositionAuthorityDto(JcaPositionAuthorityDto positionAuthorityDto);
}
