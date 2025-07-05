/*******************************************************************************
* Class        JpmParamRepository
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       Tan Tai
* Change log   2020/11/25 01-00 Tan Tai create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmAuthorityDto;
import vn.com.unit.workflow.entity.JpmAuthority;

/**
 * <p>
 * JpmAuthorityRepository
 * </p>
 * .
 *
 * @author Tan Tai
 * @version 01-00
 * @since 01-00
 */

@Repository
public interface JpmAuthorityRepository extends DbRepository<JpmAuthority, Long> {

    /**
     * <p>
     * Get list jpm authority dto by permission ids.
     * </p>
     *
     * @author Tan Tai
     * @param permissionIds
     *            type {@link List<Long>}
     * @return {@link List<JpmAuthorityDto>}
     */
    List<JpmAuthorityDto> getListJpmAuthorityDtoByPermissionIds(@Param("permissionIds") List<Long> permissionIds);

    /**
     * <p>
     * Gets the jpm authority dtos by process deploy id and role id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @param roleId
     *            type {@link Long}
     * @return the jpm authority dtos by process deploy id and role id
     * @author KhuongTH
     */
    List<JpmAuthorityDto> getJpmAuthorityDtosByProcessDeployIdAndRoleId(@Param("processDeployId") Long processDeployId,
            @Param("roleId") Long roleId);

    /**
     * <p>
     * Delete by permissio deploy ids and role id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @param roleId
     *            type {@link Long}
     * @return {@link int}
     * @author KhuongTH
     */
    @Modifying
    int deleteByProcessDeployIdAndRoleId(@Param("processDeployId") Long processDeployId, @Param("roleId") Long roleId);

    /**
     * <p>
     * Clone role for process.
     * </p>
     *
     * @param oldProcessDeployId
     *            type {@link Long}
     * @param newProcessDeployId
     *            type {@link Long}
     * @param userIdLogin
     *            type {@link Long}
     * @param sysDate
     *            type {@link Date}
     * @author KhuongTH
     */
    @Modifying
    void cloneRoleForProcess(@Param("oldProcessDeployId") Long oldProcessDeployId,
            @Param("newProcessDeployId") Long newProcessDeployId,
            @Param("userIdLogin") Long userIdLogin, 
            @Param("sysDate") Date sysDate);

}