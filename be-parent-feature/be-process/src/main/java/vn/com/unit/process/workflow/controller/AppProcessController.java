/*******************************************************************************
 * Class        JpmProcessController
 * Created date 2019/06/10
 * Lasted date  2019/06/10
 * Author       KhoaNA
 * Change log   2019/06/10 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.controller;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.constant.DataTypeConstant;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.common.utils.CommonSearchUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.AppLanguageService;
import vn.com.unit.core.workflow.enumdef.ProcessType;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.ep2p.admin.constant.ButtonTypeConstant;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.ComponentDto;
import vn.com.unit.ep2p.admin.enumdef.ConstantDisplayType;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
//import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
import vn.com.unit.ep2p.admin.utils.URLUtil;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.utils.ConditionSearchUtils;
import vn.com.unit.process.workflow.dto.AppBusinessDto;
import vn.com.unit.process.workflow.dto.AppButtonDto;
import vn.com.unit.process.workflow.dto.AppFunctionDto;
import vn.com.unit.process.workflow.dto.AppParamDto;
import vn.com.unit.process.workflow.dto.AppProcessDeployDto;
import vn.com.unit.process.workflow.dto.AppProcessDmnDto;
import vn.com.unit.process.workflow.dto.AppProcessDto;
import vn.com.unit.process.workflow.dto.AppProcessSearchDto;
import vn.com.unit.process.workflow.dto.AppStatusDto;
import vn.com.unit.process.workflow.dto.AppStepDto;
import vn.com.unit.process.workflow.dto.ImportProcessDto;
import vn.com.unit.process.workflow.enumdef.JpmProcessSearchEnum;
import vn.com.unit.process.workflow.service.AppBusinessService;
import vn.com.unit.process.workflow.service.AppButtonService;
import vn.com.unit.process.workflow.service.AppFunctionService;
import vn.com.unit.process.workflow.service.AppParamConfigStepService;
import vn.com.unit.process.workflow.service.AppParamService;
import vn.com.unit.process.workflow.service.AppProcessDeployService;
import vn.com.unit.process.workflow.service.AppProcessDmnService;
import vn.com.unit.process.workflow.service.AppProcessService;
import vn.com.unit.process.workflow.service.AppStatusService;
import vn.com.unit.process.workflow.service.AppStepService;
import vn.com.unit.process.workflow.validators.AppButtonValidator;
import vn.com.unit.process.workflow.validators.AppFunctionValidator;
import vn.com.unit.process.workflow.validators.AppParamValidator;
import vn.com.unit.process.workflow.validators.AppProcessValidator;
import vn.com.unit.process.workflow.validators.AppStatusValidator;
import vn.com.unit.process.workflow.validators.AppStepValidator;
//import vn.com.unit.ep2p.workflow.service.AppBusinessService;
//import vn.com.unit.ep2p.workflow.service.AppButtonService;
//import vn.com.unit.ep2p.workflow.service.AppFunctionService;
//import vn.com.unit.ep2p.workflow.service.AppParamConfigStepService;
//import vn.com.unit.ep2p.workflow.service.AppParamService;
//import vn.com.unit.ep2p.workflow.service.AppProcessDeployService;
//import vn.com.unit.ep2p.workflow.service.AppProcessDmnService;
//import vn.com.unit.ep2p.workflow.service.AppProcessService;
//import vn.com.unit.ep2p.workflow.service.AppStatusService;
//import vn.com.unit.ep2p.workflow.service.AppStepService;
//import vn.com.unit.ep2p.workflow.validators.AppButtonValidator;
//import vn.com.unit.ep2p.workflow.validators.AppFunctionValidator;
//import vn.com.unit.ep2p.workflow.validators.AppParamValidator;
//import vn.com.unit.ep2p.workflow.validators.AppProcessValidator;
//import vn.com.unit.ep2p.workflow.validators.AppStatusValidator;
//import vn.com.unit.ep2p.workflow.validators.AppStepValidator;
import vn.com.unit.workflow.dto.JpmButtonLangDto;
import vn.com.unit.workflow.dto.JpmParamConfigDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;
import vn.com.unit.workflow.dto.JpmProcessLangDto;
import vn.com.unit.workflow.dto.JpmStatusLangDto;
import vn.com.unit.workflow.dto.JpmStepLangDto;
import vn.com.unit.workflow.entity.JpmPermission;
import vn.com.unit.workflow.entity.JpmStep;
import vn.com.unit.workflow.enumdef.StepKind;
import vn.com.unit.workflow.service.JpmStatusCommonService;

/**
 * JpmProcessController
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Controller
@RequestMapping(value = UrlConst.JPM_PROCESS)
public class AppProcessController {

    @Autowired
    private MessageSource msg;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private AppProcessService appProcessService;

    @Autowired
    private AppBusinessService appBusinessService;

    @Autowired
    private AppProcessValidator appProcessValidator;

    @Autowired
    private AppButtonValidator appButtonValidator;

    @Autowired
    private DataTypeConstant dataTypeConstant;

    @Autowired
    private AppLanguageService languageService;

    @Autowired
    private AppParamService appParamService;

    @Autowired
    private AppStatusService appStatusService;

    @Autowired
    private AppButtonService appButtonService;

    @Autowired
    private AppFunctionService appFunctionService;

    @Autowired
    private AppParamValidator appParamValidator;

    @Autowired
    private AppStatusValidator appStatusValidator;

    @Autowired
    private AppFunctionValidator appFunctionValidator;

    @Autowired
    private AppStepService appStepService;

    @Autowired
    private AppStepValidator appStepValidator;

//    @Autowired
//    private ComponentService componentService;

    @Autowired
    private AppProcessDeployService appProcessDeployService;

    @Autowired
    private AppParamConfigStepService appParamConfigStepService;

//    @Autowired
//    private AppStatusDeployService appStatusDeployService;

    @Autowired
    private JpmStatusCommonService jpmStatusCommonService;

    @Autowired
    private ConstantDisplayService constantDisplayService;

    @Autowired
    private AppProcessDmnService appProcessDmnService;

    /** MAV */
    private static final String MAV_JPM_PROCESS_EDIT = "/views/jpm-process/jpm-process-edit.html";
    private static final String MAV_JPM_PROCESS_LIST = "/views/jpm-process/jpm-process-list.html";
    private static final String MAV_JPM_PROCESS_TABLE = "/views/jpm-process/jpm-process-table.html";

    private static final String MAV_JPM_PROCESS_PARAM = "/views/jpm-process/jpm-process-param.html";
    private static final String MAV_JPM_PROCESS_PARAM_POPUP = "/views/jpm-process/jpm-process-param-popup-body.html";

    private static final String MAV_JPM_PROCESS_STATUS = "/views/jpm-process/jpm-process-status.html";
    private static final String MAV_JPM_PROCESS_STATUS_POPUP = "/views/jpm-process/jpm-process-status-popup-body.html";

    private static final String MAV_JPM_PROCESS_BUTTON = "/views/jpm-process/jpm-process-button.html";
    private static final String MAV_JPM_PROCESS_BUTTON_POPUP = "/views/jpm-process/jpm-process-button-popup-body.html";

    private static final String MAV_JPM_PROCESS_FUNCTION = "/views/jpm-process/jpm-process-function.html";
    private static final String MAV_JPM_PROCESS_FUNCTION_POPUP = "/views/jpm-process/jpm-process-function-popup-body.html";

    private static final String MAV_JPM_PROCESS_STEP = "/views/jpm-process/jpm-process-step.html";
    private static final String MAV_JPM_PROCESS_STEP_POPUP = "/views/jpm-process/jpm-process-step-popup-body.html";

    private static final String MAV_JPM_PROCESS_DMN = "/views/jpm-process/jpm-process-dmn.html";
    private static final String MAV_JPM_PROCESS_DMN_POPUP = "/views/jpm-process/jpm-process-dmn-popup-body.html";

    private static final String MAV_JPM_PROCESS_CLONE_POPUP = "/views/jpm-process/jpm-process-clone-popup-body.html";
    private static final String MAV_JPM_PROCESS_IMPORT_POPUP = "/views/jpm-process/jpm-process-import-popup-body.html";

    /** Params */
    private static final String ID = "id";
    private static final String OBJECT_DTO = "objectDto";
    private static final String URL = "url";
    private static final String MSG_FLAG = "msgFlag";

    private static final String COMPANY_LIST = "companyList";
    private static final String BUSINESS_LIST = "businessList";
    private static final String OBJ_SEARCH = "searchDto";
    private static final String OBJ_PAGE_WRAPPER = "pageWrapper";
    private static final String OBJ_PARAM = "paramDto";
    private static final String OBJ_DATA_TYPE = "dataTypes";
    private static final String OBJ_STATUS = "statusDto";
    private static final String OBJ_CLONE_DTO = "objectCloneDto";
    private static final String OBJ_BUTTON = "buttonDto";
    private static final String OBJ_BUTTON_TYPE = "buttonTypes";
    private static final String OBJ_BUTTON_CLASS = "buttonClasses";
    private static final String OBJ_FUNCTION = "functionDto";
    private static final String OBJ_STEP = "stepDto";
    private static final String FORM_FIELD_NAME_LIST = "listFormFieldName";
    private static final String LANGUAGE_LIST = "languageList";
    private static final String STATUS_LIST = "statusList";
    private static final String BUTTON_LIST = "buttonList";
    private static final String FUNCTION_LIST = "functionList";
    private static final String STEP_KIND_LIST = "stepKindList";
    private static final String STATUS_COMMON_LIST = "statusCommons";

    private static final String SCREEN_FUNCTION_CODE = RoleConstant.PROCESS;

    private static final Logger logger = LoggerFactory.getLogger(AppProcessController.class);

    // Model mapper
    ModelMapper modelMapper = new ModelMapper();

    /**
     * dateBinder
     * 
     * @param binder
     * @param request
     * @param locale
     * @author KhoaNA
     */
    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        // The date format to parse or output your dates
        String patternDate = (String) request.getSession().getAttribute(ConstantCore.FORMAT_DATE);
        if (patternDate == null) {
            patternDate = ConstantCore.FORMAT_DATE_DDMMYYY;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
    }

    /**
     * Get JpmProcess list
     * 
     * @return ModelAndView
     * @author KhoaNA
     * @throws DetailException
     */
    @RequestMapping(value = { UrlConst.ROOT, UrlConst.LIST }, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getProcessList(@ModelAttribute(value = OBJ_SEARCH) AppProcessSearchDto search,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale, HttpServletRequest request) throws DetailException {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_LIST);
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject(COMPANY_LIST, companyList);
        search.setCompanyId(UserProfileUtils.getCompanyId());

        // set init search
        CommonSearchUtil.setSearchSelect(JpmProcessSearchEnum.class, mav);

        // Init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        
        // Session Search
        ConditionSearchUtils<AppProcessSearchDto> searchUtil = new ConditionSearchUtils<AppProcessSearchDto>();
        String[] urlContains = new String[] { "jpm-process/add", "jpm-process/edit", "jpm-process/detail", "jpm-process/list" };
        search = searchUtil.getConditionSearch(this.getClass(), search, urlContains, request, page, pageSize);
        pageSize = Optional.ofNullable(search.getPageSize()).orElse(pageSize);
        page = Optional.ofNullable(search.getPage()).orElse(page);
        // Get List
        PageWrapper<AppProcessDto> pageWrapper = appProcessService.search(page, pageSize, search);

        // Object mav
        mav.addObject(OBJ_SEARCH, search);
        mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);

        return mav;
    }

    @PostMapping(value = UrlConst.AJAXLIST)
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = OBJ_SEARCH) AppProcessSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale, HttpServletRequest request) throws DetailException {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_TABLE);

        // Init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        // Get List
        PageWrapper<AppProcessDto> pageWrapper = appProcessService.search(page, pageSize, searchDto);
        // Session Search
        ConditionSearchUtils<AppProcessSearchDto> searchUtil = new ConditionSearchUtils<AppProcessSearchDto>();
        searchUtil.setConditionSearch(request, this.getClass(), searchDto, page, pageSize);
        mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
        return mav;
    }

    /**
     * Get AppProcessDto
     * 
     * @param id
     * @param model
     * @return String
     * @author KhoaNA
     */
    @GetMapping(value = UrlConst.EDIT)
    public ModelAndView getJpmProcessDto(@RequestParam(value = ID, required = false) Long id, ModelMap model, HttpServletResponse response,
            Locale locale) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_EDIT);

        AppProcessDto appProcessDto = new AppProcessDto();
        if (id != null) {
            appProcessDto = appProcessService.getAppProcessDtoById(id);
            // Security for data
            // if (null == jpmProcessDto || !UserProfileUtils.hasRoleForCompany(jpmProcessDto.getCompanyId())) {
            // return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
            // }
            /*
             * if( jpmProcessDto == null ) { throw new BusinessException("JmpProcess not found with id=" + id); }
             */
        } else {
            appProcessDto.setCompanyId(UserProfileUtils.getCompanyId());
            List<LanguageDto> languageList = languageService.getLanguageDtoList();
            List<JpmProcessLangDto> processLangs = new ArrayList<>();
            languageList.stream().forEach(lang -> {
                JpmProcessLangDto processLangDto = new JpmProcessLangDto();
                processLangDto.setLangCode(lang.getCode());
                processLangDto.setLangId(lang.getId());
                processLangs.add(processLangDto);
            });
            appProcessDto.setProcessLangs(processLangs);
        }

        // URL ajax redirect
        StringBuilder urlRedirect = new StringBuilder(UrlConst.JPM_PROCESS.substring(1).concat(UrlConst.EDIT));
        if (id != null) {
            urlRedirect.append(URLUtil.buildParamWithPrefix(true, ID, id));
        }

        Long processId = appProcessDto.getId();
        // AppParamDto[] listJpmParamDto = appParamService.getListJpmParamByProcessId(processId);
        // AppStatusDto[] listJpmStatus = appStatusService.getListJpmStatusByProcessId(processId);
        // AppButtonDto[] listJpmButton = appButtonService.getListJpmButtonByProcessId(processId);
        // List<AppStepDto> listJpmStepDto = appStepService.getJpmStepDtoDetailByProcessId(processId, locale.getLanguage());
        // List<JpmRouterDto> listJpmRouterDto = jpmRouterService.getListRouterDtoByProcessId(processId);
        // if(CollectionUtils.isNotEmpty(listJpmRouterDto)){
        // Long routerId = listJpmRouterDto.get(0).getId();
        // List<JpmRuleDto> listJpmRuleDto = jpmRuleService.getListRuleDtoByProcessIdAndRouterId(processId, routerId);
        // jpmProcessDto.setListJpmRuleDto(listJpmRuleDto);
        // }

        // appProcessDto.setListJpmParamDto(listJpmParamDto);
        // appProcessDto.setListJpmStatusDto(listJpmStatus);
        // appProcessDto.setListJpmButtonDto(listJpmButton);
        // appProcessDto.setListJpmStepDto(listJpmStepDto);
        // jpmProcessDto.setListJpmRouterDto(listJpmRouterDto);

        // object for clone
        AppProcessDto jpmProcessCloneDto = new AppProcessDto();
        jpmProcessCloneDto.setId(processId);
        jpmProcessCloneDto.setBusinessId(appProcessDto.getBusinessId());
        jpmProcessCloneDto.setCompanyId(appProcessDto.getCompanyId());

        mav.addObject(URL, urlRedirect.toString());
        mav.addObject(OBJECT_DTO, appProcessDto);
        mav.addObject(OBJ_CLONE_DTO, jpmProcessCloneDto);

        this.initDataForEditScreen(mav, appProcessDto);

        return mav;
    }

    @PostMapping(value = UrlConst.EDIT)
    public ModelAndView saveJpmProcessDto(@Valid @ModelAttribute(value = OBJECT_DTO) AppProcessDto objectDto, BindingResult bindingResult,
            @RequestParam(value = URL, required = false) String urlRedirect, Locale locale, RedirectAttributes redirectAttributes,
            HttpServletRequest request, HttpServletResponse response) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_EDIT);

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        // old version for fail save
        Long major = objectDto.getMajorVersion();
        Long minor = objectDto.getMinorVersion();
        Long id = objectDto.getId();
        boolean isDeployed = objectDto.getIsDeployed();

        appProcessValidator.validate(objectDto, bindingResult);

        // Validation
        if (bindingResult.hasErrors()) {
            this.initDataForEditScreenWhenException(messageList, mav, urlRedirect, id, objectDto, locale, bindingResult);
            return mav;
        }

        // Add message success
        String msgContent = null;
        if (isDeployed) {
            msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_DEPLOY, null, locale);
        } else if (id != null) {
            msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        } else {
            msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
        }

        try {
            if (isDeployed) {
                id = appProcessService.saveAndDeployJpmProcessDtoWithAutoVersion(objectDto, true);
                // Redirect
                mav.setViewName(UrlConst.REDIRECT.concat(UrlConst.JPM_PROCESS_DEPLOY).concat(UrlConst.EDIT));
                redirectAttributes.addAttribute(ID, id);
            } else {
                appProcessService.saveJpmProcessDtoWithAutoVersion(objectDto, false);
                id = objectDto.getId();
                // Redirect
                mav.setViewName(UrlConst.REDIRECT.concat(UrlConst.JPM_PROCESS).concat(UrlConst.EDIT));
                redirectAttributes.addAttribute(ID, id);
            }
        } catch (Exception e) {
            // logger.error("Error: ", e);
            messageList.add(e.getMessage());
            objectDto.setMajorVersion(major);
            objectDto.setMinorVersion(minor);
            this.initDataForEditScreenWhenException(messageList, mav, urlRedirect, id, objectDto, locale, bindingResult);
            return mav;
        }

        messageList.add(msgContent);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        return mav;
    }

    private void initDataForEditScreenWhenException(MessageList messageList, ModelAndView mav, String urlRedirect, Long id,
            AppProcessDto objectDto, Locale locale, BindingResult bindingResult) {
        // Add message error
        messageList.setStatus(Message.ERROR);

        String msgError = null;
        if (objectDto.getIsDeployed()) {
            msgError = msg.getMessage(ConstantCore.MSG_ERROR_DEPLOY, null, locale);
        } else if (id == null) {
            msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
        } else {
            msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
        }

        messageList.add(msgError);

        List<FieldError> fieldErrors = bindingResult.getFieldErrors("filePathBpmn");
        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            msgError = msg.getMessage("message.error.jpm.process.validation", null, locale);
            messageList.add(msgError);

            for (FieldError fieldError : fieldErrors) {
                String[] errorArgs = new String[1];
                errorArgs[0] = fieldError.getDefaultMessage();

                msgError = msg.getMessage("message.error.jpm.process.validation.field", errorArgs, locale);
                messageList.add(msgError);
            }
        }
        List<FieldError> fieldErrorStep = bindingResult.getFieldErrors("isActive");
        if (fieldErrorStep != null && !fieldErrorStep.isEmpty() && objectDto.getIsDeployed()) {
            for (FieldError error : fieldErrorStep) {
                msgError = msg.getMessage(error.getCode(), error.getArguments(), locale);
                messageList.add(msgError);
            }
            objectDto.setIsDeployed(false);
        }

        objectDto.setIsDeployed(false);

        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject(URL, urlRedirect.toString());
        mav.addObject(OBJECT_DTO, objectDto);

        this.initDataForEditScreen(mav, objectDto);
    }

    @RequestMapping(value = UrlConst.DELETE, method = { RequestMethod.POST })
    public ModelAndView deleteProcess(@ModelAttribute(value = OBJ_SEARCH) AppProcessSearchDto search, @RequestParam(value = "id") Long id,
            Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(UrlConst.REDIRECT.concat(UrlConst.JPM_PROCESS).concat(UrlConst.LIST));

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        boolean deleteFlg = false;
        String msgContent = null;

        // delete
        try {
            deleteFlg = appProcessService.deletedById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // Check delete
        if (deleteFlg) {
            // Add message success
            msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        } else {
            // Add message error
            messageList.setStatus(Message.ERROR);
            msgContent = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
        }
        messageList.add(msgContent);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addFlashAttribute(OBJ_SEARCH, search);

        return mav;
    }

    /**
     * initDataForEditScreen
     * 
     * @param mav
     *            type ModelAndView
     * @author KhoaNA
     */
    private void initDataForEditScreen(ModelAndView mav, AppProcessDto objectDto) {
        Long companyId = objectDto.getCompanyId();
        Long processId = objectDto.getId();

        // Get company list
        boolean isCompanyAdmin = UserProfileUtils.isCompanyAdmin();
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), isCompanyAdmin);
        mav.addObject(COMPANY_LIST, companyList);

        List<Select2Dto> businessList = appBusinessService.getSelect2DtoListCompanyId(companyId);
        mav.addObject(BUSINESS_LIST, businessList);

        // lang list
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        mav.addObject(LANGUAGE_LIST, languageList);

        // init deploy modal
        List<AppProcessDeployDto> processDeployList = appProcessDeployService.findJpmProcessDeployDtoListByProcessId(processId);
        mav.addObject("processDeployList", processDeployList);

        // init isAuthority
        Long busId = objectDto.getBusinessId();
        if (null != busId) {
            AppBusinessDto bus = appBusinessService.getAppBusinessDtoById(busId);
            mav.addObject("isAuthority", bus.getIsAuthority());
        }

        // DMN
        List<AppProcessDmnDto> appProcessDmnDtos = appProcessDmnService.getAppProcessDmnDtosByProcessId(processId);
        mav.addObject("processDmnDtos", appProcessDmnDtos);
    }

    @GetMapping(value = "/param")
    public ModelAndView getListParam(@RequestParam(value = "processId") Long processId) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_PARAM);

        AppProcessDto appProcessDto = new AppProcessDto();
        appProcessDto.setListJpmParamDto(appParamService.getListJpmParamByProcessId(processId));

        mav.addObject(OBJECT_DTO, appProcessDto);

        return mav;
    }

    @GetMapping(value = "/edit-param")
    public ModelAndView editParam(@ModelAttribute(value = OBJ_PARAM) AppParamDto paramDto,
            @RequestParam(value = "paramId") Optional<Long> id, @RequestParam(value = "processId") Long processId,
            @RequestParam(value = "businessId") Long businessId) {

        if (id.isPresent())
            paramDto = appParamService.getById(id.get());

        if (paramDto == null)
            paramDto = new AppParamDto();

        List<JpmParamConfigDto> configSteps = appParamConfigStepService.getConfigsByParamId(id.orElse(null), processId);
        paramDto.setConfigSteps(configSteps);
        paramDto.setProcessId(processId);
        paramDto.setBusinessId(businessId);

        ComponentDto componentDto = new ComponentDto();
        componentDto.setCompId(BUSINESS_LIST);
        componentDto.setCompName(BUSINESS_LIST);
        // List<ComponentDto> listFormFieldName = componentService.getListcomponentByBusinessId(businessId);
        List<ComponentDto> listFormFieldName = new ArrayList<>();
        listFormFieldName.add(componentDto);

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_PARAM_POPUP);
        mav.addObject(OBJ_PARAM, paramDto);
        mav.addObject(FORM_FIELD_NAME_LIST, listFormFieldName);
        mav.addObject(OBJ_DATA_TYPE, dataTypeConstant.getDataType());
        return mav;
    }

    @PostMapping(value = "/edit-param")
    public ModelAndView saveParam(@Valid @ModelAttribute(value = OBJ_PARAM) AppParamDto paramDto, Locale locale,
            BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_PARAM_POPUP);

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        String msgContent;

        appParamValidator.validate(paramDto, bindingResult);

        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            msgContent = msg.getMessage(bindingResult.getFieldError().getCode(), null, locale);
        } else {
            try {
                appParamService.saveJpmParam(paramDto);

                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
                // Add message success
                if (paramDto.getId() != null) {
                    msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
                }
            } catch (Exception e) {
                messageList = new MessageList(Message.ERROR);
                msgContent = String.format("%s: %s", ConstantCore.MSG_FAIL_SAVE, e.getMessage());

            }
        }

