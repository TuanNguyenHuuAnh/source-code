/*******************************************************************************
 * Class        ：RuleSettingController
 * Created date ：2021/03/10
 * Lasted date  ：2021/03/10
 * Author       ：tantm
 * Change log   ：2021/03/10：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.process.admin.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.Select2Dto;
//import vn.com.unit.core.dto.JcaRuleSettingDto;
import vn.com.unit.core.security.UserProfileUtils;
//import vn.com.unit.core.service.JcaRuleSettingService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.RuleSettingDto;
//import vn.com.unit.ep2p.admin.enumdef.RuleTypeEnum;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.process.workflow.service.AppBusinessService;

/**
 * RuleSettingController
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Controller
@RequestMapping(UrlConst.RULE)
public class ProcessRuleSettingController {

    @Autowired
    private CompanyService companyService;

//    @Autowired
//    private MessageSource messageSource;

    @Autowired
    private AppBusinessService appBusinessService;

//    @Autowired
//    private JcaRuleSettingService jcaRuleSettingService;

    public static final String SCREEN_FUNCTION_CODE = RoleConstant.RULE;
    public static final String RULE = "/views/rule/rule-edit.html";
    public static final String RULE_ORG = "/views/rule/rule-org.html";
    public static final String RULE_ORG_DETAIL = "/views/rule/rule-org-detail.html";

    private static final String BUSINESS_LIST = "businessList";
    private static final String RULE_DTO = "ruleDto";
//    private static final String LIST_RULE_SETTING_DTO = "listRuleSettingDto";
//    private static final String LIST_RULE_TYPE = "listRuleType";
//    private static final String MESSAGE_ORG = "messageOrgForAccount";

    @GetMapping(value = UrlConst.EDIT)
    public ModelAndView getEdit(Model model, Locale locale) {
        ModelAndView mav = new ModelAndView(RULE);
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        RuleSettingDto ruleDto = new RuleSettingDto();
        mav.addObject(RULE_DTO, ruleDto);

        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);

        // If redirect page
        Map<String, Object> md = model.asMap();
        MessageList messageList = (MessageList) md.get(ConstantCore.MSG_LIST);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        Long companyId = UserProfileUtils.getCompanyId();
        List<Select2Dto> businessList = appBusinessService.getSelect2DtoListCompanyId(companyId);
        mav.addObject("companyId", companyId);
        mav.addObject(BUSINESS_LIST, businessList);

        return mav;
    }

//    @PostMapping(value = "/build-rule-org")
//    @ResponseBody
//    public ModelAndView buildRule(@RequestParam(value = "businessId", required = true) long businessId, HttpServletRequest request,
//            HttpServletResponse response) {
//        // Security for this page.
//        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        ModelAndView mav = new ModelAndView(RULE_ORG);
//
//        List<JcaRuleSettingDto> listRuleSettingDto = jcaRuleSettingService.getListJcaRuleSettingDtoByBusinessId(businessId);
//        mav.addObject(LIST_RULE_SETTING_DTO, listRuleSettingDto);
//
//        List<Select2Dto> listRuleType = RuleTypeEnum.getSelect2Dtos();
//        mav.addObject(LIST_RULE_TYPE, listRuleType);
//
//        return mav;
//    }
//
//    @PostMapping(value = "/add-rule-org")
//    @ResponseBody
//    public ModelAndView addRule(@RequestParam(value = "businessId", required = true) long businessId, HttpServletRequest request,
//            HttpServletResponse response) {
//        // Security for this page.
//        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        ModelAndView mav = new ModelAndView(RULE_ORG);
//        // Find list dep for account
//        List<JcaRuleSettingDto> listRuleSettingDto = jcaRuleSettingService.getListJcaRuleSettingDtoByBusinessId(businessId);
//
//        JcaRuleSettingDto jcaRuleSettingDto = new JcaRuleSettingDto();
//        jcaRuleSettingDto.setBusinessId(businessId);
//        listRuleSettingDto.add(0, jcaRuleSettingDto);
//        mav.addObject(LIST_RULE_SETTING_DTO, listRuleSettingDto);
//
//        List<Select2Dto> listRuleType = RuleTypeEnum.getSelect2Dtos();
//        mav.addObject(LIST_RULE_TYPE, listRuleType);
//
//        return mav;
//    }
//
//    @PostMapping(value = "/delete-rule-org")
//    @ResponseBody
//    public ModelAndView deleteRule(@ModelAttribute(value = RULE_DTO) RuleSettingDto ruleDto,
//            @RequestParam(value = "index", required = true) int index, HttpServletRequest request, HttpServletResponse response,
//            Locale locale) {
//        // Security for this page.
//        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        ModelAndView mav = new ModelAndView(RULE_ORG);
//
//        List<Select2Dto> listRuleType = RuleTypeEnum.getSelect2Dtos();
//        mav.addObject(LIST_RULE_TYPE, listRuleType);
//
//        String message = messageSource.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
//        List<JcaRuleSettingDto> listRuleSettingDto = ruleDto.getListRuleSettingDto();
//        JcaRuleSettingDto objDelete = listRuleSettingDto.get(index);
//        try {
//            if (null != objDelete.getOrgId()) {
//                jcaRuleSettingService.deleteJcaRuleSettingByPK(objDelete.getBusinessId(), objDelete.getOrgId(), objDelete.getPositionId());
//            }
//            listRuleSettingDto.remove(index);
//        } catch (Exception e) {
//            message = messageSource.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
//        }
//        mav.addObject(LIST_RULE_SETTING_DTO, listRuleSettingDto);
//        mav.addObject(MESSAGE_ORG, message);
//        return mav;
//    }
//
//    @PostMapping(value = "/update-rule-org")
//    @ResponseBody
//    public ModelAndView updateRule(@RequestParam(value = "businessId", required = true) long businessId,
//            @ModelAttribute(value = "ruleDto") RuleSettingDto ruleDto, HttpServletRequest request, HttpServletResponse response,
//            BindingResult bindingResult, Locale locale) {
//        // Security for this page.
//        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        ModelAndView mav = new ModelAndView(RULE_ORG);
//
//        List<Select2Dto> listRuleType = RuleTypeEnum.getSelect2Dtos();
//        mav.addObject(LIST_RULE_TYPE, listRuleType);
//
//        if (ruleDto != null && ruleDto.getListRuleSettingDto() != null) {
//
//            String messageError = jcaRuleSettingService.validateOrgList(ruleDto.getListRuleSettingDto(), locale);
//            if (messageError != null) {
//                mav.addObject(LIST_RULE_SETTING_DTO, ruleDto.getListRuleSettingDto());
//                mav.addObject(MESSAGE_ORG, messageError);
//                return mav;
//            }
//
//            try {
//                jcaRuleSettingService.saveListJcaRuleSettingDto(ruleDto.getListRuleSettingDto());
//            } catch (Exception e) {
//                e.printStackTrace();
//                mav.addObject(LIST_RULE_SETTING_DTO, ruleDto.getListRuleSettingDto());
//                mav.addObject(MESSAGE_ORG, e.getMessage());
//                return mav;
//            }
//            List<JcaRuleSettingDto> listRuleSettingDto = jcaRuleSettingService.getListJcaRuleSettingDtoByBusinessId(businessId);
//            mav.addObject(LIST_RULE_SETTING_DTO, listRuleSettingDto);
//            String messageSuccess = messageSource.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
//            mav.addObject(MESSAGE_ORG, messageSuccess);
//        }
//        return mav;
//    }

    @PostMapping(value = "/getListBuesiness")
    @ResponseBody
    public List<Select2Dto> getListBuesiness(@RequestParam(name = "companyId", required = false) Long companyId) {
        return appBusinessService.getSelect2DtoListCompanyId(companyId);
    }
}
