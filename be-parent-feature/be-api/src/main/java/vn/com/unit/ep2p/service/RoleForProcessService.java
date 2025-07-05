/*******************************************************************************
 * Class        ：RoleForProcessService
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：KhuongTH
 * Change log   ：2021/01/19：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.ep2p.dto.req.RoleForProcessAddListReq;
import vn.com.unit.ep2p.dto.res.RoleForProcessInfoRes;

/**
 * <p>
 * RoleForProcessService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface RoleForProcessService extends BaseRestService<ObjectDataRes<JcaAuthorityDto>, JcaAuthorityDto>{
    

    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @param roleForProcessAddReq
     *            type {@link RoleForProcessAddListReq}
     * @return {@link List<RoleForProcessInfoRes>}
     * @throws Exception
     *             the exception
     * @author KhuongTH
     */
    List<RoleForProcessInfoRes> create(RoleForProcessAddListReq roleForProcessAddReq) throws Exception;

    /**
     * <p>
     * Clone role for process.
     * </p>
     *
     * @param oldProcessDeployId
     *            type {@link Long}
     * @param newProcessDeployId
     *            type {@link Long}
     * @author KhuongTH
     */
    void cloneRoleForProcess(Long oldProcessDeployId, Long newProcessDeployId);
}
