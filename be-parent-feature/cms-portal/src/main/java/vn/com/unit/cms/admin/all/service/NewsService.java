/*******************************************************************************
 * Class        ：NewsService
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：TaiTM
 * Change log   ：2017/02/23：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.repository.query.Param;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.dto.NewsEditDto;
//import vn.com.unit.cms.admin.all.entity.News;
import vn.com.unit.cms.core.module.news.dto.NewsSearchDto;
import vn.com.unit.cms.core.module.news.dto.NewsSearchResultDto;
import vn.com.unit.cms.core.module.news.entity.News;
import vn.com.unit.cms.core.module.news.entity.NewsLanguage;

/**
 * NewsService
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public interface NewsService extends CmsCommonSearchFillterService<NewsSearchDto, NewsSearchResultDto, NewsEditDto> {

    /**
     * init screen news edit/add
     *
     * @param mav          ModelAndView
     * @param languageCode
     * @author TaiTM
     */
    public void initNewsEdit(ModelAndView mav, NewsEditDto newsEdit, Locale locale);

    public void initNewsListSearch(ModelAndView mav, NewsSearchDto newsSearch, Locale locale);

    /**
     * delete News by category id
     *
     * @param typeId
     * @author TaiTM
     */
    public void deleteNewsByCategoryId(Long typeId);

    /**
     * add Or Edit News
     *
     * @param NewsEditDto
     * @param action      : true is saveDraft, false is sendRequest
     * @author TaiTM
     * @throws IOException
     */
    public boolean addOrEdit(NewsEditDto newsEditDto, boolean action, HttpServletRequest request, Locale locale)
            throws IOException;

    /**
     * get NewsEditDto
     *
     * @param id           Long
     * @param action       boolean: true is edit, false is detail
     * @param languageCode
     * @return NewsEditDto
     * @author TaiTM
     */
    public NewsEditDto getNews(Long id, boolean action, Locale locale, String customerAlias);

    /**
     * get CategorySelect json string
     *
     * @param typeId
     * @param languageCode
     * @return String
     * @author TaiTM
     */
    public String getCategorySelectJson(Long typeId, String languageCode);

    /**
     * find News by code
     *
     * @param code
     * @return
     * @author TaiTM
     */
    public News findByCode(String code);

    /**
     * delete News by entity
     *
     * @author TaiTM
     */
    public void deleteById(News news);

    /**
     * find News by id
     *
     * @param id
     * @return News
     * @author TaiTM
     */
    public News findById(Long id);

    /**
     * findMaxSortByTypeAndCategory
     *
     * @param typeId
     * @return maxSort
     * @author TaiTM
     */
    Long findMaxSortByTypeAndCategory(Long customerId);

    /**
     * @param fileUrl
     * @param request
     * @param response
     * @return
     */
    public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response);

    /**
     * find all promotion type
     * 
     * @return
     */
    public Map<String, Integer> findAllPromotionType();

    /**
     * find all customer type by language code
     * 
     * @param languageCode
     * @return
     */
    public Map<Long, String> findAllCustByLanguageCode(String languageCode);

    /**
     * delete News by type id
     *
     * @param typeId
     * @author TaiTM
     */
    public void deleteNewsByTypeId(Long typeId);

    /**
     * Find news list for sorting
     * 
     * @param NewsSearchDto
     * @return List<NewsLanguageSearchDto>
     */
    List<NewsSearchResultDto> findNewsListForSorting(NewsSearchDto dto);

    /**
     * getMaxCode
     *
     * @return next code
     * @author nhutnn
     */
    String getMaxCode();

    List<NewsSearchResultDto> getNewsListForSortingByTypeIdAndCategoryId(Long customerId, Long id, Long typeId,
            Long categoryId, String lang);

    List<NewsSearchResultDto> getNewsListByProductForSorting(NewsSearchDto searchDto, String lang);

    NewsLanguage findLangByLinkAlias(String linkAlias, String languageCode, Long customerId, Long categoryId,
            Long typeId);

    public void exportExcel(NewsSearchDto newsSearch, HttpServletResponse res, Locale locale, Long customerId);

    public Long countByProductId(String productId);

    public NewsLanguage findByAliasAndCustomerId(String linkAlias, String languageCode, Long customerId);
    
	public Integer getCategoryNameCheck(@Param("categoryId")Long categoryId, @Param("typeId") Long typeId,@Param("newsId") Long newsId );

}
