/*******************************************************************************
 * Class        ：ProductCategoryService
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

import vn.com.unit.cms.admin.all.dto.ProductCategoryDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySearchDto;
import vn.com.unit.cms.admin.all.entity.ProductCategory;
import vn.com.unit.cms.admin.all.entity.ProductCategoryLanguage;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.common.dto.Select2Dto;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

/**
 * ProductCategoryService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductCategoryService
        extends DocumentWorkflowCommonService<ProductCategoryEditDto, ProductCategoryEditDto> {

    /**
     * searchCategoryLanguage
     *
     * @param page
     * @param searchDto
     *            ProductCategorySearchDto
     * @return PageWrapper
     * @author hand
     */
    public PageWrapper<ProductCategoryLanguageSearchDto> doSearch(int page,
            ProductCategorySearchDto searchDto, Locale locale);

    /**
     * getProductCategory
     *
     * @param id
     * @param languageCode
     * @param businessCode
     * @return ProductCategoryEditDto
     * @author hand
     */
    public ProductCategoryEditDto getProductCategory(Long id, Locale locale, String businessCode);

    /**
     * add Or Edit ProductCategory
     *
     * @param categoryEditDto
     * @param Locale locale
     * @param request
     * @author hand
     * @throws IOException
     */
    public boolean doEdit(ProductCategoryEditDto categoryEditDto, Locale locale, HttpServletRequest request) throws IOException;

    /**
     * delete ProductCategory by typeId
     *
     * @param typeId
     * @author hand
     */
    public void deleteByTypeId(Long typeId);

    /**
     * delete ProductCategory by id
     *
     * @param productCategory
     * @author hand
     */
    public void deleteProductCategory(ProductCategory productCategory);

    /**
     * find all ProductCategory not delete
     * 
     * @param typeId
     * @param languageCode
     * @return List<ProductCategoryDto>
     * @author hand
     */
    public List<ProductCategoryDto> findByTypeIdAndLanguageCode(Long typeId, String languageCode,Integer status);

    /**
     * find ProductCategory by typeId
     *
     * @return List<ProductCategory>
     * @author hand
     */
    public List<ProductCategory> findByTypeId(Long typeId);

    /**
     * find ProductCategory by code
     *
     * @param code
     * @return
     * @author hand
     */
    public ProductCategory findByCode(String code);

    /**
     * find ProductCategory by id
     *
     * @param id
     * @return ProductCategory
     * @author hand
     */
    public ProductCategory findById(Long id);

    /**
     * get max sort by TypeId
     *
     * @return
     * @author hand
     */
    Long findMaxSortByTypeId(Long typeId);

    /**
     * findListForSort
     *
     * @param languageCode
     * @param customerId
     * @return List<ProductCategoryLanguageSearchDto>
     * @author hand
     */
    public List<ProductCategoryLanguageSearchDto> findListForSort(String languageCode, Long customerId);
    
    /**
     * updateSortAll
     *
     * @param sortOderList
     * @author hand
     */
    public void updateSortAll(List<SortOrderDto> sortOderList);
    
    /**
     * find ProductCategory by alias and customerId
     * @param linkAlias
     * @param customerId
     * @return ProductCategory
     */
    public ProductCategoryLanguage findByAliasAndCustomerId(String linkAlias, String languageCode, Long customerId);

    /** getMaxCode
    *
    * @author nhutnn
    * @return max code
    */
    String getMaxCode();
    
    public List<ProductCategoryEditDto> findAllProduct(String languageCode , ProductCategoryEditDto categoryEdit);
    
    public List<Select2Dto> getProductByterm(String term, String languageCode, Long customerId);

    /** requestEditorDownload
     *
     * @param fileUrl
     * @param request
     * @param response
     * @author nhutnn
     */
    boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response);
    
    public void exportExcel(ProductCategorySearchDto searchDto, HttpServletResponse res, Locale locale);
    
    public List<ProductCategoryDto> findByTypeIdAndProductCateIdAndLanguageCode(Long typeId,Long productTypeId, String languageCode,Integer status);
    
    public int countDependencies(Long categoryId, List<Long> lstStatus);
    
    /** Get list dependencies of Product categories
     * @author tranlth - 22/05/219
     * @param categoryId
     * @param lstStatus
     * @return List<Map <String, String>>
     */
    public List<Map <String, String>> listDependencies(Long categoryId, List<Long> lstStatus);
    
    public List<ProductCategoryLanguageSearchDto> getProductCategoryList(String languageCode, Long customerId);
}
