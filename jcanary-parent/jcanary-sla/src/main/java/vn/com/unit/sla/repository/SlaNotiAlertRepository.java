/*******************************************************************************
 * Class        ：SlaAlertRepository
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：NganNH
 * Change log   ：2020/11/11：01-00 NganNH create a new
 *              :2020/11/12：01-00 NganNH create a new method findSlaAlertById            
 ******************************************************************************/
package vn.com.unit.sla.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.sla.dto.SlaNotiAlertDto;
import vn.com.unit.sla.entity.SlaNotiAlert;

/**
 * SlaAlertRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
public interface SlaNotiAlertRepository extends DbRepository<SlaNotiAlert, Long> {

    /**
     * <p>
     * Get sla noti alert dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param id
     *            type {@link Long}
     * @return {@link SlaNotiAlertDto}
     */
    public SlaNotiAlertDto getSlaNotiAlertDtoById(@Param("id") Long id);

    /**
     * <p>
     * Get sla noti alert dto list by condition.
     * </p>
     *
     * @author TrieuVD
     * @param fromDate
     *            type {@link Date}
     * @param toDate
     *            type {@link Date}
     * @param status
     *            type {@link String}
     * @return {@link List<SlaNotiAlertDto>}
     */
    public List<SlaNotiAlertDto> getSlaNotiAlertDtoListByCondition(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("status") Integer status);

    /**
     * <p>
     * Update status by id list.
     * </p>
     *
     * @author TrieuVD
     * @param status
     *            type {@link String}
     * @param idList
     *            type {@link List<Long>}
     */
    @Modifying
    public void updateStatusByIdList(@Param("status") Integer status, @Param("idList") List<Long> idList);
}
