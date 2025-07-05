package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryLanguageDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchResultDto;
import vn.com.unit.cms.admin.all.dto.ParentPathDto;
import vn.com.unit.cms.admin.all.dto.ProductEditDto;
import vn.com.unit.cms.admin.all.dto.ProductManagementEditDto;
import vn.com.unit.cms.admin.all.entity.DocumentCategory;
import vn.com.unit.cms.admin.all.entity.DocumentCategoryLanguage;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.DocumentCategoryLanguageRepository;
import vn.com.unit.cms.admin.all.repository.DocumentCategoryRepository;
import vn.com.unit.cms.admin.all.repository.ProductManagementRepository;
import vn.com.unit.cms.admin.all.service.DocumentCategoryService;
import vn.com.unit.cms.admin.all.service.DocumentService;
import vn.com.unit.cms.admin.all.service.ProductManagementService;
import vn.com.unit.cms.admin.all.service.ParentPathService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.module.product.dto.ProductLanguageSearchDto;
import vn.com.unit.cms.core.module.product.dto.ProductSearchDto;
import vn.com.unit.cms.core.module.product.entity.Product;
import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.tree.TreeBuilder;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProductManagementServiceImpl implements ProductManagementService {

	private static final String MENU_ROOT = "ROOT";

	private static final String PATH_PARENT_TYPE = "M_DOCUMENT_CATEGORY";

	@Autowired
	private ProductManagementRepository productManagementRepository;

	@Autowired
	private DocumentCategoryLanguageRepository documentCategoryLanguageRepository;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private CmsCommonService comService;

	@Autowired
	private Select2DataService select2DataService;

	@Autowired
	private JcaDatatableConfigService jcaDatatableConfigService;

	@Autowired
	private CommonSearchFilterUtils commonSearchFilterUtils;

	@Autowired
	private MessageSource msg;

	@Autowired
	private ParentPathService parentPathService;

	@Autowired
	private CmsCommonService cmsCommonService;

	@Override
	public List<ProductLanguageSearchDto> getListByCondition(ProductSearchDto searchDto,
			Pageable pageable) {
		searchDto.setCompanyId(UserProfileUtils.getCompanyId());
		return productManagementRepository.findListSearch(searchDto, pageable).getContent();
	}

	@Override
	public int countListByCondition(ProductSearchDto searchDto) {
		searchDto.setCompanyId(UserProfileUtils.getCompanyId());
		int count =productManagementRepository.countList(searchDto);
//		int count = 4;
		return count ;
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	@Override
	public CmsCommonService getCmsCommonService() {
		return comService;
	}

	@Override
	public JcaDatatableConfigService getJcaDatatableConfigService() {
		return jcaDatatableConfigService;
	}

	@Override
	public List<CommonSearchFilterDto> initListSearchFilter(ProductSearchDto searchDto, Locale locale) {
		List<CommonSearchFilterDto> list = ProductManagementService.super.initListSearchFilter(searchDto, locale);
		List<CommonSearchFilterDto> rs = new ArrayList<>();
//		List<Select2Dto> listDataType = select2DataService.getConstantData("DOCUMENT", "CATEGORY_DOCUMENT", locale.toString());

		if (CollectionUtils.isNotEmpty(list)) {
			for (CommonSearchFilterDto filter : list) {
//				if ("categoryType".equals(filter.getField())) {
//					filter = commonSearchFilterUtils.createSelectCommonSearchFilterDto(filter.getField(),
//							filter.getFieldName(), searchDto.getCategoryType(), listDataType);
//					rs.add(filter);
//				}else {
					rs.add(filter);
//				}
			}
		}
		return rs;
	}

	@Override
	public ProductManagementEditDto getEditDtoById(Long id, Locale locale) {
		ProductManagementEditDto resultDto = new ProductManagementEditDto();

		if (id == null) {
			resultDto.setCreateBy(UserProfileUtils.getUserNameLogin());
//			resultDto.setEnabled(true);
			Long customerId = 9L;
//			resultDto.setCustomerTypeId(customerId);
//			resultDto.setIndexLangActive(0);
			return resultDto;
		}

		// set FaqsCategory
		Product entity = productManagementRepository.findOne(id);

		// dữ liệu ko tồn tại hoặc đã bị xóa
		if (entity == null || entity.getDeleteDate() != null) {
			throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
		}

		if (null != entity) {
			resultDto.setId(entity.getId());
			resultDto.setCode(entity.getCode());
			resultDto.setProductName(entity.getProductName());
			resultDto.setProductType(entity.getProductType());
			resultDto.setUnitPrice(entity.getUnitPrice());
			resultDto.setEffectiveDate(entity.getEffectiveDate());
			resultDto.setExpiredDate(entity.getExpiredDate());
			resultDto.setProductImg(entity.getProductImg());
			resultDto.setProductPhysicalImg(entity.getProductPhysicalImg());
//			resultDto.setNote(entity.getNote());
//			resultDto.setSort(entity.getSort());
//			resultDto.setEnabled(entity.isEnabled());
//			resultDto.setCategoryType(entity.getCategoryType());

//			resultDto.setCustomerTypeId(entity.getCustomerTypeId());
//			resultDto.setItemFunctionCode(entity.getItemFunctionCode());
//			resultDto.setParentId(entity.getParentId());
//			resultDto.setForCandidate(entity.getForCandidate());
//			resultDto.setCreateBy(entity.getCreateBy());
		}

//		List<DocumentCategoryLanguageDto> languageList = getLanguageList(id);
//		resultDto.setLanguageList(languageList);

		return resultDto;
	}

//	private List<DocumentCategoryLanguageDto> getLanguageList(Long categoryId) {
//		List<DocumentCategoryLanguageDto> resultList = new ArrayList<DocumentCategoryLanguageDto>();
//
//		List<DocumentCategoryLanguage> langList = documentCategoryLanguageRepository.findByCategoryId(categoryId);
//
//		// loop categoryLanguages
//		for (DocumentCategoryLanguage entity : langList) {
//			// faqsCategoryLanguageId is languageId
//			DocumentCategoryLanguageDto categoryLanguageDto = new DocumentCategoryLanguageDto();
//			categoryLanguageDto.setId(entity.getId());
//			categoryLanguageDto.setTitle(entity.getTitle());
//			categoryLanguageDto.setLanguageCode(entity.getLanguageCode());
//			categoryLanguageDto.setKeywordsSeo(entity.getKeywordsSeo());
//			categoryLanguageDto.setKeywords(entity.getKeywords());
//			categoryLanguageDto.setKeywordsDesc(entity.getKeywordsDesc());
//			resultList.add(categoryLanguageDto);
//			break;
//		}
//		return resultList;
//	}

	@Override
	public void saveOrUpdate(ProductManagementEditDto editDto, Locale locale) throws Exception {
		String username = UserProfileUtils.getUserNameLogin();

		createOrEditData(editDto, username);
//		createOrEditDataLanguage(editDto, username);

		parentPathService.deleteMenuPathByDescendantId(editDto.getId(), PATH_PARENT_TYPE);
//		saveMenuPath(editDto, MENU_ROOT);
	}

	private void createOrEditData(ProductManagementEditDto editDto, String userName) {
		Product entity = new Product();

		if (null != editDto.getId()) {
			entity = productManagementRepository.findOne(editDto.getId());

			if (null == entity) {
				throw new BusinessException("Not found Faqs Category with id=" + editDto.getId());
			}
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(userName);
//			Long parentId = entity.getParentId();
//			Product parent = documentCategoryRepository.findOne(parentId);

//			if (parent != null && parent.getParentId().equals(editDto.getId())) {
//				parent.setParentId(parentId);
//				documentCategoryRepository.update(parent);
//			}

		} else {
			entity.setCreateDate(new Date());
			entity.setCreateBy(userName);

//			entity.setCode(CommonUtil.getNextBannerCode(editDto.getCode().toUpperCase(),
//					cmsCommonService.getMaxCodeYYMM("M_DOCUMENT_CATEGORY", editDto.getCode().toUpperCase())));
		}

//		entity.setCode(editDto.getCode());
//		entity.setCategoryType(editDto.getCategoryType());
//		entity.setNote(editDto.getNote());
//		entity.setSort(editDto.getSort());
//		entity.setEnabled(editDto.isEnabled());
//		entity.setCustomerTypeId(editDto.getCustomerTypeId());
//		entity.setItemFunctionCode(editDto.getItemFunctionCode());
//		entity.setParentId(editDto.getParentId());
//		entity.setForCandidate(editDto.getForCandidate());
//		documentCategoryRepository.save(entity);
		entity.setCode(editDto.getCode());
		entity.setProductName(editDto.getProductName());
		entity.setProductType(editDto.getProductType());
		entity.setUnitPrice(editDto.getUnitPrice());
		entity.setEffectiveDate(editDto.getEffectiveDate());
		entity.setExpiredDate(editDto.getExpiredDate());
		entity.setProductImg(editDto.getProductImg());
		entity.setProductPhysicalImg(editDto.getProductPhysicalImg());
		productManagementRepository.save(entity);
		editDto.setId(entity.getId());
	}

	/**
	 * createOrEditDataLanguage
	 *
	 * @param editDto
	 * @author TaiTM
	 */
//	private void createOrEditDataLanguage(ProductManagementEditDto editDto, String userName) {
//		for (DocumentCategoryLanguageDto cLanguageDto : editDto.getLanguageList()) {
//			DocumentCategoryLanguage entity = new DocumentCategoryLanguage();
//			DocumentCategoryLanguage entityEn = new DocumentCategoryLanguage();
//			if (null != cLanguageDto.getId()) {
//				entity = documentCategoryLanguageRepository.findLanguage(editDto.getId(), "en");
//				entityEn = documentCategoryLanguageRepository.findLanguage(editDto.getId(), "vi");
//				if (null == entity || null == entityEn) {
//					throw new BusinessException("Not found data language with id=" + cLanguageDto.getId());
//				}
//				entity.setUpdateDate(new Date());
//				entity.setUpdateBy(userName);
//				entityEn.setUpdateDate(new Date());
//				entityEn.setUpdateBy(userName);
//			} else {
//				entity.setCreateDate(new Date());
//				entity.setCreateBy(userName);
//				entityEn.setCreateDate(new Date());
//				entityEn.setCreateBy(userName);
//			}
//			entity.setCategoryId(editDto.getId());
//			entity.setTitle(cLanguageDto.getTitle());
//			entity.setLanguageCode("EN");
//			entity.setKeywordsSeo(cLanguageDto.getKeywordsSeo());
//			entity.setKeywords(cLanguageDto.getKeywords());
//			entity.setKeywordsDesc(cLanguageDto.getKeywordsDesc());
//			documentCategoryLanguageRepository.save(entity);
//
//			entityEn.setCategoryId(editDto.getId());
//			entityEn.setTitle(cLanguageDto.getTitle());
//			entityEn.setLanguageCode("VI");
//			entityEn.setKeywordsSeo(cLanguageDto.getKeywordsSeo());
//			entityEn.setKeywords(cLanguageDto.getKeywords());
//			entityEn.setKeywordsDesc(cLanguageDto.getKeywordsDesc());
//			documentCategoryLanguageRepository.save(entityEn);
//
//		}
//	}

	@Override
	public void deleteDataById(Long id) throws Exception {
		// userName
		String userName = UserProfileUtils.getUserNameLogin();

		Date deleteDate = new Date();

		Product entity = productManagementRepository.findOne(id);
		entity.setDeleteBy(userName);
		entity.setDeleteDate(deleteDate);
		productManagementRepository.update(entity);
		documentCategoryLanguageRepository.deleteData(id, userName);
	}

	@Override
	public List<ProductLanguageSearchDto> getListForSort(ProductSearchDto searchDto) {
//		return documentCategoryRepository.findListForSort(searchDto);
		return null;
	}

	@Override
	public void updateSortAll(ProductSearchDto searchDto) {
//		if (CollectionUtils.isNotEmpty(searchDto.getSortOderList())) {
//			for (SortOrderDto sort : searchDto.getSortOderList()) {
//				documentCategoryRepository.updateSortAll(sort);
//			}
//		}

	}

	@Override
	public Class<?> getEnumColumnForExport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTemplateNameForExport(Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonSearchFilterUtils getCommonSearchFilterUtils() {
		return commonSearchFilterUtils;
	}
}
