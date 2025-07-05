package vn.com.unit.ep2p.rest.cms;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.candidate.dto.CandidateInfoDto;
import vn.com.unit.cms.core.module.candidate.dto.ExamScheduleCandidateDto;
import vn.com.unit.cms.core.module.candidate.dto.ExamScheduleCandidateSearchDto;
import vn.com.unit.cms.core.module.candidate.dto.ExamScheduleHomepage;
import vn.com.unit.cms.core.module.candidate.dto.MonthYear;
import vn.com.unit.cms.core.module.candidate.dto.ProfileCandidateDetailDto;
import vn.com.unit.cms.core.module.candidate.dto.ProfileCandidateDto;
import vn.com.unit.cms.core.module.candidate.dto.ProfileCandidateSearchDto;
import vn.com.unit.cms.core.module.candidate.dto.ScheduleCandidateDto;
import vn.com.unit.cms.core.module.candidate.dto.ScheduleCandidateSearchDto;
import vn.com.unit.cms.core.module.candidate.dto.TemporaryCandidateDto;
import vn.com.unit.cms.core.module.candidate.dto.TemporaryCandidateSearchDto;
import vn.com.unit.cms.core.module.document.dto.DocumentVersionDto;
import vn.com.unit.core.dto.JcaSystemConfigDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.CandidateService;
import vn.com.unit.ep2p.service.SystemConfigService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CANDIDATE)
@Api(tags = { "Candidate" })
public class CandidateRest extends AbstractRest {

	@Autowired
	CandidateService candidateService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SystemConfigService systemConfigService;

	// HOMAPAGE
	//LICH HOC VA THI
	@GetMapping(AppApiConstant.API_GET_CANDIDATE_EXAM_SCHEDULE_HOMEPAGE)
	@ApiOperation("Api exam schedule candidate homepage")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getScheduleHomapage(HttpServletRequest request) {
		long start = System.currentTimeMillis();

		try {
			String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	ExamScheduleHomepage data = candidateService.getScheduleHomepage(agentCode);
			return this.successHandler.handlerSuccess(data, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	//QUI DINH HOC VA THI
	@GetMapping(AppApiConstant.API_GET_CANDIDATE_REGULATION_HOMEPAGE)
	@ApiOperation("Api regulation candidate")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getRegulation(HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	List<DocumentVersionDto> data = candidateService.getListRegulation(locale.getLanguage());
			return this.successHandler.handlerSuccess(data, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	//LAY THONG TIN UNG VIEN
	@GetMapping(AppApiConstant.API_GET_CANDIDATE_INFO)
	@ApiOperation("Api candidate info")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getCandidateInfo(HttpServletRequest request, String userName) {
		long start = System.currentTimeMillis();
		try {
			String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	userName=agentCode;
			CandidateInfoDto entity = candidateService.getCandidateInfo(userName);
			return this.successHandler.handlerSuccess(entity, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	// QUAN LY TUYEN DUNG
	// LICH THI
	@GetMapping(AppApiConstant.API_GET_CANDIDATE_EXAM_SCHEDULE)
	@ApiOperation("Api exam schedule candidate")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getExamScheduleCandidate(HttpServletRequest request, ExamScheduleCandidateSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			ObjectDataRes<ExamScheduleCandidateDto> resObj = candidateService.getListExamScheduleByCondition(searchDto);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	// QUAN LY TUYEN DUNG
	// LICH HOC
	@GetMapping(AppApiConstant.API_GET_CANDIDATE_SCHEDULE)
	@ApiOperation("Api exam schedule candidate")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getScheduleCandidate(HttpServletRequest request, ScheduleCandidateSearchDto searchDto) {
		long start = System.currentTimeMillis();

		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			ObjectDataRes<ScheduleCandidateDto> resObj = candidateService.getListScheduleByCondition(searchDto);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	//EXPORT LICH THI
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_CANDIDATE_EXAM_SCHEDULE)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportExamSchedule(HttpServletRequest request,@RequestBody  ExamScheduleCandidateSearchDto searchDto ) {
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			resObj = candidateService.exportExamSchedule(locale,searchDto);
		} catch (Exception e) {
			logger.error("##exportLis##", e.getMessage());
		}
		return resObj;
	}
	
	//EXPORT LICH HOC
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_CANDIDATE_SCHEDULE)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
	
	public ResponseEntity exportListIntroduceLetterByCondition(HttpServletRequest request, @RequestBody ScheduleCandidateSearchDto searchDto) {
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			resObj = candidateService.exportSchedule(locale,searchDto);
		} catch (Exception e) {
			logger.error("##exportLis##", e.getMessage());
		}
		return resObj;
	}

	@GetMapping("get-list-month")
	@ApiOperation("Api list month")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListMonth(HttpServletRequest request, HttpServletResponse response) {
		long start = System.currentTimeMillis();
		List<MonthYear> listMonth = candidateService.getListMonth();
		ObjectDataRes<MonthYear> resObj = new ObjectDataRes<>(listMonth.size(), listMonth);
		return this.successHandler.handlerSuccess(resObj, start, null, null);
	}

	@GetMapping("get-list-year")
	@ApiOperation("Api list year")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListYear(HttpServletRequest request, HttpServletResponse response) {
		long start = System.currentTimeMillis();
		List<MonthYear> listYear = candidateService.getListYear();
		ObjectDataRes<MonthYear> resObj = new ObjectDataRes<>(listYear.size(), listYear);
		return this.successHandler.handlerSuccess(resObj, start, null, null);
	}
	
	//QUAN LY HO SO UNG VIEN
	@GetMapping(AppApiConstant.API_GET_PROFILE_CANDIDATE)
	@ApiOperation("Api exam schedule candidate")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListProfileCandidate(HttpServletRequest request, ProfileCandidateSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setUserName(agentCode);
			ObjectDataRes<ProfileCandidateDto> resObj = candidateService.getListProfileCandidate(searchDto);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping(AppApiConstant.API_GET_PROFILE_CANDIDATE_DETAIL)
	@ApiOperation("Api exam schedule candidate")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListProfileCandidateDetail(HttpServletRequest request, String idCardCandidate) {// CMND/CCCD của ứng viên
		long start = System.currentTimeMillis();
		try {
			ProfileCandidateDetailDto resultData = candidateService.getProfileCandidateDetail(idCardCandidate);
			return this.successHandler.handlerSuccess(resultData, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping(AppApiConstant.API_GET_CANDIDATE_TEMPOARY)
	@ApiOperation("Api exam schedule candidate")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListTemporaryCandidateParam(HttpServletRequest request
			, @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam
            , @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
			, TemporaryCandidateSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			String agentCode = UserProfileUtils.getFaceMask();
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	searchDto.setLanguage(locale.getLanguage());
        	searchDto.setAgentCode(agentCode);
			ObjectDataRes<TemporaryCandidateDto> resObj = candidateService.getListTemporaryCandidateParam(searchDto);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_CANDIDATE_TEMPOARY)
	@ApiOperation("Api exam schedule candidate")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exporttListTemporaryCandidate(HttpServletRequest request,@RequestBody TemporaryCandidateSearchDto searchDto) {
		ResponseEntity resObj = null;
		try {
			String agentCode = UserProfileUtils.getFaceMask();
        	searchDto.setAgentCode(agentCode);
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			searchDto.setLanguage(locale.getLanguage());
			resObj = candidateService.exportListTemporaryCandidate(searchDto);
		} catch (Exception e) {
			logger.error("##exportLis##", e.getMessage());
		}
		return resObj;
	}
}
