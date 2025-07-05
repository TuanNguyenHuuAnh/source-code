/*******************************************************************************
 * Class        RoleCompanyController
 * Created date 2019/07/03
 * Author       trieuvd
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.RoleEditDto;
import vn.com.unit.ep2p.admin.dto.RoleForDisplayEmailDto;
import vn.com.unit.ep2p.admin.dto.RoleForDisplayEmailEditDto;
//import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.RoleForDisplayEmailService;
import vn.com.unit.ep2p.admin.service.RoleService;
import vn.com.unit.ep2p.constant.UrlConst;

/**
 * RoleCompanyController
 * 
 * @version 01-00
 * @since 01-00
 * @author DaiTrieu
 */
@RequestMapping("/role-display-email")
@Controller
public class RoleDisplayEmailController {

	@Autowired 
	RoleForDisplayEmailService roleForDisplayEmailService;
	
//	@Autowired
//    private CompanyService companyService;
	
	@Autowired
    private RoleService roleService;
	
	@Autowired
	private MessageSource msg;
	
	private static final String SCREEN_FUNCTION_CODE = RoleConstant.ROLE_FOR_DISPLAY_EMAIL;
	private static final String MAV_LIST = "/views/role-display-email/role-display-email-list.html";
	private static final String MAV_ROLE_LIST = "/views/role-display-email/irole-display-email-list.html";
	private static final String MAV_EDIT_LIST = "/views/role-display-email/role-display-email-edit.html";
	private static final String MAV_MESSAGE = "/views/commons/message-alert.html";
	
    @RequestMapping(value = UrlConst.LIST, method = RequestMethod.GET)
    public ModelAndView getCreate(Model model) {
    	// Security for this page.
    	if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) &&
    		!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP)) &&
    		!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
    		return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW); }
	
        ModelAndView mav = new ModelAndView(MAV_LIST);
        // Add company list
//        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
//        mav.addObject("companyList", companyList);
        
        Long companyId = UserProfileUtils.getCompanyId();
        mav.addObject("companyId", companyId);

        return mav;
    }
    
    /**
     * List Role (Ajax)
     * @return ModelAndView
     * @author trieuvd
     */
    @RequestMapping(value = UrlConst.AJAX_LIST, method = RequestMethod.GET)
    public ModelAndView getListRole(HttpServletRequest request, Locale locale,
    		@RequestParam(value = "companyId", required = true) Long companyId) {
        ModelAndView mav = new ModelAndView(MAV_ROLE_LIST);
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) &&
     		!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP)) &&
     		!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
     		return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW); }
        
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
    
    @RequestMapping(value = "/get-list-company-for-role", method = RequestMethod.GET)
	public ModelAndView getListCompanyRole(
			@RequestParam(value = "role_id", required = true) Long roleId,
			Model model) {
		// Security for this page.
    	if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) &&
        	!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP)) &&
        	!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
        	return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW); }
		
		ModelAndView mav = new ModelAndView(MAV_EDIT_LIST);
		List<RoleForDisplayEmailDto> list = new ArrayList<RoleForDisplayEmailDto>();
		if(null!=roleId) {
		    list = roleForDisplayEmailService.getListRoleForDisplayEmail(roleId);
		}
		mav.addObject("roleCompanys", list);
		mav.addObject("roleId", roleId);
		// Add company list
//		List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
//        mav.addObject("companyList", companyList);
        
		return mav;
	}
    
    @RequestMapping(value = "/post-list-company-for-role", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView ajaxListRoleForCompany(@RequestParam("roleCompanyJson") String roleCompanyJson, Locale locale, HttpServletRequest request){
		// Security for this page.
    	if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) &&
        	!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP)) &&
        	!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
        	return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW); }
    	
		ModelAndView mav = new ModelAndView(MAV_MESSAGE);
		//exec
		MessageList messageList = new MessageList(Message.SUCCESS);
		RoleForDisplayEmailEditDto roleDisplayEmail = new RoleForDisplayEmailEditDto();
		Boolean rs = false;
		String msgStr = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);;
		try {
		    ObjectMapper mapper = new ObjectMapper();
		    roleDisplayEmail = mapper.readValue(roleCompanyJson, RoleForDisplayEmailEditDto.class);
	        msgStr = roleForDisplayEmailService.validateRoleForDisplayEmail(roleDisplayEmail, locale);
	        if(StringUtils.isBlank(msgStr)) {
	            rs = roleForDisplayEmailService.saveRoleForDisplayEmail(roleDisplayEmail, locale);
	        }
        }catch (Exception e) {
            messageList = new MessageList(Message.ERROR);
            msgStr = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgStr);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            return mav;
        }
		if(rs){
			messageList = new MessageList(Message.SUCCESS);
			msgStr = msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);
	        messageList.add(msgStr);	
			mav.addObject(ConstantCore.MSG_LIST, messageList);		
		}
		else{
			messageList = new MessageList(Message.ERROR);
			if(StringUtils.isBlank(msgStr)) {
			    msgStr = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
			}
	        messageList.add(msgStr);
			mav.addObject(ConstantCore.MSG_LIST, messageList);
		}      
        
		return mav;
	}
    //add new
    @RequestMapping(value = "/add-role-for-display-email", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addRoleForCompany(@RequestParam("roleCompanyJson") String roleCompanyJson, Locale locale, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) &&
            !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP)) &&
            !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW); }
        
        ModelAndView mav = new ModelAndView(MAV_EDIT_LIST);
        ObjectMapper mapper = new ObjectMapper();
        RoleForDisplayEmailEditDto roleDisplayEmail = new RoleForDisplayEmailEditDto();
        roleDisplayEmail = mapper.readValue(roleCompanyJson, RoleForDisplayEmailEditDto.class);
        Long roleId = roleDisplayEmail.getRoleId();
        List<RoleForDisplayEmailDto> list = roleDisplayEmail.getData();
        list.add(0, new RoleForDisplayEmailDto(UserProfileUtils.getCompanyId()));
        
        mav.addObject("roleCompanys",list);
        mav.addObject("roleId",roleId);
        // Add company list
//        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
//        mav.addObject("companyList", companyList);
        
        return mav;   
    }
    //delete
    @RequestMapping(value = "/delete-role-for-display-email", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView deleteRoleForCompany(@RequestParam("roleCompanyJson") String roleCompanyJson, @RequestParam(value = "index", required = true) int index,
            HttpServletRequest request, HttpServletResponse response, Locale locale) throws JsonParseException, JsonMappingException, IOException {
        ModelAndView mav = new ModelAndView(MAV_EDIT_LIST);
        MessageList messageList = new MessageList();
        
        messageList.setStatus(Message.SUCCESS);
        messageList.add(msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale));
        
        ObjectMapper mapper = new ObjectMapper();
        RoleForDisplayEmailEditDto roleDisplayEmail = new RoleForDisplayEmailEditDto();
        roleDisplayEmail = mapper.readValue(roleCompanyJson, RoleForDisplayEmailEditDto.class);
        Long roleId = roleDisplayEmail.getRoleId();
        List<RoleForDisplayEmailDto> list = roleDisplayEmail.getData();
        
        RoleForDisplayEmailDto objDelete = list.get(index);
        try {
            Long id = objDelete.getId();
            if (id != null) {
                roleForDisplayEmailService.deleteById(id);
            }
        }catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            messageList.add(msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale));
        }
        list.remove(index);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject("roleCompanys",list);
        mav.addObject("roleId",roleId);
        // Add company list
//        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
//        mav.addObject("companyList", companyList);
        
        return mav;
    }
    
}
