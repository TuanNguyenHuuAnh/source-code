/*******************************************************************************
 * Class        ：SlaDateUtils
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：TrieuVD
 * Change log   ：2021/01/13：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.sla.constant.SlaConstant;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.dto.SlaDateResultDto;
import vn.com.unit.sla.enumdef.UnitTimeTypeSlaEnum;

/**
 * <p>
 * SlaDateUtils
 * </p>
 * 
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public class SlaDateUtils extends CommonDateUtil {

    public static final String FULL_TIME_FORMAT = "%d,%02d,%02d";

    /**
     * <p>
     * Calc due date.
     * </p>
     *
     * @param submittedDate
     *            type {@link Date}
     * @param slaMinutes
     *            type {@link int}
     * @param calendarDtoList
     *            type {@link List<SlaCalendarDto>}
     * @param lastDueDate
     *            type {@link Date}
     * @return {@link SlaDateResultDto}
     * @author TrieuVD
     */
    public static SlaDateResultDto calcDueDateByDayOffList(Date submittedDate, int slaMinutes, List<SlaCalendarDto> calendarDtoList,
            Date lastDueDate) {
        // System.out.println("SLA calculate due date: START");
        Date startDate = submittedDate;
        Date dueDate = null;
        List<SlaCalendarDto> excludedcalendarDtoList = null;
        // Have not holiday
        if (CommonCollectionUtil.isEmpty(calendarDtoList)) {
            if (null == lastDueDate) {
                dueDate = addMinutes(startDate, slaMinutes);
            } else {
                dueDate = lastDueDate;
            }
            return new SlaDateResultDto(startDate, slaMinutes, dueDate, null, false);
        }

        // Have holiday
        dueDate = addMinutes(startDate, slaMinutes);
        int addMinutesInHoliday = 0;
        Date startDayOffTime = null;
        Date endDayOffTime = null;
        boolean continueCheckHoliday = true;

        // Get submit miliseconds
        int submitMilliseconds = calcMilliseconds(truncate(submittedDate, Calendar.MINUTE), submittedDate);

        // Check holidays
        for (SlaCalendarDto calendarDto : calendarDtoList) {
            startDayOffTime = calendarDto.getStartCalendar();
            endDayOffTime = calendarDto.getEndCalendar();

            // Check submitted date in holiday
            if (truncatedCompareTo(startDayOffTime, startDate, Calendar.MINUTE) <= 0
                    && truncatedCompareTo(startDate, endDayOffTime, Calendar.MINUTE) <= 0) {
                // Add miliseconds
                startDate = addMilliseconds(endDayOffTime, submitMilliseconds);

                // Plus 1 minute when 23h:59
                if (SlaConstant.END_TIME_DEFAULT.equals(calendarDto.getEndTime())) {
                    startDate = addMinutes(startDate, 1);
                }
                dueDate = addMinutes(startDate, slaMinutes);
            }

            // if submitted date after end time of holiday then continues
            if (truncatedCompareTo(endDayOffTime, startDate, Calendar.MINUTE) <= 0) {
                continue;
            }

            // Due date before holiday
            if (truncatedCompareTo(dueDate, startDayOffTime, Calendar.MINUTE) <= 0) {
                addMinutesInHoliday = 0;
                continueCheckHoliday = false;
                // System.out.println(" Before holiday. AddMinutesInHoliday: " + addMinutesInHoliday);
                // System.out.println(" EndDate: " + DateFormatUtils.format(dueDate, DATE_PATTERN));
                break;
            }

            // Init excludedcalendarDtoList
            if (null == excludedcalendarDtoList) {
                excludedcalendarDtoList = new ArrayList<>();
            }
            excludedcalendarDtoList.add(calendarDto);

            // Due date after holiday
            if (dueDate.after(endDayOffTime)) {
                addMinutesInHoliday = calcMinutes(startDayOffTime, endDayOffTime);
                dueDate = addMinutes(dueDate, addMinutesInHoliday);
                // System.out.println(" After holiday. AddMinutesInHoliday: " + addMinutesInHoliday);
                // System.out.println(" EndDate: " + DateFormatUtils.format(dueDate, DATE_PATTERN));
                continue;
            }

            // Due date in holiday
            addMinutesInHoliday = calcMinutes(startDayOffTime, dueDate);
            // Plus 1 minute when 23h:59
            if (SlaConstant.END_TIME_DEFAULT.equals(calendarDto.getEndTime())) {
                addMinutesInHoliday += 1;
            }
            dueDate = addMinutes(endDayOffTime, addMinutesInHoliday);

            // Add miliseconds
            dueDate = addMilliseconds(dueDate, submitMilliseconds);
            // System.out.println(" In holiday. AddMinutesInHoliday: " + addMinutesInHoliday);
            // System.out.println(" EndDate: " + DateFormatUtils.format(dueDate, DATE_PATTERN));
        }

        /*
         * System.out.println("SubmittedDate: " + DateFormatUtils.format(submittedDate, DATE_PATTERN) + ", SlaMinutes: " + slaMinutes +
         * ", DueDate: " + DateFormatUtils.format(dueDate, DATE_PATTERN) + ", ContinueCheckHoliday: " + continueCheckHoliday); if
         * (CollectionUtils.isNotEmpty(excludedcalendarDtoList)) { for (calendarDto calendarDto : excludedcalendarDtoList) {
         * System.out.println("ExcludedHoliday. startDayOffTime: " + calendarDto.getstartDayOffTime() + ", endDayOffTime: " +
         * calendarDto.getendDayOffTime()); } } System.out.println("SLA calculate due date: END");
         */
        return new SlaDateResultDto(submittedDate, slaMinutes, dueDate, excludedcalendarDtoList, continueCheckHoliday);
    }

    /**
     * <p>
     * Calc elapsed minutes.
     * </p>
     *
     * @param submittedDate
     *            type {@link Date}
     * @param completedDate
     *            type {@link Date}
     * @param calendarDtoList
     *            type {@link List<SlaCalendarDto>}
     * @param slaMinutes
     *            type {@link int}
     * @return {@link SlaDateResultDto}
     * @author TrieuVD
     */
    public static SlaDateResultDto calcElapsedMinutes(Date submittedDate, Date completedDate, List<SlaCalendarDto> calendarDtoList,
            int slaMinutes) {
        // System.out.println("SLA calculate elapsed minutes: START");
        Date startDate = truncate(submittedDate, Calendar.MINUTE);
        Date endDate = truncate(completedDate, Calendar.MINUTE);
        int elapsedMinutes = 0;
        List<SlaCalendarDto> excludedcalendarDtoList = null;

        // Have not holiday
        if (CommonCollectionUtil.isEmpty(calendarDtoList)) {
            elapsedMinutes = calcMinutes(startDate, endDate);
            return new SlaDateResultDto(submittedDate, completedDate, elapsedMinutes, slaMinutes, null);
        }

        int minusMinutesInHoliday = 0;
        Date startDayOffTime = null;
        Date endDayOffTime = null;

        // Calc elapsed minutes
        elapsedMinutes = calcMinutes(startDate, endDate);

        // Check holidays
        for (SlaCalendarDto calendarDto : calendarDtoList) {
            startDayOffTime = calendarDto.getStartCalendar();
            endDayOffTime = calendarDto.getEndCalendar();

            // Init excludedcalendarDtoList
            if (null == excludedcalendarDtoList) {
                excludedcalendarDtoList = new ArrayList<>();
            }
            excludedcalendarDtoList.add(calendarDto);
            
            // Check submitted date in holiday
            if (truncatedCompareTo(startDayOffTime, startDate, Calendar.MINUTE) <= 0
                    && truncatedCompareTo(startDate, endDayOffTime, Calendar.MINUTE) <= 0) {
                startDate = endDayOffTime;

                // Plus 1 minute when 23h:59
                if (SlaConstant.END_TIME_DEFAULT.equals(calendarDto.getEndTime())) {
                    startDate = addMinutes(startDate, 1);
                }
                elapsedMinutes = calcMinutes(startDate, endDate);
            }

            // Check completed date in holiday
            if (truncatedCompareTo(startDayOffTime, endDate, Calendar.MINUTE) <= 0
                    && truncatedCompareTo(endDate, endDayOffTime, Calendar.MINUTE) <= 0) {
                elapsedMinutes -= calcMinutes(startDayOffTime, endDate);
                endDate = startDayOffTime;
                if (null != endDate && endDate.before(startDate)) {
                    elapsedMinutes = 0;
                }
                break;
            }

            // if submitted after end time of holiday then continues
            if (truncatedCompareTo(endDayOffTime, startDate, Calendar.MINUTE) <= 0) {
                continue;
            }

            // Completed date before holiday
            if (truncatedCompareTo(endDate, startDayOffTime, Calendar.MINUTE) <= 0) {
                minusMinutesInHoliday = 0;
                // System.out.println(" Before holiday. MinusMinutesInHoliday: " + minusMinutesInHoliday);
                // System.out.println(" ElapsedMinutes: " + elapsedMinutes);
                break;
            }

            // Completed date after holiday
            if (truncatedCompareTo(endDayOffTime, endDate, Calendar.MINUTE) < 0) {
                minusMinutesInHoliday = calcMinutes(startDayOffTime, endDayOffTime);

                // Plus 1 minute when 23h:59
                if (SlaConstant.END_TIME_DEFAULT.equals(calendarDto.getEndTime())) {
                    minusMinutesInHoliday += 1;
                }

                // Calc elapsed minutes
                if (minusMinutesInHoliday < elapsedMinutes) {
                    elapsedMinutes -= minusMinutesInHoliday;
                } else {
                    elapsedMinutes = 0;
                }
                // System.out.println(" After holiday. MinusMinutesInHoliday: " + minusMinutesInHoliday);
                // System.out.println(" ElapsedMinutes: " + elapsedMinutes);
                continue;
            }

            // Completed date in holiday
            minusMinutesInHoliday = calcMinutes(startDayOffTime, endDate);
            // Calc elapsed minutes
            if (minusMinutesInHoliday < elapsedMinutes) {
                elapsedMinutes -= minusMinutesInHoliday;
            } else {
                elapsedMinutes = 0;
            }
            // System.out.println(" In holiday. AddMinutesInHoliday: " + minusMinutesInHoliday);
            // System.out.println(" ElapsedMinutes: " + elapsedMinutes);
        }

        /*
         * System.out.println("SubmittedDate: " + DateFormatUtils.format(submittedDate, DATE_PATTERN) + ", CompletedDate: " +
         * DateFormatUtils.format(completedDate, DATE_PATTERN) + ", ElapsedMinutes: " + elapsedMinutes + ", SlaMinutes: " + slaMinutes); if
         * (CollectionUtils.isNotEmpty(excludedcalendarDtoList)) { for (calendarDto calendarDto : excludedcalendarDtoList) {
         * System.out.println("ExcludedHoliday. startDayOffTime: " + calendarDto.getstartDayOffTime() + ", endDayOffTime: " +
         * calendarDto.getendDayOffTime()); } } System.out.println("SLA calculate elapsed minutes: END");
         */
        return new SlaDateResultDto(submittedDate, completedDate, elapsedMinutes, slaMinutes, excludedcalendarDtoList);
    }

    /**
     * <p>
     * Calc milliseconds.
     * </p>
     *
     * @param start
     *            type {@link Date}
     * @param end
     *            type {@link Date}
     * @return {@link int}
     * @author TrieuVD
     */
    public static int calcMilliseconds(Date start, Date end) {
        int result = 0;
        if (null != start && null != end) {
            result = (int) (end.getTime() - start.getTime());
        }
        return result;
    }

    /**
     * <p>
     * Calc minutes.
     * </p>
     *
     * @param start
     *            type {@link Date}
     * @param end
     *            type {@link Date}
     * @return {@link int}
     * @author TrieuVD
     */
    public static int calcMinutes(Date start, Date end) {
        int result = 0;
        if (null != start && null != end) {
            result = (int) Math.floorDiv(calcMilliseconds(start, end), SlaConstant.MINUTE_2_MILLISECONDS);
        }
        return result;
    }

    /**
     * <p>
     * Convert to milliseconds by unit time type.
     * </p>
     *
     * @param alertTime
     *            type {@link Double}
     * @param alertTimeType
     *            type {@link String}
     * @return {@link Long}
     * @author TrieuVD
     */
    public static Long convertToMillisecondsByUnitTimeType(Long alertTime, int alertTimeType) {
        // Long result = null;
        // if (CommonStringUtil.isNotBlank(alertTimeType) && null != alertTime) {
        // UnitTimeTypeSlaEnum unitTime = UnitTimeTypeSlaEnum.resolveByValue(alertTimeType);
        // switch (unitTime) {
        // case MINUTE:
        // result = alertTime.longValue() * SlaConstant.MINUTE_2_MILLISECONDS;
        // break;
        // case HOUR:
        // result = alertTime.longValue() * SlaConstant.HOUR_2_MILLISECONDS;
        // break;
        // case DAY:
        // result = alertTime.longValue() * SlaConstant.DAY_2_MILLISECONDS;
        // break;
        // default:
        // break;
        // }
        // }
        // return result;
        return convertToMinutesByUnitTimeType(alertTime, alertTimeType) / SlaConstant.MINUTE_2_MILLISECONDS;
    }

    // public static String millisecondToFullTime(long millisecond) {
    // return timeUnitToFullTime(millisecond, TimeUnit.MILLISECONDS);
    // }
    //
    // public static String secondToFullTime(long second) {
    // return timeUnitToFullTime(second, TimeUnit.SECONDS);
    // }


    public static Long convertToMinutesByUnitTimeType(Long alertTime, int alertTimeType) {
        Long result = 0L;
        UnitTimeTypeSlaEnum unitTime = UnitTimeTypeSlaEnum.resolveByValue(alertTimeType);
        switch (unitTime) {
        case MINUTE:
            result = alertTime;
            break;
        case HOUR:
            result = alertTime * SlaConstant.HOUR_2_MINUTE;
            break;
        case DAY:
            result = alertTime * SlaConstant.DAY_2_MINUTE;
            break;
        default:
            break;
        }
        return result;
    }
    
    /**
     * <p>
     * Time unit to full time. <br/>
     * Default format: d,hh,mm
     * </p>
     *
     * @author TrieuVD
     * @param time
     *            type {@link long}
     * @param timeUnit
     *            type {@link TimeUnit}
     * @param format
     *            type {@link String}
     * @return {@link String}
     */
    public static String timeUnitToFullTime(long time, TimeUnit timeUnit, String format) {
        if (CommonStringUtil.isBlank(format)) {
            format = FULL_TIME_FORMAT;
        }
        long day = timeUnit.toDays(time);
        long hour = timeUnit.toHours(time) % 24;
        long minute = timeUnit.toMinutes(time) % 60;
        // long second = timeUnit.toSeconds(time) % 60;
        return String.format(format, day, hour, minute);

    }

    public static void main(String[] args) {

        System.out.println(timeUnitToFullTime(70, TimeUnit.MINUTES, "%d,%02d,%02d"));

        Date submittedDate = parseDate("20210117 11:40:00", "yyyyMMdd HH:mm:ss");
        int slaMinutes = 15;
        List<SlaCalendarDto> calendarDtoList = new ArrayList<>();
        calendarDtoList.add(new SlaCalendarDto(parseDate("20210117", "yyyyMMdd"), "11:35", "23:59"));
        calendarDtoList.add(new SlaCalendarDto(parseDate("20210118", "yyyyMMdd"), "00:00", "08:30"));

        // Date startDate = submittedDate;
        Date completedDate = parseDate("20210118 08:36:09", "yyyyMMdd HH:mm:ss");
        // Date endDate = completedDate;
        SlaDateResultDto resultDueDate = calcDueDateByDayOffList(submittedDate, slaMinutes, calendarDtoList, null);
        Date endDate = resultDueDate.getDueDate();

        SlaDateResultDto resultElapsed = calcElapsedMinutes(submittedDate, completedDate, calendarDtoList, slaMinutes);
        int elapsedMinutes = resultElapsed.getElapsedMinutes();

        System.out.println("slaMinutes: " + resultDueDate);
        System.out.println("slaMinutes: " + slaMinutes);
        System.out.println("submittedDate: " + formatDateToString(submittedDate, "yyyyMMdd HH:mm:ss"));
        System.out.println("dueDate: " + formatDateToString(endDate, "yyyyMMdd HH:mm:ss"));
        System.out.println("completedDate: " + formatDateToString(completedDate, "yyyyMMdd HH:mm:ss"));
        System.out.println("elapsedMinutes: " + elapsedMinutes);
    }
}
