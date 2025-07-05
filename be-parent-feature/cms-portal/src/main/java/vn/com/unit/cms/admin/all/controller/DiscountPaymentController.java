/*******************************************************************************
 * Class        ：DiscountPaymentController
 * Created date ：2017/06/14
 * Lasted date  ：2017/06/14
 * Author       ：thuydtn
 * Change log   ：2017/06/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.dto.DiscountPaymentTypeDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
import vn.com.unit.cms.admin.all.service.DiscountPaymentService;
import vn.com.unit.cms.admin.all.validator.DiscountPaymentTypeEditValidator;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
//import vn.com.unit.jcanary.constant.RoleConstant;
import vn.com.unit.ep2p.constant.UrlConst;

/**
 * DiscountPaymentController
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Controller
@RequestMapping(value = UrlConst.ROOT + UrlConst.DISCOUNT_PAYMENT)
public class DiscountPaymentController {

    @Autowired
    private DiscountPaymentService discountPaymentService;

    @Autowired
    private DiscountPaymentTypeEditValidator discountPaymentTypeEditValidator;

    @Autowired
    private MessageSource msg;

    @InitBinder
    public void databinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
    }

    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET })
    public ModelAndView getPaymentTypeList(@ModelAttribute(value = "typeSearch") CommonSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
//        if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN.concat(ConstantCore.COLON_DISP))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        PageWrapper<DiscountPaymentTypeDto> discountPaymentTypeListPageWrapper = discountPaymentService
                .searchDiscountPaymentTypeList(page, searchDto);

        ModelAndView mav = new ModelAndView("discount.paymenttype.list");
        mav.addObject("pageWrapper", discountPaymentTypeListPageWrapper);
        return mav;
    }

    @RequestMapping(value = "paymentitem/list", method = { RequestMethod.POST })
    public ModelAndView postPaymentTypeList(@ModelAttribute(value = "searchModel") CommonSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
//        if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN.concat(ConstantCore.COLON_DISP))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        PageWrapper<DiscountPaymentTypeDto> discountPaymentTypeListPageWrapper = discountPaymentService
                .searchDiscountPaymentTypeList(page, searchDto);

        ModelAndView mav = new ModelAndView("discount.paymenttype.list.table");
        mav.addObject("pageWrapper", discountPaymentTypeListPageWrapper);
        return mav;
    }

    @RequestMapping(value = "paymentitem/edit", method = { RequestMethod.GET })
    public ModelAndView getPaymentTypeEdit(@RequestParam(value = "id", required = false) Integer id, Locale locale) {
//        if (!UserProfileUtils.hasRole(RoleConstant.ROLE_ADMIN.concat(ConstantCore.COLON_DISP))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        DiscountPaymentTypeDto editModel = discountPaymentService.getDiscountPaymentTypeEditModel(id);
        String url = UrlConst.DISCOUNT_PAYMENT.concat("/paymentitem").concat(UrlConst.EDIT);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        editModel.setUrl(url);

        ModelAndView mav = new ModelAndView("discount.paymenttype.edit");
        mav.addObject("paymentTypeEditModel", editModel);
        return mav;
    }

    @RequestMapping(value = "paymentitem/edit", method = { RequestMethod.POST })
    public ModelAndView postPaymentTypeEdit(
            @Valid @ModelAttribute(value = "discountPaymentTypeModel") DiscountPaymentTypeDto discountPaymentTypeDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes) {
//        if (!UserProfileUtils.hasRole(RoleConstant.ROLE_ADMIN.concat(ConstantCore.COLON_DISP))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        ModelAndView mav = new ModelAndView("discount.paymenttype.edit");
        discountPaymentTypeEditValidator.validate(discountPaymentTypeDto, bindingResult);

        MessageList messageList = new MessageList(Message.SUCCESS);

//        if(!bindingResult.hasErrors()){
//            discountPaymentTypeEditValidator.validate(discountPaymentTypeDto, bindingResult);
//        }
        if (bindingResult.hasErrors()) {
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale);
            messageList.add(msgError);

            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("paymentTypeEditModel", discountPaymentTypeDto);

            return mav;
        }
        DiscountPaymentTypeDto editModel = discountPaymentService.saveUpdatePaymentType(discountPaymentTypeDto);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.DISCOUNT_PAYMENT)
                .concat(UrlConst.EDIT);
        redirectAttributes.addAttribute("id", editModel.getId());
        mav.setViewName(viewName);
        mav.addObject("paymentTypeEditModel", editModel);
        return mav;
    }

    @RequestMapping(value = "paymentitem/add", method = { RequestMethod.GET })
    public ModelAndView getPaymentTypeAdd(Locale locale) {
//        if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN.concat(ConstantCore.COLON_DISP))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        DiscountPaymentTypeDto editModel = discountPaymentService.initPaymentTypeAddModel();
        ModelAndView mav = new ModelAndView("discount.paymenttype.edit");
        mav.addObject("paymentTypeEditModel", editModel);
        return mav;
    }

    @RequestMapping(value = "paymentitem/add", method = { RequestMethod.POST })
    public ModelAndView postPaymentTypeAdd(
            @Valid @ModelAttribute(value = "discountPaymentTypeModel") DiscountPaymentTypeDto discountPaymentTypeDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes) {
//        if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_ADMIN.concat(ConstantCore.COLON_DISP))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        ModelAndView mav = new ModelAndView("discount.paymenttype.edit");
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        MessageList messageList = new MessageList(Message.SUCCESS);
        if (!bindingResult.hasErrors()) {
            discountPaymentTypeEditValidator.validate(discountPaymentTypeDto, bindingResult);
        }
        if (bindingResult.hasErrors()) {
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale);
            messageList.add(msgError);

            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("paymentTypeEditModel", discountPaymentTypeDto);

            return mav;
        }
        DiscountPaymentTypeDto editModel = discountPaymentService.saveAddPaymentType(discountPaymentTypeDto);

        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.DISCOUNT_PAYMENT)
                .concat("/paymentitem").concat(UrlConst.EDIT);
        redirectAttributes.addAttribute("id", editModel.getId());

        mav.setViewName(viewName);
//        mav.addObject("paymentTypeEditModel", editModel);
        return mav;
    }
}
