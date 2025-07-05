/*******************************************************************************
 * Class        ：SlaCalcServiceImpl
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.logger.DebugLogger;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.constant.SlaConstant;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.dto.SlaDateResultDto;
import vn.com.unit.sla.enumdef.TimeTypeEnum;
import vn.com.unit.sla.service.SlaCalculateService;
import vn.com.unit.sla.service.SlaCalendarService;
import vn.com.unit.sla.utils.SlaDateUtils;

/**
 * SlaCalcServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaCalculateServiceImpl extends AbstractSlaService implements SlaCalculateService {

    @Autowired
    private SlaCalendarService slaCalendarService;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalculateService#calcDueDateByWorkingTime(java.util.Date, java.lang.Double, java.lang.String,
     * java.lang.Long)
     */
    @Override
    public SlaDateResultDto calcDueDateByWorkingTime(Date submitDate, Long workTime, Integer timeType, Long calendarTypeId) {
        DebugLogger debugLogger = new DebugLogger();
        DebugLogger.debug("[PT Tracking] | [CalcSlaService] | [calcDueDateByWorkingTime] | [%d] | [Begin] | [%s] | [%d] ",
                Thread.currentThread().getId(), debugLogger.getStart(), 0);
        SlaDateResultDto resultDto = new SlaDateResultDto();
        resultDto.setPlanStartDate(submitDate);
        Date dueDate = null;
        try {
            // Get work time type
            Long timeTotal = this.getSlaMinutesByTimeType(submitDate, workTime, timeType, calendarTypeId);
            boolean isCalendar = this.checkIsCalendar(timeType);
            if (null != timeTotal) {
                // int slaMinutes = (int) (timeTotal / SlaConstant.MINUTE_2_MILLISECONDS);
                int slaMinutes = timeTotal.intValue();
                if (isCalendar) {
                    dueDate = SlaDateUtils.addMinutes(submitDate, slaMinutes);
                    resultDto.setDueDate(dueDate);
                    resultDto.setPlanDueDate(dueDate);
                } else {
                    resultDto = this.calcDueDate(submitDate, slaMinutes, calendarTypeId);
                }
                resultDto.setSlaMinutes(slaMinutes);
                resultDto.setPlanTotalTime(timeTotal);
                resultDto.setPlanEstimateUnitTime(SlaDateUtils.timeUnitToFullTime(timeTotal, TimeUnit.MINUTES, null));
            }
        } catch (Exception e) {
            DebugLogger.debug("[PT Tracking] | [CalcSlaService] | [calcDueDateByWorkingTime] | [%d] | [Error] | [%s] ",
                    Thread.currentThread().getId(), e.getMessage());
        } finally {
            debugLogger.setEndTime();
            DebugLogger.debug("[PT Tracking] | [CalcSlaService] | [calcDueDateByWorkingTime] | [%d] | [End] | [%s] | [%d] ",
                    Thread.currentThread().getId(), debugLogger.getEnd(), debugLogger.getElapsedTime());
        }
        return resultDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalculateService#calcDueDate(java.util.Date, int, java.lang.Long)
     */
    @Override
    public SlaDateResultDto calcDueDate(Date startDate, int slaMinutes, Long calendarTypeId) throws DetailException {
        SlaDateResultDto resultDto = new SlaDateResultDto();
        List<SlaCalendarDto> calendarDtoList = new ArrayList<>();
        boolean continueCheck = true;
        Date beginDate = startDate;
        Date lastDueDate = null;
        while (continueCheck) {
            List<SlaCalendarDto> slaCalendarDtoList = this.getCalendarForNextDay(beginDate, SlaConstant.NUM_DAYS_DEFAULT, calendarTypeId);
            if(CommonCollectionUtil.isNotEmpty(slaCalendarDtoList)) {
                calendarDtoList.addAll(slaCalendarDtoList);
            }
            resultDto = SlaDateUtils.calcDueDateByDayOffList(startDate, slaMinutes, calendarDtoList, lastDueDate);
            continueCheck = resultDto.isContinueCheckCalendar();
            lastDueDate = resultDto.getDueDate();
            beginDate = SlaDateUtils.addDays(beginDate, SlaConstant.NUM_DAYS_DEFAULT);
        }
        return resultDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalculateService#calcElapsedMinutes(java.util.Date, java.util.Date, int, java.lang.Long)
     */
    @Override
    public SlaDateResultDto calcElapsedMinutes(Date startDate, Date endDate, int slaMinutes, Long calendarTypeId) {
        DebugLogger debugLogger = new DebugLogger();
        DebugLogger.debug("[PT Tracking] | [CalcSlaService] | [calcElapsedMinutes] | [%d] | [Begin] | [%s] | [%d] ",
                Thread.currentThread().getId(), debugLogger.getStart(), 0);

        Date beginDate = startDate;
        long numMiliseconds = SlaDateUtils.truncate(endDate, Calendar.DATE).getTime()
                - SlaDateUtils.truncate(startDate, Calendar.DATE).getTime();
        Long numDays = TimeUnit.MILLISECONDS.toDays(numMiliseconds);
        SlaDateResultDto resDto = new SlaDateResultDto();
        try {
            List<SlaCalendarDto> dayOffTimeList = this.getCalendarForNextDay(beginDate, numDays.intValue(), calendarTypeId);
            resDto = SlaDateUtils.calcElapsedMinutes(beginDate, endDate, dayOffTimeList, slaMinutes);
        } catch (Exception e) {
            DebugLogger.debug("[PT Tracking] | [CalcSlaService] | [calcElapsedMinutes] | [%d] | [Error] | [%s] ",
                    Thread.currentThread().getId(), e.getMessage());
        } finally {
            debugLogger.setEndTime();
            DebugLogger.debug("[PT Tracking] | [CalcSlaService] | [calcElapsedMinutes] | [%d] | [End] | [%s] | [%d] ",
                    Thread.currentThread().getId(), debugLogger.getEnd(), debugLogger.getElapsedTime());
        }

        return resDto;
    }

    @Override
    public long getSlaMinutesByTimeType(Date submitDate, Long value, Integer timeType, Long calendarTypeId) {
        long res = 0;
        TimeTypeEnum timeTypeEnum = TimeTypeEnum.resolveByValue(timeType);
        switch (timeTypeEnum) {
        case WORKING_MINUTE:
        case CALENDAR_MINUTE:
            res = value;
            break;
        case WORKING_HOUR:
        case CALENDAR_HOUR:
            res = value * SlaConstant.HOUR_2_MINUTE;
            break;
        case WORKING_DAY:
            res = this.getSlaMinutesForWorkingDayCalendar(submitDate, value, calendarTypeId);
            break;
        case CALENDAR_DAY:
            res = value * SlaConstant.DAY_2_MINUTE;
            break;
        default:
            break;
        }
        return res;
    }

    /**
     * <p>
     * Get working time by calendar.
     * </p>
     *
     * @param calendarTypeId
     *            type {@link Long}
     * @return {@link long}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    private long getSlaMinutesForWorkingDayCalendar(Date submitDate, Long days, Long calendarTypeId) {
//        long result = days * SlaConstant.DAY_2_MINUTE;
        long result = 8; //TODO 1 NGAY 8H
        // Date startDate = SlaDateUtils.truncate(submitDate, Calendar.DATE);
        // Calendar calendar = Calendar.getInstance();
        // calendar.setTime(startDate);
        // calendar.add(Calendar.DATE, days.intValue());
        // Date endDate = calendar.getTime();
//        List<SlaCalendarDto> slaCalendarDtoList = this.getCalendarForNextDay(submitDate, days.intValue(), calendarTypeId);
//        if (CommonCollectionUtil.isNotEmpty(slaCalendarDtoList)) {
//            for (SlaCalendarDto slaCalendarDto : slaCalendarDtoList) {
//                result -= slaCalendarDto.getOffMinutes();
//            }
//        }
        return result;
    }

    /**
     * <p>
     * Check is calendar.
     * </p>
     *
     * @param timeType
     *            type {@link String}
     * @return true, if successful
     * @author TrieuVD
     */
    private boolean checkIsCalendar(Integer timeType) {
        boolean res = false;
        TimeTypeEnum timeTypeEnum = TimeTypeEnum.resolveByValue(timeType);
        switch (timeTypeEnum) {
        case CALENDAR_MINUTE:
        case CALENDAR_HOUR:
        case CALENDAR_DAY:
            res = true;
            break;
        default:
            break;
        }
        return res;
    }

    /**
     * <p>
     * Get calendar for next day.
     * </p>
     *
     * @author TrieuVD
     * @param beginDate
     *            type {@link Date}
     * @param intValue
     *            type {@link int}
     * @param calendarTypeId
     *            type {@link Long}
     * @return {@link List<SlaCalendarDto>}
     */
    private List<SlaCalendarDto> getCalendarForNextDay(Date beginDate, int intValue, Long calendarTypeId) {
        Date startDate = SlaDateUtils.truncate(beginDate, Calendar.DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, intValue);
        Date endDate = calendar.getTime();
        List<SlaCalendarDto> slaCalendarDtoList = new ArrayList<>();
        try {
            slaCalendarDtoList = slaCalendarService.getSlaCalendarDtoListByRangeAndCalendarTypeId(startDate, endDate, calendarTypeId);
        } catch (DetailException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return slaCalendarDtoList;
    }

}
