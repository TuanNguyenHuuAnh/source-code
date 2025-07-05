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
import org.apache.commons.lang.StringUtils;
import org.hornetq.utils.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstDispType;
import vn.com.unit.cms.admin.all.core.IntroductionCategoryNode;
import vn.com.unit.cms.admin.all.dto.GenderSelectDto;
import vn.com.unit.cms.admin.all.dto.IntroductionCategoryDto;
import vn.com.unit.cms.admin.all.dto.IntroductionDto;
import vn.com.unit.cms.admin.all.dto.IntroductionLanguageDto;
import vn.com.unit.cms.admin.all.dto.SortPageDto;
import vn.com.unit.cms.admin.all.enumdef.EnabledStatusEnum;
import vn.com.unit.cms.admin.all.enumdef.GenderEnum;
import vn.com.unit.cms.admin.all.enumdef.StepActionEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveSearchDto;
import vn.com.unit.cms.admin.all.service.BannerLanguageService;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.IntroductionCategoryService;
import vn.com.unit.cms.admin.all.service.IntroductionService;
import vn.com.unit.cms.admin.all.validator.IntroductionCategoryValidator;
import vn.com.unit.cms.admin.all.validator.IntroductionValidator;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;
import vn.com.unit.cms.core.module.banner.dto.ImageDto;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.common.utils.CommonJsonUtil;
//import vn.com.unit.jcanary.utils.Utils;
//import vn.com.unit.common.utils.CommonUtil;
//import vn.com.unit.util.Util;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
//import vn.com.unit.jcanary.constant.RoleConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.controller.DocumentWorkflowCommonController;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

@Controller
@RequestMapping("introduction")
public class IntroductionController extends DocumentWorkflowCommonController<IntroductionDto, IntroductionDto> {

    private static final String EDITOR_DIRECTORY_NAME = "editor";

    private static final String INTRODUCTION_DIRECTORY_NAME = "introduction";

    private static final String URL_EDITOR_UPLOAD = "/editor/upload";

    private static final String URL_EDITOR_FILE_UPLOAD = "/editor/file/upload";

    private static final String URL_EDITOR_DOWNLOAD = "/editor/download";

    private static final String HDB_ADMIN_INTRODUCTION_LIST_VIEW = "views/CMS/all/introduction/introduction-list.html"; // "hdb.admin.introduction.list"
    private static final String HDB_ADMIN_INTRODUCTION_AJAX_TABLE_VIEW = "views/CMS/all/introduction/introduction-table.html"; // "hdb.admin.introduction.ajax.table"
    private static final String HDB_ADMIN_INTRODUCTION_EDIT_VIEW = "views/CMS/all/introduction/introduction-edit.html"; // "hdb.admin.introduction.edit"
    private static final String URL_EDIT_DRAFT = "/edit";
    private static final String URL_INTRODUCTION = "introduction";
    private static final String URL_DOWNLOAD_IMAGE = "/image/download";

    private static final String BUSINESS_CODE = CmsCommonConstant.HDBANK_BUSINESS_INTRODUCTION;

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(IntroductionController.class);

    @Autowired
    IntroductionService introductionService;

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

//    @Autowired
//    ConstantDisplayService constDisplayService;

    @Autowired
    CmsFileService fileService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    BannerLanguageService bannerLanguageService;

    @Autowired
    IntroductionCategoryService introductionCategoryService;

    @Autowired
    private SystemConfig systemConfig;

//	@Autowired
//	private JProcessService jprocessService;

