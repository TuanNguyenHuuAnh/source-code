/*******************************************************************************
 * Class ：ChatController Created date ：2017/05/10 Lasted date ：2017/05/10 Author ：phunghn Change log ：2017/05/10：01-00
 * phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.cms.admin.all.constant.AdminRoleConstant;
import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
import vn.com.unit.cms.admin.all.dto.ContactCommentDto;
import vn.com.unit.cms.admin.all.dto.ContactEmailDto;
import vn.com.unit.cms.admin.all.dto.ContactEmailEditDto;
//import vn.com.unit.cms.admin.all.dto.ContactEmailSearchDto;
import vn.com.unit.cms.admin.all.dto.ContactEmailStatusDto;
import vn.com.unit.cms.admin.all.dto.ContactEmailUpdateItemDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryLanguageSearchDto;
import vn.com.unit.cms.admin.all.enumdef.ContactEmailExportEnum;
import vn.com.unit.cms.admin.all.enumdef.ContactEmailStatusEnum;
import vn.com.unit.cms.admin.all.service.ContactEmailService;
import vn.com.unit.cms.admin.all.service.ProductCategoryService;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstDispType;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
//import vn.com.unit.jcanary.service.ConstantDisplayService;
//import vn.com.unit.jcanary.utils.ExportExcelUtil;
//import vn.com.unit.jcanary.utils.ImportExcelUtil;
//import vn.com.unit.util.Util;
import vn.com.unit.cms.core.module.contact.dto.ContactEmailSearchDto;

/**
 * ChatController
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Controller
@RequestMapping("contact/email")
public class ContactEmailController {

	private static final String CONTACT_EMAIL_REJECT_SUCCESS_MESSAGE = "contact.email.reject.success-message";

	private static final String CONTACT_EMAIL_DONE_SUCCESS_MESSAGE = "contact.email.action.done-status-update.sucess";
	
	private static final String CONTACT_EMAIL_PROCESS_SUCCESS_MESSAGE = "contact.email.action.processing-status-update.sucess";

	private static final Logger logger = LoggerFactory.getLogger(ContactEmailController.class);

	@Autowired
	private ContactEmailService contactEmailService;

	@Autowired
	private MessageSource msg;

	@Autowired
	ServletContext servletContext;

	@Autowired
	SystemConfig systemConfig;
	
//    @Autowired
//    ConstantDisplayService constDisplayService;
    
    @Autowired
    private ProductCategoryService productCategoryService;
    
    @Autowired
	private JcaConstantService jcaConstantService;

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

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView emailGetList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@ModelAttribute(value = "searchDto") ContactEmailSearchDto searchDto, Locale locale, Model model) {
		if (!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_EMAIL)) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		
		searchDto.setLanguage(locale.toString());
		
		ModelAndView mav = new ModelAndView("views/CMS/all/contact-email/contact-email-list.html"); // "contact.email.list";
		PageWrapper<ContactEmailDto> pageWrapper = contactEmailService.getEmailList(searchDto, page);
		
		List<ContactEmailStatusDto> statusOptions = this.loadEmailStatusList();
		String url = "contact/email".concat(AdminUrlConst.LIST);
		model.addAttribute("searchDto", searchDto);
        
        List<ProductCategoryLanguageSearchDto> lstServices = productCategoryService.getProductCategoryList(locale.toString(), -1L);
        mav.addObject("lstService", lstServices);
        
        // ${constantDisplay.cat} => ${constantDisplay.kind}
 		// #{${constantDisplay.code}} => #{${constantDisplay.code}}
 		// constDispService.findByType("M10");
 		// => List<JcaConstantDto> statusList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(), "EN");

 		// type => groupCode
 		// cat	=> kind
 		// code => code
 		
 		// catOfficialName => name
 		
 		// ConstantDisplay motive = constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString());
 		// JcaConstantDto motive = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString(), "EN").get(0);

 		// List<ConstantDisplay> listBannerPage = constDispService.findByType(ConstDispType.B01);
     	// List<JcaConstantDto> listBannerPage = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(), "EN");

//        List<ConstantDisplay> lstMotive = constDisplayService.findByType(ConstDispType.MOTIVE);
        
        List<JcaConstantDto> lstMotive = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.MOTIVE.toString(), "EN");
        
        mav.addObject("lstMotive", lstMotive);
		
		model.addAttribute("statusOptions", statusOptions);
		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		mav.addObject("pageUrl", url);
		return mav;
	}

	private List<ContactEmailStatusDto> loadEmailStatusList() {
		List<ContactEmailStatusDto> statusOptions = new ArrayList<ContactEmailStatusDto>();
		for (ContactEmailStatusEnum statusEnum : ContactEmailStatusEnum.class.getEnumConstants()) {
			ContactEmailStatusDto statusItem = new ContactEmailStatusDto();
			statusItem.setStatusName(statusEnum.getStatusName());
			statusItem.setStatusTitle(statusEnum.getStatusAlias());
			statusOptions.add(statusItem);
		}
		return statusOptions;
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	public ModelAndView emailAjaxPostList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@ModelAttribute(value = "searchDto") ContactEmailSearchDto searchDto, Locale locale, Model model) {
		if (!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_EMAIL)) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		searchDto.setLanguage(locale.toString());
		
		ModelAndView mav = new ModelAndView("contact.email.table");
		PageWrapper<ContactEmailDto> pageWrapper = contactEmailService.getEmailList(searchDto, page);
		
		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		
		return mav;
	}
	
	@RequestMapping(value = "customerChange", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxGetProductCategoryList(@RequestParam(value = "customerId", required = false) Long customerId, Locale locale) {
		
		if (customerId == null) {
			customerId = -1L;
		}
        List<ProductCategoryLanguageSearchDto> lstServices = productCategoryService.getProductCategoryList(locale.toString(), customerId);
        
        String categorySelectJson = CommonJsonUtil.convertObjectToJsonString(lstServices);
        
		return categorySelectJson;
	}

	@RequestMapping(value = "exportList", method = { RequestMethod.POST })
	@ResponseBody
	public void exportHistory(@ModelAttribute(value = "searchDto") ContactEmailSearchDto searchDto, Locale locale,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		searchDto.setLanguage(locale.toString());
		
		List<ContactEmailDto> bookingList = contactEmailService.getFullEmailList(searchDto);
		exportEmailsToExcel(bookingList, response, locale);
	}

	public void exportEmailsToExcel(List<ContactEmailDto> lstData, HttpServletResponse res, Locale locale)
			throws Exception {
		// tuanh fix so dem tu 0 ->1
		for (ContactEmailDto emailDto : lstData) {
			if (emailDto.getStatusName() != null) {
				String statusTitle = msg.getMessage(emailDto.getStatusName(), null, locale);
				emailDto.setStatusTitle(statusTitle);
				
				String patternDate = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
				SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
				
				emailDto.setCreateDateString(dateFormat.format(emailDto.getCreateDate()));
			}
		}
		String templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_CONTACTEMAIL;
		String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName;
		String datePattern = "dd/MM/yyyy  HH:mm:ss";
		List<ItemColsExcelDto> cols = new ArrayList<>();
		
		// start fill data to workbook
		ImportExcelUtil.setListColumnExcel(ContactEmailExportEnum.class, cols);
		ExportExcelUtil<ContactEmailDto> exportExcel = new ExportExcelUtil<>();
		// do export
		try{
			exportExcel.exportExcelWithXSSFNonPass(template, locale, lstData, ContactEmailDto.class,
				cols, datePattern, res, templateName);
		}catch (Exception e) {
			logger.error("" + e.getMessage());
		}
		
	}

	@RequestMapping(value = "process", method = RequestMethod.POST)
	public ModelAndView postDetailEmailProcess(@ModelAttribute(value = "editModel") ContactEmailEditDto emailEditModel,
			@RequestParam(value = "id", required = true) Long emailId, Locale locale, RedirectAttributes redirectAttributes) {
		if (!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_EMAIL.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		ModelAndView mav = new ModelAndView("contact.email.detail");
		
		contactEmailService.updateEmailToProcessing(emailEditModel, emailId, locale);
		
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgError = msg.getMessage(CONTACT_EMAIL_PROCESS_SUCCESS_MESSAGE, null, locale);
		messageList.add(msgError);
		
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("contact/email").concat(UrlConst.ROOT)
				.concat("detail");
		
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		redirectAttributes.addAttribute("id", emailId);
		
		mav.setViewName(viewName);
		
		return mav;
	}

	@RequestMapping(value = "done", method = RequestMethod.POST)
	public ModelAndView postDetailEmailFinish(@ModelAttribute(value = "editModel") ContactEmailEditDto emailEditModel,
			@RequestParam(value = "id", required = true) Long emailId, Locale locale,
			RedirectAttributes redirectAttributes) {
		if (!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_EMAIL.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		ModelAndView mav = new ModelAndView("contact.email.detail");
		
		contactEmailService.updateEmailDone(emailEditModel, emailId, locale);
		
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgError = msg.getMessage(CONTACT_EMAIL_DONE_SUCCESS_MESSAGE, null, locale);
		messageList.add(msgError);
		
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("contact/email").concat(UrlConst.ROOT)
				.concat("detail");
		
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		redirectAttributes.addAttribute("id", emailId);
		
		mav.setViewName(viewName);
		return mav;
	}

	@RequestMapping(value = "reject", method = RequestMethod.POST)
	public ModelAndView postDetailEmailReject(@ModelAttribute(value = "editModel") ContactEmailEditDto emailEditModel,
			@RequestParam(value = "id", required = true) Long emailId, Locale locale,
			RedirectAttributes redirectAttributes) {
		if (!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_EMAIL.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		ModelAndView mav = new ModelAndView("contact.email.detail");
		
		contactEmailService.rejectEmail(emailEditModel, emailId, locale);
		
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgError = msg.getMessage(CONTACT_EMAIL_REJECT_SUCCESS_MESSAGE, null, locale);
		messageList.add(msgError);
		
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("contact/email").concat(UrlConst.ROOT)
				.concat("detail");
		
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		redirectAttributes.addAttribute("id", emailId);
		
		mav.setViewName(viewName);
		return mav;
	}

	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ModelAndView emailGetDetail(@ModelAttribute(value = "editModel") ContactEmailDto emailEditModel,
			@RequestParam(value = "id", required = true) Long emailId, Locale locale) {
		if (!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_EMAIL)) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		ModelAndView mav = new ModelAndView("contact.email.detail");
		ContactEmailDto emailDetail = contactEmailService.getEmailDetail(emailId);
		List<ContactEmailUpdateItemDto> updateHistory = contactEmailService.getUpdateHistory(emailId);

		List<ContactCommentDto> commentOptions = contactEmailService.getCommentOptions();

		String url = "contact/email".concat(UrlConst.DETAIL).concat("?id=") + emailId;
		emailDetail.setUrl(url);
		mav.addObject("emailModel", emailDetail);
		mav.addObject("commentOptions", commentOptions);
		mav.addObject("updateHistory", updateHistory);
		mav.addObject("emailEditModel", emailEditModel);
		return mav;
	}

	@RequestMapping(value = "detail/delete", method = RequestMethod.POST)
	public ModelAndView emailDetailGetDelete(@RequestParam(value = "id", required = true) Long emailId, Locale locale,
			RedirectAttributes redirectAttributes) {
		if (!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_EMAIL.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		ModelAndView mav = null;
		boolean deleted = contactEmailService.deleteEmail(emailId);
		if (!deleted) {
			mav = new ModelAndView("contact.email.detail");
			String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("contact/email").concat(UrlConst.DETAIL)
					.concat("?id=").concat(String.valueOf(emailId));
			mav = new ModelAndView(viewName);
			// Init message success list
			MessageList messageList = new MessageList(Message.ERROR);
			String msgInfo = msg.getMessage("contact.email.message.could-not-delete", null, locale);
			messageList.add(msgInfo);
			redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		} else {
			String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("contact/email").concat(UrlConst.LIST);
			mav = new ModelAndView(viewName);
			// Init message success list
			MessageList messageList = new MessageList(Message.SUCCESS);
			String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
			messageList.add(msgInfo);
			redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		}
		return mav;
	}

	@RequestMapping(value = "list/delete", method = RequestMethod.POST)
	public ModelAndView emailListGetDelete(@RequestParam(value = "id", required = true) Long emailId, Locale locale,
			RedirectAttributes redirectAttributes) {
		if (!UserProfileUtils.hasRole(AdminRoleConstant.ROLE_CONTACT_EMAIL.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		ModelAndView mav = null;
		boolean deleted = contactEmailService.deleteEmail(emailId);
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("contact/email").concat(UrlConst.LIST);
		mav = new ModelAndView(viewName);
		// Init message success list
		MessageList messageList = new MessageList(Message.SUCCESS);
		if (deleted) {
			messageList.setStatus(Message.SUCCESS);
			String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
			messageList.add(msgInfo);
		} else {
			messageList.setStatus(Message.ERROR);
			String msgInfo = msg.getMessage("contact.email.message.could-not-delete", null, locale);
			messageList.add(msgInfo);
		}
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		return mav;
	}
}
