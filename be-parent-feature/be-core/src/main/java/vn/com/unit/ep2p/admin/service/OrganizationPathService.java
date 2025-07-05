/*******************************************************************************
 * Class        :OrganizationPathService
 * Created date :2020/12/14
 * Lasted date  :2020/12/14
 * Author       :SonND
 * Change log   :2020/12/14:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import vn.com.unit.core.dto.JcaOrganizationPathDto;

/**
 * <p>
 * OrganizationPathService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface OrganizationPathService {

    
    /**
     * <p>
     * Save.
     * </p>
     *
     * @param jcaOrganizationPathDto
     *            type {@link JcaOrganizationPathDto}
     * @return {@link JcaOrganizationPathDto}
     * @author SonND
     */
    public JcaOrganizationPathDto save(JcaOrganizationPathDto jcaOrganizationPathDto);

}
