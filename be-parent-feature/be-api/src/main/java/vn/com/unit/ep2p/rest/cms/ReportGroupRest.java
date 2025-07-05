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
import vn.com.unit.cms.core.module.reportGroup.*;
import vn.com.unit.cms.core.module.reportGroup.dto.search.ReportGroupSearchDetailDto;
import vn.com.unit.cms.core.module.reportGroup.dto.search.ReportGroupSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiReportGroupService;
import vn.com.unit.ep2p.utils.LangugeUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

/**
 * @Last updated: 22/03/2024    nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
@RestController
@RequestMapping(CmsApiConstant.BASE_API_ODS_URL + CmsApiConstant.API_CMS_REPORT_GROUP)
@Api(tags = {CmsApiConstant.API_CMS_REPORT_GROUP_DESCR})
public class ReportGroupRest extends AbstractRest {

    @Autowired
    private ApiReportGroupService apiReportGroupService;
    
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    Db2ApiService db2ApiService;
	
	//CALL "RPT_ODS"."DS_SP_GET_REPORTING_ACTIVITY_MANPOWER_PRIMEUM"('125064', null,'UM',202201,'QTD',   0, 30,null,NULL, @V_TOTALROWS);
    @GetMapping(AppApiConstant.API_LIST_REPORT_GROUP_TOTAL) 			//Báo cáo hoạt động
    @ApiOperation("Get list report group total")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListReportGroupTotal(HttpServletRequest request,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Integer  pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Integer pageParam,
                                                  ReportGroupSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam);
            searchDto.setPageSize(pageSizeParam);
            ReportGroupTotalDto common = apiReportGroupService.getListReportGroupTotal(searchDto);
            return this.successHandler.handlerSuccess(common, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    
    //CALL "RPT_ODS"."DS_SP_GET_REPORTING_ACTIVITY_GROUP_MANPOWER"('260990', 260988,'OH',20200331,'MTD','ALL', 0, 30,null,null, @V_TOTALROWS);
    @GetMapping(AppApiConstant.API_LIST_REPORT_GROUP_MANPOWER)					//Lực lượng tư vấn
    @ApiOperation("Get list report group premium")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse  getListReportGroupManpower(HttpServletRequest request,
                                                    @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Integer  pageSizeParam,
                                                    @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Integer pageParam,
                                                    ReportGroupSearchDetailDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam);
            searchDto.setPageSize(pageSizeParam);
            CmsCommonPagination<ReportGroupResultManPower2Dto> common = apiReportGroupService.getListReportGroupManPower(searchDto);
			ObjectDataRes<ReportGroupResultManPower2Dto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    // CALL "RPT_ODS"."DS_SP_GET_REPORTING_ACTIVITY_GROUP_PREMIUM"('260990', 260988,'OH',20220131,'MTD','ALL', 0, 30,null,null, @V_TOTALROWS);
    @GetMapping(AppApiConstant.API_LIST_REPORT_GROUP_PREMIUM)						//Doanh số
    @ApiOperation("Get list report group manpower")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListReportGroupPremium(HttpServletRequest request,
                                                          @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
                                                          @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam,
                                                          ReportGroupSearchDetailDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            CmsCommonPagination<ReportGroupResultPremiumDto> common = apiReportGroupService.getListReportGroupPremium(searchDto);
			ObjectDataRes<ReportGroupResultPremiumDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    

    //CALL "RPT_ODS"."DS_SP_GET_REPORTING_ACTIVITY_GROUP_PREMIUM"('243010', null,'CAO',202111,'MTD','ALL', 0, 30,null,NULL, @V_TOTALROWS);
    @GetMapping(AppApiConstant.API_LIST_REPORT_GROUP_PREMIUM_DETAIL)						//Doanh số detail CAO -> OH
    @ApiOperation("Get list report group manpower")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListReportGroupPremiumDetail(HttpServletRequest request,
                                                          @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Integer pageSizeParam,
                                                          @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Integer pageParam,
                                                          ReportGroupSearchDetailDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<ReportGroupResultPremiumDetailDto> common = apiReportGroupService.getListReportGroupPremiumDetail(searchDto);
            ObjectDataRes<ReportGroupResultPremiumDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    
  // CALL "RPT_ODS"."DS_SP_GET_REPORTING_ACTIVITY_GROUP_MANPOWER"('260990', 260988,'OH',20200331,'MTD','ALL', 0, 30,null,null, @V_TOTALROWS);
  @GetMapping(AppApiConstant.API_LIST_REPORT_GROUP_MANPOWER_DETAIL)				//Lực lượng tư vấn detail
  @ApiOperation("Get list report group premium deatil")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
          @ApiResponse(code = 500, message = "Internal Server Error"),
          @ApiResponse(code = 401, message = "Unauthorized"),
          @ApiResponse(code = 403, message = "Forbidden")})

  public DtsApiResponse getListReportGroupManPowerDetail(HttpServletRequest request,
          @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Integer  pageSizeParam,
          @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Integer pageParam,
          ReportGroupSearchDetailDto searchDto) {
      long start = System.currentTimeMillis();
      try {
          searchDto.setPage(0);
          searchDto.setPageSize(0);
          CmsCommonPagination<ReportGroupResultManPowerDetailDto> common = apiReportGroupService.getListReportGroupManpowerDetail(searchDto);
          ObjectDataRes<ReportGroupResultManPowerDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
          return this.successHandler.handlerSuccess(resObj, start, null, null);
      } catch (Exception ex) {
          return this.errorHandler.handlerException(ex, start, null, null);
      }
  }
  
  
  	// CALL "RPT_ODS"."DS_SP_GET_REPORTING_ACTIVITY_LEADER_PREMIUM"('139841', null,'BM',20220131,'MTD',  0, 30,null,NULL, @V_TOTALROWS);
  	//CALL "RPT_ODS"."DS_SP_GET_REPORTING_ACTIVITY_LEADER_PREMIUM"('105427', null,'UM',20220131,'MTD',  0, 30,null,NULL, @V_TOTALROWS);
    @GetMapping(AppApiConstant.API_LIST_REPORT_GROUP_PREMIUM_UM_BM_DETAIl)						//Doanh số detail UM -BM
    @ApiOperation("Get list report group premium")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListReportGroupPremiumUmBmDetail(HttpServletRequest request,
                                                          @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Integer pageSizeParam,
                                                          @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Integer pageParam,
                                                          ReportGroupSearchDetailDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam);
            searchDto.setPageSize(pageSizeParam);
            if (!db2ApiService.checkChildrenInParent(UserProfileUtils.getFaceMask(), searchDto.getAgentCode())) {
            	return this.errorHandler.handlerException(new Exception("Quyền truy cập không hợp lệ"), start, null, null);
            }
            CmsCommonPagination<ReportGroupResultPremiumDetailDto> common = apiReportGroupService.getListReportGroupPremiumUmBmDetail(searchDto);
            ObjectDataRes<ReportGroupResultPremiumDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    //CALL "RPT_ODS"."DS_SP_GET_REPORTING_ACTIVITY_LEADER_MANPOWER"('139841', null,'BM',20220131,'MTD',  0, 10000,null,NULL, @V_TOTALROWS);
    @GetMapping(AppApiConstant.API_LIST_REPORT_GROUP_MANPOWER_UM_BM_DETAIl)						//Lực lượng tư vấn detail UM -BM
    @ApiOperation("Get list report group premium")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListReportGroupManPowerUmBmDetail(HttpServletRequest request,
                                                          @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Integer pageSizeParam,
                                                          @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Integer pageParam,
                                                          ReportGroupSearchDetailDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam);
            searchDto.setPageSize(pageSizeParam);
            CmsCommonPagination<ReportGroupResultManPowerDetailDto> common = apiReportGroupService.getListReportGroupManPowerUmBmDetail(searchDto);
            ObjectDataRes<ReportGroupResultManPowerDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }


    @PostMapping(AppApiConstant.API_LIST_REPORT_GROUP_PREMIUM_EXPORT) //Export Doanh số detail CAO -> OH
    @ApiOperation("Export report group")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public ResponseEntity exportReportGroupPremium(HttpServletRequest request,
    		@RequestBody ReportGroupSearchDetailDto searchDto) {
        long start = System.currentTimeMillis();
		ResponseEntity resObj = null;
        try {
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	resObj = apiReportGroupService.getListReportGroupExportPremium (searchDto, locale);
        } catch (Exception ex) {
        	logger.error("##exportLis##", ex.getMessage());
        }
        return resObj;
    }
    
  @PostMapping(AppApiConstant.API_LIST_REPORT_GROUP_MANPOWER_EXPORT) //Export Lực lượng tư vấn detail CAO -> OH
  @ApiOperation("Export report group")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
          @ApiResponse(code = 500, message = "Internal Server Error"),
          @ApiResponse(code = 401, message = "Unauthorized"),
          @ApiResponse(code = 403, message = "Forbidden")})

  public ResponseEntity exportReportGroupManpower(HttpServletRequest request,
  		@RequestBody ReportGroupSearchDetailDto searchDto) {
      ResponseEntity resObj = null;
      try {
          Locale locale = LangugeUtil.getLanguageFromHeader(request);
          resObj = apiReportGroupService.getListReportGroupExportManpower(searchDto,locale);
      } catch (Exception ex) {
			logger.error("##exportLis###", ex.getMessage());
      }
		return resObj;
  }
    
    
    @PostMapping(AppApiConstant.API_LIST_REPORT_GROUP_PREMIUM_EXPORT_BM_UM) //Export Doanh số detail UM -> BM
    @ApiOperation("Export report group")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public ResponseEntity exportReportGroupPremiumUmBm(HttpServletRequest request,
    		@RequestBody ReportGroupSearchDetailDto searchDto) {
        long start = System.currentTimeMillis();
		ResponseEntity resObj = null;
        try {
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	resObj = apiReportGroupService.getListReportGroupExportPremiumUmBm (searchDto, locale);
        } catch (Exception ex) {
        	logger.error("##exportLis##", ex.getMessage());
        }
        return resObj;
    }
    
    @PostMapping(AppApiConstant.API_LIST_REPORT_GROUP_MANPOWER_EXPORT_BM_UM) //Export lực lượng tư vấn detail UM -> BM
    @ApiOperation("Export report group")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public ResponseEntity exportReportGroupManPowerUmBm(HttpServletRequest request,
    		@RequestBody ReportGroupSearchDetailDto searchDto) {
        long start = System.currentTimeMillis();
		ResponseEntity resObj = null;
        try {
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	resObj = apiReportGroupService.getListReportGroupExportManpowerUmBm (searchDto, locale);
        } catch (Exception ex) {
        	logger.error("##exportLis##", ex.getMessage());
        }
        return resObj;
    }
    
    
    @GetMapping(AppApiConstant.API_LIST_REPORT_GROUP_TARGET_ACHIEVEMENT_SALE)						//Lưới doanh số Target Achievement
    @ApiOperation("Get list target achievement sale")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListTargetAchievementSale(HttpServletRequest request,
                                                          @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
                                                          @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
                                                          ReportGroupSearchDetailDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            CmsCommonPagination<ReportGroupResultTargetAchievementDto> common = apiReportGroupService.getListTargetAchievementSale(searchDto);
            return this.successHandler.handlerSuccess(common, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    
    @GetMapping(AppApiConstant.API_LIST_REPORT_GROUP_TARGET_ACHIEVEMENT_COMPARE)						//Lưới So sánh Doanh số		
    @ApiOperation("Get list target achievement sale")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListTargetAchievementCompare(HttpServletRequest request,
                                                          @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
                                                          @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
                                                          ReportGroupSearchDetailDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            CmsCommonPagination<ReportGroupResultTargetAchievementDto> common = apiReportGroupService.getListTargetAchievementCompare(searchDto);
            return this.successHandler.handlerSuccess(common, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping(AppApiConstant.API_LIST_REPORT_GROUP_TARGET_ACHIEVEMENT_MISSING)						//Lưới Ước tính doanh số còn thiếu so với mục tiêu			
    @ApiOperation("Get list target achievement sale")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListTargetAchievementMissing(HttpServletRequest request,
                                                          @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
                                                          @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
                                                          ReportGroupSearchDetailDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            CmsCommonPagination<ReportGroupResultTargetAchievementDto> common = apiReportGroupService.getListTargetAchievementMissing(searchDto);
            return this.successHandler.handlerSuccess(common, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    
    
    @GetMapping(AppApiConstant.API_LIST_REPORT_GROUP_TARGET_ACHIEVEMENT_PAY_FEES)						//Lưới Tình hình nộp phí theo ngày	
    @ApiOperation("Get list target achievement sale")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListTargetAchievementPayFees(HttpServletRequest request,
                                                          @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
                                                          @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
                                                          ReportGroupSearchDetailDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            CmsCommonPagination<ReportGroupResultTargetAchievementDto> common = apiReportGroupService.getListTargetAchievementPayFees(searchDto);
            return this.successHandler.handlerSuccess(common, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

}
