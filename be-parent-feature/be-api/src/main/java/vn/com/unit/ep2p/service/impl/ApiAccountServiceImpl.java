/*******************************************************************************
 * Class        ：AccountService
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.cms.core.module.account.dto.RegisterAccountDto;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetail;
import vn.com.unit.cms.core.module.events.repository.EventsRepository;
import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonNullAwareBeanUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaAccountRegister;
import vn.com.unit.core.enumdef.SendEmailTypeEnum;
import vn.com.unit.core.enumdef.param.JcaAccountSearchEnum;
import vn.com.unit.core.repository.JcaAccountRepository;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.entity.AccountSms;
import vn.com.unit.ep2p.admin.repository.AccountSmsRepository;
import vn.com.unit.ep2p.admin.service.AccountPasswordService;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.constant.ApiAccountTypeConstant;
import vn.com.unit.ep2p.constant.ApiOtpConstant;
import vn.com.unit.ep2p.constant.ResponseErrorCodeConstant;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.dto.AccountApiDto;
import vn.com.unit.ep2p.core.dto.EmailResetPassResultDto;
import vn.com.unit.ep2p.core.dto.ForgotPasswordResultDto;
import vn.com.unit.ep2p.core.dto.PasswordChangeResultDto;
import vn.com.unit.ep2p.core.dto.SendSmsApiRes;
import vn.com.unit.ep2p.core.enumdef.AccountGenderEnum;
import vn.com.unit.ep2p.core.res.dto.EmailResetPassDto;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.core.utils.NullAwareBeanUtils;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.dto.req.AccountAddReq;
import vn.com.unit.ep2p.dto.req.AccountChangePasswordGadReq;
import vn.com.unit.ep2p.dto.req.AccountChangePasswordReq;
import vn.com.unit.ep2p.dto.req.AccountRenewPasswordGadReq;
import vn.com.unit.ep2p.dto.req.AccountUpdateReq;
import vn.com.unit.ep2p.dto.req.CheckOtpReq;
import vn.com.unit.ep2p.dto.req.ForgotPasswordReq;
import vn.com.unit.ep2p.dto.req.RegisterAccountReq;
import vn.com.unit.ep2p.dto.req.RenewPasswordReq;
import vn.com.unit.ep2p.dto.res.AccountInfoRes;
import vn.com.unit.ep2p.dto.res.ForgotPasswordByAgentCodeRes;
import vn.com.unit.ep2p.dto.res.QuestionForgotPasswordRes;
import vn.com.unit.ep2p.dto.res.RegisterAccountRes;
import vn.com.unit.ep2p.service.ApiAccountService;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.ApiDsAccountQuestionService;
import vn.com.unit.ep2p.service.ForgotPasswordTypeService;
import vn.com.unit.ep2p.service.OtpService;
import vn.com.unit.ep2p.utils.LangugeUtil;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.service.FileStorageService;
import vn.com.unit.workflow.core.WorkflowIdentityService;

/**
 * <p>
 * AccountServiceImpl
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
//@Primary
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiAccountServiceImpl extends AbstractCommonService implements ApiAccountService {
    
    /** The jca account service. */
    @Autowired
    private JcaAccountService jcaAccountService;
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private WorkflowIdentityService workflowIdentityService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MasterCommonService masterCommonService;
    
    @Autowired
    private JcaEmailService jcaEmailService;
    
    @Autowired
    private JcaEmailTemplateService jcaEmailTemplateService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AccountPasswordService accountPasswordService;
    
    @Autowired
    private ApiDsAccountQuestionService apiDsAccountQuestionService;
    
    @Autowired
    private OtpService optpService;
    
	@Autowired
	ApiAgentDetailService apiAgentDetailService;
	
    @Autowired
    private Db2ApiService db2ApiService;
    
    @Autowired
    @Qualifier("appSystemConfigServiceImpl")
    private JcaSystemConfigService jcaSystemConfigService;
    
    @Autowired
    private AccountSmsRepository accountSmsRepository;
    
	@Autowired
	private JcaAccountRepository jcaAccountRepository;

    @Autowired
	private EventsRepository eventsRepository;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    MessageSource messageSource;
	/**
	 * Authentication database system
	 * 
	 * @param authentication type Authentication
	 * @return Authentication
	 * @throws AuthenticationException
	 * @author vunt
	 */
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Autowired
    private ForgotPasswordTypeService forgotPasswordTypeService;
    
    public static final String TEMPLATE_GIVE_PASSWORD_FOR_ACCOUNT = "TEMPLATE_GIVE_PASSWORD_FOR_ACCOUNT";
    public static final String TEMPLATE_SEND_MAIL_BD = "TEMPLATE_SEND_MAIL_BD";
    public static final String TEMPLATE_SEND_MAIL_CHANGE_PASSWORD_SUCCESSFULLY = "TEMPLATE_SEND_MAIL_CHANGE_PASSWORD_SUCCESSFULLY";
    public static final String TEMPLATE_GIVE_OTP_FOR_ACCOUNT = "TEMPLATE_GIVE_OTP_FOR_ACCOUNT";
    
    public static final String CHANGE_PASSWORD_GAD = "CHANGE_PASSWORD_GAD";
    public static final String RENEW_PASSWORD_GAD = "RENEW_PASSWORD_GAD";

    public static final String ACCOUNT_IMAGE_FOLDER = "account_image/";
    
    private Logger logger = LoggerFactory.getLogger(ApiAccountServiceImpl.class);
    
    /**
     * <p>
     * Mapper jca account dto to account info res.
     * </p>
     *
     * @param jcaAccountDto
     *            type {@link JcaAccountDto}
     * @return {@link AccountInfoRes}
     * @author taitt
     */
    private AccountInfoRes mapperJcaAccountDtoToAccountInfoRes(JcaAccountDto jcaAccountDto){
        return objectMapper.convertValue(jcaAccountDto, AccountInfoRes.class);
    }
    
    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountService#getAccountDtoByCondition(vn.com.unit.core.dto.JcaAccountSearchDto, org.springframework.data.domain.Pageable)
     */
    @Override
    public List<JcaAccountDto> getAccountDtoByCondition(JcaAccountSearchDto jcaAccountSearchDto, Pageable pagable) {
        return jcaAccountService.getAccountDtoByCondition(jcaAccountSearchDto, pagable);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountService#countAccountDtoByCondition(vn.com.unit.core.dto.JcaAccountSearchDto)
     */
    @Override
    public int countAccountDtoByCondition(JcaAccountSearchDto jcaAccountSearchDto) {
        return jcaAccountService.countAccountDtoByCondition(jcaAccountSearchDto);
    }
    
    private void setUsername(String username, JcaAccountDto jcaAccountDto, Long userId) throws DetailException{
        int count = jcaAccountService.countJcaAccountDtoByUsername(username, userId);
        if(count > 0) {
            throw new DetailException(AppApiExceptionCodeConstant.E402810_APPAPI_ACCOUNT_USERNAME_DUPLICATED, true);
        }else {
            jcaAccountDto.setUsername(username);
        }
    }
    
    private void setCode(String code, JcaAccountDto jcaAccountDto) throws DetailException{
        int count = jcaAccountService.countJcaAccountDtoByCode(code);
        if(count > 0) {
            throw new DetailException(AppApiExceptionCodeConstant.E402814_APPAPI_ACCOUNT_CODE_DUPLICATED, true);
        }else {
            jcaAccountDto.setCode(code);
        }
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountService#create(vn.com.unit.mbal.api.req.dto.AccountAddReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountInfoRes create(AccountAddReq accountAddReq) throws DetailException {
        JcaAccountDto jcaAccountDto = new JcaAccountDto();
        try {
            
            if(null != accountAddReq.getCode()) {
                setCode(accountAddReq.getCode(), jcaAccountDto);
            }
            
            if(CommonStringUtil.isNotBlank(accountAddReq.getUsername())) {
//                setUsername(accountAddReq.getUsername(), jcaAccountDto, null);
                jcaAccountDto.setUsername(accountAddReq.getUsername());
            }
            if(CommonStringUtil.isNotBlank(accountAddReq.getEmail())) {
                jcaAccountDto.setEmail(accountAddReq.getEmail());
            }
            jcaAccountDto.setFullname(accountAddReq.getFullname());
            String encryptedPassword = passwordEncoder.encode(accountAddReq.getPassword());
            jcaAccountDto.setPassword(encryptedPassword);
            String birthday = accountAddReq.getBirthdayStr();
            if(null != birthday) {
                jcaAccountDto.setBirthday( CommonDateUtil.parseDate(birthday, AppCoreConstant.YYYYMMDD_TIME));
                
            }
            jcaAccountDto.setGender(accountAddReq.getGender());
            jcaAccountDto.setCompanyId(accountAddReq.getCompanyId());
            if(CommonStringUtil.isNotBlank(accountAddReq.getPhone())) {
                jcaAccountDto.setPhone(accountAddReq.getPhone());
            }
            
            // upload image
            if(CommonStringUtil.isNotBlank(accountAddReq.getImgBase64()) && CommonStringUtil.isNotBlank(accountAddReq.getFileName())) {
                this.uploadAvatar(accountAddReq.getImgBase64(), accountAddReq.getFileName(), jcaAccountDto);
            }
            
            if(null != accountAddReq.getReceivedEmail()) {
                jcaAccountDto.setReceivedEmail(accountAddReq.getReceivedEmail());
            }else {
                jcaAccountDto.setReceivedEmail(false);
            }
            if(null != accountAddReq.getReceivedNotification()) {
                jcaAccountDto.setReceivedNotification(accountAddReq.getReceivedNotification());
            }else {
                jcaAccountDto.setReceivedNotification(false);
            }
            
            if(null != accountAddReq.getEnabled()) {
                jcaAccountDto.setEnabled(accountAddReq.getEnabled());
            }else {
                jcaAccountDto.setEnabled(false);
            }
            
            if(null != accountAddReq.getActived()) {
                jcaAccountDto.setActived(accountAddReq.getActived());
            }else {
                jcaAccountDto.setActived(false);
            }
            jcaAccountDto.setAccountType(accountAddReq.getAccountType());
            jcaAccountDto.setProvince(accountAddReq.getProvince());
            jcaAccountDto.setProvinceCity(accountAddReq.getProvinceCity());
            jcaAccountDto.setOffice(accountAddReq.getOffice());
            jcaAccountDto.setOfficeName(accountAddReq.getOfficeName());
            jcaAccountDto.setConfirmApplyCandidate(accountAddReq.isConfirmApplyCandidate());
            //save jca account
            jcaAccountDto = this.save(jcaAccountDto);
            
            //save jca account register
//            saveJcaAccoutnRegister(jcaAccountDto,accountAddReq);
            
            // add user to activiti
            //String userIdStr = Utils.buildActUserIdByCompanyIdAndUserName(companyId, username);
            String userIdStr = jcaAccountDto.getUserId().toString();
            String fullName = jcaAccountDto.getFullname();
            String email = jcaAccountDto.getEmail();
            workflowIdentityService.createUser(userIdStr, fullName, fullName, userIdStr, email, null, null, null);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E402802_APPAPI_ACCOUNT_ADD_ERROR);
        }
        
        return this.mapperJcaAccountDtoToAccountInfoRes(jcaAccountDto);
    }

	@Override
    @Transactional(rollbackFor = Exception.class)
    public RegisterAccountRes create(RegisterAccountReq registerAccountReq) throws Exception {

        if (StringUtils.isBlank(registerAccountReq.getEmail()) && StringUtils.isBlank(registerAccountReq.getPhone())) {
            throw new DetailException(AppApiExceptionCodeConstant.E4026102_APPAPI_EMAIL_PHONE_REQUIRED_ERROR);
        }

        AccountAddReq accAddReq = new AccountAddReq();
        NullAwareBeanUtils.copyPropertiesWONull(registerAccountReq, accAddReq);

        accAddReq.setActived(true);
        accAddReq.setEnabled(true);

        String password = UUID.randomUUID().toString().substring(0, 8);
        accAddReq.setPassword(password);
        accAddReq.setUsername(registerAccountReq.getEmail());
        accAddReq.setCompanyId(CommonConstant.COMPANY_DEFAULT);
        accAddReq.setAccountType(ApiAccountTypeConstant.TYPE_GUEST);
        
        if (StringUtils.isBlank(registerAccountReq.getEmail())) {
            accAddReq.setUsername(registerAccountReq.getPhone());
        }

        // create account
        create(accAddReq);

        RegisterAccountRes res = new RegisterAccountRes();
        copyData(registerAccountReq, res);
        // send password for email or phone
        if(StringUtils.isNotBlank(registerAccountReq.getOffice())){
            //get BD agent name
            List<Db2AgentDto> lstData = db2ApiService.getBdohInfoByOrgId(registerAccountReq.getOffice());
            if (!lstData.isEmpty()) {
                Db2AgentDto agentDto = lstData.get(0);
                Map<String, Object> paramEmailBD = new HashMap<String, Object>();
                paramEmailBD.put("fullname", registerAccountReq.getFullname());
                paramEmailBD.put("userEmail", registerAccountReq.getEmail());
                paramEmailBD.put("phoneNumber", registerAccountReq.getPhone());
                paramEmailBD.put("office", registerAccountReq.getOfficeName());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
                paramEmailBD.put("createdDate", sdf.format(new Date()));
                String bdAgentName = agentDto.getAgentName();
                String bdEmail = agentDto.getEmail();
                if(StringUtils.isBlank(bdEmail)) logger.info("SEND MAIL BDOH: BDOH Email is empty");
                paramEmailBD.put("email", bdEmail);
                paramEmailBD.put("bdAgentName", bdAgentName);
                logger.error("Register: Send Email to BD");
                sendEmail(paramEmailBD, TEMPLATE_SEND_MAIL_BD);
            }
        }
//        if (registerAccountReq.isPassWordToPhone()) {
//            // TODO
//        }
        return res;
    }
    
    private void copyData(RegisterAccountReq req, RegisterAccountRes res) {
        res.setEmail(req.getEmail());
        res.setFullname(req.getFullname());
        res.setPhone(req.getPhone());
    }
    
    private void sendEmail(Map<String, Object> paramEmail, String templateEmail) {
        // set toAddress
        try {
            String email = paramEmail.get("email").toString();
            List<String> toAddress = new ArrayList<>();
            toAddress.add(email);
//            String url = String.format("recruit-agency/%s/%s", data.getId(), data.getSecretKey());
            if(paramEmail.get("fullname") != null){
                paramEmail.put("fullname", paramEmail.get("fullname").toString());
            }

            // get template email
            if (TEMPLATE_GIVE_PASSWORD_FOR_ACCOUNT.equals(templateEmail)) {
                paramEmail.put("password", paramEmail.get("password").toString());
            }else if (TEMPLATE_GIVE_OTP_FOR_ACCOUNT.equals(templateEmail)) {
                paramEmail.put("opt", String.valueOf(paramEmail.get("otp")));
            }

            JcaEmailTemplateDto templateDto = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(templateEmail);
            if (templateDto != null) {
                String content = jcaEmailService.replaceParam(templateDto.getTemplateContent(), paramEmail);
                String subject = jcaEmailService.replaceParam(templateDto.getTemplateSubject(), paramEmail);
                // set email
                JcaEmailDto jcaEmailDto = jcaEmailService.convertValue(templateDto);
                jcaEmailDto.setToString(email);
                jcaEmailDto.setToAddress(toAddress);
                jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
                jcaEmailDto.setEmailContent(content);
                jcaEmailDto.setSubject(subject);
                JcaEmailDto dto = new JcaEmailDto();
                CommonNullAwareBeanUtil.copyPropertiesWONull(jcaEmailDto, dto);
                jcaEmailService.sendEmail(dto);
            }
        } catch (Exception e) {
            logger.error("Send mail: ", e);
        }
    }

    private void setDataToUpdate(JcaAccountDto jcaAccountDto, AccountUpdateReq accountUpdateReq) throws Exception {
        jcaAccountDto.setFullname(accountUpdateReq.getFullname());
        String birthday = accountUpdateReq.getBirthdayStr();
        if(CommonStringUtil.isNotBlank(birthday)) {
            SimpleDateFormat sdf = new SimpleDateFormat(AppCoreConstant.YYYYMMDD_TIME); 
            jcaAccountDto.setBirthday(sdf.parse(birthday));
        } else {
            jcaAccountDto.setBirthday(null);
        }
        jcaAccountDto.setGender(accountUpdateReq.getGender());
       
        if(CommonStringUtil.isNotBlank(accountUpdateReq.getEmail())) {
            jcaAccountDto.setEmail(accountUpdateReq.getEmail());
        }
        if(CommonStringUtil.isNotBlank(accountUpdateReq.getPhone())) {
            jcaAccountDto.setPhone(accountUpdateReq.getPhone());
        } else {
            jcaAccountDto.setPhone(null);
        }
        if(null != accountUpdateReq.getReceivedEmail()) {
            jcaAccountDto.setReceivedEmail(accountUpdateReq.getReceivedEmail());
        }else {
            jcaAccountDto.setReceivedEmail(false);
        }
        if(null != accountUpdateReq.getReceivedNotification()) {
            jcaAccountDto.setReceivedNotification(accountUpdateReq.getReceivedNotification());
        }else {
            jcaAccountDto.setReceivedNotification(false);
        }
        
        if(null != accountUpdateReq.getEnabled()) {
            jcaAccountDto.setEnabled(accountUpdateReq.getEnabled());
        }else {
            jcaAccountDto.setEnabled(false);
        }
        
        if(null != accountUpdateReq.getActived()) {
            jcaAccountDto.setActived(accountUpdateReq.getActived());
        }else {
            jcaAccountDto.setActived(false);
        }
        
        // upload image
        if(CommonStringUtil.isNotBlank(accountUpdateReq.getImgBase64()) && CommonStringUtil.isNotBlank(accountUpdateReq.getFileName())) {
            this.uploadAvatar(accountUpdateReq.getImgBase64(), accountUpdateReq.getFileName(), jcaAccountDto);
        }
    }
    
    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountService#update(vn.com.unit.mbal.api.req.dto.AccountUpdateReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AccountUpdateReq accountUpdateReq) throws DetailException {
        Long id = accountUpdateReq.getUserId();

        JcaAccountDto jcaAccountDto = jcaAccountService.getJcaAccountDtoById(id);
        if (null != jcaAccountDto) {
            try {
                setDataToUpdate(jcaAccountDto, accountUpdateReq);
                this.save(jcaAccountDto);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E402803_APPAPI_ACCOUNT_UPDATE_INFO_ERROR);
            }
        }
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#save(java.lang.Object)
     */
    @Override
    public JcaAccountDto save(JcaAccountDto objectDto) throws DetailException{
        JcaAccount jacAccount = jcaAccountService.saveJcaAccountDto(objectDto);
        objectDto.setUserId(jacAccount.getId());
        if(objectDto.isConfirmApplyCandidate()){
            jcaAccountService.saveJcaAccountRegister(objectDto);
        }
        return objectDto;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#detail(java.lang.Long)
     */
    @Override
    public JcaAccountDto detail(Long id) throws DetailException{
        JcaAccountDto jcaAccountDto = jcaAccountService.getJcaAccountDtoById(id);
        if (null != jcaAccountDto) {
            try {
                String genderCode = jcaAccountDto.getGender();
                if (CommonStringUtil.isNotBlank(genderCode)) {
                    if (AccountGenderEnum.MALE.toString().equals(genderCode)) {
                        jcaAccountDto.setGenderName(AccountGenderEnum.MALE.getValueMapping());
                    } else if (AccountGenderEnum.FEMALE.toString().equals(genderCode)) {
                        jcaAccountDto.setGenderName(AccountGenderEnum.FEMALE.getValueMapping());
                    }
                }

                Date birthday = jcaAccountDto.getBirthday();
                if(null != birthday) {
                    jcaAccountDto.setBirthdayStr( CommonDateUtil.formatDateToString(birthday, AppCoreConstant.YYYYMMDD_TIME)); 
                }
                
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E402805_APPAPI_ACCOUNT_INFO_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E402806_APPAPI_ACCOUNT_NOT_FOUND);
        }
        return jcaAccountDto;
    }
    
    
    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#delete(java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long accountId) throws DetailException {
        JcaAccountDto jcaAccountDto = jcaAccountService.getJcaAccountDtoById(accountId);
        if (null != jcaAccountDto) {
            try {
                // hardcode
                jcaAccountService.deleteJcaAccountById(accountId);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E402804_APPAPI_ACCOUNT_DELETE_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E402806_APPAPI_ACCOUNT_NOT_FOUND);
        }
    }
    
    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.AccountService#getAccountInfoResById(java.lang.Long)
     */
    @Override
    public AccountInfoRes getAccountInfoResById(Long accountId) throws DetailException {
        JcaAccountDto jcaAccountDto = this.detail(accountId);
        return objectMapper.convertValue(jcaAccountDto, AccountInfoRes.class);
    }


    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#search(org.springframework.util.MultiValueMap, org.springframework.data.domain.Pageable)
     */
    @Override
    public ObjectDataRes<JcaAccountDto> search(MultiValueMap<String, String> commonSearch,Pageable pageable) throws DetailException {
        ObjectDataRes<JcaAccountDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaAccount.class,JcaAccountService.TABLE_ALIAS_JCA_ACCOUNT);
            /** init param search repository */
            JcaAccountSearchDto reqSearch = this.buildJcaAccountSearchDto(commonSearch);
            
            int totalData = this.countAccountDtoByCondition(reqSearch);
            List<JcaAccountDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = this.getAccountDtoByCondition(reqSearch,pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E402801_APPAPI_ACCOUNT_LIST_ERROR);
        }
        return resObj;
    }
    

