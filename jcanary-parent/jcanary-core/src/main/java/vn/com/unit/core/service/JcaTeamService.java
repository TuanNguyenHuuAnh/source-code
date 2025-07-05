/*******************************************************************************
 * Class        JcaTeamService
 * Created date 2020/12/08
 * Lasted date  2020/12/08
 * Author       MinhNV
 * Change log   2020/12/08 01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.dto.JcaTeamSearchDto;
import vn.com.unit.core.entity.JcaItem;
import vn.com.unit.core.entity.JcaTeam;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * JcaTeamService.
 *
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
public interface JcaTeamService extends DbRepositoryService<JcaTeam, Long>{
    
    /** The Constant TABLE_ALIAS_JCA_TEAM. */
    public static final String TABLE_ALIAS_JCA_TEAM ="team";


    /**
     * <p>
     * Count jca team dto by condition.
     * </p>
     *
     * @param teamSearchDto
     *            type {@link JcaTeamSearchDto}
     * @return {@link int}
     * @author sonnd
     */
    public int countJcaTeamDtoByCondition(JcaTeamSearchDto teamSearchDto);

 
    /**
     * <p>
     * Gets the jca team dto by condition.
     * </p>
     *
     * @param teamSearchDto
     *            type {@link JcaTeamSearchDto}
     * @param pagable
     *            type {@link Pageable}
     * @return the jca team dto by condition
     * @author sonnd
     */
    public List<JcaTeamDto> getJcaTeamDtoByCondition(JcaTeamSearchDto teamSearchDto, Pageable pagable);

    /**
     * <p>
     * get team by id
     * </p>
     * .
     *
     * @param id
     *            id of item
     * @return JcaTeam
     * @author MinhNV
     */
    public JcaTeam getTeamById(Long id);

    /**
     * <p>
     * Save JcaTeam with create, update
     * </p>
     * .
     *
     * @param jcaTeam
     *            type {@link JcaTeam}
     * @return JcaTeam
     * @author MinhNV
     */
    public JcaTeam saveJcaTeam(JcaTeam jcaTeam);

    /**
     * <p>
     * Save JcaTeam with JcaTeamDto
     * </p>
     * .
     *
     * @param jcaTeamDto
     *            type {@link JcaTeamDto}
     * @return JcaTeam
     * @author MinhNV
     */
    public JcaTeam saveJcaTeamDto(JcaTeamDto jcaTeamDto);

    /**
     * <p>
     * Get team information detail by id
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return JcaTeamDto
     * @author MinhNV
     */
    public JcaTeamDto getJcaTeamDtoById(Long id);
    
    /**
     * <p>
     * Delete jca team by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @author sonnd
     */
    public void deleteJcaTeamById(Long id);

    /**
     * <p>
     * Count jca team dto by name.
     * </p>
     *
     * @param name
     *            type {@link String}
     * @param teamId
     *            type {@link Long}
     * @return {@link int}
     * @author sonnd
     */
    public int countJcaTeamDtoByName(String name, Long teamId);

    /**
     * <p>
     * Count jca team dto by code.
     * </p>
     *
     * @param code
     *            type {@link String}
     * @return {@link int}
     * @author sonnd
     */
    public int countJcaTeamDtoByCode(String code);
}
