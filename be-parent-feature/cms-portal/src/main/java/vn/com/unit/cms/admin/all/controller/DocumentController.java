/*******************************************************************************
 * Class        ：DocumentTypeController
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：TaiTM
 * Change log   ：2017/04/18：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.controller.common.CmsCommonsSearchFilterProcessController;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.DocumentService;
import vn.com.unit.cms.admin.all.util.CmsLanguageUtils;
import vn.com.unit.cms.admin.all.validator.DocumentValidator;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.document.dto.DocumentEditDto;
import vn.com.unit.cms.core.module.document.dto.DocumentLanguageDto;
import vn.com.unit.cms.core.module.document.dto.DocumentSearchDto;
import vn.com.unit.cms.core.module.document.dto.DocumentSearchResultDto;
import vn.com.unit.cms.core.module.document.entity.Document;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.dto.JcaGroupEmailDto;
import vn.com.unit.core.enumdef.SendEmailTypeEnum;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.res.dto.DocumentAppRes;
import vn.com.unit.ep2p.core.service.AttachedFileService;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;
import vn.com.unit.imp.excel.utils.Utils;
import vn.com.unit.sla.email.service.SlaEmailTemplateService;
import vn.com.unit.workflow.activiti.core.impl.ActivitiWorkflowParams;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmStatusCommonDto;
import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.service.JpmProcessDeployService;
import vn.com.unit.workflow.service.JpmStatusCommonService;
import vn.com.unit.workflow.service.JpmStatusDeployService;

/**
 * DocumentController
 *
 * @author TaiTM
 * @version 01-00
 * @since 01-00
 */
