/*******************************************************************************
 * Class        ：IntroduceManagementRepository
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：thuydtn
 * Change log   ：2017/02/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.ExportIntroductionCategoryReportDto;
import vn.com.unit.cms.admin.all.dto.IntroductionCategoryDto;
import vn.com.unit.cms.admin.all.dto.IntroductionCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.IntroductionLanguageDto;
import vn.com.unit.cms.admin.all.entity.IntroductionCategory;
//import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;

public interface IntroductionCategoryRepository extends DbRepository<IntroductionCategory, Long> {

	public List<IntroductionCategoryDto> findAllActive(@Param("languageCode") String languageCode,
			@Param("status") Integer status);

	/**
	 * 
	 * @param offset
	 * @param sizeOfPage
	 * @return
	 */
	public List<IntroductionCategoryDto> findRootActive(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("lang") String lang);

	public List<IntroductionCategoryDto> findAllRootActive(@Param("lang") String lang);

	public List<IntroductionCategoryDto> findAllActiveChildrenCategory(@Param("parentIds") List<Long> parentIds,
			@Param("lang") String lang);

	/**
	 * 
	 * @param offset
	 * @param sizeOfPage
	 * @param condition
	 * @return
	 */
	public List<IntroductionCategoryDto> findRootActiveByConditions(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("condition") IntroductionCategorySearchDto condition,
			@Param("languageCode") String languageCode);

	/**
	 * 
	 * @param condition
	 * @return
	 */
	public int countRootActiveByConditions(@Param("condition") IntroductionCategorySearchDto condition,
			@Param("languageCode") String languageCode);

	public int countRootActive();

	public IntroductionCategoryDto findDetailById(@Param("id") Long id, @Param("languageCode") String lang);

	/**
	 * 
	 * @param introCateEntity
	 * @return
	 */
	@Modifying
	public int updateIntroduction(@Param("introCate") IntroductionCategory introCateEntity);

	/**
	 * 
	 * @param id
	 * @param deleteBy
	 * @param deleteDate
	 * @return
	 */
	@Modifying
	public int updateDeleteFields(@Param("id") Long id, @Param("deleteBy") String deleteBy,
			@Param("deleteDate") Date deleteDate);

	public List<IntroductionCategoryDto> findRootListForSelection();

	/**
	 * 
	 * @param parentIds
	 * @return
	 */
	public List<IntroductionCategoryDto> findAllActiveChildrenCategoryForSelection(
			@Param("parentIds") List<Long> parentIds, @Param("String") String lang);

	/**
	 * @param code
	 * @return count
	 */
	public int countByCode(@Param("code") String code);

	/**
	 * @param id
	 * @return
	 */
	public IntroductionCategoryDto findCategoryViewDetail(@Param("id") Long id);

	/**
	 * findMaxSort
	 * 
	 * @param parentId
	 * @return
	 */
	public Long findMaxSort(@Param("parentId") Long parentId);

	/**
	 * findActiveSelectionList
	 * 
	 * @return
	 */
	public List<IntroductionCategoryDto> findAllActiveForSelection();

	/**
	 * countLinkAliasByParentAndIntroductionTypeExceptId
	 *
	 * @param languageCode
	 * @param customerId
	 * @param linkAlias
	 * @param exceptedId
	 * @param parentId
	 */
	public int countLinkAliasByParentAndIntroductionTypeExceptId(@Param("languageCode") String languageCode,
			@Param("linkAlias") String linkAlias, @Param("exceptedId") Long exceptedId,
			@Param("parentId") Long parentId);

	/**
	 * getMaxCode
	 *
	 * @author nhutnn
	 */
	String getMaxCode();

	public List<IntroductionCategoryDto> findCategoryByCondition(@Param("startIndex") int startIndex,
			@Param("sizeOfPage") int sizeOfPage, @Param("condition") IntroductionCategorySearchDto condition,
			@Param("languageCode") String languageCode);

	public List<IntroductionCategoryDto> findListSortRemovedId(@Param("id") Long id, @Param("parentId") Long parentId,
			@Param("lang") String lang);

	public IntroductionCategory findIntroductionByType(@Param("customerTypeId") Long customerTypeId,
			@Param("typeOfMain") Integer typeOfMain, @Param("pictureIntroduction") Integer pictureIntroduction);

	public List<IntroductionLanguageDto> findListForSort(@Param("customerTypeId") Long customerTypeId,
			@Param("language") String language);

	@Modifying
	public void updateSortAll(@Param("cond") SortOrderDto sortOderList);

	public List<ExportIntroductionCategoryReportDto> exportExcelWithCondition(
			@Param("condition") CommonSearchDto searchDto);

}