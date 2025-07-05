/*******************************************************************************
 * Class        :CategoryService
 * Created date :2019/04/16
 * Lasted date  :2019/04/16
 * Author       :HungHT
 * Change log   :2019/04/16:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryDto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryLangDto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategorySearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoCategory;

/**
 * CategoryService
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface CategoryService {   

	/**
     * getCategoryList
     * @param search
     * @param pageSize
     * @param page
     * @return
     * @author HungHT
     */
    PageWrapper<EfoCategoryDto> getCategoryList(EfoCategorySearchDto search, int pageSize, int page, String langCode);

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    EfoCategoryDto findById(Long id, String langCode);

	/**
     * findByName
     * @param name
     * @return EfoCategoryDto
     * @author HungHT
     */
    EfoCategoryDto findByNameAndCompanyId(String name, Long companyId, String langCode);

	/**
     * saveCategory
     * @param objectDto
     * @return
     * @author HungHT
     */
    EfoCategory saveCategory(EfoCategoryDto objectDto);

	/**
     * deleteCategory
     * @param id
     * @return
     * @author HungHT
     */
    boolean deleteCategory(Long id);
   
    /**
     * getCategoryListByCompanyId
     * 
     * @param term
     * @param companyId
     * @param isPaging
     * @return
     * @author HungHT
     */
    List<Select2Dto> getCategoryListByCompanyId(String term, Long companyId, boolean isPaging, String langCode);
    
    /**
     * findMaxDisplayOrderByCompanyId
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    Long findMaxDisplayOrderByCompanyId(Long companyId);
    
    /**
     * getCategoryLanguageListById
     * @param id
     * @return List CategoryLanguageDto
     * @author trieuvd
     */
    public List<EfoCategoryLangDto> getCategoryLanguageByCategoryId(Long categoryId);
    
    /**
     * getCategoryLanguageByName
     * @param categoryName
     * @param langCode
     * @return List CategoryLanguageDto
     * @author trieuvd
     */
    public List<EfoCategoryLangDto> getCategoryLanguageCheck(String categoryName, String langCode, Long categoryId, Long companyId);
    
    /**
     * findCategoryByListCompanyId
     * @param term
     * @param companyId
     * @param isPaging
     * @param langCode
     * @return
     * @author trieuvd
     */
    List<Select2Dto> findCategoryByListCompanyId(String term, List<Long> companyIds, boolean isPaging, String langCode);
}