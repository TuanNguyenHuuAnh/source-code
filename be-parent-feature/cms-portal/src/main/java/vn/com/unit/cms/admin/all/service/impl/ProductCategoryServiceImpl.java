/*******************************************************************************
 * Class        ：ProductCategoryServiceImpl
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
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.dto.EmailCommonDto;
import vn.com.unit.cms.admin.all.dto.ExportProductCategoryReportDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryLanguageDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySearchDto;
import vn.com.unit.cms.admin.all.entity.ProductCategory;
import vn.com.unit.cms.admin.all.entity.ProductCategoryLanguage;
import vn.com.unit.cms.admin.all.enumdef.ExportProductCategoryExportEnum;
import vn.com.unit.cms.admin.all.enumdef.StepActionEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.jcanary.utils.APIUtils;
import vn.com.unit.cms.admin.all.repository.ProductCategoryRepository;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.ProductCategoryLanguageService;
import vn.com.unit.cms.admin.all.service.ProductCategoryService;
import vn.com.unit.cms.admin.all.service.ProductService;
import vn.com.unit.cms.admin.all.util.HDBankUtil;
//import vn.com.unit.core.entity.Language;
//import vn.com.unit.cms.admin.enumdef.StepActionEnum;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
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
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

/**
 * ProductCategoryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProductCategoryServiceImpl
        extends DocumentWorkflowCommonServiceImpl<ProductCategoryEditDto, ProductCategoryEditDto>
        implements ProductCategoryService {
	@Autowired
	private ProductCategoryLanguageService categoryLanguageService;

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private LanguageService languageService;

//	@Autowired
//	HistoryApproveService historyApproveService;

//	@Autowired
//	ConstantDisplayService constDispService;

	@Autowired
	private CmsCommonService commonService;

	@Autowired
	private CmsFileService fileService;

	private static final String PREFIX_CODE = "PROD.T.";

//	@Autowired
//	JProcessService jprocessService;

	@Autowired
	EmailService emailService;

	@Autowired
	AccountService accountService;

	@Autowired
	MessageSource msg;

	@Autowired
	ServletContext servletContext;
	
//	@Autowired
//	EmailUtil emailUtil;

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

	/**
	 * add Or Edit ProductCategory
	 *
	 * @param editDto
	 * @param locale
	 * @param request
	 * @author hand
	 * @throws IOException
	 */
	@Override
	@Transactional
	public boolean doEdit(ProductCategoryEditDto editDto, Locale locale, HttpServletRequest request)
			throws IOException {
	    
	    boolean result = true;
	    
		// setLanguageCode
		editDto.setLanguageCode(locale.toString());

		// user name login
//		UserProfile userProfile = UserProfileUtils.getUserProfile();

		// do save product category
		createOrEditProductCategory(editDto, UserProfileUtils.getUserNameLogin(), locale, request);
		
		// do save product category language
		createOrEditCategoryLanguage(editDto, UserProfileUtils.getUserNameLogin(), locale);

		// move file upload
		CmsUtils.moveTempSubFolderToUpload(
				Paths.get(AdminConstant.PRODUCT_TYPE_FOLDER, AdminConstant.EDITOR_FOLDER, editDto.getRequestToken())
						.toString());

		// if action process
		if (!StringUtils.equals(editDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
			// update history approve
		    updateHistoryApprove(editDto, locale);

			// send mail
	        sendMail(editDto, request, locale);
	        
	        // clear cache api /commons
            APIUtils.callApiGet(AdminUrlConst.URL_CACHES_COMMONS);
            
            // clear cache api /personal
            APIUtils.callApiGet(AdminUrlConst.URL_CACHES_PERSONAL);
            
            // clear cache api /corporate
            APIUtils.callApiGet(AdminUrlConst.URL_CACHES_CORPORATE);
            
            APIUtils.callApiGet(AdminUrlConst.URL_CACHES_HOME);
		}

		return result;
	}

	/**
	 * updateHistoryApprove
	 * 
	 * @param editDto
	 */
	private void updateHistoryApprove(ProductCategoryEditDto editDto, Locale locale) {
		try {
			// insert comment
			HistoryApproveDto historyApproveDto = new HistoryApproveDto();
			historyApproveDto.setApprover(UserProfileUtils.getFullName());
			historyApproveDto.setComment(editDto.getComment());
			historyApproveDto.setProcessId(editDto.getProcessId());
			historyApproveDto.setProcessStep(editDto.getStatus().longValue());
			historyApproveDto.setReferenceId(editDto.getId());
			historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_PRODUCT_CATEGORY);
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
	 * create or update ProductCategory
	 *
	 * @param editDto
	 * @param action
	 *            true is saveDraft, false is sendRequest
	 * @author hand
	 * @throws IOException
	 */
	private void createOrEditProductCategory(ProductCategoryEditDto editDto, String usernameAction, Locale locale,
			HttpServletRequest request) {

			ProductCategory entity = new ProductCategory();

			if (null != editDto.getId()) {
				entity = productCategoryRepository.findOne(editDto.getId());
				// dữ liệu ko tồn tại hoặc đã bị xóa
				if (null == entity || entity.getDeleteDate() != null) {
					throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
				}
				
				if (entity.getUpdateDate() != null && !entity.getUpdateDate().equals(editDto.getUpdateDate())){
				    throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
				}
				
				List<Long> lstStatus = HDBankUtil.getListStatusForDependency();
				int countDependencies = countDependencies(entity.getId(), lstStatus);
				
				if(countDependencies > 0){
					throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
				}

				entity.setUpdateDate(new Date());
				entity.setUpdateBy(usernameAction);

			} else {
				entity.setCreateDate(new Date());
				entity.setCreateBy(usernameAction);
				// code generation
				entity.setCode(CommonUtil.getNextCode(PREFIX_CODE,
						commonService.getMaxCode("M_PRODUCT_CATEGORY", PREFIX_CODE)));
			}
			
		try {
			entity.setCustomerTypeId(editDto.getCustomerTypeId());
			entity.setName(editDto.getName());
			entity.setNote(editDto.getNote());
			entity.setSort(editDto.getSort());
			entity.setEnabled(editDto.isEnabled());
			entity.setOwnerId(UserProfileUtils.getAccountId());
			entity.setOwnerBranchId(UserProfileUtils.getBranchId());
			entity.setOwnerSectionId(UserProfileUtils.getDepartmentId());
			entity.setStatus(editDto.getStatus());
			entity.setProcessId(editDto.getProcessId());
			entity.setBannerDesktop(editDto.getBannerDesktop());
			entity.setBannerMobile(editDto.getBannerMobile());
			entity.setPromotion(editDto.isPromotion());
			entity.setLinkAlias(editDto.getLinkAlias());
			entity.setComment(editDto.getComment());

			// set sort and beforeId for dto
			updateSortAndBeforeId(editDto);
			entity.setBeforeId(editDto.getBeforeId());
			entity.setSort(editDto.getSort());

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

//			// if action process
//			if (!StringUtils.equals(editDto.getButtonId(), StepActionEnum.SAVE.getCode())) {
//				if (editDto.getProcessId() == null) {
//					// First step
//					JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(editDto.getBusinessCode(),
//							locale.toString());
//					// set process id
//					editDto.setProcessId(processDto.getProcessId());
//				}
//
//				// current step
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

			// save entity
			productCategoryRepository.save(entity);

			if (entity.getId() != null) {
				editDto.setId(entity.getId());
				editDto.setCode(entity.getCode());
			}

			// update before id for product type after product edited
			updateBeforeIdForProductAfter(editDto);

		} catch (Exception e) {
			logger.error("createOrEditProductCategory: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	/**
	 * createOrEditCategoryLanguage
	 *
	 * @param editDto
	 * @author hand
	 */
	private void createOrEditCategoryLanguage(ProductCategoryEditDto editDto, String userName, Locale locale) {
		try {
			for (ProductCategoryLanguageDto cLanguageDto : editDto.getCategoryLanguageList()) {

				ProductCategoryLanguage entity = new ProductCategoryLanguage();

				if (null != cLanguageDto.getId()) {
					entity = categoryLanguageService.findByid(cLanguageDto.getId());
					if (null == entity) {
						throw new BusinessException("Not found ProductCategoryLanguag with id=" + cLanguageDto.getId());
					}
					entity.setUpdateDate(new Date());
					entity.setUpdateBy(userName);
				} else {
					entity.setCreateDate(new Date());
					entity.setCreateBy(userName);
				}

				entity.setProductCategoryId(editDto.getId());
				entity.setTitle(cLanguageDto.getTitle());
				entity.setLanguageCode(cLanguageDto.getLanguageCode());
				entity.setDescription(cLanguageDto.getDescription());
				entity.setKeyword(cLanguageDto.getKeyword());
				entity.setLinkAlias(cLanguageDto.getLinkAlias());
				entity.setTextOnBanner(cLanguageDto.getTextOnBanner());
				entity.setKeywordDescription(cLanguageDto.getKeywordDescription());
				entity.setBannerDesktop(cLanguageDto.getBannerDesktop());
				entity.setBannerMobile(cLanguageDto.getBannerMobile());
				categoryLanguageService.saveProductCategoryLanguage(entity);
			}

		} catch (Exception e) {
			logger.error("createOrEditCategoryLanguage: " + e.getMessage());
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	/**
	 * getProductCategoryEditDto
	 *
	 * @param id
	 * @param locale
	 * @param businessCode
	 * @return ProductCategoryEditDto
	 * @author hand
	 */
	@Override
	public ProductCategoryEditDto getProductCategory(Long id, Locale locale, String businessCode) {
		ProductCategoryEditDto resultDto = new ProductCategoryEditDto();

		if (id == null) {
			resultDto.setEnabled(Boolean.TRUE);
			resultDto.setStatus(StepStatusEnum.DRAFT.getStepNo());
			resultDto.setCreateBy(UserProfileUtils.getUserNameLogin());
		} else {
			// set ProductCategory
			ProductCategory category = productCategoryRepository.findOne(id);
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
				resultDto.setEnabled(category.isEnabled());
				resultDto.setProcessId(category.getProcessId());
				resultDto.setStatus(category.getStatus());
				resultDto.setReferenceType(ConstantHistoryApprove.APPROVE_PRODUCT_CATEGORY);
				resultDto.setReferenceId(category.getId());
				resultDto.setImageName(category.getImageName());
				resultDto.setPhysicalImg(category.getPhysicalImg());
				resultDto.setImageHoverName(category.getImageHoverName());
				resultDto.setPhysicalImgHover(category.getPhysicalImgHover());
				resultDto.setIconImg(category.getIconImg());
				resultDto.setPhysicalIcon(category.getPhysicalIcon());
				resultDto.setBannerDesktop(category.getBannerDesktop());
				resultDto.setBannerMobile(category.getBannerMobile());
				resultDto.setPromotion(category.isPromotion());
				resultDto.setLinkAlias(category.getLinkAlias());
				resultDto.setCreateBy(category.getCreateBy());
				resultDto.setUpdateDate(category.getUpdateDate());
				resultDto.setComment(category.getComment());
			}

			List<ProductCategoryLanguageDto> categoryLanguageList = getProductCategoryLanguageList(id);
			resultDto.setCategoryLanguageList(categoryLanguageList);
		}

//		Long processId = resultDto.getProcessId();
//		if (processId == null) {
//			// First step
//			JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(businessCode, locale.toString());
//			processId = processDto.getProcessId();
//
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
	 * get ProductCategoryLanguageDto List
	 *
	 * @param categoryId
	 * @return ProductCategoryLanguageDto list
	 * @author hand
	 */
	private List<ProductCategoryLanguageDto> getProductCategoryLanguageList(Long categoryId) {
		List<ProductCategoryLanguageDto> resultList = new ArrayList<ProductCategoryLanguageDto>();

		List<ProductCategoryLanguage> categoryLanguages = categoryLanguageService.findByCategoryId(categoryId);

		// languageList
		List<Language> languageList = languageService.findAllActive();

		// loop language
		for (Language language : languageList) {
			// loop categoryLanguages
			for (ProductCategoryLanguage entity : categoryLanguages) {
				// productCategoryLanguageId is languageId
				if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
					ProductCategoryLanguageDto categoryLanguageDto = new ProductCategoryLanguageDto();
					categoryLanguageDto.setId(entity.getId());
					categoryLanguageDto.setTitle(entity.getTitle());
					categoryLanguageDto.setLanguageCode(entity.getLanguageCode());
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
	 * @param searchDto
	 *            ProductCategorySearchDto
	 * @return PageWrapper
	 * @author hand
	 */
	@Override
	public PageWrapper<ProductCategoryLanguageSearchDto> doSearch(int page, ProductCategorySearchDto searchDto,
			Locale locale) {

		if (null == searchDto)
			searchDto = new ProductCategorySearchDto();
		// set status name
		searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));

		int sizeOfPage = searchDto.getPageSize() != null ? searchDto.getPageSize()
				: systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		int count = productCategoryRepository.countByProductCategorySearchDto(searchDto);
		if ((count % sizeOfPage == 0 && page > count / sizeOfPage)
				|| (count % sizeOfPage > 0 && page - 1 > count / sizeOfPage)) {
			page = 1;
		}

		PageWrapper<ProductCategoryLanguageSearchDto> pageWrapper = new PageWrapper<ProductCategoryLanguageSearchDto>(
				page, sizeOfPage);
		List<ProductCategoryLanguageSearchDto> result = new ArrayList<ProductCategoryLanguageSearchDto>();
		if (count > 0) {
			int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);

			result = productCategoryRepository.findByProductCategorySearchDto(offsetSQL, sizeOfPage, searchDto);
		}

		pageWrapper.setDataAndCount(result, count);

		return pageWrapper;
	}

	/**
	 * delete ProductCategory by typeId
	 *
	 * @param typeId
	 * @author hand
	 */
	@Override
	@Transactional
	public void deleteByTypeId(Long typeId) {
		List<ProductCategory> categorieList = productCategoryRepository.findByTypeId(typeId);

		for (ProductCategory category : categorieList) {
			// delete ProductCategory
			deleteProductCategory(category);
		}
	}

	/**
	 * deleteProductCategory
	 *
	 * @param category
	 * @param userName
	 * @author hand
	 */
	@Override
	@Transactional
	public void deleteProductCategory(ProductCategory category) {
		// userName
		String userName = UserProfileUtils.getUserNameLogin();

		// delete Product
		productService.deleteProductByCategoryId(category.getId());

		// delete ProductCategoryLanguage
		categoryLanguageService.deleteByCategoryId(new Date(), userName, category.getId());

		// delete ProductCategory
		category.setDeleteDate(new Date());
		category.setDeleteBy(userName);
		productCategoryRepository.save(category);
	}

	/**
	 * find all ProductCategory not delete
	 *
	 * @param typeId
	 * @param languageCode
	 * @return List<ProductCategoryDto>
	 * @author hand
	 */
	@Override
	public List<ProductCategoryDto> findByTypeIdAndLanguageCode(Long typeId, String languageCode,Integer status) {
		return productCategoryRepository.findByTypeIdAndLanguageCode(typeId, languageCode,status);
	}

	/**
	 * find ProductCategory by typeId
	 *
	 * @return List<ProductCategory>
	 * @author hand
	 */
	@Override
	public List<ProductCategory> findByTypeId(Long typeId) {
		return productCategoryRepository.findByTypeId(typeId);
	}

	/**
	 * find ProductCategory by code
	 *
	 * @param code
	 * @return
	 * @author hand
	 */
	@Override
	public ProductCategory findByCode(String code) {
		return productCategoryRepository.findByCode(code);
	}

	/**
	 * find ProductCategory by id
	 *
	 * @param id
	 * @return ProductCategory
	 * @author hand
	 */
	@Override
	public ProductCategory findById(Long id) {
		return productCategoryRepository.findOne(id);
	}

	/**
	 * get max sort by TypeId
	 *
	 * @return
	 * @author hand
	 */
	@Override
	public Long findMaxSortByTypeId(Long typeId) {
		Long result = productCategoryRepository.findMaxSortByTypeId(typeId);
		return null == result ? 1 : result + 1;
	}

	/**
	 * findListForSort
	 *
	 * @param languageCode
	 * @param customerId
	 * @return List<ProductCategoryLanguageSearchDto>
	 * @author hand
	 */
	@Override
	public List<ProductCategoryLanguageSearchDto> findListForSort(String languageCode, Long customerId) {
		return productCategoryRepository.findListForSort(languageCode, customerId);
	}

	/**
	 * updateSortAll
	 *
	 * @param sortOderList
	 * @author hand
	 */
	@Override
	@Transactional
	public void updateSortAll(List<SortOrderDto> sortOderList) {
		for (SortOrderDto dto : sortOderList) {
			productCategoryRepository.updateSortAll(dto);
		}

		Long itemId = 0L;
		for (SortOrderDto dto : sortOderList) {
			ProductCategory item = productCategoryRepository.findOne(dto.getObjectId());
			item.setBeforeId(itemId);
			itemId = item.getId();
			productCategoryRepository.save(item);
		}

	}

	/**
	 * find ProductCategory by alias and customerId
	 * 
	 * @param linkAlias
	 * @param customerId
	 * @return ProductCategory
	 */
	@Override
	public ProductCategoryLanguage findByAliasAndCustomerId(String linkAlias, String languageCode, Long customerId) {
		return productCategoryRepository.findByAliasAndCustomerId(linkAlias, languageCode, customerId);
	}

	/**
	 * getMaxCode
	 *
	 * @author nhutnn
	 * @return max code
	 */
	@Override
	public String getMaxCode() {
		return productCategoryRepository.getMaxCode();
	}

	@Override
	public List<ProductCategoryEditDto> findAllProduct(String languageCode, ProductCategoryEditDto categoryEdit) {
		return productCategoryRepository.findAllProduct(languageCode, categoryEdit);
	}

	@Override
	public List<Select2Dto> getProductByterm(String term, String languageCode, Long customerId) {
		return productCategoryRepository.getProductByterm(term, languageCode, customerId);
	}

	private void updateSortAndBeforeId(ProductCategoryEditDto editDto) {
		List<ProductCategory> lstProductType = productCategoryRepository
				.findAllProductTypeForSort(editDto.getLanguageCode(), editDto.getCustomerTypeId(), editDto.getId());

		if (null != editDto.getBeforeId()) {
			// tim product truoc va gan sort cho productEdit
			int indexBefore = lstProductType.size();
			for (int i = 0, sz = indexBefore; i < sz; i++) {
				ProductCategory item = lstProductType.get(i);

				if (item.getId().equals(editDto.getBeforeId())) {
					indexBefore = i;
					editDto.setSort(item.getSort() + 1);
					break;
				}
			}

			// gan sort cho cac product sau produtEdit
			Long currentSort = editDto.getSort() + 1;
			for (int i = indexBefore + 1, sz = lstProductType.size(); i < sz; i++) {
				ProductCategory item = lstProductType.get(i);
				item.setSort(currentSort++);
				productCategoryRepository.save(item);
			}
		} else {
			if (CollectionUtils.isNotEmpty(lstProductType)) {
				ProductCategory item = lstProductType.get(lstProductType.size() - 1);
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

	private void updateBeforeIdForProductAfter(ProductCategoryEditDto editDto) {
		List<ProductCategory> lstProductType = productCategoryRepository
				.findAllProductTypeForSort(editDto.getLanguageCode(), editDto.getCustomerTypeId(), editDto.getId());
		if (CollectionUtils.isEmpty(lstProductType)) {
			return;
		}

		ProductCategory item = lstProductType.get(0);
		if (item.getId().equals(editDto.getBeforeId()) && !item.getBeforeId().equals(0l)) {
			item.setBeforeId(0l);
			productCategoryRepository.save(item);
		}

		for (int i = 0, sz = lstProductType.size(); i < sz - 1; i++) {
			item = lstProductType.get(i);
			if (item.getId().equals(editDto.getId())) {
				ProductCategory itemNext = lstProductType.get(i + 1);
				itemNext.setBeforeId(item.getId());

				productCategoryRepository.save(item);
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

	private void sendMail(ProductCategoryEditDto editDto, HttpServletRequest request, Locale locale){
        try {
            EmailCommonDto emailCommon = new EmailCommonDto();
            emailCommon.setActionName(msg.getMessage("email.template.product.category", null, locale));
            emailCommon.setButtonAction(editDto.getButtonAction());
            emailCommon.setButtonId(editDto.getButtonId().toString());
            emailCommon.setComment(editDto.getComment());

            // Nội dung
            LinkedHashMap<String, String> content = new LinkedHashMap<>();
            content.put("Mã", editDto.getCode());
            content.put("Loại sản phẩm", editDto.getCategoryLanguageList().get(0).getTitle());
            emailCommon.setContent(content);

            emailCommon.setCurrItem(editDto.getCurrItem());

            emailCommon.setId(editDto.getId());
            emailCommon.setOldStatus(editDto.getOldStatus());
            emailCommon.setProcessId(editDto.getProcessId());
            emailCommon.setReferenceType(editDto.getReferenceType());
            emailCommon.setStatus(editDto.getStatus());

            // Subject của email
            emailCommon.setSubject(msg.getMessage("subject.email.template.product.category", null, locale));

            emailCommon.setUrl(CmsUtils.getBaseUrl(request) + "/" + editDto.getCustomerAlias() + "/product-type/edit?id="
                    + editDto.getId());

//            emailUtil.sendMail(emailCommon, request, locale);
        } catch (Exception e) {
            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
        }
	}

	@Override
	public void exportExcel(ProductCategorySearchDto searchDto, HttpServletResponse res, Locale locale) {
		try {
			// set status name
			searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));

			/* change template */
			String templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_PRODUCT_CATEGORY;
			String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
					+ CmsCommonConstant.TYPE_EXCEL;
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);

			List<ExportProductCategoryReportDto> lstData = productCategoryRepository
					.exportExcelWithCondition(searchDto);
			List<ItemColsExcelDto> cols = new ArrayList<>();
			// start fill data to workbook
			ImportExcelUtil.setListColumnExcel(ExportProductCategoryExportEnum.class, cols);
			ExportExcelUtil<ExportProductCategoryReportDto> exportExcel = new ExportExcelUtil<>();
			// do export
			exportExcel.exportExcelWithXSSFNonPass(template, locale, lstData, ExportProductCategoryReportDto.class,
					cols, datePattern, res, templateName);

		} catch (Exception e) {
			logger.error("Exception ", e);
		}
	}

	@Override
	public List<ProductCategoryDto> findByTypeIdAndProductCateIdAndLanguageCode(Long typeId,Long productTypeId,String languageCode,
			Integer status) {
		return productCategoryRepository.findByTypeIdAndProductCateIdAndLanguageCode(typeId,productTypeId, languageCode,status);
	}

	@Override
	public int countDependencies(Long categoryId, List<Long> lstStatus) {
		return productCategoryRepository.countDependencies(categoryId, lstStatus);
	}
	
	@Override
	public List<Map <String, String>> listDependencies(Long categoryId, List<Long> lstStatus) {
		return productCategoryRepository.listDependencies(categoryId, lstStatus);
	}
	
	@Override
	public List<ProductCategoryLanguageSearchDto> getProductCategoryList(String languageCode, Long customerId) {
		return productCategoryRepository.findProductCategoryList(languageCode, customerId);
	}

    @Override
    public ProductCategoryEditDto getEdit(Long id, String customerAlias, Locale locale) {
        String businessCode = CmsCommonConstant.DN_BUSINESS_PRODUCT;
        if ("personal".equals(customerAlias)) {
            businessCode = CmsCommonConstant.BUSINESS_PRODUCT_CD;
        }
        return getProductCategory(id, locale, businessCode);
    }
}
