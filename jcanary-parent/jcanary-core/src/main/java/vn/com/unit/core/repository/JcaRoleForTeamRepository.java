/*******************************************************************************
 * Class        :JcaRoleForGroupRepository
 * Created date :2020/12/22
 * Lasted date  :2020/12/22
 * Author       :SonND
 * Change log   :2020/12/2:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleForTeamDto;
import vn.com.unit.core.dto.JcaRoleForTeamSearchDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaRoleForTeam;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaRoleForGroupRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaRoleForTeamRepository extends DbRepository<JcaRoleForTeam, Long> {


    /**
     * <p>
     * Gets the jca role for group dto by condition.
     * </p>
     *
     * @return the jca role for group dto by condition
     * @author SonND
     */
    List<JcaRoleForTeamDto> getJcaRoleForGroupDto();

    
    /**
     * <p>
     * Gets the jca role for group dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the jca role for group dto by id
     * @author SonND
     */
    JcaRoleForTeamDto getJcaRoleForGroupDtoById(@Param("id") Long id);

    /**
     * <p>
     * Get jca role dto.
     * </p>
     *
     * @param jcaRoleForTeamSearchDto
     *            type {@link JcaRoleForTeamSearchDto}
     * @return {@link List<JcaRoleDto>}
     * @author SonND
     */
    List<JcaRoleDto> getJcaRoleDto(@Param("jcaRoleForTeamSearchDto")JcaRoleForTeamSearchDto jcaRoleForTeamSearchDto);

    /**
     * <p>
     * Get jca team dto.
     * </p>
     *
     * @param jcaRoleForTeamSearchDto
     *            type {@link JcaRoleForTeamSearchDto}
     * @return {@link List<JcaTeamDto>}
     * @author SonND
     */
    List<JcaTeamDto> getJcaTeamDto(@Param("jcaRoleForTeamSearchDto")JcaRoleForTeamSearchDto jcaRoleForTeamSearchDto);

    /**
     * <p>
     * Get jca role dto by team id.
     * </p>
     *
     * @param teamId
     *            type {@link Long}
     * @param companyId
     *            type {@link Long}
     * @return {@link List<JcaRoleDto>}
     * @author SonND
     */
    List<JcaRoleDto> getJcaRoleDtoByTeamId(@Param("teamId")Long teamId, @Param("companyId") Long companyId);
    
    /**
     * <p>
     * Delete all role by team id.
     * </p>
     *
     * @param teamId
     *            type {@link Long}
     * @author SonND
     */
    @Modifying
    void deleteAllRoleByTeamId(@Param("teamId")Long teamId);

    /**
     * <p>
     * Gets the jca role for team dto by team id.
     * </p>
     *
     * @param teamId
     *            type {@link Long}
     * @return the jca role for team dto by team id
     * @author sonnd
     */
    List<JcaRoleForTeamDto> getJcaRoleForTeamDtoByTeamId(@Param("teamId")Long teamId);

}