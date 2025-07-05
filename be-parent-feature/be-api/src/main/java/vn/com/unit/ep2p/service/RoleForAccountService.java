/*******************************************************************************
 * Class        ：RoleForAccountService
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：SonND
 * Change log   ：2020/12/22：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.dto.JcaRoleForAccountDto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.req.dto.RoleForAccountInfoReq;
import vn.com.unit.ep2p.dto.res.RoleForAccountListInfoRes;

/**
 * RoleForAccountService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
/**
 * @author sonnd
 *
 */
public interface RoleForAccountService {

    /**
     * <p>
     * Gets the list role for account dto.
     * </p>
     *
     * @param accountId
     *            type {@link Long}
     * @return the list role for account dto
     * @throws DetailException
     *             the detail exception
     * @author sonnd
     */
    RoleForAccountListInfoRes getListRoleForAccountDto(Long accountId) throws DetailException;

    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @param roleForAccountInfoReq
     *            type {@link RoleForAccountInfoReq}
     * @return {@link RoleForAccountListInfoRes}
     * @throws DetailException
     *             the detail exception
     * @author sonnd
     */
    RoleForAccountListInfoRes create(RoleForAccountInfoReq roleForAccountInfoReq) throws DetailException;

    /**
     * <p>
     * Save.
     * </p>
     *
     * @param jcaRoleForAccountDto
     *            type {@link JcaRoleForAccountDto}
     * @return {@link JcaRoleForAccountDto}
     * @author sonnd
     */
    public JcaRoleForAccountDto save(JcaRoleForAccountDto jcaRoleForAccountDto);
   
    /**
     * <p>
     * Delete.
     * </p>
     *
     * @param roleForAccountId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author sonnd
     */
    public void delete(Long roleForAccountId) throws DetailException;

}