    @Autowired
    private JcaConstantService jcaConstantService;

//	@InitBinder
    @Override
    public void customeBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        if (null == request.getSession().getAttribute(ConstantCore.FORMAT_DATE)) {
            request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
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

    @RequestMapping(value = UrlConst.LIST, method = RequestMethod.GET)
    public ModelAndView getIntroductionList(@ModelAttribute CommonSearchDto searchDto,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale, Model model) {

        /** Phân quyền cho màn hình */
        if (!hasRoleAccess()) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        /** End */

        ModelAndView mav = new ModelAndView(HDB_ADMIN_INTRODUCTION_LIST_VIEW);

        PageWrapper<IntroductionLanguageDto> introPageWrapper = introductionService.getActiveIntroductionByCondition(
                page, sysConfig.getIntConfig(SystemConfig.PAGING_SIZE), searchDto, locale);

        String lang = locale.toString();

        List<IntroductionCategoryDto> selectionCategories = introductionService.getCategoriesForSelection(null, lang);
        model.addAttribute("selectionCategories", selectionCategories);

        List<SearchKeyDto> searchKeyList = introductionService.genIntroductionSearchKeyList(locale);

        model.addAttribute("searchKeyList", searchKeyList);

        // Status List process
//		List<JProcessStepDto> statusList = jprocessService.findStepStatusList(BUSINESS_CODE, locale);
//		mav.addObject("statusList", statusList);

        List<SelectItem> enabledStatus = new ArrayList<SelectItem>();
        for (EnabledStatusEnum enabled : EnabledStatusEnum.values()) {
            SelectItem item = new SelectItem(enabled.toString(), enabled.getName());
            enabledStatus.add(item);
        }
        mav.addObject("enabledSelect", enabledStatus);

        List<IntroductionCategoryDto> listIntroductionCatgoryDto = introductionCategoryService.getAllActive(lang,
                StepStatusEnum.PUBLISHED.getStepNo());
        mav.addObject("listIntroductionCatgoryDto", listIntroductionCatgoryDto);

        mav.addObject("searchDto", searchDto);

        mav.addObject("pageWrapper", introPageWrapper);
        return mav;
    }

    @RequestMapping(value = UrlConst.AJAXLIST, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView postIntroductionAjaxList(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @ModelAttribute(value = "searchDto") CommonSearchDto searchDto, Locale locale, Model model) {

        ModelAndView mav = new ModelAndView(HDB_ADMIN_INTRODUCTION_AJAX_TABLE_VIEW);
        String lang = locale.toString();

        int sizeOfPage = searchDto.getPageSize() != null ? searchDto.getPageSize()
                : sysConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        PageWrapper<IntroductionLanguageDto> introPageWrapper = introductionService
                .getActiveIntroductionByCondition(page, sizeOfPage, searchDto, locale);

        List<IntroductionCategoryDto> selectionCategories = introductionService.getCategoriesForSelection(null, lang);
        List<SearchKeyDto> searchKeyList = introductionService.genIntroductionSearchKeyList(locale);
        model.addAttribute("searchKeyList", searchKeyList);
        mav.addObject("selectionCategories", selectionCategories);
        mav.addObject("searchDto", searchDto);
        mav.addObject("pageWrapper", introPageWrapper);
        return mav;
    }

    @RequestMapping(value = "list/sort", method = RequestMethod.GET)
    public ModelAndView getSortPage(@ModelAttribute(value = "introductionDto") IntroductionDto introductionDto,
            @RequestParam(value = "categoryId", required = false) Long categoryId, Locale locale) {

        /** Phân quyền cho màn hình */
        if (!hasRoleAccess()) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        /** End */

        ModelAndView mav = new ModelAndView("hdb.admin.introduction.list.sort");

        String lang = locale.toString();

        List<IntroductionCategoryNode> selectionCategoryTree = introductionService.findSelectionCategoryTree(null,
                lang);
        IntroductionCategoryNode emptyNode = new IntroductionCategoryNode();
        emptyNode.setText(" ");
        emptyNode.setId(-1l);
        selectionCategoryTree.add(0, emptyNode);
        String selectionCategoryTreeJson = CommonJsonUtil.convertObjectToJsonString(selectionCategoryTree);
        mav.addObject("selectionCategories", selectionCategoryTreeJson);
        loadSelectionViewTypes(mav);

        List<IntroductionCategoryDto> listIntroductionCatgoryDto = introductionCategoryService.getAllActive(lang,
                StepStatusEnum.PUBLISHED.getStepNo());
        mav.addObject("listIntroductionCatgoryDto", listIntroductionCatgoryDto);

        if (categoryId == null && listIntroductionCatgoryDto != null && !listIntroductionCatgoryDto.isEmpty()) {
            categoryId = listIntroductionCatgoryDto.get(0).getId();
        }

        introductionService.initSortPage(categoryId, mav, locale);
        String url = URL_INTRODUCTION.concat(UrlConst.LIST).concat("/sort");
        mav.addObject("introductionDto", introductionDto);
        mav.addObject("pageUrl", url);
        mav.addObject("categoryId", categoryId);
        return mav;
    }

    @RequestMapping(value = "/changeCategorySort", method = RequestMethod.POST)
    public @ResponseBody ModelAndView changeCategorySort(
            @ModelAttribute("introductionDto") IntroductionDto introductionDto,
            @RequestParam(value = "categoryId", required = false) Long categoryId, Locale locale) {

        ModelAndView mav = new ModelAndView("hdb.admin.introduction.list.sort.table");

        String lang = locale.toString();

        List<IntroductionCategoryNode> selectionCategoryTree = introductionService.findSelectionCategoryTree(null,
                lang);
        IntroductionCategoryNode emptyNode = new IntroductionCategoryNode();
        emptyNode.setText(" ");
        emptyNode.setId(-1l);
        selectionCategoryTree.add(0, emptyNode);
        String selectionCategoryTreeJson = CommonJsonUtil.convertObjectToJsonString(selectionCategoryTree);
        mav.addObject("selectionCategories", selectionCategoryTreeJson);
        loadSelectionViewTypes(mav);

        introductionService.initSortPage(categoryId, mav, locale);
        introductionDto.setCategoryId(categoryId);
        mav.addObject("introductionDto", introductionDto);
        mav.addObject("categoryId", categoryId);
        return mav;
    }

    @RequestMapping(value = "list/sort", method = RequestMethod.POST)
    public ModelAndView postAjaxSortUpdate(Locale locale, @RequestBody IntroductionDto editDto,
            RedirectAttributes redirectAttributes) {

        /** Phân quyền cho màn hình */
        if (!hasRoleEdit()) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        /** End */

        ModelAndView mav = new ModelAndView("hdb.admin.introduction.list.sort");
        SortPageDto sortPageModel = new SortPageDto();
        List<SortOrderDto> list = editDto.getSortOderList();
        sortPageModel.setSortList(list);

        try {
            introductionService.updateModelsSort(sortPageModel);
        } catch (Exception e) {
            logger.error("postAjaxSortUpdate: " + e.getMessage());
            throw new BusinessException("Lỗi trong quá trình xử lý!");
        }

        MessageList messageLst = new MessageList(Message.SUCCESS);

        // success redirect edit page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("introduction/list/sort");

        this.addMsgInfo(ConstantCore.MSG_SUCCESS_UPDATE, locale, mav, messageLst);

        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageLst);
        redirectAttributes.addAttribute("categoryId", editDto.getCategoryId());

        mav.setViewName(viewName);

        return mav;
    }

//	@RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
//	public ModelAndView getIntroductionEdit(@RequestParam(value = "id", required = false) Long id,
//			@ModelAttribute(value = "searchDto") CommonSearchDto searchDto,
//			@RequestParam(value = "codeSearch", required = false) String codeSearch,
//			@RequestParam(value = "nameSearch", required = false) String nameSearch,
//			@RequestParam(value = "statusSearch", required = false) Integer statusSearch,
//			@RequestParam(value = "enabledSearch", required = false) Integer enabledSearch,
//			@RequestParam(value = "categoryIdSearch", required = false) Long categoryIdSearch,
//			Locale locale) {
//		
//        /** Phân quyền cho màn hình*/
//        if (!hasRoleAccess()){
//        	return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        /** End*/
//		
//        IntroductionDto detailDto = introductionService.getIntroductionUpdateDto(id, locale, BUSINESS_CODE);
//        
//        ModelAndView mav = new ModelAndView(HDB_ADMIN_INTRODUCTION_EDIT_VIEW);
//        
//		// inItScreenEdits
//        inItScreenEdit(mav, detailDto, locale);
//        
//		String url = INTRODUCTION_DIRECTORY_NAME.concat(UrlConst.EDIT);
//		if (id != null) {
//			url = url.concat("?id=").concat(id.toString());
//		}
//		
//		String requestToken = CommonUtil.randomStringWithTimeStamp();
//		detailDto.setRequestToken(requestToken);
//		
//		detailDto.setUrl(url);
//		
//		detailDto.setBusinessCode(BUSINESS_CODE);
//		
//		detailDto.setIndexLangActive(0);
//		
//		introductionService.setDataForSearchDto(searchDto, codeSearch, nameSearch, statusSearch, enabledSearch,
//				categoryIdSearch);
//		
//		detailDto.setCodeSearch(searchDto.getCode());
//		detailDto.setNameSearch(searchDto.getName());
//		detailDto.setStatusSearch(searchDto.getStatus());
//		detailDto.setEnabledSearch(searchDto.getEnabled());
//		detailDto.setCategoryIdSearch(searchDto.getCategoryId());
//		
//		mav.addObject("updateDto", detailDto);
//		
//		mav.addObject("codeSearch", searchDto.getCode());
//		mav.addObject("nameSearch", searchDto.getName());
//		mav.addObject("statusSearch", searchDto.getStatus());
//		mav.addObject("enabledSearch", searchDto.getEnabled());
//		mav.addObject("categoryIdSearch", searchDto.getCategoryId());
//		
//        return mav;
//    }

    private void inItScreenEdit(ModelAndView mav, IntroductionDto detailDto, Locale locale) {

        List<LanguageDto> languageList = langService.getLanguageDtoList();
        mav.addObject("languageList", languageList);

        loadSelectionCategories(mav, locale.toString());

        List<GenderSelectDto> genderSelection = genGenderSelectionList();
        mav.addObject("genderSelection", genderSelection);

        List<IntroductionCategoryDto> listIntroductionCatgoryDto = introductionCategoryService
                .getAllActive(locale.toString(), StepStatusEnum.PUBLISHED.getStepNo());
        mav.addObject("listIntroductionCatgoryDto", listIntroductionCatgoryDto);

        if (detailDto.getCategoryId() != null) {
            List<Select2Dto> listResult = new ArrayList<>();
            listResult = introductionService.getListForSort(locale.getLanguage(), 11L, detailDto.getCategoryId(),
                    detailDto.getId());
            mav.addObject("lstIntroductionSort", listResult);
        } else {
            mav.addObject("lstIntroductionSort", introductionService.getAllActiveIntroduction(locale.toString()));
        }

        // Init PageWrapper History Approval
//		PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.doSearch(1, detailDto.getId(),
//				detailDto.getProcessId(), ConstantHistoryApprove.APPROVE_INTRODUCTION, locale);
//		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
    }

    private List<GenderSelectDto> genGenderSelectionList() {
        List<GenderSelectDto> genderSelection = new ArrayList<GenderSelectDto>();
        GenderSelectDto emptyGenderSelection = new GenderSelectDto();
        emptyGenderSelection.setGenderTitle(null);
        emptyGenderSelection.setGenderValue(0);
        genderSelection.add(emptyGenderSelection);
        for (GenderEnum en : GenderEnum.class.getEnumConstants()) {
            GenderSelectDto genderSelectionItem = new GenderSelectDto();
            genderSelectionItem.setGenderValue(en.genderValue());
            genderSelectionItem.setGenderTitle(en.genderTitle());
            genderSelection.add(genderSelectionItem);
        }
        return genderSelection;
    }

    /**
     * @param mav
     */
    private void loadSelectionViewTypes(ModelAndView mav) {

        // ${constantDisplay.cat} => ${constantDisplay.kind}
        // #{${constantDisplay.code}} => #{${constantDisplay.code}}
        // constDispService.findByType("M10");
        // => List<JcaConstantDto> statusList =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(),
        // "EN");

        // type => groupCode
        // cat => kind
        // code => code

        // catOfficialName => name

        // ConstantDisplay motive =
        // constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(),
        // emailModel.getMotive().toString());
        // JcaConstantDto motive =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(),
        // emailModel.getMotive().toString(), "EN").get(0);

        // List<ConstantDisplay> listBannerPage =
        // constDispService.findByType(ConstDispType.B01);
        // List<JcaConstantDto> listBannerPage =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(),
        // "EN");

//        List<ConstantDisplay> viewTypeSelection = constDisplayService.findByType(ConstDispType.M11);

        List<JcaConstantDto> viewTypeSelection = jcaConstantService
                .getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M11.toString(), "EN");

        mav.addObject("viewTypeSelection", viewTypeSelection);
    }

