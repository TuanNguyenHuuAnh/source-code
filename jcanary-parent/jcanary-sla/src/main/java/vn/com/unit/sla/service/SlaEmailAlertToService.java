/*******************************************************************************
 * Class        ：SlaAlertService
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import java.util.List;

import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.sla.dto.SlaReceiverDto;
import vn.com.unit.sla.entity.SlaEmailAlertTo;   

/**
 * <p>
 * SlaEmailAlertToService
 * </p>
 * 
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface SlaEmailAlertToService extends DbRepositoryService<SlaEmailAlertTo, Long> {
    
    /**
     * <p>
     * Get sla receiver dto list by alert id.
     * </p>
     *
     * @author TrieuVD
     * @param alertId
     *            type {@link Long}
     * @param userType
     *            type {@link String}
     * @return {@link List<SlaReceiverDto>}
     */
    public List<SlaReceiverDto> getSlaReceiverDtoListByAlertId(Long alertId, String userType);
    
    /**
     * <p>
     * Creates the sla email alert to.
     * </p>
     *
     * @author TrieuVD
     * @param slaAccountDto
     *            type {@link SlaReceiverDto}
     * @return {@link SlaEmailAlertTo}
     */
    public SlaEmailAlertTo createSlaEmailAlertTo(SlaReceiverDto slaAccountDto);
    
    /**
     * <p>
     * Delete by alert id.
     * </p>
     *
     * @author TrieuVD
     * @param alertId
     *            type {@link Long}
     */
    public void deleteByAlertId(Long alertId);
}
