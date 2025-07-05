/*******************************************************************************
 * Class        ：ChatController
 * Created date ：2017/05/10
 * Lasted date  ：2017/05/10
 * Author       ：phunghn
 * Change log   ：2017/05/10：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

//import liquibase.pro.packaged.J;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.ep2p.core.exception.BusinessException;
//import vn.com.unit.ep2p.core.utils.NullAwareBeanUtils;
import vn.com.unit.cms.admin.all.dto.ChatControlValueDto;
import vn.com.unit.cms.admin.all.dto.ChatControlValueEditDto;
import vn.com.unit.cms.admin.all.dto.ChatControlValueLanguageDto;
import vn.com.unit.cms.admin.all.dto.ChatUserProductDto;
import vn.com.unit.cms.admin.all.dto.ChatUserProductSearchDto;
import vn.com.unit.cms.admin.all.dto.MessageDto;
import vn.com.unit.cms.admin.all.dto.RoomClientDto;
import vn.com.unit.cms.admin.all.dto.RoomClientOfflineDto;
import vn.com.unit.cms.admin.all.dto.RoomClientOfflineSearchDto;
import vn.com.unit.cms.admin.all.dto.RoomClientSearchDto;
import vn.com.unit.cms.admin.all.dto.SettingChatDto;
import vn.com.unit.cms.admin.all.dto.SettingChatEditDto;
import vn.com.unit.cms.admin.all.dto.SettingChatLanguageDto;
import vn.com.unit.cms.admin.all.dto.SocketServerDto;
import vn.com.unit.cms.admin.all.dto.UserInfoSupportDto;
import vn.com.unit.cms.admin.all.enumdef.ChatSupportStatusSearchEnum;
import vn.com.unit.cms.admin.all.enumdef.ChatUserProductSearchEnum;
import vn.com.unit.cms.admin.all.enumdef.RoleChatEnum;
import vn.com.unit.cms.admin.all.enumdef.RoomClientOfflineSearchEnum;
import vn.com.unit.cms.admin.all.enumdef.RoomClientSearchEnum;
import vn.com.unit.cms.admin.all.enumdef.SettingChatEnumType;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.RoleForChatService;
import vn.com.unit.cms.admin.all.service.RoomClientService;
import vn.com.unit.cms.admin.all.service.SocketServerService;
import vn.com.unit.cms.admin.all.util.CmsEmailUtils;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.EmailResultDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.service.AccountService;
//import vn.com.unit.ep2p.asset.entity.EmailProcess;
import vn.com.unit.core.dto.JcaEmailDto;
//import vn.com.unit.jcanary.dto.EmailDto;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.enumdef.SendEmailTypeEnum;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.ep2p.utils.SearchUtil;
//import vn.com.unit.util.Util;

/**
 * ChatController
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Controller
@RequestMapping(UrlConst.ROOT + "chat")
public class ChatController {
	private String CHAT_LIST = "chat.list";
	private String CHAT_LIST_HISTORY = "chat.list.history";
	private String CHAT_EDIT = "chat.edit";
	private String CHAT_TABLE = "chat.table";
	private String CHAT_TABLE_HISTORY = "chat.table.history";
	private String CHAT_DETAIL = "chat.detail";
	private String CHAT_DETAIL_HISTORY = "chat.detail.hisotry";
	private String CHAT_LIST_OFFLINE = "chat.list.offline";
	private String CHAT_TABLE_OFFLINE = "chat.table.offline";
	private String CHAT_CONFIG = "chat.config";
	private String CHAT_CONFIG_OFFLINE = "chat.config.offline";
	private String CHAT_CONFIG_VALUE_ADD = "chat.config.value.add";
	private String CHAT_USER_PRODUCT_ADD = "chat.user.product.add";
	private String CHAT_EDIT_CUSTOMER = "chat.edit.customer";
	private final String LIST_OFFLINE = "/list-offline";
	private final String AJAX_OFFLINE = "/ajaxList-offline";
	private final String EDIT_OFFLINE = "/edit-offline";
	private final String DETAIL_OFFLINE = "/detail-offline";
	private final String CHAT_EDIT_OFFLINE = "chat.edit.offline";
	private final String CHAT_DETAILL_OFFLINE = "chat.detail.offline";

	@Autowired
	private RoomClientService roomClientService;

	@Autowired
	private RoleForChatService roleForChatService;

	@Autowired
	private MessageSource msg;

	@Autowired
	private AccountService accountService;

//	@Autowired
//	EmailService mailservice;

	@Autowired
	CmsFileService fileService;

	@Autowired
	private LanguageService languageService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	SocketServerService socketServerService;

//	@Autowired
//	EmailService emailService;

	@Autowired
	JcaEmailService jcaEmailService;

	@Autowired
	CmsEmailUtils cmsEmailUtils;

	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

	private static final String SCREEN_FUNCTION_CODE_CHAT_USER_ROLE = CmsRoleConstant.CHAT_USER_ROLE;
	private static final String SCREEN_FUNCTION_CODE_CHAT_HISTORY_ROLE = CmsRoleConstant.CHAT_HISTORY_ROLE;

	@InitBinder
	public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
		if (null == request.getSession().getAttribute(ConstantCore.FORMAT_DATE)) {
			request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
		}
		// The date format to parse or output your dates
		String patternDate = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
		SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
	}

	/**
	 * getList
	 *
	 * @param locale
	 * @return ModelAndView
	 * @author phunghn
	 */
	@RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET })
	public ModelAndView getList(@ModelAttribute(value = "typeSearch") RoomClientSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
		ModelAndView mav = new ModelAndView(CHAT_LIST);
		try {
			if (!UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_ONLINE)) {
				return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
			}
			// search
			SearchUtil.setSearchSelect(RoomClientSearchEnum.class, mav);
			List<RoomClientDto> data = roomClientService.findAllActive(page, typeSearch, locale);

			mav.addObject("data", data);
			mav.addObject("typeSearch", typeSearch);
			mav.addObject("userOnline", roomClientService.countClientWaiting());

			String userName = UserProfileUtils.getUserNameLogin();
			JcaAccount acc = new JcaAccount();
			acc = accountService.findByUserName(userName, UserProfileUtils.getCompanyId());
			mav.addObject("account", acc);

			if (roleForChatService.isRoleAdminChat(userName)) {
				mav.addObject("isAdmin", 1);
			} else {
				mav.addObject("isAdmin", 0);
			}

			if (roleForChatService.hasRoleChat(userName)) {
				mav.addObject("agentChat", 1);
			} else {
				mav.addObject("agentChat", 0);
			}

			List<SelectItem> enabledStatus = new ArrayList<SelectItem>();
			for (ChatSupportStatusSearchEnum status : ChatSupportStatusSearchEnum.values()) {
				SelectItem item = new SelectItem(status.getCode(), status.getName());
				enabledStatus.add(item);
			}
			mav.addObject("enabledStatus", enabledStatus);

			List<ChatControlValueDto> lstService = roomClientService.getListValueControls("OCONTENT",
					locale.toString());
			mav.addObject("lstService", lstService);

		} catch (Exception e) {
			logger.error("##Chat_Controller_List##", e.getMessage());
		}
		return mav;
	}

	/**
	 * ajaxList
	 *
	 * @param typeSearch
	 * @param page
	 * @param locale
	 * @return ModelAndView
	 * @author phunghn
	 */
	@RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView ajaxList(@ModelAttribute(value = "typeSearch") RoomClientSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
		ModelAndView mav = new ModelAndView(CHAT_TABLE);
		try {
			List<RoomClientDto> data = roomClientService.findAllActive(page, typeSearch, locale);
			mav.addObject("data", data);
			mav.addObject("userOnline", roomClientService.countClientWaiting());

			String userName = UserProfileUtils.getUserNameLogin();
			JcaAccount acc = new JcaAccount();
			acc = accountService.findByUserName(userName, UserProfileUtils.getCompanyId());
			mav.addObject("account", acc);

			if (roleForChatService.isRoleAdminChat(userName)) {
				mav.addObject("isAdmin", 1);
			} else {
				mav.addObject("isAdmin", 0);
			}

			if (roleForChatService.hasRoleChat(userName)) {
				mav.addObject("agentChat", 1);
			} else {
				mav.addObject("agentChat", 0);
			}
		} catch (Exception e) {
			logger.error("##ajaxList_ChatController##", e.getMessage());
		}
		return mav;
	}

	/**
	 * Chat edit
	 */
	@RequestMapping(value = UrlConst.EDIT, method = { RequestMethod.GET })
	public ModelAndView edit(Locale locale) {
		ModelAndView mav = new ModelAndView(CHAT_EDIT);
		try {
			if (!UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_ONLINE)
					&& !UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_ONLINE.concat(ConstantCore.COLON_DISP))
					&& !UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_ONLINE.concat(ConstantCore.COLON_EDIT))) {
				return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
			}
			// get Info User and Time Login
			String userName = UserProfileUtils.getUserNameLogin();
			JcaAccount acc = new JcaAccount();
			acc = accountService.findByUserName(userName, UserProfileUtils.getCompanyId());
			mav.addObject("account", acc);
			// get message default
			String message = roomClientService.getMessageDefault(locale.toString());
			mav.addObject("message", message);
			// get timeout
			int timeOut = roomClientService.getSessionTimeout();
			mav.addObject("sessionTimeOut", timeOut);
		} catch (Exception e) {
			logger.error("##edit_ChatController##");
		}
		return mav;
	}

	@RequestMapping(value = UrlConst.DETAIL, method = { RequestMethod.GET })
	public ModelAndView detail(@RequestParam(value = "id", required = false) String id, Locale locale) {
		ModelAndView mav = new ModelAndView(CHAT_DETAIL);
		try {
			if (!UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_ONLINE)) {
				return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
			}
			List<MessageDto> list = new ArrayList<MessageDto>();
			list = roomClientService.getListMessage(id, locale);
			if (list.size() == 0) {
				MessageList messageList = new MessageList(Message.INFO);
				String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
				messageList.add(msgInfo);
				mav.addObject(ConstantCore.MSG_LIST, messageList);
			}
			mav.addObject("listMessage", list);
		} catch (Exception ex) {
			logger.error("##detail_Chat##");
		}
		return mav;
	}

	/**
	 * getList
	 *
	 * @param typeSearch
	 * @param page
	 * @param locale
	 * @return ModelAndView
	 * @author phunghn
	 */
	@RequestMapping(value = LIST_OFFLINE, method = { RequestMethod.GET })
	public ModelAndView getListClientOffline(
			@ModelAttribute(value = "typeSearch") RoomClientOfflineSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
		ModelAndView mav = new ModelAndView(CHAT_LIST_OFFLINE);
		try {
			if (!UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_OFFLINE)) {
				return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
			}
			// search
			SearchUtil.setSearchSelect(RoomClientOfflineSearchEnum.class, mav);
			// pageWrapper
			PageWrapper<RoomClientOfflineDto> pageWrapper = roomClientService.getListClientOffline(page, typeSearch,
					locale);
			mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
			mav.addObject("typeSearch", typeSearch);
		} catch (Exception ex) {
			logger.error("##getListClientOffline##", ex.getMessage());
		}
		return mav;
	}

	/**
	 * 
	 * @param typeSearch
	 * @param page
	 * @param locale
	 * @return ModelAndView
	 */
	@RequestMapping(value = AJAX_OFFLINE, method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView ajaxListOffline(@ModelAttribute(value = "typeSearch") RoomClientOfflineSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {

		ModelAndView mav = new ModelAndView(CHAT_TABLE_OFFLINE);
		PageWrapper<RoomClientOfflineDto> pageWrapper = roomClientService.getListClientOffline(page, typeSearch,
				locale);
		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		return mav;
	}

	@RequestMapping(value = "/export-list-offline", method = { RequestMethod.POST })
	@ResponseBody
	public void exportListOffline(@ModelAttribute(value = "typeSearch") RoomClientOfflineSearchDto typeSearch,
			Locale locale, HttpServletRequest request, HttpServletResponse response) throws Exception {
		roomClientService.exportListOfflineToExcel(typeSearch, response, locale);
	}

	/**
	 * 
	 * @param id
	 * @param locale
	 * @return ModelAndView
	 */
	@RequestMapping(value = EDIT_OFFLINE, method = { RequestMethod.GET })
	public ModelAndView editClientOffline(@RequestParam(value = "id", required = false) Long id, Locale locale) {
		ModelAndView mav = new ModelAndView(CHAT_EDIT_OFFLINE);
		try {
			if (!UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_OFFLINE)
					&& !UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_OFFLINE.concat(ConstantCore.COLON_DISP))
					&& !UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_OFFLINE.concat(ConstantCore.COLON_EDIT))) {
				return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
			}
			RoomClientOfflineDto item = new RoomClientOfflineDto();
			if (null != id) {
				item = roomClientService.findOneClientOffline(id);
			}
			mav.addObject("roomEdit", item);
		} catch (Exception e) {
			logger.error("##editClientOffline##", e.getMessage());
		}
		return mav;
	}

	/**
	 * 
	 * @param roomEdit
	 * @param bindingResult
	 * @param locale
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = EDIT_OFFLINE, method = { RequestMethod.POST })
	public ModelAndView sendEmailClientOffline(@Valid @ModelAttribute("recEdit") RoomClientOfflineDto roomEdit,
			BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(CHAT_EDIT_OFFLINE);
		// set message default
		MessageList messageList = new MessageList(Message.SUCCESS);
		try {
			// security for this send mail
			if (!UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_OFFLINE)
					&& !UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_OFFLINE.concat(ConstantCore.COLON_DISP))
					&& !UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_OFFLINE.concat(ConstantCore.COLON_EDIT))) {
				return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
			}

			if (bindingResult.hasErrors()) {
				// Add message error
				messageList.setStatus(Message.ERROR);
				String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale);
				messageList.add(msgError);

				mav.addObject(ConstantCore.MSG_LIST, messageList);
				return mav;
			}

			roomClientService.updateClientOffline(roomEdit);

			RoomClientOfflineDto item = new RoomClientOfflineDto();
			item = roomClientService.findOneClientOffline(roomEdit.getId());

			mav.addObject("roomEdit", item);

			// sendmail
//			EmailDto emaildto = new EmailDto();
//			emaildto.setSubject(msg.getMessage("chat.send.email.title", null, locale));
//			emaildto.setReceiveAddress(item.getEmail());
//			emaildto.setTemplateFile("support-template_" + locale.toString() + ".ftl");
//			Map<String, Object> dataFillContentMail = new HashMap<String, Object>();
//			dataFillContentMail.put("nameRecipent", item.getFullname());
//			dataFillContentMail.put("nameSender", UserProfileUtils.getFullName());
//			dataFillContentMail.put("contentMail", item.getMessageFeedback());
//			emaildto.setData(dataFillContentMail);
//			mailservice.sendEmail(emaildto);

			String subjectName = msg.getMessage("chat.send.email.title", null, locale);

			String emailTemplate = "support-template";

			String templateFile = emailTemplate + "_" + locale.toString().toLowerCase() + ".ftl";

			Map<String, Object> dataFillContentMail = new HashMap<String, Object>();
			dataFillContentMail.put("nameRecipent", item.getFullname());
			dataFillContentMail.put("nameSender", UserProfileUtils.getFullName());
			dataFillContentMail.put("contentMail", item.getMessageFeedback());

//			emailService.getContentFromTemplate(dataFillContentMail, templateFile);

			String emailContent = cmsEmailUtils.getContentFromTemplate(dataFillContentMail, templateFile);

			JcaEmailDto jcaEmailDto = new JcaEmailDto();
			jcaEmailDto.setContentType("text/html; charset=utf-8");
			jcaEmailDto.setToString(item.getEmail());
			jcaEmailDto.setToAddress(Arrays.asList(item.getEmail()));

//			if (emailCC != null && emailCC.trim().length() > 0) {
//				jcaEmailDto.setCcAddress(Arrays.asList(emailCC.split(",")));
//			}

			jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());

			jcaEmailDto.setEmailContent(emailContent);
			jcaEmailDto.setSubject(subjectName);

			@SuppressWarnings("unused")
			EmailResultDto emailResultDto = jcaEmailService.sendEmail(jcaEmailDto);

			// TODO SAVE EMAIL INTO EMAIL REPOSITORY
//			emailProcessDto.setStatus(emailResultDto.getStatusCode());
//			emailProcessDto.setStatusCode(emailResultDto.getStatusCode());
//			emailProcessDto.setTemplateEmail(templateFile);
//			emailProcessDto.setDescription(emailResultDto.getErrorMessage());
//			EmailProcess emailProcess = new EmailProcess();
//
//			NullAwareBeanUtils.copyPropertiesWONull(emailProcessDto, emailProcess);
//			emailProcessRepository.save(emailProcess);
			// sendmail

			// set message
			String msgInfo = msg.getMessage("chat.list.title.offline.edit.alert.send.send.0", null, locale);
			messageList.add(msgInfo);
			mav.addObject(ConstantCore.MSG_LIST, messageList);
		} catch (Exception e) {
			logger.error("##sendEmailClientOffline##", e.getMessage());
			// Add message error
			messageList.setStatus(Message.ERROR);
			String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale);
			messageList.add(msgError);

			mav.addObject(ConstantCore.MSG_LIST, messageList);
			return mav;
		}
		return mav;
	}

	@RequestMapping(value = DETAIL_OFFLINE, method = { RequestMethod.GET })
	public ModelAndView detailClientOffline(@RequestParam(value = "id", required = false) Long id, Locale locale) {
		ModelAndView mav = new ModelAndView(CHAT_DETAILL_OFFLINE);
		try {
//		if (!UserProfileUtils.hasRole(RoleConstant.ROLE_CHAT.concat(ConstantCore.COLON_DISP))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}
			RoomClientOfflineDto item = new RoomClientOfflineDto();
			item = roomClientService.findOneClientOffline(id);
			mav.addObject("roomEdit", item);
		} catch (Exception ex) {
			logger.error("##detailClientOffline##", ex.getMessage());
		}
		return mav;
	}

	@RequestMapping(value = "/support", method = { RequestMethod.GET })
	public ModelAndView support(@ModelAttribute(value = "userInfoSupport") UserInfoSupportDto userInfoSupport,
			@RequestParam(value = "clientid", required = false, defaultValue = "") String clientid, Locale locale,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("chat.support.list");

		try {
			if (!UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_ONLINE)
					&& !UserProfileUtils.hasRole(CmsRoleConstant.CHAT_LIST_ONLINE.concat(ConstantCore.COLON_EDIT))) {
				return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
			}
			// get Info User and Time Login
			String userName = UserProfileUtils.getUserNameLogin();
			JcaAccount acc = new JcaAccount();
			acc = accountService.findByUserName(userName, UserProfileUtils.getCompanyId());
			mav.addObject("account", acc);
			// get message default
			String message = roomClientService.getMessageDefault(locale.toString());
			if (message != null) {
				message = message.replace("@username", acc.getFullname());
			}
			mav.addObject("message", message);
			// get timeout
			int timeOut = roomClientService.getSessionTimeout();
			mav.addObject("sessionTimeOut", timeOut);
			mav.addObject("clientId", clientid);

			if (clientid != null && !clientid.equals("")) {
				userInfoSupport = roomClientService.getUserInfoSupportDto(clientid);
			}

			List<ChatControlValueDto> lstMessageDetaul = roomClientService.getListValueControls("MSG_WELCOME",
					locale.toString());

			mav.addObject("lstMessageDetaul", lstMessageDetaul);

			mav.addObject("userInfoSupport", userInfoSupport);
		} catch (Exception e) {
			logger.error("##edit_ChatController##");
		}
		return mav;
	}

	@RequestMapping(value = "/ajaxSupportUserInfo", method = { RequestMethod.POST })
	public ModelAndView ajaxSupportUserInfo(
			@ModelAttribute(value = "userInfoSupport") UserInfoSupportDto userInfoSupport,
			@RequestParam(value = "clientid", required = false, defaultValue = "") String clientid, Locale locale,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("chat.support.user.info");

		if (clientid != null && !clientid.equals("")) {
			userInfoSupport = roomClientService.getUserInfoSupportDto(clientid);
		}

		mav.addObject("userInfoSupport", userInfoSupport);
		return mav;
	}

	/**
	 * 
	 * @param request
	 * @param request2
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadImageChat", method = RequestMethod.POST)
	public @ResponseBody String uploadFileChat(MultipartHttpServletRequest request, HttpServletRequest request2,
			Model model, HttpServletResponse response) throws IOException {
		return fileService.uploadImageChat(request, request2, model, response);
	}

	/**
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam(required = true, value = "fileName") String fileName, HttpServletRequest request,
			HttpServletResponse response) {
		fileService.download(fileName, request, response);
	}

	@RequestMapping(value = "/send-mail", method = RequestMethod.POST)
	public String sendEmailClient(@RequestParam(value = "clientid", required = false) String clientid, Locale locale) {
		RoomClientDto item = new RoomClientDto();
		item = roomClientService.findOneClient(clientid);

		// sendmail
//		EmailDto emaildto = new EmailDto();
//		emaildto.setSubject(msg.getMessage("chat.send.email.title", null, locale));
//		emaildto.setReceiveAddress(item.getEmail());
//		emaildto.setTemplateFile("tks-support-template" + "_" + locale.toString() + ".ftl");
//		Map<String, Object> dataFillContentMail = new HashMap<String, Object>();
//		dataFillContentMail.put("nameRecipent", item.getFullname());
//		emaildto.setData(dataFillContentMail);
//		mailservice.sendEmail(emaildto);
		
		String subjectName = msg.getMessage("chat.send.email.title", null, locale);

		String emailTemplate = "tks-support-template";

		String templateFile = emailTemplate + "_" + locale.toString().toLowerCase() + ".ftl";

		Map<String, Object> dataFillContentMail = new HashMap<String, Object>();
		dataFillContentMail.put("nameRecipent", item.getFullname());

//		emailService.getContentFromTemplate(dataFillContentMail, templateFile);

		String emailContent = cmsEmailUtils.getContentFromTemplate(dataFillContentMail, templateFile);

		JcaEmailDto jcaEmailDto = new JcaEmailDto();
		jcaEmailDto.setContentType("text/html; charset=utf-8");
		jcaEmailDto.setToString(item.getEmail());
		jcaEmailDto.setToAddress(Arrays.asList(item.getEmail()));

//		if (emailCC != null && emailCC.trim().length() > 0) {
//			jcaEmailDto.setCcAddress(Arrays.asList(emailCC.split(",")));
//		}

		jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());

		jcaEmailDto.setEmailContent(emailContent);
		jcaEmailDto.setSubject(subjectName);

		@SuppressWarnings("unused")
		EmailResultDto emailResultDto = jcaEmailService.sendEmail(jcaEmailDto);

		// TODO SAVE EMAIL INTO EMAIL REPOSITORY
//		emailProcessDto.setStatus(emailResultDto.getStatusCode());
//		emailProcessDto.setStatusCode(emailResultDto.getStatusCode());
//		emailProcessDto.setTemplateEmail(templateFile);
//		emailProcessDto.setDescription(emailResultDto.getErrorMessage());
//		EmailProcess emailProcess = new EmailProcess();
//
//		NullAwareBeanUtils.copyPropertiesWONull(emailProcessDto, emailProcess);
//		emailProcessRepository.save(emailProcess);
		// sendmail

		return "0";
	}

	@RequestMapping(value = "/config", method = { RequestMethod.GET })
	public ModelAndView config(Locale locale) {
		ModelAndView mav = new ModelAndView(CHAT_CONFIG);

		/* taitm - Phân quyền màn hình */
		if (!hasRoleAccessChatConfig()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		/* END */

		roomClientService.initScreenSettingForm(mav);
		// get online form
		List<SettingChatLanguageDto> listSettingChatLanguage = new ArrayList<SettingChatLanguageDto>();
		listSettingChatLanguage = roomClientService
				.getSettingFormOnline(Integer.parseInt(SettingChatEnumType.ONLINE.toString()));

		SettingChatEditDto settingDto = new SettingChatEditDto();
		settingDto.setListSettingLanguageChat(listSettingChatLanguage);
		mav.addObject("settingDto", settingDto);
		return mav;
	}

	@RequestMapping(value = "/config-add-value", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView configAddValue(@RequestParam(value = "field") String field,
			@RequestParam(value = "controlCode") String controlCode, @RequestParam(value = "values") String values,
			Locale locale) throws JsonParseException, JsonMappingException, IOException {

		if (!hasRoleAccessChatConfig() && !hasRoleAccessChatConfigOffline()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(CHAT_CONFIG_VALUE_ADD);
		ChatControlValueEditDto valuesDto = new ChatControlValueEditDto();
		// convert values jsonto list
		List<ChatControlValueLanguageDto> controlValues = new ArrayList<ChatControlValueLanguageDto>();
		ObjectMapper mapper = new ObjectMapper();
		if (values.length() > 0) {
			controlValues = mapper.readValue(values, new TypeReference<List<ChatControlValueLanguageDto>>() {
			});
		}
		if (controlValues == null) {
			// init data
			controlValues = new ArrayList<ChatControlValueLanguageDto>();
			List<LanguageDto> languageList = languageService.getLanguageDtoList();
			for (LanguageDto itemLang : languageList) {
				ChatControlValueLanguageDto itemAdd = new ChatControlValueLanguageDto();
				itemAdd.setmLanguageCode(itemLang.getCode());
				itemAdd.setListControlValue(new ArrayList<ChatControlValueDto>());
				controlValues.add(itemAdd);
			}
		}
		valuesDto.setControlValues(controlValues);
		valuesDto.setField(field);
		valuesDto.setControlCode(controlCode);
		mav.addObject("valuesDto", valuesDto);
		mav.addObject("languageList", languageService.getLanguageDtoList());
		return mav;
	}

	@RequestMapping(value = "/config-add-value-field", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView configAddValueField(@ModelAttribute(value = "valuesDto") ChatControlValueEditDto valuesDto,
			Locale locale) {

		if (!hasRoleEditChatConfig() && !hasRoleEditChatConfigOffline()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(CHAT_CONFIG_VALUE_ADD);
		if (valuesDto == null) {
			valuesDto = new ChatControlValueEditDto();
		}
		List<ChatControlValueLanguageDto> values = new ArrayList<ChatControlValueLanguageDto>();
		values = roomClientService.insertRowControlaValue(valuesDto.getControlValues(), valuesDto.getField());
		if (valuesDto.getControlValues() == null) {
			valuesDto.setControlValues(new ArrayList<ChatControlValueLanguageDto>());
		}
		valuesDto.setControlValues(values);
		mav.addObject("valuesDto", valuesDto);
		mav.addObject("languageList", languageService.getLanguageDtoList());
		return mav;
	}

	@RequestMapping(value = "/config-delete-value-field", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView configDeleteValueField(@RequestParam(value = "id") String id,
			@RequestParam(value = "dataJsonValue") String dataJsonValue, Locale locale)
			throws JsonParseException, JsonMappingException, IOException {

		if (!hasRoleEditChatConfig() && !hasRoleEditChatConfigOffline()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(CHAT_CONFIG_VALUE_ADD);
		ChatControlValueEditDto valuesDto = new ChatControlValueEditDto();
		ObjectMapper mapper = new ObjectMapper();
		//
		valuesDto = mapper.readValue(dataJsonValue, ChatControlValueEditDto.class);

		for (ChatControlValueLanguageDto itemControlValueLang : valuesDto.getControlValues()) {
			// remove item by language
			List<ChatControlValueDto> listControlValue = new ArrayList<ChatControlValueDto>();
			for (ChatControlValueDto itemControlDto : itemControlValueLang.getListControlValue()) {
				if (itemControlDto.getId() != Integer.parseInt(id)) {
					listControlValue.add(itemControlDto);
				}
			}
			// set
			itemControlValueLang.setListControlValue(listControlValue);
		}
		mav.addObject("valuesDto", valuesDto);
		mav.addObject("languageList", languageService.getLanguageDtoList());
		return mav;
	}

	@RequestMapping(value = "/generate-json-value-form", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String generateJsonValueForm(@ModelAttribute(value = "valuesDto") ChatControlValueEditDto valuesDto,
			Locale locale) throws JsonParseException, JsonMappingException, IOException {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		result = mapper.writeValueAsString(valuesDto);
		return result;
	}

	@RequestMapping(value = "/generate-json-setting-form", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String generateJsonSettingForm(@ModelAttribute(value = "settingDto") SettingChatEditDto settingDto,
			Locale locale) throws JsonParseException, JsonMappingException, IOException {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		result = mapper.writeValueAsString(settingDto);
		return result;
	}

	@RequestMapping(value = "/config-add-row", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView configAddRow(@ModelAttribute(value = "settingDto") SettingChatEditDto settingDto,
			BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes)
			throws JsonParseException, JsonMappingException, IOException {

		if (!hasRoleEditChatConfig() && !hasRoleEditChatConfigOffline()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(CHAT_CONFIG);
		roomClientService.initScreenSettingForm(mav);
		// visible
		for (SettingChatLanguageDto item : settingDto.getListSettingLanguageChat()) {
			for (SettingChatDto itemDto : item.getListControls()) {
				if (itemDto.getIsVisible() == false) {
					itemDto.setIsVisible(true);
					break;
				}
			}
		}
		mav.addObject("settingDto", settingDto);
		return mav;
	}

	@RequestMapping(value = "/config", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView ajaxPostConfig(@ModelAttribute(value = "settingDto") SettingChatEditDto settingDto,
			BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes)
			throws JsonParseException, JsonMappingException, IOException {

		if (!hasRoleEditChatConfig()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(CHAT_CONFIG);
		MessageList messageList = new MessageList(Message.SUCCESS);
		ObjectMapper mapper = new ObjectMapper();
		//
		SettingChatEditDto jsonSettingDto = new SettingChatEditDto();

		String jsonDto = settingDto.getJsonDto();
		jsonSettingDto = mapper.readValue(jsonDto, SettingChatEditDto.class);
		//
		int result = roomClientService.EditConfig(jsonSettingDto, settingDto,
				Integer.parseInt(SettingChatEnumType.ONLINE.toString()));
		if (result == 0) {
			messageList.setStatus(Message.ERROR);
			String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale);
			messageList.add(msgError);
			mav.addObject(ConstantCore.MSG_LIST, messageList);
		} else {
			String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
			messageList.add(msgInfo);
			mav.addObject(ConstantCore.MSG_LIST, messageList);
		}
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("chat").concat("/config");
		mav.setViewName(viewName);
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		return mav;
	}

	@RequestMapping(value = "/config-offline", method = { RequestMethod.GET })
	public ModelAndView configOffline(Locale locale) {
		ModelAndView mav = new ModelAndView(CHAT_CONFIG_OFFLINE);
		if (!hasRoleAccessChatConfigOffline()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		roomClientService.initScreenSettingForm(mav);
		List<SettingChatLanguageDto> listSettingChatLanguage = new ArrayList<SettingChatLanguageDto>();
		listSettingChatLanguage = roomClientService
				.getSettingFormOffline(Integer.parseInt(SettingChatEnumType.OFFLINE.toString()));
		SettingChatEditDto settingDto = new SettingChatEditDto();
		settingDto.setListSettingLanguageChat(listSettingChatLanguage);
		mav.addObject("settingDto", settingDto);
		return mav;
	}

	@RequestMapping(value = "/config-offline", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView ajaxPostConfigOffline(@ModelAttribute(value = "settingDto") SettingChatEditDto settingDto,
			BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes)
			throws JsonParseException, JsonMappingException, IOException {

		if (!hasRoleEditChatConfigOffline()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(CHAT_CONFIG_OFFLINE);
		MessageList messageList = new MessageList(Message.SUCCESS);
		ObjectMapper mapper = new ObjectMapper();
		//
		SettingChatEditDto jsonSettingDto = new SettingChatEditDto();
		String jsonDto = settingDto.getJsonDto();
		jsonSettingDto = mapper.readValue(jsonDto, SettingChatEditDto.class);
		//
		int result = roomClientService.EditConfig(jsonSettingDto, settingDto,
				Integer.parseInt(SettingChatEnumType.OFFLINE.toString()));
		if (result == 0) {
			messageList.setStatus(Message.ERROR);
			String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale);
			messageList.add(msgError);
			mav.addObject(ConstantCore.MSG_LIST, messageList);
		} else {
			String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
			messageList.add(msgInfo);
			mav.addObject(ConstantCore.MSG_LIST, messageList);
		}
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("chat").concat("/config-offline");
		mav.setViewName(viewName);
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		return mav;
	}

	@RequestMapping(value = "/add-user-product", method = { RequestMethod.GET })
	public ModelAndView addUserProduct(@ModelAttribute(value = "typeSearch") ChatUserProductSearchDto typeSearch,
			@RequestParam(value = "roleName", required = false, defaultValue = "-1") String roleName, Locale locale) {
		ModelAndView mav = new ModelAndView(CHAT_USER_PRODUCT_ADD);
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CHAT_USER_ROLE)
				&& !UserProfileUtils.hasRole(CmsRoleConstant.ROLE_CHAT.concat(ConstantCore.COLON_DISP))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		ChatUserProductDto chatUserProductDto = new ChatUserProductDto();
		chatUserProductDto.setFieldSearch(typeSearch.getFieldSearch());
		chatUserProductDto.setFieldValues(typeSearch.getFieldValues());

		List<SelectItem> roles = SearchUtil.getSearchSelect(RoleChatEnum.class);

		mav.addObject("roles", roles);
		mav.addObject("chatUserProductDto", chatUserProductDto);
		mav.addObject("users", roomClientService.getListUser());
		mav.addObject("roleName", roleName);
		return mav;
	}

	@RequestMapping(value = "/get-list-user-role", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getListUserRole(@RequestParam(value = "role") String role, Locale locale)
			throws JsonGenerationException, JsonMappingException, IOException {
		String result = "";
		try {
			List<ChatUserProductDto> list = new ArrayList<ChatUserProductDto>();
			list = roleForChatService.getListUserByRole(role);

			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(list);

		} catch (JsonProcessingException e) {
			throw new EmptyStackException();
		}
		return result;
	}

	@RequestMapping(value = "/save-user-product", method = RequestMethod.POST)
	@ResponseBody
	public String saveUserProduct(@RequestParam(value = "user") String user, @RequestParam(value = "role") String role,
			Locale locale) {
		int rs = roleForChatService.saveRoleForChat(role, user);
		return String.valueOf(rs);
	}

	@RequestMapping(value = "/list-user-product", method = { RequestMethod.GET })
	public ModelAndView getListUserProduct(@ModelAttribute(value = "typeSearch") ChatUserProductSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
		ModelAndView mav = new ModelAndView("chat.user.product.list");
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CHAT_USER_ROLE)) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		// search
		SearchUtil.setSearchSelect(ChatUserProductSearchEnum.class, mav);
		//
		PageWrapper<ChatUserProductDto> pageWrapper = roleForChatService.getListUserRole(page, typeSearch, locale);

		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		mav.addObject("typeSearch", typeSearch);

		ChatUserProductDto chatUserProductDto = new ChatUserProductDto();
		chatUserProductDto.setFieldSearch(typeSearch.getFieldSearch());
		chatUserProductDto.setFieldValues(typeSearch.getFieldValues());

		List<SelectItem> roles = SearchUtil.getSearchSelect(RoleChatEnum.class);

		mav.addObject("roles", roles);
		mav.addObject("chatUserProductDto", chatUserProductDto);
		mav.addObject("users", roomClientService.getListUser());

		return mav;
	}

	@RequestMapping(value = "/ajax-list-user-product", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView ajaxListUserProduct(@ModelAttribute(value = "typeSearch") ChatUserProductSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {

		ModelAndView mav = new ModelAndView("chat.user.product.table");

		PageWrapper<ChatUserProductDto> pageWrapper = roleForChatService.getListUserRole(page, typeSearch, locale);

		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		return mav;
	}

	@RequestMapping(value = "/delete-user-product", method = { RequestMethod.POST })
	public ModelAndView deleteUserProduct(@RequestParam(value = "id", required = true) Long id, Locale locale,
			RedirectAttributes redirectAttributes) {

		if (id <= 0) {
			throw new BusinessException("illegal data: id");
		}
		roomClientService.deleteProduct(id);
		ModelAndView mav = new ModelAndView("chat.user.product.list");
		// Init message success list
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
		messageList.add(msgInfo);
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		// redirect
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("chat/list-user-product");
		mav.setViewName(viewName);
		return mav;

	}

	/**
	 * getList
	 *
	 * @param locale
	 * @return ModelAndView
	 * @author phunghn
	 */
	@RequestMapping(value = "/history", method = { RequestMethod.GET })
	public ModelAndView getListHistory(@ModelAttribute(value = "typeSearch") RoomClientSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
		ModelAndView mav = new ModelAndView(CHAT_LIST_HISTORY);
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CHAT_HISTORY_ROLE)) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		// search
		SearchUtil.setSearchSelect(RoomClientSearchEnum.class, mav);
		//
		PageWrapper<RoomClientDto> pageWrapper = roomClientService.findHistoryAllActive(page, typeSearch, locale);

		JcaAccount acc = new JcaAccount();
		acc = accountService.findByUserName(UserProfileUtils.getUserNameLogin(), UserProfileUtils.getCompanyId());
		mav.addObject("account", acc);

		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		mav.addObject("typeSearch", typeSearch);

		if (roleForChatService.isRoleAdminChat(UserProfileUtils.getUserNameLogin())) {
			mav.addObject("isAdmin", 1);
		} else {
			mav.addObject("isAdmin", 0);
		}

		return mav;
	}

	@RequestMapping(value = "/ajax-history-list", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView ajaxHistoryList(@ModelAttribute(value = "typeSearch") RoomClientSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {

		ModelAndView mav = new ModelAndView(CHAT_TABLE_HISTORY);
		PageWrapper<RoomClientDto> pageWrapper = roomClientService.findHistoryAllActive(page, typeSearch, locale);
		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);

		JcaAccount acc = new JcaAccount();
		acc = accountService.findByUserName(UserProfileUtils.getUserNameLogin(), UserProfileUtils.getCompanyId());
		mav.addObject("account", acc);

		if (roleForChatService.isRoleAdminChat(UserProfileUtils.getUserNameLogin())) {
			mav.addObject("isAdmin", 1);
		} else {
			mav.addObject("isAdmin", 0);
		}

		return mav;
	}

	@RequestMapping(value = "/history/detail", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView detailSupport(@RequestParam(value = "agent", required = true) String agent,
			@RequestParam(value = "clientid", required = false) String clientid, Locale locale) {

		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CHAT_HISTORY_ROLE)) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		boolean isAdminChat = roleForChatService.isRoleAdminChat(UserProfileUtils.getUserNameLogin());

		if (agent != null && !agent.equals(UserProfileUtils.getUserNameLogin()) && !isAdminChat) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(CHAT_DETAIL_HISTORY);

		List<RoomClientDto> lstContact = new ArrayList<>();
		List<RoomClientDto> lstContactHistory = new ArrayList<>();

		if (agent.equals("undefined")) {
			lstContact = roomClientService.getAllRoomClientByAgent(null, new Date());
			lstContactHistory = roomClientService.getAllRoomClientByAgent(null, null);
		} else {
			lstContact = roomClientService.getAllRoomClientByAgent(agent, new Date());
			lstContactHistory = roomClientService.getAllRoomClientByAgent(agent, null);
		}

		mav.addObject("lstContact", CommonJsonUtil.convertObjectToJsonString(lstContact));

		mav.addObject("lstContactHistory", CommonJsonUtil.convertObjectToJsonString(lstContactHistory));

		JcaAccount acc = new JcaAccount();
		acc = accountService.findByUserName(agent, UserProfileUtils.getCompanyId());

		if (acc == null) {
			acc = new JcaAccount();
		}

		mav.addObject("account", acc);
		mav.addObject("agent", agent);
		mav.addObject("clientid", clientid);

		return mav;
	}

	@RequestMapping(value = "/getInfoUser", method = { RequestMethod.GET })
	@ResponseBody
	public RoomClientDto getInfoUser(@RequestParam("clientid") String clientid) {
		RoomClientDto result = roomClientService.findOneClient(clientid);
		return result;
	}

	@RequestMapping(value = "/getMessageClient", method = { RequestMethod.GET })
	@ResponseBody
	public List<MessageDto> getMessageClient(@RequestParam("clientid") String clientid, Locale locale) {
		List<MessageDto> result = roomClientService.getListMessage(clientid, locale);
		return result;
	}

	@RequestMapping(value = "/saveUnreadMessage", method = { RequestMethod.POST })
	@ResponseBody
	public void saveUnreadMessage(@RequestParam("clientid") String clientid, @RequestParam("number") Integer number,
			Locale locale) {

		roomClientService.saveUnreadMessage(clientid, number);
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/getUnreadMessage", method = { RequestMethod.GET })
	@ResponseBody
	public String getUnreadMessage(@RequestParam("clientid") String clientid, Locale locale) {
		Integer number = roomClientService.getUnreadMessage(clientid);
		MessageDto messageDto = roomClientService.getLastMessage(clientid, 1);

		String timeSend = "";
		String message = "";
		if (messageDto != null) {
			message = messageDto.getMessage();
			timeSend = messageDto.getCreatedDate().getHours() + ":" + messageDto.getCreatedDate().getMinutes();
		}

		String result = "{\"message\":\"" + message + "\",\"numberUnread\":" + number + ",\"timeSend\":\"" + timeSend
				+ "\"}";

		return result;
	}

	@RequestMapping(value = "/export-history", method = { RequestMethod.POST })
	@ResponseBody
	public void exportHistory(@ModelAttribute(value = "typeSearch") RoomClientSearchDto typeSearch, Locale locale,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		roomClientService.exportHistoryToExcel(typeSearch, response, locale);
	}

	@RequestMapping(value = "/edit-customer", method = { RequestMethod.GET })
	public ModelAndView editCustomer(@Valid @ModelAttribute("RoomDto") RoomClientDto roomEdit,
			@RequestParam(value = "clientid", required = false) String id, Locale locale) {
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CHAT_HISTORY_ROLE)) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		ModelAndView mav = new ModelAndView(CHAT_EDIT_CUSTOMER);
		RoomClientDto dto = roomClientService.findOneClient(id);
		if (null != roomEdit) {
			dto.setFieldSearch(roomEdit.getFieldSearch());
			dto.setFieldValues(roomEdit.getFieldValues());
			dto.setFromDate(roomEdit.getFromDate());
			dto.setToDate(roomEdit.getToDate());
		}
		mav.addObject("RoomDto", dto);
		return mav;
	}

	@RequestMapping(value = "/edit-customer", method = { RequestMethod.POST })
	public ModelAndView postEditCustomer(@Valid @ModelAttribute("RoomDto") RoomClientDto roomEdit,
			BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes) {
		// security for this send mail
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE_CHAT_HISTORY_ROLE)) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(CHAT_EDIT_CUSTOMER);

		// set message default
		MessageList messageList = new MessageList(Message.SUCCESS);
		if (bindingResult.hasErrors()) {

			// Add message error
			messageList.setStatus(Message.ERROR);
			String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale);
			messageList.add(msgError);

			mav.addObject(ConstantCore.MSG_LIST, messageList);
			return mav;
		}
		roomClientService.updateInfoCustomer(roomEdit);
		RoomClientDto dto = roomClientService.findOneClient(roomEdit.getClientid());

		if (null != roomEdit) {
			dto.setFieldSearch(roomEdit.getFieldSearch());
			dto.setFieldValues(roomEdit.getFieldValues());
			dto.setFromDate(roomEdit.getFromDate());
			dto.setToDate(roomEdit.getToDate());
		}

		mav.addObject("RoomDto", dto);
		String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
		messageList.add(msgInfo);
		mav.addObject(ConstantCore.MSG_LIST, messageList);
		return mav;
	}

	/**
	 * getList
	 *
	 * @param locale
	 * @return ModelAndView
	 * @author phunghn
	 */
	@RequestMapping(value = "/user-online/list", method = { RequestMethod.GET })
	public ModelAndView getListUserOnline(@ModelAttribute(value = "typeSearch") RoomClientSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
		ModelAndView mav = new ModelAndView("chat.user.online.list");
		try {
			if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_USER_ONLINE)) {
				return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
			}

			List<SocketServerDto> data = socketServerService.getListUserOnline();
			mav.addObject("data", data);

		} catch (Exception e) {
			logger.error("##Chat_Controller_List##", e.getMessage());
		}
		return mav;
	}

	/**
	 * ajaxList
	 *
	 * @param typeSearch
	 * @param page
	 * @param locale
	 * @return ModelAndView
	 * @author phunghn
	 */
	@RequestMapping(value = "/user-online/ajaxList", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView ajaxListUserOnline(@ModelAttribute(value = "typeSearch") RoomClientSearchDto typeSearch,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
		ModelAndView mav = new ModelAndView("chat.user.online.table");
		try {
			if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_USER_ONLINE)) {
				return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
			}

			List<SocketServerDto> data = socketServerService.getListUserOnline();
			mav.addObject("data", data);
		} catch (Exception e) {
			logger.error("##ajaxList_ChatController##", e.getMessage());
		}
		return mav;
	}

	private boolean hasRoleAccessChatConfig() {
		if (!UserProfileUtils.hasRole(CmsRoleConstant.CHAT_CONFIG_ONLINE)) {
			return false;
		}

		return true;
	}

	private boolean hasRoleEditChatConfig() {
		if (!UserProfileUtils.hasRole(CmsRoleConstant.CHAT_CONFIG_ONLINE.concat(ConstantCore.COLON_EDIT))) {
			return false;
		}

		return true;
	}

	private boolean hasRoleAccessChatConfigOffline() {
		if (!UserProfileUtils.hasRole(CmsRoleConstant.CHAT_CONFIG_OFFLINE)) {
			return false;
		}

		return true;
	}

	private boolean hasRoleEditChatConfigOffline() {
		if (!UserProfileUtils.hasRole(CmsRoleConstant.CHAT_CONFIG_OFFLINE.concat(ConstantCore.COLON_EDIT))) {
			return false;
		}

		return true;
	}

}
