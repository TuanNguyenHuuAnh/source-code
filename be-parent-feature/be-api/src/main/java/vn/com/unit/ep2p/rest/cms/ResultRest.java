package vn.com.unit.ep2p.rest.cms;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.result.dto.ObjectDataCustomRes;
import vn.com.unit.cms.core.module.result.dto.ResultDto;
import vn.com.unit.cms.core.module.result.dto.ResultSearchDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiResultService;
import vn.com.unit.ep2p.utils.LangugeUtil;

/**
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
@RestController
@RequestMapping(CmsApiConstant.BASE_API_ODS_URL + CmsApiConstant.API_ODS_RESULT)
@Api(tags = { CmsApiConstant.API_ODS_RESULT_DESCR })
public class ResultRest extends AbstractRest {
	
	@Autowired
	private ApiResultService apiResultService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	//thang tien phong  phong
	@GetMapping(AppApiConstant.API_LIST_RESULT_PROMOTION)
	@ApiOperation("Get all promotion result")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
				@ApiResponse(code = 500, message = "Internal Server Error"),
				@ApiResponse(code = 401, message = "Unauthorized"), 
				@ApiResponse(code = 403, message = "Forbidden"),
				@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListResultPromotion(HttpServletRequest request,
			@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
			ResultSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			searchDto.setPage(pageParam.get());
			searchDto.setPageSize(pageSizeParam.get());
			searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			CmsCommonPagination<ResultDto> common = apiResultService.getLisResultPromotion(searchDto);
			ObjectDataCustomRes<ResultDto> resObj = new ObjectDataCustomRes<>(common.getTotalData(), common.getData(), common.getTotalPromoteDemote());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	//duy tri nhong phong
	@GetMapping(AppApiConstant.API_LIST_RESULT_MAINTAIN)
	@ApiOperation("Get all list result maintain")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
				@ApiResponse(code = 500, message = "Internal Server Error"),
				@ApiResponse(code = 401, message = "Unauthorized"), 
				@ApiResponse(code = 403, message = "Forbidden"),
				@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListResultMaintain(HttpServletRequest request,
			@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
			ResultSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			searchDto.setPage(pageParam.get());
			searchDto.setPageSize(pageSizeParam.get());
			CmsCommonPagination<ResultDto> common = apiResultService.getLisResultMaintain(searchDto);
			ObjectDataCustomRes<ResultDto> resObj = new ObjectDataCustomRes<>(common.getTotalData(), common.getData(), common.getTotalPromoteDemote());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	//thang tien ca nhan
	@GetMapping(AppApiConstant.API_LIST_RESULT_PROMOTION_PERSONAL)
	@ApiOperation("Get promotion personal result")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListResultPromotionPersonal(HttpServletRequest request,
		 ResultSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			ResultDto common = apiResultService.getLisResultPromotionPersonal(searchDto);
			return this.successHandler.handlerSuccess(common, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	//duy tri ca nhan
	@GetMapping(AppApiConstant.API_LIST_RESULT_MAINTAIN_PERSONAL)
	@ApiOperation("Get promotion maintain result")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListResultPromotionMaintain(HttpServletRequest request,
														 ResultSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			ResultDto common = apiResultService.getLisResultMaintainPersonal(searchDto);
			return this.successHandler.handlerSuccess(common, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_RESULT_PROMOTION_EXPORT)
    @ApiOperation("Api export result promotion by angentTitle")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), 
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden"), })
    public ResponseEntity exportResultPromotionByAgentTitle(HttpServletRequest request,
			@RequestBody ResultSearchDto searchDto)  {
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			resObj = apiResultService.exportPromotion(searchDto,locale);
		} catch (Exception e) {
			logger.error("##exportLis##", e);
		}
		return resObj;
        
    } 
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_RESULT_MAINTAIN_EXPORT)
    @ApiOperation("Api export result maintain by angentTitle")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), 
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden"), })
    public ResponseEntity exportResultMaintainByAgentTitle(HttpServletRequest request,
			@RequestBody ResultSearchDto searchDto)  {
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			resObj = apiResultService.exportMaintain(searchDto,locale);
		} catch (Exception e) {
			logger.error("##exportLis##", e);
		}
		return resObj;
        
    }
	@GetMapping("/check-agent-type")
	@ApiOperation("Api export result maintain by angentTitle")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), })
	public DtsApiResponse checkAgentType(HttpServletRequest request, String agentType)  {
		DtsApiResponse resObj = null;
		long start = System.currentTimeMillis();
		try {
			return this.successHandler.handlerSuccess(apiResultService.checkAgentType(agentType), start, null, null);
		} catch (Exception e) {
			return this.errorHandler.handlerException(e, start, null, null);
		}
	}
}
