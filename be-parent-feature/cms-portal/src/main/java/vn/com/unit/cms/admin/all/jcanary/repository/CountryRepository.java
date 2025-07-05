/*******************************************************************************
 * Class        CountryRepository
 * Created date 2017/02/23
 * Lasted date  2017/02/23
 * Author       TranLTH
 * Change log   2017/02/2301-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.jcanary.dto.CountryDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CountrySearchDto;
import vn.com.unit.cms.admin.all.jcanary.entity.Country;
import vn.com.unit.cms.admin.all.jcanary.entity.CountryLanguage;
import vn.com.unit.db.repository.DbRepository;

//import vn.com.unit.jcanary.dto.CountryDto;
//import vn.com.unit.jcanary.dto.CountrySearchDto;
//import vn.com.unit.jcanary.entity.Country;
//import vn.com.unit.jcanary.entity.CountryLanguage;

/**
 * CountryRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface CountryRepository extends DbRepository<Country, Long> {
	/**
	 * countCountryByCondition
	 *
	 * @param countrySearchDto
	 * @return
	 * @author TranLTH
	 */
	public int countCountryByCondition(@Param("countrySearchDto") CountrySearchDto countrySearchDto);

	/**
	 * findCountryLimitByCondition
	 *
	 * @param startIndex
	 * @param sizeOfPage
	 * @param countrySearchDto
	 * @return List<CountryDto>
	 * @author TranLTH
	 */
	public List<CountryDto> findCountryLimitByConditionMYSQL(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("countrySearchDto") CountrySearchDto countrySearchDto);

	/**
	 * 
	 * @param offset
	 * @param sizeOfPage
	 * @param countrySearchDto
	 * @return
	 */
	public List<CountryDto> findCountryLimitByConditionSQLServer(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("countrySearchDto") CountrySearchDto countrySearchDto);

	/**
	 * findAllCountryListByCondition
	 *
	 * @param countryDto
	 * @return List<CountryDto>
	 * @author TranLTH
	 */
	public List<CountryDto> findAllCountryListByCondition(@Param("countryDto") CountryDto countryDto,
			@Param("languageCode") String languageCode);

	/**
	 * getCountryDtoById
	 *
	 * @param countryId
	 * @return CountryDto
	 * @author TranLTH
	 */
	public CountryDto findCountryDtoById(@Param("countryId") Long countryId);

	/**
	 * findCountryIdLanguage
	 *
	 * @param countryId
	 * @return
	 * @author TranLTH
	 */
	public List<CountryLanguage> findCountryIdLanguage(@Param("countryId") Long countryId);

	/**
	 * findCountryLimitByConditionSQLServer
	 *
	 * @param offset
	 * @param sizeOfPage
	 * @param countrySearchDto
	 * @return
	 * @author phatvt
	 */
	public List<CountryDto> findCountryLimitByConditionOracle(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("countrySearchDto") CountrySearchDto countrySearchDto);

	public int countCountryByConditionForOracle(@Param("countrySearchDto") CountrySearchDto countrySearchDto);
}
