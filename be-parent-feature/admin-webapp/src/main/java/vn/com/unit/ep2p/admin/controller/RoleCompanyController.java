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
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.dto.JcaRoleForCompanyDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.RoleEditDto;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.RoleForCompanyService;
import vn.com.unit.ep2p.admin.service.RoleService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.dto.RoleForCompanyEditDto;
import vn.com.unit.common.dto.Select2Dto;

/**
 * RoleCompanyController
 * 
 * @version 01-00
 * @since 01-00
 * @author DaiTrieu
 */
@RequestMapping("/role-company")
@Controller
public class RoleCompanyController {

	@Autowired 
	RoleForCompanyService roleForCompanyService;
	
	@Autowired
    private CompanyService companyService;
	
	@Autowired
    private RoleService roleService;
	
	@Autowired
	private MessageSource msg;
	
	@Autowired
	private SystemConfig systemConfig;
	
	private final static String ROLE_COMPANY = RoleConstant.ROLE_COMPANY;
	
    @RequestMapping(value = UrlConst.LIST, method = RequestMethod.GET)
    public ModelAndView getCreate(Model model) {
    	// Security for this page.
    	if (!UserProfileUtils.hasRole(ROLE_COMPANY) &&
    		!UserProfileUtils.hasRole(ROLE_COMPANY.concat(ConstantCore.COLON_DISP)) &&
    		!UserProfileUtils.hasRole(ROLE_COMPANY.concat(ConstantCore.COLON_EDIT))) {
    		return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW); }
	
        //List<TeamDto> teams = roleTeamService.getListTeam(UserProfileUtils.getCompanyId());
        ModelAndView mav = new ModelAndView("/views/role-company/role-company-list.html");
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
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
        ModelAndView mav = new ModelAndView("/views/role-company/irole-company-list.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_COMPANY) &&
     		!UserProfileUtils.hasRole(ROLE_COMPANY.concat(ConstantCore.COLON_DISP)) &&
     		!UserProfileUtils.hasRole(ROLE_COMPANY.concat(ConstantCore.COLON_EDIT))) {
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
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale,
			Model model) {
		// Security for this page.
    	if (!UserProfileUtils.hasRole(ROLE_COMPANY) &&
        	!UserProfileUtils.hasRole(ROLE_COMPANY.concat(ConstantCore.COLON_DISP)) &&
        	!UserProfileUtils.hasRole(ROLE_COMPANY.concat(ConstantCore.COLON_EDIT))) {
        	return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW); }
		
		ModelAndView mav = new ModelAndView("/views/role-company/role-company-edit.html");
		List<JcaRoleForCompanyDto> list = new ArrayList<JcaRoleForCompanyDto>();
		if(null!=roleId)
			list = roleForCompanyService.getListRoleForCompany(roleId);
		mav.addObject("roleCompanys",list);
		mav.addObject("roleId",roleId);
		// Add company list
		
