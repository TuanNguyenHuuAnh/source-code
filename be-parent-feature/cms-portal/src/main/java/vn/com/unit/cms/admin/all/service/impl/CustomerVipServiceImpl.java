package vn.com.unit.cms.admin.all.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.dto.CustomerVipEditDto;
import vn.com.unit.cms.admin.all.dto.CustomerVipLanguageEditDto;
import vn.com.unit.cms.admin.all.dto.CustomerVipLanguageResultDto;
import vn.com.unit.cms.admin.all.dto.CustomerVipLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.EmailCommonDto;
import vn.com.unit.cms.admin.all.entity.CustomerVip;
import vn.com.unit.cms.admin.all.entity.CustomerVipLanguage;
import vn.com.unit.cms.admin.all.enumdef.ExportCustomerExportEnum;
import vn.com.unit.cms.admin.all.enumdef.StepActionEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
//import vn.com.unit.jcanary.service.AccountService;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.jcanary.utils.APIUtils;
import vn.com.unit.cms.admin.all.repository.CustomerVipLanguageRepository;
import vn.com.unit.cms.admin.all.repository.CustomerVipRepository;
import vn.com.unit.cms.admin.all.service.CustomerVipService;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.service.EmailService;
//import vn.com.unit.jcanary.utils.ExportExcelUtil;
//import vn.com.unit.jcanary.utils.ImportExcelUtil;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.entity.Language;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.ep2p.dto.AccountDetailDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;
//import vn.com.unit.jcanary.service.HistoryApproveService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CustomerVipServiceImpl extends DocumentWorkflowCommonServiceImpl<CustomerVipEditDto, CustomerVipEditDto>
	implements CustomerVipService {

	@Autowired
	private CustomerVipRepository customerVipRepository;

	@Autowired
	private CustomerVipLanguageRepository customerVipLanguageRepository;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private SystemConfig systemConfig;

//	@Autowired
//	HistoryApproveService historyApproveService;

	@Autowired
	private CmsFileService fileService;

	@Autowired
	private CmsCommonService commonService;

//	@Autowired
//	JProcessService jprocessService;

	@Autowired
	EmailService emailService;

//	@Autowired
//	AccountService accountService;

	@Autowired
	MessageSource msg;

	@Autowired
	ServletContext servletContext;

//	@Autowired
//	EmailUtil emailUtil;

	private static final String PREFIX_CODE = "CUST.";

	private static final Logger logger = LoggerFactory.getLogger(CustomerVipServiceImpl.class);

	@Override
	public PageWrapper<CustomerVipLanguageResultDto> doSearch(int page, CustomerVipLanguageSearchDto searchDto,
			Locale locale) {
		
		
		 if (null == searchDto)
			 searchDto = new CustomerVipLanguageSearchDto();

		int sizeOfPage = searchDto.getPageSize() != null ? searchDto.getPageSize()
				: systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);

		// set status name
//		searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));

		int count = customerVipRepository.countByCustomerSearchDto(searchDto);
//		if ((count % sizeOfPage == 0 && page > count / sizeOfPage)
//				|| (count % sizeOfPage > 0 && page - 1 > count / sizeOfPage)) {
//			page = 1;
//		}

		PageWrapper<CustomerVipLanguageResultDto> pageWrapper = new PageWrapper<CustomerVipLanguageResultDto>(page,
				sizeOfPage);
		List<CustomerVipLanguageResultDto> result = new ArrayList<CustomerVipLanguageResultDto>();
		if (count > 0) {
			int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);

			result = customerVipRepository.findByCustomerSearchDto(offsetSQL, sizeOfPage, searchDto);
		}

		pageWrapper.setDataAndCount(result, count);

		return pageWrapper;
	}

	@Override
	public boolean doEdit(CustomerVipEditDto customerVipEditDto, Locale locale, HttpServletRequest request)
			throws IOException {
		// user login
//		UserProfile userProfile = UserProfileUtils.getUserProfile();

		createOrEditCustomer(customerVipEditDto, UserProfileUtils.getUserNameLogin(), locale, request);

		createOrEditCustomerLanguage(customerVipEditDto, UserProfileUtils.getUserNameLogin());

		// if action process
		if (!StringUtils.equals(customerVipEditDto.getButtonCode(), StepActionEnum.SAVE.getCode())) {
			// updateHistoryApprove
			updateHistoryApprove(customerVipEditDto);

			sendMail(customerVipEditDto, request);

			// clear cache api /commons
			APIUtils.callApiGet(AdminUrlConst.URL_CACHES_COMMONS);

			// clear cache api /personal
			APIUtils.callApiGet(AdminUrlConst.URL_CACHES_PERSONAL);

			// clear cache api /corporate
			APIUtils.callApiGet(AdminUrlConst.URL_CACHES_CORPORATE);

			APIUtils.callApiGet(AdminUrlConst.URL_CACHES_HOME);
		}

		return true;
	}

	private void createOrEditCustomer(CustomerVipEditDto editDto, String usernameLogin, Locale locale,
			HttpServletRequest request) {
		// entity
		CustomerVip entity = new CustomerVip();

		// product exists id
		if (null != editDto.getId()) {

			entity = customerVipRepository.findOne(editDto.getId());

			// dữ liệu ko tồn tại hoặc đã bị xóa
			if (null == entity || entity.getDeleteDate() != null) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
			}

			if (entity.getUpdateDate() != null && !entity.getUpdateDate().equals(editDto.getUpdateDate())) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
			}

