package vn.com.unit.process.admin.sla.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.entity.JcaNotiTemplate;
import vn.com.unit.core.security.UserProfileUtils;
//import vn.com.unit.core.service.JcaNotiTemplateService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.admin.service.AppNotiTemplateService;
//import vn.com.unit.ep2p.admin.service.AppNotiTemplateService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.sla.dto.SlaInfoDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaSearchDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaSettingDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaStepDto;
import vn.com.unit.ep2p.admin.sla.enumdef.AppSlaTimeTypeEnum;
import vn.com.unit.ep2p.admin.sla.enumdef.AppUnitTimeTypeEnum;
import vn.com.unit.ep2p.admin.sla.service.CalendarTypeAppService;
//import vn.com.unit.ep2p.admin.validators.SlaInfoValidator;
import vn.com.unit.ep2p.constant.CommonConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.process.admin.sla.service.SlaInfoService;
import vn.com.unit.process.admin.sla.service.SlaSettingService;
import vn.com.unit.process.admin.sla.service.SlaStepService;
import vn.com.unit.process.admin.validators.SlaInfoValidator;
import vn.com.unit.process.workflow.dto.AppBusinessDto;
import vn.com.unit.process.workflow.dto.AppProcessDeployDto;
import vn.com.unit.process.workflow.service.AppBusinessService;
import vn.com.unit.process.workflow.service.AppButtonDeployService;
import vn.com.unit.process.workflow.service.AppProcessDeployService;
//import vn.com.unit.ep2p.workflow.dto.AppBusinessDto;
//import vn.com.unit.ep2p.workflow.dto.AppProcessDeployDto;
//import vn.com.unit.ep2p.workflow.service.AppBusinessService;
//import vn.com.unit.ep2p.workflow.service.AppButtonDeployService;
//import vn.com.unit.ep2p.workflow.service.AppProcessDeployService;
import vn.com.unit.sla.dto.SlaCalendarTypeDto;
import vn.com.unit.sla.service.SlaInvoledTypeService;
import vn.com.unit.workflow.dto.JpmSlaInfoDto;

/**
 * SlaController
 * 
 * @author TuyenTD
 * @version 01-00
 * @since 01-00
 */
@Controller
@RequestMapping(UrlConst.SLA)
public class SlaController {

    @Autowired
    private MessageSource msgSrc;

    @Autowired
    private SlaInfoService slaInfoService;

    @Autowired
    private AppBusinessService appBusinessService;

    @Autowired
    private AppProcessDeployService appProcessDeployService;

//    @Autowired
//    private ConstantDisplayService constDispService;

    @Autowired
    private SlaStepService slaStepService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SlaSettingService slaSettingService;

//    @Autowired
//    private AppButtonDeployService buttonDeployService;

    @Autowired
    private CalendarTypeAppService calendarTypeAppService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    AppButtonDeployService appButtonDeployService;

    @Autowired
    SystemConfig systemConfig;

//    @Autowired
//    private SlaValidator validator;

    @Autowired
    private SlaInfoValidator slaInfoValidator;
    
    @Autowired
    private AppNotiTemplateService appNotiTemplateService;
    
//    @Autowired
//    private JcaNotiTemplateService appNotiTemplateService;
    
    @Autowired
    private SlaInvoledTypeService slaInvoledTypeService;

    // @Autowired
    // private HandoverService handoverService;

    private static final Logger logger = LoggerFactory.getLogger(SlaController.class);

    private static final String VIEW_LIST = "/views/sla/sla-list.html";

    private static final String VIEW_NOTIFICATION_LIST = "/views/sla/sla-notification-list.html";

    private static final String VIEW_REMINDER_LIST = "/views/sla/sla-reminder-list.html";

    private static final String VIEW_TABLE_LIST = "/views/sla/sla-table.html";

    private static final String VIEW_EDIT = "/views/sla/sla-edit.html";

    private static final String VIEW_ALERT_SETTING = "/views/sla/sla-alert-setting.html";

    private static final String VIEW_ESCALATE_LIST = "/views/sla/sla-escalate-list.html";

//    private static final String VIEW_STEP_TABLE = "/views/sla/sla-step-detail-table2.html";

    private static final String SCREEN_FUNCTION_CODE = RoleConstant.SLA;

    private static final String OBJ_PAGE_WRAPPER = "pageWrapper";

    private static final String OBJ_SEARCH = "searchDto";

    private static final String OBJ_SLA = "slaInfoDto";

    private static final String OBJ_STEP = "slaStepDto";

    private static final String OBJ_COMPANY = "companyList";

//    private static final String OBJ_BUSINESS = "businessList";
//
//    private static final String OBJ_PROCESS = "processList";

    private static final String OBJ_TYPE = "slaTypeList";

    private static final String OBJ_EMAIL_TO = "userListOfEmailTo";

    private static final String OBJ_EMAIL_CC = "userListOfEmailCC";

    private static final String OBJ_TIME = "unitTimeList";

    private static final String OBJ_STEP_ID = "slaStepId";

//    private static final String OBJ_IS_TRANFER = "ObjIsTranfer";

    private static final String NO_EMAIL_TO_LIST = "noEmailToList";

    private static final String NO_EMAIL_CC_LIST = "noEmailCCList";

    private static final String RE_EMAIL_TO_LIST = "reEmailToList";

    private static final String RE_EMAIL_CC_LIST = "reEmailCCList";

    private static final String ES_EMAIL_TO_LIST = "esEmailToList";

    private static final String ES_EMAIL_CC_LIST = "esEmailCCList";

    private static final String SETTING_URL = "/alert-setting";

    private static final String NOTIFICATION_URL = "/add-notification";

    private static final String REMINDER_URL = "/add-reminder";

    private static final String ESCALATE_URL = "/add-escalate";

    private static final String AJAX_LIST_URL = "/ajaxList";

    private static final String AJAX_EMAIL_URL = "/ajax/getEmailTemplateByCompanyId";
    
    private static final String AJAX_NOTI_URL = "/ajax/get-noti-template-by-companyid";

    private static final String AJAX_CALENDAR_URL = "/ajax/getCalendarTypeListByCompanyId";

    private static final String AJAX_PROCESS_URL = "/ajax/getProcessListByBusinessId";

    private static final String AJAX_BUSINESS_URL = "/ajax/getBusinessListByCompanyId";

    private static final String AJAX_EMAIL_TO_URL = "/ajax/getEmailToListBycompanyId";

