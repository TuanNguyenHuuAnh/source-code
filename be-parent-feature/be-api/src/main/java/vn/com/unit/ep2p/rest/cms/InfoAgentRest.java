package vn.com.unit.ep2p.rest.cms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;

import fr.opensagres.xdocreport.document.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetail;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetailSearchDto;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentTerminationInfor;
import vn.com.unit.cms.core.module.agent.dto.FileInfor;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentDto;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentSearchDto;
import vn.com.unit.cms.core.module.agent.dto.PersonalInfoSubmit;
import vn.com.unit.cms.core.module.agent.dto.ContractDocumentDto;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.AgentContactInfoDto;
import vn.com.unit.ep2p.admin.dto.AgentGroupResponse;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.dto.req.AgentInfoReq;
import vn.com.unit.ep2p.dto.res.CheckCaptchaRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.ReCaptchaService;
import vn.com.unit.ep2p.utils.Aes256;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + "/infoagent")
@Api(tags = { "Infomation Agent" })
public class InfoAgentRest extends AbstractRest {

	@Autowired
	ApiAgentDetailService apiAgentDetailService;
	@Autowired
	@Qualifier("appSystemConfigServiceImpl")
	JcaSystemConfigService jcaSystemConfigService;
	@Autowired
	Db2ApiService db2ApiService;
    @Autowired
    private JcaAccountService jcaAccountService;

    @Autowired
	private ReCaptchaService reCaptchaService;
    
