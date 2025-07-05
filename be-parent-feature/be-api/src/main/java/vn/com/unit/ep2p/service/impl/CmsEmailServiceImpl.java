package vn.com.unit.ep2p.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import vn.com.unit.common.dto.EmailResultDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.enumdef.SendEmailTypeEnum;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.service.CmsEmailService;
import vn.com.unit.ep2p.service.OtpService;
import vn.com.unit.sla.email.service.SlaEmailTemplateService;
@Service
public class CmsEmailServiceImpl implements CmsEmailService{
	@Autowired
	JcaEmailTemplateService jcaEmailTemplateService;
	
	@Autowired
	private JcaEmailService jcaEmailService;
	
	@Autowired
	@Qualifier("appSystemConfigServiceImpl")
	JcaSystemConfigService jcaSystemConfigService;

	@Autowired
	private SystemConfig systemConfig;
	
	@Autowired
	private SlaEmailTemplateService slaEmailTemplateService;
    
    @Autowired
    private OtpService optpService;
	
	private Logger logger = LoggerFactory.getLogger(CmsEmailServiceImpl.class);
	private static final String TEMPLATE_GIVE_OTP_FOR_ACCOUNT_TER = "TEMPLATE_GIVE_OTP_FOR_ACCOUNT_TER";
	@Override
	public EmailResultDto sendMailOtp(String agentCode, String email) throws IOException {
		List<String> emailTo = new ArrayList<>();
		final String senderAddress = systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT);
		// emailTo.add(jcaSystemConfigService.getValueByKey(dto.getTitle(),
		// UserProfileUtils.getCompanyId())); 
		logger.error("NEED GET COMPANY ID FROM USER PROFILE UTILS WHEN FE DEVELOP"); 
		emailTo.add(email); 
		JcaEmailDto jcaEmailDto = new JcaEmailDto(); 
		jcaEmailDto.setSenderAddress(senderAddress); 
		jcaEmailDto.setContentType("text/html; charset=utf-8"); 
		jcaEmailDto.setToString(String.join(",", emailTo)); 
		jcaEmailDto.setToAddress(emailTo);
		jcaEmailDto.setCompanyId(2L);
		jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue()); 
		JcaEmailTemplateDto emailTemplate = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(TEMPLATE_GIVE_OTP_FOR_ACCOUNT_TER);
		jcaEmailDto.setSubject(emailTemplate.getTemplateSubject()); 
		String otp = String.valueOf(optpService.generateOTP(agentCode));
		Map<String, Object> mapDataEmail = new HashMap<String, Object>(); 
		mapDataEmail.put("password", otp); 
		String content = slaEmailTemplateService.replaceParam(emailTemplate.getTemplateContent(), mapDataEmail); 
		jcaEmailDto.setEmailContent(content);
		
		return jcaEmailService.sendEmail(jcaEmailDto);
	}

}
