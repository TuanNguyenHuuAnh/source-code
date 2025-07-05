/**
 * 
 */
package vn.com.unit.ep2p.admin.sla.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.sla.dto.VietnameseHolidaySearchDto;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.entity.SlaCalendar;

/**
 * @author NghiaPV
 *
 */
public interface VietnameseHolidayRepository extends DbRepository<SlaCalendar, Long> {

    public List<SlaCalendarDto> findAllVietnameseHolidayListByCondition(@Param("date") Date date,
            @Param("description") String description);

    public List<SlaCalendarDto> findAllVietnameseHolidayList();

    /**
     * countVietnameseHolidayByCondition
     *
     * @param vietnameseHolidaySearchDto
     * @return int
     * @author NghiaPV
     */
    public int countVietnameseHolidayByCondition(
            @Param("vietnameseHolidaySearchDto") VietnameseHolidaySearchDto vietnameseHolidaySearchDto);

    /**
     * 
     * @param offset
     * @param sizeOfPage
     * @param vietnameseHolidaySearchDto
     * @return
     */
    public List<SlaCalendarDto> findVietnameseHolidayLimitByCondition(
            // @Param("offset") int offset,
            @Param("startIndex") int startIndex,
            // @Param("sizeOfPage") int sizeOfPage,
            @Param("endIndex") int endIndex,
            @Param("vietnameseHolidaySearchDto") VietnameseHolidaySearchDto vietnameseHolidaySearchDto);

    public List<Map<String, Object>> findVietnameseHolidayLimitByConditionMap(
            // @Param("offset") int offset,
            @Param("startIndex") int startIndex,
            // @Param("sizeOfPage") int sizeOfPage,
            @Param("endIndex") int endIndex,
            @Param("vietnameseHolidaySearchDto") VietnameseHolidaySearchDto vietnameseHolidaySearchDto);

    public int findExistenceDate(@Param("vietnameseHolidayDate") Date vietnameseHolidayDate,
            @Param("companyId") Long companyId, @Param("calendarType") Long calendarType);

    public SlaCalendar findTop1DateByCondition(@Param("vietnameseHolidayDate") Date vietnameseHolidayDate,
            @Param("companyId") Long companyId, @Param("calendarType") Long calendarType);

    @Modifying
    public Object editVietnameseHoliday(@Param("vietnameseHoliday") SlaCalendar vietnameseHoliday);

    @Modifying
    public Object addVietnameseHoliday(@Param("vietnameseHoliday") SlaCalendar vietnameseHoliday);

    @Modifying
    public Object deleteVietnameseHoliday(@Param("vietnameseHolidayDate") Date vietnameseHolidayDate,
            @Param("companyId") Long companyId, @Param("calendarType") Long calendarType);

    /**
     * findAllVietnameseHolidayListByYear
     *
     * @param vietnameseHolidaySearchDto
     * @return List<VietnameseHolidayDto>
     * @author trongcv
     */
    public List<SlaCalendarDto> findAllVietnameseHolidayListByYear(
            @Param("vietnameseHolidaySearchDto") VietnameseHolidaySearchDto vietnameseHolidaySearchDto);

    /**
     * findAllVietnameseHolidayListByYearOracle
     *
     * @param vietnameseHolidaySearchDto
     * @return List<VietnameseHolidayDto>
     * @author hiepth
     */
    public List<SlaCalendarDto> findAllVietnameseHolidayListByYearOracle(
            @Param("vietnameseHolidaySearchDto") VietnameseHolidaySearchDto vietnameseHolidaySearchDto);

    /**
     * findAllVietnameseHolidayListByYearOracle
     *
     * @param vietnameseHolidaySearchDto
     * @return List<VietnameseHolidayDto>
     * @author hiepth
     */
    public SlaCalendar findTop1DateByConditionOracle(@Param("vietnameseHolidayDate") Date vietnameseHolidayDate,
            @Param("companyId") Long companyId, @Param("calendarType") Long calendarType);

    public List<SlaCalendarDto> findListVietnameseHolidayBetweenPeriodTime(@Param("startDate") Date startDate,
            @Param("endDate") Date endDate, @Param("companyId") Long companyId,
            @Param("calendarType") Long calendarType);

}
