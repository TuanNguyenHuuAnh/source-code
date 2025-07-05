/*******************************************************************************
 * Class        ：NewsCategoryRepository
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：TaiTM
 * Change log   ：2017/02/27：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.NewsCategoryDto;
import vn.com.unit.cms.admin.all.dto.NewsCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.NewsCategorySearchResultDto;
import vn.com.unit.cms.admin.all.dto.NewsCategorySearchDto;
import vn.com.unit.cms.admin.all.entity.NewsCategory;
import vn.com.unit.cms.admin.all.entity.NewsCategoryLanguage;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

/**
 * NewsCategoryRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public interface NewsCategoryRepository extends DbRepository<NewsCategory, Long> {

    /**
     * @author TaiTM
     */
    int countList(@Param("searchDto") NewsCategorySearchDto searchDto);

    /**
     * @author TaiTM
     */
    Page<NewsCategorySearchResultDto> findListSearch(@Param("searchDto") NewsCategorySearchDto searchDto,
            Pageable pageable);

    /**
     * find all NewsCategory by typeId
     *
     * @param typeId
     * @author TaiTM
     */
    List<NewsCategory> findByTypeId(@Param("typeId") Long typeId);

    /**
     * find all active NewsCategory by typeId
     *
     * @param typeId
     * @author TaiTM
     */
    List<NewsCategory> findAllActiveByTypeId(@Param("typeId") Long typeId);

    /**
     * find all NewsCategory not delete
     *
     * @param typeId
     * @param languageCode
     * @return List<NewsCategoryDto>
     * @author TaiTM
     */
    List<NewsCategoryDto> findByTypeIdAndLanguageCode(@Param("typeId") Long typeId,
            @Param("languageCode") String languageCode);

    /**
     * find NewsCategory by code
     *
     * @param code
     * @return
     * @author TaiTM
     */
    NewsCategory findByCode(@Param("code") String code);

    /**
     * get max sort by TypeId
     *
     * @return
     * @author TaiTM
     */
    Long findMaxSortByTypeId(@Param("typeId") Long typeId);

    /**
     * updateSortAll
     *
     * @param sortOderList
     * @author TaiTM
     */
    @Modifying
    public void updateSortAll(@Param("cond") SortOrderDto sortOderList);

    /**
     * @author TaiTM
     */
    List<NewsCategorySearchResultDto> findListForSort(@Param("cond") NewsCategorySearchDto dto);

    /**
     * getMaxCode
     *
     * @author TaiTM
     */
    String getMaxCode();

    List<NewsCategoryEditDto> findNewsCategoryByCustomerIdAndTypeId(@Param("customerId") Long customerId,
            @Param("typeId") Long typeId, @Param("lang") String lang);

    List<NewsCategoryEditDto> findNewsListForSortingNotCategoryId(@Param("customerId") Long customerId,
            @Param("typeId") Long typeId, @Param("categoryId") Long categoryId, @Param("lang") String lang);

    List<NewsCategory> findNewsCategorySortByNotId(@Param("customerId") Long customerId,
            @Param("categoryId") Long categoryId, @Param("lang") String lang);

    List<NewsCategoryDto> findNewsCategoryDtoByCustomerIdAndTypeId(@Param("customerId") Long customerId,
            @Param("typeId") Long typeId, @Param("lang") String lang);

    NewsCategoryLanguage findByAliasTypeId(@Param("linkAlias") String linkAlias,
            @Param("languageCode") String languageCode, @Param("customerId") Long customerId,
            @Param("typeId") Long typeId);
}
