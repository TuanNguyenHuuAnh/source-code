/*******************************************************************************
 * Class        ：JcaConfigMailService
 * Created date ：2021/01/29
 * Lasted date  ：2021/01/29
 * Author       ：TrieuVD
 * Change log   ：2021/01/29：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.Map;

/**
 * JcaConfigMailService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface JcaConfigMailService {
    
    /**
     * <p>
     * Get config mail.
     * </p>
     *
     * @param companyId
     *            type {@link Long}
     * @return {@link Map<String,String>}
     * @author TrieuVD
     */
    public Map<String, String> getConfigMailByCompanyId(Long companyId);
}
