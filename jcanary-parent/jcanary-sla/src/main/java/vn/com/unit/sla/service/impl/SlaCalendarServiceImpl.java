/*******************************************************************************
 * Class        ：SlaCalendarDtoServiceImpl
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
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.constant.SlaExceptionCodeConstant;
import vn.com.unit.sla.dto.SlaCalendarAddDto;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.dto.SlaCalendarSearchDto;
import vn.com.unit.sla.entity.SlaCalendar;
import vn.com.unit.sla.entity.SlaWorkingDay;
import vn.com.unit.sla.repository.SlaCalendarRepository;
import vn.com.unit.sla.service.SlaCalendarService;
import vn.com.unit.sla.service.SlaWorkingDayService;
import vn.com.unit.sla.utils.SlaDateUtils;

/**
 * SlaCalendarDtoServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaCalendarServiceImpl extends AbstractSlaService implements SlaCalendarService {

    @Autowired
    private SlaCalendarRepository slaCalendarDtoRepository;

    @Autowired
    private SlaWorkingDayService slaWorkingDayService;

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(SlaCalendarServiceImpl.class);

    private static final String DAY_OFF_ID = "CalendarDtoId";
    private static final String AUTO_GEN = "Auto gen";

    @Override
    public List<SlaCalendarDto> getSlaCalendarDtoListByRangeAndCalendarTypeId(Date startDate, Date endDate, Long calendarTypeId)
            throws DetailException {
        List<SlaCalendarDto> resultList = new ArrayList<>();
        SlaCalendarSearchDto calendarDtoSearchDto = new SlaCalendarSearchDto();
        calendarDtoSearchDto.setFromDate(startDate);
        calendarDtoSearchDto.setToDate(endDate);
        calendarDtoSearchDto.setCalendarTypeId(calendarTypeId);
        try {
            resultList = this.getSlaCalendarDtoListBySearchDto(calendarDtoSearchDto);
        } catch (DetailException detailException) {
            throw detailException;
        } catch (Exception exception) {
            throw new DetailException(SlaExceptionCodeConstant.E201600_SLA_INTERNAL_ERROR, true);
        }
        return resultList;
    }

    @Override
    @Transactional
    public List<SlaCalendarDto> getSlaCalendarDtoListBySearchDto(SlaCalendarSearchDto CalendarDtoSearchDto) throws Exception {
        CalendarDtoSearchDto.setFromDate(SlaDateUtils.truncate(CalendarDtoSearchDto.getFromDate(), Calendar.DATE));
        CalendarDtoSearchDto.setToDate(SlaDateUtils.truncate(CalendarDtoSearchDto.getToDate(), Calendar.DATE));
        List<SlaCalendarDto> CalendarDtoDtoList = slaCalendarDtoRepository.getSlaCalendarDtoListBySearchDto(CalendarDtoSearchDto);
        // return this.generateCalendarDtoWeekend(CalendarDtoDtoList, CalendarDtoSearchDto);
        return CalendarDtoDtoList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalendarDtoService#saveSlaCalendarDto(vn.com.unit.sla.entity.SlaCalendarDto)
     */
    @Override
    @Transactional
    public SlaCalendar saveSlaCalendar(SlaCalendar slaCalendarDto) throws Exception {
        Long id = slaCalendarDto.getId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getAccountId();
        if (null != id) {
            SlaCalendar oldSlaCalendarDto = slaCalendarDtoRepository.findOne(id);
            if (null != oldSlaCalendarDto) {
                slaCalendarDto.setUpdatedId(userId);
                slaCalendarDto.setUpdatedDate(sysDate);
                slaCalendarDto.setCreatedId(oldSlaCalendarDto.getCreatedId());
                slaCalendarDto.setCreatedDate(oldSlaCalendarDto.getCreatedDate());
                slaCalendarDtoRepository.update(slaCalendarDto);
            } else {
                logger.error("[SlaCalendarDtoServiceImpl] [saveSlaCalendarDto] data not found, id: {}", id);
                throw new DetailException(SlaExceptionCodeConstant.E201702_SLA_DATA_NOT_FOUND_ERROR, true);
            }
        } else {
            slaCalendarDto.setCreatedId(userId);
            slaCalendarDto.setCreatedDate(sysDate);
            slaCalendarDto.setUpdatedId(userId);
            slaCalendarDto.setUpdatedDate(sysDate);
            slaCalendarDtoRepository.create(slaCalendarDto);
        }
        return slaCalendarDto;
        // return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalendarDtoService#getSlaCalendarDtoById(java.lang.Long)
     */
    @Override
    public SlaCalendarDto getSlaCalendarDtoById(Long calendarDtoId) throws DetailException {
        if (null == calendarDtoId) {
            logger.error("[SlaCalendarDtoServiceImpl] [getSlaCalendarDtoById] calendarId is null");
            throw new DetailException(SlaExceptionCodeConstant.E201701_SLA_VALIDATE_REQUIRED_ERROR, new String[] { DAY_OFF_ID }, true);
        } else {
            return slaCalendarDtoRepository.getSlaCalendarDtoById(calendarDtoId);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalendarDtoService#getSlaCalendarDtoDtoByCondition(java.util.Date, java.lang.Long, java.lang.Long)
     */
    @Override
    public SlaCalendarDto getSlaCalendarDtoByCondition(Date date, Long calendarTypeId, Long companyId) throws DetailException {
        date = CommonDateUtil.truncate(date, Calendar.DATE);
        return slaCalendarDtoRepository.getSlaCalendarDtoByCondition(date, calendarTypeId, companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalendarDtoService#saveSlaCalendarDtoDto(vn.com.unit.sla.dto.SlaCalendarDtoDto)
     */
    @Override
    @Transactional
    public SlaCalendar saveSlaCalendarDto(SlaCalendarDto slaCalendarDtoDto) throws Exception {
        SlaCalendar objectSave = mapper.convertValue(slaCalendarDtoDto, SlaCalendar.class);
        return this.saveSlaCalendar(objectSave);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalendarDtoService#deleteById(java.lang.Long)
     */
    @Override
    @Transactional
    public void deleteById(Long CalendarDtoId) throws DetailException {
        if (null == CalendarDtoId) {
            logger.error("[SlaCalendarDtoServiceImpl] [getSlaCalendarDtoDtoById] CalendarDtoId is null");
            throw new DetailException(SlaExceptionCodeConstant.E201701_SLA_VALIDATE_REQUIRED_ERROR, new String[] { DAY_OFF_ID }, true);
        } else {
            slaCalendarDtoRepository.delete(CalendarDtoId);
        }
    }

    @Override
    @Transactional
    public void deleteByCalendarTypeIdAndDate(Date date, Long calendarTypeId) throws DetailException {
        try {
            date = CommonDateUtil.truncate(date, Calendar.DATE);
            slaCalendarDtoRepository.deleteByCalendarTypeIdAndDate(date, calendarTypeId);

        } catch (Exception e) {
            throw new DetailException(SlaExceptionCodeConstant.E201701_SLA_VALIDATE_REQUIRED_ERROR, new String[] { DAY_OFF_ID }, true);
        }
    }
    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalendarDtoService#generateCalendarDtoWeekend(java.util.List,
     * vn.com.unit.sla.dto.SlaCalendarDtoSearchDto)
     */

    // public List<SlaCalendarDto> generateCalendarDtoWeekend(List<SlaCalendarDto> CalendarDtoDtoList, SlaCalendarSearchDto
    // CalendarDtoSearchDto) throws Exception {
    // List<SlaCalendarDto> resultList = new ArrayList<>();
    // Long calendarTypeId = CalendarDtoSearchDto.getCalendarTypeId();
    // Date fromDate = CalendarDtoSearchDto.getFromDate();
    // Date toDate = CalendarDtoSearchDto.getToDate();
    // Date current = fromDate;
    // Calendar calendar = Calendar.getInstance();
    // while (current.before(toDate)) {
    // Date dateFilter = current;
    // SlaCalendarDto CalendarDtoDto = CalendarDtoDtoList.stream()
    // .filter(f -> CommonDateUtil.truncatedCompareTo(f.getCalendarDate(), dateFilter, Calendar.DATE) == 0).findFirst().orElse(null);
    // calendar.setTime(current);
    // if (null == CalendarDtoDto) {
    // CalendarDtoDto = new SlaCalendarDto();
    // CalendarDtoDto.setCalendarDate(current);
    // CalendarDtoDto.setCalendarTypeId(calendarTypeId);
    // CalendarDtoDto.setDescription(AUTO_GEN);
    // if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
    // CalendarDtoDto.setCalendarDtoType(CalendarDtoTypeEnum.FULL_DAY.getValue());
    // } else {
    // CalendarDtoDto.setCalendarDtoType(CalendarDtoTypeEnum.NO_VALUE.getValue());
    // }
    // CalendarDtoDto.setId(this.saveSlaCalendarDtoDto(CalendarDtoDto).getId());
    // }
    // resultList.add(CalendarDtoDto);
    //
    // calendar.add(Calendar.DATE, 1);
    // current = calendar.getTime();
    // }
    // return resultList;
    // }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SlaCalendarDto> generateCalendarDtoWeekend(SlaCalendarAddDto slaCalendarAddDto) throws Exception {
        List<SlaCalendarDto> resultList = new ArrayList<>();
        Long calendarTypeId = slaCalendarAddDto.getCalendarTypeId();
        Date fromDate = slaCalendarAddDto.getFromDate();
        Date toDate = slaCalendarAddDto.getToDate();
        Date current = fromDate;
        String startTimeMorning = slaCalendarAddDto.getStartTimeMorning();
        String endTimeMorning = slaCalendarAddDto.getEndTimeMorning();
        String startTimeEvening = slaCalendarAddDto.getStartTimeEvening();
        String endTimeEvening = slaCalendarAddDto.getEndTimeEvening();
        String description = slaCalendarAddDto.getDescription();
        
        List<Integer> listDate = slaCalendarAddDto.getListDate();
        Calendar calendar = Calendar.getInstance();
        while (current.before(toDate) || current.equals(toDate)) {
            calendar.setTime(current);

            Date dateFilter = current;
            SlaCalendarDto oldCalendar = this.getSlaCalendarDtoByCondition(dateFilter, calendarTypeId, null);
            if (oldCalendar != null) {
                this.deleteByCalendarTypeIdAndDate(dateFilter, calendarTypeId);
            }
            List<SlaWorkingDay> oldSlaWorkingDay = slaWorkingDayService.getSlaWorkingDayByCondition(dateFilter, calendarTypeId);
            if (CommonCollectionUtil.isNotEmpty(oldSlaWorkingDay)) {
                slaWorkingDayService.deleteByCalendarTypeIdAndDate(dateFilter, calendarTypeId);
            }
            // SlaCalendarDto calendarDtoDto = CalendarDtoDtoList.stream()
            // .filter(f -> CommonDateUtil.truncatedCompareTo(f.getCalendarDate(), dateFilter, Calendar.DATE) ==
            // 0).findFirst().orElse(null);
            // calendar.setTime(current);
            // if (null == calendarDtoDto) {

            if (CommonCollectionUtil.isNotEmpty(listDate)) {

                if (listDate.indexOf(calendar.get(Calendar.DAY_OF_WEEK)) > -1) {
                    // TẠO BUỔI SÁNG
                        for (Integer date : listDate) {
                            if (calendar.get(Calendar.DAY_OF_WEEK) == date) {
                                SlaCalendarDto slaCalendarDtoMorning = new SlaCalendarDto();
                                if (CommonStringUtil.isNotBlank(startTimeMorning) && CommonStringUtil.isNotBlank(endTimeMorning)) {
                                    slaCalendarDtoMorning.setCalendarDate(dateFilter);
                                    slaCalendarDtoMorning.setCalendarTypeId(calendarTypeId);
                                    slaCalendarDtoMorning.setStartTime("00:00");
                                    slaCalendarDtoMorning.setEndTime(startTimeMorning);
                                    slaCalendarDtoMorning.setDescription(description);
                                    try {
                                        slaCalendarDtoMorning.setId(this.saveSlaCalendarDto(slaCalendarDtoMorning).getId());

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // tạo ngày làm việc buổi sáng
                                    SlaWorkingDay slaWorkingDayMorning = new SlaWorkingDay();
                                    slaWorkingDayMorning.setCalendarTypeId(calendarTypeId);
                                    slaWorkingDayMorning.setWorkingDay(dateFilter);
                                    slaWorkingDayMorning.setStartTime(startTimeMorning);
                                    slaWorkingDayMorning.setEndTime(endTimeMorning);
                                    slaWorkingDayMorning.setDescription(description);

                                    try {
                                        slaWorkingDayService.saveSlaWorkingDay(slaWorkingDayMorning);
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                    resultList.add(slaCalendarDtoMorning);
                                } else if(CommonStringUtil.isNotBlank(startTimeEvening)){
                                    // khong lam viec buoi sang
                                    slaCalendarDtoMorning.setCalendarDate(dateFilter);
                                    slaCalendarDtoMorning.setCalendarTypeId(calendarTypeId);
                                    slaCalendarDtoMorning.setStartTime("00:00");
                                    slaCalendarDtoMorning.setEndTime(startTimeEvening);
                                    slaCalendarDtoMorning.setDescription(description);

                                    try {
                                        slaCalendarDtoMorning.setId(this.saveSlaCalendarDto(slaCalendarDtoMorning).getId());

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    resultList.add(slaCalendarDtoMorning);

                                }
                            

                            // tạo buoi trưa
                            SlaCalendarDto slaCalendarDtoNoon = new SlaCalendarDto();

                            if ( CommonStringUtil.isNotBlank(endTimeMorning)
                                    && CommonStringUtil.isNotBlank(startTimeEvening)) {
                                slaCalendarDtoNoon.setCalendarDate(dateFilter);
                                slaCalendarDtoNoon.setCalendarTypeId(calendarTypeId);
                                slaCalendarDtoNoon.setStartTime(endTimeMorning);
                                slaCalendarDtoNoon.setEndTime(startTimeEvening);
                                slaCalendarDtoNoon.setDescription(description);

                                try {
                                    slaCalendarDtoNoon.setId(this.saveSlaCalendarDto(slaCalendarDtoNoon).getId());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                resultList.add(slaCalendarDtoNoon);
                            }
                            // tạo buổi chiều
                            SlaCalendarDto slaCalendarDtoEvening = new SlaCalendarDto();
                                if (CommonStringUtil.isNotBlank(endTimeEvening) && CommonStringUtil.isNotBlank(startTimeEvening)) {
                                    slaCalendarDtoEvening.setCalendarDate(dateFilter);
                                    slaCalendarDtoEvening.setCalendarTypeId(calendarTypeId);
                                    slaCalendarDtoEvening.setStartTime(endTimeEvening);
                                    slaCalendarDtoEvening.setEndTime("23:59");
                                    slaCalendarDtoEvening.setDescription(description);

                                    try {
                                        slaCalendarDtoEvening.setId(this.saveSlaCalendarDto(slaCalendarDtoEvening).getId());

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // tạo ngày làm việc
                                    SlaWorkingDay slaWorkingDayAffterNoon = new SlaWorkingDay();
                                    slaWorkingDayAffterNoon.setCalendarTypeId(calendarTypeId);
                                    slaWorkingDayAffterNoon.setWorkingDay(dateFilter);
                                    slaWorkingDayAffterNoon.setStartTime(startTimeEvening);
                                    slaWorkingDayAffterNoon.setEndTime(endTimeEvening);
                                    slaWorkingDayAffterNoon.setDescription(description);

                                    try {
                                        slaWorkingDayService.saveSlaWorkingDay(slaWorkingDayAffterNoon);
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                    resultList.add(slaCalendarDtoEvening);

                                } else if(CommonStringUtil.isNotBlank(endTimeMorning)) {
                                    slaCalendarDtoEvening.setCalendarDate(dateFilter);
                                    slaCalendarDtoEvening.setCalendarTypeId(calendarTypeId);
                                    slaCalendarDtoEvening.setStartTime(endTimeMorning);
                                    slaCalendarDtoEvening.setEndTime("23:59");
                                    slaCalendarDtoEvening.setDescription(description);

                                    try {
                                        slaCalendarDtoEvening.setId(this.saveSlaCalendarDto(slaCalendarDtoEvening).getId());

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    resultList.add(slaCalendarDtoEvening);
                                }
                                /*
                                 * SlaCalendarDto offDay = new SlaCalendarDto(); if(CommonStringUtil.isNotBlank(startTimeMorning) &&
                                 * CommonStringUtil.isNotBlank(endTimeMorning) &&CommonStringUtil.isNotBlank(endTimeEvening) &&
                                 * CommonStringUtil.isNotBlank(startTimeEvening)) { offDay.setCalendarDate(dateFilter);
                                 * offDay.setCalendarTypeId(calendarTypeId); offDay.setStartTime("00:00"); offDay.setEndTime("23:59"); try {
                                 * offDay.setId(this.saveSlaCalendarDto(offDay).getId());
                                 * 
                                 * } catch (Exception e) { e.printStackTrace(); } resultList.add(offDay);
                                 * 
                                 * }
                                 */
                                
                            }
                        }
                    
                } else {
                    SlaCalendarDto slaCalendarDto = new SlaCalendarDto();
                    slaCalendarDto.setCalendarDate(dateFilter);
                    slaCalendarDto.setCalendarTypeId(calendarTypeId);
                    slaCalendarDto.setStartTime("00:00");
                    slaCalendarDto.setEndTime("23:59");
                    slaCalendarDto.setDescription(description);

                    try {
                        slaCalendarDto.setId(this.saveSlaCalendarDto(slaCalendarDto).getId());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    resultList.add(slaCalendarDto);
                }
                if(!CommonStringUtil.isNotBlank(endTimeMorning)
                    && !CommonStringUtil.isNotBlank(endTimeEvening) 
                    && !CommonStringUtil.isNotBlank(startTimeMorning)
                    && !CommonStringUtil.isNotBlank(startTimeEvening)) {
                    
                    SlaCalendarDto slaCalendarDtoAllDayOff= new SlaCalendarDto();
              
                        slaCalendarDtoAllDayOff.setCalendarDate(dateFilter);
                        slaCalendarDtoAllDayOff.setCalendarTypeId(calendarTypeId);
                        slaCalendarDtoAllDayOff.setStartTime("00:00");
                        slaCalendarDtoAllDayOff.setEndTime("23:59");
                        slaCalendarDtoAllDayOff.setDescription(description);

                        try {
                            slaCalendarDtoAllDayOff.setId(this.saveSlaCalendarDto(slaCalendarDtoAllDayOff).getId());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        resultList.add(slaCalendarDtoAllDayOff);

                    
                }
            }else {
                SlaCalendarDto slaCalendarDto = new SlaCalendarDto();
                slaCalendarDto.setCalendarDate(dateFilter);
                slaCalendarDto.setCalendarTypeId(calendarTypeId);
                slaCalendarDto.setStartTime("00:00");
                slaCalendarDto.setEndTime("23:59");
                slaCalendarDto.setDescription(description);

                try {
                    slaCalendarDto.setId(this.saveSlaCalendarDto(slaCalendarDto).getId());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                resultList.add(slaCalendarDto);
            }

            calendar.add(Calendar.DATE, 1);
            current = calendar.getTime();
        }

        return resultList;
    }

}
