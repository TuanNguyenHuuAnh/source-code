/*******************************************************************************
 * Class        ：SlaAlertRepository
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：NganNH
 * Change log   ：2020/11/11：01-00 NganNH create a new
 *              :2020/11/12：01-00 NganNH create a new method findSlaAlertById            
 ******************************************************************************/
package vn.com.unit.sla.repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.sla.entity.SlaNotiAlertToHistory;

/**
 * SlaAlertRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
public interface SlaNotiAlertToHistoryRepository extends DbRepository<SlaNotiAlertToHistory, Long> {
    
}
