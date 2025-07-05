package vn.com.unit.ep2p.rest.sam;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.events.dto.EventsMasterDataDto;
import vn.com.unit.cms.core.module.sam.dto.ActErrMessageDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitiesResponse;
import vn.com.unit.cms.core.module.sam.dto.ActivitiesStatusDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitityDetailDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitityRequest;
import vn.com.unit.cms.core.module.sam.dto.CategoryDto;
import vn.com.unit.cms.core.module.sam.dto.OrgLocationResponse;
import vn.com.unit.cms.core.module.sam.dto.ParticipantDto;
import vn.com.unit.cms.core.module.sam.entity.SamActivity;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.dto.AgentInfoDto;
import vn.com.unit.ep2p.admin.dto.RolePermissionDto;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.sam.service.SamExportCsvService;
import vn.com.unit.ep2p.sam.service.SamService;
import vn.com.unit.ep2p.sam.validator.ActivityValidation;
import vn.com.unit.ep2p.service.EventsService;
import vn.com.unit.ep2p.utils.LangugeUtil;

/**
 * @author ntr.bang
 * SR16172 - create date 20/4/2024 - Add API get role permission (getRolesPermission and getAgentTypeAndRoles)
 * Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 * 				 15/05/2024	nt.tinh SR16451 - Enhance SAM mADP/ADPortal
 */
@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_SAM)
@Api(tags = { CmsApiConstant.API_CMS_SAM_DESCR })
public class SamRest {

	@Autowired
	protected ErrorHandler errorHandler;

	@Autowired
	protected SuccessHandler successHandler;

	@Autowired
	private SamExportCsvService exportCsvService;

	@Autowired
	private SamService service;

	@Autowired
	private EventsService eventsService;
	
	@Autowired
	MessageSource messageSource;

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private static final String FUNCTION_ID = "SCREEN#FONTEND#SAM";

