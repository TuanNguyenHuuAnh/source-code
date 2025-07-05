/*******************************************************************************
 * Class        ：SlaAlertHistoryService
 * Created date ：2021/01/14
 * Lasted date  ：2021/01/14
 * Author       ：TrieuVD
 * Change log   ：2021/01/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.dto.SlaNotiAlertHistoryDto;
import vn.com.unit.sla.entity.SlaNotiAlertHistory;

/**
 * SlaAlertHistoryService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaNotiAlertHistoryService extends DbRepositoryService<SlaNotiAlertHistory, Long>{

    /**
     * <p>
     * Create the sla alert history dto.
     * </p>
     *
     * @param slaAlertHistoryDto
     *            type {@link SlaNotiAlertHistoryDto}
     * @return {@link SlaNotiAlertHistoryDto}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaNotiAlertHistoryDto createSlaAlertHistoryDto(SlaNotiAlertHistoryDto slaAlertHistoryDto) throws DetailException;
}
