/*******************************************************************************
 * Class        AuthorityProcessController
 * Created date 2019/07/01
 * Lasted date  2019/07/01
 * Author       KhuongTH
 * Change log   2019/07/01 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.admin.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.JcaAuthorityListDto;
import vn.com.unit.ep2p.admin.dto.RoleEditDto;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.RoleService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.process.workflow.service.AppBusinessService;
import vn.com.unit.process.workflow.service.AppProccessAuthorityService;
import vn.com.unit.process.workflow.service.AppProcessDeployService;

@Controller
@RequestMapping(UrlConst.AUTHORITY_PROCESS)
public class AuthorityProcessController {

    /** AuthorityService */
    @Autowired
    private AppProccessAuthorityService proccessAuthorityService;

    /** MessageSource */
    @Autowired
    private MessageSource msg;

    /** RoleService */
    @Autowired
    private RoleService roleService;

    /** systemLogsService */
    @Autowired
    private SystemLogsService systemLogsService;

    /** SCREEN_FUNCTION_CODE */
    private static final String SCREEN_FUNCTION_CODE = RoleConstant.AUTHORITY;

    /** JpmBusinessService */
    @Autowired
    private AppBusinessService jpmBusinessService;

    /** JpmProcessService */
    @Autowired
    private AppProcessDeployService appProcessDeployService;

    // @Autowired
    // private JpmIdentityService jpmIdentityService;

    @Autowired
    private CompanyService companyService;

    /**
     * Screen list authority
     * 
     * @return ModelAndView
     * @author KhuongTH
     */
    @GetMapping(value = UrlConst.LIST)
    public ModelAndView getList(HttpServletRequest request, Locale locale) {
        ModelAndView mav = new ModelAndView("/views/authority-process/authority-process-list.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);

        Long companyId = UserProfileUtils.getCompanyId();
        mav.addObject("companyId", companyId);

        return mav;
    }

    /**
     * 
     * getListAuthority
     * 
     * @param request
     * @param locale
     * @param companyId
     * @return
     * @author KhuongTH
     */
    @GetMapping(value = UrlConst.AJAX_LIST)
    public ModelAndView getListAuthority(HttpServletRequest request, Locale locale,
            @RequestParam(value = "companyId", required = true) Long companyId) {
        ModelAndView mav = new ModelAndView("/views/authority/iauthority-list.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        List<RoleEditDto> roleList = roleService.findRoleListByCompanyId(companyId);

        Long roleIdSelected = null;
        if (!roleList.isEmpty()) {
            RoleEditDto roleFirst = roleList.get(0);
            roleIdSelected = roleFirst.getId();
        }

        mav.addObject("roleList", roleList);
        mav.addObject("roleIdSelected", roleIdSelected);
        return mav;
    }

    /**
     * Screen edit authority
     * 
     * @return ModelAndView
     * @author KhuongTH
     */
    @GetMapping(value = UrlConst.AJAX_EDIT)
    @ResponseBody
    public ModelAndView getAjaxEdit(@RequestParam(value = "roleId", required = false) Long roleId,
            @RequestParam(value = "businessId", required = false) Long businessId,
            @RequestParam(value = "processId", required = false) Long processId,
            @RequestParam(value = "companyId", required = false) Long companyId, Locale locale) {

        ModelAndView mav = new ModelAndView("/views/authority-process/iauthority-process-edit.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // Long companyId = null;
        // if (null != roleId) {
        // RoleEditDto role = roleService.findRoleEditDtoById(roleId);
        // if (null != role) {
        // companyId = role.getCompanyId();
        // }
        // }

        List<Select2Dto> listBusiness = jpmBusinessService.getSelect2DtoListHasAuthorityByCompanyId(companyId);
        List<JcaAuthorityDto> authorityDtos = new ArrayList<>();
        List<Select2Dto> listProcess = new ArrayList<>();
        if (CommonCollectionUtil.isNotEmpty(listBusiness)) {
            if (null == businessId) {
                businessId = Long.parseLong(listBusiness.get(0).getId());
            }

            List<JcaAuthorityDto> serviceList = proccessAuthorityService.getServiceAuthorityByBusinessIdAndRoleId(businessId, roleId);
            mav.addObject("serviceList", serviceList);

            listProcess = appProcessDeployService.getJpmProcessDtoTypeSelect2DtoByBusinessId(businessId, locale.getLanguage());
            if (CommonCollectionUtil.isNotEmpty(listProcess)) {
                if (null == processId) {
                    processId = Long.parseLong(listProcess.get(0).getId());
                }

                authorityDtos = proccessAuthorityService.getAuthorityDtosByProcessDeployIdAndRoleId(processId, roleId);

                mav.addObject("authorityList", authorityDtos);
                mav.addObject("processList", listProcess);
                mav.addObject("processId", processId);
            }
            mav.addObject("businessId", businessId);
        }

        mav.addObject("roleIdSelected", roleId);
        mav.addObject("businessList", listBusiness);
        // mav.addObject("types", businessDefinitionService.findBusinessDefinitionListByCompanyAdminAndCompanyId(false, companyId));
        return mav;
    }

    /**
     * Screen edit authority
     * 
     * @return String
     * @author KhuongTH
     */
    @PostMapping(value = UrlConst.AJAX_EDIT)
    @ResponseBody
    public ModelAndView postAjaxEdit(@RequestBody JcaAuthorityListDto authorityListDto, Locale locale, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/views/commons/message-alert.html");
        // Write system logs
        systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Add Function to Role", "Save Add Function to Role", request);
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgStr = msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);

        try {
            proccessAuthorityService.saveAuthorityProcess(authorityListDto);
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            msgStr = e.getMessage();
        }

        messageList.add(msgStr);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        return mav;
    }

    /**
     * 
     * getAjaxListRole
     * 
     * @param roleId
     * @param businessId
     * @param processId
     * @return
     * @author KhuongTH
     */
    @GetMapping(value = UrlConst.ROLE_AJAXLIST)
    @ResponseBody
    public ModelAndView getAjaxListRole(@RequestParam(value = "roleId", required = true) Long roleId,
            @RequestParam(value = "processId", required = false) Long processId,
            @RequestParam(value = "companyId", required = false) Long companyId) {

        ModelAndView mav = new ModelAndView("/views/authority-process/iauthority-process-list-by-role.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        /*
         * Long companyId = null; if (null != roleId) { RoleEditDto role = roleService.findRoleEditDtoById(roleId); if (null != role) {
         * companyId = role.getCompanyId(); } }
         */

        List<JcaAuthorityDto> authorityDtos = proccessAuthorityService.getAuthorityDtosByProcessDeployIdAndRoleId(processId, roleId);

        mav.addObject("authorityList", authorityDtos);

        return mav;
    }
}