//			List<Long> lstStatus = HDBankUtil.getListStatusForDependency();
//			int countDependencies = countDependencies(entity.getId(), lstStatus);

//			if (countDependencies > 0) {
//				throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
//			}

			entity.setUpdateDate(new Date());
			entity.setUpdateBy(usernameLogin);
		} else {
			entity.setCreateDate(new Date());
			entity.setCreateBy(usernameLogin);
			entity.setCode(
					CommonUtil.getNextCode(PREFIX_CODE, commonService.getMaxCode("M_CUSTOMER_VIP", PREFIX_CODE)));
		}

		try {
			Long sort = customerVipRepository.getMaxSort();

			if (sort == null || sort == 0) {
				sort = Long.valueOf(1);
			}

			entity.setSort(sort);

			entity.setStatus(editDto.getStatus());

			if (editDto.getCustomerAlias().equals("customer-fdi")) {
				entity.setFdi(1);
				editDto.setFdi(1);
			} else if (editDto.getCustomerAlias().equals("customer-vip")) {
				entity.setVip(1);
				editDto.setVip(1);
			}
			
			// physical file template
			String physicalImgBannerTmpName = editDto.getBannerUrl();
			// upload images
			if (StringUtils.isNotEmpty(physicalImgBannerTmpName)) {
				String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalImgBannerTmpName, AdminConstant.CUSTOMER_FOLDER);
				entity.setImgBannerUrl(newPhiscalName);
				entity.setImgBannerName(editDto.getImgName());
			} else {
				entity.setImgBannerUrl(null);
				entity.setImgBannerName(null);
			}

			String physicalImgBannerMobileTmpName = editDto.getBannerMobileUrl();
			// upload images
			if (StringUtils.isNotEmpty(physicalImgBannerMobileTmpName)) {
				String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalImgBannerMobileTmpName,
						AdminConstant.CUSTOMER_FOLDER);
				entity.setImgBannerMobileUrl(newPhiscalName);
				entity.setImgBannerMobileName(editDto.getImgName());
			} else {
				entity.setImgBannerMobileUrl(null);
				entity.setImgBannerMobileName(null);
			}

			// physical file template
			String physicalImgTmpName = editDto.getImgUrl();
			// upload images
			if (StringUtils.isNotEmpty(physicalImgTmpName)) {
				String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalImgTmpName, AdminConstant.CUSTOMER_FOLDER);
				entity.setImgUrl(newPhiscalName);
				entity.setImgName(editDto.getImgName());
			} else {
				entity.setImgUrl(null);
				entity.setImgName(null);
			}

			String physicalImgMobileTmpName = editDto.getImgMobileUrl();
			// upload images
			if (StringUtils.isNotEmpty(physicalImgMobileTmpName)) {
				String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalImgMobileTmpName,
						AdminConstant.CUSTOMER_FOLDER);
				entity.setImgMobileUrl(newPhiscalName);
				entity.setImgMobileName(editDto.getImgName());
			} else {
				entity.setImgMobileUrl(null);
				entity.setImgMobileName(null);
			}
			
			if (editDto.getVip() != null && editDto.getVip() == 1) {
				entity.setImgMobileUrl(entity.getImgUrl());
				entity.setImgMobileName(entity.getImgName());
			}

			// physical file template
			String physicalIconName = editDto.getIconUrl();
			// upload icon
			if (StringUtils.isNotEmpty(physicalIconName)) {
				String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalIconName, AdminConstant.CUSTOMER_FOLDER);
				entity.setIconUrl(newPhiscalName);
				entity.setIconName(editDto.getIconName());
			} else {
				entity.setIconUrl(null);
				entity.setIconName(null);
			}

			// physical file template
			String physicalIconMobileName = editDto.getIconMobileUrl();
			// upload icon
			if (StringUtils.isNotEmpty(physicalIconMobileName)) {
				String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalIconMobileName,
						AdminConstant.CUSTOMER_FOLDER);
				entity.setIconMobileUrl(newPhiscalName);
				entity.setIconMobileName(editDto.getIconName());
			} else {
				entity.setIconMobileUrl(null);
				entity.setIconMobileName(null);
			}

			entity.setComment(editDto.getComment());
			entity.setEnabled(editDto.isEnabled());

			// if action process
			if (!StringUtils.equals(editDto.getButtonCode(), StepActionEnum.SAVE.getCode())) {
//				if (editDto.getProcessId() == null) {
//					// First step
//					JProcessStepDto processDto = jprocessService
//							.findFirstStepOfProcess(CommonConstant.BUSINESS_CUSTOMER, locale.toString());
//					editDto.setProcessId(processDto.getProcessId());
//				}
//
//				JProcessStepDto currentActionStep = jprocessService.findCurrentProcessStep(editDto.getProcessId(),
//						editDto.getStatus(), editDto.getButtonId());
//				Integer status = jprocessService.getNextStepNo(currentActionStep, null);
//
//				editDto.setOldStatus(editDto.getStatus());
//				// set status
//				editDto.setStatus(status);
//				editDto.setCurrItem(currentActionStep.getItems());
			}

			entity.setProcessId(editDto.getProcessId());
			entity.setStatus(editDto.getStatus());

			// Set comment
			//entity.setComment("");

			customerVipRepository.save(entity);

			editDto.setId(entity.getId());
			editDto.setCode(entity.getCode());

		} catch (Exception e) {
			logger.error("createOrEditCustomer: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}

	}

	private void createOrEditCustomerLanguage(CustomerVipEditDto editDto, String username) {
	    if (CollectionUtils.isNotEmpty(editDto.getCustomerVipLanguageList())) {
	        for (CustomerVipLanguageEditDto cLanguageDto : editDto.getCustomerVipLanguageList()) {

	            // entity
	            CustomerVipLanguage entity = new CustomerVipLanguage();

	            if (null != cLanguageDto.getId()) {
	                entity = customerVipLanguageRepository.findOne(cLanguageDto.getId());
	                if (null == entity) {
	                    throw new BusinessException("Not found data with id=" + cLanguageDto.getId());
	                }
	                entity.setUpdateDate(new Date());
	                entity.setUpdateBy(username);
	            } else {
	                entity.setCreateDate(new Date());
	                entity.setCreateBy(username);
	            }

	            entity.setCustomerVipId(editDto.getId());
	            entity.setLanguageCode(cLanguageDto.getLanguageCode());
	            entity.setTitle(cLanguageDto.getTitle());
	            entity.setShortContent(cLanguageDto.getShortContent());
	            entity.setContent(cLanguageDto.getContent());

	            customerVipLanguageRepository.save(entity);
	        }
	    }
	}

	private void updateHistoryApprove(CustomerVipEditDto editDto) {
		try {
			// insert comment
			HistoryApproveDto historyApproveDto = new HistoryApproveDto();
			historyApproveDto.setApprover(UserProfileUtils.getFullName());
			historyApproveDto.setComment(editDto.getComment());
			historyApproveDto.setProcessId(editDto.getProcessId());
			historyApproveDto.setProcessStep(editDto.getStatus().longValue());
			historyApproveDto.setReferenceId(editDto.getId());
			historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_CUSTOMER_VIP);
			historyApproveDto.setActionId(editDto.getButtonCode());
			historyApproveDto.setOldStep(editDto.getOldStatus());
			historyApproveDto.setAccountId(UserProfileUtils.getAccountId());
//			historyApproveService.addHistoryApprove(historyApproveDto);
		} catch (Exception e) {
			logger.error("updateHistoryApprove: " + e.getMessage());
		}
	}

    private void sendMail(CustomerVipEditDto editDto, HttpServletRequest request) {
        try {
            // locale default
            String defaultlocale = systemConfig.getConfig(SystemConfig.LANGUAGE_DEFAULT);
            Locale locale = new Locale(defaultlocale);

            EmailCommonDto emailCommon = new EmailCommonDto();
            if (editDto.getVip() != null && editDto.getVip() == 1) {
                emailCommon.setActionName(msg.getMessage("email.template.customer.vip", null, locale));
            } else if (editDto.getFdi() != null && editDto.getFdi() == 1) {
                emailCommon.setActionName(msg.getMessage("email.template.customer.fdi", null, locale));
            }

            emailCommon.setButtonAction(editDto.getButtonAction());
            emailCommon.setButtonId(editDto.getButtonCode());
            emailCommon.setComment(editDto.getComment());

            // Nội dung
            LinkedHashMap<String, String> content = new LinkedHashMap<>();

            // tìm tên loại sản phẩm

            content.put("Mã", editDto.getCode());
            content.put("Tên", editDto.getCustomerVipLanguageList().get(0).getTitle());
            emailCommon.setContent(content);

            emailCommon.setCurrItem(editDto.getCurrItem());

            emailCommon.setId(editDto.getId());
            emailCommon.setOldStatus(editDto.getOldStatus());
            emailCommon.setProcessId(editDto.getProcessId());
            emailCommon.setReferenceType(editDto.getReferenceType());
            emailCommon.setStatus(editDto.getStatus());

            // Subject của email
            if (editDto.getVip() != null && editDto.getVip() == 1) {
                emailCommon.setSubject(msg.getMessage("subject.email.template.customer.vip", null, locale));
            } else if (editDto.getFdi() != null && editDto.getFdi() == 1) {
                emailCommon.setSubject(msg.getMessage("subject.email.template.customer.fdi", null, locale));
            }

            emailCommon.setUrl(
                    CmsUtils.getBaseUrl(request) + "/" + editDto.getCustomerAlias() + "/edit?id=" + editDto.getId());

//	      emailUtil.sendMail(emailCommon, request, locale);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

	@Override
	public CustomerVipEditDto getCustomerVip(Long id, Locale locale) {
		CustomerVipEditDto resultDto = new CustomerVipEditDto();

		// languageList
		List<Language> languageList = languageService.findAllActive();

		if (id == null) {
			// resultDto.setProcessId(processService.getProcessIdByBusinessCode(MasterType.AP1.toString()));
			resultDto.setStatus(StepStatusEnum.DRAFT.getStepNo());
			resultDto.setEnabled(Boolean.TRUE);

			// set ProductDetailLanguage
			List<CustomerVipLanguageEditDto> customerVipLanguageList = initCustomerVipLanguageList(languageList);
			resultDto.setCustomerVipLanguageList(customerVipLanguageList);

			@SuppressWarnings("unused")
			Long processId = resultDto.getProcessId();
//			if (processId == null) {
//				// First step
//				JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(CommonConstant.BUSINESS_CUSTOMER,
//						locale.toString());
//				processId = processDto.getProcessId();
//			}
//
//			// List button of step
//			List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId,
//					resultDto.getStatus(), locale.toString());
//			resultDto.setStepBtnList(stepButtonList);
//
//			String statusName = jprocessService.getStatusName(resultDto.getProcessId(), resultDto.getStatus(), locale);
//			resultDto.setStatusName(statusName);

			return resultDto;
		}
		// set customerVip
		CustomerVip customerVip = customerVipRepository.findOne(id);

		// dữ liệu ko tồn tại hoặc đã bị xóa
		if (customerVip == null || customerVip.getDeleteDate() != null) {
			throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
		}
		if (null != customerVip) {
			resultDto.setId(customerVip.getId());
			resultDto.setCode(customerVip.getCode());
			resultDto.setComment(customerVip.getComment());

			resultDto.setImgName(customerVip.getImgName());
			resultDto.setImgUrl(customerVip.getImgUrl());

			resultDto.setImgMobileName(customerVip.getImgMobileName());
			resultDto.setImgMobileUrl(customerVip.getImgMobileUrl());

			resultDto.setIconName(customerVip.getIconName());
			resultDto.setIconUrl(customerVip.getIconUrl());

			resultDto.setIconMobileName(customerVip.getIconMobileName());
			resultDto.setIconMobileUrl(customerVip.getIconMobileUrl());
			
			resultDto.setBannerName(customerVip.getImgBannerName());
			resultDto.setBannerUrl(customerVip.getImgBannerUrl());
			
			resultDto.setBannerMobileName(customerVip.getImgBannerMobileName());
			resultDto.setBannerMobileUrl(customerVip.getImgBannerMobileUrl());

			resultDto.setEnabled(customerVip.isEnabled());
			resultDto.setStatus(customerVip.getStatus());
			resultDto.setSort(customerVip.getSort());
			resultDto.setReferenceType(ConstantHistoryApprove.APPROVE_CUSTOMER_VIP);
			resultDto.setReferenceId(customerVip.getId());

			resultDto.setCreateBy(customerVip.getCreateBy());
			resultDto.setApprovedBy(customerVip.getApprovedBy());
			resultDto.setApprovedDate(customerVip.getApprovedDate());
			resultDto.setPublishedBy(customerVip.getPublishedBy());
			resultDto.setPublishedDate(customerVip.getPublishedDate());

			resultDto.setProcessId(customerVip.getProcessId());
			resultDto.setUpdateDate(customerVip.getUpdateDate());

			@SuppressWarnings("unused")
			Long processId = resultDto.getProcessId();
//			if (processId == null) {
//				// First step
//				JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(CommonConstant.BUSINESS_CUSTOMER,
//						locale.toString());
//				processId = processDto.getProcessId();
//			}
//
//			// List button of step
//			List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId,
//					resultDto.getStatus(), locale.toString());
//			resultDto.setStepBtnList(stepButtonList);
//
//			String statusName = jprocessService.getStatusName(resultDto.getProcessId(), resultDto.getStatus(), locale);
//			resultDto.setStatusName(statusName);
//
//			String statusCode = jprocessService.getStatusCode(resultDto.getProcessId(), resultDto.getStatus(), locale);
//			resultDto.setStatusCode(statusCode);
		}

		// set customerVipLanguage
		List<CustomerVipLanguageEditDto> customerVipLanguageList = getCustomerVipLanguageList(id, languageList);
		resultDto.setCustomerVipLanguageList(customerVipLanguageList);

		return resultDto;
	}

	private List<CustomerVipLanguageEditDto> initCustomerVipLanguageList(List<Language> languageList) {
		List<CustomerVipLanguageEditDto> resultList = new ArrayList<>();

		// loop language
		for (Language language : languageList) {
			CustomerVipLanguageEditDto detailDto = new CustomerVipLanguageEditDto();
			detailDto.setLanguageCode(language.getCode());
			resultList.add(detailDto);
		}

		return resultList;
	}

	private List<CustomerVipLanguageEditDto> getCustomerVipLanguageList(Long id, List<Language> languageList) {
		List<CustomerVipLanguageEditDto> resultList = new ArrayList<CustomerVipLanguageEditDto>();
		// ProductLanguageList
		List<CustomerVipLanguage> customerVipLanguageList = customerVipLanguageRepository.findByCustomerId(id);

		// loop language
		for (Language language : languageList) {
			// loop productLanguageList
			for (CustomerVipLanguage entity : customerVipLanguageList) {
				// productLanguageId is languageId
				if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
					CustomerVipLanguageEditDto productLanguageDto = new CustomerVipLanguageEditDto();
					productLanguageDto.setId(entity.getId());
					productLanguageDto.setLanguageCode(entity.getLanguageCode());
					productLanguageDto.setTitle(entity.getTitle());
					productLanguageDto.setShortContent(entity.getShortContent());
					productLanguageDto.setContent(entity.getContent());

					resultList.add(productLanguageDto);
					break;
				}
			}
		}
		return resultList;
	}

	@Override
	public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response) {
		boolean retVal = false;
		if (fileUrl != null) {
			if (CmsUtils.fileExistedInMain(fileUrl)) {
				fileService.download(fileUrl, request, response);
				retVal = true;
			} else if (CmsUtils.fileExistedInTemp(fileUrl)) {
				fileService.downloadTemp(fileUrl, request, response);
				retVal = true;
			}
		}
		return retVal;
	}

	@Override
	public CustomerVip getCustomerVipById(Long id) {
		return customerVipRepository.findOne(id);
	}

	@Override
	public void deleteData(Long id, String username) {
		CustomerVip cus = customerVipRepository.findOne(id);
		cus.setDeleteBy(username);
		cus.setDeleteDate(new Date());
		customerVipRepository.save(cus);
	}

	@Override
	public void exportExcel(CustomerVipLanguageSearchDto searchDto, HttpServletResponse res, Locale locale) throws Exception {
		// set status name
		searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));
		
		String templateName = "";
		/* change template */
		if (searchDto.getVip() != null && searchDto.getVip() == 1) {
			templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_CUSTOMER_VIP;
		}else if (searchDto.getFdi() != null && searchDto.getFdi() == 1) {
			templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_CUSTOMER_FDI;
		}

		String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
				+ CmsCommonConstant.TYPE_EXCEL;
		String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);

		List<CustomerVipLanguageResultDto> lstData = customerVipRepository.exportExcelWithCondition(searchDto);
		List<ItemColsExcelDto> cols = new ArrayList<>();
		// start fill data to workbook
		ImportExcelUtil.setListColumnExcel(ExportCustomerExportEnum.class, cols);
		ExportExcelUtil<CustomerVipLanguageResultDto> exportExcel = new ExportExcelUtil<>();
		// do export
		exportExcel.exportExcelWithXSSFNonPass(template, locale, lstData, CustomerVipLanguageResultDto.class,
				cols, datePattern, res, templateName);
	}
	
	@Override
    public DocumentActionReq actionBusiness(DocumentActionReq documentActionReq, EfoDocDto efoDocDto, Locale locale)
            throws Exception {

		// documentActionReq.getProcessStatusCode();

		CustomerVipEditDto editDto = (CustomerVipEditDto) documentActionReq;
		this.doEdit(editDto, locale, null);
		return editDto;
	}

	@Override
	public void sendMailProcess(DocumentActionReq abstractProcessDto, Integer nextStepNo, String nextStatusCode,
			Integer curStepNo, AccountDetailDto accountAction, HttpServletRequest httpServletRequest, Locale locale)
			throws Exception {
		super.sendMailProcess(abstractProcessDto, nextStepNo, nextStatusCode, curStepNo, accountAction,
				httpServletRequest, locale);
	}

	@Override
	public CustomerVipEditDto getEdit(Long id, String customerAlias, Locale locale) {

		return getCustomerVip(id, locale);
	}

}
