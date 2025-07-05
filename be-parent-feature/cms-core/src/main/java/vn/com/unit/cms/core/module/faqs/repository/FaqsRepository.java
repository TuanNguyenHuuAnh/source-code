/*******************************************************************************
 * Class        ：FaqsRepository
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：TaiTM
 * Change log   ：2017/02/28：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.faqs.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.faqs.dto.FaqsCategoryLangDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchResultDto;
import vn.com.unit.cms.core.module.faqs.entity.Faqs;
import vn.com.unit.cms.core.module.faqs.entity.FaqsLanguage;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

/**
 * FaqsRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public interface FaqsRepository extends DbRepository<Faqs, Long> {

    /**
     * count faqs
     * 
     * @param faqsDto
     * @return Integer
     * @author TaiTM
     */
    public Integer countFaqs(@Param("searchDto") FaqsSearchDto faqsDto);

    /**
     * Find category faqs list
     * 
     * @param sizeOfPage
     * @param sizeOfPage
     * @param faqsDto
     * @return List<FaqsDto>
     * @author TaiTM
     */
    public Page<FaqsSearchResultDto> findFaqsList(@Param("searchDto") FaqsSearchDto faqsDto, Pageable pageable);

    /**
     * Find Faqs by code
     * 
     * @param faqsCode
     * @return Faqs
     * @author TaiTM
     */
    public Faqs findFaqsByCode(@Param("faqsCode") String faqsCode);

    /**
     * Find list Faqs by categoryId
     * 
     * @param categoryId
     * @return Faqs
     * @author hand
     */
    public List<Faqs> findByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * findMaxSortByTypeAndCategory
     *
     * @param typeId
     * @param categoryId
     * @return
     * @author hand
     */
    long findMaxSortByTypeAndCategory(@Param("typeId") Long typeId, @Param("categoryId") Long categoryId);

    public String getMaxFaqsCode();

    @Modifying
    public void updateSortAll(@Param("cond") SortOrderDto sortOderList);
    
    public List<FaqsSearchResultDto> findAllFaqsForSort(@Param("searchDto") FaqsSearchDto faqsDto);

    /**
     * findByAliasCategoryIdAndTypeId
     *
     * @param linkAlias
     * @param languageCode
     * @param customerId
     * @param categoryId
     * @param typeId
     * @author nhutnn
     */
    FaqsLanguage findByAliasCategoryIdAndTypeId(@Param("linkAlias") String linkAlias,
            @Param("languageCode") String languageCode, @Param("customerId") Long customerId,
            @Param("categoryId") Long categoryId, @Param("typeId") Long typeId,
            @Param("categoryListId") List<Long> categoryProductListId,
            @Param("categorySubListId") List<Long> categorySubProductListId,
            @Param("productListId") List<Long> productListId);

    Long countByProductId(@Param("productId") String productId);

    /**
     * @author ThuyNTB
     * @param faqsDto description: export file excel for list Faqs.
     *                FaqsRepository_exportExcelFaqs
     */
    public List<FaqsSearchDto> exportExcelFaqs(@Param("searchCond") FaqsSearchDto faqsDto);
    
	public int countFaqsByIdCategory(@Param("searchKey")String searchKey,@Param("idCategory")Long idCategory, @Param("modeView")Integer modeView, @Param("language")String language);

	public List<FaqsSearchResultDto> searchFaqsByIdCategory(
			@Param("offset") int offset, @Param("size") int size,
			@Param("searchKey")String searchKey,@Param("idCategory")Long idCategory, @Param("language")String language, @Param("modeView")Integer modeView);

	public List<FaqsCategoryLangDto> getListCategoryFaqs(@Param("language")String language, @Param("modeView")Integer modeView);
	public Page<FaqsSearchResultDto> findListData(@Param("searchDto") FaqsSearchDto faqsDto, Pageable pageable);

    public String findCateFaq(@Param("id") Long id,@Param("lang")  String lang);
}
