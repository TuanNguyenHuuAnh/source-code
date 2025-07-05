/*******************************************************************************
 * Class        ：ProductRepository
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;
import java.util.Map;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.ExportProductReportDto;
import vn.com.unit.cms.admin.all.dto.ProductEditDto;
import vn.com.unit.cms.admin.all.dto.ProductLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.ProductSearchDto;
import vn.com.unit.cms.admin.all.entity.Product;
import vn.com.unit.cms.admin.all.entity.ProductLanguage;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
//import vn.com.unit.common.dto.Select2Dto;

/**
 * ProductRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductRepository extends DbRepository<Product, Long> {

	/**
	 * getIdProductMicrosite
	 *
	 * @author nhutnn
	 */
	ProductEditDto getProductDtoMicrosite(@Param("language") String language);

	/**
	 * updateProductOffMicrosite
	 *
	 * @param idProduct
	 * @author nhutnn
	 */
	@Modifying
	void updateProductOffMicrosite(@Param("idProduct") Long idProduct);

	/**
	 * count all record ProductLanguage
	 *
	 * @param searchDto
	 * @return int
	 * @author hand
	 */
	int countByProductSearchDto(@Param("searchCond") ProductSearchDto searchDto);

	/**
	 * find all ProductLanguage by ProductSearchDto
	 *
	 * @param offsetSQL
	 * @param sizeOfPage
	 * @param searchDto  ProductSearchDto
	 * @return
	 * @author hand
	 */
	List<ProductLanguageSearchDto> findByProductSearchDto(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("searchCond") ProductSearchDto searchDto);

	/**
	 * find all Product by categoryId
	 *
	 * @param typeId
	 * @return
	 * @author hand
	 */
	List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

	/**
	 * find Product by code
	 *
	 * @param code
	 * @return
	 * @author hand
	 */
	Product findByCode(@Param("code") String code);

	/**
	 * findMaxSortByTypeAndCategory
	 *
	 * @param typeId
	 * @param categoryId
	 * @param categorySubId
	 * @return maxSort
	 * @author hand
	 */
	Long findMaxSortByTypeAndCategory(@Param("typeId") Long typeId, @Param("categoryId") Long categoryId,
			@Param("categorySubId") Long categorySubId);

	/**
	 * findByAliasCustomerIdCategoryId
	 * 
	 * @param linkAlias
	 * @param customerId
	 * @param categoryId
	 * @return
	 */
	ProductLanguage findByAliasCustomerIdCategoryId(@Param("linkAlias") String linkAlias,
			@Param("languageCode") String languageCode, @Param("customerId") Long customerId,
			@Param("categoryId") Long categoryId, @Param("typeId") Long typeId);

	List<ProductLanguageSearchDto> findListProductForSorting(@Param("typeId") Long typeId,
			@Param("categoryId") Long categoryId, @Param("categorySubId") Long categorySubId,
			@Param("languageCode") String languageCode, @Param("status") Integer status);

	List<ProductLanguageSearchDto> findListProductForSortingByList(@Param("customerId") Long customerId,
			@Param("categoryListId") List<Long> categoryId, @Param("categorySubListId") List<Long> categorySubId,
			@Param("languageCode") String languageCode);

	/*
	 * findListProductByListSubId Get List Product for list
	 * 
	 * @author thuyntb
	 */
	List<ProductLanguageSearchDto> findListProductByListSubId(@Param("typeId") Long typeId,
			@Param("categorySubListId") Long categorySubListId, @Param("languageCode") String languageCode,
			@Param("status") Integer status);

	/**
	 * getMaxCode
	 *
	 * @author nhutnn
	 */
	String getMaxCode();

	/**
	 * findAllProductForSort
	 *
	 * @param languageCode
	 * @param categoryEdit
	 * @author nhutnn
	 */
	List<Product> findAllProductForSort(@Param("languageCode") String languageCode,
			@Param("categoryEdit") ProductSearchDto categoryEdit);

	/**
	 * findListProductDtoForSort
	 *
	 * @param languageCode
	 * @param categoryEdit
	 * @author nhutnn
	 */
	List<ProductEditDto> findListProductDtoForSort(@Param("languageCode") String languageCode,
			@Param("categoryEdit") ProductSearchDto categoryEdit, @Param("status") Integer status);

	/**
	 * findByTypeId
	 *
	 * @param typeId
	 * @author nhutnn
	 */
	List<Product> findByTypeId(@Param("typeId") Long typeId);

	List<ProductLanguageSearchDto> findListProductByListSubIdForEdit(@Param("typeId") Long typeId,
			@Param("categoryListId") List<Long> categoryListId,
			@Param("categorySubListId") List<Long> categorySubListId, @Param("languageCode") String languageCode,
			@Param("status") Integer status);

	public List<ExportProductReportDto> exportExcelWithCondition(@Param("searchCond") ProductSearchDto searchDto);

	public Long countByProductCategorySubId(@Param("productCategorySubId") Long productCategorySubId);

	public Long countByBannerDesktop(@Param("bannerId") Long bannerId);

	public Long countByBannerMobile(@Param("bannerId") Long bannerId);

	/**
	 * get List BeforeId of Product
	 */
	List<Select2Dto> findListProductForSort2(@Param("languageCode") String languageCode,
			@Param("customerId") Long customerId, @Param("productId") Long productId,
			@Param("categoryId") Long categoryId, @Param("categorySubId") Long categorySubId);

	public int countDependencies(@Param("productId") Long productId, @Param("lstStatus") List<Long> lstStatus);

	/**
	 * Danh sách tổng số con phụ thuộc
	 * 
	 * @author tranlth - 23/05/2019
	 * @param productId
	 * @param lstStatus
	 * 
	 * @return List<Map <String, String>>
	 */
	public List<Map<String, String>> listDependencies(@Param("productId") Long productId,
			@Param("lstStatus") List<Long> lstStatus);

}
