/*******************************************************************************
 * Class        ：FaqsController
 * Created date ：2017/03/19
 * Lasted date  ：2017/03/19
 * Author       ：TaiTM
 * Change log   ：2017/03/19：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.controller.common.CmsCommonsSearchFilterProcessController;
import vn.com.unit.cms.admin.all.dto.FaqsCategoryDto;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.DocumentService;
import vn.com.unit.cms.admin.all.service.FaqsCategoryService;
import vn.com.unit.cms.admin.all.service.FaqsService;
import vn.com.unit.cms.admin.all.util.CmsLanguageUtils;
import vn.com.unit.cms.admin.all.validator.FaqsEditValidator;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.document.dto.DocumentEditDto;
import vn.com.unit.cms.core.module.document.entity.Document;
import vn.com.unit.cms.core.module.faqs.dto.FaqsEditDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsLanguageDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchResultDto;
import vn.com.unit.cms.core.module.faqs.entity.Faqs;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.dto.JcaGroupEmailDto;
import vn.com.unit.core.enumdef.SendEmailTypeEnum;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.res.dto.DocumentAppRes;
import vn.com.unit.ep2p.core.service.AttachedFileService;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;
import vn.com.unit.sla.email.service.SlaEmailTemplateService;
import vn.com.unit.workflow.dto.JpmStatusCommonDto;
import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.service.JpmStatusCommonService;
import vn.com.unit.workflow.service.JpmStatusDeployService;

/**
 * FaqsController
 *
 * @author TaiTM
 * @version 01-00
 * @since 01-00
 */
