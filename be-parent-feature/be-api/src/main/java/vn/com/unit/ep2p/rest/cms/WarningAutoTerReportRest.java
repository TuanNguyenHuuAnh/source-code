package vn.com.unit.ep2p.rest.cms;

import java.util.Locale;

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
import vn.com.unit.cms.core.module.autoter.dto.AutoterReportDetailDto;
import vn.com.unit.cms.core.module.autoter.dto.AutoterSearchDto;
import vn.com.unit.cms.core.module.autoter.dto.AutoterSumaryDto;
import vn.com.unit.cms.core.module.reportGroup.ReportGroupResultManPowerDetailDto;
import vn.com.unit.cms.core.module.reportGroup.dto.search.ReportGroupSearchDetailDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiReportGroupService;
import vn.com.unit.ep2p.service.WarningAutoTerReportService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_ODS_URL + CmsApiConstant.API_CMS_REPORT_AUTOTER)
@Api(tags = {CmsApiConstant.API_CMS_REPORT_AUTOTER_DESCR})
public class WarningAutoTerReportRest extends AbstractRest{
	
    @Autowired
    private WarningAutoTerReportService warningAutoTerReportService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//CALL "RPT_ODS"."DS_SP_GET_LIST_AUTO_WARNING_TER_FCSA"('260990', 260988,'OH',20220131, 0, 30,null,null, @V_TOTALROWS);
	//CALL "RPT_ODS"."DS_SP_GET_LIST_AUTO_WARNING_TER_FCSA"('139841', null,'BM',20220131, 0, 30,null,null, @V_TOTALROWS);
	//CALL "RPT_ODS"."DS_SP_GET_LIST_AUTO_WARNING_TER_FCSA"('139841', null,'BM',20220131, 0, 30,null,NULL, @V_TOTALROWS);
	//CALL "RPT_ODS"."DS_SP_GET_LIST_AUTO_WARNING_TER_FCSA"('105427', null,'UM',20220131, 0, 0,null,NULL, @V_TOTALROWS);
	//CALL "RPT_ODS"."DS_SP_GET_LIST_AUTO_WARNING_TER_FCSA"('192899', null,'FC',20220131, 0, 0,null,NULL, @V_TOTALROWS);
	@GetMapping(AppApiConstant.API_LIST_REPORT_AUTOTER_DETAIL)				//
	@ApiOperation("Get list autoter")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
	@ApiResponse(code = 500, message = "Internal Server Error"),
    @ApiResponse(code = 401, message = "Unauthorized"),
	@ApiResponse(code = 403, message = "Forbidden")})
	public DtsApiResponse getListReportGroupManPowerDetail(HttpServletRequest request,
	          @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Integer  pageSizeParam,
	          @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Integer pageParam,
	          AutoterSearchDto searchDto) {
	      long start = System.currentTimeMillis();
	      try {
	          searchDto.setPage(pageParam);
	          searchDto.setPageSize(pageSizeParam);
	          CmsCommonPagination<AutoterReportDetailDto> common = warningAutoTerReportService.getListAutoTerReportDetail(searchDto);
	          ObjectDataRes<AutoterReportDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
	          return this.successHandler.handlerSuccess(resObj, start, null, null);
	      } catch (Exception ex) {
	          return this.errorHandler.handlerException(ex, start, null, null);
	      }
	  }
	
	
	   @PostMapping(AppApiConstant.API_LIST_REPORT_AUTOTER_DETAIL_EXPORT) 
	    @ApiOperation("Export report autoter")
	    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
	            @ApiResponse(code = 500, message = "Internal Server Error"),
	            @ApiResponse(code = 401, message = "Unauthorized"),
	            @ApiResponse(code = 403, message = "Forbidden")})

	    public ResponseEntity exportReportAutoter(HttpServletRequest request,
	    		@RequestBody AutoterSearchDto searchDto) {
	        long start = System.currentTimeMillis();
			ResponseEntity resObj = null;
	        try {
	        	
	        	resObj = warningAutoTerReportService.getListAutoTerReportExport (searchDto);
	        } catch (Exception ex) {
	        	logger.error("##exportLis##", ex.getMessage());
	        }
	        return resObj;
	    }
	
	   
	   
	   // CALL "RPT_ODS"."DS_SP_GET_LIST_AUTO_WARNING_TER_FCSA_TOTAL"('260990', 260988,'OH',20220131, null, @V_TOTALROWS);
	   // CALL "RPT_ODS"."DS_SP_GET_LIST_AUTO_WARNING_TER_FCSA_TOTAL"('139841', null,'BM',20220131,null, @V_TOTALROWS);
	   // CALL "RPT_ODS"."DS_SP_GET_LIST_AUTO_WARNING_TER_FCSA_TOTAL"('139841', null,'BM',20220131, NULL, @V_TOTALROWS);
	   // CALL "RPT_ODS"."DS_SP_GET_LIST_AUTO_WARNING_TER_FCSA_TOTAL"('105427', null,'UM',20220131,NULL, @V_TOTALROWS);
	   // CALL "RPT_ODS"."DS_SP_GET_LIST_AUTO_WARNING_TER_FCSA_TOTAL"('192899', null,'FC',20220131,NULL, @V_TOTALROWS);
		@GetMapping(AppApiConstant.API_AUTOTER_SUMARY)				//
		@ApiOperation("Get list autoter")
		@ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
		@ApiResponse(code = 500, message = "Internal Server Error"),
	    @ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")})
		public DtsApiResponse getAutoterSumary(HttpServletRequest request,
		          AutoterSearchDto searchDto) {
		      long start = System.currentTimeMillis();
		      try {
		          CmsCommonPagination<AutoterSumaryDto> common = warningAutoTerReportService.getAutoterSumary(searchDto);
		          ObjectDataRes<AutoterSumaryDto> resObj = new ObjectDataRes<>(0, common.getData());
		          return this.successHandler.handlerSuccess(resObj, start, null, null);
		      } catch (Exception ex) {
		          return this.errorHandler.handlerException(ex, start, null, null);
		      }
		  }
		
}
