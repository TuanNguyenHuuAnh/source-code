/*******************************************************************************
 * Class        ：NotiTemplateController
 * Created date ：2019/11/11
 * Lasted date  ：2019/11/11
 * Author       ：trieuvd
 * Change log   ：2019/11/11：01-00 trieuvd create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaNotiTemplateDto;
import vn.com.unit.core.entity.JcaNotiTemplate;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.UrlConst;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.service.AppNotiTemplateService;
import vn.com.unit.ep2p.admin.validators.NotiTemplateValidator;
import vn.com.unit.ep2p.dto.NotiTemplateSearchDto;

/**
 * <p>
 * NotiTemplateController
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Controller
@RequestMapping(UrlConst.NOTI_TEMPLATE)
public class NotiTemplateController {

    @Autowired
    private AppNotiTemplateService appNotiTemplateService;

    @Autowired
    private MessageSource msg;

    @Autowired
    private SystemConfig systemConfig;
    
    @Autowired
    private NotiTemplateValidator notiTemplateValidator;

    private static final String SCREEN_FUNCTION_CODE = RoleConstant.EMAIL_TEMPLATE;
    private static final String MAV_NOTI_TEMPLATE_LIST = "/views/noti-template/noti-template-list.html";
    private static final String MAV_NOTI_TEMPLATE_TABLE = "/views/noti-template/noti-template-table.html";
    private static final String MAV_NOTI_TEMPLATE_DETAIL = "/views/noti-template/noti-template-detail.html";
    private static final String OBJ_SEARCH = "searchDto";
    private static final String OBJECT_DTO = "objectDto";
    private static final String OBJ_PAGE_WRAPPER = "pageWrapper";
    private static final String URL = "url";
    private static final String TEMPLATE_ID = "id";

    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getPostList(@ModelAttribute(value = OBJ_SEARCH) NotiTemplateSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) throws DetailException {
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(MAV_NOTI_TEMPLATE_LIST);
        if (null == searchDto) {
            searchDto = new NotiTemplateSearchDto();
            searchDto.setCompanyId(UserProfileUtils.getCompanyId());
        }
        searchDto.setCompanyId(UserProfileUtils.getCompanyId());
        // Init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        // Get List
        PageWrapper<JcaNotiTemplateDto> pageWrapper = appNotiTemplateService.getJcaNotiTemplateDtoList(searchDto, pageSize, page);

        appNotiTemplateService.initScreenList(mav, searchDto, locale);
        // Object mav
        mav.addObject(OBJ_SEARCH, searchDto);
        mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
        return mav;
    }

    /**
     * ajaxList
     *
     * @param searchDto
     * @param page
     * @param pageSizeParam
     * @param locale
     * @return
     * @author KhoaNA
     * @throws DetailException
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = OBJ_SEARCH) NotiTemplateSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) throws DetailException {
        ModelAndView mav = new ModelAndView(MAV_NOTI_TEMPLATE_TABLE);
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        if (null == searchDto) {
            searchDto = new NotiTemplateSearchDto();
            searchDto.setCompanyId(UserProfileUtils.getCompanyId());
        }
        
        // Init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        // Get List
        PageWrapper<JcaNotiTemplateDto> pageWrapper = appNotiTemplateService.getJcaNotiTemplateDtoList(searchDto, pageSize, page);
        appNotiTemplateService.initScreenList(mav, searchDto, locale);
        // Object mav
        mav.addObject(OBJ_SEARCH, searchDto);
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
    public ModelAndView getDetail(@RequestParam(value = TEMPLATE_ID, required = false) Long templateId, Locale locale) {
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(MAV_NOTI_TEMPLATE_DETAIL);
        // Object dto
        JcaNotiTemplateDto objectDto = null;
        // URL ajax redirect
        StringBuilder urlRedirect = new StringBuilder(UrlConst.NOTI_TEMPLATE.substring(1));
        if (templateId != null) {
            objectDto = appNotiTemplateService.getJcaNotiTemplateDtoById(templateId);
            urlRedirect.append(UrlConst.DETAIL.concat("?id=").concat(templateId.toString()));
        } else {
            objectDto = new JcaNotiTemplateDto();
            objectDto.setCompanyId(UserProfileUtils.getCompanyId());
            urlRedirect.append(UrlConst.ADD);
        }
        appNotiTemplateService.initScreenEdit(mav, objectDto, locale);
        // Object mav
        mav.addObject(OBJECT_DTO, objectDto);
        mav.addObject(URL, urlRedirect.toString());

        return mav;
    }

    @RequestMapping(value = UrlConst.SAVE, method = { RequestMethod.POST })
    public ModelAndView saveCategoryDetail(@ModelAttribute(value = OBJECT_DTO) JcaNotiTemplateDto objectDto, BindingResult bindingResult,
            Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(MAV_NOTI_TEMPLATE_DETAIL);
        // Init message list
        MessageList messageList = new MessageList(Message.ERROR);
        String msgInfo = CommonStringUtil.EMPTY;
        
        notiTemplateValidator.validate(objectDto, bindingResult);
        if(!bindingResult.hasErrors()) {
            try {
                JcaNotiTemplate result = appNotiTemplateService.saveJcaNotiTemplateDto(objectDto);
                messageList.setStatus(Message.SUCCESS);
                msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);
                messageList.add(msgInfo);
                String viewName = UrlConst.REDIRECT.concat(UrlConst.NOTI_TEMPLATE).concat(UrlConst.DETAIL);
                Long id = result.getId();
                redirectAttributes.addAttribute(TEMPLATE_ID, id);
                redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
                mav.setViewName(viewName);
                return mav;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        appNotiTemplateService.initScreenEdit(mav, objectDto, locale);
        msgInfo = msg.getMessage(ConstantCore.MSG_FAIL_SAVE, null, locale);
        messageList.add(msgInfo);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject(OBJECT_DTO, objectDto);
        return mav;
    }

    @RequestMapping(value = UrlConst.DELETE, method = { RequestMethod.POST })
    public ModelAndView deleteCategoryDetail(@RequestParam(value = TEMPLATE_ID) Long id, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        try {
            appNotiTemplateService.deleteJcaNotiTemplate(id);
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
        }
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        // redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.NOTI_TEMPLATE).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);
        return mav;
    }
}