    @RequestMapping(value = URL_EDIT_DRAFT, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView doEdit(@Valid @ModelAttribute(value = "updateDto") IntroductionDto updateDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes,
            HttpServletRequest request) throws IOException {

        /** Phân quyền cho màn hình */
        if (StringUtils.equals(updateDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
            // Security for this page.
            if (!hasRoleEdit()) {
                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
            }
        } else {
            if (!UserProfileUtils.hasRole(updateDto.getCurrItem())) {
                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
            }
        }
        /** End */

        ModelAndView mav = new ModelAndView(HDB_ADMIN_INTRODUCTION_EDIT_VIEW);

        String lang = locale.toString();

        loadSelectionCategories(mav, lang);

        introductionValidator.validate(updateDto, bindingResult);

        MessageList messageLst = new MessageList(Message.SUCCESS);

        if (bindingResult.hasErrors()) {
            // Add message error
            messageLst.setStatus(Message.ERROR);

            this.addMsgInfo(ConstantCore.MSG_ERROR_UPDATE, locale, mav, messageLst);

            loadSelectionCategories(mav, lang);

            inItScreenEdit(mav, updateDto, locale);

            if (updateDto.getIndexLangActive() == null) {
                updateDto.setIndexLangActive(0);
            }
            mav.addObject("updateDto", updateDto);
            return mav;
        }

        updateDto = introductionService.doEdit(updateDto, locale, request);

        // success redirect edit page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("introduction").concat(UrlConst.EDIT);

        String msgInfo = msg.getMessage("message.success.label", new String[] { updateDto.getButtonAction() }, locale);
        messageLst.add(msgInfo);

        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageLst);
        redirectAttributes.addAttribute("id", updateDto.getId());

        redirectAttributes.addAttribute("codeSearch", updateDto.getCodeSearch());
        redirectAttributes.addAttribute("nameSearch", updateDto.getNameSearch());
        redirectAttributes.addAttribute("statusSearch", updateDto.getStatusSearch());
        redirectAttributes.addAttribute("enabledSearch", updateDto.getEnabledSearch());
        redirectAttributes.addAttribute("categoryIdSearch", updateDto.getCategoryIdSearch());

        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageLst);

        mav.setViewName(viewName);
        return mav;
    }