//    @Override
//    public <T extends ObjectDataRes<JcaAccountDto>> T search(MultiValueMap<String, String> commonSearch,Pageable pageable) throws DetailException {
//        AccountListObjectRes resObj = null;
//        try {
//            /** init pageable */
//            Pageable pageableAfterBuild = this.buildPageable(pageable);
//            /** init param search repository */
//            JcaAccountSearchDto reqSearch = this.buildJcaAccountSearchDto(commonSearch);
//            
//            int totalData = this.countAccountDtoByCondition(reqSearch);
//            List<JcaAccountDto> datas = new ArrayList<>();
//            if (totalData > 0) {
//                datas = this.getAccountDtoByCondition(reqSearch,pageableAfterBuild);
//            }
////            resObj1 = new ObjectDataRes<>(totalData, datas);
//            resObj = new AccountListObjectRes("test", totalData, datas);
//        } catch (Exception e) {
//            handlerCastException.castException(e, AppApiExceptionCodeConstant.E402801_APPAPI_ACCOUNT_LIST_ERROR);
//        }
//        return (T) resObj;
//    }
 
    
    /**
     * <p>
     * Builds the jca account search dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link JcaAccountSearchDto}
     * @author taitt
     */
    private JcaAccountSearchDto buildJcaAccountSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaAccountSearchDto reqSearch = new JcaAccountSearchDto();
        
        String keySearch = CommonStringUtil.isNotBlank(commonSearch.getFirst("keySearch")) ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        Boolean actived = null != commonSearch.getFirst("active") ? Boolean.valueOf(commonSearch.getFirst("active")) : null;
        
        reqSearch.setActived(actived);
        reqSearch.setCompanyId(companyId);
        
        if (CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumsValue: enumsValues) {
                switch (JcaAccountSearchEnum.valueOf(enumsValue)) {
                case EMAIL:
                    reqSearch.setEmail(keySearch);
                    break;
                case FULLNAME:
                    reqSearch.setFullName(keySearch);
                    break;
                case PHONE:
                    reqSearch.setPhone(keySearch);
                    break;
                case USERNAME:
                    reqSearch.setUsername(keySearch);
                    break;
                default:
                    reqSearch.setEmail(keySearch);
                    reqSearch.setUsername(keySearch);
                    reqSearch.setPhone(keySearch);
                    reqSearch.setFullName(keySearch);
                    break;
                }
            }
        }else {
            reqSearch.setEmail(keySearch);
            reqSearch.setUsername(keySearch);
            reqSearch.setPhone(keySearch);
            reqSearch.setFullName(keySearch);
        }
        return  reqSearch;
    }

    @Override
    public JcaAccountDto uploadAvatar( String imgBase64, String fileName, JcaAccountDto jcaAccountDto) throws Exception {
        if (null != imgBase64) {
            String subFilePath = ACCOUNT_IMAGE_FOLDER;
            String imageString = CommonBase64Util.removeBase64Header(imgBase64);
            byte[] imageByte = CommonBase64Util.decodeToByte(imageString);
            
            //fileupload
            FileUploadParamDto param = new FileUploadParamDto();
            param.setFileByteArray(imageByte);
            param.setFileName(fileName);
            param.setRename(null);
            
            param.setTypeRule(2);
            param.setDateRule(null);
            param.setSubFilePath(subFilePath);
            param.setCompanyId(1L);
            
           param.setRepositoryId(5L);
            FileUploadResultDto uploadResultDto = fileStorageService.upload(param);
            //hardcode 
            String filePath = uploadResultDto.getFilePath();
            
            jcaAccountDto.setAvatar(filePath);
            jcaAccountDto.setAvatarRepoId(uploadResultDto.getRepositoryId());
            
        }
        return jcaAccountDto;
    }

    @Override
    public void changePassword(AccountChangePasswordReq accountChangePasswordReq) throws Exception {
        Long userId = accountChangePasswordReq.getUserId();
        String passwordOld= accountChangePasswordReq.getPasswordOld();
        String passwordNew = accountChangePasswordReq.getPasswordNew();
        String confirmPasswordNew = accountChangePasswordReq.getConfirmPasswordNew();
        if(null != userId) {
            JcaAccount jcaAccount = jcaAccountService.getAccountById(userId);
			if (null != jcaAccount) {
//				if (!checkPasswordOld(jcaAccount.getPassword(), passwordOld)) {
//					throw new DetailException(
//							AppApiExceptionCodeConstant.E402808_APPAPI_ACCOUNT_PASSWORD_OLD_INCORRECT);
//				}
				if (!checkConfirmPassword(passwordNew, confirmPasswordNew)) {
					throw new DetailException(
							AppApiExceptionCodeConstant.E402809_APPAPI_ACCOUNT_CONFIRM_PASSWORD_NEW_INCORRECT);
				}
                final String API_TOKEN = "1726e098d6f941688a01819d5d78d406";
				// Call api change password
				PasswordChangeResultDto resultDto = null;
				try {
                    resultDto = RetrofitUtils.changePassword(UserProfileUtils.getUserNameLogin(), passwordOld, passwordNew, API_TOKEN);
                } catch (Exception e){
                    throw new Exception(e.getMessage());
                }
				if(resultDto == null) {
                    logger.error("resultDto.getErrLog(): null");
                    throw new Exception("Change password failed!");
                }
				if (resultDto!= null && !StringUtils.equals(resultDto.getResult(), "true")) {
				    String error = resultDto.getErrLog();
                    logger.error("resultDto.getErrLog(): ", error);
					throw new Exception(error);
				}
                if(resultDto != null){
                    String errResult = resultDto.getResult();
                    String resultMessage = resultDto.getErrLog();
                    logger.error("errResult: ", errResult);
                    logger.error("resultMessage: ", resultMessage);
                    logger.error("debug: ", StringUtils.equalsIgnoreCase(errResult, "true"));
                    if(StringUtils.equalsIgnoreCase(errResult, "true")) {
//                      send mail: change password successfully
                        logger.error("send mail start");
                        sendEmailChangePasswordSuccessfully(UserProfileUtils.getUserNameLogin());
                        //update reset password
                        jcaAccount.setResetPassword(BigDecimal.ZERO);
                        jcaAccount.setPassword(bCryptPasswordEncoder().encode(passwordNew));
                        jcaAccountService.saveJcaAccount(jcaAccount);

                        //update first login
                        accountPasswordService.updateWhenFirstLogin(jcaAccount.getId());
                    }
                }
			} else {
			    logger.error("CHANGE-PASSWORD-ERROR");
				throw new DetailException(AppApiExceptionCodeConstant.E402806_APPAPI_ACCOUNT_NOT_FOUND);
			}
            
        }
         
    }

    @Override
    public boolean checkPasswordOld(String oldPassword, String password) {
        if (CommonStringUtil.isNotBlank(oldPassword)) {
            return passwordEncoder.matches(password, oldPassword);
        }
        return false;
    }
    
    
    private boolean checkConfirmPassword(String passwordNew, String confirmPasswordNew) {
        boolean result = false;
        if (CommonStringUtil.isNotBlank(passwordNew) && CommonStringUtil.isNotBlank(confirmPasswordNew) && confirmPasswordNew.equals(passwordNew)) {
           result = true;
        }
        return result;
    }
    
    private void updatePassword(Long userId, String passwordNew) throws DetailException {
        if(CommonStringUtil.isNotBlank(passwordNew)) {
            try {
                jcaAccountService.updatePassword(userId, passwordNew); 
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E402807_APPAPI_ACCOUNT_CHANGE_PASSWORD_ERROR);
            }
        }
    }
    
    @Override
    public List<EnumsParamSearchRes> getListEnumSearch() {
        return masterCommonService.getEnumsParamSearchResForEnumClass(JcaAccountSearchEnum.values());
    }

    @Override
    public AccountInfoRes getAccountInfoResByRegisterAccountReq(RegisterAccountReq registerAccountReq)
            throws DetailException {
        return null;
    }

    @Override
    public int countAccountByEmail(String email) {
        int count = jcaAccountService.countJcaAccountDtoByEmail(email, null);
        return count;
    }

    @Override
    public int countAccountByPhone(String phone) {
        int count = jcaAccountService.countJcaAccountDtoByPhone(phone, null);
        return count;
    }

    @Override
    public AccountInfoRes getAccountInfoResByUsername(String username) throws DetailException {
        AccountInfoRes res = null;

        List<JcaAccount> listAccount = jcaAccountService.getListByUserName(username);
        if (CollectionUtils.isNotEmpty(listAccount)) {
            JcaAccount jcaAccountDto = listAccount.get(0);

            res = new AccountInfoRes();

            try {
                NullAwareBeanUtils.copyPropertiesWONull(jcaAccountDto, res);
                res.setUserId(jcaAccountDto.getId());
            } catch (Exception e) {
                logger.error("Exception ", e);
            }
        }

        return res;
    }

    @Override
    public ForgotPasswordByAgentCodeRes forgotPasswordByAgentCode(String agentCode) throws Exception {

//        AccountInfoRes account = getAccountInfoResByUsername(agentCode);//Task #59541
        Db2AgentDto agentInfo = db2ApiService.getAgentClientDetail(agentCode);
//        if (account == null) {
//            throw new Exception("AGENT_NOT_LOGIN_YET");
//        }
        //xet agent còn inforce
        Db2AgentDto agentDto = db2ApiService.getAgentInfoByCondition(agentCode);
        if(agentDto == null ){
            throw new DetailException(AppApiExceptionCodeConstant.E4026105_APPAPI_AGENT_CODE_EXISTS_ERROR);
        }
        if(agentDto.getAgentStatusCode() == 0){
            throw new DetailException(AppApiExceptionCodeConstant.E4026108_APPAPI_AGENT_NOT_INFORCE);
        }
        //xet agent còn vi phạm có hiệu lực hay không
        int blockLevel = db2ApiService.getAgentDiscipline(agentCode);
        if(blockLevel == 1){
            throw new DetailException(AppApiExceptionCodeConstant.E4026108_APPAPI_AGENT_NOT_INFORCE);
        }
        ForgotPasswordByAgentCodeRes res = new ForgotPasswordByAgentCodeRes();
        String otpTo = ApiOtpConstant.OTP_TYPE_ANSWER_QUESTION;
        if (StringUtils.isNotBlank(agentInfo.getEmailAddress2())) {
            otpTo = ApiOtpConstant.OTP_TYPE_EMAIL_PERSONAL;
        } else if (StringUtils.isNotBlank(agentInfo.getEmailAddress1())) {
            otpTo = ApiOtpConstant.OTP_TYPE_EMAIL_DLVN;
            String email1 = agentInfo.getEmailAddress1();
            if(email1.indexOf("@agency.dai-ichi-life.com.vn") > -1) {
            	res.setAgencyFlag(true);
            }
        } else if (StringUtils.isNotBlank(agentInfo.getMobilePhone())) {
             otpTo = ApiOtpConstant.OTP_TYPE_SMS;
        }
        
        forgotPasswordTypeService.createForgotType(agentCode, otpTo);

        res.setOtpTo(otpTo);
        res.setAgentCode(agentCode);
        res.setQuestions(new ArrayList<QuestionForgotPasswordRes>());
//        if (ApiOtpConstant.OTP_TYPE_ANSWER_QUESTION.equals(otpTo) || StringUtils.equalsIgnoreCase(ApiOtpConstant.OTP_TYPE_SMS, otpTo)) {
//            List<QuestionForgotPasswordRes> questions = apiDsAccountQuestionService.getQuestionForgotPasswordRes(agentCode,
//                    2L);
//            res.setQuestions(questions);
//        }
        
        return res;
    }

    @Override
    public boolean checkOtp(CheckOtpReq checkOtpReq) throws DetailException {
        String cacheOtp = optpService.getOtp(checkOtpReq.getAgentCode());
        if("0".equals(cacheOtp)){
        	// optpService.generateOTP(cacheOtp);
            throw new DetailException(AppApiExceptionCodeConstant.E4026112_APPAPI_OTP_IS_EXPIRED_ERROR);
        }
        //try {
        	int countIncorrect = optpService.getIncorrectOtp(checkOtpReq.getAgentCode() + "_c1");
        	if (countIncorrect >= 3) {
        		throw new DetailException(AppApiExceptionCodeConstant.E4026113_APPAPI_INCORRECT_OTP);
        	}
            boolean check = StringUtils.equalsIgnoreCase(cacheOtp, checkOtpReq.getOtp());
            if (!check) {
            	optpService.setIncorrectOtp(checkOtpReq.getAgentCode() + "_c1", String.valueOf(countIncorrect + 1));
                throw new DetailException(AppApiExceptionCodeConstant.E4026111_APPAPI_OTP_IS_NOT_CORRECT_ERROR);
            } else {
                return true;
            }
        //} catch (Exception e){
        //    throw new DetailException(AppApiExceptionCodeConstant.E4026111_APPAPI_OTP_IS_NOT_CORRECT_ERROR);
        //}
    }

    @Override
    public AccountSms forgotPassword(ForgotPasswordReq forgotPasswordReq) throws Exception {
        if(StringUtils.isBlank(forgotPasswordReq.getAgentCode())){
            throw new Exception("Mã đại lý rỗng");
        }

        JcaAccount account = accountService.findByUserName(forgotPasswordReq.getAgentCode(), 2L);
        //save temporary account in order to check send sms
        if(account == null) {
            account = new JcaAccount();
            account.setUsername(forgotPasswordReq.getAgentCode());
            account.setEnabled(true);
            account.setActived(true);
            account.setCompanyId(2L);
            accountService.save(account);
        }
        String email = "";
        Db2AgentDto agentInfo = db2ApiService.getAgentClientDetail(forgotPasswordReq.getAgentCode());
        if(agentInfo == null){
            throw new Exception("Mã đại lý không tồn tại trong hệ thống");
        }

        // validate thông tin nhập
        if ((ApiOtpConstant.OTP_TYPE_EMAIL_PERSONAL.equals(forgotPasswordReq.getOptTo())
                && StringUtils.isBlank(forgotPasswordReq.getEmailPersonal()))
                || (ApiOtpConstant.OTP_TYPE_EMAIL_DLVN.equals(forgotPasswordReq.getOptTo())
                        && StringUtils.isBlank(forgotPasswordReq.getEmailDlvn()))
                || (ApiOtpConstant.OTP_TYPE_SMS.equals(forgotPasswordReq.getOptTo())
                        && StringUtils.isBlank(forgotPasswordReq.getPhoneNumber()))) {
            throw new DetailException(AppApiExceptionCodeConstant.E402805_APPAPI_ACCOUNT_INFO_ERROR);
        }
        
        if (ApiOtpConstant.OTP_TYPE_EMAIL_PERSONAL.equals(forgotPasswordReq.getOptTo())) {
            email = forgotPasswordReq.getEmailPersonal();
            if(!StringUtils.equalsIgnoreCase(agentInfo.getEmailAddress2(), forgotPasswordReq.getEmailPersonal())){
                throw new DetailException(AppApiExceptionCodeConstant.E402815_APPAPI_INVALID_INFO);
            }
        } else if (ApiOtpConstant.OTP_TYPE_EMAIL_DLVN.equals(forgotPasswordReq.getOptTo())) {
            email = forgotPasswordReq.getEmailDlvn();
            if(!StringUtils.equalsIgnoreCase(agentInfo.getEmailAddress1(), forgotPasswordReq.getEmailDlvn())){
                throw new DetailException(AppApiExceptionCodeConstant.E402815_APPAPI_INVALID_INFO);
            }
        } else if (ApiOtpConstant.OTP_TYPE_ANSWER_QUESTION.equals(forgotPasswordReq.getOptTo())) {
            // TODO: xử lý question
        } else if (ApiOtpConstant.OTP_TYPE_SMS.equals(forgotPasswordReq.getOptTo())) {
            if (!StringUtils.equalsIgnoreCase(agentInfo.getMobilePhone(), forgotPasswordReq.getPhoneNumber())) {
                throw new DetailException(AppApiExceptionCodeConstant.E402815_APPAPI_INVALID_INFO);
            }
        }

        String otp = optpService.generateOTP(forgotPasswordReq.getAgentCode());
        Date currentDate = new Date();
        AccountSms accountSms = null;
        // save temp data account sms
        accountSms = accountSmsRepository.findByAccountId(account.getId(), currentDate, forgotPasswordReq.getOptTo());
        if (accountSms == null){
            accountSms = new AccountSms();
            accountSms.setSmsCount(1);
        } else {
            accountSms.setSmsCount(Optional.ofNullable(accountSms.getSmsCount()).orElse(0) + 1);
            //check gửi 2 sms/ ngày
            if(ApiOtpConstant.OTP_TYPE_SMS.equals(forgotPasswordReq.getOptTo()) && accountSms.getSmsCount() > 2){
                throw new DetailException(AppApiExceptionCodeConstant.E4026109_APPAPI_INVALID_NUMBER_OF_SMS);
            }
        }
        accountSms.setAccountId(account.getId());
        accountSms.setEffectiveDate(currentDate);
        accountSms.setOtp(String.valueOf(otp));
        accountSms.setTypeOtp(forgotPasswordReq.getOptTo());
        accountSmsRepository.save(accountSms);

        if (StringUtils.isNotBlank(email)) {
            Map<String, Object> paramEmail = new HashMap<String, Object>();
            paramEmail.put("email", email);
            paramEmail.put("fullname", agentInfo.getAgentName());
            paramEmail.put("password", String.valueOf(otp));
            paramEmail.put("min", String.valueOf(ApiOtpConstant.OTP_EXPIRE_MINS));

            sendEmail(paramEmail, TEMPLATE_GIVE_OTP_FOR_ACCOUNT);
        } else {
            //GET CONFIG SEND OTP
            final String URL = jcaSystemConfigService.getValueByKey("IIBHCMS_BASE_URL", 2L);
            final String SENDER = jcaSystemConfigService.getValueByKey("SENDER_SMS", 2L);
            final String SMS_TYPE = jcaSystemConfigService.getValueByKey("SMS_TYPE", 2L);
            final String TOKEN = jcaSystemConfigService.getValueByKey("SEND_SMS_TOKEN", 2L);
            final String TEMPLATE = jcaSystemConfigService.getValueByKey("SMS_RESET_PASSWORD", 2L);
            String INFO = StringUtils.replaceOnce(TEMPLATE, "{password}", String.valueOf(otp));
    		INFO = StringUtils.replaceOnce(INFO, "{agentcode}", forgotPasswordReq.getAgentCode());
            //CALL API SEND OTP
            SendSmsApiRes sendSmsApiRes = RetrofitUtils.sendSMSApi(TOKEN, URL, forgotPasswordReq.getAgentCode(), agentInfo.getMobilePhone(), SENDER, SMS_TYPE, INFO);
            //GET RESPONSE
            if(sendSmsApiRes == null){
                logger.error("sendSmsApiRes: RESPONSE NULL");
                throw new DetailException(AppApiExceptionCodeConstant.E4026110_APPAPI_CAN_NOT_SEND_OTP);
            }
            if(sendSmsApiRes != null && !StringUtils.equalsIgnoreCase(sendSmsApiRes.getResponseCode(),"00")){
                logger.error("sendSmsApiRes: " , sendSmsApiRes.getResponseCode());
                throw new DetailException(AppApiExceptionCodeConstant.E4026110_APPAPI_CAN_NOT_SEND_OTP);
            }
        }
        accountSms.setOtp(null);
        return accountSms;
    }

    @Override
    public String renewPasswordByOtp(RenewPasswordReq renewPasswordReq) throws Exception {
        //validate new Password
        if(StringUtils.compareIgnoreCase(renewPasswordReq.getNewPassword(), renewPasswordReq.getConfirmNewPassword()) != 0){
            throw new Exception("Mật khẩu mới chưa chính xác");
        }
        //check valid agent code + question

        String cacheOtp = optpService.getOtp(renewPasswordReq.getAgentCode());
        boolean check = StringUtils.equalsIgnoreCase(cacheOtp, renewPasswordReq.getOtp());
        if (StringUtils.isNotEmpty(cacheOtp) && !"0".equalsIgnoreCase(cacheOtp) ) {
        	int countIncorrect = optpService.getIncorrectOtp(renewPasswordReq.getAgentCode() + "_c2");
        	if (countIncorrect >= 3) {
        		throw new DetailException(AppApiExceptionCodeConstant.E4026113_APPAPI_INCORRECT_OTP);
        	}
        	if (!check) {
            	optpService.setIncorrectOtp(renewPasswordReq.getAgentCode() + "_c2", String.valueOf(countIncorrect + 1));
        		throw new Exception("Bạn không có quyền truy cập");
	        }
        } else {
            if(renewPasswordReq != null){
                if(ObjectUtils.isNotEmpty(renewPasswordReq.getQuestions())){
                    QuestionForgotPasswordRes question = renewPasswordReq.getQuestions().get(0);
                    if(apiDsAccountQuestionService.checkValidAgentQuestion(renewPasswordReq.getAgentCode(), question.getQuestionCode(), question.getAnswer())){
                        throw new Exception("Bạn không có quyền truy cập");
                    }
                } else {
                	throw new Exception("Bạn không có quyền truy cập");
                }
            }
        }

        String resultMessage = "";
        final String API_TOKEN = "1726e098d6f941688a01819d5d78d406";
        ForgotPasswordResultDto result = null;
        try {
            result = RetrofitUtils.forgotPassword(renewPasswordReq.getAgentCode(), renewPasswordReq.getNewPassword(), API_TOKEN);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

        if(result == null){
            throw new Exception("Renew password: Call API error");
        }
        if(result != null && !StringUtils.equalsIgnoreCase(result.getResult(), "true")){
            String error = result.getErrLog();
            logger.error(error);
            throw new Exception(error);
        }
        if(result != null){
            String errResult = result.getResult();
            resultMessage = result.getErrLog();
            logger.error("errResult: ", errResult);
            logger.error("resultMessage: ", resultMessage);
            if(StringUtils.equalsIgnoreCase(errResult, "true")){
//               call api daiichi notify update password
//                notifyChangePassword(renewPasswordReq);
                //send mail: change password successfully
                sendEmailChangePasswordSuccessfully(renewPasswordReq.getAgentCode());
            }
        }
        Db2AgentDto agentDto = db2ApiService.getAgentClientDetail(renewPasswordReq.getAgentCode());
        return agentDto.getEmailAddress1();
    }
    private void notifyChangePassword(RenewPasswordReq renewPasswordReq) throws Exception {
        EmailResetPassDto emailResetPassDto = new EmailResetPassDto();
        emailResetPassDto.setUsername(renewPasswordReq.getAgentCode());
        emailResetPassDto.setPassword(renewPasswordReq.getNewPassword());
        EmailResetPassResultDto emailResetPassResultDto = null;
        try {
            emailResetPassResultDto = RetrofitUtils.sendEmailResetPass(emailResetPassDto);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        if(emailResetPassResultDto == null){
            throw new Exception("Renew password: Call API error");
        }
        if(emailResetPassResultDto != null && (!StringUtils.equalsIgnoreCase(emailResetPassResultDto.getErrCode(), "00")
                                            || !StringUtils.equalsIgnoreCase(emailResetPassResultDto.getStatus(), "SUCCESS"))) {
            String error = emailResetPassResultDto.getErrMsg();
            logger.error("Renew password error: " + error);
            throw new Exception(error);
        }
    }
    @Override
    public boolean isFirstLogin(Long userId) throws DetailException {
        boolean res = false;
        try {
            res = accountPasswordService.isFirstLogin(userId);
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    @Override
	public JcaAccount changePasswordAccountGa(AccountChangePasswordGadReq accountChangePasswordReq) throws Exception {
		Long userId = accountChangePasswordReq.getUserId();
		String passwordOld = accountChangePasswordReq.getPasswordOld();
		String passwordGadOld = accountChangePasswordReq.getPasswordGadOld();
		String passwordGadNew = accountChangePasswordReq.getPasswordGadNew();
		String confirmPasswordGadNew = accountChangePasswordReq.getConfirmPasswordGadNew();
		JcaAccount jcaAccount = jcaAccountService.getAccountById(userId);
		if (null != jcaAccount) {
		    //CHECK PASSWORD AGENT
            try{
                if (!checkPasswordAgent(jcaAccount, passwordOld)) {
                    throw new Exception("PASSWORD_AGENT_ERROR");
                }
            } catch (Exception e){
                throw new Exception(e.getMessage());
            }

			//CHECK PASSWORD GAD
			if (!checkPasswordOld(jcaAccount.getGadPassword(), passwordGadOld)) {
				throw new Exception("PASSWORD_GAD_ERROR");
			}
			//CHECK CONFIRM PASSWORD GAD
			if (!checkConfirmPassword(passwordGadNew, confirmPasswordGadNew)) {
				throw new DetailException(
						AppApiExceptionCodeConstant.E402809_APPAPI_ACCOUNT_CONFIRM_PASSWORD_NEW_INCORRECT);
			}
			String encryptedPassword = bCryptPasswordEncoder().encode(passwordGadNew);
			accountService.updatePasswordGad(userId, encryptedPassword);
			CmsAgentDetail resObj = apiAgentDetailService.getCmsAgentDetailByUsername(UserProfileUtils.getFaceMask());
			sendEmailChangePasswordGad(resObj.getFullName(), resObj.getEmailDlvn(), resObj.getGender());
			jcaAccount.setPassword(null);
			jcaAccount.setGadPassword(null);
			return jcaAccount;
		} else {
			throw new DetailException(AppApiExceptionCodeConstant.E402806_APPAPI_ACCOUNT_NOT_FOUND);
		}
	}

	@Override
    public boolean checkPasswordAgent(JcaAccount jcaAccount, String passwordOld) throws Exception{
        boolean result = true;
        String TOKEN = jcaSystemConfigService.getValueByKey("LOGIN_API_SECRET_KEY", 2L);
        final String DEVICE_ID = "33C85780-8A5E-45F3-938B-2E2241FE9DFE";
        AccountApiDto accountApiDto = null;
        try{
            accountApiDto = RetrofitUtils.loginApi(TOKEN, jcaAccount.getUsername(), passwordOld, jcaAccount.getDeviceTokenMobile(), DEVICE_ID);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        if (accountApiDto != null) {
            // ResponseMsg
            String responseMsg = accountApiDto.getResponseMsg();
            // AuthStatus
            String authStatus = accountApiDto.getAuthStatus();
            // Account exists in API
            if(StringUtils.equals(authStatus, "false")
                    && StringUtils.equalsIgnoreCase(responseMsg, ResponseErrorCodeConstant.WRONG_PASSWORD_VI)){
                result = false;
            }
        } else {
            throw new Exception("Login Api Dai-ichi fails");
        }
        return result;
    }

    private void sendEmailChangePasswordGad(String gadName, String email, String gender) {
    	// send password for email or phone
        Map<String, Object> paramEmail = new HashMap<String, Object>();
        paramEmail.put("gadName", gadName);
        paramEmail.put("email", email);
        paramEmail.put("gender", StringUtils.equalsIgnoreCase(gender, "M") ? "Anh" : "Chị");

        sendEmail(paramEmail, CHANGE_PASSWORD_GAD);
    }
    private String sendEmailChangePasswordSuccessfully(String agentCode) {
        logger.error("send mail begin: ");
        Db2AgentDto agentInfo = db2ApiService.getAgentClientDetail(agentCode);
        String emailTo = "";
        JcaAccount account = accountService.findByUserName(agentCode, 2L);
        Map<String, Object> paramEmail = new HashMap<String, Object>();
        if(account != null) {
            if(agentInfo == null) {
                emailTo = account.getEmail();
            } else {
                if (StringUtils.isNotBlank(agentInfo.getEmailAddress2())) {
                    emailTo = agentInfo.getEmailAddress2();
                } else if (StringUtils.isNotBlank(agentInfo.getEmailAddress1())) {
                    emailTo = agentInfo.getEmailAddress1();
                } else {
                    emailTo = account.getEmail();
                }
            }
            logger.error("SEND MAIL TO: ", emailTo);
            paramEmail.put("email", emailTo);
            // set toAddress
            try {
                String email = paramEmail.get("email").toString();
                List<String> toAddress = new ArrayList<>();
                toAddress.add(email);
                JcaEmailTemplateDto templateDto = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(TEMPLATE_SEND_MAIL_CHANGE_PASSWORD_SUCCESSFULLY);
                if (templateDto != null) {
                    String content = jcaEmailService.replaceParam(templateDto.getTemplateContent(), paramEmail);
                    String subject = jcaEmailService.replaceParam(templateDto.getTemplateSubject(), paramEmail);
                    // set email
                    JcaEmailDto jcaEmailDto = jcaEmailService.convertValue(templateDto);
                    jcaEmailDto.setToString(email);
                    jcaEmailDto.setToAddress(toAddress);
                    jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
                    jcaEmailDto.setEmailContent(content);
                    jcaEmailDto.setSubject(subject);
                    JcaEmailDto dto = new JcaEmailDto();
                    CommonNullAwareBeanUtil.copyPropertiesWONull(jcaEmailDto, dto);
                    jcaEmailService.sendEmail(dto);
                }
            } catch (Exception e) {
                logger.error("Send mail: ", e);
            }
        }
        return "";
    }

	@Override
	public String getUserName(String agentCode) {
		return jcaAccountRepository.findUsername(agentCode);
	}

    @Override
    public Db2AgentDto checkAgentExist(String agentCode) throws Exception {
        Db2AgentDto agentDto = new Db2AgentDto();
        try {
            agentDto = db2ApiService.getAgentInfoByCondition(agentCode);
        } catch (Exception e) {
            throw new Exception("Có lỗi xảy ra, bạn vui lòng thực hiện lại sau!");
        }
        if (agentDto == null) {
            // throw new Exception("Tài khoản không tồn tại trong hệ thống.");
            throw new Exception("INPUT-ERROR");
        } else {
            if (agentDto.getAgentStatusCode() == 0) {
                throw new Exception("Tài khoản của bạn đã bị khóa do bạn đã chấm dứt mã số. Vui lòng liên hệ hotline để được hỗ trợ");
                // throw new Exception("AGENT_TERMINATED");
            }
            // xet agent còn vi phạm có hiệu lực hay không
            int blockLevel = db2ApiService.getAgentDiscipline(agentCode);
            if (blockLevel == 1) {
                throw new Exception(
                        "Tài khoản của bạn đã bị tạm khoá do vi phạm. Vui lòng liên hệ Chatbot hoặc Bộ phận Quản lý Đại lý để được hỗ trợ");
                // throw new Exception("AGENT_DISCIPLINE");
            }
        }
        return agentDto;
    }

    @Override
    public RegisterAccountRes getAccountCandidateByEmail(String email, String fullname, String phone) {
        RegisterAccountRes registerAccountRes = null;
        RegisterAccountDto registerAccountDto = eventsRepository.getAccountCandidateByEmail(email, fullname, phone);
        if(registerAccountDto != null){
            registerAccountRes = new RegisterAccountRes();
            registerAccountRes.setEmail(registerAccountDto.getEmail());
            registerAccountRes.setFullname(registerAccountDto.getFullname());
            registerAccountRes.setPhone(registerAccountDto.getPhone());
            registerAccountRes.setRegisterDate(registerAccountDto.getRegisterDate());
        }
        return registerAccountRes;
    }
    @Override
    public JcaAccountRegister saveJcaAccountRegisterContacted(Long id) throws Exception {
        return jcaAccountService.saveJcaAccountRegisterContacted(id);
    }

    @Override
    @Transactional(transactionManager = "transactionManager",  propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    public void loginGad(JcaAccount jcaAccount, String passwordGad) throws Exception{
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        // Get configure so lan login fail se khoa tai khoan
        int failLoginCount = systemConfig.getIntConfig("FAILED_LOGIN_COUNT");
        // Get thoi gian khoa tai khoan trong bao lau
        int lockedTimeMinutes = systemConfig.getIntConfig("TIME_LOCK");

        // Check lock user
        Duration timeElapsed = this.getDurationBetween(jcaAccount);
        checkLockedUser(locale, jcaAccount, failLoginCount, lockedTimeMinutes, timeElapsed);

        //if account's valid then reset failLoginCount = 0
        if (jcaAccount.getGadFailedLoginCount() >= failLoginCount && Long.compare(timeElapsed.getSeconds(), Long.valueOf(lockedTimeMinutes) * 60) > 0) {
            jcaAccount.setGadFailedLoginCount(0);
        }
        boolean loginSuccess = checkPasswordOld(jcaAccount.getGadPassword(), passwordGad);
        //login success
        if(!loginSuccess){
            validateLoginFail(jcaAccount, failLoginCount, lockedTimeMinutes);
        } else {
            jcaAccount.setGadLoginDate(new Date());
            jcaAccount.setGadLoginLock(false);
            jcaAccount.setGadFailedLoginCount(0);
            accountService.saveJcaAccount(jcaAccount);
        }
        //send OTP
//        resendOtpGad(jcaAccount);
    }

    @Override
    public void resendOtpGad(JcaAccount jcaAccount) throws Exception {
        //send OTP
        Db2AgentDto agentInfo = db2ApiService.getAgentClientDetail(jcaAccount.getUsername());
        String otp = optpService.generateOTP(jcaAccount.getUsername());
        String email = agentInfo.getEmailAddress2();
        if (agentInfo != null && StringUtils.isNotBlank(email)) {
            Map<String, Object> paramEmail = new HashMap<String, Object>();
            paramEmail.put("email", email);
            paramEmail.put("gender", StringUtils.equalsIgnoreCase(agentInfo.getGender(), "M") ? "Anh" : "Chị");
            paramEmail.put("gadName", agentInfo.getAgentName());
            paramEmail.put("password", String.valueOf(otp));
            paramEmail.put("min", String.valueOf(ApiOtpConstant.OTP_EXPIRE_MINS));

            sendEmail(paramEmail, RENEW_PASSWORD_GAD);
        } else {
            throw  new Exception("Bạn chưa đăng ký email cá nhân, vui lòng liên hệ Hotline để được hướng dẫn.");
        }
    }

    @Override
    public boolean checkOtpGad(JcaAccount jcaAccount, String otp) throws Exception{
        String cacheOtp = optpService.getOtp(jcaAccount.getUsername());
        if("0".equals(cacheOtp)){
            throw new Exception("EXPIRED_OTP");
        }
        int countIncorrect = optpService.getIncorrectOtp(jcaAccount.getUsername() + "_c3");
    	if (countIncorrect >= 3) {
    		throw new DetailException(AppApiExceptionCodeConstant.E4026113_APPAPI_INCORRECT_OTP);
    	}
        boolean check = StringUtils.equalsIgnoreCase(cacheOtp, otp);
        if (!check) {
        	optpService.setIncorrectOtp(jcaAccount.getUsername() + "_c3", String.valueOf(countIncorrect + 1));
        }
        return check;
    }

    private void checkLockedUser(Locale locale, JcaAccount account, int failLoginCount, int timeInLocked, Duration timeElapsed) throws Exception{
        if (account.getGadFailedLoginCount() >= failLoginCount && Long.compare(timeElapsed.getSeconds(), Long.valueOf(timeInLocked) * 60) < 1) {
            long lockedTime = (Long.valueOf(timeInLocked) * 60) - timeElapsed.getSeconds();
            // check how much time account locked left
            long lockedTimeMinutes = lockedTime / 60;
            if(lockedTime >= 60){
                if(lockedTime % 60 <= 30){
                    throw new Exception(checkErrorMessage("account.has.been.locked.minutes", new Object[] { failLoginCount, lockedTimeMinutes },
                            locale));
                } else {
                    throw new Exception(checkErrorMessage("account.has.been.locked.minutes", new Object[] { failLoginCount, lockedTimeMinutes + 1 },
                            locale));
                }
            } else {
                throw new Exception(checkErrorMessage("account.has.been.locked.seconds", new Object[] { failLoginCount, lockedTime },
                        locale));
            }
        }
    }
    private Duration getDurationBetween(JcaAccount account) {
        Instant start = Optional.ofNullable(account.getGadLoginDate()).orElse(new Date()).toInstant(); ;
        Instant end = Instant.now();
        return Duration.between(start, end);
    }
    private void validateLoginFail(JcaAccount account, int failLoginCount, int lockedTimeMinutes) throws  Exception{
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        account.setGadFailedLoginCount(Optional.ofNullable(account.getGadFailedLoginCount()).orElse(0) + 1);
        if (account.getGadFailedLoginCount() < failLoginCount) {
            account.setGadLoginDate(new Date());
            account.setGadLoginLock(account.isGadLoginLock());
            accountService.saveJcaAccount(account);
            String messageError = checkErrorMessage("gad.password.is.incorrect.lock", new Object[] { failLoginCount,
                    (failLoginCount - Optional.ofNullable(account.getGadFailedLoginCount()).orElse(0)) }, locale);
            throw new Exception(messageError);
        } else {
            account.setGadLoginDate(new Date());
            account.setGadLoginLock(true);
            accountService.saveJcaAccount(account);
            throw new Exception(checkErrorMessage("account.has.been.locked.minutes", new Object[] { failLoginCount, lockedTimeMinutes },
                    locale));
        }
    }
    public String checkErrorMessage(String codeError, Object[] param, Locale locale) {
        String messageError = messageSource.getMessage(codeError,param,locale);

        return messageError;
    }

    @Override
    public void renewPasswordAccountGa(AccountRenewPasswordGadReq accountRenewPasswordGadReq) throws Exception {
        //update GAD password
        Long userId = UserProfileUtils.getAccountId();
        String password = accountRenewPasswordGadReq.getPassword();
        String confirmPassword = accountRenewPasswordGadReq.getConfirmPassword();
        JcaAccount jcaAccount = jcaAccountService.getAccountById(userId);
        if (null != jcaAccount) {

            //CHECK CONFIRM PASSWORD GAD
            if (!checkConfirmPassword(password, confirmPassword)) {
                throw new DetailException(AppApiExceptionCodeConstant.E402809_APPAPI_ACCOUNT_CONFIRM_PASSWORD_NEW_INCORRECT);
            }
            String encryptedPassword = bCryptPasswordEncoder().encode(password);
            accountService.updatePasswordGad(userId, encryptedPassword);
//            CmsAgentDetail resObj = apiAgentDetailService.getCmsAgentDetailByUsername(UserProfileUtils.getFaceMask());
//            //send mail
//            sendEmailRenewPasswordGad(resObj.getFullName(), resObj.getEmailDlvn(), resObj.getGender(), );
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E402806_APPAPI_ACCOUNT_NOT_FOUND);
        }
    }
}
