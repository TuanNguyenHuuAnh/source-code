package vn.com.unit.ep2p.rest.cms;

import java.io.File;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import fr.opensagres.xdocreport.document.json.JSONArray;
import fr.opensagres.xdocreport.document.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.agent.dto.CertificateSearchDto;
import vn.com.unit.cms.core.module.agent.dto.ContractDocumentDto;
import vn.com.unit.cms.core.module.agent.dto.FileDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.CertificationContractDocumentService;
import vn.com.unit.ep2p.service.TaxCommitmentService;
import vn.com.unit.imp.excel.constant.CommonConstant;

/**
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + "/contractinfo")
@Api(tags = { "Infomation Agent" })
public class CertificationContractDocumentRest extends AbstractRest {

	@Autowired
	ApiAgentDetailService apiAgentDetailService;
	
	@Autowired
	@Qualifier("appSystemConfigServiceImpl")
	JcaSystemConfigService jcaSystemConfigService;
	
	@Autowired
	CertificationContractDocumentService certificationContractDocumentService;
	
	@Autowired
	TaxCommitmentService taxCommitmentService;
	
	@Autowired
	ServletContext servletContext;

    private Logger logger = LoggerFactory.getLogger(getClass());
    
	@GetMapping(value = AppApiConstant.API_LIST_CONFIRM_DOCUMENT)
	@ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public DtsApiResponse getConfirmDocument(HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
			List<String> docIds = new ArrayList<>();
        	docIds.add("DOC_24");
        	List<ContractDocumentDto> listDocName = certificationContractDocumentService.getListTermsAndConditions(docIds);
        	String fileName = "";
        	if (listDocName != null && listDocName.size() > 0) {
        		fileName = listDocName.get(0).getDocName();
        	}
        	
        	Integer count = 1;
	    	String agentCode = UserProfileUtils.getFaceMask();
	    	FileDto fileDto = null;
	    	Gson gson = new Gson();
	    	List<FileDto> listData = new ArrayList<>();
    		String url = jcaSystemConfigService.getValueByKey("PERSONAL_INFO_SUBMITED", ConstantCore.COMP_CUSTOMER_ID) + "/GetAADocument";
    		
    		ContractDocumentDto doc = new ContractDocumentDto();
    		doc.setSourceSystem("D-Success");
    		doc.setAgentCode(agentCode);
    		
    		List<String> listDocumentName_confirmLetter = new ArrayList<>();
    		listDocumentName_confirmLetter.add("ConfirmationLetter");
    		doc.setListDocumentName(listDocumentName_confirmLetter);
    		String jsonDtoSubmit_confirmLetter = gson.toJson(doc);
    		
    		List<String> listDocumentName_taxForm = new ArrayList<>();
    		listDocumentName_taxForm.add("AgentTaxConfirmation");
    		doc.setListDocumentName(listDocumentName_taxForm);
    		String jsonDtoSubmit_taxForm = gson.toJson(doc);
    		
//			logger.error("PERSONAL_INFO_SUBMITED: "+ url);
//			logger.error("jsonDtoSubmit: "+ jsonDtoSubmit_confirmLetter);
//			logger.error("jsonDtoSubmit: "+ jsonDtoSubmit_taxForm);
			
			JSONObject obj_confirmLetter = RetrofitUtils.callApi(url, jsonDtoSubmit_confirmLetter);
			JSONObject obj_taxForm = RetrofitUtils.callApi(url, jsonDtoSubmit_taxForm);
			
//			logger.error("obj File base64: "+ jsonDtoSubmit_confirmLetter);
//			logger.error("obj File base64: "+ jsonDtoSubmit_taxForm);
			
			// iterate through confirm letters
			JSONArray jarray_confirmLetter = obj_confirmLetter.getJSONArray("DocumentList");
			for(Object objFile : jarray_confirmLetter) {
				fileDto = new FileDto();
//				logger.error("##RetrieveDocument##", objFile);
				
				JSONObject ojbDoc = (JSONObject) objFile;
				
//				logger.error("", ojbDoc.get("content"));//modele attribute
//				logger.error("##person.get(\"content\")##", ojbDoc.get("content"));
				
				fileDto.setBaseString(ojbDoc.get("content").toString());
				fileDto.setFileName(fileName);
				fileDto.setFileType(ojbDoc.get("fileExtension").toString());
				fileDto.setEffectiveDate(ojbDoc.get("updatedDate")==null ? ojbDoc.get("updatedDate").toString() :ojbDoc.get("createdDate").toString());
				fileDto.setNo((count++).toString());
				listData.add(fileDto);
			}
			
			// iterate through tax docs
//			docIds = new ArrayList<>();
//        	docIds.add("DOC_31");
//        	listDocName = certificationContractDocumentService.getListTermsAndConditions(docIds);
//        	if (listDocName != null && listDocName.size() > 0) {
//        		fileName = listDocName.get(0).getDocName();
//        	}
			
			fileName = "Văn bản cam kết thuế";
			JSONArray jarray_taxForm = obj_taxForm.getJSONArray("DocumentList");
			for(Object objFile : jarray_taxForm) {
				fileDto = new FileDto();
				
				JSONObject ojbDoc = (JSONObject) objFile;
				
				fileDto.setBaseString(ojbDoc.get("content").toString());
				fileDto.setFileName(fileName);
				fileDto.setFileType(ojbDoc.get("fileExtension").toString());
				fileDto.setEffectiveDate(ojbDoc.get("updatedDate")==null ? ojbDoc.get("updatedDate").toString() :ojbDoc.get("createdDate").toString());
				fileDto.setNo((count++).toString());
				listData.add(fileDto);
			}
			
			ObjectDataRes<FileDto> resObj = new ObjectDataRes<>(listData.size(), listData);
			return this.successHandler.handlerSuccess(resObj, start, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping(AppApiConstant.API_LIST_TERMS_CONDITIONS)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListTermsAndConditions(HttpServletRequest request,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , CertificateSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            List<String> docIds = new ArrayList<>();
        	docIds.add("DOC_25");
        	docIds.add("DOC_26");
            List<ContractDocumentDto> listData = certificationContractDocumentService.getListTermsAndConditions(docIds);
        	ObjectDataRes<ContractDocumentDto> resObj = new ObjectDataRes<>(listData.size(), listData);
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@PostMapping("upload-letter")
	public DtsApiResponse getTACByApiRetrieveDocument(@RequestBody FileDto fileType) {
		long start = System.currentTimeMillis();
		try {
	    	String fileName = fileType.getFileName();
	    	if (fileName.contains("../")) {
	    		return this.errorHandler.handlerException(new Exception("Tên file không hợp lệ."), start, null, null);
	    	}
	    	FileDto fileDto = new FileDto();

	    	String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_PDF) + File.separator;
			templatePath = Paths.get(templatePath, fileName).toString();
		    File fi = new File(templatePath);
		    byte[] bytes = Files.readAllBytes(fi.toPath());
		    String fileStream = Base64.getEncoder().encodeToString(bytes);
		    
			fileDto.setBaseString(fileStream);
			fileDto.setFileName(fileName);
			fileDto.setFileType("ContractDocuments");
			return this.successHandler.handlerSuccess(fileDto, start, fileDto.getFileName());
		} catch (FileSystemException ex) {
			return this.errorHandler.handlerException(new Exception("Tên file không hợp lệ."), start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@PostMapping("get-file-by-document")
	public DtsApiResponse uploadImage(@RequestBody FileDto file) {
		long start = System.currentTimeMillis();
		try {
			return this.successHandler.handlerSuccess(certificationContractDocumentService.getFileByBase64(file), start, file.getFileName());
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
}
