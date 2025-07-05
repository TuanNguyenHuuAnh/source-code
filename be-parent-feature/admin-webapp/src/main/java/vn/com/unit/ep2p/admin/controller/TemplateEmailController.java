/*******************************************************************************
 * Class        TemplateEmailController
 * Created date 2018/08/29
 * Lasted date  2018/08/29
 * Author       phatvt
 * Change log   2018/08/2901-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

//import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import org.springframework.web.util.HtmlUtils;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.entity.JcaEmailTemplate;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.BeAdminFileService;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.ep2p.admin.service.TemplateService;
import vn.com.unit.ep2p.admin.validators.TemplateValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.utils.FileReaderUtils;
import vn.com.unit.ep2p.dto.TemplateEmailDto;
import vn.com.unit.ep2p.dto.TemplateSearchDto;
import vn.com.unit.ep2p.enumdef.TemplateSearchEnum;
import vn.com.unit.ep2p.export.util.SearchUtil;
import vn.com.unit.ep2p.utils.ConditionSearchUtils;

/**
 * TemplateEmailController
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
@Controller
@RequestMapping(UrlConst.TEMPLATE_EMAIL)
public class TemplateEmailController {

    @Autowired
    private MessageSource msg;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TemplateValidator templateValidator;

    @Autowired
    private BeAdminFileService fileService;
    
    @Autowired
    private JRepositoryService jRepositoryService;

    // @Autowired
    // private TemplateParameterService templateParameterService;

    private static final String TEMPLATE_VIEW = "/views/template-email/template-email-list.html";

    private static final String TEMPLATE_TABLE = "/views/template-email/template-email-table.html";

    private static final String TEMPLATE_DETAIL = "/views/template-email/template-email-detail2.html";

    private static final String TEMPLATE_EDIT = "/views/template-email/template-email-edit.html";

    private static final String SCREEN_FUNCTION_CODE = RoleConstant.EMAIL_TEMPLATE;

    public static final String EMAIL_TEMPALTE_FOLDER = "email_template/";

    private static final String OBJECT_DTO = "objectDto";

    // private static final String MSG_FLAG = "msgFlag";

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
     * listTemplate
     *
     * @param searchDto
     * @param page
     * @param locale
     * @return
     * @author phatvt
     * @throws DetailException
     */
    @GetMapping(value = UrlConst.LIST)
    public ModelAndView listTemplate(@ModelAttribute(value = "searchDto") TemplateSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale, HttpServletRequest request)
            throws DetailException {
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(TEMPLATE_VIEW);

        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        Long companyId = UserProfileUtils.getCompanyId();
        searchDto.setCompanyId(companyId);
        mav.addObject("companyId", companyId);
        // set init search
        SearchUtil.setSearchSelect(TemplateSearchEnum.class, mav);

        // init page size
        //int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(AppSystemConfig.PAGING_SIZE));
        int pageSize = pageSizeParam
                .orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE, UserProfileUtils.getCompanyId()));
        
     // Session Search
        ConditionSearchUtils<TemplateSearchDto> searchUtil = new ConditionSearchUtils<TemplateSearchDto>();
        String[] urlContains = new String[] { "email/template/add", "email/template/edit", "email/template/detail", "email/template/list" };
        searchDto = searchUtil.getConditionSearch(this.getClass(), searchDto, urlContains, request, page, pageSize);
        pageSize = Optional.ofNullable(searchDto.getPageSize()).orElse(pageSize);
        page = Optional.ofNullable(searchDto.getPage()).orElse(page);

        PageWrapper<JcaEmailTemplateDto> pageWrapper = templateService.doSearch(page, searchDto, pageSize, locale);
//        if (pageWrapper.getCountAll() == 0) {
//            // Init message list
//            MessageList messageList = new MessageList(Message.INFO);
//            String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
//            messageList.add(msgInfo);
//            mav.addObject(ConstantCore.MSG_LIST, messageList);
//        }
        
