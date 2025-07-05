/**
 * 
 */
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.ExchangeRateSearchDto;
import vn.com.unit.cms.admin.all.dto.ExchnageRateHistoryDto;
import vn.com.unit.cms.admin.all.entity.ExchnageRateHistory;
import vn.com.unit.db.repository.DbRepository;

/**
 * @author phunghn
 *
 */
public interface ExchnageRateHistoryRepository extends DbRepository<ExchnageRateHistory, Long> {

	List<ExchnageRateHistoryDto> findAllActive(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("exRateDto") ExchangeRateSearchDto exRateDto);

	int countFindAllActive(@Param("exRateDto") ExchangeRateSearchDto exRateDto);

}
