package vn.com.unit.ep2p.admin.sla.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.exception.SystemException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.ManualService;
import vn.com.unit.ep2p.admin.sla.dto.VietnameseHolidaySearchDto;
import vn.com.unit.ep2p.admin.sla.repository.VietnameseHolidayRepository;
import vn.com.unit.ep2p.admin.sla.service.HolidaysService;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.entity.SlaCalendar;
import vn.com.unit.sla.service.impl.SlaCalendarServiceImpl;

/**
 * @author trongcv
 *
 */

@Service
@Primary
@Transactional(rollbackFor = Exception.class)
public class HolidaysServiceImpl extends SlaCalendarServiceImpl implements HolidaysService, AbstractCommonService {

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    VietnameseHolidayRepository vietnameseHolidayRepository;
    
    @Autowired
    CommonService comService;
    
    @Autowired
    private ManualService manualService;

    @Override
    @Transactional
    public int addOrEditVietnameseHoliday(SlaCalendarDto vietnameseHolidayDto) {
//        Long userNameLogin = UserProfileUtils.getAccountId();
//        Date vietnameseHolidayDate = vietnameseHolidayDto.getCalendarDate();
//
//        // update data Vietnamese_Holiday table
//        SlaCalendar vietnameseHoliday = new SlaCalendar();

        // check existence
//        int isDateExisted = vietnameseHolidayRepository.findExistenceDate(vietnameseHolidayDate, vietnameseHolidayDto.getCompanyId(), vietnameseHolidayDto.getCalendarTypeId());
//
//        if (isDateExisted > 0) {
//            vietnameseHoliday = manualService.findTop1DateByCondition(vietnameseHolidayDate, vietnameseHolidayDto.getCompanyId(), vietnameseHolidayDto.getCalendarTypeId());
//            if (null == vietnameseHoliday) {
//                throw new BusinessException("Not found VietnameseHoliday by date= " + vietnameseHolidayDate.toString());
//            }
//            vietnameseHoliday.setUpdatedId(userNameLogin);
//            vietnameseHoliday.setUpdatedDate(comService.getSystemDateTime());
//
//        } else {
//            vietnameseHoliday.setCreatedId(userNameLogin);
//            vietnameseHoliday.setCreatedDate(comService.getSystemDateTime());
//        }
//        vietnameseHoliday.setDescription(vietnameseHolidayDto.getDescription());
//        vietnameseHoliday.setDayOff(vietnameseHolidayDto.getDayOff());
////        vietnameseHoliday.setCompanyId(vietnameseHolidayDto.getCompanyId());
//        vietnameseHoliday.setCalendarTypeId(vietnameseHolidayDto.getCalendarTypeId());
		try {
//			if (isDateExisted > 0 && vietnameseHolidayDto.getIsHoliday()) {
//				vietnameseHolidayRepository.editVietnameseHoliday(vietnameseHoliday);
//			} else if (isDateExisted > 0 && !vietnameseHolidayDto.getIsHoliday()) {
//				vietnameseHolidayRepository.deleteVietnameseHoliday(vietnameseHoliday.getVietnameseHolidayDate(), vietnameseHolidayDto.getCompanyId(), vietnameseHolidayDto.getCalendarType());
//			} else if (isDateExisted == 0 && vietnameseHolidayDto.getIsHoliday()) {
//				vietnameseHolidayRepository.addVietnameseHoliday(vietnameseHoliday);
//			}
			return 1;
		} catch (Exception ex) {
			throw new SystemException(ex);

		}
    }

    /**
     * findAllVietnameseHolidayListByYear
     *
     * @return
     * @author trongcv
     */
    @Override
    public List<SlaCalendarDto> findAllVietnameseHolidayListByYear(VietnameseHolidaySearchDto vietnameseHolidaySearchDto) {
        return vietnameseHolidayRepository.findAllVietnameseHolidayListByYear(vietnameseHolidaySearchDto);
    }
    
    /**
     * findTop1DateByCondition
     *
     * @param vietnameseHolidayDate
     * @return VietnameseHoliday
     * @author hiepth
     */
    @Override
    public SlaCalendar findTop1DateByCondition(Date vietnameseHolidayDate, Long companyId, Long calendarType) {
        return vietnameseHolidayRepository.findTop1DateByCondition(vietnameseHolidayDate, companyId, calendarType);
    }
    
    /**
     * findAllVietnameseHolidayListByYearOracle
     *
     * @param vietnameseHolidaySearchDto
     * @return List<VietnameseHolidayDto>
     * @author hiepth
     */
    @Override
    public SlaCalendar findTop1DateByConditionOracle(Date vietnameseHolidayDate, Long companyId, Long calendarType) {
        return vietnameseHolidayRepository.findTop1DateByConditionOracle(vietnameseHolidayDate, companyId, calendarType);
    }

	@Override
	public List<SlaCalendarDto> setAllVietnameseHolidayWhileSundayAndSaturday(
			VietnameseHolidaySearchDto vietnameseHolidaySearch) {
		List<SlaCalendarDto> data = manualService.findAllVietnameseHolidayListByYear(vietnameseHolidaySearch);
//		int year = vietnameseHolidaySearch.getYear();
//		Long companyId = vietnameseHolidaySearch.getCompanyId();
//		Long calendarType = vietnameseHolidaySearch.getCalendarType();
//		for(SlaCalendarDto dto : data) {
////			Date d = dto.getDayOff();
////			Calendar c = Calendar.getInstance();
////			c.setTime(dto.getDayOff());
////			if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
////				dto.setDayOffType(CalendarDtoTypeEnum.FULL_DAY.getValue());
////				dto.setDescription("auto gen");
////				dto.setCalendarTypeId(calendarType);
//////				dto.setCompanyId(companyId);
////				int temp = addOrEditVietnameseHoliday(dto);
////			}
//		}
		return data;
	}

	@Override
	public vn.com.unit.common.service.JCommonService getCommonService() {
		return comService;
	}

}
