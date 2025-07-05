/***************************************************************
 * @author vunt					
 * @date Apr 7, 2021	
 * @project mbal-webapp
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.ConstantDeleteDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaConstantSearchDto;
import vn.com.unit.core.dto.JcaGroupConstantDto;
import vn.com.unit.core.enumdef.param.JcaConstantSearchEnum;
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
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.enumdef.EmailSearchEnum;
import vn.com.unit.ep2p.export.util.SearchUtil;

@Controller
@RequestMapping(UrlConst.CONSTANT_DISPLAY)
public class JcaConstantController {
	@Autowired
	private ConstantDisplayService service;

	@Autowired
	private MessageSource msg;

	@Autowired
	private JcaConstantService jcaConstantService;

	@Autowired
	private SystemConfig systemConfig;

	// @Autowired
	// private CommonService comService;

	@Autowired
	CompanyService companyService;

//    private static final String MESSAGE_VIEW = "/views/commons/message-alert.html";

	private static final String LIST = "/views/constant-display/constant-display-list.html";

	private static final String TABLE = "/views/constant-display/constant-display-table.html";

//    private static final String DETAIL = "/views/constant-display/constant-display-detail.html";
	private static final String EDIT = "/views/constant-display/constant-display-edit.html";

	/** SCREEN_FUNCTION_CODE */

//    private static final String ROLE_SCREEN = RoleConstant.SCREEN_JCA_CONSTANT;
//    private static final String ROLE_ADD = RoleConstant.BUTTON_JCA_CONSTANT_ADD;
//    private static final String ROLE_EDIT = RoleConstant.BUTTON_JCA_CONSTANT_EDIT;
	private static final String ROLE_DELETE = RoleConstant.BUTTON_JCA_CONSTANT_DELETE;
//    private static final String ROLE_EXPORT = RoleConstant.BUTTON_JCA_CONSTANT_EXPORT;

