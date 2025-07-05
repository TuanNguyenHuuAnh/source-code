/*******************************************************************************
 * Class        ：JcaEmailServiceImpl
 * Created date ：2021/01/29
 * Lasted date  ：2021/01/29
 * Author       ：TrieuVD
 * Change log   ：2021/01/29：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.dto.EmailResultDto;
import vn.com.unit.common.service.EmailService;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonNullAwareBeanUtil;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.config.SystemSettingKey;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailSearchDto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.entity.JcaAttachFileEmail;
import vn.com.unit.core.entity.JcaEmail;
import vn.com.unit.core.enumdef.EmailStatusEnum;
import vn.com.unit.core.enumdef.SendEmailTypeEnum;
import vn.com.unit.core.enumdef.param.ConstDispType;
import vn.com.unit.core.repository.JcaEmailRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConfigMailService;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.JcaEmailService;

/**
 * JcaEmailServiceImpl
 * 
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaEmailServiceImpl implements JcaEmailService {

	@Autowired
	private JcaEmailRepository jcaEmailRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JCommonService commonService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private JcaConfigMailService jcaConfigMailService;

	@Autowired
	private JcaConstantService jcaConstantService;

	private Logger logger = LoggerFactory.getLogger(JcaEmailServiceImpl.class);

	public static final String PARAM_START_WITH = "${";
	public static final String PARAM_END_WITH = "}";
	public static final Pattern PARAM_PATTERN = Pattern
			.compile(Pattern.quote(PARAM_START_WITH) + "(.*?)" + Pattern.quote(PARAM_END_WITH));
	/*
	 * (non-Javadoc)
	 * 
	 * @see vn.com.unit.core.service.JcaEmailService#sendEmail(vn.com.unit.core.dto.
	 * JcaEmailDto)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public EmailResultDto sendEmail(JcaEmailDto emailDto) {
		logger.error("Send Email Start");
		emailDto.setId(null);
		emailDto.setEmailId(null);
		String sendEmailTypeDefault = systemConfig.getConfig(SystemConfig.TYPE_OF_SENDMAIL);
		if("AWS".equals(sendEmailTypeDefault)) {
			try {
				logger.error("Send Email AWS");
				EmailResultDto resultDto = sendEmailAws(emailDto, null);
				return resultDto;
			} catch (Exception e) {
				logger.error("AWS", e);
			}
		}
		EmailResultDto resultDto = new EmailResultDto();
		Long companyId = emailDto.getCompanyId();
		// check companyId
		// TODO TrieuVD
		String sendEmailType = emailDto.getSendEmailType();

		SendEmailTypeEnum emailType = SendEmailTypeEnum.resolveByValue(sendEmailType);
		boolean sendEmailFlag = false;
		boolean saveEmailFlag = false;
		switch (emailType) {
		case SEND_DIRECT_NO_SAVE:
			sendEmailFlag = true;
			break;
		case SEND_BY_BATCH:
			saveEmailFlag = true;
			emailDto.setSendStatus(EmailStatusEnum.EMAIL_SAVED.getValue());
			break;
		case SEND_DIRECT_SAVE:
		default:
			sendEmailFlag = true;
			saveEmailFlag = true;
			break;
		}
		if (sendEmailFlag) {
			Map<String, String> configMail = jcaConfigMailService.getConfigMailByCompanyId(companyId);
			resultDto = emailService.sendEmail(emailDto, configMail);
			emailDto.setSendDate(commonService.getSystemDate());
			if (resultDto.isStatus()) {
				emailDto.setSendStatus(EmailStatusEnum.EMAIL_SEND_SUCCESS.getValue());
			} else {
				emailDto.setSendStatus(EmailStatusEnum.EMAIL_SEND_FAIL.getValue());
			}
		}
		if (saveEmailFlag) {
			List<String> emailToList = emailDto.getToAddress();
			if (CommonCollectionUtil.isNotEmpty(emailToList)) {
				emailDto.setToString(emailToList.stream().collect(Collectors.joining(CommonConstant.COMMA)));
			}
			List<String> emailCcList = emailDto.getCcAddress();
			if (CommonCollectionUtil.isNotEmpty(emailCcList)) {
				emailDto.setCcString(emailCcList.stream().collect(Collectors.joining(CommonConstant.COMMA)));
			}
			List<String> emailBccList = emailDto.getBccAddress();
			if (CommonCollectionUtil.isNotEmpty(emailBccList)) {
				emailDto.setBccString(emailBccList.stream().collect(Collectors.joining(CommonConstant.COMMA)));
			}
			logger.error("Save Email Dto 2");
			this.saveJcaEmailDto(emailDto);
			logger.error("Send Email End");
		}
		return resultDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * vn.com.unit.core.service.JcaEmailService#saveJcaEmailDto(vn.com.unit.core.dto
	 * .JcaEmailDto)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JcaEmailDto saveJcaEmailDto(JcaEmailDto jcaEmailDto) {
		Long userId = null != UserProfileUtils.getUserPrincipal() ? UserProfileUtils.getUserPrincipal().getAccountId()
				: CommonConstant.SYSTEM_ID;
		Date sysDate = commonService.getSystemDate();
		Long id = jcaEmailDto.getId();
		JcaEmail objectSave;
		if (null != id) {
			objectSave = jcaEmailRepository.findOne(id);
			CommonObjectUtil.copyPropertiesNonNull(jcaEmailDto, objectSave);
			objectSave.setUpdatedId(userId);
			objectSave.setUpdatedDate(sysDate);
			jcaEmailRepository.update(objectSave);
		} else {
			objectSave = objectMapper.convertValue(jcaEmailDto, JcaEmail.class);
			objectSave.setCreatedId(userId);
			objectSave.setCreatedDate(sysDate);
			objectSave.setUpdatedId(userId);
			objectSave.setUpdatedDate(sysDate);
			jcaEmailRepository.create(objectSave);
		}
		jcaEmailDto.setId(objectSave.getId());
		return jcaEmailDto;
	}

	@Override
	public List<JcaEmailDto> getJcaEmailDtoListBySearchDto(JcaEmailSearchDto searchDto, Pageable pageable) {
		return jcaEmailRepository.getJcaEmailDtoListBySearchDto(searchDto, pageable).getContent();
	}

	@Override
	public int countJcaEmailDtoBySearchDto(JcaEmailSearchDto searchDto) {
		return jcaEmailRepository.countJcaEmailDtoBySearchDto(searchDto);
	}

	@Override
	public JcaEmailDto getJcaEmailDtoById(Long id) {
		return jcaEmailRepository.getJcaEmailDtoById(id);
	}

	@Override
	public JcaEmailDto convertValue(JcaEmailTemplateDto templateDto) {
		JcaEmailDto emailDto = objectMapper.convertValue(templateDto, JcaEmailDto.class);
		emailDto.setEmailContent(templateDto.getTemplateContent());
		emailDto.setSubject(templateDto.getTemplateSubject());
		emailDto.setContentType("text/html; charset=utf-8");
		return emailDto;
	}

	@Override
	public String replaceParam(String content, Map<String, Object> mapData) {
		LinkedHashSet<String> hashParam = new LinkedHashSet<String>();
		hashParam.addAll(getParams(content));
		for (String param : hashParam) {
			if (mapData.containsKey(param)) {
				String text = (String) mapData.get(param);
				if (text != null) {
					try {
						String str = PARAM_START_WITH.concat(param).concat(PARAM_END_WITH);
						while (content.contains(str)) {
							content = content.replace(str, text);
						}
					} catch (Exception e) {
						logger.error("######replaceParam######", e);
					}
				}
			}
		}
		return content;
	}

	/**
	 * @author TaiTM
	 * @date 2021-04-29
	 */
	public List<String> getParams(String content) {
		List<String> paramList = new ArrayList<String>();
		if (StringUtils.isNotBlank(content)) {
			Matcher matcher = PARAM_PATTERN.matcher(content);
			while (matcher.find()) {
				String param = matcher.group(1);
				paramList.add(param);
			}
		}
		return paramList;
	}

	/**
	 * @author VuNT
	 * @date 2021-10-20
	 * @description config send email AWS
	 */
	@Override
	public EmailResultDto sendEmailAws(JcaEmailDto emailDto, AmazonSimpleEmailService client) throws Exception {
		EmailResultDto response = new EmailResultDto();
		try {
			// type send mail default
			String sendEmailTypeDefault = systemConfig.getConfig(SystemConfig.SEND_EMAIL_TYPE_DEFAULT);
			// type send mail
			JcaConstantDto constBatch = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind("sendemail.opt.direct",ConstDispType.SENDEMAILOPT.name()
					, "1", "EN");
			JcaConstantDto constDirSave = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind("sendemail.opt.direct_archive",ConstDispType.SENDEMAILOPT.name()
					, "2", "EN");
			// status send mail
			JcaConstantDto typeSave = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind("sendemail.status.saved", CommonConstant.SEND_EMAIL_STATUS
					,"-1", "EN");
			// default type send mail
			String sendEmailType = emailDto.getSendEmailType();
			String statusSendMail = emailDto.getStatusSendMail();

			// set default type send mail default if type param is null
			if (StringUtils.isEmpty(sendEmailType)) {
				sendEmailType = sendEmailTypeDefault;
				// default status send mail
				emailDto.setStatusSendMail(typeSave.getKind());
			}
			// type send mail by batch
			if (sendEmailType.equals(constBatch.getKind())) {
				JcaConstantDto typeResend = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind("sendemail.status.resend", CommonConstant.SEND_EMAIL_STATUS
						, "3", "EN");
				JcaConstantDto typeCancel = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind("sendemail.status.cancel", CommonConstant.SEND_EMAIL_STATUS
						, "4", "EN");
				// status send mail : Resend||Cancel
				if (StringUtils.equals(statusSendMail, typeResend.getKind())) {
					emailDto.setStatusSendMail(typeResend.getKind());
				} else if (StringUtils.equals(statusSendMail, typeCancel.getKind())) {
					emailDto.setStatusSendMail(typeCancel.getKind());
				} else {
					JcaConstantDto constd = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind("sendemail.status.saved", CommonConstant.SEND_EMAIL_STATUS
							,"-1", "EN");
					emailDto.setStatusSendMail(constd.getKind());
				}
				// save email
				JcaEmail emailEntity = new JcaEmail();
				CommonNullAwareBeanUtil.copyPropertiesWONull(emailDto, emailEntity);
				emailEntity.setId(emailDto.getEmailId());
				emailEntity.setSendDate(new Date());
				logger.error("Save Email Dto");
				jcaEmailRepository.save(emailEntity);
				if (emailDto.getNumberAttachFile() != null && emailDto.getNumberAttachFile() > 0) {
					// save emailId to tb attachFile
					jcaEmailRepository.updateEmailId(emailEntity.getId(), emailDto.getUuidEmail());
				}
			} else {

				final String sender = systemConfig.getConfig(SystemSettingKey.AWS_EMAIL_SENDER);
				final String repo = systemConfig.getConfig(SystemSettingKey.AWS_REPO_URL_ATTACH_FILE);
				if(client == null) {
					//connection aws
					// config information
					final String accessKeyId = systemConfig.getConfig(SystemSettingKey.AWS_ACCESS_KEY);
					logger.info("accessKeyId", accessKeyId);
					final String secretKey = systemConfig.getConfigDecrypted(SystemSettingKey.AWS_SECRET_KEY);
					logger.info("secretKey", secretKey);
					final String region = systemConfig.getConfig(SystemSettingKey.AWS_EMAIL_REGION);
					
					// initial login
					AWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKey);
					// connect api client
					AmazonSimpleEmailServiceClientBuilder amazonSimpleEmailServiceClientBuilder = AmazonSimpleEmailServiceClientBuilder.standard();
					/*
					 * ClientConfiguration clientConfiguration = new ClientConfiguration();
					 * clientConfiguration.setConnectionTimeout(300000);
					 * clientConfiguration.setSocketTimeout(300000);
					 * clientConfiguration.setClientExecutionTimeout(1000000);
					 */
					client = amazonSimpleEmailServiceClientBuilder
							/* .withClientConfiguration(clientConfiguration) */.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
							.withRegion(region).build();
					//end connection
				}
				
				
				// get folder repository
				 final String urlAttach = systemConfig.getPhysicalPathById(repo != null? repo : "2", null);
				// initial variable
				 List<JcaAttachFileEmail> listFileAttach = new ArrayList<>();
				JcaEmail emailEntity = new JcaEmail();
				emailEntity.setSenderAddress(sender);
				SendEmailTypeEnum emailType = SendEmailTypeEnum.resolveByValue(sendEmailType);
				boolean sendEmailFlag = false;
				boolean saveEmailFlag = false;
				switch (emailType) {
				case SEND_DIRECT_NO_SAVE:
					sendEmailFlag = true;
					break;
				case SEND_BY_BATCH:
					saveEmailFlag = true;
					emailDto.setSendStatus(EmailStatusEnum.EMAIL_SAVED.getValue());
					break;
				case SEND_DIRECT_SAVE:
				default:
					sendEmailFlag = true;
					saveEmailFlag = true;
					break;
				}
				if (saveEmailFlag) {
					List<String> emailToList = emailDto.getToAddress();
					if (CommonCollectionUtil.isNotEmpty(emailToList)) {
						emailDto.setToString(emailToList.stream().filter(email -> email != null).collect(Collectors.joining(CommonConstant.COMMA)));
					}
					List<String> emailCcList = emailDto.getCcAddress();
					if (CommonCollectionUtil.isNotEmpty(emailCcList)) {
						emailDto.setCcString(emailCcList.stream().filter(email -> email != null).collect(Collectors.joining(CommonConstant.COMMA)));
					}
					List<String> emailBccList = emailDto.getBccAddress();
					if (CommonCollectionUtil.isNotEmpty(emailBccList)) {
						emailDto.setBccString(emailBccList.stream().filter(email -> email != null).collect(Collectors.joining(CommonConstant.COMMA)));
					}
				}
				// type send mail send direct and save
				if (sendEmailType.equals(constDirSave.getKind())) {
					// save email JcaConstantDto typeSave = jcaConstantService
					JcaConstantDto constd = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind("sendemail.status.sending", CommonConstant.SEND_EMAIL_STATUS
							,"2", "EN");
					emailDto.setStatusSendMail(constd.getKind());
					CommonNullAwareBeanUtil.copyPropertiesWONull(emailDto, emailEntity);
					if (emailDto.getEmailId() != null) {
						emailEntity.setId(emailDto.getEmailId());
						jcaEmailRepository.save(emailEntity);
					} else {
						jcaEmailRepository.save(emailEntity);
						emailDto.setEmailId(emailEntity.getId());
					}
					// update emailId to tb attach_file
					if (emailDto.getNumberAttachFile() != null && emailDto.getNumberAttachFile() > 0) {
						jcaEmailRepository.updateEmailId(emailEntity.getId(), emailDto.getUuidEmail());
						listFileAttach = jcaEmailRepository.getAttachFileWithEmailId(emailEntity.getId());
					}
				} else {
					// type send mail send direct not save
					listFileAttach = jcaEmailRepository.getAttachFileWithEmailId(emailDto.getEmailId());
					if (listFileAttach.size() > 0) {
						String emailUuid = listFileAttach.get(0).getUuidEmail();
						emailDto.setUuidEmail(emailUuid);
					}
				}
				Session session = Session.getDefaultInstance(new Properties());
				MimeMessage message = new MimeMessage(session);
				// set message headers
				message.addHeader("Content-type", "text/HTML; charset=UTF-8");
				message.addHeader("format", "flowed");
				message.addHeader("Content-Transfer-Encoding", "8bit");

				message.setFrom(new InternetAddress(sender));

				message.setSubject(emailDto.getSubject(), "UTF-8");
				if (emailDto.getBccString() != null && !("").equals(emailDto.getBccString())) {
					message.setRecipients(RecipientType.BCC, InternetAddress.parse(emailDto.getBccString()));
				}
				if (emailDto.getCcString() != null && !("").equals(emailDto.getCcString())) {
					message.setRecipients(RecipientType.CC, InternetAddress.parse(emailDto.getCcString()));
				}
				// Create the message part
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(emailDto.getEmailContent() != null ? emailDto.getEmailContent() : "", "text/html; charset=utf-8");

				// Create a multipar message
				Multipart multipart = new MimeMultipart();

				// Set text message part
				multipart.addBodyPart(messageBodyPart);
				// Part two is attachment
				if (listFileAttach.size() > 0) {
					for (JcaAttachFileEmail attachFile : listFileAttach) {
						// String path = urlAttach + "//" + emailDto.getUuidEmail() + "//" + attachFile.getFileNameUuid();
						Path pathUrl = Paths.get(urlAttach, emailDto.getUuidEmail(), attachFile.getFileName());
						String path = pathUrl.toString();

						logger.error("##EmailJobDto##", urlAttach, emailDto.getUuidEmail(), attachFile.getFileName());
						addAttachment(multipart, path, attachFile.getFileName());
					}
				}
				message.setContent(multipart);
				message.setSentDate(new Date());
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDto.getToString(), true));


				// write data to stream
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				message.writeTo(outputStream);
				// send request
				SendRawEmailRequest request = new SendRawEmailRequest().withRawMessage(new RawMessage(ByteBuffer.wrap(outputStream.toByteArray())));
				logger.error("Send Email Raw");
				client.sendRawEmail(request);

				JcaConstantDto typeCancel = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind("sendemail.status.cancel", CommonConstant.SEND_EMAIL_STATUS
						,"4", "EN");
				if (sendEmailType.equals(constDirSave.getKind()) || emailDto.getStatusSendMail().equals(typeCancel.getKind())) {
					// update status send mail
					JcaEmail emailUpdateStatus = new JcaEmail();

					
					emailUpdateStatus = jcaEmailRepository.findOne(emailEntity.getId());
					emailUpdateStatus.setSendDate(new Date());
					emailUpdateStatus.setSenderAddress(sender);
					logger.error("save email");
					jcaEmailRepository.save(emailUpdateStatus);
				}
			}
			JcaConstantDto constd = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind("sendemail.status.saved", CommonConstant.SEND_EMAIL_STATUS
					,"-1", "EN");
			if (sendEmailType.equals(constDirSave.getKind())) {
				// update status send mail with type send direct and save
				JcaEmail emailEntity = new JcaEmail();
				emailEntity = jcaEmailRepository.findOne(emailDto.getEmailId());
				emailEntity.setSendStatus(EmailStatusEnum.EMAIL_SEND_SUCCESS.getValue());
				jcaEmailRepository.save(emailEntity);
			}
			response.setStatusCode(constd.getKind());
			response.setSendStatus(constd.getName());
			response.setStatus(true);
		} catch (Exception e) {
			logger.error("##EmailJobDto##", e);
			JcaConstantDto constd = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind("sendemail.status.failed", CommonConstant.SEND_EMAIL_STATUS
					,"0", "EN");
			response.setStatusCode(constd.getKind());
			response.setSendStatus(constd.getName());
			response.setStatus(false);
			if(e.getClass().getSimpleName().equalsIgnoreCase("SdkClientException")){
				throw new Exception(e.getMessage());
			}
		}
		return response;
	}

	@SuppressWarnings("unused")
	private static void addAttachment(Multipart multipart, String path, String filename) throws MessagingException {
		DataSource source = new FileDataSource(path);
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(filename);
		multipart.addBodyPart(messageBodyPart);
	}

}
