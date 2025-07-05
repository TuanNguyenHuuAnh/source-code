/*******************************************************************************
 * Class        ：SlaDeviceTokenService
 * Created date ：2021/01/21
 * Lasted date  ：2021/01/21
 * Author       ：khadm
 * Change log   ：2021/01/21：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.sla.noti.service;

import java.util.List;

/**
 * <p>
 * SlaDeviceTokenService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface SlaDeviceTokenService {
    
    /**
     * <p>
     * Get list token by list account id.
     * </p>
     *
     * @author khadm
     * @param accountIds
     *            type {@link List<Long>}
     * @return {@link List<String>}
     */
    public List<String> getListTokenByListReceiverIdList(List<Long> receiverIdList);

}