    private static final String AJAX_EMAIL_CC_URL = "/ajax/getEmailCCListBycompanyId";

    /**
     * getList
     * 
     * @param searchDto
     * @param pageSizeParam
     * @param page
     * @param locale
     * @return
     * @author TuyenTD
     * @throws DetailException
     */
    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getList(@ModelAttribute(value = OBJ_SEARCH) SlaSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale) throws DetailException {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // Initialize view
        ModelAndView mav = new ModelAndView(VIEW_LIST);

        // Get company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject(OBJ_COMPANY, companyList);
        searchDto.setCompanyId(UserProfileUtils.getCompanyId());

        // Set pageSize
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));

        // Search SLA Info return PageWrapper
        PageWrapper<JpmSlaInfoDto> pageWrapper = slaInfoService.search(searchDto, page, pageSize);

        // If search No data
//        if (pageWrapper.getCountAll() == 0) {
//
//            // Initialize message list
//            MessageList messageList = new MessageList(Message.INFO);
//            String msgInfo = msgSrc.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
//            messageList.add(msgInfo);
//            mav.addObject(ConstantCore.MSG_LIST, messageList);
//        }

        // set pageWrapper data to view
        mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
        mav.addObject(OBJ_SEARCH, searchDto);
        return mav;
    }

    /**
     * postAjaxList
     * 
     * @param searchDto
     * @param pageSizeParam
     * @param page
     * @param locale
     * @return
     * @author TuyenTD
     * @throws DetailException
     */
    @RequestMapping(value = AJAX_LIST_URL, method = RequestMethod.POST)
    public ModelAndView postAjaxList(@ModelAttribute(value = OBJ_SEARCH) SlaSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale) throws DetailException {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // Initialize view
        ModelAndView mav = new ModelAndView(VIEW_TABLE_LIST);

        // // Check if selected company set companyId for searchDto
        // if (searchDto.getCompanyId() != null && searchDto.getCompanyId() != 0) {
        // List<Long> lstCompanyId = new ArrayList<>();
        // lstCompanyId.add(searchDto.getCompanyId());
        // searchDto.setCompanyIdList(lstCompanyId);
        // } else {
        // // Set companyId list
        // // boolean isAdmin = UserProfileUtils.isCompanyAdmin();
        // // if(isAdmin) {
        // // searchDto.setCompanyIdList(null);
        // // } else {
        // // List<Long> lstCompanyId = UserProfileUtils.getCompanyIdList();
        // // searchDto.setCompanyIdList(lstCompanyId);
        // // }
        // }

        // Initialize pageSize
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));

        // Search SLA Info return pageWrapper
        PageWrapper<JpmSlaInfoDto> pageWrapper = slaInfoService.search(searchDto, page, pageSize);

        // If search no data
        if (pageWrapper.getCountAll() == 0) {
            // Initialize message list
            MessageList messageList = new MessageList(Message.INFO);
            String msgInfo = msgSrc.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }

        // Add pageWrapper to view
        mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);

        mav.addObject(OBJ_SEARCH, searchDto);

        return mav;
    }

    /**
     * getEdit
     * 
     * @param id
     * @param slaInfoDto
     * @param locale
     * @return
     * @author TuyenTD
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView getEdit(@RequestParam(value = "id", required = false) Long id, Locale locale) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // Initialize view
        ModelAndView mav = new ModelAndView(VIEW_EDIT);

        // try {
        // // If slaInfoId not null
        // if (id != null) {
        // // Get SLA Info by Id
        // slaInfoDto = slaInfoService.findById(id, locale);
        //
        // // Initialize message list
        // MessageList message = slaInfoDto.getMessageList();
        // slaInfoDto.setMessageList(message);
        // }
        // } catch (Exception e) {
        // logger.error("##getEdit##", e);
        // // slaInfoDto.addMessage(Message.ERROR, e.getMessage());
        // }
        // mav.addObject(OBJ_SLA, slaInfoDto);
        // try {
        // this.initDataForEdit(mav, slaInfoDto, locale);
        // } catch (Exception e) {
        // logger.error("##getEdit##", e);
        // // slaInfoDto.addMessage(Message.ERROR, e.getMessage());
        // }
        SlaInfoDto slaInfoDto = new SlaInfoDto();
        if (null != id) {
            slaInfoDto = slaInfoService.findById(id, locale);
        }
        initDataForSlaInfoEdit(mav, slaInfoDto, locale);
        mav.addObject(OBJ_SLA, slaInfoDto);
        return mav;
    }

    private void initDataForSlaInfoEdit(ModelAndView mav, SlaInfoDto slaInfoDto, Locale locale) {
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject(OBJ_COMPANY, companyList);
        if (null == slaInfoDto.getCompanyId()) {
            slaInfoDto.setCompanyId(UserProfileUtils.getCompanyId());
        }

        if (null != slaInfoDto.getBusinessId()) {
            AppBusinessDto appBusinessDto = appBusinessService.getAppBusinessDtoById(slaInfoDto.getBusinessId());
            if (null != appBusinessDto) {
                slaInfoDto.setBusinessName(appBusinessDto.getName());
            }
        }

        if (null != slaInfoDto.getProcessDeployId()) {
            AppProcessDeployDto appProcessDeployDto = appProcessDeployService.getJpmProcessDeployDtoById(slaInfoDto.getProcessDeployId());
            if (null != appProcessDeployDto) {
                String processDeployName = appProcessDeployDto.getName().concat(ConstantCore.HYPHEN).concat(ConstantCore.VERSION)
                        .concat(String.valueOf(appProcessDeployDto.getMajorVersion())).concat(ConstantCore.DOT)
                        .concat(String.valueOf(appProcessDeployDto.getMinorVersion()));
                slaInfoDto.setProcessDeployName(processDeployName);
            }
        }

        if (null != slaInfoDto.getSlaCalendarTypeId()) {
            SlaCalendarTypeDto slaCalendarTypeDto;
            try {
                slaCalendarTypeDto = calendarTypeAppService.getCalendarTypeDtoById(slaInfoDto.getSlaCalendarTypeId());
                if (null != slaCalendarTypeDto) {
                    slaInfoDto.setSlaCalendarType(slaCalendarTypeDto.getName());
                }
            } catch (DetailException e) {
                e.printStackTrace();
            }
        }
        
        List<Select2Dto> slaTypeList = AppSlaTimeTypeEnum.getSelect2DtoList();
        mav.addObject(OBJ_TYPE, slaTypeList);

    }

    /**
     * postEdit
     * 
     * @param id
     * @param slaInfoDto
     * @param locale
     * @return
     * @author TuyenTD
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    public ModelAndView postEdit(@ModelAttribute(value = OBJ_SLA) SlaInfoDto slaInfoDto, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Locale locale) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // Initialize view
        ModelAndView mav = new ModelAndView(VIEW_EDIT);
        // try {
        // // Initialize message list
        // MessageList messageList = new MessageList(Message.ERROR);
        // String message = CommonStringUtil.EMPTY;
        // // Check if SLA info exist
        // Long id = slaInfoDto.getId();
        // Long companyId = slaInfoDto.getCompanyId();
        // Long processId = slaInfoDto.getProcessDeployId();
        // String name = slaInfoDto.getName();
        // boolean validate = slaInfoService.checkSlaInfoExist(companyId, name);
        // boolean hasProcess = slaInfoService.checkProcessExist(processId);
        // // If exist
        // if (validate) {
        // // Add message exist to message list
        // message = msgSrc.getMessage("message.error.sla.name.exist", null, locale);
        // messageList.add(message);
        // if (id != null) {
        // slaInfoDto = slaInfoService.findById(id, locale);
        // }
        // } else if (hasProcess && id == null) {
        // message = msgSrc.getMessage("message.error.sla.process.exist", null, locale);
        // } else {
        // // Save SLA Info
        // slaInfoService.saveSlaInfoDto(slaInfoDto);
        //
        // // Initialize success message
        // messageList = new MessageList(Message.SUCCESS);
        // String msgSuccess = null;
        //
        // if (id != null) {
        // // Add message update success
        // slaInfoDto = slaInfoService.findById(id, locale);
        // msgSuccess = msgSrc.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        // } else {
        // // Add message create success
        // msgSuccess = msgSrc.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
        // }
        //
        // messageList.add(msgSuccess);
        // String viewName = UrlConst.REDIRECT.concat(UrlConst.TEMPLATE_EMAIL).concat(UrlConst.EDIT);
        // Long slaInfoId = slaInfoDto.getId();
        // redirectAttributes.addAttribute("id", slaInfoId);
        // redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        // mav.setViewName(viewName);
        // return mav;
        // }
        // slaInfoDto.setMessageList(messageList);
        //
        // } catch (Exception e) {
        // logger.error("##postEdit##", e);
        // //slaInfoDto.addMessage(Message.ERROR, e.getMessage());
        // }
        //
        // mav.addObject(OBJ_SLA, slaInfoDto);
        // try {
        // this.initDataForEdit(mav, slaInfoDto, locale);
        // } catch (Exception e) {
        // logger.error("##postEdit##", e);
        // //slaInfoDto.addMessage(Message.ERROR, e.getMessage());
        // }

        // Init message list
        MessageList messageList = new MessageList(Message.ERROR);
        String msgInfo = CommonStringUtil.EMPTY;

        slaInfoValidator.validate(slaInfoDto, bindingResult);
        if (!bindingResult.hasErrors()) {
            try {
                slaInfoService.saveSlaInfoDto(slaInfoDto);
                messageList.setStatus(Message.SUCCESS);
                msgInfo = msgSrc.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);
                messageList.add(msgInfo);
                String viewName = UrlConst.REDIRECT.concat(UrlConst.SLA).concat(UrlConst.EDIT);
                Long slaInfoId = slaInfoDto.getId();
                redirectAttributes.addAttribute("id", slaInfoId);
                redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
                mav.setViewName(viewName);
                return mav;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // init data
        this.initDataForSlaInfoEdit(mav, slaInfoDto, locale);
        msgInfo = msgSrc.getMessage(ConstantCore.MSG_FAIL_SAVE, null, locale);
        messageList.add(msgInfo);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject(OBJ_SLA, slaInfoDto);
        return mav;

    }

    /**
     * initDataForEdit
     * 
     * @param mav
     * @param slaInfoDto
     */
