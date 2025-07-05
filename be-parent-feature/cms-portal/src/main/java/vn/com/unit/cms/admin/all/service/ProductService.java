/*******************************************************************************
 * Class        ：ProductService
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.dto.ProductCategoryDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubDto;
import vn.com.unit.cms.admin.all.dto.ProductEditDto;
import vn.com.unit.cms.admin.all.dto.ProductLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.ProductSearchDto;
//import vn.com.unit.common.dto.Select2Dto;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.cms.admin.all.entity.Product;
import vn.com.unit.cms.admin.all.entity.ProductLanguage;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

/**
 * ProductService
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductService extends DocumentWorkflowCommonService<ProductEditDto, ProductEditDto> {
    /**
     * search Product list
     *
     * @param page
     * @param productSearch
     *            ProductSearchDto
     * @return PageWrapper<ProductLanguageSearchDto>
     * @author hand
     */
    PageWrapper<ProductLanguageSearchDto> search(int page, ProductSearchDto productSearch, Locale locale);

    /**
     * init screen product edit/add
     *
     * @param mav
     *            ModelAndView
     * @param languageCode
     * @author hand
     */
    public void initProductEdit(ModelAndView mav, ProductEditDto productEdit, Locale locale);

    /**
     * delete Product by category id
     *
     * @param typeId
     * @author hand
     */
    public void deleteProductByCategoryId(Long typeId);

    /**
     * add Or Edit Product
     *
     * @param ProductEditDto
     * @param doEdit
     *            : true is saveDraft, false is sendRequest
     * @author hand
     * @throws IOException
     */
    public boolean doEdit(ProductEditDto productEditDto, Locale locale, HttpServletRequest request) throws IOException;

    /**
     * get ProductEditDto
     *
     * @param id
     *            Long
     * @param action
     *            boolean: true is edit, false is detail
     * @param languageCode
     * @return ProductEditDto
     * @author hand
     */
    public ProductEditDto getProduct(Long id, boolean action, Locale locale, String customerAlias);

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
     * find Product by code
     *
     * @param code
     * @return
     * @author hand
     */
    public Product findByCode(String code);

    /**
     * delete Product by entity
     *
     * @author hand
     */
    public void deleteById(Product product);

    /**
     * init screen product detail
     *
     * @param mav
     *            ModelAndView
     * @author hand
     */
    public void initProductDetail(ModelAndView mav);

    /**
     * updateProcess
     *
     * @param id
     * @param processEnum
     * @author hand
     */
