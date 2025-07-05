package vn.com.unit.cms.core.module.news.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
//import vn.com.unit.cms.admin.all.dto.ExportNewsReportDto;
//import vn.com.unit.cms.admin.all.dto.NewsSearchDto;
//import vn.com.unit.cms.admin.all.entity.NewsLanguage;
import vn.com.unit.cms.core.module.news.dto.ExportNewsReportDto;
import vn.com.unit.cms.core.module.news.dto.FeNewsSearchDto;
import vn.com.unit.cms.core.module.news.dto.NewsByTypeSearchDto;
import vn.com.unit.cms.core.module.news.dto.NewsSearchResultDto;
import vn.com.unit.cms.core.module.news.dto.NewsSearchDto;
import vn.com.unit.cms.core.module.news.dto.NewsTypeSearchAllPage;
import vn.com.unit.cms.core.module.news.entity.News;
import vn.com.unit.cms.core.module.news.entity.NewsLanguage;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

public interface NewsRepository extends DbRepository<News, Long> {

    public int countNewsDto(@Param("searchCond") FeNewsSearchDto searchDto, @Param("modeView") Integer modeView,  @Param("language")String language,  @Param("channel")String channel);

    public List<NewsSearchResultDto> findNewsDto(@Param("offset") int offset, @Param("sizeOfPage") Integer sizeOfPage,
            @Param("searchCond") FeNewsSearchDto searchDto, @Param("language") String language,
            @Param("modeView") Integer modeView, @Param("channel") String channel );

    public NewsSearchResultDto getDetailListNews(@Param("id") Long id, @Param("language") String language);

    List<NewsSearchResultDto> getListLableNewsType(@Param("typeIds") Long[] typeIds);

    /**
     * count all record NewsLanguage
     *
     * @param searchDto
     * @return int
     * @author TaiTM
     */
    int countList(@Param("searchDto") NewsSearchDto searchDto);

    /**
     * find all NewsLanguage by NewsSearchDto
     *
     * @param offsetSQL
     * @param sizeOfPage
     * @param searchDto  NewsSearchDto
     * @return
     * @author TaiTM
     */
    Page<NewsSearchResultDto> findListSearch(@Param("searchDto") NewsSearchDto searchDto, Pageable pageable);

    /**
     * find all News by categoryId
     *
     * @param typeId
     * @return
     * @author TaiTM
     */
    List<News> findByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * find all active News by typeId
     *
     * @param typeId
     * @author nhutnn
     */
    List<News> findAllActiveByTypeId(@Param("typeId") Long typeId);

    /**
     * find News by code
     *
     * @param code
     * @return
     * @author TaiTM
     */
    News findByCode(@Param("code") String code);

    /**
     * findMaxSortByTypeAndCategory
     *
     * @param typeId
     * @return maxSort
     * @author TaiTM
     */
    Long findMaxSortByTypeAndCategory(@Param("customerId") Long customerId);

    /**
     * find all News by categoryId
     *
     * @param typeId
     * @return
     * @author TaiTM
     */
    List<News> findByTypeId(@Param("typeId") Long typeId);

    /**
     * updateSortAll
     *
     * @param sortOderList
     * @author TaiTM
     */
    @Modifying
    public void updateSortAll(@Param("cond") SortOrderDto sortOderList);

    /**
     * Find news list for sorting
     * 
     * @param EmulateSearchDto
     * @return List<NewsLanguageSearchDto>
     */
    List<NewsSearchResultDto> findNewsListForSorting(@Param("cond") NewsSearchDto dto);

    List<NewsSearchResultDto> findNewsListForSortingByTypeIdAndCategoryId(@Param("customerId") Long customerId,
            @Param("id") Long id, @Param("typeId") Long typeId, @Param("categoryId") Long categoryId,
            @Param("lang") String lang);

    /**
     * getMaxCode
     *
     * @author nhutnn
     */
    String getMaxCode();

