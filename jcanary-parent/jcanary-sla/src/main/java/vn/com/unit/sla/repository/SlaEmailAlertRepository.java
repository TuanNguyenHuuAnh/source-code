/*******************************************************************************
 * Class        ：SlaEmailAlertRepository
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.sla.dto.SlaEmailAlertDto;
import vn.com.unit.sla.entity.SlaEmailAlert;

/**
 * <p>
 * SlaEmailAlertRepository
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface SlaEmailAlertRepository extends DbRepository<SlaEmailAlert, Long> {

    /**
     * <p>
     * Get sla email alert dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param id
     *            type {@link Long}
     * @return {@link SlaEmailAlertDto}
     */
    public SlaEmailAlertDto getSlaEmailAlertDtoById(@Param("id") Long id);

    /**
     * <p>
     * Get sla email alert dto list by condition.
     * </p>
     *
     * @author TrieuVD
     * @param fromDate
     *            type {@link Date}
     * @param toDate
     *            type {@link Date}
     * @param status
     *            type {@link String}
     * @return {@link List<SlaEmailAlertDto>}
     */
    public List<SlaEmailAlertDto> getSlaEmailAlertDtoListByCondition(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
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
