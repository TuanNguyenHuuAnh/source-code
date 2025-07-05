package vn.com.unit.cms.admin.all.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstDispType;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.constant.CmsStepNoStatusConstant;
import vn.com.unit.cms.admin.all.core.InvestorCategoryNode;
import vn.com.unit.cms.admin.all.dto.EmailCommonDto;
import vn.com.unit.cms.admin.all.dto.ExportInvestorReportDto;
import vn.com.unit.cms.admin.all.dto.InvestorCategoryDto;
import vn.com.unit.cms.admin.all.dto.InvestorCategoryLanguageDto;
import vn.com.unit.cms.admin.all.dto.InvestorEditDto;
import vn.com.unit.cms.admin.all.dto.InvestorLanguageDto;
import vn.com.unit.cms.admin.all.dto.InvestorLanguageEditDto;
import vn.com.unit.cms.admin.all.dto.InvestorSearchDto;
import vn.com.unit.cms.admin.all.dto.SortPageDto;
import vn.com.unit.cms.admin.all.entity.Investor;
import vn.com.unit.cms.admin.all.entity.InvestorCategory;
import vn.com.unit.cms.admin.all.entity.InvestorLanguage;
import vn.com.unit.cms.admin.all.enumdef.ExportInvestorExportEnum;
import vn.com.unit.cms.admin.all.enumdef.StepActionEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.InvestorCategoryRepository;
import vn.com.unit.cms.admin.all.repository.InvestorLanguageRepository;
import vn.com.unit.cms.admin.all.repository.InvestorRepository;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.InvestorService;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.utils.CommonJsonUtil;
//import vn.com.unit.jcanary.utils.ExportExcelUtil;
//import vn.com.unit.jcanary.utils.ImportExcelUtil;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.LanguageDto;
//import vn.com.unit.jcanary.entity.ConstantDisplay;
import vn.com.unit.core.entity.Language;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
//import vn.com.unit.util.Util;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class InvestorServiceImpl extends DocumentWorkflowCommonServiceImpl<InvestorEditDto, InvestorEditDto> implements InvestorService {

	@Autowired
	private InvestorRepository investorRepository;

	@Autowired
	private InvestorCategoryRepository investorCategoryRepository;

	@Autowired
	private InvestorLanguageRepository investorLanguageRepository;

//	@Autowired
//	private JProcessService jprocessService;

	@Autowired
	private CmsFileService fileService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private LanguageService languageService;

//	@Autowired
//	HistoryApproveService historyApproveService;
//
//	@Autowired
//	EmailUtil emailUtil;

	@Autowired
	ServletContext servletContext;

	@Autowired
	private CmsCommonService commonService;

	@Autowired
	MessageSource msg;

//	@Autowired
//	ConstantDisplayService constDisplayService;
	
    @Autowired
	private JcaConstantService jcaConstantService;

	private static final Logger logger = LoggerFactory.getLogger(InvestorServiceImpl.class);

	private static final String PREFIX_CODE = "INVES.";
//	private static final String BUSINESS_CODE = CommonConstant.BUSINESS_INVESTOR;

	@Override
	public void initSrceenSearch(ModelAndView mav, String businessCode, Locale locale) {
		// Status List process
//		List<JProcessStepDto> statusList = jprocessService.findStepStatusList(BUSINESS_CODE, locale);
//		mav.addObject("statusList", statusList);

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
		
//		List<ConstantDisplay> viewTypeSelection = constDisplayService.findByType(ConstDispType.NDT);
		List<JcaConstantDto> viewTypeSelection = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.NDT.toString(), "EN");

		List<InvestorCategoryNode> selectionCategoryTree = findSelectionCategoryTree(null, locale.toString());

		InvestorCategoryNode emptyNode = new InvestorCategoryNode();
		emptyNode.setText("Tất cả");
		emptyNode.setId(-1l);
		emptyNode.setChecked("true");
		emptyNode.setState("open");
		selectionCategoryTree.add(0, emptyNode);

		String selectionCategoryTreeJson = CommonJsonUtil.convertObjectToJsonString(selectionCategoryTree);
		mav.addObject("selectionCategories", selectionCategoryTreeJson);

		mav.addObject("kindList", viewTypeSelection);
	}

	@Override
	public PageWrapper<InvestorLanguageDto> doSearch(int page, InvestorSearchDto searchDto, Locale locale) {
		
		if (null == searchDto)
			searchDto = new InvestorSearchDto();
		int sizeOfPage = searchDto.getPageSize() != null ? searchDto.getPageSize()
				: systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);

		// set status name
		//searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));

		searchDto.setLanguage(locale.toString());

		int count = investorRepository.countBySearchDto(searchDto);

