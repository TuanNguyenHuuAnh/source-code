/*******************************************************************************
 * Class        :CalendarTypeController
 * Created date :2019/06/25
 * Lasted date  :2019/06/25
 * Author       :HungHT
 * Change log   :2019/06/25:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
//import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.sla.dto.CalendarTypeSearchDto;
import vn.com.unit.ep2p.admin.sla.enumdef.CalendarTypeSearchEnum;
import vn.com.unit.ep2p.admin.sla.service.CalendarTypeAppService;
import vn.com.unit.ep2p.admin.validators.CalendarTypeValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.export.util.SearchUtil;
import vn.com.unit.ep2p.utils.ConditionSearchUtils;
import vn.com.unit.sla.dto.SlaCalendarTypeDto;
import vn.com.unit.sla.entity.SlaCalendarType;

/**
 * CalendarTypeController
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Controller
@RequestMapping("/calendar-type")
public class CalendarTypeController {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(CalendarTypeController.class);

	@Autowired
    CalendarTypeValidator calendarTypeValidator;

    @Autowired
    CalendarTypeAppService calendarTypeAppService;

    @Autowired
    MessageSource msg;
    
    @Autowired
    SystemConfig systemConfig;
    
    @Autowired
    CompanyService companyService;

//    private static final String SCREEN_FUNCTION_CODE = RoleConstant.CALENDAR_TYPE;

    private static final String MAV_CALENDAR_TYPE_LIST = "/views/calendar-type/calendar-type-list.html";

    private static final String MAV_CALENDAR_TYPE_TABLE = "/views/calendar-type/calendar-type-table.html";

	private static final String MAV_CALENDAR_TYPE_DETAIL = "/views/calendar-type/calendar-type-detail.html";
	
	private static final String MAV_CALENDAR_TYPE_ADD = "/views/calendar-type/calendar-type-add.html";

    private static final String OBJ_SEARCH = "search";

    private static final String OBJ_PAGE_WRAPPER = "pageWrapper";

	private static final String OBJECT_DTO = "objectDto";
	
	private static final String URL_REDIRECT = "urlRedirect";
	
//	private static final String START_MORNING_TIME_DEFAULT = "08:00";
//	
//	private static final String END_MORNING_TIME_DEFAULT = "12:00";
//	
//	private static final String START_AFTERNOON_TIME_DEFAULT = "13:00";
//	
//	private static final String END_AFTERNOON_TIME_DEFAULT = "17:00";

    /**
     * dateBinder
     * 
     * @param binder
     * @param request
     * @param locale
     * @author HungHT
     */
    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        // The date format to parse or output your dates
        String patternDate = (String) request.getSession().getAttribute(ConstantCore.FORMAT_DATE);
        if (patternDate == null) {
            patternDate = ConstantCore.FORMAT_DATE_FULL;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
    }

    /**
     * getCalendarTypeList
     * 
     * @param search
     * @param pageSizeParam
     * @param pageParam
	 * @param locale
     * @return
     * @author HungHT
     * @throws DetailException 
     */
	@RequestMapping(value = { UrlConst.ROOT, UrlConst.LIST }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getCalendarTypeList(
			@ModelAttribute(value = OBJ_SEARCH) CalendarTypeSearchDto search,
			Pageable pageable,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale, HttpServletRequest request) throws DetailException {
		
		// Security for this page.
//		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}
		List<String> order = search.getOrderColumn();
		Object session = request.getSession().getAttribute("orderColumn");
		if(order == null) {
			if(session == null) {
				order = Arrays.asList("companyName", "code", "name", "workingHours", "description");
				session = order;
			}else {
				request.getSession().setAttribute("orderColumn", session);
			}
		}
		ModelAndView mav = new ModelAndView(MAV_CALENDAR_TYPE_LIST);
		// hide column
		String sName = search.getSName();
		String sWorkingHours = search.getSWorkingHours();
		String sDescription = search.getSDescription();
		
		saveSessionShowHideColumn(request, "sName", sName, mav);
		saveSessionShowHideColumn(request, "sWorkingHours", sWorkingHours, mav);
		saveSessionShowHideColumn(request, "sDescription", sDescription, mav);
		
		// set init search
        SearchUtil.setSearchSelect(CalendarTypeSearchEnum.class,mav);
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
//        Long companyId = UserProfileUtils.getCompanyId();
//        search.setCompanyId(companyId);
		// Init page size
        int pageSize = pageSizeParam
                .orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE, search.getCompanyId()));
		int page = pageParam.orElse(1);
		
		 // Session Search
        ConditionSearchUtils<CalendarTypeSearchDto> searchUtil = new ConditionSearchUtils<CalendarTypeSearchDto>();
        String[] urlContains = new String[] { "calendar-type/add", "calendar-type/edit", "calendar-type/detail", "calendar-type/list" };
        search = searchUtil.getConditionSearch(this.getClass(), search, urlContains, request, page, pageSize);
        pageSize = Optional.ofNullable(search.getPageSize()).orElse(pageSize);
        page = Optional.ofNullable(search.getPage()).orElse(page);
        
		// Get List
		search.setOrderColumn(null);
		PageWrapper<SlaCalendarTypeDto> pageWrapper = calendarTypeAppService.getCalendarTypeList(search, pageSize, page, pageable);
		// Object mav
		search.setFieldValues(search.getSearchKeyIds());		
		mav.addObject("order",session);
		mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
		return mav;
	}
	
	public void saveSessionShowHideColumn(HttpServletRequest request, String idCheckbox, String getId, ModelAndView mav) {
		Object session = request.getSession().getAttribute(idCheckbox);
		if(session == null) {
			if(getId != null) {
				session = getId;
			}else {
				session = 1;
			}
		}else {
			getId = session.toString();
		}
		request.getSession().setAttribute(idCheckbox, getId);
		mav.addObject(idCheckbox, session);
	}

    /**
     * getCalendarTypeTable
     * 
     * @param search
     * @param pageSizeParam
     * @param pageParam
	 * @param locale
     * @return
     * @author HungHT
     * @throws DetailException 
     */
	@RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
	public ModelAndView getCalendarTypeTable(
			@ModelAttribute(value = OBJ_SEARCH) CalendarTypeSearchDto search,
			Pageable pageable,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale, HttpServletRequest request) throws DetailException {

		// Security for this page.
//		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}
				
		List<String> order = search.getOrderColumn();
		Object session = request.getSession().getAttribute("orderColumn");
		if(order == null) {
			if(session == null) {
				order = Arrays.asList("companyName", "code", "name", "workingHours", "description");
				session = order;
			}else {
				request.getSession().setAttribute("orderColumn", session);
			}
		}else {
			session = order;
			request.getSession().setAttribute("orderColumn", session);
		}
		
		ModelAndView mav = new ModelAndView(MAV_CALENDAR_TYPE_TABLE);
		//hide column
		String sName = search.getSName();
		String sWorkingHours = search.getSWorkingHours();
		String sDescription = search.getSDescription();
	
		saveSessionShowHideColumnAjax(request, "sName", sName, mav);
		saveSessionShowHideColumnAjax(request, "sWorkingHours", sWorkingHours, mav);
		saveSessionShowHideColumnAjax(request, "sDescription", sDescription, mav);
		
		// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);

		// Get List
		search.setOrderColumn(null);
 		PageWrapper<SlaCalendarTypeDto> pageWrapper = calendarTypeAppService.getCalendarTypeList(search, pageSize, page, pageable);
 		ConditionSearchUtils<CalendarTypeSearchDto> searchUtil = new ConditionSearchUtils<CalendarTypeSearchDto>();
        searchUtil.setConditionSearch(request, this.getClass(), search, page, pageSize);
		// Object mav		
		mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
		mav.addObject("search", search);
		mav.addObject("order",session);
		return mav;
	}
	
	public void saveSessionShowHideColumnAjax(HttpServletRequest request, String idCheckbox, String getId, ModelAndView mav) {
		Object session = request.getSession().getAttribute(idCheckbox);
		if(session == null) {
			if(getId != null) {
				session = getId;
			}else {
				session = 1;
			}
		}else {
			if(session.toString().equals("1") && !getId.equals("0")) {
				getId = session.toString();
			}else {
				session = getId;
			}			
		}
		request.getSession().setAttribute(idCheckbox, getId);
		mav.addObject(idCheckbox, session);
	}

	/**
     * getCalendarTypeAdd
     * 
     * @param id
	 * @param locale
     * @return
     * @author HungHT
	 * @throws DetailException 
     */
    @RequestMapping(value = { UrlConst.ADD, UrlConst.EDIT }, method = RequestMethod.GET)
    public ModelAndView getCalendarTypeAdd(@RequestParam(value = "id", required = false) Long id, Locale locale) throws DetailException {

		// Security for this page.
//		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}
        ModelAndView mav = new ModelAndView(MAV_CALENDAR_TYPE_ADD);

		// Object dto
        SlaCalendarTypeDto objectDto = null;
		// URL ajax redirect
		StringBuilder urlRedirect = new StringBuilder("calendar-type");
		if (id != null) {
			objectDto = calendarTypeAppService.getCalendarTypeDtoById(id);
			// Security for data 
//	        if (null == objectDto || !UserProfileUtils.hasRoleForCompany(objectDto.getCompanyId())) {
//	            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//	        }
			urlRedirect.append(UrlConst.EDIT.concat("?id=").concat(id.toString()));
		} else {
			objectDto = new SlaCalendarTypeDto();
//			objectDto.setDisplayOrder(calendarTypeService.findMaxDisplayOrderByCompanyId(UserProfileUtils.getCompanyId())+1);
			objectDto.setCompanyId(UserProfileUtils.getCompanyId());
//			objectDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
//			objectDto.setStartMorningTime(START_MORNING_TIME_DEFAULT);
//			objectDto.setEndMorningTime(END_MORNING_TIME_DEFAULT);
//			objectDto.setStartAfternoonTime(START_AFTERNOON_TIME_DEFAULT);
//			objectDto.setEndAfternoonTime(END_AFTERNOON_TIME_DEFAULT);
			urlRedirect.append(UrlConst.ADD);
		}
		
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
		mav.addObject("companyList", companyList);
		
		// Object mav
		mav.addObject(OBJECT_DTO, objectDto);
		mav.addObject(URL_REDIRECT, urlRedirect.toString());
        return mav;
    }
    
    /**
     * getCalendarTypeDetail
     * 
     * @param id
	 * @param locale
     * @return
     * @author XuanN
     * @throws DetailException 
     */
    @RequestMapping(value = { UrlConst.DETAIL}, method = RequestMethod.GET)
    public ModelAndView getCalendarTypeDetail(@RequestParam(value = "id", required = false) Long id, Locale locale) throws DetailException {

		// Security for this page.
//		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//		    return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}
        ModelAndView mav = new ModelAndView(MAV_CALENDAR_TYPE_DETAIL);

		// Object dto
        SlaCalendarTypeDto objectDto = null;
		// URL ajax redirect
		StringBuilder urlRedirect = new StringBuilder("calendar-type");
		if (id != null) {
			objectDto = calendarTypeAppService.getCalendarTypeDtoById(id);
			// Security for data 
//			 if (null == objectDto || (objectDto.getCompanyId() != null && !UserProfileUtils.hasRoleForCompany(objectDto.getCompanyId()))) {
//		            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		        }
			urlRedirect.append(UrlConst.DETAIL.concat("?id=").concat(id.toString()));
		}	
		 // Add company list
		List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
		
		// Object mav
		mav.addObject(OBJECT_DTO, objectDto);
		mav.addObject(URL_REDIRECT, urlRedirect.toString());
        return mav;
    }

	/**
     * saveCalendarTypeDetail
     * 
     * @param objectDto
	 * @param urlRedirect
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return
     * @author HungHT
     */
	@RequestMapping(value = UrlConst.SAVE, method = { RequestMethod.POST })
	public ModelAndView saveCalendarTypeAdd(@ModelAttribute(value = OBJECT_DTO) SlaCalendarTypeDto objectDto,
			@RequestParam(value = "url", required = false) String urlRedirect, BindingResult bindingResult, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {

		// Security for this page.
//		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}
		ModelAndView mav = new ModelAndView(MAV_CALENDAR_TYPE_ADD);
		// Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        boolean saveFlg = false;
        String msgContent = null;
		SlaCalendarType objectSave = null;

        // Save
        try {
			// Validate
			calendarTypeValidator.validate(objectDto, bindingResult);

            if (bindingResult.hasErrors()) {
                // Add message error
                messageList.setStatus(Message.ERROR);
                String msgError = msg.getMessage(ConstantCore.MSG_FAIL_SAVE, null, locale);
                messageList.add(msgError);

                // Add company list
                List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
                mav.addObject("companyList", companyList);
                mav.addObject(ConstantCore.MSG_LIST, messageList);
                mav.addObject(OBJECT_DTO, objectDto);
                return mav;
            }

            objectSave = calendarTypeAppService.saveCalendarType(objectDto);
            if (objectSave != null) {
                saveFlg = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // Check save
        if (saveFlg) {
			// Add message success
            if (objectDto.getId() == null) {
                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
            } else {
                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
            }
            messageList.add(msgContent);
            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        } else {
            // Add message error
            messageList.setStatus(Message.ERROR);
            if (objectDto.getId() == null) {
                msgContent = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
            } else {
                msgContent = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            }
            messageList.add(msgContent);

			// Init screen
			calendarTypeAppService.initScreenDetail(mav, objectDto, locale);

			// Object mav
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject(OBJECT_DTO, objectDto);
            mav.addObject(URL_REDIRECT, urlRedirect);
            return mav;
        }

		// Redirect
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("calendar-type").concat(UrlConst.EDIT);
        mav.setViewName(viewName);
        redirectAttributes.addAttribute("id", objectSave.getId());
        return mav;
	}

	 /**
     * deleteCalendarTypeDetail
     * 
     * @param search
     * @param id
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return
     * @author HungHT
     */
    @RequestMapping(value = UrlConst.DELETE, method = { RequestMethod.POST })
    public ModelAndView deleteCalendarTypeDetail(@ModelAttribute(value = OBJ_SEARCH) CalendarTypeSearchDto search,
            @RequestParam(value = "id") Long id, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        // Security for this page.
//        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//               && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//               && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        boolean deleteFlg = false;
        String msgContent = null;

        // delete
        try {
            deleteFlg = calendarTypeAppService.deleteCalendarType(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // Check delete
        if (deleteFlg) {
            // Add message success
            msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        } else {
            // Add message error
            messageList.setStatus(Message.ERROR);
            msgContent = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
        }
		messageList.add(msgContent);
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addFlashAttribute(OBJ_SEARCH, search);

        // Redirect
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("calendar-type").concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);
        return mav;
    }
    
    /**
	 * postSortPage
	 *
	 * @param locale
	 * @return ModelAndView
	 * @author hand
	 */
	@RequestMapping(value = "/list/sort", method = RequestMethod.POST)
	public ModelAndView postSortPage(
			@RequestBody SlaCalendarTypeDto editDto, Locale locale, RedirectAttributes redirectAttributes) {
		// Security for this page.

		// uppdate sort all
		calendarTypeAppService.updateSortAll(editDto.getSortOderList());

		// update success redirect sort list page
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("calendar-type").concat(UrlConst.LIST);
		ModelAndView mav = new ModelAndView(viewName);

		// Init message success list
//		MessageList messageList = new MessageList(Message.SUCCESS);
//		String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
//		messageList.add(msgInfo);

//		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		return mav;
	}
	
	@RequestMapping(value = "/order", method = { RequestMethod.POST })
	public ModelAndView getSort(
			@ModelAttribute(value = OBJ_SEARCH) CalendarTypeSearchDto search,
			//@ModelAttribute(value = "orderColumn") CalendarTypeSearchDto orderColumn,
			Pageable pageable,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale, HttpServletRequest request) throws DetailException {	
		// Get List
		List<String> order = search.getOrderColumn();
		request.getSession().setAttribute("orderColumn", order);
		Object session = request.getSession().getAttribute("orderColumn");
	
	 	ModelAndView mav = new ModelAndView(MAV_CALENDAR_TYPE_TABLE);
		// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);
		
		search.setOrderColumn(null);
 		PageWrapper<SlaCalendarTypeDto> pageWrapper = calendarTypeAppService.getCalendarTypeList(search, pageSize, page, pageable);
 		
		// Object mav
		mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
		
		mav.addObject("order",session);
		
		return mav;
	}

}