//    private void initDataForEdit(ModelAndView mav, SlaInfoDto slaInfoDto, Locale locale) {
//
//        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
//                UserProfileUtils.isCompanyAdmin());
//        mav.addObject(OBJ_COMPANY, companyList);
//        if (null == slaInfoDto.getCompanyId()) {
//            slaInfoDto.setCompanyId(UserProfileUtils.getCompanyId());
//        }

        // // Get company Id
        // Long companyId = slaInfoDto.getCompanyId();
        // // Get company list
        // List<Long> list = UserProfileUtils.getCompanyIdList();
        // boolean isAdmin = UserProfileUtils.isCompanyAdmin();
        // List<Select2Dto> companyList = companyService.findByListCompanyId(list, isAdmin);
        // mav.addObject(OBJ_COMPANY, companyList);
        // if (companyList != null && companyList.size() == 1 && Objects.isNull(slaInfoDto.getId())) {
        // companyId = new Long(companyList.get(0).getId());
        // slaInfoDto.setCompanyId(companyId);
        // }
        //
        // // Initialize business list by company id
        // List<AppBusinessDto> businessList = jpmBusinessService.findJpmBusinessDtoListByCompanyId(companyId);
        // mav.addObject(OBJ_BUSINESS, businessList);
        //
        // // Initialize calendar list
        // List<Select2Dto> calendarList = calendarTypeAppService.getCalendarTypeListByYearnCompany(0, companyId);
        // mav.addObject("calendarList", calendarList);
        //
        // // If edit SLA and business selected
        // if (slaInfoDto.getBusinessId() != null) {
        // Long busId = slaInfoDto.getBusinessId();
        // List<Select2Dto> lstProcess = appProcessDeployService.getJpmProcessDtoTypeSelect2DtoByBusinessId(busId, locale.getLanguage());
        // mav.addObject(OBJ_PROCESS, lstProcess);
        // }
        // Check if user not administrator, manage only one company
        // if (slaInfoDto.getId() != null || (companyList != null && companyList.size() <= 1)) {
        // // Initialize alert type list
        // List<String> codes = Arrays.asList("1", "2", "3", "4", "5", "6");
        // String lang = locale.getLanguage().toUpperCase();
        // List<ConstantDisplay> slaTypeList = constDispService.findByTypeAndCode("M16", codes, lang);
        // mav.addObject(OBJ_TYPE, slaTypeList);
        // }
