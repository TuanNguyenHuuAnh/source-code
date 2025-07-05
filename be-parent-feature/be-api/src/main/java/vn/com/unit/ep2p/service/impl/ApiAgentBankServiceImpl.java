package vn.com.unit.ep2p.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

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

import fr.opensagres.xdocreport.document.json.JSONObject;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import vn.com.unit.cms.core.module.agentbank.entity.AgentBankUpdateHistory;
import vn.com.unit.cms.core.module.agentbank.repository.AgentBankUpdateHistoryRepository;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.common.utils.CommonNullAwareBeanUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.constant.ConstantCore;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.enumdef.SendEmailTypeEnum;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.AgentBankDto;
import vn.com.unit.ep2p.admin.dto.BankInfoUpdateHistoryDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.service.ApiAgentBankService;
import vn.com.unit.ep2p.service.OtpService;
import vn.com.unit.ep2p.utils.OcrUtil;
import vn.com.unit.imp.excel.constant.CommonConstant;
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiAgentBankServiceImpl implements ApiAgentBankService {
	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	@Autowired
	Db2ApiService db2ApiService;

	@Autowired
	private OtpService otpService;
	
	@Autowired
	private ServletContext servletContext;

	@Autowired
    private JcaEmailTemplateService jcaEmailTemplateService;
    
    @Autowired
    private JcaEmailService jcaEmailService;

    @Autowired
	@Qualifier("appSystemConfigServiceImpl")
	JcaSystemConfigService jcaSystemConfigService;
    
    @Autowired
    private SystemConfig systemConfig;

    @Autowired
	private OcrUtil<String> ocrUtil;
    
    @Autowired
    private AgentBankUpdateHistoryRepository agentBankUpdateHistoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(ApiAgentBankServiceImpl.class);
    private static final String TEMPLATE_OTP_UPDATE_BANK_INFO = "TEMPLATE_OTP_UPDATE_BANK_INFO";
    private static final String TEMPLATE_INFO_UPDATE_BANK_INFO = "TEMPLATE_INFO_UPDATE_BANK_INFO";

	@Override
    public AgentBankDto getAgentBankInfo(String agentCode) throws DetailException {
		AgentBankDto entity = db2ApiService.getAgentBankInfo(agentCode);        
        return entity;
    }
	
	@Override
	public List<SelectItem> getListBanks() {
		List<SelectItem> listBank = db2ApiService.getListBanks();
		return listBank;
	}
	
	@Override
	public List<SelectItem> getListBankBranchs(String bankCode) {
		List<SelectItem> listBank = db2ApiService.getListBankBranchs(bankCode);
		return listBank;
	}
	
	@Override
	public boolean sendOtp(String agentCode) throws Exception {
		try {
			String otp = otpService.generateOTP(agentCode);
			Db2AgentDto agentInfo = db2ApiService.getAgentClientDetail(agentCode);
			if (agentInfo != null) {
				String email = agentInfo.getEmailAddress2();
				if (StringUtils.isEmpty(email)) {
					return false;
				}
				Map<String, Object > paramEmail = new HashMap<String, Object>();
				List<String> toAddress = new ArrayList<>();
				
	            paramEmail.put("otp", String.valueOf(otp));
			
	            toAddress.add(email);
	            
	            JcaEmailTemplateDto templateDto = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(TEMPLATE_OTP_UPDATE_BANK_INFO);
				if (templateDto != null) {
					String content = jcaEmailService.replaceParam(templateDto.getTemplateContent(), paramEmail);
					String subject = templateDto.getTemplateSubject();
					//setting content for email
					JcaEmailDto jcaEmailDto = jcaEmailService.convertValue(templateDto);
					jcaEmailDto.setToString("");
					jcaEmailDto.setToAddress(toAddress);
					jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
					jcaEmailDto.setEmailContent(content);
					jcaEmailDto.setSubject(subject);
					JcaEmailDto dto = new JcaEmailDto();
					CommonNullAwareBeanUtil.copyPropertiesWONull(jcaEmailDto, dto);
					return jcaEmailService.sendEmail(dto).isStatus();
				}
			}
			return false;
		}
        catch (Exception e) {
        	logger.error("Send mail: ", e);
        	return false;
		}
	}
	
	@Override
	public String getOcrInfo(List<MultipartFile> documentList) throws Exception {
		String ret = null;
		JSONObject data = null;
		String ocrUrl = jcaSystemConfigService.getValueByKey("OCR_API_URL", ConstantCore.COMP_CUSTOMER_ID);
		if ( ocrUrl != null && !("").equals(ocrUrl) ) {
			String encodedFile;
			try {
				boolean hasCCCDCF = false;
				boolean hasCCCDCB = false;
				for (MultipartFile file : documentList) {
					encodedFile = Base64.getEncoder().encodeToString(file.getBytes());
					MultiValueMap<String, Object> multiMap = new LinkedMultiValueMap<>();
					multiMap.put("base64_img", Arrays.asList(encodedFile));
					
					try {
						JSONObject result = ocrUtil.callApi(
							"ocr_instant_respond", null, HttpMethod.POST, multiMap,
							MediaType.APPLICATION_FORM_URLENCODED, String.class
						);
				
						data = result.getJSONObject("data");
						if (data.get("idType") == null) {
							throw new DetailException(AppApiExceptionCodeConstant.E9028100_APPAPI_ID_NUMBER_INVALID);
						} else {
							if ("CCCDCF".equals(data.get("idType"))
									|| "CCCDF".equals(data.get("idType"))
									|| "CC2024F".equals(data.get("idType"))) {
								ret = data.get("idNum").toString();
								hasCCCDCF = true;
							}
							if ("CCCDCB".equals(data.get("idType"))
									|| "CCCDB".equals(data.get("idType"))
									|| "CC2024B".equals(data.get("idType"))) {
								hasCCCDCB = true;
							}
						}	
					} catch (Exception ex) {
						logger.error("Error: " + ex.toString());
						throw new DetailException(AppApiExceptionCodeConstant.E9028100_APPAPI_ID_NUMBER_INVALID);
					}
				}
				if (!hasCCCDCF || !hasCCCDCB) {
					throw new DetailException(AppApiExceptionCodeConstant.E9028100_APPAPI_ID_NUMBER_INVALID);
				}
			} catch (IOException e) {
				logger.error("File Error: " + e.toString());
			}
		}
		return ret;
	}
	
	@Override
    public AgentBankUpdateHistory updateBankInfo(AgentBankDto data, List<MultipartFile> listFile) throws Exception {
		String agentCode = UserProfileUtils.getFaceMask();
		String cacheOtp = otpService.getOtp(agentCode);
		if("0".equals(cacheOtp)){
            throw new DetailException(AppApiExceptionCodeConstant.E4026112_APPAPI_OTP_IS_EXPIRED_ERROR);
        }
        int countIncorrect = otpService.getIncorrectOtp(data.getAgentCode() + "_c3");
    	if (countIncorrect >= 3) {
    		throw new DetailException(AppApiExceptionCodeConstant.E4026113_APPAPI_INCORRECT_OTP);
    	}
        boolean check = StringUtils.equalsIgnoreCase(cacheOtp, data.getOtp());
        if (!check) {
        	otpService.setIncorrectOtp(data.getAgentCode() + "_c3", String.valueOf(countIncorrect + 1));
        	throw new DetailException(AppApiExceptionCodeConstant.E4026111_APPAPI_OTP_IS_NOT_CORRECT_ERROR);
        }
        
        Db2AgentDto agentInfo = db2ApiService.getAgentClientDetail(agentCode);
        String email = null;
		if (agentInfo != null) {
			email = agentInfo.getEmailAddress2();
		}
        AgentBankUpdateHistory entity = new AgentBankUpdateHistory();
        entity.setAgentCode(agentCode);
        entity.setIdNumber(data.getIdNumber());
        entity.setBankAccountNumber(data.getBankAccountNumber());
        entity.setBankAccountName(data.getBankAccountName());
        entity.setBankCode(db2ApiService.getBankCode(data.getBankName(), data.getBankBranchName()));
        entity.setBankName(data.getBankName());
        entity.setBankBranchName(data.getBankBranchName());
        entity.setOtp(data.getOtp());
        entity.setEmail(email);
        entity.setTransactionType("1");
        entity.setSubmitTime(new Date());
        saveAttachFile(entity, listFile);
        agentBankUpdateHistoryRepository.save(entity);
		
        return entity;
	}
	
	@Override
	public List<BankInfoUpdateHistoryDto> getBankInfoUpdHistorys(String agentCode) {
		return agentBankUpdateHistoryRepository.getUpdateHistory(agentCode);
	}
	
	@Override
	public ResponseEntity<byte[]> getDocument(BankInfoUpdateHistoryDto data) throws Exception {
		String pathTemplatePdf = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_PDF);
		String templatePath = Paths.get(pathTemplatePdf, "update-bank-account.jrxml").toString();
		
		FileInputStream inputStream = new FileInputStream(templatePath);
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		
		// configure parameters
		Map<String, Object> params = new HashMap<>();
		params.put("path", pathTemplatePdf);
		params.put("agentCode", data.getAgentCode());
		params.put("agentName", data.getAgentName());
		params.put("bankAccountName", data.getBankAccountName());
		params.put("idNumber", data.getIdNumber());
		params.put("bankAccountNumber", data.getBankAccountNumber());
		params.put("bankName", data.getBankName());
		params.put("bankBranchName", data.getBankBranchName());
		params.put("email", data.getEmail());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		params.put("submitTime", sdf.format(data.getSubmitTime()));
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
		
		byte[] result = JasperExportManager.exportReportToPdf(jasperPrint);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CommonConstant.CONTENT_DISPOSITION, "inline;filename=Chi_tiet_cap_nhat_TKNH.pdf");
		
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(result);
	}
	
	@Override
    public boolean sendMailSuccess(AgentBankDto data) throws Exception {       
        Map<String, Object > paramEmail = new HashMap<String, Object>();
        paramEmail.put("agentCode", data.getAgentCode());
        paramEmail.put("agentName", data.getAgentName());
        paramEmail.put("idNumber", data.getIdNumber());
        paramEmail.put("bankAccountNumber", data.getBankAccountNumber());
        paramEmail.put("bankName", data.getBankName());
        List<String> toAddress = new ArrayList<>();
        toAddress.add(data.getEmail());
        
        JcaEmailTemplateDto templateDto = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(TEMPLATE_INFO_UPDATE_BANK_INFO);
		if (templateDto != null) {
			String content = jcaEmailService.replaceParam(templateDto.getTemplateContent(), paramEmail);
			String subject = templateDto.getTemplateSubject();
			//setting content for email
			JcaEmailDto jcaEmailDto = jcaEmailService.convertValue(templateDto);
			jcaEmailDto.setToString("");
			jcaEmailDto.setToAddress(toAddress);
			jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
			jcaEmailDto.setEmailContent(content);
			jcaEmailDto.setSubject(subject);
			JcaEmailDto dto = new JcaEmailDto();
			CommonNullAwareBeanUtil.copyPropertiesWONull(jcaEmailDto, dto);
			return jcaEmailService.sendEmail(dto).isStatus();
		}
		
        return false;
	}
	
	private void saveAttachFile(AgentBankUpdateHistory entity, List<MultipartFile> listFile) {
		SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentDate = formatDateExport.format(new Date());
		String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
		String path = systemConfig.getPhysicalPathById(repo, null);
		path = Paths.get(path, "CCCD/" + entity.getAgentCode()).toString();
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Path pathFile = null;
		int indexDot, i = 0;
		for (MultipartFile file : listFile) {
			i++;
			try {
				byte[] fileCode = file.getBytes();
				indexDot = file.getOriginalFilename().lastIndexOf(".");
				pathFile = Paths.get(path, file.getOriginalFilename().substring(0, indexDot) + "_" + currentDate + file.getOriginalFilename().substring(indexDot));
				Files.write(pathFile, fileCode);
				if (i == 1) {
					entity.setImg1Physical(pathFile.toString());
				} else {
					entity.setImg2Physical(pathFile.toString());
				}
			} catch (IOException e) {
				logger.error("Exception ", e);
			}
		}
	}
}