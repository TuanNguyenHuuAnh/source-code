/*******************************************************************************
 * Class        :JcaRoleForTeamService
 * Created date :2020/12/22
 * Lasted date  :2020/12/22
 * Author       :SonND
 * Change log   :2020/12/22:01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleForTeamDto;
import vn.com.unit.core.dto.JcaRoleForTeamSearchDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaRoleForTeam;

/**
 * <p>
 * JcaRoleForTeamService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaRoleForTeamService {

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
    List<JcaRoleDto> getJcaRoleDto(JcaRoleForTeamSearchDto jcaRoleForTeamSearchDto);
   
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
    List<JcaTeamDto> getJcaTeamDto(JcaRoleForTeamSearchDto jcaRoleForTeamSearchDto);
   
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
    List<JcaRoleDto> getJcaRoleDtoByTeamId(Long teamId, Long companyId);
    
    /**
     * <p>
     * Save jca role for team.
     * </p>
     *
     * @param jcaRoleForTeam
     *            type {@link JcaRoleForTeam}
     * @return {@link JcaRoleForTeam}
     * @author sonnd
     */
    JcaRoleForTeam saveJcaRoleForTeam(JcaRoleForTeam jcaRoleForTeam);
   
    /**
     * <p>
     * Save jca role for team dto.
     * </p>
     *
     * @param jcaRoleForTeamDto
     *            type {@link JcaRoleForTeamDto}
     * @return {@link JcaRoleForTeam}
     * @author sonnd
     */
    JcaRoleForTeam saveJcaRoleForTeamDto(JcaRoleForTeamDto jcaRoleForTeamDto);
   
    /**
     * <p>
     * Gets the jca role for team by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the jca role for team by id
     * @author sonnd
     */
    JcaRoleForTeam getJcaRoleForTeamById(Long id);
    
    /**
     * <p>
     * Delete all role by team id.
     * </p>
     *
     * @param teamId
     *            type {@link Long}
     * @author sonnd
     */
    void deleteAllRoleByTeamId(Long teamId);
    
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
    List<JcaRoleForTeamDto> getJcaRoleForTeamDtoByTeamId(Long teamId);
	
}
