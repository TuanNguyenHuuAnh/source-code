/*******************************************************************************
 * Class        ：SlaAlertRepository
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：NganNH
 * Change log   ：2020/11/11：01-00 NganNH create a new
 *              :2020/11/12：01-00 NganNH create a new method findSlaAlertById            
 ******************************************************************************/
package vn.com.unit.sla.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.sla.dto.SlaReceiverDto;
import vn.com.unit.sla.entity.SlaEmailAlertTo;

/**
 * SlaAlertRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
public interface SlaEmailAlertToRepository extends DbRepository<SlaEmailAlertTo, Long> {
    
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
    public List<SlaReceiverDto> getSlaReceiverDtoListByAlertId(@Param("alertId") Long alertId, @Param("userType") String userType);
    
    /**
     * <p>
     * Delete by alert id.
     * </p>
     *
     * @param alertId
     *            type {@link Long}
     * @author TrieuVD
     */
    @Modifying
    public void deleteByAlertId(@Param("alertId") Long alertId);
}
