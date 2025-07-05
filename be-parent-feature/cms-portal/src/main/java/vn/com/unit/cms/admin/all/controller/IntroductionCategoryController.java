package vn.com.unit.cms.admin.all.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.hornetq.utils.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
import vn.com.unit.cms.admin.all.constant.CmsStepNoStatusConstant;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
//import vn.com.unit.cms.admin.all.core.IntroductionCategoryNode;
//import vn.com.unit.cms.admin.all.dto.BannerLanguageDto;
import vn.com.unit.cms.admin.all.dto.IntroductionCategoryDto;
import vn.com.unit.cms.admin.all.dto.IntroductionCategoryLanguageDto;
import vn.com.unit.cms.admin.all.dto.IntroductionLanguageDto;
//import vn.com.unit.cms.admin.all.dto.IntroductionTypeSelectDto;
//import vn.com.unit.cms.admin.all.entity.BannerLanguage;
import vn.com.unit.cms.admin.all.entity.IntroductionCategory;
import vn.com.unit.cms.admin.all.enumdef.BannerTypeEnum;
import vn.com.unit.cms.admin.all.enumdef.EnabledStatusEnum;
//import vn.com.unit.cms.admin.all.enumdef.IntroductionTypeEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.service.BannerLanguageService;
import vn.com.unit.cms.admin.all.service.BannerService;
import vn.com.unit.cms.admin.all.service.CmsFileService;
//import vn.com.unit.cms.admin.all.service.BannerLanguageService;
//import vn.com.unit.cms.admin.all.service.BannerService;
import vn.com.unit.cms.admin.all.service.IntroductionCategoryService;
import vn.com.unit.cms.admin.all.validator.IntroductionCategoryValidator;
import vn.com.unit.cms.admin.all.validator.IntroductionValidator;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;
import vn.com.unit.cms.core.module.banner.dto.ImageDto;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.common.utils.CommonJsonUtil;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.common.utils.CommonUtil;
//import vn.com.unit.util.Util;
//import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.LanguageDto;
//import vn.com.unit.jcanary.entity.ConstantDisplay;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.security.UserProfileUtils;
//import vn.com.unit.jcanary.service.HistoryApproveService;
//import vn.com.unit.jcanary.service.JProcessService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.controller.DocumentWorkflowCommonController;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

