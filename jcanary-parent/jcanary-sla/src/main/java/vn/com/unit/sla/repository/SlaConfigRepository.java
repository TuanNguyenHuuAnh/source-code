/*******************************************************************************
 * Class        ：SlaCalendarTypeRepository
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：NganNH
 * Change log   ：2020/11/11：01-00 NganNH create a new
 *              :2020/11/12：01-00 NganNH create a new method findSlaConfigById
 ******************************************************************************/
package vn.com.unit.sla.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.sla.dto.SlaConfigDto;
import vn.com.unit.sla.entity.SlaConfig;

/**
 * SlaAlertRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
public interface SlaConfigRepository extends DbRepository<SlaConfig, Long> {

    /**
     * <p>
     * Get sla config by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link SlaConfigDto}
     * @author TrieuVD
     */
    public SlaConfigDto getSlaConfigById(@Param("id") Long id);
    
    
    public Page<SlaConfigDto> getListConfig(Pageable pageable);
    
    public int countListConfig(); 

}
