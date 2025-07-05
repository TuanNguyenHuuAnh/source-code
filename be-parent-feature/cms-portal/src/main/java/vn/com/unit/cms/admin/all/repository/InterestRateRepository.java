/**
 * 
 */
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.InterestRateSearchDto;
import vn.com.unit.cms.admin.all.entity.InterestRate;
import vn.com.unit.db.repository.DbRepository;

/**
 * @author sonnt
 *
 */
public interface InterestRateRepository extends DbRepository<InterestRate, Long> {

	public List<InterestRate> findBySearchCondition(
			@Param("interestRateSearchDto") InterestRateSearchDto interestRateSearchDto);

	@Modifying
	public void insertOrUpdate(@Param("interestRate") InterestRate interestRate);

}
