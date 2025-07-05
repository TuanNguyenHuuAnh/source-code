package vn.com.unit.ep2p.rest.cms;

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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingCourseDto;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingCourseSearchDto;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingTraineeSearchDto;
import vn.com.unit.core.constant.ConstantCore;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.dto.OfficeDto;
import vn.com.unit.ep2p.admin.dto.TrainingTraineeDB2Dto;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.service.TrainingCourseService;
import vn.com.unit.ep2p.utils.AppStringUtils;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_TRAINING_COURSES)
@Api(tags = { CmsApiConstant.API_CMS_TRAINING_COURSES_DESCR })
public class TrainingCourseRest {
	
	@Autowired
	protected ErrorHandler errorHandler;

	@Autowired
	protected SuccessHandler successHandler;
	
	@Autowired
	private TrainingCourseService trainingCourseService;
	
	@Autowired
	MessageSource messageSource;

	private Logger loggerClass = LoggerFactory.getLogger(getClass());
	private static final String FUNCTION_ID = "SCREEN#FRONTEND#TRAIN_MANAGEMENT";
	
    @GetMapping(AppApiConstant.API_GET_LIST_GUESTS_TRAINING)
    @ApiOperation("Get guest training by condition")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getListTrainingGuest(HttpServletRequest request,
            @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam
            ,TrainingTraineeSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
        	if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
        	CmsCommonPagination<TrainingTraineeDB2Dto> common = trainingCourseService.getListGuestsOfTraining(searchDto);
        	ObjectDataRes<TrainingTraineeDB2Dto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @GetMapping(AppApiConstant.API_GET_LIST_TRAINING_BY_CONDITION)
    @ApiOperation("Get training by condition")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getListTrainingByCondition(HttpServletRequest request,
                                                   @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
                                                   @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam
            ,TrainingCourseSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
            searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            searchDto.setPage(pageParam.get());
            searchDto.setPageSize(pageSizeParam.get());
            CmsCommonPagination<TrainingCourseDto> common = trainingCourseService.getListTrainingCourseByCondition(searchDto);
            ObjectDataRes<TrainingCourseDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @PostMapping(AppApiConstant.API_EDIT_TRAINING_COURSE)
    @ApiOperation("Api add/edit training course")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse editOrEditTrainingCourse(HttpServletRequest request, @RequestBody TrainingCourseDto dto)  {
        long start = System.currentTimeMillis();
        try {
        	if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
            if (AppStringUtils.checkByRegex(dto.getContents(), "<(\\\"[^\\\"]*\\\"|'[^']*'|[^'\\\">])*>")) {
                return this.errorHandler.handlerException(new Exception("Nội dung không hợp lệ(có chứa mã html)"), start, null, null);
            }
            TrainingCourseDto resObj = null;
            if (dto.getId() == null) {
            	resObj = trainingCourseService.addTrainingCourse(dto);	
            } else {
            	resObj = trainingCourseService.updateTrainingCourse(dto);
            }
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @GetMapping(AppApiConstant.API_GET_DETAILS_TRAINING_COURSE)
    @ApiOperation("Get detail training course")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getDetailTrainingCourse(HttpServletRequest request, TrainingCourseSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
            TrainingCourseDto dto = trainingCourseService.getDetailTrainingCourse(searchDto);
            return this.successHandler.handlerSuccess(dto, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping(AppApiConstant.API_GET_LIST_OFFICE)
    @ApiOperation("Get list office of agent leader")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListOffice(HttpServletRequest request)  {
        long start = System.currentTimeMillis();
        try {
        	List<OfficeDto> listOffice = trainingCourseService.getListOfficeByAgent(UserProfileUtils.getFaceMask());
            return this.successHandler.handlerSuccess(listOffice, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @PostMapping(AppApiConstant.API_UPDATE_STATUS)
    @ApiOperation("Api update training course")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse updateStatus(HttpServletRequest request, @RequestBody TrainingCourseDto dto)  {
        long start = System.currentTimeMillis();
        try {
        	if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
            trainingCourseService.updateSatus(dto);
            return this.successHandler.handlerSuccess(true, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @PostMapping(AppApiConstant.API_CHECKIN_TRAINING_COURSE)
    @ApiOperation("Checkin training course for trainee")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse checkinTrainingCourse(HttpServletRequest request,
    		@RequestBody TrainingCourseDto dto)  {
        long start = System.currentTimeMillis();
        try {
        	if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
        	int result = trainingCourseService.checkinTrainingCourse(dto.getCourseCode(), UserProfileUtils.getFaceMask());
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_LIST_TRAINING_COURSE)
	@ApiOperation("Api export excel list training course")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListTrainingCourses(HttpServletRequest request, HttpServletResponse response,
			@RequestBody TrainingCourseSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
			resObj = trainingCourseService.exportListTrainingCourses(searchDto, response, locale);
		} catch (Exception e) {
			loggerClass.error("##exportListTrainingCourses##", e.getMessage());
		}
		return resObj;
	}
}