@Controller
@RequestMapping(value = UrlConst.URL_INTRODUCTION_CATEGORY)
public class IntroductionCategoryController
		extends DocumentWorkflowCommonController<IntroductionCategoryDto, IntroductionCategoryDto> {

	private static final String EDITOR_DIRECTORY_NAME = "editor";

	private static final String INTRODUCTION_DIRECTORY_NAME = "introduction-category";

	private static final String URL_EDITOR_IMAGE_UPLOAD = "/editor/upload";

	private static final String URL_EDITOR_FILE_UPLOAD = "/editor/file/upload";

	private static final String URL_EDITOR_DOWNLOAD = "/editor/download";

	private static final String HDB_ADMIN_INTRODUCTION_CATEGORY_AJAX_TABLE = "views/CMS/all/introduction/category/introduction-category-table.html"; // "hdb.admin.introduction.category.ajax.table"
	private static final String HDB_ADMIN_INTRODUCTION_CATEGORY_EDIT_VIEW = "views/CMS/all/introduction/category/introduction-category-edit.html"; // "hdb.admin.introduction.category.edit"
	private static final String HDB_ADMIN_INTRODUCTION_CATEGORY_LIST_VIEW = "views/CMS/all/introduction/category/introduction-category-list.html"; // "hdb.admin.introduction.category.list"
	private static final String HDB_ADMIN_INTRODUCTION_CATEGORY_LIST_SORT = "hdb.admin.introduction.category.list.sort";

	private static final String URL_CATEGORY_EDIT = "/edit";
	private static final String URL_CATEGORY_LIST = "/list";
	private static final String URL_CATEGORY_AJAX_LIST = "/ajaxList";

	private static final Long HDBANK_CUSTOMER_TYPE_ID = 11L;

	@Autowired
	IntroductionCategoryService introductionCategoryService;

	/** MessageSource */
	@Autowired
	LanguageService langService;

	@Autowired
	private MessageSource msg;

	@Autowired
	SystemConfig sysConfig;

	@Autowired
	IntroductionCategoryValidator categoryValidator;

	@Autowired
	IntroductionValidator introductionValidator;

//    @Autowired
//    HistoryApproveService historyApproveService;
//    
	@Autowired
	ConstantDisplayService constDisplayService;

	@Autowired
	CmsFileService fileService;

	@Autowired
	CmsCommonService commonService;

	@Autowired
	BannerService bannerService;

	@Autowired
	BannerLanguageService bannerLanguageService;

	@Autowired
	private LanguageService languageService;

//    @Autowired
//    private JProcessService jprocessService;

//	@InitBinder
	@Override
	public void customeBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
		if (null == request.getSession().getAttribute(ConstantCore.FORMAT_DATE)) {
			request.getSession().setAttribute("formatDate", sysConfig.getConfig(SystemConfig.DATE_PATTERN));
		}
		// The date format to parse or output your dates
		String patternDate = ConstantCore.FORMAT_DATE_FULL;
		SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
	}

	@RequestMapping(value = URL_CATEGORY_LIST, method = RequestMethod.GET)
	public ModelAndView getCategoryList(@ModelAttribute(value = "searchDto") CommonSearchDto searchDto,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale, Model model) {

		/*---- taitm - phân quyền theo menu -------*/
		if (!hasRoleAccess()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		/*---- END -------*/

		ModelAndView mav = new ModelAndView(HDB_ADMIN_INTRODUCTION_CATEGORY_LIST_VIEW);

		// Status List process
		// TODO: find process status list
//        List<JProcessStepDto> statusList = jprocessService.findStepStatusList(CommonConstant.HDBANK_BUSINESS_INTRODUCTION, locale);
//        mav.addObject("statusList", statusList);

		// init data enabled search
		List<SelectItem> enabledStatus = new ArrayList<SelectItem>();
		for (EnabledStatusEnum enabled : EnabledStatusEnum.values()) {
			SelectItem item = new SelectItem(enabled.toString(), enabled.getName());
			enabledStatus.add(item);
		}
		mav.addObject("enabledSelect", enabledStatus);

		int pageSize;
		if (searchDto.getPageSize() != null) {
			pageSize = searchDto.getPageSize();
		} else {
			pageSize = sysConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		}
		PageWrapper<IntroductionCategoryDto> pageWrapper = introductionCategoryService
				.getActiveCategoryByCondition(page, pageSize, searchDto, locale);
		List<SearchKeyDto> searchKeyList = introductionCategoryService.genCategorySearchKeyList(locale);
		String url = "/introduction-category/list";
		model.addAttribute("searchKeyList", searchKeyList);
		model.addAttribute("pageUrl", url);
		mav.addObject("searchDto", searchDto);
		mav.addObject("pageWrapper", pageWrapper);
		return mav;
	}

	@RequestMapping(value = URL_CATEGORY_AJAX_LIST, method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView postCategoryList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@ModelAttribute(value = "searchDto") CommonSearchDto searchDto, Locale locale, Model model) {

		/*---- taitm - phân quyền theo menu -------*/
		if (!hasRoleAccess()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		/*---- END -------*/

		ModelAndView mav = new ModelAndView(HDB_ADMIN_INTRODUCTION_CATEGORY_AJAX_TABLE);

		int pageSize;
		if (searchDto.getPageSize() != null) {
			pageSize = searchDto.getPageSize();
		} else {
			pageSize = sysConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		}
		PageWrapper<IntroductionCategoryDto> pageWrapper = introductionCategoryService
				.getActiveCategoryByCondition(page, pageSize, searchDto, locale);
		List<SearchKeyDto> searchKeyList = introductionCategoryService.genCategorySearchKeyList(locale);
		model.addAttribute("searchKeyList", searchKeyList);
		mav.addObject("searchDto", searchDto);
		mav.addObject("pageWrapper", pageWrapper);
		return mav;
	}

	private void initDataEditPage(ModelAndView mav, IntroductionCategoryDto detailDto, Locale locale) {
		// requestToken
		String requestToken = CommonUtil.randomStringWithTimeStamp();
		detailDto.setRequestToken(requestToken);

		detailDto.setCustomerTypeId(HDBANK_CUSTOMER_TYPE_ID);

		// Init master data
		List<LanguageDto> lstLang = languageService.getLanguageDtoList();
		mav.addObject("languageList", lstLang);

		List<List<BannerLanguageDto>> lstBannerLangSelect = new ArrayList<>();
		for (LanguageDto languageDto : lstLang) {
			List<BannerLanguageDto> bannerDesktopList = bannerService.findBannerLanguageByTypeAndDeviceLanguage(
					BannerTypeEnum.BANNER_TOP.toString(), false, languageDto.getCode(),
					CmsStepNoStatusConstant.STEP_APPROVED);
			lstBannerLangSelect.add(bannerDesktopList);
		}
		mav.addObject("bannerDesktopList", lstBannerLangSelect);

		List<List<BannerLanguageDto>> lstBannerLangSelectMobile = new ArrayList<>();
		for (LanguageDto languageDto : lstLang) {
			List<BannerLanguageDto> bannerMobileList = bannerService.findBannerLanguageByTypeAndDeviceLanguage(
					BannerTypeEnum.BANNER_TOP.toString(), true, languageDto.getCode(),
					CmsStepNoStatusConstant.STEP_APPROVED);
			lstBannerLangSelectMobile.add(bannerMobileList);
		}

		if (detailDto.getId() == null) {
			List<IntroductionCategoryLanguageDto> lstInfoByLanguage = new ArrayList<IntroductionCategoryLanguageDto>();
			List<Language> languageList = langService.findAllActive();
			for (Language lang : languageList) {
				IntroductionCategoryLanguageDto introCateLangDto = new IntroductionCategoryLanguageDto();
				introCateLangDto.setLanguageCode(lang.getCode());
				introCateLangDto.setLanguageDispName(lang.getName());
				lstInfoByLanguage.add(introCateLangDto);
				detailDto.setInfoByLanguages(lstInfoByLanguage);
			}
		}

		mav.addObject("bannerMobileList", lstBannerLangSelectMobile);

		// list sort
		mav.addObject("lstSort", getListSort(detailDto.getId(), null, locale.toString()));

		// Init PageWrapper History Approval
//        PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.doSearch(1, detailDto.getId(),
//                detailDto.getProcessId(), ConstantHistoryApprove.APPROVE_INTRODUCTION_CATEGORY, locale);
//        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
	}

	private List<IntroductionCategoryDto> getListSort(Long id, Long parentId, String lang) {
		List<IntroductionCategoryDto> lstSort = introductionCategoryService.getListSortRemovedId(id, parentId, lang);
		if (lstSort == null) {
			lstSort = new ArrayList<IntroductionCategoryDto>();
		}

		return lstSort;
	}

//	private List<IntroductionTypeSelectDto> initIntroTypeSelectionList() {
//		List<IntroductionTypeSelectDto> typeSelectionList = new ArrayList<IntroductionTypeSelectDto>();
//		for (IntroductionTypeEnum typeEnum : IntroductionTypeEnum.class.getEnumConstants()) {
//			IntroductionTypeSelectDto typeSelectDto = new IntroductionTypeSelectDto();
//			typeSelectDto.setTitleMessageKey(typeEnum.getSelectMsgKey());
//			typeSelectDto.setTypeValue(typeEnum.getTypeName());
//			typeSelectionList.add(typeSelectDto);
//		}
//		return typeSelectionList;
//	}

//	@RequestMapping(value = URL_CATEGORY_EDIT, method = RequestMethod.GET)
//	public ModelAndView getCategoryEdit(@ModelAttribute(value = "searchDto") CommonSearchDto searchDto,
//			@RequestParam(value = "id", required = false) Long id,
//			@RequestParam(value = "codeSearch", required = false) String codeSearch,
//			@RequestParam(value = "nameSearch", required = false) String nameSearch,
//			@RequestParam(value = "statusSearch", required = false) Integer statusSearch,
//			@RequestParam(value = "enabledSearch", required = false) Integer enabledSearch,
//			@RequestParam(value = "typeOfMainSearch", required = false) Integer typeOfMainSearch,
//			@RequestParam(value = "pictureIntroductionSearch", required = false) Integer pictureIntroductionSearch,
//			Locale locale, Model model) {
//
//		/*---- taitm - phân quyền theo menu -------*/
//		if (!hasRoleAccess()) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}
//		/*---- END -------*/
//
//		ModelAndView mav = new ModelAndView(HDB_ADMIN_INTRODUCTION_CATEGORY_EDIT_VIEW);
//
//		String businessCode = CommonConstant.HDBANK_BUSINESS_INTRODUCTION;
//
//		IntroductionCategoryDto detailDto = introductionCategoryService.getCategory(id, locale, businessCode);
//
//		String url = INTRODUCTION_DIRECTORY_NAME.concat(URL_CATEGORY_EDIT);
//
//		if (null != id) {
//			url = url.concat("?id=").concat(id.toString());
//		}
//		detailDto.setUrl(url);
//
//		String lang = locale.toString();
//
//		List<IntroductionCategoryNode> selectionCategoryTree = introductionCategoryService.findSelectionCategoryTree(id,
//				lang);
//		IntroductionCategoryNode emptyNode = new IntroductionCategoryNode();
//
//		emptyNode.setText(" ");
//		emptyNode.setId(-1l);
//		selectionCategoryTree.add(0, emptyNode);
//
//		String selectionCategoryTreeJson = CommonJsonUtil.convertObjectToJsonString(selectionCategoryTree);
//		mav.addObject("selectionCategories", selectionCategoryTreeJson);
//
//		loadSelectionViewTypes(mav);
//
//		initDataEditPage(mav, detailDto, locale);
//
//		List<IntroductionTypeSelectDto> typeSelections = this.initIntroTypeSelectionList();
//		model.addAttribute("typeSelection", typeSelections);
//
//		detailDto.setIndexLangActive(0);
//
//		introductionCategoryService.setDataForSearchDto(searchDto, codeSearch, nameSearch, statusSearch, enabledSearch,
//				typeOfMainSearch, pictureIntroductionSearch);
//
//		detailDto.setCodeSearch(searchDto.getCode());
//		detailDto.setNameSearch(searchDto.getName());
//		detailDto.setStatusSearch(searchDto.getStatus());
//		detailDto.setTypeOfMainSearch(searchDto.getTypeOfMain());
//		detailDto.setEnabledSearch(searchDto.getEnabled());
//		detailDto.setPictureIntroductionSearch(searchDto.getPictureIntroduction());
//
//		mav.addObject("codeSearch", searchDto.getCode());
//		mav.addObject("nameSearch", searchDto.getName());
//		mav.addObject("statusSearch", searchDto.getStatus());
//		mav.addObject("typeOfMainSearch", searchDto.getTypeOfMain());
//		mav.addObject("enabledSearch", searchDto.getEnabled());
//		mav.addObject("pictureIntroductionSearch", searchDto.getPictureIntroduction());
//
//		model.addAttribute("updateDto", detailDto);
//		return mav;
//	}

	@RequestMapping(value = URL_CATEGORY_EDIT, method = RequestMethod.POST)
	public ModelAndView postCategoryEdit(@Valid @ModelAttribute(value = "updateDto") IntroductionCategoryDto updateDto,
			BindingResult result, Locale locale, Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws IOException {

		/*---- taitm - phân quyền theo menu -------*/
//        if (StringUtils.equals(updateDto.getButtonId(), StepActionEnum.SAVE.getCode())) {
//            if (!hasRoleEdit()){
//                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//            }
//        }else{
		// Security for this page.
		if (!UserProfileUtils.hasRole(updateDto.getCurrItem())) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
//        }

		/*---- END -------*/

		ModelAndView mav = new ModelAndView(HDB_ADMIN_INTRODUCTION_CATEGORY_EDIT_VIEW);

		MessageList messageLst = new MessageList(Message.SUCCESS);

		CommonSearchDto searchDto = new CommonSearchDto();
		searchDto.setCode(updateDto.getCodeSearch());
		searchDto.setName(updateDto.getNameSearch());
		searchDto.setStatus(updateDto.getStatusSearch());

		if (!result.hasErrors()) {
			categoryValidator.validate(updateDto, result);
		}
		if (result.hasErrors()) {
			// Add message error
			messageLst.setStatus(Message.ERROR);

			this.addMsgInfo(ConstantCore.MSG_ERROR_CREATE, locale, mav, messageLst);

			initDataEditPage(mav, updateDto, locale);

			if (updateDto.getIndexLangActive() == null) {
				updateDto.setIndexLangActive(0);
			}
			mav.addObject("updateDto", updateDto);
			return mav;
		}

		updateDto = introductionCategoryService.saveUpdateCategory(updateDto, locale, request);

		// success redirect edit page
		String viewName = UrlConst.REDIRECT.concat(UrlConst.URL_INTRODUCTION_CATEGORY).concat(URL_CATEGORY_EDIT);

		String msgInfo = msg.getMessage("message.success.label", new String[] { updateDto.getButtonAction() }, locale);
		messageLst.add(msgInfo);

		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageLst);
		redirectAttributes.addAttribute("id", updateDto.getId());

		setValueSearch(searchDto, redirectAttributes);

		mav.setViewName(viewName);

		return mav;
	}

	/**
	 * getSortPage
	 *
	 * @param locale
	 * @param model
	 * @return ModelAndView
	 * @author hand
	 */
	@RequestMapping(value = AdminUrlConst.LIST_SORT, method = RequestMethod.GET)
	public ModelAndView getSortPage(@ModelAttribute(value = "searchDto") CommonSearchDto searchDto, Locale locale,
			RedirectAttributes redirectAttributes) {

		/*---- taitm - phân quyền theo menu -------*/
		if (!hasRoleAccess()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		/*---- END -------*/

		ModelAndView mav = new ModelAndView(HDB_ADMIN_INTRODUCTION_CATEGORY_LIST_SORT);

		List<IntroductionLanguageDto> shareholderList = introductionCategoryService
				.getListForSort(HDBANK_CUSTOMER_TYPE_ID, locale.toString());
		String url = UrlConst.URL_INTRODUCTION_CATEGORY.concat(UrlConst.ROOT).concat(AdminUrlConst.LIST_SORT);

		mav.addObject("sortPageModel", shareholderList);
		mav.addObject("pageUrl", url);

		return mav;
	}

	/**
	 * postSortPage
	 *
	 * @param locale
	 * @return ModelAndView
	 * @author hand
	 */
	@RequestMapping(value = AdminUrlConst.LIST_SORT, method = RequestMethod.POST)
	public ModelAndView postSortPage(@RequestBody IntroductionCategoryDto editDto, Locale locale,
			RedirectAttributes redirectAttributes) {

		/*---- taitm - phân quyền theo menu -------*/
		if (!hasRoleEdit()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		/*---- END -------*/

		// uppdate sort all
		introductionCategoryService.updateSortAll(editDto.getSortOderList());

		// update success redirect sort list page
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("introduction-category").concat(UrlConst.ROOT)
				.concat(AdminUrlConst.LIST_SORT);
		ModelAndView mav = new ModelAndView(viewName);

		// Init message success list
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
		messageList.add(msgInfo);

		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

		redirectAttributes.addAttribute("code", editDto.getCodeSearch());
		redirectAttributes.addAttribute("name", editDto.getNameSearch());
		redirectAttributes.addAttribute("status", editDto.getStatusSearch());
		redirectAttributes.addAttribute("typeOfMain", editDto.getEnabledSearch());
		redirectAttributes.addAttribute("enabled", editDto.getTypeOfMainSearch());
		redirectAttributes.addAttribute("pictureIntroduction", editDto.getPictureIntroductionSearch());
		return mav;
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getCategoryDelete(@RequestParam(value = "id", required = true) Long id,
			@ModelAttribute(value = "searchDto") CommonSearchDto searchDto, Locale locale, Model model,
			final RedirectAttributes redirectAttributes) {

		/*---- taitm - phân quyền theo menu -------*/
		if (!hasRoleEdit()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		/*---- END -------*/

		if (id <= 0) {
			throw new BusinessException("illegal data: id");
		}

		introductionCategoryService.deleteCateById(id);
		String viewName = UrlConst.REDIRECT.concat(UrlConst.URL_INTRODUCTION_CATEGORY).concat(URL_CATEGORY_LIST);
		ModelAndView mav = new ModelAndView(viewName);

		// Init message success list
		MessageList messageLst = new MessageList(Message.SUCCESS);
		this.addMsgInfo(ConstantCore.MSG_SUCCESS_DELETE, locale, mav, messageLst);

		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageLst);
		redirectAttributes.addAttribute("code", searchDto.getCode());
		redirectAttributes.addAttribute("name", searchDto.getName());
		redirectAttributes.addAttribute("status", searchDto.getStatus());
		redirectAttributes.addAttribute("typeOfMain", searchDto.getEnabled());
		redirectAttributes.addAttribute("enabled", searchDto.getTypeOfMain());
		redirectAttributes.addAttribute("pictureIntroduction", searchDto.getPictureIntroduction());
		return mav;

	}

	/**
	 * @param mav
	 */
//	private void loadSelectionViewTypes(ModelAndView mav) {
////        List<ConstantDisplay> viewTypeSelection = constDisplayService.findByType(ConstDispType.INTRODUCTION_TYPE);
//		List<JcaConstantDto> viewTypeSelection = constDisplayService
//				.findConstantDisplayByTypeAndLang(EDITOR_DIRECTORY_NAME, new Locale("EN"));
//		mav.addObject("viewTypeSelection", viewTypeSelection);
//	}

	/**
	 * @param locale
	 * @param mav
	 * @param messageLst
	 */
	private void addMsgInfo(String msgId, Locale locale, ModelAndView mav, MessageList messageLst) {
		String msgInfo = msg.getMessage(msgId, null, locale);
		messageLst.add(msgInfo);
		mav.addObject(ConstantCore.MSG_LIST, messageLst);
	}

	/**
	 * ajaxHistoryList
	 *
	 * @param condition
	 * @param locale
	 * @param page
	 * @return
	 * @author TranLTH
	 */
//    @RequestMapping(value = UrlConst.AJAX_HISTORY, method = { RequestMethod.POST })
//    @ResponseBody
//    public ModelAndView ajaxHistoryList(@ModelAttribute(value = "historySearh") HistoryApproveSearchDto condition,
//            Locale locale, @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page) {
//        ModelAndView mav = new ModelAndView(ViewConstant.VIEW_HISTORY_TABLE);
//
//        // Init PageWrapper
//        PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.searchHistory(page,
//                condition.getReferenceId(), condition.getReferenceType());
//        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
//
//        return mav;
//    }

	@RequestMapping(value = "/changeParent", method = RequestMethod.POST)
	public @ResponseBody Long categoryChangeParent(@RequestParam(value = "parentId", required = false) Long parentId) {
		Long maxSort = introductionCategoryService.getMaxCategorySort(parentId);
		if (null == maxSort) {
			return 0l;
		} else {
			return maxSort + 1;
		}
	}

	@RequestMapping(value = URL_EDITOR_DOWNLOAD, method = RequestMethod.GET)
	public void editorDownload(@RequestParam(required = true, value = "url") String fileUrl, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String url = Paths
				.get(INTRODUCTION_DIRECTORY_NAME, EDITOR_DIRECTORY_NAME, FilenameUtils.separatorsToSystem(fileUrl))
				.toString();
		introductionCategoryService.requestEditorDownload(url, request, response);
	}

	@RequestMapping(value = URL_EDITOR_IMAGE_UPLOAD, method = RequestMethod.POST)
	public @ResponseBody String editorImageUpload(MultipartHttpServletRequest request, HttpServletRequest request2,
			Model model, HttpServletResponse response,
			@RequestParam(required = true, value = "requesttoken") String requestToken,
			@RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
			throws JSONException, IOException {
		String fileName = fileService.uploadTemp(request, request2, model, response,
				Paths.get(INTRODUCTION_DIRECTORY_NAME, EDITOR_DIRECTORY_NAME).toString(), requestToken);
		String fileUrl = URLEncoder.encode(fileName, "UTF-8");
		String downloadUrl = request2.getContextPath().concat(UrlConst.ROOT).concat(INTRODUCTION_DIRECTORY_NAME)
				.concat(URL_EDITOR_DOWNLOAD).concat("?url=").concat(fileUrl);
		return "<script> " + "window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", \"" + downloadUrl + "\");"
				+ "</script>";
	}

	@RequestMapping(value = URL_EDITOR_FILE_UPLOAD, method = RequestMethod.POST)
	public @ResponseBody String editorFileUpload(MultipartHttpServletRequest request, HttpServletRequest request2,
			Model model, HttpServletResponse response,
			@RequestParam(required = true, value = "requesttoken") String requestToken,
			@RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
			throws JSONException, IOException {
		String fileName = fileService.uploadTemp(request, request2, model, response,
				Paths.get(INTRODUCTION_DIRECTORY_NAME, EDITOR_DIRECTORY_NAME).toString(), requestToken);
		String fileUrl = URLEncoder.encode(fileName, "UTF-8");
		String downloadUrl = request2.getContextPath().concat(UrlConst.ROOT).concat(INTRODUCTION_DIRECTORY_NAME)
				.concat(URL_EDITOR_DOWNLOAD).concat("?url=").concat(fileUrl);
		return "<script> " + "window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", \"" + downloadUrl + "\");"
				+ "</script>";
	}

	@RequestMapping(value = AdminUrlConst.DOWLOAD, method = RequestMethod.GET)
	public @ResponseBody List<String> downloadImageBanner(
			@RequestParam(required = true, value = "bannerId") Long bannerId, HttpServletRequest request,
			HttpServletResponse response, Locale locale) {
		// Init master data
		List<LanguageDto> languageList = languageService.getLanguageDtoList();

		List<String> lstImgPhysic = new ArrayList<>();
		// BannerLanguage
		for (LanguageDto languageDto : languageList) {
			BannerLanguageDto banner = bannerLanguageService.findByBannerIdAndLangueCode(bannerId	);

			// dowload banner image
			lstImgPhysic.add(banner.getBannerPhysicalImg());
		}

		return lstImgPhysic;
	}

	@RequestMapping(value = UrlConst.URL_DOWNLOAD_IMAGE, method = RequestMethod.GET)
	public void download(@RequestParam(required = true, value = "fileName") String fileName, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		if (fileName.contains(ConstantCore.AT_FILE)) {
			fileService.downloadTemp(fileName, request, response);
		} else {
			fileService.download(fileName, request, response);
		}
	}

	@RequestMapping(value = UrlConst.URL_UPLOADTEMP, method = RequestMethod.POST)
	public @ResponseBody String uploadTemp(MultipartHttpServletRequest request, HttpServletRequest request2,
			Model model, HttpServletResponse response,
			@RequestParam(required = false, value = "requestToken") String requestToken,
			@RequestParam(required = false, value = "widthImg") Integer widthImg,
			@RequestParam(required = false, value = "heightImg") Integer heightImg)
			throws NoSuchAlgorithmException, IOException {
		try {
//            return fileService.uploadTemp(request, request2, model, response, "", AdminConstant.INTRODUCTION_CATEGORY_FOLDER).replace("\\", "/");
			return fileService.uploadImageTemp(request, request2, model, response, "",
					Paths.get(AdminConstant.INTRODUCTION_CATEGORY_FOLDER, requestToken).toString(), widthImg, heightImg)
					.replace("\\", "/");
		} catch (IOException e) {
			throw e;
		}
	}

	@RequestMapping(value = "/path/images/list", method = RequestMethod.GET)
	@ResponseBody
	public String getPathImagesJson(HttpServletRequest request) throws IOException {

		List<ImageDto> resultList = CmsUtils.getFilePathListServer(INTRODUCTION_DIRECTORY_NAME,
				request.getContextPath(), null);

		String result = CommonJsonUtil.convertObjectToJsonString(resultList);

		return result;
	}

	private boolean hasRoleAccess() {
		if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_INTRODUCTION_CATEGORY)) {
			return false;
		}

		return true;
	}

	private boolean hasRoleEdit() {
		if (!UserProfileUtils
				.hasRole(CmsRoleConstant.PAGE_LIST_INTRODUCTION_CATEGORY.concat(ConstantCore.COLON_EDIT))) {
			return false;
		}

		return true;
	}

	private void setValueSearch(CommonSearchDto searchDto, RedirectAttributes redirectAttributes) {
		if (searchDto != null) {
			redirectAttributes.addAttribute("codeSearch", searchDto.getCode());
			redirectAttributes.addAttribute("nameSearch", searchDto.getName());
			redirectAttributes.addAttribute("statusSearch", searchDto.getStatus());
			redirectAttributes.addAttribute("typeOfMainSearch", searchDto.getTypeOfMain());
			redirectAttributes.addAttribute("enabledSearch", searchDto.getEnabled());
			redirectAttributes.addAttribute("pictureIntroductionSearch", searchDto.getPictureIntroduction());
		}
	}

	@RequestMapping(value = "/checkType", method = RequestMethod.POST)
	@ResponseBody
	public String checkType(@ModelAttribute(value = "updateDto") IntroductionCategoryDto typeEdit)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		Integer typeOfMain = -1;
		Integer pictureIntroduction = -1;
		if (typeEdit.getTypeOfMain()) {
			typeOfMain = 1;
		}

		if (typeEdit.getPictureIntroduction()) {
			pictureIntroduction = 1;
		}

		if (typeOfMain.equals(-1) && pictureIntroduction.equals(-1)) {
			return "{}";
		}

		IntroductionCategory result = introductionCategoryService.getIntroductionByType(HDBANK_CUSTOMER_TYPE_ID,
				typeOfMain, pictureIntroduction);

		if (result != null && typeEdit.getId() != null) {
			if (!result.getId().equals(typeEdit.getId())) {
				return CommonJsonUtil.convertObjectToJsonString(result);
			}
		}

		return "{}";
	}

	@PostMapping("/export-excel")
	public ModelAndView exportListNonExport(@ModelAttribute(value = "searchDto") CommonSearchDto searchDto,
			HttpServletRequest req, HttpServletResponse res, Locale locale) {

		try {
			searchDto.setLanguageCode(locale.toString());
			introductionCategoryService.exportExcel(searchDto, res, locale);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView(HDB_ADMIN_INTRODUCTION_CATEGORY_LIST_VIEW);
		// set url
		searchDto.setUrl(UrlConst.URL_INTRODUCTION_CATEGORY.concat(UrlConst.LIST));
		mav.addObject("searchDto", searchDto);

		return mav;
	}

	@Override
	public String getPermisionItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentWorkflowCommonService<IntroductionCategoryDto, IntroductionCategoryDto> getService() {
		return introductionCategoryService;
	}

	@Override
	public String getControllerURL(String customerAlias) {
		return UrlConst.URL_INTRODUCTION_CATEGORY;
	}

	@Override
	public String getBusinessCode(String customerAlias) {

		String businessCode = CmsCommonConstant.HDBANK_BUSINESS_INTRODUCTION;

        logger.error("NEED CHANGE BUSINESS CODE");
        // TODO remove "BUSINESS_BANNER"
        businessCode = "BUSINESS_BANNER"; // remove this line, only for test
        logger.error("NEED CHANGE BUSINESS CODE");

		return businessCode;
	}

	@Override
	public String viewEdit(String customerAlias) {
		return HDB_ADMIN_INTRODUCTION_CATEGORY_EDIT_VIEW;
	}

	@Override
	public void sendEmailAction(IntroductionCategoryDto editDto, Long buttonId) {

	}

	@Override
	public void sendEmailEdit(IntroductionCategoryDto editDto, Long userUpdated) {

	}

	@Override
	public String objectEditName() {
		return "updateDto";
	}

	@Override
	public String firstStepInProcess(String customerAlias) {
		return "submit";
	}

	@Override
	public String roleForAttachment(String customerAlias) {
		return CmsRoleConstant.PAGE_LIST_INTRODUCTION_CATEGORY;
	}

	@Override
	public void initDataEdit(ModelAndView mav, String customerAlias, IntroductionCategoryDto editDto, boolean isDetail,
			Locale locale) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate(IntroductionCategoryDto editDto, BindingResult bindingResult) {
		categoryValidator.validate(editDto, bindingResult);

	}

	@Override
	public ModelAndView returnModelProcess(IntroductionCategoryDto objectDto, Long referenceId, String customerAlias,
			HttpServletRequest httpServletRequest, Locale locale, RedirectAttributes redirectAtrribute) {
		ModelAndView mav = new ModelAndView();
		Long id = objectDto.getId();
		String viewName = UrlConst.REDIRECT.concat(UrlConst.URL_INTRODUCTION_CATEGORY).concat("/detail?id=" + id);
		redirectAtrribute.addFlashAttribute("messageList", objectDto.getMessageList());

		mav.setViewName(viewName);
		return mav;
	}

}