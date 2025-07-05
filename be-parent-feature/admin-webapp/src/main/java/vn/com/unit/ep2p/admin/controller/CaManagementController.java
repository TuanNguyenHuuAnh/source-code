/*******************************************************************************
 * Class        :CaManagementController
 * Created date :2019/08/26
 * Lasted date  :2019/08/26
 * Author       :HungHT
 * Change log   :2019/08/26:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaCaManagementDto;
import vn.com.unit.core.dto.JcaCaManagementSearchDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaCaManagement;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.enumdef.CaManagementSearchEnum;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.admin.service.CaManagementService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.validators.CaManagementValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.ep2p.export.util.SearchUtil;

/**
 * CaManagementController
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Controller
@RequestMapping("/ca-management")
public class CaManagementController {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(CaManagementController.class);

	@Autowired
    CaManagementValidator caManagementValidator;

    @Autowired
    CaManagementService caManagementService;

    @Autowired
    MessageSource msg;
    
    @Autowired
    SystemConfig systemConfig;
    
    @Autowired
    CompanyService companyService;
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    ObjectMapper objectMapper;

    private static final String SCREEN_FUNCTION_CODE = "SA1#S09_CAManagement";

    private static final String MAV_CA_MANAGEMENT_LIST = "/views/ca-management/ca-management-list.html";

    private static final String MAV_CA_MANAGEMENT_TABLE = "/views/ca-management/ca-management-table.html";

	private static final String MAV_CA_MANAGEMENT_DETAIL = "/views/ca-management/ca-management-detail.html";

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
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
    }

    /**
     * getCaManagementList
     * 
     * @param search
     * @param pageSizeParam
     * @param pageParam
	 * @param locale
     * @return
     * @author HungHT
     */
	@RequestMapping(value = { UrlConst.ROOT, UrlConst.LIST }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getCaManagementList(
			@ModelAttribute(value = OBJ_SEARCH) JcaCaManagementSearchDto search,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
	    
		ModelAndView mav = new ModelAndView(MAV_CA_MANAGEMENT_LIST);
		
		// set init search
        SearchUtil.setSearchSelect(CaManagementSearchEnum.class, mav);
        
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        search.setCompanyId(UserProfileUtils.getCompanyId());
        
		// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);
		// Get List
		PageWrapper<JcaCaManagementDto> pageWrapper = caManagementService.getCaManagementList(search, pageSize, page);
		// Object mav
		mav.addObject(OBJ_SEARCH, search);
		mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
		return mav;
	}

    /**
     * getCaManagementTable
     * 
     * @param search
     * @param pageSizeParam
     * @param pageParam
	 * @param locale
     * @return
     * @author HungHT
     */
	@RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
	public ModelAndView getCaManagementTable(
			@ModelAttribute(value = OBJ_SEARCH) JcaCaManagementSearchDto search,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		ModelAndView mav = new ModelAndView(MAV_CA_MANAGEMENT_TABLE);
		// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);
		// Get List
		PageWrapper<JcaCaManagementDto> pageWrapper = caManagementService.getCaManagementList(search, pageSize, page);

		// Object mav
		mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
		return mav;
	}

	/**
     * getCaManagementDetail
     * 
     * @param id
	 * @param locale
     * @return
     * @author HungHT
     */
    @RequestMapping(value = { UrlConst.ADD, UrlConst.DETAIL }, method = RequestMethod.GET)
    public ModelAndView getCaManagementDetail(@RequestParam(value = "id", required = false) Long id, Locale locale) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
        ModelAndView mav = new ModelAndView(MAV_CA_MANAGEMENT_DETAIL);

		// Object dto 
        JcaCaManagementDto objectDto = null;
		// URL ajax redirect
		StringBuilder urlRedirect = new StringBuilder("ca-management");
		if (id != null) {
			objectDto = caManagementService.findById(id);
			// Security for data
//	        if (null == objectDto || (objectDto.getCompanyId() != null && !UserProfileUtils.hasRoleForCompany(objectDto.getCompanyId()))) {
//	            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//	        }
	        
			urlRedirect.append(UrlConst.DETAIL.concat("?id=").concat(id.toString()));
		} else {
			objectDto = new JcaCaManagementDto();
			objectDto.setStoreType(ConstantCore.NUMBER_ONE);
			urlRedirect.append(UrlConst.ADD);
		}
		// Init screen
		caManagementService.initScreenDetail(mav, objectDto, locale);
        // Object mav
        mav.addObject(OBJECT_DTO, objectDto);
		mav.addObject(URL_REDIRECT, urlRedirect.toString());
        return mav;
    }

	/**
     * saveCaManagementDetail
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
	@RequestMapping(value = UrlConst.SAVE, method = { RequestMethod.POST })
	public ModelAndView saveCaManagementDetail(@ModelAttribute(value = OBJECT_DTO) JcaCaManagementDto objectDto,
			@RequestParam(value = "url", required = false) String urlRedirect, BindingResult bindingResult, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		ModelAndView mav = new ModelAndView(MAV_CA_MANAGEMENT_DETAIL);
		// Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        boolean saveFlg = false;
        String msgContent = null;
		JcaCaManagement objectSave = null;

        // Save
        try {
			// Validate
			caManagementValidator.validate(objectDto, bindingResult);

            if (bindingResult.hasErrors()) {
                throw new Exception();
            }

            JcaCaManagementDto dto = objectMapper.convertValue(objectDto, JcaCaManagementDto.class);
            dto.setCaManagementId(objectDto.getCaManagementId());
            objectSave = caManagementService.saveJcaCaManagementDto(dto);
            if (objectSave != null) {
                saveFlg = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // Check save
        if (saveFlg) {
			// Add message success
            if (objectDto.getCaManagementId() == null) {
                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
            } else {
                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
            }
            messageList.add(msgContent);
            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        } else {
            // Add message error
            messageList.setStatus(Message.ERROR);
            if (objectDto.getCaManagementId() == null) {
                msgContent = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
            } else {
                msgContent = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            }
            messageList.add(msgContent);

			// Init screen
			caManagementService.initScreenDetail(mav, objectDto, locale);

			// Object mav
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject(OBJECT_DTO, objectDto);
            mav.addObject(URL_REDIRECT, urlRedirect);
            return mav;
        }

		// Redirect
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("ca-management").concat(UrlConst.DETAIL);
        mav.setViewName(viewName);
        redirectAttributes.addAttribute("id", objectSave.getId());
        return mav;
	}

	 /**
     * deleteCaManagementDetail
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
    public ModelAndView deleteCaManagementDetail(@ModelAttribute(value = OBJ_SEARCH) JcaCaManagementSearchDto search,
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
            caManagementService.delete(id);
            deleteFlg = true;
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
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("ca-management").concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);
        return mav;
    }
    
    @PostMapping(value = "/get-account")
    @ResponseBody
    public Object getJpmBusinessList(@RequestParam(required = false) String keySearch, @RequestParam(required = true) Long companyId, @RequestParam(required = false) Long caId,
            @RequestParam(required = false) boolean isPaging) {
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> lst = accountService.findListSelect2DtoForCA(keySearch, companyId, caId, isPaging);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
    
    @RequestMapping(value = "/generate-label", method = RequestMethod.GET)
    @ResponseBody
    public JcaCaManagementDto ajaxDetail(@RequestParam(value = "companyId", required = true) Long companyId, 
            @RequestParam(value = "accountId", required = true) Long accountId, Locale locale) {
        JcaCaManagementDto caManagementDto = new JcaCaManagementDto();
        CompanyDto companyDto = companyService.findById(companyId);
        JcaAccount account = null;
        try {
            account = accountService.findById(accountId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(null != companyDto && null != account) {
            String label = companyDto.getSystemCode().concat(ConstantCore.UNDERSCORE).concat(account.getUsername());
            caManagementDto.setCaLabel(label);
            caManagementDto.setCaName(account.getFullname());
        }
        return caManagementDto;
    }
}