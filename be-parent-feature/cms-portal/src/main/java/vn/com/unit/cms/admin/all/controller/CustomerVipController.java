//package vn.com.unit.cms.admin.all.controller;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.nio.file.Paths;
//import java.security.NoSuchAlgorithmException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
////import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
////import java.util.List;
//import java.util.Locale;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
////import javax.validation.Valid;
//
//import org.hornetq.utils.json.JSONException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.context.MessageSource;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.WebDataBinder;
////import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import vn.com.unit.ep2p.admin.binding.DoubleEditor;
//import vn.com.unit.ep2p.admin.config.AppSystemConfig;
//import vn.com.unit.ep2p.admin.constant.ConstantCore;
//import vn.com.unit.ep2p.admin.constant.Message;
//import vn.com.unit.ep2p.admin.constant.MessageList;
////import vn.com.unit.common.dto.SelectItem;
//import vn.com.unit.ep2p.core.controller.DocumentWorkflowCommonController;
//import vn.com.unit.ep2p.core.exception.BusinessException;
//import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;
//import vn.com.unit.cms.admin.all.constant.AdminConstant;
//import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
//import vn.com.unit.cms.admin.all.dto.CustomerVipEditDto;
//import vn.com.unit.cms.admin.all.dto.CustomerVipLanguageResultDto;
////import vn.com.unit.cms.admin.all.dto.CustomerVipLanguageResultDto;
//import vn.com.unit.cms.admin.all.dto.CustomerVipLanguageSearchDto;
////import vn.com.unit.cms.admin.all.dto.DocumentCategoryDto;
////import vn.com.unit.cms.admin.all.dto.ProductSearchDto;
//import vn.com.unit.cms.admin.all.entity.CustomerVip;
//import vn.com.unit.cms.admin.all.enumdef.EnabledStatusEnum;
////import vn.com.unit.cms.admin.all.enumdef.EnabledStatusEnum;
//import vn.com.unit.cms.admin.all.service.CustomerVipService;
////import vn.com.unit.cms.admin.utils.HDBankUtil;
//import vn.com.unit.cms.core.constant.CmsRoleConstant;
//import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.common.dto.SelectItem;
////import vn.com.unit.jcanary.authentication.UserProfile;
//import vn.com.unit.core.security.UserProfileUtils;
////import vn.com.unit.common.dto.PageWrapper;
////import vn.com.unit.jcanary.config.SystemConfig;
////import vn.com.unit.cms.admin.all.constant.CommonConstant;
////import vn.com.unit.cms.admin.constant.ConstantHistoryApprove;
////import vn.com.unit.jcanary.constant.RoleConstant;
//import vn.com.unit.ep2p.constant.UrlConst;
//import vn.com.unit.ep2p.admin.constant.ViewConstant;
////import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
////import vn.com.unit.ep2p.dto.JProcessStepDto;
////import vn.com.unit.core.dto.LanguageDto;
//import vn.com.unit.cms.admin.service.FileService;
////import vn.com.unit.jcanary.service.HistoryApproveService;
////import vn.com.unit.jcanary.service.JProcessService;
////import vn.com.unit.core.service.LanguageService;
////import vn.com.unit.common.utils.CommonJsonUtil;
////import vn.com.unit.common.utils.CommonUtil;
////import vn.com.unit.util.Util;
//
//@Controller
//@RequestMapping("/{customerAlias}")
//public class CustomerVipController extends DocumentWorkflowCommonController<CustomerVipEditDto, CustomerVipEditDto> {
//
//	private static final String LIST_VIEW = "views/CMS/all/customer-vip/customer-vip-list.html"; // "customer.vip.list"
//
////	private static final String TABLE_VIEW = "customer.vip.table";
//
//	private static final String EDIT_VIEW = "views/CMS/all/customer-vip/customer-vip-edit.html"; // "customer.vip.edit"
//
//	private static final String CUSTOMER_VIP = "customer-vip";
//
//	private static final String CUSTOMER_FDI = "customer-fdi";
//
////	private static final String STATUS_CODE_PUBLISHED = "99";
//
//	private static final String CUSTOMER_VIP_FOLDER = "customer-vip";
//
//	@Autowired
//	private MessageSource msg;
//
//	@Autowired
//	private FileService fileService;
//
//	@Autowired
//	private CustomerVipService customerVipService;
//
////	@Autowired
////	private JProcessService jprocessService;
//
//	@Autowired
//	private AppSystemConfig systemConfig;
//	
//	private static final Logger logger = LoggerFactory.getLogger(CustomerVipController.class);
//
////	@Autowired
////	private LanguageService languageService;
//
////	@Autowired
////	HistoryApproveService historyApproveService;
//
////	@InitBinder
////	public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
////		if (null == request.getSession().getAttribute(ConstantCore.FORMAT_DATE)) {
////			request.getSession().setAttribute("formatDate", systemConfig.getConfig(AppSystemConfig.DATE_PATTERN));
////		}
////		// The date format to parse or output your dates
////		String patternDate = ConstantCore.FORMAT_DATE_FULL;
////		SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
////		// Create a new CustomDateEditor
////		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
////		// Register it as custom editor for the Date type
////		binder.registerCustomEditor(Date.class, editor);
////
////		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
////	}
//	
//	@Override
//    public void customeBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
//		if (null == request.getSession().getAttribute(ConstantCore.FORMAT_DATE)) {
//			request.getSession().setAttribute("formatDate", systemConfig.getConfig(AppSystemConfig.DATE_PATTERN));
//		}
//		// The date format to parse or output your dates
//		String patternDate = ConstantCore.FORMAT_DATE_FULL;
//		SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
//		// Create a new CustomDateEditor
//		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
//		// Register it as custom editor for the Date type
//		binder.registerCustomEditor(Date.class, editor);
//
//		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
//	}
//
//	@RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
//	public ModelAndView getCustomerList(@PathVariable String customerAlias,
//			@ModelAttribute(value = "searchDto") CustomerVipLanguageSearchDto searchDto,
//			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
//
//		logger.info("vn.com.unit.cms.admin.all.controller.CustomerVipController.getCustomerList: " + customerAlias);
//		
//		/*---- taitm - phân quyền theo menu -------*/
//		if (!hasRoleAccess(customerAlias)) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}
//		/*---End---*/
//
//		setCustomerType(customerAlias, searchDto);
//
//		// set url
//		searchDto.setUrl(customerAlias.concat(UrlConst.LIST));
//
//		ModelAndView mav = new ModelAndView(LIST_VIEW);
//
//		// languageCode
//		String languageCode = locale.toString();
//
//		// set language
//		searchDto.setLanguageCode(languageCode);
//
//		PageWrapper<CustomerVipLanguageResultDto> pageWrapper = customerVipService.doSearch(page, searchDto, locale);
//
//		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
//		mav.addObject("searchDto", searchDto);
//
//		// Status List process
////		List<JProcessStepDto> statusList = jprocessService.findStepStatusList(CommonConstant.BUSINESS_CUSTOMER,
////				locale);
////		mav.addObject("statusList", statusList);
//
//		List<SelectItem> enabledStatus = new ArrayList<SelectItem>();
//		for (EnabledStatusEnum enabled : EnabledStatusEnum.values()) {
//			SelectItem item = new SelectItem(enabled.toString(), enabled.getName());
//			enabledStatus.add(item);
//		}
//
//		mav.addObject("enabledStatus", enabledStatus);
//
//		return mav;
//	}
//
////	@RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
////	@ResponseBody
////	public ModelAndView ajaxList(@PathVariable String customerAlias,
////			@ModelAttribute(value = "searchDto") CustomerVipLanguageSearchDto searchDto,
////			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
////		ModelAndView mav = new ModelAndView(TABLE_VIEW);
////
////		setCustomerType(customerAlias, searchDto);
////
////		// set language code
////		searchDto.setLanguageCode(locale.toString());
////		// Init PageWrapper
////		PageWrapper<CustomerVipLanguageResultDto> pageWrapper = customerVipService.doSearch(page, searchDto, locale);
////		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
////
////		return mav;
////	}
//
////	@RequestMapping(value = UrlConst.EDIT, method = { RequestMethod.GET })
////	public ModelAndView doGetEdit(@PathVariable String customerAlias,
////			@ModelAttribute(value = "searchDto") CustomerVipLanguageSearchDto searchDto,
////			@RequestParam(value = "searchDtoJson", required = false) String searchDtoJson,
////			@RequestParam(value = "id", required = false) Long customerVipId, Locale locale) {
////
////		ModelAndView mav = new ModelAndView(EDIT_VIEW);
////		/*---- taitm - phân quyền theo menu -------*/
////		if (!hasRoleAccess(customerAlias)) {
////			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
////		}
////		/*---End---*/
////
////		mav.addObject("customerAlias", customerAlias);
////
////		CustomerVipEditDto customerVipEdit = customerVipService.getCustomerVip(customerVipId, locale);
////
////		initCustomerVipEdit(mav, customerVipEdit, customerAlias, locale);
////
////		String url = customerAlias.concat(UrlConst.EDIT);
////		if (null != customerVipId) {
////			url = url.concat("?id=").concat(customerVipId.toString());
////		}
////		customerVipEdit.setUrl(url);
////
////		String requestToken = CommonUtil.randomStringWithTimeStamp();
////		customerVipEdit.setRequestToken(requestToken);
////
////		// user login
////		if (customerVipId == null) {
//////			UserProfile userProfile = UserProfileUtils.getUserProfile();
////			customerVipEdit.setCreateBy(UserProfileUtils.getUserNameLogin());
////		}
////
////		customerVipEdit.setIndexLangActive(0);
////
////		String searchDtoStr = CommonJsonUtil.convertObjectToJsonString(searchDto);
////
////		if (searchDtoJson != null) {
////			searchDtoStr = searchDtoJson;
////		}
////		// check statusCode published
//////		boolean checkPublishedAndHaveData = false;
//////
//////		if (null != customerVipEdit.getStatusCode() && customerVipEdit.getStatusCode() != ""
//////				&& customerVipEdit.getStatusCode().equals(STATUS_CODE_PUBLISHED)) {
//////			List<Long> lstStatus = HDBankUtil.getListStatusForDependency();
//////
//////			int countDependencies = customerVipService.countDependencies(customerVipId, lstStatus);
//////
//////			if (countDependencies > 0) {
//////				checkPublishedAndHaveData = true;
//////			}
//////
//////			/**
//////			 * Get list dependencies
//////			 * 
//////			 * @author tranlth - 22/05/2019
//////			 */
//////			List<Map<String, String>> listDependencies = (List<Map<String, String>>) productService
//////					.listDependencies(customerVipId, lstStatus);
//////			mav.addObject("listDependencies", listDependencies);
//////		}
//////		mav.addObject("checkPublishedAndHaveData", checkPublishedAndHaveData);
////
////		customerVipEdit.setSearchDto(searchDtoStr);
////
////		mav.addObject("editDto", customerVipEdit);
////
////		return mav;
////	}
//
////	@RequestMapping(value = UrlConst.SAVE_DRAFT, method = { RequestMethod.POST })
////	@ResponseBody
////	public ModelAndView doPost(@PathVariable String customerAlias,
////			@Valid @ModelAttribute("editDto") CustomerVipEditDto editDto, BindingResult bindingResult, Locale locale,
////			RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException {
////		/*---- taitm - phân quyền theo menu -------*/
////		if (!hasRoleEdit(customerAlias)) {
////			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
////		}
////
////		return saveOrEdit(editDto, bindingResult, locale, redirectAttributes, false, customerAlias, request);
////	}
//
//	/**
//	 * saveOrEdit
//	 * 
//	 * @param editDto
//	 * @param bindingResult
//	 * @param locale
//	 * @param redirectAttributes
//	 * @param action             : true is saveDraft, false is sendRequest
//	 * @param customerAlias
//	 * @return ModelAndView
//	 * @throws IOException
//	 */
////	private ModelAndView saveOrEdit(CustomerVipEditDto editDto, BindingResult bindingResult, Locale locale,
////			RedirectAttributes redirectAttributes, boolean action, String customerAlias, HttpServletRequest request)
////			throws IOException {
////		ModelAndView mav = new ModelAndView(EDIT_VIEW);
////
////		mav.addObject("customerAlias", customerAlias);
////
////		// Init message list
////		MessageList messageList = new MessageList(Message.SUCCESS);
////
////		// check statusCode published
//////		boolean checkPublishedAndHaveData = false;
//////		List<Long> lstStatus = HDBankUtil.getListStatusForDependency();
//////
//////		if (null != productEdit.getStatusCode() && productEdit.getStatusCode() != ""
//////				&& productEdit.getStatusCode().equals(STATUS_CODE_PUBLISHED)) {
//////			
//////			int countDependencies = productService.countDependencies(productEdit.getId(), lstStatus);
//////			
//////			if (countDependencies > 0) {
//////				checkPublishedAndHaveData = true;
//////			}
//////			
//////			/** Get list dependencies
//////			 * @author tranlth - 22/05/2019
//////			 */
//////			List<Map <String, String>> listDependencies = (List<Map <String, String>>) productService.listDependencies(productEdit.getId(), lstStatus);
//////			mav.addObject("listDependencies", listDependencies);
//////		}
//////		mav.addObject("checkPublishedAndHaveData", checkPublishedAndHaveData);
////
////		// create or edit
////		editDto.setLanguageCode(locale.getLanguage());
////
////		customerVipService.doEdit(editDto, locale, request);
////
////		// success redirect edit page
////		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(customerAlias).concat(UrlConst.EDIT);
////		String msgInfo = msg.getMessage("message.success.label", new String[] { editDto.getButtonAction() }, locale);
////		messageList.add(msgInfo);
////
////		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
////		redirectAttributes.addAttribute("id", editDto.getId());
////		redirectAttributes.addAttribute("searchDtoJson", editDto.getSearchDto());
////
////		mav.setViewName(viewName);
////		return mav;
////	}
//
////	private void initCustomerVipEdit(ModelAndView mav, CustomerVipEditDto editDto, String customerAlias,
////			Locale locale) {
////		List<LanguageDto> languageList = languageService.getLanguageDtoList();
////		mav.addObject("languageList", languageList);
////
////		editDto.setCustomerAlias(customerAlias);
////		
////		if (customerAlias.equals(CUSTOMER_VIP)) {
////			editDto.setVip(1);
////		}
////		if (customerAlias.equals(CUSTOMER_FDI)) {
////			editDto.setFdi(1);
////		}
////
////		// Init PageWrapper History Approval
//////		PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.doSearch(1, editDto.getId(),
//////				editDto.getProcessId(), ConstantHistoryApprove.APPROVE_CUSTOMER_VIP, locale);
//////		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
////	}
//
//	private void setCustomerType(String customerAlias, CustomerVipLanguageSearchDto searchDto) {
//		if (customerAlias.equals(CUSTOMER_VIP)) {
//			searchDto.setVip(1);
//			searchDto.setCustomerAlias(customerAlias);
//		}
//		if (customerAlias.equals(CUSTOMER_FDI)) {
//			searchDto.setFdi(1);
//			searchDto.setCustomerAlias(customerAlias);
//		}
//	}
//
//	@RequestMapping(value = AdminUrlConst.URL_EDITOR_FILE_UPLOAD, method = RequestMethod.POST)
//	public @ResponseBody String editorFileUpload(@PathVariable String customerAlias,
//			MultipartHttpServletRequest request, HttpServletRequest request2, Model model, HttpServletResponse response,
//			@RequestParam(required = true, value = "requesttoken") String requestToken,
//			@RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
//			throws JSONException, IOException {
//		String fileName = fileService.uploadTemp(request, request2, model, response,
//				Paths.get(UrlConst.PRODUCT, AdminConstant.EDITOR_FOLDER).toString(), requestToken);
//		String fileUrl = URLEncoder.encode(fileName, "UTF-8");
//		String downloadUrl = request2.getContextPath().concat(UrlConst.ROOT).concat(customerAlias).concat(UrlConst.ROOT)
//				.concat(UrlConst.PRODUCT).concat(UrlConst.URL_EDITOR_DOWNLOAD).concat("?url=").concat(fileUrl);
//		return "<script> " + "window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", \"" + downloadUrl + "\");"
//				+ "</script>";
//	}
//
//	@RequestMapping(value = UrlConst.URL_EDITOR_UPLOAD, method = RequestMethod.POST)
//	public @ResponseBody String editorUpload(@PathVariable String customerAlias, MultipartHttpServletRequest request,
//			HttpServletRequest request2, Model model, HttpServletResponse response,
//			@RequestParam(required = true, value = "requesttoken") String requestToken,
//			@RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
//			throws JSONException, IOException {
//		String fileName = fileService.uploadTemp(request, request2, model, response,
//				Paths.get(CUSTOMER_VIP_FOLDER, AdminConstant.EDITOR_FOLDER).toString(), requestToken);
//
//		String fileUrl = URLEncoder.encode(fileName, "UTF-8");
//
//		String downloadUrl = request2.getContextPath().concat(UrlConst.ROOT).concat(customerAlias).concat(UrlConst.ROOT)
//				.concat(UrlConst.PRODUCT).concat(UrlConst.URL_EDITOR_DOWNLOAD).concat("?url=").concat(fileUrl);
//		return "<script> " + "window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", \"" + downloadUrl + "\");"
//				+ "</script>";
//	}
//
//	@RequestMapping(value = UrlConst.URL_EDITOR_DOWNLOAD, method = RequestMethod.GET)
//	public void editorDownload(@PathVariable String customerAlias,
//			@RequestParam(required = true, value = "url") String fileUrl, HttpServletRequest request,
//			HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//		String url = Paths.get(CUSTOMER_VIP_FOLDER, AdminConstant.EDITOR_FOLDER, fileUrl).toString();
//
//		customerVipService.requestEditorDownload(url, request, response);
//	}
//
//	@RequestMapping(value = UrlConst.URL_UPLOADTEMP, method = RequestMethod.POST)
//	public @ResponseBody String uploadTemp(@PathVariable String customerAlias, MultipartHttpServletRequest request,
//			HttpServletRequest request2, Model model, HttpServletResponse response,
//			@RequestParam(required = false, value = "requestToken") String requestToken,
//			@RequestParam(required = false, value = "widthImg") Integer widthImg,
//			@RequestParam(required = false, value = "heightImg") Integer heightImg)
//			throws NoSuchAlgorithmException, IOException {
//		try {
//			return fileService.uploadImageTemp(request, request2, model, response, "", AdminConstant.CUSTOMER_FOLDER,
//					widthImg, heightImg).replace("\\", "/");
//		} catch (IOException e) {
//			throw e;
//		}
//	}
//
//	@RequestMapping(value = UrlConst.URL_DOWNLOAD_IMAGE, method = RequestMethod.GET)
//	public void download(@PathVariable String customerAlias,
//			@RequestParam(required = true, value = "fileName") String fileName, HttpServletRequest request,
//			HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//		if (fileName.contains(ConstantCore.AT_FILE)) {
//			fileService.downloadTemp(fileName, request, response);
//		} else {
//			fileService.download(fileName, request, response);
//		}
//
//	}
//
//	@RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
//	@ResponseBody
//	public ModelAndView delete(@PathVariable String customerAlias, @RequestParam(value = "id", required = true) Long id,
//			Locale locale, RedirectAttributes redirectAttributes) {
//
//		/*---- taitm - phân quyền theo menu -------*/
//		if (!hasRoleEdit(customerAlias)) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}
//
////        // check exists Product
//		CustomerVip entity = customerVipService.getCustomerVipById(id);
//		if (null == entity) {
//			throw new BusinessException("Not found data with id=" + id);
//		}
//
//		// delete Product
//		customerVipService.deleteData(id, UserProfileUtils.getUserNameLogin());
////
////        // Delete success redirect list page
//		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(customerAlias).concat(UrlConst.LIST);
//		ModelAndView mav = new ModelAndView(viewName);
//
//		// Init message success list
//		MessageList messageList = new MessageList(Message.SUCCESS);
//		String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
//		messageList.add(msgInfo);
//
//		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
//
//		return mav;
//	}
//	
//	@PostMapping("/export-excel")
//	public void exportListNonExport(@PathVariable String customerAlias,
//			@ModelAttribute(value = "searchDto") CustomerVipLanguageSearchDto searchDto, HttpServletRequest req,
//			HttpServletResponse res, Locale locale) {
//		
//        try {
//    		setCustomerType(customerAlias, searchDto);
//
//    		// set language code
//    		searchDto.setLanguageCode(locale.toString());
//    		
//        	customerVipService.exportExcel(searchDto, res, locale);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//	private boolean hasRoleAccess(String customerAlias) {
//		if (customerAlias.equals(CUSTOMER_VIP)) {
//			if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_CUSTOMER_VIP)) {
//				return false;
//			}
//		}else if (customerAlias.equals(CUSTOMER_FDI)) {
//			if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_CUSTOMER_FDI)) {
//				return false;
//			}
//		} else {
//			return false;
//		}
//
//		return true;
//	}
//
//	private boolean hasRoleEdit(String customerAlias) {
//		if (customerAlias.equals(CUSTOMER_VIP)) {
//			if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_CUSTOMER_VIP.concat(ConstantCore.COLON_EDIT))) {
//				return false;
//			}
//		}else if (customerAlias.equals(CUSTOMER_FDI)) {
//			if (!UserProfileUtils.hasRole(CmsRoleConstant.ROLE_CUSTOMER_FDI.concat(ConstantCore.COLON_EDIT))) {
//				return false;
//			}
//		} else {
//			return false;
//		}
//
//		return true;
//	}
//	@Override
//	public String getPermisionItem() {
////		return CmsRoleConstant.DOCUMENT_CATEGORY_LIST;
//		return "";
//	}
//
//	@Override
//	public DocumentWorkflowCommonService<CustomerVipEditDto, CustomerVipEditDto> getService() {
//		return customerVipService;
//	}
//
//	@Override
//	public String getControllerURL(String customerAlias) {
//		return "customer-vip";
//	}
//
//	@Override
//	public String getBusinessCode(String customerAlias) {
//		return "BUSINESS_BANNER";
//	}
//
//	@Override
//	public String viewEdit(String customerAlias) {
//		return EDIT_VIEW;
//	}
//
//	@Override
//	public String objectEditName() {
//		return "editDto";
//	}
//
//	@Override
//	public String firstStepInProcess(String customerAlias) {
//		return "submit";
//	}
//
//	@Override
//	public String roleForAttachment(String customerAlias) {
//		return "CUSTOMER#ATTACH_FILE".concat(ConstantCore.COLON_EDIT);
//	}
//
//	@Override
//	public void initDataEdit(ModelAndView mav, String customAlias, CustomerVipEditDto editDto, boolean isDetail,
//			Locale locale) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void validate(CustomerVipEditDto editDto, BindingResult bindingResult) {
//		//customerVipValidator.validate(editDto, bindingResult);
//		
//	}
//
//	@Override
//	public ModelAndView returnModelProcess(CustomerVipEditDto objectDto, Long referenceId, String customerAlias,
//			HttpServletRequest httpServletRequest, Locale locale, RedirectAttributes redirectAtrribute) {
//		ModelAndView mav = new ModelAndView();
//
//        Long id = objectDto.getId();
//
//        String viewName = UrlConst.REDIRECT.concat("/detail?id=" + id);
//        redirectAtrribute.addFlashAttribute("messageList", objectDto.getMessageList());
//
//        mav.setViewName(viewName);
//        return mav;
//	}
//}
