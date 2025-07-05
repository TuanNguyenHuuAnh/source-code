/*******************************************************************************
 * Class        AppProcessDeployController
 * Created date 2019/07/04
 * Lasted date  2019/07/04
 * Author       KhuongTH
 * Change log   2019/07/04 01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.controller;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.constant.DataTypeConstant;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.common.utils.CommonSearchUtil;
import vn.com.unit.common.utils.CommonStringUtil;
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
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.enumdef.ConstantDisplayType;
import vn.com.unit.ep2p.admin.service.AccountOrgService;
//import vn.com.unit.ep2p.admin.service.AppStatusLangDeployService;
//import vn.com.unit.ep2p.admin.service.AppStepLangDeployService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
//import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
import vn.com.unit.ep2p.admin.utils.URLUtil;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.process.workflow.dto.AppButtonDeployDto;
import vn.com.unit.process.workflow.dto.AppFunctionDeployDto;
import vn.com.unit.process.workflow.dto.AppParamDeployDto;
import vn.com.unit.process.workflow.dto.AppProcessDeployDto;
import vn.com.unit.process.workflow.dto.AppProcessDeploySearchDto;
import vn.com.unit.process.workflow.dto.AppStatusDeployDto;
import vn.com.unit.process.workflow.dto.AppStepDeployDto;
import vn.com.unit.process.workflow.enumdef.JpmProcessDeploySearchEnum;
import vn.com.unit.process.workflow.service.AppBusinessService;
import vn.com.unit.process.workflow.service.AppButtonDeployService;
import vn.com.unit.process.workflow.service.AppFunctionDeployService;
import vn.com.unit.process.workflow.service.AppParamDeployService;
import vn.com.unit.process.workflow.service.AppProcessDeployService;
import vn.com.unit.process.workflow.service.AppProcessDmnDeployService;
import vn.com.unit.process.workflow.service.AppStatusDeployService;
import vn.com.unit.process.workflow.service.AppStepDeployService;
//import vn.com.unit.ep2p.workflow.service.AppBusinessService;
//import vn.com.unit.ep2p.workflow.service.AppButtonDeployService;
//import vn.com.unit.ep2p.workflow.service.AppFunctionDeployService;
//import vn.com.unit.ep2p.workflow.service.AppParamDeployService;
//import vn.com.unit.ep2p.workflow.service.AppProcessDeployService;
//import vn.com.unit.ep2p.workflow.service.AppProcessDmnDeployService;
//import vn.com.unit.ep2p.workflow.service.AppStatusDeployService;
//import vn.com.unit.ep2p.workflow.service.AppStepDeployService;
import vn.com.unit.storage.utils.FileStorageUtils;
import vn.com.unit.workflow.dto.JpmButtonLangDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDmnDeployDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;
import vn.com.unit.workflow.dto.JpmStatusLangDeployDto;
import vn.com.unit.workflow.dto.JpmStepLangDeployDto;
import vn.com.unit.workflow.enumdef.StepKind;

/**
 * JpmProcessController
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Controller
@RequestMapping(value = UrlConst.JPM_PROCESS_DEPLOY)
public class AppProcessDeployController {

    @Autowired
    private MessageSource msg;
	
    @Autowired
    private CompanyService companyService;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    AppProcessDeployService appProcessDeployService;
    
    @Autowired
    AppBusinessService jpmBusinessService;

    @Autowired
    private DataTypeConstant dataTypeConstant;

    @Autowired
    ConstantDisplayService constantDisplayService;
    
//    @Autowired
//    AppStepLangDeployService appStepLangDeployService;

    @Autowired
    AppLanguageService languageService;

    @Autowired
    AppParamDeployService appParamDeployService;

    @Autowired
    AppStatusDeployService appStatusDeployService;

//    @Autowired
//    AppStatusLangDeployService appStatusLangDeployService;

    @Autowired
    AppButtonDeployService appButtonDeployService;

    @Autowired
    AppFunctionDeployService appFunctionDeployService;

    @Autowired
    AppStepDeployService appStepDeployService;

    @Autowired
    AccountOrgService accountOrgService;
    
    @Autowired
    AppProcessDmnDeployService appProcessDmnDeployService;

    
    /** MAV */
    private static final String MAV_JPM_PROCESS_DEPLOY_EDIT = "/views/jpm-process-deploy/jpm-process-deploy-edit.html";
    private static final String MAV_JPM_PROCESS_DEPLOY_LIST = "/views/jpm-process-deploy/jpm-process-deploy-list.html";
    private static final String MAV_JPM_PROCESS_DEPLOY_TABLE = "/views/jpm-process-deploy/jpm-process-deploy-table.html";

    private static final String MAV_JPM_PROCESS_DEPLOY_PARAM = "/views/jpm-process-deploy/jpm-process-deploy-param.html";
    private static final String MAV_JPM_PROCESS_DEPLOY_PARAM_POPUP = "/views/jpm-process-deploy/jpm-process-deploy-param-popup-body.html";

    private static final String MAV_JPM_PROCESS_DEPLOY_STATUS = "/views/jpm-process-deploy/jpm-process-deploy-status.html";
    private static final String MAV_JPM_PROCESS_DEPLOY_STATUS_POPUP = "/views/jpm-process-deploy/jpm-process-deploy-status-popup-body.html";

    private static final String MAV_JPM_PROCESS_DEPLOY_BUTTON = "/views/jpm-process-deploy/jpm-process-deploy-button.html";
    private static final String MAV_JPM_PROCESS_DEPLOY_BUTTON_POPUP = "/views/jpm-process-deploy/jpm-process-deploy-button-popup-body.html";

    private static final String MAV_JPM_PROCESS_DEPLOY_FUNCTION = "/views/jpm-process-deploy/jpm-process-deploy-function.html";
    private static final String MAV_JPM_PROCESS_DEPLOY_FUNCTION_POPUP = "/views/jpm-process-deploy/jpm-process-deploy-function-popup-body.html";

    private static final String MAV_JPM_PROCESS_DEPLOY_STEP_POPUP = "/views/jpm-process-deploy/jpm-process-deploy-step-popup-body.html";
    private static final String MAV_JPM_PROCESS_DEPLOY_STEP = "/views/jpm-process-deploy/jpm-process-deploy-step.html";

    /** Params */
    private static final String ID = "id";
    private static final String OBJECT_DTO = "objectDto";
    private static final String URL = "url";
    
    private static final String COMPANY_LIST = "companyList";
    private static final String BUSINESS_LIST = "businessList";
    private static final String OBJ_SEARCH = "searchDto";
    private static final String OBJ_PAGE_WRAPPER = "pageWrapper";
    private static final String OBJ_PARAM = "paramDto";
    private static final String OBJ_DATA_TYPE = "dataTypes";
    private static final String OBJ_STATUS = "statusDto";
    private static final String OBJ_BUTTON = "buttonDto";
    private static final String OBJ_BUTTON_TYPE = "buttonTypes";
    private static final String OBJ_BUTTON_CLASS = "buttonClasses";
    private static final String OBJ_FUNCTION = "functionDto";
    private static final String OBJ_STEP = "stepDto";
    private static final String LANGUAGE_LIST = "languageList";
    private static final String STATUS_LIST = "statusList";
    private static final String BUTTON_LIST = "buttonList";
    private static final String FUNCTION_LIST = "functionList";
    private static final String STEP_KIND_LIST = "stepKindList";
    private static final String STATUS_COMMON_LIST = "statusCommons";

    private static final String DATE_PATTERN = "datePattern";

    private static final String SCREEN_FUNCTION_CODE = RoleConstant.PROCESS;

    private static final Logger logger = LoggerFactory.getLogger(AppProcessDeployController.class);

    // Model mapper
    ModelMapper modelMapper = new ModelMapper();

    /**
     * dateBinder
     * 
     * @param binder
     * @param request
     * @param locale
     * @author KhuongTH
     */
    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
    	request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
    	// The date format to parse or output your dates
        String patternDate = (String) request.getSession().getAttribute(ConstantCore.FORMAT_DATE);
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
    }

    /**
	 * Get JpmProcess list
	 * @param
	 * @param
	 * @param
	 * @param model
	 * @return String
     * @throws DetailException 
	 */
    @RequestMapping(value = { UrlConst.ROOT, UrlConst.LIST }, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getProcessList(
            @ModelAttribute(value = OBJ_SEARCH) AppProcessDeploySearchDto search,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) throws DetailException {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
    	
    	ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DEPLOY_LIST);
    	
    	// set init search
        CommonSearchUtil.setSearchSelect(JpmProcessDeploySearchEnum.class, mav);
    	
        // Init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        // Get List
        search.setCompanyId(UserProfileUtils.getCompanyId());
        PageWrapper<AppProcessDeployDto> pageWrapper = appProcessDeployService.search(page, pageSize, search);

        this.initDataForListScreen(mav);
        
        // Object mav
        mav.addObject(OBJ_SEARCH, search);
        mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
        
        return mav;
    }
    
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = OBJ_SEARCH) AppProcessDeploySearchDto searchDto,
                                 @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
                                 @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, 
                                 Locale locale) throws DetailException {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

    	ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DEPLOY_TABLE);
    	
    	// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);
		// Get List
		PageWrapper<AppProcessDeployDto> pageWrapper = appProcessDeployService.search(page, pageSize, searchDto);

		//InitSearch
		CommonSearchUtil.setSearchSelect(JpmProcessDeploySearchEnum.class, mav.getModelMap());

        this.initDataForListScreen(mav);
        
        mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
        return mav;
    }

    /**
	 * Get AppProcessDto
	 * @param id
	 * @param model
	 * @return String
	 * @author KhuongTH
	 */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView getJpmProcessDto(@RequestParam(value = ID, required = false) Long id,
                Locale locale, ModelMap model) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DEPLOY_EDIT);
    	
    	AppProcessDeployDto jpmProcessDto = new AppProcessDeployDto();
    	if( id != null ) {
    		jpmProcessDto = appProcessDeployService.getJpmProcessDeployDtoById(id);
    		if( jpmProcessDto == null ) {
                // throw new BusinessException("JmpProcess not found with id=" + id)
                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
            }
            Integer processType = jpmProcessDto.getProcessType();
            jpmProcessDto.setIsWorkflow(ProcessType.BPMN.getValue() == processType);
        }

    	// URL ajax redirect
		StringBuilder urlRedirect = new StringBuilder(UrlConst.JPM_PROCESS_DEPLOY.substring(1).concat(UrlConst.EDIT));
		if( id != null ) {
			urlRedirect.append(URLUtil.buildParamWithPrefix(true, ID, id));
		}