//		if ((count % sizeOfPage == 0 && page > count / sizeOfPage)
//				|| (count % sizeOfPage > 0 && page - 1 > count / sizeOfPage)) {
//			page = 1;
//		}

		PageWrapper<InvestorLanguageDto> pageWrapper = new PageWrapper<InvestorLanguageDto>(page, sizeOfPage);
		List<InvestorLanguageDto> result = new ArrayList<>();

		if (count > 0) {
			int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);

			result = investorRepository.findBySearchDto(offsetSQL, sizeOfPage, searchDto);

			for (InvestorLanguageDto data : result) {
				getDataParentCategory(data.getCategoryId(), data, locale);
			}
		}

		pageWrapper.setDataAndCount(result, count);

		return pageWrapper;
	}

	private void getDataParentCategory(Long categoryId, InvestorLanguageDto data, Locale locale) {
		List<Long> parentsId = new ArrayList<>();
		parentsId.add(categoryId);

		InvestorCategoryDto investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(categoryId,
				StepStatusEnum.PUBLISHED.getStepNo(), locale.toString());

		if (investorCategoryDto != null && investorCategoryDto.getLevelId() != null) {
			switch (investorCategoryDto.getLevelId()) {
			case 1:
				// level 1
				data.setInvestorCategoryIdLevel1(investorCategoryDto.getId());
				data.setInvestorCategoryNameLevel1(investorCategoryDto.getTitle());
				break;
			case 2:
				// level 2
				data.setInvestorCategoryIdLevel2(investorCategoryDto.getId());
				data.setInvestorCategoryNameLevel2(investorCategoryDto.getTitle());

				getDataParentCategory(investorCategoryDto.getParentId(), data, locale);
				break;
			case 3:
				// level 3
				data.setInvestorCategoryIdLevel3(investorCategoryDto.getId());
				data.setInvestorCategoryNameLevel3(investorCategoryDto.getTitle());

				// level 2
				getDataParentCategory(investorCategoryDto.getParentId(), data, locale);

				// level 1
				investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(
						investorCategoryDto.getParentId(), StepStatusEnum.PUBLISHED.getStepNo(), locale.toString());
				getDataParentCategory(investorCategoryDto.getParentId(), data, locale);

				break;
			case 4:
				// level 4
				data.setInvestorCategoryIdLevel4(investorCategoryDto.getId());
				data.setInvestorCategoryNameLevel4(investorCategoryDto.getTitle());

				// level 3
				getDataParentCategory(investorCategoryDto.getParentId(), data, locale);

				// level 2
				investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(
						investorCategoryDto.getParentId(), StepStatusEnum.PUBLISHED.getStepNo(), locale.toString());
				getDataParentCategory(investorCategoryDto.getParentId(), data, locale);

				// level 1
				investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(
						investorCategoryDto.getParentId(), StepStatusEnum.PUBLISHED.getStepNo(), locale.toString());
				getDataParentCategory(investorCategoryDto.getParentId(), data, locale);

				break;
			}
		}

	}

	@Override
	public void initSrceenEdit(ModelAndView mav, InvestorEditDto editDto, Locale locale) {

		String url = "investor/edit";

		if (null != editDto.getId()) {
			url = url.concat("?id=").concat(editDto.getId().toString());
		}

		editDto.setUrl(url);

		// user login
		if (editDto.getId() == null) {
//			UserProfile userProfile = UserProfileUtils.getUserProfile();
			editDto.setCreateBy(UserProfileUtils.getUserNameLogin());
		}

		String requestToken = CommonUtil.randomStringWithTimeStamp();
		editDto.setRequestToken(requestToken);

		editDto.setIndexLangActive(0);

		List<LanguageDto> languageList = languageService.getLanguageDtoList();
		mav.addObject("languageList", languageList);

		if (editDto.getInvestorLanguageList() == null) {
			List<InvestorLanguageEditDto> investorLanguageList = new ArrayList<>();
			// loop language
			for (LanguageDto language : languageList) {
				InvestorLanguageEditDto investorlang = new InvestorLanguageEditDto();
				investorlang.setLanguageCode(language.getCode());
				investorLanguageList.add(investorlang);
			}

			editDto.setInvestorLanguageList(investorLanguageList);
		}

		// Init PageWrapper History Approval
//		PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.doSearch(1, editDto.getId(),
//				editDto.getProcessId(), ConstantHistoryApprove.APPROVE_INVESTOR, locale);
//		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
	}

	@Override
	public InvestorEditDto getInvestorEditDto(Long id, Locale locale) {
		InvestorEditDto resultDto = new InvestorEditDto();

		List<Language> languageList = languageService.findAllActive();

		if (id == null) {
			resultDto.setStatus(StepStatusEnum.DRAFT.getStepNo());
			resultDto.setEnabled(Boolean.TRUE);
			resultDto.setReferenceType(ConstantHistoryApprove.APPROVE_INVESTOR);
//			Long processId = resultDto.getProcessId();
//			if (processId == null) {
//				// First step
//				JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(CommonConstant.BUSINESS_INVESTOR,
//						locale.toString());
//				processId = processDto.getProcessId();
//				processId = processDto.getProcessId();
//			}

			List<InvestorLanguageEditDto> investorLanguageList = new ArrayList<>();

			// loop language
			for (Language language : languageList) {
				InvestorLanguageEditDto investorlang = new InvestorLanguageEditDto();
				investorlang.setLanguageCode(language.getCode());
				investorLanguageList.add(investorlang);
			}

			resultDto.setInvestorLanguageList(investorLanguageList);

//			// List button of step
//			List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId,
//					resultDto.getStatus(), locale.toString());
//			resultDto.setStepBtnList(stepButtonList);
//
//			String statusName = jprocessService.getStatusName(resultDto.getProcessId(), resultDto.getStatus(), locale);
//			resultDto.setStatusName(statusName);

			return resultDto;
		}

		Investor investor = investorRepository.findOne(id);

		// dữ liệu ko tồn tại hoặc đã bị xóa
		if (investor == null || investor.getDeleteDate() != null) {
			throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
		}

		if (null != investor) {
			resultDto.setId(investor.getId());

			// set category id
			resultDto.setCategoryId(investor.getCategoryId());
			if (investor.getCategoryId() != null) {
				InvestorCategory category = investorCategoryRepository.findOne(investor.getCategoryId());
				if (category != null && !category.getStatus().equals(CmsStepNoStatusConstant.STEP_APPROVED)) {
					resultDto.setCategoryId(null);
				}
			}

			resultDto.setCode(investor.getCode());
			resultDto.setImageUrl(investor.getImageName());
			resultDto.setPhysicalImg(investor.getPhysicalImg());
			resultDto.setStatus(investor.getStatus());
			resultDto.setSort(investor.getSort());
			resultDto.setReferenceType(ConstantHistoryApprove.APPROVE_INVESTOR);
			resultDto.setReferenceId(investor.getId());
			resultDto.setCreateBy(investor.getCreateBy());
			resultDto.setProcessId(investor.getProcessId());
			resultDto.setUpdateDate(investor.getUpdateDate());
			resultDto.setEnabled(investor.isEnabled());
			resultDto.setHomepage(investor.getHomepage());
			resultDto.setHotNews(investor.getHotNews());
			resultDto.setPostedDate(investor.getPostedDate());

//			Long processId = resultDto.getProcessId();
//			if (processId == null) {
//				// First step
//				JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(CommonConstant.BUSINESS_INVESTOR,
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

		// set ProductLanguage
		List<InvestorLanguageEditDto> investorLanguageList = getInvestorLanguageList(id, languageList);
		resultDto.setInvestorLanguageList(investorLanguageList);

		return resultDto;
	}

	private List<InvestorLanguageEditDto> getInvestorLanguageList(Long investorId, List<Language> languageList) {
		List<InvestorLanguageEditDto> resultList = new ArrayList<InvestorLanguageEditDto>();

		List<InvestorLanguage> investorLanguageList = investorLanguageRepository.findByInvestorId(investorId);

		// loop language
		for (Language language : languageList) {
			// loop productLanguageList
			for (InvestorLanguage entity : investorLanguageList) {
				// productLanguageId is languageId
				if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
					InvestorLanguageEditDto investorLanguageDto = new InvestorLanguageEditDto();
					investorLanguageDto.setId(entity.getId());
					investorLanguageDto.setLanguageCode(entity.getLanguageCode());
					investorLanguageDto.setTitle(entity.getTitle());
					investorLanguageDto.setShortContent(entity.getShortContent());
					investorLanguageDto.setLinkAlias(entity.getLinkAlias());
					investorLanguageDto.setKeyword(entity.getKeyword());
					investorLanguageDto.setDescriptionKeyword(entity.getDescriptionKeyword());
					investorLanguageDto.setContent(entity.getContent());
					resultList.add(investorLanguageDto);
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
	public void doEdit(InvestorEditDto editDto, Locale locale, HttpServletRequest request) throws IOException {

		createOrEdit(editDto, locale, request);

		createOrEditLanguage(editDto, locale);

		// if action process
		if (!StringUtils.equals(editDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
			// updateHistoryApprove
			updateHistoryApprove(editDto, locale);

			sendMail(editDto, request);
		}
	}

	private void createOrEdit(InvestorEditDto editDto, Locale locale, HttpServletRequest request) {
		// user login
//		UserProfile userProfile = UserProfileUtils.getUserProfile();

		Investor entity = new Investor();

		if (null != editDto.getId()) {

			entity = investorRepository.findOne(editDto.getId());
			// dữ liệu ko tồn tại hoặc đã bị xóa
			if (null == entity || entity.getDeleteDate() != null) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
			}

			if (entity.getUpdateDate() != null && !entity.getUpdateDate().equals(editDto.getUpdateDate())) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
			}

			entity.setUpdateDate(new Date());
			entity.setUpdateBy(UserProfileUtils.getUserNameLogin());

		} else {
			entity.setCreateDate(new Date());
			entity.setCreateBy(UserProfileUtils.getUserNameLogin());
			entity.setCode(CommonUtil.getNextCode(PREFIX_CODE, commonService.getMaxCode("M_INVESTOR", PREFIX_CODE)));
			// set sort is first
			entity.setSort(1l);
		}

		try {
			entity.setStatus(editDto.getStatus());

			if (editDto.getCategoryId() != null) {
				InvestorCategoryDto investorCategory = investorCategoryRepository.findInvestorCategoryDtoById(
						editDto.getCategoryId(), StepStatusEnum.PUBLISHED.getStepNo(), locale.toString());

				if (investorCategory != null) {
					editDto.setKind(investorCategory.getCategoryType());
				}
			}

			if (editDto.getKind() != null) {
				entity.setKind(Long.valueOf(editDto.getKind()));
			}

			// physical file template
			String physicalImgTmpName = editDto.getPhysicalImg();
			// upload images
			if (StringUtils.isNotEmpty(physicalImgTmpName)) {
				String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalImgTmpName, AdminConstant.INVESTOR_FOLDER);
				entity.setPhysicalImg(newPhiscalName);
				entity.setImageName(editDto.getImageUrl());
			} else {
				entity.setPhysicalImg(null);
				entity.setImageName(null);
			}

			entity.setCustomerTypeId(12L);

			entity.setCategoryId(editDto.getCategoryId());
			entity.setEnabled(editDto.getEnabled());

			entity.setHotNews(editDto.getHotNews());
			entity.setHomepage(editDto.getHomepage());

			entity.setPostedDate(editDto.getPostedDate());

			// if action process
//			if (!StringUtils.equals(editDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
//				if (editDto.getProcessId() == null) {
//					// First step
//					JProcessStepDto processDto = jprocessService
//							.findFirstStepOfProcess(CommonConstant.BUSINESS_INVESTOR, locale.toString());
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
//			}

			entity.setProcessId(editDto.getProcessId());
			entity.setStatus(editDto.getStatus());

			investorRepository.save(entity);

			editDto.setCode(entity.getCode());
			if (editDto.getId() == null) {
				editDto.setId(entity.getId());
				updateBeforeIdAfter(editDto, locale);
			}
			
		} catch (Exception e) {
			logger.error("createOrEdit: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	private void updateBeforeIdAfter(InvestorEditDto editDto, Locale locale) {
		InvestorSearchDto searchDto = new InvestorSearchDto();
		searchDto.setCategoryId(editDto.getCategoryId());
		searchDto.setId(editDto.getId());

		List<InvestorEditDto> lstProductType = investorRepository.findAllInvestorForSort(searchDto, locale.toString());
		if (CollectionUtils.isEmpty(lstProductType)) {
			return;
		}

		InvestorEditDto item = new InvestorEditDto();

		for (int i = 0; i < lstProductType.size(); i++) {
			item = lstProductType.get(i);
			Investor entity = investorRepository.findOne(item.getId());
			entity.setSort((long) (i + 2));
			investorRepository.save(entity);
		}
	}

	private void createOrEditLanguage(InvestorEditDto editDto, Locale locale) {
		try {
//			UserProfile userProfile = UserProfileUtils.getUserProfile();
			for (InvestorLanguageEditDto cLanguageDto : editDto.getInvestorLanguageList()) {
				InvestorLanguage entity = new InvestorLanguage();

				if (null != cLanguageDto.getId()) {
					entity = investorLanguageRepository.findOne(cLanguageDto.getId());
					if (null == entity) {
						throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
					}
					entity.setUpdateDate(new Date());
					entity.setUpdateBy(UserProfileUtils.getUserNameLogin());
				} else {
					entity.setCreateDate(new Date());
					entity.setCreateBy(UserProfileUtils.getUserNameLogin());
				}

				entity.setInvestorId(editDto.getId());
				entity.setLanguageCode(cLanguageDto.getLanguageCode());
				entity.setTitle(cLanguageDto.getTitle());
				entity.setShortContent(cLanguageDto.getShortContent());
				entity.setContent(cLanguageDto.getContent());
				entity.setLinkAlias(cLanguageDto.getLinkAlias());
				entity.setKeyword(cLanguageDto.getKeyword());
				entity.setDescriptionKeyword(cLanguageDto.getDescriptionKeyword());

				investorLanguageRepository.save(entity);

				CmsUtils.moveTempSubFolderToUpload(
						Paths.get(AdminConstant.INVESTOR_EDITOR_FOLDER, editDto.getRequestToken()).toString());
			}
		} catch (Exception e) {
			logger.error("createOrEditLanguage: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	private void updateHistoryApprove(InvestorEditDto editDto, Locale locale) {
		try {
			// insert comment
			HistoryApproveDto historyApproveDto = new HistoryApproveDto();
			historyApproveDto.setApprover(UserProfileUtils.getFullName());
			historyApproveDto.setComment(editDto.getComment());
			historyApproveDto.setProcessId(editDto.getProcessId());
			historyApproveDto.setProcessStep(editDto.getStatus().longValue());
			historyApproveDto.setReferenceId(editDto.getId());
			historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_INVESTOR);
			historyApproveDto.setActionId(editDto.getButtonId().toString());
			historyApproveDto.setOldStep(editDto.getOldStatus());
			historyApproveDto.setAccountId(UserProfileUtils.getAccountId());
//			historyApproveService.addHistoryApprove(historyApproveDto);
		} catch (Exception e) {
			logger.error("updateHistoryApprove: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	private void sendMail(InvestorEditDto editDto, HttpServletRequest request) {
		// locale default
		String defaultlocale = systemConfig.getConfig(SystemConfig.LANGUAGE_DEFAULT);
		Locale locale = new Locale(defaultlocale);

		EmailCommonDto emailCommon = new EmailCommonDto();
		emailCommon.setActionName(msg.getMessage("email.template.investor", null, locale));
		emailCommon.setButtonAction(editDto.getButtonAction());
		emailCommon.setButtonId(editDto.getButtonId().toString());
		emailCommon.setComment(editDto.getComment());

		// Nội dung
		LinkedHashMap<String, String> content = new LinkedHashMap<>();

		content.put("Mã", editDto.getCode());
		content.put("Danh mục nhà đầu tư", editDto.getInvestorLanguageList().get(0).getTitle());
		content.put("Danh sách nhà đầu tư", editDto.getInvestorLanguageList().get(0).getTitle());
		emailCommon.setContent(content);

		emailCommon.setCurrItem(editDto.getCurrItem());

		emailCommon.setId(editDto.getId());
		emailCommon.setOldStatus(editDto.getOldStatus());
		emailCommon.setProcessId(editDto.getProcessId());
		emailCommon.setReferenceType(editDto.getReferenceType());
		emailCommon.setStatus(editDto.getStatus());

		// Subject của email
		emailCommon.setSubject(msg.getMessage("subject.email.template.investor", null, locale));

		emailCommon.setUrl(CmsUtils.getBaseUrl(request) + "/investor/edit?id=" + editDto.getId());

//		emailUtil.sendMail(emailCommon, request, locale);
	}

	@Override
	public void deleteInvestor(Investor investor, Locale locale) {
		try {
			String userName = UserProfileUtils.getUserNameLogin();
			// delete investor
			investor.setDeleteDate(new Date());
			investor.setDeleteBy(userName);
			investorRepository.save(investor);

			// delete investor language

			List<InvestorLanguage> lstInvestorLanguage = investorLanguageRepository.findByInvestorId(investor.getId());

			for (InvestorLanguage investorLang : lstInvestorLanguage) {
				investorLang.setDeleteBy(userName);
				investorLang.setDeleteDate(new Date());
				investorLanguageRepository.save(investorLang);
			}

		} catch (Exception e) {
			logger.error("deleteInvestor:" + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	@Override
	public Investor getInvestorByCode(String code) {
		return investorRepository.findInvestorByCode(code);
	}

	@Override
	public Investor getInvestorById(Long id) {
		return investorRepository.findOne(id);
	}

	@Override
	public void exportExcel(InvestorSearchDto searchDto, HttpServletResponse res, Locale locale) {
		try {
			// set status name
			searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));

			/* change template */
			String templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_INVESTOR;
			String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
					+ CmsCommonConstant.TYPE_EXCEL;
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);

			List<ExportInvestorReportDto> lstData = investorRepository.exportExcelWithCondition(searchDto);

			for (ExportInvestorReportDto data : lstData) {
				getDataParentCategoryForExport(data.getCategoryId(), data, locale);
			}

			List<ItemColsExcelDto> cols = new ArrayList<>();
			// start fill data to workbook
			ImportExcelUtil.setListColumnExcel(ExportInvestorExportEnum.class, cols);
			ExportExcelUtil<ExportInvestorReportDto> exportExcel = new ExportExcelUtil<>();
			// do export
			exportExcel.exportExcelWithXSSFNonPass(template, locale, lstData, ExportInvestorReportDto.class, cols,
					datePattern, res, templateName);
		} catch (Exception e) {
			logger.error("exportExcel:" + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	private void getDataParentCategoryForExport(Long categoryId, ExportInvestorReportDto data, Locale locale) {
		List<Long> parentsId = new ArrayList<>();
		parentsId.add(categoryId);

		InvestorCategoryDto investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(categoryId,
				StepStatusEnum.PUBLISHED.getStepNo(), locale.toString());

		switch (investorCategoryDto.getLevelId()) {
		case 1:
			// level 1
			data.setInvestorCategoryNameLevel1(investorCategoryDto.getTitle());
			break;
		case 2:
			// level 2
			data.setInvestorCategoryNameLevel2(investorCategoryDto.getTitle());

			getDataParentCategoryForExport(investorCategoryDto.getParentId(), data, locale);
			break;
		case 3:
			// level 3
			data.setInvestorCategoryNameLevel3(investorCategoryDto.getTitle());

			// level 2
			getDataParentCategoryForExport(investorCategoryDto.getParentId(), data, locale);

			// level 1
			investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(
					investorCategoryDto.getParentId(), StepStatusEnum.PUBLISHED.getStepNo(), locale.toString());
			getDataParentCategoryForExport(investorCategoryDto.getParentId(), data, locale);

			break;
		case 4:
			// level 4
			data.setInvestorCategoryNameLevel4(investorCategoryDto.getTitle());

			// level 3
			getDataParentCategoryForExport(investorCategoryDto.getParentId(), data, locale);

			// level 2
			investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(
					investorCategoryDto.getParentId(), StepStatusEnum.PUBLISHED.getStepNo(), locale.toString());
			getDataParentCategoryForExport(investorCategoryDto.getParentId(), data, locale);

			// level 1
			investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(
					investorCategoryDto.getParentId(), StepStatusEnum.PUBLISHED.getStepNo(), locale.toString());
			getDataParentCategoryForExport(investorCategoryDto.getParentId(), data, locale);

			break;
		}

	}

	@Override
	public void initSortPage(Long categoryId, ModelAndView mav, Locale locale) {
		InvestorSearchDto searchDto = new InvestorSearchDto();
		searchDto.setCategoryId(categoryId);

		List<InvestorEditDto> investorList = new ArrayList<>();
		if (null != categoryId) {
			investorList = investorRepository.findAllInvestorForSort(searchDto, locale.toString());
		}

		SortPageDto sortPageModel = createSortOrderDtoList(investorList);
		mav.addObject("sortPageModel", sortPageModel);
		mav.addObject("sortList", investorList);
	}

	private SortPageDto createSortOrderDtoList(List<InvestorEditDto> investorList) {
		SortPageDto sortPageModel = new SortPageDto();
		List<SortOrderDto> sortList = new ArrayList<SortOrderDto>();
		Long sortIndex = 0L;
		for (InvestorEditDto shareholderDto : investorList) {
			SortOrderDto sortItem = new SortOrderDto();
			sortItem.setObjectId(shareholderDto.getId());
			sortItem.setSortValue(sortIndex);
			sortList.add(sortItem);
		}
		sortPageModel.setSortList(sortList);
		return sortPageModel;
	}

	@Override
	public void updateModelsSort(SortPageDto sortPageModel) {
		if (sortPageModel.getSortList() != null) {
			for (SortOrderDto sortItem : sortPageModel.getSortList()) {
				investorRepository.updateSortAll(sortItem);
			}
		}
	}

	@Override
	public List<InvestorCategoryLanguageDto> getAllInvestorCategoryLanguageDto(Locale locale) {
		return null;
	}

	@Override
	public List<InvestorCategoryNode> findSelectionCategoryTree(Long exceptCategoryId, String lang) {

		List<InvestorCategoryDto> categories = investorCategoryRepository.findAllRootActive(lang,
				StepStatusEnum.PUBLISHED.getStepNo());

		List<InvestorCategoryDto> listCategoryDto = loadFullCategoryTreeByRoots(categories, true, exceptCategoryId,
				lang);

		List<InvestorCategoryNode> categoryTree = new ArrayList<InvestorCategoryNode>();
		List<InvestorCategoryDto> listRoot = new ArrayList<InvestorCategoryDto>();

		if (null != listCategoryDto && !listCategoryDto.isEmpty()) {
			if (null != listCategoryDto) {
				for (InvestorCategoryDto category : listCategoryDto) {
					if (null == category.getParentId() || category.getParentId().equals(0L))
						listRoot.add(category);
				}
				List<InvestorCategoryNode> parentNodeList = new ArrayList<InvestorCategoryNode>();
				for (InvestorCategoryDto categoryDto : listRoot) {
					InvestorCategoryNode categoryNode = new InvestorCategoryNode();
					categoryNode.setId(categoryDto.getId());
					categoryNode.setText(categoryDto.getTitle());
					categoryNode.setState(ConstantCore.OPEN);
					categoryTree.add(categoryNode);
					parentNodeList.add(categoryNode);
				}
				do {
					parentNodeList = loadAllSubNodes(parentNodeList, listCategoryDto, null);
				} while (parentNodeList != null && parentNodeList.size() > 0);

			}
		}

		return categoryTree;
	}

	private List<InvestorCategoryDto> loadFullCategoryTreeByRoots(List<InvestorCategoryDto> categories,
			boolean forSelection, Long exceptCategoryId, String lang) {
		if (exceptCategoryId != null) {
			categories = removeExceptCategory(categories, exceptCategoryId);
		}
		categories = loadDescendandCategories(categories, forSelection, exceptCategoryId, lang);
		List<InvestorCategoryDto> categoryDtos = new ArrayList<InvestorCategoryDto>();
		for (InvestorCategoryDto category : categories) {
			categoryDtos.add(category);
		}
		return categoryDtos;
	}

	private List<InvestorCategoryDto> removeExceptCategory(List<InvestorCategoryDto> categories,
			Long exceptCategoryId) {
		for (int index = 0; index < categories.size(); ++index) {
			InvestorCategoryDto iCategory = categories.get(index);
			if (iCategory.getId().equals(exceptCategoryId)) {
				categories.remove(index);
				break;
			}
		}
		return categories;
	}

	private List<InvestorCategoryDto> loadDescendandCategories(List<InvestorCategoryDto> categories,
			boolean forSelection, Long exceptCategoryId, String lang) {
		List<Long> selectionParentIds = createCategoryIdList(categories);
		if (selectionParentIds.size() > 0) {
			List<InvestorCategoryDto> childrens;
			do {
				if (forSelection) {
					childrens = investorCategoryRepository.findAllActiveChildrenCategoryForSelection(selectionParentIds,
							lang, CmsStepNoStatusConstant.STEP_APPROVED);
				} else {
					childrens = investorCategoryRepository.findAllActiveChildrenCategory(selectionParentIds, lang,
							CmsStepNoStatusConstant.STEP_APPROVED);
				}
				if (exceptCategoryId != null) {
					childrens = removeExceptCategory(childrens, exceptCategoryId);
				}
				selectionParentIds = this.createCategoryIdList(childrens);
				categories.addAll(childrens);
			} while (childrens != null && childrens.size() > 0);
		}
		return categories;
	}

	private List<Long> createCategoryIdList(List<InvestorCategoryDto> categories) {
		List<Long> idList = new ArrayList<Long>();
		for (InvestorCategoryDto category : categories) {
			idList.add(category.getId());
		}
		return idList;
	}

	public List<InvestorCategoryNode> loadAllSubNodes(List<InvestorCategoryNode> parentNodeList,
			List<InvestorCategoryDto> listAllCategory, Long categoryId) {
		List<InvestorCategoryNode> retVal = new ArrayList<InvestorCategoryNode>();
		for (InvestorCategoryNode parentNodeItem : parentNodeList) {
			List<InvestorCategoryNode> listSub = getListNodeSubCategory(parentNodeItem.getId(), listAllCategory);
			parentNodeItem.setChildren(listSub);
			if (listSub != null && listSub.size() > 0) {
				retVal.addAll(listSub);
			}
		}
		return retVal;
	}

	private List<InvestorCategoryNode> getListNodeSubCategory(Long menuId, List<InvestorCategoryDto> listAllCategory) {
		List<InvestorCategoryNode> listSubCategory = new ArrayList<InvestorCategoryNode>();
		for (InvestorCategoryDto menu : listAllCategory) {
			if (menu.getParentId() != null && menu.getParentId().equals(menuId)) {
				InvestorCategoryNode categoryNode = new InvestorCategoryNode();
				categoryNode.setId(menu.getId());
				categoryNode.setText(menu.getTitle());
				categoryNode.setState(ConstantCore.OPEN);
				listSubCategory.add(categoryNode);
			}
		}
		return listSubCategory;
	}

	@Override
	public List<InvestorCategoryDto> getAllIntroductionCategoryActive(String languageCode, Integer status) {
		List<InvestorCategoryDto> introductionCategoryDtoList = investorCategoryRepository
				.findAllRootActive(languageCode, status);
		return introductionCategoryDtoList;
	}

	@Override
	public InvestorCategoryDto getInvestorCategoryDtoById(Long categoryId, Integer status, Locale locale) {
		return investorCategoryRepository.findInvestorCategoryDtoById(categoryId, status, locale.toString());
	}

	@Override
	public List<InvestorCategoryDto> findAllActiveChildrenCategory(Long parentId, Locale locale) {
		List<Long> parentIds = new ArrayList<Long>();
		parentIds.add(parentId);
		return investorCategoryRepository.findAllActiveChildrenCategory(parentIds, locale.toString(),
				CmsStepNoStatusConstant.STEP_APPROVED);
	}

	@Override
	public InvestorCategoryDto checkTypeCategory(Long categoryId, Locale locale) {
		InvestorCategoryDto data = new InvestorCategoryDto();

		InvestorCategoryDto investorCategory = getInvestorCategoryDtoById(categoryId,
				StepStatusEnum.PUBLISHED.getStepNo(), locale);

		List<InvestorCategoryDto> lstInvestorCategory = new ArrayList<>();

		switch (investorCategory.getLevelId()) {
		case 1:
			// Level 1
			lstInvestorCategory = findAllActiveChildrenCategory(investorCategory.getId(), locale);

			if (lstInvestorCategory != null && !lstInvestorCategory.isEmpty()) {
				data = lstInvestorCategory.get(0);
			} else {
				setMessageForInvestorCategory(investorCategory, locale);
				return investorCategory;
			}

			return checkTypeCategory(data.getId(), locale);
		case 2:
			// Tim level 2
			lstInvestorCategory = findAllActiveChildrenCategory(investorCategory.getId(), locale);

			if (lstInvestorCategory != null && !lstInvestorCategory.isEmpty()) {
				data = lstInvestorCategory.get(0);
			} else {
				setMessageForInvestorCategory(investorCategory, locale);
				return investorCategory;
			}

			return checkTypeCategory(data.getId(), locale);
		case 3:
			// Tim level 3
			lstInvestorCategory = findAllActiveChildrenCategory(investorCategory.getId(), locale);

			if (lstInvestorCategory != null && !lstInvestorCategory.isEmpty()) {
				data = lstInvestorCategory.get(0);
			} else {
				setMessageForInvestorCategory(investorCategory, locale);
				return investorCategory;
			}

			return checkTypeCategory(data.getId(), locale);
		case 4:
			// Tim level 4
			setMessageForInvestorCategory(investorCategory, locale);
			return investorCategory;
		}

		return data;
	}

	private void setMessageForInvestorCategory(InvestorCategoryDto investorCategory, Locale locale) {
		Integer cat = investorCategory.getCategoryType();

		if (cat != null) {
			

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
			
//			ConstantDisplay constDisplay = constDisplayService.findByTypeAndCat(ConstDispType.NDT.toString(),
//					cat.toString());

			JcaConstantDto constDisplay = jcaConstantService
					.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.NDT.toString(), cat.toString(), "EN").get(0);

			String messageNotify = msg.getMessage("investor.message.notify", null, locale) + " "
					+ constDisplay.getName();
			investorCategory.setMessageNotify(messageNotify);
		}
	}

	@Override
	public InvestorLanguageEditDto getByAliasAndCategoryId(Long categoryId, String linkAlias, String language) {
		return investorRepository.findByAliasAndCategoryId(categoryId, linkAlias, language);
	}

    @Override
    public InvestorEditDto getEdit(Long id, String customerAlias, Locale locale) {
        return getInvestorEditDto(id, locale);
    }
}
