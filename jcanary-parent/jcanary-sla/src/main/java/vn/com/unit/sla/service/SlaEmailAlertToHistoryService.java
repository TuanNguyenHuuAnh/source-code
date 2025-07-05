/*******************************************************************************
 * Class        ：SlaAlertService
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.sla.dto.SlaReceiverDto;
import vn.com.unit.sla.entity.SlaEmailAlertToHistory;

/**
 * SlaAlertService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaEmailAlertToHistoryService extends DbRepositoryService<SlaEmailAlertToHistory, Long> {
    
    /**
     * <p>
     * Creates the sla email alert to history.
     * </p>
     *
     * @author TrieuVD
     * @param slaAccountDto
     *            type {@link SlaReceiverDto}
     * @return {@link SlaEmailAlertToHistory}
     */
    public SlaEmailAlertToHistory createSlaEmailAlertToHistory(SlaReceiverDto slaReceiverDto);
}