//	private static final Logger logger = LoggerFactory.getLogger(JcaConstantController.class);

	@InitBinder
	public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
		request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
		// The date format to parse or output your dates
		String patternDate = (String) request.getSession().getAttribute(ConstantCore.FORMAT_DATE);
		SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
	}

	@GetMapping(value = UrlConst.LIST)
	public ModelAndView getPostList(@ModelAttribute(value = "searchDto") JcaConstantSearchDto searchDto,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam, Locale locale)
			throws DetailException {
		ModelAndView mav = new ModelAndView(LIST);
		// Security for this page.
//        if (!UserProfileUtils.hasRole(ROLE_SCREEN)
//                && !UserProfileUtils.hasRole(ROLE_SCREEN.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(ROLE_SCREEN.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
		// set init search
		SearchUtil.setSearchSelect(JcaConstantSearchEnum.class, mav);

		// init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));

		PageWrapper<JcaConstantDto> pageWrapper = service.doSearch(page, searchDto, pageSize);
		mav.addObject("pageWrapper", pageWrapper);
		mav.addObject("searchDto", searchDto);
		return mav;
	}

	@PostMapping(value = UrlConst.AJAXLIST)
	@ResponseBody
	public ModelAndView ajaxListEmail(@ModelAttribute(value = "searchDto") JcaConstantSearchDto searchDto,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam, Locale locale)
			throws DetailException {
		ModelAndView mav = new ModelAndView(TABLE);
		// Security for this page.
//        if (!UserProfileUtils.hasRole(ROLE_SCREEN)
//                && !UserProfileUtils.hasRole(ROLE_SCREEN.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(ROLE_SCREEN.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
		// set init search
		SearchUtil.setSearchSelect(EmailSearchEnum.class, mav);

		// init page size
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		PageWrapper<JcaConstantDto> pageWrapper = service.doSearch(page, searchDto, pageSize);
		mav.addObject("pageWrapper", pageWrapper);
		mav.addObject("searchDto", searchDto);
		return mav;
	}

	@GetMapping(value = UrlConst.ADD)
	public ModelAndView getAdd(Locale locale) {
		ModelAndView mav = new ModelAndView(EDIT);
//        if (!UserProfileUtils.hasRole(ROLE_ADD) 
//                && !UserProfileUtils.hasRole(ROLE_ADD.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(ROLE_ADD.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
		String url = "constant-display".concat(UrlConst.ADD);
		JcaGroupConstantDto dto = new JcaGroupConstantDto();
		List<JcaConstantDto> lstDisplayFlag = jcaConstantService
				.getListJcaConstantDtoByGroupCodeAndKind("JCA_ADMIN_CHECKBOX", "CHECKBOX_ACTIVED", "EN");
		mav.addObject("lstDisplayFlag", lstDisplayFlag);
		mav.addObject("url", url);
		mav.addObject("dto", dto);
		mav.addObject("action", 1);
		return mav;
	}

	@GetMapping(value = UrlConst.EDIT)
	public ModelAndView getEdit(@RequestParam(value = "groupCode", required = false) String groupCode,
			@RequestParam(value = "action", required = false) String action, Locale locale) {
		ModelAndView mav = new ModelAndView(EDIT);
//        if (!UserProfileUtils.hasRole(ROLE_EDIT) 
//                && !UserProfileUtils.hasRole(ROLE_EDIT.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(ROLE_EDIT.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
		JcaGroupConstantDto dto = jcaConstantService.getAllJcaConstantDtoByGroupCode(groupCode);
		// set url ajax
		// URL ajax redirect
		String url = "constant-display".concat(UrlConst.EDIT);
		if (ObjectUtils.isNotEmpty(dto)) {
			url = url.concat("?groupCode=").concat(groupCode == null ? "" : groupCode);
			mav.addObject("action", action);
		} else {
			dto = new JcaGroupConstantDto();
		}
		List<JcaConstantDto> lstDisplayFlag = jcaConstantService
				.getListJcaConstantDtoByGroupCodeAndKind("JCA_ADMIN_CHECKBOX", "CHECKBOX_ACTIVED", "EN");
		mav.addObject("lstDisplayFlag", lstDisplayFlag);
		mav.addObject("url", url);
		mav.addObject("dto", dto);

		// Add company list
		return mav;
	}

	@RequestMapping(value = "/doSave", method = RequestMethod.POST, produces = "application/json")
	public ModelAndView saveConstantDisplay(@RequestBody JcaGroupConstantDto jcaGroupConstantDto, Locale locale,
			BindingResult bindingResult, final RedirectAttributes redirectAttributes) throws ParseException {

		ModelAndView mav = new ModelAndView(EDIT);
		// Security for this page.
//        if (!UserProfileUtils.hasRole(ROLE_EDIT) 
//                && !UserProfileUtils.hasRole(ROLE_EDIT.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(ROLE_EDIT.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }

		// Init message list
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgInfo = StringUtils.EMPTY;
		// Validate business
		// validator.validate(dto, bindingResult);

		String groupCode = jcaGroupConstantDto.getCode();

		// Validation
		if (bindingResult.hasErrors()) {
			// Add message error
			messageList.setStatus(Message.ERROR);
			String msgError = null;
			if (StringUtils.isNotEmpty(groupCode)) {
				mav.addObject("action", 2);
				msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
			} else {
				msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
			}
			messageList.add(msgError);
			messageList.add(msgInfo);
			mav.addObject(ConstantCore.MSG_LIST, messageList);
			mav.addObject("dto", jcaGroupConstantDto);

			return mav;
		}

		try {
			// Update
			service.submit(jcaGroupConstantDto);

			if (StringUtils.isNotEmpty(groupCode)) {
				msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
			} else {
				msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
			}
			messageList.add(msgInfo);
			redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		} catch (Exception e) {
			messageList.setStatus(Message.ERROR);
			if (StringUtils.isNotEmpty(groupCode)) {

				mav.addObject("action", 2);
				msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
			} else {
				msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
			}

			List<JcaConstantDto> lstDisplayFlag = jcaConstantService
					.getListJcaConstantDtoByGroupCodeAndKind("JCA_ADMIN_CHECKBOX", "CHECKBOX_ACTIVED", "EN");
			mav.addObject("lstDisplayFlag", lstDisplayFlag);

			messageList.add(msgInfo);
			mav.addObject(ConstantCore.MSG_LIST, messageList);
			mav.addObject("dto", jcaGroupConstantDto);
			return mav;
		}

		// Redirect
		String viewName = UrlConst.REDIRECT.concat("/constant-display").concat(UrlConst.EDIT);
		mav.setViewName(viewName);
		redirectAttributes.addAttribute("groupCode", groupCode);
		redirectAttributes.addAttribute("action", 2);

		return mav;
	}

	@RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
	public ModelAndView postDelete(@RequestParam(value = "groupCode", required = true) String groupCode, Locale locale,
			@RequestParam(value = "kind", required = false) String kind,
			@RequestParam(value = "code", required = false) String code, RedirectAttributes redirectAttributes)
			throws Exception {
		// Security for this page.
//        if (!UserProfileUtils.hasRole(ROLE_DELETE) 
//                && !UserProfileUtils.hasRole(ROLE_DELETE.concat(ConstantCore.COLON_DISP))
//                && !UserProfileUtils.hasRole(ROLE_DELETE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
		JcaGroupConstantDto dto = new JcaGroupConstantDto();
//        dto = accountTeamService.getJcaTeamDtoById(id);
//        accountTeamService.deleteTeamDto(dto.getTeamId());
		jcaConstantService.doDeleteConstantByCodeAndKind(code, groupCode, kind);

		// Init message list
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
		messageList.add(msgInfo);
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

		// Delete success redirect list page
		String viewName = UrlConst.REDIRECT.concat("/constant-display").concat(UrlConst.LIST);
		ModelAndView mav = new ModelAndView(viewName);
		return mav;
	}

	@RequestMapping(value = UrlConst.DELETE_ITEM, method = RequestMethod.POST)
	public ModelAndView postDeleteItem(@RequestParam(value = "groupCode", required = true) String groupCode,
			Locale locale, @RequestParam(value = "kind", required = false) String kind,
			@RequestParam(value = "code", required = false) String code, RedirectAttributes redirectAttributes)
			throws Exception {

		String deleteItem = jcaConstantService.validateDeleteConstantByCodeAndKind(code, groupCode, kind);
		
		if (deleteItem == "") {
			MessageList messageList = new MessageList(Message.SUCCESS);
			String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
			messageList.add(msgInfo);
			redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

		} else {
			MessageList messageList = new MessageList(Message.ERROR);
			String msgInfo = msg.getMessage(deleteItem, null, locale);
			messageList.add(msgInfo);
			redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
			messageList.setStatus(Message.ERROR);
		}
		
		String viewName = UrlConst.REDIRECT.concat("/constant-display").concat(UrlConst.LIST);
		ModelAndView mav = new ModelAndView(viewName);
		return mav;

	}
}
