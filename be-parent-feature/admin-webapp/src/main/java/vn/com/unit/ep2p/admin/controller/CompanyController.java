/*******************************************************************************
 * Class        :CompanyController
 * Created date :2019/05/07
 * Lasted date  :2019/05/07
 * Author       :HungHT
 * Change log   :2019/05/07:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import vn.com.unit.common.exception.AppException;
import vn.com.unit.core.dto.JcaCompanyDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.ResultDto;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.enumdef.CompanySearchEnum;
import vn.com.unit.ep2p.admin.enumdef.ResultStatus;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.validators.CompanyValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.dto.CommonSearchDto;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.ep2p.dto.CompanySearchDto;
import vn.com.unit.ep2p.export.util.SearchUtil;
import vn.com.unit.ep2p.utils.ConditionSearchUtils;
import vn.com.unit.ep2p.utils.ExecMessage;

/**
 * CompanyController
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Controller
@RequestMapping("/company")
public class CompanyController {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
    CompanyValidator companyValidator;

    @Autowired
    CompanyService companyService;

    @Autowired
    MessageSource msg;
    
    @Autowired
    SystemConfig systemConfig;

    private static final String SCREEN_FUNCTION_CODE = RoleConstant.COMPANY_LIST;
    
    private static final String SCREEN_FUNCTION_CODE_CONFIG = RoleConstant.COMPANY_CONFIG;


    private static final String MAV_COMPANY_LIST = "/views/company/company-list.html";

    private static final String MAV_COMPANY_TABLE = "/views/company/company-table.html";

	private static final String MAV_COMPANY_DETAIL = "/views/company/company-detail-main.html";
	
	private static final String MAV_COMPANY_CONFIG = "/views/company/company-config.html";
	
	private static final String MAV_COMPANY_CONFIG_DETAIL = "/views/company/company-detail.html";
	
    private static final String OBJ_SEARCH = "search";

    private static final String OBJ_PAGE_WRAPPER = "pageWrapper";

	private static final String OBJECT_DTO = "objectDto";
	
	private static final String URL_REDIRECT = "urlRedirect";

    /**
     * dateBinder
     * 
     * @param binder
     * @param request
     * @param locale
     * @author HungHT
     */
    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
		// The date format to parse or output your dates
        String patternDate = (String) request.getSession().getAttribute(ConstantCore.FORMAT_DATE);
        if (patternDate == null) {
            patternDate = ConstantCore.FORMAT_DATE_FULL;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
    }

    /**
     * getCompanyList
     * 
     * @param search
     * @param pageSizeParam
     * @param pageParam
	 * @param locale
     * @return
     * @author HungHT
     */
    @RequestMapping(value = { UrlConst.ROOT, UrlConst.LIST }, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getCompanyList(@ModelAttribute(value = OBJ_SEARCH) CompanySearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request,
            Locale locale) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(MAV_COMPANY_LIST);

        // Set init search
        SearchUtil.setSearchSelect(CompanySearchEnum.class, mav);

        // Init PageWrapper
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);

        // Session Search
        ConditionSearchUtils<CompanySearchDto> searchUtil = new ConditionSearchUtils<CompanySearchDto>();
        String[] urlContains = new String[] { "company/add", "company/edit", "company/detail", "company/list" };
        searchDto = searchUtil.getConditionSearch(this.getClass(), searchDto, urlContains, request, page, pageSize);
        pageSize = Optional.ofNullable(searchDto.getPageSize()).orElse(pageSize);
        page = Optional.ofNullable(searchDto.getPage()).orElse(page);

        // Get List
        PageWrapper<JcaCompanyDto> pageWrapper = null;
        try {
            pageWrapper = companyService.getCompanyList(searchDto, pageSize, page);
        } catch (DetailException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Object mav
        mav.addObject(OBJ_SEARCH, searchDto);
        mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
        return mav;
    }

    /**
     * getCompanyTable
     * 
     * @param search
     * @param pageSizeParam
     * @param pageParam
	 * @param locale
     * @return
     * @author HungHT
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    public ModelAndView getCompanyTable(@ModelAttribute(value = OBJ_SEARCH) CompanySearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request,
            Locale locale) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(MAV_COMPANY_TABLE);
        // Init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        // Get List
        PageWrapper<JcaCompanyDto> pageWrapper = null;
        try {
            pageWrapper = companyService.getCompanyList(searchDto, pageSize, page);
        } catch (DetailException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Object mav
        mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);

        // Session Search
        ConditionSearchUtils<CommonSearchDto> searchUtil = new ConditionSearchUtils<CommonSearchDto>();
        searchUtil.setConditionSearch(request, this.getClass(), searchDto, page, pageSize);

        return mav;
    }

	/**
     * getCompanyDetail
     * 
     * @param id
	 * @param locale
     * @return
     * @author HungHT
     */
    @RequestMapping(value = { UrlConst.ADD, UrlConst.DETAIL }, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getCompanyDetail(@RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "status", required = false) Long status, Locale locale) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(MAV_COMPANY_DETAIL);
        if (null == status) {
            // Object dto
            CompanyDto objectDto = null;
            // URL ajax redirect
            StringBuilder urlRedirect = new StringBuilder("company");
            if (id != null) {
                objectDto = companyService.findById(id);
                urlRedirect.append(UrlConst.DETAIL.concat("?id=").concat(id.toString()));
            } else {
                objectDto = new CompanyDto();
                objectDto.setActived(true);
                urlRedirect.append(UrlConst.ADD);
            }
            // Init screen
            companyService.initScreenDetail(mav, objectDto, locale);

            // Object mav
            mav.addObject(OBJECT_DTO, objectDto);
            mav.addObject(URL_REDIRECT, urlRedirect.toString());
        } else {
            mav = new ModelAndView("/views/commons/message-alert.html");
            MessageList messageList = new MessageList(Message.SUCCESS);
            String msgContent = null;
            if (ResultStatus.SUCCESS.toInt() == status) {

                // Set message
                msgContent = ResultStatus.SUCCESS.toInt() == status
        				? msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale)
        				: msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);	
            } else {
                // Set message
                messageList.setStatus(Message.ERROR);
                if (StringUtils.isNotBlank(message)) {
                	if(message.equals("System code is already being used, please select another code!"))
                		msgContent = msg.getMessage(ConstantCore.MSG_SYSTEM_CODE_EXISTED, null, locale);
                	else
                		msgContent = msg.getMessage(ConstantCore.MSG_ERROR_SERIAL_INFO_EXPIRED, null, locale);
                } else {
                    msgContent = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
                }
            }
            messageList.add(msgContent);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }
        return mav;
    }
    
    /**
     * companyConfig
     * @param message
     * @param status
     * @param locale
     * @return
     * @author HungHT
     */
    @RequestMapping(value = UrlConst.ROOT + UrlConst.CONFIG, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView companyConfig(@RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "status", required = false) Long status, Locale locale) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CONFIG)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CONFIG.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CONFIG.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(MAV_COMPANY_CONFIG);
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        mav.addObject("companyId", UserProfileUtils.getCompanyId());
        if (null == status) {
            // Object dto
            CompanyDto objectDto = companyService.findById(UserProfileUtils.getCompanyId());
            objectDto.setScreenConfig(true);
            // URL ajax redirect
            StringBuilder urlRedirect = new StringBuilder("company");
            urlRedirect.append(UrlConst.ROOT + UrlConst.CONFIG);
            // Init screen
            companyService.initScreenDetail(mav, objectDto, locale);

            // Object mav
            mav.addObject(OBJECT_DTO, objectDto);
            mav.addObject(URL_REDIRECT, urlRedirect.toString());
        } else {
            mav = new ModelAndView("/views/commons/message-alert.html");
            MessageList messageList = new MessageList(Message.SUCCESS);
            String msgContent = null;
            if (ResultStatus.SUCCESS.toInt() == status) {

                // Set message
                msgContent = message;
            } else {
                // Set message
                messageList.setStatus(Message.ERROR);
                if (StringUtils.isNotBlank(message)) {
                    msgContent = message;
                } else {
                    msgContent = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
                }
            }
            messageList.add(msgContent);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }
        return mav;
    }
    
    /**
     * saveCompany
     * 
     * @param objectDto
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return
     * @author HungHT
     */
    @PostMapping(value = UrlConst.SAVE)
    @ResponseBody
    public ResultDto saveCompany(@ModelAttribute(value = OBJ_SEARCH) CompanyDto objectDto, BindingResult bindingResult, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {

        ResultDto result = new ResultDto();
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CONFIG)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CONFIG.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CONFIG.concat(ConstantCore.COLON_EDIT))) {
            result.setStatus(0);
            result.setMessage(ExecMessage.getErrorMessage(msg, "B006", locale).getErrorDesc());
            return result;
        }

        try {
            result = companyService.saveCompany(objectDto, locale);
        } catch (AppException e) {
            result.setStatus(0);
            result.setMessage(ExecMessage.getErrorMessage(msg, e.getCode(), e.getArgs(), locale).getErrorDesc());
            logger.error(e.getMessage());
        } catch (Exception e) {
            result.setStatus(0);
            logger.error(e.getMessage());
        }
        return result;
    }

	/**
     * saveCompanyDetail
     * 
     * @param objectDto
	 * @param urlRedirect
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return
     * @author HungHT
     */