//    }

    /**
     * getAlertSetting
     * 
     * @param slaStepDto
     * @param slaStepId
     * @param locale
     * @return
     * @author TuyenTD
     */
    @RequestMapping(value = SETTING_URL, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView getAlertSetting(@RequestParam(value = OBJ_STEP_ID, required = true) Long slaStepId, Locale locale) {
        ModelAndView mav = new ModelAndView(VIEW_ALERT_SETTING);
        MessageList messageList = null;
        SlaStepDto slaStepDto = new SlaStepDto();
        try {
            String lang = locale.getLanguage();
            slaStepDto = slaStepService.getSlaStepDtoById(slaStepId, lang);
        } catch (Exception e) {
            messageList = new MessageList(Message.ERROR);
            messageList.add(e.getMessage());
        }
        if (slaStepDto.getSlaDueTime() == null) {
            slaStepDto.setSlaDueTime(0L);
        }
        // try {
        // initAlertSettingforStep(mav, slaStepDto, locale);
        // this.initDataForAlertSetting(mav, slaStepDto, locale);
        // } catch (Exception e) {
        // messageList = new MessageList(Message.ERROR);
        // messageList.add(e.getMessage());
        // }
        // slaStepDto.setMessageList(messageList);
        
        initDataForAlertSetting(mav, slaStepDto, locale);
        
        try {
            initAlertSettingforStep(mav, slaStepDto, locale);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        mav.addObject(OBJ_STEP, slaStepDto);
        return mav;
    }

    /**
     * saveAlertSetting
     * 
     * @param slaStepDto
     * @param slaStepId
     * @param locale
     * @return
     * @author TuyenTD
     */
    @RequestMapping(value = SETTING_URL, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView saveAlertSetting(@Valid @ModelAttribute(value = OBJ_STEP) SlaStepDto slaStepDto, BindingResult bindingResult, 
            RedirectAttributes redirectAttributes, HttpServletRequest request, Locale locale) {
        ModelAndView mav = new ModelAndView(VIEW_ALERT_SETTING);
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msgSrc.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        try {
            slaSettingService.saveSLASetting(slaStepDto, locale);
            
            messageList.add(msgInfo);
            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
            
            String viewName = UrlConst.REDIRECT.concat(UrlConst.SLA).concat(SETTING_URL); 
            Long id = slaStepDto.getJpmSlaConfigId();
            redirectAttributes.addAttribute("slaStepId", id);
            mav.setViewName(viewName);
            
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            msgInfo = msgSrc.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            initDataForAlertSetting(mav, slaStepDto, locale);
            try {
                initAlertSettingforStep(mav, slaStepDto, locale);
            } catch (Exception e1) {
                e.printStackTrace();
            }
        }
        
        return mav;
    }

    private void initAlertSettingforStep(ModelAndView mav, SlaStepDto slaStepDto, Locale locale) throws Exception {
        List<SlaSettingDto> notificationList = slaStepDto.getNotificationList();
        List<SlaSettingDto> reminderList = slaStepDto.getReminderList();
        List<SlaSettingDto> escalateList = slaStepDto.getEscalateList();
        slaStepDto.setNoNotificationMessage(msgSrc.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale));
        slaStepDto.setNoReminderMessage(msgSrc.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale));
        slaStepDto.setNoEscalateMessage(msgSrc.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale));
        if (!CollectionUtils.isEmpty(notificationList)) {
            int size = notificationList.size();
            for (int i = 0; i < size; i++) {
                String name = notificationList.get(i).getEmailTemplateName();
                Long templateId = notificationList.get(i).getEmailTemplateId();
                name = slaInfoService.getEmailTemplateNameById(templateId);
                notificationList.get(i).setEmailTemplateName(name);
                
                Long notiTemplateId = notificationList.get(i).getNotiTemplateId();
                if(null != notiTemplateId) {
                    JcaNotiTemplate jcaNotiTemplate = appNotiTemplateService.findOne(notiTemplateId);
                    String notiTemplate = null != jcaNotiTemplate ? jcaNotiTemplate.getName() : null;
                    notificationList.get(i).setNotiTemplateName(notiTemplate);
                }
            }

            List<Select2Dto> to = setDerfaultEmailTo(locale);
            List<Select2Dto> cc = setDerfaultEmailCC(locale);
            Select2ResultDto emailTo = setEmailToAndCC(notificationList);
            to.addAll(emailTo.getResults());
            mav.addObject(NO_EMAIL_TO_LIST, to);
            cc.addAll(emailTo.getResults());
            mav.addObject(NO_EMAIL_CC_LIST, cc);
        }
        if (!CollectionUtils.isEmpty(reminderList)) {
            int size = reminderList.size();
            for (int i = 0; i < size; i++) {
                String name = reminderList.get(i).getEmailTemplateName();
                Long templateId = reminderList.get(i).getEmailTemplateId();
                name = slaInfoService.getEmailTemplateNameById(templateId);
                reminderList.get(i).setEmailTemplateName(name);
                
                Long notiTemplateId = reminderList.get(i).getNotiTemplateId();
                if(null != notiTemplateId) {
                    JcaNotiTemplate jcaNotiTemplate = appNotiTemplateService.findOne(notiTemplateId);
                    String notiTemplate = null != jcaNotiTemplate ? jcaNotiTemplate.getName() : null;
                    reminderList.get(i).setNotiTemplateName(notiTemplate);
                }
            }
            List<Select2Dto> to = setDerfaultEmailTo(locale);
            Select2ResultDto emailTo = setEmailToAndCC(reminderList);
            to.addAll(emailTo.getResults());
            mav.addObject(RE_EMAIL_TO_LIST, to);
            List<Select2Dto> cc = setDerfaultEmailCC(locale);
            cc.addAll(emailTo.getResults());
            mav.addObject(RE_EMAIL_CC_LIST, cc);
        }
        if (!CollectionUtils.isEmpty(escalateList)) {
            int size = escalateList.size();
            for (int i = 0; i < size; i++) {
                String name = escalateList.get(i).getEmailTemplateName();
                Long templateId = escalateList.get(i).getEmailTemplateId();
                name = slaInfoService.getEmailTemplateNameById(templateId);
                escalateList.get(i).setEmailTemplateName(name);
                
                Long notiTemplateId = escalateList.get(i).getNotiTemplateId();
                if(null != notiTemplateId) {
                    JcaNotiTemplate jcaNotiTemplate = appNotiTemplateService.findOne(notiTemplateId);
                    String notiTemplate = null != jcaNotiTemplate ? jcaNotiTemplate.getName() : null;
                    escalateList.get(i).setNotiTemplateName(notiTemplate);
                }
            }

            List<Select2Dto> to = setDerfaultEmailTo(locale);
            List<Select2Dto> cc = setDerfaultEmailCC(locale);
            Select2ResultDto emailTo = setEmailToAndCC(escalateList);
            to.addAll(emailTo.getResults());
            mav.addObject(ES_EMAIL_TO_LIST, to);
            cc.addAll(emailTo.getResults());
            mav.addObject(ES_EMAIL_CC_LIST, cc);
        }
        
        List<Select2Dto> unitTimeList = AppUnitTimeTypeEnum.getSelect2DtoList();
        mav.addObject(OBJ_TIME, unitTimeList);
    }

    private void initSettingTranferforStep(ModelAndView mav, SlaStepDto slaStepDto, Locale locale) throws Exception {
        // // Get company list
        // Long processId = slaStepDto.getProcessDeployId()
        // String code = slaStepDto.getStepCode();
        // String lang = locale.getLanguage().toUpperCase();
        // List<Select2Dto> buttonList = appButtonDeployService.getButtonActions(processId, code, lang);
        // /** set mav ObjIsTranfer -- TaiTT */
        // boolean ObjIsTranfer = false;
        // /** check haveBtnReference */
        // String stepType = slaStepDto.getStepType() == null ? StringUtils.EMPTY : slaStepDto.getStepType();
        // if (null != buttonList && !buttonList.isEmpty()) {
        // List<Select2Dto> buttonListRefer = buttonList.stream()
        // .filter(e -> ButtonType.REFERENCE.toString().equals(e.getName().toString())).collect(Collectors.toList());
        // if ((null != buttonListRefer && !buttonListRefer.isEmpty()) || ConstantCore.STEP_TYPE_END_EVENT.equals(stepType)) {
        // ObjIsTranfer = true;
        // }
        // } else if (ConstantCore.STEP_TYPE_END_EVENT.equals(stepType)) {
        // ObjIsTranfer = true;
        // }
        // slaStepDto.setObjIsTranfer(ObjIsTranfer);
    }

    /**
     * @param mav
     * @param slaStepDto
     * @param locale
     * @throws Exception
     */
    private void initDataForAlertSetting(ModelAndView mav, SlaStepDto slaStepDto, Locale locale) {
//        Long companyId = slaStepDto.getCompanyId();
        List<Select2Dto> slaTypeList = AppSlaTimeTypeEnum.getSelect2DtoList();
        mav.addObject(OBJ_TYPE, slaTypeList);
    }

    @RequestMapping(value = NOTIFICATION_URL, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView addNotificationByStep(@ModelAttribute(value = OBJ_STEP) SlaStepDto slaStepDto, Locale locale) {
        ModelAndView mav = new ModelAndView(VIEW_NOTIFICATION_LIST);
        List<SlaSettingDto> notificationList = slaStepDto.getNotificationList();
        List<Long> toList = new ArrayList<>();
        List<Long> ccList = new ArrayList<>();
        Long emailDefault = Long.valueOf(0);
        toList.add(emailDefault);
        ccList.add(emailDefault);
        if (notificationList == null) {
            notificationList = new ArrayList<>();
        } else {
            for (SlaSettingDto item : notificationList) {
                Long templateId = item.getEmailTemplateId();
                String name = slaInfoService.getEmailTemplateNameById(templateId);
                item.setEmailTemplateName(name);
            }
        }

        SlaSettingDto settingDto = new SlaSettingDto();

        String assignName = msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_TO_LABEL, null, locale);
        String requestName = msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_REQUEST_LABEL, null, locale);
        List<Select2Dto> mailTo = setEmailForInitial(toList, locale, assignName);
        List<Select2Dto> mailCC = setEmailForInitial(ccList, locale, requestName);

        settingDto.setLstEmailToId(toList);
        settingDto.setLstEmailCcId(ccList);

        notificationList.add(settingDto);
        slaStepDto.setNotificationList(notificationList);
        MessageList messageList = null;
        try {
            Long slaId = slaStepDto.getSlaInfoId();
            this.initAlertSettingforStep(mav, slaStepDto, locale);
            this.initDataForAddAlert(mav, locale, slaId, Boolean.TRUE);
            this.initSettingTranferforStep(mav, slaStepDto, locale);
        } catch (Exception e) {
            messageList = new MessageList(Message.ERROR);
            messageList.add(e.getMessage());
        }
        slaStepDto.setMessageList(messageList);
        mav.addObject(NO_EMAIL_TO_LIST, mailTo);
        mav.addObject(NO_EMAIL_CC_LIST, mailCC);
        mav.addObject(OBJ_STEP, slaStepDto);
        return mav;
    }

    /**
     * addReminderByStep
     * 
     * @param slaStepDto
     * @param slaStepId
     * @param locale
     * @return
     * @author TuyenTD
     */
    @RequestMapping(value = REMINDER_URL, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView addReminderByStep(@ModelAttribute(value = OBJ_STEP) SlaStepDto slaStepDto, Locale locale) {
        ModelAndView mav = new ModelAndView(VIEW_REMINDER_LIST);
        List<SlaSettingDto> reminderList = slaStepDto.getReminderList();
        List<Long> list = new ArrayList<>();
        list.add(Long.valueOf(0));
        if (reminderList == null) {
            reminderList = new ArrayList<>();
        } else {
            for (SlaSettingDto item : reminderList) {
                Long templateId = item.getEmailTemplateId();
                String name = slaInfoService.getEmailTemplateNameById(templateId);
                item.setEmailTemplateName(name);
            }
        }

        String assignName = msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_TO_LABEL, null, locale);
        String requestName = msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_REQUEST_LABEL, null, locale);
        List<Select2Dto> mailTo = setEmailForInitial(list, locale, assignName);
        List<Select2Dto> mailCC = setEmailForInitial(list, locale, requestName);

        SlaSettingDto settingDto = new SlaSettingDto();
        // settingDto.setAlertTime(Double.valueOf(0));
        settingDto.setLstEmailToId(list);
        settingDto.setLstEmailCcId(list);
        reminderList.add(settingDto);
        slaStepDto.setReminderList(reminderList);
        MessageList messageList = null;
        try {
            Long slaId = slaStepDto.getSlaInfoId();
            this.initAlertSettingforStep(mav, slaStepDto, locale);
            this.initDataForAddAlert(mav, locale, slaId, Boolean.FALSE);
        } catch (Exception e) {
            messageList = new MessageList(Message.ERROR);
            messageList.add(e.getMessage());
        }
        slaStepDto.setMessageList(messageList);
        mav.addObject(RE_EMAIL_TO_LIST, mailTo);
        mav.addObject(RE_EMAIL_CC_LIST, mailCC);
        mav.addObject(OBJ_STEP, slaStepDto);
        return mav;
    }

    /**
     * @param mav
     * @param locale
     * @throws Exception
     */
    private void initDataForAddAlert(ModelAndView mav, Locale locale, Long slaId, boolean isNotifcation) throws Exception {
//        List<ConstantDisplay> unitTimeList = new ArrayList<>();

        SlaInfoDto sla = slaInfoService.findById(slaId, locale);
        Long companyId = sla == null ? null : sla.getCompanyId();
        List<JcaAccountDto> userList = companyId == null ? accountService.getAllAcountActive(Boolean.TRUE)
                : accountService.getListAccountByCompanyId(companyId);
        Map<Long, String> userStrList = new HashMap<>();
        Map<Long, String> userListOfEmailCC = new HashMap<>();
        if (userList != null && !userList.isEmpty()) {
            for (JcaAccountDto dto : userList) {
                Long key = dto.getUserId();
                String email = dto.getEmail();
                userStrList.put(key, email);
            }
        }

        List <Select2Dto> slaInvoledTypeList = slaInvoledTypeService.getSelect2DtoListByLang(locale.getLanguage().toUpperCase());
        
        userListOfEmailCC.putAll(userStrList);
        if(CommonCollectionUtil.isNotEmpty(slaInvoledTypeList)) {
            for (Select2Dto select2Dto : slaInvoledTypeList) {
                userStrList.put(Long.valueOf(select2Dto.getId()), select2Dto.getName());
                userListOfEmailCC.put(Long.valueOf(select2Dto.getId()), select2Dto.getName());
            }
        }
        // email list of email to
//        userStrList.put(Long.valueOf(-1), msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_TO_LABEL, null, locale));
//        userStrList.put(Long.valueOf(-2), msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_REQUEST_LABEL, null, locale));
        mav.addObject(OBJ_EMAIL_TO, userStrList);
        // email list of email CC
//        userListOfEmailCC.put(Long.valueOf(-1), msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_CC_LABEL, null, locale));
//        userListOfEmailCC.put(Long.valueOf(-2), msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_REQUEST_LABEL, null, locale));

        if (!isNotifcation) {
//            String lang = locale.getLanguage().toUpperCase();
//            List<String> codes = Arrays.asList("0", "1", "2");
//            unitTimeList = constDispService.findByTypeAndCode("M18", codes, lang);
            List<Select2Dto> unitTimeList = AppUnitTimeTypeEnum.getSelect2DtoList();
            mav.addObject(OBJ_TIME, unitTimeList);
        }

//        userListOfEmailCC.put(Long.valueOf(-2), msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_RELATION_LABEL, null, locale));
        mav.addObject(OBJ_EMAIL_CC, userListOfEmailCC);

    }

    /**
     * addEscalateByStep
     * 
     * @param slaStepDto
     * @param slaStepId
     * @param locale
     * @return
     * @author TuyenTD
     */
    @RequestMapping(value = ESCALATE_URL, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView addEscalateByStep(@ModelAttribute(value = OBJ_STEP) SlaStepDto slaStepDto, Locale locale) {
        ModelAndView mav = new ModelAndView(VIEW_ESCALATE_LIST);
        List<SlaSettingDto> escalateList = slaStepDto.getEscalateList();
        List<Long> list = new ArrayList<>();
        list.add(Long.valueOf(0));
        if (escalateList == null) {
            escalateList = new ArrayList<>();
        } else {
            for (SlaSettingDto item : escalateList) {
                Long templateId = item.getEmailTemplateId();
                String name = slaInfoService.getEmailTemplateNameById(templateId);
                item.setEmailTemplateName(name);
            }
        }

        String assignName = msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_TO_LABEL, null, locale);
        String requestName = msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_REQUEST_LABEL, null, locale);
        List<Select2Dto> mailTo = setEmailForInitial(list, locale, assignName);
        List<Select2Dto> mailCC = setEmailForInitial(list, locale, requestName);

        SlaSettingDto settingDto = new SlaSettingDto();
        // settingDto.setAlertTime(Double.valueOf(0));
        settingDto.setLstEmailToId(list);
        settingDto.setLstEmailCcId(list);
        escalateList.add(settingDto);
        slaStepDto.setEscalateList(escalateList);
        MessageList messageList = null;
        try {
            Long slaId = slaStepDto.getSlaInfoId();
            this.initAlertSettingforStep(mav, slaStepDto, locale);
            this.initDataForAddAlert(mav, locale, slaId, Boolean.FALSE);
        } catch (Exception e) {
            messageList = new MessageList(Message.ERROR);
            messageList.add(e.getMessage());
        }
        slaStepDto.setMessageList(messageList);
        mav.addObject(ES_EMAIL_TO_LIST, mailTo);
        mav.addObject(ES_EMAIL_CC_LIST, mailCC);
        mav.addObject(OBJ_STEP, slaStepDto);
        return mav;
    }

    @RequestMapping(value = UrlConst.SLA_RESET_STEP, method = RequestMethod.POST)
    public ModelAndView resetSLAStep(@RequestParam(value = "id", required = false) Long id, 
            @RequestParam(value = "slaId", required = false) Long slaInfoId, RedirectAttributes redirectAttributes, HttpServletRequest request, Locale locale) {

        // Security for this page.
//        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }

        ModelAndView mav = new ModelAndView(VIEW_EDIT);
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msgSrc.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);
        
        try {
            slaStepService.deleteStepSetting(id);
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            msgInfo = msgSrc.getMessage(ConstantCore.MSG_FAIL_SAVE, null, locale);
        }

        messageList.add(msgInfo);
        String viewName = UrlConst.REDIRECT.concat(UrlConst.SLA).concat(UrlConst.EDIT);
        redirectAttributes.addAttribute("id", slaInfoId);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        
