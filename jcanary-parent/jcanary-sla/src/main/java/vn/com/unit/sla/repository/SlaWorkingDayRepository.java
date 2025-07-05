/*******************************************************************************
 * Class        ：SlaWorkingDayRepository
 * Created date ：2021/03/10
 * Lasted date  ：2021/03/10
 * Author       ：ngannh
 * Change log   ：2021/03/10：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.sla.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.dto.SlaWorkingDayDto;
import vn.com.unit.sla.dto.SlaWorkingDaySearchDto;
import vn.com.unit.sla.entity.SlaWorkingDay;

/**
 * SlaWorkingDayRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public interface SlaWorkingDayRepository extends DbRepository<SlaWorkingDay, Long>  {

    /**
     * Delete by calendar type id and date.
     *
     * @param date the date
     * @param calendarTypeId the calendar type id
     */
    @Modifying
    public void deleteByCalendarTypeIdAndDate(@Param("workDay") Date date,@Param("calendarTypeId") Long calendarTypeId);

    /**
     * getSlaWorkingDayByCondition
     * @param date
     * @param calendarTypeId
     * @return
     * @author ngannh
     */
    public List<SlaWorkingDay> getSlaWorkingDayByCondition(@Param("workDay") Date date,@Param("calendarTypeId")  Long calendarTypeId);

    /**
     * getSlaWorkingDayDtoListBySearchDto
     * @param daySearchDto
     * @return
     * @author ngannh
     */
    public List<SlaCalendarDto> getSlaWorkingDayDtoListBySearchDto(@Param("search") SlaWorkingDaySearchDto daySearchDto);
}
