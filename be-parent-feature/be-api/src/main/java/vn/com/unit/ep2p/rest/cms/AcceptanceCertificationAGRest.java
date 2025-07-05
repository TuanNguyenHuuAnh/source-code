package vn.com.unit.ep2p.rest.cms;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;

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
import vn.com.unit.cms.core.module.ag.dto.AcceptanceCertificationAGDto;
import vn.com.unit.cms.core.module.ag.dto.AcceptanceCertificationDetailAG;
import vn.com.unit.cms.core.module.ag.dto.AcceptanceCertificationReportAGDto;
import vn.com.unit.cms.core.module.payment.entity.PaymentConfirm;
import vn.com.unit.cms.core.module.ga.dto.search.IncomeSearchGa;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.dto.AgentTaxBankInfoDto;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.service.AcceptanceCertificationAGService;
import vn.com.unit.ep2p.service.OtpService;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_ODS_ACCEPTANCE_AG)
@Api(tags = {CmsApiConstant.API_ODS_ACCEPTANCE_AG_DESCR})
public class AcceptanceCertificationAGRest {
	@Autowired
	protected ErrorHandler errorHandler;
	
	@Autowired
	protected SuccessHandler successHandler;
	
	@Autowired
	private AcceptanceCertificationAGService acceptanceCertificationAGService;
	
	private static final Logger logger = LoggerFactory.getLogger(AcceptanceCertificationAGRest.class);
	
	@GetMapping(AppApiConstant.API_GET_AGENT_TAX_BANK_INFO)
	@ApiOperation("Get agent tax bank info AG")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	
	public DtsApiResponse getAgentTaxBankInfo(HttpServletRequest request, IncomeSearchGa searchDto) throws JsonProcessingException {
		long start = System.currentTimeMillis();
		try {
			String agentCode = UserProfileUtils.getFaceMask();
			
			AgentTaxBankInfoDto taxbankInfo = acceptanceCertificationAGService.getTaxBusinessHouseHoldByAgentCode(agentCode);
			
			return this.successHandler.handlerSuccess(taxbankInfo, start, null, null);
		} catch (Exception ex) {
			logger.error("Error while retrieving agent tax bank info AG: " + ex.toString());
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping(AppApiConstant.API_GET_PAYMENT_MONTH_AG)
	@ApiOperation("Get acceptance certification AG")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	
	public DtsApiResponse getPaymentMonthAG(HttpServletRequest request, IncomeSearchGa searchDto) throws JsonProcessingException {
		long start = System.currentTimeMillis();
		try {
			String agentCode = UserProfileUtils.getFaceMask();
			
			AcceptanceCertificationAGDto common = acceptanceCertificationAGService.getAcceptanceCertificationAG(agentCode, searchDto.getCutOffDateYYYYMM());
			
			PaymentConfirm paymentConfirm = acceptanceCertificationAGService.getPaymentConfirmInfoAG(agentCode, searchDto.getCutOffDateYYYYMM());

			
			if (paymentConfirm != null) {
				common.setConfirmId(paymentConfirm.getId());
				if (paymentConfirm.getConfirmTime() != null) {
					common.setConfirmTime(paymentConfirm.getConfirmTime());
				}
				else if (paymentConfirm.getRejectReason() != null) {
					common.setConfirmTime(null);
					common.setRejectReason(paymentConfirm.getRejectReason());
				}
			}
			
			return this.successHandler.handlerSuccess(common, start, null, null);
		} catch (Exception ex) {
			logger.error("Error while retrieving acceptance certification AG info: " + ex.toString());
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@PostMapping(AppApiConstant.API_ACCEPTANCE_CERTIFICATION_CONFIRM_AG)
	@ApiOperation("Confirm acceptance certification AG")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	
	public DtsApiResponse confirmAcceptanceCertificationAG(
			HttpServletRequest request, 
			@RequestBody AcceptanceCertificationDetailAG data
	) throws JsonProcessingException {
		long start = System.currentTimeMillis();
		try {
			boolean isSuccess = acceptanceCertificationAGService.confirmAcceptanceCertificationAG(data);
			return this.successHandler.handlerSuccess(isSuccess, start, null, null);
		}
		catch (Exception e) {
			logger.error("Error while confirming AC: " + e.toString());
			return this.errorHandler.handlerException(e, start, null, null);
		}
	}
	
	@PostMapping(AppApiConstant.API_ACCEPTANCE_CERTIFICATION_DENY_AG)
	@ApiOperation("Confirm acceptance certification AG")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	
	public DtsApiResponse denyAcceptanceCertificationAG(HttpServletRequest request, @RequestBody AcceptanceCertificationDetailAG data) throws JsonProcessingException {
		long start = System.currentTimeMillis();
		try {
			boolean isSuccess = acceptanceCertificationAGService.denyAcceptanceCertificationAG(data);
			return this.successHandler.handlerSuccess(isSuccess, start, null, null);
		}
		catch (Exception e) {
			logger.error("Error while saving reject reason: " + e.toString());
			return this.errorHandler.handlerException(e, start, null, null);
		}
	}
	
	@PostMapping(AppApiConstant.API_OTP_ACCEPTANCE_CERTIFICATION_AG)
	@ApiOperation("Initializing OTP session for confirming acceptance certification AG")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	
	public DtsApiResponse getOtpAcceptanceCertificationAG(HttpServletRequest request, @RequestBody IncomeSearchGa searchDto) throws JsonProcessingException {
		long start = System.currentTimeMillis();
		try {
			boolean isSuccess = acceptanceCertificationAGService.sendOTPAgent(UserProfileUtils.getFaceMask(), searchDto.getCutOffDateYYYYMM());			
			return this.successHandler.handlerSuccess(isSuccess, start, null, null);
		}
		catch (Exception e) {
			logger.error("Error while sending OTP: " + e.toString());
			return this.errorHandler.handlerException(e, start, null, null);		
		}
	}
	
	@PostMapping(AppApiConstant.API_EXPORT_ACCEPTANCE_CERTIFICATION_AG)
	@ApiOperation("Export acceptance certification AG")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	
	public ResponseEntity<byte[]> exportAcceptanceCertificationPdfAG(
		HttpServletRequest request, 
		@RequestBody IncomeSearchGa searchDto
	) {
		try {
			String agentCode = UserProfileUtils.getFaceMask();
			return acceptanceCertificationAGService.getAcceptanceCertificationReportAG(agentCode, searchDto.getCutOffDateYYYYMM(), searchDto.getNote2(), searchDto.getNote3());
		} catch (Exception e) {
			logger.error("Error while exporting PDF: " + e.toString());
			return null;
		}
	}
}