//        Long businessId = paramDto.getBusinessId();

        ComponentDto componentDto = new ComponentDto();
        componentDto.setCompId(BUSINESS_LIST);
        componentDto.setCompName(BUSINESS_LIST);
        // List<ComponentDto> listFormFieldName = componentService.getListcomponentByBusinessId(businessId);
        List<ComponentDto> listFormFieldName = new ArrayList<>();
        listFormFieldName.add(componentDto);

        messageList.add(msgContent);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject(OBJ_DATA_TYPE, dataTypeConstant.getDataType());
        mav.addObject(FORM_FIELD_NAME_LIST, listFormFieldName);

        return mav;
    }

    @PostMapping(value = "/delete-param")
    public ModelAndView deleteParam(@RequestParam(value = "processId") Long processId, @RequestParam(value = "paramId") Long id,
            Locale locale) {
        MessageList messageList;
        try {
            appParamService.deleteJpmParam(id);
            messageList = new MessageList(Message.SUCCESS);
            messageList.add(msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale));
        } catch (Exception e) {
            messageList = new MessageList(Message.ERROR);
            messageList.add(e.getMessage());
        }

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_PARAM);

        AppProcessDto appProcessDto = new AppProcessDto();
        appProcessDto.setListJpmParamDto(appParamService.getListJpmParamByProcessId(processId));

        mav.addObject(OBJECT_DTO, appProcessDto);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        return mav;
    }

    @GetMapping(value = "/status")
    public ModelAndView getListStatus(@RequestParam(value = "processId") Long processId) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_STATUS);

        AppProcessDto appProcessDto = new AppProcessDto();
        appProcessDto.setListJpmStatusDto(appStatusService.getListJpmStatusByProcessId(processId));

        mav.addObject(OBJECT_DTO, appProcessDto);

        return mav;
    }

    @GetMapping(value = "/edit-status")
    public ModelAndView editStatus(@ModelAttribute(value = OBJ_STATUS) AppStatusDto statusDto,
            @RequestParam(value = "statusId") Optional<Long> id, @RequestParam(value = "processId") Long processId) {

        if (id.isPresent())
            statusDto = appStatusService.getById(id.get());

        if (statusDto == null)
            statusDto = new AppStatusDto();

        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        Map<String, Long> langIdConverter = languageList.stream().collect(Collectors.toMap(LanguageDto::getCode, LanguageDto::getId));
        statusDto.setProcessId(processId);
        List<JpmStatusLangDto> listJpmStatusLang = statusDto.getListJpmStatusLang();
        if (null == listJpmStatusLang) {
            listJpmStatusLang = new ArrayList<>();
        }

        Set<String> setCodeLanguage = langIdConverter.keySet();
        setCodeLanguage.removeAll(listJpmStatusLang.stream().map(l -> l.getLangCode()).collect(Collectors.toList()));
        for (String code : setCodeLanguage) {
            JpmStatusLangDto newJpmStatusLang = new JpmStatusLangDto();
            newJpmStatusLang.setLangCode(code);
            newJpmStatusLang.setLangId(langIdConverter.getOrDefault(code, 0L));
            listJpmStatusLang.add(newJpmStatusLang);
        }

        statusDto.setListJpmStatusLang(listJpmStatusLang);

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_STATUS_POPUP);
        mav.addObject(OBJ_STATUS, statusDto);
        mav.addObject(LANGUAGE_LIST, languageList);
        return mav;
    }

    @PostMapping(value = "/edit-status")
    public ModelAndView saveStatus(@Valid @ModelAttribute(value = OBJ_STATUS) AppStatusDto statusDto, Locale locale,
            BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_STATUS_POPUP);

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        String msgContent;

        appStatusValidator.validate(statusDto, bindingResult);

        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            msgContent = msg.getMessage(bindingResult.getFieldError().getCode(), null, locale);
        } else {
            try {
                appStatusService.saveJpmStatus(statusDto);

                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
                // Add message success
                if (statusDto.getId() != null) {
                    msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
                }
            } catch (Exception e) {
                messageList = new MessageList(Message.ERROR);
                msgContent = String.format("%s: %s", ConstantCore.MSG_FAIL_SAVE, e.getMessage());

            }
        }

        messageList.add(msgContent);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject(LANGUAGE_LIST, languageService.getLanguageDtoList());

        return mav;
    }

    @PostMapping(value = "/delete-status")
    public ModelAndView deleteStatus(@RequestParam(value = "processId") Long processId, @RequestParam(value = "statusId") Long id,
            Locale locale) {
        MessageList messageList;

        if (!appStatusService.validateJpmStatusUsing(id)) {
            messageList = new MessageList(Message.ERROR);
            messageList.add(msg.getMessage("jpm.process.status.using", null, locale));
        } else {
            try {
                appStatusService.deleteJpmStatus(id);
                messageList = new MessageList(Message.SUCCESS);
                messageList.add(msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale));
            } catch (Exception e) {
                messageList = new MessageList(Message.ERROR);
                messageList.add(e.getMessage());
            }
        }

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_STATUS);

        AppProcessDto appProcessDto = new AppProcessDto();
        appProcessDto.setListJpmStatusDto(appStatusService.getListJpmStatusByProcessId(processId));

        mav.addObject(OBJECT_DTO, appProcessDto);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        return mav;
    }

    @GetMapping(value = "/button")
    public ModelAndView getListButton(@RequestParam(value = "processId") Long processId) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_BUTTON);

        AppProcessDto appProcessDto = new AppProcessDto();
        appProcessDto.setListJpmButtonDto(appButtonService.getListJpmButtonByProcessId(processId));

        mav.addObject(OBJECT_DTO, appProcessDto);

        return mav;
    }

    @RequestMapping(value = "/edit-button", method = { RequestMethod.GET })
    public ModelAndView editButton(@ModelAttribute(value = OBJ_BUTTON) AppButtonDto buttonDto,
            @RequestParam(value = "buttonId") Optional<Long> id, @RequestParam(value = "processId") Long processId) {

        if (id.isPresent())
            buttonDto = appButtonService.getById(id.get());

        if (buttonDto == null)
            buttonDto = new AppButtonDto();

        buttonDto.setProcessId(processId);

        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        Map<String, Long> langIdConverter = languageList.stream().collect(Collectors.toMap(LanguageDto::getCode, LanguageDto::getId));
        List<JpmButtonLangDto> listJpmButtonLang = buttonDto.getListJpmButtonLang();
        if (null == listJpmButtonLang)
            listJpmButtonLang = new ArrayList<>();

        Set<String> setCodeLanguage = langIdConverter.keySet();
        setCodeLanguage.removeAll(listJpmButtonLang.stream().map(l -> l.getLangCode()).collect(Collectors.toList()));
        for (String code : setCodeLanguage) {
            JpmButtonLangDto newJpmButtonLang = new JpmButtonLangDto();
            newJpmButtonLang.setLangCode(code);
            newJpmButtonLang.setLangId(langIdConverter.getOrDefault(code, 0L));
            listJpmButtonLang.add(newJpmButtonLang);
        }

        buttonDto.setListJpmButtonLang(listJpmButtonLang);

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_BUTTON_POPUP);
        mav.addObject(OBJ_BUTTON, buttonDto);
        mav.addObject(OBJ_BUTTON_TYPE, ButtonTypeConstant.getButtonType());
        mav.addObject(LANGUAGE_LIST, languageList);
        mav.addObject(OBJ_BUTTON_CLASS, constantDisplayService.getListJcaConstantDtoByKind(ConstantDisplayType.PROCESS_BUTTON_CLASS_TYPE.toString(), UserProfileUtils.getLanguage()));
        return mav;
    }

    @RequestMapping(value = "/edit-button", method = { RequestMethod.POST })
    public ModelAndView saveButton(@Valid @ModelAttribute(value = OBJ_BUTTON) AppButtonDto buttonDto, Locale locale,
            BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_BUTTON_POPUP);

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        String msgContent;

        appButtonValidator.validate(buttonDto, bindingResult);

        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            msgContent = msg.getMessage(bindingResult.getFieldError().getCode(), null, locale);
        } else {
            try {
                appButtonService.saveJpmButton(buttonDto);

                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
                // Add message success
                if (buttonDto.getId() != null) {
                    msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
                }
            } catch (Exception e) {
                messageList = new MessageList(Message.ERROR);
                msgContent = String.format("%s: %s", ConstantCore.MSG_FAIL_SAVE, e.getMessage());

            }
        }

        messageList.add(msgContent);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject(LANGUAGE_LIST, languageService.getLanguageDtoList());
        // mav.addObject(FUNCTION_LIST, appFunctionService.getListJpmFunctionByProcessId(buttonDto.getProcessId()));
        mav.addObject(OBJ_BUTTON_TYPE, ButtonTypeConstant.getButtonType());
        // mav.addObject(OBJ_BUTTON_CLASS, constantDisplayService.findByType(ConstantDisplayType.J_PRP_BTN_001.toString()));

        return mav;
    }

    @RequestMapping(value = "/delete-button", method = { RequestMethod.POST })
    public ModelAndView deleteButton(@RequestParam(value = "processId") Long processId, @RequestParam(value = "buttonId") Long id,
            Locale locale) {
        MessageList messageList;

        if (!appButtonService.validateJpmButtonUsing(id)) {
            messageList = new MessageList(Message.ERROR);
            messageList.add(msg.getMessage("jpm.process.button.using", null, locale));
        } else {
            try {
                appButtonService.deleteJpmButton(id);
                messageList = new MessageList(Message.SUCCESS);
                messageList.add(msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale));
            } catch (Exception e) {
                messageList = new MessageList(Message.ERROR);
                messageList.add(e.getMessage());
            }
        }

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_BUTTON);

        AppProcessDto appProcessDto = new AppProcessDto();
        appProcessDto.setListJpmButtonDto(appButtonService.getListJpmButtonByProcessId(processId));

        mav.addObject(OBJECT_DTO, appProcessDto);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        return mav;
    }

    @RequestMapping(value = "/function", method = { RequestMethod.GET })
    public ModelAndView getListFunction(@RequestParam(value = "processId") Long processId) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_FUNCTION);

        AppProcessDto appProcessDto = new AppProcessDto();
        appProcessDto.setListJpmFunctionDto(appFunctionService.getListJpmFunctionByProcessId(processId));

        mav.addObject(OBJECT_DTO, appProcessDto);

        return mav;
    }

    @RequestMapping(value = "/edit-function", method = { RequestMethod.GET })
    public ModelAndView editFunction(@ModelAttribute(value = OBJ_FUNCTION) AppFunctionDto functionDto,
            @RequestParam(value = "functionId") Optional<Long> id, @RequestParam(value = "processId") Long processId) {

        if (id.isPresent())
            functionDto = appFunctionService.getById(id.get());

        if (functionDto == null)
            functionDto = new AppFunctionDto();

        functionDto.setProcessId(processId);

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_FUNCTION_POPUP);
        mav.addObject(OBJ_FUNCTION, functionDto);
        return mav;
    }

    @RequestMapping(value = "/edit-function", method = { RequestMethod.POST })
    public ModelAndView saveFunction(@Valid @ModelAttribute(value = OBJ_FUNCTION) AppFunctionDto functionDto, Locale locale,
            BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_FUNCTION_POPUP);

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        String msgContent;

        appFunctionValidator.validate(functionDto, bindingResult);

        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            msgContent = msg.getMessage(bindingResult.getFieldError().getCode(), null, locale);
        } else {
            try {
                JpmPermission jpmPermission = appFunctionService.saveJpmFunction(functionDto);

                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
                // Add message success
                if (functionDto.getId() != null) {
                    msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
                }
                functionDto = modelMapper.map(jpmPermission, AppFunctionDto.class);
            } catch (Exception e) {
                messageList = new MessageList(Message.ERROR);
                msgContent = String.format("%s: %s", ConstantCore.MSG_FAIL_SAVE, e.getMessage());

            }
        }

        messageList.add(msgContent);
        mav.addObject(OBJ_FUNCTION, functionDto);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        // Redirect
        // String viewName = UrlConst.REDIRECT.concat(UrlConst.JPM_PROCESS).concat("/edit-param");
        // mav = new ModelAndView(viewName);

        // redirectAttributes.addAttribute(OBJ_PARAM, paramDto);
        // redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        // redirectAttributes.addAttribute("paramId", paramDto.getId());
        // redirectAttributes.addAttribute("processId", paramDto.getProcessId());

        // mav.setViewName(viewName);

        return mav;
    }

    @RequestMapping(value = "/delete-function", method = { RequestMethod.POST })
    public ModelAndView deleteFunction(@RequestParam(value = "processId") Long processId, @RequestParam(value = "functionId") Long id,
            Locale locale) {
        MessageList messageList;

        if (!appFunctionService.validateJpmFunctionUsing(id)) {
            messageList = new MessageList(Message.ERROR);
            messageList.add(msg.getMessage("jpm.process.function.using", null, locale));
        } else {
            try {
                appFunctionService.deleteJpmFunction(id);
                messageList = new MessageList(Message.SUCCESS);
                messageList.add(msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale));
            } catch (Exception e) {
                messageList = new MessageList(Message.ERROR);
                messageList.add(e.getMessage());
            }
        }

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_FUNCTION);

        AppProcessDto appProcessDto = new AppProcessDto();
        appProcessDto.setListJpmFunctionDto(appFunctionService.getListJpmFunctionByProcessId(processId));

        mav.addObject(OBJECT_DTO, appProcessDto);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        return mav;
    }

    @RequestMapping(value = "/step", method = { RequestMethod.GET })
    public ModelAndView getListStep(@RequestParam(value = "processId") Long processId, Locale locale) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_STEP);

        AppProcessDto appProcessDto = new AppProcessDto();
        appProcessDto.setListJpmStepDto(appStepService.getJpmStepDtoDetailByProcessId(processId, locale.getLanguage()));

        mav.addObject(OBJECT_DTO, appProcessDto);

        return mav;
    }

    @RequestMapping(value = "/edit-step", method = { RequestMethod.GET })
    public ModelAndView editStep(@ModelAttribute(value = OBJ_STEP) AppStepDto stepDto, @RequestParam(value = "stepId") Optional<Long> id,
            @RequestParam(value = "processId") Long processId, Locale locale) throws Exception {

        if (id.isPresent())
            stepDto = appStepService.getById(id.get());

        if (stepDto == null)
            stepDto = new AppStepDto();

        stepDto.setProcessId(processId);

        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        Map<String, Long> langIdConverter = languageList.stream().collect(Collectors.toMap(LanguageDto::getCode, LanguageDto::getId));
        List<JpmStepLangDto> listJpmStepLang = stepDto.getListJpmStepLang();
        if (null == listJpmStepLang) {
            listJpmStepLang = new ArrayList<>();
        }

        Set<String> setCodeLanguage = langIdConverter.keySet();
        setCodeLanguage.removeAll(listJpmStepLang.stream().map(l -> l.getLangCode()).collect(Collectors.toList()));
        for (String code : setCodeLanguage) {
            JpmStepLangDto newJpmButtonLang = new JpmStepLangDto();
            newJpmButtonLang.setLangCode(code);
            newJpmButtonLang.setLangId(langIdConverter.getOrDefault(code, 0L));
            listJpmStepLang.add(newJpmButtonLang);
        }

        stepDto.setListJpmStepLang(listJpmStepLang);
        // stepDto.setListJpmButton(appButtonForStepService.getButtonForStepByStepId(stepDto.getId()));

        AppProcessDto process = appProcessService.getAppProcessDtoById(processId);
        String processType = null == process ? ConstantCore.EMPTY : process.getProcessType();
        boolean isFreeProcess = ProcessType.FREE.toString().equals(processType);

        // Common status
        List<Select2Dto> statusCommons = jpmStatusCommonService.getStatusCommonSelect2Dtos(locale.getLanguage());

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_STEP_POPUP);
        mav.addObject(OBJ_STEP, stepDto);
        mav.addObject(BUTTON_LIST, appButtonService.getSelect2DtoByProcess(processId, locale.getLanguage()));
        mav.addObject(FUNCTION_LIST, appFunctionService.getListJpmFunctionByProcessId(processId));
        mav.addObject(STATUS_LIST, appStatusService.getListJpmStatusByProcessId(processId));
        mav.addObject(STATUS_COMMON_LIST, statusCommons);
        mav.addObject(STEP_KIND_LIST, StepKind.getList());
        mav.addObject(LANGUAGE_LIST, languageList);
        mav.addObject("isFreeProcess", isFreeProcess);

        return mav;
    }

    @RequestMapping(value = "/edit-step", method = { RequestMethod.POST })
    public ModelAndView saveStep(@Valid @ModelAttribute(value = OBJ_STEP) AppStepDto stepDto, Locale locale, BindingResult bindingResult)
            throws Exception {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_STEP_POPUP);

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        String msgContent;

        appStepValidator.validate(stepDto, bindingResult);

        List<LanguageDto> languageList = languageService.getLanguageDtoList();

        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            msgContent = msg.getMessage(bindingResult.getFieldError().getCode(), null, locale);
        } else {
            try {
                JpmStep jpmStep = appStepService.saveJpmStep(stepDto);

                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
                stepDto = appStepService.getById(jpmStep.getId());
            } catch (Exception e) {
                messageList = new MessageList(Message.ERROR);
                msgContent = String.format("%s: %s", ConstantCore.MSG_FAIL_SAVE, e.getMessage());
            }
        }

        // Common status
        List<Select2Dto> statusCommons = jpmStatusCommonService.getStatusCommonSelect2Dtos(locale.getLanguage());

        messageList.add(msgContent);
        mav.addObject(OBJ_STEP, stepDto);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject(BUTTON_LIST, appButtonService.getSelect2DtoByProcess(stepDto.getProcessId(), locale.getLanguage()));
        mav.addObject(FUNCTION_LIST, appFunctionService.getListJpmFunctionByProcessId(stepDto.getProcessId()));
        mav.addObject(STATUS_LIST, appStatusService.getListJpmStatusByProcessId(stepDto.getProcessId()));
        mav.addObject(STATUS_COMMON_LIST, statusCommons);
        mav.addObject(STEP_KIND_LIST, StepKind.getList());
        mav.addObject(LANGUAGE_LIST, languageList);

        return mav;
    }

    @GetMapping(value = "/clone-process")
    public ModelAndView getModalCloneProject(@RequestParam(value = "companyId", required = true) Long companyId,
            @RequestParam(value = "businessId", required = true) Long businessId, @RequestParam(value = "id", required = true) Long id) {

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_CLONE_POPUP);
        AppProcessDto objectCloneDto = new AppProcessDto();

        objectCloneDto.setCompanyId(companyId);
        objectCloneDto.setBusinessId(businessId);
        objectCloneDto.setId(id);

        mav.addObject(OBJ_CLONE_DTO, objectCloneDto);

        AppProcessDto processDto = new AppProcessDto();
        processDto.setCompanyId(companyId);

        this.initDataForEditScreen(mav, processDto);
        return mav;
    }

    @PostMapping(value = "/clone-process")
    public ModelAndView cloneProject(@ModelAttribute(value = OBJ_CLONE_DTO) AppProcessDto objectDto,
            @RequestParam(value = URL, required = false) String urlRedirect, Locale locale, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

        Long oldId = objectDto.getId();
        Long processId = null;
        objectDto.setId(null);
        objectDto.setIsClone(true);
        appProcessValidator.validate(objectDto, bindingResult);

        MessageList messageList = new MessageList(Message.SUCCESS);

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_CLONE_POPUP);

        String msgContent;

        objectDto.setId(oldId);
        // Validation
        if (bindingResult.hasErrors()) {
            // // Add message error
            // messageList.setStatus(Message.ERROR);
            // msgContent = msg.getMessage(bindingResult.getFieldError().getCode(), null, locale);
            //
            // String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CLONE, null, locale);

            // messageList.add(msgError);

            // List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            // if( fieldErrors != null && !fieldErrors.isEmpty() ) {
            //// msgError = msg.getMessage("message.error.jpm.process.validation", null, locale);
            //// messageList.add(msgError);
            //
            // for (FieldError fieldError : fieldErrors) {
            // String[] errorArgs = new String[1];
            // errorArgs[0] = fieldError.getDefaultMessage();
            //
            // msgError = msg.getMessage("message.error.jpm.process.validation.field", errorArgs, locale);
            // messageList.add(msgError);
            // }
            // }
            // mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject(URL, urlRedirect.toString());
            mav.addObject(OBJ_CLONE_DTO, objectDto);
            this.initDataForEditScreen(mav, objectDto);
            response.setHeader(MSG_FLAG, ConstantCore.STR_ONE);
            return mav;
        } else {
            try {
                processId = appProcessService.cloneJpmProcess(objectDto);
                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_CLONE, null, locale);
            } catch (Exception e) {
                messageList = new MessageList(Message.ERROR);
                messageList.add(msg.getMessage(ConstantCore.MSG_ERROR_CLONE, null, locale));
                if (e.getMessage() != null) {
                    msgContent = String.format("%s: %s", msg.getMessage(ConstantCore.MSG_FAIL_SAVE, null, locale),
                            e.getMessage());
                } else {
                    msgContent = String.format("%s", msg.getMessage(ConstantCore.MSG_FAIL_SAVE, null, locale));
                }
            }
        }

        messageList.add(msgContent);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        // Redirect
        mav.setViewName(UrlConst.REDIRECT.concat(UrlConst.JPM_PROCESS).concat(UrlConst.EDIT));
        redirectAttributes.addAttribute(ID, processId);
        return mav;
    }

    @PostMapping(value = UrlConst.AJAX_LOAD_PROCESS)
    @ResponseBody
    public List<Select2Dto> getProcessListForSelect(@RequestParam(required = false) Long businessId) {

        List<Select2Dto> processList = appProcessService.getSelect2DtoListBusinessId(businessId);

        return processList;
    }

    @GetMapping(value = "/import")
    @ResponseBody
    public ModelAndView getModalImportJpmProcess() {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_IMPORT_POPUP);
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());

        ImportProcessDto objectDto = new ImportProcessDto();

        mav.addObject(OBJECT_DTO, objectDto);
        mav.addObject(COMPANY_LIST, companyList);
        return mav;
    }

    @PostMapping(value = "/import")
    @ResponseBody
    public ModelAndView importJpmProcess(@ModelAttribute ImportProcessDto objectDto, HttpServletResponse response,
            RedirectAttributes redirectAttributes, Locale locale) throws Exception {

        ModelAndView mav = new ModelAndView("/views/commons/message-alert.html");
        MessageList messageList = new MessageList(Message.SUCCESS);
        JpmProcessImportExportDto processDto = new JpmProcessImportExportDto();
        try {
            MultipartFile importFile = objectDto.getImportFile();

            byte[] contentBytes = importFile.getBytes();
            String contentFile = CommonBase64Util.decode(new String(contentBytes, StandardCharsets.UTF_8));

            Long companyId = objectDto.getCompanyId();

            processDto = CommonJsonUtil.convertJSONToObject(contentFile, JpmProcessImportExportDto.class);
            boolean isOverride = objectDto.getIsOverride();
            if (!isOverride) {
                String code = processDto.getProcessCode();
                AppProcessDto appProcessDto = appProcessService.getJpmProcessByCodeAndCompanyId(code, companyId);

                if (null != appProcessDto) {
                    messageList.setStatus(Message.ERROR);
                    String msgError = msg.getMessage(ConstantCore.MSG_ERROR_IMPORT, null, locale);
                    String msgContent = msg.getMessage("message.error.jpm.process.import.existed", null, locale);
                    messageList.add(msgError);
                    messageList.add(msgContent);

                    mav.addObject(ConstantCore.MSG_LIST, messageList);
                    response.addHeader(MSG_FLAG, ConstantCore.STR_ONE);
                    return mav;
                }
            }

            processDto.setCompanyId(companyId);
            Long processId = appProcessService.importJpmProcess(processDto);
            // Redirect
            mav.setViewName(UrlConst.REDIRECT.concat(UrlConst.JPM_PROCESS).concat(UrlConst.EDIT));
            redirectAttributes.addAttribute(ID, processId);

            String msgError = msg.getMessage(ConstantCore.MSG_SUCCESS_IMPORT, null, locale);
            messageList.add(msgError);
            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_IMPORT, null, locale);
            String msgContent = msg.getMessage("message.error.jpm.process.import.corrupt", null, locale);
            messageList.add(msgError);
            messageList.add(msgContent);
            messageList.add(e.getMessage());

            mav.addObject(ConstantCore.MSG_LIST, messageList);
            response.addHeader(MSG_FLAG, ConstantCore.STR_ONE);
            return mav;
        }
        return mav;
    }

    @GetMapping(value = "/dmn")
    public ModelAndView getListDmn(@RequestParam(value = "processId") Long processId, ModelMap model) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DMN);

        List<AppProcessDmnDto> appProcessDmnDtos = appProcessDmnService.getAppProcessDmnDtosByProcessId(processId);

        mav.addObject("processDmnDtos", appProcessDmnDtos);

        return mav;
    }

    @GetMapping(value = "/view-dmn")
    public ModelAndView viewDmn(@RequestParam(value = "id") Optional<Long> id) {

        if (id.isPresent()) {

        }

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DMN_POPUP);
        return mav;
    }

    @PostMapping(value = "/upload-dmn")
    public ModelAndView uploadDmn(@RequestParam(value = "processId") Long processId,
            @RequestParam(value = "dmnFiles") List<MultipartFile> dmnFiles, RedirectAttributes redirectAttributes, Locale locale) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DMN);

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        String msgContent;

        // Validation
        if (validateDmn(processId, dmnFiles, messageList, locale)) {
            try {
                if (appProcessDmnService.uploadDmnFiles(processId, dmnFiles)) {
                    msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);
                } else {
                    messageList.setStatus(Message.ERROR);
                    msgContent = msg.getMessage(ConstantCore.MSG_FAIL_SAVE, null, locale);
                }
            } catch (Exception e) {
                messageList.setStatus(Message.ERROR);
                msgContent = String.format("%s: %s", ConstantCore.MSG_FAIL_SAVE, e.getMessage());
            }
            messageList.add(msgContent);
        }

        mav.setViewName(UrlConst.REDIRECT.concat(UrlConst.JPM_PROCESS).concat("/dmn"));
        redirectAttributes.addAttribute("processId", processId);
        redirectAttributes.addFlashAttribute("messages", messageList);

        return mav;
    }

    @PostMapping(value = "/delete-dmn")
    public ModelAndView deleteDmn(@RequestParam(value = "processId") Long processId, @RequestParam(value = "dmnId") Long id,
            RedirectAttributes redirectAttributes, Locale locale) {

        MessageList messageList = new MessageList(Message.SUCCESS);

        try {
            if (appProcessDmnService.delete(id)) {
                messageList.add(msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale));
            } else {
                messageList.setStatus(Message.ERROR);
                messageList.add(msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale));
            }
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            messageList.add(e.getMessage());
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName(UrlConst.REDIRECT.concat(UrlConst.JPM_PROCESS).concat("/dmn"));
        redirectAttributes.addAttribute("processId", processId);
        redirectAttributes.addFlashAttribute("messages", messageList);

        return mav;
    }

    private boolean validateDmn(Long processId, List<MultipartFile> dmnFiles, MessageList messageList, Locale locale) {
        boolean res = true;

        List<AppProcessDmnDto> processDmnDtos = appProcessDmnService.getAppProcessDmnDtosByProcessId(processId);
        if (CommonCollectionUtil.isNotEmpty(processDmnDtos)) {
            List<String> currentFileNames = processDmnDtos.stream().map(AppProcessDmnDto::getDmnFileName).collect(Collectors.toList());

            for (MultipartFile dmnFile : dmnFiles) {
                if (currentFileNames.contains(dmnFile.getOriginalFilename())) {
                    res = false;
                    messageList.setStatus(Message.ERROR);
                    messageList.add(msg.getMessage("message.file.already.exists", null, locale));
                    break;
                }
            }
        }
        return res;
    }
}
