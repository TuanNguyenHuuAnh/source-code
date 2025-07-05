/*******************************************************************************
 * Class        RoleTeamController
 * Created date 2017/09/12
 * Lasted date  2017/09/12
 * Author       phunghn
 * Change log   2017/09/1201-00 phunghn create a new
 ******************************************************************************/

package vn.com.unit.ep2p.admin.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.dto.JcaRoleForTeamDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.RoleTeamEditDto;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.RoleTeamService;
import vn.com.unit.ep2p.constant.UrlConst;
/**
 * RoleTeamController
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@RequestMapping(UrlConst.ROLE_TEAM)
@Controller
public class RoleTeamController {

	private static final String VIEW_ROLE_ADD = "/views/role-team/role-team-list.html";
	private static final String VIEW_ROLE_ADD_LIST = "/views/role-team/role-team-edit.html";

	@Autowired 
	RoleTeamService roleTeamService;
	
	@Autowired
    private CompanyService companyService;
	
	@Autowired
	private MessageSource msg;
	 
	private static final String ROLE_TEAM = RoleConstant.ROLE_TEAM;
	
    @GetMapping(value = UrlConst.ADD)
    public ModelAndView getCreate(Model model) {
        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_TEAM) && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
		List<JcaTeamDto> teams = roleTeamService.getListTeam(UserProfileUtils.getCompanyId());
		ModelAndView mav = new ModelAndView(VIEW_ROLE_ADD);

		mav.addObject("teams", teams);
		if (CommonCollectionUtil.isNotEmpty(teams)) {
			mav.addObject("roleIdSelected", teams.get(0).getTeamId());
		}

		// Add company list
		List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
				UserProfileUtils.isCompanyAdmin());
		mav.addObject("companyList", companyList);
        
        Long companyId = UserProfileUtils.getCompanyId();
        mav.addObject("companyId", companyId);

        return mav;
    }
	
    /**
     * get List RoleTeam (Ajax)
     * @return ModelAndView
     * @author trieuvd
     */
    @GetMapping(value = UrlConst.AJAX_LIST)
    public ModelAndView getListRoleTeam(HttpServletRequest request, Locale locale,
            @RequestParam(value = "companyId", required = true) Long companyId) {
        ModelAndView mav = new ModelAndView("/views/role-team/irole-team-list.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_TEAM) && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        List<JcaTeamDto> teams = roleTeamService.findTeamByCompanyId(companyId);
        mav.addObject("teams", teams);
        if (CommonCollectionUtil.isNotEmpty(teams)) {
            mav.addObject("roleIdSelected", teams.get(0).getTeamId());
        }
        return mav;
    }
    
	@GetMapping(value = "/get-list-role-for-team")
	public ModelAndView getListRoleForGrade(
			@RequestParam(name="team-id", required = false) Long teamId,
			Model model) {
		// Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_TEAM) 
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
		
		ModelAndView mav = new ModelAndView(VIEW_ROLE_ADD_LIST);
		List<JcaRoleForTeamDto> list = new ArrayList<>();
		if (null != teamId)
			list = roleTeamService.getListRoleForTeam(teamId);
		mav.addObject("roleTeams", list);
		mav.addObject("teamId", teamId);
		return mav;
	}
	
	@PostMapping(value = "/post-list-role-for-team")
	@ResponseBody
	public ModelAndView ajaxListRoleForGrade(
			@RequestParam("roleTeam") String roleTeam,
			Locale locale,
			Model model) throws IOException {
		// Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_TEAM) 
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
		ModelAndView mav = new ModelAndView("/views/commons/message-alert.html");
		ObjectMapper mapper = new ObjectMapper();
		RoleTeamEditDto roleDto = mapper.readValue(roleTeam, RoleTeamEditDto.class);
		//exec
		int rs = roleTeamService.saveRoleForTeam(roleDto);
		MessageList messageList = null;
		if(rs == 1){
			messageList = new MessageList(Message.SUCCESS);
			String msgStr = msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);
	        messageList.add(msgStr);	
			mav.addObject(ConstantCore.MSG_LIST, messageList);		
		}
		else{
			messageList = new MessageList(Message.ERROR);
			String msgStr = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
	        messageList.add(msgStr);
			mav.addObject(ConstantCore.MSG_LIST, messageList);
		}      
        
		return mav;
	}

}
