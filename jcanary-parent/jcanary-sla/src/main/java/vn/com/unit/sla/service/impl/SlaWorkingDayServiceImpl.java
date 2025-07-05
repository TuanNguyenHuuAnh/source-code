/*******************************************************************************
 * Class        ：SlaWorkingDayServiceImpl
 * Created date ：2021/03/10
 * Lasted date  ：2021/03/10
 * Author       ：ngannh
 * Change log   ：2021/03/10：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.constant.SlaExceptionCodeConstant;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.dto.SlaWorkingDayDto;
import vn.com.unit.sla.dto.SlaWorkingDaySearchDto;
import vn.com.unit.sla.entity.SlaWorkingDay;
import vn.com.unit.sla.repository.SlaWorkingDayRepository;
import vn.com.unit.sla.service.SlaWorkingDayService;
import vn.com.unit.sla.utils.SlaDateUtils;

/**
 * SlaWorkingDayServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaWorkingDayServiceImpl extends AbstractSlaService implements SlaWorkingDayService {
    
    private static final Logger logger = LoggerFactory.getLogger(SlaWorkingDayServiceImpl.class);

    @Autowired 
    private SlaWorkingDayRepository slaWorkingDayRepository;
    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaWorkingDayService#deleteByCalendarTypeIdAndDate(java.util.Date, java.lang.Long)
     */
    @Override
    public void deleteByCalendarTypeIdAndDate(Date date, Long calendarTypeId) throws DetailException {
        date = CommonDateUtil.truncate(date, Calendar.DATE);

        slaWorkingDayRepository.deleteByCalendarTypeIdAndDate(date, calendarTypeId);       
    }

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaWorkingDayService#saveSlaWorkingDay(vn.com.unit.sla.entity.SlaWorkingDay)
     */
    @Override
    public SlaWorkingDay saveSlaWorkingDay(SlaWorkingDay slaWorkingDay) throws Exception {
        Long id = slaWorkingDay.getId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getAccountId();
        if (null != id) {
            SlaWorkingDay oldWorkingDay = slaWorkingDayRepository.findOne(id);
            if (null != oldWorkingDay) {
                slaWorkingDay.setUpdatedId(userId);
                slaWorkingDay.setUpdatedDate(sysDate);
                slaWorkingDay.setCreatedId(oldWorkingDay.getCreatedId());
                slaWorkingDay.setCreatedDate(oldWorkingDay.getCreatedDate());
                slaWorkingDayRepository.update(slaWorkingDay);
            } else {
                logger.error("[SlaCalendarDtoServiceImpl] [saveSlaCalendarDto] data not found, id: {}", id);
                throw new DetailException(SlaExceptionCodeConstant.E201702_SLA_DATA_NOT_FOUND_ERROR, true);
            }
        } else {
            slaWorkingDay.setCreatedId(userId);
            slaWorkingDay.setCreatedDate(sysDate);
            slaWorkingDay.setUpdatedId(userId);
            slaWorkingDay.setUpdatedDate(sysDate);
            slaWorkingDayRepository.create(slaWorkingDay);
        }
        return slaWorkingDay;
    }
    
    @Override
    public List<SlaWorkingDay> getSlaWorkingDayByCondition(Date date, Long calendarTypeId) throws DetailException {
        date = CommonDateUtil.truncate(date, Calendar.DATE);
        return slaWorkingDayRepository.getSlaWorkingDayByCondition(date, calendarTypeId);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaWorkingDayService#getSlaCalendarDtoListBySearchDto(vn.com.unit.sla.dto.SlaWorkingDaySearchDto)
     */
    @Override
    public List<SlaCalendarDto> getSlaCalendarDtoListBySearchDto(SlaWorkingDaySearchDto daySearchDto) throws Exception {
        daySearchDto.setFromDate(SlaDateUtils.truncate(daySearchDto.getFromDate(), Calendar.DATE));
        daySearchDto.setToDate(SlaDateUtils.truncate(daySearchDto.getToDate(), Calendar.DATE));
        List<SlaCalendarDto> list = slaWorkingDayRepository.getSlaWorkingDayDtoListBySearchDto(daySearchDto);
        // return this.generateCalendarDtoWeekend(CalendarDtoDtoList, CalendarDtoSearchDto);
        return list;
    }

}
