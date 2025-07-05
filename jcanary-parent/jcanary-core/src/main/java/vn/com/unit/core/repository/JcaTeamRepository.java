/*******************************************************************************
 * Class        :JcaTeamRepository
 * Created date :2020/12/08
 * Lasted date  :2020/12/08
 * Author       :MinhNV
 * Change log   :2020/12/08:01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.dto.JcaTeamSearchDto;
import vn.com.unit.core.entity.JcaTeam;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaTeamRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
public interface JcaTeamRepository extends DbRepository<JcaTeam, Long> {

    /**
     * <p>
     * count all team by condition
     * </p>
     * .
     *
     * @param jcaTeamSearchDto
     *            type {@link JcaTeamSearchDto}
     * @return int
     * @author MinhNV
     */
    int countJcaTeamDtoByCondition(@Param("jcaTeamSearchDto") JcaTeamSearchDto jcaTeamSearchDto);

    /**
     * <p>
     * get list JcaTeamDto by condition
     * </p>
     * .
     *
     * @param jcaTeamSearchDto
     *            type {@link JcaTeamSearchDto}
     * @param pagable
     *            type {@link Pageable}
     * @return List of JcaTeamDto
     * @author MinhNV
     */
    List<JcaTeamDto> getJcaTeamDtoByCondition(@Param("jcaTeamSearchDto") JcaTeamSearchDto jcaTeamSearchDto, Pageable pagable);

    /**
     * <p>
     * get JcaTeam by id
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return JcaTeam
     * @author MinhNV
     */
    JcaTeamDto getJcaTeamDtoById(@Param("id") Long id);

    /**
     * <p>
     * get list JcaTeam
     * </p>
     * .
     *
     * @return List<JcaTeam>
     * @author MinhNV
     */
    List<JcaTeam> getTeamDtoList();

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
    int countJcaTeamDtoByName(@Param("name")String name,@Param("teamId")Long teamId);

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
    int countJcaTeamDtoByCode(@Param("code")String code);
}
