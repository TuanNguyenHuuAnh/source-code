/*******************************************************************************
 * Class        ：AppProccessAuthorityService
 * Created date ：2021/03/15
 * Lasted date  ：2021/03/15
 * Author       ：KhuongTH
 * Change log   ：2021/03/15：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.process.workflow.service;

import java.util.List;

import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.ep2p.admin.dto.JcaAuthorityListDto;

public interface AppProccessAuthorityService {

    /**
     * <p>
     * Gets the authority dtos by process deploy id and role id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the authority dtos by process deploy id and role id
     * @author KhuongTH
     */
    List<JcaAuthorityDto> getAuthorityDtosByProcessDeployIdAndRoleId(Long processDeployId, Long roleId);

    /**
     * <p>
     * Save authority process.
     * </p>
     *
     * @param jcaAuthorityListDto
     *            type {@link JcaAuthorityListDto}
     * @author KhuongTH
     */
    void saveAuthorityProcess(JcaAuthorityListDto jcaAuthorityListDto);

    /**
     * <p>
     * Gets the service authority by business id and role id.
     * </p>
     *
     * @param businessId
     *            type {@link Long}
     * @param roleId
     *            type {@link Long}
     * @return the service authority by business id and role id
     * @author KhuongTH
     */
    List<JcaAuthorityDto> getServiceAuthorityByBusinessIdAndRoleId(Long businessId, Long roleId);
}
