/*******************************************************************************
 * Class        ：NewsTypeService
 * Created date ：2017/03/01
 * Lasted date  ：2017/03/01
 * Author       ：hand
 * Change log   ：2017/03/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.com.unit.cms.admin.all.dto.NewsTypeCategoryDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeEditDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeSearchDto;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.cms.admin.all.entity.NewsType;
import vn.com.unit.cms.admin.all.entity.NewsTypeLanguage;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

/**
 * NewsTypeService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface NewsTypeService {

    /**
     * get all NewsType by language code
     * 
     * @param languageCode
     * @return List<NewsTypeDto>
     * @author hand
     */
    public List<NewsTypeDto> findByLanguageCode(String languageCode);

    /**
     * searchTypeLanguage
     *
     * @param page
     * @param searchDto NewsTypeSearchDto
     * @return PageWrapper
     * @author hand
     */
    public PageWrapper<NewsTypeLanguageSearchDto> searchTypeLanguage(int page, NewsTypeSearchDto searchDto,
            Locale locale);

    /**
     * getNewsTypeEditDto
     *
     * @param id
     * @param languageCode
     * @param action:      true: edit, false: detail
     * @return NewsTypeEditDto
     * @author hand
     */
    public NewsTypeEditDto getNewsTypeEditDto(Long id, Locale locale, String businessCode);

    /**
     * add Or Edit NewsType
     *
     * @param categoryEditDto
     * @author hand
     */
    public boolean addOrEdit(NewsTypeEditDto categoryEditDto, Locale locale, HttpServletRequest request)
            throws IOException;

    /**
     * delete NewsType by id
     *
     * @param id
     * @author hand
     */
    public void deleteById(Long id);

    /**
     * find NewsType by code
     *
     * @param code
     * @return
     * @author hand
     */
    public NewsType findByCode(String code);

    /**
     * find NewsType by id
     *
     * @param id
     * @return
     * @author hand
     */
    public NewsType findById(Long id);

    /**
     * get max sort by TypeId
     *
     * @return
     * @author hand
     */
    public Long findMaxSortByTypeId(Long typeId);
    
    /**
     * find NewsType by alias and customerId
     * 
     * @param linkAlias
     * @param customerId
     * @return Product
     */
    public NewsTypeLanguage findByAliasAndCustomerId(String linkAlias, String languageCode, Long customerId);

    /**
     * find news list for sorting
     * 
     * @param languageCode
     * @return List<NewsTypeDto>
     */
    List<NewsTypeLanguageSearchDto> findNewsListForSorting(Long customerId, String languageCode);

    /**
     * updateSortAll
     *
     * @param sortOderList
     * @author hand
     */
    public void updateSortAll(List<SortOrderDto> sortOderList);

    /**
     * findNewsTypeListByCustomerIdAndPromotion
     * 
     * @param customerId
     * @param languageCode
     * @param promotion
     * @return List<NewsTypeDto>
     */
    public List<NewsTypeDto> findListByCustomerIdAndPromotion(Long customerId, String languageCode, Boolean promotion);

    /**
     * getMaxCode
     *
     * @author nhutnn
     * @return max code
     */
    String getMaxCode();

    public List<Select2Dto> getNewsTypeByCustomerId(Long customerId, String lang);

    public List<NewsTypeEditDto> getNewsTypeEditDtoByNotId(Long id, Long customerId, String lang);

    public List<NewsType> getNewsTypeSortByNotId(Long id, Long customerId, String lang);

    public List<NewsTypeCategoryDto> getNewsTypeCategory(String languageCode, Long customerTypeId, Integer status);

    public NewsType getNewsTypeTypeOfLibary(Integer typeOfLibary, Long customerId);

    public boolean checkTypeOfLibary(Integer typeOfLibary, Long customerId);

    public void exportExcel(NewsTypeSearchDto searchDto, HttpServletResponse res, Locale locale);

}
