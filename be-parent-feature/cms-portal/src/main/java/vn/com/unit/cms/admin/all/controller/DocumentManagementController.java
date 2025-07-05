package vn.com.unit.cms.admin.all.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.util.WebUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import vn.com.unit.cms.admin.all.constant.CommonConstant;
import vn.com.unit.cms.admin.all.dto.DocumentManagementDto;
import vn.com.unit.cms.admin.all.service.DocumentManagementService;
//import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
//import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
//import vn.com.unit.ep2p.admin.constant.RoleConstant;
//import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.enumdef.MenuSearchEnum;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.controller.DocumentWorkflowCommonController;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;
import vn.com.unit.ep2p.utils.SearchUtil;

@Controller
@RequestMapping(value = UrlConst.ROOT + UrlConst.DOCUMENT_MANAGEMENT)
public class DocumentManagementController
		extends DocumentWorkflowCommonController<DocumentManagementDto, DocumentManagementDto> {

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private DocumentManagementService documentManagementService;

	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(DocumentManagementController.class);

	private static final String DOCUMENT_MANAGEMENT_LIST_VIEW = "/views/CMS/all/document-management/document-management-list.html";
	private static final String DOCUMENT_MANAGEMENT_EDIT_VIEW = "/views/CMS/all/document-management/document-management-edit.html";

	@Override
	public void customeBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
		if (null == request.getSession().getAttribute(ConstantCore.FORMAT_DATE)) {
			request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
		}
		// The date format to parse or output your dates
		String patternDate = ConstantCore.FORMAT_DATE_FULL;
		SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
	}