    /**
     * @param mav
     */
    private void loadSelectionCategories(ModelAndView mav, String lang) {
        List<IntroductionCategoryNode> selectionCategoryTree = introductionService.findSelectionCategoryTree(null,
                lang);
        String selectionCategoryTreeJson = CommonJsonUtil.convertObjectToJsonString(selectionCategoryTree);
        mav.addObject("selectionCategories", selectionCategoryTreeJson);
    }

    @RequestMapping(value = UrlConst.DELETE, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getIntroductionDelete(@ModelAttribute CommonSearchDto searchDto,
            @RequestParam(value = "id", required = true, defaultValue = "") Long id, Locale locale, Model model,
            final RedirectAttributes redirectAttributes) {
        if (id <= 0) {
            throw new BusinessException("illegal data: id");
        }
        IntroductionDto introductionDetail = introductionService.getIntroductionObject(id);

        if (!hasRoleEdit()) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        String viewName = UrlConst.REDIRECT.concat("/introduction/list");
        ModelAndView mav = new ModelAndView(viewName);

        if (introductionDetail == null) {
            MessageList messageLst = new MessageList(Message.ERROR);
            this.addMsgInfo(ConstantCore.MSG_SUCCESS_DELETE, locale, mav, messageLst);
            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageLst);
            return mav;
        }

        introductionService.deleteIntroById(id);
        // Init message success list
        MessageList messageLst = new MessageList(Message.SUCCESS);
        this.addMsgInfo(ConstantCore.MSG_SUCCESS_DELETE, locale, mav, messageLst);

        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageLst);