    List<NewsSearchResultDto> findBeforeIdSelectJsonByProduct(@Param("customerId") Long customerId,
            @Param("categoryListId") List<Long> categoryListId,
            @Param("categorySubListId") List<Long> categorySubListId, @Param("productListId") List<Long> productListId,
            @Param("id") Long id, @Param("lang") String lang, @Param("status") Integer status);

    List<NewsSearchResultDto> findNewsListByProductForSorting(@Param("searchDto") NewsSearchDto searchDto,
            @Param("lang") String lang);

    NewsLanguage findLangByLinkAlias(@Param("linkAlias") String linkAlias, @Param("languageCode") String languageCode,
            @Param("customerId") Long customerId, @Param("categoryId") Long categoryId, @Param("typeId") Long typeId);

    NewsLanguage findLangByLinkAliasWithNewsCategoryIdAndNewsTypeId(@Param("linkAlias") String linkAlias,
            @Param("languageCode") String languageCode, @Param("customerId") Long customerId,
            @Param("categoryId") Long categoryId, @Param("typeId") Long typeId);

    List<NewsSearchResultDto> exportExcelNews(@Param("searchCond") NewsSearchDto searchDto);

    List<ExportNewsReportDto> exportExcelNewsExportEnum(@Param("searchCond") NewsSearchDto searchDto);

    Long countByProductId(@Param("productId") String productId);

    public NewsLanguage findByAliasAndCustomerId(@Param("linkAlias") String linkAlias,
            @Param("languageCode") String languageCode, @Param("customerId") Long customerId);

    List<News> findNewsByTypeIds(@Param("typeIds") Long[] typeIds);

    public List<NewsSearchResultDto> getListCategoryByPageType(@Param("pageType") String pageType,
            @Param("modeView") Integer modeView,
            @Param("channel") String channel);

    public NewsSearchResultDto getDetailNewsByLink(@Param("link") String link, @Param("type") String type, @Param("categoryId") String categoryId, @Param("language") String language);

    public NewsSearchResultDto getTypeByPageType(@Param("pageType") String pageType,
            @Param("modeView") Integer modeView, @Param("language") String language);

    public List<NewsTypeSearchAllPage> getTypeByNewsKey(@Param("key") String key, @Param("modeView") Integer modeView,
            @Param("language") String language);

    public int countNewsByType(@Param("searchDto") NewsByTypeSearchDto searchDto, @Param("modeView") Integer modeView,
            @Param("language") String language);

    public List<NewsSearchResultDto> searchNewsByType(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
            @Param("searchDto") NewsByTypeSearchDto searchDto, @Param("language") String language,
            @Param("modeView") Integer modeView);

    public List<NewsSearchResultDto> getListPageType(@Param("language") String language,
            @Param("modeView") Integer modeView, @Param("channel") String channel);

	public List<NewsSearchResultDto> listNewsCareer(@Param("typeCode")String typeCode, @Param("language")String language);
	
	public Integer findCategoryName(@Param("categoryId")Long categoryId, @Param("typeId") Long typeId,@Param("newsId") Long newsId);
	
	public List<NewsSearchResultDto> listNewsEvent(@Param("typeCode")String typeCode, @Param("language")String language,@Param("newsId") Long newsId);
	
	public NewsSearchResultDto newsHot(@Param("typeCode")String typeCode, @Param("language")String language);
	
	public List<NewsSearchResultDto> getNewsHot(@Param("language")String language, @Param("mNewsCategoryId") Long mNewsCategoryId);
	
	public List<NewsSearchResultDto> getAllByTypeCode(@Param("typeCode")String typeCode, @Param("language")String language);
	
	public List<NewsSearchResultDto> getListCategoryOfUserGuide(@Param("pageType") String pageType, @Param("mNewsCategoryId") Long mNewsCategoryId
            , @Param("modeView") Integer modeView);

}
