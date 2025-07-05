/*******************************************************************************
 * Class        AccountTeamController
 * Created date 2017/09/12
 * Lasted date  2017/09/12
 * Author       phatvt
 * Change log   2017/09/1201-00 phatvt create a new
 ******************************************************************************/

package vn.com.unit.ep2p.admin.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.TeamSearchDto;
import vn.com.unit.ep2p.admin.dto.UserTeamSearchDto;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.TeamService;
import vn.com.unit.ep2p.admin.validators.TeamValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.enumdef.TeamEnum;
import vn.com.unit.ep2p.export.util.SearchUtil;
import vn.com.unit.ep2p.utils.ConditionSearchUtils;

/**
 * AccountTeamController
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
@Controller
@RequestMapping(UrlConst.ACCOUNT_TEAM)
public class TeamController{

    @Autowired
    TeamService accountTeamService;
    
    @Autowired
    CompanyService companyService;

    @Autowired
    private MessageSource msg;
    
    @Autowired
    TeamValidator teamValidator;
    
    @Autowired
    private SystemConfig systemConfig;
    
//    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);
   

    private String VIEW_ACCOUNT_TEAM_LIST = "/views/account-team/account-team-list.html";

    private String VIEW_ACCOUNT_TEAM_TABLE = "/views/account-team/account-team-table.html";

    private String VIEW_ACCOUNT_TEAM_EDIT = "/views/account-team/account-team-edit.html";

    private String VIEW_TEAM_DETAIL = "/views/account-team/account-team-detail.html";

    private String VIEW_ACCOUNT_TEAM_USER = "/views/account-team/account-team-user-list.html";
    
    private String VIEW_ACCOUNT_TEAM_USER_DETAIL = "/views/account-team/account-team-user-list-detail.html";
    
    public static final String LINK_TEAM_VIEW = "/team-view";
    
    public static final String USER_IDS = "userIds[]";
    public static final String ID = "id";
    public static final String KEY_SEARCH = "keySearch";
    public static final String TEAM_ID = "teamId";
    public static final String COMPANY_ID = "companyId";
    public static final String MSG_DELETE_USER = "account.team.message.delete.user";
    public static final String MSG_ADD_USER = "account.team.message.add.user";
    
    

    public static final String ROLE_TEAM = RoleConstant.TEAM_MANAGEMENT;


    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getPostList(@ModelAttribute(value = "searchDto") TeamSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request,
            Locale locale) throws Exception {
        ModelAndView mav = new ModelAndView(VIEW_ACCOUNT_TEAM_LIST);
        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_TEAM) && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);

        searchDto.setCompanyId(UserProfileUtils.getCompanyId());

        SearchUtil.setSearchSelect(TeamEnum.class, mav);
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);

        // Session Search
        ConditionSearchUtils<TeamSearchDto> searchUtil = new ConditionSearchUtils<TeamSearchDto>();
        String[] urlContains = new String[] { "account-team/add", "account-team/edit", "account-team/detail",
                "account-team/list" };
        searchDto = searchUtil.getConditionSearch(this.getClass(), searchDto, urlContains, request, page, pageSize);
        pageSize = Optional.ofNullable(searchDto.getPageSize()).orElse(pageSize);
        page = Optional.ofNullable(searchDto.getPage()).orElse(page);

        PageWrapper<JcaTeamDto> pageWrapper = accountTeamService.search(page, searchDto, pageSize);
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("searchDto", searchDto);
        // teamService.
        return mav;
    }

    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = "searchDto") TeamSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request,
            Locale locale) throws DetailException {
        ModelAndView mav = new ModelAndView(VIEW_ACCOUNT_TEAM_TABLE);

        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_TEAM) 
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        SearchUtil.setSearchSelect(TeamEnum.class, mav);
        
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        PageWrapper<JcaTeamDto> pageWrapper = accountTeamService.search(page, searchDto, pageSize);
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("searchDto", searchDto);
        
        // Session Search
        ConditionSearchUtils<TeamSearchDto> searchUtil = new ConditionSearchUtils<TeamSearchDto>();
        searchUtil.setConditionSearch(request, this.getClass(), searchDto, page, pageSize);
        
        return mav;
    }

//   
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView getEdit(@RequestParam(value = ID, required = false) String id, Locale locale) {
        ModelAndView mav = new ModelAndView(VIEW_ACCOUNT_TEAM_EDIT);
        if (!UserProfileUtils.hasRole(ROLE_TEAM) 
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        JcaTeamDto teamDto = accountTeamService.getTeam(id);
        // set url ajax
        // URL ajax redirect
        String url = "account-team".concat(UrlConst.EDIT);
        if (StringUtils.isNotBlank(id)) {
            // Security for data edit
//            if (null == teamDto || !UserProfileUtils.hasRoleForCompany(teamDto.getCompanyId())) {
//                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//            }
            url = url.concat("?id=").concat(id);
        }else {
            teamDto = new JcaTeamDto();
            teamDto.setCompanyId(UserProfileUtils.getCompanyId());
        }
        mav.addObject("url", url);
        mav.addObject("teamDto", teamDto);
        
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        return mav;
    }
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    public ModelAndView postEdit(@Valid @ModelAttribute(value = "teamDto") JcaTeamDto teamDto, BindingResult bindingResult,
            Locale locale, final RedirectAttributes redirectAttributes) throws ParseException {

        ModelAndView mav = new ModelAndView(VIEW_ACCOUNT_TEAM_EDIT);
        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_TEAM) 
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = StringUtils.EMPTY;
        // Validate business
        teamValidator.validate(teamDto, bindingResult);
        
        Long teamId = teamDto.getTeamId();
        
        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = null;
            if (null != teamId) {
                msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            } else {
                msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
            }
            messageList.add(msgError);
            
            // Add company list
            List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
            mav.addObject("companyList", companyList);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("teamDto", teamDto);
            
            return mav;
        }
       
        try {
        // Update
        accountTeamService.editTeamDto(teamDto);

       
        if (null != teamId) {
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        } else {
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
        }
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        }catch(Exception e) {
            messageList.setStatus(Message.ERROR);
            if (teamDto.getTeamId() == null) {
                msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
            } else {
                msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            }
        
        messageList.add(msgInfo);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject("teamDto", teamDto);
        return mav;
        }
        //Redirect
        String viewName = UrlConst.REDIRECT.concat("/account-team").concat(UrlConst.EDIT);
        mav.setViewName(viewName);
        redirectAttributes.addAttribute(ID, teamDto.getTeamId());

        return mav;
    }



    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    public ModelAndView postDelete(@RequestParam(value = "id", required = true) long id, Locale locale,
            RedirectAttributes redirectAttributes) throws Exception {
    	 // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_TEAM) 
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        JcaTeamDto jcaTeamDto = new  JcaTeamDto();
        jcaTeamDto = accountTeamService.getJcaTeamDtoById(id);
        accountTeamService.deleteTeamDto(jcaTeamDto.getTeamId());

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat("/account-team").concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);
        return mav;
    }

    @RequestMapping(value = LINK_TEAM_VIEW, method = RequestMethod.GET)
    public ModelAndView getViewAgentDetail(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(required = true) String id) {
        ModelAndView mav = new ModelAndView(VIEW_TEAM_DETAIL);
        JcaTeamDto teamDto = accountTeamService.getTeam(id);
        // Security for data detail
//        if (null == teamDto || (teamDto.getCompanyId() != null && !UserProfileUtils.has hasRoleForCompany(teamDto.getCompanyId()))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        mav.addObject("teamDto", teamDto);
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        return mav;
    }

    @RequestMapping(value = UrlConst.ACCOUNT_TEAM_GET, method = RequestMethod.GET)
    public ModelAndView addTeam(@ModelAttribute(value = "searchDto") UserTeamSearchDto searchDto
            , @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam
            , @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) {
        ModelAndView mav = new ModelAndView(VIEW_ACCOUNT_TEAM_USER);
        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_TEAM) 
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        // Init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        
        Long teamId = searchDto.getTeamId();
        String keySearch = searchDto.getKeySearch();
        
        PageWrapper<JcaAccountDto> listAccount = accountTeamService.getListUserForTeam(keySearch, teamId, pageSize, page);
        mav.addObject(ConstantCore.PAGE_WRAPPER, listAccount);
        mav.addObject("searchDto", searchDto);
        return mav;
    }
    
    @RequestMapping(value = UrlConst.ACCOUNT_TEAM_ADD, method = RequestMethod.POST)
    public ModelAndView addUser(@RequestParam(value = USER_IDS, required = true) List<Long> ids
    		, @ModelAttribute(value = "searchDto") UserTeamSearchDto searchDto
            , RedirectAttributes redirectAttributes
            , Locale locale) {
        ModelAndView mav = new ModelAndView(VIEW_ACCOUNT_TEAM_USER);
        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_TEAM) 
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        
        Long teamId = searchDto.getTeamId();
        try {
            int count = accountTeamService.addListUserForTeam(ids, teamId);
            Integer[] infoArgs = new Integer[1];
            infoArgs[0] = count;
            String msgInfo = msg.getMessage(MSG_ADD_USER, infoArgs, locale);
            messageList.add(msgInfo);
        } catch (ParseException e) {
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
            messageList.add(msgError);
        }
        mav.setViewName(UrlConst.REDIRECT
                .concat(UrlConst.ACCOUNT_TEAM)
                .concat(UrlConst.ACCOUNT_TEAM_GET));
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addFlashAttribute("searchDto", searchDto);
        return mav;
    }
    
    @RequestMapping(value = UrlConst.ACCOUNT_TEAM_DELETE, method = RequestMethod.POST)
    public ModelAndView deleteUser(@RequestParam(value = USER_IDS, required = true) List<Long> userId
    		, @ModelAttribute(value = "searchDto") UserTeamSearchDto searchDto
            , RedirectAttributes redirectAttributes
            , Locale locale) {
        ModelAndView mav = new ModelAndView(VIEW_ACCOUNT_TEAM_USER);
        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_TEAM) 
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        
        Long teamId = searchDto.getTeamId();
        try {
            int count = accountTeamService.deleteListUserForTeam(userId, teamId);
            Integer[] infoArgs = new Integer[1];
            infoArgs[0] = count;
            String msgInfo = msg.getMessage(MSG_DELETE_USER, infoArgs, locale);
            messageList.add(msgInfo);
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
            messageList.add(msgError);
        }
        mav.setViewName(UrlConst.REDIRECT
                .concat(UrlConst.ACCOUNT_TEAM)
                .concat(UrlConst.ACCOUNT_TEAM_GET));
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addFlashAttribute("searchDto", searchDto);
        return mav;
    }
    
    @RequestMapping(value = UrlConst.ACCOUNT_TEAM_DETAIL_GET, method = RequestMethod.GET)
    public ModelAndView userForTeamDetail(@ModelAttribute(value = "searchDto") UserTeamSearchDto searchDto
            , @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam
            , @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) {
        ModelAndView mav = new ModelAndView(VIEW_ACCOUNT_TEAM_USER_DETAIL);
        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_TEAM) 
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_TEAM.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        // Init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        
        Long teamId = searchDto.getTeamId();
        String keySearch = searchDto.getKeySearch();
        
        PageWrapper<JcaAccountDto> listAccount = accountTeamService.getListUserForTeam(keySearch, teamId, pageSize, page);
        mav.addObject(ConstantCore.PAGE_WRAPPER, listAccount);

        return mav;
    }
}
