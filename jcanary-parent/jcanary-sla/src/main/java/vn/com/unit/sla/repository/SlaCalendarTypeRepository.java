/*******************************************************************************
 * Class        ：SlaCalendarTypeRepository
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：NganNH
 * Change log   ：2020/11/11：01-00 NganNH create a new
 *              :2020/11/12：01-00 NganNH create a new method findSlaCalendarTypeById            
 ******************************************************************************/
package vn.com.unit.sla.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.sla.dto.SlaCalendarTypeDto;
import vn.com.unit.sla.dto.SlaCalendarTypeSearchDto;
import vn.com.unit.sla.entity.SlaCalendarType;

/**
 * SlaAlertRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
public interface SlaCalendarTypeRepository extends DbRepository<SlaCalendarType, Long> {

    /**
     * <p>
     * Find calendar type by id.
     * </p>
     *
     * @param calendarTypeId
     *            type {@link Long}
     * @return {@link SlaCalendarTypeDto}
     * @author TrieuVD
     */
    public SlaCalendarTypeDto getCalendarTypeDtoById(@Param("id") Long calendarTypeId);

    /**
     * <p>
     * Count by search condition.
     * </p>
     *
     * @param slaCalendarTypeSearchDto
     *            type {@link SlaCalendarTypeSearchDto}
     * @return {@link int}
     * @author TrieuVD
     */
    public int countBySearchCondition(@Param("searchDto") SlaCalendarTypeSearchDto slaCalendarTypeSearchDto);

    /**
     * <p>
     * Get calendar type dto list by condition.
     * </p>
     *
     * @param slaCalendarTypeSearchDto
     *            type {@link SlaCalendarTypeSearchDto}
     * @param pageIndex
     *            type {@link int}
     * @param pageSize
     *            type {@link int}
     * @param isPaging
     *            type {@link boolean}
     * @return {@link List<SlaCalendarTypeDto>}
     * @author TrieuVD
     */
    public Page<SlaCalendarTypeDto> getCalendarTypeDtoListByCondition(@Param("searchDto") SlaCalendarTypeSearchDto slaCalendarTypeSearchDto, @Param("pageable") Pageable pageable);
    
    /**
     * <p>
     * Get calendar type dto by org id.
     * </p>
     *
     * @param orgId
     *            type {@link Long}
     * @return {@link SlaCalendarTypeDto}
     * @author TrieuVD
     */
    public SlaCalendarTypeDto getCalendarTypeDtoByOrgId(@Param("orgId") Long orgId);
}
