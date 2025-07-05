/*******************************************************************************
 * Class        PositionAuthorityRepository
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaRoleForPositionDto;
import vn.com.unit.core.repository.JcaRoleForPositionRepository;

/**
 * PositionAuthorityRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNAs
 */
public interface RoleForPositionRepository extends JcaRoleForPositionRepository {

    /**
     * Find roleForPositionDto list by positionId
     * 
     * @param positionId
     *          type Long
     * @return List<RoleForPositionDto>
     * @author KhoaNA
     */
    List<JcaRoleForPositionDto> findRoleForPositionDtoListByPositionId(@Param("positionId") Long positionId, @Param("companyId") Long companyId);
    
    /**
     * Delete roleForPosition by ids
     * 
     * @param ids
     *          type List<Long>
     * @return
     * @author KhoaNA
     */
    @Modifying
    void deleteRoleForPositionByIds(@Param("ids") List<Long> ids, @Param("userNameLogin") Long userNameLogin, @Param("systemDate") Date systemDate);

    @Modifying
    public void deleteByPositionIdAndRoleId(@Param("positionId") Long positionId,@Param("roleId") Long roleId);
    
    JcaRoleForPositionDto findByPositionIdAndRoleId(@Param("positionId") Long positionId, @Param("roleId") Long roleId);
}
