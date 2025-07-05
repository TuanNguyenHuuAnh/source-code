/*******************************************************************************
 * Class        HomeController
 * Created date 2017/02/15
 * Lasted date  2017/02/15
 * Author       trieunh
 * Change log   2017/02/1501-00 trieunh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import java.util.HashMap;
import java.util.Locale;
//import java.util.Map;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javassist.NotFoundException;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.dto.JcaAccountTeamDto;
import vn.com.unit.core.dto.JcaStyleDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.MenuDto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountEditDto;
import vn.com.unit.ep2p.admin.service.AccountOrgService;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.CustomRoleService;
import vn.com.unit.ep2p.admin.service.MenuService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.admin.utils.CookieUtils;
import vn.com.unit.ep2p.admin.validators.AccountEditValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.ep2p.utils.ExecMessage;

/**
 * HomeController
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh
 */
@Controller(value = "")
public class HomeController {
	@Autowired
	CustomRoleService customRoleService;

	@Autowired
	MenuService menuService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private CompanyService companyService;
	
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AccountOrgService accountOrgService;
    
    @Autowired
    AccountEditValidator accEditValidator;
    
    /** systemLogsService */
    @Autowired
    private SystemLogsService systemLogsService;
    
    /** MessageSource */
    @Autowired
    private MessageSource messageSource;
    
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private static final int INVALID_SESSION_STATUS_CODE = 901;

	@RequestMapping(value = "/jcanary-home", method = RequestMethod.GET)
	public ModelAndView jcanaryHome() {
		ModelAndView mav = new ModelAndView("jcanary.home");
		return mav;
	}

	// Set page default for jcanary project
	@RequestMapping(value = UrlConst.ROOT, method = RequestMethod.GET)
	public String index() throws NotFoundException {
		String urlDefault = null;

		// 4.Get url default for home page
		MenuDto menu = customRoleService.findMenuByAccountId(UserProfileUtils.getUserPrincipal().getAccountId());
		if (menu != null) {
			String url = menu.getUrl();
			if (!url.isEmpty() && !UrlConst.ROOT.equals(url.trim())) {
				urlDefault = UrlConst.REDIRECT.concat(url);
			}
		}

		if (StringUtils.isEmpty(urlDefault)) {
			urlDefault = getUrlDefaultWhenLoginSuccessfull();
		}

		return urlDefault;
	}

    protected String getUrlDefaultWhenLoginSuccessfull() {
        return UrlConst.REDIRECT.concat("/account/info");
    }

	@RequestMapping(value = "/start-process", method = RequestMethod.GET)
	public ModelAndView startProcess(Model model) {
		ModelAndView mav = new ModelAndView("jcanary.home");

		// Test for jBPM
		/*
		 * String deploymentId = "unit:vccb-admin:1.0"; String
		 * processDefinitionId = "vccb-admin.vccb-approver-process"; String
		 * vccbUser = "vccbuser"; // String vccbManager = "vccbmanager"; Long
		 * processInstanceId = 37L;
		 * 
		 * RuntimeEngine runtimeEngine =
		 * remoteAPIService.findRuntimeEngine(deploymentId, vccbUser, vccbUser);
		 * 
		 * // Start process KieSession kieSession =
		 * remoteAPIService.findKieSession(deploymentId, runtimeEngine);
		 * ProcessInstance processInstance =
		 * kieSession.startProcess(processDefinitionId); processInstanceId =
		 * processInstance.getId(); System.out.println("processIntanceId : " +
		 * processInstanceId);
		 * 
		 * 
		 * // Before start process, it will initial with 'saved' status. User is
		 * 'vccbuser' TaskService taskService =
		 * remoteAPIService.findTaskService(runtimeEngine); // TaskSummary
		 * taskSummary =
		 * remoteAPIService.findTaskSummaryByUserIdInstanceId(taskService,
		 * vccbUser, processInstanceId); TaskSummary taskSummary =
		 * remoteAPIService.findTaskSummaryByInstanceId(taskService,
		 * processInstanceId); Map<String, Object> params = new HashMap<>();
		 * params.put("type", "news"); params.put("action", "save");
		 * remoteAPIService.completeTask(taskService, taskSummary, vccbUser,
		 * params);
		 */

		// Manager reject
		// TaskService taskServiceManager =
		// remoteAPIService.findTaskService(deploymentId, vccbManager,
		// vccbManager);
		// TaskSummary taskSummary =
		// remoteAPIService.findTaskSummaryByUserIdInstanceId(taskServiceManager,
		// vccbManager, 7L);
		// Map<String, Object> params = new HashMap<>();
		// params.put("currentStatus", "approved");
		// remoteAPIService.completeTask(taskServiceManager, taskSummary,
		// vccbUser, params);

		// remoteAPIService.findLisTaskByUser(taskService, vccbUser);
		// remoteAPIService.findLisTaskByUser(taskServiceManager, vccbManager);

		return mav;
	}

