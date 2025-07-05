/*******************************************************************************
 * Class        ：ProductCategorySubServiceImpl
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import vn.com.unit.common.exception.SystemException;
import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.constant.CmsStepNoStatusConstant;
import vn.com.unit.cms.admin.all.dto.EmailCommonDto;
import vn.com.unit.cms.admin.all.dto.ExportProductCategorySubReportDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubEditDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubLanguageDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubSearchDto;
import vn.com.unit.cms.admin.all.entity.ProductCategorySub;
import vn.com.unit.cms.admin.all.entity.ProductCategorySubLanguage;
import vn.com.unit.cms.admin.all.enumdef.ExportProductCategorySubExportEnum;
import vn.com.unit.cms.admin.all.enumdef.StepActionEnum;
//import vn.com.unit.cms.admin.constant.StepNoStatusConstant;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.jcanary.utils.APIUtils;
import vn.com.unit.cms.admin.all.repository.ProductCategorySubRepository;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.ProductCategoryLanguageService;
import vn.com.unit.cms.admin.all.service.ProductCategoryService;
import vn.com.unit.cms.admin.all.service.ProductCategorySubLanguageService;
import vn.com.unit.cms.admin.all.service.ProductCategorySubService;
import vn.com.unit.cms.admin.all.service.ProductService;
import vn.com.unit.cms.admin.all.util.HDBankUtil;
//import vn.com.unit.core.entity.Language;
//import vn.com.unit.cms.admin.enumdef.StepActionEnum;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.service.EmailService;
//import vn.com.unit.cms.admin.all.jcanary.utils.APIUtils;
//import vn.com.unit.jcanary.utils.ExportExcelUtil;
//import vn.com.unit.jcanary.utils.ImportExcelUtil;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.entity.Language;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
import vn.com.unit.ep2p.core.exception.BusinessException;
//import vn.com.unit.util.Util;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

/**
 * ProductCategorySubServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProductCategorySubServiceImpl
        extends DocumentWorkflowCommonServiceImpl<ProductCategorySubEditDto, ProductCategorySubEditDto>
        implements ProductCategorySubService {
	@Autowired
	private ProductCategorySubLanguageService categorySubLanguageService;

	@Autowired
	private ProductCategorySubRepository productCategorySubRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private LanguageService languageService;

//	@Autowired
//	HistoryApproveService historyApproveService;
//
//	@Autowired
//	ConstantDisplayService constDispService;

	@Autowired
	ProductCategoryService productCategoryService;

	@Autowired
	ProductCategoryLanguageService categoryLanguageService;

	@Autowired
	private CmsCommonService commonService;

	@Autowired
	private CmsFileService fileService;

//	@Autowired
//	JProcessService jprocessService;

	@Autowired
	MessageSource msg;

	@Autowired
	AccountService accountService;

	@Autowired
	EmailService emailService;

	@Autowired
	ServletContext servletContext;

//	@Autowired
//	EmailUtil emailUtil;

	private static final String PREFIX_CODE = "PROD.C.";
	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(ProductCategorySubServiceImpl.class);

	/**
	 * add Or Edit ProductCategorySub
	 *
	 * @param categoryEditDto
	 * @author hand
	 * @throws IOException
	 */
	@Override
	@Transactional
	public boolean addOrEdit(ProductCategorySubEditDto categoryEditDto, Locale locale, HttpServletRequest request)
			throws IOException {

		boolean result = true;

		// user name login
//		UserProfile userProfile = UserProfileUtils.getUserProfile();

		// do save product Sub category
		createOrEditProductCategorySub(categoryEditDto, UserProfileUtils.getUserNameLogin(), locale, request);

		if (!result) {
			return result;
		}

		// do save product category Sub language
		createOrEditCategoryLanguage(categoryEditDto, UserProfileUtils.getUserNameLogin(), locale);

		// move file upload
		CmsUtils.moveTempSubFolderToUpload(Paths.get(AdminConstant.PRODUCT_CATEGORY_FOLDER, AdminConstant.EDITOR_FOLDER,
				categoryEditDto.getRequestToken()).toString());

		// if action process
		if (!StringUtils.equals(categoryEditDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
			// update history approve
			updateHistoryApprove(categoryEditDto, locale);

			// send mail
			sendMail(categoryEditDto, request, locale);

			// clear cache api /commons
			APIUtils.callApiGet(AdminUrlConst.URL_CACHES_COMMONS);

			// clear cache api /personal
			APIUtils.callApiGet(AdminUrlConst.URL_CACHES_PERSONAL);

			// clear cache api /corporate
			APIUtils.callApiGet(AdminUrlConst.URL_CACHES_CORPORATE);

			// clear cache api /home
			APIUtils.callApiGet(AdminUrlConst.URL_CACHES_HOME);
		}
		return true;
	}

	/**
	 * create or update ProductCategorySub
	 *
	 * @param editDto
	 * @author hand
	 * @throws IOException
	 */
	private void createOrEditProductCategorySub(ProductCategorySubEditDto editDto, String usernameAction, Locale locale,
			HttpServletRequest request) throws IOException {

		ProductCategorySub entity = new ProductCategorySub();

		if (null != editDto.getId()) {
			entity = productCategorySubRepository.findOne(editDto.getId());
			if (null == entity || entity.getDeleteDate() != null) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
			}

			if (entity.getUpdateDate() != null && !entity.getUpdateDate().equals(editDto.getUpdateDate())) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
			}

			List<Long> lstStatus = HDBankUtil.getListStatusForDependency();
			int countDependencies = countDependencies(entity.getId(), lstStatus);

			if (countDependencies > 0) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
			}

			entity.setUpdateDate(new Date());
			entity.setUpdateBy(usernameAction);
		} else {
			entity.setCreateDate(new Date());
			entity.setCreateBy(usernameAction);
			// code generation
			entity.setCode(CommonUtil.getNextCode(PREFIX_CODE,
					commonService.getMaxCode("M_PRODUCT_CATEGORY_SUB", PREFIX_CODE)));
		}

		try {
			entity.setCustomerTypeId(editDto.getCustomerTypeId());
			entity.setName(editDto.getName());
			entity.setNote(editDto.getNote());
			entity.setCategId(editDto.getCategoryId());
			entity.setEnabled(editDto.isEnabled());
			entity.setStatus(editDto.getStatus());
			entity.setProcessId(editDto.getProcessId());
			entity.setBannerDesktop(editDto.getBannerDesktop());
			entity.setBannerMobile(editDto.getBannerMobile());

			// set sort and beforeId for dto
			updateSortAndBeforeId(editDto);
			entity.setBeforeId(editDto.getBeforeId());
			entity.setSort(editDto.getSort());

			entity.setPriority(editDto.getIsPriority());

			// physical image
			String physicalImgTmpName = editDto.getPhysicalImg();
			// upload images
			if (StringUtils.isNotEmpty(physicalImgTmpName)) {
				String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalImgTmpName,
						AdminConstant.PRODUCT_CATEGORY_FOLDER);
				entity.setPhysicalImg(newPhiscalName);
				entity.setImageName(editDto.getImageName());
			} else {
				entity.setPhysicalImg(null);
				entity.setImageName(null);
			}
			// physical hover
			String physicalHoverTmpName = editDto.getPhysicalImgHover();
			// upload images
			if (StringUtils.isNotEmpty(physicalHoverTmpName)) {
				String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalHoverTmpName,
						AdminConstant.PRODUCT_CATEGORY_FOLDER);
				entity.setPhysicalImgHover(newPhiscalName);
				entity.setImageHoverName(editDto.getImageHoverName());
			} else {
				entity.setPhysicalImgHover(null);
				entity.setImageHoverName(null);
			}

			// physical icon
			String physicalIconTmpName = editDto.getPhysicalIcon();
			// upload images
			if (StringUtils.isNotEmpty(physicalIconTmpName)) {
				String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalIconTmpName,
						AdminConstant.PRODUCT_CATEGORY_FOLDER);
				entity.setPhysicalIcon(newPhiscalName);
				entity.setIconImg(editDto.getIconImg());
			} else {
				entity.setPhysicalIcon(null);
				entity.setIconImg(null);
			}

			// if action process
