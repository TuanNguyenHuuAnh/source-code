package vn.com.unit.ep2p.rest.cms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.agentbank.entity.AgentBankUpdateHistory;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.dto.AgentBankDto;
import vn.com.unit.ep2p.admin.dto.BankInfoUpdateHistoryDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiAgentBankService;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + "/agentbank")
@Api(tags = { "Infomation Agent Bank" })
public class AgentBankRest extends AbstractRest {

	@Autowired
	ApiAgentBankService agentBankService;
    
	@Autowired
	Db2ApiService db2ApiService;
	
	@Autowired
	@Qualifier("appSystemConfigServiceImpl")
	JcaSystemConfigService jcaSystemConfigService;
	
	@GetMapping("/get-list-banks")
	@ApiOperation("Api provides a list Info Bank on system")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })

	public DtsApiResponse getListBanks(HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
			List<SelectItem> listBanks = agentBankService.getListBanks();
			return this.successHandler.handlerSuccess(listBanks, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping("/get-list-branchs")
	@ApiOperation("Api provides a list branchs")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListBranchs(HttpServletRequest request, @RequestParam(value = "bankCode", required = false) String bankCode) {
		long start = System.currentTimeMillis();
		try {
			List<SelectItem> listBank = agentBankService.getListBankBranchs(bankCode);
			return this.successHandler.handlerSuccess(listBank, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping("/get-agent-bank-info")
	@ApiOperation("Api provides a list Info Bank on system")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getBankInfoAgent(HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
			AgentBankDto agentBank = db2ApiService.getAgentBankInfo(UserProfileUtils.getFaceMask());
			return this.successHandler.handlerSuccess(agentBank, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@PostMapping("/send-otp")
	@ApiOperation("Send OTP when update bank info")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	
	public DtsApiResponse sendOtpForUpdateBankInfo(HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
			boolean isSuccess = agentBankService.sendOtp(UserProfileUtils.getFaceMask());
			return this.successHandler.handlerSuccess(isSuccess, start, null, null);
		}
		catch (Exception e) {
			return this.errorHandler.handlerException(e, start, null, null);		
		}
	}
	
	@PostMapping("/get-ocr-info")
	@ApiOperation("Get ORC information")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	
	public DtsApiResponse getOrcInfo(
			@RequestParam(required = false, name = "documentList")List<MultipartFile> documentList,
			HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
			String idNum = agentBankService.getOcrInfo(documentList);
			return this.successHandler.handlerSuccess(idNum, start, null, null);
		}
		catch (Exception e) {
			return this.errorHandler.handlerException(e, start, null, null);
		}
	}
	
	@GetMapping("/get-bank-info-upd-historys")
	@ApiOperation("Api provides a list Info Bank on system")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getBankInfoUpdHistorys(HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
			List<BankInfoUpdateHistoryDto> data = agentBankService.getBankInfoUpdHistorys(UserProfileUtils.getFaceMask());
			return this.successHandler.handlerSuccess(data, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@PostMapping("/update-bank-info")
	@ApiOperation("Update bank info")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse updateBankInfo(
			@RequestParam(required = false, name = "documentList")List<MultipartFile> documentList,
			@RequestParam(required = false, name = "dto")String dto,
			HttpServletRequest request) {
        long start = System.currentTimeMillis();

		Gson gson = new Gson();
		AgentBankDto dataSubmit = gson.fromJson(dto, AgentBankDto.class);
		dataSubmit.setAgentCode(UserProfileUtils.getFaceMask());
		try {
			AgentBankUpdateHistory entity = agentBankService.updateBankInfo(dataSubmit, documentList);
			dataSubmit.setBankCode(entity.getBankCode());
			dataSubmit.setEmail(entity.getEmail());
			String res = RetrofitUtils.updateBankAccount(dataSubmit);
			if (res != null) {
				return this.errorHandler.handlerException(new Exception(res), start, null, null);
			}
			agentBankService.sendMailSuccess(dataSubmit);
			return this.successHandler.handlerSuccess(true, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@PostMapping("/get-bank-document")
	@ApiOperation("Api provides pdf template")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 500, message = "Internal Server Error"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	public ResponseEntity<byte[]> getExportTemplate(
			HttpServletRequest request,
			@RequestBody BankInfoUpdateHistoryDto data
	) {
		try {
			return agentBankService.getDocument(data);
		} catch (Exception ex) {
			return null;
		}
	}
	
	@GetMapping("/check-dup-bank-account-number")
    @ApiOperation("Check duplicate bank account number")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse checkDupBankAccountNumber(String bankAccountNumber) {
		long start = System.currentTimeMillis();
        try {
			boolean res = db2ApiService.checkDupBankAccountNumber(UserProfileUtils.getFaceMask(), bankAccountNumber);
			return this.successHandler.handlerSuccess(res, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
    }
}
