package vn.com.unit.ep2p.rest.cms;

import java.util.Base64;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
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
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.agent.dto.CertificateAnotherSearchDto;
import vn.com.unit.cms.core.module.agent.dto.CertificateDto;
import vn.com.unit.cms.core.module.agent.dto.CertificateSearchDto;
import vn.com.unit.cms.core.module.agent.dto.FileDto;
import vn.com.unit.cms.core.module.agent.dto.IntroduceLetterDto;
import vn.com.unit.cms.core.module.agent.dto.IntroduceSearchDto;
import vn.com.unit.cms.core.module.agent.dto.LetterAgentDto;
import vn.com.unit.cms.core.module.agent.dto.LetterAgentSearchDto;
import vn.com.unit.cms.core.module.agent.dto.RetrieveDocument;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.CertificateAndLetterService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + "/certificate-letter")
@Api(tags = { "certificate and letter Agent" })
public class CertificateAndLetterRest extends AbstractRest{

	@Autowired
	CertificateAndLetterService certificateAndLetterService;
	@Autowired
	@Qualifier("appSystemConfigServiceImpl")
	JcaSystemConfigService jcaSystemConfigService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	 
	@GetMapping(AppApiConstant.API_CERTIFICATE)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListCertificateByCondition(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , CertificateSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {

            Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setAgentCode(agentCode);
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	searchDto.setLanguage(locale.getLanguage());
        	ObjectDataRes<CertificateDto> resObj = certificateAndLetterService.getListCertificateByCondition(searchDto);
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_LETTER_AGENT)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListLetterAgentByCondition(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , LetterAgentSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setAgentCode(agentCode);
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	searchDto.setLanguage(locale.getLanguage());
        	CmsCommonPagination<LetterAgentDto> common = certificateAndLetterService.getListLetterAgentByCondition(searchDto);
        	ObjectDataRes<LetterAgentDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

	@GetMapping(AppApiConstant.API_INTRODUCE_LETTER)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListIntroduceLetterByCondition(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , IntroduceSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setAgentCode(agentCode);
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	searchDto.setLanguage(locale.getLanguage());
        	CmsCommonPagination<IntroduceLetterDto> common = certificateAndLetterService.getListIntroduceLetterByCondition(searchDto);
        	ObjectDataRes<IntroduceLetterDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }//

	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_LETTER_AGENT)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportListLetterAgentByCondition(HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer modeView
			, HttpServletResponse response
			, LetterAgentSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
    	String agentCode = UserProfileUtils.getFaceMask();
    	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
    		agentCode = UserProfileUtils.getFaceMask();
    	}
    	searchDto.setAgentCode(agentCode);
    	searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
			resObj = certificateAndLetterService.exportExcelLetterAgent(searchDto, response, locale);
		} catch (Exception e) {
			logger.error("##exportLis##", e.getMessage());
		}
		return resObj;
	}
	@PostMapping("get-file-by-document")
	public DtsApiResponse uploadImage(@RequestBody FileDto file) {
		long start = System.currentTimeMillis();
		try {
//	    	String agentCode = UserProfileUtils.getFaceMask();
//    		String url = jcaSystemConfigService.getValueByKey("PERSONAL_INFO_SUBMITED", ConstantCore.COMP_CUSTOMER_ID) + "/RetrieveDocument";
//    		RetrieveDocument doc = new RetrieveDocument();
//    		doc.setSourceSystem("D-Success");
//    		doc.setAgentCode(agentCode);
//    		doc.setTemplateType(file.getFileType());
//    		Gson gson = new Gson();
//			String jsonDtoSubmit = gson.toJson(doc);
//			System.out.print("PERSONAL_INFO_SUBMITED: "+ url);
//			System.out.print("jsonDtoSubmit: "+ jsonDtoSubmit);
//			JSONObject obj = RetrofitUtils.callApi(url, jsonDtoSubmit);
//			System.out.print("obj File base64: "+ jsonDtoSubmit);
//			JSONArray jarray = obj.getJSONArray("DocumentList");
//			for(Object objFile : jarray) {
//				logger.error("##RetrieveDocument##", objFile);
//				JSONObject person = (JSONObject) objFile;
//                System.out.println(person.get("fileName"));//modele object
//				logger.error("##person.get(\"fileName\")##", person.get("fileName"));
//                System.out.println(person.get("content"));//modele attribute
//				logger.error("##person.get(\"content\")##", person.get("content"));
//				byte[] content =  SerializationUtils.serialize(person.get("content"));
//				file.setBase64(content);
//				file.setFileName(person.get("fileName").toString());
//				
//			}
			return this.successHandler.handlerSuccess(certificateAndLetterService.getFileByBase64(file), start, file.getFileName());
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	@PostMapping("get-text-html-by-document")
	public DtsApiResponse uploadBaseConvertText(@RequestBody FileDto file) {
		long start = System.currentTimeMillis();
		try {
			 Base64.Decoder decoder = Base64.getUrlDecoder();  
		        // Decoding URl  
		     String dStr = new String(decoder.decode(file.getBase64()));  
			return this.successHandler.handlerSuccess(dStr, start, file.getFileName());
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	@PostMapping("upload-file")
	public DtsApiResponse getFileByApiRetrieveDocument(@RequestBody FileDto fileType) {
		long start = System.currentTimeMillis();
		try {
	    	String agentCode = UserProfileUtils.getFaceMask();
	    	FileDto fileDto = new FileDto();
    		String url = jcaSystemConfigService.getValueByKey("PERSONAL_INFO_SUBMITED", ConstantCore.COMP_CUSTOMER_ID) + "/RetrieveDocument";
    		RetrieveDocument doc = new RetrieveDocument();
    		doc.setSourceSystem("D-Success");
    		doc.setAgentCode(agentCode);
    		if (StringUtils.isNotEmpty(fileType.getAgentCode())) {
        		doc.setAgentCode(fileType.getAgentCode());
    		}
    		doc.setTemplateType(fileType.getFileType());
    		doc.setCatagory(fileType.getCategory());
    		doc.setEffectiveDate(fileType.getEffectiveDate());
    		Gson gson = new Gson();
			String jsonDtoSubmit = gson.toJson(doc);
			logger.error("PERSONAL_INFO_SUBMITED: "+ url);
			logger.error("jsonDtoSubmit: "+ jsonDtoSubmit);
			JSONObject obj = RetrofitUtils.callApi(url, jsonDtoSubmit);
			logger.error("obj File base64: "+ jsonDtoSubmit);
			JSONArray jarray = obj.getJSONArray("DocumentList");
			for(Object objFile : jarray) {
				logger.error("##RetrieveDocument##", objFile);
				JSONObject person = (JSONObject) objFile;
				logger.error("", person.get("fileName"));//modele object
				logger.error("##person.get(\"fileName\")##", person.get("fileName"));
				logger.error("", person.get("content"));//modele attribute
				logger.error("##person.get(\"content\")##", person.get("content"));
				fileDto.setBaseString(person.get("content").toString());
				fileDto.setFileName(person.get("fileName").toString());
				fileDto.setFileType(fileType.getFileType());
				
			}
			return this.successHandler.handlerSuccess(fileDto, start, fileDto.getFileName());
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_INTRODUCE_LETTER)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportListIntroduceLetterByCondition(HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer modeView
			, HttpServletResponse response
			, IntroduceSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setAgentCode(agentCode);
        	searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
			resObj = certificateAndLetterService.exportExcelIntroduceLetter(searchDto, response, locale);
		} catch (Exception e) {
			logger.error("##exportLis##", e.getMessage());
		}
		return resObj;
	}
	@GetMapping(AppApiConstant.API_CERTIFICATE_ANOTHER)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListCertificateAnotherByCondition(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , CertificateAnotherSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setAgentCode(agentCode);
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	searchDto.setLanguage(locale.getLanguage());
        	ObjectDataRes<CertificateDto> resObj = certificateAndLetterService.getListCertificateAnotherByCondition(searchDto);
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_CERTIFICATE)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportListCertificateByCondition(HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer modeView
			, HttpServletResponse response
			, CertificateSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
    	String agentCode = UserProfileUtils.getFaceMask();
    	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
    		agentCode = UserProfileUtils.getFaceMask();
    	}
    	searchDto.setAgentCode(agentCode);
    	searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
			resObj = certificateAndLetterService.exportListCertificateByCondition(searchDto, response, locale);
		} catch (Exception e) {
			logger.error("##exportLis##", e.getMessage());
		}
		return resObj;
	}
	
	@PostMapping("upload-letter")
	public DtsApiResponse getLetterByApiRetrieveDocument(@RequestBody FileDto fileType) {
		long start = System.currentTimeMillis();
		try {
	    	String agentCode = UserProfileUtils.getFaceMask();
	    	FileDto fileDto = new FileDto();
    		String url = jcaSystemConfigService.getValueByKey("PERSONAL_INFO_SUBMITED", ConstantCore.COMP_CUSTOMER_ID) + "/AgentMovementLetter";
    		RetrieveDocument doc = new RetrieveDocument();
    		doc.setSourceSystem("D-Success");
    		doc.setAgentCode(agentCode);
    		doc.setLetterType(fileType.getFileType());
    		doc.setCatagory(fileType.getCategory());
    		doc.setAgentMovementId(fileType.getAgentMovementId());
    		doc.setAssignDate(fileType.getAssignDate());
    		doc.setPolicyNo(fileType.getPolicyNo());
    		Gson gson = new Gson();
			String jsonDtoSubmit = gson.toJson(doc);
			System.out.print("PERSONAL_INFO_SUBMITED: "+ url);
			System.out.print("jsonDtoSubmit: "+ jsonDtoSubmit);
			JSONObject obj = RetrofitUtils.callApi(url, jsonDtoSubmit);
//			
			fileDto.setBaseString(obj.get("content").toString());
			fileDto.setFileName(obj.get("fileName").toString());
			fileDto.setFileType(fileType.getFileType());
			fileDto.setResponseMessage(obj.get("responseMessage").toString());
			fileDto.setResponseCode(obj.get("responseCode").toString());
//			JSONArray jarray = obj.getJSONArray("DocumentList");
//			for(Object objFile : jarray) {
//				logger.error("##RetrieveDocument##", objFile);
//				JSONObject person = (JSONObject) objFile;
//                System.out.println(person.get("fileName"));//modele object
//				logger.error("##person.get(\"fileName\")##", person.get("fileName"));
//                System.out.println(person.get("content"));//modele attribute
//				logger.error("##person.get(\"content\")##", person.get("content"));
//				fileDto.setBaseString(person.get("content").toString());
//				fileDto.setFileName(person.get("fileName").toString());
//				fileDto.setFileType(fileType.getFileType());
//				
//			}
			return this.successHandler.handlerSuccess(fileDto, start, fileDto.getFileName());
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	@GetMapping(AppApiConstant.API_LETTER_AGENT_TER)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListLetterAgentTer(HttpServletRequest request
            , LetterAgentSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	if(StringUtils.isEmpty(searchDto.getAgentCode())) {
            	searchDto.setAgentCode(agentCode);
        	}
        	CmsCommonPagination<LetterAgentDto> common = certificateAndLetterService.getListLetterAgentTer(searchDto);
        	ObjectDataRes<LetterAgentDto> resObj = new ObjectDataRes<>(0, common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}

