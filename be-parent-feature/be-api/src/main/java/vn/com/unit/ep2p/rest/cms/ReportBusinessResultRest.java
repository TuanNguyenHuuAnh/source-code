package vn.com.unit.ep2p.rest.cms;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.report.dto.ReportResultSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportResultViewGADTabPremiumDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ReportBusinessResultGaService;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_ODS_REPORT_BUSINESS_RESULT_GA) //report-business-result-ga
@Api(tags = { "Report Business Result Rest" })
public class ReportBusinessResultRest extends AbstractRest{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ReportBusinessResultGaService reportBusinessResultGaService;
	
	@GetMapping(AppApiConstant.API_LIST_REPORT_BUSINESS_VIEW_GAD_TAB_PREMIUM) //PREMIUM
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListMtdViewGadTabPremium(HttpServletRequest request
    		, ReportResultSearchDto searchDto){
        long start = System.currentTimeMillis();
        try {
            searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            ObjectDataRes<ReportResultViewGADTabPremiumDto> resObj = reportBusinessResultGaService.getListResultViewGadPremium(searchDto);
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_LIST_REPORT_BUSINESS_VIEW_GAD_TAB_MANPOWER)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListMtdViewGadTabManpower(HttpServletRequest request
    		, ReportResultSearchDto searchDto){
        long start = System.currentTimeMillis();
        try {
            searchDto.setAgentCode(UserProfileUtils.getFaceMask());
        	ObjectDataRes<ReportResultViewGADTabPremiumDto> resObj = reportBusinessResultGaService.getListResultViewGadManpower(searchDto);
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_REPORT_BUSINESS_VIEW_GAD_PREMIUM)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportGadPremium(HttpServletRequest request, @RequestBody ReportResultSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            resObj = reportBusinessResultGaService.exportGad(searchDto, 1);
        } catch (Exception e) {
            logger.error("##exportGadPremium##", e);
        }
        return resObj;
    }
	
	@SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_REPORT_BUSINESS_VIEW_GAD_MANPOWER)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportGadManpower(HttpServletRequest request, @RequestBody ReportResultSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            resObj = reportBusinessResultGaService.exportGad(searchDto, 2);
        } catch (Exception e) {
            logger.error("##exportGadManpower##", e);
        }
        return resObj;
    }
	
//	@GetMapping(AppApiConstant.API_LIST_REPORT_BUSINESS_SUMARY)
//    @ApiOperation("Api provides constant on systems")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
//            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
//            @ApiResponse(code = 402601, message = "Error process type new") })
//    
//    public DtsApiResponse getListSumary(HttpServletRequest request
//            , String agentCode, String orgCode, String agentGroup){
//        long start = System.currentTimeMillis();
//        try {
//            ReportResultSumaryDto resObj = reportBusinessResultGaService.getSumary(agentCode, orgCode, agentGroup);
//            return this.successHandler.handlerSuccess(resObj, start, null, null);
//        } catch (Exception ex) {
//            return this.errorHandler.handlerException(ex, start, null, null);
//        }
//    }
}
