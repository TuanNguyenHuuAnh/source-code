package vn.com.unit.ep2p.admin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.sla.dto.AppCalendarDto;
import vn.com.unit.ep2p.admin.sla.service.CalendarTypeAppService;
import vn.com.unit.ep2p.admin.sla.service.HolidaysService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.sla.dto.SlaCalendarAddDto;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.dto.SlaCalendarSearchDto;
import vn.com.unit.sla.dto.SlaCalendarTypeDto;
import vn.com.unit.sla.dto.SlaWorkingDaySearchDto;
//import vn.com.unit.sla.entity.SlaCalendar;
import vn.com.unit.sla.entity.SlaWorkingDay;
import vn.com.unit.sla.enumdef.DateOfWeekEnum;
import vn.com.unit.sla.service.SlaWorkingDayService;
import vn.com.unit.sla.utils.SlaDateUtils;

/**
 * PublicHolidaysController
 * 
 * @version 01-00
 * @since 01-00
 * @author trongcv
 */
@Controller
@RequestMapping(UrlConst.HOLIDAY)
public class HolidaysController {

    /** publicHolidaysService */
    // @Autowired
    // private HolidaysService publicHolidaysService;

    @Autowired
    private MessageSource msg;

    @Autowired
    private SystemConfig systemConfig;

    // @Autowired
    // private ManualService manualService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CalendarTypeAppService calendarTypeAppService;

    @Autowired
    private HolidaysService holidaysService;
    
    @Autowired
    private SlaWorkingDayService slaWorkingDayService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    
    /** SCREEN_FUNCTION_CODE */
    private static final String SCREEN_FUNCTION_CODE = RoleConstant.ACCOUNT;// HOLIDAY;

