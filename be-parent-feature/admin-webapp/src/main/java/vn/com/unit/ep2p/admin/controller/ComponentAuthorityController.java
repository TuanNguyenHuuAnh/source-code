/*******************************************************************************
 * Class        :ComponentAuthorityController
 * Created date :2019/04/22
 * Lasted date  :2019/04/22
 * Author       :HungHT
 * Change log   :2019/04/22:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
//import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.ComponentAuthorityJcanaryService;
import vn.com.unit.ep2p.admin.service.FormService;
import vn.com.unit.ep2p.constant.AppUrlConst;
import vn.com.unit.ep2p.constant.ItemConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.dto.ComponentAuthorityListDto;

/**
 * ComponentAuthorityController
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Controller
@RequestMapping(AppUrlConst.COMPONENT_AUTHORITY)
public class ComponentAuthorityController {

    /** MessageSource */
    @Autowired
    private MessageSource msg;

    @Autowired
    ComponentAuthorityJcanaryService componentAuthorityJcanaryService;

    @Autowired
    FormService formService;
    
//    @Autowired
//    private CompanyService companyService;

    /** SCREEN_FUNCTION_CODE */
    private static final String SCREEN_FUNCTION_CODE = ItemConstant.ITEM_SCREEN_COMPONENT_AUTHORITY;

    /**
     * Screen list authority (Init)
     * 
     * @return ModelAndView
     * @author KhoaNA
     */
    @GetMapping(UrlConst.LIST)
    public ModelAndView getList(HttpServletRequest request, Locale locale) {
        ModelAndView mav = new ModelAndView("/views/component-authority/component-authority-list.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // Add company list
//        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
//        mav.addObject("companyList", companyList);
        
        Long companyId = UserProfileUtils.getCompanyId();
        mav.addObject("companyId", companyId);
        
        List<Select2Dto> formList = formService.findByCompanyId(companyId);
        mav.addObject("formList", formList);
        
        return mav;
    }

    /**
     * getAjaxEdit
     * 
     * @param itemId
     * @param formId
     * @return
     * @author HungHT
     */
    @GetMapping(UrlConst.AJAX_EDIT)
    @ResponseBody
    public ModelAndView getAjaxEdit(@RequestParam(value = "itemId", required = false) Long itemId,
            @RequestParam(value = "formId", required = false) Long formId,
            @RequestParam(value = "companyId", required = true) Long companyId) {
        ModelAndView mav = new ModelAndView("/views/component-authority/component-iauthority-edit.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        mav.addObject("itemId", itemId);
        
//        List<ComponentAuthorityDto> componentAuthorityList = componentAuthorityJcanaryService.getComponentAuthorityList(itemId, formId);
//        mav.addObject("componentAuthorityList", componentAuthorityList);
        return mav;
    }

    /**
     * postAjaxEdit
     * 
     * @param componentAuthority
     * @param locale
     * @param request
     * @return
     * @author HungHT
     */
    @PostMapping(UrlConst.AJAX_EDIT)
    @ResponseBody
    public ModelAndView postAjaxEdit(@RequestBody ComponentAuthorityListDto componentAuthority, Locale locale, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/views/commons/message-alert.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgStr = msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);

        try {
//        	componentAuthorityJcanaryService.saveComponentAuthority(componentAuthority);
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            msgStr = msg.getMessage(ConstantCore.MSG_FAIL_SAVE, null, locale);
        }

        messageList.add(msgStr);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        return mav;
    }
    
    /**
     * Ajax
     * getListFuction
     * @param request
     * @param locale
     * @param companyId
     * @return
     * @author trieuvd
     */
    @RequestMapping(value = UrlConst.AJAX_LIST, method = RequestMethod.GET)
    public ModelAndView getListFuction(HttpServletRequest request, Locale locale,
            @RequestParam(value = "companyId", required = true) Long companyId) {
        ModelAndView mav = new ModelAndView("/views/component-authority/component-authority-function-list.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        List<Select2Dto> itemList = componentAuthorityJcanaryService.getItemListByForm(null, companyId, false, false, 2L);

        String itemIdSelected = null;
        if (itemList.isEmpty()) {
            MessageList messageList = new MessageList(Message.WARNING);
            String content = msg.getMessage("table.emptyTable.label", null, locale);
            messageList.add(content);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        } else {
            Select2Dto itemFirst = itemList.get(0);
            itemIdSelected = itemFirst.getId();
        }
        
        mav.addObject("itemList", itemList);
        mav.addObject("itemIdSelected", itemIdSelected);
        return mav;
    }
    
    @RequestMapping(value = "/get_formList_by_company", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getFormListByCompany(@RequestParam(value = "companyId", required = true) Long companyId) {
        List<Select2Dto> formList = formService.findByCompanyId(companyId);
        String formListJson = CommonJsonUtil.convertObjectToJsonString(formList);
        return formListJson;
    }
}
