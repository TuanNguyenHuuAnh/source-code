/*******************************************************************************
 * Class        ：RoleForCompanyService
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：ngannh
 * Change log   ：2020/12/22：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.RoleForCompanyAddListReq;
import vn.com.unit.ep2p.dto.req.RoleForCompanyAddReq;
import vn.com.unit.ep2p.dto.res.RoleForCompanyInfoRes;

/**
 * RoleForCompanyService.
 *
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public interface RoleForCompanyService {

    /**
     * <p>
     * Get role for company info res by id.
     * </p>
     *
     * @param roleId
     *            type {@link Long}
     * @param companyId
     *            type {@link Long}
     * @return {@link RoleForCompanyInfoRes}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    public RoleForCompanyInfoRes getRoleForCompanyInfoResById(Long roleId, Long companyId) throws DetailException;

    /**
     * <p>
     * Save list role for list.
     * </p>
     *
     * @param roleForCompanyAddListReq
     *            type {@link RoleForCompanyAddListReq}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    public void saveListRoleForList(RoleForCompanyAddListReq roleForCompanyAddListReq) throws DetailException;
    
    /**
     * <p>
     * Save role for company.
     * </p>
     *
     * @param roleForCompanyAddReq
     *            type {@link RoleForCompanyAddReq}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    public void saveRoleForCompany(RoleForCompanyAddReq roleForCompanyAddReq) throws DetailException;
}
