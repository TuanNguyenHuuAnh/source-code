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
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchResultDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.cms.core.module.information.dto.DebtInformationDto;
import vn.com.unit.cms.core.module.information.dto.DebtInformationSearchDto;
import vn.com.unit.cms.core.module.information.dto.LetterSearchDto;
import vn.com.unit.cms.core.module.information.dto.ResultLetterSearchDto;
import vn.com.unit.cms.core.module.information.dto.GAInformationDto;
import vn.com.unit.cms.core.module.information.dto.GaInforRes;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.InformationService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_ODS_INFORMATION_PAGE) // information-page
@Api(tags = { "Information activity" })
public class InformationPageRest extends AbstractRest {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	InformationService informationService;

	// info nợ
	@GetMapping(AppApiConstant.API_LIST_INFORMATION_DEBT)
	@ApiOperation("info Debt")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListDebtInfor(HttpServletRequest request,
			DebtInformationSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			searchDto.setLanguage(locale.getLanguage());
			ObjectDataRes<DebtInformationDto> resObj = informationService.getListDebtInformation(searchDto);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	//export info nợ
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_LIST_INFORMATION_DEBT)
	@ApiOperation("export info debt")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListDebtInfor(HttpServletRequest request, HttpServletResponse response,
			@RequestBody DebtInformationSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		String agentCode = UserProfileUtils.getFaceMask();
		if (StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
			agentCode = UserProfileUtils.getFaceMask();
		}
		searchDto.setAgentCode(agentCode);
		searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
			resObj = informationService.exportListDebtInformation(response, locale, searchDto);
		} catch (Exception e) {
			logger.error("exportListDebtInfor: ", e);
		}
		return resObj;
	}

	@GetMapping(AppApiConstant.API_LIST_INFORMATION_GA)
	@ApiOperation("Api provides constant on systems")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListGAInfor(HttpServletRequest request, String orgCode, String agentCode) {
		long start = System.currentTimeMillis();
		try {
		    agentCode=UserProfileUtils.getFaceMask();
		    List<GAInformationDto> res = informationService.getListGA(agentCode, orgCode);
			return this.successHandler.handlerSuccess(res, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_OFFICE_GAD)
	@ApiOperation("Api provides constant on systems")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListOfficeOfGad(HttpServletRequest request, String agentCode) {
		long start = System.currentTimeMillis();
		try {
//			Locale locale = LangugeUtil.getLanguageFromHeader(request);
//			String agentCode = UserProfileUtils.getFaceMask();
//			if (StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
//				agentCode = UserProfileUtils.getFaceMask();
//			}
			List<Select2Dto> resObj = informationService.getOfficeOfGad(agentCode);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_CHALLENGE_LETTER)
	@ApiOperation("Api provides constant on systems")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListChallengeLetterOffice(HttpServletRequest request, LetterSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
//			Locale locale = LangugeUtil.getLanguageFromHeader(request);
//			String agentCode = UserProfileUtils.getFaceMask();
//			if (StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
//				agentCode = UserProfileUtils.getFaceMask();
//			}
			List<EmulateSearchDto> resObj = informationService.getListchallengeLetterOffice(searchDto);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_CHALLENGE_LETTER_RESULT)
	@ApiOperation("Api provides constant on systems")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListChallengeLetterResultOffice(HttpServletRequest request,
			ResultLetterSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
//			Locale locale = LangugeUtil.getLanguageFromHeader(request);
//			String agentCode = UserProfileUtils.getFaceMask();
//			if (StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
//				agentCode = UserProfileUtils.getFaceMask();
//			}
			//searchDto.setAgentCode(agentCode);
			//searchDto.setLanguage(locale.getLanguage());
			List<EmulateResultSearchResultDto> resObj = informationService
					.getListResultchallengeLetterOffice(searchDto);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(AppApiConstant.API_EXPORT_CHALLENGE_LETTER_RESULT)
	@ApiOperation("Api provides constant on systems")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListChallengeLetterResultOffice(HttpServletRequest request
			, HttpServletResponse response, ResultLetterSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		String agentCode = UserProfileUtils.getFaceMask();
		if (StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
			agentCode = UserProfileUtils.getFaceMask();
		}
		searchDto.setAgentCode(agentCode);
		searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
			resObj = informationService.exportListResultchallengeLetterOffice(response, locale, searchDto);
		} catch (Exception e) {
			logger.error("exportListDebtInfor: ", e);
		}
		return resObj;
	}
}
