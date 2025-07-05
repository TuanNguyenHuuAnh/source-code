/*******************************************************************************
 * Class        ：ProductCategoryRepository
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;
import java.util.Map;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.ExportProductCategoryReportDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.ProductCategoryLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySearchDto;
import vn.com.unit.cms.admin.all.entity.ProductCategory;
import vn.com.unit.cms.admin.all.entity.ProductCategoryLanguage;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
//import vn.com.unit.common.dto.Select2Dto;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;

/**
 * ProductCategoryRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductCategoryRepository extends DbRepository<ProductCategory, Long> {

	/**
	 * count all record CategoryLanguage
	 *
	 * @param searchDto
	 * @return int
	 * @author hand
	 */
	int countByProductCategorySearchDto(@Param("categCond") ProductCategorySearchDto searchDto);

	/**
	 * find all CategoryLanguage by ProductCategorySearchDto
	 *
	 * @param offsetSQL
	 * @param sizeOfPage
	 * @param searchDto  ProductCategorySearchDto
	 * @return
	 * @author hand
	 */
	List<ProductCategoryLanguageSearchDto> findByProductCategorySearchDto(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("categCond") ProductCategorySearchDto searchDto);

	/**
	 * find all ProductCategory by typeId
	 *
	 * @param typeId
	 * @author hand
	 */
	List<ProductCategory> findByTypeId(@Param("typeId") Long typeId);

	/**
	 * find all ProductCategory not delete
	 *
	 * @param typeId
	 * @param languageCode
	 * @return List<ProductCategoryDto>
	 * @author hand
	 */
	List<ProductCategoryDto> findByTypeIdAndLanguageCode(@Param("typeId") Long typeId,
			@Param("languageCode") String languageCode, @Param("status") Integer status);

	/**
	 * find ProductCategory by code
	 *
	 * @param code
	 * @return
	 * @author hand
	 */
	ProductCategory findByCode(@Param("code") String code);

	/**
	 * get max sort by TypeId
	 *
	 * @return
	 * @author hand
	 */
	Long findMaxSortByTypeId(@Param("typeId") Long typeId);

	/**
	 * findListForSort
	 *
	 * @param languageCode
	 * @param customerId
	 * @return List<ProductCategoryLanguageSearchDto>
	 * @author hand
	 */
	List<ProductCategoryLanguageSearchDto> findListForSort(@Param("languageCode") String languageCode,
			@Param("customerId") Long customerId);

	/**
	 * updateSortAll
	 *
	 * @param sortOderList
	 * @author hand
	 */
	@Modifying
	public void updateSortAll(@Param("cond") SortOrderDto sortOderList);

	/**
	 * find ProductCategory by alias and customerId
	 * 
	 * @param linkAlias
	 * @param customerId
	 * @return ProductCategory
	 */
	public ProductCategoryLanguage findByAliasAndCustomerId(@Param("linkAlias") String linkAlias,
			@Param("languageCode") String languageCode, @Param("customerId") Long customerId);

	/**
	 * getMaxCode
	 *
	 * @author nhutnn
	 */
	String getMaxCode();

	/**
	 * findAllProduct
	 *
	 * @param languageCode
	 * @return
	 * @author hangnkm
	 */
	public List<ProductCategoryEditDto> findAllProduct(@Param("languageCode") String languageCode,
			@Param("categoryEdit") ProductCategoryEditDto categoryEdit);

	/**
	 * findAllProductTypeForSort
	 *
	 * @param languageCode
	 * @param customerTypeId
	 * @param id
	 * @author nhutnn
	 */
	public List<ProductCategory> findAllProductTypeForSort(@Param("languageCode") String languageCode,
			@Param("customerTypeId") Long customerTypeId, @Param("id") Long id);

	public List<Select2Dto> getProductByterm(@Param("term") String term, @Param("languageCode") String languageCode,
			@Param("customerId") Long customerId);

	public List<ExportProductCategoryReportDto> exportExcelWithCondition(
			@Param("categCond") ProductCategorySearchDto searchDto);

	/**
	 * @author ThuyNTB
	 * @return description: Trả về list loại sản phẩm với productCategoryId
	 *         ProductCategoryRepository_findByTypeIdAndProductCateIdAndLanguageCode
	 */
	List<ProductCategoryDto> findByTypeIdAndProductCateIdAndLanguageCode(@Param("typeId") Long typeId,
			@Param("productTypeId") Long productTypeId, @Param("languageCode") String languageCode,
			@Param("status") Integer status);

	/**
	 * Danh sách tổng số con phụ thuộc
	 * 
	 * @author tranlth - 22/05/2019
	 * @param categoryId
	 * @param lstStatus
	 * @param listFlag
	 * @return List<Map <String, String>>
	 */
	public List<Map<String, String>> listDependencies(@Param("categoryId") Long categoryId,
			@Param("lstStatus") List<Long> lstStatus);

	public int countDependencies(@Param("categoryId") Long categoryId, @Param("lstStatus") List<Long> lstStatus);

	List<ProductCategoryLanguageSearchDto> findProductCategoryList(@Param("language") String language,
			@Param("customerId") Long customerId);

}
