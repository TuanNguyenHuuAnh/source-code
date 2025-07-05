/*******************************************************************************
 * Class        ：IntroduceInternetBankingController
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hornetq.utils.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingEditDto;
import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingSearchDto;
import vn.com.unit.cms.admin.all.enumdef.IntroduceInternetBankingSearchEnum;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.IntroduceInternetBankingService;
import vn.com.unit.cms.admin.all.validator.IntroduceInternetBankingEditValidator;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.constant.RoleConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.ep2p.utils.SearchUtil;
import vn.com.unit.common.utils.CommonUtil;

/**
 * IntroduceInternetBankingController
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
@Controller
@RequestMapping(value = AdminUrlConst.INTRODUCE_INTERNET_BANKING)
public class IntroduceInternetBankingController {

    @Autowired
    private MessageSource msg;

    @Autowired
    private IntroduceInternetBankingEditValidator introduceInternetBankingEditValidator;

    @Autowired
    private IntroduceInternetBankingService introduceInternetBankingService;

    @Autowired
    private CmsFileService fileService;

    @Autowired
    private CmsCommonService commonService;

    private static final String INTRODUCE_INTERNET_BANKING_EDIT = "introduce.internet.banking.edit";

    private static final String INTRODUCE_INTERNET_BANKING_DETAIL = "introduce.internet.banking.detail";

    private static final String INTRODUCE_INTERNET_BANKING_TABLE = "introduce.internet.banking.table";

    private static final String INTRODUCE_INTERNET_BANKING_LIST = "introduce.internet.banking.list";

    private static final String PREFIX_CODE = "BANK.";

    /**
     * ajaxList
     *
     * @param introduceInternetBankingSearch
     * @param page
     * @param locale
     * @return ModelAndView
     * @author hoangnp
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(
            @ModelAttribute(value = "introduceInternetBankingSearch") IntroduceInternetBankingSearchDto introduceInternetBankingSearch,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
        // Security for this page.
        if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_INTERNET_BANKING)
                && !UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_INTERNET_BANKING.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(INTRODUCE_INTERNET_BANKING_TABLE);
        // set language code
        introduceInternetBankingSearch.setLanguageCode(locale.toString());
        // Init PageWrapper
        PageWrapper<IntroduceInternetBankingLanguageSearchDto> pageWrapper = introduceInternetBankingService
                .search(page, introduceInternetBankingSearch);
        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);

        return mav;
    }

    /**
     * getIntroduceInternetBankingList
     *
     * @param introduceInternetBankingSearch
     * @param page
     * @param locale
     * @return ModelAndView
     * @author hoangnp
     */
    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getIntroduceInternetBankingList(
            @ModelAttribute(value = "introduceInternetBankingSearch") IntroduceInternetBankingSearchDto introduceInternetBankingSearch,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_INTERNET_BANKING)
                && !UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_INTERNET_BANKING.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // set url
        introduceInternetBankingSearch.setUrl(AdminUrlConst.INTRODUCE_INTERNET_BANKING.concat(UrlConst.LIST));

        ModelAndView mav = new ModelAndView(INTRODUCE_INTERNET_BANKING_LIST);

        // Init master data
        SearchUtil.setSearchSelect(IntroduceInternetBankingSearchEnum.class, mav);

        // introduceTypeList
        introduceInternetBankingService.initIntroduceInternetBankingEdit(mav, locale.toString());

        // set language
        introduceInternetBankingSearch.setLanguageCode(locale.toString());

        PageWrapper<IntroduceInternetBankingLanguageSearchDto> pageWrapper = introduceInternetBankingService
                .search(page, introduceInternetBankingSearch);

        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);

        mav.addObject("introduceInternetBankingSearch", introduceInternetBankingSearch);

        return mav;

    }

    /**
     * delete
     *
     * @param id
     * @param locale
     * @param redirectAttributes
     * @return ModelAndView
     * @author hoangnp
     */
    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView delete(@RequestParam(value = "id", required = true) Long id, Locale locale,
            RedirectAttributes redirectAttributes) {
        // Security for this page.
//        if (!UserProfileUtils.hasRole(RoleConstant.BUTTON_INTERNET_BANKING_DELETE)
//            && !UserProfileUtils.hasRole(RoleConstant.BUTTON_INTERNET_BANKING_DELETE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }

        // Init message success list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);

        // delete introduce
        try {
            introduceInternetBankingService.deleteIntroduce(id);
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
        }

        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(AdminUrlConst.INTRODUCE_INTERNET_BANKING)
                .concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);

        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        return mav;

    }

    /**
     * addOrEdit
     *
     * @param introduceInternetBankingId
     * @param locale
     * @return ModelAndView
     * @author hoangnp
     */
    @RequestMapping(value = UrlConst.EDIT, method = { RequestMethod.GET })
    public ModelAndView addOrEdit(@RequestParam(value = "id", required = false) Long introduceInternetBankingId,
            Locale locale) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_INTERNET_BANKING_EDIT) && !UserProfileUtils
                .hasRole(CmsRoleConstant.BUTTON_INTERNET_BANKING_EDIT.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(INTRODUCE_INTERNET_BANKING_EDIT);

        introduceInternetBankingService.initIntroduceInternetBankingEdit(mav, locale.toString());

        IntroduceInternetBankingEditDto introduceInternetBankingEditDto = introduceInternetBankingService
                .getIntroduceInternetBanking(introduceInternetBankingId, locale.toString());

        String url = AdminUrlConst.INTRODUCE_INTERNET_BANKING.concat(UrlConst.EDIT);
        if (null != introduceInternetBankingId) {
            url = url.concat("?id=").concat(introduceInternetBankingId.toString());
        }
        introduceInternetBankingEditDto.setUrl(url);

        String requestToken = CommonUtil.randomStringWithTimeStamp();
        introduceInternetBankingEditDto.setRequestToken(requestToken);

        mav.addObject("introduceInternetBankingEdit", introduceInternetBankingEditDto);

        return mav;
    }

    /**
     * sendRequest
     *
     * @param introduceInternetBankingEdit
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @return ModelAndView
     * @throws IOException
     * @author hoangnp
     */
    @RequestMapping(value = UrlConst.EDIT, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView sendRequest(
            @Valid @ModelAttribute("introduceInternetBankingEdit") IntroduceInternetBankingEditDto introduceInternetBankingEdit,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes) throws IOException {

        return saveOrEdit(introduceInternetBankingEdit, bindingResult, locale, redirectAttributes);
    }

    private ModelAndView saveOrEdit(IntroduceInternetBankingEditDto introduceInternetBankingEdit,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes) {
        // Security for this page.
        if (!UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_INTERNET_BANKING_EDIT) && !UserProfileUtils
                .hasRole(CmsRoleConstant.BUTTON_INTERNET_BANKING_EDIT.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(INTRODUCE_INTERNET_BANKING_EDIT);
        if (introduceInternetBankingEdit.getId() == null) {
            introduceInternetBankingEdit.setCode(CommonUtil.getNextCode(PREFIX_CODE,
                    commonService.getMaxCode("M_INTRODUCE_INTERNET_BANKING", PREFIX_CODE)));
        }

        // Validate business
        introduceInternetBankingEditValidator.validate(introduceInternetBankingEdit, bindingResult);

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale);
            messageList.add(msgError);

            // Init master data
            introduceInternetBankingService.initIntroduceInternetBankingEdit(mav, locale.toString());

            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("introduceInternetBankingEdit", introduceInternetBankingEdit);

            return mav;
        }

        // create or edit
        introduceInternetBankingService.addOrEdit(introduceInternetBankingEdit);
        String msgInfo = StringUtils.EMPTY;
        String viewName = StringUtils.EMPTY;

        msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        if (null == introduceInternetBankingEdit.getId()) {
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
        }
        // success redirect detail page
        viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(AdminUrlConst.INTRODUCE_INTERNET_BANKING)
                .concat(UrlConst.EDIT);

        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addAttribute("id", introduceInternetBankingEdit.getId());

        mav.setViewName(viewName);
        return mav;
    }

    /**
     * detail
     *
     * @param introduceInternetBankingId
     * @param locale
     * @return ModelAndView
     * @author hoangnp
     */
    @RequestMapping(value = UrlConst.DETAIL, method = { RequestMethod.GET })
    public ModelAndView detail(@RequestParam(value = "id", required = true) Long introduceInternetBankingId,
            Locale locale) {

        ModelAndView mav = new ModelAndView(INTRODUCE_INTERNET_BANKING_DETAIL);

        introduceInternetBankingService.initIntroduceInternetBankingEdit(mav, locale.toString());

        IntroduceInternetBankingEditDto introduceInternetBankingEditDto = introduceInternetBankingService
                .getIntroduceInternetBanking(introduceInternetBankingId, locale.toString());

        String url = AdminUrlConst.INTRODUCE_INTERNET_BANKING.concat(UrlConst.DETAIL);
        if (null != introduceInternetBankingId) {
            url = url.concat("?id=").concat(introduceInternetBankingId.toString());
        }
        introduceInternetBankingEditDto.setUrl(url);

        mav.addObject("introduceInternetBankingEdit", introduceInternetBankingEditDto);

        return mav;
    }

    /**
     * editorDownload
     *
     * @param fileUrl
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @author hand
     */
    @RequestMapping(value = UrlConst.URL_EDITOR_DOWNLOAD, method = RequestMethod.GET)
    public void editorDownload(@RequestParam(required = true, value = "url") String fileUrl, HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String url = Paths.get(AdminConstant.INTRODUCE_INTERNET_BANKING_FOLDER, AdminConstant.EDITOR_FOLDER, fileUrl)
                .toString();

        fileService.requestEditorDownload(url, request, response);
    }

    /**
     * editorUpload
     *
     * @param request
     * @param request2
     * @param model
     * @param response
     * @param requestToken
     * @param funcNum
     * @return
     * @throws JSONException
     * @author hand
     * @throws IOException
     */
    @RequestMapping(value = UrlConst.URL_EDITOR_UPLOAD, method = RequestMethod.POST)
    public @ResponseBody String editorUpload(MultipartHttpServletRequest request, HttpServletRequest request2,
            Model model, HttpServletResponse response,
            @RequestParam(required = true, value = "requesttoken") String requestToken,
            @RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
            throws JSONException, IOException {
        String fileName = fileService.uploadTemp(request, request2, model, response,
                Paths.get(AdminConstant.INTRODUCE_INTERNET_BANKING_FOLDER, AdminConstant.EDITOR_FOLDER).toString(),
                requestToken);

        String fileUrl = URLEncoder.encode(fileName, "UTF-8");

        String downloadUrl = request2.getContextPath().concat(UrlConst.ROOT)
                .concat(AdminUrlConst.INTRODUCE_INTERNET_BANKING).concat(UrlConst.URL_EDITOR_DOWNLOAD).concat("?url=")
                .concat(fileUrl);
        return "<script> " + "window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", \"" + downloadUrl + "\");"
                + "</script>";
    }

    /**
     * upload file ckeditor
     * 
     * @param request
     * @param request2
     * @param model
     * @param response
     * @param requestToken
     * @param funcNum
     * @return
     * @throws JSONException
     * @throws IOException
     */
    @RequestMapping(value = AdminUrlConst.URL_EDITOR_FILE_UPLOAD, method = RequestMethod.POST)
    public @ResponseBody String editorFileUpload(MultipartHttpServletRequest request, HttpServletRequest request2,
            Model model, HttpServletResponse response,
            @RequestParam(required = true, value = "requesttoken") String requestToken,
            @RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
            throws JSONException, IOException {
        String fileName = fileService.uploadTemp(request, request2, model, response,
                Paths.get(AdminConstant.INTRODUCE_INTERNET_BANKING_FOLDER, AdminConstant.EDITOR_FOLDER).toString(),
                requestToken);
        String fileUrl = URLEncoder.encode(fileName, "UTF-8");
        String downloadUrl = request2.getContextPath().concat(UrlConst.ROOT)
                .concat(AdminUrlConst.INTRODUCE_INTERNET_BANKING).concat(AdminUrlConst.URL_EDITOR_FILE_DOWNLOAD)
                .concat("?url=").concat(fileUrl);
        return "<script> " + "window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", \"" + downloadUrl + "\");"
                + "</script>";
    }
}
