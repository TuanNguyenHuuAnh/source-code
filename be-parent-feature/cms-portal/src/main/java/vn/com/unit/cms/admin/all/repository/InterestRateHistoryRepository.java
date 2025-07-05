/**
 * 
 */
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.InterestRateHistoryDto;
import vn.com.unit.cms.admin.all.entity.InterestRate;
import vn.com.unit.cms.admin.all.entity.InterestRateHistory;
import vn.com.unit.db.repository.DbRepository;

/**
 * @author sonnt
 *
 */
public interface InterestRateHistoryRepository extends DbRepository<InterestRate, Long> {

	public List<InterestRateHistoryDto> findAllByCity(@Param("offset") int offsetSQL, @Param("sizeOfPage") int pageSize,
			@Param("cityId") Long cityId);

	@Modifying
	public void insert(@Param("interestRateHistory") InterestRateHistory interestRateHistory);

	public int countByCity(@Param("cityId") Long cityId);

}