	@GetMapping(AppApiConstant.API_GET_AGENT_LIST)
	@ApiOperation("Api provides a list Info Agent on system")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })

	public DtsApiResponse getInfoAgent(HttpServletRequest request, CmsAgentDetailSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			List<CmsAgentDetail> data = apiAgentDetailService.getListAgentByCondition(searchDto);
			return this.successHandler.handlerSuccess(data, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@PostMapping(AppApiConstant.API_GET_AGENT_DETAIL_BY_USERNAME)
	@ApiOperation("Api provides constant on systems")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getCmsAgentDetailByUsername(HttpServletRequest request, @RequestBody AgentInfoReq agentInfoReq) {
		long start = System.currentTimeMillis();
		try {
			CmsAgentDetail resObj = apiAgentDetailService.getCmsAgentDetailByUsername(agentInfoReq.getUsername());
			if (ObjectUtils.isNotEmpty(resObj) && resObj.getAgentLevel() != null) {
				List<JcaAccount> listAccount = jcaAccountService.getListByUserName(agentInfoReq.getUsername());
		        if (CollectionUtils.isNotEmpty(listAccount)) {
		            JcaAccount jcaAccountDto = listAccount.get(0);
		            resObj.setAvatar(jcaAccountDto.getAvatar());
		        }
			}else {
				resObj = null;
			}

			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	@GetMapping(AppApiConstant.API_GET_AGENT_LOGIN_BY_USERNAME)
	@ApiOperation("Api provides constant on systems")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getCmsAgentLoginByUsername(HttpServletRequest request) {
		long start = System.currentTimeMillis();
		try {
	        String username = UserProfileUtils.getFaceMask();
			CmsAgentDetail resObj = apiAgentDetailService.getCmsAgentLoginByUsername(username);
			if (ObjectUtils.isNotEmpty(resObj) && resObj.getAgentLevel() != null) {
				List<JcaAccount> listAccount = jcaAccountService.getListByUserName(username);
		        if (CollectionUtils.isNotEmpty(listAccount)) {
		            JcaAccount jcaAccountDto = listAccount.get(0);
		            resObj.setAvatar(jcaAccountDto.getAvatar());
		        }
			}else {
				resObj = null;
			}

			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping("/common-pagination")
	@ApiOperation("Api provides constant on systems")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListAgentByCondition(HttpServletRequest request, CmsAgentDetailSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			List<CmsAgentDetail> data = apiAgentDetailService.getListAgentByCondition(searchDto);
			return this.successHandler.handlerSuccess(data, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_CHECK_AGENT_BY_CODE)
	@ApiOperation("Api provides constant on systems")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse checkAgentByCode(String code, String email,
			@RequestParam(value = "", defaultValue = "") String otp,
			@RequestParam(value = "", defaultValue = "") String valueToken) {
		long start = System.currentTimeMillis();
		try {
			String key = code;
        	if (StringUtils.isNotEmpty(email)) {
        		key = email;
        	} else if (StringUtils.isNotEmpty(otp)) {
        		key = otp;
        	}
        	if (!"undefined".equals(key) && !"undefined".equals(valueToken)) {
        		CheckCaptchaRes captchaRes = reCaptchaService.verifyCaptcha(key, valueToken);
    			if (captchaRes != null) {
    				return this.successHandler.handlerSuccess(captchaRes, start, key, null);
    			}
        	}
			CmsAgentTerminationInfor resultData = apiAgentDetailService.checkAgentByCode(code, email, otp);
			return this.successHandler.handlerSuccess(resultData, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_INFO_AGENT_LIST)
	@ApiOperation("Api provides constant on systems")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListInfoAgent(HttpServletRequest request,
			@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
			InfoAgentSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {

			CmsCommonPagination<InfoAgentDto> groupAgent = apiAgentDetailService.getListInfoAgent(searchDto);
			ObjectDataRes<InfoAgentDto> resObj = new ObjectDataRes<>(groupAgent.getTotalData(), groupAgent.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_INFO_AGENT_LIST_BRANCH)
	@ApiOperation("Api info agent list branch ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListInfoBranchAgent(HttpServletRequest request,
			@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
			InfoAgentSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {

			CmsCommonPagination<InfoAgentDto> groupAgent = apiAgentDetailService.getListInfoBranchAgent(searchDto);
			ObjectDataRes<InfoAgentDto> resObj = new ObjectDataRes<>(groupAgent.getTotalData(), groupAgent.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_INFO_AGENT_LIST_DETAIL)
	@ApiOperation("Api provides constant on systems")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getListInfoAgentDetail(HttpServletRequest request,
			@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
			InfoAgentSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {

			InfoAgentDto groupAgent = apiAgentDetailService.getListInfoAgentDetail(searchDto);
			return this.successHandler.handlerSuccess(groupAgent, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping("/contact-and-common-info")
	@ApiOperation("Api provides constant on systems")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getContactAndCommonInfo(HttpServletRequest request,
			@RequestParam(required = false, name = "agentCode")Long agentCode) {
		long start = System.currentTimeMillis();
		try {

			AgentContactInfoDto groupAgent = apiAgentDetailService.getContactAndCommonInfo(agentCode);
			return this.successHandler.handlerSuccess(groupAgent, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@PostMapping(value = AppApiConstant.API_PERSONAL_INFO_SUBMIT)
	public DtsApiResponse updatePersonalInfo(
			@RequestParam(required = false, name = "documentList")List<MultipartFile> documentList,
			@RequestParam(required = false, name = "dto")String dto,
			 HttpServletRequest request) {
    	String agentCode = UserProfileUtils.getFaceMask();
        long start = System.currentTimeMillis();

		String url = jcaSystemConfigService.getValueByKey("PERSONAL_INFO_SUBMITED", ConstantCore.COMP_CUSTOMER_ID) + "/UploadDocument";
		Gson gson = new Gson();
		PersonalInfoSubmit dataSubmit = gson.fromJson(dto, PersonalInfoSubmit.class);
		dataSubmit.setAgentCode(agentCode);
		List<FileInfor> lstString = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(documentList)) {

			for(MultipartFile document: documentList) {
				 try {
					String image = Base64.getEncoder().encodeToString(document.getBytes());
					FileInfor fileI = new FileInfor();
					fileI.setDocumentTitle("ChangeInfo");
					fileI.setMimeType(document.getContentType());
					fileI.setFileSize(Long.toString(document.getSize()));
					fileI.setContent(image);
					fileI.setNumberOfPages("1");
					fileI.setFilePath("");
					/*
					 * String dataDoc ="{documentTitle: "ChangeInfo"," + "\"mimeType\": \"" +
					 * document.getContentType() + "\"," + "\"fileSize\": \"" + document.getSize() +
					 * "\"," + "\"filePath\": \"" + document.getName() + "\"," + "\"content\": \"" +
					 * base64Image + "\"" + "}";
					 */
					lstString.add(fileI);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("Exception ", e);
				}
			}
			dataSubmit.setDocumentList(lstString);
		}
		String jsonDtoSubmit = gson.toJson(dataSubmit);
		JSONObject obj = RetrofitUtils.callApi(url, jsonDtoSubmit);
		return this.successHandler.handlerSuccess(obj, start, null, null);
	}
	
	@PostMapping(value = AppApiConstant.API_CERTIFICATION_CONTRACT_DOCUMENT)
	public DtsApiResponse updateContractDocument(
			@RequestParam(required = false, name = "documentList")List<MultipartFile> documentList, 
			@RequestParam(required = false, name = "dto") String dto,
			 HttpServletRequest request) {
    	String agentCode = UserProfileUtils.getFaceMask();
        long start = System.currentTimeMillis();
    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	try {

    		String url = jcaSystemConfigService.getValueByKey("PERSONAL_INFO_SUBMITED", ConstantCore.COMP_CUSTOMER_ID) + "/UploadDocument";
			Gson gson = new Gson();
			ContractDocumentDto dataSubmit = gson.fromJson(dto, ContractDocumentDto.class);
			dataSubmit.setAgentCode(agentCode);
			dataSubmit.setTaxCodePersonal("");
			List<FileInfor> lstString = new ArrayList<>();
			if(CollectionUtils.isNotEmpty(documentList)) {

				for(MultipartFile document: documentList) {
					 String imageString = CommonBase64Util.removeBase64Header(document.getName());
					 byte[] base64Image = CommonBase64Util.decodeToByte(imageString);
					 try {
						String image = Base64.getEncoder().encodeToString(document.getBytes());
						FileInfor fileI = new FileInfor();
						fileI.setDocumentTitle("ConfirmationLetter");
						fileI.setMimeType(document.getContentType());
						fileI.setFileSize(Long.toString(document.getSize()));
						fileI.setContent(image);
						fileI.setNumberOfPages("1");
						fileI.setFilePath("");
						/*
						 * String dataDoc ="{documentTitle: "ChangeInfo"," + "\"mimeType\": \"" +
						 * document.getContentType() + "\"," + "\"fileSize\": \"" + document.getSize() +
						 * "\"," + "\"filePath\": \"" + document.getName() + "\"," + "\"content\": \"" +
						 * base64Image + "\"" + "}";
						 */
						lstString.add(fileI);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.error("Exception ", e);
					}
				
					
				}
				dataSubmit.setDocumentList(lstString);
			}
			String jsonDtoSubmit = ow.writeValueAsString(dataSubmit);
			JSONObject obj = RetrofitUtils.callApi(url, jsonDtoSubmit);
			return this.successHandler.handlerSuccess(obj, start, null, null);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			System.out.print("PERSONAL_INFO_SUBMITED"+ e);
			return this.errorHandler.handlerException(e, start, null, null);
		}
	}
	
	private Logger logger = LoggerFactory.getLogger(getClass());
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_LIST_INFO_AGENT)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    
    public ResponseEntity exportListInfoAgent(HttpServletRequest request
            , HttpServletResponse response
            , @RequestBody InfoAgentSearchDto searchDto) {
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        //searchDto.setUsername(UserProfileUtils.getFaceMask());
        searchDto.setLanguage(locale.getLanguage());
        ResponseEntity resObj = null;
        try {
            resObj = apiAgentDetailService.exportListInfoAgent(searchDto, response, locale);
        } catch (Exception e) {
            logger.error("##exportListInfoAgent##", e);
        }
        return resObj;
    }

	@PostMapping(AppApiConstant.API_GET_AGENT_DETAIL_BY_FACE_MASK)
	@ApiOperation("Api provides constant on systems")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public DtsApiResponse getCmsAgentDetailByFaceMask(HttpServletRequest request, @RequestBody AgentInfoReq agentInfoReq) {
		long start = System.currentTimeMillis();
		try {
			List<GrantedAuthority> lstAuthor = (List<GrantedAuthority>) SecurityContextHolder.getContext()
					.getAuthentication().getAuthorities();
			Optional<GrantedAuthority> item = lstAuthor.stream()
					.filter(e -> StringUtils.equalsIgnoreCase(e.getAuthority(), "SCREEN#FONTEND#FACE_MASK"))
					.findFirst();
			if (!item.isPresent()) {
				throw new Exception("Bạn không có quyền truy cập");
			}
			CmsAgentDetail resObj = apiAgentDetailService.getCmsAgentDetailByFaceMask(agentInfoReq.getUsername());
			if (!UserProfileUtils.getChannel().contains(resObj.getChannel())) {
				throw new Exception("Bạn không cùng kênh với mặt nạ");
			}
			return this.successHandler.handlerSuccess(resObj, start, null, null);

		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}


	@GetMapping("/get-group-agent-document")
	@ApiOperation("Get group agent by agent code")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })
    public DtsApiResponse getGroupAgentDocument(HttpServletRequest request, 
       		@RequestParam(required = false, name = "deviceId")String deviceId,
       		@RequestParam(required = false, name = "clientId")String clientId,
    		@RequestParam(required = true, name = "proposalNo")String proposalNo,
    		@RequestParam(required = true, name = "policyKey")String policyKey) {
        long start = System.currentTimeMillis();
        try {
        	String sdkPolicySecrectKey = jcaSystemConfigService.getValueByKey("SDK_POLICY_SECRECT_KEY", ConstantCore.COMP_CUSTOMER_ID);
        	if(StringUtils.isEmpty(sdkPolicySecrectKey)) {
        		throw new Exception("Giá trị [SDK_POLICY_SECRECT_KEY] không được thiết lập.");
        	}
        	String response = "";
        	AgentGroupResponse agentGroupRes = new AgentGroupResponse();
        	String agentCode = UserProfileUtils.getFaceMask();
            List<AgentInfoDb2> data = db2ApiService.getGroupAgentDocument(UserProfileUtils.getFaceMask(), proposalNo, policyKey);
            if (CollectionUtils.isNotEmpty(data) && StringUtils.isNotEmpty(data.get(0).getAgentGroup())) {
                agentGroupRes.sourceSystem = "D-Success";
                if ("AD".equals(UserProfileUtils.getChannel())) {
                	agentGroupRes.sourceSystem = "AD Portal";
                }
                
                agentGroupRes.deviceId = deviceId;
                if(StringUtils.isEmpty(deviceId)) { 
                	agentGroupRes.deviceId = "";
                }
                agentGroupRes.clientId = clientId;
                if(StringUtils.isEmpty(clientId)) {
                	agentGroupRes.clientId = "";
                }
                agentGroupRes.policyNo = policyKey;
                agentGroupRes.proposalNo = proposalNo.trim();
                agentGroupRes.group = data.get(0).getAgentGroup();
            
            	Gson gson = new Gson();
            	String jsonString = gson.toJson(agentGroupRes);
            	response = Aes256.encrypt(jsonString, sdkPolicySecrectKey);
            }
        	return this.successHandler.handlerSuccess(response, start, null, agentCode);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
	@PostMapping("/dai-ichi-on")
	@ApiOperation("Get URL of Dai-Ichi-On")
	public DtsApiResponse getDaiIchiOnUrl(){
		long start = System.currentTimeMillis();
		try {
			String url = RetrofitUtils.getDaiIchiOnUrl(UserProfileUtils.getFaceMask(), UserProfileUtils.getFullName());
			return this.successHandler.handlerSuccess(url, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

}