		// If redirect page
        Map<String, Object> md = model.asMap();
        MessageList messageList = (MessageList) md.get(ConstantCore.MSG_LIST);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        
		List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        mav.addObject("companyId", UserProfileUtils.getCompanyId());
        // Init PageWrapper
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
//        PageWrapper<RoleForCompanyDto> pageWrapper = roleForCompanyService.getListRoleForCompanyPageWrapper(page, pageSize, roleId);
        PageWrapper<JcaRoleForCompanyDto> pageWrapper = roleForCompanyService.getListRoleForCompanyPageWrapper(page, pageSize, roleId);
        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
        mav.addObject("roleId", roleId);
		return mav;
	}
    
    @RequestMapping(value = "/post-list-company-for-role", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView ajaxListRoleForCompany(@RequestParam("roleCompanyJson") String roleCompanyJson, Locale locale, HttpServletRequest request,
	        @RequestParam(value = "roleId", required = true) Long roleId, RedirectAttributes redirectAttributes){
		// Security for this page.
    	if (!UserProfileUtils.hasRole(ROLE_COMPANY) &&
        	!UserProfileUtils.hasRole(ROLE_COMPANY.concat(ConstantCore.COLON_DISP)) &&
        	!UserProfileUtils.hasRole(ROLE_COMPANY.concat(ConstantCore.COLON_EDIT))) {
        	return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW); }
    	
		ModelAndView mav = new ModelAndView("/views/commons/message-alert.html");
		//exec
		MessageList messageList = new MessageList(Message.SUCCESS);
		RoleForCompanyEditDto roleCompany = new RoleForCompanyEditDto();
		Boolean rs = false;
		String msgStr = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
		try {
		    ObjectMapper mapper = new ObjectMapper();
	        roleCompany = mapper.readValue(roleCompanyJson, RoleForCompanyEditDto.class);
	        msgStr = roleForCompanyService.validateRoleForCompay(roleCompany, locale);
	        if(StringUtils.isBlank(msgStr)) {
	            rs = roleForCompanyService.saveRoleForCompany(roleCompany, locale);
	        }
        }catch (Exception e) {
            messageList = new MessageList(Message.ERROR);
            msgStr = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgStr);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            //return mav;
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
        
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        String viewName = UrlConst.REDIRECT.concat("/role-company/get-list-company-for-role");
        mav.setViewName(viewName);
        redirectAttributes.addAttribute("role_id", roleId);
		
		return mav;
	}
    //add new
    @RequestMapping(value = "/add-role-for-company", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addRoleForCompany(@RequestParam("roleCompanyJson") String roleCompanyJson, Locale locale, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_COMPANY) &&
            !UserProfileUtils.hasRole(ROLE_COMPANY.concat(ConstantCore.COLON_DISP)) &&
            !UserProfileUtils.hasRole(ROLE_COMPANY.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW); }
        
        ModelAndView mav = new ModelAndView("/views/role-company/role-company-edit.html");
        ObjectMapper mapper = new ObjectMapper();
        RoleForCompanyEditDto roleCompany = new RoleForCompanyEditDto();
        roleCompany = mapper.readValue(roleCompanyJson, RoleForCompanyEditDto.class);
        Long roleId = roleCompany.getRoleId();
        List<JcaRoleForCompanyDto> list = roleCompany.getData();
        //list.add(0, new RoleForCompanyDto(UserProfileUtils.getCompanyId()));
        
        mav.addObject("roleCompanys",list);
        mav.addObject("roleId",roleId);
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        return mav;   
    }
    //delete
    @RequestMapping(value = "/delete-role-for-company", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView deleteRoleForCompany(
            @RequestParam(value = "companyId", required = false) Long companyId, 
            @RequestParam(value = "roleId", required = true) Long roleId,
            @RequestParam(value = "orgId", required = false) Long orgId,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request, HttpServletResponse response, Locale locale) throws JsonParseException, JsonMappingException, IOException {
        ModelAndView mav = new ModelAndView("/views/role-company/role-company-edit.html");
        MessageList messageList = new MessageList();
        
        messageList.setStatus(Message.SUCCESS);
        messageList.add(msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale));
        
        /*ObjectMapper mapper = new ObjectMapper();
        RoleForCompanyEditDto roleCompany = new RoleForCompanyEditDto();
        roleCompany = mapper.readValue(roleCompanyJson, RoleForCompanyEditDto.class);
        Long roleId = roleCompany.getRoleId();
        List<RoleForCompanyDto> list = roleCompany.getData();
        
        RoleForCompanyDto objDelete = list.get(index);*/
        try {
            // Long id = objDelete.getId();
            if (companyId != null && orgId != null) {
                roleForCompanyService.deleteByCompanyIdAndOrgIdAndRoleId(companyId, orgId, roleId);
            }
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            messageList.add(msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale));
        }
        /*//list.remove(index);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject("roleId", roleId);*/
        
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        String viewName = UrlConst.REDIRECT.concat("/role-company/get-list-company-for-role");
        mav.setViewName(viewName);
        redirectAttributes.addAttribute("role_id", roleId);
        
        return mav;
    }
    
}
