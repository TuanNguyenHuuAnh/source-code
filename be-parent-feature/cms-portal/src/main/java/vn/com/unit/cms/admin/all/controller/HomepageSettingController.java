/*******************************************************************************
 * Class        ：BannerController
 * Created date ：2017/02/15
 * Lasted date  ：2017/02/15
 * Author       ：hand
 * Change log   ：2017/02/15：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hornetq.utils.json.JSONException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.dto.BannerEditDto;
import vn.com.unit.cms.admin.all.dto.HomepageSearchDto;
import vn.com.unit.cms.admin.all.dto.HomepageSettingDto;
import vn.com.unit.cms.admin.all.dto.HomepageSettingEditDto;
import vn.com.unit.cms.admin.all.enumdef.StatusSearchEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveSearchDto;
import vn.com.unit.cms.admin.all.service.BannerService;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.HomepageService;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.banner.dto.ImageDto;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.common.utils.CommonJsonUtil;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.controller.DocumentWorkflowCommonController;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;
//import vn.com.unit.jcanary.service.ProcessService;
import vn.com.unit.ep2p.utils.SearchUtil;

/**
 * 
 * HomepageSettingController
 * 
 * @version 01-00
 * @since 01-00
 * @author sonnt
 */
@Controller
@RequestMapping(value = UrlConst.ROOT + UrlConst.HOMEPAGE_SETTING)
public class HomepageSettingController
        extends DocumentWorkflowCommonController<HomepageSettingEditDto, HomepageSettingEditDto> {

    @Autowired
    private MessageSource msg;

    @Autowired
    private HomepageService homePageService;

    @Autowired
    private BannerService bannerService;

//    @Autowired
//    ConstantDisplayService constDispService;

    @Autowired
    private CmsFileService fileService;

//    @Autowired
//    ProcessService processService;

    @Autowired
    private SystemConfig systemConfig;

//    @Autowired
//    HistoryApproveService historyApproveService;
//    
//    @Autowired
//    private JProcessService jprocessService;

    private static final String HOMEPAGE_LIST = "views/CMS/all/homepage-setting/homepage-setting-list.html"; // "homepage.setting.list"

    private static final String HOMEPAGE_EDIT = "views/CMS/all/homepage-setting/homepage-setting-edit.html"; // "homepage.setting.edit"

//    private static final String HOMEPAGE_DETAIL = "homepage.setting.detail";

    private static final String HOMEPAGE_TABLE = "views/CMS/all/homepage-setting/homepage-setting-table.html"; // "homepage.setting.table"

    private static final String HOMEPAGE_EDIT_IMAGE = "homepage.setting.edit.image";

//    private static final String BUSINESS_CODE = CommonConstant.BUSINESS_HOMEPAGESETTIMG;;

//    private static final Logger logger = LoggerFactory.getLogger(HomepageSettingController.class);

    @Override
    public void customeBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
        // The date format to parse or output your dates
        String patternDate = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
    }

    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getBannerList(@ModelAttribute(value = "homepageSearchDto") HomepageSearchDto homepageSearchDto,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {

        // set url
        homepageSearchDto.setUrl(UrlConst.HOMEPAGE_SETTING.concat(UrlConst.LIST));
        // Security for this page.
        if (!hasRoleAccess()) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(HOMEPAGE_LIST);

        // Status List process
//        List<JProcessStepDto> statusList = jprocessService.findStepStatusList(BUSINESS_CODE, locale);
//        mav.addObject("statusList", statusList);

        homepageSearchDto.setLang(locale.toString());

        // Init data status search
        List<SelectItem> statusSelect = SearchUtil.getSearchSelect(StatusSearchEnum.class);
        mav.addObject("statusSelect", statusSelect);

        PageWrapper<HomepageSettingDto> pageWrapper = homePageService.search(page, homepageSearchDto, locale);

        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);

        mav.addObject("homepageSearchDto", homepageSearchDto);

        mav.addObject("listBannerPage", homePageService.listBannerPage());

        return mav;
    }

    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = "homepageSearchDto") HomepageSearchDto homepageSearchDto,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
        // Security for this page.
        if (!hasRoleAccess()) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(HOMEPAGE_TABLE);

        homepageSearchDto.setLang(locale.toString());

        PageWrapper<HomepageSettingDto> pageWrapper = homePageService.search(page, homepageSearchDto, locale);

        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);

        mav.addObject("homepageSearchDto", homepageSearchDto);

        return mav;
    }

    /**
     * add or edit banner
     * 
     * @param bannerModel
     * @param bid         bannerId
     * @param locale
     * @return ModelAndView mav
     */
