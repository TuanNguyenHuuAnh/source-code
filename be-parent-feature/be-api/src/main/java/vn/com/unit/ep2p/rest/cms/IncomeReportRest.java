package vn.com.unit.ep2p.rest.cms;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetail;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalMonthSearchDto;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalMonthlyDto;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalWeeklyDto;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalYearlyDto;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalYearlySearchDto;
import vn.com.unit.cms.core.module.income.dto.YearlyPaymentDto;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportMO;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportSearchDto;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportTQP;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportTSM;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.enumdef.IncomePersonalWeeklyEnum;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.ApiIncomeReportService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_ODS_REPORT_INCOME)
@Api(tags = { "Report income" })
public class IncomeReportRest extends AbstractRest{
	
	@Autowired
	ApiIncomeReportService apiIncomeReportService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping(AppApiConstant.API_REPORT_INCOME_BONUS_MO)
    @ApiOperation("List report income bonus MO by view")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
	
    public DtsApiResponse getListReportIncomeBonusMO(HttpServletRequest request
    		, IncomeBonusReportSearchDto searchDto){
    	long start = System.currentTimeMillis();
        try {
            String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setAgentCode(agentCode);
            List<IncomeBonusReportMO> data = apiIncomeReportService.getListReportIncomeBonusMO(searchDto);
        	return this.successHandler.handlerSuccess(data, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_REPORT_INCOME_BONUS_TQP)
    @ApiOperation("List report income bonus TQP by view")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
	
    public DtsApiResponse getListReportIncomeBonusTQP(HttpServletRequest request
    		, IncomeBonusReportSearchDto searchDto){
    	long start = System.currentTimeMillis();
        try {
            String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setAgentCode(agentCode);
            List<IncomeBonusReportTQP> data = apiIncomeReportService.getListReportIncomeBonusTQP(searchDto);
        	return this.successHandler.handlerSuccess(data, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_REPORT_INCOME_BONUS_TSM)
    @ApiOperation("List report income bonus TQP by view")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
	
    public DtsApiResponse getListReportIncomeBonusTSM(HttpServletRequest request
    		, IncomeBonusReportSearchDto searchDto){
    	long start = System.currentTimeMillis();
        try {
            String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setAgentCode(agentCode);
            List<IncomeBonusReportTSM> data = apiIncomeReportService.getListReportIncomeBonusTSM(searchDto);
        	return this.successHandler.handlerSuccess(data, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@SuppressWarnings("rawtypes")
	@GetMapping(AppApiConstant.API_EXPORT_REPORT_INCOME_BONUS)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportReportIncome(HttpServletRequest request
			, IncomeBonusReportSearchDto searchDto ){
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setAgentCode(agentCode);
			resObj = apiIncomeReportService.exportReportIncome(searchDto, locale);
		} catch (Exception e) {
			logger.error("##exportLis##", e);
		}
		return resObj;
	}
	
	//monthly sumary
	@GetMapping(AppApiConstant.API_INCOME_PERSONAL_MONTHLY_SUMARY)
    @ApiOperation("List income personal weekly")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
	
    public DtsApiResponse getListIncomePersonalMonthlySumary(HttpServletRequest request
    		, IncomePersonalMonthSearchDto searchDto){
    	long start = System.currentTimeMillis();
        try {
        	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            List<IncomePersonalMonthlyDto> datas = apiIncomeReportService.getListIncomePersonalMonthlySumary(searchDto);
        	return this.successHandler.handlerSuccess(datas, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	//monthly detail
    @GetMapping(AppApiConstant.API_INCOME_PERSONAL_MONTHLY_DETAIL)
    @ApiOperation("List income personal weekly")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
    
    public DtsApiResponse getListIncomePersonalMonthlyDetail(HttpServletRequest request
            , IncomePersonalMonthSearchDto searchDto){
        long start = System.currentTimeMillis();
        try {
            searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            List<IncomePersonalMonthlyDto> datas = apiIncomeReportService.getListIncomePersonalMonthlyDetail(searchDto);
            return this.successHandler.handlerSuccess(datas, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_INCOME_PERSONAL_MONTHLY)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportIncomePersonalMonthly(HttpServletRequest request
			, @RequestBody IncomePersonalMonthSearchDto searchDto ){
		ResponseEntity resObj = null;
		try {
        	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			resObj = apiIncomeReportService.exportIncomePersonalMonthly(searchDto);
		} catch (Exception e) {
			logger.error("##exportIncomePersonalMonthly##", e);
		}
		return resObj;
	}
	
	//weekly
	@GetMapping(AppApiConstant.API_INCOME_PERSONAL_WEEKLY)
    @ApiOperation("List income personal weekly")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
	
    public DtsApiResponse getListIncomePersonalWeekly(HttpServletRequest request
    		, IncomePersonalMonthSearchDto searchDto){
    	long start = System.currentTimeMillis();
        try {
        	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            ObjectDataRes<IncomePersonalWeeklyDto> datas = apiIncomeReportService.getListIncomePersonalWeekly(searchDto);
        	return this.successHandler.handlerSuccess(datas, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@Autowired
    ApiAgentDetailService apiAgentDetailService;
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_INCOME_PERSONAL_WEEKLY)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportIncomePersonalWeekly(HttpServletRequest request, @RequestBody IncomePersonalMonthSearchDto searchDto ){
		ResponseEntity resObj = null;
		try {
        	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
		    CmsAgentDetail infoAgent = apiAgentDetailService.getCmsAgentDetailByUsername(searchDto.getAgentCode());
		    ObjectDataRes<IncomePersonalWeeklyDto> datas = apiIncomeReportService.getListIncomePersonalWeekly(searchDto);
		    	for(IncomePersonalWeeklyDto ls:datas.getDatas()) {
			       int no = datas.getDatas().indexOf(ls);
				   ls.setNo(no+1);			    
				 }		    		
		   // datas.getDatas().stream().forEach(x -> x.setPaymentAmountDto(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(x.getPaymentAmount()))));
		    	
		    datas.getDatas().stream().forEach(x -> x.setPaymentAmountDto(BigDecimal.valueOf(x.getPaymentAmount())));

			int maxRow = datas.getDatas().size();
			IncomePersonalWeeklyDto total = datas.getDatas().get(maxRow-1);
			datas.getDatas().remove(maxRow-1);
			
			String type = total.getIncomeType();
			BigDecimal amount =total.getPaymentAmountDto();
			
		    List<String> lstInfo = Arrays.asList(
		            "Mã đại lý: "+infoAgent.getAgentCode(),
		            "Tên đại lý: "+infoAgent.getFullName(),
		            "Số TK: "+infoAgent.getBankAccountNumber() + " - " + infoAgent.getBankAccountName(),
		            "Kỳ thanh toán: "+ (searchDto.getPaymentPeriod().substring(0, 6)).substring(4) +"/" +searchDto.getPaymentPeriod().substring(0, 4)
		            );
			resObj = apiIncomeReportService.exportListData(datas.getDatas(), type, amount ,"Thu_nhap_tuan.xlsx", "A9", IncomePersonalWeeklyEnum.class, IncomePersonalWeeklyDto.class, lstInfo, "A3");
		} catch (Exception e) {
			logger.error("##exportIncomePersonalWeekly##", e);
		}
		return resObj;
	}
	
	//year
	@GetMapping(AppApiConstant.API_INCOME_PERSONAL_YEARLY)
	@ApiOperation("List income personal yearly")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
	
    public DtsApiResponse getListIncomePersonalWeekly(HttpServletRequest request
    		, IncomePersonalYearlySearchDto searchDto){
    	long start = System.currentTimeMillis();
        try {
            String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setAgentCode(agentCode);
            List<IncomePersonalYearlyDto> data = apiIncomeReportService.getListIncomePersonalYearly(searchDto);
        	return this.successHandler.handlerSuccess(data, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_INCOME_PERSONAL_YEARLY)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportIncomePersonalYearly(HttpServletRequest request, IncomePersonalYearlySearchDto searchDto ){
		ResponseEntity resObj = null;
		try {
        	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			resObj = apiIncomeReportService.exportIncomePersonalYearly(searchDto);
		} catch (Exception e) {
			logger.error("##exportLis##", e);
		}
		return resObj;
	}
	
	// Export year
		
	@GetMapping(AppApiConstant.API_GET_YEARLY_PAYMENT_FOR_AG)
    @ApiOperation("List income personal weekly")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
	
    public DtsApiResponse getYearlyPaymentForAG(HttpServletRequest request
    		, IncomePersonalYearlySearchDto searchDto){
    	long start = System.currentTimeMillis();
        try {
        	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            YearlyPaymentDto data = apiIncomeReportService.getYearlyPaymentForAG(searchDto);
        	return this.successHandler.handlerSuccess(data, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_GET_YEARLY_PAYMENT_FOR_GA)
    @ApiOperation("List income personal weekly")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
	
    public DtsApiResponse getYearlyPaymentForGA(HttpServletRequest request
    		, IncomePersonalYearlySearchDto searchDto){
    	long start = System.currentTimeMillis();
        try {
        	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            YearlyPaymentDto data = apiIncomeReportService.getYearlyPaymentForGA(searchDto);
        	return this.successHandler.handlerSuccess(data, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_YEARLY_PAYMENT_FOR_AG)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportYearlyPaymentForAG(HttpServletRequest request
			, @RequestBody IncomePersonalYearlySearchDto searchDto ){
		ResponseEntity resObj = null;
		try {
        	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			resObj = apiIncomeReportService.exportYearlyPaymentForAG(searchDto);
		} catch (Exception e) {
			logger.error("##exportIncomePersonalMonthly##", e);
		}
		return resObj;
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_YEARLY_PAYMENT_FOR_GA)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportYearlyPaymentForGA(HttpServletRequest request
			, @RequestBody IncomePersonalYearlySearchDto searchDto ){
		ResponseEntity resObj = null;
		try {
        	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			resObj = apiIncomeReportService.exportYearlyPaymentForGA(searchDto);
		} catch (Exception e) {
			logger.error("##exportIncomePersonalMonthly##", e);
		}
		return resObj;
	}
}
