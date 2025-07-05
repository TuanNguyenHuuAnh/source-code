package vn.com.unit.ep2p.rest.cms;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.report.dto.ReportDetailDto;
import vn.com.unit.cms.core.module.report.dto.ReportDetailSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportGetDetailDto;
import vn.com.unit.cms.core.module.report.dto.ReportGroupDetailDto;
import vn.com.unit.cms.core.module.report.dto.ReportGroupDetailSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportListDetailSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.ApiReportDetailService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_ODS_URL + CmsApiConstant.API_ODS_REPORT_DETAIL)
@Api(tags = { CmsApiConstant.API_ODS_REPORT_DETAIL_DESCR })
public class ReportDetailRest extends AbstractRest {

	@Autowired
	private ApiReportDetailService apiReportDetailService;

	@Autowired
	ApiAgentDetailService apiAgentDetailService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping(AppApiConstant.API_GET_REPORT_DETAIL)
	@ApiOperation("Get report detail by agent")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })

	public DtsApiResponse getReportDetailByAgentCode(HttpServletRequest request, ReportDetailSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
//        	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			CmsCommonPagination<ReportGetDetailDto> data = apiReportDetailService.getReportDetailByAgentCode(searchDto);
			return this.successHandler.handlerSuccess(data, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	private String formatPolicyNumber(int digits, String policyNumber) {
		if (StringUtils.isEmpty(policyNumber)) {
			return "";
		}
		return IntStream.range(0, digits - policyNumber.length()).mapToObj(i -> "0").collect(Collectors.joining(""))
				.concat(policyNumber);
	}

	@GetMapping(AppApiConstant.API_LIST_REPORT_DETAIL)
	@ApiOperation("Get list report detail by agent")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })

	public DtsApiResponse getListReportDetailByAgentCode(HttpServletRequest request,
			@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam,
			ReportListDetailSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {

			String agentParent = UserProfileUtils.getFaceMask();
			if (StringUtils.isNotEmpty(agentParent) && !agentParent.equalsIgnoreCase(searchDto.getAgentCode())) {
				boolean isChild = apiAgentDetailService.checkAgentChild(agentParent, searchDto.getAgentCode());
				if (!isChild) {
					throw new DetailException(AppApiExceptionCodeConstant.E4027100_APPAPI_LINK_EXISTS_ERROR);
				}
			}
			searchDto.setPage(pageParam.get());
			searchDto.setPageSize(pageSizeParam.get());
			CmsCommonPagination<ReportDetailDto> data = apiReportDetailService
					.getListReportDetailByAgentCode(searchDto);
			data.getData().forEach(e -> {
				if (StringUtils.isNotBlank(e.getPolicyKey())) {
					e.setPolicyKey(formatPolicyNumber(9, e.getPolicyKey()));
				}
			});
			ObjectDataRes<ReportDetailDto> resObj = new ObjectDataRes<>(data.getTotalData(), data.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_RESULT_DETAIL_EXPORT)
	@ApiOperation("Api export result detail by agentCode")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), })
	public ResponseEntity exportResultDetailByAgentCode(HttpServletRequest request, HttpServletResponse response,
			@RequestBody ReportListDetailSearchDto searchDto) {
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			resObj = apiReportDetailService.exportResultDetail(searchDto, response, locale);
		} catch (Exception e) {
			logger.error("##exportList##", e.getMessage());
		}
		return resObj;
	}

	@GetMapping(AppApiConstant.API_LIST_REPORT_GROUP_DETAIL)
	@ApiOperation("Get list report detail by agent")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })

	public DtsApiResponse getListReportGroupDetailByAgentGroup(HttpServletRequest request,
			@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "0") Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam,
			ReportGroupDetailSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			searchDto.setPage(pageParam.get());
			searchDto.setPageSize(pageSizeParam.get());
			CmsCommonPagination<ReportGroupDetailDto> data = apiReportDetailService
					.getListReportGroupDetailByAgentGroup(searchDto);
			ObjectDataRes<ReportGroupDetailDto> resObj = new ObjectDataRes<>(data.getTotalData(), data.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_RESULT_GROUP_DETAIL_EXPORT)
	@ApiOperation("Api export result group detail by agentGroup")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), })
	public ResponseEntity exportResultGroupDetailByAgentGroup(HttpServletRequest request,
			@RequestBody ReportGroupDetailSearchDto searchDto) {
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			resObj = apiReportDetailService.exportResulGrouptDetail(searchDto, locale);
		} catch (Exception e) {
			logger.error("##exportList##", e.getMessage());
		}
		return resObj;
	}
}
