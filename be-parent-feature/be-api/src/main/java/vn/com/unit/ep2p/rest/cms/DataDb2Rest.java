package vn.com.unit.ep2p.rest.cms;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.common.dto.AaGaOfficeDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.dto.GADOfficeDto;
import vn.com.unit.ep2p.admin.dto.TotalBdByAgentCode;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.utils.LangugeUtil;

/**
 * @Last updated: 22/03/2024    nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_DB2)
@Api(tags = { CmsApiConstant.API_CMS_DB2_DESCR })
public class DataDb2Rest extends AbstractRest {

	@Autowired
	Db2ApiService db2ApiService;

	@PostMapping(AppApiConstant.API_LIST_OFFICE_DB2)
	@ApiOperation("Api get list Office on db2")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListOffice(HttpServletRequest request,
			@RequestParam(value = "param", required = false, defaultValue = "") String province) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			List<Select2Dto> datas = db2ApiService.getListOfficeDb2(province);
			ObjectDataRes<Select2Dto> resObj = new ObjectDataRes<>(datas.size(), datas);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_TERRITORY_DB2)
	@ApiOperation("Api get list Territory on db2")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListTerritory(HttpServletRequest request) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			String agentCode = UserProfileUtils.getFaceMask();
			List<Select2Dto> datas = db2ApiService.getListTerritory(agentCode);
			ObjectDataRes<Select2Dto> resObj = new ObjectDataRes<>(datas.size(), datas);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_REGION_DB2)
	@ApiOperation("Api get list Region on db2")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListRegion(HttpServletRequest request,
			@RequestParam(value = "territory", required = false) String territory) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			String agentCode = UserProfileUtils.getFaceMask();
			List<Select2Dto> datas = db2ApiService.getListRegion(territory, agentCode);
			ObjectDataRes<Select2Dto> resObj = new ObjectDataRes<>(datas.size(), datas);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_AREA_DB2)
	@ApiOperation("Api get list Area on db2")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListArea(HttpServletRequest request,
			@RequestParam(value = "region", required = false) String region
	, @RequestParam(value = "territory", required = false) String territory) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			String agentCode = UserProfileUtils.getFaceMask();
			List<Select2Dto> datas = db2ApiService.getListArea(region, agentCode, territory);
			ObjectDataRes<Select2Dto> resObj = new ObjectDataRes<>(datas.size(), datas);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_EVENT_OFFICE_DB2)
	@ApiOperation("Api get list Office on db2")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListEventOffice(HttpServletRequest request,
			@RequestParam(value = "area", required = false) String area
			, @RequestParam(value = "region", required = false) String region
			, @RequestParam(value = "territory", required = false) String territory) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			String agentCode = UserProfileUtils.getFaceMask();
			List<Select2Dto> datas = db2ApiService.getListEventOffice(area, agentCode, region, territory);
			ObjectDataRes<Select2Dto> resObj = new ObjectDataRes<>(datas.size(), datas);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_POSITION_DB2)
	@ApiOperation("Api get list Office on db2")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListPosition(HttpServletRequest request) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			List<Select2Dto> datas = db2ApiService.getListEventPosition();
			ObjectDataRes<Select2Dto> resObj = new ObjectDataRes<>(datas.size(), datas);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_PARENT_BY_AGENT_DB2)
	@ApiOperation("Api get list Office on db2")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getParentByAgentCode(HttpServletRequest request, String agentCode, String agentType, String orgCode) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			/*if (!db2ApiService.checkChildrenInParent(UserProfileUtils.getFaceMask(), agentCode)) {
				return this.errorHandler.handlerException(new Exception("Quyền truy cập không hợp lệ"), start, null, null);
			}*/
			AgentInfoDb2 resObj = db2ApiService.getParentByAgentCode(agentCode, agentType, orgCode);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_OFFICE_BY_GAD)
	@ApiOperation("Api get list Office on db2")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListOfficeByGad(HttpServletRequest request,
			@RequestParam(value = "", required = false, defaultValue = "") String orgCode) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			String agentCode = UserProfileUtils.getFaceMask();
			List<AaGaOfficeDto> datas = db2ApiService.getListOfficeByGad(agentCode, orgCode, "0");
			ObjectDataRes<AaGaOfficeDto> resObj = new ObjectDataRes<>(datas.size(), datas);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_OFFICE_BY_GAD_PAYMENT)
	@ApiOperation("Api get list Office on db2")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListOfficeByGad(HttpServletRequest request,
		@RequestParam(value = "", required = false, defaultValue = "") String orgCode,
		String cutoffDate,
		@RequestParam(value = "payroll", required = false, defaultValue = "false") Boolean payroll
	) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			String agentCode = UserProfileUtils.getFaceMask();
			List<AaGaOfficeDto> datas = new ArrayList<>();
			if ( cutoffDate != null && cutoffDate.length() == 4 ) {
				datas = db2ApiService.getListOfficeByGadForYearlyPayment(agentCode, cutoffDate);
			} else {
				datas = db2ApiService.getListOfficeByGadForPayment(agentCode, orgCode, cutoffDate, payroll);
			}
			ObjectDataRes<AaGaOfficeDto> resObj = new ObjectDataRes<>(datas.size(), datas);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_TOTAL_BD_BY_AGENT_CODE)
	@ApiOperation("Api get list total on db2")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListOfficeByGad(HttpServletRequest request,
			@RequestParam(value = "", required = false, defaultValue = "") String agentCode
			,@RequestParam(value = "", required = false, defaultValue = "") String agentGroup
			, @RequestParam(value = "", required = false, defaultValue = "") String orgCode) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			TotalBdByAgentCode datas = db2ApiService.getTotalBd(agentCode, agentGroup, orgCode);
			return this.successHandler.handlerSuccess(datas, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	@GetMapping(AppApiConstant.API_GET_AGENT_GROUP_HIST)
	@ApiOperation("Api get list total on db2")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getAgentGroupHist(HttpServletRequest request,
			@RequestParam(value = "", required = false, defaultValue = "") String agentCode
			,@RequestParam(value = "", required = false, defaultValue = "") String yyyyMm
			, @RequestParam(value = "", required = false, defaultValue = "") String orgCode) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			AgentInfoDb2 agentGroup= db2ApiService.getAgentGroupHist(agentCode, yyyyMm, orgCode);
			return this.successHandler.handlerSuccess(agentGroup, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@PostMapping("/getGDAOfficeInformation")
	@ApiOperation("Api ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getGDAOfficeInformation(HttpServletRequest request,
			@RequestParam(value = "officeCode", required = false, defaultValue = "") String officeCode,
			@RequestParam(value = "gadCode", required = false, defaultValue = "") String gadCode) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			List<GADOfficeDto> datas = db2ApiService.getGADOfficeInformation(officeCode, gadCode);
			return this.successHandler.handlerSuccess(datas, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
}
