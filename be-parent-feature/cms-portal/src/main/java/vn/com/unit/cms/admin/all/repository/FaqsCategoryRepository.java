/*******************************************************************************
 * Class        ：FaqsCategoryRepository
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：TaiTM
 * Change log   ：2017/02/28：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.FaqsCategoryDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategorySearchResultDto;
import vn.com.unit.cms.admin.all.entity.FaqsCategory;
import vn.com.unit.cms.admin.all.entity.FaqsCategoryLanguage;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

/**
 * FaqsCategoryRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public interface FaqsCategoryRepository extends DbRepository<FaqsCategory, Long> {
    /**
     * count category faqs
     * 
     * @param condition
     * @return Integer
     * @author TaiTM
     */
    public Integer countList(@Param("searchDto") FaqsCategorySearchDto condition);

    /**
     * Find list category faqs
     * 
     * @param startIndex
     * @param sizeOfPage
     * @param condition
     * @return List<FaqsCategorySearchDto>
     * @author TaiTM
     */
    public Page<FaqsCategorySearchResultDto> findListSearch(@Param("searchDto") FaqsCategorySearchDto condition,
            Pageable pageable);

    /**
     * findListFaqsCategoryForSort
     *
     * @param condition
     * @author TaiTM
     */
    public List<FaqsCategorySearchResultDto> findListForSort(@Param("searchCond") FaqsCategorySearchDto condition);

    /**
     * find all category faqs
     * 
     * @return List<FaqsCategoryDto>
     * @author TaiTM
     */
    public List<FaqsCategoryDto> findAllFaqsCategory(@Param("lang") String lang);

    /**
     * find all FaqsCategory by typeId
     *
     * @param typeId
     * @author TaiTM
     */
    List<FaqsCategory> findByTypeId(@Param("typeId") Long typeId);

    /**
     * find FaqsCategory by code
     *
     * @param code
     * @return
     * @author TaiTM
     */
    FaqsCategory findByCode(@Param("code") String code);

    /**
     * get max sort by TypeId
     *
     * @return
     * @author TaiTM
     */
    Long findMaxSortByTypeId(@Param("typeId") Long typeId);

    /**
     * get findByTypeIdAndLanguageCode
     *
     * @param typeId
     * @param languageCode
     * @return List<FaqsCategoryDto>
     * @author TaiTM
     */
    public List<FaqsCategoryDto> findByTypeIdAndLanguageCode(@Param("typeId") Long typeId,
            @Param("languageCode") String languageCode);

    public List<FaqsCategoryDto> findCategoryByTypeAndCustomerId(@Param("typeId") Long typeId,
            @Param("customerId") Long customerId, @Param("languageCode") String languageCode);

    /**
     * updateSortAll
     *
     * @param sortOderList
     * @author TaiTM
     */
    @Modifying
    public void updateSortAll(@Param("cond") SortOrderDto sortOderList);

    public List<FaqsCategoryDto> findAllFaqsCategoryByCustomerId(@Param("lang") String lang,
            @Param("customerId") Long customerId);

    FaqsCategoryLanguage findByAliasCustomerId(@Param("linkAlias") String linkAlias,
            @Param("languageCode") String languageCode, @Param("customerId") Long customerId);

    List<FaqsCategoryEditDto> findFaqsCategoryListForSortingNotCategoryId(@Param("customerId") Long customerId,
            @Param("typeId") Long typeId, @Param("categoryId") Long categoryId, @Param("lang") String lang);

    List<FaqsCategoryEditDto> findListForTree(@Param("languageCode") String lang);
}
