/*******************************************************************************
 * Class        RegionRepository
 * Created date 2017/02/14
 * Lasted date  2017/02/14
 * Author       TranLTH
 * Change log   2017/02/1401-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.jcanary.dto.RegionDto;
import vn.com.unit.cms.admin.all.jcanary.dto.RegionSearchDto;
import vn.com.unit.cms.admin.all.jcanary.entity.Region;
import vn.com.unit.cms.admin.all.jcanary.entity.RegionLanguage;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;

//import vn.com.unit.jcanary.dto.RegionDto;
//import vn.com.unit.jcanary.dto.RegionSearchDto;
//import vn.com.unit.jcanary.dto.Select2Dto;
//import vn.com.unit.jcanary.entity.Region;
//import vn.com.unit.jcanary.entity.RegionLanguage;

/**
 * RegionRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface CmsRegionRepository extends DbRepository<Region, Long> {
	/**
	 * findAllRegionListByCondition
	 *
	 * @param regionDto
	 * @param languageCode
	 * @return
	 * @author TranLTH
	 */
	public List<RegionDto> findAllRegionListByCondition(@Param("countryId") Long countryId,
			@Param("languageCode") String languageCode);

	/**
	 * getRegionDtoById
	 *
	 * @param regionId
	 * @return
	 * @author TranLTH
	 */
	public RegionDto findRegionDtoById(@Param("regionId") Long regionId);

	/**
	 * countRegionByCondition
	 *
	 * @param regionSearchDto
	 * @return int
	 * @author TranLTH
	 */
	public int countRegionByCondition(@Param("regionSearchDto") RegionSearchDto regionSearchDto);

	/**
	 * findRegionLimitByCondition
	 *
	 * @param startIndex
	 * @param sizeOfPage
	 * @param regionSearchDto
	 * @return List<RegionDto>
	 * @author TranLTH
	 */
	public List<RegionDto> findRegionLimitByConditionMYSQL(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("regionSearchDto") RegionSearchDto regionSearchDto);

	/**
	 * 
	 * @param offset
	 * @param sizeOfPage
	 * @param regionSearchDto
	 * @return
	 */
	public List<RegionDto> findRegionLimitByConditionSQLServer(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("regionSearchDto") RegionSearchDto regionSearchDto);

	/**
	 * findRegionIdLanguage
	 *
	 * @param regionId
	 * @return
	 * @author TranLTH
	 */
	public List<RegionLanguage> findRegionIdLanguage(@Param("regionId") Long regionId);

	/**
	 * findRegionLimitByConditionOracle
	 *
	 * @param offset
	 * @param sizeOfPage
	 * @param regionSearchDto
	 * @return
	 * @author phatvt
	 */
	public List<RegionDto> findRegionLimitByConditionOracle(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("regionSearchDto") RegionSearchDto regionSearchDto);

	public int countRegionByConditionForOracle(@Param("regionSearchDto") RegionSearchDto regionSearchDto);

	/**
	 * findAllRegionCountry
	 *
	 * @param countryId
	 * @param language
	 * @return
	 * @author hangnkm
	 */
	public List<Select2Dto> findAllRegionCountry(@Param("language") String language);
}
