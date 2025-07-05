package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.nio.file.Paths;
import java.sql.Timestamp;
import javax.servlet.ServletContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import vn.com.unit.ep2p.admin.dto.AcceptanceCertificationGaInformationDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.service.AcceptanceCertificationGaService;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.OtpService;
import vn.com.unit.cms.core.module.ga.dto.param.IncomeConfirmPaymentParamGa;
import vn.com.unit.cms.core.module.ga.dto.IncomeConfirmPaymentDto;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetail;
import vn.com.unit.cms.core.module.ga.dto.AcceptanceCertificationDetailGa;
import vn.com.unit.cms.core.module.ga.dto.AcceptanceCertificationDto;
import vn.com.unit.cms.core.module.ga.dto.AcceptanceCertificationIncomeDto;
import vn.com.unit.cms.core.module.ga.dto.AcceptanceCertificationReportDto;
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
import vn.com.unit.imp.excel.constant.CommonConstant;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class AcceptanceCertificationGaServiceImpl implements AcceptanceCertificationGaService {
	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;
	
	@Autowired
    @Qualifier("appSystemConfigServiceImpl")
    private JcaSystemConfigService jcaSystemConfigService;
	
	@Autowired
	private PaymentConfirmRepository paymentConfirmRepository;
	
	@Autowired
	private Db2ApiService db2ApiService;
	
	@Autowired
	private OtpService otpService;
	
    @Autowired
    private JcaEmailTemplateService jcaEmailTemplateService;
    
    @Autowired
    private JcaEmailService jcaEmailService;
    
    @Autowired
	private ApiAgentDetailService apiAgentDetailService;
    
    @Autowired
	private ServletContext servletContext;
	
	private static final Logger logger = LoggerFactory.getLogger(AcceptanceCertificationGaServiceImpl.class);
	
	private static final String STORE_CONFIRM_PAYMENT_GA = "RPT_ODS.DS_SP_GET_CONFIRM_PAYMENT_GA";
	
	public static final String TEMPLATE_GIVE_OTP_FOR_ACCOUNT = "TEMPLATE_GIVE_OTP_FOR_ACCOUNT";
	
	private static final String TEMPLATE_GIVE_OTP_FOR_ACCEPTANCE_CERTIFICATE_GA_CONFIRM = "TEMPLATE_GIVE_OTP_FOR_ACCEPTANCE_CERTIFICATE_GA_CONFIRM";
	
	@Override
	public AcceptanceCertificationDto getAcceptanceCertificationDetail(IncomeConfirmPaymentParamGa incomeParamGa) {
		AcceptanceCertificationDto data = new AcceptanceCertificationDto();
		
		sqlManagerDb2Service.call(STORE_CONFIRM_PAYMENT_GA, incomeParamGa);
		
		if (incomeParamGa.lstData.size() <= 0) {
			data.setIncomes(incomeParamGa.lstData);
			data.setGaInformation(null);
			return data;
		}
		
        AcceptanceCertificationGaInformationDto gaInfomation = db2ApiService.getAcceptanceCertification(incomeParamGa.gadCode, incomeParamGa.orgCode, incomeParamGa.period);
        
		data.setIncomes(incomeParamGa.lstData);
		data.setGaInformation(gaInfomation);

		return data;
	};
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean confirmAcceptanceCertification(AcceptanceCertificationDetailGa data) throws Exception {
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
        
        
        int count = paymentConfirmRepository.checkGAConfirmed(data.getAgentCode(), data.getGaCode(), data.getPeriod());
        
        if (count < 1) {
        	PaymentConfirm newConfirm = new PaymentConfirm();
        
        	newConfirm.setAgentCode(data.getAgentCode());
        	newConfirm.setGaCode(data.getGaCode());
        	newConfirm.setPeriod(data.getPeriod());
        	newConfirm.setConfirmTime(new Date());
        	newConfirm.setRejectTime(null);
        	newConfirm.setOtp(data.getOtp());
        	paymentConfirmRepository.save(newConfirm);		
		}
		
        return true;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean denyAcceptanceCertification(AcceptanceCertificationDetailGa data) {
		PaymentConfirm newConfirm = new PaymentConfirm();
        
		newConfirm.setAgentCode(data.getAgentCode());
        newConfirm.setGaCode(data.getGaCode());
        newConfirm.setPeriod(data.getPeriod());
        newConfirm.setConfirmTime(null);
        newConfirm.setOtp(null);
        newConfirm.setRejectReason(data.getRejectReason());
        newConfirm.setRejectTime(new Timestamp(System.currentTimeMillis()));
        
        paymentConfirmRepository.save(newConfirm);
        return true;
	}
	
	@Override
	public boolean sendOtpGad(String agentCode) throws Exception {
		try {
			Db2AgentDto agentInfo = db2ApiService.getAgentClientDetail(agentCode);
			
			String otp = otpService.generateOTP(agentCode);
			String email = agentInfo.getEmailAddress1();
			
			if (agentInfo != null && StringUtils.isNotBlank(email)) {	
				Map<String, Object > paramEmail = new HashMap<String, Object>();
				List<String> toAddress = new ArrayList<>();
				
	            paramEmail.put("otp", String.valueOf(otp));
			
	            toAddress.add(email);
	            
	            JcaEmailTemplateDto templateDto = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(TEMPLATE_GIVE_OTP_FOR_ACCEPTANCE_CERTIFICATE_GA_CONFIRM);
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
	public PaymentConfirm getPaymentConfirmInfo(String agentCode, String gaCode, String period) {
		if(gaCode == null) {
			PaymentConfirm paymentConfirm = paymentConfirmRepository.getConfirmInfo(agentCode, period);
			return paymentConfirm;		
		} else {
			PaymentConfirm paymentConfirm = paymentConfirmRepository.getGAConfirmInfo(agentCode, gaCode, period);
			return paymentConfirm;
		}
		
	}
	
	@Override
	public ResponseEntity<byte[]> getAcceptanceCertificationReport(
		AcceptanceCertificationReportDto dataSource,
		PaymentConfirm paymentConfirm
	) {
		byte[] byteArr = null;
		List<AcceptanceCertificationIncomeDto> incomes = new ArrayList<>();
		String datePattern = "dd/MM/yyyy";
		DateFormat dateFormatForDate = new SimpleDateFormat(datePattern);
		DateTimeFormatter dateFormatForLocalDate = DateTimeFormatter.ofPattern(datePattern);
		DateFormat dateFormatForDb = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			IncomeConfirmPaymentParamGa incomesParams = dataSource.getIncomesParams();
			String agentCode = incomesParams.gadCode;
			sqlManagerDb2Service.call(STORE_CONFIRM_PAYMENT_GA, incomesParams);
			
			CmsAgentDetail agentDetail = apiAgentDetailService.getCmsAgentDetailByUsername(agentCode);
			
			AcceptanceCertificationGaInformationDto gaInformation = db2ApiService.getAcceptanceCertification(agentCode, incomesParams.orgCode, incomesParams.period);
			
			int count = 1;
			String currMainName = "";
			String currMainCode = "";
			for (IncomeConfirmPaymentDto item : incomesParams.lstData) {
				if (item.getSubCode() == null) {
					count = 1;
					currMainName = item.getSubName();
					currMainCode = item.getMainCode();
					continue;
				}
				
				if (item.getMainCode().equals(currMainCode)) {
					incomes.add(new AcceptanceCertificationIncomeDto(
						count++,
						item.getSubName(),
						item.getAmount(),
						currMainName
					));
				}
			}
			
			String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_PDF);
			String jasperDesignPathFile = Paths.get(templatePath, "confirmPayment_GA.jrxml").toString();
			
			FileInputStream inputStream = new FileInputStream(jasperDesignPathFile);
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			
			// configure parameters
			String note1 = String.format(
				"Căn cứ Hợp đồng Đại lý Tổ Chức số %s ký ngày %s và Căn cứ Hợp đồng Dịch vụ số %s ký ngày %s giữa CÔNG TY TNHH BẢO HIỂM NHÂN THỌ DAI-ICHI VIỆT NAM (DLVN) và %s (Tổng Đại lý)",
				gaInformation.getHdtdlNo(),
				dateFormatForDate.format(dateFormatForDb.parse(gaInformation.getAgContractDate())),
				gaInformation.getHddvNo(),
				dateFormatForDate.format(dateFormatForDb.parse(gaInformation.getSeContractDate())),
				gaInformation.getCompany()
			);
			String note2 = String.format(
				"DLVN gửi Biên bản nghiệm thu và xác nhận các công việc liên quan đến thực hiện Hoạt động Tổng Đại lý và Dịch vụ mà Tổng Đại Lý đã cung cấp trong khoảng thời gian từ ngày 01/%s/%s đến %s như sau:",
				dataSource.getMonth(),
				dataSource.getYear(),
				YearMonth.of(Integer.parseInt(dataSource.getYear()), Integer.parseInt(dataSource.getMonth())).atEndOfMonth().format(dateFormatForLocalDate)
			);
			String note3 = " ";
			if (paymentConfirm != null) {
				if (paymentConfirm.getConfirmTime() != null) {
					note3 = String.format(
						"Đại diện theo pháp luật: %s_%s xác nhận OTP ngày %s.",
						agentCode,
						agentDetail.getFullName(),
						dateFormatForDate.format(paymentConfirm.getConfirmTime())
					);
				}
				else if (paymentConfirm.getRejectReason() != null) {
					note3 = String.format(
						"Đã từ chối với lý do: %s.",
						paymentConfirm.getRejectReason()
					);
				}
			}
			
			String trainedStatus = gaInformation.getTrainedId() == null ? "Chưa hoàn thành" : "Hoàn thành";
			
			Map<String, Object> params = new HashMap<>();
			params.put("agentCode", agentCode);
			params.put("note1", note1);
			params.put("note2", note2);
			params.put("note3", note3);
			params.put("trained", trainedStatus);
			params.put("taxCode", gaInformation.getTaxCodeOrg());
			params.put("agentName", agentDetail.getFullName());
			params.put("bankAccount", dataSource.getBankAccountNumber());
			params.put("bankName", dataSource.getBankAccountName());
			params.put("office", dataSource.getOffice());
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