@Controller
@RequestMapping(value = "/{customerAlias}" + UrlConst.DOCUMENT)
public class DocumentController
        extends CmsCommonsSearchFilterProcessController<DocumentSearchDto, DocumentSearchResultDto, DocumentEditDto> {

    private static final String DOCUMENT_SHARE = "/share";
    private static final String VIEW_EDIT = "views/CMS/all/document/document-edit.html";

    private static final String VIEW_LIST_SORT = "views/CMS/all/document/document-list-sort.html";
    private static final String VIEW_TABLE_SORT = "views/CMS/all/document/document-table-sort.html";
    private static final String VIEW_LIST = "views/CMS/all/document/document-list.html";
    private static final String CMS_ROLE_ADMIN = "CMS#ROLE_ADMIN";

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private ActivitiWorkflowParams activitiWorkflowParams;

    @Autowired
    private JcaEmailService jcaEmailService;

    @Autowired
    private JcaEmailTemplateService jcaEmailTemplateService;

    @Autowired
    private SlaEmailTemplateService slaEmailTemplateService;

    @Autowired
    private MessageSource msg;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentValidator documentValidator;

    @Autowired
    private Select2DataService select2DataService;

    @Autowired
    private JpmStatusCommonService jpmStatusCommonService;

    @Autowired
    private JpmStatusDeployService jpmStatusDeployService;

    @Autowired
    private AttachedFileService attachedFileService;

    @Autowired
    private JcaAccountService jcaAccountService;

    @Autowired
    private JpmProcessDeployService jpmProcessDeployService;

    private static final String STATUS_COMMON_DRAFT = "000";

    private static final String TEMPLATE_MAIL_ACTION_MODERATOR_SEND = "TEMPLATE_MAIL_ACTION_MODERATOR_SEND";
    private static final String TEMPLATE_MAIL_ACTION_MODERATIR_WITHDRAW = "TEMPLATE_MAIL_ACTION_MODERATIR_WITHDRAW";
    private static final String TEMPLATE_MAIL_RETURN_BROWSER = "TEMPLATE_MAIL_RETURN_BROWSER";
    private static final String TEMPLATE_MAIL_RETURN_EDIT = "TEMPLATE_MAIL_RETURN_EDIT";
    private static final String TEMPLATE_MAIL_RETURN_REFUSE = "TEMPLATE_MAIL_RETURN_REFUSE";
    private static final String TEMPLATE_MAIL_EDIT_HIDE = "TEMPLATE_MAIL_ADMIN_HIDE";

    @RequestMapping(value = "/clone-data", method = RequestMethod.POST)
    public ModelAndView doCloneData(@PathVariable(required = false) String customerAlias,
                                    @RequestParam(value = "id", required = false) Long id, Locale locale) throws Exception {

        ModelAndView mav = new ModelAndView(viewEdit(customerAlias));
        if (!hasRoleForEdit(customerAlias)) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        DocumentEditDto editDto = getService().getEdit(id, customerAlias, locale);

        editDto.setId(null);
        editDto.setCode(null);
        editDto.setDocId(null);
        editDto.setCloneData(true);
        for (DocumentLanguageDto langDto : editDto.getListLanguage()) {
            langDto.setId(null);
        }
        setStatusForEditDto(editDto, locale);

        // init attach file plugin
        attachedFileService.initComponent(mav, getBusinessCode(customerAlias), editDto.getRefAttachment(), true,
                roleForAttachment(customerAlias));

        boolean isDetail = false;
        editDto.setUrl(getControllerURL(customerAlias).concat("/edit"));
        mav.addObject(objectEditName(), editDto);

        initDataEdit(mav, customerAlias, editDto, isDetail, locale);
        initDataProcess(mav, customerAlias, editDto, isDetail, locale);

        mav.addObject("customerAlias", customerAlias);
        mav.addObject("hasRoleForEdit", hasRoleForEdit(customerAlias));
        mav.addObject("hasRoleForAdd", hasRoleForAdd(customerAlias));
        mav.addObject("id", null);

        return mav;
    }

    private void setStatusForEditDto(DocumentEditDto editDto, Locale locale) {
        if (editDto.getDocId() == null) {
            JpmStatusCommonDto statusCommon = jpmStatusCommonService.getStatusCommon(STATUS_COMMON_DRAFT,
                    locale.toString());
            if (statusCommon != null) {
                editDto.setStatusName(statusCommon.getStatusName());
                editDto.setStatusCode(statusCommon.getStatusCode());
            }
        } else {
            JpmStatusDeployDto status = jpmStatusDeployService.getStatusDeploy(editDto.getDocId(), locale.toString());
            if (status != null) {
                editDto.setStatusName(status.getStatusName());
                editDto.setStatusCode(status.getStatusCode());
            }
        }
        if (editDto.getId() == null) {
            JpmStatusCommonDto statusCommon = jpmStatusCommonService.getStatusCommon(STATUS_COMMON_DRAFT,
                    locale.toString());
            if (statusCommon != null) {
                editDto.setStatusName(statusCommon.getStatusName());
                editDto.setStatusCode(statusCommon.getStatusCode());
            }
        }
    }
//    @RequestMapping(value = DOCUMENT_AJAX_LIST, method = RequestMethod.POST)
//    @ResponseBody
//    public ModelAndView postDocumentAjaxList(@PathVariable String customerAlias,
//            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
//            @ModelAttribute(value = "searchDto") CommonSearchDto searchDto, Locale locale, Model model) {
//
//        ModelAndView mav = new ModelAndView(HDB_ADMIN_DOCUMENT_TABLE_VIEW);
//        mav.addObject("customerAlias", customerAlias);
//
//        searchDto.setCustomerTypeId(HDBankUtil.getCustomerType(customerAlias));
//
//        if (searchDto.getCategoryId() != null && searchDto.getCategoryId().equals(-1l)) {
//            searchDto.setCategoryId(null);
//        }
//
//        int pageSize = searchDto.getPageSize() != null ? searchDto.getPageSize()
//                : sysConfig.getIntConfig(AppSystemConfig.PAGING_SIZE);
//        PageWrapper<DocumentEditDto> documentPageWrapper = new PageWrapper<DocumentEditDto>(1, pageSize);
//        documentPageWrapper = documentService.searchActiveDocumentByCondition(page, pageSize, searchDto, locale);
//        List<SearchKeyDto> searchKeyList = documentService.genDocumentSearchKeyList(locale);
//        model.addAttribute("searchKeyList", searchKeyList);
//        mav.addObject("searchDto", searchDto);
//        mav.addObject("pageWrapper", documentPageWrapper);
//        // mav.addObject("catePageWrapper", catePageWrapper);
//        return mav;
//    }

//	@RequestMapping(value = UrlConst.ADD, method = RequestMethod.GET)
//	public ModelAndView getDocumentAdd(@PathVariable String customerAlias,
//			@ModelAttribute(value = "searchDto") CommonSearchDto searchDto,
//			@RequestParam(value = "searchDtoJson", required = false) String searchDtoJson, Locale locale, Model model) {
//		
//		ModelAndView mav = new ModelAndView(HDB_ADMIN_DOCUMENT_EDIT_VIEW);
//	    
//        /*---- taitm - phân quyền theo menu -------*/
//        if (!hasRoleEdit(customerAlias)) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        /*---- END -------*/
//        
//		mav.addObject("customerAlias", customerAlias);
//		
//		Long customerTypeId = HDBankUtil.getCustomerType(customerAlias);
//		
//		//SetCustomerID
//		searchDto.setCustomerTypeId(customerTypeId);
//		
//		
//		
//		DocumentDto detailDto = documentService.getDocumentEditDto(null, locale, customerAlias);
//		detailDto.setCustomerTypeId(customerTypeId);
//		detailDto.setCustomerAlias(customerAlias);
//		documentService.initDocumentDto(mav, detailDto, locale);
//		String requestToken = CommonUtil.randomStringWithTimeStamp();
//		detailDto.setRequestToken(requestToken);
//		 
//		List<DocumentLanguageDto> lstInfoByLanguage = new ArrayList<DocumentLanguageDto>();
//		List<LanguageDto> languageList = languageService.getLanguageDtoList();
//		mav.addObject("languageList", languageList);
//		for (LanguageDto lang : languageList) {
//			DocumentLanguageDto documentCateLangDto = new DocumentLanguageDto();
//			documentCateLangDto.setLanguageCode(lang.getCode());
//			documentCateLangDto.setLanguageDispName(lang.getName());
//			lstInfoByLanguage.add(documentCateLangDto);
//			detailDto.setInfoByLanguages(lstInfoByLanguage);
//		}
//		
//		loadSelectionCategories(mav, customerTypeId);
//
//		List<DocumentTypeSelectionDto> lstDocTypes = documentService.getSelectionTypeList(customerTypeId,
//				locale.toString(),StepNoStatusConstant.STEP_APPROVED);
//		mav.addObject("lstDocTypes", lstDocTypes);
//
//		String url = customerAlias.concat("/document").concat(UrlConst.ADD);
//		detailDto.setUrl(url);
//		List<DocumentViewAuthoritySelectDto> viewAuthoritiesSelect = documentService
//				.getSelectionViewAuthorityItems(locale);
//		model.addAttribute("viewAuthoritiesSelection", viewAuthoritiesSelect);
//
//		String searchDtoStr = CommonJsonUtil.convertObjectToJsonString(searchDto);
//
//		if (searchDtoJson != null) {
//			searchDtoStr = searchDtoJson;
//		}
//
//		detailDto.setSearchDto(searchDtoStr);
//
//		detailDto.setIndexLangActive(0);
//		model.addAttribute("updateDto", detailDto);
//		return mav;
//	}

//	@RequestMapping(value = DOCUMENT_ADD_DRAFT, method = RequestMethod.POST)
//	public ModelAndView postDocumentAddDraft(@PathVariable String customerAlias,
//			@Valid @ModelAttribute(value = "updateDto") DocumentDto updateDto,
//			@RequestParam(value = "searchDtoJson", required = false) String searchDtoJson, BindingResult result,
//			Locale locale, Model model, final RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {
//	    
//        /*---- taitm - phân quyền theo menu -------*/
//        if (!hasRoleEdit(customerAlias)) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        /*---- END -------*/
//	    
//		updateDto.setLanguageCode(locale.toString());
//		String searchDtoStr = "{}";
//
//		if (searchDtoJson != null && updateDto.getSearchDto() == null) {
//			searchDtoStr = searchDtoJson;
//		}
//		updateDto.setSearchDto(searchDtoStr);
//		return saveAdd(updateDto, result, locale, redirectAttributes, model, true, customerAlias, request);
//	}

    /**
     * @param updateDto
     * @param result
     * @param locale
     * @param model
     * @return
     * @throws Exception
     */
//	private ModelAndView saveAdd(DocumentDto updateDto, BindingResult result, Locale locale,
//			RedirectAttributes redirectAttributes, Model model, boolean saveDraft, String customerAlias, HttpServletRequest request)
//			throws Exception {
//		ModelAndView mav = new ModelAndView(HDB_ADMIN_DOCUMENT_EDIT_VIEW);
//		mav.addObject("customerAlias", customerAlias);
//		
//		Long customerTypeId = HDBankUtil.getCustomerType(customerAlias);
//		String searchDtoJson = updateDto.getSearchDto();
//		
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		loadSelectionCategories(mav, customerTypeId);
//
//		updateDto.setCustomerTypeId(customerTypeId);
//
//		if (!result.hasErrors()) {
//			documentValidator.validate(updateDto, result);
//		}
//		if (result.hasErrors()) {
//			// Add message error
//			messageLst.setStatus(Message.ERROR);
//
//			//this.addMsgInfo(ConstantCore.MSG_ERROR_CREATE, locale, mav, messageLst);
//			String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale);
//			messageLst.add(msgError);
//			
//			List<LanguageDto> languageList = languageService.getLanguageDtoList();
//			mav.addObject("languageList", languageList);
//			
////			List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(updateDto.getProcessId(),
////					updateDto.getStatus(), locale.toString());
////			updateDto.setStepBtnList(stepButtonList);
//
//			List<DocumentTypeSelectionDto> lstDocTypes = documentService.getSelectionTypeList(customerTypeId,
//					locale.toString(),StepNoStatusConstant.STEP_APPROVED);
//			mav.addObject("lstDocTypes", lstDocTypes);
//
//			if (updateDto.getIndexLangActive() == null) {
//				updateDto.setIndexLangActive(0);
//			}
//			documentService.initDocumentDto(mav, updateDto, locale);
//			List<DocumentViewAuthoritySelectDto> viewAuthoritiesSelect = documentService
//					.getSelectionViewAuthorityItems(locale);
//			mav.addObject(ConstantCore.MSG_LIST, messageLst);
//			model.addAttribute("viewAuthoritiesSelection", viewAuthoritiesSelect);
//			mav.addObject("updateModel", updateDto);
//			return mav;
//		}
//
//		updateDto = documentService.saveDocument(updateDto, locale, request);
//		
//		if (updateDto != null){
//		    updateDto.setSearchDto(searchDtoJson);
//		}
//		
//		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(customerAlias).concat(UrlConst.ROOT)
//				.concat(UrlConst.DOCUMENT).concat(UrlConst.EDIT);
//		
//        String msgInfo = msg.getMessage("message.success.label", new String[] { updateDto.getButtonAction() },
//                locale);
//        messageLst.add(msgInfo);
//
//		redirectAttributes.addAttribute("id", updateDto.getId());
//		redirectAttributes.addAttribute("searchDtoJson", updateDto.getSearchDto());
//		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageLst);
//		mav.setViewName(viewName);
//		return mav;
//	}

//	@RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
//	public ModelAndView getDocumentEdit(@PathVariable String customerAlias,
//			@ModelAttribute(value = "searchDto") CommonSearchDto searchDto,
//			@RequestParam(value = "searchDtoJson", required = false) String searchDtoJson,
//			@RequestParam(value = "id", required = true, defaultValue = "") Long id, Locale locale, Model model) {
//	    
//        /*---- taitm - phân quyền theo menu -------*/
//        if (!hasRoleEdit(customerAlias)) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        /*---- END -------*/
//	    
//		if (id <= 0) {
//		    throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_WITH_ID, null, locale) + id);
//		}
//		
//		ModelAndView mav = new ModelAndView(HDB_ADMIN_DOCUMENT_EDIT_VIEW);
//		mav.addObject("customerAlias", customerAlias);
//		Long customerTypeId = HDBankUtil.getCustomerType(customerAlias);
//		
//		//setCustomerTypeId
//		searchDto.setCustomerTypeId(customerTypeId);
//		
//		DocumentDto detailDto = documentService.getDocumentEditDto(id, locale, customerAlias);
//		detailDto.setCustomerAlias(customerAlias);
//		detailDto.setCustomerTypeId(customerTypeId);
//		documentService.initDocumentDto(mav, detailDto, locale);
//
//		loadSelectionCategories(mav, customerTypeId);
//
//		List<DocumentTypeSelectionDto> lstDocTypes = documentService.getSelectionTypeList(customerTypeId,
//				locale.toString(),StepNoStatusConstant.STEP_APPROVED);
//		mav.addObject("lstDocTypes", lstDocTypes);
//
//		String url = customerAlias.concat(UrlConst.ROOT).concat("document").concat(UrlConst.EDIT);
//		if (id != null) {
//			url = url.concat("?id=").concat(id.toString());
//		}
//		detailDto.setUrl(url);
//		List<DocumentViewAuthoritySelectDto> viewAuthoritiesSelect = documentService
//				.getSelectionViewAuthorityItems(locale);
//		model.addAttribute("viewAuthoritiesSelection", viewAuthoritiesSelect);
//
//		String searchDtoStr = CommonJsonUtil.convertObjectToJsonString(searchDto);
//
//		if (searchDtoJson != null && !searchDtoJson.equals("{}")) {
//			searchDtoStr = searchDtoJson;
//		}
//
//		detailDto.setSearchDto(searchDtoStr);
//
//		detailDto.setIndexLangActive(0);
//		model.addAttribute("updateDto", detailDto);
//		return mav;
//	}
//
    @RequestMapping(value = "/download/file", method = RequestMethod.GET)
    public void download(@PathVariable String customerAlias, @RequestParam(required = true, value = "id") Long id,
                         @RequestParam(required = true, value = "version") Long versionId,
                         @RequestParam(required = false, value = "token", defaultValue = "tokenDownloadFile") String token,
                         HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        try {
            Utils.addCookieForExport(token, request, response);

            documentService.requestDownloadDocument(id, versionId, token, request, response);
        } catch (Exception ex) {

        }
    }
//
//	@RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
//	public ModelAndView getDocumentDetail(@PathVariable String customerAlias,
//			@RequestParam(value = "id", required = true, defaultValue = "") Long id, Locale locale, Model model,
//			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page) {
//
//        /*---- taitm - phân quyền theo menu -------*/
//        if (!hasRoleAccess(customerAlias)) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        /*---- END -------*/
//        
//		ModelAndView mav = new ModelAndView(HDB_ADMIN_DOCUMENT_DETAIL_VIEW);
//		Long customerTypeId = HDBankUtil.getCustomerType(customerAlias);
//
//		DocumentDto detailDto = null;
//		if (id <= 0) {
//			throw new BusinessException("illegal data: id");
//		}
//		loadSelectionCategories(mav, customerTypeId);
//		detailDto = documentService.getDocumentViewDto(id, locale);
//		String url = "document".concat(UrlConst.DETAIL).concat("?id=").concat(id.toString());
//		detailDto.setUrl(url);
//		model.addAttribute("viewDto", detailDto);
//		// Init PageWrapper
////		PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.searchHistory(page, id,
////				ConstantHistoryApprove.APPROVE_DOCUMENT);
////		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
//		return mav;
//	}

//	@RequestMapping(value = DOCUMENT_DETAIL_APPROVE, method = RequestMethod.POST)
//	public ModelAndView postDocumentDetailApprove(@PathVariable String customerAlias,
//			@Valid @ModelAttribute(value = "viewDto") DocumentDto documentDto, BindingResult result, Locale locale,
//			Model model, final RedirectAttributes redirectAttributes,
//			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page)
//			throws SystemException {
//	    
//        /*---- taitm - phân quyền theo menu -------*/
//        if (!hasRoleEdit(customerAlias)) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        /*---- END -------*/
//        
//		ModelAndView mav = new ModelAndView(HDB_ADMIN_DOCUMENT_DETAIL_VIEW);
//		Long customerTypeId = HDBankUtil.getCustomerType(customerAlias);
//
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		loadSelectionCategories(mav, customerTypeId);
//		DocumentDto updateDto = documentService.approve(documentDto, locale);
//		if (updateDto != null) {
//			this.addMsgInfo(ConstantCore.MSG_SUCCESS_APPROVE, locale, mav, messageLst);
//		}
//		// Init PageWrapper
////		PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.searchHistory(page, documentDto.getId(),
////				ConstantHistoryApprove.APPROVE_DOCUMENT);
////		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
//		mav.addObject("viewDto", updateDto);
//		return mav;
//	}
//
//	@RequestMapping(value = DOCUMENT_DETAIL_REJECT, method = RequestMethod.POST)
//	public ModelAndView postDocumentDetailReject(@PathVariable String customerAlias,
//			@Valid @ModelAttribute(value = "viewDto") DocumentDto documentDto, BindingResult result, Locale locale,
//			Model model, final RedirectAttributes redirectAttributes,
//			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page)
//			throws SystemException {
//	    
//        /*---- taitm - phân quyền theo menu -------*/
//        if (!hasRoleEdit(customerAlias)) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        /*---- END -------*/
//        
//		ModelAndView mav = new ModelAndView(HDB_ADMIN_DOCUMENT_DETAIL_VIEW);
//		Long customerTypeId = HDBankUtil.getCustomerType(customerAlias);
//
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		loadSelectionCategories(mav, customerTypeId);
//		DocumentDto updateDto = documentService.reject(documentDto, locale);
//		if (updateDto != null) {
//			this.addMsgInfo(ConstantCore.MSG_SUCCESS_RETURN, locale, mav, messageLst);
//		}
//		// Init PageWrapper
////		PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.searchHistory(page, documentDto.getId(),
////				ConstantHistoryApprove.APPROVE_DOCUMENT);
////		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
//		mav.addObject("viewDto", updateDto);
//		return mav;
//	}
//
//	@RequestMapping(value = DOCUMENT_DETAIL_SUBMIT, method = RequestMethod.POST)
//	public ModelAndView postDocumentDetailSubmit(@PathVariable String customerAlias,
//			@Valid @ModelAttribute(value = "approvalDto") DocumentDto documentDto, BindingResult result, Locale locale,
//			Model model, final RedirectAttributes redirectAttributes,
//			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page) {
//	    
//        /*---- taitm - phân quyền theo menu -------*/
//        if (!hasRoleEdit(customerAlias)) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        /*---- END -------*/
//        
//		ModelAndView mav = new ModelAndView(HDB_ADMIN_DOCUMENT_DETAIL_VIEW);
//		Long customerTypeId = HDBankUtil.getCustomerType(customerAlias);
//
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		loadSelectionCategories(mav, customerTypeId);
//		DocumentDto updateDto = documentService.submit(documentDto, locale);
//		if (updateDto != null) {
//			this.addMsgInfo(ConstantCore.MSG_SUCCESS_SEND_REQUEST, locale, mav, messageLst);
//			redirectAttributes.addAttribute("id", updateDto.getId());
//		}
////		PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.searchHistory(page, documentDto.getId(),
////				ConstantHistoryApprove.APPROVE_DOCUMENT);
////		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
//		mav.addObject("viewDto", updateDto);
//		return mav;
//	}
//
//    @RequestMapping(value = UrlConst.DELETE, method = { RequestMethod.GET, RequestMethod.POST })
//    public ModelAndView getDocumentDelete(@PathVariable String customerAlias,
//            @RequestParam(value = "id", required = true, defaultValue = "") Long id, Locale locale, Model model,
//            final RedirectAttributes redirectAttributes) {
//
//        /*---- taitm - phân quyền theo menu -------*/
//        if (!hasRoleEdit(customerAlias)) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        /*---- END -------*/
//
//        if (id <= 0) {
//            throw new BusinessException("illegal data: id");
//        }
//
//        documentService.deleteDocumentById(id);
//        String viewName = UrlConst.REDIRECT.concat("list");
//        ModelAndView mav = new ModelAndView(viewName);
//
//        // Init message success list
//        MessageList messageList = new MessageList(Message.SUCCESS);
//        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
//        messageList.add(msgInfo);
//
//        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
//
//        return mav;
//    }

    @RequestMapping(value = DOCUMENT_SHARE, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getDocumentShareLink(@PathVariable String customerAlias,
                                       @RequestParam(value = "id", required = true, defaultValue = "") Long id,
                                       @RequestParam(value = "version", required = true, defaultValue = "") Long versionId, Locale locale,
                                       Model model, final RedirectAttributes redirectAttributes)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (id <= 0) {
            throw new BusinessException("illegal data: id");
        }
        String linkShare = documentService.genDocumentShareToken(id, versionId);
        return linkShare;
    }

//    /**
//     * @param mav
//     */
//    private void loadSelectionCategories(ModelAndView mav, Long customerTypeId) {
//        List<DocumentCategoryNode> selectionCategoryTree = documentService.findSelectionCategoryTree(null,
//                customerTypeId.toString());
//        String selectionCategoryTreeJson = CommonJsonUtil.convertObjectToJsonString(selectionCategoryTree);
//        mav.addObject("selectionCategories", selectionCategoryTreeJson);
//    }
//    
//	private void addMsgInfo(String msgId, Locale locale, ModelAndView mav, MessageList messageLst) {
//		String msgInfo = msg.getMessage(msgId, null, locale);
//		messageLst.add(msgInfo);
//		mav.addObject(ConstantCore.MSG_LIST, messageLst);
//	}
//    @RequestMapping(value = URL_IMAGE_DOWNLOAD, method = RequestMethod.GET)
//    public void imageDownload(@PathVariable String customerAlias,
//            @RequestParam(required = true, value = "url") String imgUrl, HttpServletRequest request,
//            HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        documentService.requestDownloadImage(imgUrl, request, response);
//    }
//
//    /**
//     * Export File Excel for Document
//     */
//    @PostMapping(DOCUMENT_EXPORTEXCEL)
//    public ModelAndView exportListDocumentExport(@PathVariable String customerAlias,
//            @ModelAttribute(value = "searchDto") CommonSearchDto searchDto, HttpServletRequest request,
//            HttpServletResponse response, Locale locale) {
//
//        /*---- taitm - phân quyền theo menu -------*/
//        if (!hasRoleEdit(customerAlias)) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        /*---- END -------*/
//
//        try {
//            // add cookie for close loading on client
//            Utils.addCookieForExport(searchDto.getToken(), request, response);
//            searchDto.setCustomerTypeId(HDBankUtil.getCustomerType(customerAlias));
//            searchDto.setLanguageCode(locale.toString());
//
//            // exportExcel
//            documentService.exportExcelDocument(searchDto, response, locale);
//        } catch (Exception e) {
//            logger.error("##exportListDocumentExport##", e);
//        }
//
//        ModelAndView mav = new ModelAndView(HDB_ADMIN_DOCUMENT_LIST_VIEW);
//        Long customerTypeId = HDBankUtil.getCustomerType(customerAlias);
//        searchDto.setCustomerTypeId(customerTypeId);
//        mav.addObject("searchDto", searchDto);
//
//        return mav;
//    }

    private boolean hasRoleAccess(String customerAlias) {
        // Security for this page.
        if (customerAlias.equals(UrlConst.PERSONAL)) {
            // Security for this page.
            if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_DOCUMENT)) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    // sắp xếp
//	@RequestMapping(value = AdminUrlConst.LIST_SORT, method = { RequestMethod.GET })
//	public ModelAndView getSortPage(@PathVariable(value = "customerAlias") String customerAlias,
//			@ModelAttribute(value = "searchDto") CommonSearchDto searchDto, Locale locale,
//			@RequestParam(required = false, value = "typeId") Long typeId) {
//		ModelAndView mav = new ModelAndView(VIEW_DOCUMENT_LIST_SORT);
//		try {
//			/*---- taitm - phân quyền theo menu -------*/
//			if (!hasRoleAccess(customerAlias)) {
//				return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//			}
//			/*---- END -------*/
//			Long customerTypeId = HDBankUtil.getCustomerType(customerAlias);
//			searchDto.setCustomerTypeId(customerTypeId);
//			// get lost DocTypes
//			List<DocumentTypeSelectionDto> lstDocTypes = new ArrayList<>();
//			List<DocumentEditDto> documentList = new ArrayList<>();
//			if (typeId == null) {
//				lstDocTypes = documentService.getSelectionTypeList(customerTypeId, locale.toString(),
//						CmsStepNoStatusConstant.STEP_APPROVED);
//				typeId = lstDocTypes.get(0).getId();
//				// Load danh sách tài liệu
//				documentList = documentService.findListDocumentForSort(locale.getLanguage(), customerTypeId, typeId);
//			} else {
//				lstDocTypes = documentService.getSelectionTypeList(customerTypeId, locale.toString(),
//						CmsStepNoStatusConstant.STEP_APPROVED);
//				// Load danh sách tài liệu
//				documentList = documentService.findListDocumentForSort(locale.getLanguage(), customerTypeId, typeId);
//			}
//			searchDto.setTypeId(typeId);
//
//			String url = customerAlias + "/document/list/sort";
//
//			mav.addObject("pageUrl", url);
//			mav.addObject("sortPageModel", documentList);
//			mav.addObject("lstDocTypes", lstDocTypes);
//			mav.addObject("customerAlias", customerAlias);
//			mav.addObject("searchDto", searchDto);
//		} catch (Exception e) {
//			logger.error("##getSortPage##", e.getMessage());
//		}
//		return mav;
//	}
//
//    @RequestMapping(value = "documentType/change", method = { RequestMethod.POST })
//    public ModelAndView changeDocumentType(@PathVariable(value = "customerAlias") String customerAlias,
//            @RequestParam(value = "typeId") Long typeId, Locale locale) {
//        ModelAndView mav = new ModelAndView(HDB_ADMIN_DOCUMENT_TABLE_SORT_VIEW);
//        try {
//
//            if (!hasRoleAccess(customerAlias)) {
//                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//            }
//            Long customerTypeId = HDBankUtil.getCustomerType(customerAlias);
//            List<DocumentEditDto> documentList = new ArrayList<>();
//            documentList = documentService.findListDocumentForSort(locale.getLanguage(), customerTypeId, typeId);
//
//            mav.addObject("sortPageModel", documentList);
//        } catch (Exception e) {
//            logger.error("##changeDocumentType##", e.getMessage());
//        }
//        return mav;
//    }

//    @RequestMapping(value = "save-doc-sort", method = { RequestMethod.POST })
//    public ModelAndView changeDocumentType(@PathVariable(value = "customerAlias") String customerAlias,
//            @RequestParam(required = false, value = "typeId") Long typeId,
//            @ModelAttribute(value = "searchDto") CommonSearchDto searchDto, Locale locale,
//            @RequestBody DocumentEditDto documentDto, RedirectAttributes redirectAttributes) {
//
//        /*---- taitm - phân quyền theo menu -------*/
//        if (!hasRoleEdit(customerAlias)) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
//        /*---- END -------*/
//        Long customerTypeId = HDBankUtil.getCustomerType(customerAlias);
//        searchDto.setCustomerTypeId(customerTypeId);
//        searchDto.setTypeId(typeId);
////		Boolean result = documentService.updateSortDocumentAll(documentDto.getSortOderList());
//
//        // update success redirect sort list page
//        MessageList messageList = new MessageList(Message.SUCCESS);
//        if (result == false) {
//            messageList = new MessageList(Message.ERROR);
//            String msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
//            messageList.add(msgInfo);
//        } else {
//            String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
//            messageList.add(msgInfo);
//        }
//
//        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(customerAlias).concat(UrlConst.ROOT)
//                .concat(UrlConst.DOCUMENT).concat(UrlConst.ROOT).concat(AdminUrlConst.LIST_SORT);
//        viewName += "?typeId=" + typeId;
//        ModelAndView mav = new ModelAndView(viewName);
//        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
//        return mav;
//    }

    @Override
    public String getTitlePageList(Locale locale) {
        return msg.getMessage("document.list.title", null, locale);
    }

    @Override
    public String getPermisionItem() {
        return null;
    }

    @Override
    public DocumentWorkflowCommonService<DocumentEditDto, DocumentEditDto> getService() {
        return documentService;
    }

    @Override
    public String getControllerURL(String customerAlias) {
        return customerAlias + "/document";
    }

    @Override
    public String getBusinessCode(String customerAlias) {
        String businessCode = CmsCommonConstant.BUSINESS_CMS;
        return businessCode;
    }

    @Override
    public String viewEdit(String customerAlias) {
        return VIEW_EDIT;
    }

    @Override
    public void sendEmailAction(DocumentEditDto editDto, Long buttonId) {
        String ROLE_CHECKER = "5";// nguoi duyet bai
        String ROLE_MAKER = "4"; // nguoi gui duyet

        String REFUSE = "Reject";// tu choi
        String BROWSE = "Approve";// duyet

        String SEND_BROWSE = "Submit";// gui duyet
        String WITHDRAW_POST = "Retract";// rut lai


        String buttonName = documentService.getbutton(buttonId, editDto.getProcessDeployId());

        //get mail top history
        DocumentAppRes<DocumentEditDto> docDto = new DocumentAppRes();
        Long docId = editDto.getDocId();
        if (docId != null) {
            try {
                docDto = getService().detail(docId);
            } catch (Exception e) {
            }
        }
        List<JcaGroupEmailDto> lstEmailCc = new ArrayList<>();
        // get email by process step

        String userName = UserProfileUtils.getUserNameLogin();
        JcaGroupEmailDto mailAgentCode = jcaEmailTemplateService.findUserNameAction(userName);
        switch (buttonName) {
            case "Submit":
                // gửi duyệt : Cc: người submit, <Admin>
                lstEmailCc = documentService.lstEmailCcByCondition(CMS_ROLE_ADMIN, null);
                lstEmailCc.add(mailAgentCode);
                break;
            case "Retract":
                lstEmailCc = documentService.lstEmailCcByCondition(CMS_ROLE_ADMIN, null);
                lstEmailCc.add(mailAgentCode);
//			rúc lại: Cc: người submit, <Admin>
                break;
            case "Approve":
                lstEmailCc = documentService.lstEmailCcByCondition(CMS_ROLE_ADMIN, null);
//			approve: Cc: <Admin>
                break;
            case "Reject":
                lstEmailCc = documentService.lstEmailCcByCondition(CMS_ROLE_ADMIN, null);
//			reject: Cc: <Admin>
                break;
            default:
                break;
        }
        String mailReturnUser = docDto.getListProcessHistory().get(0).getCompletedEmail();

        //get category name
        String entity = documentService.getLsDocument(editDto.getId(), editDto.getLanguageCode());

        List<String> emailCc = new ArrayList<>();
        JcaEmailDto jcaEmailDto = new JcaEmailDto();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        final String senderAddress = systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT, null);
        jcaEmailDto.setSenderAddress(senderAddress);

        jcaEmailDto.setContentType("text/html; charset=utf-8");

        Map<String, Map<String, Object>> mapDataEmailUser = new HashMap<>();

        if (StringUtils.equalsIgnoreCase(buttonName, SEND_BROWSE) || StringUtils.equalsIgnoreCase(buttonName, WITHDRAW_POST)) {
            List<JcaGroupEmailDto> mailCheckr = new ArrayList<>();
            List<String> emailTo = new ArrayList<>();
            mailCheckr = documentService.getListRoleNameByUserNameAndProcessDeployId(null, editDto.getProcessDeployId(), "00002");
            for (JcaGroupEmailDto ls : mailCheckr) {
                Map<String, Object> mapDataEmail = new HashMap<>();
                if (!StringUtils.isBlank(editDto.getId().toString())) {
                    String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/personal/document/edit?id=" + editDto.getId().toString();
                    mapDataEmail.put("id", baseUrl);
                }
                mapDataEmail.put("fullname", "Approver");
                if (!StringUtils.isBlank(mailAgentCode.getUserName())) {
                    mapDataEmail.put("user", mailAgentCode.getUserName());
                } else {
                    mapDataEmail.put("user", " ");
                }
                if (!StringUtils.isBlank(editDto.getCode())) {
                    mapDataEmail.put("code", editDto.getCode());
                } else {
                    mapDataEmail.put("code", " ");
                }
                if (!StringUtils.isBlank(entity)) {
                    mapDataEmail.put("categoryId", entity);
                } else {
                    mapDataEmail.put("categoryId", " ");
                }
                if (!StringUtils.isBlank(editDto.getListLanguage().get(0).getTitle())) {
                    mapDataEmail.put("title", editDto.getListLanguage().get(0).getTitle());
                } else {
                    mapDataEmail.put("title", " ");
                }
                if (editDto.getPostedDate() != null) {
                    mapDataEmail.put("postedDate", format.format(editDto.getPostedDate().getTime()));
                } else {
                    mapDataEmail.put("postedDate", " ");
                }
                if (editDto.getExpirationDate() != null) {
                    mapDataEmail.put("expirationDate", format.format(editDto.getExpirationDate().getTime()));
                } else {
                    mapDataEmail.put("expirationDate", " ");
                }
                if (!StringUtils.isBlank(editDto.getNote()) && StringUtils.equalsIgnoreCase(buttonName, WITHDRAW_POST)) {
                    mapDataEmail.put("note", editDto.getNote());
                } else {
                    mapDataEmail.put("note", " ");
                }
                mapDataEmailUser.put(ls.getUserName(), mapDataEmail);
                if (!emailTo.contains(ls.getEmail())) {
                    emailTo.add(ls.getEmail());
                }

                for (JcaGroupEmailDto lsd : lstEmailCc) {
                    if (!emailCc.contains(lsd.getEmail())) {
                        emailCc.add(lsd.getEmail());
                    }
                }
                String emailTemplaMail = null;
                if (StringUtils.equalsIgnoreCase(buttonName, SEND_BROWSE)) {
                    emailTemplaMail = TEMPLATE_MAIL_ACTION_MODERATOR_SEND;
                } else {
                    emailTemplaMail = TEMPLATE_MAIL_ACTION_MODERATIR_WITHDRAW;
                }
                JcaEmailTemplateDto emailTemplate = jcaEmailTemplateService
                        .findJcaEmailTemplateDtoByCode(emailTemplaMail);
                if (emailTemplate != null) {
                    jcaEmailDto.setSubject(emailTemplate.getTemplateSubject());
                    String content = slaEmailTemplateService.replaceParam(emailTemplate.getTemplateContent(), mapDataEmail);
                    jcaEmailDto.setEmailContent(content);
                    jcaEmailDto.setToString(String.join(",", emailTo));
                    jcaEmailDto.setToAddress(emailTo);
                    jcaEmailDto.setCcAddress(emailCc);
                    jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
                }
            }
            jcaEmailService.sendEmail(jcaEmailDto);
        }

        if (StringUtils.equalsIgnoreCase(buttonName, REFUSE) || StringUtils.equalsIgnoreCase(buttonName, BROWSE)) {
            Map<String, Object> mapDataEmail = new HashMap<>();
            List<String> emailTo = new ArrayList<>();

            emailTo.add(mailReturnUser);
            for (JcaGroupEmailDto lsd : lstEmailCc) {
                if (!emailCc.contains(lsd.getEmail())) {
                    emailCc.add(lsd.getEmail());
                }
            }

            if (!StringUtils.isBlank(editDto.getId().toString())) {
                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/personal/document/edit?id=" + editDto.getId().toString();
                mapDataEmail.put("id", baseUrl);
            }
            //mapDataEmail.put("fullname", docDto.getListProcessHistory().get(0).getCompletedName());
            mapDataEmail.put("fullname", "Moderator");


            if (!StringUtils.isBlank(mailAgentCode.getUserName())) {
                mapDataEmail.put("user", mailAgentCode.getUserName());
            } else {
                mapDataEmail.put("user", " ");
            }

            if (!StringUtils.isBlank(editDto.getCode())) {
                mapDataEmail.put("code", editDto.getCode());
            } else {
                mapDataEmail.put("code", " ");
            }
            if (!StringUtils.isBlank(entity)) {
                mapDataEmail.put("categoryId", entity);
            } else {
                mapDataEmail.put("categoryId", " ");
            }
            if (!StringUtils.isBlank(editDto.getListLanguage().get(0).getTitle())) {
                mapDataEmail.put("title", editDto.getListLanguage().get(0).getTitle());
            } else {
                mapDataEmail.put("title", " ");
            }
            if (editDto.getPostedDate() != null) {
                mapDataEmail.put("postedDate", format.format(editDto.getPostedDate().getTime()));
            } else {
                mapDataEmail.put("postedDate", " ");
            }
            if (editDto.getExpirationDate() != null) {
                mapDataEmail.put("expirationDate", format.format(editDto.getExpirationDate().getTime()));
            } else {
                mapDataEmail.put("expirationDate", " ");
            }
            if (!StringUtils.isBlank(editDto.getNote())) {
                mapDataEmail.put("note", editDto.getNote());
            } else {
                mapDataEmail.put("note", " ");
            }

            String emailTemplaMail = null;
            if (StringUtils.equalsIgnoreCase(buttonName, BROWSE)) {
                emailTemplaMail = TEMPLATE_MAIL_RETURN_BROWSER;
            } else {
                emailTemplaMail = TEMPLATE_MAIL_RETURN_REFUSE;
            }
            JcaEmailTemplateDto emailTemplate = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(emailTemplaMail);
            if (emailTemplate != null) {

                jcaEmailDto.setSubject(emailTemplate.getTemplateSubject());
                String content = slaEmailTemplateService.replaceParam(emailTemplate.getTemplateContent(), mapDataEmail);
                jcaEmailDto.setEmailContent(content);

                jcaEmailDto.setToString(String.join(",", emailTo));
                jcaEmailDto.setToAddress(emailTo);
                jcaEmailDto.setCcAddress(emailCc);
                jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
                jcaEmailService.sendEmail(jcaEmailDto);
            }
        }
    }

    @Override
    public void sendEmailEdit(DocumentEditDto editDto, Long userUpdatedId) {
//        String ROLE_CHECKER = "5";// nguoi duyet baiđ 
//        String ROLE_ADMIN = "6"; // ADMIN
        JcaAccountDto userUpdateDoc = new JcaAccountDto();
        try {
            userUpdateDoc = jcaAccountService.getJcaAccountDtoById(userUpdatedId);
        } catch (Exception e) {
            System.out.print("userUpdatedId" + e);
        }
        Document getCreateBy = documentService.getlsDocument(editDto.getId());

        String userName = UserProfileUtils.getUserNameLogin();
        JcaGroupEmailDto user = jcaEmailTemplateService.findUserNameAction(userName);

        JcaGroupEmailDto mailAgentCode = jcaEmailTemplateService.findUserNameAction(getCreateBy.getCreateBy());

        //get category name
        String entity = documentService.getLsDocument(editDto.getId(), editDto.getListLanguage().get(0).getLanguageCode());

//        List<JcaGroupEmailDto> mailAdmin = documentService.lstEmailCcByCondition(CMS_ROLE_ADMIN, null);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        List<String> emailTo = new ArrayList<>();
        List<String> emailCc1 = new ArrayList<>();
        JcaEmailDto jcaEmailDto = new JcaEmailDto();
        final String senderAddress = systemConfig.getConfig(SystemConfig.EMAIL_DEFAULT, null);
        jcaEmailDto.setSenderAddress(senderAddress);
        jcaEmailDto.setContentType("text/html; charset=utf-8");
        Map<String, Object> mapDataEmail = new HashMap<>();
        if (!StringUtils.isBlank(editDto.getId().toString())) {
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/personal/document/edit?id=" + editDto.getId().toString();
            mapDataEmail.put("id", baseUrl);
        }
            mapDataEmail.put("fullname", "Moderator");
        if (!StringUtils.isBlank(user.getUserName())) {
            mapDataEmail.put("user", user.getUserName());
        } else {
            mapDataEmail.put("user", " ");
        }
        if (!StringUtils.isBlank(editDto.getCode())) {
            mapDataEmail.put("code", editDto.getCode());
        } else {
            mapDataEmail.put("code", " ");
        }
        if (!StringUtils.isBlank(entity)) {
            mapDataEmail.put("categoryId", entity);
        } else {
            mapDataEmail.put("categoryId", " ");
        }
        if (!StringUtils.isBlank(editDto.getListLanguage().get(0).getTitle())) {
            mapDataEmail.put("title", editDto.getListLanguage().get(0).getTitle());
        } else {
            mapDataEmail.put("title", " ");
        }
        if (editDto.getPostedDate() != null) {
            mapDataEmail.put("postedDate", format.format(editDto.getPostedDate().getTime()));
        } else {
            mapDataEmail.put("postedDate", " ");
        }
        if (editDto.getExpirationDate() != null) {
            mapDataEmail.put("expirationDate", format.format(editDto.getExpirationDate().getTime()));
        } else {
            mapDataEmail.put("expirationDate", " ");
        }
        if (!StringUtils.isBlank(editDto.getNote())) {
            mapDataEmail.put("note", editDto.getNote());
        } else {
            mapDataEmail.put("note", " ");
        }

        emailTo.add(mailAgentCode.getEmail());

        emailCc1.add(user.getEmail());
        emailCc1.add(userUpdateDoc.getEmail());
        Document data = documentService.getlsDocument(editDto.getId());

        String template = null;
        if (data.isEnabled() != editDto.isEnabled()) {
            template = TEMPLATE_MAIL_EDIT_HIDE;
        } else {
            template = TEMPLATE_MAIL_RETURN_EDIT;
        }
        JcaEmailTemplateDto emailTemplate = jcaEmailTemplateService.findJcaEmailTemplateDtoByCode(template);
        if (emailTemplate != null) {
            jcaEmailDto.setSubject(emailTemplate.getTemplateSubject());
            String content = slaEmailTemplateService.replaceParam(emailTemplate.getTemplateContent(), mapDataEmail);
            jcaEmailDto.setEmailContent(content);
            jcaEmailDto.setToString(String.join(",", emailTo));
            jcaEmailDto.setToAddress(emailTo);
            jcaEmailDto.setCcAddress(emailCc1);

            jcaEmailDto.setSendEmailType(SendEmailTypeEnum.SEND_DIRECT_SAVE.getValue());
            jcaEmailService.sendEmail(jcaEmailDto);
        }


    }

    @Override
    public String objectEditName() {
        return "updateDto"; // "documentEdit"
    }

    @Override
    public String firstStepInProcess(String customerAlias) {
        return "taskSubmit";
    }

    @Override
    public String roleForAttachment(String customerAlias) {
        return null;
    }

    @Override
    public void initDataEdit(ModelAndView mav, String customAlias, DocumentEditDto editDto, boolean isDetail,
                             Locale locale) {
        CmsLanguageUtils.initLanguageList(mav);

        editDto.setCustomerAlias("personal");
        editDto.setCustomerTypeId(9L);
        editDto.setIndexLangActive(0);

        List<Select2Dto> listCategory = select2DataService.getListDocumentCategory(locale.toString(), UserProfileUtils.getChannel());
        mav.addObject("listCategory", listCategory);

        mav.addObject(super.objectEditName(), editDto);
    }

    @Override
    public void validate(DocumentEditDto editDto, BindingResult bindingResult) {
        documentValidator.validate(editDto, bindingResult);

    }

    @Override
    public void setParamSearchForListSort(DocumentSearchDto searchDto, Locale locale) {
        super.setParamSearchForListSort(searchDto, locale);

        searchDto.setUsername(UserProfileUtils.getUserNameLogin());
    }

    @Override
    public void initScreenListSort(ModelAndView mav, DocumentSearchDto searchDto, Locale locale) {
        List<Select2Dto> listCategory = select2DataService.getListDocumentCategory(locale.toString(), UserProfileUtils.getChannel());
        mav.addObject("listCategory", listCategory);
    }

    @Override
    public void customRedirectAttributesForSaveListSort(RedirectAttributes redirectAttributes,
                                                        DocumentSearchDto searchDto, Locale locale) {
        super.customRedirectAttributesForSaveListSort(redirectAttributes, searchDto, locale);

        redirectAttributes.addAttribute("categoryId", searchDto.getCategoryId());
    }

    @Override
    public void initScreenList(ModelAndView mav, DocumentSearchDto documentSearchDto, Locale locale, String customerAlias) {
        mav.addObject("hasRoleMaker", hasRoleMaker(customerAlias));
    }

    @Override
    public String getFunctionCode() {
        return "M_DOCUMENT";
    }

    @Override
    public String viewListSort(String customerAlias) {
        return VIEW_LIST_SORT;
    }

    @Override
    public String viewListSortTable(String customerAlias) {
        return VIEW_TABLE_SORT;
    }

    @Override
    public String getUrlByCustomerAlias(String customerAlias) {
        return customerAlias + "/document";
    }

    @Override
    public Class<DocumentSearchResultDto> getClassSearchResult() {
        return DocumentSearchResultDto.class;
    }

    @Override
    public boolean hasRoleForList(String customerAlias) {
        return hasRoleAccess(customerAlias);
    }

    @Override
    public boolean hasRoleForAdd(String customerAlias) {
        // Security for this page.
        if (customerAlias.equals(UrlConst.PERSONAL)) {
            if (!UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_DOCUMENT_EDIT) && !UserProfileUtils
                    .hasRole(CmsRoleConstant.BUTTON_DOCUMENT_EDIT.concat(ConstantCore.COLON_EDIT))) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    @Override
    public boolean hasRoleForEdit(String customerAlias) {
        // Security for this page.
        JpmProcessDeployDto processDeployDto = jpmProcessDeployService.getJpmProcessDeployLastedByBusinessCode(
                UserProfileUtils.getCompanyId(), getBusinessCode(customerAlias));
        List<JcaGroupEmailDto> lstRoleName = documentService.getListRoleNameByUserNameAndProcessDeployId(UserProfileUtils.getUserNameLogin(), processDeployDto.getProcessDeployId(), null);
        boolean hasRoleMaker = CollectionUtils.isNotEmpty(lstRoleName);
        if (customerAlias.equals(UrlConst.PERSONAL)) {
//            if (!UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_DOCUMENT_EDIT) && !UserProfileUtils
//                    .hasRole(CmsRoleConstant.BUTTON_DOCUMENT_EDIT.concat(ConstantCore.COLON_EDIT))) {
//                return false;
//            }
            return hasRoleMaker;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasRoleMaker(String customerAlias) {
        // Security for this page.
        JpmProcessDeployDto processDeployDto = jpmProcessDeployService.getJpmProcessDeployLastedByBusinessCode(
                UserProfileUtils.getCompanyId(), getBusinessCode(customerAlias));
        List<JcaGroupEmailDto> lstRoleName = documentService.getListRoleNameByUserNameAndProcessDeployId(UserProfileUtils.getUserNameLogin(), processDeployDto.getProcessDeployId(), "00001");
        boolean hasRoleMaker = CollectionUtils.isNotEmpty(lstRoleName);
        if (customerAlias.equals(UrlConst.PERSONAL)) {
//            if (!UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_DOCUMENT_EDIT) && !UserProfileUtils
//                    .hasRole(CmsRoleConstant.BUTTON_DOCUMENT_EDIT.concat(ConstantCore.COLON_EDIT))) {
//                return false;
//            }
            return hasRoleMaker;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasRoleSpecialForEdit(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.CMS_ROLE_ADMIN.concat(ConstantCore.COLON_EDIT));
    }

    @Override
    public boolean hasRoleForDetail(String customerAlias) {
        // Security for this page.
        if (customerAlias.equals(UrlConst.PERSONAL)) {
            if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_DOCUMENT)) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean showButtonUpdate(DocumentEditDto editDto) {
        if (UserProfileUtils.hasRole(CmsRoleConstant.CMS_ROLE_ADMIN.concat(CoreConstant.COLON_EDIT)) || showButtonClone(editDto)) {
            return true;
        }
        return super.showButtonUpdate(editDto);
    }

    @Override
    public CmsCommonSearchFillterService<DocumentSearchDto, DocumentSearchResultDto, DocumentEditDto> getCmsCommonSearchFillterService() {
        return documentService;
    }

    public boolean showButtonImport(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_DOCUMENT_IMPORT.concat(ConstantCore.COLON_EDIT));
    }

    @Override
    public String viewList(String customerAlias) {
        return VIEW_LIST;
    }


    public boolean showButtonClone(DocumentEditDto editDto) {
        List<JcaGroupEmailDto> lstRoleName = documentService.getListRoleNameByUserNameAndProcessDeployId(UserProfileUtils.getUserNameLogin(), editDto.getProcessDeployId(), "00001");
        boolean hasRoleMaker = CollectionUtils.isNotEmpty(lstRoleName);
        if ((CmsCommonConstant.STATUS_COMMON_FINISHED.equals(editDto.getStatusCode())
                || "994".equals(editDto.getStatusCode())) && hasRoleMaker) {
            return true;
        }
        return super.showButtonClone(editDto);
    }

}
