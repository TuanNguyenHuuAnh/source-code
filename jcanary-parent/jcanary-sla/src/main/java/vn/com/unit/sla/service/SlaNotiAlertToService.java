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
import vn.com.unit.sla.entity.SlaNotiAlertTo;

/**
 * SlaAlertService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaNotiAlertToService extends DbRepositoryService<SlaNotiAlertTo, Long> {
    
    /**
     * <p>
     * Get sla account dto list by alert id.
     * </p>
     *
     * @param alertId
     *            type {@link Long}
     * @param userType
     *            type {@link String}
     * @return {@link List<SlaAccountDto>}
     * @author TrieuVD
     */
    public List<SlaReceiverDto> getSlaAccountDtoListByAlertId(Long alertId, String userType);
    
    /**
     * <p>
     * Create the sla alert to.
     * </p>
     *
     * @param slaAccountDto
     *            type {@link SlaReceiverDto}
     * @return {@link SlaNotiAlertTo}
     * @author TrieuVD
     */
    public SlaNotiAlertTo createSlaAlertTo(SlaReceiverDto slaAccountDto);
    
    /**
     * <p>
     * Delete by alert id.
     * </p>
     *
     * @param alertId
     *            type {@link Long}
     * @author TrieuVD
     */
    public void deleteByAlertId(Long alertId);
}