//    public void updateProcess(ProductEditDto productEdit, ProductProcessEnum processEnum);

    /**
     * find Product by id
     *
     * @param id
     * @return Product
     * @author hand
     */
    public Product findById(Long id);

    /**
     * findMaxSortByTypeAndCategory
     *
     * @param typeId
     * @param categoryId
     * @param categorySubId
     * @return maxSort
     * @author hand
     */
    Long findMaxSortByTypeAndCategory(Long typeId, Long categoryId, Long categorySubId);

    /**
     * @param fileUrl
     * @param request
     * @param response
     * @return
     */
    public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response);

    /**
     * getCategorySubSelectJson
     *
     * @param typeId
     * @param categoryId
     * @param languageCode
     * @return
     * @author hand
     */
    public String getCategorySubSelectJson(Long typeId, Long categoryId, String languageCode,Integer status);

    /**
     * find Product by alias and customerId and categoryId
     * 
     * @param linkAlias
     * @param customerId
     * @param categoryId
     * @return Product
     */
    public ProductLanguage findByAliasCustomerIdCategoryId(String linkAlias, String languageCode, Long customerId, Long categoryId, Long typeId);
    /**
     * getCategoryByCustomerId
     *
     * @param typeId
     * @param languageCode
     * @return
     * @author phunghn
     */
    public List<ProductCategoryDto> getCategoryByCustomerId(Long typeId,  String languageCode);
    
    
    /**
     * getCategorySubByCategoryAndCustomerId
     *
     * @param typeId
     * @param categoryId
     * @param languageCode
     * @return
     * @author phunghn
     */
    public List<ProductCategorySubDto> getCategorySubByCategoryAndCustomerId(Long typeId, Long categoryId, String languageCode,Integer Status);
    
    /**
     * findListProductForSorting
     *
     * @param typeId
     * @param categoryId
     * @param categorySubId
     * @param languageCode
     * @return
     * @author phunghn
     */
    List<ProductLanguageSearchDto> findListProductForSorting(Long typeId, Long categoryId,
            Long categorySubId, String languageCode,Integer status);
    
    /**
     * saveProductOfSorting
     *
     * @param list
     * @return
     * @author phunghn
     */
    
    Boolean saveProductOfSorting(List<SortOrderDto> list);

    /** getMaxCode
     *
     * @return max code
     * @author nhutnn
     */
    String getMaxCode();

    /** findListProductDtoForSort
     *
     * @param searchDto
     * @param languageCode
     * @author nhutnn
     */
    List<ProductEditDto> findListProductDtoForSort(ProductSearchDto searchDto, String languageCode,Integer status);

    /** findAllProductForSort
     *
     * @param searchDto
     * @param languageCode
     * @author nhutnn
     */
    List<Product> findAllProductForSort(ProductSearchDto searchDto, String languageCode);

    /** getBeforeIdSelectJson
     *
     * @param id
     * @param typeId 
     * @param categoryId
     * @param categorySubId
     * @param languageCode
     * @author nhutnn
     */
    public String getBeforeIdSelectJson(Long id, Long typeId, Long categoryId, Long categorySubId, String languageCode,Integer status);
    
    String getProductSelectJson(Long customerId, Long categoryId, Long categorySubId, String languageCode);
    
    String getProductSelectJsonByList(Long customerId, List<Long> categoryId, List<Long> categorySubId, String languageCode);

    /** getListCategorySubSelectJsonByListCategory
     *
     * @param typeId
     * @param categoryListId
     * @param languageCode
     * @author nhutnn
     */
    String getListCategorySubSelectJsonByListCategory(Long typeId, List<Long> categoryListId, String languageCode,Integer status);

    /** getProductSelectJsonByListSubId
     *
     * @param customerId
     * @param categorySubListId
     * @param languageCode
     * @author nhutnn
     */
    String getProductSelectJsonByListSubId(Long customerId, Long categorySubListId, String languageCode,Integer status);

    /** findListProductByListSubId
     *
     * @param customerId
     * @param categorySubListIdd
     * @param languageCode
     * @author nhutnn
     */
    List<ProductLanguageSearchDto> findListProductByListSubId(Long customerId, Long categorySubListId, String languageCode,Integer status);

    /** getProductDtoMicrosite
     *
     * @param languageCode
     * @author nhutnn
     */
    ProductEditDto getProductDtoMicrosite(String languageCode);
    
    String getListCategorySubSelectJsonByListCategoryForEdit(Long typeId, List<Long> categoryListId, String languageCode,Integer status);
    
    String getProductSelectJsonByListSubIdForEdit(Long customerId, List<Long> categoryListId, List<Long> categorySubListId, String languageCode,Integer status);
    
    public void exportExcel(ProductSearchDto searchDto, HttpServletResponse res, Locale locale);
    
    public Long countByProductCategorySubId(Long productCategorySubId);
    
    public Long countByBannerDesktop(Long bannerId);
    
    public Long countByBannerMobile(Long bannerId);
    
	public List<Select2Dto> findListProductForSort2(String languageCode, Long customerId, Long productId, Long categoryId,
			Long categorySubId);
	
	public int countDependencies(Long productId, List<Long> lstStatus);
	
	/** Get list dependencies of Product categories
     * @author tranlth - 23/05/219
     * @param categorySubId
     * @param lstStatus
     * @return List<Map <String, String>>
     */
    public List<Map <String, String>> listDependencies(Long productId, List<Long> lstStatus);
}