//	@RequestMapping(value = UrlConst.SAVE, method = { RequestMethod.POST })
//	public ModelAndView saveCompanyDetail(@ModelAttribute(value = OBJECT_DTO) CompanyDto objectDto,
//			@RequestParam(value = "url", required = false) String urlRedirect, BindingResult bindingResult, Locale locale,
//            RedirectAttributes redirectAttributes, HttpServletRequest request) {
//
//	    // Security for this page.
//        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//		ModelAndView mav = new ModelAndView(MAV_COMPANY_DETAIL);
//		// Init message list
//        MessageList messageList = new MessageList(Message.SUCCESS);
//        boolean saveFlg = false;
//        String msgContent = null;
//		Company objectSave = null;
//
//        // Save
//        try {
//			// Validate
//			companyValidator.validate(objectDto, bindingResult);
//
//            if (bindingResult.hasErrors()) {
//                throw new Exception();
//            }
//
//            objectSave = companyService.saveCompany(objectDto);
//            if (objectSave != null) {
//                saveFlg = true;
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//
//        // Check save
//        if (saveFlg) {
//			// Add message success
//            if (objectDto.getId() == null) {
//                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
//            } else {
//                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
//            }
//            messageList.add(msgContent);
//            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
//        } else {
//            // Add message error
//            messageList.setStatus(Message.ERROR);
//            if (objectDto.getId() == null) {
//                msgContent = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
//            } else {
//                msgContent = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
//            }
//            messageList.add(msgContent);
//
//			// Init screen
//			companyService.initScreenDetail(mav, objectDto, locale);
//
//			// Object mav
//            mav.addObject(ConstantCore.MSG_LIST, messageList);
//            mav.addObject(OBJECT_DTO, objectDto);
//            mav.addObject(URL_REDIRECT, urlRedirect);
//            return mav;
//        }
//
//		// Redirect
//		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("company").concat(UrlConst.DETAIL);
//        mav.setViewName(viewName);
//        redirectAttributes.addAttribute("id", objectSave.getId());
//        return mav;
//	}

	 /**
     * deleteCompanyDetail
     * 
     * @param search
     * @param id
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return
     * @author HungHT
     */
    @RequestMapping(value = UrlConst.DELETE, method = { RequestMethod.POST })
    public ModelAndView deleteCompanyDetail(@ModelAttribute(value = OBJ_SEARCH) CompanySearchDto search,
            @RequestParam(value = "id") Long id, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {

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
            deleteFlg = companyService.deleteCompany(id);
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

        // Redirect
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("company").concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);
        return mav;
    }
    
    /**
     * getCompanyList
     * 
     * @param keySearch
     * @param isPaging
     * @return
     * @author HungHT
     */
    @RequestMapping(value = "/get-company", method = RequestMethod.POST)
    @ResponseBody
    public Object getCompanyList(@RequestParam(required = false) String keySearch, @RequestParam(required = false) boolean isPaging) {
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> lst = companyService.getCompanyListByCompanyId(keySearch, null, true, true);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
    
    @RequestMapping(value = UrlConst.ROOT + UrlConst.CONFIG + UrlConst.DETAIL, method = { RequestMethod.GET})
    public ModelAndView companyConfigDetail(@RequestParam(value = "companyId", required = true) Long companyId, Locale locale) {

//         Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CONFIG)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CONFIG.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CONFIG.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(MAV_COMPANY_CONFIG_DETAIL);
            // Object dto
            CompanyDto objectDto = companyService.findById(companyId);
            objectDto.setScreenConfig(true);
            // Init screen
            companyService.initScreenDetail(mav, objectDto, locale);

            // Object mav
            mav.addObject(OBJECT_DTO, objectDto);
            
            return mav;
    }
}