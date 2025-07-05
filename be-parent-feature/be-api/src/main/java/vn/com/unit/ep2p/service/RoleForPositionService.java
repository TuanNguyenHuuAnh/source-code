/*******************************************************************************
 * Class        ：RoleForProcessService
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：KhuongTH
 * Change log   ：2021/01/19：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.dto.JcaPositionAuthorityDto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.RoleForPositionAddInforReq;
import vn.com.unit.ep2p.dto.req.RoleForPositionAddListReq;
import vn.com.unit.ep2p.dto.res.RoleForPositionInfoRes;

/**
 * <p>
 * RoleForPositionService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface RoleForPositionService{
    
    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @param roleForPositionAddInforReq
     *            type {@link RoleForPositionAddInforReq}
     * @throws Exception
     *             the exception
     * @author SonND
     */
    void create(RoleForPositionAddInforReq roleForPositionAddInforReq) throws DetailException;

    /**
     * <p>
     * Save.
     * </p>
     *
     * @param jcaPositionAuthorityDto
     *            type {@link JcaPositionAuthorityDto}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    void save(JcaPositionAuthorityDto jcaPositionAuthorityDto) throws DetailException;

    /**
     * <p>
     * Detail.
     * </p>
     *
     * @param positionId
     *            type {@link Long}
     * @param companyId
     *            type {@link Long}
     * @return {@link RoleForPositionInfoRes}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    RoleForPositionInfoRes detail(Long positionId, Long companyId) throws DetailException;
    
    
    /**
     * <p>
     * Creates the role for position.
     * </p>
     *
     * @param roleForPositionAddListReq
     *            type {@link RoleForPositionAddListReq}
     * @author SonND
     */
    void createRoleForPosition(RoleForPositionAddListReq roleForPositionAddListReq) throws DetailException;

}
