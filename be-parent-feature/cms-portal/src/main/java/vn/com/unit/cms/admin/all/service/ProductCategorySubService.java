/*******************************************************************************
 * Class        ：ProductCategorySubService
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.com.unit.cms.admin.all.dto.ProductCategorySubDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubEditDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubSearchDto;
import vn.com.unit.cms.admin.all.entity.ProductCategorySub;
import vn.com.unit.cms.admin.all.entity.ProductCategorySubLanguage;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.common.dto.Select2Dto;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

/**
 * ProductCategorySubService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductCategorySubService
        extends DocumentWorkflowCommonService<ProductCategorySubEditDto, ProductCategorySubEditDto> {

    /**
     * searchCategoryLanguage
     *
     * @param page
     * @param searchDto
     *            ProductCategorySubSearchDto
     * @return PageWrapper
     * @author hand
     */
    public PageWrapper<ProductCategorySubLanguageSearchDto> searchCategoryLanguage(int page,
            ProductCategorySubSearchDto searchDto, Locale locale);

    /**
     * getProductCategorySub
     *
     * @param id
     * @param action
     *            true is edit, false is detail
     * @param languageCode
     * @return ProductCategorySubEditDto
     * @author hand
     */
    public ProductCategorySubEditDto getProductCategorySub(String businessCode, String customerAlias,Long id,Locale locale);

    /**
     * add Or Edit ProductCategorySub
     *
     * @param categoryEditDto
     * @author hand
     * @throws IOException
     */
    public boolean addOrEdit(ProductCategorySubEditDto categoryEditDto,Locale locale, HttpServletRequest request) throws IOException;

    /**
     * delete ProductCategorySub by typeId
     *
     * @param typeId
     * @author hand
     */
    public void deleteByTypeId(Long typeId);

    /**
     * delete ProductCategorySub by id
     *
     * @param productCategorySub
     * @author hand
     */
    public void deleteProductCategorySub(ProductCategorySub productCategorySub);

    /**
     * find all ProductCategorySub not delete
     * 
     * @param typeId
     * @param languageCode
     * @return List<ProductCategorySubDto>
     * @author hand
     */
    public List<ProductCategorySubDto> findByTypeIdAndLanguageCode(Long typeId, String languageCode);

    /**
     * find ProductCategorySub by typeId
     *
     * @return List<ProductCategorySub>
     * @author hand
     */
    public List<ProductCategorySub> findByTypeId(Long typeId);

    /**
     * find ProductCategorySub by code
     *
     * @param code
     * @return
     * @author hand
     */
    public ProductCategorySub findByCode(String code);

    /**
     * find ProductCategorySub by id
     *
     * @param id
     * @return ProductCategorySub
     * @author hand
     */
    public ProductCategorySub findById(Long id);

    /**
     * get max sort by TypeId and categoryId
     *
     * @param typeId
     * @param categoryId
     * @return
     * @author hand
     */
    public Long findMaxSortByTypeIdAndCategoryId(Long typeId, Long categoryId);

    /**
     * get CategorySelect json string
     *
     * @param typeId
     * @param languageCode
     * @return String
     * @author hand
     */
    public String getCategorySelectJson(Long typeId, String languageCode);

    /**
     * findByTypeIdAndCategoryId
     *
     * @param typeId
     * @param categoryId
     * @param languageCode
     * @return List<ProductCategorySubDto>
     * @author hand
     */
    public List<ProductCategorySubDto> findByTypeIdAndCategoryId(Long typeId, Long categoryId, String languageCode,Integer status);
    /**
     * findListForSort
     *
     * @param languageCode
     * @param customerId
     * @param categoryId
     * @return
     * @author TranLTH
     */
    public List<ProductCategorySubLanguageSearchDto> findListForSort(String languageCode, Long customerId, Long categoryId);
    /**
     * updateSortAll
     *
     * @param sortOderList
     * @author TranLTH
     */
    public void updateSortAll(List<SortOrderDto> sortOderList);

    /** getMaxCode
    *
    * @author nhutnn
    * @return max code
    */
    String getMaxCode();
    
    public List<Select2Dto> findListForSort2(String languageCode, Long customerId, Long categoryId, Long typeId);

    /** findByTypeIdAndListCategoryId
     *
     * @param typeId
     * @param categoryListId
     * @param languageCode
     * @return
     * @author nhutnn
     */
    List<ProductCategorySubDto> findByTypeIdAndListCategoryId(Long typeId, List<Long> categoryListId, String languageCode,Integer status);

    /** requestEditorDownload
     *
     * @param fileUrl
     * @param request
     * @param response
     * @author nhutnn
     */
    boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response);
    
    List<ProductCategorySubDto> getByTypeIdAndListCategoryIdForEdit(Long typeId, List<Long> categoryListId, String languageCode,Integer status);

    /** findByAliasAndCustomerId
     *
     * @param linkAlias
     * @param languageCode
     * @param customerId
     * @param typeId
     * @author nhutnn
     */
    ProductCategorySubLanguage findByAliasAndCustomerId(String linkAlias, String languageCode, Long customerId, Long typeId);
    
    public void exportExcel(ProductCategorySubSearchDto searchDto, HttpServletResponse res, Locale locale);
    
    public Long countByProductCategoryId(Long productCategoryId);
    
	public List<ProductCategorySubDto> getListProductCategorySubDto(Long customerId,Long productCategoryId, Locale locale, Integer status);
	
	public int countDependencies(Long categorySubId, List<Long> lstStatus);
	
	/** Get list dependencies of Product categories
     * @author tranlth - 22/05/219
     * @param categorySubId
     * @param lstStatus
     * @return List<Map <String, String>>
     */
    public List<Map <String, String>> listDependencies(Long categorySubId, List<Long> lstStatus);
}