//        try {
//            if (id != null) {
//                slaStepService.deleteStepSetting(id);
//                MessageList message = slaInfoDto.getMessageList();
//                slaInfoDto = slaInfoService.findById(slaId, locale);
//                slaInfoDto.setMessageList(message);
//
//            }
//        } catch (Exception e) {
//            logger.error("##resetSLAStep##", e);
//            // slaInfoDto.addMessage(Message.ERROR, e.getMessage());
//        }
//        try {
//            this.initDataForEdit(mav, slaInfoDto, locale);
//        } catch (Exception e) {
//            logger.error("##resetSLAStep##", e);
//            // slaInfoDto.addMessage(Message.ERROR, e.getMessage());
//        }
//        mav.addObject(OBJ_SLA, slaInfoDto);
        
        mav.setViewName(viewName);
        
        return mav;
    }

    @RequestMapping(value = UrlConst.DELETE, method = { RequestMethod.POST })
    public ModelAndView deleteSla(@RequestParam(value = "id", required = true) Long id,
            RedirectAttributes redirectAttributes, HttpServletRequest request, Locale locale) {
        ModelAndView mav = new ModelAndView(VIEW_LIST);
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msgSrc.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);

        // Add company list
        // List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
        // UserProfileUtils.isCompanyAdmin());
        // mav.addObject(OBJ_COMPANY, companyList);

