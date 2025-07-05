
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.InterestRateValueDto;
import vn.com.unit.cms.admin.all.entity.InterestRateValue;
import vn.com.unit.db.repository.DbRepository;

public interface InterestRateValueRepository extends DbRepository<InterestRateValue, Long> {

	/**
	 * findInterestRateValueByDto
	 *
	 * @param interestRateValueDto
	 * @author nhutnn
	 */
	List<InterestRateValueDto> findInterestRateValueByDto(@Param("dto") InterestRateValueDto interestRateValueDto);

	/**
	 * deleteByListId
	 *
	 * @param deleteDate
	 * @param deleteBy
	 * @param listId
	 * @author nhutnn
	 */
	@Modifying
	void deleteByListId(@Param("deleteDate") Date deleteDate, @Param("deleteBy") String deleteBy,
			@Param("listId") List<Long> listId);

	/**
	 * countTotalTitleHaveValue
	 *
	 * @param interestRateType
	 * @author nhutnn
	 */
	Integer countTotalTitleHaveValue(@Param("interestRateType") String interestRateType);

}
