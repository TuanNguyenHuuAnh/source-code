package vn.com.unit.cms.admin.all.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import jp.sf.amateras.mirage.SqlManager;
import jp.sf.amateras.mirage.StringSqlResource;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
import vn.com.unit.cms.admin.all.core.InvestorCategoryNode;
import vn.com.unit.cms.admin.all.dto.EmailCommonDto;
import vn.com.unit.cms.admin.all.dto.InvestorCategoryDto;
import vn.com.unit.cms.admin.all.dto.InvestorCategoryLanguageDto;
import vn.com.unit.cms.admin.all.dto.InvestorCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.SortPageDto;
import vn.com.unit.cms.admin.all.entity.InvestorCategory;
import vn.com.unit.cms.admin.all.entity.InvestorCategoryLanguage;
import vn.com.unit.cms.admin.all.enumdef.ExportInvestorCategoryExportEnum;
import vn.com.unit.cms.admin.all.enumdef.StepActionEnum;
import vn.com.unit.cms.admin.all.repository.InvestorCategoryLanguageRepository;
import vn.com.unit.cms.admin.all.repository.InvestorCategoryRepository;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.InvestorCategorLanguageService;
import vn.com.unit.cms.admin.all.service.InvestorCategoryService;
//import vn.com.unit.cms.admin.all.util.EmailUtil;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstDispType;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.constant.CmsStepNoStatusConstant;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
//import vn.com.unit.ep2p.dto.JProcessStepDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.core.dto.JcaConstantDto;
//import vn.com.unit.jcanary.entity.ConstantDisplay;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.repository.LanguageRepository;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.cms.core.utils.CmsUtils;
//import vn.com.unit.jcanary.service.HistoryApproveService;
//import vn.com.unit.jcanary.service.JProcessService;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.cms.admin.all.jcanary.utils.APIUtils;
//import vn.com.unit.jcanary.utils.ExportExcelUtil;
//import vn.com.unit.jcanary.utils.ImportExcelUtil;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.ep2p.core.utils.Utility;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class InvestorCategoryServiceImpl extends DocumentWorkflowCommonServiceImpl<InvestorCategoryDto, InvestorCategoryDto> implements InvestorCategoryService {

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private InvestorCategoryRepository investorCategoryRepository;

//	@Autowired
//	private JProcessService jprocessService;

	@Autowired
	private MessageSource msg;

	@Autowired
	private InvestorCategorLanguageService investorCategorLanguageService;

	@Autowired
	private InvestorCategoryLanguageRepository investorcategoryLanguageCato;

	@Autowired
	@Qualifier("languageRepository")
	LanguageRepository langRepo;

	@Autowired
	private CmsFileService fileService;

//	@Autowired
//	ConstantDisplayService constDisplayService;

	@Autowired
	LanguageService langService;

//	@Autowired
//	HistoryApproveService historyApproveService;
//
//	@Autowired
//	EmailUtil emailUtil;
	
//	@Autowired
//	private CmsCommonService commonService;
	
	@Autowired
	@Qualifier("sqlManagerServicePr")
	private SqlManagerServiceImpl sqlManager;
	
	@Autowired
	ServletContext servletContext;
	
    @Autowired
	private JcaConstantService jcaConstantService;

	private static final Logger logger = LoggerFactory.getLogger(InvestorCategoryServiceImpl.class);

//	private static final String PREFIX_CODE = "INVES.C.";

	public final static Long INVESTOR_TYPE_ID = 12L;
	
	public static final Integer LEVEL_ID_1 = 1;
    public static final Integer LEVEL_ID_2 = 2;
    public static final Integer LEVEL_ID_3 = 3;
    public static final Integer LEVEL_ID_4 = 4;
	
	@Override
	public PageWrapper<InvestorCategoryDto> doSearch(int page, InvestorCategorySearchDto searchDto, Locale locale) {
		if (null == searchDto)
			searchDto = new InvestorCategorySearchDto();
		// set status name
		//searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));
		
		int sizeOfPage = searchDto.getPageSize() != null ? searchDto.getPageSize()
				: systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);

		int count = investorCategoryRepository.countInvestorCategory(searchDto);
		
		//int sizeOfPage = count;

