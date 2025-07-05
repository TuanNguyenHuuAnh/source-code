
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.InterestRateTitleDto;
import vn.com.unit.cms.admin.all.entity.InterestRateTitle;
import vn.com.unit.db.repository.DbRepository;

public interface InterestRateTitleRepository extends DbRepository<InterestRateTitle, Long> {

	/**
	 * findInterestRateTitleByType
	 *
	 * @param type
	 * @param customerTypeId
	 * @return
	 * @author nhutnn
	 */
	List<InterestRateTitleDto> findInterestRateTitleByType(@Param("type") String type,
			@Param("customerTypeId") Long customerTypeId);

	/**
	 * findInterestRateTitleByDto
	 *
	 * @param interestRateTitleDto
	 * @return
	 * @author nhutnn
	 */
	List<InterestRateTitleDto> findInterestRateTitleByDto(@Param("dto") InterestRateTitleDto interestRateTitleDto);

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

}
