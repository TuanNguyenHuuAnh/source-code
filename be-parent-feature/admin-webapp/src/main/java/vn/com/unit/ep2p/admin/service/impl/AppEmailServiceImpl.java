/*******************************************************************************
 * Class        MailServiceImpl
 * Created date 2017/05/18
 * Lasted date  2017/05/18
 * Author       phunghn
 * Change log   2017/05/1801-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.AttachFileEmailDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailSearchDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaAttachFileEmail;
import vn.com.unit.core.entity.JcaEmail;
import vn.com.unit.core.entity.JcaEmailTemplate;
import vn.com.unit.core.enumdef.SendEmailTypeEnum;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.core.dto.FileResultDto;
import vn.com.unit.ep2p.admin.dto.ResponseEmailDto;
import vn.com.unit.ep2p.admin.repository.AccountRepository;
import vn.com.unit.ep2p.admin.repository.AttachFileEmailRepository;
import vn.com.unit.ep2p.admin.repository.EmailRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.AppEmailService;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.BeAdminFileService;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.ep2p.admin.service.TemplateService;
import vn.com.unit.ep2p.admin.utils.JCanaryPasswordUtil;
import vn.com.unit.ep2p.constant.CommonConstant;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.ep2p.dto.EmailDto;
import vn.com.unit.ep2p.dto.EmailSearchDto;
import vn.com.unit.ep2p.dto.TemplateAttachDto;
import vn.com.unit.ep2p.dto.TemplateEmailDto;
import vn.com.unit.ep2p.enumdef.EmailSearchEnum;
import vn.com.unit.ep2p.utils.FileUtil;

/**
 * MailServiceImpl
 * 
 * TODO TrieuVd update code attach file email remove Jcanary
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppEmailServiceImpl implements AppEmailService, AbstractCommonService {

    private static final Logger logger = LoggerFactory.getLogger(AppEmailServiceImpl.class);

    @Autowired
    private JcaEmailService jcaEmailService;

    /*
     * mailSender for config in mail-context.xml
     */
//  @Autowired
//  JavaMailSender mailSender;
//    
//    @Resource(name = "freemarkerConfig")
//    private FreeMarkerConfigurer freemarkerConfig;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private AttachFileEmailRepository attachFileEmailRepository;

    @Autowired
    private JcaConstantService jcaConstantService;

    @Autowired
    private CommonService comService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    CompanyService companyService;

    @Autowired
    private BeAdminFileService fileService;

    @Autowired
    JRepositoryService jrepositoryService;

//    @Autowired
//    private MessageSource msg;

    @Autowired
    private AccountRepository accRepository;

//    @Autowired
//    private LanguageService languageService;

//    @Autowired
//    private TemplateParameterRepository templateParameterRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Model mapper
    ModelMapper modelMapper = new ModelMapper();

//    /** Constant param for template */
//    private static final String ATTACH_FILE_BIG = "The total attachment size is larger than specified";
//
//    // private static final String DATE_FORMAT = "dd/MM/yyyy";
//
//    private static final String REGEX_PARAM = "\\$\\{(.*?)\\}";

    /**
     * 
     * @return JavaMailSender
     */
    @SuppressWarnings("unused")
    private JavaMailSender configHostMail() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(systemConfig.getConfig(SystemConfig.EMAIL_HOST));
        mailSender.setPort(systemConfig.getIntConfig(SystemConfig.EMAIL_PORT));
        mailSender.setUsername(systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT));
        mailSender.setPassword(systemConfig.getConfigDecrypted(SystemConfig.EMAIL_PASSWORD));

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    /**
     * 
     * @param mail
     */
    /*
     * public void sendEmail(EmailDto mail) { JavaMailSender mailSender =
     * configHostMail(); MimeMessage mimeMessage = mailSender.createMimeMessage();
     * try { MimeMessageHelper mimeMessageHelper = new
     * MimeMessageHelper(mimeMessage, true, "UTF-8");
     *//**
        * Q => Quoted Printable for ASCII B => encode by Base64
        *//*
           * mimeMessageHelper.setSubject(MimeUtility.encodeText(mail.getSubject(),
           * "UTF-8","Q"));
           * mimeMessageHelper.setFrom(systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT))
           * ; mimeMessageHelper.setTo(mail.getReceiveAddress());
           * mail.setHtmlTemplate(geContentFromTemplate(mail.getData(),mail.
           * getTemplateFile())); mimeMessageHelper.setText(mail.getHtmlTemplate(), true);
           * 
           * mailSender.send(mimeMessageHelper.getMimeMessage()); } catch (Exception e) {
           * e.printStackTrace(); } }
           */

    @Override
    public void saveAttachFileEmail(JcaAttachFileEmail attachFile) {
        if (ObjectUtils.isNotEmpty(attachFile.getId()))
            attachFileEmailRepository.update(attachFile);
        else
            attachFileEmailRepository.create(attachFile);
    }

    @Override
    public List<JcaEmail> findAllEmail() {
        return (List<JcaEmail>) emailRepository.findAll();
    }

    @Override
    @Transactional
    public PageWrapper<JcaEmailDto> doSearch(int page, EmailSearchDto searchDto, int pageSize) throws DetailException {
        // Get listPageSize, sizeOfPage
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<JcaEmailDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        if (null == searchDto) {
            searchDto = new EmailSearchDto();
        }
        // set SearchParm
//        setSearchParm(searchDto);
        pageWrapper.setListPageSize(listPageSize);
        pageWrapper.setSizeOfPage(sizeOfPage);

        /** init pageable */
        Pageable pageableAfterBuild = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), JcaAccount.class,
                JcaEmailService.TABLE_ALIAS_JCA_EMAIL);

        /** init param search repository */
        MultiValueMap<String, String> commonSearch = CommonUtil.convert(searchDto, objectMapper);
        JcaEmailSearchDto reqSearch = this.buildJcaEmailSearchDto(commonSearch);

        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        if (searchDto.getFromDate() != null) {
            reqSearch.setFromDate(format.format(searchDto.getFromDate()));
        }
        
        if (searchDto.getToDate() != null) {
            reqSearch.setToDate(format.format(searchDto.getToDate()));
        }
        
