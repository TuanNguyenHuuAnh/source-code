/**
 * 
 */
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.CurrencyDto;
import vn.com.unit.cms.admin.all.dto.CurrencyListDto;
import vn.com.unit.cms.admin.all.dto.CurrencySearchDto;
import vn.com.unit.cms.admin.all.entity.Currency;
import vn.com.unit.db.repository.DbRepository;

/**
 * @author phunghn
 *
 */
public interface CurrencyRepository extends DbRepository<Currency, Long> {

	List<CurrencyDto> findByLangeCode(@Param("languageCode") String languageCode);

	CurrencyDto findByIdLangeCode(@Param("id") Long id, @Param("languageCode") String languageCode);

	public int countBySearchCondition(@Param("currencySearchDto") CurrencySearchDto currencySearchDto,
			@Param("languageCode") String languageCode);

	public List<CurrencyListDto> findBySearchCondition(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("currencySearchDto") CurrencySearchDto currencySearchDto,
			@Param("languageCode") String languageCode);

	public Currency getCurrency(@Param("id") Long id, @Param("language") String language);

	/**
	 * getMaxCode
	 *
	 * @author nhutnn
	 */
	String getMaxCode();

}