//			if (!StringUtils.equals(editDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
//				if (editDto.getProcessId() == null) {
//					// First step
//					JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(editDto.getBusinessCode(),
//							locale.toString());
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
			entity.setComment(editDto.getComment());
			// save entity
			productCategorySubRepository.save(entity);

			if (entity.getId() != null) {
				editDto.setId(entity.getId());
				editDto.setCode(entity.getCode());
			}
			// update before id for product after product edited
			updateBeforeIdForProductAfter(editDto);
		} catch (Exception e) {
			logger.error("createOrEditProductCategorySub: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}

	}

	/**
	 * updateHistoryApprove
	 * 
	 * @param editDto
	 */
	private void updateHistoryApprove(ProductCategorySubEditDto editDto, Locale locale) {
		try {
			// insert comment
			HistoryApproveDto historyApproveDto = new HistoryApproveDto();
			historyApproveDto.setApprover(UserProfileUtils.getFullName());
			historyApproveDto.setComment(editDto.getComment());
			historyApproveDto.setProcessId(editDto.getProcessId());
			historyApproveDto.setProcessStep(editDto.getStatus().longValue());
			historyApproveDto.setReferenceId(editDto.getId());
			historyApproveDto.setReferenceType(editDto.getReferenceType());
//			historyApproveDto.setActionId(editDto.getButtonId());
			historyApproveDto.setOldStep(editDto.getOldStatus());
			historyApproveDto.setAccountId(UserProfileUtils.getAccountId());
//			historyApproveService.addHistoryApprove(historyApproveDto);
		} catch (Exception e) {
			logger.error("updateHistoryApprove: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	/**
	 * createOrEditCategoryLanguage
	 *
	 * @param editDto
	 * @author hand
	 */
	private void createOrEditCategoryLanguage(ProductCategorySubEditDto editDto, String userName, Locale locale) {
		try {
			for (ProductCategorySubLanguageDto cLanguageDto : editDto.getCategoryLanguageList()) {

				ProductCategorySubLanguage entity = new ProductCategorySubLanguage();

				if (null != cLanguageDto.getId()) {
					entity = categorySubLanguageService.findByid(cLanguageDto.getId());
					if (null == entity) {
						throw new BusinessException(
								"Not found ProductCategorySubLanguag with id=" + cLanguageDto.getId());
					}
					entity.setUpdateDate(new Date());
					entity.setUpdateBy(userName);
				} else {
					entity.setCreateDate(new Date());
					entity.setCreateBy(userName);
				}

				entity.setProductCategorySubId(editDto.getId());
				entity.setTitle(cLanguageDto.getTitle());
				entity.setLanguageCode(cLanguageDto.getlanguageCode());
				entity.setDescription(cLanguageDto.getDescription());
				entity.setKeyword(cLanguageDto.getKeyword());
				entity.setLinkAlias(cLanguageDto.getLinkAlias());
				entity.setTextOnBanner(cLanguageDto.getTextOnBanner());
				entity.setKeywordDescription(cLanguageDto.getKeywordDescription());
				entity.setBannerDesktop(cLanguageDto.getBannerDesktop());
				entity.setBannerMobile(cLanguageDto.getBannerMobile());
				categorySubLanguageService.saveProductCategorySubLanguage(entity);
			}
		} catch (Exception e) {
			logger.error("createOrEditProductCategorySubLanguage: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	/**
	 * getProductCategorySubEditDto
	 *
	 * @param id
	 * @param action       true is edit, false is detail
	 * @param languageCode
	 * @return ProductCategorySubEditDto
	 * @author hand
	 */
	@Override
	public ProductCategorySubEditDto getProductCategorySub(String businessCode, String customerAlias, Long id,
			Locale locale) {
		ProductCategorySubEditDto resultDto = new ProductCategorySubEditDto();
		if (id == null) {
			resultDto.setEnabled(Boolean.TRUE);
			resultDto.setStatus(StepStatusEnum.DRAFT.getStepNo());
			resultDto.setCreateBy(UserProfileUtils.getUserNameLogin());
		} else {
			// set ProductCategorySub
			ProductCategorySub category = productCategorySubRepository.findOne(id);

			// dữ liệu ko tồn tại hoặc đã bị xóa
			if (category == null || category.getDeleteDate() != null) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
			}

			if (null != category) {
				resultDto.setId(category.getId());
				resultDto.setCustomerTypeId(category.getCustomerTypeId());
				resultDto.setCode(category.getCode());
				resultDto.setName(category.getName());
				resultDto.setNote(category.getNote());
				resultDto.setSort(category.getSort());
				resultDto.setBeforeId(category.getBeforeId());
				resultDto.setCategoryId(category.getCategId());
				resultDto.setEnabled(category.isEnabled());
				resultDto.setProcessId(category.getProcessId());
				resultDto.setStatus(category.getStatus());
				resultDto.setImageName(category.getImageName());
				resultDto.setPhysicalImg(category.getPhysicalImg());
				resultDto.setImageHoverName(category.getImageHoverName());
				resultDto.setPhysicalImgHover(category.getPhysicalImgHover());
				resultDto.setIconImg(category.getIconImg());
				resultDto.setPhysicalIcon(category.getPhysicalIcon());
				resultDto.setBannerDesktop(category.getBannerDesktop());
				resultDto.setBannerMobile(category.getBannerMobile());
				resultDto.setComment(category.getComment());
				resultDto.setCreateBy(category.getCreateBy());
				resultDto.setIsPriority(category.getPriority());
				resultDto.setUpdateDate(category.getUpdateDate());
				resultDto.setReferenceId(category.getId());
				resultDto.setComment(category.getComment());

			}
			List<ProductCategorySubLanguageDto> categoryLanguageList = getProductCategorySubLanguageList(id);
			resultDto.setCategoryLanguageList(categoryLanguageList);
		}

		resultDto.setReferenceType(ConstantHistoryApprove.APPROVE_PRODUCT_CATEGORY_SUB);

//		// get process
//		Long processId = resultDto.getProcessId();
//		if (processId == null) {
//			// First step
//			JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(businessCode, locale.toString());
//			processId = processDto.getProcessId();
//		}
//		// List button of step
//		List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId,
//				resultDto.getStatus(), locale.toString());
//		resultDto.setStepBtnList(stepButtonList);
//
//		String statusName = jprocessService.getStatusName(resultDto.getProcessId(), resultDto.getStatus(), locale);
//		resultDto.setStatusName(statusName);
//
//		String statusCode = jprocessService.getStatusCode(resultDto.getProcessId(), resultDto.getStatus(), locale);
//		resultDto.setStatusCode(statusCode);

		return resultDto;
	}

	/**
	 * get ProductCategorySubLanguageDto List
	 *
	 * @param categoryId
	 * @return ProductCategorySubLanguageDto list
	 * @author hand
	 */
	private List<ProductCategorySubLanguageDto> getProductCategorySubLanguageList(Long categoryId) {
		List<ProductCategorySubLanguageDto> resultList = new ArrayList<ProductCategorySubLanguageDto>();

		List<ProductCategorySubLanguage> categoryLanguages = categorySubLanguageService.findByCategorySubId(categoryId);

		// languageList
		List<Language> languageList = languageService.findAllActive();

		// loop language
		for (Language language : languageList) {
			// loop categoryLanguages
			for (ProductCategorySubLanguage entity : categoryLanguages) {
				// productCategorySubLanguageId is languageId
				if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
					ProductCategorySubLanguageDto categoryLanguageDto = new ProductCategorySubLanguageDto();
					categoryLanguageDto.setId(entity.getId());
					categoryLanguageDto.setTitle(entity.getTitle());
					categoryLanguageDto.setlanguageCode(entity.getLanguageCode());
					categoryLanguageDto.setDescription(entity.getDescription());
					categoryLanguageDto.setKeyword(entity.getKeyword());
					categoryLanguageDto.setLinkAlias(entity.getLinkAlias());
					categoryLanguageDto.setTextOnBanner(entity.getTextOnBanner());
					categoryLanguageDto.setKeywordDescription(entity.getKeywordDescription());
					categoryLanguageDto.setBannerDesktop(entity.getBannerDesktop());
					categoryLanguageDto.setBannerMobile(entity.getBannerMobile());
					resultList.add(categoryLanguageDto);
					break;
				}
			}
		}
		return resultList;
	}

	/**
	 * searchCategoryLanguage
	 *
	 * @param page
	 * @param searchDto ProductCategorySubSearchDto
	 * @return PageWrapper
	 * @author hand
	 */
	@Override
	public PageWrapper<ProductCategorySubLanguageSearchDto> searchCategoryLanguage(int page,
			ProductCategorySubSearchDto searchDto, Locale locale) {
		if (null == searchDto)
			searchDto = new ProductCategorySubSearchDto();
		// set status name
		searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));

		int sizeOfPage = searchDto.getPageSize() != null ? searchDto.getPageSize()
				: systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		// set SearchParm

		int count = productCategorySubRepository.countByProductCategorySubSearchDto(searchDto);
		if ((count % sizeOfPage == 0 && page > count / sizeOfPage)
				|| (count % sizeOfPage > 0 && page - 1 > count / sizeOfPage)) {
			page = 1;
		}

		PageWrapper<ProductCategorySubLanguageSearchDto> pageWrapper = new PageWrapper<ProductCategorySubLanguageSearchDto>(
				page, sizeOfPage);
		List<ProductCategorySubLanguageSearchDto> result = new ArrayList<ProductCategorySubLanguageSearchDto>();
		if (count > 0) {
			int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);

			result = productCategorySubRepository.findByProductCategorySubSearchDto(offsetSQL, sizeOfPage, searchDto);
		}

		pageWrapper.setDataAndCount(result, count);

		return pageWrapper;
	}

	/**
	 * delete ProductCategorySub by typeId
	 *
	 * @param typeId
	 * @author hand
	 */
	@Override
	@Transactional
	public void deleteByTypeId(Long typeId) {
		List<ProductCategorySub> categorieList = productCategorySubRepository.findByTypeId(typeId);

		for (ProductCategorySub category : categorieList) {
			// delete ProductCategorySub
			deleteProductCategorySub(category);
		}
	}

	/**
	 * deleteProductCategorySub
	 *
	 * @param category
	 * @param userName
	 * @author hand
	 */
	@Override
	@Transactional
	public void deleteProductCategorySub(ProductCategorySub category) {
		// userName
		String userName = UserProfileUtils.getUserNameLogin();

		// delete Product
		productService.deleteProductByCategoryId(category.getId());

		// delete ProductCategorySubLanguage
		categorySubLanguageService.deleteByCategoryId(new Date(), userName, category.getId());

		// delete ProductCategorySub
		category.setDeleteDate(new Date());
		category.setDeleteBy(userName);
		productCategorySubRepository.save(category);
	}

	/**
	 * find all ProductCategorySub not delete
	 *
	 * @param typeId
	 * @param languageCode
	 * @return List<ProductCategorySubDto>
	 * @author hand
	 */
	@Override
	public List<ProductCategorySubDto> findByTypeIdAndLanguageCode(Long typeId, String languageCode) {
		return productCategorySubRepository.findByTypeIdAndLanguageCode(typeId, languageCode);
	}

	/**
	 * find ProductCategorySub by typeId
	 *
	 * @return List<ProductCategorySub>
	 * @author hand
	 */
	@Override
	public List<ProductCategorySub> findByTypeId(Long typeId) {
		return productCategorySubRepository.findByTypeId(typeId);
	}

	/**
	 * find ProductCategorySub by code
	 *
	 * @param code
	 * @return
	 * @author hand
	 */
	@Override
	public ProductCategorySub findByCode(String code) {
		return productCategorySubRepository.findByCode(code);
	}

	/**
	 * find ProductCategorySub by id
	 *
	 * @param id
	 * @return ProductCategorySub
	 * @author hand
	 */
	@Override
	public ProductCategorySub findById(Long id) {
		return productCategorySubRepository.findOne(id);
	}

	/**
	 * get max sort by TypeId and categoryId
	 *
	 * @param typeId
	 * @param categoryId
	 * @return
	 * @author hand
	 */
	@Override
	public Long findMaxSortByTypeIdAndCategoryId(Long typeId, Long categoryId) {
		Long result = productCategorySubRepository.findMaxSortByTypeIdAndCategoryId(typeId, categoryId);
		return null == result ? 1 : result + 1;
	}

	/**
	 * get CategorySelect json string
	 *
	 * @param typeId
	 * @param languageCode
	 * @return String
	 * @author hand
	 */
	@Override
	public String getCategorySelectJson(Long typeId, String languageCode) {
		List<ProductCategoryDto> categoryList = productCategoryService.findByTypeIdAndLanguageCode(typeId, languageCode,
				CmsStepNoStatusConstant.STEP_APPROVED);

		List<SelectItem> categorySelect = new ArrayList<SelectItem>();
		for (ProductCategoryDto category : categoryList) {
			categorySelect.add(new SelectItem(category.getId().toString(),
					category.getCode() + ConstantCore.HYPHEN + category.getTitle()));
		}

		String categorySelectJson = CommonJsonUtil.convertObjectToJsonString(categorySelect);
		return categorySelectJson;
	}

	/**
	 * findByTypeIdAndCategoryId
	 *
	 * @param typeId
	 * @param categoryId
	 * @param languageCode
	 * @return List<ProductCategorySubDto>
	 * @author hand
	 */
	@Override
	public List<ProductCategorySubDto> findByTypeIdAndCategoryId(Long typeId, Long categoryId, String languageCode,
			Integer status) {
		return productCategorySubRepository.findByTypeIdAndCategoryId(typeId, categoryId, languageCode, status);
	}

	@Override
	public List<ProductCategorySubDto> findByTypeIdAndListCategoryId(Long typeId, List<Long> categoryListId,
			String languageCode, Integer status) {
		return productCategorySubRepository.findByTypeIdAndListCategoryId(typeId, categoryListId, languageCode, status);
	}

	@Override
	public List<ProductCategorySubLanguageSearchDto> findListForSort(String languageCode, Long customerId,
			Long categoryId) {
		List<ProductCategorySubLanguageSearchDto> a = productCategorySubRepository.findListForSort(languageCode,
				customerId, categoryId);
		return a;
	}

	@Override
	@Transactional
	public void updateSortAll(List<SortOrderDto> sortOderList) {
		for (SortOrderDto dto : sortOderList) {
			productCategorySubRepository.updateSortAll(dto);
		}

		Long itemId = 0L;
		for (SortOrderDto dto : sortOderList) {
			ProductCategorySub item = productCategorySubRepository.findOne(dto.getObjectId());
			item.setBeforeId(itemId);
			itemId = item.getId();
			productCategorySubRepository.save(item);
		}
	}

	/**
	 * getMaxCode
	 *
	 * @author nhutnn
	 * @return max code
	 */
	@Override
	public String getMaxCode() {
		return productCategorySubRepository.getMaxCode();
	}

	@Override
	public List<Select2Dto> findListForSort2(String languageCode, Long customerId, Long categoryId, Long typeId) {
		return productCategorySubRepository.findListForSort2(languageCode, customerId, categoryId, typeId);
	}

	private void updateSortAndBeforeId(ProductCategorySubEditDto editDto) {
		List<ProductCategorySub> lstProductType = productCategorySubRepository.findAllProductCategorySubForSort(
				editDto.getLanguageCode(), editDto.getCustomerTypeId(), editDto.getCategoryId(), editDto.getId());

		if (null != editDto.getBeforeId()) {
			// tim product truoc va gan sort cho productEdit
			int indexBefore = lstProductType.size();
			for (int i = 0, sz = indexBefore; i < sz; i++) {
				ProductCategorySub item = lstProductType.get(i);

				if (item.getId().equals(editDto.getBeforeId())) {
					indexBefore = i;
					editDto.setSort(item.getSort() + 1);
					break;
				}
			}

			// gan sort cho cac product sau produtEdit
			Long currentSort = editDto.getSort() + 1;
			for (int i = indexBefore + 1, sz = lstProductType.size(); i < sz; i++) {
				ProductCategorySub item = lstProductType.get(i);
				item.setSort(currentSort++);
				productCategorySubRepository.save(item);
			}
		} else {
			if (CollectionUtils.isNotEmpty(lstProductType)) {
				ProductCategorySub item = lstProductType.get(lstProductType.size() - 1);
				editDto.setSort(item.getSort() + 1);
				if (!item.getId().equals(editDto.getId())) {
					editDto.setBeforeId(item.getId());
				} else if (lstProductType.size() > 2) {
					editDto.setBeforeId(lstProductType.get(lstProductType.size() - 2).getId());
				} else {
					editDto.setBeforeId(0l);
				}
			} else {
				editDto.setSort(1l);
				editDto.setBeforeId(0l);
			}
		}
	}

	private void updateBeforeIdForProductAfter(ProductCategorySubEditDto editDto) {
		List<ProductCategorySub> lstProductType = productCategorySubRepository.findAllProductCategorySubForSort(
				editDto.getLanguageCode(), editDto.getCustomerTypeId(), editDto.getCategoryId(), editDto.getId());
		if (CollectionUtils.isEmpty(lstProductType)) {
			return;
		}

		ProductCategorySub item = lstProductType.get(0);
		if (item.getId().equals(editDto.getBeforeId()) && !item.getBeforeId().equals(0l)) {
			item.setBeforeId(0l);
			productCategorySubRepository.save(item);
		}

		for (int i = 0, sz = lstProductType.size(); i < sz - 1; i++) {
			item = lstProductType.get(i);
			if (item.getId().equals(editDto.getId())) {
				ProductCategorySub itemNext = lstProductType.get(i + 1);
				itemNext.setBeforeId(item.getId());

				productCategorySubRepository.save(item);
				break;
			}
		}
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
	public List<ProductCategorySubDto> getByTypeIdAndListCategoryIdForEdit(Long typeId, List<Long> categoryListId,
			String languageCode, Integer status) {
		return productCategorySubRepository.findByTypeIdAndListCategoryIdForEdit(typeId, categoryListId, languageCode,
				status);
	}

	@Override
	public ProductCategorySubLanguage findByAliasAndCustomerId(String linkAlias, String languageCode, Long customerId,
			Long typeId) {
		return productCategorySubRepository.findByAliasAndCustomerId(linkAlias, languageCode, customerId, typeId);
	}

	private void sendMail(ProductCategorySubEditDto editDto, HttpServletRequest request, Locale locale) {
		try {
			EmailCommonDto emailCommon = new EmailCommonDto();
			emailCommon.setActionName(msg.getMessage("email.template.product.category.sub", null, locale));
			emailCommon.setButtonAction(editDto.getButtonAction());
//			emailCommon.setButtonId(editDto.getButtonId());
			emailCommon.setComment(editDto.getComment());

			// Nội dung
			// tìm tên loại sản phẩm
			String businessCode = CmsCommonConstant.BUSINESS_PRODUCT_CD;
			if (editDto.getCustomerTypeId() == 10L) {
				businessCode = CmsCommonConstant.DN_BUSINESS_PRODUCT;
			}
			ProductCategoryEditDto productCategory = productCategoryService.getProductCategory(editDto.getCategoryId(),
					locale, businessCode);

			LinkedHashMap<String, String> content = new LinkedHashMap<>();
			content.put("Mã", editDto.getCode());
			content.put("Loại sản phẩm", productCategory.getCategoryLanguageList().get(0).getTitle());
			content.put("Danh mục sản phẩm", editDto.getCategoryLanguageList().get(0).getTitle());
			emailCommon.setContent(content);

			emailCommon.setCurrItem(editDto.getCurrItem());

			emailCommon.setId(editDto.getId());
			emailCommon.setOldStatus(editDto.getOldStatus());
			emailCommon.setProcessId(editDto.getProcessId());
			emailCommon.setReferenceType(editDto.getReferenceType());
			emailCommon.setStatus(editDto.getStatus());

			// Subject của email
			emailCommon.setSubject(msg.getMessage("subject.email.template.product.category.sub", null, locale));

			emailCommon.setUrl(CmsUtils.getBaseUrl(request) + "/" + editDto.getCustomerAlias()
					+ "/product-category/edit?id=" + editDto.getId());

//			emailUtil.sendMail(emailCommon, request, locale);
		} catch (Exception e) {
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	@Override
	public void exportExcel(ProductCategorySubSearchDto searchDto, HttpServletResponse res, Locale locale) {
		try {
			// set status name
			searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));

			/* change template */
			String templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_PRODUCT_CATEGORY_SUB;
			String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
					+ CmsCommonConstant.TYPE_EXCEL;
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);

			List<ExportProductCategorySubReportDto> lstData = productCategorySubRepository
					.exportExcelWithCondition(searchDto);
			List<ItemColsExcelDto> cols = new ArrayList<>();
			// start fill data to workbook
			ImportExcelUtil.setListColumnExcel(ExportProductCategorySubExportEnum.class, cols);
			ExportExcelUtil<ExportProductCategorySubReportDto> exportExcel = new ExportExcelUtil<>();
			// do export
			exportExcel.exportExcelWithXSSFNonPass(template, locale, lstData, ExportProductCategorySubReportDto.class,
					cols, datePattern, res, templateName);

		} catch (Exception e) {
			logger.error("Exception ", e);
		}
	}

	@Override
	public Long countByProductCategoryId(Long productCategoryId) {
		return productCategorySubRepository.countByProductCategoryId(productCategoryId);
	}

	@Override
	public List<ProductCategorySubDto> getListProductCategorySubDto(Long customerId, Long productCategoryId,
			Locale locale, Integer status) {
		return productCategorySubRepository.getListProductCategorySubDto(customerId, productCategoryId,
				locale.getLanguage(), status);
	}

	@Override
	public int countDependencies(Long categorySubId, List<Long> lstStatus) {
		return productCategorySubRepository.countDependencies(categorySubId, lstStatus);
	}

	@Override
	public List<Map<String, String>> listDependencies(Long categorySubId, List<Long> lstStatus) {
		return productCategorySubRepository.listDependencies(categorySubId, lstStatus);
	}
	
    @Override
    public ProductCategorySubEditDto getEdit(Long id, String customerAlias, Locale locale) {
        String businessCode = CmsCommonConstant.DN_BUSINESS_PRODUCT;
        if ("personal".equals(customerAlias)) {
            businessCode = CmsCommonConstant.BUSINESS_PRODUCT_CD;
        }

        logger.error("NEED CHANGE BUSINESS CODE");
        // TODO remove "BUSINESS_BANNER"
        businessCode = "BUSINESS_BANNER"; // remove this line, only for test
        logger.error("NEED CHANGE BUSINESS CODE");

        return getProductCategorySub(businessCode, customerAlias, id, locale);
    }
}