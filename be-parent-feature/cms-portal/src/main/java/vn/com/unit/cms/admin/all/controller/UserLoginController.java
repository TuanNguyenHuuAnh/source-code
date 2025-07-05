package vn.com.unit.cms.admin.all.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.service.UserLoginAdminService;
import vn.com.unit.cms.core.module.usersLogin.dto.ChannelCountDto;
import vn.com.unit.cms.core.module.usersLogin.dto.UserLoginDto;
import vn.com.unit.cms.core.module.usersLogin.dto.UserLoginSearchDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.enumdef.UserLoginSearchEnum;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.utils.ConditionSearchUtils;
import vn.com.unit.ep2p.utils.SearchUtil;

@Controller
@RequestMapping("/user-login")
public class UserLoginController {
    
    @Autowired
    private UserLoginAdminService userLoginService;

    /** MessageSource */
    @Autowired
    private MessageSource msg;
    
    
    @Autowired
    private SystemConfig systemConfig;

    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
    
    private static final String USER_LOGIN_LIST_VIEW = "/views/user-login/user-login-list.html";
    private static final String USER_LOGIN_TABLE_VIEW = "/views/user-login/user-login-table.html";
    
    private static final String SORT_COMPANY_ID = "SORT_COMPANY_ID";
          
    @Autowired
    private CompanyService companyService;
    

    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView loadUserLoginAjax(@ModelAttribute(value = "searchDto") UserLoginSearchDto searchDto,
    		@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam, Locale locale,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, HttpServletRequest request) throws DetailException {
        ModelAndView mav = new ModelAndView(USER_LOGIN_LIST_VIEW);
        // set url ajax
        //searchDto.setUrl(UrlConst.LOG_API.concat(UrlConst.LIST));
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.USER_LOGIN) 
                && !UserProfileUtils.hasRole(RoleConstant.USER_LOGIN.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.USER_LOGIN.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
               
        // set init search
        SearchUtil.setSearchSelect(UserLoginSearchEnum.class, mav);
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE, UserProfileUtils.getCompanyId()));
//        int pageSize = pageSizeParam.orElse(100);
        ConditionSearchUtils<UserLoginSearchDto> searchUtil = new ConditionSearchUtils<UserLoginSearchDto>();
        String[] urlContains = new String[] { "user-login/list" };
        searchDto = searchUtil.getConditionSearch(this.getClass(), searchDto, urlContains, request, page, pageSize);
        pageSize = Optional.ofNullable(searchDto.getPageSize()).orElse(pageSize);
        page = Optional.ofNullable(searchDto.getPage()).orElse(page);

        PageWrapper<UserLoginDto> pageWrapper = new PageWrapper<>(); 
        List<ChannelCountDto> channelCountList = new ArrayList<>();
        int userLoginCount = userLoginService.countAllAccessTokenValidate();
		searchDto.setFieldValues(searchDto.getSearchKeyIds()); 
		userLoginService.doSearch(pageWrapper, channelCountList, page, searchDto, pageSize, locale);
		mav.addObject("pageWrapper", pageWrapper);
		mav.addObject("searchDto", searchDto);
		mav.addObject("channelCountList", channelCountList);
		mav.addObject("countUserLogin", userLoginCount);
		return mav;
	}

    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = "searchDto") UserLoginSearchDto searchDto, 
    		@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam, Locale locale,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, HttpServletRequest request) throws DetailException {
        ModelAndView mav = new ModelAndView(USER_LOGIN_TABLE_VIEW);
        // set init search
        SearchUtil.setSearchSelect(UserLoginSearchEnum.class, mav);
       
        // init page size
         int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        
        // Init PageWrapper
        ConditionSearchUtils<UserLoginSearchDto> searchUtil = new ConditionSearchUtils<UserLoginSearchDto>();
        searchUtil.setConditionSearch(request, this.getClass(), searchDto, page, pageSize);
        PageWrapper<UserLoginDto> pageWrapper = new PageWrapper<>(); 
        List<ChannelCountDto> channelCountList = new ArrayList<>();
        int userLoginCount = userLoginService.countAllAccessTokenValidate();
        userLoginService.doSearch(pageWrapper, channelCountList, page, searchDto, pageSize, locale);
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("searchDto", searchDto);
        mav.addObject("channelCountList", channelCountList);
        mav.addObject("countUserLogin", userLoginCount);
        return mav;
    }
}