//		if ((count % sizeOfPage == 0 && page > count / sizeOfPage)
//				|| (count % sizeOfPage > 0 && page - 1 > count / sizeOfPage)) {
//			page = 1;
//		}
		PageWrapper<InvestorCategoryDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
		int countInvestorRoot = 0;
		int investorRootAdded = 0;
		List<InvestorCategoryDto> result = new LinkedList<InvestorCategoryDto>();
		int menuIndex = 0;
		List<InvestorCategoryDto> listMenuPage = new LinkedList<InvestorCategoryDto>();
		int currentPage = pageWrapper.getCurrentPage();
		if (count > 0) {
			int startIndex = (currentPage - 1) * sizeOfPage;
			result = investorCategoryRepository.getListInvestorCategory(startIndex, sizeOfPage, searchDto);
			if (StringUtils.isNotEmpty(searchDto.getCode()) || StringUtils.isNotEmpty(searchDto.getName()) 
					|| searchDto.getCategoryType() != null || searchDto.getStatus() != null) 
				result = getParentLine(result,locale);
			
			// resultRoot
			List<InvestorCategoryDto> resultRoot = getInvestorCateRoot(result);

			result = sortInvestorCategoryDtoByConstructorTree(result, resultRoot);

			countInvestorRoot = resultRoot.size();
			int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);

			boolean startCount = true;
			for (InvestorCategoryDto dto : result) {
				if (page > 1 && startCount) {
					menuIndex++;
				}

				if (dto.getParentId() != null && dto.getParentId().equals(0l)) {
					investorRootAdded++;
				}

				if (offsetSQL < investorRootAdded && investorRootAdded <= offsetSQL + sizeOfPage) {
					startCount = false;
					listMenuPage.add(dto);
				}
			}
		}
		pageWrapper.setDataAndCount(listMenuPage, countInvestorRoot);
		if (page > 1) {
			pageWrapper.setStartIndexCurrent(menuIndex);
		}
		return pageWrapper;
	}

	private List<InvestorCategoryDto> getParentLine(List<InvestorCategoryDto> listChild,Locale locale) {
		List<Long> investorIds = new ArrayList<>();
		List<InvestorCategoryDto> listParentLine = new ArrayList<>();
		for (InvestorCategoryDto child : listChild) {
			List<InvestorCategoryDto> menuIdLine= getParentLineId(investorIds,child.getId(),locale);
			listParentLine.addAll(menuIdLine);
		}
		return listParentLine;
	}

	private List<InvestorCategoryDto> getParentLineId(List<Long> investorIds,Long childId, Locale locale) {
		Long investorId = childId;
		InvestorCategoryDto investorCategoryDto;
		List<InvestorCategoryDto> str = new ArrayList<>();
		do {
			investorCategoryDto = getInvestorCategoryDtoByInvestorId(investorId,locale);
			if(investorCategoryDto.getId() != null && !investorIds.contains(investorCategoryDto.getId())) {
				investorIds.add(investorId);
				str.add(investorCategoryDto);
			}
			investorId = investorCategoryDto.getParentId();
		}while(investorCategoryDto.getParentId() != null && !investorCategoryDto.getParentId().equals(0L));
		return str;
	}

	private InvestorCategoryDto getInvestorCategoryDtoByInvestorId(Long investorId,Locale locale) {
		InvestorCategorySearchDto searchDto = new InvestorCategorySearchDto();
			searchDto.setId(investorId);
			searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));
		List<InvestorCategoryDto> listInvestor= investorCategoryRepository.getListInvestorCategory(0,1,searchDto);
		return listInvestor.isEmpty() ? new InvestorCategoryDto() : listInvestor.get(0);
	}

	@Override
	public void initScreenListSearch(ModelAndView mav, InvestorCategorySearchDto searchDto, Locale locale) {

		// init statusList
//		List<JProcessStepDto> statusList = jprocessService.findStepStatusList(CommonConstant.BUSINESS_INVESTOR, locale);
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
		
		// init Phan Loai
//		List<ConstantDisplay> enabledType = constDisplayService.findByType(ConstDispType.NDT);
		
		List<JcaConstantDto> enabledType = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.NDT.toString(), "EN");
		
		mav.addObject("enabledType", enabledType);
	}

	/**
	 * findById_Table_M_INVESTOR_CATEGORY
	 */
	@Override
	public InvestorCategory findById(Long id) {
		return investorCategoryRepository.findOne(id);
	}

	@Override
	public boolean deleteInvestorCategory(Long id) {
		InvestorCategory category = investorCategoryRepository.findOne(id);
		// userName
		String userName = UserProfileUtils.getUserNameLogin();

		// set-delete_date
		category.setDeleteDate(new Date());
		category.setDeleteBy(userName);
		InvestorCategory resultEntity = investorCategoryRepository.save(category);
		if (resultEntity != null) {
			// delete Investor-Category-language
			investorCategorLanguageService.deleteByInvestorCategoryId(new Date(), userName, category.getId());
		}

		return resultEntity != null;
	}

	@Override
	public InvestorCategoryDto getDataCategory(Long id, Locale locale, String businessCode) {
		InvestorCategoryDto resultDto = new InvestorCategoryDto();
		if (id == null) {
			resultDto.setEnabled(Boolean.TRUE);
			resultDto.setStatus(StepStatusEnum.DRAFT.getStepNo());
			resultDto.setCreateBy(UserProfileUtils.getUserNameLogin());
			resultDto.setCreateDate(new Date());
		} else {
			InvestorCategory entity = investorCategoryRepository.findOne(id);
			// dữ liệu ko tồn tại hoặc đã bị xóa
			if (entity == null || entity.getDeleteDate() != null) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
			}
			if (null != entity) {
				resultDto.setId(entity.getId());
				resultDto.setCode(entity.getCode());
				resultDto.setName(entity.getName());
				resultDto.setCategoryType(entity.getInvestorCategoryType());
				// locationLeft
				resultDto.setCategoryLocationLeft(entity.getInvestorCategoryLocationLeft());
				// locationRightTop
				resultDto.setCategoryLocationRightTop(entity.getInvestorCategoryLocationRightTop());
				// locationRightBottom
				resultDto.setCategoryLocationRightBottom(entity.getInvestorCategoryLocationRightBottom());
				
				resultDto.setParentId(entity.getInvestorCategoryParentId());
				resultDto.setImageNameLeft(entity.getImageNameLeft());
				resultDto.setImageUrlLeft(entity.getImageUrlLeft());
				resultDto.setImageNameRight(entity.getImageNameRight());
				resultDto.setImageUrlRight(entity.getImageUrlRight());
				resultDto.setImageName(entity.getImageName());
				resultDto.setImageUrl(entity.getImageUrl());
				resultDto.setNote(entity.getNote());
				resultDto.setCreateBy(entity.getCreateBy());
				resultDto.setCreateDate(entity.getCreateDate());
				resultDto.setUpdateDate(entity.getUpdateDate());
				resultDto.setUpdateBy(entity.getUpdateBy());
				resultDto.setApprovedDate(entity.getApproveDate());
				resultDto.setApproveBy(entity.getApproveBy());
				resultDto.setPublishBy(entity.getPublishBy());
				resultDto.setBeforeId(entity.getBeforeId());
				resultDto.setSort(entity.getSort());
				resultDto.setEnabled(entity.getEnable());
				resultDto.setStatus(entity.getStatus());
				resultDto.setProcessId(entity.getProcessId());
				resultDto.setReferenceType(ConstantHistoryApprove.APPROVE_NDT_INVESTOR_CATEGORY);
				resultDto.setReferenceId(entity.getId());
				resultDto.setCustomerTypeId(entity.getCustomerTypeId());
				resultDto.setBeforeId(entity.getBeforeId());
				resultDto.setNote(entity.getNote());
				if(entity.getInvestorCategoryType() != null) {
					resultDto.setCategoryKind(entity.getInvestorCategoryType().toString());
				}
				resultDto.setLevelId(entity.getInvestorCategoryLevelId());
				resultDto.setInvestorParentId(entity.getId());
			}
			List<InvestorCategoryLanguageDto> infoBylanguage = this.loadCategoryInfoByLanguage(entity.getId());
			resultDto.setInfoByLanguages(infoBylanguage);
		}
		Long processId = resultDto.getProcessId();
		if (processId == null) {
			// First step
//			JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(businessCode, locale.toString());
//			processId = processDto.getProcessId();

		}
		// List button of step