//        List<Long> lstCompanyId = new ArrayList<>();
        // if (!CollectionUtils.isEmpty(companyList)) {
        // List<String> strings = companyList.stream().map(Select2Dto::getId).collect(Collectors.toList());
        // lstCompanyId = strings.stream().map(Long::valueOf).collect(Collectors.toList());
        // if(companyList.size() == 1)
        // {
        // List<JpmBusinessDto> businessList =
        // jpmBusinessService.findJpmBusinessDtoListByCompanyId(Long.parseLong(companyList.get(0).getId()));
        // // Set business list to view
        // mav.addObject(OBJ_BUSINESS, businessList);
        // }
        // } else {
        // lstCompanyId.add(UserProfileUtils.getCompanyId());
        // }

        // searchDto.setCompanyIdList(lstCompanyId);
        try {
            slaInfoService.deleteSla(id, locale);
//            int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(AppSystemConfig.PAGING_SIZE));
//            int page = pageParam.orElse(1);
//            PageWrapper<JpmSlaInfoDto> pageWrapper = slaInfoService.search(searchDto, page, pageSize);
//            mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            msgInfo = msgSrc.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
        }
        
        messageList.add(msgInfo);
        String viewName = UrlConst.REDIRECT.concat(UrlConst.SLA).concat(UrlConst.LIST);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        mav.setViewName(viewName);
