/*******************************************************************************
 * Class        ：CustomerController
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

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

import vn.com.unit.cms.admin.all.dto.CustomerTypeEditDto;
import vn.com.unit.cms.admin.all.dto.CustomerTypeLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.CustomerTypeSearchDto;
import vn.com.unit.cms.admin.all.enumdef.CustomerTypeSearchEnum;
import vn.com.unit.cms.admin.all.service.CustomerTypeService;
import vn.com.unit.cms.admin.all.validator.CustomerTypeEditValidator;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
//import vn.com.unit.jcanary.constant.RoleConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.utils.SearchUtil;

/**
 * CustomerController
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Controller
@RequestMapping(value = UrlConst.ROOT + UrlConst.CUSTOMER_TYPE)
public class CustomerTypeController {
    @Autowired
    private MessageSource msg;

    @Autowired
    private CustomerTypeService typeService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private CustomerTypeEditValidator customerTypeEditValidator;

    private static final String CUSTOMER_TYPE_LIST = "customer.type.list";

    private static final String CUSTOMER_TYPE_EDIT = "customer.type.edit";

    private static final String CUSTOMER_TYPE_DETAIL = "customer.type.detail";

    private static final String CUSTOMER_TYPE_TABLE = "customer.type.table";

    /**
     * getCustomerTypeList
     *
     * @param typeSearch
     * @param page
     * @return ModelAndView
     * @author hand
     */
    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getCustomerTypeList(@ModelAttribute(value = "typeSearch") CustomerTypeSearchDto typeSearch,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
        // Security for this page.
//		if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN)
//				&& !UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}

        // set url
        typeSearch.setUrl(UrlConst.CUSTOMER_TYPE.concat(UrlConst.LIST));

        ModelAndView mav = new ModelAndView(CUSTOMER_TYPE_LIST);

        // set init search
        SearchUtil.setSearchSelect(CustomerTypeSearchEnum.class, mav);

        // set language
        typeSearch.setLanguageCode(locale.toString());

        PageWrapper<CustomerTypeLanguageSearchDto> pageWrapper = typeService.searchTypeLanguage(page, typeSearch);

        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);

        mav.addObject("typeSearch", typeSearch);

        return mav;
    }

    /**
     * ajaxList
     *
     * @param typeSearch
     * @param page
     * @param locale
     * @return ModelAndView
     * @author hand
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = "customerSearch") CustomerTypeSearchDto typeSearch,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {

        // Security for this page.
//		if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN)
//				&& !UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}

        ModelAndView mav = new ModelAndView(CUSTOMER_TYPE_TABLE);
        // set language code
        typeSearch.setLanguageCode(locale.toString());
        // Init PageWrapper
        PageWrapper<CustomerTypeLanguageSearchDto> pageWrapper = typeService.searchTypeLanguage(page, typeSearch);
        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);

        return mav;
    }

    /**
     * addOrEditCustomerType
     *
     * @param categoryId
     * @return ModelAndView
     * @author hand
     */
    @RequestMapping(value = UrlConst.EDIT, method = { RequestMethod.GET })
    public ModelAndView addOrEditCustomerType(@RequestParam(value = "id", required = false) Long typeId) {
        // Security for this page.
//		if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN)
//				&& !UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}

        ModelAndView mav = new ModelAndView(CUSTOMER_TYPE_EDIT);

        // Init master data
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        mav.addObject("languageList", languageList);

        CustomerTypeEditDto typeEdit = typeService.getCustomerTypeEditDto(typeId);

        String url = UrlConst.CUSTOMER_TYPE.concat(UrlConst.EDIT);
        if (null != typeId) {
            url = url.concat("?id=").concat(typeId.toString());
        }
        typeEdit.setUrl(url);

        mav.addObject("typeEdit", typeEdit);

        return mav;
    }

    @RequestMapping(value = UrlConst.EDIT, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView addOrEditCustomerTypePost(@Valid @ModelAttribute("typeEdit") CustomerTypeEditDto typeEdit,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView(CUSTOMER_TYPE_EDIT);
        // Security for this page.
//		if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN)
//				&& !UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}

        // Validate business
        customerTypeEditValidator.validate(typeEdit, bindingResult);

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale);
            messageList.add(msgError);

            // Init master data
            List<LanguageDto> languageList = languageService.getLanguageDtoList();
            mav.addObject("languageList", languageList);

            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("typeEdit", typeEdit);

            return mav;
        }

        // create or edit
        typeService.addOrEdit(typeEdit);

        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        // success redirect edit page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.CUSTOMER_TYPE).concat(UrlConst.EDIT);
        redirectAttributes.addAttribute("id", typeEdit.getId());
        mav.setViewName(viewName);

        return mav;
    }

    /**
     * delete CustomerType
     *
     * @param id
     * @param locale
     * @param redirectAttributes
     * @return ModelAndView
     * @author hand
     */
    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView delete(@RequestParam(value = "id", required = true) Long id, Locale locale,
            RedirectAttributes redirectAttributes) {
        // Security for this page.
//		if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN)
//				&& !UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}

        // delete CustomerType
        typeService.deleteById(id);

        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.CUSTOMER_TYPE).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);

        // Init message success list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        messageList.add(msgInfo);

        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        return mav;
    }

    /**
     * detail
     *
     * @param id
     * @return ModelAndView
     * @author hand
     */
    @RequestMapping(value = UrlConst.DETAIL, method = { RequestMethod.GET })
    public ModelAndView detail(@RequestParam(value = "id", required = true) Long id) {
        // Security for this page.
//		if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN)
//				&& !UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}

        ModelAndView mav = new ModelAndView(CUSTOMER_TYPE_DETAIL);

        // Init master data
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        mav.addObject("languageList", languageList);

        CustomerTypeEditDto typeDetail = typeService.getCustomerTypeEditDto(id);

        String url = UrlConst.CUSTOMER_TYPE.concat(UrlConst.DETAIL);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        typeDetail.setUrl(url);

        mav.addObject("typeDetail", typeDetail);

        return mav;
    }
}
