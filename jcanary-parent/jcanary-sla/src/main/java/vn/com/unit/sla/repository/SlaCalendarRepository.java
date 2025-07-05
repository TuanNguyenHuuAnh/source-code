/*******************************************************************************
 * Class        ：SlaCalendarRepository
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
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.dto.SlaCalendarSearchDto;
import vn.com.unit.sla.entity.SlaCalendar;

/**
 * <p>
 * SlaCalendarRepository
 * </p>
 * 
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface SlaCalendarRepository extends DbRepository<SlaCalendar, Long> {

    /**
     * <p>
     * Get sla calendar dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param id
     *            type {@link Long}
     * @return {@link SlaCalendarDto}
     */
    public SlaCalendarDto getSlaCalendarDtoById(@Param("id") Long id);
    
    /**
     * <p>
     * Get sla calendar dto by condition.
     * </p>
     *
     * @author TrieuVD
     * @param date
     *            type {@link Date}
     * @param calendarTypeId
     *            type {@link Long}
     * @param companyId
     *            type {@link Long}
     * @return {@link SlaCalendarDto}
     */
    public SlaCalendarDto getSlaCalendarDtoByCondition(@Param("date") Date date, @Param("calendarTypeId") Long calendarTypeId, @Param("companyId") Long companyId);
    
    /**
     * <p>
     * Get sla calendar dto list by search dto.
     * </p>
     *
     * @author TrieuVD
     * @param dayOffSearchDto
     *            type {@link SlaCalendarSearchDto}
     * @return {@link List<SlaCalendarDto>}
     */
    public List<SlaCalendarDto> getSlaCalendarDtoListBySearchDto(@Param("search") SlaCalendarSearchDto dayOffSearchDto);

    /**
     * deleteByCakendarTypeIdAndDate
     * @param date
     * @param calendarTypeId
     * @author ngannh
     */
    @Modifying
    public void deleteByCalendarTypeIdAndDate(@Param("calendarDate") Date date,@Param("calendarTypeId") Long calendarTypeId);

}