//        messageList.add(msgInfo);
//        mav.addObject(ConstantCore.MSG_LIST, messageList);
        return mav;
    }

    @RequestMapping(value = AJAX_BUSINESS_URL, method = RequestMethod.POST)
    @ResponseBody
    public Object getBusinessListForSelect(@RequestParam(required = false) Long companyId) {
        try {
            return slaInfoService.getBusinessListForSelect(companyId);
        } catch (Exception e) {
            logger.error("##getBusinessListByCompanyIdd##", e);
        }
        return ObjectUtils.NULL;
    }

    @RequestMapping(value = AJAX_PROCESS_URL, method = RequestMethod.POST)
    @ResponseBody
    public Object getProcessListForSelect(@RequestParam(required = false) Long companyId, Locale locale) {
        try {
            return slaInfoService.getProcessListForSelect(companyId, locale.getLanguage());
        } catch (Exception e) {
            logger.error("##getProcessListByCompanyId##", e);
        }
        return ObjectUtils.NULL;
    }

    @RequestMapping(value = AJAX_CALENDAR_URL, method = RequestMethod.POST)
    @ResponseBody
    public Object getCalendarTypeListForSelect(@RequestParam(required = false) Long companyId) {
        try {
            return slaInfoService.getCalendarTypeListForSelect(companyId);
        } catch (Exception e) {
            logger.error("##getCalendarTypeListByCompanyId##", e);
        }
        return ObjectUtils.NULL;
    }

    @RequestMapping(value = AJAX_NOTI_URL, method = RequestMethod.POST)
    @ResponseBody
    public Object getNotiTemplateListForSelect(@RequestParam(value = "key", required = false) String key,
            @RequestParam(required = false) Long slaId, Locale locale) {
        try {
            SlaInfoDto sla = slaInfoService.findById(slaId, locale);
            Long companyId = sla == null ? null : sla.getCompanyId();
            Select2ResultDto obj = new Select2ResultDto();
            List<Select2Dto> result = appNotiTemplateService.getSelect2DtoList(key, companyId, true); 
            if(result == null) {
                result = new ArrayList<>();
            }
            obj.setTotal(result.size());
            obj.setResults(result);
            return obj;
        } catch (Exception e) {
            logger.error("##getCalendarTypeListByCompanyId##", e);
        }
        return ObjectUtils.NULL;
    }
    
    @RequestMapping(value = AJAX_EMAIL_URL, method = RequestMethod.POST)
    @ResponseBody
    public Object getEmailTemplateListForSelect(@RequestParam(value = "key", required = false) String key,
            @RequestParam(required = false) Long slaId, Locale locale) {
        try {
            SlaInfoDto sla = slaInfoService.findById(slaId, locale);
            Long companyId = sla == null ? null : sla.getCompanyId();
            return slaInfoService.getEmailTamplateList(key, companyId);
        } catch (Exception e) {
            logger.error("##getCalendarTypeListByCompanyId##", e);
        }
        return ObjectUtils.NULL;
    }

    @RequestMapping(value = AJAX_EMAIL_TO_URL, method = RequestMethod.POST)
    @ResponseBody
    public Object getEmailToListBycompanyId(@RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "busCode", required = false) String busCode,
            @RequestParam(value = "isPaging", required = false) Boolean isPaging, Locale locale) {
        try {
            Select2ResultDto obj = accountService.getAllEmailofAllCompany(null, key, isPaging);
            List<Select2Dto> defaultList = setDerfaultEmailTo(locale);
            if (StringUtils.isNotBlank(key)) {
                String sKey = key.toUpperCase().replaceAll("[ÀÁÂÃÅÄÂẦẤẬ]", "A").replaceAll("[Đ]", "D").replaceAll("[ÈÉẺẼẸËỀẾÊỂỄỆ]", "E")
                        .replaceAll("[ÌÍÎÏỈĨỊ]", "I").replaceAll("[ƠỚỜỠỢỞ]", "O").replaceAll("[ƯỨỪỮỰ]", "U");
                defaultList = defaultList.stream()
                        .filter(da -> da.getText().toUpperCase().replaceAll("[ÀÁÂÃÅÄÂẦẤẬ]", "A").replaceAll("[Đ]", "D")
                                .replaceAll("[ÈÉẺẼẸËỀẾÊỂỄỆ]", "E").replaceAll("[ÌÍÎÏỈĨỊ]", "I").replaceAll("[ƠỚỜỠỢỞ]", "O")
                                .replaceAll("[ƯỨỪỮỰ]", "U").contains(sKey))
                        .collect(Collectors.toList());
            }
            obj.getResults().addAll(defaultList);

            return obj;
        } catch (Exception e) {
            logger.error("##getEmailToListBycompanyId##", e);
        }
        return ObjectUtils.NULL;
    }

    @RequestMapping(value = AJAX_EMAIL_CC_URL, method = RequestMethod.POST)
    @ResponseBody
    public Object getEmailCCListBycompanyId(@RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "busCode", required = false) String busCode,
            @RequestParam(value = "isPaging", required = false) Boolean isPaging, Locale locale) {
        try {
            Select2ResultDto obj = accountService.getAllEmailofAllCompany(null, key, isPaging);
            List<Select2Dto> defaultList = setDerfaultEmailCC(locale);
            if (StringUtils.isNotBlank(key)) {
                String sKey = key.toUpperCase().replaceAll("[ÀÁÂÃÅÄÂẦẤẬ]", "A").replaceAll("[Đ]", "D").replaceAll("[ÈÉẺẼẸËỀẾÊỂỄỆ]", "E")
                        .replaceAll("[ÌÍÎÏỈĨỊ]", "I").replaceAll("[ƠỚỜỠỢỞ]", "O").replaceAll("[ƯỨỪỮỰ]", "U");
                defaultList = defaultList.stream()
                        .filter(da -> da.getText().toUpperCase().replaceAll("[ÀÁÂÃÅÄ]", "A").replaceAll("[Đ]", "D")
                                .replaceAll("[ÈÉẺẼẸËỀẾÊỂỄỆ]", "E").replaceAll("[ÌÍÎÏ]", "I").replaceAll("[ƠỚỜỠỢỞ]", "O")
                                .replaceAll("[ƯỨỪỮỰỬ]", "U").contains(sKey))
                        .collect(Collectors.toList());
            }
            obj.getResults().addAll(defaultList);
            return obj;
        } catch (Exception e) {
            logger.error("##getEmailCCListBycompanyId##", e);
        }
        return ObjectUtils.NULL;
    }

    /**
     * @param locale
     * @return
     * @throws NullPointerException
     */
    private List<Select2Dto> setDerfaultEmailTo(Locale locale) throws NullPointerException {
//        List<Select2Dto> list = new ArrayList<>();
//        String assignName = msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_TO_LABEL, null, locale);
//        String requestName = msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_REQUEST_LABEL, null, locale);
//        String transferName = msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_TRANSFER_LABEL, null, locale);
//        Select2Dto requester = new Select2Dto();
//        Select2Dto assignee = new Select2Dto();
//        Select2Dto transfer = new Select2Dto();
//        assignee.setId(String.valueOf(0));
//        assignee.setName(assignName);
//        assignee.setText(assignName);
//        requester.setId(String.valueOf(-1));
//        requester.setName(requestName);
//        requester.setText(requestName);
//        transfer.setId(String.valueOf(-2));
//        transfer.setName(transferName);
//        transfer.setText(transferName);
//        list.add(assignee);
//        list.add(requester);
//        list.add(transfer);
//        return list;
        return slaInvoledTypeService.getSelect2DtoListByLang(locale.getLanguage().toUpperCase());
        
    }

    /**
     * @param locale
     * @return
     * @throws NullPointerException
     */
    private List<Select2Dto> setDerfaultEmailCC(Locale locale) throws NullPointerException {
//        List<Select2Dto> list = new ArrayList<>();
//        String ccName = msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_CC_LABEL, null, locale);
//        String requestName = msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_REQUEST_LABEL, null, locale);
//        String relatorName = msgSrc.getMessage(CommonConstant.MSG_SLA_MAIL_RELATION_LABEL, null, locale);
//        Select2Dto supervisor = new Select2Dto();
//        Select2Dto requester = new Select2Dto();
//        Select2Dto relator = new Select2Dto();
//        supervisor.setId(String.valueOf(0));
//        supervisor.setName(ccName);
//        supervisor.setText(ccName);
//        requester.setId(String.valueOf(-1));
//        requester.setName(requestName);
//        requester.setText(requestName);
//        relator.setId(String.valueOf(-2));
//        relator.setName(relatorName);
//        relator.setText(relatorName);
//        list.add(supervisor);
//        list.add(requester);
//        list.add(relator);
//        return list;
        return slaInvoledTypeService.getSelect2DtoListByLang(locale.getLanguage().toUpperCase());
    }

    /**
     * @param values
     * @param locale
     * @param name
     * @return
     */
    private List<Select2Dto> setEmailForInitial(List<Long> values, Locale locale, String name) {
        List<Select2Dto> result = new ArrayList<>();
        for (Long item : values) {
            Select2Dto emailDto = new Select2Dto();
            emailDto.setId(item.toString());
            emailDto.setText(name);
            result.add(emailDto);
        }
        return result;
    }

    /**
     * @param notificationList
     * @return
     * @throws NullPointerException
     * @throws SQLException
     */
    private Select2ResultDto setEmailToAndCC(List<SlaSettingDto> list) throws NullPointerException, SQLException {
        Set<Long> total = new HashSet<>();
        for (SlaSettingDto dto : list) {
            List<Long> itemTo = dto.getLstEmailToId();
            List<Long> itemCC = dto.getLstEmailCcId();
            if (itemTo != null && !CollectionUtils.isEmpty(itemTo)) {
                total.addAll(itemTo);
            }
            if (itemCC != null && !CollectionUtils.isEmpty(itemCC)) {
                total.addAll(itemCC);
            }

        }
        return accountService.getAllEmailofAllCompany(new ArrayList<>(total), null, true);
    }

}