	/**
	 * Screen login
	 *
	 * @param model
	 * @return String
	 * @author KhoaNA
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = UrlConst.LOGIN, method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) {
		Map params = request.getParameterMap();
		Iterator i = params.keySet().iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			String value = ((String[]) params.get(key))[0];
			if ("error".equalsIgnoreCase(key) && !"true".equalsIgnoreCase(value)) {
				return UrlConst.REDIRECT.concat(UrlConst.LOGIN + "?error=true");
			}
			if ("reason".equalsIgnoreCase(key) && value.contains(UrlConst.ROOT)) {
				return UrlConst.REDIRECT.concat(UrlConst.LOGIN + "?reason=invalid.user.password");
			}
			if ("invalid-session".equalsIgnoreCase(key) && "true".equalsIgnoreCase(value)) {
			    response.setStatus(INVALID_SESSION_STATUS_CODE);
            }
		}
		String STYLE_DEFAULT = "STYLE_DEFAULT";
		JcaStyleDto styleDto = systemConfig.getStyleLogin(null, systemConfig.getConfig(STYLE_DEFAULT));
		String urlLogin = UrlConst.LOGIN;
		CookieUtils.createCookie(null, CookieUtils.COOKIE_LOGIN, urlLogin, null, request, response);
		WebUtils.setSessionAttribute(request, CookieUtils.COOKIE_LOGIN, urlLogin);
		WebUtils.setSessionAttribute(request, CookieUtils.DOMAIN_LOGIN, false);
		if (null != styleDto) {
			model.addAttribute("packageShortcutIcon", styleDto.getPackageShortcutIcon());
			model.addAttribute("packageLoginLogo", styleDto.getPackageLoginLogo());
			model.addAttribute("title", systemConfig.getConfig(SystemConfig.DISPLAY_SYSTEM_NAME));
		}
		model.addAttribute("style", styleDto);
		return "/views/home/login.html";
	}

	/**
	 * loginWithSystem
	 * 
	 * @param system
	 * @param request
	 * @param model
	 * @return
	 * @author HungHT
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{system}" + UrlConst.LOGIN, method = RequestMethod.GET)
	public String loginWithSystem(@PathVariable("system") String system, HttpServletRequest request,
			HttpServletResponse response, Model model, Locale locale) {
		Map params = request.getParameterMap();
		Iterator i = params.keySet().iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			String value = ((String[]) params.get(key))[0];
			if ("error".equalsIgnoreCase(key) && !"true".equalsIgnoreCase(value)) {
				return UrlConst.REDIRECT
						.concat(UrlConst.ROOT.concat(system.toLowerCase()).concat(UrlConst.LOGIN) + "?error=true");
			}
			if ("reason".equalsIgnoreCase(key) && value.contains(UrlConst.ROOT)) {
				return UrlConst.REDIRECT.concat(UrlConst.ROOT.concat(system.toLowerCase()).concat(UrlConst.LOGIN)
						+ "?reason=invalid.user.password");
			}
		}
		JcaStyleDto styleDto = null;
		String packageShortcutIcon = null;
		String shortcutIcon = null;
		Long shortcutIconRepoId = null;
		String packageLoginLogo = null;
		String loginLogo = null;
		Long loginLogoRepoId = null;
		String title = systemConfig.getConfig(SystemConfig.DISPLAY_SYSTEM_NAME);
		CompanyDto company = companyService.findBySystemCode(system);
		String urlLogin = null;
		if (null != company) {
			styleDto = systemConfig.getStyleLogin(company.getId(), company.getStyle());
			shortcutIcon = company.getShortcutIcon();
			shortcutIconRepoId = company.getShortcutIconRepoId();
			packageShortcutIcon = company.getPackageShortcutIcon();
			loginLogo = company.getLoginBackground();
			loginLogoRepoId = company.getLoginBackgroundRepoId();
			packageLoginLogo = company.getPackageLoginBackground();
			title = company.getSystemName();
			urlLogin = "/".concat(system.toLowerCase()).concat(UrlConst.LOGIN);
		} else {
			urlLogin = UrlConst.LOGIN;
		}
		if (null == styleDto) {
			styleDto = new JcaStyleDto();
		}
		CookieUtils.createCookie(null, CookieUtils.COOKIE_LOGIN, urlLogin, null, request, response);
		WebUtils.setSessionAttribute(request, CookieUtils.COOKIE_LOGIN, urlLogin);
		WebUtils.setSessionAttribute(request, CookieUtils.DOMAIN_LOGIN, true);
		model.addAttribute("packageShortcutIcon", packageShortcutIcon);
		model.addAttribute("shortcutIcon", shortcutIcon);
		model.addAttribute("shortcutIconRepoId", shortcutIconRepoId);
		model.addAttribute("packageLoginLogo", packageLoginLogo);
		model.addAttribute("loginLogo", loginLogo);
		model.addAttribute("loginLogoRepoId", loginLogoRepoId);
		model.addAttribute("title", title);
		model.addAttribute("style", styleDto);
		model.addAttribute("system", system);
		return "/views/home/login.html";
	}

	/**
	 * Keep current page
	 *
	 * @param request
	 * @param response
	 * @param locale
	 * @param redirectAttributes
	 * @author KhoaNA
	 */
	@RequestMapping(value = UrlConst.CHANGE_LANG, method = RequestMethod.GET)
	public void changeLang(HttpServletRequest request, HttpServletResponse response, Locale locale,
			RedirectAttributes redirectAttributes) {
		try {
			
			UserProfileUtils.getUserPrincipal().setLocale(locale);
			
			// Create cookies for lang
			String style = request.getParameter("lang");
			CookieUtils.createCookie(null, CookieUtils.COOKIE_LANGUAGE, style, null, request, response);

			// go to current page
			String url = request.getParameter("url");

			if (StringUtils.isNotEmpty(url)) {
				String param = request.getParameter("param");

				if (StringUtils.isNotEmpty(param)) {
					param = param.replaceAll(",", "&");
					url += "?" + param;
				}

				response.reset();
				// Check external URL
				if (url.contains(UrlConst.HTTP_PROTOCOL) || url.contains(UrlConst.WORLD_WIDE_WEB)) {
					url = request.getContextPath() + UrlConst.ACCESS_DENIED_PAGE;
				}
				response.sendRedirect(url);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * changeStyle
	 * 
	 * @param request
	 * @param response
	 * @param locale
	 * @param redirectAttributes
	 * @author HungHT
	 */
	@RequestMapping(value = UrlConst.CHANGE_STYLE, method = RequestMethod.GET)
	public void changeStyle(HttpServletRequest request, HttpServletResponse response, Locale locale,
			RedirectAttributes redirectAttributes) {
		try {
			// Create cookies for style
			String style = request.getParameter("style");
			CookieUtils.createCookie(UserProfileUtils.getAccountId(), CookieUtils.COOKIE_STYLE, style, null, request,
					response);
			// go to current page
			String url = request.getParameter("url");

			if (StringUtils.isNotEmpty(url)) {
				String param = request.getParameter("param");

				if (StringUtils.isNotEmpty(param)) {
					param = param.replaceAll(",", "&");
					url += "?" + param;
				}

				response.reset();
				// Check external URL
				if (url.contains(UrlConst.HTTP_PROTOCOL) || url.contains(UrlConst.WORLD_WIDE_WEB)) {
					url = request.getContextPath() + UrlConst.ACCESS_DENIED_PAGE;
				}
				response.sendRedirect(url);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@RequestMapping(value = UrlConst.ACCESS_DENIED_PAGE, method = RequestMethod.GET)
	public ModelAndView accessDeniedPage(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		ModelAndView mav = new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		return mav;
	}

	/**
	 * errorPage
	 *
	 * @param request
	 * @param response
	 * @param locale
	 * @return
	 * @author HUNGHT
	 */
	@RequestMapping(value = UrlConst.COMMON_ERROR_PAGE, method = RequestMethod.GET)
	public ModelAndView errorPage(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		return new ModelAndView(ViewConstant.COMMON_ERROR_MODELANDVIEW);
	}

	@RequestMapping(value = "/check-timeout", method = RequestMethod.GET)
	@ResponseBody
	public void checkTimeout(HttpServletRequest request, HttpServletResponse response, Locale locale) {

	}

    @RequestMapping(value = UrlConst.ROOT + UrlConst.ACCOUNT + UrlConst.INFO, method = RequestMethod.GET)
    public ModelAndView getInfo(Model model, Locale locale) {
        ModelAndView mav = new ModelAndView("/views/account/account-info.html");
        
        Long id = UserProfileUtils.getAccountId();

        // If redirect page
        Map<String, Object> md = model.asMap();
        MessageList messageList = (MessageList) md.get(ConstantCore.MSG_LIST);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        // Get data account
        JcaAccountEditDto accountDto = accountService.findAccountEditDtoById(id);
        
        mav.addObject("accountDto", accountDto);
        List<JcaAccountTeamDto> accountTeams = accountService.findAccountTeamByAccountId(id);
        List<Long> listGroupId = new ArrayList<Long>();
        for (JcaAccountTeamDto accountTeam : accountTeams) {
            listGroupId.add(accountTeam.getTeamId());
        }
        accountDto.setGroupId(listGroupId);
        mav.addObject("accountTeams", accountTeams);
        // Init master data
        accountService.initScreenAccountEdit(mav, model, accountDto, locale);
        
        List<JcaAccountOrgDto> listOrgForAccount = accountOrgService.findAccountOrgDtoByAccountId(id);
        if(CollectionUtils.isNotEmpty(listOrgForAccount)) {
            listOrgForAccount = listOrgForAccount.stream().filter(f -> f.getActived()).collect(Collectors.toList());
        }
        mav.addObject("listOrgForAccount", listOrgForAccount);

        return mav;
    }
    
    @RequestMapping(value = UrlConst.ROOT + UrlConst.ACCOUNT + UrlConst.INFO, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView postInfo(@Valid @ModelAttribute(value = "accountDto") JcaAccountEditDto accountDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, Model model,
            HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView("/views/account/account-info.html");

        // Write system logs
        systemLogsService.writeSystemLogs(RoleConstant.ACCOUNT, "Save Info Account",
                "Save Account(User Name: " + accountDto.getUsername() + ")", request);
        
        // Security for this page.
        if (!UserProfileUtils.getAccountId().equals(accountDto.getId())) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        
        // Validation
        accEditValidator.validate(accountDto, bindingResult);
        if (bindingResult.hasErrors())
        {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = messageSource.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgError);

            // Init master data
            accountService.initScreenAccountEdit(mav, model, accountDto, locale);

            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("accountDto", accountDto);
            return mav;
        }

        // Save account
        try {
            accountService.updateInfo(accountDto, locale);
            String msgSuccess = messageSource.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
            messageList.add(msgSuccess);
            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        } catch (Exception e) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = messageSource.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgError);
            
            if(e instanceof AppException){
                AppException ae = (AppException) e;
                String msgCodeError = ae.getCode();
                String msgContent = ExecMessage.getErrorMessage(messageSource, msgCodeError, locale).getErrorDesc();
                messageList.add(msgContent);
            }
            
            // Init master data
            accountService.initScreenAccountEdit(mav, model, accountDto, locale);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("accountDto", accountDto);
            return mav;
        }
        
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.ACCOUNT).concat(UrlConst.INFO);
        mav.setViewName(viewName);

        return mav;
    }
}