    private static final String VIEW_HOLIDAY_LIST = "/views/holidays/public-holidays-list.html";
    private static final String VIEW_HOLIDAY_TABLE = "/views/holidays/public-holidays-table.html";

    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
        // The date format to parse or output your dates
        String patternDate = (String) request.getSession().getAttribute(ConstantCore.FORMAT_DATE);
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
    }

    /**
     * getPostList
     *
     * @param vietnameseHolidaySearch
     * @param page
     * @param locale
     * @return
     * @author phatvt
     * @throws Exception
     */
    @GetMapping(UrlConst.LIST)
    public ModelAndView getPostList(@ModelAttribute(value = "slaCalendarSearchDto") SlaCalendarSearchDto slaCalendarSearchDto,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale) throws Exception {

        ModelAndView mav = new ModelAndView(VIEW_HOLIDAY_LIST);


//        // Security for this page.
//        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//
        if (slaCalendarSearchDto.getYear() == 0) {
            slaCalendarSearchDto.setYear(Calendar.getInstance().get(Calendar.YEAR));
        }
//
//        
        
        List<Integer> listYear = new ArrayList<>();

        for (int i = Calendar.getInstance().get(Calendar.YEAR) - 2; i < Calendar.getInstance().get(Calendar.YEAR) + 5; i++) {
            listYear.add(i);
        }

        Long companyId = slaCalendarSearchDto.getCompanyId();

        if ( companyId == null) {
            companyId = UserProfileUtils.getCompanyId();
            slaCalendarSearchDto.setCompanyId(companyId);
        }
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);

        // Add calendar type
        List<Select2Dto> calendarList = calendarTypeAppService.getCalendarTypeListByYearnCompany(companyId);
        mav.addObject("calendarList", calendarList);

        Long calendarTypeId = slaCalendarSearchDto.getCalendarTypeId();
        SlaCalendarTypeDto calendarType = new SlaCalendarTypeDto();
        if (slaCalendarSearchDto.getCalendarTypeId() == null && !calendarList.isEmpty()) {
            slaCalendarSearchDto.setCalendarTypeId(Long.parseLong(calendarList.get(0).getId()));
            calendarTypeId = Long.parseLong(calendarList.get(0).getId());
        }

        boolean isAdmin = UserProfileUtils.isCompanyAdmin();
        mav.addObject("isAdmin", isAdmin);

        Map<Integer, List<AppCalendarDto>> mapData = new LinkedHashMap<>();

        MessageList messageList = new MessageList();
        boolean isDisplayTable = true;
        if (!calendarList.isEmpty()) {
            // List<SlaDayOffDto> data = manualService.findAllVietnameseHolidayListByYear(vietnameseHolidaySearch);

            // data = setWeekendWhileListEmpty(data, vietnameseHolidaySearch);
            //
            //
            // for (int i = 1; i <= 12; i++) {
            // List<SlaDayOffDto> listItem = new ArrayList<>();
            // Iterator<SlaDayOffDto> iterator = data.iterator();
            //
            // Calendar cal = Calendar.getInstance();
            // while (iterator.hasNext()) {
            // SlaDayOffDto element = iterator.next();
            // cal.setTime(element.getDayOff());
            // int month = cal.get(Calendar.MONTH) + 1;
            // if (month == i) {
            // listItem.add(element);
            // iterator.remove();
            // }
            //
            // }
            // mapData.put(i, listItem);
            // }
            // common
            mapData = buildMapData( slaCalendarSearchDto, calendarTypeId);

            calendarType = calendarTypeAppService.findById(calendarTypeId);

        }

        SlaCalendarAddDto slaCalendarAddDto = new SlaCalendarAddDto();
        if (CommonCollectionUtil.isNotEmpty(companyList)) {
            slaCalendarAddDto.setCompanyId(UserProfileUtils.getCompanyId());
        }
        
        if (CommonCollectionUtil.isNotEmpty(calendarList)) {
            slaCalendarAddDto.setCalendarTypeId(Long.parseLong(calendarList.get(0).getId()));
        }

        List<DateOfWeekEnum> dateOfWeek = Arrays.asList(DateOfWeekEnum.values());
        mav.addObject("dateOfWeek", dateOfWeek);

        mav.addObject("calendarType", calendarType);
        mav.addObject("listYear", listYear);
        mav.addObject("mapData", mapData);
        mav.addObject("messageListTable", messageList);
        mav.addObject("isDisplayTable", isDisplayTable);
        
        mav.addObject("slaCalendarAddDto", slaCalendarAddDto);
        mav.addObject("slaCalendarSearchDto", slaCalendarSearchDto);
        
        String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
        mav.addObject("datePattern", datePattern);
        return mav;
    }

    private Map<Integer, List<AppCalendarDto>> buildMapData(SlaCalendarSearchDto searchDto, Long calendarTypeId) throws Exception {

        Map<Integer, List<AppCalendarDto>> mapData = new LinkedHashMap<>();

        Calendar calendar = Calendar.getInstance();
        int year = searchDto.getYear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date fromDate = SlaDateUtils.truncate(calendar.getTime(), Calendar.DATE);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        Date toDate = SlaDateUtils.truncate(calendar.getTime(), Calendar.DATE);
        
//        SlaDayOffSearchDto searchDto = new SlaDayOffSearchDto();
        searchDto.setFromDate(fromDate);
        searchDto.setToDate(toDate);
        searchDto.setCalendarTypeId(calendarTypeId);
        SlaWorkingDaySearchDto workSearchDto = objectMapper.convertValue(searchDto, SlaWorkingDaySearchDto.class);
        List<SlaCalendarDto> data = slaWorkingDayService.getSlaCalendarDtoListBySearchDto(workSearchDto);
        
        Map<Date, List<SlaCalendarDto>> mapCalendar = data.stream()
                .collect(Collectors.groupingBy(SlaCalendarDto::getCalendarDate));
        List<AppCalendarDto> appCalendarDtoList = new ArrayList<>();
//        Date toDate = searchDto.getToDate();
//        Date fromDate = searchDto.getFromDate();
        toDate = CommonDateUtil.truncate(toDate, Calendar.DATE);
        fromDate = CommonDateUtil.truncate(fromDate, Calendar.DATE);
        Date current = fromDate;
        Calendar ca = Calendar.getInstance();
        while (current.before(toDate)) {
            ca.setTime(current);
            Date dateFilter = current;
//            for (Date calendarDate : mapCalendar.keySet()) {
//                if(CommonDateUtil.isSameDay(calendarDate, dateFilter)) {
//                    appCalendarDtoList.add(new AppCalendarDto(calendarDate, "0", null));
//                } else if (!mapCalendar.containsKey(dateFilter)) {
//                    appCalendarDtoList.add(new AppCalendarDto(calendarDate, "3", null));
//                }
//                
//            }
//            appCalendarDtoList.add(new AppCalendarDto(dateFilter, "3", null));

            if (mapCalendar.containsKey(dateFilter)) {
                appCalendarDtoList.add(new AppCalendarDto(dateFilter, "0", null));
            }else {
            	int dayOfWeek = ca.get(Calendar.DAY_OF_WEEK);
                if(Calendar.SUNDAY == dayOfWeek) {
                    appCalendarDtoList.add(new AppCalendarDto(dateFilter, "8", null));
                }else if(Calendar.SATURDAY == dayOfWeek) {
                    appCalendarDtoList.add(new AppCalendarDto(dateFilter, "7", null));
                }else {

                    appCalendarDtoList.add(new AppCalendarDto(dateFilter, "3", null));
                }
            }


            ca.add(Calendar.DATE, 1);
            current = ca.getTime();

        }
       
        
        
        // data = setWeekendWhileListEmpty(data, vietnameseHolidaySearch);

        for (int i = 1; i <= 12; i++) {
            List<AppCalendarDto> listItem = new ArrayList<>();
            Iterator<AppCalendarDto> iterator = appCalendarDtoList.iterator();

            Calendar cal = Calendar.getInstance();
            while (iterator.hasNext()) {
                AppCalendarDto element = iterator.next();
                if (null != element.getCalendar()) {
                    cal.setTime(element.getCalendar());
                    int month = cal.get(Calendar.MONTH) + 1;
                    if (month == i) {
                        listItem.add(element);
                        iterator.remove();
                    }
                } else {
                    iterator.remove();
                }
            }
            mapData.put(i, listItem);
        }
        return mapData;
    }

    /*
     * reload table when change year
     * 
     */
    /**
     * getTable
     *
     * @param vietnameseHolidaySearch
     * @param model
     * @return
     * @author phatvt
     * @throws Exception
     */
    @PostMapping(UrlConst.AJAX)
    @ResponseBody
    public ModelAndView getTable(@ModelAttribute(value = "slaCalendarSearchDto") SlaCalendarSearchDto slaCalendarSearchDto, Model model, Locale locale)
            throws Exception {
        ModelAndView nav = new ModelAndView(VIEW_HOLIDAY_TABLE);

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        Long companyId = slaCalendarSearchDto.getCompanyId();
        if (companyId == null) {
            slaCalendarSearchDto.setCompanyId(UserProfileUtils.getCompanyId());
            companyId = slaCalendarSearchDto.getCompanyId();
        }
        Map<Integer, List<AppCalendarDto>> mapData = new LinkedHashMap<>();

        // Add calendar type
        List<Select2Dto> calendarList = calendarTypeAppService.getCalendarTypeListByYearnCompany(companyId);
        MessageList messageList = new MessageList();
        boolean isDisplayTable = true;
        if (!calendarList.isEmpty()) {
            // List<SlaDayOffDto> data = manualService.findAllVietnameseHolidayListByYear(vietnameseHolidaySearch);
            //
            // data = setWeekendWhileListEmpty(data, vietnameseHolidaySearch);
            //
            //
            // for (int i = 1; i <= 12; i++) {
            // List<SlaDayOffDto> listItem = new ArrayList<>();
            // Iterator<SlaDayOffDto> iterator = data.iterator();
            //
            // Calendar cal = Calendar.getInstance();
            // while (iterator.hasNext()) {
            // SlaDayOffDto element = iterator.next();
            // cal.setTime(element.getDayOff());
            // int month = cal.get(Calendar.MONTH) + 1;
            // if (month == i) {
            // listItem.add(element);
            // iterator.remove();
            // }
            //
            // }
            // mapData.put(i, listItem);
            // }
            mapData = buildMapData(slaCalendarSearchDto, slaCalendarSearchDto.getCalendarTypeId());

        }
        String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
        nav.addObject("datePattern", datePattern);
        nav.addObject("mapData", mapData);
        nav.addObject("messageListTable", messageList);
        nav.addObject("isDisplayTable", isDisplayTable);

        return nav;
    }

    // private List<SlaDayOffDto> setWeekendWhileListEmpty(List<SlaDayOffDto> data, VietnameseHolidaySearchDto vietnameseHolidaySearch) {
    // int count = 0;
    // for(SlaDayOffDto dto : data) {
    // if(dto.getDayOffType() != null && !DayOffTypeEnum.NO_VALUE.getValue().equals(dto.getDayOffType())) {
    // count++;
    // break;
    // }
    // }
    //
    // if(count == 0 && null != vietnameseHolidaySearch.getCalendarType()) {
    // data = publicHolidaysService.setAllVietnameseHolidayWhileSundayAndSaturday(vietnameseHolidaySearch);
    // }
    // return data;
    // }

	private String formatHourAndMinute(String time) {

		if (CommonStringUtil.isEmpty(time)) {
			return time;
		}

		SimpleDateFormat df = new SimpleDateFormat("HH:mm");

		try {
			df.parse(time);
			return time;
		} catch (Exception e) {
		}

		try {
			df.parse(time + ":00");
			return time + ":00";
		} catch (Exception e) {
		}

		return "00:00";
	}

    /**
     * postEdit
     *
     * @param vietnameseHolidayDto
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return
     * @author phatvt
     * @throws Exception 
     */
    @PostMapping(UrlConst.SAVE)
    @ResponseBody
    public ModelAndView postEdit(@Valid @ModelAttribute(value = "slaCalendarAddDto") SlaCalendarAddDto slaCalendarAddDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception{
        ModelAndView mav = new ModelAndView(VIEW_HOLIDAY_LIST);

		slaCalendarAddDto.setStartTimeMorning(formatHourAndMinute(slaCalendarAddDto.getStartTimeMorning()));
		slaCalendarAddDto.setStartTimeEvening(formatHourAndMinute(slaCalendarAddDto.getStartTimeEvening()));

		slaCalendarAddDto.setEndTimeMorning(formatHourAndMinute(slaCalendarAddDto.getEndTimeMorning()));
		slaCalendarAddDto.setEndTimeEvening(formatHourAndMinute(slaCalendarAddDto.getEndTimeEvening()));

//        // Security for this page.
//        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) 
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }

        // Init message list
        MessageList messageList = new MessageList();
        
        Long companyId = slaCalendarAddDto.getCompanyId();
        if(companyId==null) {
            slaCalendarAddDto.setCompanyId(UserProfileUtils.getCompanyId());
    	}
//        
//        int result = 0;
//		SlaCalendar slaDayOff = new SlaCalendar();
		List<SlaCalendarDto> listCalendar = new ArrayList<SlaCalendarDto>();
		try {
			if (slaCalendarAddDto.getCalendarTypeId() != null) {
			   listCalendar = holidaysService.generateCalendarDtoWeekend(slaCalendarAddDto);
			}
			if (CommonCollectionUtil.isNotEmpty(listCalendar)) {
//	            vietnameseHolidaySearchDto.addMessage(Message.SUCCESS, msg.getMessage("message.success.save.label", null, locale));
				messageList.setStatus(Message.SUCCESS);
				String msgError = msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);
				messageList.add(msgError);
			} else {
//	            vietnameseHolidaySearchDto.addMessage(Message.ERROR, msg.getMessage("message.save.fail", null, locale));
				messageList.setStatus(Message.ERROR);
				String msgError = msg.getMessage(ConstantCore.MSG_FAIL_SAVE, null, locale);
				messageList.add(msgError);
			}
		} catch (NoSuchMessageException e) {
			messageList.setStatus(Message.ERROR);
			String msgError = msg.getMessage(ConstantCore.MSG_FAIL_SAVE, null, locale);
			messageList.add(msgError);
		}		
		redirectAttributes.addAttribute("calendarTypeId", slaCalendarAddDto.getCalendarTypeId());
		redirectAttributes.addAttribute("companyId", slaCalendarAddDto.getCompanyId());
		//redirectAttributes.addAttribute("year", year);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        String viewName = UrlConst.REDIRECT.concat(UrlConst.HOLIDAY).concat(UrlConst.LIST);

