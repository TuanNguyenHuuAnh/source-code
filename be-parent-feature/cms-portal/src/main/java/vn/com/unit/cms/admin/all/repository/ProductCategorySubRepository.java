/*******************************************************************************
 * Class        ：ProductCategorySubRepository
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
import vn.com.unit.cms.admin.all.dto.ExportProductCategorySubReportDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.ProductCategorySubSearchDto;
import vn.com.unit.cms.admin.all.entity.ProductCategorySub;
import vn.com.unit.cms.admin.all.entity.ProductCategorySubLanguage;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
//import vn.com.unit.common.dto.Select2Dto;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;

/**
 * ProductCategorySubRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductCategorySubRepository extends DbRepository<ProductCategorySub, Long> {

	/**
	 * count all record CategoryLanguage
	 *
	 * @param searchDto
	 * @return int
	 * @author hand
	 */
	int countByProductCategorySubSearchDto(@Param("categCond") ProductCategorySubSearchDto searchDto);

	/**
	 * find all CategoryLanguage by ProductCategorySubSearchDto
	 *
	 * @param offsetSQL
	 * @param sizeOfPage
	 * @param searchDto  ProductCategorySubSearchDto
	 * @return
	 * @author hand
	 */
	List<ProductCategorySubLanguageSearchDto> findByProductCategorySubSearchDto(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("categCond") ProductCategorySubSearchDto searchDto);

	/**
	 * find all ProductCategorySub by typeId
	 *
	 * @param typeId
	 * @author hand
	 */
	List<ProductCategorySub> findByTypeId(@Param("typeId") Long typeId);

	/**
	 * find all ProductCategorySub not delete
	 *
	 * @param typeId
	 * @param languageCode
	 * @return List<ProductCategorySubDto>
	 * @author hand
	 */
	List<ProductCategorySubDto> findByTypeIdAndLanguageCode(@Param("typeId") Long typeId,
			@Param("languageCode") String languageCode);

	/**
	 * find ProductCategorySub by code
	 *
	 * @param code
	 * @return
	 * @author hand
	 */
	ProductCategorySub findByCode(@Param("code") String code);

	/**
	 * get max sort by TypeId and categoryId
	 *
	 * @param typeId
	 * @param categoryId
	 * @return
	 * @author hand
	 */
	Long findMaxSortByTypeIdAndCategoryId(@Param("typeId") Long typeId, @Param("categoryId") Long categoryId);

	/**
	 * findByTypeIdAndCategoryId
	 *
	 * @param typeId
	 * @param categoryId
	 * @param languageCode
	 * @return List<ProductCategorySubDto>
	 * @author hand
	 */
	List<ProductCategorySubDto> findByTypeIdAndCategoryId(@Param("typeId") Long typeId,
			@Param("categoryId") Long categoryId, @Param("languageCode") String languageCode,
			@Param("status") Integer status);

	/**
	 * findByTypeIdAndCategoryId
	 *
	 * @param typeId
	 * @param categoryId
	 * @param languageCode
	 * @return List<ProductCategorySubDto>
	 * @author hand
	 */
	List<ProductCategorySubDto> findByTypeIdAndListCategoryId(@Param("typeId") Long typeId,
			@Param("categoryListId") List<Long> categoryId, @Param("languageCode") String languageCode,
			@Param("status") Integer status);

	/**
	 * findListForSort
	 *
	 * @param languageCode
	 * @param customerId
	 * @param categoryId
	 * @return
	 * @author TranLTH
	 */
	List<ProductCategorySubLanguageSearchDto> findListForSort(@Param("languageCode") String languageCode,
			@Param("customerId") Long customerId, @Param("categoryId") Long categoryId);

	/**
	 * updateSortAll
	 *
	 * @param sortOderList
	 * @author TranLTH
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
	public ProductCategorySubLanguage findByAliasAndCustomerId(@Param("linkAlias") String linkAlias,
			@Param("languageCode") String languageCode, @Param("customerId") Long customerId,
			@Param("typeId") Long typeId);

	/**
	 * getMaxCode
	 *
	 * @author nhutnn
	 */
	String getMaxCode();

	/**
	 * findAllProductCategorySubForSort
	 *
	 * @param languageCode
	 * @param customerTypeId
	 * @param productTypeId
	 * @param id
	 * @author nhutnn
	 */
	public List<ProductCategorySub> findAllProductCategorySubForSort(@Param("languageCode") String languageCode,
			@Param("customerTypeId") Long customerTypeId, @Param("productTypeId") Long productTypeId,
			@Param("id") Long id);

	List<Select2Dto> findListForSort2(@Param("languageCode") String languageCode, @Param("customerId") Long customerId,
			@Param("categoryId") Long categoryId, @Param("typeId") Long typeId);

	List<ProductCategorySub> findByCustomerIdAndCategoryId(@Param("customerId") Long customerId,
			@Param("productTypeId") Long productTypeId);

	List<ProductCategorySubDto> findByTypeIdAndListCategoryIdForEdit(@Param("typeId") Long typeId,
			@Param("categoryListId") List<Long> categoryId, @Param("languageCode") String languageCode,
			@Param("status") Integer status);

	public List<ExportProductCategorySubReportDto> exportExcelWithCondition(
			@Param("categCond") ProductCategorySubSearchDto searchDto);

	public Long countByProductCategoryId(@Param("productCategoryId") Long productCategoryId);

	/**
	 * getListProductCategorySubDto
	 */
	List<ProductCategorySubDto> getListProductCategorySubDto(@Param("customerId") Long customerId,
			@Param("productCategoryId") Long productCategoryId, @Param("languageCode") String languageCode,
			@Param("status") Integer status);

	public int countDependencies(@Param("categorySubId") Long categorySubId, @Param("lstStatus") List<Long> lstStatus);

	/**
	 * Danh sách tổng số con phụ thuộc
	 * 
	 * @author tranlth - 23/05/2019
	 * @param categorySubId
	 * @param lstStatus
	 * @param listFlag
	 * @return List<Map <String, String>>
	 */
	public List<Map<String, String>> listDependencies(@Param("categorySubId") Long categorySubId,
			@Param("lstStatus") List<Long> lstStatus);

}