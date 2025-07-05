package vn.com.unit.ep2p.admin.schedule;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import vn.com.unit.core.entity.JcaEmail;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.repository.EmailRepository;
import vn.com.unit.ep2p.admin.service.AppEmailService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.ep2p.dto.EmailDto;
import vn.com.unit.ep2p.utils.JsonUtils;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;
import vn.com.unit.common.utils.CommonDateUtil
;

/**
 * EmailJobServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hientn
 */

@Service
public class EmailJobServiceImpl implements EmailJobService {
	
private static final Logger logger = LoggerFactory.getLogger(EmailJobServiceImpl.class);
	
	public static final Locale locale = LocaleContextHolder.getLocale();

	private static final String WORKING_DAY = "2";
	
	@Autowired
	private AppEmailService emailService;
	
	@Autowired
	private SystemConfig systemConfig;
	
	@Autowired
    private CompanyService companyService;
	
	@Autowired
	private EmailRepository emailRepository;
	
	@Override
	public void sendEmailForAlertJob(SecurityContext securityContext, QrtzMJob job, QrtzMJobLog jobLog) throws Exception {
		SecurityContextHolder.setContext(securityContext);
		if (job != null && job.getSendNotification() != null && job.getSendNotification()) {         
            if (StringUtils.isBlank(job.getSendStatus())) {
                return;
            }
            EmailDto emailDto = new EmailDto();
    		// setSendType
    		emailDto.setSendEmailType(WORKING_DAY);
    		Long comId = job.getCompanyId();
			emailDto.setCompanyId(comId);
            Long jobStatus = jobLog.getStatus();
            String[] sendStatus = job.getSendStatus().split(ConstantCore.COMMA);
            String checkStatus = jobStatus == null ? StringUtils.EMPTY : Arrays.stream(sendStatus).filter(x -> x.trim().equals(jobStatus.toString())).findFirst().orElse(null);
            if (StringUtils.isBlank(checkStatus)) {
            	logger.debug("##sendEmailForAlertJob - status of job :" + jobStatus);
                return;
            }
            // Recipient address
            String recipientAddress = job.getRecipientAddress();
            if (StringUtils.isNotBlank(recipientAddress)) {
                emailDto.setReceiveAddress(recipientAddress);
                emailDto.setToString(recipientAddress);
            }
            // CC address
            String ccAddress = job.getCcAddress();
            if (StringUtils.isNotBlank(ccAddress)) {
                emailDto.setCcString(ccAddress);
            }
            // BCC address
            String bccAddress = job.getBccAddress();
            if (StringUtils.isNotBlank(bccAddress)) {
                emailDto.setBccString(bccAddress);
            }
            Long id = Long.valueOf(job.getEmailTemplate());
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node1 = (ObjectNode) mapper.readTree(JsonUtils.convertObjectToJSON(job));
            ObjectNode node2 = (ObjectNode) mapper.readTree(JsonUtils.convertObjectToJSON(jobLog));
            
            // Add status Name
            String statusName = StringUtils.EMPTY;
            if (null != jobLog.getStatus()) {
                statusName = ScheduleTask.JobStatus.resolve(jobLog.getStatus()).toString();
            }
            node2.put("statusName", statusName);
            
            // Add run Date
            String runDate = StringUtils.EMPTY;
            if (null != jobLog.getStartTime()) {
                runDate = CommonDateUtil.formatDateToString(jobLog.getStartTime(), systemConfig.getConfig(SystemConfig.DATE_PATTERN, job.getCompanyId()) + " HH:mm:ss");
            }
            node2.put("runDate", runDate);
            
            // Add errorMsg 
            String errorMsg = StringUtils.EMPTY;
            if(StringUtils.isNotBlank(jobLog.getErrorMessage())) {
                errorMsg = "Error message: "  + jobLog.getErrorMessage();
            }
            node2.put("error", errorMsg);
           
            // Add url Default
            String urlDefault = systemConfig.getConfig(SystemConfig.URL_DEFAULT, job.getCompanyId()).concat("/quartz/job-schedule/list")
                    .replace("//", "/");
            node2.put("urlDefault", urlDefault);

            // Add system Name
            String systemName = StringUtils.EMPTY;
            CompanyDto company = companyService.findById(job.getCompanyId());
            if (null != company) {
                systemName = company.getSystemName();
            }
            node2.put("systemName", systemName);
            
            // Add sender Name
            String senderName = systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT_NAME, job.getCompanyId());
            node2.put("senderName", senderName);
            
            JsonNode merged = node1.setAll(node2);
            String jsonInput = JsonUtils.convertObjectToJSON(merged);
            logger.debug("##sendEmailForAlertJob - before sending email : templateId = " + id + "; Json input = " + jsonInput);
            emailService.sendMailByTemplateIdAndJsonPram(id, emailDto, jsonInput, null, locale, Boolean.TRUE);
		}			
	}

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStatusForListEmail(List<JcaEmail> listEmail, String status) throws Exception {
        if(null != listEmail && !listEmail.isEmpty()) {
            listEmail.stream().forEach(email -> {
            	email.setSendStatus(status);
            	if(ObjectUtils.isNotEmpty(email.getId()))
            		emailRepository.update(email);
            	else
            		emailRepository.create(email);
            });
           // emailRepository.save(listEmail);
        }
    }

}