//        int count = emailRepository.countEmailByCondition(searchDto);
        int count = jcaEmailService.countJcaEmailDtoBySearchDto(reqSearch);
        List<JcaEmailDto> result = new ArrayList<>();
        if (count > 0) {
//            int currentPage = pageWrapper.getCurrentPage();
//            int startIndex = (currentPage - 1) * sizeOfPage;
//            result = emailRepository.findAllEmailListByCondition(startIndex, sizeOfPage, searchDto);
            result = jcaEmailService.getJcaEmailDtoListBySearchDto(reqSearch, pageableAfterBuild);
        }
        // TODO get list file attach
//        for(JcaEmailDto email : result){
//            String content = StringUtils.EMPTY;
//            if(StringUtils.isNotBlank(email.getEmailContent())) {
//                content = email.getEmailContent().replaceAll("\\<[^>]*>","");
//            }
//            email.setEmailContent(content);
//            List<AttachFileEmail> listAttach = attachFileEmailRepository.getAttachFileWithEmailId(email.getEmailId());
//            if(listAttach.size() > 0){
//                email.setListAttach(listAttach);
//            }
//        }

        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

    private JcaEmailSearchDto buildJcaEmailSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaEmailSearchDto reqSearch = new JcaEmailSearchDto();
        String keySearch = CommonStringUtil.isNotBlank(commonSearch.getFirst("fieldSearch"))
                ? commonSearch.getFirst("fieldSearch")
                : DtsConstant.EMPTY;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("fieldValues"))
                ? java.util.Arrays.asList(CommonStringUtil.split(commonSearch.getFirst("fieldValues"), ","))
                : null;

      Long companyId = CommonStringUtil.isNotBlank(commonSearch.getFirst("companyId")) ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
      String fromDate = CommonStringUtil.isNotBlank(commonSearch.getFirst("fromDate")) ? commonSearch.getFirst("fromDate") : null;
      String toDate = CommonStringUtil.isNotBlank(commonSearch.getFirst("toDate")) ? commonSearch.getFirst("toDate") : null;
      String sendStatus = CommonStringUtil.isNotBlank(commonSearch.getFirst("sendStatus")) ? commonSearch.getFirst("sendStatus"): DtsConstant.EMPTY;        

//      DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
//      DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
//		if (fromDate != null) {
//			LocalDate dateFrom = LocalDate.parse(fromDate, inputFormatter);
//			String formattedDateFrom = outputFormatter.format(dateFrom);
//			reqSearch.setFromDate(formattedDateFrom);
//		}
//		if (toDate != null) {
//			LocalDate dateTo = LocalDate.parse(toDate, inputFormatter);
//			String formattedDateTo = outputFormatter.format(dateTo);
//			reqSearch.setToDate(formattedDateTo);
//		}
      	if (fromDate != null) {
			reqSearch.setFromDate(fromDate.substring(0, 10));
		}
		if (toDate != null) {
			reqSearch.setToDate(toDate.substring(0, 10));
		}
		reqSearch.setCompanyId(companyId);
		reqSearch.setSendStatus(sendStatus);

        if (CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumsValue : enumsValues) {
                switch (EmailSearchEnum.valueOf(enumsValue)) {
                case SENDER:
                    reqSearch.setSenderAddress(keySearch);
                    break;
                case RECEIVE:
                    reqSearch.setReceiveAddress(keySearch);
                    break;
                case SUBJECT:
                    reqSearch.setSubject(keySearch);
                    break;
                case CONTENT:
                    reqSearch.setContentEmail(keySearch);
                    break;
                default:
                    reqSearch.setSenderAddress(keySearch);
                    reqSearch.setReceiveAddress(keySearch);
                    reqSearch.setSubject(keySearch);
                    reqSearch.setContentEmail(keySearch);
                    break;
                }
            }
        } else {
            reqSearch.setSenderAddress(keySearch);
            reqSearch.setReceiveAddress(keySearch);
            reqSearch.setSubject(keySearch);
            reqSearch.setContentEmail(keySearch);
        }
        return reqSearch;
    }

