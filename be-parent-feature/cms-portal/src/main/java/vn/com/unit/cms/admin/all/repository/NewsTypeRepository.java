/*******************************************************************************
 * Class        ：NewsTypeRepository
 * Created date ：2017/03/01
 * Lasted date  ：2017/03/01
 * Author       ：hand
 * Change log   ：2017/03/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.ExportNewsTypeReportDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeCategoryDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeEditDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeSearchDto;
import vn.com.unit.cms.admin.all.entity.NewsType;
import vn.com.unit.cms.admin.all.entity.NewsTypeLanguage;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;

/**
 * NewsTypeRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface NewsTypeRepository extends DbRepository<NewsType, Long> {

    /**
     * find all NewsType by language code
     * 
     * @param languageCode
     * @return
     * @author hand
     */
    List<NewsTypeDto> findByLanguageCode(@Param("languageCode") String languageCode);

    /**
     * count all record TypeLanguage
     *
     * @param searchDto
     * @return int
     * @author hand
     */
    int countByNewsTypeSearchDto(@Param("searchCond") NewsTypeSearchDto searchDto);

    /**
     * find all TypeLanguage by NewsTypeSearchDto
     *
     * @param offsetSQL
     * @param sizeOfPage
     * @param searchDto  NewsTypeSearchDto
     * @return
     * @author hand
     */
    List<NewsTypeLanguageSearchDto> findByNewsTypeSearchDto(@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, @Param("searchCond") NewsTypeSearchDto searchDto);

    /**
     * find NewsType by code
     *
     * @param code
     * @return NewsType
     * @author hand
     */
    NewsType findByCode(@Param("code") String code);

    /**
     * findMaxSortByTypeId
     *
     * @param typeId
     * @return
     * @author hand
     */
    Long findMaxSortByTypeId(@Param("typeId") Long typeId);

    /**
     * findByCustomerId
     *
     * @param customerId
     * @param languageCode
     * @param promotion
     * @return List<NewsTypeDto>
     * @author hand
     */
    List<NewsTypeDto> findByCustomerId(@Param("customerId") Long customerId, @Param("languageCode") String languageCode,
            @Param("promotion") Boolean promotion);

    /**
     * find NewsType by alias and customerId
     * 
     * @param linkAlias
     * @param customerId
     * @return Product
     */
    public NewsTypeLanguage findByAliasAndCustomerId(@Param("linkAlias") String linkAlias,
            @Param("languageCode") String languageCode, @Param("customerId") Long customerId);

    /**
     * find news list for sorting
     * 
     * @param languageCode
     * @return List<NewsTypeDto>
     */
    List<NewsTypeLanguageSearchDto> findNewsListForSorting(@Param("customerId") Long customerid,
            @Param("languageCode") String languageCode);

    /**
     * updateSortAll
     *
     * @param sortOderList
     * @author hand
     */
    @Modifying
    public void updateSortAll(@Param("cond") SortOrderDto sortOderList);

    /**
     * getMaxCode
     *
     * @author nhutnn
     */
    String getMaxCode();

    public List<Select2Dto> findNewsTypeByCustomerId(@Param("customerId") Long customerId, @Param("lang") String lang);

    public List<NewsTypeEditDto> findNewsTypeEditDtoByNotId(@Param("typeId") Long typeId,
            @Param("customerId") Long customerId, @Param("lang") String lang);

    public List<NewsType> findNewsTypeSortByNotId(@Param("typeId") Long typeId, @Param("customerId") Long customerId,
            @Param("lang") String lang);

    public Long findMaxSort(@Param("customerId") Long customerId, @Param("lang") String lang);

    public List<NewsTypeCategoryDto> findByNewsCustomerTypeId(@Param("customerTypeId") Long customerTypeId,
            @Param("languageCode") String languageCode, @Param("status") Integer status);

    public NewsType findTypeOfLibary(@Param("typeOfLibary") Integer typeOfLibary, @Param("customerId") Long customerId);

    public List<ExportNewsTypeReportDto> exportExcelWithCondition(@Param("searchCond") NewsTypeSearchDto searchDto);

}
