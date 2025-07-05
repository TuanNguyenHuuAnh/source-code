package vn.com.unit.ep2p.rest.cms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.ga.dto.GrowthGaDto;
import vn.com.unit.cms.core.module.ga.dto.search.GrowthSearchGa;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.service.GrowthGaService;
import vn.com.unit.ep2p.service.impl.PersonalInsuranceDocServiceImpl;
import vn.com.unit.ep2p.utils.LangugeUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_ODS_GROWTH_GROUP)
@Api(tags = { CmsApiConstant.API_ODS_GROWTH_GROUP_DESCR })
public class GrowthGaRest {

	@Autowired
	protected ErrorHandler errorHandler;

	@Autowired
	protected SuccessHandler successHandler;

	@Autowired
	private GrowthGaService growthGaService;

	private static final Logger logger = LoggerFactory.getLogger(PersonalInsuranceDocServiceImpl.class);

	// get lít reprot grouth Ga
	@GetMapping(AppApiConstant.API_LIST_REPORT_GROWTH_GA)
	@ApiOperation("Get report grouth ga months")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
                            @ApiResponse(code = 500, message = "Internal Server Error"),
                            @ApiResponse(code = 401, message = "Unauthorized"),
                            @ApiResponse(code = 403, message = "Forbidden") })

	public DtsApiResponse getListGrowthGa(HttpServletRequest request, GrowthSearchGa searchDto) {
		long start = System.currentTimeMillis();
		try {
			CmsCommonPagination<GrowthGaDto> common = growthGaService.getListGrowthGa(searchDto);
			ObjectDataRes<GrowthGaDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	// get lít reprot grouth Ga bonus
		@GetMapping(AppApiConstant.API_LIST_REPORT_GROWTH_GA_BONUS)
		@ApiOperation("Get report grouth ga bonus")
		@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
	                            @ApiResponse(code = 500, message = "Internal Server Error"),
	                            @ApiResponse(code = 401, message = "Unauthorized"),
	                            @ApiResponse(code = 403, message = "Forbidden") })

		public DtsApiResponse getListGrowthGaBonus(HttpServletRequest request, GrowthSearchGa searchDto) {
			long start = System.currentTimeMillis();
			try {
				CmsCommonPagination<GrowthGaDto> common = growthGaService.getListGrowthGaBonus(searchDto);
				ObjectDataRes<GrowthGaDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
				return this.successHandler.handlerSuccess(resObj, start, null, null);
			} catch (Exception ex) {
				return this.errorHandler.handlerException(ex, start, null, null);
			}
		}
	//export list report grouth ga month/quarter
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_REPORT_GROWTH_GA)
	@ApiOperation("Api export list report grouth ga month/quarter")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListGrowthGa(HttpServletRequest request,
		@RequestBody GrowthSearchGa searchDto) {
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			resObj = growthGaService.exporttListGrowthGa(searchDto,locale);
		} catch (Exception e) {
			logger.error("##exportPolicyExpired##", e);
		}
		return resObj;
	}

	@PostMapping(AppApiConstant.API_EXPORT_REPORT_GROWTH_GA_BONUS)
	@ApiOperation("Api export list report grouth ga bonus")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListGrowthGaBonus(HttpServletRequest request,
		@RequestBody GrowthSearchGa searchDto) {
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			resObj = growthGaService.exporttListGrowthGaBonus(searchDto,locale);
		} catch (Exception e) {
			logger.error("##exportPolicyExpired##", e);
		}
		return resObj;
	}
}
