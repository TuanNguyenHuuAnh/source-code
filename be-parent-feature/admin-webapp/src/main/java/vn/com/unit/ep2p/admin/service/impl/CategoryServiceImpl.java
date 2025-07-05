/*******************************************************************************
 * Class        :CategoryServiceImpl
 * Created date :2019/04/16
 * Lasted date  :2019/04/16
 * Author       :HungHT
 * Change log   :2019/04/16:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.entity.CategoryLanguage;
import vn.com.unit.core.enumdef.param.JcaCategorySearchEnum;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.enumdef.CitySearchEnum;
import vn.com.unit.ep2p.admin.repository.CategoryLanguageRepository;
import vn.com.unit.ep2p.admin.repository.CategoryRepository;
import vn.com.unit.ep2p.admin.service.CategoryService;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryDto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryLangDto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategorySearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoCategory;
import vn.com.unit.ep2p.core.efo.service.impl.EfoCategoryServiceImpl;

/**
 * CategoryServiceImpl
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceImpl extends EfoCategoryServiceImpl implements CategoryService {  

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    CategoryLanguageRepository categoryLanguageRepository;
    
    @Autowired
    private CommonService comService;
    
    @Autowired
    private ObjectMapper objectMapper;

	// Model mapper
	ModelMapper modelMapper = new ModelMapper();

    /**
     * getCategoryList
     * @param search
     * @param pageSize
     * @param page
     * @return
     * @author HungHT
     */
    public PageWrapper<EfoCategoryDto> getCategoryList(EfoCategorySearchDto search, int pageSize, int page, String lang) {
        List<Integer> listPageSize = systemConfig.getListPageSize();
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<EfoCategoryDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        pageWrapper.setListPageSize(listPageSize);
        pageWrapper.setSizeOfPage(sizeOfPage);
        
        setSearchParm(search);
        int count = categoryRepository.countCategoryList(search, lang);
        List<EfoCategoryDto> result = new ArrayList<>();
        if (count > 0) {
            int currentPage = pageWrapper.getCurrentPage();
            int startIndex = (currentPage - 1) * sizeOfPage;
            result = categoryRepository.getCategoryList(search, startIndex, sizeOfPage, lang);   
        }
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }
    
    private void setSearchParm(EfoCategorySearchDto searchDto) {
		if (null == searchDto.getFieldValues()) {
			searchDto.setFieldValues(new ArrayList<>());
		}

		if (searchDto.getFieldValues().isEmpty()) {
			searchDto.setName(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim() : searchDto.getFieldSearch());
			searchDto.setDescription(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim() : searchDto.getFieldSearch());
		} else {
			for (String field : searchDto.getFieldValues()) {
				if (StringUtils.equals(field, JcaCategorySearchEnum.NAME.name())) {
					searchDto.setName(searchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, CitySearchEnum.DESCRIPTION.name())) {
					searchDto.setDescription(searchDto.getFieldSearch().trim());
					continue;
				}
			}
		}
		
		// Add company_id
		if (searchDto.getCompanyId() == 0 ) {
		    searchDto.setCompanyId(0l);
	        searchDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
	        searchDto.setCompanyIdList(UserProfileUtils.getCompanyIdList());
        }
		
	}

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    public EfoCategoryDto findById(Long id, String langCode) {
        return categoryRepository.findById(id, langCode);
    }

	/**
     * findByNameAndCompanyId
     * @param name
     * @return
     * @author HungHT
     */
    public EfoCategoryDto findByNameAndCompanyId(String name, Long companyId, String langCode) {
        return categoryRepository.findByNameAndCompanyId(name, companyId, langCode);
    }

	/**
     * saveCategory
     * @param objectDto
     * @return
     * @author HungHT
     */
    public EfoCategory saveCategory(EfoCategoryDto objectDto) {        
        EfoCategory objectResult = null;
		Date sysDate = comService.getSystemDateTime();
        //EfoCategory objectSave = modelMapper.map(objectDto, EfoCategory.class);
        EfoCategory objectSave = new EfoCategory();
        objectSave.setId(objectDto.getCategoryId());
        objectSave.setCompanyId(objectDto.getCompanyId());
        objectSave.setDescription(objectDto.getDescription());
        objectSave.setDisplayOrder(objectDto.getDisplayOrder());
        objectSave.setActived(objectDto.isActived());
        objectSave.setCreatedId(UserProfileUtils.getAccountId());
        objectSave.setCreatedDate(sysDate);
        
        
        List<EfoCategoryLangDto> listCategoryLanguageDtos = objectDto.getListCategoryLangDto();
        
        if (objectSave != null) {            
            if (objectSave.getId() == null) {
            	objectSave.setCreatedId(UserProfileUtils.getAccountId());
            	objectSave.setCreatedDate(sysDate);
            	
            } else {
                EfoCategory objectCurrent = categoryRepository.findOne(objectDto.getCategoryId());
                // Keep value not change
                objectSave.setCreatedId(objectCurrent.getCreatedId());
                objectSave.setCreatedDate(objectCurrent.getCreatedDate());
                objectSave.setUpdatedId(UserProfileUtils.getAccountId());
                objectSave.setUpdatedDate(sysDate);
            }

            objectSave.setActived(objectDto.isActived());

            Long displayOrder = objectDto.getDisplayOrder();
            if (displayOrder == null) {
                Long displayOrderMax = categoryRepository.findMaxDisplayOrderByCompanyId(objectSave.getCompanyId());
                objectSave.setDisplayOrder(displayOrderMax + 1);
            }

            //set category name
            if (objectSave.getId() != null) {
                objectResult = categoryRepository.update(objectSave);
            } else {
                objectResult = categoryRepository.create(objectSave);
            }
            
            //save/update categoryLanguage
            for (EfoCategoryLangDto categoryLanguageDto : listCategoryLanguageDtos) {
                CategoryLanguage categoryLanguage = categoryLanguageRepository.findByCategoryIdAndLangId(objectResult.getId(), categoryLanguageDto.getLangId());
                if (categoryLanguage != null) {
                    categoryLanguage.setName(categoryLanguageDto.getName());
                    categoryLanguage.setUpdatedDate(sysDate);
                    categoryLanguage.setUpdatedId(UserProfileUtils.getAccountId());
                    categoryLanguageRepository.update(categoryLanguage);
                } else {
                    categoryLanguage = objectMapper.convertValue(categoryLanguageDto, CategoryLanguage.class);
                    categoryLanguage.setCategoryId(objectResult.getId());
                    categoryLanguage.setCreatedDate(objectResult.getCreatedDate());
                    categoryLanguage.setCreatedId(UserProfileUtils.getAccountId());
                    categoryLanguageRepository.create(categoryLanguage);
                }
			}
        }
        return objectResult;
    }

	/**
     * deleteCategory
     * @param id
     * @return
     * @author HungHT
     */
    public boolean deleteCategory(Long id) {
		Date sysDate = comService.getSystemDateTime();
//        String user = UserProfileUtils.getUserNameLogin();
        EfoCategory object = categoryRepository.findOne(id);
        object.setDeletedId(UserProfileUtils.getAccountId());
        object.setDeletedDate(sysDate);
        categoryRepository.update(object);
        
//        //delete categoryLang
//        List<CategoryLanguage> categoryLanguages = categoryLanguageRepository.getByCategoryID(id);
//        if(null!= categoryLanguages) {
//        	for (CategoryLanguage categoryLanguage : categoryLanguages) {
//				categoryLanguageRepository.update(categoryLanguage);				
//			}
//        }
        return true;
    }

    /**
     * getCategoryListByCompanyId
     * 
     * @param keySearch
     * @param companyId
     * @param isPaging
     * @return
     * @author HungHT
     */
    public List<Select2Dto> getCategoryListByCompanyId(String keySearch, Long companyId, boolean isPaging, String langCode) {
        return categoryRepository.getCategoryListByCompanyId(keySearch, companyId, isPaging, langCode);
    }
    
    /**
     * findMaxDisplayOrderByCompanyId
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    public Long findMaxDisplayOrderByCompanyId(Long companyId) {
        return categoryRepository.findMaxDisplayOrderByCompanyId(companyId);
    }

    /**
     * getCategoryLanguageByCategoryId
     * @param categoryId
     * @return List CategoryLanguageDto
     */
	@Override
	public List<EfoCategoryLangDto> getCategoryLanguageByCategoryId(Long categoryId) {
		List<EfoCategoryLangDto> categoryLanguageDtos = new ArrayList<EfoCategoryLangDto>();
		List<CategoryLanguage> categoryLanguages = categoryLanguageRepository.getByCategoryID(categoryId);
		for (CategoryLanguage categoryLanguage : categoryLanguages) {
			EfoCategoryLangDto categoryLanguageDto = modelMapper.map(categoryLanguage, EfoCategoryLangDto.class);
			categoryLanguageDtos.add(categoryLanguageDto);
		}
		return categoryLanguageDtos;
	}

	/**
	 * getCategoryLanguageCheck
	 * @param categoryName
	 * @param langCode
	 * @param categoryId
	 */
	@Override
	public List<EfoCategoryLangDto> getCategoryLanguageCheck(String categoryName, String langCode, Long categoryId, Long companyId) {
		return categoryLanguageRepository.getListToCheckName(categoryName, langCode, categoryId, companyId);
	}

    @Override
    public List<Select2Dto> findCategoryByListCompanyId(String term, List<Long> companyIds, boolean isPaging, String langCode){
        return categoryRepository.getCategoryListByListCompanyId(term, companyIds, isPaging, langCode);
    }
    
}