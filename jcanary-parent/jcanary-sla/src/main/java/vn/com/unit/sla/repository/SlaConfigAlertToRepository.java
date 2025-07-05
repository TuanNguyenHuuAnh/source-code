/*******************************************************************************
 * Class        ：SlaConfigAlertToRepository
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：TrieuVD
 * Change log   ：2021/01/13：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.sla.dto.SlaConfigAlertToDto;
import vn.com.unit.sla.entity.SlaConfigAlertTo;

/**
 * <p>
 * SlaConfigAlertToRepository
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaConfigAlertToRepository extends DbRepository<SlaConfigAlertTo, Long> {

    /**
     * <p>
     * Get sla config alert to dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link SlaConfigAlertToDto}
     * @author TrieuVD
     */
    public SlaConfigAlertToDto getSlaConfigAlertToDtoById(@Param("id") Long id);

    /**
     * <p>
     * getListByConfigDetailId
     * </p>
     * .
     *
     * @param slaConfigDetailId
     *            type {@link Long}
     * @return {@link List<SlaConfigAlertToDto>}
     * @author TrieuVD
     */
    public List<SlaConfigAlertToDto> getListByConfigDetailId(@Param("slaConfigDetailId") Long slaConfigDetailId);
    
    /**
     * <p>
     * Delete by config detail id.
     * </p>
     *
     * @param slaConfigDetailId
     *            type {@link Long}
     * @author TrieuVD
     */
    @Modifying
    public void deleteByConfigDetailId(@Param("slaConfigDetailId") Long slaConfigDetailId);

    /**
     * <p>
     * Clone sla config alert tos by sla config detail id.
     * </p>
     *
     * @param oldSlaConfigDetailId
     *            type {@link Long}
     * @param newSlaConfigDetailId
     *            type {@link Long}
     * @param userId
     *            type {@link Long}
     * @param sysDate
     *            type {@link Date}
     * @author sonnd
     */
    @Modifying
    public void cloneSlaConfigAlertTosBySlaConfigDetailId(@Param("oldSlaConfigDetailId") Long oldSlaConfigDetailId, @Param("newSlaConfigDetailId") Long newSlaConfigDetailId, @Param("userId") Long userId, @Param("sysDate") Date sysDate);

}
