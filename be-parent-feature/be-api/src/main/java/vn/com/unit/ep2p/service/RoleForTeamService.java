/*******************************************************************************
 * Class        ：RoleForTeamService
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：SonND
 * Change log   ：2020/12/22：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import org.springframework.util.MultiValueMap;

import vn.com.unit.core.dto.JcaRoleForTeamDto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.req.dto.RoleForTeamInfoListReq;
import vn.com.unit.ep2p.core.req.dto.RoleForTeamInfoReq;
import vn.com.unit.ep2p.dto.res.RoleForTeamInfoRes;
import vn.com.unit.ep2p.dto.res.RoleForTeamListInfoRes;

/**
 * RoleForTeamService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
/**
 * @author sonnd
 *
 */
public interface RoleForTeamService {

    /**
     * <p>
     * Get list role for team dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link RoleForTeamListInfoRes}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    RoleForTeamListInfoRes getListRoleForTeamDto(MultiValueMap<String, String> commonSearch) throws DetailException;

    /**
     * <p>
     * Save role for team.
     * </p>
     *
     * @param roleForTeamInfoReq
     *            type {@link RoleForTeamInfoReq}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    void saveRoleForTeam(RoleForTeamInfoReq roleForTeamInfoReq) throws DetailException;
    
    /**
     * <p>
     * Save list role for team.
     * </p>
     *
     * @param roleForTeamInfoListReq
     *            type {@link RoleForTeamInfoListReq}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    void saveListRoleForTeam(RoleForTeamInfoListReq roleForTeamInfoListReq) throws DetailException;

    /**
     * <p>
     * Save.
     * </p>
     *
     * @param jcaRoleForTeamDto
     *            type {@link JcaRoleForTeamDto}
     * @return {@link JcaRoleForTeamDto}
     * @author SonND
     */
    public JcaRoleForTeamDto save(JcaRoleForTeamDto jcaRoleForTeamDto);
   
    /**
     * <p>
     * Get role for team info res by team id.
     * </p>
     *
     * @param teamId
     *            type {@link Long}
     * @param companyId
     *            type {@link Long}
     * @return {@link RoleForTeamInfoRes}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    public RoleForTeamInfoRes getRoleForTeamInfoResByTeamId(Long teamId, Long companyId) throws DetailException;
    
    /**
     * <p>
     * Delete.
     * </p>
     *
     * @param teamId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    public void delete(Long teamId) throws DetailException;

}