//		List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId,
//				resultDto.getStatus(), locale.toString());
//		resultDto.setStepBtnList(stepButtonList);

//		String statusName = jprocessService.getStatusName(resultDto.getProcessId(), resultDto.getStatus(), locale);
//		resultDto.setStatusName(statusName);

		return resultDto;
	}

	private List<InvestorCategoryLanguageDto> loadCategoryInfoByLanguage(Long categoryId) {
		List<InvestorCategoryLanguage> infoBylanguageEntities = investorcategoryLanguageCato
				.findByCategoryId(categoryId);

		HashMap<String, InvestorCategoryLanguage> introLangMap = new HashMap<String, InvestorCategoryLanguage>();

		for (InvestorCategoryLanguage infoByLangEntity : infoBylanguageEntities) {
			introLangMap.put(infoByLangEntity.getLanguageCode(), infoByLangEntity);
		}
		// languageList
		List<Language> languages = langRepo.findAllActive();

		// loop language
		List<InvestorCategoryLanguageDto> cateLangDtos = new ArrayList<InvestorCategoryLanguageDto>();

		for (Language language : languages) {
			InvestorCategoryLanguage cateLangEntity = introLangMap.get(language.getCode());
			if (cateLangEntity != null) {
				InvestorCategoryLanguageDto introLangDto = new InvestorCategoryLanguageDto();
				introLangDto.setId(cateLangEntity.getId());
				introLangDto.setCategoryId(cateLangEntity.getCategoryId());
				introLangDto.setLabel(cateLangEntity.getTitle());
				introLangDto.setLinkAlias(cateLangEntity.getLinkAlias());
				introLangDto.setKeyWord(cateLangEntity.getKeyWord());
				introLangDto.setDescriptionKeyword(cateLangEntity.getDescriptionKeyword());
				introLangDto.setShortContent(cateLangEntity.getShortContent());
				introLangDto.setLanguageCode(cateLangEntity.getLanguageCode());
				introLangDto.setLanguageDispName(language.getName());
				introLangDto.setDescription(cateLangEntity.getDescription());
				cateLangDtos.add(introLangDto);
			} else {
				InvestorCategoryLanguageDto cateLangDto = new InvestorCategoryLanguageDto();
				cateLangDto.setCategoryId(categoryId);
				cateLangDto.setLanguageCode(language.getCode());
				cateLangDto.setLanguageDispName(language.getName());
				cateLangDtos.add(cateLangDto);
			}
		}
		return cateLangDtos;
	}

	@Override
	public void setDataForSearchDto(InvestorCategorySearchDto searchDto, String codeSearch, String nameSearch,
			Integer statusSearch, Integer categoryTypeSearch) {

		if (codeSearch != null) {
			searchDto.setCode(codeSearch);
		}
		if (nameSearch != null) {
			searchDto.setName(nameSearch);
		}
		if (statusSearch != null) {
			searchDto.setStatus(statusSearch);
		}
		if (categoryTypeSearch != null) {
			searchDto.setCategoryType(categoryTypeSearch);
		}
	}

	@Override
	public InvestorCategoryDto checkTypeCategory(Long categoryId, Locale locale) {
		InvestorCategoryDto data = new InvestorCategoryDto();
		InvestorCategoryDto investorCategory = getInvestorCategoryDtoById(categoryId,CmsStepNoStatusConstant.STEP_APPROVED, locale);
			if(investorCategory != null) {
				return investorCategory;
			}
		return data;
	}
	
	@Override
	public List<InvestorCategoryNode> findSelectionCategoryTree(Long exceptCategoryId, String lang) {

		List<InvestorCategoryDto> categories = investorCategoryRepository.findAllRootActive(lang, CmsStepNoStatusConstant.STEP_APPROVED);

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

		// set root
		InvestorCategoryNode nodeRoot = new InvestorCategoryNode();
		nodeRoot.setId(0l);
		nodeRoot.setText("");
		nodeRoot.setState(ConstantCore.OPEN);
		nodeRoot.setChildren(categoryTree);

		List<InvestorCategoryNode> treeResult = new LinkedList<InvestorCategoryNode>();
		treeResult.add(nodeRoot);
		
		return treeResult;
//		return categoryTree;
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
							lang,CmsStepNoStatusConstant.STEP_APPROVED);
				} else {
					childrens = investorCategoryRepository.findAllActiveChildrenCategory(selectionParentIds, lang,CmsStepNoStatusConstant.STEP_APPROVED);
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
	public void initDataEditPage(ModelAndView mav, InvestorCategoryDto editDto, Locale locale) {
		// requestToken
		String requestToken = CommonUtil.randomStringWithTimeStamp();
		editDto.setRequestToken(requestToken);
		
		if (editDto.getId() == null && CollectionUtils.isEmpty(editDto.getInfoByLanguages())) {
			List<InvestorCategoryLanguageDto> lstInfoByLanguage = new ArrayList<InvestorCategoryLanguageDto>();
			List<Language> languageList = langService.findAllActive();
			for (Language lang : languageList) {
				InvestorCategoryLanguageDto introCateLangDto = new InvestorCategoryLanguageDto();
				introCateLangDto.setLanguageCode(lang.getCode());
				introCateLangDto.setLanguageDispName(lang.getName());
				lstInfoByLanguage.add(introCateLangDto);
				editDto.setInfoByLanguages(lstInfoByLanguage);
			}
		}
		// Init PhanLoai
		@SuppressWarnings("unused")
		List<JcaConstantDto> enabledType = new ArrayList<JcaConstantDto>();
		if(editDto.getLevelId() != null) {
			if(editDto.getLevelId().equals(vn.com.unit.cms.admin.all.constant.CmsCommonConstant.LEVEL_ID_3) 
					|| editDto.getLevelId().equals(vn.com.unit.cms.admin.all.constant.CmsCommonConstant.LEVEL_ID_4)) {
				
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
				
//				enabledType = constDisplayService.findByType(ConstDispType.NDT);
				
				enabledType = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.NDT.toString(), "EN");
			}
		}
//		enabledType = constDisplayService.findTypeInvestorCategoryByLevelId(ConstDispType.NDT);
//		mav.addObject("enabledType", enabledType);

		// list sort
		mav.addObject("lstSort", getListSortId(editDto.getId(), null, locale.toString()));

		//locationLeft 
		String locationLeft = investorCategoryRepository.countInvestorCategoryLocationLeft(editDto.getId(),INVESTOR_TYPE_ID);
		mav.addObject("locationLeft", locationLeft);
		//locationRightTop
		String locationRightTop = investorCategoryRepository.countInvestorCategoryLocationRightTop(editDto.getId(),INVESTOR_TYPE_ID);
		mav.addObject("locationRightTop", locationRightTop);
		//locationRightBottom
		String locationRightBottom = investorCategoryRepository.countInvestorCategoryLocationRightBottom(editDto.getId(),INVESTOR_TYPE_ID);
		mav.addObject("locationRightBottom", locationRightBottom);
		
		//countAllLeft
		Integer countLocationLeft = investorCategoryRepository.countAllInvestorCategoryLocationLeft(INVESTOR_TYPE_ID);
		mav.addObject("countLocationLeft", countLocationLeft);
		//countAllRightTop
		Integer countLocationRightTop = investorCategoryRepository.countAllInvestorCategoryLocationRightTop(INVESTOR_TYPE_ID);
		mav.addObject("countLocationRightTop", countLocationRightTop);
		//countAllRightBottom
		Integer countLocationRightBottom = investorCategoryRepository.countAllInvestorCategoryLocationRightBottom(INVESTOR_TYPE_ID);
		mav.addObject("countLocationRightBottom", countLocationRightBottom);
		
		//set processId
		@SuppressWarnings("unused")
		String businessCode = CmsCommonConstant.BUSINESS_INVESTOR;
		Long processId = editDto.getProcessId();
		if ( processId == null) {
			// First step
//			JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(businessCode, locale.toString());
//			 processId = processDto.getProcessId();
		}
		// List button of step
//		List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId,
//				editDto.getStatus(), locale.toString());
//		editDto.setStepBtnList(stepButtonList);

//		String statusName = jprocessService.getStatusName(editDto.getProcessId(), editDto.getStatus(), locale);
//		editDto.setStatusName(statusName);

		// Init PageWrapper History Approval
//		PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.doSearch(1, editDto.getId(),
//				editDto.getProcessId(), ConstantHistoryApprove.APPROVE_NDT_INVESTOR_CATEGORY, locale);
//		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
	}

	@Override
	public List<InvestorCategoryDto> getListSortId(Long id, Long parentId, String lang) {
		List<InvestorCategoryDto> lstSort = investorCategoryRepository.findListSortRemovedId(id, parentId, lang);
		if (lstSort == null) {
			lstSort = new ArrayList<InvestorCategoryDto>();
			return lstSort;
		}
		return lstSort;
	}

	@Override
	public InvestorCategory getInvestorCategoryByCode(String code) {
		return investorCategoryRepository.findInvestorCategoryByCode(code);
	}

	@Override
	public InvestorCategoryLanguageDto getByAliasAndCategoryId(Long categoryId,Long customerId, String linkAlias, String language) {
		return investorCategoryRepository.findByAliasAndCategoryId(categoryId,customerId, linkAlias, language);
	}

	@Override
	public void doEdit(InvestorCategoryDto editDto, Locale locale, HttpServletRequest request) throws IOException {
		
		createOrEdit(editDto, locale, request);
		createOrEditLanguage(editDto, locale);
		// if action process
		if (!StringUtils.equals(editDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
			// updateHistoryApprove
			updateHistoryApprove(editDto, locale);

			sendMail(editDto, request);
			
			// clear cache api /commons
            APIUtils.callApiGet(AdminUrlConst.URL_CACHES_COMMONS);
            
         // clear cache api /investors
            APIUtils.callApiGet(AdminUrlConst.URL_CACHES_INVESTORS);
		}
	}
	
	private void createOrEdit(InvestorCategoryDto editDto, Locale locale, HttpServletRequest request) {
		// user login
// 		UserProfile userProfile = UserProfileUtils.getUserProfile();
		
		InvestorCategory entity =  new InvestorCategory();
		
		if (null != editDto.getId()) {
			entity = investorCategoryRepository.findOne(editDto.getId());
			// dữ liệu ko tồn tại hoặc đã bị xóa
			if (null == entity || entity.getDeleteDate() != null) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
			}
			if (entity.getUpdateDate() != null && !entity.getUpdateDate().equals(editDto.getUpdateDate())) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
			}
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(UserProfileUtils.getUserNameLogin());
			
		}else {
			entity.setCreateDate(new Date());
			entity.setCreateBy(UserProfileUtils.getUserNameLogin());
//			entity.setCode(CommonUtil.getNextCode(PREFIX_CODE, commonService.getMaxCode("M_INVESTOR_CATEGORY", PREFIX_CODE)));

		}
		// setParentId
		Long parentId = editDto.getParentId();
		if (parentId == null) {
			entity.setInvestorCategoryLevelId(LEVEL_ID_1);
			entity.setInvestorCategoryParentId(0L);
		} else {
			// set levelID
			InvestorCategorySearchDto searchDto = new InvestorCategorySearchDto();
			searchDto.setParentId(parentId);
			searchDto.setCustomerId(INVESTOR_TYPE_ID);
			InvestorCategoryDto itemParent = investorCategoryRepository.findLevelIdByParentId(searchDto,
					locale.toString());
			if (itemParent != null) {
				entity.setInvestorCategoryParentId(itemParent.getId());
				// check esixts of CategoryType in ParentId
				Integer categoryType = itemParent.getCategoryType();
				if (categoryType != null) {
					// Không được phép tạo danh mục con
					throw new BusinessException(msg.getMessage(ConstantCore.MSG_ERROR_EXISTS_CATEGORYTYPE_PARENTID, null, locale));
				}
				// Tiếp tục tạo level.
				Integer levelId = itemParent.getLevelId();
				if (levelId != null) {
					if (levelId.equals(LEVEL_ID_4)) {
						throw new BusinessException(msg.getMessage(ConstantCore.MSG_RULE_LEVEL_ID, null, locale));
					}
					entity.setInvestorCategoryLevelId(levelId + 1);
				}
			}
		}
		try {
			// updateBeforeId
			updateSortAndBeforeId(editDto, locale);
			entity.setSort(editDto.getSort());
			entity.setBeforeId(editDto.getBeforeId());
			entity.setStatus(editDto.getStatus());
			// Hình bên trái
			String urlImgTmpNameLeft = editDto.getImageUrlLeft();
			// upload images
			if (StringUtils.isNotEmpty(urlImgTmpNameLeft)) {
				String newUrlNameLeft = CmsUtils.moveTempToUploadFolder(urlImgTmpNameLeft,
						AdminConstant.INTVESTOR_CATEGORY_FOLDER);
				entity.setImageUrlLeft(newUrlNameLeft);
				entity.setImageNameLeft(editDto.getImageNameLeft());
			} else {
				entity.setImageUrlLeft(null);
				entity.setImageNameLeft(null);
			}
			// hình bên phải
			String urlImgTmpNameRight = editDto.getImageUrlRight();
			if (StringUtils.isNotEmpty(urlImgTmpNameRight)) {
				String newUrlNameRight = CmsUtils.moveTempToUploadFolder(urlImgTmpNameRight,
						AdminConstant.INTVESTOR_CATEGORY_FOLDER);
				entity.setImageUrlRight(newUrlNameRight);
				entity.setImageNameRight(editDto.getImageNameRight());
			} else {
				entity.setImageUrlRight(null);
				entity.setImageNameRight(null);
			}
			
			// hình danh mục tin tức
			String urlImgTmpName = editDto.getImageUrl();
			if (StringUtils.isNotEmpty(urlImgTmpName)) {
				String newUrlName = CmsUtils.moveTempToUploadFolder(urlImgTmpName,
						AdminConstant.INTVESTOR_CATEGORY_FOLDER);
				entity.setImageUrl(newUrlName);
				entity.setImageName(editDto.getImageName());
			} else {
				entity.setImageUrl(null);
				entity.setImageName(null);
			}

			// set LocationLeft
			if(editDto.getCategoryLocationLeft() == true) {
				setValueNullForCategoryLeft();
				entity.setInvestorCategoryLocationLeft(editDto.getCategoryLocationLeft());
			}
			entity.setInvestorCategoryLocationLeft(editDto.getCategoryLocationLeft());
			//set LocationRightTop
			if(editDto.getCategoryLocationRightTop() == true) {
				setValueNullForCategoryRightTop();
				entity.setInvestorCategoryLocationRightTop(editDto.getCategoryLocationRightTop());
			}
			entity.setInvestorCategoryLocationRightTop(editDto.getCategoryLocationRightTop());
			
			//set LocationRightBottom
			if(editDto.getCategoryLocationRightBottom() == true) {
				setValueNullForCategoryRightBottom();
				entity.setInvestorCategoryLocationRightBottom(editDto.getCategoryLocationRightBottom());
			}
			entity.setInvestorCategoryLocationRightBottom(editDto.getCategoryLocationRightBottom());
			
			//setCategoryType
			if(StringUtils.isNotEmpty(editDto.getCategoryKind()) ) {
				Integer categoryType  = Integer.parseInt(editDto.getCategoryKind());
				entity.setInvestorCategoryType(categoryType);
			}
			entity.setCustomerTypeId(12L);
			entity.setEnable(editDto.getEnabled());

			// Process
			if (!StringUtils.equals(editDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
				if (editDto.getProcessId() == null) {
					// First step
//					JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(CommonConstant.BUSINESS_INVESTOR, locale.toString());
//					editDto.setProcessId(processDto.getProcessId());
				}
//				JProcessStepDto currentActionStep = jprocessService.findCurrentProcessStep(editDto.getProcessId(),
//						editDto.getStatus(), editDto.getButtonId());
//				Integer status = jprocessService.getNextStepNo(currentActionStep, null);

				editDto.setOldStatus(editDto.getStatus());
				// set status
//				editDto.setStatus(status);
//				editDto.setCurrItem(currentActionStep.getItems());
			}
			
			entity.setProcessId(editDto.getProcessId());
			entity.setStatus(editDto.getStatus());
			entity.setNote(editDto.getNote());
			investorCategoryRepository.save(entity);
			editDto.setId(entity.getId());
			editDto.setCode(entity.getCode());
			updateBeforeIdAfter(editDto, locale);
		} catch (Exception e) {
			logger.error("createOrEdit: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}

	}

	private void updateBeforeIdAfter(InvestorCategoryDto editDto, Locale locale) {
		InvestorCategorySearchDto searchDto = new InvestorCategorySearchDto();
		searchDto.setId(editDto.getId());
		List<InvestorCategoryDto> listInvestorCate = investorCategoryRepository.findAllInvestorCategoryForSort(searchDto,
				locale.toString());
		if (CollectionUtils.isEmpty(listInvestorCate)) {
			return;
		}
		
		InvestorCategoryDto item = listInvestorCate.get(0);
		if (item.getId().equals(editDto.getBeforeId()) && !item.getBeforeId().equals(0l)) {
			InvestorCategory entity = investorCategoryRepository.findOne(item.getId());
			entity.setBeforeId(0L);
			investorCategoryRepository.save(entity);
		}
		for (int i = 0, sz = listInvestorCate.size(); i < sz - 1; i++) {
			item = listInvestorCate.get(i);
			if (item.getId().equals(editDto.getId())) {
				InvestorCategoryDto itemNext = listInvestorCate.get(i + 1);
				InvestorCategory entity = investorCategoryRepository.findOne(itemNext.getId());
				entity.setBeforeId(item.getId());
				investorCategoryRepository.save(entity);
				break;
			}
		}
	}
	
	private void updateSortAndBeforeId(InvestorCategoryDto editDto, Locale locale) {
		InvestorCategorySearchDto searchDto = new InvestorCategorySearchDto();
		searchDto.setId(editDto.getId());
		searchDto.setParentId(editDto.getParentId());

		List<InvestorCategoryDto> listInvestorCate = investorCategoryRepository.findAllInvestorCategoryForSort(searchDto,
				locale.toString());
		if (null != editDto.getBeforeId()) {
			int indexBefore = listInvestorCate.size();
			for (int i = 0, sz = indexBefore; i < sz; i++) {
				InvestorCategoryDto item = listInvestorCate.get(i);
				if (item.getId().equals(editDto.getBeforeId())) {
					indexBefore = i;
					editDto.setSort(item.getSort() + 1);
					break;
				}
			}
			Long currentSort = editDto.getSort() + 1;
			for (int i = indexBefore + 1, sz = listInvestorCate.size(); i < sz; i++) {
				InvestorCategoryDto item = listInvestorCate.get(i);
				InvestorCategory entity = investorCategoryRepository.findOne(item.getId());
				entity.setSort(currentSort++);
				investorCategoryRepository.save(entity);
			}
		} else {
			if (CollectionUtils.isNotEmpty(listInvestorCate)) {
				InvestorCategoryDto item = listInvestorCate.get(listInvestorCate.size() - 1);
				if(editDto.getSort() != null) {
					editDto.setSort(editDto.getSort());
				}else {
					editDto.setSort(item.getSort() + 1);
				}
				if (!item.getId().equals(editDto.getId())) {
					editDto.setBeforeId(item.getId());
				} else if (listInvestorCate.size() > 2) {
					editDto.setBeforeId(listInvestorCate.get(listInvestorCate.size() - 2).getId());
				} else {
					editDto.setBeforeId(0l);
				}
			} else {
				if(editDto.getId() == null && editDto.getParentId() != null) {
					InvestorCategoryDto sortMax = investorCategoryRepository.getMaxSortInvestorCate();
					Long maxSort = sortMax.getSort();
					editDto.setSort(maxSort + 1);
					editDto.setBeforeId(0l);
				}
				editDto.setSort(1L);
				editDto.setBeforeId(0l);
			}
		}
	}
	
	private void createOrEditLanguage(InvestorCategoryDto editDto, Locale locale) {
		try {
//			UserProfile userProfile = UserProfileUtils.getUserProfile();
			for (InvestorCategoryLanguageDto cLanguageDto : editDto.getInfoByLanguages()) {
				InvestorCategoryLanguage entity = new InvestorCategoryLanguage();
				if (null != cLanguageDto.getId()) {
					entity = investorcategoryLanguageCato.findOne(cLanguageDto.getId());
					if (null == entity || entity.getDeleteDate() != null) {
						throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
					}
					entity.setUpdateDate(new Date());
					entity.setUpdateBy(UserProfileUtils.getUserNameLogin());
				} else {
					entity.setCreateDate(new Date());
					entity.setCreateBy(UserProfileUtils.getUserNameLogin());
				}
				entity.setCategoryId(editDto.getId());
				entity.setLanguageCode(cLanguageDto.getLanguageCode().toUpperCase());
				entity.setTitle(cLanguageDto.getLabel());
				entity.setLinkAlias(cLanguageDto.getLinkAlias());
				entity.setKeyWord(cLanguageDto.getKeyWord());
				entity.setDescriptionKeyword(cLanguageDto.getDescriptionKeyword());
				entity.setShortContent(cLanguageDto.getShortContent());

				investorcategoryLanguageCato.save(entity);
			}
			
			CmsUtils.moveTempSubFolderToUpload(Paths.get(AdminConstant.INTVESTOR_CATEGORY_EDITOR_FOLDER, editDto.getRequestToken()).toString());
		} catch (Exception e) {
			logger.error("createOrEditLanguage: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	private void updateHistoryApprove(InvestorCategoryDto editDto, Locale locale) {
		try {
			// insert comment
			HistoryApproveDto historyApproveDto = new HistoryApproveDto();
			historyApproveDto.setApprover(UserProfileUtils.getFullName());
			historyApproveDto.setComment(editDto.getNote());
			historyApproveDto.setProcessId(editDto.getProcessId());
			historyApproveDto.setProcessStep(editDto.getStatus().longValue());
			historyApproveDto.setReferenceId(editDto.getId());
			historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_NDT_INVESTOR_CATEGORY);
			historyApproveDto.setActionId(editDto.getButtonId().toString());
			historyApproveDto.setOldStep(editDto.getOldStatus());
			historyApproveDto.setAccountId(UserProfileUtils.getAccountId());
//			historyApproveService.addHistoryApprove(historyApproveDto);
		} catch (Exception e) {
			logger.error("updateHistoryApprove: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	private void sendMail(InvestorCategoryDto editDto, HttpServletRequest request) {
		// locale default
		String defaultlocale = systemConfig.getConfig(SystemConfig.LANGUAGE_DEFAULT);
		Locale locale = new Locale(defaultlocale);

		EmailCommonDto emailCommon = new EmailCommonDto();
		emailCommon.setActionName(msg.getMessage("subject.email.template.investor.category", null, locale));
		emailCommon.setButtonAction(editDto.getButtonAction());
		emailCommon.setButtonId(editDto.getButtonId().toString());
		emailCommon.setComment(editDto.getNote());

		// Nội dung
		LinkedHashMap<String, String> content = new LinkedHashMap<>();

		content.put("Mã", editDto.getCode());
		content.put("Danh mục nhà đầu tư", editDto.getInfoByLanguages().get(0).getLabel());
		emailCommon.setContent(content);

		emailCommon.setCurrItem(editDto.getCurrItem());

		emailCommon.setId(editDto.getId());
		emailCommon.setOldStatus(editDto.getOldStatus());
		emailCommon.setProcessId(editDto.getProcessId());
		emailCommon.setReferenceType(editDto.getReferenceType());
		emailCommon.setStatus(editDto.getStatus());

		// Subject của email
		emailCommon.setSubject(msg.getMessage("subject.email.template.investor.category", null, locale));

		emailCommon.setUrl(CmsUtils.getBaseUrl(request) + "/investor-category/edit?id=" + editDto.getId());

//		emailUtil.sendMail(emailCommon, request, locale);
	}

	@Override
	public InvestorCategoryDto getInvestorCategoryDtoById(Long id, Integer status, Locale locale) {
		return investorCategoryRepository.findInvestorCategoryDtoById(id, status, locale.toString());
	}

	@Override
	public List<InvestorCategoryDto> findAllActiveChildrenCategory(Long parentId, Locale locale) {
		List<Long> parentIds = new ArrayList<Long>();
		parentIds.add(parentId);
		return investorCategoryRepository.findAllActiveChildrenCategory(parentIds, locale.toString(),CmsStepNoStatusConstant.STEP_APPROVED);
	}

	/**
	 * setValueNullForCategoryRightBottom
	 */
	private void setValueNullForCategoryRightBottom() {
		StringBuffer sqlStrBuffer = new StringBuffer();
		sqlStrBuffer.append("UPDATE M_INVESTOR_CATEGORY SET INVESTOR_CATEGORY_LOCATION_RIGHT_BOTTOM = null ");
		sqlManager.executeUpdate(new StringSqlResource(sqlStrBuffer.toString()));
	}

	/**
	 * setValueNullForCategoryRightTop
	 */
	private void setValueNullForCategoryRightTop() {
		StringBuffer sqlStrBuffer = new StringBuffer();
		sqlStrBuffer.append("UPDATE M_INVESTOR_CATEGORY SET INVESTOR_CATEGORY_LOCATION_RIGHT_TOP = null ");
		sqlManager.executeUpdate(new StringSqlResource(sqlStrBuffer.toString()));
	}

	/**
	 * setValueNullForCategoryLeft
	 */
	private void setValueNullForCategoryLeft() {
		StringBuffer sqlStrBuffer = new StringBuffer();
		sqlStrBuffer.append("UPDATE M_INVESTOR_CATEGORY SET INVESTOR_CATEGORY_LOCATION_LEFT = null ");
		sqlManager.executeUpdate(new StringSqlResource(sqlStrBuffer.toString()));
	}

	@Override
	public List<InvestorCategoryDto> findExistOfParentIdById(Long id, Long customerId) {
		return investorCategoryRepository.checkExistChildrenIdById(id, customerId);
	}

	@Override
	public List<InvestorCategoryDto> getInvestorCateRoot(List<InvestorCategoryDto> investorList) {
		List<InvestorCategoryDto> result = new ArrayList<>();
		// Find roots
		for (int i = 0; i < investorList.size(); i++) {
			InvestorCategoryDto investorCategoryDto = investorList.get(i);
			if (null == investorCategoryDto.getParentId() || investorCategoryDto.getParentId().equals(0l)) {
				result.add(investorCategoryDto);
			}
		}
		return result;
	}
	@Override
	public List<InvestorCategoryDto> sortInvestorCategoryDtoByConstructorTree(List<InvestorCategoryDto> investorList,
			List<InvestorCategoryDto> resultRoot) {
		List<InvestorCategoryDto> result = new ArrayList<>();
		result.addAll(resultRoot);
		int i = 0;
		while (i < result.size()) {
			InvestorCategoryDto dto = result.get(i);
			Long cateId = dto.getId();
			List<InvestorCategoryDto> dtoChildren = new ArrayList<InvestorCategoryDto>();
			if (investorList != null && !investorList.isEmpty()) {
				int investorListSize = investorList.size();
				for (int j = investorListSize - 1; j >= 0; j--) {
					InvestorCategoryDto childDto = investorList.get(j);
					Long parentId = childDto.getParentId();
					if (parentId != null && parentId.equals(cateId) ) {
						result.add(i + 1, childDto);
						dtoChildren.add(childDto);
					}
				}
				investorList.removeAll(dtoChildren);
				i++;
			} else {
				break;
			}
		}
		return result;
	}

	@Override
	public void exportExcelInvestorCategory(InvestorCategorySearchDto searchDto, HttpServletResponse response,
			Locale locale) {
		try {
			// set status name
			searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));
			
			/* change template */
			String templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_INVESTOR_CATEGORY;
			String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
					+ CmsCommonConstant.TYPE_EXCEL;
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
			
			List<InvestorCategoryDto> exportResult = investorCategoryRepository.exportExcelWithCondition(searchDto);
			
			if (StringUtils.isNotEmpty(searchDto.getCode()) || StringUtils.isNotEmpty(searchDto.getName()) 
					|| searchDto.getCategoryType() != null || searchDto.getStatus() != null) 
				exportResult = getParentLine(exportResult,locale);
			
			// resultRoot
			List<InvestorCategoryDto> resultRoot = getInvestorCateRoot(exportResult);

			exportResult = sortInvestorCategoryDtoByConstructorTree(exportResult, resultRoot);
			
			for(InvestorCategoryDto data : exportResult) {
				getDataParentCategoryForExport(data.getId(), data, locale);
			}

			//set STT
			for (int i = 0; i < exportResult.size(); i++) {
				exportResult.get(i).setStt(i + 1);
			}
			List<ItemColsExcelDto> cols = new ArrayList<>();
			// start fill data to workbook
			ImportExcelUtil.setListColumnExcel(ExportInvestorCategoryExportEnum.class, cols);

			ExportExcelUtil<InvestorCategoryDto> exportExcel = new ExportExcelUtil<>();
			// do export
			exportExcel.exportExcelWithXSSFNonPass(template, locale, exportResult, InvestorCategoryDto.class, cols,
					datePattern, response, templateName);
		}catch(Exception e) {
			logger.error("exportExcelInvestorCategory:" + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_EXPORT_EXCEL, null, locale));
		}
	}

	/**
	 * getDataParentCategoryForExport
	 */
	private void getDataParentCategoryForExport(Long id, InvestorCategoryDto data, Locale locale) {
		List<Long> parentsId = new ArrayList<>();
		parentsId.add(id);
		InvestorCategoryDto investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(id,null, locale.toString());
		if(investorCategoryDto.getLevelId() != null) {
			switch (investorCategoryDto.getLevelId()) {
			case 1:
				// level 1
				break;
			case 2:
				investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(
						investorCategoryDto.getParentId(), null, locale.toString());
				getDataParentCategoryForExport(investorCategoryDto.getId(), data, locale);
				//set levelname1
				data.setInvestorCategoryNameLevel1(investorCategoryDto.getTitle());
				
				break;
			case 3:
				// level 2
				investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(
						investorCategoryDto.getParentId(), null, locale.toString());
				getDataParentCategoryForExport(investorCategoryDto.getId(), data, locale);
				
				data.setInvestorCategoryNameLevel2(investorCategoryDto.getTitle());
				
				// level 1
				investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(
						investorCategoryDto.getParentId(), null, locale.toString());
				getDataParentCategoryForExport(investorCategoryDto.getId(), data, locale);
				
				break;
			case 4:
				// level 3
				investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(
						investorCategoryDto.getParentId(), null, locale.toString());
				getDataParentCategoryForExport(investorCategoryDto.getId(), data, locale);
				data.setInvestorCategoryNameLevel3(investorCategoryDto.getTitle());
				// level 2
				investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(
						investorCategoryDto.getParentId(), null, locale.toString());
				getDataParentCategoryForExport(investorCategoryDto.getId(), data, locale);
				// level 1
				investorCategoryDto = investorCategoryRepository.findInvestorCategoryDtoById(
						investorCategoryDto.getParentId(), null, locale.toString());
				getDataParentCategoryForExport(investorCategoryDto.getId(), data, locale);
				break;
			}
		}
		
	}

	@Override
	public void initSortPage(Long parentId, ModelAndView mav, Locale locale) {
		InvestorCategorySearchDto searchDto = new InvestorCategorySearchDto();
		if(parentId != null) {
			searchDto.setParentId(parentId);
			searchDto.setLanguageCode(locale.toString());
			searchDto.setCustomerId(INVESTOR_TYPE_ID);
		}
		List<InvestorCategoryDto> investorCateList = investorCategoryRepository.findInvestorCategoryForSort(searchDto);

		SortPageDto sortPageModel = createSortOrderDtoList(investorCateList);
		mav.addObject("sortPageModel", sortPageModel);
		mav.addObject("sortList", investorCateList);
	}

	private SortPageDto createSortOrderDtoList(List<InvestorCategoryDto> investorCateList) {
		SortPageDto sortPageModel = new SortPageDto();
		List<SortOrderDto> sortList = new ArrayList<SortOrderDto>();
		Long sortIndex = 0L;
		for(InvestorCategoryDto dto : investorCateList) {
			SortOrderDto sortItem = new SortOrderDto();
			sortItem.setObjectId(dto.getId());
			sortItem.setSortValue(sortIndex);
			sortList.add(sortItem);
		}
		sortPageModel.setSortList(sortList);
		return sortPageModel;
	}

	@Override
	public void updateModelsSort(SortPageDto sortPageModel) {
		if (CollectionUtils.isNotEmpty(sortPageModel.getSortList())) {
			for (SortOrderDto sortItem : sortPageModel.getSortList()) {
				investorCategoryRepository.updateInvestorCategoryChildSortAll(sortItem);
			}
		}
		Long itemId = 0L;
		for (SortOrderDto dto : sortPageModel.getSortList()) {
			InvestorCategory item = investorCategoryRepository.findOne(dto.getObjectId());
			item.setBeforeId(itemId);
			itemId = item.getId();
			investorCategoryRepository.save(item);
		}
	}

	@Override
	public boolean checkRelationshipChild(Long id) {
		return investorCategoryRepository.checkRelationshipChild(INVESTOR_TYPE_ID, id);
	}
	
	@Override
	public List<Map <String, String>> listDependencies(Long id) {
		return investorCategoryRepository.listDependencies(INVESTOR_TYPE_ID, id);
	}

	@Override
	public InvestorCategoryDto getEdit(Long id, String customerAlias, Locale locale) {
		return getEdit(id, customerAlias, locale);
	}
}
