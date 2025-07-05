package vn.com.unit.ep2p.rest.cms;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.report.dto.ReportActiveContractMonthDto;
import vn.com.unit.cms.core.module.report.dto.ReportActiveContractSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportActiveDto;
import vn.com.unit.cms.core.module.report.dto.ReportActiveSearchDto;
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
import vn.com.unit.ep2p.service.ApiReportActiveService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_ODS_URL + CmsApiConstant.API_ODS_REPORT_ACTIVE)
@Api(tags = {CmsApiConstant.API_ODS_REPORT_ACTIVE_DESCR})
public class ReportActiveRest extends AbstractRest {

    @Autowired
    private ApiReportActiveService apiReportActiveService;

	@Autowired
	ApiAgentDetailService apiAgentDetailService;
    
	private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping(AppApiConstant.API_LIST_REPORT_ACTIVE)
    @ApiOperation("Get list report active by agentCode")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})

    public DtsApiResponse getListReportActivevByAgentCode(HttpServletRequest request,
      @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
      @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam,
      ReportActiveSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
	        String agentParent = UserProfileUtils.getFaceMask();
	        if (StringUtils.isNotEmpty(agentParent) && !agentParent.equalsIgnoreCase(searchDto.getAgentCode())) {
	            boolean isChild = apiAgentDetailService.checkAgentChild(agentParent, searchDto.getAgentCode());
	            if (!isChild) {
	                throw new DetailException(AppApiExceptionCodeConstant.E4027100_APPAPI_LINK_EXISTS_ERROR);
	            }
	        }
        	String yyyyMM = searchDto.getYear().concat(searchDto.getMonth());
        	searchDto.setYyyyMM(yyyyMM);
            CmsCommonPagination<ReportActiveDto> data = apiReportActiveService.getListReportActiveByAgentCode(searchDto);
            return this.successHandler.handlerSuccess(data, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

//    @GetMapping(AppApiConstant.API_LIST_REPORT_ACTIVE_3MONTHSAGO)
//    @ApiOperation("Get List report active three months ago by agent")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
//            @ApiResponse(code = 500, message = "Internal Server Error"),
//            @ApiResponse(code = 401, message = "Unauthorized"),
//            @ApiResponse(code = 403, message = "Forbidden"),
//            @ApiResponse(code = 402601, message = "Error process type new")})
//
//    public DtsApiResponse getListReportActive3MonthsAgo(HttpServletRequest request,
//      @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
//      @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
//      ReportActiveSearchDto searchDto) {
//        long start = System.currentTimeMillis();
//        try {
//        	String yyyyMM = searchDto.getYear().concat(searchDto.getMonth());
//        	searchDto.setYyyyMM(yyyyMM);
//            CmsCommonPagination<ReportActiveDto> data = apiReportActiveService.getListReportActive3MonthsAgoByAgent(searchDto);
//            return this.successHandler.handlerSuccess(data, start, null, null);
//        } catch (Exception ex) {
//            return this.errorHandler.handlerException(ex, start, null, null);
//        }
//    }

    @GetMapping(AppApiConstant.API_LIST_CONTRACT_MONTH)
    @ApiOperation("Get List contract month by agentTarget target")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})

    public DtsApiResponse getListContractMonth(HttpServletRequest request,
        @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
        @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam,
        ReportActiveContractSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
        	if(StringUtils.isBlank(searchDto.getAgentCode())) {
        		searchDto.setAgentCode(UserProfileUtils.getFaceMask());
        	}
            searchDto.setPage(pageParam.get());
            searchDto.setPageSize(pageSizeParam.get());
            CmsCommonPagination<ReportActiveContractMonthDto> data = apiReportActiveService.getListContractMonth(searchDto);
            ObjectDataRes<ReportActiveContractMonthDto> common =  new ObjectDataRes<>(data.getTotalData(), data.getData());
            return this.successHandler.handlerSuccess(common, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    @GetMapping("/get-list-contract-year")
    @ApiOperation("Get List contract month by agentTarget target")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})

    public DtsApiResponse getListContractYear(HttpServletRequest request,
                                               @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
                                               @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam,
                                               ReportActiveContractSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
        	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            searchDto.setPage(pageParam.get());
            searchDto.setPageSize(pageSizeParam.get());
            CmsCommonPagination<ReportActiveContractMonthDto> data = apiReportActiveService.getListContractYear(searchDto);
            ObjectDataRes<ReportActiveContractMonthDto> common =  new ObjectDataRes<>(data.getTotalData(), data.getData());
            return this.successHandler.handlerSuccess(common, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
	@SuppressWarnings("rawtypes") 
	@PostMapping(AppApiConstant.API_EXPORT_CONTRACT_MONTH)  
    @ApiOperation("api export List contract month by agentTarget target")  
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),   
    		@ApiResponse(code = 500, message = "Internal Server Error"), 
            @ApiResponse(code = 401, message = "Unauthorized"),  
            @ApiResponse(code = 403, message = "Forbidden"), })  
    public ResponseEntity exportResultDetailByAgentCode(HttpServletRequest request,
			 HttpServletResponse response,
			@RequestBody ReportActiveContractSearchDto searchDto)  {  
		ResponseEntity resObj = null;
		try {
			if(StringUtils.isBlank(searchDto.getAgentCode())) {
        		searchDto.setAgentCode(UserProfileUtils.getFaceMask());
        	}
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			resObj = apiReportActiveService.exportResultListContractMonth(searchDto, response, locale);
		} catch (Exception e) {
			logger.error("##exportList##", e.getMessage());
		}
		return resObj; 
    }
    @SuppressWarnings("rawtypes")
    @PostMapping("/export-list-contract-year")
    @ApiOperation("api export List contract month by agentTarget target")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"), })
    public ResponseEntity exportResultDetailByAgentCodeYear(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        @RequestBody ReportActiveContractSearchDto searchDto)  {
        ResponseEntity resObj = null;
        try {
            if(StringUtils.isBlank(searchDto.getAgentCode())) {
                searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            }
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
            resObj = apiReportActiveService.exportResultListContractYear(searchDto, response, locale);
        } catch (Exception e) {
            logger.error("##exportList##", e.getMessage());
        }
        return resObj;
    }
}