//    @RequestMapping(value = UrlConst.EDIT, method = { RequestMethod.GET })
//    public ModelAndView addOrEditHomepage(@RequestParam(value = "id", required = false) Long homePageId,
//            @RequestParam(value = "startDate", required = false) String startDateSearch,
//            @RequestParam(value = "endDate", required = false) String endDateSearch,
//            @RequestParam(value = "status", required = false) Integer statusSearch,
//            @RequestParam(value = "bannerPage", required = false) String bannerPageSearch, Locale locale) {
//
//    	ModelAndView mav = new ModelAndView(HOMEPAGE_EDIT);
//        // Security for this page.
//        if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_HOME_LIST) &&
//        	!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_HOME_LIST.concat(ConstantCore.COLON_EDIT))){
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        
//        HomepageSettingEditDto homepageEditDto = homePageService.getHomepageEdit(homePageId, locale, BUSINESS_CODE);
//        
//        homepageEditDto.setStartDateSearch(startDateSearch);
//        homepageEditDto.setEndDateSearch(endDateSearch);
//        homepageEditDto.setStatusSearch(statusSearch);
//        homepageEditDto.setBannerPageSearch(bannerPageSearch);
//        
//        
//        inItScreenEdit(mav, homepageEditDto, locale);
//        
//        mav.addObject("homepageEdit", homepageEditDto);
//        /** getDate from System config*/
//        String patternDate = systemConfig.getConfig(AppSystemConfig.DATE_PATTERN);
//        mav.addObject("formatDate", patternDate);
//        /**End*/
//        return mav;
//    }

    private void inItScreenEdit(ModelAndView mav, HomepageSettingEditDto homepageEditDto, Locale locale) {

        String url = UrlConst.HOMEPAGE_SETTING.concat(UrlConst.EDIT);
        if (null != homepageEditDto.getId()) {
            url = url.concat("?id=").concat(homepageEditDto.getId().toString());
            mav.addObject("listBannerPage", homePageService.getBannerPageOfEditById(homepageEditDto.getId()));
        } else {
            mav.addObject("listBannerPage", homePageService.listBannerPageOfEdit());
        }
        homepageEditDto.setUrl(url);

        String requestToken = CommonUtil.randomStringWithTimeStamp();
        homepageEditDto.setRequestToken(requestToken);

        // Init PageWrapper History Approval
//        PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.doSearch(1, homepageEditDto.getId(),
//                homepageEditDto.getProcessId(), ConstantHistoryApprove.APPROVE_HOMEPAGESETTIMG, locale);
//        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
    }

    /**
     * saveOrEdit
     *
     * @param homepageEditDto
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @return
     * @author sonnt
     */
