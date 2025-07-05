/*******************************************************************************
 * Class        JpmBusinessController
 * Created date 2019/06/10
 * Lasted date  2019/06/10
 * Author       KhoaNA
 * Change log   2019/06/10 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonSearchUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.workflow.enumdef.ProcessType;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.ConstantDisplayDto;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.utils.URLUtil;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.utils.ConditionSearchUtils;
import vn.com.unit.process.workflow.dto.AppBusinessDto;
import vn.com.unit.process.workflow.dto.AppBusinessSearchDto;
import vn.com.unit.process.workflow.enumdef.JpmBusinessSearchEnum;
import vn.com.unit.process.workflow.service.AppBusinessService;
import vn.com.unit.process.workflow.validators.AppBusinessValidator;

/**
 * JpmBusinessController
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Controller
@RequestMapping(value = UrlConst.JPM_BUSINESS)
public class AppBusinessController {

    @Autowired
    private MessageSource msg;

    @Autowired
    private CompanyService companyService;
    
//    @Autowired
//    private ConstantDisplayService constantDisplayService;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    AppBusinessService jpmBusinessService;
    
    @Autowired
    AppBusinessValidator jpmBusinessValidator;
    
    /** MAV */
    private static final String MAV_JPM_BUSINESS_EDIT = "/views/jpm-business/jpm-business-edit.html";
    private static final String MAV_JPM_BUSINESS_LIST = "/views/jpm-business/jpm-business-list.html";
    private static final String MAV_JPM_BUSINESS_TABLE = "/views/jpm-business/jpm-business-table.html";
    
    /** Params */
    private static final String ID = "id";
    private static final String OBJECT_DTO = "objectDto";
    private static final String URL = "url";
    
    private static final String COMPANY_LIST = "companyList";
    private static final String PROCESS_TYPE_LIST = "processTypeList";
    private static final String OBJ_SEARCH = "searchDto";
    private static final String OBJ_PAGE_WRAPPER = "pageWrapper";

    private static final String SCREEN_FUNCTION_CODE = RoleConstant.BUSINESS;

    private static final Logger logger = LoggerFactory.getLogger(AppBusinessController.class);

    /**
	 * Get JpmBusiness list
	 * @param formId
	 * @param id
	 * @param pageMode
	 * @return ModelAndView
     * @throws DetailException 
	 */
    @RequestMapping(value = { UrlConst.ROOT, UrlConst.LIST }, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getBusinessList(
            @ModelAttribute(value = OBJ_SEARCH) AppBusinessSearchDto search,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale, HttpServletRequest request) throws DetailException {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        ModelAndView mav = new ModelAndView(MAV_JPM_BUSINESS_LIST);
        
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject(COMPANY_LIST, companyList);
        search.setCompanyId(UserProfileUtils.getCompanyId());
    	// set init search
        CommonSearchUtil.setSearchSelect(JpmBusinessSearchEnum.class, mav);
    	
        // Init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        
        // Session Search
        ConditionSearchUtils<AppBusinessSearchDto> searchUtil = new ConditionSearchUtils<AppBusinessSearchDto>();
        String[] urlContains = new String[] { "jpm-business/add", "jpm-business/edit", "jpm-business/detail", "jpm-business/list" };
        search = searchUtil.getConditionSearch(this.getClass(), search, urlContains, request, page, pageSize);
        pageSize = Optional.ofNullable(search.getPageSize()).orElse(pageSize);
        page = Optional.ofNullable(search.getPage()).orElse(page);
        // Get List
        PageWrapper<AppBusinessDto> pageWrapper = jpmBusinessService.search(page, pageSize, search);

        // Object mav
        mav.addObject(OBJ_SEARCH, search);
        mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);

        return mav;
    }

    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = OBJ_SEARCH) AppBusinessSearchDto searchDto,
                                 @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
                                 @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, 
                                 Locale locale, HttpServletRequest request) throws DetailException {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

    	ModelAndView mav = new ModelAndView(MAV_JPM_BUSINESS_TABLE);
    	
    	// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);
		// Get List
		PageWrapper<AppBusinessDto> pageWrapper = jpmBusinessService.search(page, pageSize, searchDto);

		// Session Search
        ConditionSearchUtils<AppBusinessSearchDto> searchUtil = new ConditionSearchUtils<AppBusinessSearchDto>();
        searchUtil.setConditionSearch(request, this.getClass(), searchDto, page, pageSize);
        
        mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
        return mav;
    }

    /**
	 * Get JpmBusinessDto
	 * @param id
	 * @param model
	 * @return String
	 * @author KhoaNA
	 */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView getJpmBusinessDto(@RequestParam(value = ID, required = false) Long id, Locale locale) {

       // Security for this page.
       if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
       }

       ModelAndView mav = new ModelAndView(MAV_JPM_BUSINESS_EDIT);
    	
    	AppBusinessDto jpmBusinessDto = new AppBusinessDto();
    	if( id != null ) {
    		jpmBusinessDto = jpmBusinessService.getAppBusinessDtoById(id);
    		 // Security for data 
//            if (null == jpmBusinessDto || !UserProfileUtils.hasRoleForCompany(jpmBusinessDto.getCompanyId())) {
//                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//            }
    		/*if( jpmBusinessDto == null ) {
    			throw new BusinessException("JmpBusiness not found with id=" + id);
    		}*/
    	}else {
    	    jpmBusinessDto.setCompanyId(UserProfileUtils.getCompanyId()); 
    	    jpmBusinessDto.setIsActive(true);
    	    jpmBusinessDto.setIsAuthority(true);
    	}
    	
    	// URL ajax redirect
		StringBuilder urlRedirect = new StringBuilder(UrlConst.JPM_BUSINESS.substring(1).concat(UrlConst.EDIT));
		if( id != null ) {
			urlRedirect.append(URLUtil.buildParamWithPrefix(true, ID, id));
		}
		
		mav.addObject(URL, urlRedirect.toString());
		mav.addObject(OBJECT_DTO, jpmBusinessDto);
		
		this.initDataForEditScreen(mav, jpmBusinessDto, locale);
    			
    	return mav;
    }

    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    public ModelAndView saveJpmBusinessDto(@Valid @ModelAttribute(value = OBJECT_DTO) AppBusinessDto objectDto, BindingResult bindingResult,
    					@RequestParam(value = URL, required = false) String urlRedirect, Locale locale,
                        RedirectAttributes redirectAttributes, HttpServletRequest request) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(MAV_JPM_BUSINESS_EDIT);
        
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        
        Long id = objectDto.getId();
        
        jpmBusinessValidator.validate(objectDto, bindingResult);
        
        //Validation
        if (bindingResult.hasErrors()) {
        	this.initDataForEditScreenWhenException(messageList, mav, urlRedirect, id, objectDto, locale);
            return mav;
        }

        try {
            jpmBusinessService.saveJpmBusinessDto(objectDto);
		} catch (Exception e) {
//			logger.error("Error: ", e);
			this.initDataForEditScreenWhenException(messageList, mav, urlRedirect, id, objectDto, locale);
            return mav;
		}
        
        String msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
        // Add message success
        if (id != null) {
        	msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        }
        messageList.add(msgContent);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        
        id = objectDto.getId();
        // Redirect
        mav.setViewName(UrlConst.REDIRECT.concat(UrlConst.JPM_BUSINESS).concat(UrlConst.EDIT));
        redirectAttributes.addAttribute(ID, id);
        return mav;
    }
    
    private void initDataForEditScreenWhenException(MessageList messageList, ModelAndView mav, String urlRedirect, Long id, AppBusinessDto objectDto, Locale locale) {
    	// Add message error
        messageList.setStatus(Message.ERROR);
        
        String msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
        
        if( id == null ) {
        	msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
        }
        messageList.add(msgError);
        
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject(URL, urlRedirect.toString());
        mav.addObject(OBJECT_DTO, objectDto);
		
		this.initDataForEditScreen(mav, objectDto, locale);
    }

    @RequestMapping(value = UrlConst.DELETE, method = { RequestMethod.POST })
    public ModelAndView deleteBusiness(@ModelAttribute(value = OBJ_SEARCH) AppBusinessSearchDto search,
                                       @RequestParam(value = "id") Long id,
                                       Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        boolean deleteFlg = false;
        String msgContent = null;

        // delete
        try {
            deleteFlg = jpmBusinessService.deletedById(id);
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
            msgContent = msg.getMessage("message.error.business.management", null, locale);
        }
        messageList.add(msgContent);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addFlashAttribute(OBJ_SEARCH, search);

        // Redirect
        String viewName = UrlConst.REDIRECT.concat(UrlConst.JPM_BUSINESS).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);
        return mav;
    }

    /**
	 * initDataForEditScreen
	 * 
	 * @param mav
	 * 			type ModelAndView
	 * @author KhoaNA
	 */
    private void initDataForEditScreen(ModelAndView mav, AppBusinessDto jpmBusinessDto, Locale locale) {
        // Get company list
//    	Long companyId = UserProfileUtils.getCompanyId();
    	boolean isCompanyAdmin = UserProfileUtils.isCompanyAdmin();
    	List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), isCompanyAdmin);
        mav.addObject(COMPANY_LIST, companyList);
        
        //Process type list
