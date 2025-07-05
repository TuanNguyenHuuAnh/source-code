/*******************************************************************************
 * Class        ：SlaWorkingDayService
 * Created date ：2021/03/10
 * Lasted date  ：2021/03/10
 * Author       ：ngannh
 * Change log   ：2021/03/10：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.dto.SlaCalendarSearchDto;
import vn.com.unit.sla.dto.SlaWorkingDayDto;
import vn.com.unit.sla.dto.SlaWorkingDaySearchDto;
import vn.com.unit.sla.entity.SlaCalendar;
import vn.com.unit.sla.entity.SlaWorkingDay;

// TODO: Auto-generated Javadoc
/**
 * SlaWorkingDayService.
 *
 * @author ngannh
 * @version 01-00
 * @since 01-00
 */
public interface SlaWorkingDayService {
    

    /**
     * Delete by calendar type id and date.
     *
     * @param date the date
     * @param calendarTypeId the calendar type id
     * @throws DetailException the detail exception
     */
    void deleteByCalendarTypeIdAndDate(Date date, Long calendarTypeId) throws DetailException;
    
    /**
     * Save sla working day.
     *
     * @param slaWorkingDay the sla working day
     * @return the sla working day
     * @throws Exception the exception
     */
    public SlaWorkingDay saveSlaWorkingDay(SlaWorkingDay slaWorkingDay) throws Exception;

    /**
     * getSlaWorkingDayByCondition
     * @param date
     * @param calendarTypeId
     * @return
     * @throws DetailException
     * @author ngannh
     */
    List<SlaWorkingDay> getSlaWorkingDayByCondition(Date date, Long calendarTypeId) throws DetailException;

    public List<SlaCalendarDto> getSlaCalendarDtoListBySearchDto(SlaWorkingDaySearchDto daySearchDto) throws Exception;

}
