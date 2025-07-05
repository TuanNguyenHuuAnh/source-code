package vn.com.unit.ep2p.service.impl;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.threeten.bp.Year;

import fr.opensagres.xdocreport.document.json.JSONObject;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import vn.com.unit.cms.core.module.agent.dto.AgentInfoTaxCommitmentExportDto;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetail;
import vn.com.unit.cms.core.module.agent.dto.ContractDocumentDto;
import vn.com.unit.cms.core.module.agent.dto.FileInfor;
import vn.com.unit.cms.core.module.income.entity.TaxRegistration;
import vn.com.unit.cms.core.module.income.repository.CommitmentRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.AgentAllowExportTaxCommitmentDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.dto.req.CommitmentInfoFileReq;
import vn.com.unit.ep2p.dto.res.UploadTaxCommitmentDocRes;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.CertificationContractDocumentService;
import vn.com.unit.ep2p.service.TaxCommitmentService;
import vn.com.unit.ep2p.utils.OcrUtil;
import vn.com.unit.imp.excel.constant.CommonConstant;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class TaxCommitmentServiceImpl extends AbstractCommonService implements TaxCommitmentService {
	
	@Autowired
	private Db2ApiService db2ApiService;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private OcrUtil<String> ocrUtil;
	
	@Autowired
	private CommitmentRepository commitmentRepository;
	
	@Autowired
	CertificationContractDocumentService certificationContractDocumentService;
	
	@Autowired
	private ApiAgentDetailService apiAgentDetailService;

	
	@Autowired
	@Qualifier("appSystemConfigServiceImpl")
	JcaSystemConfigService jcaSystemConfigService;
	
	private final Double CONF_THRESHOLD = 0.5;
	
	private final int MAX_CHECK_GET_OCR = 15;
	
	private final int WAITING_TIME_EACH = 1000; // in milliseconds
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public AgentAllowExportTaxCommitmentDto getAgentInfoTaxCommitment(String agentCode) {
		return db2ApiService.getAgentInfoTaxCommitment(agentCode);
	}
	
	@Override
	public ResponseEntity<byte[]> getExportTemplate(AgentInfoTaxCommitmentExportDto data) throws Exception {
		String templatePath = Paths.get(
			servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_PDF),
			"Mau-08-CK-TNCN_DLVN.jrxml"
		).toString();
		
		FileInputStream inputStream = new FileInputStream(templatePath);
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		
		// configure parameters
		Map<String, Object> params = new HashMap<>();
		params.put("agentName", data.getAgentName());
		params.put("fullAddress", data.getFullAddress());
		params.put("taxCode", data.getTaxCode());
		params.put("date", data.getDate());
		params.put("identificationNumber", data.getIdentificationNumber());
		params.put("agentCode", data.getAgentCode());
		params.put("year", Integer.toString(Year.now().getValue()));
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
		
		byte[] result = JasperExportManager.exportReportToPdf(jasperPrint);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CommonConstant.CONTENT_DISPOSITION, "inline;filename=Mau-08-CK-TNCN_DLVN.pdf");
		
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(result);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean checkUploadStatus(String agentCode, String year) {
		// true if already have valid document uploaded
		String res = commitmentRepository.getDocumentStatus(agentCode, year);
		if (!StringUtils.isEmpty(res)) {
			return !StringUtils.isEmpty(res.trim());
		}
		return false;
	}
	
	@Override
	public boolean checkDocumentStatus(String agentCode) {
		// true if document status is not rejected, or not invalid
		 return db2ApiService.checkDocumentStatus(agentCode);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUploadTime(TaxRegistration data) {
		commitmentRepository.save(data);
	}
	
	@Override
	public String getTaxCommitmentFileName() {
		List<String> docIds = new ArrayList<>();
    	docIds.add("DOC_31");
    	List<ContractDocumentDto> listDocName = certificationContractDocumentService.getListTermsAndConditions(docIds);
    	String fileName = "Văn bản cam kết thuế";
    	if (listDocName != null && listDocName.size() > 0) {
    		fileName = listDocName.get(0).getDocName();
    	}
    	
    	return fileName;
	}
	
	@Override
	public UploadTaxCommitmentDocRes uploadTaxCommitment(
		MultipartFile file,
		CommitmentInfoFileReq fileInfo
	)
	{
		try {
			String agentCode = UserProfileUtils.getFaceMask();
			
			CmsAgentDetail agentDetail = apiAgentDetailService.getCmsAgentDetailByUsername(agentCode);
			
			String taxCode = agentDetail.getIdPersonalIncomeTax();
			String url = jcaSystemConfigService.getValueByKey("PERSONAL_INFO_SUBMITED", ConstantCore.COMP_CUSTOMER_ID) + "/UploadDocument" ;
			String encodedFile = Base64.getEncoder().encodeToString(file.getBytes());
			String ocrUrl = jcaSystemConfigService.getValueByKey("OCR_API_URL", ConstantCore.COMP_CUSTOMER_ID);
            String isArchiveOnly = jcaSystemConfigService.getValueByKey("IS_ARCHIVE_ONLY", ConstantCore.COMP_CUSTOMER_ID);
            String checkOCR = jcaSystemConfigService.getValueByKey("CHECK_OCR", ConstantCore.COMP_CUSTOMER_ID);
			// upload tax document to ocr system for validity checking
		    if ( ocrUrl != null && !("").equals(ocrUrl) ) {
			MultiValueMap<String, Object> multiMap = new LinkedMultiValueMap<>();
			multiMap.put("base64_img", Arrays.asList(encodedFile));
			
			JSONObject resultPost = ocrUtil.callApi(
				"ocr_post_base64", null, HttpMethod.POST, multiMap,
				MediaType.APPLICATION_FORM_URLENCODED, String.class
			);
			
			// get the result
			Map<Object, Object> params = new HashMap<>();
			params.put("qid", resultPost.get("QID").toString());
			
			JSONObject resultGet = null;
			for (int i = 0; i < this.MAX_CHECK_GET_OCR; i++) {
				if (
					resultGet != null &&
					resultGet.getInt("errorCode") != 9 &&
					!resultGet.getString("errorMessage").contains("Your request is being processed.")
				) {
					break;
				}
				
				resultGet = ocrUtil.callApi(
					"ocr_get_base64", params, HttpMethod.GET, null,
					MediaType.APPLICATION_FORM_URLENCODED, String.class
				);
				Thread.sleep(this.WAITING_TIME_EACH);
			}
		
			// get and process the data
			JSONObject data = resultGet.getJSONObject("data");
			if ( ("1").equals(checkOCR)) {
			if (
				!resultGet.containsKey("errorCode") || resultGet.getInt("errorCode") != 0 ||
				!resultGet.containsKey("errorMessage") || !resultGet.getString("errorMessage").equals("Success") ||
				!data.containsKey("idType") || !data.getString("idType").equals("IC") ||
				!data.containsKey("isSigned") || !data.getBoolean("isSigned") || data.getDouble("isSignedConf") < this.CONF_THRESHOLD ||
				!data.containsKey("address") || data.getJSONObject("address") == null || data.getDouble("addressConf") < this.CONF_THRESHOLD ||
				!data.containsKey("taxCode") || StringUtils.isEmpty(data.getString("taxCode").trim()) || data.getDouble("taxCodeConf") < this.CONF_THRESHOLD ||
				!data.containsKey("fullName") || StringUtils.isEmpty(data.getString("fullName").trim()) || data.getDouble("fullNameConf") < this.CONF_THRESHOLD ||
				!data.containsKey("idNum") || StringUtils.isEmpty(data.getString("idNum").trim()) || data.getDouble("idNumConf") < this.CONF_THRESHOLD ||
				!data.containsKey("agentCode") || StringUtils.isEmpty(data.getString("agentCode").trim()) || data.getDouble("agentCodeConf") < this.CONF_THRESHOLD
			) {
				return new UploadTaxCommitmentDocRes() {{
					setStatus(2);
					setMessage("Image is not clear or not enough information!");
				}};
			}
			
			if (
				!data.getString("taxCode").equals(agentDetail.getTaxCode()) ||
				!data.getString("fullName").equals(agentDetail.getFullName()) ||
				!data.getString("idNum").equals(agentDetail.getIdNumber()) ||
				!data.getString("agentCode").equals(agentCode)
			) {
				return new UploadTaxCommitmentDocRes() {{
					setStatus(4);
					setMessage("Wrong information or image is not clear!");
				}};
			}
			}
		    }
			// after being checked by OCR, we send the document to IBPS
			ContractDocumentDto doc = new ContractDocumentDto();
			doc.setSourceSystem("D-Success");
			doc.setAgentCode(agentCode);
			doc.setIsArchiveOnly(isArchiveOnly);
			doc.setTaxCodePersonal(taxCode);
			doc.setAction("UploadTaxConfirmation");
			
			FileInfor fileInfor = new FileInfor();
			fileInfor.setDocumentTitle("AgentTaxConfirmation");
			fileInfor.setMimeType(fileInfo.getMimeType());
			fileInfor.setFileSize(Long.toString(file.getSize()));
			fileInfor.setContent(encodedFile);
			fileInfor.setNumberOfPages(fileInfo.getNumberOfPages());
			fileInfor.setFilePath("");
			List<FileInfor> listDocumentInfo = Arrays.asList(fileInfor);
			doc.setDocumentList(listDocumentInfo);
			
			Gson gson = new Gson();
			String json = gson.toJson(doc);
			
			JSONObject res = RetrofitUtils.callApi(url, json);
			if (
				res.get("responseMessage").toString().equals("Successful") ||
				res.get("responseCode").toString().equals("D000")
			) {
				return new UploadTaxCommitmentDocRes() {{
					setStatus(0);
					setMessage("Success");
				}};
			}
			
			return new UploadTaxCommitmentDocRes() {{
				setStatus(3);
				setMessage("Cannot submit document! Please try again later.");
			}};
		}
		catch (Exception e) {
			logger.error("OCR Response Error: " + e.toString());
			return new UploadTaxCommitmentDocRes() {{
				setStatus(-1);
				setMessage("Internal server error! Please try again later.");
				setErrorStr(e.toString());
			}};
		}
	}
}