/*******************************************************************************
 * Class        ：SlaCalendarTypeRepository
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：NganNH
 * Change log   ：2020/11/11：01-00 NganNH create a new
 *              :2020/11/12：01-00 NganNH create a new method findSlaConfigDetailById
 ******************************************************************************/
package vn.com.unit.sla.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.sla.dto.SlaConfigDetailDto;
import vn.com.unit.sla.dto.SlaConfigDetailSearchDto;
import vn.com.unit.sla.entity.SlaConfigDetail;

/**
 * SlaAlertRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
public interface SlaConfigDetailRepository extends DbRepository<SlaConfigDetail, Long> {

    /**
     * <p>
     * Get sla config detail dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link SlaConfigDetailDto}
     * @author TrieuVD
     */
    public SlaConfigDetailDto getSlaConfigDetailDtoById(@Param("id") Long id);

    /**
     * <p>
     * Get sla config detail dto list by condition.
     * </p>
     *
     * @param searchDto
     *            type {@link SlaConfigDetailSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Page<SlaConfigDetailDto>}
     * @author TrieuVD
     */
    public Page<SlaConfigDetailDto> getSlaConfigDetailDtoListByCondition(@Param("searchDto") SlaConfigDetailSearchDto searchDto,
            Pageable pageable);

    /**
     * <p>
     * Count by condition.
     * </p>
     *
     * @param searchDto
     *            type {@link SlaConfigDetailSearchDto}
     * @return {@link int}
     * @author TrieuVD
     */
    public int countByCondition(@Param("searchDto") SlaConfigDetailSearchDto searchDto);

    /**
     * <p>
     * Gets the sla config detail dto by sla config id.
     * </p>
     *
     * @param slaConfigId
     *            type {@link Long}
     * @return the sla config detail dto by sla config id
     * @author sonnd
     */
    public List<SlaConfigDetailDto> getSlaConfigDetailDtoBySlaConfigId(@Param("slaConfigId") Long slaConfigId);

    /**
     * <p>
     * Find all by config id.
     * </p>
     *
     * @param slaConfigId
     *            type {@link Long}
     * @return {@link List<SlaConfigDetailDto>}
     * @author TrieuVD
     */
    public List<SlaConfigDetailDto> findAllByConfigId(@Param("slaConfigId") Long slaConfigId);

    /**
     * <p>
     * Clone sla config details by sla config id.
     * </p>
     *
     * @param oldSlaConfigId
     *            type {@link Long}
     * @param newSlaConfigId
     *            type {@link Long}
     * @param userId
     *            type {@link Long}
     * @param sysDate
     *            type {@link Date}
     * @author sonnd
     */
    @Modifying
    public void cloneSlaConfigDetailsBySlaConfigId(@Param("oldSlaConfigId") Long oldSlaConfigId,
            @Param("newSlaConfigId") Long newSlaConfigId, @Param("userId") Long userId, @Param("sysDate") Date sysDate);
    
    @Modifying
    public void deleteBySlaConfigId(@Param("slaConfigId") Long slaConfigId);

}