//        ConstantDisplayDto condition = new ConstantDisplayDto();
//        condition.setType(ConstantDisplayType.J_PROCESS_TYPE_001.toString());
//        List<ConstantDisplayDto> processTypes = constantDisplayService.findConstantDisplayDtoByCondition(condition, locale);
        List<ConstantDisplayDto> processTypes = new ArrayList<>();
        for(ProcessType processType : ProcessType.values()) {
            ConstantDisplayDto constantDisplayDto = new ConstantDisplayDto(0, String.valueOf(processType.getValue()), String.valueOf(processType.getValue()));
            constantDisplayDto.setCatOfficialNameVi(processType.getText());
            processTypes.add(constantDisplayDto);
        }
        mav.addObject(PROCESS_TYPE_LIST, processTypes);
    }
    
    /**
     * getJpmBusinessList
     * @param keySearch
     * @param companyId
     * @param isPaging
     * @return
     * @author trieuvd
     */
    @PostMapping(value = "/get-jpm-business")
    @ResponseBody
    public Object getJpmBusinessList(@RequestParam(required = false) String keySearch, @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) boolean isPaging) {
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> lst = jpmBusinessService.getSelect2DtoListByTermAndCompanyId(keySearch, companyId, false, isPaging);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
    
    @PostMapping(value = "/get-jpm-business-unregistered")
    @ResponseBody
    public Object getJpmBusinessUnregisteredList(@RequestParam(required = false) String keySearch, @RequestParam(required = false) Long companyId, @RequestParam(required = false) Integer formType,
            @RequestParam(required = false) boolean isPaging) {
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> lst = jpmBusinessService.findSelect2DtoUnregisteredByCompanyId(keySearch, companyId, formType, isPaging);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
    
    @PostMapping(value = "/ajax-get-business")
    @ResponseBody
    public Object ajaxGetJpmBusiness(@RequestParam(required = true) Long businessId) {
        
        AppBusinessDto jpmBusinessDto = jpmBusinessService.getAppBusinessDtoById(businessId);
        
        return jpmBusinessDto;
    }
}
