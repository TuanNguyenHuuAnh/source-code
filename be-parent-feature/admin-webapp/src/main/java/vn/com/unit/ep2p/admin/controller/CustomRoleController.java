/*******************************************************************************
 * Class        CustomRoleController
 * Created date 2018/01/31
 * Lasted date  2018/01/31
 * Author       Phucdq
 * Change log   2018/01/3101-00 Phucdq create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.MenuDto;
import vn.com.unit.ep2p.admin.dto.RoleCustomizableDto;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.CustomRoleService;
import vn.com.unit.ep2p.admin.service.RoleService;
import vn.com.unit.ep2p.constant.UrlConst;

/**
 * CustomRoleController
 * 
 * @version 01-00
 * @since 01-00
 * @author Phucdq
 */
@Controller
@RequestMapping(UrlConst.CUSTOM_ROLE)
public class CustomRoleController  {

    @Autowired
    RoleService roleService;

    @Autowired
    CustomRoleService roleCustomService;

    @Autowired
    MessageSource msg;
    
    @Autowired
	private CompanyService companyService;
    
    private static final String ROLE_CUSTOM_VIEW = "/views/role-custom/role-custom-list.html";
    private static final String MENU_TABLE = "/views/role-custom/role-custom-menu-table.html";
    /** SCREEN_FUNCTION_CODE */
    private static final String SCREEN_FUNCTION_CODE = RoleConstant.CUSTOM_ROLE;

    @RequestMapping(value = UrlConst.LIST, method = RequestMethod.GET)
    public ModelAndView getList(HttpServletRequest request, Locale locale) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(ROLE_CUSTOM_VIEW);
        List<RoleCustomizableDto> roleList = roleCustomService.getListRole();

        Long roleIdSelected = null;
        if (roleList.isEmpty()) {
            MessageList messageList = new MessageList(Message.WARNING);
            String content = msg.getMessage("message.warning.authority.not.data", null, locale);
            messageList.add(content);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        } else {
            RoleCustomizableDto roleFirst = roleList.get(0);
            roleIdSelected = roleFirst.getRoleId();
        }
        // Add company list
     	List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
//     	List<MenuDto> menuDtoList = roleCustomService.getListMenuByRoleID(roleIdSelected, UserProfileUtils.getCompanyId(), locale.getLanguage());
     	mav.addObject("companyList", companyList);
     	mav.addObject("companyId", UserProfileUtils.getCompanyId().toString());
     	//END
        mav.addObject("roleList", roleList);
     	mav.addObject("roleIdSelected", roleIdSelected);
        return mav;
    }
    
  /**
     * Load Ajax List
     * param companyId
     * **/
   @RequestMapping(value ="/get-company", method = RequestMethod.GET)
   @ResponseBody
    public ModelAndView getAjaxList(@RequestParam(value = "companyId", required = true) Long companyId, Locale locale) 
    {
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView("/views/role-custom/role-custom-list-company-role.html");
        List<RoleCustomizableDto> roleList = roleCustomService.getListByRole(companyId);
        Long roleIdSelected = null;
        if (!roleList.isEmpty()) {
            RoleCustomizableDto roleFirst = roleList.get(0);
            roleIdSelected = roleFirst.getRoleId();
        }
        mav.addObject("roleList", roleList);
        mav.addObject("roleIdSelected", roleIdSelected);
        return mav;
    }
    
    @RequestMapping(value = UrlConst.AJAX_LIST, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getAjaxEdit(@RequestParam(value = "roleId", required = true) Long roleId, Locale locale,
    								@RequestParam(value = "companyId", required = true) Long companyId
    		) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(MENU_TABLE);
        List<MenuDto> menuDtoList = roleCustomService.getListMenuByRoleID(roleId, companyId, locale.getLanguage());
        
        RoleCustomizableDto a = roleCustomService.findRoleCustomByRoleId(roleId);
        Long defaulMenuId = a.getMenuId() == null ? 0 : a.getMenuId();
        // Add company list
     	List<Select2Dto> companyList = companyService.getCompanyListByCompanyId(null, UserProfileUtils.getCompanyId(),
     				UserProfileUtils.isCompanyAdmin(), false);
     	mav.addObject("companyList", companyList);
     	//END company List
        mav.addObject("defaultMenuId", defaulMenuId);
        mav.addObject("roleIdSelected", roleId);
        mav.addObject("menuList", menuDtoList);
        return mav;
    }

    @RequestMapping(value = UrlConst.AJAX_EDIT, method = RequestMethod.POST)
    public ModelAndView updateRole(@RequestBody List<RoleCustomizableDto> roleModel, Locale locale) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView("/views/commons/message-alert.html");
        MessageList messageList = new MessageList();
        try {
            if (roleCustomService.updateRole(roleModel))
                messageList.add(Message.SUCCESS, msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale));
        } catch (Exception e) {
            messageList.add(Message.ERROR, e.getMessage());
        }
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        return mav;
    }
    
  
}
