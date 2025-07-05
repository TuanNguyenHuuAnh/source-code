/*******************************************************************************
 * Class        RepositoryController
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/0801-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

//import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.RepositorySearchDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.service.CompanyService;
//import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.admin.utils.JCanaryPasswordUtil;
import vn.com.unit.ep2p.admin.validators.RepositoryValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.enumdef.RepositorySearchEnum;
import vn.com.unit.ep2p.export.util.SearchUtil;
import vn.com.unit.ep2p.utils.ConditionSearchUtils;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.entity.JcaRepository;
//import vn.com.unit.storage.enumdef.FileProtocol;

/**
 * RepositoryController
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Controller
@RequestMapping(UrlConst.REPOSITORY)
public class RepositoryController {

	/** repositoryService */
	@Autowired
	private JRepositoryService repositoryService;
	
	@Autowired
	private JcaConstantService jcaConstantService;

	@Autowired
	private MessageSource msg;

	/** RepositoryValidator */
	@Autowired
	RepositoryValidator repositoryValidator;

	/** systemLogsService */
	@Autowired
	private SystemLogsService systemLogsService;

	/** constantDisplayService */
//	@Autowired
//	private ConstantDisplayService constantDisplayService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	SystemConfig systemConfig;

	/** SCREEN_FUNCTION_CODE */
	private static final String SCREEN_FUNCTION_CODE = RoleConstant.REPOSITORY;
	
    /**
     * getPostList
     *
     * @param searchDto
     *            type RepositorySearchDto
     * @param page
     *            type int
     * @param pageSizeParam
     *            type int
     * @param locale
     *            type Locale
     * @return ModelAndView
     * @author KhoaNA
     * @throws DetailException 
     */
    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getPostList(@ModelAttribute(value = "searchDto") RepositorySearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page, HttpServletRequest request,
            Locale locale) throws DetailException {
        ModelAndView mav = new ModelAndView("/views/repository/repository-list.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set init search
        SearchUtil.setSearchSelect(RepositorySearchEnum.class, mav);
        // init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        // Session Search
        ConditionSearchUtils<RepositorySearchDto> searchUtil = new ConditionSearchUtils<RepositorySearchDto>();
        String[] urlContains = new String[] { "repository/add", "repository/edit", "repository/detail", "repository/list" };
        searchDto = searchUtil.getConditionSearch(this.getClass(), searchDto, urlContains, request, page, pageSize);
        pageSize = Optional.ofNullable(searchDto.getPageSize()).orElse(pageSize);
        page = Optional.ofNullable(searchDto.getPage()).orElse(page);

        searchDto.setCompanyId(UserProfileUtils.getCompanyId());
        // set url ajax
        String url = UrlConst.REPOSITORY.concat(UrlConst.LIST);
        url = url.substring(1);
        searchDto.setUrl(url);
        
        PageWrapper<JcaRepositoryDto> pageWrapper = repositoryService.searchByCondition(page, searchDto, pageSize);
        
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("searchDto", searchDto);
        return mav;
    }

    /**
     * ajaxList
     *
     * @param searchDto
     *            type RepositorySearchDto
     * @param page
     *            type int
     * @param pageSizeParam
     *            type int
     * @param locale
     *            type Locale
     * @return ModelAndView
     * @author KhoaNA
     * @throws DetailException 
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = "searchDto") RepositorySearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page, HttpServletRequest request,
            Locale locale) throws DetailException {
        ModelAndView mav = new ModelAndView("/views/repository/repository-table.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));

        // Init PageWrapper
        PageWrapper<JcaRepositoryDto> pageWrapper = repositoryService.searchByCondition(page, searchDto, pageSize);
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("searchDto", searchDto);

        // Session Search
        ConditionSearchUtils<RepositorySearchDto> searchUtil = new ConditionSearchUtils<RepositorySearchDto>();
        searchUtil.setConditionSearch(request, this.getClass(), searchDto, page, pageSize);

        return mav;
    }

	/**
	 * getDetail
	 *
	 * @param id
	 *            type Long
	 * @param locale
	 *            type Locale
	 * @return ModelAndView
	 * @author KhoaNA
	 */
	@RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
	public ModelAndView getDetail(@RequestParam(value = "id", required = true) Long id, Locale locale) {
		ModelAndView mav = new ModelAndView("/views/repository/repository-detail.html");
		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		JcaRepositoryDto repositoryDto = repositoryService.getRepositoryDtoById(id);
		// set url ajax
		String url = UrlConst.REPOSITORY.concat(UrlConst.DETAIL);
		if (null != id) {
		    // Security for data detail
//	        if (null == repositoryDto || (repositoryDto.getCompanyId() != null && !UserProfileUtils.hasRoleForCompany(repositoryDto.getCompanyId()))) {
//	            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//	        }
			url = url.concat("?id=").concat(id.toString());
		}
		url = url.substring(1);
//		repositoryDto.setUrl(url);

		mav.addObject("repositoryDto", repositoryDto);
		return mav;
	}

	/**
	 * getEdit
	 *
	 * @param id
	 *            type Long
	 * @param locale
	 *            type Locale
	 * @return ModelAndView
	 * @author KhoaNA
	 */
	@RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
	public ModelAndView getEdit(@RequestParam(value = "id", required = false) Long id, Locale locale) {
		ModelAndView mav = new ModelAndView("/views/repository/repository-edit.html");
		this.initDataForEditRepository(mav, locale);
		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

 		JcaRepositoryDto repositoryDto = repositoryService.getRepositoryDtoById(id);
// 		List<JcaConstantDto> typeRepoList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind("JCA_ADMIN_REPO", "REPO_TYPE", locale.getLanguage());
// 		for(JcaConstantDto item : typeRepoList) {
// 			if(repositoryDto.getTypeRepo().equals(item.getCode())) {
// 				repositoryDto.setTypeRepoName(item.getName());
// 			}
// 		}
		// set url ajax
		String url = UrlConst.REPOSITORY.concat(UrlConst.EDIT);
		if (null != id) {
		    // Security for data 
//	        if (null == repositoryDto || !UserProfileUtils.hasRoleForCompany(repositoryDto.getCompanyId())) {
//	            return new ModelAndView(ViewConstRant.ACCESS_DENIED_MODELANDVIEW);
//	        }
			url = url.concat("?id=").concat(id.toString());
		}else {
		    repositoryDto.setCompanyId(UserProfileUtils.getCompanyId());
		}
		url = url.substring(1);
		repositoryDto.setUrl(url);
		
		repositoryDto.setPassword(repositoryDto.getPassword() != null
				? JCanaryPasswordUtil.decryptString(repositoryDto.getPassword()) : repositoryDto.getPassword());

		mav.addObject("repositoryDto", repositoryDto);
		this.initDataForEditRepository(mav, locale);
		return mav;
	}

	/**
	 * postEdit
	 *
	 * @param regionModel
	 * @param bindingResult
	 * @param locale
	 * @param redirectAttributes
	 * @param request
	 * @return ModelAndView
	 * @author KhoaNA
	 * @throws DetailException 
	 */
	@RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
	public ModelAndView postEdit(@Valid @ModelAttribute(value = "repositoryDto") JcaRepositoryDto repositoryDto,
			BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws DetailException {
		ModelAndView mav = new ModelAndView("/views/repository/repository-edit.html");
		MessageList messageList = new MessageList(Message.SUCCESS);
		Long id = repositoryDto.getId();
		String code = repositoryDto.getCode();
		// Write system logs
		if (null == id) {
			systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Add Repository",
					"Save Repository(code: " + code + ")", request);
		} else {
			systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Edit Repository",
					"Save Repository(code: " + code + ")", request);
		}

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		
		// Bug #40914
		List<JcaRepository> lstRepository = repositoryService.getListRepositoryByCodeAndCompany(repositoryDto.getCode(), repositoryDto.getCompanyId());

		if (CommonCollectionUtil.isNotEmpty(lstRepository) && lstRepository.get(0).getId() != repositoryDto.getId()) {
			this.initDataForEditRepository(mav, locale);

			// Add message error
			messageList.setStatus(Message.ERROR);
			String msgError = msg.getMessage("repository.code.has.been.use", null, locale);
			messageList.add(msgError);

			mav.addObject(ConstantCore.MSG_LIST, messageList);
			mav.addObject("repositoryDto", repositoryDto);
			return mav;
		}
		
		// Init message list
		repositoryValidator.validate(repositoryDto, bindingResult);

		// Validation
		if (bindingResult.hasErrors()) {
			this.initDataForEditRepository(mav, locale);

			// Add message error
			messageList.setStatus(Message.ERROR);
			String msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
			messageList.add(msgError);

			mav.addObject(ConstantCore.MSG_LIST, messageList);
			mav.addObject("repositoryDto", repositoryDto);
			return mav;
		}

		// Check connect
//		if (repositoryDto.getFileProtocol() == null || repositoryDto.getFileProtocol() == null)
		if (repositoryDto.getFileProtocol() != null && repositoryDto.getFileProtocol() != 1) {
			@SuppressWarnings("unused")
			String user = repositoryDto.getUsername();
			String password = repositoryDto.getPassword();

			// Get passwork from database
			if (CommonConstant.PASSWORD_ENCRYPT.equals(repositoryDto.getPassword())) {
				JcaRepositoryDto repository = repositoryService.getRepositoryDtoById(id);
				if (null != repository) {
					password = JCanaryPasswordUtil.decryptString(repository.getPassword());
				}
			}
			repositoryDto.setPassword(password);
			//TODO XINHTTM
//			boolean isConnected = repositoryService.checkConnectToRepository(repositoryDto.getPhysicalPath(), user,
//					password);
//			if (!isConnected) {
//				this.initDataForEditRepository(mav);
//				messageList.setStatus(Message.ERROR);
//				String msgError = msg.getMessage(ConstantCore.MSG_FAIL_CONNECT, null, locale);
//				messageList.add(msgError);
//				mav.addObject(ConstantCore.MSG_LIST, messageList);
//				mav.addObject("repositoryDto", repositoryDto);
//				return mav;
//			}
		}
		// Update repository
//		System.out.println("noidung"+repositoryDto);
		Long repoId = repositoryService.saveRepositoryDto(repositoryDto).getId();

		String msgInfo = CommonStringUtil.EMPTY;
		if (null != id) {
			msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
		} else {
			msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
		}
		messageList.setStatus(Message.SUCCESS);
		messageList.add(msgInfo);
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

		String viewName = UrlConst.REDIRECT.concat(UrlConst.REPOSITORY).concat(UrlConst.EDIT);
		redirectAttributes.addAttribute("id", repoId);
		mav.setViewName(viewName);
		return mav;
	}

	private void initDataForEditRepository(ModelAndView mav, Locale locale) {
////		List<ConstantDisplay> typeRepoList = constantDisplayService.findByType("M10","en");
//		Long companyId = UserProfileUtils.getCompanyId();
		
		List<JcaConstantDto> typeRepoList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind("JCA_ADMIN_REPO", "REPO_TYPE", locale.getLanguage());
		mav.addObject("typeRepoList", typeRepoList);

		List<JcaConstantDto> subFolderRuleList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind("JCA_ADMIN_REPO", "REPO_STRORAGE_TYPE", locale.getLanguage());
		mav.addObject("subFolderRuleList", subFolderRuleList);
		
		List<JcaConstantDto> fileProtocolList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind("JCA_ADMIN_REPO", "FILE_PROTOCOL", locale.getLanguage());
		mav.addObject("fileProtocol", fileProtocolList);

		List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
		mav.addObject("companyList", companyList);
	}

	/**
	 * postDelete
	 *
	 * @param repositoryId
	 * @param locale
	 * @param redirectAttributes
	 * @param request
	 * @return ModelAndView
	 * @author KhoaNA
	 */
	@RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
	public ModelAndView postDelete(@RequestParam(value = "repositoryId", required = true) Long repositoryId,
			Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		MessageList messageList = null;
		String viewName = UrlConst.REDIRECT.concat(UrlConst.REPOSITORY).concat(UrlConst.LIST);
		ModelAndView mav = new ModelAndView(viewName);
		try {
			// Write system logs
			systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Delete Repository",
					"Delete Repository(id: " + repositoryId + ")", request);

			// Security for this page.
			if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
					&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
					&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
				return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
			}

			repositoryService.deleteRepositoryById(repositoryId, locale);

			// Init message list
			messageList = new MessageList(Message.SUCCESS);
			String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
			messageList.add(msgInfo);
			redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

		} catch (Exception e) {
			messageList = new MessageList(Message.ERROR);
			messageList.add(e.getMessage());
			redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		}
		return mav;
	}
}