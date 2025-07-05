/*******************************************************************************
 * Class        ：SlaCalendarService
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.dto.SlaCalendarAddDto;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.dto.SlaCalendarSearchDto;
import vn.com.unit.sla.entity.SlaCalendar;

/**
 * <p>
 * SlaCalendarService
 * </p>
 * 
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface SlaCalendarService {

    /**
     * <p>
     * Get sla calendar dto list by range and calendar type id.
     * </p>
     *
     * @author TrieuVD
     * @param startDate
     *            type {@link Date}
     * @param endDate
     *            type {@link Date}
     * @param calendarTypeId
     *            type {@link Long}
     * @return {@link List<SlaCalendarDto>}
     * @throws DetailException
     *             the detail exception
     */
    public List<SlaCalendarDto> getSlaCalendarDtoListByRangeAndCalendarTypeId(Date startDate, Date endDate, Long calendarTypeId) throws DetailException;

    /**
     * <p>
     * Get sla calendar dto list by search dto.
     * </p>
     *
     * @author TrieuVD
     * @param CalendarSearchDto
     *            type {@link SlaCalendarSearchDto}
     * @return {@link List<SlaCalendarDto>}
     * @throws Exception
     *             the exception
     */
    public List<SlaCalendarDto> getSlaCalendarDtoListBySearchDto(SlaCalendarSearchDto calendarSearchDto) throws Exception;

    /**
     * <p>
     * Get sla calendar dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param CalendarId
     *            type {@link Long}
     * @return {@link SlaCalendarDto}
     * @throws DetailException
     *             the detail exception
     */
    public SlaCalendarDto getSlaCalendarDtoById(Long CalendarId) throws DetailException;
    
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
     * @throws DetailException
     *             the detail exception
     */
    public SlaCalendarDto getSlaCalendarDtoByCondition(Date date, Long calendarTypeId, Long companyId) throws DetailException;

    /**
     * <p>
     * Save sla calendar.
     * </p>
     *
     * @author TrieuVD
     * @param slaCalendar
     *            type {@link SlaCalendar}
     * @return {@link SlaCalendar}
     * @throws Exception
     *             the exception
     */
    public SlaCalendar saveSlaCalendar(SlaCalendar slaCalendar) throws Exception;

    /**
     * <p>
     * Save sla calendar dto.
     * </p>
     *
     * @author TrieuVD
     * @param slaCalendarDto
     *            type {@link SlaCalendarDto}
     * @return {@link SlaCalendar}
     * @throws Exception
     *             the exception
     */
    public SlaCalendar saveSlaCalendarDto(SlaCalendarDto slaCalendarDto) throws Exception;

    /**
     * <p>
     * Delete by id.
     * </p>
     *
     * @author TrieuVD
     * @param CalendarId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     */
    public void deleteById(Long CalendarId) throws DetailException;


    /**
     * generateCalendarDtoWeekend
     * @param slaCalendarAddDto
     * @return
     * @throws Exception
     * @author ngannh
     */
    List<SlaCalendarDto> generateCalendarDtoWeekend(SlaCalendarAddDto slaCalendarAddDto) throws Exception;



    /**
     * deleteByCalendarTypeIdAndDate
     * @param date
     * @param calendarTypeId
     * @throws DetailException
     * @author ngannh
     */
    void deleteByCalendarTypeIdAndDate(Date date, Long calendarTypeId) throws DetailException;
    
    /**
     * <p>
     * Generate calendar weekend.
     * </p>
     *
     * @author TrieuVD
     * @param CalendarDtoList
     *            type {@link List<SlaCalendarDto>}
     * @param CalendarSearchDto
     *            type {@link SlaCalendarSearchDto}
     * @return {@link List<SlaCalendarDto>}
     * @throws Exception
     *             the exception
     */
//    public List<SlaCalendarDto> generateCalendarWeekend(List<SlaCalendarDto> calendarDtoList, SlaCalendarSearchDto calendarSearchDto) throws Exception;
}