//    private void setSearchParm(EmailSearchDto searchDto) {
//        if (null == searchDto.getFieldValues()) {
//            searchDto.setFieldValues(new ArrayList<String>());
//        }
//
//        if (searchDto.getFieldValues().isEmpty()) {
//            searchDto.setReceiveAddress(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim()
//                    : searchDto.getFieldSearch());
//            searchDto.setSenderAddress(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim()
//                    : searchDto.getFieldSearch());
//            searchDto.setSubject(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim()
//                    : searchDto.getFieldSearch());
//            searchDto.setContent(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim()
//                    : searchDto.getFieldSearch());
//        } else {
//            for (String field : searchDto.getFieldValues()) {
//                if (StringUtils.equals(field, EmailSearchEnum.RECEIVE.name())) {
//                    searchDto.setReceiveAddress(searchDto.getFieldSearch().trim());
//                    continue;
//                }
//           /*    if (StringUtils.equals(field, EmailSearchEnum.IMMEDIATELY.name())) {
//                    searchDto.setImmediately(Integer.parseInt(searchDto.getFieldSearch()));
//                    continue;
//                }*/
//                if (StringUtils.equals(field, EmailSearchEnum.SENDER.name())) {
//                    searchDto.setSenderAddress(searchDto.getFieldSearch().trim());
//                    continue;
//                }
//                if (StringUtils.equals(field, EmailSearchEnum.SUBJECT.name())) {
//                    searchDto.setSubject(searchDto.getFieldSearch().trim());
//                    continue;
//                }
//                if (StringUtils.equals(field, EmailSearchEnum.CONTENT.name())) {
//                    searchDto.setContent(searchDto.getFieldSearch().trim());
//                    continue;
//                }
//            }
//        }
//        
////        searchDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
////        searchDto.setCompanyIdList(UserProfileUtils.getCompanyIdList());
//    }

    @Override
    public JcaEmailDto getDetailEmail(Long emailId) throws Exception {
//    	JcaEmail emailEntity = emailRepository.findOne(emailId);
//        EmailDto emailDto = new EmailDto();
//        NullAwareBeanUtils.copyPropertiesWONull(emailEntity, emailDto);
//        emailDto.setEmailId(emailId);
//        //get list file Attach
//        List<AttachFileEmail> listAttach = attachFileEmailRepository.getAttachFileWithEmailId(emailId);
//        emailDto.setNumberAttachFile(listAttach.size());
//        if(listAttach.size() > 0 ){
//            emailDto.setListAttach(listAttach);
//        }
//        return emailDto;
        return jcaEmailService.getJcaEmailDtoById(emailId);
    }

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public ResponseEmailDto sendEmail(EmailDto emailDto) {
        ResponseEmailDto response = new ResponseEmailDto();
        Long companyId = null == emailDto.getCompanyId() ? UserProfileUtils.getCompanyId() : emailDto.getCompanyId();
        // company null -> không send mail
        if (companyId == null) {
            String error = "[Send Email false] companyId is null";
            logger.error(error);
            JcaConstantDto constd = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(
                    CommonConstant.EMAIL_SEND_FAIL, CommonConstant.GROUP_JCA_APP_SLA,
                    CommonConstant.KIND_SLA_SEND_STATUS, UserProfileUtils.getLanguage());
            response.setStatus(constd.getName());
            response.setStatusCode(constd.getCode());
            response.setMessage(error);

            return response;
        }

        // Setup attachFile
        if (null != emailDto.getAttachFileId() && null == emailDto.getEmailId()) {
//            for (Long id : emailDto.getAttachFileId()) {
//                JcaAttachFileEmail attachFileEmail = attachFileEmailRepository.findOne(id);
//                if(null!= attachFileEmail && attachFileEmail.getEmailId()!=0) {
//                    attachFileEmail.setEmailId(0);
//                    attachFileEmail.setId(null);
//                    attachFileEmailRepository.save(attachFileEmail);
//                }
//            }
        }

        try {
            emailDto.setSendDate(comService.getSystemDateTime());
            // Setup to, cc, bcc
            String emailTo = getEmails(emailDto.getToString());
            String emailCC = getEmails(emailDto.getCcString());
            String emailBCC = getEmails(emailDto.getBccString());

            String sendEmailTypeDefault = systemConfig.getConfig(SystemConfig.SEND_EMAIL_TYPE_DEFAULT, companyId);
            JcaConstantDto constBatch = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(
                    CommonConstant.SEND_BY_BATCH, CommonConstant.GROUP_JCA_APP_SLA, CommonConstant.KIND_SLA_SEND_TYPE,
                    UserProfileUtils.getLanguage());
            JcaConstantDto constDirSave = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(
                    CommonConstant.SEND_DIRECT_SAVE, CommonConstant.GROUP_JCA_APP_SLA,
                    CommonConstant.KIND_SLA_SEND_TYPE, UserProfileUtils.getLanguage());
            // ConstantDisplay constDirNoSave =
            // constantDisplayService.findTypeAndKind(ConstDispType.SENDEMAILOPT.name(),
            // CommonConstant.SEND_DIRECT_NO_SAVE);
            JcaConstantDto typeSave = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(
                    CommonConstant.EMAIL_SAVED, CommonConstant.GROUP_JCA_APP_SLA, CommonConstant.KIND_SLA_SEND_STATUS,
                    UserProfileUtils.getLanguage());

            // default type send mail
            String sendEmailType = emailDto.getSendEmailType() != null ? emailDto.getSendEmailType() : null;
            if (sendEmailType == null) {
                sendEmailType = sendEmailTypeDefault;
            }
            // default status send mail
            if (emailDto.getStatusSendMail() == null) {

                emailDto.setStatusSendMail(typeSave.getCode());
            }
            // type send mail by batch
            if (sendEmailType.equals(constBatch.getCode())) {
                logger.debug("send mail by batch " + emailDto.toString());
                JcaConstantDto typeResend = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(
                        CommonConstant.EMAIL_RESEND, CommonConstant.GROUP_JCA_APP_SLA,
                        CommonConstant.KIND_SLA_SEND_STATUS, UserProfileUtils.getLanguage());
                JcaConstantDto typeCancel = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(
                        CommonConstant.EMAIL_CANCEL, CommonConstant.GROUP_JCA_APP_SLA,
                        CommonConstant.KIND_SLA_SEND_STATUS, UserProfileUtils.getLanguage());
                // status send mail
                if (emailDto.getStatusSendMail() != null && emailDto.getStatusSendMail().equals(typeResend.getCode())) {
                    emailDto.setStatusSendMail(typeResend.getCode());
                } else if (emailDto.getStatusSendMail() != null
                        && emailDto.getStatusSendMail().equals(typeCancel.getCode())) {
                    emailDto.setStatusSendMail(typeCancel.getCode());
                } else {
                    JcaConstantDto constd = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(
                            CommonConstant.EMAIL_SAVED, CommonConstant.GROUP_JCA_APP_SLA,
                            CommonConstant.KIND_SLA_SEND_STATUS, UserProfileUtils.getLanguage());
                    emailDto.setStatusSendMail(constd.getCode());
                }
                // save email
                JcaEmail emailEntity = new JcaEmail();
                CommonObjectUtil.copyPropertiesNonNull(emailDto, emailEntity);
                emailEntity.setId(emailDto.getEmailId());
                emailEntity.setCompanyId(companyId);
                emailEntity.setSendDate(comService.getSystemDateTime());
                this.save(emailEntity);

                if (emailDto.getNumberAttachFile() > 0) {
                    // save emailId to tb attachFile
                    attachFileEmailRepository.updateEmailId(emailEntity.getId(), emailDto.getUuidEmail(), companyId);
                }
            } else {
                // config mail
                final String emailHost = systemConfig.getConfig(SystemConfig.EMAIL_HOST, companyId);
                final int emailPort = Integer.parseInt(systemConfig.getConfig(SystemConfig.EMAIL_PORT, companyId));
                final String passMail = JCanaryPasswordUtil
                        .decryptString(systemConfig.getConfig(SystemConfig.EMAIL_PASSWORD, companyId));
                final String senderAddress = systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT, companyId);
                if (emailDto.getSenderAddress() == null || emailDto.getPswEmail() == null) {
                    emailDto.setSenderAddress(senderAddress);
                    emailDto.setPswEmail(passMail);
                }
                emailDto.setReceiveAddress(emailDto.getReceiveAddress());

                Properties props = new Properties();
                props.put("mail.smtp.host", emailHost); // SMTP Host
                props.put("mail.smtp.port", emailPort); // TLS Port
                props.put("mail.smtp.auth", "true"); // enable authentication
                props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS
                props.put("mail.smtp.ssl.trust", "*");
                // props.put("mail.debug", "true");

                // KhoaNA - 20200416 - The attached file name is converted to .dat in outlook if
                // long name above 60
                System.setProperty("mail.mime.encodeparameters", "false");

                // create Authenticator object to pass in Session.getInstance argument
                Authenticator auth = new Authenticator() {
                    // override the getPasswordAuthentication method
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailDto.getSenderAddress(), emailDto.getPswEmail());
                    }
                };
                List<JcaAttachFileEmail> listFileAttach = new ArrayList<>();
                JcaEmail emailEntity = new JcaEmail();
                // type send mail send direct and save
                if (sendEmailType.equals(constDirSave.getCode())) {
                    logger.debug("send mail send direct and save " + emailDto.toString());
                    // save email
                    JcaConstantDto constd = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(
                            CommonConstant.EMAIL_SENDING, CommonConstant.GROUP_JCA_APP_SLA,
                            CommonConstant.KIND_SLA_SEND_STATUS, UserProfileUtils.getLanguage());
                    emailDto.setStatusSendMail(constd.getCode());
                    CommonObjectUtil.copyPropertiesNonNull(emailDto, emailEntity);
                    if (emailDto.getEmailId() != null) {
                        emailEntity.setId(emailDto.getEmailId());
                        emailEntity.setCompanyId(companyId);
                        this.save(emailEntity);
                    } else {
                        emailEntity.setCompanyId(companyId);
                        this.save(emailEntity);
                        emailDto.setEmailId(emailEntity.getId());
                    }
                    // update emailId to tb attach_file
                    if (emailDto.getNumberAttachFile() > 0) {
                        attachFileEmailRepository.updateEmailId(emailEntity.getId(), emailDto.getUuidEmail(),
                                companyId);
                        listFileAttach = attachFileEmailRepository.getAttachFileWithEmailId(emailEntity.getId());
                    }
                } else {
                    logger.debug("send mail send direct and no save" + emailDto.toString());
                    // type send mail send direct not save
                    // listFileAttach =
                    // attachFileEmailRepository.getAttachFileWithEmailUUID(emailDto.getUuidEmail());
                    if (null != emailDto.getListAttach() && !emailDto.getListAttach().isEmpty()) {
                        listFileAttach.addAll(emailDto.getListAttach());
                    } else {
                        listFileAttach = attachFileEmailRepository.getAttachFileWithEmailUUID(emailDto.getUuidEmail());
                    }
                    /*
                     * if(listFileAttach.size() > 0){ String emailUuid =
                     * listFileAttach.get(0).getUuidEmail(); emailDto.setUuidEmail(emailUuid); }
                     */
                }
                // do send mail
                MimeMessage msg = new MimeMessage(Session.getInstance(props, auth));
                // set message headers
                msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                msg.addHeader("format", "flowed");
                msg.addHeader("Content-Transfer-Encoding", "8bit");

                msg.setFrom(new InternetAddress(systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT, companyId)));

                // msg.setReplyTo(InternetAddress.parse("vtphat91@gmail.com",false));

                // ---TRIEUVD - 20200828 FIX SUBJECT 255
                String subject = emailDto.getSubject();
                if (StringUtils.isNotBlank(subject) && subject.length() > 255) {
                    subject = subject.substring(0, 252).concat(ConstantCore.ELLIPSIS);
                }
                msg.setSubject(subject, "UTF-8");
                // --- END TRIEUVD - 20200828 FIX SUBJECT 255

                /*
                 * if(emailDto.getBccString() != null && !("").equals(emailDto.getBccString())){
                 * msg.setRecipients(RecipientType.BCC,
                 * InternetAddress.parse(emailDto.getBccString())); } if(emailDto.getCcString()
                 * != null && !("").equals(emailDto.getCcString())){
                 * msg.setRecipients(RecipientType.CC,
                 * InternetAddress.parse(emailDto.getCcString())); }
                 */

                if (StringUtils.isNotBlank(emailBCC)) {
                    msg.setRecipients(RecipientType.BCC, InternetAddress.parse(emailTo));
                }
                if (StringUtils.isNotBlank(emailCC)) {
                    msg.setRecipients(RecipientType.CC, InternetAddress.parse(emailCC));
                }

                /*
                 * // Create the message part BodyPart messageBodyPart = new MimeBodyPart();
                 * messageBodyPart.setContent(emailDto.getEmailContent() != null ?
                 * emailDto.getEmailContent() : "", "text/html; charset=utf-8");
                 * 
                 * // Create a multipar message Multipart multipart = new MimeMultipart();
                 * 
                 * // Set text message part multipart.addBodyPart(messageBodyPart);
                 * 
                 */

                // fix img in outlook
                Multipart multipart = setMultipart(
                        emailDto.getEmailContent() != null ? emailDto.getEmailContent() : "");

