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
import vn.com.unit.sla.entity.SlaNotiAlertToHistory;

/**
 * SlaAlertService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaNotiAlertToHistoryService extends DbRepositoryService<SlaNotiAlertToHistory, Long> {
    
    /**
     * <p>
     * Creates the sla alert to history.
     * </p>
     *
     * @param slaAccountDto
     *            type {@link SlaReceiverDto}
     * @return {@link SlaNotiAlertToHistory}
     * @author TrieuVD
     */
    public SlaNotiAlertToHistory createSlaAlertToHistory(SlaReceiverDto slaAccountDto);
}
