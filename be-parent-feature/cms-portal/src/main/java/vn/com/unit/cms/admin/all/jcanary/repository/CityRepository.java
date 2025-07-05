/*******************************************************************************
 * Class        CityRepository
 * Created date 2017/02/17
 * Lasted date  2017/02/17
 * Author       TranLTH
 * Change log   2017/02/1701-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.jcanary.dto.CityDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CitySearchDto;
import vn.com.unit.cms.admin.all.jcanary.entity.City;
import vn.com.unit.cms.admin.all.jcanary.entity.CityLanguage;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
//import vn.com.unit.jcanary.dto.CityDto;
//import vn.com.unit.jcanary.dto.CitySearchDto;
//import vn.com.unit.jcanary.dto.Select2Dto;
//import vn.com.unit.jcanary.entity.City;
//import vn.com.unit.jcanary.entity.CityLanguage;

/**
 * CityRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface CityRepository extends DbRepository<City, Long> {
	/**
	 * findAllCityListByCondition
	 *
	 * @param CitcityDtoyDto
	 * @param languageCode
	 * @return List<City>
	 * @author TranLTH
	 */
	public List<CityDto> findAllCityListByCondition(@Param("cityDto") CityDto cityDto,
			@Param("languageCode") String languageCode);

	public List<CityDto> findAllCityListByConditionOracle(@Param("cityDto") CityDto cityDto,
			@Param("languageCode") String languageCode);

	/**
	 * getCityDtoById
	 *
	 * @param cityId
	 * @return CityDto
	 * @author TranLTH
	 */
	public CityDto findCityDtoById(@Param("cityId") Long cityId);

	/**
	 * Find city dto by id for Oracle
	 * 
	 * @param cityId *
	 * @return CityDto
	 * @author trieunh
	 */
	public CityDto findCityDtoByIdOracle(@Param("cityId") Long cityId);

	/**
	 * countCityByCondition
	 *
	 * @param citySearchDto
	 * @return int
	 * @author TranLTH
	 */
	public int countCityByCondition(@Param("citySearchDto") CitySearchDto citySearchDto);

	/**
	 * findCityLimitByCondition
	 *
	 * @param startIndex
	 * @param sizeOfPage
	 * @param citySearchDto
	 * @return List<CityDto>
	 * @author TranLTH
	 */
	public List<CityDto> findCityLimitByConditionMYSQL(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("citySearchDto") CitySearchDto citySearchDto);

	/**
	 * 
	 * @param offset
	 * @param sizeOfPage
	 * @param citySearchDto
	 * @return
	 */
	public List<CityDto> findCityLimitByConditionSQLServer(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("citySearchDto") CitySearchDto citySearchDto);

	/**
	 * findCityIdLanguage
	 *
	 * @param cityId
	 * @return List<CityLanguage>
	 * @author TranLTH
	 */
	public List<CityLanguage> findCityIdLanguage(@Param("cityId") Long cityId);

	public List<CityLanguage> findAllCityLanguageByLanguageCode(@Param("languageCode") String languageCode);

	/**
	 * findAllCityList
	 *
	 * @param languageCode
	 * @return List<CityDto>
	 * @author hand
	 */
	public List<CityDto> findAllCityList(@Param("languageCode") String languageCode);

	public List<CityDto> findAllCityListOracle(@Param("languageCode") String languageCode);

	/**
	 * findCityLimitByConditionOracle
	 *
	 * @param offset
	 * @param sizeOfPage
	 * @param citySearchDto
	 * @return
	 * @author phatvt
	 */
	public List<CityDto> findCityLimitByConditionOracle(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("citySearchDto") CitySearchDto citySearchDto);

	public int countCityByConditionForOracle(@Param("citySearchDto") CitySearchDto citySearchDto);

	/**
	 * @param term
	 * @param nationalCode
	 * @param language
	 * @return
	 * @author longpnt
	 */
	public List<Select2Dto> findCityByCondition(@Param("countryCode") String countryCode,
			@Param("language") String language);

	/**
	 * @param term
	 * @param nationalCode
	 * @param language
	 * @return
	 * @author longpnt
	 */
	public List<Select2Dto> findCityByConditionForOracle(@Param("countryCode") String countryCode,
			@Param("language") String language);

	/**
	 * findAllCityForSelect2
	 * 
	 * @param term
	 * @return List<Select2Dto>
	 * @author longpnt
	 */
	public List<Select2Dto> findAllCityForSelect2(@Param("term") String term);

	/**
	 * findAllCity
	 *
	 * @param language
	 * @return
	 * @author hangnkm
	 */
	public List<Select2Dto> findAllCity(@Param("language") String language);

	/**
	 * findAllCityByRegionId
	 *
	 * @param regionId
	 * @return
	 * @author hangnkm
	 */
	public List<Select2Dto> findAllCityByRegionId(@Param("languageCode") String language,
			@Param("regionId") Long regionId, @Param("cType") List<String> ctype);

}