        redirectAttributes.addAttribute("code", searchDto.getCode());
        redirectAttributes.addAttribute("name", searchDto.getName());
        redirectAttributes.addAttribute("status", searchDto.getStatus());
        redirectAttributes.addAttribute("enabled", searchDto.getEnabled());
        redirectAttributes.addAttribute("categoryId", searchDto.getCategoryId());

        return mav;
    }

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
    @RequestMapping(value = UrlConst.AJAX_HISTORY, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxHistoryList(@ModelAttribute(value = "historySearh") HistoryApproveSearchDto condition,
            Locale locale, @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page) {
        ModelAndView mav = new ModelAndView(ViewConstant.VIEW_HISTORY_TABLE);

        // Init PageWrapper
//        PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.searchHistory(page,
//                condition.getReferenceId(), condition.getReferenceType());
//        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);

        return mav;
    }

    @RequestMapping(value = "/category/changeParent", method = RequestMethod.POST)
    public @ResponseBody Long categoryChangeParent(@RequestParam(value = "parentId", required = false) Long parentId) {
        Long maxSort = introductionService.getMaxCategorySort(parentId);
        if (null == maxSort) {
            return 0l;
        } else {
            return maxSort + 1;
        }
    }

    @RequestMapping(value = "/changeCategory", method = RequestMethod.POST)
    public @ResponseBody Long changeCategory(@RequestParam(value = "categoryId", required = false) Long categoryId) {
        Long maxSort = introductionService.getMaxIntroductionSort(categoryId);
        if (null == maxSort) {
            return 0l;
        } else {
            return maxSort + 1;
        }
    }

    @RequestMapping(value = URL_DOWNLOAD_IMAGE, method = RequestMethod.GET)
    public void download(@RequestParam(required = true, value = "url") String imgUrl, HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        introductionService.requestDownloadImage(imgUrl, request, response);
    }

    @RequestMapping(value = URL_EDITOR_DOWNLOAD, method = RequestMethod.GET)
    public void editorDownload(@RequestParam(required = true, value = "url") String fileUrl, HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String url = Paths
                .get(INTRODUCTION_DIRECTORY_NAME, EDITOR_DIRECTORY_NAME, FilenameUtils.separatorsToSystem(fileUrl))
                .toString();
        introductionService.requestEditorDownload(url, request, response);
    }

    @RequestMapping(value = URL_EDITOR_UPLOAD, method = RequestMethod.POST)
    public @ResponseBody String editorImageUpload(MultipartHttpServletRequest request, HttpServletRequest request2,
            Model model, HttpServletResponse response,
            @RequestParam(required = true, value = "requesttoken") String requestToken,
            @RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
            throws JSONException, IOException {
        String fileName = fileService.uploadTemp(request, request2, model, response,
                Paths.get(INTRODUCTION_DIRECTORY_NAME, EDITOR_DIRECTORY_NAME).toString(), requestToken);
        String fileUrl = URLEncoder.encode(fileName, "UTF-8");
        String downloadUrl = request2.getContextPath().concat(UrlConst.ROOT).concat(URL_INTRODUCTION)
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
        String downloadUrl = request2.getContextPath().concat(UrlConst.ROOT).concat(URL_INTRODUCTION)
                .concat(URL_EDITOR_DOWNLOAD).concat("?url=").concat(fileUrl);
        return "<script> " + "window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", \"" + downloadUrl + "\");"
                + "</script>";
    }

    @RequestMapping(value = "/path/images/list", method = RequestMethod.GET)
    @ResponseBody
    public String getPathImagesJson(HttpServletRequest request) throws IOException {

        List<ImageDto> resultList = CmsUtils.getFilePathListServer(INTRODUCTION_DIRECTORY_NAME,
                request.getContextPath(), null);

        String result = CommonJsonUtil.convertObjectToJsonString(resultList);

        return result;
    }

    @RequestMapping(value = "/getLstSort", method = RequestMethod.GET)
    @ResponseBody
    public List<Select2Dto> ajaxGetLstSortByCategory(@RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "typeId", required = false) Long typeId, Locale locale) {
        List<Select2Dto> listResult = new ArrayList<>();
        listResult = introductionService.getListForSort(locale.getLanguage(), 11L, typeId, id);
        return listResult;
    }

    /**
     * downloadImageBanner
     *
     * @param bannerId
     * @param request
     * @param response
     * @author hand
     */
    @RequestMapping(value = AdminUrlConst.DOWLOAD, method = RequestMethod.GET)
    public @ResponseBody List<String> downloadImageBanner(
            @RequestParam(required = true, value = "bannerId") Long bannerId, HttpServletRequest request,
            HttpServletResponse response, Locale locale) {
        // Init master data
        List<LanguageDto> languageList = languageService.getLanguageDtoList();

        List<String> lstImgPhysic = new ArrayList<>();
        // BannerLanguage
        for (LanguageDto languageDto : languageList) {
        	BannerLanguageDto banner = bannerLanguageService.findByBannerIdAndLangueCode(bannerId);

            // dowload banner image
            lstImgPhysic.add(banner.getBannerPhysicalImg());
        }

        return lstImgPhysic;
    }

    @PostMapping("/export-excel")
    public ModelAndView exportListNonExport(@ModelAttribute CommonSearchDto searchDto, HttpServletRequest req,
            HttpServletResponse res, Locale locale) {

        /*---- taitm - phân quyền theo menu -------*/
        if (!hasRoleAccess()) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        /*---- END -------*/

        try {
            searchDto.setLanguageCode(locale.toString());
            searchDto.setCustomerTypeId(11L);
            introductionService.exportExcel(searchDto, res, locale);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        ModelAndView mav = new ModelAndView(HDB_ADMIN_INTRODUCTION_LIST_VIEW);
        // set url
        searchDto.setUrl((UrlConst.ROOT).concat("introduction").concat(UrlConst.LIST));
        mav.addObject("productSearch", searchDto);

        return mav;
    }

    private boolean hasRoleAccess() {
        // Security for this page.
        if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_INTRODUCTION)) {
            return false;
        }

        return true;
    }

    private boolean hasRoleEdit() {
        // Security for this page.
        if (!UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_INTRODUCTION_EDIT.concat(ConstantCore.COLON_EDIT))) {
            return false;
        }

        return true;
    }

    @Override
    public String getPermisionItem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DocumentWorkflowCommonService<IntroductionDto, IntroductionDto> getService() {
        return introductionService;
    }

    @Override
    public String getControllerURL(String customerAlias) {
        return "introduction";
    }

    @Override
    public String getBusinessCode(String customerAlias) {

        String businessCode = BUSINESS_CODE;

        logger.error("NEED CHANGE BUSINESS CODE");
        // TODO remove "BUSINESS_BANNER"
        businessCode = "BUSINESS_BANNER"; // remove this line, only for test
        logger.error("NEED CHANGE BUSINESS CODE");

        return businessCode;
    }

    @Override
    public String viewEdit(String customerAlias) {
        return HDB_ADMIN_INTRODUCTION_EDIT_VIEW;
    }

    @Override
    public void sendEmailAction(IntroductionDto editDto, Long buttonId) {

    }

    @Override
    public void sendEmailEdit(IntroductionDto editDto, Long userUpdated) {

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initDataEdit(ModelAndView mav, String customerAlias, IntroductionDto editDto, boolean isDetail,
            Locale locale) {
        inItScreenEdit(mav, editDto, locale);
    }

    @Override
    public void validate(IntroductionDto editDto, BindingResult bindingResult) {
        introductionValidator.validate(editDto, bindingResult);
    }
}