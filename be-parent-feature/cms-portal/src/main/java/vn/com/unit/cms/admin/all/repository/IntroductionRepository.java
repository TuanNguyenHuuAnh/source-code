/*******************************************************************************
 * Class        ：IntroductionRepository
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
import vn.com.unit.cms.admin.all.dto.ExportIntroductionReportDto;
import vn.com.unit.cms.admin.all.dto.IntroductionDto;
import vn.com.unit.cms.admin.all.dto.IntroductionLanguageDto;
import vn.com.unit.cms.admin.all.dto.IntroductionSearchDto;
import vn.com.unit.cms.admin.all.entity.Introduction;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
//import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
//import vn.com.unit.common.dto.Select2Dto;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;

public interface IntroductionRepository extends DbRepository<Introduction, Long> {
	/**
	 * @param offset
	 * @param sizeOfPage
	 * @return
	 */
	public List<Introduction> findAllActive(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("language") String language);

	/**
	 * 
	 * @return
	 */
	public List<Introduction> findAllActiveNoPaging(@Param("language") String language);

	/**
	 * @param condition
	 * @return
	 */
	public int countActiveByConditions(@Param("condition") IntroductionSearchDto condition,
			@Param("languageCode") String languageCode);

	public int countActive();

	/**
	 * @param cateId
	 * @return
	 */
	public int countActiveByCategory(@Param("cateId") Long cateId);

	/**
	 * @param offset
	 * @param sizeOfPage
	 * @param cateId
	 * @return
	 */
	public List<Introduction> findAllActiveByCategory(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("cateId") Long cateId);

	/**
	 * @param offset
	 * @param sizeOfPage
	 * @param condition
	 * @return
	 */
	public List<IntroductionLanguageDto> findActiveByConditions(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("condition") IntroductionSearchDto condition,
			@Param("languageCode") String languageCode);

	/**
	 * @param id
	 * @return
	 */
	public Introduction findDetailById(@Param("id") Long id);

	/**
	 * @param updateModel
	 * @return
	 */
	@Modifying
	public int updateIntroduce(@Param("intro") Introduction updateModel);

	/**
	 * @param id
	 * @param deleteBy
	 * @param deleteDate
	 * @return
	 */
	@Modifying
	public int updateDeleteFields(@Param("id") long id, @Param("deleteBy") String deleteBy,
			@Param("deleteDate") Date deleteDate);

	/**
	 * @param code
	 * @return
	 */
	public int countByCode(@Param("code") String code);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public IntroductionDto findIntroductionViewDetail(@Param("id") long id);

	/**
	 * @param categoryId
	 * @return
	 */
	public Long findMaxSort(@Param("categoryId") Long categoryId);

	/**
	 * @param id
	 * @param username
	 * @param date
	 */
	@Modifying
	public void deleteByCategoryId(@Param("categoryId") Long categoryId, @Param("deleteBy") String deleteBy,
			@Param("deleteDate") Date deleteDate);

	/**
	 * countSameLinkAliasInCategoryWithExceptedId
	 * 
	 * @param linkAlias
	 * @param categoryId
	 * @param exceptedId
	 * @return
	 */
	public int countSameLinkAliasInCategoryWithExceptedId(@Param("languageCode") String languageCode,
			@Param("linkAlias") String linkAlias, @Param("categoryId") Long categoryId,
			@Param("exceptedId") Long exceptedId);

	/**
	 * findAllActiveByCategorySort
	 *
	 * @return
	 * @author TranLTH
	 */
	public List<IntroductionDto> findAllActiveByCategorySort(@Param("customerId") Long customerId,
			@Param("language") String language, @Param("cateId") Long cateId);

	/**
	 * getMaxCode
	 *
	 * @author nhutnn
	 */
	String getMaxCode();

	public List<IntroductionDto> findAllActiveByLanguage(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("languageCode") String languageCode);

	List<Select2Dto> findListForSort(@Param("languageCode") String language, @Param("customerId") Long customerId,
			@Param("categoryId") Long categoryId, @Param("id") Long id);

	public List<ExportIntroductionReportDto> exportExcelWithCondition(@Param("condition") CommonSearchDto searchDto);

	@Modifying
	public void updateSortAll(@Param("cond") SortOrderDto sortItem);

}