//                CompanyDto company = companyService.findById(companyId);
                // Part two is attachment
                if(listFileAttach.size() > 0){
//                    //check total size attachment
//                    Long totalSize = attachFileEmailRepository.sumAttachFileSizeByCondition(listFileAttach.get(0).getUuidEmail(), 0L);
//                    if(totalSize>ConstantCore.ATTACH_FILE_SIZE_BYTE) {
//                        //zip file
//                        
//                        //thow error
//                        throw new Exception(ATTACH_FILE_BIG);
//                    }
//                    
                    for(JcaAttachFileEmail attachFile : listFileAttach){
                        //String urlAttach = systemConfig.getPhysicalPathById(attachFile.getRepositoryId().toString(), null);
//                        Path pathUrl = Paths.get(ConstantCore.EMAIL_ATTACH_FOLDER, company.getSystemCode(), attachFile.getUuidEmail() , attachFile.getFileNameUuid());
                        Path pathUrl = Paths.get(attachFile.getFilePath());
                        String path = pathUrl.toString();
                        addAttachment(multipart, path, attachFile.getFileName(), attachFile.getRepositoryId());
                    }
                }
                msg.setContent(multipart);
                msg.setSentDate(comService.getSystemDateTime());
                // msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailDto.getReceiveAddress(),
                // true));
                if (StringUtils.isNotBlank(emailTo)) {
                    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo, true));
                } else {
                    msg.setRecipients(Message.RecipientType.TO, InternetAddress
                            .parse(systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT, companyId), true));
                }
                logger.debug("Email Before Send: " + emailDto.toString());
                Transport.send(msg);
                JcaConstantDto typeCancel = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(
                        CommonConstant.EMAIL_CANCEL, CommonConstant.GROUP_JCA_APP_SLA,
                        CommonConstant.KIND_SLA_SEND_STATUS, UserProfileUtils.getLanguage());
                if (sendEmailType.equals(constDirSave.getCode())
                        || emailDto.getStatusSendMail().equals(typeCancel.getCode())) {
                    // update status send mail
                    JcaEmail emailUpdateStatus = new JcaEmail();
                    emailUpdateStatus = emailRepository.findOne(emailEntity.getId());
                    emailUpdateStatus.setSendDate(comService.getSystemDateTime());
                    this.save(emailUpdateStatus);
                }
            }
            JcaConstantDto constd = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(
                    CommonConstant.EMAIL_SEND_SUCCESS, CommonConstant.GROUP_JCA_APP_SLA,
                    CommonConstant.KIND_SLA_SEND_STATUS, UserProfileUtils.getLanguage());
            if (sendEmailType.equals(constDirSave.getCode())) {
                // update status send mail with type send direct and save
                JcaEmail emailEntity = new JcaEmail();
                emailEntity = emailRepository.findOne(emailDto.getEmailId());
                emailEntity.setSendStatus(constd.getCode());
                this.save(emailEntity);
            }
            response.setStatusCode(constd.getCode());
            response.setStatus(constd.getName());

        } catch (Exception e) {
            logger.error("Error send Emai: ", e);
            JcaConstantDto constd = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(
                    CommonConstant.EMAIL_SEND_FAIL, CommonConstant.GROUP_JCA_APP_SLA,
                    CommonConstant.KIND_SLA_SEND_STATUS, UserProfileUtils.getLanguage());
            response.setStatus(constd.getName());
            response.setStatusCode(constd.getCode());
            response.setMessage(e.getMessage());

            JcaEmail emailEntity = new JcaEmail();
            emailEntity = emailRepository.findOne(emailDto.getEmailId());
            if (emailEntity != null) {
                emailEntity.setSendStatus(constd.getCode());
                this.save(emailEntity);
            }
        }
        return response;
    }

    private void addAttachment(Multipart multipart, String path, String filename, Long repositoryId)
            throws MessagingException, IOException {
        InputStream inputStream = fileService.getInputStreamByRepositoryIdAndFilePath(repositoryId, path);
        DataSource source = new ByteArrayDataSource(inputStream, FileUtil.getContentType(filename));
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);
    }

    public List<JcaConstantDto> getOptionSend() {
        return jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(CommonConstant.GROUP_JCA_APP_SLA,
                CommonConstant.KIND_SLA_SEND_TYPE, UserProfileUtils.getLanguage());
    }

    @Override
    public List<JcaAttachFileEmail> getListFileAttach(Long emailId) {
        return attachFileEmailRepository.getAttachFileWithEmailId(emailId);
    }

    @Override
    public List<JcaEmail> getListEmail(List<String> listStatus) {
        return emailRepository.findAllEmailByStatus(listStatus);
    }

    @Override
    public String getContentFromTemplate(Map<String, Object> model, String templateFile) {
        String content = new String();

        try {
//			content = FreeMarkerTemplateUtils.processTemplateIntoString(
//					freemarkerConfig.getConfiguration().getTemplate(templateFile, "UTF-8"), model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    // Get all params in template file - BaoHG - 20190703
    public List<String> getParamsByTemplate(String template) {
        List<String> paramList = new ArrayList<String>();

        Matcher matcher = ConstantCore.PARAM_PATTERN.matcher(template);
        while (matcher.find()) {
            String param = matcher.group(1);
            paramList.add(param);
        }
        return paramList;
    }

    /*
     * // Get content email by object - BaoHG - 20190703 // classObject defined the
     * class for the itemObjectDto public String getContentEmailByObject(Template
     * template, Class<T> classObject, Object itemObjectDto) throws
     * InstantiationException, IllegalAccessException, NoSuchFieldException,
     * SecurityException { // content Template String content =
     * template.getTemplateContent(); // get all param in content List<String>
     * params = getParamsByTemplate(content); // get list fields of class object T
     * objDefault = (T) classObject.newInstance(); Class<?> cls =
     * objDefault.getClass(); Field[] fields = cls.getDeclaredFields(); // put value
     * Object to Map for (Field f : fields) { Field field =
     * itemObjectDto.getClass().getDeclaredField(f.getName().toString());
     * field.setAccessible(true); Object value = field.get(itemObjectDto); for
     * (String contentParam : params) {
     * if(contentParam.contains(f.getName().toString())) { contentParam =
     * "${".concat(contentParam).concat("}"); content =
     * content.replace(contentParam, value.toString()); } } } return content; }
     * 
     * @Override
     * 
     * @Transactional public ResponseEmailDto sendMailByTemplateId(Long templateId,
     * EmailDto emailDto, Class<T> classObject, Object itemObjectDto) throws
     * Exception { ResponseEmailDto response = new ResponseEmailDto(); try { String
     * emailTo = emailDto.getReceiveAddress(); String emailCc =
     * emailDto.getCcString(); String emailBcc = emailDto.getBccString(); String
     * subjectName = emailDto.getSubject();
     * 
     * Email email = new Email(); Template template =
     * templateService.getTemplateById(templateId);
     * emailDto.setSenderAddress(systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT))
     * ; emailDto.setReceiveAddress(emailTo); emailDto.setCcString(emailCc);
     * emailDto.setBccString(emailBcc);
     * emailDto.setSenderName(systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT_NAME
     * )); emailDto.setSubject(subjectName); String content =
     * getContentEmailByObject(template, classObject, itemObjectDto);
     * emailDto.setEmailContent(content);
     * 
     * response = sendEmail(emailDto);
     * NullAwareBeanUtils.copyPropertiesWONull(emailDto, email); ConstantDisplay
     * constd = constantDisplayService.findByTypeAndCatOfficial(CommonConstant.
     * SEND_EMAIL_STATUS, CommonConstant.EMAIL_SEND_SUCCESS);
     * 
     * //update status send mail with type send direct and save email =
     * emailRepository.findOne(emailDto.getEmailId());
     * email.setStatusSendMail(constd.getCat());
     * response.setStatusCode(constd.getCat());
     * response.setStatus(constd.getCatAbbrName()); emailRepository.save(email);
     * }catch(Exception e){ logger.error("Error : ", e); ConstantDisplay constd =
     * constantDisplayService.findByTypeAndCatOfficial(CommonConstant.
     * SEND_EMAIL_STATUS, CommonConstant.EMAIL_SEND_FAIL);
     * response.setStatus(constd.getCatOfficialName());
     * response.setStatusCode(constd.getCat()); response.setMessage(e.toString()); }
     * return response; }
     */

    @Override
    public Long totalSizeAttach(String emailUuid) {
        return attachFileEmailRepository.sumAttachFileSizeByCondition(emailUuid, 0L);
    }

    @Override
    public void deleteAttachFileById(Long id) {
        if (id != null) {
            JcaAttachFileEmail attachFileEmail = attachFileEmailRepository.findOne(id);
            deleteAttachFile(attachFileEmail);
        }
    }

    @Override
    public void clearAttachFile(String emailUuid) {
        List<JcaAttachFileEmail> attachFileEmails = attachFileEmailRepository.getAttachFileWithEmailUUID(emailUuid);
        for (JcaAttachFileEmail attachFileEmail : attachFileEmails) {
            deleteAttachFile(attachFileEmail);
        }
    }

    public void deleteAttachFile(JcaAttachFileEmail attachFileEmail) {
//        if(null != attachFileEmail && attachFileEmail.getEmailId()==0) {
//            CompanyDto company = companyService.findById(attachFileEmail.getCompanyId());
//            Path pathUrl = Paths.get(ConstantCore.EMAIL_ATTACH_FOLDER, company.getSystemCode(), attachFileEmail.getUuidEmail() , attachFileEmail.getFileNameUuid());
//            String path = pathUrl.toString();
//            fileService.deleteFileByRepositoryIdAndFilePath(attachFileEmail.getRepositoryId(), path);
//            
//            attachFileEmailRepository.delete(attachFileEmail);
//        }
    }

    @Override
    public ResponseEmailDto sendMailByTemplateCodeAndJsonPram(String templateCode, EmailDto emailDto, String jsonParam,
            List<TemplateAttachDto> templateAttach, Locale locale, boolean isSendMail) throws Exception {
        Long companyId = null != emailDto.getCompanyId() ? emailDto.getCompanyId() : UserProfileUtils.getCompanyId();
        // get template
        JcaEmailTemplate template = templateService.getByCodeAndCompanyId(templateCode, companyId);
        if (template == null)
            throw new Exception("Can not found template");

        return sendMailByTemplateIdAndJsonPram(template.getId(), emailDto, jsonParam, templateAttach, locale,
                isSendMail);
    }

    @Override
    public ResponseEmailDto sendMailByTemplateIdAndJsonPram(Long templateId, EmailDto emailDto, String jsonParam,
            List<TemplateAttachDto> templateAttach, Locale locale, boolean isSendMail) throws Exception {
        // logger.info("#####Start sendMailByTemplateIdAndJsonPram######");
        Long companyId = null != emailDto.getCompanyId() ? emailDto.getCompanyId() : UserProfileUtils.getCompanyId();
        ResponseEmailDto response = new ResponseEmailDto();
        // check company
        if (companyId == null) {
            String error = "[Send Email false] companyId is null";
            logger.error(error);
            JcaConstantDto constd = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(
                    CommonConstant.EMAIL_SEND_FAIL, CommonConstant.GROUP_JCA_APP_SLA,
                    CommonConstant.KIND_SLA_SEND_STATUS, UserProfileUtils.getLanguage());
            response.setStatus(constd.getName());
            response.setStatusCode(constd.getCode());
            response.setMessage(error);
            return response;
        }

        UUID uUid = UUID.randomUUID();
        emailDto.setUuidEmail(uUid.toString());
        // get template
//        TemplateEmailDto template = templateService.getMapLangAndSubjectNotificationById(templateId);
        JcaEmailTemplate template = templateService.getTemplateById(templateId);
        
//        String lang = companyService.getLanguageById(companyId);
//        List<Language> languageList = languageService.findAllActive();
        
        if (template == null)
            throw new Exception("Can not found template");
        // subject
        if (null == emailDto.getSubject()) {
            // replace param subject
//            Map<String,String> subjectMap = template.getSubjectNotificationMap();

//            for (Language language: languageList){
//                String subject = replaceParam( subjectMap.get(language.getCode()), jsonParam, companyId);
//                subjectMap.put(language.getCode(), subject);
//            }
//            emailDto.setSubject(subjectMap.get(lang));
//            String subject = replaceParam(template.getSubject(), jsonParam);
//            emailDto.setSubject(subject);
//            emailDto.setSubjectNotificationMap(subjectMap);
        }

        // replace param content
        String content = template.getTemplateContent();
        String emailContent = replaceParam(content, jsonParam, companyId);
        emailDto.setEmailContent(emailContent);

        // replace param notification
//        Map<String,String> notificationEmailDtoMap = template.getContentNotificationMap();

//        for (Language language: languageList){
//            String notification = replaceParam( notificationEmailDtoMap.get(language.getCode()), jsonParam, companyId);
//            notificationEmailDtoMap.put(language.getCode(), notification);
//        }
////        String notification = replaceParam(template.getMobileNotification(), jsonParam);
////        emailDto.setMobileNotification(notification);
//        emailDto.setContentNotificationMap(notificationEmailDtoMap);;
        // Không send mail
        if (!isSendMail || CommonConstant.CONTENT_TEMPLATE_BLANK.equals(emailContent)) {
            String msg = null;
            if (!isSendMail) {
                msg = "isSendMail = false";
            } else {
                msg = "Content template blank";
            }
            logger.debug("[sendMailByTemplateIdAndJsonPram]" + msg);
            return null;
        }

        try {
            // attach file
            if (null != emailDto.getAttachments()) {
                emailDto.setNumberAttachFile(emailDto.getAttachments().size());
                saveListAttachFile(emailDto.getAttachments(), emailDto.getUuidEmail(), companyId, locale);
            }
            // template attach
            if (null != templateAttach) {
                for (TemplateAttachDto templateAttachDto : templateAttach) {
                    templateToPdfAttach(templateAttachDto.getTemplateId(), uUid.toString(),
                            templateAttachDto.getFileName(), companyId, jsonParam, locale);
                }
            }
        } catch (Exception e) {
            String error = e.getMessage();
            logger.error("Error : ", error);
            JcaConstantDto constd = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(
                    CommonConstant.EMAIL_SEND_FAIL, CommonConstant.GROUP_JCA_APP_SLA,
                    CommonConstant.KIND_SLA_SEND_STATUS, UserProfileUtils.getLanguage());
            response.setStatus(constd.getName());
            response.setStatusCode(constd.getCode());
            response.setMessage(error);

            if (emailDto.getSendEmailType() != SendEmailTypeEnum.SEND_DIRECT_NO_SAVE.toString()) {
                JcaEmail emailEntity = new JcaEmail(); // save email
                modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
                emailEntity = modelMapper.map(emailDto, JcaEmail.class);
                emailEntity.setCompanyId(companyId);
                emailEntity.setSendDate(comService.getSystemDateTime());
                emailEntity.setSendStatus(constd.getCode());
                this.save(emailEntity);
            }
            return response;
        }

        // send
        response = sendEmail(emailDto);

        return response;
    }

    /**
     * replaceParam
     * 
     * @param template
     * @param jsonParam
     * @return
     * @author trieuvd
     */
    public String replaceParam(String content, String jsonParam, Long companyId) {
//        if(StringUtils.isNotBlank(content) && StringUtils.isNotBlank(jsonParam)) {
//            List<TemplateParameterDto> listParameterDto = templateParameterRepository.findByCompany(companyId);
//            List<String> listParam = getParamsByTemplate(content);
//            JSONObject jsonObject = new JSONObject(jsonParam);
//            TemplateParameterDto parameterDto = new TemplateParameterDto();
//            for (String param : listParam) {
//                String value = CommonJsonUtil.getValueByKey(jsonObject, param);
//                if(StringUtils.isNotBlank(value)) {
//                    parameterDto = listParameterDto.stream().filter(p -> param.equals(p.getParameterFullName())).findAny().orElse(new TemplateParameterDto());
//                    value = formatParam(parameterDto, value);
//                    String contentParam = ConstantCore.PARAM_START_WITH.concat(param).concat(ConstantCore.PARAM_END_WITH);
//                    content = content.replace(contentParam, value); 
//                }
//            }
//        }
//        //replace blank các param con lai
//        if(StringUtils.isNotBlank(content)) {
//            content = content.replaceAll(REGEX_PARAM, StringUtils.EMPTY);
//        }
        return content;
    }

//    /**
//     * formatParam
//     * 
//     * @param param
//     * @param value
//     * @param parameterDto
//     * @return
//     * @author trieuvd
//     */
//    private String formatParam(TemplateParameterDto parameterDto, String value) {
//        String dataType = null == parameterDto ? StringUtils.EMPTY : parameterDto.getDataType();
//        String format = null == parameterDto ? StringUtils.EMPTY : parameterDto.getFormat();
//        if (DataTypeParameterEnum.DATE.getValue().equals(dataType)) {
//            try {
//                long val = Long.parseLong(value);
//                Date date = new Date(val);
//                value = CommonDateUtil.formatDateToString(date, format);
//            } catch (NumberFormatException e) {
//                logger.error("[formatParam] param: " + parameterDto.getParameterFullName() + ", value: " + value);
//            }
//        } else if (DataTypeParameterEnum.CHAR.getValue().equals(dataType) && null != format) {
//            switch (format) {
//            case "U":
//                value = value.toUpperCase();
//                break;
//            case "L":
//                value = value.toLowerCase();
//                break;
//            case "C":
//                value = StringUtils.capitalize(value.toLowerCase());
//                break;
//            default:
//                break;
//            }
//        }
//        return value;
//    }

    /**
     * saveListAttachFile
     * 
     * @param fileAttachs fileAttach: - fileName not null + extention - fileSize -
     *                    fileType trong EXT_ATTACH_FILE_LIST - repoId not null -
     *                    repoType 1: repo 2: ecmRepo
     * @param uuidEmail
     * @param companyId
     * @param locale
     * @return
     * @author trieuvd
     * @throws Exception
     */
    public void saveListAttachFile(List<AttachFileEmailDto> fileAttachs, String uuidEmail, Long companyId,
            Locale locale) throws Exception {
//        //import attach to repo attach
//        for (AttachFileEmailDto fileAttach : fileAttachs) {
//            String error = msg.getMessage(ConstantCore.IMPORT_ATTACH_FAIL, null, locale);
//            if(null==fileAttach.getFileName()||null==fileAttach.getFileSize()||!ConstantCore.EXT_ATTACH_FILE_LIST.contains(fileAttach.getFileType())) {
//                throw new Exception(error);
//            }
//            InputStream inputStream = null;
//            CompanyDto company = companyService.findById(companyId);
//            Path pathUrl = Paths.get(ConstantCore.EMAIL_ATTACH_FOLDER, company.getSystemCode(), uuidEmail);
//            String subFilePath = pathUrl.toString();
//            FileResultDto fileResultDto = new FileResultDto();
//            fileResultDto.setStatus(false);
//            fileResultDto.setMessage(error);
//            switch (fileAttach.getRepoType()) {
//            case 1:
//                inputStream = fileService.getInputStreamByRepositoryIdAndFilePath(fileAttach.getRepoId(), fileAttach.g);
//                fileResultDto = jrepositoryService.uploadInputStreamBySettingKey(inputStream, fileAttach.getFileName(), AppSystemConfig.REPO_URL_ATTACH_FILE, 2, null, subFilePath, companyId, locale);
//                break;
//            case 2:   
//                AlfDocumentDto alfDocumentDto = fileService.getDocumentByRepositoryIdAndFilePathWithECM(fileAttach.getRepoId(), fileAttach.getPath());
//                inputStream = alfDocumentDto.getStream();
//                fileResultDto = jrepositoryService.uploadInputStreamBySettingKey(inputStream, fileAttach.getFileName(), AppSystemConfig.REPO_URL_ATTACH_FILE, 2, null, subFilePath, companyId, locale);
//                break;
//            }
//            
//            if(fileResultDto.isStatus()) {
//                JcaAttachFileEmail attachFile = new JcaAttachFileEmail();
//                attachFile.setFileName(fileAttach.getFileName());
//                attachFile.setFileSize(fileAttach.getFileSize());
////                attachFile.setCreatedBy(UserProfileUtils.getUserNameLogin());
//                attachFile.setCreatedDate(comService.getSystemDateTime());
////                attachFile.setUuidEmail(uuidEmail);
////                attachFile.setFileNameUuid(fileResultDto.getFileName());
//                attachFile.setCompanyId(companyId);
//                attachFile.setRepositoryId(fileResultDto.getRepositoryId());
//                this.saveAttachFileEmail(attachFile);
//            }else {
//                throw new Exception(fileResultDto.getMessage());
//            }
//        }
    }

    public void templateToPdfAttach(Long templateId, String uuidEmai, String pdfName, Long companyId, String jsonParam,
            Locale locale) throws Exception {
        // get template
        JcaEmailTemplate template = templateService.getTemplateById(templateId);
        if (template == null)
            throw new Exception("Can not found template");

        String content = replaceParam(template.getTemplateContent(), jsonParam, companyId);
        // path
        CompanyDto company = companyService.findById(companyId);
        String pathUrl = Paths.get(ConstantCore.EMAIL_ATTACH_FOLDER, company.getSystemCode(), uuidEmai).toString();
        // fileName
        if (StringUtils.isBlank(pdfName)) {
            pdfName = template.getTemplateSubject();
        }
        String temp = Normalizer.normalize(pdfName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        pdfName = pattern.matcher(temp).replaceAll("");
        pdfName = pdfName.replaceAll("\\s+", "_");

        FileResultDto fileResultDto = jrepositoryService.saveFilePDF(content, pathUrl, pdfName,
                SystemConfig.REPO_URL_ATTACH_FILE, companyId, locale);
        if (fileResultDto.isStatus()) {
            JcaAttachFileEmail attachFile = new JcaAttachFileEmail();
            attachFile.setFileName(fileResultDto.getFileName());
            // attachFile.setFileSize(fileAttach.getFileSize());
//            attachFile.setCreatedBy(UserProfileUtils.getUserNameLogin());
            attachFile.setCreatedDate(comService.getSystemDateTime());
//            attachFile.setUuidEmail(uuidEmai);
//            attachFile.setFileNameUuid(fileResultDto.getFileName());
            attachFile.setCompanyId(companyId);
            attachFile.setRepositoryId(fileResultDto.getRepositoryId());
            this.saveAttachFileEmail(attachFile);
        } else {
            throw new Exception(fileResultDto.getMessage());
        }
    }

    private String getEmails(String emails) throws Exception {
        String result = null;
        List<String> listEmail = new ArrayList<String>();
        List<String> listResult = new ArrayList<String>();
        if (StringUtils.isNotBlank(emails)) {
            listEmail = Arrays.asList(emails.split("\\s*,\\s*"));
            listEmail = new ArrayList<String>(listEmail);
            while (!listEmail.isEmpty()) {
                if (listEmail.size() > 1000) {
                    List<String> listTamp = listEmail.subList(0, 999);
                    listResult.addAll(getListEmailSend(listTamp));
                    listEmail.removeAll(listTamp);
                } else {
                    listResult.addAll(getListEmailSend(listEmail));
                    listEmail.clear();
                }
            }
        }
        result = listResult.stream().map(Object::toString).collect(Collectors.joining(","));
        return result;
    }

    private List<String> getListEmailSend(List<String> listEmail) throws Exception {
        List<String> listResult = new ArrayList<String>(listEmail);
        List<String> listEmailInTable = accRepository.findListEmailByListEmail(listEmail, false);
        List<String> listEmaiPushEmail = accRepository.findListEmailByListEmail(listEmail, true);

        listResult.removeAll(new ArrayList<String>(listEmailInTable));
        listResult.addAll(listEmaiPushEmail);

        return listResult;
    }

    @Override
    public List<JcaEmail> getListEmailByListStatusAndCompanyId(List<String> listStatus, Long companyId) {
        return emailRepository.findAllEmailByStatusAndCompanyId(listStatus, companyId);
    }

    private Multipart setMultipart(String content) throws Exception {
        Pattern regexImgTag = Pattern.compile("<img[^>]*>");
        Pattern regexImgSrc = Pattern.compile("src=\"(.*?)\"");

        Multipart multipart = new MimeMultipart();
        BodyPart messageBodyPart = new MimeBodyPart();

        // get image
        List<String> imageList = new ArrayList<>();
        Matcher matcher = regexImgTag.matcher(content);
        while (matcher.find()) {
            imageList.add(matcher.group());
        }

        int index = 0;
        if (CollectionUtils.isNotEmpty(imageList)) {
            for (String imageTag : imageList) {
                // get scr
                Matcher matherSrc = regexImgSrc.matcher(imageTag);
                if (matherSrc.find()) {
                    String dataString = matherSrc.group(1);
                    String cid = "cid_image" + index;
                    String imageTagNew = imageTag.replace(dataString, "cid:" + cid);

                    // replace content
                    content = content.replace(imageTag, imageTagNew);

                    // DataSource
                    int dataStartIndex = dataString.indexOf(",") + 1;
                    String data = dataString.substring(dataStartIndex);
                    byte[] imgBytes = Base64.getDecoder().decode(data);
                    DataSource fds = new ByteArrayDataSource(imgBytes, "image/*");

                    messageBodyPart = new MimeBodyPart();

                    messageBodyPart.setDataHandler(new DataHandler(fds));
                    messageBodyPart.setHeader("Content-ID", "<" + cid + ">");

                    // add image to the multipart
                    multipart.addBodyPart(messageBodyPart);

                    index++;
                }
            }
        }

        // add content to the multipart
        messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(content, "text/html; charset=utf-8");

        multipart.addBodyPart(messageBodyPart);

        return multipart;
    }

    @Override
    public vn.com.unit.common.service.JCommonService getCommonService() {
        return comService;
    }

    private void save(JcaEmail entity) {
        if (ObjectUtils.isNotEmpty(entity.getId()))
            emailRepository.update(entity);
        else
            emailRepository.create(entity);
    }

}
