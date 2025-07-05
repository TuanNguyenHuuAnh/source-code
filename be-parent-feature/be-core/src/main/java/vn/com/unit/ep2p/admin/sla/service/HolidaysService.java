/**
 * 
 */
package vn.com.unit.ep2p.admin.sla.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.ep2p.admin.sla.dto.VietnameseHolidaySearchDto;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.entity.SlaCalendar;
import vn.com.unit.sla.service.SlaCalendarService;;

/**
 * @author trongcv
 *
 */
public interface HolidaysService extends SlaCalendarService{

    /**
     * addOrEditVietnameseHoliday
     *
     * @param vietnameseHolidayDto
     * @throws Exception
     * @author trongcv
     */
    public int addOrEditVietnameseHoliday(SlaCalendarDto vietnameseHolidayDto);

    /**
     * findAllVietnameseHolidayListByYear
     *
     * @return
     * @author trongcv
     */
    public List<SlaCalendarDto> findAllVietnameseHolidayListByYear(VietnameseHolidaySearchDto vietnameseSearchHolidayDto);
    
    /** 
     * findTop1DateByCondition
     *
     * @param vietnameseHolidayDate
     * @return
     * @author THANH HUNG
     */
    public SlaCalendar findTop1DateByCondition(Date vietnameseHolidayDate, Long companyId, Long calendarType);

    public SlaCalendar findTop1DateByConditionOracle(Date vietnameseHolidayDate, Long companyId, Long calendarType);

	public List<SlaCalendarDto> setAllVietnameseHolidayWhileSundayAndSaturday(
			VietnameseHolidaySearchDto vietnameseHolidaySearch);

}
