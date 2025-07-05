/*******************************************************************************
 * Class        ：ExchangeRateServiceRepository
 * Created date ：2017/04/26
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;

import vn.com.unit.cms.admin.all.dto.ExchangeRateDto;
import vn.com.unit.cms.admin.all.dto.ExchangeRateSearchDto;
import vn.com.unit.cms.admin.all.entity.ExchangeRate;
import vn.com.unit.db.repository.DbRepository;
/**
* ExchangeRateServiceRepository
* 
* @version 01-00
* @since 01-00
* @author phunghn
*/
public interface ExchangeRateServiceRepository extends DbRepository<ExchangeRate, Long> {
    /**
     * findAllActive
     *
     * @param offset
     * @param sizeOfPage
     * @param exRateDto
     * @return List<ExchangeRateDto>
     * @author phunghn
     */
    List<ExchangeRateDto> findAllActive(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
            @Param("exRateDto") ExchangeRateSearchDto exRateDto);

    /**
     * countFindAllActive
     *
     * @param exRateDto
     * @return int
     * @author phunghn
     */
    int countFindAllActive(@Param("exRateDto") ExchangeRateSearchDto exRateDto);

    /**
     * getExchangeRateByCurrencyMaxDate
     *
     * @param mcurrencyId
     * @param displayDate
     * @return ExchangeRateDto
     * @author phunghn
     */
    ExchangeRateDto getExchangeRateByCurrencyMaxDate(@Param("mcurrencyId") Long mcurrencyId,
            @Param("displayDate") Date displayDate);

    /**
     * getMaxId
     *
     * @param offset
     * @param sizeOfPage
     * @param exRateDto
     * @return List<ExchangeRateDto>
     * @author phunghn
     */
    List<ExchangeRateDto> getMaxId(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
            @Param("exRateDto") ExchangeRateSearchDto exRateDto);

    /**
     * insertExchangeRate
     *
     * @param exRate
     * @author phunghn
     */
    @Modifying
    void insertExchangeRate(@Param("exRate") ExchangeRate exRate);

    /**
     * updateExchangeRate
     *
     * @param exRate
     * @author phunghn
     */
    @Modifying
    void updateExchangeRate(@Param("exRate") ExchangeRate exRate);

}
