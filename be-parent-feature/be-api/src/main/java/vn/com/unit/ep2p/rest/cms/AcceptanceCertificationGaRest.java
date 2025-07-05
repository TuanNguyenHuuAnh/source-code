package vn.com.unit.ep2p.rest.cms;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;

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
import vn.com.unit.cms.core.module.ga.dto.AcceptanceCertificationDetailGa;
import vn.com.unit.cms.core.module.ga.dto.AcceptanceCertificationDto;
import vn.com.unit.cms.core.module.ga.dto.AcceptanceCertificationReportDto;
import vn.com.unit.cms.core.module.ga.dto.param.IncomeConfirmPaymentParamGa;
import vn.com.unit.cms.core.module.ga.dto.search.IncomeSearchGa;
import vn.com.unit.cms.core.module.payment.entity.PaymentConfirm;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.service.AcceptanceCertificationGaService;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_ODS_ACCEPTANCE_GA)
@Api(tags = {CmsApiConstant.API_ODS_ACCEPTANCE_DESCR})
public class AcceptanceCertificationGaRest {
	@Autowired
	protected ErrorHandler errorHandler;
	
	@Autowired
	protected SuccessHandler successHandler;
	
	@Autowired
	private AcceptanceCertificationGaService acceptanceCertificationGaService;
	
	private static final Logger logger = LoggerFactory.getLogger(AcceptanceCertificationGaRest.class);
	
	@GetMapping(AppApiConstant.API_GET_ACCEPTANCE_CERTIFICATION_GA)
	@ApiOperation("Get acceptance certification ga")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	
	public DtsApiResponse getAcceptanceCertificationGa(HttpServletRequest request, IncomeSearchGa searchDto) throws JsonProcessingException {
		long start = System.currentTimeMillis();
		try {
			searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			
			IncomeConfirmPaymentParamGa incomeParamGa = new IncomeConfirmPaymentParamGa();
			incomeParamGa.gadCode = searchDto.getAgentCode();
			incomeParamGa.orgCode = searchDto.getOrgCode() != "" ? searchDto.getOrgCode() : null;
			incomeParamGa.period = searchDto.getPaymentPeriod();
			if (StringUtils.isEmpty(searchDto.getPaymentPeriod())) {
				incomeParamGa.period = searchDto.getYear() + searchDto.getMonth();
			}
			
			AcceptanceCertificationDto common = acceptanceCertificationGaService.getAcceptanceCertificationDetail(incomeParamGa);
			if (common.getIncomes().size() <= 0) {
				return this.successHandler.handlerSuccess(common, start, null, null);
			}
			
			PaymentConfirm paymentConfirm = acceptanceCertificationGaService.getPaymentConfirmInfo(incomeParamGa.gadCode, incomeParamGa.orgCode, incomeParamGa.period);
			if (paymentConfirm != null) {
				common.setConfirmId(paymentConfirm.getId());
				if (paymentConfirm.getConfirmTime() != null) {
					common.setConfirmTime(paymentConfirm.getConfirmTime());
				}
				else if (paymentConfirm.getRejectReason() != null) {
					common.setConfirmTime(null);
					common.setRejectReason(paymentConfirm.getRejectReason());
					common.setRejectTime(paymentConfirm.getRejectTime());
				}
			}
			
			return this.successHandler.handlerSuccess(common, start, null, null);
		} catch (Exception ex) {
			logger.error("Error while retrieving acceptance certification GA info: " + ex.toString());
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@PostMapping(AppApiConstant.API_ACCEPTANCE_CERTIFICATION_CONFIRM_GA)
	@ApiOperation("Confirm acceptance certification ga")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	
	public DtsApiResponse confirmAcceptanceCertificationGa(
			HttpServletRequest request, 
			@RequestBody AcceptanceCertificationDetailGa data
	) throws JsonProcessingException {
		long start = System.currentTimeMillis();
		try {
			boolean isSuccess = acceptanceCertificationGaService.confirmAcceptanceCertification(data);
			return this.successHandler.handlerSuccess(isSuccess, start, null, null);
		}
		catch (Exception e) {
			logger.error("Error while confirming AC: " + e.toString());
			return this.errorHandler.handlerException(e, start, null, null);
		}
	}
	
	@PostMapping(AppApiConstant.API_ACCEPTANCE_CERTIFICATION_DENY_GA)
	@ApiOperation("Confirm acceptance certification ga")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	
	public DtsApiResponse denyAcceptanceCertificationGa(HttpServletRequest request, @RequestBody AcceptanceCertificationDetailGa data) throws JsonProcessingException {
		long start = System.currentTimeMillis();
		try {
			boolean isSuccess = acceptanceCertificationGaService.denyAcceptanceCertification(data);
			return this.successHandler.handlerSuccess(isSuccess, start, null, null);
		}
		catch (Exception e) {
			logger.error("Error while saving reject reason: " + e.toString());
			return this.errorHandler.handlerException(e, start, null, null);
		}
	}
	
	@PostMapping(AppApiConstant.API_OTP_ACCEPTANCE_CERTIFICATION_GA)
	@ApiOperation("Initializing OTP session for confirming acceptance certification")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	
	public DtsApiResponse getOtpAcceptanceCertificationGa(HttpServletRequest request) throws JsonProcessingException {
		long start = System.currentTimeMillis();
		try {
			boolean isSuccess = acceptanceCertificationGaService.sendOtpGad(UserProfileUtils.getFaceMask());
			return this.successHandler.handlerSuccess(isSuccess, start, null, null);
		}
		catch (Exception e) {
			logger.error("Error while sending OTP: " + e.toString());
			return this.errorHandler.handlerException(e, start, null, null);		
		}
	}
	
	@PostMapping(AppApiConstant.API_EXPORT_ACCEPTANCE_CERTIFICATION_GA)
	@ApiOperation("Export acceptance certification ga")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	
	public ResponseEntity<byte[]> exportAcceptanceCertificationPdfGa(
		HttpServletRequest request, 
		@RequestBody AcceptanceCertificationReportDto req
	) {
		try {
			IncomeConfirmPaymentParamGa incomesParams = req.getIncomesParams();
			PaymentConfirm paymentConfirm = acceptanceCertificationGaService.getPaymentConfirmInfo(
				incomesParams.gadCode,
				incomesParams.orgCode,
				incomesParams.period
			);
			return acceptanceCertificationGaService.getAcceptanceCertificationReport(req, paymentConfirm);
		} catch (Exception e) {
			logger.error("Error while exporting PDF: " + e.toString());
			return null;
		}
	}
}