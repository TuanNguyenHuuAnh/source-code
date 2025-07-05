package vn.com.unit.ep2p.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import vn.com.unit.cms.core.module.ag.dto.AcceptanceCertificationAGDto;
import vn.com.unit.cms.core.module.ag.dto.AcceptanceCertificationDetailAG;
import vn.com.unit.cms.core.module.ag.dto.AcceptanceCertificationIncomeAGDto;
import vn.com.unit.cms.core.module.ga.dto.param.IncomeConfirmPaymentParamAg;
import vn.com.unit.cms.core.module.payment.entity.PaymentConfirm;
import vn.com.unit.cms.core.module.payment.repository.PaymentConfirmRepository;
import vn.com.unit.common.utils.CommonNullAwareBeanUtil;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.enumdef.SendEmailTypeEnum;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.AgentTaxBankInfoDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.IncomeConfirmPaymentAGDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.service.AcceptanceCertificationAGService;
import vn.com.unit.ep2p.service.OtpService;
import vn.com.unit.imp.excel.constant.CommonConstant;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class AcceptanceCertificationAGServiceImpl implements AcceptanceCertificationAGService {
	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;
	
	@Autowired
    @Qualifier("appSystemConfigServiceImpl")
    private JcaSystemConfigService jcaSystemConfigService;
	
	@Autowired
	private PaymentConfirmRepository aGPaymentConfirmRepository;
	
	@Autowired
	private Db2ApiService db2ApiService;
	
	@Autowired
	private OtpService otpService;
	
    @Autowired
    private JcaEmailTemplateService jcaEmailTemplateService;
    
    @Autowired
    private JcaEmailService jcaEmailService;
    
    @Autowired
	ServletContext servletContext;
    
	private static final Logger logger = LoggerFactory.getLogger(AcceptanceCertificationAGServiceImpl.class);
	
	private static final String STORE_CONFIRM_PAYMENT_AG = "RPT_ODS.DS_SP_GET_CONFIRM_PAYMENT_AG";
	
	public static final String TEMPLATE_GIVE_OTP_FOR_ACCOUNT = "TEMPLATE_GIVE_OTP_FOR_ACCOUNT";
	
	private static final String TEMPLATE_GIVE_OTP_FOR_ACCEPTANCE_CERTIFICATE_AG_CONFIRM = "TEMPLATE_GIVE_OTP_FOR_ACCEPTANCE_CERTIFICATE_AG_CONFIRM";
	
	
	@Override
	public AgentTaxBankInfoDto getTaxBusinessHouseHoldByAgentCode(String agentCode) {
		return db2ApiService.getTaxBusinessHouseHoldByAgentCode(agentCode);		
	};
	
	@Override
	public AcceptanceCertificationAGDto getAcceptanceCertificationAG(String agentCode, String cutOffDateYYYYMM) {	
		AcceptanceCertificationAGDto data = new AcceptanceCertificationAGDto();
		IncomeConfirmPaymentParamAg  incomeParamAg = new IncomeConfirmPaymentParamAg();
		incomeParamAg.agentCode = agentCode;
		incomeParamAg.period = cutOffDateYYYYMM;
		sqlManagerDb2Service.call(STORE_CONFIRM_PAYMENT_AG, incomeParamAg);
		
		AgentTaxBankInfoDto agInfo = db2ApiService.getAgentTaxBankInfo(agentCode, cutOffDateYYYYMM);
	        
		data.setIncomes(incomeParamAg.lstData);
		data.setTaxBankInfo(agInfo);

		return data;
	};
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean confirmAcceptanceCertificationAG(AcceptanceCertificationDetailAG data) throws Exception {
		String cacheOtp = otpService.getOtp(data.getAgentCode());
		if("0".equals(cacheOtp)){
            throw new Exception("EXPIRED_OTP");
        }
        int countIncorrect = otpService.getIncorrectOtp(data.getAgentCode() + "_c3");
    	if (countIncorrect >= 3) {
    		throw new DetailException(AppApiExceptionCodeConstant.E4026113_APPAPI_INCORRECT_OTP);
    	}
        boolean check = StringUtils.equalsIgnoreCase(cacheOtp, data.getOtp());
        if (!check) {
        	otpService.setIncorrectOtp(data.getAgentCode() + "_c3", String.valueOf(countIncorrect + 1));
        	return false;
        }
        
        PaymentConfirm currEntity = aGPaymentConfirmRepository.getConfirmInfo(data.getAgentCode(), data.getPeriod());
        
        if (currEntity == null) {
        	PaymentConfirm newConfirm = new PaymentConfirm();
            
            newConfirm.setAgentCode(data.getAgentCode());
            newConfirm.setGaCode(null);
            newConfirm.setPeriod(data.getPeriod());
            newConfirm.setConfirmTime(data.getConfirmTime());
            newConfirm.setRejectTime(null);
            newConfirm.setOtp(data.getOtp());
            
            aGPaymentConfirmRepository.save(newConfirm);
        }
        else {
        	currEntity.setConfirmTime(new Timestamp(System.currentTimeMillis()));
        	aGPaymentConfirmRepository.update(currEntity);
        }
        
        return true;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean denyAcceptanceCertificationAG(AcceptanceCertificationDetailAG data) {
		PaymentConfirm newConfirm = new PaymentConfirm();
        
		newConfirm.setAgentCode(data.getAgentCode());
		newConfirm.setGaCode(null);
        newConfirm.setPeriod(data.getPeriod());
        newConfirm.setConfirmTime(null);
        newConfirm.setRejectTime(new Timestamp(System.currentTimeMillis()));
        newConfirm.setOtp(null);
        newConfirm.setRejectReason(data.getRejectReason());
        
        aGPaymentConfirmRepository.save(newConfirm);
        return true;
	}
	
	@Override
	public boolean sendOTPAgent(String agentCode, String cutOffDateYYYYMM) throws Exception {
		try {
			Db2AgentDto agentInfo = db2ApiService.getAgentClientDetail(agentCode);
			
			String otp = otpService.generateOTP(agentCode);
			
			if (agentInfo != null) {	
				String email = agentInfo.getEmailAddress1();
				if(StringUtils.isBlank(email)) {
					email = agentInfo.getEmailAddress2();
				}
				Map<String, Object > paramEmail = new HashMap<String, Object>();
				List<String> toAddress = new ArrayList<>();
				
				paramEmail.put("cutOffDateYYYYMM", cutOffDateYYYYMM); 
	            paramEmail.put("otp", String.valueOf(otp));	            
			
	            toAddress.add(email);
	            
	            JcaEmailTemplateDto templateDto = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(TEMPLATE_GIVE_OTP_FOR_ACCEPTANCE_CERTIFICATE_AG_CONFIRM);
				if (templateDto != null) {
					String content = jcaEmailService.replaceParam(templateDto.getTemplateContent(), paramEmail);
					String subject = jcaEmailService.replaceParam(templateDto.getTemplateSubject(), paramEmail);
					//setting content for email
					JcaEmailDto jcaEmailDto = jcaEmailService.convertValue(templateDto);
					jcaEmailDto.setToString("");
					jcaEmailDto.setToAddress(toAddress);
					jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
					jcaEmailDto.setEmailContent(content);
					jcaEmailDto.setSubject(subject);
					JcaEmailDto dto = new JcaEmailDto();
					CommonNullAwareBeanUtil.copyPropertiesWONull(jcaEmailDto, dto);
					return jcaEmailService.sendEmail(dto).isStatus(); // return EmailResultDto: isStatus() = getStatus()
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
	@Transactional(rollbackFor = Exception.class)
	public PaymentConfirm getPaymentConfirmInfoAG(String agentCode, String cutOffDateYYYYMM) {
		return aGPaymentConfirmRepository.getConfirmInfo(agentCode, cutOffDateYYYYMM);		
	}
	
	@Override
	public ResponseEntity<byte[]> getAcceptanceCertificationReportAG(String agentCode, String cutOffDateYYYYMM, String note2, String note3) {
		byte[] byteArr = null;
		List<AcceptanceCertificationIncomeAGDto> incomes = new ArrayList<>();
		try {
			
			IncomeConfirmPaymentParamAg  incomeParamAg = new IncomeConfirmPaymentParamAg();
			incomeParamAg.agentCode = agentCode;
			incomeParamAg.period = cutOffDateYYYYMM;
			sqlManagerDb2Service.call(STORE_CONFIRM_PAYMENT_AG, incomeParamAg);
			
			AgentTaxBankInfoDto agInfo = db2ApiService.getAgentTaxBankInfo(agentCode, cutOffDateYYYYMM);
			
			int count = 1;
			String mainName = "Tổng cộng";
			for (IncomeConfirmPaymentAGDto item : incomeParamAg.lstData) {
				
				incomes.add(new AcceptanceCertificationIncomeAGDto(
					count++,
					item.getSubName(),
					item.getSubAmount(),
					mainName
				));
			}
			
			String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_PDF);
			String jasperDesignPathFile = Paths.get(templatePath, "confirmPayment_AG.jrxml").toString();
			
			FileInputStream inputStream = new FileInputStream(jasperDesignPathFile);
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			
			// configure parameters
			Map<String, Object> params = new HashMap<>();
			params.put("agentCode", agentCode);
			params.put("businessHouseholds", agInfo.getBusinessHouseholds());
			params.put("note2", note2);
			params.put("trained", agInfo.getTrainedId() != null ? "Hoàn thành" : "Chưa hoàn thành");
			params.put("agentType", agInfo.getAgentType());
			params.put("agentName", agInfo.getAgentName());
			params.put("bankAccount", agInfo.getBankAccountNumber());
			params.put("taxCode", agInfo.getTaxCodeInb());
			params.put("note3", note3);
			params.put("bankName", agInfo.getBankAccountName());
			params.put("path", templatePath);
			JRBeanCollectionDataSource collDataSource = new JRBeanCollectionDataSource(incomes);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, collDataSource);
			
			byteArr = JasperExportManager.exportReportToPdf(jasperPrint);
		} catch(Exception e) {
			if (e instanceof FileNotFoundException) {
				logger.error("Jasper file not found!");
			}
			else if (e instanceof NumberFormatException) {
				logger.error("mainCode is not number!");
			}
			else {
				logger.error("Error when exporting pdf: ", e);
			}
			return null;
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(
			CommonConstant.CONTENT_DISPOSITION,
			"inline;filename=report.pdf"
		);
		return ResponseEntity
			.ok()
	        .contentType(MediaType.APPLICATION_PDF)
	        .headers(headers)
	        .body(byteArr);
	}
}
