/*******************************************************************************
 * Class        ：SlaCalendarTypeService
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.dto.SlaCalendarTypeDto;
import vn.com.unit.sla.dto.SlaCalendarTypeSearchDto;
import vn.com.unit.sla.entity.SlaCalendarType;

/**
 * <p>
 * SlaCalendarTypeService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaCalendarTypeService {
    
    static final String TABLE_ALIAS_SLA_CALENDAR_TYPE = "cal";
    
    /**
     * <p>
     * Get calendar type by id.
     * </p>
     *
     * @param calendarTypeId
     *            type {@link Long}
     * @return {@link SlaCalendarType}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaCalendarType getCalendarTypeById(Long calendarTypeId) throws DetailException;

    /**
     * <p>
     * Get calendar type dto by id.
     * </p>
     *
     * @param calendarTypeId
     *            type {@link Long}
     * @return {@link SlaCalendarTypeDto}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaCalendarTypeDto getCalendarTypeDtoById(Long calendarTypeId) throws DetailException;
    
    /**
     * <p>
     * Save sla calendar type.
     * </p>
     *
     * @param slaCalendarType
     *            type {@link SlaCalendarType}
     * @return {@link SlaCalendarType}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaCalendarType saveSlaCalendarType(SlaCalendarType slaCalendarType) throws DetailException;

    /**
     * <p>
     * Save sla calendar type dto.
     * </p>
     *
     * @param slaCalendarTypeDto
     *            type {@link SlaCalendarTypeDto}
     * @return {@link SlaCalendarType}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public SlaCalendarTypeDto saveSlaCalendarTypeDto(SlaCalendarTypeDto slaCalendarTypeDto) throws DetailException;

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
    public int countBySearchCondition(SlaCalendarTypeSearchDto slaCalendarTypeSearchDto);

    /**
     * <p>
     * Get calendar type dto list by condition.
     * </p>
     *
     * @param slaCalendarTypeSearchDto
     *            type {@link SlaCalendarTypeSearchDto}
     * @param offset
     *            type {@link int}
     * @param pageSize
     *            type {@link int}
     * @param isPaging
     *            type {@link boolean}
     * @return {@link List<SlaCalendarTypeDto>}
     * @author TrieuVD
     */
    public List<SlaCalendarTypeDto> getCalendarTypeDtoListByCondition(SlaCalendarTypeSearchDto slaCalendarTypeSearchDto, Pageable pageable);

    /**
     * <p>
     * Delete by id.
     * </p>
     *
     * @param calendarTypeId
     *            type {@link Long}
     * @return true, if successful
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public boolean deleteById(Long calendarTypeId) throws DetailException;
    
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
    public SlaCalendarTypeDto getCalendarTypeDtoByOrgId(Long orgId);
}
