package vn.com.unit.ep2p.rest.cms;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.threeten.bp.Year;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;

import vn.com.unit.ep2p.service.TaxCommitmentService;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.req.CommitmentInfoFileReq;
import vn.com.unit.ep2p.dto.res.UploadTaxCommitmentDocRes;
import vn.com.unit.cms.core.module.agent.dto.AgentInfoTaxCommitmentExportDto;
import vn.com.unit.cms.core.module.income.entity.TaxRegistration;
import vn.com.unit.core.security.UserProfileUtils;

import vn.com.unit.ep2p.admin.dto.AgentAllowExportTaxCommitmentDto;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_INFO_AGENT)
@Api(tags = { CmsApiConstant.API_CMS_INFO_AGENT_DESCR })
public class TaxCommitmentRest extends AbstractRest {
	
	@Autowired
	private TaxCommitmentService taxCommitmentService;
	
	@GetMapping(AppApiConstant.API_CHECK_EXPORT_TAX_COMMITMENT)
	@ApiOperation("Api provides agent info")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 500, message = "Internal Server Error"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	public DtsApiResponse getInfoAgent(HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
			AgentAllowExportTaxCommitmentDto data = taxCommitmentService.getAgentInfoTaxCommitment(UserProfileUtils.getFaceMask());
			return this.successHandler.handlerSuccess(data, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@PostMapping(AppApiConstant.API_GET_TEMPLATE_EXPORT_TAX_COMMITMENT)
	@ApiOperation("Api provides template for export")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 500, message = "Internal Server Error"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	public ResponseEntity<byte[]> getExportTemplate(
			HttpServletRequest request,
			@RequestBody AgentInfoTaxCommitmentExportDto data
	) {
		try {
			return taxCommitmentService.getExportTemplate(data);
		} catch (Exception ex) {
			return null;
		}
	}
	
	@PostMapping(AppApiConstant.API_UPLOAD_TAX_COMMITMENT)
	@ApiOperation("Api upload tax commitment document")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 500, message = "Internal Server Error"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	public DtsApiResponse uploadTaxCommitment(
		HttpServletRequest request,
		@RequestParam(required = true, name = "file") MultipartFile file,
		@RequestParam(required = true, name = "numberOfPages") String numberOfPages,
		@RequestParam(required = true, name = "mimeType") String mimeType
	) throws Exception {
		long start = System.currentTimeMillis();
		try {
			UploadTaxCommitmentDocRes res = new UploadTaxCommitmentDocRes();
			
			CommitmentInfoFileReq fileInfo = new CommitmentInfoFileReq() {{
				setNumberOfPages(numberOfPages);
				setMimeType(mimeType);
			}};
			String agentCode = UserProfileUtils.getFaceMask();
			Integer currYear = Year.now().getValue();
			boolean checkDocumentStatus = taxCommitmentService.checkDocumentStatus(agentCode);
			if (taxCommitmentService.checkUploadStatus(agentCode, currYear.toString()) && checkDocumentStatus) {
				res.setStatus(1);
				res.setMessage("Cannot submit more than once in a year!");
				return this.successHandler.handlerSuccess(res, start, null, null);
			}
			
			res = taxCommitmentService.uploadTaxCommitment(file, fileInfo);
			
			if (res.getStatus() == 0) {
				TaxRegistration newData = new TaxRegistration();
				newData.setAgentCode(agentCode);
				newData.setDocumentName(taxCommitmentService.getTaxCommitmentFileName());
				newData.setRegistrationTime(new Date());
				
				taxCommitmentService.saveUploadTime(newData);
			}
			return this.successHandler.handlerSuccess(res, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
}