	@GetMapping(AppApiConstant.API_ACTIVITIES_SEARCH)
	@ApiOperation("Get all activities by event date")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getActivityInfo(HttpServletRequest request,
			@RequestParam(value = "agentCode", required = false) String agentCode,
			@RequestParam(value = "agentType", required = false) String agentType,
			@RequestParam(value = "requestDate", required = true) String requestDate,
			@RequestParam(value = "actCode", required = false) String actCode,
			@RequestParam(value = "planDate", required = false) String planDate,
			@RequestParam(value = "actStatus", required = false) Long statusId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false) Integer pageSize
			, CommonSearchWithPagingDto searchDto) {
		
		log.info("Begin getActivityInfo");
		log.debug("Request date: ", requestDate);
		
		long start = System.currentTimeMillis();
		try {
			if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
			CmsCommonPagination<ActivitiesResponse> common = service.searchActivities(agentCode, agentType, requestDate, actCode, planDate, statusId, page, pageSize, searchDto);
			ObjectDataRes<ActivitiesResponse> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping(AppApiConstant.API_ACTIVITY_FIND_TOP_FIVE)
	@ApiOperation("Find top five activities code")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse findFiveActivities(HttpServletRequest request, @RequestParam(value = "number", required = false) Integer number
			, @RequestParam(value = "agentCode", required = false) String agentCode) {
		
		log.info("Begin findFiveActivities");
		long start = System.currentTimeMillis();
		try {
			if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
			if (number == null) {
				number = 5;
			}
			agentCode = UserProfileUtils.getFaceMask();
			List<String> res = service.findFiveActivities(agentCode, number);
			return this.successHandler.handlerSuccess(res, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping(AppApiConstant.API_ACTIVITY_FIND_ALL_STATUS)
	@ApiOperation("Find all activities status")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse findAllActivitiesStatus(HttpServletRequest request) {
		
		log.info("Begin findAllActivitiesStatus");
		long start = System.currentTimeMillis();
		try {
			List<ActivitiesStatusDto> res = service.findAllActivitiesStatus();
			return this.successHandler.handlerSuccess(res, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping(AppApiConstant.API_FIND_ALL_PARTICIPANT)
	@ApiOperation("Find all paticipants")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse findAllParticipants(HttpServletRequest request) {
		
		log.info("Begin findAllPaticipants");
		long start = System.currentTimeMillis();
		try {
			List<ParticipantDto> res = service.findAllParticipants();
			return this.successHandler.handlerSuccess(res, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping(AppApiConstant.API_ACTIVITY_FIND_ALL_CATEGORIES)
	@ApiOperation("Find all categories")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse findAllCategories(HttpServletRequest request) {
		
		log.info("Begin findAllCategories");
		long start = System.currentTimeMillis();
		try {
			List<CategoryDto> res = service.findAllCategories();
			return this.successHandler.handlerSuccess(res, start, null, null);
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
            List<EventsMasterDataDto> res = eventsService.getListMasterData(type, parentId, null);
            return this.successHandler.handlerSuccess(res, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_ACTIVITY_DETAIL)
	@ApiOperation("Get activity detail by activity code")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getActivitiDetail(HttpServletRequest request, 
			@RequestParam(value = "actCode", required = true) String actCode,
			@RequestParam(value = "agentType", required = true) String agentType) {
		
		log.info("Begin getActivitiDetail");
		long start = System.currentTimeMillis();
		try {
			if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
			ActivitityDetailDto res = service.getActivityDetail(UserProfileUtils.getFaceMask(), agentType, actCode);
			return this.successHandler.handlerSuccess(res, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_ACTIVITY_ORGANIZATION_LOCATION)
	@ApiOperation("Get organization location")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getOrgLocation(HttpServletRequest request, @RequestParam(value = "agentCode", required = false) String agentCode,
			@RequestParam(value = "agentType", required = false) String agentType) {
		
		log.info("Begin getOrgLocation");
		long start = System.currentTimeMillis();
		try {
			OrgLocationResponse res = service.getOrglocation(agentCode, agentType);
			return this.successHandler.handlerSuccess(res, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@PostMapping(AppApiConstant.API_ACTIVITY_CREATE)
    @ApiOperation("Create a activity")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse createActivity(HttpServletRequest request, @RequestParam(required = false, name = "listFile")List<MultipartFile> listFile,
			@RequestParam(required = false, name = "activityDto") String dto)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        ActErrMessageDto result = new  ActErrMessageDto();
        Gson gson = new Gson();
        try {
        	if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
        	if (listFile != null) {
        		for (MultipartFile multipartFile : listFile) {
            		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename()).toLowerCase();
        			if (!"pdf".equals(extension) 
        					&& !"docx".equals(extension) && !"doc".equals(extension) 
        					&& !"xlsx".equals(extension) && !"xls".equals(extension)) {
        				return this.errorHandler.handlerException(new Exception("File không hợp lệ."), start, null, null);
        			}
            	}	
        	}
        	ActivitityRequest activitityDto = gson.fromJson(dto, ActivitityRequest.class);
        	// Validate
        	result = ActivityValidation.validate(messageSource, locale, activitityDto);
        	if (StringUtils.isNotBlank(result.getMessage())) {
        		result.setCode(-1);
    			result.setMessage(result.getMessage());
        	} else {
        		if (!StringUtils.isEmpty(UserProfileUtils.getFaceMask())) {
        			activitityDto.setAgentCode(UserProfileUtils.getFaceMask());
        		}
        		int code = service.createActivity(activitityDto, listFile);
        		if (code == -1) {
        			result.setCode(code);
        			result.setMessage(result.getMessage());
        		} else {
        			result.setCode(code);
        		}
        	}
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	
	@PostMapping(AppApiConstant.API_ACTIVITY_UPDATE)
    @ApiOperation("Change status of a activity")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse updateActivity(HttpServletRequest request, @RequestParam(required = false, name = "listFile")List<MultipartFile> listFile,
			@RequestParam(required = false, name = "activityDto") String dto)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        ActErrMessageDto result = new  ActErrMessageDto();
        Gson gson = new Gson();
        try {
        	if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
        	if (listFile != null) {
        		for (MultipartFile multipartFile : listFile) {
            		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename()).toLowerCase();
        			if (!"jpeg".equals(extension) && !"jpg".equals(extension) && !"png".equals(extension) 
        					&& !"heic".equals(extension) && !"jfif".equals(extension)) {
        				return this.errorHandler.handlerException(new Exception("File không hợp lệ."), start, null, null);
        			}
        		}
        	}
        	ActivitityRequest activitityDto = gson.fromJson(dto, ActivitityRequest.class);
        	// Check existing
        	SamActivity entity = service.findById(activitityDto.getId());
        	if (entity == null || entity.getId() == null) {
        		result.setCode(-1);
        		result.setMessage(messageSource.getMessage("activity.does.not.exist", null, locale));
        	} else {
        		result = ActivityValidation.validateChangeStatus(messageSource, locale, activitityDto);
        		if (StringUtils.isNotBlank(result.getMessage())) {
            		result.setCode(-1);
        			result.setMessage(result.getMessage());
            	} else {
            		if (!StringUtils.isEmpty(UserProfileUtils.getFaceMask())) {
            			activitityDto.setAgentCode(UserProfileUtils.getFaceMask());
            		}
            		int code = service.updateActivity(activitityDto, entity, listFile);
            		if (code == -1) {
            			result.setCode(code);
            			result.setMessage(result.getMessage());
            		} else {
            			result.setCode(code);
            		}
            	}
        	}
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@SuppressWarnings("rawtypes")
	@GetMapping(AppApiConstant.API_ACTIVITY_EXPORT_CSV)
	@ApiOperation("Export CSV, all data from data to date of activities")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	public ResponseEntity exportCsv(HttpServletRequest request, @RequestParam(value = "fromDate", required = true) String fromDate
			, @RequestParam(value = "toDate", required = true) String toDate, @RequestParam(value = "isDetail", required = false) Boolean isDetail) {
		ResponseEntity resObj = null;
		log.info("Begin exportCsv");
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			resObj = exportCsvService.exportCsv(fromDate, toDate, isDetail, locale);
				
		} catch (Exception e) {
			log.error("Export CSV", e.getMessage());
		}
		return resObj;
	}
	
    @GetMapping(AppApiConstant.API_ACTIVITY_GET_AGENT_AND_ROLES)
    @ApiOperation("Get agent type and roles")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getAgentTypeAndRoles(HttpServletRequest request, @RequestParam(value = "agentCode", required = true) String agentCode) {
        
        log.info("Get agent type and roles");
        long start = System.currentTimeMillis();
        if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
			return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
		}
        try {
            List<AgentInfoDto> res = service.getAgentTypeAndRoles(agentCode);
            return this.successHandler.handlerSuccess(res, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @GetMapping(AppApiConstant.API_ACTIVITY_GET_ROLES_PERMISSION)
    @ApiOperation("Get agent type and roles")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getRolesPermission(HttpServletRequest request, @RequestParam(value = "agentCode", required = true) String agentCode) {
        
        log.info("Get roles permission");
        long start = System.currentTimeMillis();
        if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
			return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
		}
        try {
            List<RolePermissionDto> res = service.getRolesPermission(agentCode);
            return this.successHandler.handlerSuccess(res, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}