@Controller
@RequestMapping("/{customerAlias}/" + UrlConst.FAQS)
public class FaqsController
        extends CmsCommonsSearchFilterProcessController<FaqsSearchDto, FaqsSearchResultDto, FaqsEditDto> {
    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private JcaEmailService jcaEmailService;

    @Autowired
    private JcaEmailTemplateService jcaEmailTemplateService;

    @Autowired
    private SlaEmailTemplateService slaEmailTemplateService;

    @Autowired
    private FaqsService faqsService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private FaqsEditValidator faqsEditValidator;

    @Autowired
    private FaqsCategoryService faqsCategoryService;

    @Autowired
    private MessageSource msg;

    @Autowired
    private AttachedFileService attachedFileService;

    @Autowired
    private JpmStatusCommonService jpmStatusCommonService;

    @Autowired
    private JpmStatusDeployService jpmStatusDeployService;


    private static final String VIEW_LIST_SORT = "views/CMS/all/faqs/faqs-list-sort.html";

    private static final String VIEW_TABLE_SORT = "views/CMS/all/faqs/faqs-table-sort.html";

    private static final String FAQS_EDIT = "views/CMS/all/faqs/faqs-edit.html";

    private static final String VIEW_LIST = "views/CMS/all/faqs/faqs-list.html";

    private static final String STATUS_COMMON_DRAFT = "000";


    private static final Logger logger = LoggerFactory.getLogger(FaqsController.class);

    private static final String TEMPLATE_MAIL_ACTION_MODERATOR_SEND = "TEMPLATE_FAQ_MAIL_ACTION_MODERATOR_SEND";
    private static final String TEMPLATE_MAIL_ACTION_MODERATIR_WITHDRAW = "TEMPLATE_FAQ_MAIL_ACTION_MODERATIR_WITHDRAW";
    private static final String TEMPLATE_MAIL_RETURN_BROWSER = "TEMPLATE_FAQ_MAIL_RETURN_BROWSER";
    private static final String TEMPLATE_MAIL_RETURN_EDIT = "TEMPLATE_FAQ_MAIL_RETURN_EDIT";
    private static final String TEMPLATE_MAIL_RETURN_REFUSE = "TEMPLATE_FAQ_MAIL_RETURN_REFUSE";
    private static final String TEMPLATE_MAIL_EDIT_HIDE = "TEMPLATE_FAQ_MAIL_ADMIN_HIDE";

    @RequestMapping(value = "/clone-data", method = RequestMethod.POST)
    public ModelAndView doCloneData(@PathVariable(required = false) String customerAlias,
                                    @RequestParam(value = "id", required = false) Long id, Locale locale) throws Exception {

        ModelAndView mav = new ModelAndView(viewEdit(customerAlias));
        if (!hasRoleForEdit(customerAlias)) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        FaqsEditDto editDto = getService().getEdit(id, customerAlias, locale);

        editDto.setId(null);
        editDto.setDocId(null);
        editDto.setCloneData(true);
        for (FaqsLanguageDto langDto : editDto.getFaqsLanguageList()) {
            langDto.setId(null);
        }

        setStatusForEditDto(editDto, locale);

        // init attach file plugin
        attachedFileService.initComponent(mav, getBusinessCode(customerAlias), editDto.getRefAttachment(), true,
                roleForAttachment(customerAlias));

        boolean isDetail = false;
        editDto.setUrl(getControllerURL(customerAlias).concat("/edit"));
        mav.addObject(objectEditName(), editDto);

        initDataEdit(mav, customerAlias, editDto, isDetail, locale);
        initDataProcess(mav, customerAlias, editDto, isDetail, locale);

        mav.addObject("customerAlias", customerAlias);
        mav.addObject("hasRoleForEdit", hasRoleForEdit(customerAlias));
        mav.addObject("id", null);

        return mav;
    }

    private void setStatusForEditDto(FaqsEditDto editDto, Locale locale) {
        if (editDto.getDocId() == null) {
            JpmStatusCommonDto statusCommon = jpmStatusCommonService.getStatusCommon(STATUS_COMMON_DRAFT,
                    locale.toString());
            if (statusCommon != null) {
                editDto.setStatusName(statusCommon.getStatusName());
                editDto.setStatusCode(statusCommon.getStatusCode());
            }
        } else {
            JpmStatusDeployDto status = jpmStatusDeployService.getStatusDeploy(editDto.getDocId(), locale.toString());
            if (status != null) {
                editDto.setStatusName(status.getStatusName());
                editDto.setStatusCode(status.getStatusCode());
            }
        }
        if (editDto.getId() == null) {
            JpmStatusCommonDto statusCommon = jpmStatusCommonService.getStatusCommon(STATUS_COMMON_DRAFT,
                    locale.toString());
            if (statusCommon != null) {
                editDto.setStatusName(statusCommon.getStatusName());
                editDto.setStatusCode(statusCommon.getStatusCode());
            }
        }
    }

    @Override
    public void customDateFormat(ModelAndView mav) {
        super.customDateFormat(mav);
        mav.addObject("dateFormat", "FULL_DATE");
    }

    @Override
    public String getTitlePageList(Locale locale) {
        return msg.getMessage("faqs.list.title", null, locale);
    }

    @Override
    public String getPermisionItem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DocumentWorkflowCommonService<FaqsEditDto, FaqsEditDto> getService() {
        return faqsService;
    }

    @Override
    public String getControllerURL(String customerAlias) {
        return customerAlias + UrlConst.ROOT + UrlConst.FAQS;
    }

    @Override
    public String getBusinessCode(String customerAlias) {
        String businessCode = CmsCommonConstant.BUSINESS_CMS;
        return businessCode;
    }

    @Override
    public String viewEdit(String customerAlias) {
        return FAQS_EDIT;
    }

    @Override
    public void sendEmailAction(FaqsEditDto editDto, Long buttonId) {
        String ROLE_CHECKER = "5";// nguoi duyet bai
        String ROLE_MAKER = "4"; // nguoi gui duyet
        String ROLE_ADMIN = "6"; // ADMIN

        String REFUSE = "REJECT";// tu choi
        String BROWSE = "APPROVE";// duyet

        String SEND_BROWSE = "SUBMIT";// gui duyet
        String WITHDRAW_POST = "RETURN";// rut lai

        //get mail top history
        DocumentAppRes<DocumentEditDto> docDto = new DocumentAppRes();
        Long docId = editDto.getDocId();
        if (docId != null) {
            try {
                docDto = getService().detail(docId);
            } catch (Exception e) {
            }
        }
        String mailReturnUser = docDto.getListProcessHistory().get(0).getCompletedEmail();
        String buttonName = documentService.getbutton(editDto.getButtonId(), editDto.getProcessDeployId());

        //get cateFaq
        String entity = faqsService.getCateFaq(editDto.getId(), editDto.getLanguageCode());

        String userName = UserProfileUtils.getUserNameLogin();

        List<String> emailCc = new ArrayList<>();
        JcaEmailDto jcaEmailDto = new JcaEmailDto();
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        final String senderAddress = systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT, null);
        jcaEmailDto.setSenderAddress(senderAddress);

        jcaEmailDto.setContentType("text/html; charset=utf-8");

        List<JcaGroupEmailDto> mailAdmin = jcaEmailTemplateService.findGourpMail(ROLE_ADMIN);

        JcaGroupEmailDto mailAgentCode = jcaEmailTemplateService.findUserNameAction(userName);

        Map<String, Map<String, Object>> mapDataEmailUser = new HashMap<>();

        if (StringUtils.equalsIgnoreCase(buttonName, SEND_BROWSE) || StringUtils.equalsIgnoreCase(buttonName, WITHDRAW_POST)) { 	
            List<JcaGroupEmailDto> mailCheckr = jcaEmailTemplateService.findGourpMail(ROLE_CHECKER);	

            for (JcaGroupEmailDto ls : mailCheckr) {	
                List<String> emailTo = new ArrayList<>();	
                Map<String, Object> mapDataEmail = new HashMap<>();	
                if (!StringUtils.isBlank(editDto.getId().toString())) { 	
                    mapDataEmail.put("id", editDto.getId().toString());	
                }
                if (!StringUtils.isBlank(ls.getUserName())) { 
                    mapDataEmail.put("fullname", ls.getUserName()); 
                } else {
                    mapDataEmail.put("fullname", " ");
                }          
                if (!StringUtils.isBlank(mailAgentCode.getUserName())) { 
                    mapDataEmail.put("user", mailAgentCode.getUserName());  
                } else { 
                    mapDataEmail.put("user", " "); 
                }   
                if (!StringUtils.isBlank(editDto.getCode())) {  
                    mapDataEmail.put("code", editDto.getCode());  
                } else {	 
                    mapDataEmail.put("code", " ");	 
                }	
                if (!StringUtils.isBlank(entity)) {	
                    mapDataEmail.put("categoryId", entity);	
                } else {	
                    mapDataEmail.put("categoryId", " ");	
                }	
                if (!StringUtils.isBlank(editDto.getFaqsLanguageList().get(0).getTitle())) {	
                    mapDataEmail.put("title", editDto.getFaqsLanguageList().get(0).getTitle());	
                } else {																		
                    mapDataEmail.put("title", " ");
                }
                if (editDto.getPostedDate() != null) {
                    mapDataEmail.put("postedDate", format.format(editDto.getPostedDate().getTime()));
                } else {
                    mapDataEmail.put("postedDate", " ");
                }
                if (editDto.getExpirationDate() != null) {
                    mapDataEmail.put("expirationDate", format.format(editDto.getExpirationDate().getTime()));
                } else {
                    mapDataEmail.put("expirationDate", " ");
                }
                if (!StringUtils.isBlank(editDto.getNote())) {
                    mapDataEmail.put("note", editDto.getNote());
                } else {
                    mapDataEmail.put("note", " ");
                }
                mapDataEmailUser.put(ls.getUserName(), mapDataEmail);

                emailTo.add(ls.getEmail());

                for (JcaGroupEmailDto lsd : mailAdmin) {
                    emailCc.add(lsd.getEmail());
                }

                emailCc.add(mailAgentCode.getEmail());

                String emailTemplaMail = null;
                if (StringUtils.equalsIgnoreCase(buttonName, SEND_BROWSE)) {
                    emailTemplaMail = TEMPLATE_MAIL_ACTION_MODERATOR_SEND;
                } else {
                    emailTemplaMail = TEMPLATE_MAIL_ACTION_MODERATIR_WITHDRAW;
                }
                JcaEmailTemplateDto emailTemplate = jcaEmailTemplateService
                        .findJcaEmailTemplateDtoByCode(emailTemplaMail);
                if (emailTemplate != null) {

                    jcaEmailDto.setSubject(emailTemplate.getTemplateSubject());
                    String content = slaEmailTemplateService.replaceParam(emailTemplate.getTemplateContent(), mapDataEmail);
                    jcaEmailDto.setEmailContent(content);
                    jcaEmailDto.setToString(String.join(",", emailTo));

                    jcaEmailDto.setToAddress(emailTo);
                    jcaEmailDto.setCcAddress(emailCc);

                    jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
                    jcaEmailService.sendEmail(jcaEmailDto);
                }
            }
        }

        if (StringUtils.equalsIgnoreCase(buttonName, REFUSE) || StringUtils.equalsIgnoreCase(buttonName, BROWSE)) {
            List<String> emailTo = new ArrayList<>();
            Map<String, Object> mapDataEmail = new HashMap<>();

            emailTo.add(mailReturnUser);
            for (JcaGroupEmailDto lsd : mailAdmin) {
                emailCc.add(lsd.getEmail());
            }

            if (!StringUtils.isBlank(editDto.getId().toString())) {
                mapDataEmail.put("id", editDto.getId().toString());
            }
            if (!StringUtils.isBlank(docDto.getListProcessHistory().get(0).getCompletedName())) {
                mapDataEmail.put("fullname", docDto.getListProcessHistory().get(0).getCompletedName());
            } else {
                mapDataEmail.put("fullname", " ");
            }
          	if (!StringUtils.isBlank(mailAgentCode.getUserName())) {
	             mapDataEmail.put("user", mailAgentCode.getUserName());
	        } else {
	             mapDataEmail.put("user", " ");
	        } 
            if (!StringUtils.isBlank(editDto.getCode())) {
                mapDataEmail.put("code", editDto.getCode());
            } else {
                mapDataEmail.put("code", " ");
            }
            if (!StringUtils.isBlank(entity)) {
                mapDataEmail.put("categoryId", entity);
            } else {
                mapDataEmail.put("categoryId", " ");
            }
            if (!StringUtils.isBlank(editDto.getFaqsLanguageList().get(0).getTitle())) {
                mapDataEmail.put("title", editDto.getFaqsLanguageList().get(0).getTitle());
            } else {
                mapDataEmail.put("title", " ");
            }
            if (editDto.getPostedDate() != null) {
                mapDataEmail.put("postedDate", format.format(editDto.getPostedDate().getTime()));
            } else {
                mapDataEmail.put("postedDate", " ");
            }
            if (editDto.getExpirationDate() != null) {
                mapDataEmail.put("expirationDate", format.format(editDto.getExpirationDate().getTime()));
            } else {
                mapDataEmail.put("expirationDate", " ");
            }
            if (!StringUtils.isBlank(editDto.getNote())) {
                mapDataEmail.put("note", editDto.getNote());
            } else {
                mapDataEmail.put("note", " ");
            }

            String emailTemplaMail = null;
            if (StringUtils.equalsIgnoreCase(buttonName, BROWSE)) {
                emailTemplaMail = TEMPLATE_MAIL_RETURN_BROWSER;
            } else {
                emailTemplaMail = TEMPLATE_MAIL_RETURN_REFUSE;
            }
            JcaEmailTemplateDto emailTemplate = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(emailTemplaMail);
            if (emailTemplate != null) {

                jcaEmailDto.setSubject(emailTemplate.getTemplateSubject());
                String content = slaEmailTemplateService.replaceParam(emailTemplate.getTemplateContent(), mapDataEmail);
                jcaEmailDto.setEmailContent(content);

                jcaEmailDto.setToString(String.join(",", emailTo));
                jcaEmailDto.setToAddress(emailTo);
                jcaEmailDto.setCcAddress(emailCc);
                jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
                jcaEmailService.sendEmail(jcaEmailDto);
            }
        }

    }


    @Override
    public void sendEmailEdit(FaqsEditDto editDto, Long userUpdated) {
        String ROLE_CHECKER = "5";// nguoi duyet bai
        String ROLE_MAKER = "4"; // nguoi gui duyet
        String ROLE_ADMIN = "6"; // ADMIN


        Faqs getCreateBy = faqsService.getlsDocument(editDto.getId());
        JcaGroupEmailDto mailAgentCode = jcaEmailTemplateService.findUserNameAction(getCreateBy.getCreateBy());


        //get cateFaq
        String entity = faqsService.getCateFaq(editDto.getId(), editDto.getFaqsLanguageList().get(0).getLanguageCode());
        List<JcaGroupEmailDto> mailAdmin = jcaEmailTemplateService.findGourpMail(ROLE_ADMIN);

        
        String userName = UserProfileUtils.getUserNameLogin();
        JcaGroupEmailDto user = jcaEmailTemplateService.findUserNameAction(userName);

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        List<String> emailTo = new ArrayList<>();
        List<String> emailCc1 = new ArrayList<>();
        List<String> emailCc2 = new ArrayList<>();

        JcaEmailDto jcaEmailDto = new JcaEmailDto();
        final String senderAddress = systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT, null);
        jcaEmailDto.setSenderAddress(senderAddress);
        jcaEmailDto.setContentType("text/html; charset=utf-8");

        Map<String, Map<String, Object>> mapDataEmailUser = new HashMap<>();

        List<JcaGroupEmailDto> mailCheckr = jcaEmailTemplateService.findGourpMail(ROLE_CHECKER);

        Map<String, Object> mapDataEmail = new HashMap<>();
        if (!StringUtils.isBlank(editDto.getId().toString())) {
            mapDataEmail.put("id", editDto.getId().toString());
        }
        if (!StringUtils.isBlank(getCreateBy.getCreateBy())){
            mapDataEmail.put("fullname", getCreateBy.getCreateBy());
        } else {
            mapDataEmail.put("fullname", " ");
        }
        if (!StringUtils.isBlank(user.getUserName())) {
	         mapDataEmail.put("user", user.getUserName());
	    } else {
	         mapDataEmail.put("user", " ");
	    } 
        if (!StringUtils.isBlank(editDto.getCode())) {
            mapDataEmail.put("code", editDto.getCode());
        } else {
            mapDataEmail.put("code", " ");
        }
        if (!StringUtils.isBlank(entity)) {
            mapDataEmail.put("categoryId", entity);
        } else {
            mapDataEmail.put("categoryId", " ");
        }
        if (!StringUtils.isBlank(editDto.getFaqsLanguageList().get(0).getTitle())) {
            mapDataEmail.put("title", editDto.getFaqsLanguageList().get(0).getTitle());
        } else {
            mapDataEmail.put("title", " ");
        }
        if (editDto.getPostedDate() != null) {
            mapDataEmail.put("postedDate", format.format(editDto.getPostedDate().getTime()));
        } else {
            mapDataEmail.put("postedDate", " ");
        }
        if (editDto.getExpirationDate() != null) {
            mapDataEmail.put("expirationDate", format.format(editDto.getExpirationDate().getTime()));
        } else {
            mapDataEmail.put("expirationDate", " ");
        }
        if (!StringUtils.isBlank(editDto.getNote())) {
            mapDataEmail.put("note", editDto.getNote());
        } else {
            mapDataEmail.put("note", " ");
        }

        emailTo.add(mailAgentCode.getEmail());

        for (JcaGroupEmailDto lsd : mailAdmin) {
            emailCc1.add(lsd.getEmail());
        }
        for (JcaGroupEmailDto ls : mailCheckr) {
            emailCc2.add(ls.getEmail());
        }

        emailCc1.addAll(emailCc2);
        Faqs data = faqsService.findById(editDto.getId());

        String template = null;
        if (data.isEnabled() != editDto.isEnabled()) {
            template = TEMPLATE_MAIL_EDIT_HIDE;
        } else {
            template = TEMPLATE_MAIL_RETURN_EDIT;
        }
        JcaEmailTemplateDto emailTemplate = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(template);
        if (emailTemplate != null) {

            jcaEmailDto.setSubject(emailTemplate.getTemplateSubject());
            String content = slaEmailTemplateService.replaceParam(emailTemplate.getTemplateContent(), mapDataEmail);
            jcaEmailDto.setEmailContent(content);
            jcaEmailDto.setToString(String.join(",", emailTo));
            jcaEmailDto.setToAddress(emailTo);
            jcaEmailDto.setCcAddress(emailCc1);

            jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
            jcaEmailService.sendEmail(jcaEmailDto);
        }

    }


    @Override
    public String objectEditName() {
        return "faqsModel";
    }

    @Override
    public String firstStepInProcess(String customerAlias) {
        return "taskSubmit";
    }

    @Override
    public String roleForAttachment(String customerAlias) {
        return "";
    }

    @Override
    public void initDataEdit(ModelAndView mav, String customerAlias, FaqsEditDto editDto, boolean isDetail,
                             Locale locale) {
        mav.addObject("customerAlias", editDto.getCustomerAlias());

        String lang = locale.toString();

        List<FaqsCategoryDto> faqsCategoryList = faqsCategoryService.findAllFaqsCategoryByCustomerId(lang,
                editDto.getCustomerId());
        mav.addObject("faqsCategoryList", faqsCategoryList);

        editDto.setIndexLangActive(0);

        CmsLanguageUtils.initLanguageList(mav);

        String url = getControllerURL(customerAlias);
        if (editDto.isCloneData()) {
            editDto.setCode(null);
            url = url.concat("/edit");
        } else {
            if (!editDto.getHasEdit()) {
                url = url.concat("/detail");
            } else {
                url = url.concat("/edit");
            }
            if (editDto.getId() != null) {
                url = url.concat("?id=" + editDto.getId());
            }
        }
        editDto.setUrl(url);

        if (CmsCommonConstant.STATUS_COMMON_FINISHED.equals(editDto.getStatusCode()) && showButtonUpdate(editDto)) {
            editDto.setHasUpdateData(true);
        }

        mav.addObject("editDto", editDto);
    }

    @Override
    public void validate(FaqsEditDto editDto, BindingResult bindingResult) {
        faqsEditValidator.validate(editDto, bindingResult);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public void initScreenList(ModelAndView mav, FaqsSearchDto searchDto, Locale locale, String customerAlias) {
        // TODO Auto-generated method stub
        String username = UserProfileUtils.getUserNameLogin();
        boolean hasRoleMaker = documentService.isUserHasRoleMaker(username);
        mav.addObject("hasRoleMaker", hasRoleMaker);
    }

    @Override
    public void initScreenListSort(ModelAndView mav, FaqsSearchDto searchDto, Locale locale) {
        List<FaqsCategoryDto> faqsCategoryList = faqsCategoryService.findAllFaqsCategoryByCustomerId(locale.toString(),
                null);
        mav.addObject("faqsCategoryList", faqsCategoryList);
    }

    @Override
    public void setParamSearchForListSort(FaqsSearchDto searchDto, Locale locale) {
        super.setParamSearchForListSort(searchDto, locale);
        searchDto.setUsername(UserProfileUtils.getUserNameLogin());
    }

    @Override
    public void customRedirectAttributesForSaveListSort(RedirectAttributes redirectAttributes, FaqsSearchDto searchDto,
                                                        Locale locale) {
        super.customRedirectAttributesForSaveListSort(redirectAttributes, searchDto, locale);

        redirectAttributes.addAttribute("categoryId", searchDto.getCategoryId());
    }

    @Override
    public String getUrlByCustomerAlias(String customerAlias) {
        return customerAlias + UrlConst.ROOT.concat(UrlConst.FAQS);
    }

    @Override
    public Class<FaqsSearchResultDto> getClassSearchResult() {
        return FaqsSearchResultDto.class;
    }

    @Override
    public String getFunctionCode() {
        return "M_FAQS";
    }

    @Override
    public String viewListSort(String customerAlias) {
        return VIEW_LIST_SORT;
    }

    @Override
    public String viewListSortTable(String customerAlias) {
        return VIEW_TABLE_SORT;
    }

    @Override
    public boolean hasRoleForList(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_FAQS);
    }

    @Override
    public boolean hasRoleForEdit(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_FAQS_EDIT.concat(ConstantCore.COLON_EDIT));
    }

    @Override
    public boolean hasRoleSpecialForEdit(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.CMS_ROLE_ADMIN.concat(ConstantCore.COLON_EDIT));
    }

    @Override
    public boolean hasRoleForDetail(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_FAQS);
    }

    @Override
    public boolean showButtonClone(FaqsEditDto editDto) {
        String ROLE_MAKER = "4";
        String user = UserProfileUtils.getUserNameLogin();
        List<String> lstRoleName = documentService.getListRoleNameByUserName(UserProfileUtils.getUserNameLogin());
        boolean hasRoleMaker = lstRoleName == null ? false : lstRoleName.contains(ROLE_MAKER);
        if ((CmsCommonConstant.STATUS_COMMON_FINISHED.equals(editDto.getStatusCode())
                || "994".equals(editDto.getStatusCode())) && hasRoleMaker) {
            return true;
        }
        return super.showButtonClone(editDto);
    }

    @Override
    public boolean showButtonUpdate(FaqsEditDto editDto) {
        if (UserProfileUtils.hasRole(CmsRoleConstant.CMS_ROLE_ADMIN.concat(CoreConstant.COLON_EDIT))) {
            return true;
        }
        return super.showButtonUpdate(editDto);
    }

    @Override
    public CmsCommonSearchFillterService<FaqsSearchDto, FaqsSearchResultDto, FaqsEditDto> getCmsCommonSearchFillterService() {
        return faqsService;
    }

    @Override
    public String viewList(String customerAlias) {
        return VIEW_LIST;
    }

    public boolean showButtonImport(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_FAQS_IMPORT.concat(ConstantCore.COLON_EDIT));
    }
}
