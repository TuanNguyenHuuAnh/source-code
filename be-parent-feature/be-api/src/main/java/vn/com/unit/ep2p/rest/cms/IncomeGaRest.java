package vn.com.unit.ep2p.rest.cms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.ga.dto.IncomeGaDto;
import vn.com.unit.cms.core.module.ga.dto.IncomeMonthsGaDto;
import vn.com.unit.cms.core.module.ga.dto.search.IncomeSearchGa;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalWeeklyDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.enumdef.IncomeWeeklyGAEnum;
import vn.com.unit.ep2p.service.IncomeGaService;
import vn.com.unit.ep2p.service.impl.PersonalInsuranceDocServiceImpl;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_ODS_INCOME_GA)
@Api(tags = { CmsApiConstant.API_ODS_INCOME_GA_DESCR })
public class IncomeGaRest {

	@Autowired
	protected ErrorHandler errorHandler;

	@Autowired
	protected SuccessHandler successHandler;

	@Autowired
	private IncomeGaService incomeGaService;
	

	private static final Logger logger = LoggerFactory.getLogger(PersonalInsuranceDocServiceImpl.class);


	// get list income Ga months
	@GetMapping(AppApiConstant.API_LIST_INCOME_GA)
	@ApiOperation("Get income ga months")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })

	public DtsApiResponse getListIncomeGa(HttpServletRequest request, IncomeSearchGa searchDto) {
		long start = System.currentTimeMillis();
		try {
			IncomeGaDto common = incomeGaService.getListIncomeGa(searchDto);
			return this.successHandler.handlerSuccess(common, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	// get list income Ga Year
	@GetMapping(AppApiConstant.API_LIST_INCOME_GA_YEAR)
	@ApiOperation("Get income ga months")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
                            @ApiResponse(code = 500, message = "Internal Server Error"),
                            @ApiResponse(code = 401, message = "Unauthorized"),
                            @ApiResponse(code = 403, message = "Forbidden") })

	public DtsApiResponse getListIncomeGaYear(HttpServletRequest request, IncomeSearchGa searchDto) {
		long start = System.currentTimeMillis();
		try {
			List<IncomeGaDto> common = incomeGaService.getListIncomeGaYear(searchDto);
			return this.successHandler.handlerSuccess(common, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	// get list income Ga
	@GetMapping(AppApiConstant.API_LIST_INCOME_GA_DETAIL)
	@ApiOperation("Get income ga months")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })

	public DtsApiResponse getListIncomeGaDetail(HttpServletRequest request, IncomeSearchGa searchDto) {
		long start = System.currentTimeMillis();
		try {
			searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			List<IncomeMonthsGaDto> common = incomeGaService.callStoreIncomeGadDetail(searchDto);
			return this.successHandler.handlerSuccess(common, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	// export list income Ga
	@PostMapping(AppApiConstant.API_EXPORT_INCOME_GA)
	@ApiOperation("export income ga")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })

	public ResponseEntity exportListIncomeGa(HttpServletRequest request,
			 @RequestBody IncomeSearchGa searchDto) {
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			resObj = incomeGaService.exportListIncomeGa(searchDto,locale);
		} catch (Exception e) {
			logger.error("Export", e);
		}
		return resObj;
	}


	// export list income Ga
	@PostMapping(AppApiConstant.API_EXPORT_INCOME_GA_YEAR)
	@ApiOperation("export income ga year")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })

	public ResponseEntity exportListIncomeYearGa(HttpServletRequest request,
											 @RequestBody IncomeSearchGa searchDto) {
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			resObj = incomeGaService.exportListIncomeGaYear(searchDto,locale);
		} catch (Exception e) {
			logger.error("Export", e);
		}
		return resObj;
	}
	
	// Weekly 
	@GetMapping(AppApiConstant.API_DROPLIST_INCOME_WEEKLY_GA)
	@ApiOperation("Get income ga months")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
                            @ApiResponse(code = 500, message = "Internal Server Error"),
                            @ApiResponse(code = 401, message = "Unauthorized"),
                            @ApiResponse(code = 403, message = "Forbidden") })

	public DtsApiResponse getDroplistIncomeWeeklyGA(HttpServletRequest request, IncomeSearchGa searchDto) {
		long start = System.currentTimeMillis();
		try {
			searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			List<IncomeGaDto> common = incomeGaService.getDroplistIncomeWeeklyGA(searchDto);
			return this.successHandler.handlerSuccess(common, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	
	@GetMapping(AppApiConstant.API_LIST_INCOME_WEEKLY_GA)
	@ApiOperation("Get income ga months")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
                            @ApiResponse(code = 500, message = "Internal Server Error"),
                            @ApiResponse(code = 401, message = "Unauthorized"),
                            @ApiResponse(code = 403, message = "Forbidden") })

	public DtsApiResponse getListWeeklyGA(HttpServletRequest request, IncomeSearchGa searchDto) {
		long start = System.currentTimeMillis();
		try {			
			if (null == searchDto.getGadCode() || "".equalsIgnoreCase(searchDto.getGadCode())) {
				searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			} else {
				searchDto.setAgentCode(searchDto.getGadCode());
			}
			ObjectDataRes<IncomeGaDto> datas = incomeGaService.getListIncomeWeeklyGA(searchDto);
			return this.successHandler.handlerSuccess(datas, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	

	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_INCOME_WEEKLY_GA)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportIncomeWeeklyGA(HttpServletRequest request
			, @RequestBody IncomeSearchGa searchDto ){
		ResponseEntity resObj = null;
		try {
			
			if (null == searchDto.getGadCode() || "".equalsIgnoreCase(searchDto.getGadCode())) {
				searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			} else {
				searchDto.setAgentCode(searchDto.getGadCode());
			}
			
			String orgCode = searchDto.getOrgCode();

			List<IncomeGaDto> drops = incomeGaService.getDroplistIncomeWeeklyGA(searchDto);
			Optional<IncomeGaDto> firstItem = drops.stream()
				    .filter(item -> orgCode.equals(item.getOrgCode()))
				    .findFirst();
			
			List<String> lstInfo = new ArrayList<String>();
			if (firstItem.isPresent()) {
			    IncomeGaDto dto = firstItem.get();
			    lstInfo.add("Văn phòng : " + dto.getOfficeNameDisplay());
			    lstInfo.add("Giám đốc TĐL : " + dto.getGadCode() + " - "+ dto.getGadName());
			    lstInfo.add("Số tài khoản TĐL : " + dto.getBankAccountNumber() + " - "+ dto.getBankAccountName());
			    lstInfo.add("Phân hạng : " + dto.getSegmentGa());
				
			}
			// make format MM/YYYY
			String yearMonth = searchDto.getPaymentPeriod();
			String year = yearMonth.substring(0, 4); 
		    String month = yearMonth.substring(4);
		    lstInfo.add("Kỳ thanh toán : " + month + "/" + year); 
        	
		    ObjectDataRes<IncomeGaDto> datas = incomeGaService.getListIncomeWeeklyGA(searchDto);
		    datas.getDatas().stream().forEach(x -> x.setPayAmountBig(BigDecimal.valueOf(x.getPayAmount())));

		    // total row
		    int maxRow = datas.getDatas().size();
		    IncomeGaDto total = datas.getDatas().get(maxRow-1);
			datas.getDatas().remove(maxRow-1);
			
			String typePaymentName = total.getTypePaymentName();
			BigDecimal totalAmount =total.getPayAmountBig();
			
			String appendExportFileName = "_" + searchDto.getAgentCode() + "_" + orgCode + "_" + month + year;
			
			resObj = incomeGaService.exportIncomeWeeklyGA(datas.getDatas(), typePaymentName, totalAmount,
					"Thu_nhap_tuan_GA.xlsx", "B12", IncomeWeeklyGAEnum.class, IncomeGaDto.class, lstInfo, "B4", appendExportFileName);
		} catch (Exception e) {
			logger.error("##exportIncomeWeeklyGA##", e);
		}
		return resObj;
	}

}