//		jpmProcessDto.setListJpmParamDto(appParamDeployService.getListParamDeployDtoByProcessId(jpmProcessDto.getId()));
//        jpmProcessDto.setListJpmStatusDto(appStatusDeployService.getListStatusDeployDtoByProcessId(jpmProcessDto.getId()));
//        jpmProcessDto.setListJpmButtonDto(appButtonDeployService.getListButtonDeployDtoByProcessId(jpmProcessDto.getId()));
        jpmProcessDto.setListJpmStepDto(appStepDeployService.getJpmStepDeployDtoDetailByProcessId(jpmProcessDto.getId(), locale.getLanguage()));
        
		model.addAttribute(URL, urlRedirect.toString());
		model.addAttribute(OBJECT_DTO, jpmProcessDto);
		
		this.initDataForEditScreen(model, jpmProcessDto);

    	return mav;
    }
    
    
    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    public ModelAndView getJpmProcessDto(@ModelAttribute(value = OBJ_SEARCH) AppProcessDeploySearchDto searchDto,
    		@RequestParam(value = "id") Long id,
            Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {

    	 // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        ModelAndView mav = new ModelAndView(UrlConst.REDIRECT.concat(UrlConst.JPM_PROCESS_DEPLOY).concat(UrlConst.LIST));
        
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        boolean deleteFlg = false;
        String msgContent = null;

        // delete
        try {
            deleteFlg = appProcessDeployService.deletedById(id);
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
            msgContent = msg.getMessage(ConstantCore.MSG_ERROR_DELETE_PROCESS_DEPLOY, null, locale);
        }
        messageList.add(msgContent);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        // Object mav
        redirectAttributes.addFlashAttribute(OBJ_SEARCH, searchDto);

        return mav;
    }

   
    /**
	 * initDataForEditScreen
	 * 
	 * @param model
	 * 			type ModelMap
	 * @author KhuongTH
	 */
    private void initDataForEditScreen(ModelMap model, AppProcessDeployDto jpmProcessDto) {
        // Get company list
    	Long companyId = jpmProcessDto.getCompanyId();
    	boolean isCompanyAdmin = UserProfileUtils.isCompanyAdmin();
        List<Long> companyIds = UserProfileUtils.getCompanyIdList();
        List<Select2Dto> companyList = companyService.findByListCompanyId(companyIds, isCompanyAdmin);
        model.addAttribute(COMPANY_LIST, companyList);
        
        List<Select2Dto> businessList = jpmBusinessService.getSelect2DtoListCompanyId(companyId);
        model.addAttribute(BUSINESS_LIST, businessList);

        // lang list
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        model.addAttribute(LANGUAGE_LIST, languageList);
        
        // DMN
        List<JpmProcessDmnDeployDto> appProcessDmnDtos = appProcessDmnDeployService.getJpmProcessDmnDeployDtosByProcessDeployId(jpmProcessDto.getId());
        model.addAttribute("processDmnDtos", appProcessDmnDtos);
    }

    private void initDataForListScreen(ModelAndView mav){
    	Long companyId = UserProfileUtils.getCompanyId();
    	boolean isCompanyAdmin = UserProfileUtils.isCompanyAdmin();
    	List<Long> companyIds = UserProfileUtils.getCompanyIdList();
        List<Select2Dto> companyList = companyService.findByListCompanyId(companyIds, isCompanyAdmin);
        mav.addObject(COMPANY_LIST, companyList);
        mav.addObject("companyId", companyId);
        
        List<Select2Dto> businessList = jpmBusinessService.getSelect2DtoListCompanyId(companyId);
        mav.addObject(BUSINESS_LIST, businessList);

        String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
        mav.addObject(DATE_PATTERN, datePattern);
        
    }
    
    @RequestMapping(value = "/param", method = { RequestMethod.GET })
    public ModelAndView getListParam(@RequestParam(value = "processId") Long processId) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DEPLOY_PARAM);

        AppProcessDeployDto jpmProcessDto = new AppProcessDeployDto();
        jpmProcessDto.setListJpmParamDto(appParamDeployService.getListParamDeployDtoByProcessId(processId));

        mav.addObject(OBJECT_DTO, jpmProcessDto);

        return mav;
    }

    @RequestMapping(value = "/edit-param", method = { RequestMethod.GET })
    public ModelAndView editParam(@ModelAttribute(value = OBJ_PARAM) AppParamDeployDto paramDto,
                                  @RequestParam(value = "paramId") Optional<Long> id,
                                  @RequestParam(value = "processId") Long processId) {

        if (id.isPresent())
            paramDto = appParamDeployService.getById(id.get());

        if (paramDto == null)
            paramDto = new AppParamDeployDto();

        paramDto.setProcessId(processId);

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DEPLOY_PARAM_POPUP);
        mav.addObject(OBJ_PARAM, paramDto);
        mav.addObject(OBJ_DATA_TYPE, dataTypeConstant.getDataType());
        return mav;
    }


    @RequestMapping(value = "/status", method = { RequestMethod.GET })
    public ModelAndView getListStatus(@RequestParam(value = "processId") Long processId) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DEPLOY_STATUS);

        AppProcessDeployDto jpmProcessDto = new AppProcessDeployDto();
        jpmProcessDto.setListJpmStatusDto(appStatusDeployService.getListStatusDeployDtoByProcessId(processId));

        mav.addObject(OBJECT_DTO, jpmProcessDto);

        return mav;
    }

    @RequestMapping(value = "/edit-status", method = { RequestMethod.GET })
    public ModelAndView editStatus(@ModelAttribute(value = OBJ_STATUS) AppStatusDeployDto statusDto,
                                  @RequestParam(value = "statusId") Optional<Long> id,
                                  @RequestParam(value = "processId") Long processId) {

        if (id.isPresent())
            statusDto = appStatusDeployService.getById(id.get());

        if (statusDto == null)
            statusDto = new AppStatusDeployDto();

        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        List<String> listCodeLanguage = languageList.stream().map(lang -> lang.getCode()).sorted().collect(Collectors.toList());
        statusDto.setProcessId(processId);
        List<JpmStatusLangDeployDto> listJpmStatusLang = statusDto.getListJpmStatusLang();
        if (null==listJpmStatusLang) listJpmStatusLang = new ArrayList<>();
        Set<String> setCodeLanguage = new HashSet<>(listCodeLanguage);
        setCodeLanguage.removeAll(listJpmStatusLang.stream().map(l -> l.getLangCode()).collect(Collectors.toList()));

        for (String code : setCodeLanguage) {
            JpmStatusLangDeployDto newJpmStatusLang = new JpmStatusLangDeployDto();
            newJpmStatusLang.setLangCode(code);
            listJpmStatusLang.add(newJpmStatusLang);
        }

        statusDto.setListJpmStatusLang(listJpmStatusLang);

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DEPLOY_STATUS_POPUP);
        mav.addObject(OBJ_STATUS, statusDto);
        mav.addObject(LANGUAGE_LIST, languageList);
        return mav;
    }


    @RequestMapping(value = "/button", method = { RequestMethod.GET })
    public ModelAndView getListButton(@RequestParam(value = "processId") Long processId) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DEPLOY_BUTTON);

        AppProcessDeployDto jpmProcessDto = new AppProcessDeployDto();
        jpmProcessDto.setListJpmButtonDto(appButtonDeployService.getListButtonDeployDtoByProcessId(processId));

        mav.addObject(OBJECT_DTO, jpmProcessDto);

        return mav;
    }

    @RequestMapping(value = "/edit-button", method = { RequestMethod.GET })
    public ModelAndView editButton(@ModelAttribute(value = OBJ_BUTTON) AppButtonDeployDto buttonDto,
                                   @RequestParam(value = "buttonId") Optional<Long> id,
                                   @RequestParam(value = "processId") Long processId) {

        if (id.isPresent())
            buttonDto = appButtonDeployService.getById(id.get());

        if (buttonDto == null)
            buttonDto = new AppButtonDeployDto();

        buttonDto.setProcessId(processId);

        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        List<String> listCodeLanguage = languageList.stream().map(lang -> lang.getCode()).sorted().collect(Collectors.toList());
        List<JpmButtonLangDeployDto> listJpmButtonLang = buttonDto.getListJpmButtonLang();
        if (null == listJpmButtonLang)
            listJpmButtonLang = new ArrayList<>();
        Set<String> setCodeLanguage = new HashSet<>(listCodeLanguage);
        setCodeLanguage.removeAll(listJpmButtonLang.stream().map(l -> l.getLangCode()).collect(Collectors.toList()));
        for (String code : setCodeLanguage) {
            JpmButtonLangDeployDto newJpmButtonLang = new JpmButtonLangDeployDto();
            newJpmButtonLang.setLangCode(code);
            listJpmButtonLang.add(newJpmButtonLang);
        }

        buttonDto.setListJpmButtonLang(listJpmButtonLang);

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DEPLOY_BUTTON_POPUP);
        mav.addObject(OBJ_BUTTON, buttonDto);
        mav.addObject(OBJ_BUTTON_TYPE, ButtonTypeConstant.getButtonType());
        mav.addObject(LANGUAGE_LIST, languageList);
        mav.addObject(FUNCTION_LIST, appFunctionDeployService.getListFunctionDeployDtoByProcessId(processId));
        mav.addObject(OBJ_BUTTON_CLASS, constantDisplayService.getListJcaConstantDtoByKind(ConstantDisplayType.PROCESS_BUTTON_CLASS_TYPE.toString(), UserProfileUtils.getLanguage()));
        return mav;
    }

    
    @RequestMapping(value = "/function", method = { RequestMethod.GET })
    public ModelAndView getListFunction(@RequestParam(value = "processId") Long processId) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DEPLOY_FUNCTION);

        AppProcessDeployDto jpmProcessDto = new AppProcessDeployDto();
        jpmProcessDto.setListJpmFunctionDto(appFunctionDeployService.getListFunctionDeployDtoByProcessId(processId));

        mav.addObject(OBJECT_DTO, jpmProcessDto);

        return mav;
    }

    @RequestMapping(value = "/edit-function", method = { RequestMethod.GET })
    public ModelAndView editFunction(@ModelAttribute(value = OBJ_FUNCTION) AppFunctionDeployDto functionDto,
                                   @RequestParam(value = "functionId") Optional<Long> id,
                                   @RequestParam(value = "processId") Long processId) {

        if (id.isPresent())
            functionDto = appFunctionDeployService.getById(id.get());

        if (functionDto == null)
            functionDto = new AppFunctionDeployDto();

        functionDto.setProcessId(processId);

        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DEPLOY_FUNCTION_POPUP);
        mav.addObject(OBJ_FUNCTION, functionDto);
        return mav;
    }

    @RequestMapping(value = "/step", method = { RequestMethod.GET })
    public ModelAndView getListStep(@RequestParam(value = "processId") Long processId, Locale locale) {
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DEPLOY_STEP);

        AppProcessDeployDto jpmProcessDto = new AppProcessDeployDto();
        jpmProcessDto.setListJpmStepDto(appStepDeployService.getJpmStepDeployDtoDetailByProcessId(processId, locale.getLanguage()));

        mav.addObject(OBJECT_DTO, jpmProcessDto);

        return mav;
    }

    
    @RequestMapping(value = "/edit-step", method = { RequestMethod.GET })
    public ModelAndView editStep(@ModelAttribute(value = OBJ_STEP) AppStepDeployDto stepDto,
                                     @RequestParam(value = "stepId") Optional<Long> id,
                                     @RequestParam(value = "processId") Long processId,
                                     Locale locale) {

        if (id.isPresent())
            stepDto = appStepDeployService.getById(id.get());

        if (stepDto == null)
            stepDto = new AppStepDeployDto();

        stepDto.setProcessId(processId);
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        List<String> listCodeLanguage = languageList.stream().map(LanguageDto::getCode).sorted().collect(Collectors.toList());
        List<JpmStepLangDeployDto> listJpmStepLangDeploy = stepDto.getListJpmStepLangDeploy();
        
        if (null == listJpmStepLangDeploy)
            listJpmStepLangDeploy = new ArrayList<>();
        
        Set<String> setCodeLanguage = new HashSet<>(listCodeLanguage);
        setCodeLanguage.removeAll(listJpmStepLangDeploy.stream().map(JpmStepLangDeployDto::getLangCode).collect(Collectors.toList()));
        for (String code : setCodeLanguage) {
            JpmStepLangDeployDto newJpmButtonLangDeploy = new JpmStepLangDeployDto();
            newJpmButtonLangDeploy.setLangCode(code);
            listJpmStepLangDeploy.add(newJpmButtonLangDeploy);
        }

        stepDto.setListJpmStepLangDeploy(listJpmStepLangDeploy);
        
//        stepDto.setListJpmButton(appButtonForStepDeployService.getButtonForStepByStepId(stepDto.getId()));

        //Common status
        List<Select2Dto> statusCommons = new ArrayList<>();
		try {
			statusCommons = appStatusDeployService.getStatusListCommon(locale);
		} catch (Exception e) {
			logger.error("Can not get list common status");
		}
        
        ModelAndView mav = new ModelAndView(MAV_JPM_PROCESS_DEPLOY_STEP_POPUP);
        mav.addObject(OBJ_STEP, stepDto);
        mav.addObject(BUTTON_LIST, appButtonDeployService.getListButtonDeployDtoByProcessId(processId));
        mav.addObject(FUNCTION_LIST, appFunctionDeployService.getListFunctionDeployDtoByProcessId(processId));
        mav.addObject(STATUS_LIST, appStatusDeployService.getListStatusDeployDtoByProcessId(processId));
        mav.addObject(STATUS_COMMON_LIST, statusCommons);
        mav.addObject(STEP_KIND_LIST, StepKind.getList());
        mav.addObject(LANGUAGE_LIST, languageList);
        return mav;
    }
    
    
    /**
     * Ajax-Load-Process
     * @return String
     * @author KhuongTH
     */
    @RequestMapping(value ="/ajax/load-business", method = RequestMethod.POST)
    @ResponseBody
    public List<Select2Dto> getBusinessListForSelect(@RequestParam(required = false) Long companyId) {
		
		List<Select2Dto> businessList = jpmBusinessService.getSelect2DtoListCompanyId(companyId); 		
        
        return businessList;
    }
    
	@RequestMapping(value = "ajax/getListProcessDeploy", method = RequestMethod.POST)
	@ResponseBody
	public List<Select2Dto> getListProcessByBusinessId(@RequestParam(value = "businessId", required = true) Long businessId
			, Locale locale) {
		return appProcessDeployService.getJpmProcessDtoTypeSelect2DtoByBusinessId(businessId, locale.getLanguage());
	}
	
	@RequestMapping(value = "ajax/getListProcessDeployByFormId", method = RequestMethod.POST)
	@ResponseBody
	public Object getListProcessByFormId(@RequestParam(value = "keySearch", required = false) String keySearch
	        , @RequestParam(value = "formId", required = true) Long formId
			, @RequestParam(required = false) boolean isPaging, Locale locale) {
		
		Select2ResultDto obj = new Select2ResultDto();
        
        List<Select2Dto> lst = appProcessDeployService.getJpmProcessDeployListByFormId(keySearch, formId, isPaging, locale.getLanguage());
        obj.setTotal(lst.size());
        obj.setResults(lst);
		
		return obj;
	}
	
	@RequestMapping(value = "ajax/getListOrgByCompanyId", method = RequestMethod.POST)
	@ResponseBody
	public Object getListOrgByCompanyId(@RequestParam(value = "keySearch", required = false) String keySearch,
			@RequestParam(value = "companyId", required = false) Long companyId, @RequestParam(required = false) boolean isPaging) {
		
		Select2ResultDto obj = new Select2ResultDto();
		
		if(companyId == null) {
			companyId = UserProfileUtils.getCompanyId();
		}
        
		List<Select2Dto> lst = accountOrgService.findSelect2DtoByAccountId(UserProfileUtils.getAccountId(), companyId, keySearch, isPaging);
		
        obj.setTotal(lst.size());
        obj.setResults(lst);
		
		return obj;
	}
	
    /**
     * getListStatusByProcessDeployId
     * @param processDeployId
     * @return
     * @author KhoaNA
     */
    @RequestMapping(value = "ajax/getListStatusByProcessDeployId", method = RequestMethod.POST)
    @ResponseBody
    public Object getStatusListForSelect(@RequestParam(required = false) Long processDeployId, Locale locale) {
        Select2ResultDto obj = new Select2ResultDto();
        
        List<Select2Dto> lst = null;
        String lang = locale.getLanguage();
        if( processDeployId == null ) {
     		try {
     			lst = appStatusDeployService.getStatusListCommon(locale, ConstantDisplayType.PROFILE_STATUS.toString());
     		} catch (Exception e) {
     			logger.error("Can not get list common status");
     		}
        } else {
        	lst = appStatusDeployService.getStatusDeployListByProcessDeployId(processDeployId, lang.toUpperCase());
        }
        
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportJpmProcess(@RequestParam(required = true) Long id) throws Exception {

        JpmProcessImportExportDto processDto = appProcessDeployService.exportJpmProcessDeploy(id);
        ResponseEntity<byte[]> response = null;
        
        String json = CommonJsonUtil.convertObjectToJSON(processDto);
        json = CommonBase64Util.encode(json);
        try{
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            String fileName = CommonStringUtil.removeAccent(processDto.getProcessName());
            fileName = fileName.replace(ConstantCore.SPACE, ConstantCore.UNDERSCORE);
            fileName = "EXPORT_".concat(fileName);
            String extension = ".jpm";
            
            // Create header
            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + extension + "\"");
            header.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
            header.add(HttpHeaders.CONTENT_TYPE, FileStorageUtils.getContentType( fileName + extension));
            header.add(HttpHeaders.PRAGMA, "no-cache");
            header.add(HttpHeaders.EXPIRES, "0");
            header.add(HttpHeaders.ACCEPT_ENCODING, StandardCharsets.UTF_8.toString());
            header.add(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.toString());
            header.add(HttpHeaders.TRANSFER_ENCODING, StandardCharsets.UTF_8.toString());
            
            response = new ResponseEntity<>(bytes, header, HttpStatus.OK);
            
        }catch(Exception e) {
            logger.error(e.getMessage());
        }
        return response;
    }
}
