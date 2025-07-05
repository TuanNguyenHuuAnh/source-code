/*******************************************************************************
 * Class        TeamService
 * Created date 2020/12/08
 * Lasted date  2020/12/08
 * Author       MinhNV
 * Change log   2020/12/08 01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.dto.JcaTeamSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.TeamAddReq;
import vn.com.unit.ep2p.dto.req.TeamUpdateReq;
import vn.com.unit.ep2p.dto.res.TeamInfoRes;

/**
 * TeamService.
 *
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
public interface TeamService extends BaseRestService<ObjectDataRes<JcaTeamDto>, JcaTeamDto> {

    /**
     * <p>
     * count team by condition
     * </p>
     * .
     *
     * @param jcaTeamSearchDto
     *            type {@link JcaTeamSearchDto}
     * @return int
     * @author MinhNV
     */
    int countJcaTeamDtoByCondition(JcaTeamSearchDto jcaTeamSearchDto);

    /**
     * get list team by condition with pagination.
     *
     * @param jcaAccountSearchDto
     *            type {@link JcaTeamSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return List<JcaTeamDto>
     * @author MinhNV
     */
    List<JcaTeamDto> getJcaTeamDtoByCondition(JcaTeamSearchDto jcaAccountSearchDto,Pageable pageable);

    /**
     * <p>
     * create team
     * </p>
     * .
     *
     * @param reqTeamAddDto
     *            type {@link TeamAddReq}
     * @return TeamInfoRes
     * @throws Exception
     *             the exception
     * @author MinhNV
     */
    TeamInfoRes create(TeamAddReq reqTeamAddDto) throws Exception;

    /**
     * <p>
     * update team
     * </p>
     * .
     *
     * @param reqTeamUpdateDto
     *            type {@link TeamUpdateReq}
     * @throws Exception
     *             the exception
     * @author MinhNV
     */
    void update(TeamUpdateReq reqTeamUpdateDto) throws Exception;


    /**
     * <p>
     * get info an Team
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return TeamInfoRes
     * @throws Exception
     *             the exception
     * @author MinhNV
     */
    TeamInfoRes getTeamInfoById(Long id) throws Exception;

    /**
     * <p>
     * Get list enum search.
     * </p>
     *
     * @return {@link List<EnumsParamSearchRes>}
     * @author SonND
     */
    List<EnumsParamSearchRes> getListEnumSearch();
}
