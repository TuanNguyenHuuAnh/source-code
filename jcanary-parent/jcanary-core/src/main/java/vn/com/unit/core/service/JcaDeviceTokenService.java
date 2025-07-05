/*******************************************************************************
 * Class        ：JcaDeviceTokenService
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.dts.exception.DetailException;

/**
 * <p>
 * JcaDeviceTokenService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface JcaDeviceTokenService {
    
    /**
     * <p>
     * Get device token by account id.
     * </p>
     *
     * @author khadm
     * @param accountId
     *            type {@link Long}
     * @return {@link List<String>}
     * @throws DetailException
     *             the detail exception
     */
    public List<String> getDeviceTokenByAccountId(Long accountId) throws DetailException;
    
    
    /**
     * <p>
     * Get device token by list account id.
     * </p>
     *
     * @author khadm
     * @param accountIds
     *            type {@link List<Long>}
     * @return {@link List<String>}
     * @throws DetailException
     *             the detail exception
     */
    public List<String> getDeviceTokenByListAccountId(List<Long> accountIds) throws DetailException;

}
