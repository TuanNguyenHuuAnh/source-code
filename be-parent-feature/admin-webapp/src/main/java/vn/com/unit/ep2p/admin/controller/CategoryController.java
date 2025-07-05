/*******************************************************************************
 * Class        :CategoryController
 * Created date :2019/04/16
 * Lasted date  :2019/04/16
 * Author       :HungHT
 * Change log   :2019/04/16:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonSearchUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.enumdef.param.JcaCategorySearchEnum;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.AppLanguageService;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.service.CategoryService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.validators.CategoryValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryDto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryLangDto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategorySearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoCategory;

/**
 * CategoryController
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Controller
@RequestMapping(UrlConst.CATEGORY)
public class CategoryController {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
    CategoryValidator categoryValidator;

    @Autowired
    CategoryService categoryService;

    @Autowired
    MessageSource msg;
    
    @Autowired
    SystemConfig systemConfig;
    
    @Autowired
	private AppLanguageService languageService;
    
    @Autowired
    private CompanyService companyService;

    private static final String SCREEN_FUNCTION_CODE = RoleConstant.CATEGORY_LIST;

    private static final String MAV_CATEGORY_LIST = "/views/category/category-list.html";

    private static final String MAV_CATEGORY_TABLE = "/views/category/category-table.html";

	private static final String MAV_CATEGORY_DETAIL = "/views/category/category-detail.html";

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
     * getCategoryList
     * 
     * @param search
     * @param pageSizeParam
     * @param pageParam
	 * @param locale
     * @return
     * @author HungHT
     */
	@RequestMapping(value = { UrlConst.ROOT, UrlConst.LIST }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getCategoryList(
			@ModelAttribute(value = OBJ_SEARCH) EfoCategorySearchDto search,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) {

		// Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
		ModelAndView mav = new ModelAndView(MAV_CATEGORY_LIST);
		
		// set init search
        CommonSearchUtil.setSearchSelect(JcaCategorySearchEnum.class, mav);
        
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        search.setCompanyId(UserProfileUtils.getCompanyId());
        
		// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);
		// Get List
		String lang = locale.getLanguage();
		PageWrapper<EfoCategoryDto> pageWrapper = categoryService.getCategoryList(search, pageSize, page, lang);
		// Object mav
		mav.addObject(OBJ_SEARCH, search);
		mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
		return mav;
	}

    /**
     * getCategoryTable
     * 
     * @param search
     * @param pageSizeParam
     * @param pageParam
	 * @param locale
     * @return
     * @author HungHT
     */
	@RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
	public ModelAndView getCategoryTable(
			@ModelAttribute(value = OBJ_SEARCH) EfoCategorySearchDto search,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) {

	    // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
		ModelAndView mav = new ModelAndView(MAV_CATEGORY_TABLE);
		
		// Init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);
		// Get List
		String lang = locale.getLanguage();
		PageWrapper<EfoCategoryDto> pageWrapper = categoryService.getCategoryList(search, pageSize, page, lang);

		// Object mav
		mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
		return mav;
	}

	/**
     * getCategoryDetail
     * 
     * @param id
	 * @param locale
     * @return
     * @author HungHT
     */
    @RequestMapping(value = { UrlConst.ADD, UrlConst.DETAIL }, method = RequestMethod.GET)
    public ModelAndView getCategoryDetail(@RequestParam(value = "id", required = false) Long id, Locale locale) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(MAV_CATEGORY_DETAIL);
        String lang = locale.getLanguage();
        
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        //add languageList
      	List<LanguageDto> languageList = languageService.getLanguageDtoList();
      	mav.addObject("languageList", languageList);

		// Object dto
        EfoCategoryDto objectDto = null;
		// URL ajax redirect
		StringBuilder urlRedirect = new StringBuilder(UrlConst.CATEGORY.substring(1));
		if (id != null) {
			objectDto = categoryService.findById(id, lang);
			// Security for data 
//	        if (null == objectDto || !UserProfileUtils.hasRoleForCompany(objectDto.getCompanyId())) {
//	            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//	        }
			//add list categorylanguage
			List<EfoCategoryLangDto> categoryLanguageDtos = new ArrayList<>();
			List<EfoCategoryLangDto> categoryLanguageDtosTmp = new ArrayList<>();
			categoryLanguageDtosTmp = categoryService.getCategoryLanguageByCategoryId(id);
			// Build language for category when edit. listMenuLanguageTmp not empty.			
			for (LanguageDto languageDto : languageList) {
				boolean checkExist = false;
				for (EfoCategoryLangDto categoryLanguageDto : categoryLanguageDtosTmp) {
					if (languageDto.getCode().equals(categoryLanguageDto.getLangCode())) {
						EfoCategoryLangDto newCategoryLanguageDto = new EfoCategoryLangDto();
						newCategoryLanguageDto.setCategoryId(categoryLanguageDto.getCategoryId());
						newCategoryLanguageDto.setLangCode(categoryLanguageDto.getLangCode());
						newCategoryLanguageDto.setCategoryId(categoryLanguageDto.getCategoryId());
						newCategoryLanguageDto.setName(categoryLanguageDto.getName());
						categoryLanguageDtos.add(newCategoryLanguageDto);
						checkExist = true;
						break;
					}
				}
				if (!checkExist) {
					EfoCategoryLangDto newCategoryLanguageDto = new EfoCategoryLangDto();
					newCategoryLanguageDto.setLangCode(languageDto.getCode());
					categoryLanguageDtos.add(newCategoryLanguageDto);
				}
			}
			
			objectDto.setListCategoryLangDto(categoryLanguageDtos);
			
			urlRedirect.append(UrlConst.DETAIL.concat("?id=").concat(id.toString()));
		} else {
			objectDto = new EfoCategoryDto();
			objectDto.setActived(true);
			objectDto.setDisplayOrder(categoryService.findMaxDisplayOrderByCompanyId(UserProfileUtils.getCompanyId()) + 1);
			objectDto.setCompanyId(UserProfileUtils.getCompanyId());
//			objectDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
			urlRedirect.append(UrlConst.ADD);
		}
		
        // Object mav
        mav.addObject(OBJECT_DTO, objectDto);
		mav.addObject(URL_REDIRECT, urlRedirect.toString());
		
        return mav;
    }

	/**
     * saveCategoryDetail
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
	public ModelAndView saveCategoryDetail(@ModelAttribute(value = OBJECT_DTO) EfoCategoryDto objectDto, BindingResult bindingResult,
			@RequestParam(value = "url", required = false) String urlRedirect, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {

	    // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
		ModelAndView mav = new ModelAndView(MAV_CATEGORY_DETAIL);
		// Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        boolean saveFlg = false;
        String msgContent = null;
		EfoCategory objectSave = null;

        // Save
        try {
			// Validate
			categoryValidator.validate(objectDto, bindingResult);

            if (bindingResult.hasErrors()) {
                throw new Exception();
            }

            objectSave = categoryService.saveCategory(objectDto);
            if (objectSave != null) {
                saveFlg = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // Check save
        if (saveFlg) {
			// Add message success
            if (objectDto.getCategoryId() == null) {
                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
            } else {
                msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
            }
            messageList.add(msgContent);
            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        } else {
            // Add message error
            messageList.setStatus(Message.ERROR);
            if (objectDto.getCategoryId() == null) {
                msgContent = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
            } else {
                msgContent = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            }
            messageList.add(msgContent);
            // Add company list
            List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
            mav.addObject("companyList", companyList);
			// Object mav
            //add languageList
      		List<LanguageDto> languageList = languageService.getLanguageDtoList();
      		mav.addObject("languageList", languageList);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject(OBJECT_DTO, objectDto);
            mav.addObject(URL_REDIRECT, urlRedirect);
            return mav;
        }

		// Redirect
		String viewName = UrlConst.REDIRECT.concat(UrlConst.CATEGORY).concat(UrlConst.DETAIL);
        mav.setViewName(viewName);
        redirectAttributes.addAttribute("id", objectSave.getId());
        return mav;
	}

	 /**
     * deleteCategoryDetail
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
    public ModelAndView deleteCategoryDetail(@ModelAttribute(value = OBJ_SEARCH) EfoCategorySearchDto search,
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
            deleteFlg = categoryService.deleteCategory(id);
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
        String viewName = UrlConst.REDIRECT.concat(UrlConst.CATEGORY).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);
        return mav;
    }
    
    /**
     * getCategoryList
     * @param keySearch
     * @param companyId
     * @param isPaging
     * @return
     * @author HungHT
     */
    @RequestMapping(value = "/get-category", method = RequestMethod.POST)
    @ResponseBody
    public Object getCategoryList(@RequestParam(required = false) String keySearch, @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) boolean isPaging, Locale locale) {
    	String lang = locale.getLanguage();
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> lst = new ArrayList<Select2Dto>();
        if(companyId != 0) {
            lst = categoryService.getCategoryListByCompanyId(keySearch, companyId, isPaging, lang);
        }else {
//            lst = categoryService.findCategoryByListCompanyId(keySearch, UserProfileUtils.getCompanyIdList(), isPaging, lang);
        }
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
}