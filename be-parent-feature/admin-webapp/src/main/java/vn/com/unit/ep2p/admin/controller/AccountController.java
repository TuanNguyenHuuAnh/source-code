/*******************************************************************************
 * Class        AccountController
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.PartnerDto;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.dto.JcaAccountTeamDto;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaRoleForAccountDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.JcaAccountAddDto;
import vn.com.unit.ep2p.admin.dto.OrgNode;
import vn.com.unit.ep2p.admin.dto.ReturnObject;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.dto.Select2ResultInfoDto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountChangePasswordDto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountDetailDto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountEditDto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountInfoSelect2Dto;
import vn.com.unit.ep2p.admin.entity.AccountPassword;
import vn.com.unit.ep2p.admin.enumdef.ConstantDisplayType;
import vn.com.unit.ep2p.admin.enumdef.OrgType;
import vn.com.unit.ep2p.admin.exception.BusinessException;
import vn.com.unit.ep2p.admin.service.AccountOrgService;
import vn.com.unit.ep2p.admin.service.AccountPasswordService;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.admin.service.OrgInfoService;
import vn.com.unit.ep2p.admin.service.OrganizationService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.admin.service.TeamService;
import vn.com.unit.ep2p.admin.utils.JCanaryPasswordUtil;
import vn.com.unit.ep2p.admin.validators.AccountAddValidator;
import vn.com.unit.ep2p.admin.validators.AccountEditValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.utils.RegexPasswordUtils;
import vn.com.unit.ep2p.utils.ConditionSearchUtils;
import vn.com.unit.ep2p.utils.ExecMessage;

/**
 * AccountController
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Controller
@RequestMapping(UrlConst.ROOT + UrlConst.ACCOUNT)
public class AccountController {
	
	@Autowired
	private JCanaryPasswordUtil jCanaryPasswordUtil;

	/** AccountService */
	@Autowired
	private AccountService accountService;

	/** MessageSource */
	@Autowired
	private MessageSource messageSource;

	/** AccountAddValidator */
	@Autowired
	AccountAddValidator accAddValidator;
	
	@Autowired
    AccountEditValidator accEditValidator;

	@Autowired
	private AccountService accService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private AccountPasswordService accountPasswordService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
    TeamService teamService;
    
    @Autowired
    private OrgInfoService orgService;
    
    @Autowired
    OrganizationService organizationService;
    
    @Autowired
    private AccountOrgService accountOrgService;
    
    @Autowired
    private JcaConstantService jcaConstantService;
    
    @Autowired
    private Db2ApiService db2ApiService;
    
    
    /** MessageSource */
    @Autowired
    private MessageSource msg;
	
	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	/** systemLogsService */
	@Autowired
	private SystemLogsService systemLogsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static final String SCREEN_FUNCTION_CODE = RoleConstant.ACCOUNT;
	public static final String ORG_FOR_ACCOUNT = "/views/account/org-for-account.html";
    public static final String ORG_FOR_ACCOUNT_DETAIL = "/views/account/org-for-account-detail.html";

	private static final String ACCOUNT_DTO = "accountDto";

	// thaonv: update
	public static final String DATE_FORMAT_DEFAULT = "dd/MM/yyyy";

	@InitBinder
	public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
		if (null == request.getSession().getAttribute(ConstantCore.FORMAT_DATE)) {
			request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
		}
		// The date format to parse or output your dates
		// thaonv: update
		String patternDate = (String) request.getSession().getAttribute(ConstantCore.FORMAT_DATE);
		SimpleDateFormat dateFormat;
		if (patternDate != null) {
			dateFormat = new SimpleDateFormat(patternDate);
		} else {
			dateFormat = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
		}
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
	}

	/**
	 * Screen list account (Init)
	 * 
	 * @return ModelAndView
	 * @author KhoaNA
	 * @throws DetailException 
	 * @throws ParseException
	 */
    @RequestMapping(value = UrlConst.LIST, method = RequestMethod.GET)
    public ModelAndView getList(@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request,
            Locale locale) throws DetailException {
        ModelAndView mav = new ModelAndView("/views/account/account-list.html");

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		
		JcaAccountSearchDto searchDto = new JcaAccountSearchDto();
		searchDto.setEnabled(true);
		searchDto.setCompanyId(UserProfileUtils.getCompanyId());
		
		initScreenAccountList(mav, locale);
		
		// set url page
		String url = UrlConst.ACCOUNT.concat(UrlConst.LIST);

        // Init PageWrapper
        int pageSize = pageSizeParam
                .orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE, UserProfileUtils.getCompanyId()));
        int page = pageParam.orElse(1);
        
		// Session Search
        ConditionSearchUtils<JcaAccountSearchDto> searchUtil = new ConditionSearchUtils<JcaAccountSearchDto>();
        String[] urlContains = new String[] { "account/add", "account/edit", "account/detail", "account/list" };
        searchDto = searchUtil.getConditionSearch(this.getClass(), searchDto, urlContains, request, page, pageSize);
        pageSize = Optional.ofNullable(searchDto.getPageSize()).orElse(pageSize);
        page = Optional.ofNullable(searchDto.getPage()).orElse(page);
		
		PageWrapper<JcaAccountDto> pageWrapper = accountService.search(page, pageSize, searchDto);
		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		mav.addObject("pageUrl", url);
		
        if (StringUtils.isNoneBlank(searchDto.getSearchKeyIds())) {
            List<String> list = new ArrayList<>();
            CollectionUtils.addAll(list, searchDto.getSearchKeyIds().split(","));
            searchDto.setListSearchKeyIds(list);
        }
        
        mav.addObject("searchDto", searchDto);
		
		return mav;
	}

	/**
	 * Screen list account (Search)
	 * 
	 * @return ModelAndView
	 * @author KhoaNA
	 * @throws DetailException 
	 * @throws ParseException
	 */
    @RequestMapping(value = UrlConst.AJAXLIST, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView postList(@ModelAttribute(value = "searchDto") JcaAccountSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request,
            Locale locale) throws DetailException {
        ModelAndView mav = new ModelAndView("/views/account/account-table.html");
        
		// Init PageWrapper
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);
		
		PageWrapper<JcaAccountDto> pageWrapper = accountService.search(page, pageSize, searchDto);
		
		mav.addObject("searchDto", searchDto);
		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		
		// Session Search
        ConditionSearchUtils<JcaAccountSearchDto> searchUtil = new ConditionSearchUtils<JcaAccountSearchDto>();
        searchUtil.setConditionSearch(request, this.getClass(), searchDto, page, pageSize);
        
		return mav;
	}
    
    public void initScreenAccountList(ModelAndView mav, Locale locale) {
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);

        // Add search key List
        List<SearchKeyDto> searchKeyList = accountService.genSearchKeyList(locale);
        mav.addObject("searchKeyList", searchKeyList);
    }

	/**
	 * Screen add account (Init)
	 * 
	 * @return ModelAndView
	 * @author KhoaNA
	 */
	@RequestMapping(value = UrlConst.ADD, method = RequestMethod.GET)
	public ModelAndView getAdd(Model model, HttpServletRequest request, Locale locale) {
		ModelAndView mav = new ModelAndView("/views/account/account-add.html");
		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		// Init accountAddDto
		JcaAccountAddDto accountDto = new JcaAccountAddDto();

		// Set default
		accountDto.setEnabled(true);
		accountDto.setLocked(false);
		accountDto.setCompanyId(UserProfileUtils.getCompanyId());
		accountDto.setPushNotification(true);
		accountDto.setArchiveFlag(false);

		mav.addObject("accountDto", accountDto);

		// Init master data
		accountService.initScreenAccountAdd(mav, accountDto, locale);

		return mav;
	}

	/**
	 * Screen add account (Add)
	 * 
	 * @return ModelAndView
	 * @author KhoaNA
	 * @throws IOException
	 */
    @RequestMapping(value = UrlConst.ADD, method = RequestMethod.POST)
    public ModelAndView postAdd(@Valid @ModelAttribute(value = "accountDto") JcaAccountAddDto accountDto, BindingResult bindingResult,
            Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView("/views/account/account-add.html");

        // Write system logs
        systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Add Account",
                "Save Account(User Name: " + accountDto.getUsername() + ")", request);

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        // Validate business
        accAddValidator.validate(accountDto, bindingResult);

        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = messageSource.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
            messageList.add(msgError);

            // Init master data
            accountService.initScreenAccountAdd(mav, accountDto, locale);

            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("accountDto", accountDto);
            return mav;
        }

        // Check limit account
        Long limitUser = companyService.getLimitNumberUsers(accountDto.getCompanyId());
        if (null != limitUser) {
            long currentNumberAccount = accountService.countNumberAccountByCompanyId(accountDto.getCompanyId());
            if (currentNumberAccount >= limitUser.longValue()) {
                // Add message error
                messageList.setStatus(Message.ERROR);
                String msgError = messageSource.getMessage("account.limit", null, locale);
                messageList.add(msgError);

                // Init master data
                accountService.initScreenAccountAdd(mav, accountDto, locale);

                mav.addObject(ConstantCore.MSG_LIST, messageList);
                mav.addObject("accountDto", accountDto);
                return mav;
            }
        }

        // Create account
        try {
            JcaAccount  account = accountService.create(accountDto, locale);
            
            String msgSuccess = messageSource.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
            messageList.add(msgSuccess);
            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
            String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.ACCOUNT).concat(UrlConst.EDIT);
            mav.setViewName(viewName);
            redirectAttributes.addAttribute("id", account.getId());
        } catch (Exception e) {
        	logger.error("Create accoung error #2021-05-04-18-00: ", e);
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = messageSource.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
            messageList.add(msgError);

            if(e instanceof AppException){
                AppException ae = (AppException) e;
                String msgCodeError = ae.getCode();
                String msgContent = ExecMessage.getErrorMessage(msg, msgCodeError, locale).getErrorDesc();
                messageList.add(msgContent);
            }
            
            // Init master data
            accountService.initScreenAccountAdd(mav, accountDto, locale);

            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject(ACCOUNT_DTO, accountDto);
            
            return mav;
        }
        return mav;
    }

	/**
	 * Screen edit account (Init)
	 * 
	 * @param id
	 * @return ModelAndView
	 * @author KhoaNA
	 */
	@RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
	public ModelAndView getEdit(@RequestParam(value = "id", required = true) Long id, Model model, Locale locale) {
		ModelAndView mav = new ModelAndView("/views/account/account-edit.html");
		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		// If redirect page
		Map<String, Object> md = model.asMap();
		MessageList messageList = (MessageList) md.get(ConstantCore.MSG_LIST);
		mav.addObject(ConstantCore.MSG_LIST, messageList);

		// Get data account
		JcaAccountEditDto accountDto = accountService.findAccountEditDtoById(id);
		
        // Security for data
//        if (null == accountDto || !UserProfileUtils.hasRoleForCompany(accountDto.getCompanyId())) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        
		// set url page
		String url = UrlConst.ACCOUNT.concat(UrlConst.EDIT);
		if (id != null) {
			url = url.concat("?id=").concat(id.toString());
		}
		accountDto.setUrl(url);

//		mav.addObject("accountDto", accountDto);
		List<JcaAccountTeamDto> accountTeams = accountService.findAccountTeamByAccountId(id);
		List<Long> listGroupId = new ArrayList<Long>();
        for (JcaAccountTeamDto accountTeam : accountTeams) {
            listGroupId.add(accountTeam.getTeamId());
        }
        accountDto.setGroupId(listGroupId);
		// Init master data
		accountService.initScreenAccountEdit(mav, model, accountDto, locale);
		mav.addObject("accountDto", accountDto);
		return mav;
	}
	
    /**
     * Screen edit account (Edit)
     * 
     * @return ModelAndView
     * @author KhoaNA
     * @throws IOException
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView postEdit(@Valid @ModelAttribute(value = "accountDto") JcaAccountEditDto accountDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, Model model,
            HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView("/views/account/account-edit.html");

        // Write system logs
        systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Edit Account",
                "Save Account(User Name: " + accountDto.getUsername() + ")", request);

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
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
            mav.addObject(ACCOUNT_DTO, accountDto);
            return mav;
        }
        
        if (accountDto.getChannelList() != null) {         
        	accountDto.setChannel(String.join(",", accountDto.getChannelList())); 
        }
        
        if (accountDto.getPartnerList() != null) {
        	accountDto.setPartner(String.join(",", accountDto.getPartnerList()));
        }
        
        // Save account
        try {
            accountService.update(accountDto, locale);
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
                String msgContent = ExecMessage.getErrorMessage(msg, msgCodeError, locale).getErrorDesc();
                messageList.add(msgContent);
            }
            
            // Init master data
            accountService.initScreenAccountEdit(mav, model, accountDto, locale);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject(ACCOUNT_DTO, accountDto);
            return mav;
        }
        
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.ACCOUNT).concat(UrlConst.EDIT);
        mav.setViewName(viewName);
        redirectAttributes.addAttribute("id", accountDto.getId());

        return mav;
    }

	@RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
	public ModelAndView getDetail(@RequestParam(value = "id", required = true) Long id, Model model, Locale locale) {
		ModelAndView mav = new ModelAndView("/views/account/account-detail.html");
		// Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

		// If redirect page
		Map<String, Object> md = model.asMap();
		MessageList messageList = (MessageList) md.get(ConstantCore.MSG_LIST);
		mav.addObject(ConstantCore.MSG_LIST, messageList);

		// Get data account
		JcaAccountDetailDto accountDto = accountService.findAccountDetailDtoById(id);
		// Security for data
//        if (null == accountDto || !UserProfileUtils.hasRoleForCompany(accountDto.getCompanyId())) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
		String url = UrlConst.ACCOUNT.concat(UrlConst.DETAIL);
		if (id != null) {
			url = url.concat("?id=").concat(id.toString());
		}
		accountDto.setUrl(url);
		mav.addObject(ACCOUNT_DTO, accountDto);
		//get group
		List<JcaAccountTeamDto> accountTeams = accountService.findAccountTeamByAccountId(id);
		mav.addObject("accountTeams", accountTeams);
		//gender
		List<JcaConstantDto> genders = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstantDisplayType.JCA_ADMIN_GENDER.toString(), locale.getLanguage());
        
        String gender = null;
        for (JcaConstantDto jcaConstantLanguageDto : genders) {
            if (jcaConstantLanguageDto.getCode().equals(accountDto.getGender())) {
                gender = jcaConstantLanguageDto.getName();
                break;
            }
        }
        mav.addObject("gender", gender);
        
		List<JcaConstantDto> channels = jcaConstantService.getListJcaConstantDisplayByType(ConstantDisplayType.CHANNEL.toString());
		mav.addObject("channels", channels);
		List<PartnerDto> partners = db2ApiService.getListPartnerByChannel("AD");
		mav.addObject("partners", partners);
		return mav;
	}
	
	/**
	 * Build list role for account. (AJAX)
	 * 
	 * @param accountId
	 * @param request
	 * @param response
	 * @return accountDto
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	@RequestMapping(value = UrlConst.BUILD_ROLE_FOR_ACCOUNT, method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView buildRoleForAccount(@RequestParam(value = "accountId", required = true) long accountId,
			HttpServletRequest request, HttpServletResponse response) {
		// Find list role for account
		List<JcaRoleForAccountDto> listRoleForAccount = accountService.findRoleForAccount(accountId);
		JcaAccountEditDto accountDto = accountService.findAccountEditDtoById(accountId);
		if (null != accountDto) {
			accountDto.setListRoleForAccount(listRoleForAccount);
		}

		ModelAndView mav = new ModelAndView("/views/account/role-for-account.html");
		mav.addObject("accountDto", accountDto);
		mav.addObject("listRole", accountService.findAllRole(accountDto.getCompanyId()));
		return mav;
	}

	@RequestMapping(value = UrlConst.BUILD_ROLE_FOR_ACCOUNT_DETAIL, method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView buildRoleForAccountDetail(@RequestParam(value = "accountId", required = true) long accountId,
			HttpServletRequest request, HttpServletResponse response) {
		// Find list role for account
		List<JcaRoleForAccountDto> listRoleForAccount = accountService.findRoleForAccountDetail(accountId);

		ModelAndView mav = new ModelAndView("/views/account/role-for-account-detail.html");
		mav.addObject("listRoleForAccount", listRoleForAccount);
		return mav;
	}

	/**
	 * Add role for account. (AJAX)
	 * 
	 * @param accountId
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	@RequestMapping(value = UrlConst.ADD_ROLE_FOR_ACCOUNT, method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView addRoleForAccount(@RequestParam(value = "accountId", required = true) long accountId,
			HttpServletRequest request, HttpServletResponse response) {

		// Write system logs
		systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Add Role for Account", "Add Role for Account",
				request);

		List<JcaRoleForAccountDto> listRoleForAccount = accountService.findRoleForAccount(accountId);
		listRoleForAccount.add(0, new JcaRoleForAccountDto());
		JcaAccountEditDto accountDto = accountService.findAccountEditDtoById(accountId);
		if (null != accountDto) {
			accountDto.setListRoleForAccount(listRoleForAccount);
		}
		ModelAndView mav = new ModelAndView("/views/account/role-for-account.html");
		mav.addObject("accountDto", accountDto);
		mav.addObject("listRole", accountService.findAllRole(accountDto.getCompanyId()));
		return mav;
	}

	/**
	 * Delete role for account. (AJAX)
	 * 
	 * @param accountId
	 * @param roleForAccountId
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	@RequestMapping(value = UrlConst.DELETE_ROLE_FOR_ACCOUNT, method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView deleteRoleForAccount(@RequestParam(value = "accountId", required = true) long accountId,
			@RequestParam(value = "roleId", required = true) long roleId,
			HttpServletRequest request, HttpServletResponse response, Locale locale) {
		// Write system logs
		systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Delete Role for Account", "Delete Role for Account",
				request);
		String mesContent = null;
		    try{
		        accountService.deleteRoleForAccount(roleId, accountId);
		        mesContent = messageSource.getMessage("message.delete.success", null, locale);
			}catch (AppException e) {
			    String msgCodeError = e.getCode();
			    mesContent = ExecMessage.getErrorMessage(msg, msgCodeError, locale).getErrorDesc();
            }
		List<JcaRoleForAccountDto> listRoleForAccount = accountService.findRoleForAccount(accountId);
		JcaAccountEditDto accountDto = accountService.findAccountEditDtoById(accountId);
		if (null != accountDto) {
			accountDto.setListRoleForAccount(listRoleForAccount);
		}
		ModelAndView mav = new ModelAndView("/views/account/role-for-account.html");
		mav.addObject(ACCOUNT_DTO, accountDto);
		mav.addObject("listRole", accountService.findAllRole(accountDto.getCompanyId()));
		mav.addObject("messageAccountForRole", mesContent);
		return mav;
	}

	@RequestMapping(value = UrlConst.UPDATE_ROLE_FOR_ACCOUNT, method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateRoleForAccount(@ModelAttribute(value = "accountDto") JcaAccountEditDto accountDto,
			HttpServletRequest request, HttpServletResponse response, BindingResult bindingResult, Locale locale) {

		// Write system logs
		systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Update Role for Account", "Update Role for Account",
				request);

		ModelAndView mav = new ModelAndView("/views/account/role-for-account.html");

		mav.addObject("listRole", accountService.findAllRole(accountDto.getCompanyId()));
		
        String mesContent = null;
        
//        boolean isSuccess = false;
		
		if (accountDto != null && accountDto.getListRoleForAccount() != null) {
//			if (!accountService.checkStartEndDate(accountDto.getListRoleForAccount())) {
				if (!accountService.checkDuplicateRole(accountDto.getListRoleForAccount())) {
				    try{
				        accountService.updateRoleForAccount(accountDto);
		                mesContent = messageSource.getMessage("message.success.update.label", null, locale);
//		                isSuccess = true;
		            }catch (AppException e) {
		                String msgCodeError = e.getCode();
		                mesContent = ExecMessage.getErrorMessage(msg, msgCodeError, locale).getErrorDesc();
		                logger.debug("Failed to copy role to Activity");
		            }
				} else {
				    mesContent = messageSource.getMessage("message.duplicate.role", null, locale);
					logger.debug("Duplicate Role");
				}

//			} else {
//			    mesContent = messageSource.getMessage("message.save.fail.formdatetodate", null, locale);
//				logger.debug("StartDate after EndDate");
//			}

//			if (isSuccess) {
			    List<JcaRoleForAccountDto> listRoleForAccount = accountService.findRoleForAccount(accountDto.getId());
                accountDto.setListRoleForAccount(listRoleForAccount);
//			}
			mav.addObject("messageAccountForRole", mesContent);
		}
		mav.addObject(ACCOUNT_DTO, accountDto);
		return mav;
	}

	@RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
	public ModelAndView deleteAccount(@RequestParam(value = "id", required = true, defaultValue = "") Long id,
			Locale locale, Model model, final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		// Write system logs
		systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Delete Account", "Delete Account(Id: " + id + ")",
				request);

		// if
		// (!UserProfileUtils.hasRole(RoleConstant.BUTTON_SHAREHOLDER_DELETE.concat(ConstantCore.COLON_DISP)))
		// {
		// return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		// }
		if (id <= 0) {
			throw new BusinessException("illegal data: id");
		}

		accountService.delete(id);
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.ACCOUNT.concat(UrlConst.LIST));
		ModelAndView mav = new ModelAndView(viewName);

		// Init message success list
		MessageList messageLst = new MessageList(Message.SUCCESS);
		this.addMsgInfo(ConstantCore.MSG_SUCCESS_DELETE, locale, mav, messageLst);

		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageLst);
		return mav;

	}

	/**
	 * @param locale
	 * @param mav
	 * @param messageLst
	 */
	private void addMsgInfo(String msgId, Locale locale, ModelAndView mav, MessageList messageLst) {
		String msgInfo = messageSource.getMessage(msgId, null, locale);
		messageLst.add(msgInfo);
		mav.addObject(ConstantCore.MSG_LIST, messageLst);
	}

	@RequestMapping(value = UrlConst.CHANGE_PASSWORD, method = RequestMethod.GET)
	public ModelAndView changPassword(Model model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/views/account/account-change-password.html");
//		if(!UserProfileUtils.getUserProfile().getLocalAccount()) {
//		    return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}
		JcaAccountChangePasswordDto accountDto = new JcaAccountChangePasswordDto();
		mav.addObject(ACCOUNT_DTO, accountDto);
		return mav;
	}

	@RequestMapping(value = UrlConst.AJAX_CHANGE_PASSWORD, method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView ajaxchangPassword(
			@Valid @ModelAttribute(value = ACCOUNT_DTO) JcaAccountChangePasswordDto accountDto,
			BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, Model model,
			HttpServletRequest request) throws NoSuchAlgorithmException, NoSuchMessageException {
		ModelAndView mav = new ModelAndView("/views/account/account-change-password.html");
		MessageList messageList = new MessageList(Message.SUCCESS);
		// check
		JcaAccount accDB = accService.findByUserName(UserProfileUtils.getUserNameLogin(), UserProfileUtils.getCompanyId());
		int check = 1;
		String msg = "";

		// check Minimum Password Age
		int passAge = Integer.parseInt(systemConfig.getConfig(SystemConfig.MIN_PASSWORD_AGE));
		AccountPassword accPass = accountPasswordService.getHistoryPassword(accountDto.getAccountId());
		if (accPass != null) {
			LocalDate passAgeDate = accPass.getEffectedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
					.plusMonths(passAge);
			LocalDate currentDate = LocalDate.now();

			if (passAgeDate.isAfter(currentDate)) {
				String[] pasAge = new String[] { String.valueOf(passAge) };
				msg = messageSource.getMessage("account.pass.age", pasAge, locale);
				check = 0;
			}
		}
		
		//if (check != 0 && !Util.encryptMD5(accountDto.getOldPassword()).equals(accDB.getPassword())) {
        if (check != 0 && !passwordEncoder.matches(accountDto.getOldPassword(), accDB.getPassword())) {
            msg = messageSource.getMessage("account.old.password.alert", null, locale);
            check = 0;
        }

		if (check != 0 && !accountDto.getNewPassword().equals(accountDto.getConfirmNewPassword())) {
			msg += messageSource.getMessage("account.old.password.alert.1", null, locale);
			if (check == 1) {
				check = 0;
			}
		}

		if (check != 0 && accountDto.getOldPassword().equals(accountDto.getNewPassword())) {
			msg = messageSource.getMessage("account.password.newpassword.alert", null, locale);
			check = 0;
		}

		Integer flagComplexity = Integer.parseInt(systemConfig.getConfig(SystemConfig.FLAG_COMPLEXITY));
		if (flagComplexity != 0) {
			// Check strong password
			if (check != 0 && !jCanaryPasswordUtil.isStrongPassword(accountDto.getNewPassword())) {
				Integer sysLowerCase = Integer.parseInt(systemConfig.getConfig(SystemConfig.FLAG_LOWER_CASE));
				Integer sysUpperCase = Integer.parseInt(systemConfig.getConfig(SystemConfig.FLAG_UPPER_CASE));
				Integer sysNumbericCase = Integer.parseInt(systemConfig.getConfig(SystemConfig.FLAG_NUMBER_CASE));
				Integer sysSpecialCase = Integer.parseInt(systemConfig.getConfig(SystemConfig.FLAG_SPECIAL_CASE));
				Integer sysLengthCase = Integer
						.parseInt(systemConfig.getConfig(SystemConfig.MIN_PASSWORD_LENGTH) != null
								? systemConfig.getConfig(SystemConfig.MIN_PASSWORD_LENGTH) : "6");
				ArrayList<String> array = new ArrayList<String>();
				array.add(String.valueOf(sysLengthCase));
				if (sysLowerCase > 0) {
					String msgInfo = messageSource.getMessage(ConstantCore.MSG_LOWER, null, locale);
					array.add(msgInfo);
				}
				if (sysUpperCase > 0) {
					String msgInfo = messageSource.getMessage(ConstantCore.MSG_UPPER, null, locale);
					array.add(msgInfo);
				}
				if (sysNumbericCase > 0) {
					String msgInfo = messageSource.getMessage(ConstantCore.MSG_NUMBER, null, locale);
					array.add(msgInfo);
				}
				if (sysSpecialCase > 0) {
					String msgInfo = messageSource.getMessage(ConstantCore.MSG_SYMBOL, null, locale);
					array.add(msgInfo);
				}
				int total = sysUpperCase + sysLowerCase + sysNumbericCase + sysSpecialCase;
				if (total == 3) {
					msg += messageSource.getMessage(ConstantCore.MSG_STRONG_PW, array.toArray(), locale);
				} else {
					msg += messageSource.getMessage(ConstantCore.MSG_STRONG_PW1, array.toArray(), locale);
				}
				check = 0;
			}
		}

		if (check != 0 && accountPasswordService.passwordHaveBeenUsed(accountDto.getAccountId(),
		        passwordEncoder.encode(accountDto.getNewPassword()))) {
			msg = messageSource.getMessage(ConstantCore.MSG_HAVE_USED, null, locale);
			check = 0;
		}
		
        // check regex
        if (!RegexPasswordUtils.isRegex(accountDto.getNewPassword())) {
            check = 0;
            msg = messageSource.getMessage(ConstantCore.MSG_STRONG_REGEX, null, locale);
        }

		if (check == 0) {
			messageList = new MessageList(Message.ERROR);
			messageList.add(msg);
			mav.addObject(ConstantCore.MSG_LIST, messageList);
			mav.addObject(ACCOUNT_DTO, accountDto);
			return mav;
		} else {
			accService.changePassword(UserProfileUtils.getUserPrincipal().getAccountId(), accountDto.getNewPassword());
			msg = messageSource.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
			messageList.add(msg);
			mav.addObject(ConstantCore.MSG_LIST, messageList);
			mav.addObject(ACCOUNT_DTO, accountDto);
			return mav;
		}
	}
	
	@RequestMapping(value = "/get_position_group_by_company", method = RequestMethod.GET)
    public ModelAndView getPositionGroup(@RequestParam(value = "companyId", required = true) Long companyId,
            HttpServletRequest request, Locale locale) {
	    ModelAndView mav = new ModelAndView("/views/account/iaccount-add.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        };   
        //List<PositionDto> positionDtoList = new ArrayList<PositionDto>();
        List<JcaTeamDto> groupDtoList = new ArrayList<JcaTeamDto>();
        List<JcaTeamDto> groupDtoListDisable = new ArrayList<JcaTeamDto>();
        if(null!=companyId) {
            //positionDtoList = positionService.findPositionDtoByCompany(companyId);
            groupDtoList = teamService.findTeamByCompanyIdForUser(companyId);
        }
//        if(!UserProfileUtils.isCompanyAdmin()) {
//            for (TeamDto teamDto : groupDtoList) {
//                if(null == teamDto.getCompanyId())
//                    groupDtoListDisable.add(teamDto);
//            }
//        }
        mav.addObject("groupDtoListDisable", groupDtoListDisable);
        //mav.addObject("positionDtoList", positionDtoList);
        mav.addObject("groupDtoList", groupDtoList);
        return mav;
    }
	
	@RequestMapping(value = "/get_branch_by_company", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getBranchByCompany(@RequestParam(value = "companyId", required = true) Long companyId) {
	    List<OrgNode> branchList = orgService.getNodeByOrgTypeCompanyId(OrgType.BRANCH, companyId);
        String branchTreeJson = CommonJsonUtil.convertObjectToJsonString(branchList);
        return StringUtils.isNotBlank(branchTreeJson)? branchTreeJson:CommonJsonUtil.convertObjectToJsonString(new OrgNode());
    }
	
	@RequestMapping(value = "/get_dep_by_company", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getDepByCompany(@RequestParam(value = "companyId", required = true) Long companyId, @RequestParam(value = "branchId") Long branchId) {
	    List<OrgNode> deptList = orgService.getNodeByParentIdOrgTypeCompanyId(branchId, OrgType.SECTION, companyId);
        String depTreeJson = CommonJsonUtil.convertObjectToJsonString(deptList);
        return StringUtils.isNotBlank(depTreeJson)?depTreeJson:CommonJsonUtil.convertObjectToJsonString(new OrgNode());
    }

    
    @RequestMapping(value = UrlConst.BUILD_ORG_FOR_ACCOUNT, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView buildOrgForAccount(@RequestParam(value = "accountId", required = true) long accountId,
            HttpServletRequest request, HttpServletResponse response) {
       
        ModelAndView mav = new ModelAndView(ORG_FOR_ACCOUNT);
        // Find list dep for account
        List<JcaAccountOrgDto> listOrgForAccount = accountOrgService.findAccountOrgDtoByAccountId(accountId);
        mav.addObject("listOrgForAccount", listOrgForAccount);
        return mav;
    }
    
    @RequestMapping(value = UrlConst.BUILD_ORG_FOR_ACCOUNT_DETAIL, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView buildOrgForAccountDetail(@RequestParam(value = "accountId", required = true) long accountId,
            HttpServletRequest request, HttpServletResponse response) {
       
        ModelAndView mav = new ModelAndView(ORG_FOR_ACCOUNT_DETAIL);
        // Find list dep for account
        List<JcaAccountOrgDto> listOrgForAccount = accountOrgService.findAccountOrgDtoByAccountId(accountId);
        mav.addObject("listOrgForAccount", listOrgForAccount);
        return mav;
    }

    @RequestMapping(value = UrlConst.ADD_ORG_FOR_ACCOUNT, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addOrgForAccount(@RequestParam(value = "accountId", required = true) long accountId,
            HttpServletRequest request, HttpServletResponse response) {

        // Write system logs
        /*systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Add Org for Account", "Add Org for Account",
                request);*/
        ModelAndView mav = new ModelAndView(ORG_FOR_ACCOUNT);
        // Find list dep for account
        List<JcaAccountOrgDto> listOrgForAccount = accountOrgService.findAccountOrgDtoByAccountId(accountId);
        JcaAccountOrgDto jcaAccountOrgDto = new JcaAccountOrgDto();
        jcaAccountOrgDto.setUserId(accountId);
        jcaAccountOrgDto.setActived(true);
        listOrgForAccount.add(0, jcaAccountOrgDto);
        
        mav.addObject("listOrgForAccount", listOrgForAccount);

        return mav;
    }

    @RequestMapping(value = UrlConst.DELETE_ORG_FOR_ACCOUNT, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView deleteOrgForAccount(@ModelAttribute(value = "accountDto") JcaAccountEditDto accountDto,
            @RequestParam(value = "index", required = true) int index,
            HttpServletRequest request, HttpServletResponse response, Locale locale){
        // Write system logs
        /*systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Delete Role for Account", "Delete Role for Account",
                request);*/
        ModelAndView mav = new ModelAndView(ORG_FOR_ACCOUNT);
        String message = messageSource.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        List<JcaAccountOrgDto> listAccountOrgDto = accountDto.getListOrgForAccount();
        JcaAccountOrgDto objDelete = listAccountOrgDto.get(index);
        try {
            if (null != objDelete.getOrgId()) {
                accountOrgService.deleteJcaAccountOrgByPK(accountDto.getId(), objDelete.getOrgId(), objDelete.getPositionId());
            }
            listAccountOrgDto.remove(index);
        } catch (Exception e) {
            message = messageSource.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
        }
        mav.addObject("listOrgForAccount", listAccountOrgDto);
        mav.addObject("messageOrgForAccount", message);
        return mav;
    }

    @RequestMapping(value = UrlConst.UPDATE_ORG_FOR_ACCOUNT, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView updateOrgForAccount(@ModelAttribute(value = "accountDto") JcaAccountEditDto accountDto,
            HttpServletRequest request, HttpServletResponse response, BindingResult bindingResult, Locale locale) {

        // Write system logs
        /*systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Update Role for Account", "Update Role for Account",
                request);*/

        ModelAndView mav = new ModelAndView(ORG_FOR_ACCOUNT);
        MessageList messageList = new MessageList(Message.SUCCESS);
        
        List<JcaAccountOrgDto> listOrgForAccount;
        if (accountDto != null && accountDto.getListOrgForAccount() != null) {
            String messageError = accountOrgService.validateOrgList(accountDto.getListOrgForAccount(), locale);
            if (messageError!=null) {
                listOrgForAccount = accountOrgService.findAccountOrgDtoByAccountId(accountDto.getId());
                mav.addObject("listOrgForAccount", listOrgForAccount);
                mav.addObject("messageOrgForAccount", messageError);
                messageList.add(messageError);
                mav.addObject(ConstantCore.MSG_LIST, messageList);
                return mav;
            }
            try {
                accountOrgService.updateOrgForAccount(accountDto);
            } catch (Exception e) {
                e.printStackTrace();
                mav.addObject("listOrgForAccount", accountDto.getListOrgForAccount());
                mav.addObject("messageOrgForAccount", e.getMessage());
                return mav;
            }
            listOrgForAccount = accountOrgService.findAccountOrgDtoByAccountId(accountDto.getId());
            mav.addObject("listOrgForAccount", listOrgForAccount);
            String messageSuccess = messageSource.getMessage("message.success.update.label", null, locale);
            messageList.add(messageSuccess);

            mav.addObject("messageOrgForAccount", messageSuccess);
        }
        
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        return mav;
    }
    
	@RequestMapping(value = "/ajax-get-list-user-by-key", method = RequestMethod.POST)
    @ResponseBody
    public Object getListUserByKey(@RequestParam(value = "key", required = false) String key
            , @RequestParam(value = "isPaging") boolean isPaging
            , @RequestParam(value = "orgId", required = false) Long orgId
            , @RequestParam(value = "companyIds", required = false) List<Long> companyIds) {
	    Select2ResultDto obj = new Select2ResultDto();
//	    if(null == companyIds || companyIds.size() == 0){
//	        companyIds = UserProfileUtils.getCompanyIdEmailList();
//	    }
	    Long accountId = UserProfileUtils.getAccountId();
        List<Select2Dto> lst = accountService.getSelect2DtoListByKeyAndListCompanyId(key, orgId, accountId, companyIds, isPaging);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
	
//	@RequestMapping(value = UrlConst.INFO, method = RequestMethod.GET)
//    public ModelAndView getInfo(Model model, Locale locale) {
//        ModelAndView mav = new ModelAndView("/views/account/account-info.html");
//        
//        Long id = UserProfileUtils.getAccountId();
//
//        // If redirect page
//        Map<String, Object> md = model.asMap();
//        MessageList messageList = (MessageList) md.get(ConstantCore.MSG_LIST);
//        mav.addObject(ConstantCore.MSG_LIST, messageList);
//
//        // Get data account
//        JcaAccountEditDto accountDto = accountService.findAccountEditDtoById(id);
//        
//        mav.addObject("accountDto", accountDto);
//        List<JcaAccountTeamDto> accountTeams = accountService.findAccountTeamByAccountId(id);
//        List<Long> listGroupId = new ArrayList<Long>();
//        for (JcaAccountTeamDto accountTeam : accountTeams) {
//            listGroupId.add(accountTeam.getTeamId());
//        }
//        accountDto.setGroupId(listGroupId);
//        mav.addObject("accountTeams", accountTeams);
//        // Init master data
//        accountService.initScreenAccountEdit(mav, model, accountDto, locale);
//        
//        List<JcaAccountOrgDto> listOrgForAccount = accountOrgService.findAccountOrgDtoByAccountId(id);
//        if(CollectionUtils.isNotEmpty(listOrgForAccount)) {
//        	listOrgForAccount = listOrgForAccount.stream().filter(f -> f.getActived()).collect(Collectors.toList());
//        }
//        mav.addObject("listOrgForAccount", listOrgForAccount);
//
//        return mav;
//    }
//	
//	@RequestMapping(value = UrlConst.INFO, method = RequestMethod.POST)
//    @ResponseBody
//    public ModelAndView postInfo(@Valid @ModelAttribute(value = "accountDto") JcaAccountEditDto accountDto,
//            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, Model model,
//            HttpServletRequest request) throws IOException {
//        ModelAndView mav = new ModelAndView("/views/account/account-info.html");
//
//        // Write system logs
//        systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Info Account",
//                "Save Account(User Name: " + accountDto.getUsername() + ")", request);
//        
//        // Security for this page.
//        if (!UserProfileUtils.getAccountId().equals(accountDto.getId())) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        // Init message list
//        MessageList messageList = new MessageList(Message.SUCCESS);
//        
//        // Validation
//        accEditValidator.validate(accountDto, bindingResult);
//        if (bindingResult.hasErrors())
//        {
//            // Add message error
//            messageList.setStatus(Message.ERROR);
//            String msgError = messageSource.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
//            messageList.add(msgError);
//
//            // Init master data
//            accountService.initScreenAccountEdit(mav, model, accountDto, locale);
//
//            mav.addObject(ConstantCore.MSG_LIST, messageList);
//            mav.addObject(ACCOUNT_DTO, accountDto);
//            return mav;
//        }
//
//        // Save account
//        try {
//            accountService.updateInfo(accountDto, locale);
//            String msgSuccess = messageSource.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
//            messageList.add(msgSuccess);
//            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
//        } catch (Exception e) {
//            // Add message error
//            messageList.setStatus(Message.ERROR);
//            String msgError = messageSource.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
//            messageList.add(msgError);
//            
//            if(e instanceof AppException){
//                AppException ae = (AppException) e;
//                String msgCodeError = ae.getCode();
//                String msgContent = ExecMessage.getErrorMessage(msg, msgCodeError, locale).getErrorDesc();
//                messageList.add(msgContent);
//            }
//            
//            // Init master data
//            accountService.initScreenAccountEdit(mav, model, accountDto, locale);
//            mav.addObject(ConstantCore.MSG_LIST, messageList);
//            mav.addObject("accountDto", accountDto);
//            return mav;
//        }
//        
//        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.ACCOUNT).concat(UrlConst.INFO);
//        mav.setViewName(viewName);
//
//        return mav;
//    }
	
	@RequestMapping(value = "/find-select2-by-multiple-conditions", method = RequestMethod.POST)
    @ResponseBody
    public Object findSelect2DtoByMultipleConditions(@RequestParam(value = "key", required = false) String key, @RequestParam(value = "isPaging") boolean isPaging
            , @RequestParam(value = "companyId", required = false) Long companyId) {
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> lst = accountService.findSelect2DtoByMultipleConditions(key, isPaging, companyId);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
	
	@RequestMapping(value = "/ajax-get-list-user-by-org", method = RequestMethod.POST)
    @ResponseBody
    public Object getListUserByOrg(@RequestParam(value = "key", required = false) String key
            , @RequestParam(value = "isPaging") boolean isPaging
            , @RequestParam(value = "orgId", required = false) Long orgId) {
	    Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> lst = accountService.getSelect2DtoByOrg(key, orgId, isPaging);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
	
	@RequestMapping(value = "/ajax-get-list-user-select2-by-key", method = RequestMethod.POST)
    @ResponseBody
    public Object getSelect2UserByKey(@RequestParam(value = "key", required = false) String key
            , @RequestParam(value = "isPaging") boolean isPaging
            , @RequestParam(value = "orgId", required = false) Long orgId
            , @RequestParam(value = "companyIds", required = false) List<Long> companyIds) {
	    Select2ResultInfoDto<JcaAccountInfoSelect2Dto> obj = new Select2ResultInfoDto<JcaAccountInfoSelect2Dto>();
//        if(null == companyIds || companyIds.size() == 0){
//            companyIds = UserProfileUtils.getCompanyIdEmailList();
//        }
        Long accountId = UserProfileUtils.getAccountId();
        List<JcaAccountInfoSelect2Dto> lst = accountService.getAccountInfoSelect2DtoListByKeyAndListCompanyId(key, orgId, accountId, companyIds, isPaging);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
	@RequestMapping(value = "/synchronize-ldap", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public ModelAndView synchronizeLDAP(HttpServletRequest request, Locale locale) {
		
		ModelAndView mav = new ModelAndView("message.alert");
		boolean syncLDAP =  accountService.syncLDAP();
//		boolean syncDataJob =  accountService.syncDataJob();
		MessageList messageList = new MessageList();
		if(syncLDAP) {
			messageList = new MessageList(Message.SUCCESS);
			String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_SYNC_LDAP, null, locale);
			messageList.add(msgInfo);
		}else {
			messageList = new MessageList(Message.ERROR);
			String msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_SYNC_LDAP, null, locale);
			messageList.add(msgInfo);
		}
//		if(syncDataJob) {
//			messageList = new MessageList(Message.SUCCESS);
//			String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_SYNC_SAP, null, locale);
//			messageList.add(msgInfo);
//		}else {
//			messageList = new MessageList(Message.ERROR);
//			String msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_SYNC_SAP, null, locale);
//			messageList.add(msgInfo);
//		}
		mav.addObject(ConstantCore.MSG_LIST, messageList);
		return mav;
	}
	
	@PostMapping("/find-account-ldap")
	@ResponseBody
	public ReturnObject findAccountLdap(@RequestParam(value = "username", required = true) String username,
			HttpServletRequest request, Locale locale) {
		return accountService.syncAccountByUsername(username,locale);
	}
	
	@RequestMapping(value = "/get-list-partner-by-channel", method = RequestMethod.POST)
	@ResponseBody
	public Object getListJobClass(@RequestParam(value = "channel", required = false) String channel) {
		List<PartnerDto> lstPartners = db2ApiService.getListPartnerByChannel(channel);
		return lstPartners;
	}
}
