package vn.com.unit.ep2p.rest.cms;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationDetailDto;
import vn.com.unit.cms.core.module.events.dto.EventsDto;
import vn.com.unit.cms.core.module.events.dto.EventsGuestDetailDto;
import vn.com.unit.cms.core.module.events.dto.EventsGuestSearchDto;
import vn.com.unit.cms.core.module.events.dto.EventsMasterDataDto;
import vn.com.unit.cms.core.module.events.dto.EventsProResponeDto;
import vn.com.unit.cms.core.module.events.dto.EventsSearchDto;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingCourseDto;
import vn.com.unit.core.constant.ConstantCore;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.dto.CustomerBirthdayDto;
import vn.com.unit.ep2p.admin.dto.CustomerChargeDto;
import vn.com.unit.ep2p.admin.dto.EventDetailDto;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.service.EventsService;
import vn.com.unit.ep2p.service.TrainingCourseService;
import vn.com.unit.ep2p.utils.AppStringUtils;
import vn.com.unit.ep2p.utils.LangugeUtil;

/**
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_EVENTS)
@Api(tags = { CmsApiConstant.API_CMS_EVENTS_DESCR })
public class EventsRest {
	
	@Autowired
	protected ErrorHandler errorHandler;

	@Autowired
	protected SuccessHandler successHandler;
	
	@Autowired
	private EventsService eventsService;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
    TrainingCourseService trainingCourseService;

	private Logger loggerClass = LoggerFactory.getLogger(getClass());
	
	@PostMapping(AppApiConstant.API_EDIT_EVENTS)
    @ApiOperation("Api add/edit events")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse editOrEditEvents(HttpServletRequest request, @RequestBody EventsDto dto)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
        	if (AppStringUtils.checkByRegex(dto.getContents(), "<(\\\"[^\\\"]*\\\"|'[^']*'|[^'\\\">])*>")) {
        		return this.errorHandler.handlerException(new Exception("Nội dung không hợp lệ(có chứa mã html)"), start, null, null);
        	}
        	EventsDto resObj = eventsService.editOrAddEvents(dto);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    @GetMapping(AppApiConstant.API_LIST_EVENTS_BY_DATE)
    @ApiOperation("Get all event by event date")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListEvent(HttpServletRequest request, @RequestParam(value = "eventDate", required = false) String eventMiliSecond
    , @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize
    , CommonSearchWithPagingDto searchDto)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date eventDate;
            try{
                eventDate = sdf.parse(eventMiliSecond);
            } catch (ParseException e){
                eventDate = null;
            }
            CmsCommonPagination<EventsDto> common = eventsService.getListEventByDate(eventDate, page, pageSize, searchDto);
            ObjectDataRes<EventsDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    @GetMapping(AppApiConstant.API_LIST_CUSTOMER_BIRTHDAY_BY_DATE)
    @ApiOperation("Get all customer's birthday by date")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListCustomerBirthDay(HttpServletRequest request, @RequestParam(value = "eventDate", required = false) String eventMiliSecond
    , @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize
    , @RequestParam(value= "customerRole", required = false) String customerRole
    , CommonSearchWithPagingDto searchDto)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date eventDate;
            try{
                eventDate = sdf.parse(eventMiliSecond);
            } catch (ParseException e){
                eventDate = null;
            }
            CmsCommonPagination<CustomerBirthdayDto> common = eventsService.getListCustomerBirthDay(eventDate, page, pageSize, customerRole, searchDto);
            ObjectDataRes<CustomerBirthdayDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    @GetMapping(AppApiConstant.API_LIST_CUSTOMER_CHARGE_BY_DATE)
    @ApiOperation("Get all customer's payment by date")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getCalendarCustomerCharge(HttpServletRequest request, @RequestParam(value = "eventDate", required = false) String eventMiliSecond
            , @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize
    , @RequestParam(value = "status", required = false)String status, CommonSearchWithPagingDto searchDto)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date eventDate;
            try{
                eventDate = sdf.parse(eventMiliSecond);
            } catch (ParseException e){
                eventDate = null;
            }
            CmsCommonPagination<CustomerChargeDto> common = eventsService.getListCustomerChargeByDate(eventDate, page, pageSize, status, searchDto);
            ObjectDataRes<CustomerChargeDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    @GetMapping(AppApiConstant.API_GET_ALL_EVENT_IN_MONTH)
    @ApiOperation("Get all events in a month")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getAllEventsInMonth(HttpServletRequest request, @RequestParam(value = "month", required = false) String month)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date eventDate;
            try{
                eventDate = sdf.parse(month);
            } catch (ParseException e){
                eventDate = null;
            }
            List<EventDetailDto> lstEvents = eventsService.getAllEventsInMonth(eventDate);
            return this.successHandler.handlerSuccess(lstEvents, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    @GetMapping("/get-detail-customer")
    @ApiOperation("Get all events in a month")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getDetailCustomer(HttpServletRequest request, @RequestParam(value = "customerId", required = false) String customerId)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
            CustomerInformationDetailDto customerInformationDetailDto = eventsService.getDetailCustomer(customerId);
            return this.successHandler.handlerSuccess(customerInformationDetailDto, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping(AppApiConstant.API_GET_LIST_MASTERDATA)
    @ApiOperation("Get masterdata by type")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListMasterData(HttpServletRequest request,
    		@RequestParam(value = "type", required = true) String type,
    		@RequestParam(value = "parentId", required = false) String parentId
    		)  {
        long start = System.currentTimeMillis();
        try {
            List<EventsMasterDataDto> listMasterData = eventsService.getListMasterData(type, parentId, null);
            return this.successHandler.handlerSuccess(listMasterData, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping(AppApiConstant.API_GET_LIST_EVENTS_BY_CONDITION)
    @ApiOperation("Get events by condition")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getListEventsByCondition(HttpServletRequest request,
            @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam
            ,EventsSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
    		searchDto.setCreateBy(UserProfileUtils.getUserPrincipal().getUsername());
    		searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	CmsCommonPagination<EventsDto> common = eventsService.getListEventsByCondition(searchDto);
        	ObjectDataRes<EventsDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping(AppApiConstant.API_GET_DETAIL_EVENT)
    @ApiOperation("Get detail event")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getDetailEvent(HttpServletRequest request, @RequestParam(value = "eventId", required = true) String eventId)  {
        long start = System.currentTimeMillis();
        try {
            EventsDto eventsDto = eventsService.getDetailEvent(eventId,
            		UserProfileUtils.getUserPrincipal().getUsername());
            return this.successHandler.handlerSuccess(eventsDto, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping(AppApiConstant.API_GET_QR_CODE)
    @ApiOperation("Get QRcode")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getQrCode(HttpServletRequest request, @RequestParam(value = "qrCode", required = true) String qrCode)  {
        long start = System.currentTimeMillis();
        try {
        	byte[] bytes = eventsService.readQrCode(qrCode);
            return this.successHandler.handlerSuccess(bytes, start, null, null);
        } catch (Exception ex) {
        	return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @PostMapping(AppApiConstant.API_UPDATE_EVENT)
    @ApiOperation("Update event")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse updateEvent(HttpServletRequest request, @RequestBody EventsDto dto)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        EventsProResponeDto result = new  EventsProResponeDto();
        try {
        	if (AppStringUtils.checkByRegex(dto.getContents(), "<(\\\"[^\\\"]*\\\"|'[^']*'|[^'\\\">])*>")) {
        		return this.errorHandler.handlerException(new Exception("Nội dung không hợp lệ(có chứa mã html)"), start, null, null);
        	}
        	// Check xem job co dang xu ly hay khong?
        	if (eventsService.checkProcessing(dto.getId())) {
        		result.setCode(-1);
    			result.setMessage(messageSource.getMessage("events.delete.processing", null, locale));
        	} else {
        		dto.setUpdateBy(UserProfileUtils.getUserPrincipal().getUsername());
        		eventsService.updateEvent(dto);
        		result.setCode(1);
        	}
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @DeleteMapping(AppApiConstant.API_DELETE_EVENT)
    @ApiOperation("Api delete event")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse deleteEvent(HttpServletRequest request, @RequestParam(value = "id") Long id)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        EventsProResponeDto result = new  EventsProResponeDto();
        try {
        	// Check xem job co dang xu ly hay khong?
        	if (eventsService.checkProcessing(id)) {
        		result.setCode(-1);
    			result.setMessage(messageSource.getMessage("events.delete.processing", null, locale));
        	} else {
        		eventsService.deleteEventById(id, UserProfileUtils.getUserPrincipal().getUsername());
        		result.setCode(1);
        	}
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping(AppApiConstant.API_GET_TITLE_LIST_OF_GUEST)
    @ApiOperation("Check event")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getTitleListOfGuest(HttpServletRequest request,
    		@RequestParam(value = "eventCode", required = true) String eventCode) {
        long start = System.currentTimeMillis();
        try {
        	EventsDto dto = eventsService.getParticipantInfo(eventCode);
        	BigDecimal rate = new BigDecimal(0);
        	if (!BigDecimal.ZERO.equals(dto.getQuantity())) {
        		rate = dto.getAttendanceQuantity().divide(dto.getQuantity(), 2, RoundingMode.HALF_UP)
            			.multiply(new BigDecimal(100)).setScale(0);
        	}
        	String title = String.format("Danh sách tham dự %s<br/>Tỷ lệ tham dự %s"
        			, eventCode, rate);
        	title += "%";
            return this.successHandler.handlerSuccess(title, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping(AppApiConstant.API_GET_LIST_GUESTS_EVENT)
    @ApiOperation("Get events by condition")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getListEventsGuest(HttpServletRequest request,
            @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam
            ,EventsGuestSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
        	CmsCommonPagination<EventsGuestDetailDto> common = eventsService.getListGuestsOfEvent(searchDto);
        	ObjectDataRes<EventsGuestDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping(AppApiConstant.API_CHECK_EVENT)
    @ApiOperation("Check event")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse checkEvent(HttpServletRequest request,
    		@RequestParam(value = "eventCode", required = true) String eventCode) {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        EventsProResponeDto result = new  EventsProResponeDto();
        try {
        	// Hoạt động huấn luyện
            if (eventCode != null && eventCode.contains("HL")) {
            	TrainingCourseDto trainingDto = trainingCourseService.getTrainingCourseByCode(eventCode, UserProfileUtils.getFaceMask());
            	if (trainingDto == null) {
            		result.setCode(-1);
            		result.setMessage(messageSource.getMessage("events.qr.code.invalid", null, locale));
            	} else {
            		result.setCode(1);
            	}
            } else {
            	EventsDto eventsDto = eventsService.getEventByCode(eventCode);
            	if (eventsDto != null) {
            		// Trước 1h sự kiện diễn ra hoặc đang diễn ra
            		if ("13".equals(eventsDto.getStatus()) || "2".equals(eventsDto.getStatus())) {
            			result.setCode(1);
            		} else if ("3".equals(eventsDto.getStatus())) {
            			result.setCode(-1);
            			result.setMessage(messageSource.getMessage("events.has.ended", null, locale));
            		} else {
            			result.setCode(-1);
            			result.setMessage(messageSource.getMessage("events.has.not.happen", null, locale));
            		}
            	} else {
            		result.setCode(-1);
            		result.setMessage(messageSource.getMessage("events.qr.code.invalid", null, locale));
            	}	
            }
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @PostMapping(AppApiConstant.API_CHECKIN_EVENT)
    @ApiOperation("Checkin event for guest")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse checkinEvent(HttpServletRequest request,
    		@RequestBody EventsGuestDetailDto dto)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        EventsProResponeDto result = new  EventsProResponeDto();
        try {
        	// Hoạt động huấn luyện
            if (dto.getEventCode() != null && dto.getEventCode().contains("HL")) {
            	if (trainingCourseService.checkinTrainingCourse(dto.getEventCode(), dto.getAgentCode()) > 0) {
            		result.setCode(1);
            	} else {
            		result.setCode(-1);
            		result.setMessage(messageSource.getMessage("training.attendancetime.error", null, locale));
            	}
            } else {
            	EventsDto eventsDto = eventsService.getEventByCode(dto.getEventCode());
            	dto.setEventId(eventsDto.getId());
            	dto.setEventType(eventsDto.getEventType());
            	result.setCode(1);
            	if ("2".equals(eventsDto.getEventType())) {
            		if (eventsService.updateGuest(dto) == 0) {
            			result.setCode(-1);
            			result.setMessage(messageSource.getMessage("events.guest.not.exist", null, locale));
            		}
            	} else {
            		if (eventsService.updateGuest(dto) == 0) {
            			eventsService.addGuest(dto);
            		}
            	}	
            }
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_LIST_EVENTS)
	@ApiOperation("Api export excel list events")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListEvents(HttpServletRequest request, HttpServletResponse response,
			@RequestBody EventsSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
			searchDto.setCreateBy(UserProfileUtils.getUserPrincipal().getUsername());
			resObj = eventsService.exportListEvents(searchDto, response, locale);
		} catch (Exception e) {
			loggerClass.error("##exportListEvents##", e.getMessage());
		}
		return resObj;
	}
    
    @SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_LIST_EVENT_GUESTS)
	@ApiOperation("Api export excel list events")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListEventGuests(HttpServletRequest request, HttpServletResponse response,
			@RequestBody EventsGuestSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
			resObj = eventsService.exportListGuestsOfEvent(searchDto, response, locale);
		} catch (Exception e) {
			loggerClass.error("##exportListEvents##", e.getMessage());
		}
		return resObj;
	}
}