//	@RequestMapping(value = UrlConst.LIST, method = RequestMethod.GET)
//	public ModelAndView getDocumentManagementList(@PathVariable String customerAlias,
//			@ModelAttribute(value = "searchDto") CommonSearchDto searchDto,
//			@RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale) {
//		
////		if (!UserProfileUtils.hasRole(CmsRoleConstant.)) {
////			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
////        }
//	}

	@RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView loadMenuAjax(@ModelAttribute(value = "menuModel") DocumentManagementDto searchDto,
			Locale locale, @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(DOCUMENT_MANAGEMENT_LIST_VIEW);

		// set url ajax
//		condition.setUrl(UrlConst.DOCUMENT_MANAGEMENT.concat(UrlConst.LIST));

		// Security for this page.
//		if (!UserProfileUtils.hasRole(RoleConstant.DOCUMENT_MANAGEMENT)
//				&& !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_DISP))
//				&& !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}

		// Set default company
		if (null == searchDto.getCompanyId()) {
			searchDto.setCompanyId(UserProfileUtils.getCompanyId());
		}

//        WebUtils.setSessionAttribute(request, SORT_COMPANY_ID, condition.getCompanyId());

		// Add company list
		List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
				UserProfileUtils.isCompanyAdmin());
		mav.addObject("companyList", companyList);

		// set init search
		SearchUtil.setSearchSelect(MenuSearchEnum.class, mav);
		try {

//			condition.setMenuTypeList(menuService.getMenuTypeList(true));

			String lang = locale.getLanguage();
			PageWrapper<DocumentManagementDto> pageWrapper = documentManagementService.search(page, searchDto, lang);

			mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
			mav.addObject("searchDto", searchDto);
		} catch (Exception e) {
			logger.error("#list#", e);
		}
		return mav;
	}

	@RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
	public ModelAndView deleteDocumentManagement(
			@RequestParam(value = "id", required = true, defaultValue = "") Long id, Locale locale,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		// Security for this page.
//		if (!UserProfileUtils.hasRole(RoleConstant.DOCUMENT_MANAGEMENT)
//				&& !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_DISP))
//				&& !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}

		ModelAndView mav = new ModelAndView(UrlConst.REDIRECT.concat("list"));

		MessageList messageList = null;

		try {
			documentManagementService.delete(id);
			messageList = new MessageList(Message.SUCCESS);
			messageList.add(messageSource.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale));
		} catch (Exception e) {
			messageList = new MessageList(Message.ERROR);
			messageList.add(e.getMessage());
			logger.error("vn.com.unit.cms.admin.all.controller.DocumentManagementController.deleteDocumentManagement",
					e);
		}

		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		return mav;
	}

	@RequestMapping(value = "/create-folder", method = RequestMethod.POST)
	public ModelAndView createFolder(@RequestParam(value = "id", required = true, defaultValue = "") Long id,
			@RequestParam(value = "name", required = true, defaultValue = "") String name, Locale locale,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		// Security for this page.
//		if (!UserProfileUtils.hasRole(RoleConstant.DOCUMENT_MANAGEMENT)
//				&& !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_DISP))
//				&& !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}

		ModelAndView mav = new ModelAndView(UrlConst.REDIRECT.concat("list"));

		MessageList messageList = null;

		try {
			documentManagementService.createFolder(id, name.split("/")[0].split("\\\\")[0]);
			messageList = new MessageList(Message.SUCCESS);
			messageList.add(messageSource.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale));
		} catch (Exception e) {
			messageList = new MessageList(Message.ERROR);
			messageList.add(e.getMessage());
			logger.error("vn.com.unit.cms.admin.all.controller.DocumentManagementController.deleteDocumentManagement",
					e);
		}

		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		return mav;
	}

	@RequestMapping(value = "/upload-file", method = RequestMethod.POST)
	public ModelAndView uploadFile(@RequestParam(value = "id", required = true, defaultValue = "") Long id,
			@RequestParam("file") MultipartFile[] listFile, Locale locale, RedirectAttributes redirectAttributes) {
		// Security for this page.
//		if (!UserProfileUtils.hasRole(RoleConstant.DOCUMENT_MANAGEMENT)
//				&& !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_DISP))
//				&& !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}

		ModelAndView mav = new ModelAndView(UrlConst.REDIRECT.concat("list"));

		MessageList messageList = null;

		try {
			for (MultipartFile file : listFile) {
				documentManagementService.uploadFile(id, file);
			}
			messageList = new MessageList(Message.SUCCESS);
			messageList.add(messageSource.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale));
		} catch (Exception e) {
			messageList = new MessageList(Message.ERROR);
			messageList.add(e.getMessage());
			logger.error("vn.com.unit.cms.admin.all.controller.DocumentManagementController.deleteDocumentManagement",
					e);
		}

		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		return mav;
	}

	@RequestMapping(value = "/download-file/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id, Locale locale,
			RedirectAttributes redirectAttributes) throws IOException {
		// Security for this page.
//		if (!UserProfileUtils.hasRole(RoleConstant.DOCUMENT_MANAGEMENT)
//				&& !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_DISP))
//				&& !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}

//		InputStreamResource resource = new InputStreamResource(
//		new FileInputStream(documentManagementService.getDocumentManagementPhysicalPathById(id)));
//
//return ResponseEntity.ok()
////		.headers(headers)
////		.contentLength(file.length())
////		.contentType(MediaType.APPLICATION_OCTET_STREAM)
//		.body(resource);

		File file = new File(documentManagementService.getDocumentManagementPhysicalPathById(id));

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + documentManagementService.findOne(id).getName());
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@Override
	public String getPermisionItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentWorkflowCommonService<DocumentManagementDto, DocumentManagementDto> getService() {
		return documentManagementService;
	}

	@Override
	public String getControllerURL(String customerAlias) {
		return "document-management";
	}

	@Override
	public String getBusinessCode(String customerAlias) {
		String businessCode = "";

		logger.error("NEED CHANGE BUSINESS CODE");
		// TODO remove "BUSINESS_BANNER"
		businessCode = "BUSINESS_BANNER"; // remove this line, only for test
		logger.error("NEED CHANGE BUSINESS CODE");

		return businessCode;
	}

	@Override
	public String viewEdit(String customerAlias) {
		return DOCUMENT_MANAGEMENT_EDIT_VIEW;
	}

	@Override
	public void sendEmailAction(DocumentManagementDto editDto, Long buttonId) {

	}

	@Override
	public void sendEmailEdit(DocumentManagementDto editDto, Long userUpdated) {

	}

	@Override
	public String objectEditName() {
		return "documentManagementDto";
	}

	@Override
	public String firstStepInProcess(String customerAlias) {
		return null;
	}

	@Override
	public String roleForAttachment(String customerAlias) {
		return null;
	}

	@Override
	public void initDataEdit(ModelAndView mav, String customerAlias, DocumentManagementDto editDto, boolean isDetail,
			Locale locale) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate(DocumentManagementDto editDto, BindingResult bindingResult) {
		// TODO Auto-generated method stub

	}
}
