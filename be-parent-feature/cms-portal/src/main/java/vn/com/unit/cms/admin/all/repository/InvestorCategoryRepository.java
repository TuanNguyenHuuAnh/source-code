package vn.com.unit.cms.admin.all.repository;

import java.util.List;
import java.util.Map;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.InvestorCategoryDto;
import vn.com.unit.cms.admin.all.dto.InvestorCategoryLanguageDto;
import vn.com.unit.cms.admin.all.dto.InvestorCategorySearchDto;
import vn.com.unit.cms.admin.all.entity.InvestorCategory;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;

public interface InvestorCategoryRepository extends DbRepository<InvestorCategory, Long> {

	/**
	 * countInvestorCategory
	 * 
	 * @author ThuyNTB
	 */
	int countInvestorCategory(@Param("searchDto") InvestorCategorySearchDto searchDto);

	String countInvestorCategoryLocationLeft(@Param("id") Long id, @Param("customerId") Long customerId);

	String countInvestorCategoryLocationRightTop(@Param("id") Long id, @Param("customerId") Long customerId);

	String countInvestorCategoryLocationRightBottom(@Param("id") Long id, @Param("customerId") Long customerId);

	Integer countAllInvestorCategoryLocationLeft(@Param("customerId") Long customerId);

	Integer countAllInvestorCategoryLocationRightTop(@Param("customerId") Long customerId);

	Integer countAllInvestorCategoryLocationRightBottom(@Param("customerId") Long customerId);

	/**
	 * getListInvestorCategory_Screen List
	 * 
	 * @author ThuyNTB
	 */
	List<InvestorCategoryDto> getListInvestorCategory(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("searchDto") InvestorCategorySearchDto searchDto);

	List<InvestorCategoryDto> findListSortRemovedId(@Param("id") Long id, @Param("parentId") Long parentId,
			@Param("lang") String lang);

	List<InvestorCategoryDto> findAllRootActive(@Param("language") String language, @Param("status") Integer status);

	public List<InvestorCategoryDto> findAllActiveChildrenCategoryForSelection(@Param("parentIds") List<Long> parentIds,
			@Param("lang") String lang, @Param("status") Integer status);

	public List<InvestorCategoryDto> findAllActiveChildrenCategory(@Param("parentIds") List<Long> parentIds,
			@Param("lang") String lang, @Param("status") Integer status);

	public InvestorCategoryDto findInvestorCategoryDtoById(@Param("id") Long id, @Param("status") Integer status,
			@Param("language") String language);

	public InvestorCategory findInvestorCategoryByCode(@Param("code") String code);

	InvestorCategoryLanguageDto findByAliasAndCategoryId(@Param("categoryId") Long categoryId,
			@Param("customerId") Long customerId, @Param("linkAlias") String linkAlias,
			@Param("language") String language);

	List<InvestorCategoryDto> findAllInvestorCategoryForSort(@Param("searchDto") InvestorCategorySearchDto searchDto,
			@Param("language") String language);

	InvestorCategoryDto findLevelIdByParentId(@Param("searchDto") InvestorCategorySearchDto searchDto,
			@Param("language") String language);

	public List<InvestorCategoryDto> checkExistChildrenIdById(@Param("id") Long id,
			@Param("customerId") Long customerId);

	public List<InvestorCategoryDto> exportExcelWithCondition(@Param("searchDto") InvestorCategorySearchDto searchDto);

	public List<InvestorCategoryDto> findInvestorCategoryForSort(
			@Param("searchDto") InvestorCategorySearchDto searchDto);

	@Modifying
	public void updateInvestorCategoryChildSortAll(@Param("cond") SortOrderDto sortItem);

	/**
	 * check relationship with M_INVESTOR_CATEGORY, M_INVESTOR
	 * 
	 * @param customerId
	 * @param id
	 * @return
	 */
	public boolean checkRelationshipChild(@Param("customerId") Long customerId, @Param("id") Long id);

	public InvestorCategoryDto getMaxSortInvestorCate();

	/**
	 * Danh sách tổng số con phụ thuộc
	 * 
	 * @author tranlth - 23/05/2019
	 * @param customerId
	 * @param id
	 * 
	 * @return List<Map <String, String>>
	 */
	public List<Map<String, String>> listDependencies(@Param("customerId") Long customerId, @Param("id") Long id);

}