//        Calendar cal = Calendar.getInstance();
//        cal.setTime(vietnameseHolidayDto.getDayOff());
//        vietnameseHolidaySearchDto.setYear(cal.get(Calendar.YEAR));
//        vietnameseHolidaySearchDto.setCalendarType(vietnameseHolidayDto.getCalendarTypeId());
//        vietnameseHolidaySearchDto.setCompanyId(vietnameseHolidayDto.getCompanyId());
//        vietnameseHolidaySearchDto.setNotFirst(true);
//        redirectAttributes.addFlashAttribute("vietnameseHolidaySearch", vietnameseHolidaySearchDto);
        mav.setViewName(viewName);
        return mav;
    }

    /**
     * getVietnameseHoliday
     *
     * @param vietnameseHolidayDate
     * @return
     * @author phatvt
     * @throws DetailException
     * @throws ParseException 
     */
    @PostMapping(UrlConst.VIETNAM_HOLIDAY)
    @ResponseBody
    public SlaCalendarAddDto getVietnameseHoliday(@RequestParam(value = "vietnameseHolidayDate") Date vietnameseHolidayDate,
            @RequestParam(name = "companyId", required = false) Long companyId, @RequestParam(name = "calendarType") Long calendarType)
            throws DetailException, ParseException {
        if (companyId == null) {
            companyId = UserProfileUtils.getCompanyId();
        }
        SlaCalendarAddDto calendarAddDto = new SlaCalendarAddDto();
        List<SlaWorkingDay> listWorkingDay = slaWorkingDayService.getSlaWorkingDayByCondition(vietnameseHolidayDate, calendarType);
//        Calendar c = Calendar.getInstance();
//        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Calendar cal =  Calendar.getInstance();
        cal.setTime(vietnameseHolidayDate);

        if(CommonCollectionUtil.isNotEmpty(listWorkingDay)) {
            for (SlaWorkingDay slaWorkingDay : listWorkingDay) {
                // replace with your start date string

				Date startTime = new Date();
				try {
					startTime = df.parse(slaWorkingDay.getStartTime());
				} catch (Exception e) {
					try {
						startTime = df.parse(slaWorkingDay.getStartTime() + ":00");
					} catch (Exception skip) {
						startTime = df.parse("00:00");
					}
				}

                Calendar gc =  Calendar.getInstance();
                gc.setTime(startTime);
             
                if(gc.get(Calendar.AM_PM) == Calendar.AM) {
                    calendarAddDto.setStartTimeMorning(slaWorkingDay.getStartTime());
                    calendarAddDto.setEndTimeMorning(slaWorkingDay.getEndTime());
                }else {
                    calendarAddDto.setStartTimeEvening(slaWorkingDay.getStartTime());
                    calendarAddDto.setEndTimeEvening(slaWorkingDay.getEndTime()); 
                }

               }
           
            calendarAddDto.setDescription(listWorkingDay.get(0).getDescription());

        }
        List<Integer> listDate = new ArrayList<Integer>();
        listDate.add(cal.get(Calendar.DAY_OF_WEEK));
        calendarAddDto.setListDate(listDate);

//        calendar.set(Calendar.HOUR_OF_DAY,)
        calendarAddDto.setCalendarTypeId(calendarType);
        calendarAddDto.setCompanyId(companyId);
           
        return calendarAddDto;
        // return manualService.findTop1DateByCondition(vietnameseHolidayDate, companyId, calendarType);
//        return? null;
    }

    /**
     * getVietnameseHoliday
     *
     * @param vietnameseHolidayDate
     * @return
     * @author hiepth
     */
    @PostMapping(value = "/getListTypeCalendar")
    @ResponseBody
    public List<Select2Dto> getListTypeCalendar(@RequestParam(name = "companyId", required = false) Long companyId) {
        /*
         * if(companyId==null) { companyId = UserProfileUtils.getCompanyId(); }
         */
        List<Select2Dto> result = calendarTypeAppService.getCalendarTypeListByYearnCompany(companyId);
        return result;
    }

    @PostMapping(value = "/getCalendarType")
    @ResponseBody
    public SlaCalendarTypeDto getListTypeById(@RequestParam(name = "calendarTypeId", required = false) Long calendarTypeId) {
        SlaCalendarTypeDto calendarType = calendarTypeAppService.findById(calendarTypeId);
        return calendarType;
    }

}
