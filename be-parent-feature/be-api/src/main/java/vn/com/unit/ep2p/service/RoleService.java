/*******************************************************************************
 * Class        ：RoleService
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：ngannh
 * Change log   ：2020/12/02：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.RoleAddReq;
import vn.com.unit.ep2p.dto.req.RoleUpdateReq;
import vn.com.unit.ep2p.dto.res.RoleInfoRes;

/**
 * RoleService.
 *
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public interface RoleService extends BaseRestService<ObjectDataRes<JcaRoleDto>, JcaRoleDto> {

    /**
     * <p>
     * Count RoleDto by condition
     * </p>
     * .
     *
     * @param jcaRoleSearchDto
     *            type {@link JcaRoleSearchDto}
     * @return {@link int}
     * @author taitt
     */
    public int countRoleDtoByCondition(JcaRoleSearchDto jcaRoleSearchDto);

    /**
     * <p>
     * Get RoleDto By Condition
     * </p>
     * .
     *
     * @param jcaRoleSearchDto
     *            type {@link JcaRoleSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JcaRoleDto>}
     * @author taitt
     */
    public List<JcaRoleDto> getRoleDtoByCondition(JcaRoleSearchDto jcaRoleSearchDto,Pageable pageable);

   
    /**
     * <p>
     * Get RoleDto By Id
     * <p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return {@link RoleInfoRes}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    public RoleInfoRes getItemInfoById(Long id) throws Exception;

    /**
     * update.
     *
     * @param roleUpdateReq
     *            type {@link RoleUpdateReq}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    public void update(RoleUpdateReq roleUpdateReq) throws Exception;

    /**
     * create.
     *
     * @param roleAddReq
     *            type {@link RoleAddReq}
     * @return {@link RoleInfoRes}
     * @throws Exception
     *             the exception
     * @author taitt
     */
    public RoleInfoRes create(RoleAddReq roleAddReq) throws Exception;

    /**
     * <p>
     * Get list enum search.
     * </p>
     *
     * @return {@link List<EnumsParamSearchRes>}
     * @author SonND
     */
    public List<EnumsParamSearchRes> getListEnumSearch();
    
}