//        if (StringUtils.isNoneBlank(searchDto.getSearchKeyIds())) {
//            List<String> list = new ArrayList<>();
//            CollectionUtils.addAll(list, searchDto.getSearchKeyIds().split(","));
//            searchDto.setFieldValues(list);
//        }
        searchDto.setFieldValues(searchDto.getSearchKeyIds());
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("searchDto", searchDto);
        return mav;
    }

    /**
     * ajaxListEmail
     *
     * @param searchDto
     * @param page
     * @param locale
     * @return
     * @author TaiTT
     * @throws DetailException
     */
    @PostMapping(value = UrlConst.AJAXLIST)
    @ResponseBody
    public ModelAndView ajaxListEmail(@ModelAttribute(value = "searchDto") TemplateSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale, HttpServletRequest request)
            throws DetailException {
        ModelAndView mav = new ModelAndView(TEMPLATE_TABLE);
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set init search
        SearchUtil.setSearchSelect(TemplateSearchEnum.class, mav);

        // init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));

        PageWrapper<JcaEmailTemplateDto> pageWrapper = templateService.doSearch(page, searchDto, pageSize, locale);
        
        ConditionSearchUtils<TemplateSearchDto> searchUtil = new ConditionSearchUtils<TemplateSearchDto>();
        searchUtil.setConditionSearch(request, this.getClass(), searchDto, page, pageSize);
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("searchDto", searchDto);
        return mav;
    }

    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    public ModelAndView postDelete(@RequestParam(value = "templateId", required = true) Long templateId, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        try {
            templateService.deleteTemplate(templateId);
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
        }
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        // redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.TEMPLATE_EMAIL).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);
        return mav;
    }

    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView getEdit(@RequestParam(value = "id", required = false) Long id, Model model, Locale locale) {
        ModelAndView mav = new ModelAndView(TEMPLATE_EDIT);
        // Security for this page.
        // if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
        // &&
        // !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
        // &&
        // !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT)))
        // {
        // return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        // }
        // If redirect page
        Map<String, Object> md = model.asMap();
        MessageList messageList = (MessageList) md.get(ConstantCore.MSG_LIST);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        TemplateEmailDto objectDto = new TemplateEmailDto();
        // set url ajax
        String url = UrlConst.TEMPLATE_EMAIL.concat(UrlConst.EDIT);
        if (null != id) {
            objectDto = templateService.getTemplateEmailDtoById(id);
            // Security for data
            // if (null == objectDto ||
            // !UserProfileUtils.hasRoleForCompany(objectDto.getCompanyId())) {
            // return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
            // }
            url = url.concat("?id=").concat(id.toString());
        } else {
            objectDto.setCompanyId(UserProfileUtils.getCompanyId());
        }
        url = url.substring(1);
        objectDto.setUrl(url);
        // init data
        templateService.InitScreenEdit(mav, objectDto, locale);

        mav.addObject(OBJECT_DTO, objectDto);
        return mav;
    }

    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    public ModelAndView postEdit(@Valid @ModelAttribute(value = OBJECT_DTO) TemplateEmailDto objectDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(TEMPLATE_EDIT);
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        objectDto.setTemplateContent(HtmlUtils.htmlEscape(objectDto.getTemplateContent()));

        // Init message list
        MessageList messageList = new MessageList(Message.ERROR);
        String msgInfo = CommonStringUtil.EMPTY;

        templateValidator.validate(objectDto, bindingResult);
        if (!bindingResult.hasErrors()) {
            try {
                TemplateEmailDto result = templateService.saveTemplateDto(objectDto);
                messageList.setStatus(Message.SUCCESS);
                msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);
                messageList.add(msgInfo);
                String viewName = UrlConst.REDIRECT.concat(UrlConst.TEMPLATE_EMAIL).concat(UrlConst.EDIT);
                Long id = result.getTemplateId();
                redirectAttributes.addAttribute("id", id);
                redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
                mav.setViewName(viewName);
                return mav;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // init data
        templateService.InitScreenEdit(mav, objectDto, locale);
        msgInfo = msg.getMessage(ConstantCore.MSG_FAIL_SAVE, null, locale);
        messageList.add(msgInfo);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject(OBJECT_DTO, objectDto);
        return mav;
    }

    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
    public ModelAndView getDetail(@RequestParam(value = "id", required = true) Long id, Locale locale) {
        ModelAndView mav = new ModelAndView(TEMPLATE_DETAIL);
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        TemplateEmailDto objectDto = new TemplateEmailDto();
        if (null != id) {
            objectDto = templateService.getTemplateEmailDtoById(id);
            // Security for data
            // if (null == objectDto ||
            // !UserProfileUtils.hasRoleForCompany(objectDto.getCompanyId())) {
            // return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
            // }
        } else {
            objectDto.setCompanyId(UserProfileUtils.getCompanyId());
        }
        // init data
        templateService.InitScreenEdit(mav, objectDto, locale);

        mav.addObject(OBJECT_DTO, objectDto);
        return mav;
    }

    @PostMapping(UrlConst.TEMPLATE_DETAIL)
    @ResponseBody
    public JcaEmailTemplate getContentTemplate(@RequestParam("templateId") String templateId) {
        JcaEmailTemplate template = templateService.getTemplateById(Long.parseLong(templateId));
        return template;
    }

    @PostMapping(value = UrlConst.IMPORT)
    @ResponseBody
    public JcaEmailTemplate importTemplate(@RequestParam("file") MultipartFile mpf,
            @RequestParam(value = "companyId", required = false, defaultValue = "1") Long companyId, Locale locale,
            RedirectAttributes redirectAttributes) throws IOException {

        JcaEmailTemplate template = new JcaEmailTemplate();

        String fileName = fileService.uploadTemp(mpf, "TEMPLATE_EMAIL", "TEMP");

        String tempPath = jRepositoryService.getPathByRepository(SystemConfig.REPO_UPLOADED_TEMP);
        String path = Paths.get(tempPath, "TEMPLATE_EMAIL").toString() + "//" + fileName;
        StringBuffer textFile = FileReaderUtils.readFile(path);
        template.setTemplateContent(textFile.toString());

        return template;
    }
}
