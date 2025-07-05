/*******************************************************************************
 * Class        AuthorityController
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.entity.JcaAuthority;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.JcaAuthorityListDto;
import vn.com.unit.ep2p.admin.dto.RoleEditDto;
import vn.com.unit.ep2p.admin.service.AppAuthorityService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.RoleService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.constant.UrlConst;

/**
 * AuthorityController
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Controller
@RequestMapping(UrlConst.AUTHORITY)
public class AuthorityController {
    
    /** AuthorityService */
    @Autowired
    private AppAuthorityService authorityService;
    
    /** RoleService */
    @Autowired
    private RoleService roleService;
//    
//    /** StatusAuthorityService */
//    @Autowired
//    private StatusAuthorityService statusAuthorityService;
//    
//    /** ProcessService */
//    @Autowired
//    private ProcessService processService;
    
    @Autowired
    private CompanyService companyService;
	
	/** MessageSource */
    @Autowired
    private MessageSource msg;
    
    @Autowired
	JcaConstantService jcaConstantService;
    
    /** systemLogsService */
    @Autowired
    private SystemLogsService systemLogsService;
    
    
    /** SCREEN_FUNCTION_CODE */
    private static final String SCREEN_FUNCTION_CODE = RoleConstant.AUTHORITY;
    private static final List<String> FUNCTION_TYPE_LIST = Arrays.asList("1", "2");
    /**
     * Screen list authority (Init)
     * @return ModelAndView
     * @author KhoaNA
     */
    @GetMapping(value = UrlConst.LIST)
    public ModelAndView getList(HttpServletRequest request, Locale locale) {
        ModelAndView mav = new ModelAndView("/views/authority/authority-list.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        Long companyId = UserProfileUtils.getCompanyId();
        mav.addObject("companyId", companyId);
        
        return mav;
    }
    
    /**
     * List Authority (Ajax)
     * @return ModelAndView
     * @author trieuvd
     */
    @GetMapping(value = UrlConst.AJAX_LIST)
    public ModelAndView getListAuthority(HttpServletRequest request, Locale locale,
    		@RequestParam(value = "companyId", required = false) Long companyId) {
        ModelAndView mav = new ModelAndView("/views/authority/iauthority-list.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        List<RoleEditDto> roleList = roleService.findRoleListByCompanyId(companyId);
        
        Long roleIdSelected = null;
        if( roleList.isEmpty() ) {
            MessageList messageList = new MessageList(Message.WARNING);
            String content = msg.getMessage("message.authority.not.data", null, locale);
            messageList.add(content);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        } else {
            RoleEditDto roleFirst = roleList.get(0);
            roleIdSelected = roleFirst.getId();
        }
        
        mav.addObject("roleList", roleList);
        mav.addObject("roleIdSelected", roleIdSelected);
        return mav;
    }
    
    /**
     * Screen edit authority (Ajax-Init)
     * @return String
     * @author KhoaNA
     */
    @GetMapping(value = UrlConst.AJAX_EDIT)
    @ResponseBody
    public ModelAndView getAjaxEdit(@RequestParam(value = "roleId", required = false) Long roleId) {
        ModelAndView mav = new ModelAndView("/views/authority/iauthority-edit.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        List<JcaAuthorityDto> authorityDtoList = new ArrayList<>();

        Long companyId = null;
        if (null != roleId) {
            RoleEditDto role = roleService.findRoleEditDtoById(roleId);
            if (null != role) {
                companyId = role.getCompanyId();
                authorityDtoList = authorityService.findAuthorityDtoListByRoleIdAndFunctionType(roleId, FUNCTION_TYPE_LIST, companyId);
            }
        }

        mav.addObject("roleIdSelected", roleId);
        mav.addObject("authorityList", authorityDtoList);
        return mav;
    }
    
    /**
     * Screen edit authority (Ajax-Edit)
     * @return String
     * @author KhoaNA
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
        messageList.add(msgStr);
        
        // Get list authority
        List<JcaAuthorityDto> authorityDtoList = authorityListDto.getData();
        // Convert list authorityDto to list authority
        List<JcaAuthority> authorityList = authorityService.authorityDtoListToAuthorityList(authorityDtoList);
        // Save list authority
        Long roleId = authorityListDto.getRoleId();
        authorityService.saveAuthority(authorityList, roleId);
        
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        return mav;
    }
    
    /**
     * Screen edit status authority (Ajax-Init)
     * @return ModelAndView
     * @author KhoaNA
     */
//    @RequestMapping(value = UrlConst.AJAX_EDIT_STATUS_AUTHORITY, method = RequestMethod.GET)
//    @ResponseBody
//    public ModelAndView getStatusAuthority(@RequestParam(value = "roleId", required = true) Long roleId,
//            @RequestParam(value = "itemId", required = true) Long itemId) {
//        ModelAndView mav = new ModelAndView("/views/authority/iauthority-status.html");
//        // Security for this page.
//        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        List<JpmProcess> processList = processService.findProcessListByItemId(itemId);
//        
//        List<StatusAuthorityDto> statusAuthorityDtoList = null;
//        Long processId = null;
//        if( !processList.isEmpty() ) {
//            Process process = processList.get(0);
//            
//            processId = process.getId();
//            statusAuthorityDtoList = statusAuthorityService.findStatusAuthorityDtoListByProcessIdAndRoleIdAndItemId(processId, roleId, itemId);
//        }
//        
//        mav.addObject("roleId", roleId);
//        mav.addObject("itemId", itemId);
//        mav.addObject("processId", processId);
//        mav.addObject("processList", processList);
//        mav.addObject("statusAuthorityDtoList", statusAuthorityDtoList);
//        
//        return mav;
//    }
    
//    /**
//     * Screen edit status authority (Ajax-Change)
//     * @return ModelAndView
//     * @author KhoaNA
//     */
//    @RequestMapping(value = UrlConst.AJAX_STATUS_AUTHORITY_PROCESS, method = RequestMethod.GET)
//    @ResponseBody
//    public ModelAndView getStatusAuthorityByProcessCode(@RequestParam(value = "roleId", required = true) Long roleId,
//            @RequestParam(value = "itemId", required = true) Long itemId,
//            @RequestParam(value = "processId", required = true) Long processId) {
//        ModelAndView mav = new ModelAndView("/views/authority/iauthority-status-table.html");
//        // Security for this page.
//        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        List<StatusAuthorityDto> statusAuthorityDtoList = statusAuthorityService.findStatusAuthorityDtoListByProcessIdAndRoleIdAndItemId(processId, roleId, itemId);
//        
//        mav.addObject("roleId", roleId);
//        mav.addObject("itemId", itemId);
//        mav.addObject("processId", processId);
//        mav.addObject("statusAuthorityDtoList", statusAuthorityDtoList);
//        return mav;
//    }
    
    /**
     * Ajax save StatusAuthority
     * @return String
     * @author KhoaNA
     */
//    @RequestMapping(value = UrlConst.AJAX_EDIT_STATUS_AUTHORITY, method = RequestMethod.POST)
//    @ResponseBody
//    public ModelAndView postSaveStatusAuthority(@ModelAttribute(value = "statusAuthorityListDto") StatusAuthorityListDto statusAuthorityListDto, Locale locale) {
//        ModelAndView mav = new ModelAndView("/views/commons/message-alert.html");
//        // Security for this page.
//        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        MessageList messageList = new MessageList(Message.SUCCESS);
//        String msgStr = msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);
//        messageList.add(msgStr);
//        
//        // Get list statusAuthority
//        List<StatusAuthorityDto> authDtoLst = statusAuthorityListDto.getData();
//        // Create StatusAuthority List
//        Long roleId = statusAuthorityListDto.getRoleId();
//        Long itemId = statusAuthorityListDto.getItemId();
//        Long processId = statusAuthorityListDto.getProcessId();
//        List<StatusAuthority> authList = statusAuthorityService.statusAuthorityDtoListToStatusAuthorityList(authDtoLst, roleId, itemId);
//        // Save list authority
//        statusAuthorityService.saveStatusAuthority(authList, roleId, itemId, processId);
//        
//        mav.addObject(ConstantCore.MSG_LIST, messageList);
//        return mav;
//    }
    
    @GetMapping(value = UrlConst.AUTHORITY_LIST)
    public ModelAndView getListAuthority(HttpServletRequest request, Locale locale) {
        ModelAndView mav = new ModelAndView("/views/authority/authority-list.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
//        List<RoleEditDto> roleList = roleService.findRoleListByRoleType(null, UserProfileUtils.getCompanyId(), UserProfileUtils.isCompanyAdmin());
//        
//        Long roleIdSelected = null;
//        if( roleList.isEmpty() ) {
//            MessageList messageList = new MessageList(Message.WARNING);
//            String content = msg.getMessage("message.warning.authority.not.data", null, locale);
//            messageList.add(content);
//            mav.addObject(ConstantCore.MSG_LIST, messageList);
//        } else {
//            RoleEditDto roleFirst = roleList.get(0);
//            roleIdSelected = roleFirst.getId();
//        }
//        
//        mav.addObject("roleList", roleList);
//        mav.addObject("roleIdSelected", roleIdSelected);
        return mav;
    }
    
    @GetMapping(value = UrlConst.AUTHORITY_AJAX_EDIT)
    @ResponseBody
    public ModelAndView getAjaxEditAuthority(@RequestParam(value = "roleId", required = true) Long roleId) {
        ModelAndView mav = new ModelAndView("/views/authority/iauthority-edit.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        RoleEditDto role = roleService.findRoleEditDtoById(roleId);
        List<JcaAuthorityDto> authorityDtoList = authorityService.findAuthorityListByRoleIdAndFunctionType(roleId, role.getRoleType());

        mav.addObject("roleIdSelected", roleId);
        mav.addObject("authorityList", authorityDtoList);
        mav.addObject("types", jcaConstantService.getListJcaConstantDtoByKind("FUNCTION_TYPE", UserProfileUtils.getLanguage()));
        return mav;
    }
    
    @PostMapping(value = UrlConst.AUTHORITY_AJAX_EDIT)
    @ResponseBody
    public ModelAndView postAjaxEditAuthority(@RequestBody JcaAuthorityListDto authorityListDto, Locale locale, HttpServletRequest request) {
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
        messageList.add(msgStr);
        
        // Get list authority
        List<JcaAuthorityDto> authorityDtoList = authorityListDto.getData();
        // Convert list authorityDto to list authority
        List<JcaAuthority> authorityList = authorityService.authorityDtoListToAuthorityList(authorityDtoList);
        // Save list authority
        Long roleId = authorityListDto.getRoleId();
        authorityService.saveAuthorityWithRoleType(authorityList, roleId);
        
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        return mav;
    }
}
