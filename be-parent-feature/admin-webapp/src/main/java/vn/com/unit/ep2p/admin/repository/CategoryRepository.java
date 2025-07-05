/*******************************************************************************
 * Class        :CategoryRepository
 * Created date :2019/04/16
 * Lasted date  :2019/04/16
 * Author       :HungHT
 * Change log   :2019/04/16:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryDto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategorySearchDto;
import vn.com.unit.ep2p.core.efo.repository.EfoCategoryRepository;
import vn.com.unit.ep2p.dto.ResBoardListCategoryDto;

/**
 * CategoryRepository
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
/**	
 * CategoryRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface CategoryRepository extends EfoCategoryRepository {

	/**
     * countCategoryList
     * @param search
     * @return
     * @author HungHT
     */
    int countCategoryList(@Param("search") EfoCategorySearchDto search, @Param("languageCode") String langCode);

	/**
     * getCategoryList
     * @param search
     * @param offset
     * @param sizeOfPage
     * @return
     * @author HungHT
     */
    List<EfoCategoryDto> getCategoryList(@Param("search") EfoCategorySearchDto search, @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, @Param("languageCode") String langCode);

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    EfoCategoryDto findById(@Param("id") Long id, @Param("languageCode") String langCode);

	/**
     * findByName
     * @param name
     * @return CategoryDto
     * @author HungHT
     */
    EfoCategoryDto findByNameAndCompanyId(@Param("name") String name, @Param("companyId") Long companyId, @Param("languageCode") String langCode);

    /**
     * findMaxDisplayOrderByCompanyId
     * @param companyId
     * @return Long
     * @author HungHT
     */
    Long findMaxDisplayOrderByCompanyId(@Param("companyId") Long companyId);
    
    /**
     * getCategoryListByCompanyId
     * @param keySearch
     * @param companyId
     * @return
     * @author HungHT
     */
    List<Select2Dto> getCategoryListByCompanyId(@Param("keySearch") String keySearch, @Param("companyId") Long companyId, @Param("isPaging") boolean isPaging, @Param("languageCode") String langCode);
    
    /**
     * getCategoryByListId
     * 
     * @param listId
     * @return
     * @author HungHT
     */
    List<Select2Dto> getCategoryByListId(@Param("keySearch")String keySearch,@Param("listId") List<Long> listId, @Param("languageCode") String langCode);
    
    /**
     * 
     * getApiCategoryByListId
     * @param keySearch
     * @param listId
     * @param langCode
     * @return
     * @author taitt
     */
    List<ResBoardListCategoryDto> getApiCategoryByListId(@Param("keySearch")String keySearch,@Param("listId") List<Long> listId, @Param("languageCode") String langCode);
    
    
    /**
     * getCategoryByListCompanyId
     * 
     * @param companyId
     * @return
     * @author TaiTT
     */
    List<ResBoardListCategoryDto> getMytaskCategoryByListCompanyId(@Param("companyId") Long companyId,@Param("groupIdList") List<String> groupIdList,@Param("userId") String userId,@Param("languageCode") String langCode);
    
    /**
     * 
     * getDocumentCategoryByListCompanyId
     * @param companyId
     * @param langCode
     * @return
     * @author taitt
     */
    ResBoardListCategoryDto getDocumentCategoryByListCompanyId(@Param("companyId") Long companyId,@Param("languageCode") String langCode);
    
    /**
     * 
     * getMyTemplateCategoryByListCompanyId
     * @param companyId
     * @param langCode
     * @return
     * @author taitt
     */
    List<ResBoardListCategoryDto> getMyTemplateCategoryByListCompanyId(@Param("companyId") Long companyId,@Param("accountId")Long accountId ,@Param("languageCode") String langCode);
    
    
    /**
     * getCategoryDocumentByListCompanyId
     * 
     * @param companyId
     * @return
     * @author TaiTT
     */
//    List<ResDocumentListCategoryDto> getCategoryDocumentByListCompanyId(@Param("companyId") Long companyId,@Param("languageCode") String langCode);
    
    /**
     * getCategoryListByListCompanyId
     * @param keySearch
     * @param companyId
     * @param isPaging
     * @param langCode
     * @return
     * @author trieuvd
     */
    List<Select2Dto> getCategoryListByListCompanyId(@Param("keySearch") String keySearch, @Param("companyIds") List<Long> companyIds, @Param("isPaging") boolean isPaging, @Param("languageCode") String langCode);
}