//    @RequestMapping(value = UrlConst.EDIT, method = { RequestMethod.POST })
//    @ResponseBody
//    public ModelAndView saveOrEdit(@Valid @ModelAttribute(value = "homepageEdit") HomepageSettingEditDto homepageEditDto,
//            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
//
//        ModelAndView mav = new ModelAndView(HOMEPAGE_EDIT);
//        try {
//            // if action is save draft
//    		if (StringUtils.equals(homepageEditDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
//    			if (!hasRoleEdit()) {
//    				return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//    			}
//    		} else {
//    			// Security for this page.
//    			if (!UserProfileUtils.hasRole(homepageEditDto.getCurrItem())) {
//    				return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//    			}
//    		}
//            
//            String startDateSearch = homepageEditDto.getStartDateSearch();
//            String endDateSearch = homepageEditDto.getEndDateSearch();
//            Integer statusSearch = homepageEditDto.getStatusSearch();
//            String bannerPageSearch = homepageEditDto.getBannerPageSearch();
//            
//            // Init message list
//            MessageList messageList = new MessageList(Message.SUCCESS);
//            
//            // Validation
//            if (bindingResult.hasErrors()) {
//                // inint laguage list
//
//                // Add message error
//                messageList.setStatus(Message.ERROR);
//                String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale);
//                messageList.add(msgError);
//
//                mav.addObject(ConstantCore.MSG_LIST, messageList);
//                mav.addObject("homepageEdit", homepageEditDto);
//
//                return mav;
//            }
//
//            // create or edit
//            boolean result = homePageService.doEdit(homepageEditDto, locale, request);
//            if (!result) {
//                mav.addObject(ConstantCore.MSG_LIST, messageList);
//                // inItScreenEdits
//                this.inItScreenEdit(mav, homepageEditDto, locale);
//                mav.addObject("homepageEdit", homepageEditDto);
//                return mav;
//            }
//            // success redirect edit page
//            String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.HOMEPAGE_SETTING).concat(UrlConst.EDIT);
//            String msgInfo = msg.getMessage("message.success.label", new String[] { homepageEditDto.getButtonAction() },
//                    locale);
//            messageList.add(msgInfo);
//
//            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
//            redirectAttributes.addAttribute("id", homepageEditDto.getId());
//            redirectAttributes.addAttribute("startDate", startDateSearch);
//            redirectAttributes.addAttribute("endDate", endDateSearch);
//            redirectAttributes.addAttribute("status", statusSearch);
//            redirectAttributes.addAttribute("bannerPage", bannerPageSearch);
//            
//            mav.setViewName(viewName);
//
//        }catch(Exception ex) {
//        	logger.error("##saveOrEdit_HomepageSetting##",ex.getMessage());
//        }
//
//        return mav;
//    }

    /**
     * ajaxList
     * 
     * @param bannerId
     * @return
     * @author sonnt
     */
    @RequestMapping(value = "/ajax/loadBannerImage", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView ajaxList(@RequestParam(value = "id", required = true) Long bannerId, Locale locale) {
        ModelAndView modelAndView = new ModelAndView(HOMEPAGE_EDIT_IMAGE);

        BannerEditDto bannerEditDto = bannerService.getBannerEdit(bannerId, locale);
        if (null == bannerId || bannerId == 0) {
            bannerEditDto = new BannerEditDto();
        }
        modelAndView.addObject("bannerEditDto", bannerEditDto);
        return modelAndView;
    }

    /**
     * detail
     *
     * @param homepageId
     * @return ModelAndView
     * @author sonnt
     * 
     */
//    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
//    public ModelAndView detail(@RequestParam(value = "id", required = true) Long homepageId,
//            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
//
//        ModelAndView mav = new ModelAndView(HOMEPAGE_DETAIL);
//        // Security for this page.
//        if (!hasRoleAccess()){
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        
//        String businessCode = CommonConstant.BUSINESS_HOMEPAGESETTIMG;
//        
//        // BannerEditDto
//        HomepageSettingEditDto homepageSettingDetail = homePageService.getHomepageEdit(homepageId, locale, businessCode);
//        
//        String url = UrlConst.HOMEPAGE_SETTING.concat(UrlConst.DETAIL);
//        if (null != homepageId) {
//            url = url.concat("?id=").concat(homepageId.toString());
//        }
//        homepageSettingDetail.setUrl(url);
//
//        mav.addObject("homepageSettingDetail", homepageSettingDetail);
//        
//        mav.addObject("listBannerPage", homePageService.listBannerPage());
//        
//        mav.addObject("fixBannerList", homePageService.generateBannerSelectionList(homePageService.findBannerByTypeAndDevice(BannerTypeEnum.BANNER_MIDDLE.toString(),false)));
//        
//        // Init PageWrapper History Approval
//        String referenceType = homepageSettingDetail.getReferenceType();
//        
////        PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.searchHistory(page, homepageId, referenceType);
////        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
//        return mav;
//    }
//    
//    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.POST)
//    @ResponseBody
//    public ModelAndView postApprover(@Valid @ModelAttribute(value = "homepageSettingEditDto") HomepageSettingEditDto homepageSettingEditDto,
//            Locale locale, RedirectAttributes redirectAttributes) {
//        ModelAndView mav = new ModelAndView(HOMEPAGE_DETAIL);
//        // Security for this page.
//        if (!hasRoleEdit()){
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        
//        // Approve          
//        homePageService.approver(homepageSettingEditDto);                 
//        
//        String msgInfo = "";
//        if (!homepageSettingEditDto.isAction()) {
//            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_RETURN, null, locale);
//        } else {
//            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_APPROVE, null, locale);
//        }
//        // Init message list
//        MessageList messageList = new MessageList(Message.SUCCESS);
//        messageList.add(msgInfo);
//        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
//        
//        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT + UrlConst.HOMEPAGE_SETTING).concat(UrlConst.DETAIL);
//        mav.setViewName(viewName);
//        redirectAttributes.addAttribute("id", homepageSettingEditDto.getId());
//        
//        return mav;
//    }

    /**
     * download
     *
     * @param fileName
     * @param request
     * @param response
     * @author TranLTH
     */
    @RequestMapping(value = "ajax/download", method = RequestMethod.GET)
    public void download(@RequestParam(required = true, value = "fileName") String fileName, HttpServletRequest request,
            HttpServletResponse response) {
        fileService.download(fileName, request, response);
    }

    /**
     * postDelete
     *
     * @param homepageId
     * @param locale
     * @param redirectAttributes
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    public ModelAndView postDelete(@RequestParam(value = "id", required = true) Long homepageId,
            @RequestParam(value = "startDate", required = false) String startDateSearch,
            @RequestParam(value = "endDate", required = false) String endDateSearch,
            @RequestParam(value = "status", required = false) Integer statusSearch,
            @RequestParam(value = "bannerPage", required = false) String bannerPageSearch, Locale locale,
            RedirectAttributes redirectAttributes) {

        // Security for this page.
        if (!hasRoleEdit()) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        homePageService.delete(homepageId);

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        redirectAttributes.addAttribute("startDate", startDateSearch);
        redirectAttributes.addAttribute("endDate", endDateSearch);
        redirectAttributes.addAttribute("status", statusSearch);
        redirectAttributes.addAttribute("bannerPage", bannerPageSearch);

        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.HOMEPAGE_SETTING)
                .concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);
        return mav;
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
        // Security for this page.
        if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_HOME)
                && !UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_HOME.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(ViewConstant.VIEW_HISTORY_TABLE);

        // Init PageWrapper
//        PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.searchHistory(page,
//                condition.getReferenceId(), condition.getReferenceType());
//        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);

        return mav;
    }

    @RequestMapping(value = "path/images/list", method = RequestMethod.GET)
    @ResponseBody
    public String getPathImagesJson(HttpServletRequest request) throws IOException {

        List<ImageDto> resultList = CmsUtils.getFilePathListServer(AdminConstant.HOMEPAGER_FOLDER,
                request.getContextPath(), null);

        String result = CommonJsonUtil.convertObjectToJsonString(resultList);

        return result;
    }

    @RequestMapping(value = UrlConst.URL_EDITOR_UPLOAD, method = RequestMethod.POST)
    public @ResponseBody String editorUpload(MultipartHttpServletRequest request, HttpServletRequest request2,
            Model model, HttpServletResponse response,
            @RequestParam(required = true, value = "requesttoken") String requestToken,
            @RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
            throws JSONException, IOException {
        String fileName = fileService.uploadTemp(request, request2, model, response,
                Paths.get(AdminConstant.HOMEPAGER_FOLDER, AdminConstant.EDITOR_FOLDER).toString(), requestToken);

        String fileUrl = URLEncoder.encode(fileName, "UTF-8");

        String downloadUrl = request2.getContextPath().concat(UrlConst.ROOT).concat(UrlConst.HOMEPAGE_SETTING)
                .concat(UrlConst.URL_EDITOR_DOWNLOAD).concat("?url=").concat(fileUrl);
        return "<script> " + "window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", \"" + downloadUrl + "\");"
                + "</script>";
    }

    @RequestMapping(value = AdminUrlConst.URL_EDITOR_FILE_UPLOAD, method = RequestMethod.POST)
    public @ResponseBody String editorFileUpload(MultipartHttpServletRequest request, HttpServletRequest request2,
            Model model, HttpServletResponse response,
            @RequestParam(required = true, value = "requesttoken") String requestToken,
            @RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
            throws JSONException, IOException {
        String fileName = fileService.uploadTemp(request, request2, model, response,
                Paths.get(UrlConst.HOMEPAGE_SETTING, AdminConstant.EDITOR_FOLDER).toString(), requestToken);
        String fileUrl = URLEncoder.encode(fileName, "UTF-8");
        String downloadUrl = request2.getContextPath().concat(UrlConst.ROOT).concat(UrlConst.HOMEPAGE_SETTING)
                .concat(UrlConst.URL_EDITOR_DOWNLOAD).concat("?url=").concat(fileUrl);
        return "<script> " + "window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", \"" + downloadUrl + "\");"
                + "</script>";
    }

    @RequestMapping(value = UrlConst.URL_EDITOR_DOWNLOAD, method = RequestMethod.GET)
    public void editorDownload(@RequestParam(required = true, value = "url") String fileUrl, HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String url = AdminConstant.HOMEPAGER_EDITOR_FOLDER + "/" + fileUrl;
        homePageService.requestEditorDownload(url, request, response);
    }

    private boolean hasRoleAccess() {
        if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_HOME)) {
            return false;
        }

        return true;
    }

    private boolean hasRoleEdit() {
        if (!UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_HOMEPAGE_EDIT.concat(ConstantCore.COLON_EDIT))) {
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
    public DocumentWorkflowCommonService<HomepageSettingEditDto, HomepageSettingEditDto> getService() {
        return homePageService;
    }

    @Override
    public String getControllerURL(String customerAlias) {
        return UrlConst.HOMEPAGE_SETTING;
    }

    @Override
    public String getBusinessCode(String customerAlias) {

        String businessCode = CmsCommonConstant.BUSINESS_HOMEPAGESETTIMG;

        logger.error("NEED CHANGE BUSINESS CODE");
        // TODO remove "BUSINESS_BANNER"
        businessCode = "BUSINESS_BANNER"; // remove this line, only for test
        logger.error("NEED CHANGE BUSINESS CODE");

        return businessCode;
    }

    @Override
    public String viewEdit(String customerAlias) {
        return HOMEPAGE_EDIT;
    }

    @Override
    public void sendEmailAction(HomepageSettingEditDto editDto, Long buttonId) {

    }

    @Override
    public void sendEmailEdit(HomepageSettingEditDto editDto, Long userUpdated) {

    }

    @Override
    public String objectEditName() {
        return "homepageEdit";
    }

    @Override
    public String firstStepInProcess(String customerAlias) {
        return "submit";
    }

    @Override
    public String roleForAttachment(String customerAlias) {
        return CmsRoleConstant.PAGE_LIST_HOME;
    }

    @Override
    public void initDataEdit(ModelAndView mav, String customerAlias, HomepageSettingEditDto editDto, boolean isDetail,
            Locale locale) {
        inItScreenEdit(mav, editDto, locale);
    }

    @Override
    public void validate(HomepageSettingEditDto editDto, BindingResult bindingResult) {
    }
}